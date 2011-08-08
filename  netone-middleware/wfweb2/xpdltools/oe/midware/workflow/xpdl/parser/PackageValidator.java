/*--

 Copyright (C) 2002-2003 Aetrion LLC.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	obe@aetrion.com.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Aetrion LLC (obe@aetrion.com).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://www.openbusinessengine.org/>.

 */

package oe.midware.workflow.xpdl.parser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import oe.midware.workflow.xpdl.XPDLNames;
import oe.midware.workflow.xpdl.model.activity.*;
import oe.midware.workflow.xpdl.model.application.Application;
import oe.midware.workflow.xpdl.model.condition.Condition;
import oe.midware.workflow.xpdl.model.condition.ConditionType;
import oe.midware.workflow.xpdl.model.data.*;
import oe.midware.workflow.xpdl.model.ext.Event;
import oe.midware.workflow.xpdl.model.ext.EventType;
import oe.midware.workflow.xpdl.model.misc.*;
import oe.midware.workflow.xpdl.model.participant.Participant;
import oe.midware.workflow.xpdl.model.pkg.ExternalPackage;
import oe.midware.workflow.xpdl.model.pkg.PackageHeader;
import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.transition.*;
import oe.midware.workflow.xpdl.model.workflow.ProcessHeader;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.midware.workflow.xpdl.parser.dom4j.Dom4JXPDLParser;

// TODO: use standard XPath expressions for the prefix.

/**
 * Validates an XPDL package bean.
 *
 * @author Adrian Price
 */
public class PackageValidator implements PackageValidatorMessages {
    public static final String GRAPH_CONFORMANCE_MIN_PROP =
        "graph.conformance.minimum";
    private static final String PACKAGE = "Package";
    private static final String ACTIVITY = "Activity";
    private static final String ACTIVITY_SET = "ActivitySet";
    private static final String PKG_APPLICATION = "Application(Package)";
    private static final String APPLICATION = "Application";
    private static final String PKG_DATA_FIELD = "DataField(Package)";
    private static final String DATA_FIELD = "DataField";
    private static final String PKG_EVENT_TYPE = "Event(Package)";
    private static final String EVENT_TYPE = "Event";
    private static final String FORMAL_PARAMETER = "FormalParameter";
    private static final String PKG_PARTICIPANT = "Participant(Package)";
    private static final String PARTICIPANT = "Participant";
    private static final String TRANSITION = "Transition";
    private static final String TYPE_DECLARATION = "TypeDeclaration";
    private static final String WORKFLOW_PROCESS = "WorkflowProcess";
    private static final String WORKFLOW_FORMAL_PARAMETER = "FormalParameter(Workflow)";
    private static final String[][] DEFAULTS = {
        {GRAPH_CONFORMANCE_MIN_PROP, GraphConformance.NON_BLOCKED.toString()},
    };
    private static final Set _countryCodes = new TreeSet();
    private static final Properties _defaultProps = new Properties();
    private Properties _props = new Properties(_defaultProps);

    static {
        // Extract valid country codes from the available locales.
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            String country = locales[i].getCountry();
            if (country.length() > 0)
                _countryCodes.add(country);
        }
    }

    private static class ValidationContext {
        private XPDLPackage pkg;
        private Stack _prefixes = new Stack();
        private Map _uniqueIds = new HashMap();
        private String _prefix;
        private List _errors;
        private List _warnings;

        ValidationContext(XPDLPackage pkg) {
            this.pkg = pkg;
        }

        void popPrefix() {
            _prefixes.pop();
            generatePrefix();
        }

        String peekPrefix() {
            return (String)_prefixes.peek();
        }

        void pushPrefix(String prefix) {
            _prefixes.push(prefix);
            generatePrefix();
        }

        private void generatePrefix() {
            StringBuffer sb = new StringBuffer();
            for (Iterator i = _prefixes.iterator(); i.hasNext();) {
                sb.append('/');
                sb.append(i.next());
            }
            _prefix = sb.toString();
        }

        void checkValidUniqueId(Object src, String prefix, String priKey,
            String id, Object obj) {

            checkValidUniqueId(src, prefix, priKey, null, null, id, obj);
        }

        void checkValidUniqueId(Object src, String prefix, String priKey,
            String secKey, String id, Object obj) {

            checkValidUniqueId(src, prefix, priKey, secKey, null, id, obj);
        }

        void checkValidUniqueId(Object src, String prefix, String priKey,
            String secKey, String tertKey, String id, Object obj) {

            String owner = prefix != null ? prefix : peekPrefix();
            if (prefix == null)
                prefix = "";
            if (id == null || id.trim().length() == 0) {
                addError(src, ID_MUST_BE_SPECIFIED, new Object[]{owner},
                    prefix + "/Id must be specified");
            } else {
                // Make sure this is a valid NMTOKEN string.
                if (!isValidNMToken(id)) {
                    addError(src, INVALID_NMTOKEN, new Object[]{owner},
                        prefix + "/Id is not a valid NMTOKEN");
                }

                // Make sure it isn't already in use.
                Map uniqueIds = getUniqueIds(priKey);
                boolean exists = uniqueIds.containsKey(id);
                if (!exists && secKey != null)
                    exists = getUniqueIds(secKey).containsKey(id);
                if (!exists && tertKey != null)
                    exists = getUniqueIds(tertKey).containsKey(id);
                if (exists) {
                    addError(src, ID_ALREADY_DEFINED, new Object[]{owner},
                        prefix +
                        " is already defined. Remove the duplicate entity or assign it a unique ID.");
                } else {
                    uniqueIds.put(id, obj);
                }
            }
        }

        /**
         * Checks whether a string is a valid XML Schema
         * <a href="http://www.w3.org/TR/2000/WD-xml-2e-20000814#NT-Nmtoken">
         * NMTOKEN</a>.
         *
         * @param id The string to check.
         * @return <code>true</code> if it is a valid NMTOKEN.
         */
        private boolean isValidNMToken(String id) {
            // TODO: Handle Unicode CombiningChar and Extender
            // CombiningChar    ::=    [#x0300-#x0345] | [#x0360-#x0361] |
            // [#x0483-#x0486] | [#x0591-#x05A1] | [#x05A3-#x05B9] |
            // [#x05BB-#x05BD] | #x05BF | [#x05C1-#x05C2] | #x05C4 |
            // [#x064B-#x0652] | #x0670 | [#x06D6-#x06DC] | [#x06DD-#x06DF] |
            // [#x06E0-#x06E4] | [#x06E7-#x06E8] | [#x06EA-#x06ED] |
            // [#x0901-#x0903] | #x093C | [#x093E-#x094C] | #x094D |
            // [#x0951-#x0954] | [#x0962-#x0963] | [#x0981-#x0983] | #x09BC |
            // #x09BE | #x09BF | [#x09C0-#x09C4] | [#x09C7-#x09C8] |
            // [#x09CB-#x09CD] | #x09D7 | [#x09E2-#x09E3] | #x0A02 | #x0A3C |
            // #x0A3E | #x0A3F | [#x0A40-#x0A42] | [#x0A47-#x0A48] |
            // [#x0A4B-#x0A4D] | [#x0A70-#x0A71] | [#x0A81-#x0A83] | #x0ABC |
            // [#x0ABE-#x0AC5] | [#x0AC7-#x0AC9] | [#x0ACB-#x0ACD] |
            // [#x0B01-#x0B03] | #x0B3C | [#x0B3E-#x0B43] | [#x0B47-#x0B48] |
            // [#x0B4B-#x0B4D] | [#x0B56-#x0B57] | [#x0B82-#x0B83] |
            // [#x0BBE-#x0BC2] | [#x0BC6-#x0BC8] | [#x0BCA-#x0BCD] | #x0BD7 |
            // [#x0C01-#x0C03] | [#x0C3E-#x0C44] | [#x0C46-#x0C48] |
            // [#x0C4A-#x0C4D] | [#x0C55-#x0C56] | [#x0C82-#x0C83] |
            // [#x0CBE-#x0CC4] | [#x0CC6-#x0CC8] | [#x0CCA-#x0CCD] |
            // [#x0CD5-#x0CD6] | [#x0D02-#x0D03] | [#x0D3E-#x0D43] |
            // [#x0D46-#x0D48] | [#x0D4A-#x0D4D] | #x0D57 | #x0E31 |
            // [#x0E34-#x0E3A] | [#x0E47-#x0E4E] | #x0EB1 | [#x0EB4-#x0EB9] |
            // [#x0EBB-#x0EBC] | [#x0EC8-#x0ECD] | [#x0F18-#x0F19] | #x0F35 |
            // #x0F37 | #x0F39 | #x0F3E | #x0F3F | [#x0F71-#x0F84] |
            // [#x0F86-#x0F8B] | [#x0F90-#x0F95] | #x0F97 | [#x0F99-#x0FAD] |
            // [#x0FB1-#x0FB7] | #x0FB9 | [#x20D0-#x20DC] | #x20E1 |
            // [#x302A-#x302F] | #x3099 | #x309A
            //
            // Extender ::= #x00B7 | #x02D0 | #x02D1 | #x0387 | #x0640 |
            // #x0E46 | #x0EC6 | #x3005 | [#x3031-#x3035] | [#x309D-#x309E] |
            // [#x30FC-#x30FE
            //
            // NameChar ::= Letter | Digit  | '.' | '-' | '_' | ':' |
            // CombiningChar | Extender
            //
            // Nmtoken ::= (NameChar)+

            boolean valid = true;
            for (int i = 0, n = id.length(); i < n; i++) {
                char c = id.charAt(i);
                if (!Character.isLetterOrDigit(c) && c != '.' && c != '-' &&
                    c != '_') {

                    valid = false;
                    break;
                }
            }
            return valid;
        }

        Object checkValidIdRef(Object src, String prefix, String primaryKey,
            String refId) {

            return checkValidIdRef(src, prefix, primaryKey, null, null, refId);
        }

        Object checkValidIdRef(Object src, String prefix, String primaryKey,
            String secondaryKey, String refId) {

            return checkValidIdRef(src, prefix, primaryKey, secondaryKey, null,
                refId);
        }

        Object checkValidIdRef(Object src, String prefix, String primaryKey,
            String secondaryKey, String tertiaryKey, String refId) {

            String owner = prefix != null ? prefix : peekPrefix();
            if (prefix == null)
                prefix = "";
            Object obj = getUniqueIds(primaryKey).get(refId);
            if (obj == null && secondaryKey != null)
                obj = getUniqueIds(secondaryKey).get(refId);
            if (obj == null && tertiaryKey != null)
                obj = getUniqueIds(tertiaryKey).get(refId);
            if (obj == null) {
                addError(src, UNDEFINED_REFERENCE,
                    new Object[]{owner, primaryKey, refId}, prefix +
                    " references an undefined " + primaryKey + '[' + refId +
                    ']');
            }
            return obj;
        }

        void resetUniqueId(String key) {
            getUniqueIds(key).clear();
        }

        boolean checkNotNull(Object src, String prefix, Object obj) {
            if (prefix == null)
                prefix = "";
            if (obj instanceof String) {
                String s = ((String)obj).trim();
                if (s.length() == 0)
                    obj = null;
            }
            boolean b = obj != null;
            if (!b) {
                addError(src, PROPERTY_MISSING,
                    new Object[]{peekPrefix(), prefix},
                    prefix + " must be specified");
            }
            return b;
        }

        boolean checkEQ(Object src, String prefix, String property,
            int expected, int actual) {

            if (prefix == null)
                prefix = "";
            boolean b = actual == expected;
            if (!b) {
                addError(src, COUNT_INCORRECT,
                    new Object[]{peekPrefix(), property,
                                 new Integer(expected),
                                 new Integer(actual)},
                    prefix + ": expected <" + expected + ">, actual <" +
                    actual + '>');
            }
            return b;
        }

        boolean checkGE(Object src, String prefix, String property,
            int expected, int actual) {

            if (prefix == null)
                prefix = "";
            boolean b = actual >= expected;
            if (!b) {
                addError(src, PROPERTY_VALUE_TOO_LOW,
                    new Object[]{peekPrefix(), property,
                                 new Integer(expected), new Integer(actual)},
                    prefix + ": expected <" + expected + ">, actual <" +
                    actual + '>');
            }
            return b;
        }

        public void checkAssignable(Object src, String prefix, Type toType,
            Type fromType) {

            if (!toType.isAssignableFrom(fromType)) {
                addError(src, UNSUPPORTED_TYPE_CONVERSION,
                    new Object[]{fromType, toType},
                    "Unsupported type conversion: from <" + fromType +
                    "> to <" + toType + '>');
            }
        }

        void addError(Object src, int msgCode, Object[] args, String msg) {
            if (_errors == null)
                _errors = new ArrayList();
            _errors.add(new ValidationError(ValidationError.TYPE_ERROR, msgCode,
                args, src, _prefix + msg));
        }

        void addWarning(Object src, int msgCode, Object[] args, String msg) {
            if (_warnings == null)
                _warnings = new ArrayList();
            _warnings.add(new ValidationError(ValidationError.TYPE_WARNING,
                msgCode, args, src, _prefix + msg));
        }

        ValidationError[] getErrors() {
            return _errors == null
                ? null
                : (ValidationError[])_errors.toArray(
                    new ValidationError[_errors.size()]);
        }

        ValidationError[] getWarnings() {
            return _warnings == null
                ? null
                : (ValidationError[])_warnings.toArray(
                    new ValidationError[_warnings.size()]);
        }

        private Map getUniqueIds(String key) {
            Map ids = (Map)_uniqueIds.get(key);
            if (ids == null) {
                ids = new HashMap();
                _uniqueIds.put(key, ids);
            }
            return ids;
        }
    }

    static {
        for (int i = 0; i < DEFAULTS.length; i++) {
            String[] entry = DEFAULTS[i];
            _defaultProps.setProperty(entry[0], entry[1]);
        }
    }

    public static void main(String[] args) {
        try {
            XPDLParser parser = new Dom4JXPDLParser();
            if (args.length == 0)
                usage();
            int i = 0;
            Properties props = null;
            if (args[i].startsWith("-props")) {
                String propfile = args[i++];
                if (i == args.length)
                    usage();
                props = new Properties();
                InputStream in = new FileInputStream(propfile);
                props.load(in);
                in.close();
            }
            PackageValidator validator = new PackageValidator(props);
            for (; i < args.length; i++) {
                String src = args[i];
                Reader rdr = new FileReader(src);
                XPDLPackage pkg = parser.parse(rdr);
                rdr.close();
                ValidationError[] errors = validator.validate(pkg, false);
                if (errors == null)
                    System.out.println(src + " is a valid XPDL package");
                else {
                    System.err.println(src + " contains errors:\n");
                    for (int j = 0; j < errors.length; j++)
                        System.err.println(errors[j].getMessage());
                    System.err.println("\n" + errors.length + " errors\n");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void usage() {
        System.out.println("usage: java org.obe.xpdl.parser.PackageValidator [-props propfile] file...");
        System.exit(1);
    }

    public PackageValidator() {
    }

    public PackageValidator(Properties props) {
        if (props != null)
            _props.putAll(props);
    }

    public String getProperty(String key) {
        return _props.getProperty(key);
    }

    public Object setProperty(String key, String value) {
        return _props.setProperty(key, value);
    }

    public ValidationError[] validate(XPDLPackage pkg, boolean throwException)
        throws InvalidPackageException {

        // Make sure all transitions connect to their associated activities.
        for (int i = 0, n = pkg.getWorkflowProcess().length; i < n; i++)
            pkg.getWorkflowProcess(i).resolveReferences();

        ValidationContext ctx = new ValidationContext(pkg);
        checkPackage(ctx);

        ValidationError[] errors = ctx.getErrors();
        if (errors != null && throwException)
            throw new InvalidPackageException(pkg.getId(), errors);
        return errors;
    }

    private void checkPackage(ValidationContext ctx) {
        XPDLPackage pkg = ctx.pkg;
        ctx.pushPrefix("Package[" + pkg.getId() + ']');
        ctx.checkValidUniqueId(pkg, null, PACKAGE, pkg.getId(), pkg);
        checkPackageHeader(ctx);
        checkRedefinableHeader(pkg.getRedefinableHeader(), ctx);
        checkConformanceClass(ctx);
        checkScript(ctx);
        checkExternalPackages(ctx);
        checkTypeDeclarations(ctx);
        checkParticipants(pkg.getParticipant(), true, ctx);
        checkApplications(pkg.getApplication(), true, ctx);
        checkDataFields(pkg.getDataField(), true, ctx);
        checkWorkflows(ctx);
        ctx.popPrefix();
    }

    private void checkPackageHeader(ValidationContext ctx) {
        ctx.pushPrefix("PackageHeader");
        PackageHeader hdr = ctx.pkg.getPackageHeader();
        ctx.checkNotNull(hdr, "/XPDLVersion", hdr.getXPDLVersion());
        ctx.checkNotNull(hdr, "/Vendor", hdr.getVendor());
        ctx.checkNotNull(hdr, "/Created", hdr.getCreated());
        ctx.popPrefix();
    }

    private void checkRedefinableHeader(RedefinableHeader hdr,
        ValidationContext ctx) {

        ctx.pushPrefix("RedefinableHeader");
        if (hdr != null) {
            // TODO: enable codepage check in JDK1.4 only.
//        String codepage = hdr.getCodepage();
//        if (codepage != null && Charset.isSupported(codepage)) {
//            ctx.addError(hdr, UNSUPPORTED_CODEPAGE, new Object[]{codepage},
//                "Unsupported codepage: " + codepage);
//        }

            String country = hdr.getCountrykey();
            if (country != null && !_countryCodes.contains(country)) {
                ctx.addWarning(hdr, UNSUPPORTED_COUNTRY, new Object[]{country},
                    "Unsupported country: " + country);
            }
        }
        ctx.popPrefix();
    }

    // Ensure a minimum graph conformance level, and that the activity net
    // does actually conform to that level.
    private void checkConformanceClass(ValidationContext ctx) {
        ConformanceClass cc = ctx.pkg.getConformanceClass();
        GraphConformance gc = cc == null ? null : cc.getGraphConformance();
        if (gc == null)
            gc = GraphConformance.NON_BLOCKED;
        String gcl = getProperty(GRAPH_CONFORMANCE_MIN_PROP);
        ctx.checkGE(ctx.pkg, " Graph conformance level must be at least " + gcl,
            XPDLNames.GRAPH_CONFORMANCE,
            GraphConformance.valueOf(gcl).value(), gc.value());
        // TODO: verify activity nets conform to declared graph conformance class.
    }

    private void checkScript(ValidationContext ctx) {
        Script script = ctx.pkg.getScript();
        if (script != null) {
            String type = script.getType();
            ctx.pushPrefix("Script[" + type + ']');
            if (type == null ||
                !type.startsWith("text/x-") &&
                !type.startsWith("text/xml/x-")) {

                ctx.addError(ctx.pkg, INVALID_SCRIPT, new Object[]{script},
                    " is not a valid script declaration. The script type is" +
                    " an extended MIME Media Type and must start with " +
                    "\"text/x-\" or \"text/xml/x-\"");
            }
            ctx.popPrefix();
        }
    }

    private void checkExternalPackages(ValidationContext ctx) {
        ExternalPackage[] pkgs = ctx.pkg.getExternalPackage();
        for (int i = 0; i < pkgs.length; i++) {
            ExternalPackage pkg = pkgs[i];
            ctx.checkNotNull(ctx.pkg,
                "/ExternalPackages/ExternalPackage[" + i + "]/@href",
                pkg.getHref());
        }
    }

    private void checkTypeDeclarations(ValidationContext ctx) {
        ctx.resetUniqueId(TYPE_DECLARATION);
        TypeDeclaration[] typeDeclarations = ctx.pkg.getTypeDeclaration();
        if (typeDeclarations != null) {
            for (int i = 0, n = typeDeclarations.length; i < n; i++)
                checkTypeDeclaration(typeDeclarations[i], ctx);
        }
    }

    private void checkTypeDeclaration(TypeDeclaration typeDecl,
        ValidationContext ctx) {

        ctx.pushPrefix("TypeDeclaration[" + typeDecl.getId() + ']');
        ctx.checkValidUniqueId(typeDecl, null, TYPE_DECLARATION,
            typeDecl.getId(), typeDecl);
        Type type = typeDecl.getType();
        if (type == null) {
            ctx.addError(ctx.pkg, INVALID_TYPE_DECL, null, typeDecl +
                " is not a valid type declaration: a type is required.");
        }
        ctx.popPrefix();
    }

    private void checkParticipants(Participant[] participants,
        boolean pkgLevel, ValidationContext ctx) {

        String pri = pkgLevel ? PKG_PARTICIPANT : PARTICIPANT;
        String sec = pkgLevel ? null : PKG_PARTICIPANT;
        ctx.resetUniqueId(pri);
        if (participants != null) {
            for (int i = 0, n = participants.length; i < n; i++) {
                Participant particip = participants[i];
                ctx.checkValidUniqueId(particip, "/Participant[" +
                    particip.getId() + ']', pri, sec, particip.getId(),
                    particip);
            }
        }
    }

    private void checkApplications(Application[] applications, boolean pkgLevel,
        ValidationContext ctx) {

        String pri = pkgLevel ? PKG_APPLICATION : APPLICATION;
        String sec = pkgLevel ? null : PKG_APPLICATION;
        ctx.resetUniqueId(pri);
        if (applications != null) {
            for (int i = 0, n = applications.length; i < n; i++) {
                Application app = applications[i];
                ctx.pushPrefix("Application[" + app.getId() + ']');
                ctx.checkValidUniqueId(app, null, pri, sec, app.getId(), app);
                checkFormalParameters(app.getFormalParameter(), false, ctx);
                ctx.popPrefix();
            }
        }
    }

    private void checkFormalParameters(FormalParameter[] parameters,
        boolean wfLevel, ValidationContext ctx) {

        String key = wfLevel ? WORKFLOW_FORMAL_PARAMETER : FORMAL_PARAMETER;
        ctx.resetUniqueId(key);
        if (parameters != null) {
            for (int i = 0, n = parameters.length; i < n; i++) {
                FormalParameter parm = parameters[i];
                ctx.checkValidUniqueId(parm, "/FormalParameter[" +
                    parm.getId() + ']', key, parm.getId(), parm);
            }
        }
    }

    private void checkDataFields(DataField[] dataFields,
        boolean pkgLevel, ValidationContext ctx) {

        ctx.resetUniqueId(pkgLevel ? PKG_DATA_FIELD : DATA_FIELD);
        if (dataFields != null) {
            for (int i = 0, n = dataFields.length; i < n; i++)
                checkDataField(dataFields[i], pkgLevel, ctx);
        }
    }

    private void checkDataField(DataField dataField,
        boolean pkgLevel, ValidationContext ctx) {

        String pri = pkgLevel ? PKG_DATA_FIELD : DATA_FIELD;
        String sec = pkgLevel ? null : WORKFLOW_FORMAL_PARAMETER;
        String tert = pkgLevel ? null : PKG_DATA_FIELD;
        String dataFieldId = dataField.getId();
        ctx.pushPrefix("DataField[" + dataFieldId + ']');
        ctx.checkValidUniqueId(dataField, null, pri, sec, tert, dataFieldId,
            dataField);
        DataType dataType = dataField.getDataType();
        Type type = dataType == null ? null : dataType.getType();
        ctx.checkNotNull(dataField, "/Type", type);
        if (type instanceof BasicType) {
        } else if (type instanceof DeclaredType) {
            ctx.checkValidIdRef(dataField, "/Id", TYPE_DECLARATION,
                ((DeclaredType)type).getId());
        } else if (type instanceof SchemaType) {
        } else if (type instanceof ExternalReference) {
        } else if (type instanceof RecordType) {
        } else if (type instanceof UnionType) {
        } else if (type instanceof EnumerationType) {
        } else if (type instanceof ArrayType) {
        } else if (type instanceof ListType) {
        }
        ctx.popPrefix();
    }

    private void checkWorkflows(ValidationContext ctx) {
        ctx.resetUniqueId(WORKFLOW_PROCESS);
        WorkflowProcess[] workflowProcesses = ctx.pkg.getWorkflowProcess();
        if (workflowProcesses != null) {
            for (int i = 0, n = workflowProcesses.length; i < n; i++)
                checkWorkflow(workflowProcesses[i], ctx);
        }
    }

    private void checkWorkflow(WorkflowProcess workflow,
        ValidationContext ctx) {

        ctx.resetUniqueId(DATA_FIELD);
        ctx.resetUniqueId(WORKFLOW_FORMAL_PARAMETER);
        ctx.resetUniqueId(ACTIVITY);
        ctx.resetUniqueId(TRANSITION);
        String workflowId = workflow.getId();
        ctx.pushPrefix("Workflow[" + workflowId + ']');
        ctx.checkValidUniqueId(workflow, null, WORKFLOW_PROCESS, workflowId,
            workflow);
        checkProcessHeader(workflow, ctx);
        checkRedefinableHeader(workflow.getRedefinableHeader(), ctx);
        checkFormalParameters(workflow.getFormalParameter(), true, ctx);
        checkDataFields(workflow.getDataField(), false, ctx);
        checkParticipants(workflow.getParticipant(), false, ctx);
        checkApplications(workflow.getApplication(), false, ctx);
        checkActivitySets(workflow.getActivitySet(), ctx);
        checkActivities(workflow, workflow.getActivity(), ctx);
        checkTransitions(workflow.getTransition(), ctx);
        ctx.popPrefix();
    }

    private void checkProcessHeader(WorkflowProcess workflow,
        ValidationContext ctx) {

        ProcessHeader hdr = workflow.getProcessHeader();
        Date validFrom = hdr.getValidFrom();
        Date validTo = hdr.getValidTo();
        if (validFrom != null && validTo != null && validTo.before(validFrom)) {
            ctx.addError(workflow, INVALID_FROM_TO_DATES,
                new Object[]{validFrom, validTo},
                "Invalid ValidFrom/ValidTo specification: " + validFrom + '/' +
                validTo + ". ValidFrom must pre-date ValidTo");
        }
    }

    private void checkActivitySets(ActivitySet[] activitySets,
        ValidationContext ctx) {

        ctx.resetUniqueId(ACTIVITY_SET);
        if (activitySets != null) {
            for (int i = 0, n = activitySets.length; i < n; i++)
                checkActivitySet(activitySets[i], ctx);
        }
    }

    private void checkActivitySet(ActivitySet activitySet,
        ValidationContext ctx) {

        String id = activitySet.getId();
        ctx.pushPrefix("ActivitySet[" + id + ']');
        ctx.checkValidUniqueId(activitySet, null, ACTIVITY_SET, id, activitySet);
        checkActivities(activitySet, activitySet.getActivity(), ctx);
        checkTransitions(activitySet.getTransition(), ctx);
        ctx.popPrefix();
    }

    private void checkActivities(Object src, Activity[] activities,
        ValidationContext ctx) {

        if (activities != null) {
            boolean foundStart = false;
            boolean foundExit = false;
            for (int i = 0, n = activities.length; i < n; i++) {
                Activity activity = activities[i];
                checkActivity(activity, ctx);
                if (activity.isStartActivity())
                    foundStart = true;
                if (activity.isExitActivity())
                    foundExit = true;
            }
            if (!foundStart) {
                ctx.addError(src, START_ACTIVITY_REQUIRED,
                    new Object[]{ctx.peekPrefix()},
                    " must have at least one start activity (one with no afferent (inbound) transitions)");
            }
            if (!foundExit) {
                ctx.addError(src, EXIT_ACTIVITY_REQUIRED,
                    new Object[]{ctx.peekPrefix()},
                    " must have at least one exit activity (one with no efferent (outbound) transitions)");
            }
        }
    }

    private void checkActivity(Activity activity, ValidationContext ctx) {
        String activityId = activity.getId();
        ctx.pushPrefix("Activity[" + activityId + ']');
        ctx.checkValidUniqueId(activity, null, ACTIVITY, activityId, activity);
        String performer = activity.getPerformer();
        if (performer != null) {
            StringTokenizer strTok = new StringTokenizer(performer, ",");
            while (strTok.hasMoreTokens()) {
                ctx.checkValidIdRef(activity, "/Performer", PARTICIPANT,
                    PKG_PARTICIPANT, strTok.nextToken());
            }
        }
        int bodyCount = 0;
        BlockActivity blk = activity.getBlockActivity();
        if (blk != null) {
            String blockId = blk.getBlockId();
            if (ctx.checkNotNull(activity, "/BlockActivity/Id", blockId) &&
                blk.getActivitySet() == null) {

                ctx.addError(activity, UNDEFINED_ACTIVITY_SET,
                    new Object[]{ctx.peekPrefix(), blockId},
                    "/BlockActivity references an undefined ActivitySet[" +
                    blockId + ']');
            }
            bodyCount++;
        }
        Implementation impl = activity.getImplementation();
        if (impl != null) {
            ctx.pushPrefix("Implementation");
            if (impl instanceof NoImplementation) {
                // Nothing to check.
            } else if (impl instanceof SubFlow) {
                SubFlow subFlow = (SubFlow)impl;
                String subFlowId = subFlow.getId();
                ctx.pushPrefix("SubFlow[" + subFlowId + ']');
                if (ctx.checkNotNull(subFlow, null, subFlowId)) {
                    boolean found = false;
                    for (int i = 0, n = ctx.pkg.getWorkflowProcess().length;
                         i < n; i++) {

                        WorkflowProcess workflow = ctx.pkg.getWorkflowProcess(i);
                        if (workflow.getId().equals(subFlowId)) {
                            checkActualParameters(subFlow,
                                workflow.getFormalParameter(),
                                subFlow.getActualParameter(), ctx);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ctx.addError(activity, UNDEFINED_SUBPROCESS,
                            new Object[]{activityId, subFlowId},
                            " references an undefined WorkflowProcess[" +
                            subFlowId + ']');
                    }
                }
                ctx.popPrefix();
            } else if (impl instanceof ToolSet) {
                ToolSet toolSet = (ToolSet)impl;
                for (int j = 0,  n = toolSet.getTool().length; j < n; j++) {
                    Tool tool = toolSet.getTool(j);
                    String toolId = tool.getId();
                    if (ctx.checkNotNull(tool, "/Tool/Id", toolId)) {
                        ctx.pushPrefix("Tool[" + toolId + ']');
                        Application app = (Application)ctx.checkValidIdRef(
                            tool, null, APPLICATION, PKG_APPLICATION, toolId);
                        ctx.checkNotNull(tool, "/Type", tool.getToolType());
                        if (app != null) {
                            ExternalReference extRef =
                                app.getExternalReference();
                            if (extRef == null) {
                                checkActualParameters(tool,
                                    app.getFormalParameter(),
                                    tool.getActualParameter(), ctx);
                            } else {
                                checkActualParameters(tool, extRef,
                                    tool.getActualParameter(), ctx);
                            }
                        }
                        ctx.popPrefix();
                    }
                }
            }
            bodyCount++;
            ctx.popPrefix();
        }
        if (activity.getRoute() != null) {
            if (performer != null)
                ctx.addError(activity, ROUTE_CANNOT_HAVE_PERFORMER,
                    new Object[]{activityId}, "/Route cannot have a Performer");
            bodyCount++;
        }
        if (bodyCount != 1) {
            ctx.addError(activity, ACTIVITY_BODY_MISSING,
                new Object[]{activityId},
                " must include only one of: Route, Implementation, or BlockActivity");
        }

        Deadline[] deadlines = activity.getDeadline();
        if (deadlines != null) {
            Set deadlineExceptions = new HashSet();
            ctx.pushPrefix("Deadline");
            int syncCount = 0;
            for (int i = 0, n = deadlines.length; i < n; i++) {
                Deadline deadline = deadlines[i];
                // TODO: validate condition.
                String condition = deadline.getDeadlineCondition();
                ctx.checkNotNull(deadline, "/Condition", condition);
                String exceptionName = deadline.getExceptionName();
                ctx.checkNotNull(deadline, "/Exception", exceptionName);
                deadlineExceptions.add(exceptionName);
                if (deadline.getExecutionType() == ExecutionType.SYNCHRONOUS &&
                    ++syncCount == 2) {

                    ctx.addError(activity, MAX_ONE_SYNC_DEADLINE,
                        new Object[]{activityId},
                        " can only have one synchronous deadline");
                }
            }
            // Check that all deadline exceptions are handled.
            Map transitions = activity.getEfferentTransitions();
            if (transitions!= null) {
                for (Iterator i = transitions.values().iterator();
                     i.hasNext();) {

                    Transition transition = (Transition)i.next();
                    Condition condition = transition.getCondition();
                    if (condition != null) {
                        ConditionType type = condition.getType();
                        if (type == ConditionType.DEFAULTEXCEPTION) {
                            deadlineExceptions.clear();
                            break;
                        } else if (type == ConditionType.EXCEPTION) {
                            deadlineExceptions.remove(condition.getValue());
                        }
                    }
                    if (transition.getExecution() != null &&
                        deadlines.length > 0) {

                        ctx.addError(activity, INCOMPATIBLE_TRANSITION,
                            new Object[]{activityId, transition.getId()},
                            " is incompatible with Transition[" +
                            transition.getId() + "]/ExtendedAttribute[" +
                            XPDLNames.EXECUTION + ']');
                    }
                }
            }
            for (Iterator i = deadlineExceptions.iterator(); i.hasNext();) {
                String exception = (String)i.next();
                ctx.addError(activity, EXCEPTION_NOT_HANDLED,
                    new Object[]{activityId, exception},
                    "/ExceptionName[" + exception +
                    "] is not handled by any efferent (outbound) transition");
            }
            ctx.popPrefix();
        }

        TransitionRestriction[] restrictions =
            activity.getTransitionRestriction();
        SplitType splitType = null;
        boolean joinFound = false;
        boolean splitFound = false;
        boolean otherwiseFound = false;
        boolean conditionFound = false;
        boolean nonConditionFound = false;
        if (restrictions != null) {
            ctx.pushPrefix("TransitionRestriction");
            for (int i = 0, n = restrictions.length; i < n; i++) {
                TransitionRestriction restriction = restrictions[i];
                Join join = restriction.getJoin();
                if (join != null) {
                    // Only one join is permitted.
                    if (joinFound) {
                        ctx.addError(activity,
                            TRANSITION_RESTRICTIONS_ONLY_ONE_JOIN,
                            new Object[]{activityId},
                            " TransitionRestrictions can only contain one join.");
                    }
                    joinFound = true;
                }
                Split split = restriction.getSplit();
                if (split != null) {
                    // Only one split is permitted.
                    if (splitFound) {
                        ctx.addError(activity,
                            TRANSITION_RESTRICTIONS_ONLY_ONE_SPLIT,
                            new Object[]{activityId},
                            " TransitionRestrictions can only contain one split in OBE (XPDL permits more, but does not define the semantics).");
                    }
                    splitFound = true;
                    splitType = split.getType();
                    ctx.checkNotNull(split, "/Split/Type", splitType);
                    if (splitType == SplitType.XOR) {
                        ctx.pushPrefix("Split[XOR]");
                        String[] transitionRefs = split.getTransitionReference();
                        if (transitionRefs == null) {
                            ctx.addError(activity, SPLIT_MISSING_REFERENCES,
                                new Object[]{activityId},
                                " does not reference any transitions");
                        } else {
                            // TransitionRefs must refer to valid transitions.
                            for (int j = 0, p = transitionRefs.length;
                                 j < p; j++) {

                                String transitionId = transitionRefs[j];
                                if (!activity.getEfferentTransitions()
                                    .containsKey(transitionId)) {

                                    ctx.addError(activity,
                                        INVALID_TRANSITION_REF,
                                        new Object[]{activityId, transitionId},
                                        "/TransitionRef[" + transitionId +
                                        "] references non-efferent (outbound) Transition");
                                }
                            }
                            // Efferent transitions must have TransitionRefs
                            // (other than exception and event transitions).
                            for (Iterator j = activity.getEfferentTransitions()
                                .values().iterator(); j.hasNext();) {

                                Transition transition = (Transition)j.next();
                                Condition condition = transition.getCondition();
                                ConditionType conditionType =
                                    condition == null
                                    ? null : condition.getType();
                                Event event = transition.getEvent();
                                if (event == null) {
                                    if (conditionType == null ||
                                        conditionType == ConditionType.CONDITION ||
                                        conditionType == ConditionType.OTHERWISE) {

                                        if (conditionType == null)
                                            nonConditionFound = true;
                                        else if (conditionType == ConditionType.CONDITION)
                                            conditionFound = true;

                                        String transitionId = transition.getId();
                                        boolean found = false;
                                        for (int k = 0; k <
                                            transitionRefs.length; k++) {
                                            if (transitionRefs[k].equals(
                                                transitionId)) {

                                                found = true;
                                                break;
                                            }
                                        }
                                        if (!found) {
                                            ctx.addError(activity,
                                                TRANSITION_REF_MISSING,
                                                new Object[]{activityId, transitionId},
                                                " does not reference Transition[" +
                                                transitionId + ']');
                                        }
                                    }
                                } else {
                                    if (conditionType == ConditionType.DEFAULTEXCEPTION ||
                                        conditionType == ConditionType.EXCEPTION ||
                                        conditionType == ConditionType.OTHERWISE) {

                                        ctx.addError(transition,
                                            EVENT_TRANSITION_MISMATCH,
                                            new Object[]{transition.getId(),
                                                         conditionType},
                                            " OBE event transitions cannot be " +
                                            "used in conjunction with an XPDL " +
                                            "transition type of " +
                                            conditionType);
                                    } else {
                                        // TODO: move the event check elsewhere.
                                        // Check that the EventType is declared.
                                        String id = event.getId();
                                        String prefix = "/Event[" + id + ']';
                                        EventType eventType = (EventType)
                                            ctx.checkValidIdRef(transition,
                                                prefix, EVENT_TYPE,
                                                PKG_EVENT_TYPE, id);

                                        // Check the event parameters.
                                        checkActualParameters(transition,
                                            eventType.getFormalParameter(),
                                            event.getActualParameter(), ctx);

                                        // Check that any DataField is declared.
                                        String dataFieldId =
                                            event.getDataField();
                                        if (dataFieldId != null) {
                                            DataField dataField = (DataField)
                                                ctx.checkValidIdRef(transition,
                                                    prefix, DATA_FIELD,
                                                    FORMAL_PARAMETER,
                                                    PKG_DATA_FIELD,
                                                    dataFieldId);

                                            // Check that the EventType and
                                            // DataField are compatible.
                                            if (dataField != null) {
                                                DataType dataType = dataField
                                                    .getDataType();
                                                Type type = dataType != null
                                                    ? null : dataType.getType();
                                                if (type != null) {
                                                    // TODO: check type?
                                                }
                                            }
                                        }
                                    }
                                }
                                if (conditionType == ConditionType.OTHERWISE) {
                                    if (otherwiseFound) {
                                        ctx.addError(transition,
                                            OTHERWISE_ALREADY_DEFINED,
                                            new Object[]{activityId},
                                            " an OTHERWISE transition has already been defined");
                                    }
                                    otherwiseFound = true;
                                }
                            }
                        }
                        ctx.popPrefix();
                    } else {
                        for (Iterator j = activity.getEfferentTransitions()
                            .values().iterator(); j.hasNext();) {

                            Transition transition = (Transition)j.next();
                            Condition condition = transition.getCondition();
                            ConditionType transitionType =
                                condition == null
                                ? null
                                : condition.getType();
                            if (transitionType == ConditionType.OTHERWISE) {
                                if (otherwiseFound) {
                                    ctx.addError(transition,
                                        OTHERWISE_ALREADY_DEFINED,
                                        new Object[]{activityId},
                                        " an OTHERWISE transition has already been defined");
                                }
                                otherwiseFound = true;
                            }
                        }
                    }
                }
            }
            ctx.popPrefix();
        }
        if (!joinFound) {
            int count = activity.getAfferentTransitions().size();
            if (count > 1) {
                ctx.addError(activity, JOIN_REQUIRED,
                    new Object[]{activityId, new Integer(count)},
                    " has " + count +
                    " afferent (inbound) transitions, and therefore requires a Join");
            }
        }
        if (!splitFound) {
            int count = 0;
            for (Iterator i = activity.getEfferentTransitions().values()
                .iterator(); i.hasNext();) {

                Transition transition = (Transition)i.next();
                Condition condition = transition.getCondition();
                ConditionType transitionType =
                    condition == null ? null : condition.getType();
                if (transition.getEvent() == null &&
                    (transitionType == null ||
                    transitionType == ConditionType.CONDITION ||
                    transitionType == ConditionType.OTHERWISE)) {

                    count++;
                }
            }
            if (count > 1) {
                ctx.addError(activity, SPLIT_REQUIRED,
                    new Object[]{activityId, new Integer(count)},
                    " has " + count +
                    " regular efferent (outbound) transitions, and therefore requires a Split");
            }
        }

        // XOR-Splits in FULL_BLOCKED workflows must contain an OTHERWISE
        // or unconditional transition.
        if (splitType == SplitType.XOR && conditionFound && 
            !nonConditionFound && !otherwiseFound) {
            ConformanceClass conformanceClass = activity.getWorkflowProcess()
                .getPackage().getConformanceClass();
            boolean fullBlocked = conformanceClass != null &&
                conformanceClass.getGraphConformance() ==
                GraphConformance.FULL_BLOCKED;

            if (fullBlocked) {
                ctx.addError(activity, OTHERWISE_REQUIRED,
                    new Object[]{activityId},
                    " is in a FULL_BLOCKED WorkflowProcess and has an " +
                    "XOR-Split, and therefore requires an OTHERWISE or " +
                    "unconditional transition.");
            }
        }
        ctx.popPrefix();
    }

    private void checkActualParameters(Object src, ExternalReference extRef,
        ActualParameter[] actualParms, ValidationContext ctx) {

        // TODO: Retrieve external reference and check parameters against it.
    }

    private void checkActualParameters(Object src, FormalParameter[] formalParms,
        ActualParameter[] actualParms, ValidationContext ctx) {

        int fpCount = formalParms == null ? 0 : formalParms.length;
        int apCount = actualParms == null ? 0 : actualParms.length;
        boolean ok = ctx.checkEQ(src,
            " formal and actual parameter counts differ", "FormalParameters",
            fpCount, apCount);
        if (ok) {
            for (int i = 0; i < fpCount; i++) {
                FormalParameter fp = formalParms[i];
                ActualParameter ap = actualParms[i];
                int pmode = fp.getMode() != null
                    ? fp.getMode().value() : ParameterMode.IN_INT;
                switch (pmode) {
                    case ParameterMode.IN_INT:
                        // It's hard to validate an arbitrary script expression,
                        // especially at design time when we might not have a
                        // a properly configured WorkFlowEngine and
                        // EvaluatorFactory available.
                        break;
                    case ParameterMode.INOUT_INT:
                    case ParameterMode.OUT_INT:
                        String apName = ap.getText();
                        String prefix = "/ActualParameter[" + fp.getId() + ']';
                        Object ref = ctx.checkValidIdRef(src, prefix,
                            DATA_FIELD, WORKFLOW_FORMAL_PARAMETER,
                            PKG_DATA_FIELD, apName);
                        if (ref == null)
                            break;
                        DataType dt = null;
                        if (ref instanceof FormalParameter)
                            dt = ((FormalParameter)ref).getDataType();
                        else if (ref instanceof DataField)
                            dt = ((DataField)ref).getDataType();
                        if (dt == null) {
                            fp.getIndex();
                            ctx.addError(src, DATATYPE_NOT_FOUND,
                                new Object[]{String.valueOf(i), apName},
                                "Unable to determine DataType for ActualParameter[" +
                                 i + "]: " + apName);
                        } else {
                            ctx.checkAssignable(src, prefix,
                                fp.getDataType().getType(), dt.getType());
                        }
                        break;
                }
            }
        }
    }

    private void checkTransitions(Transition[] transitions,
        ValidationContext ctx) {

        if (transitions != null) {
            for (int i = 0, n = transitions.length; i < n; i++)
                checkTransition(transitions[i], ctx);
        }
    }

    private void checkTransition(Transition transition,
        ValidationContext ctx) {

        String transitionId = transition.getId();
        ctx.pushPrefix("Transition[" + transitionId + ']');
        ctx.checkValidUniqueId(transition, null, TRANSITION, transitionId,
            transition);
        if (transition.getFromActivity() == null) {
            String fromId = transition.getFrom();
            ctx.addError(transition, TRANSITION_FROM_ACTIVITY_UNDEFINED,
                new Object[]{transitionId, fromId},
                "/From references an undefined Activity[" + fromId + ']');
        }
        if (transition.getToActivity() == null) {
            String toId = transition.getTo();
            ctx.addError(transition, TRANSITION_TO_ACTIVITY_UNDEFINED,
                new Object[]{transitionId, toId},
                "/To references an undefined Activity[" + toId + ']');
        }
        ctx.popPrefix();
    }
}