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

package oe.midware.workflow.xpdl.model.ext;

import java.beans.PropertyVetoException;
import oe.midware.workflow.util.AbstractBean;
import oe.midware.workflow.xpdl.PackageVisitor;
import oe.midware.workflow.xpdl.model.data.ExternalReference;
import oe.midware.workflow.xpdl.model.data.FormalParameter;
import oe.midware.workflow.xpdl.model.misc.Invokable;

/**
 * Describes an application event type, referenced by an event-type transition.
 * This is an OBE XPDL-1.0 extension.
 *
 * @author Adrian Price
 */
public final class EventType extends AbstractBean implements Invokable {
    static final long serialVersionUID = -6721041421989416656L;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String EXTERNAL_REFERENCE = "externalReference";
    public static final String FORMAL_PARAMETER = "formalParameter";
    private static final FormalParameter[] EMPTY_FORMAL_PARM = {};
    private static final String[] _indexedPropertyNames = {FORMAL_PARAMETER};
    private static final Object[] _indexedPropertyValues = {EMPTY_FORMAL_PARM};
    private static final int FORMAL_PARAMETER_IDX = 0;

    private String _id;
    private String _name;
    private String _description;
    private FormalParameter[] _formalParameter = EMPTY_FORMAL_PARM;
    private ExternalReference _externalReference;

    public EventType() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    public EventType(String id, String name, String description) {
        super(_indexedPropertyNames, _indexedPropertyValues);
        _id = id;
        _name = name;
        _description = description;
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        for (int i = 0; i < _formalParameter.length; i++)
            _formalParameter[i].accept(visitor);
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public void add(FormalParameter formalParameter)
        throws PropertyVetoException {

        _formalParameter = (FormalParameter[])add(FORMAL_PARAMETER_IDX,
            formalParameter);
    }

    public void remove(FormalParameter formalParameter)
        throws PropertyVetoException {

        _formalParameter = (FormalParameter[])remove(FORMAL_PARAMETER_IDX,
            formalParameter);
    }

    public FormalParameter[] getFormalParameter() {
        return (FormalParameter[])get(FORMAL_PARAMETER_IDX);
    }

    public FormalParameter getFormalParameter(int i) {
        return _formalParameter[i];
    }

    public FormalParameter getFormalParameter(String id) {
        if (_formalParameter != null) {
            for (int i = 0; i < _formalParameter.length; i++) {
                FormalParameter fp = _formalParameter[i];
                if (fp.getId().equals(id))
                    return fp;
            }
        }
        return null;
    }

    public void setFormalParameter(FormalParameter[] formalParameters)
        throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, _formalParameter = formalParameters == null
            ? EMPTY_FORMAL_PARM : formalParameters);
    }

    public void setFormalParameter(int i, FormalParameter formalParameter)
        throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, i, formalParameter);
    }

    public ExternalReference getExternalReference() {
        return _externalReference;
    }

    public void setExternalReference(ExternalReference externalReference) {
        _externalReference = externalReference;
    }

    public String toString() {
        return "[id=" + _id +
            ", name=" + _name +
            ", formalParameter=" + _formalParameter +
            ", externalReference=" + _externalReference +
            ']';
    }
}