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

package oe.midware.workflow.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import oe.midware.workflow.FormalParameterNotFoundException;
import oe.midware.workflow.xpdl.model.activity.*;
import oe.midware.workflow.xpdl.model.application.Application;
import oe.midware.workflow.xpdl.model.data.FormalParameter;
import oe.midware.workflow.xpdl.model.data.ParameterMode;
import oe.midware.workflow.xpdl.model.data.TypeDeclaration;
import oe.midware.workflow.xpdl.model.misc.RedefinableHeader;
import oe.midware.workflow.xpdl.model.participant.Participant;
import oe.midware.workflow.xpdl.model.pkg.ExternalPackage;
import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;


/**
 * Utility class for searching activity/workflow/package for settings and
 * definitions.
 *
 * @author Anthony Eden
 * @author Adrian Price
 */
public final class WorkflowUtilities {
    private static final int DEFAULT_PRIORITY = 5;
    private static final Log log = LogFactory.getLog(WorkflowUtilities.class);

    /**
     * Finds an activity within a workflow process definition.
     *
     * @param workflow The workflow process definition to search.
     * @param activityId The activity ID.
     * @return The activity definition.
     * @throws WMInvalidActivityNameException if the activity was not found.
     */
    public static Activity findActivity(WorkflowProcess workflow,
        String activityId) throws Exception {

        for (int i = 0, n = workflow.getActivity().length; i < n; i++) {
            Activity activity = workflow.getActivity(i);
            if (activity.getId().equals(activityId))
                return activity;
        }
        for (int i = 0, m = workflow.getActivitySet().length; i < m; i++) {
            ActivitySet as = workflow.getActivitySet(i);
            for (int j = 0, n = as.getActivity().length; j < n; j++) {
                Activity activity = as.getActivity(j);
                if (activity.getId().equals(activityId))
                    return activity;
            }
        }
        throw new Exception(activityId);
    }

    /**
     * Finds the priority to assign to an activity.  The priority is taken from
     * the activity itself if defined therein, or from its associated workflow
     * priority if not.
     *
     * @param activity The activity definition.
     * @return The activity priority.
     */
    public static int findActivityPriority(Activity activity) {
        String priorityStr = activity.getPriority();
        return priorityStr != null
            ? Integer.parseInt(priorityStr)
            : findWorkflowPriority(activity.getWorkflowProcess());
    }

    /**
     * Gets the ParameterMode for the named parameter in the given application.
     *
     * @param app The Application
     * @param paramName The parameter name
     * @return The ParameterMode
     * @throws FormalParameterNotFoundException If the named parameter could
     * be found.
     */
    public static ParameterMode findParameterMode(Application app,
        String paramName) throws FormalParameterNotFoundException {

        if (log.isDebugEnabled())
            log.debug("findParameterMode(" + app + ", " + paramName + ')');

        for (int i = 0, n = app.getFormalParameter().length; i < n; i++) {
            FormalParameter fp = app.getFormalParameter(i);
            if (fp.getId().equals(paramName)) {
                if (log.isDebugEnabled())
                    log.debug("Parameter mode: " + fp.getMode());
                return fp.getMode();
            }
        }
        throw new FormalParameterNotFoundException(paramName + " not found");
    }

    /**
     * Finds the Participant with the specified ID in the given workflow or
     * package.
     *
     * @param wp The workflow process definition.
     * @param participantId The participant ID.  This can be a comma-separated
     * list if there are two or more participants.
     * @return The participant definition.
     * @throws WMInvalidTargetUserException if any of the participants could
     * not be located.
     */
    public static Participant findParticipant(WorkflowProcess wp,
        String participantId) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("Searching workflow '" + wp.getId() +
                "' for participant '" + participantId + '\'');
        }

        for (int i = 0, n = wp.getParticipant().length; i < n; i++) {
            Participant p = wp.getParticipant(i);
            if (p.getId().equals(participantId)) {
                if (log.isDebugEnabled()) {
                    log.debug("Participant '" + participantId +
                        "' found in workflow '" + wp.getId() + '\'');
                }
                return p;
            }
        }

        XPDLPackage pkg = wp.getPackage();
        if (log.isDebugEnabled()) {
            log.debug("Searching package '" + pkg.getId() +
                "' for participant '" + participantId + '\'');
        }

        for (int i = 0, n = pkg.getParticipant().length; i < n; i++) {
            Participant p = pkg.getParticipant(i);
            if (p.getId().equals(participantId)) {
                if (log.isDebugEnabled()) {
                    log.debug("Participant '" + participantId +
                        "' found in package '" + pkg.getId() + '\'');
                }
                return p;
            }
        }

        // perhaps this should be optional?
        if (log.isDebugEnabled()) {
            log.debug("Searching external packages for participant '" +
                participantId + '\'');
        }

        for (int i = 0, m = pkg.getExternalPackage().length; i < m; i++) {
            ExternalPackage extPkg = pkg.getExternalPackage(i);
            pkg = extPkg.getPackage();
            for (int j = 0, n = pkg.getParticipant().length; j < n; j++) {
                Participant p = pkg.getParticipant(j);
                if (p.getId().equals(participantId)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Participant '" + participantId +
                            "' found in external package '" +
                            extPkg.getHref() + '\'');
                    }
                    return p;
                }
            }
        }
        throw new Exception(participantId);
    }

    /**
     * Find the Participants with the specified IDs in the given workflow or
     * package.
     *
     * @param wp The workflow process definition.
     * @param particips The participant ID(s).  This can be a comma-separated
     * list if there are two or more participants.
     * @return The participant definition.
     * @throws WMInvalidTargetUserException if any of the participants could
     * not be located.
     */
    public static Participant[] findParticipants(WorkflowProcess wp,
        String particips) throws Exception {

        List participants = new ArrayList();
        StringTokenizer strTok = new StringTokenizer(particips, ", ");
        outer: while (strTok.hasMoreTokens()) {
            String id = strTok.nextToken();
            if (log.isDebugEnabled()) {
                log.debug("Searching workflow '" + wp.getId() +
                    "' for participant '" + id + '\'');
            }

            for (int i = 0, n = wp.getParticipant().length; i < n; i++) {
                Participant p = wp.getParticipant(i);
                if (p.getId().equals(id)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Participant '" + id +
                            "' found in workflow '" + wp.getId() + '\'');
                    }
                    participants.add(p);
                    continue outer;
                }
            }

            XPDLPackage pkg = wp.getPackage();
            if (log.isDebugEnabled()) {
                log.debug("Searching package '" + pkg.getId() +
                    "' for participant '" + id + '\'');
            }

            for (int i = 0, n = pkg.getParticipant().length; i < n; i++) {
                Participant p = pkg.getParticipant(i);
                if (p.getId().equals(id)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Participant '" + id +
                            "' found in package '" + pkg.getId() + '\'');
                    }
                    participants.add(p);
                    continue outer;
                }
            }

            // perhaps this should be optional?
            if (log.isDebugEnabled()) {
                log.debug("Searching external packages for participant '" +
                    id + '\'');
            }
            for (int i = 0, m = pkg.getExternalPackage().length; i < m; i++) {
                ExternalPackage extPkg = pkg.getExternalPackage(i);
                pkg = extPkg.getPackage();
                for (int j = 0, n = pkg.getParticipant().length; j < n; j++) {
                    Participant p = pkg.getParticipant(j);
                    if (p.getId().equals(id)) {
                        if (log.isDebugEnabled()) {
                            log.debug("Participant '" + id +
                                "' found in external package '" +
                                extPkg.getHref() + '\'');
                        }
                        participants.add(p);
                        continue outer;
                    }
                }
            }
            throw new Exception(id);
        }
        return (Participant[])participants.toArray(
            new Participant[participants.size()]);
    }

    public static Tool findTool(Activity activity, int toolIndex)
        throws Exception {

        Implementation impl = activity.getImplementation();
        if (impl instanceof ToolSet) {
            Tool[] tools = ((ToolSet)activity.getImplementation()).getTool();
            if (toolIndex >= 0 && toolIndex < tools.length)
                return tools[toolIndex];
        }
        throw new Exception(String.valueOf(toolIndex));
    }

    private static Application findToolDefinition(Application[] toolDefs,
        String id) {

        for (int i = 0, n = toolDefs.length; i < n; i++) {
            Application toolDef = toolDefs[i];
            if (toolDef.getId().equals(id))
                return toolDef;
        }
        return null;
    }

    /**
     * Finds the definition of the specified tool in a workflow or package.
     *
     * @param wp The WorkflowProcess to search.
     * @param toolId The ID of the tool for which the definition is required.
     * @return The tool definition.
     * @throws WMInvalidToolException if the tool definition could not be
     * located.
     */
    public static Application findToolDefinition(WorkflowProcess wp,
        String toolId) throws Exception {

        Application toolDef;

        if (log.isDebugEnabled()) {
            log.debug("Searching workflow '" + wp.getId() + "' for tool '" +
                toolId + '\'');
        }
        toolDef = findToolDefinition(wp.getApplication(), toolId);
        if (toolDef == null) {
            XPDLPackage pkg = wp.getPackage();
            if (log.isDebugEnabled()) {
                log.debug("Searching package '" + pkg.getId() + "' for tool '" +
                    toolId + '\'');
            }
            toolDef = findToolDefinition(pkg.getApplication(), toolId);
            if (toolDef == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Searching external packages for tool '" +
                        toolId + '\'');
                }
                for (int i = 0, n = pkg.getExternalPackage().length; i < n; i++) {
                    ExternalPackage externalPackage = pkg.getExternalPackage(i);
                    toolDef = findToolDefinition(
                        externalPackage.getPackage().getApplication(), toolId);
                    if (toolDef != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("Tool '" + toolId +
                                "' found in external package '" +
                                externalPackage.getHref() + '\'');
                        }
                        break;
                    }
                }
                if (toolDef == null)
                    throw new Exception(toolId);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Tool '" + toolId + " found in package '" +
                        pkg.getId() + '\'');
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Tool '" + toolId + " found in workflow '" +
                    wp.getId() + '\'');
            }
        }

        return toolDef;
    }

    /**
     * Finds a type declaration within a package.
     *
     * @param pkg The package to search.
     * @param declaredTypeId The ID of the declared type.
     * @return The type declaration.
     * @throws WMInvalidProcessDefinitionException if the type declaration could
     * not be found.
     */
    public static TypeDeclaration findTypeDeclaration(XPDLPackage pkg,
        String declaredTypeId) throws Exception {

        for (int i = 0, n = pkg.getTypeDeclaration().length; i < n; i++) {
            TypeDeclaration typeDecl = pkg.getTypeDeclaration(i);
            if (typeDecl.getId().equals(declaredTypeId))
                return typeDecl;
        }
        throw new Exception(declaredTypeId);
    }

    /**
     * Finds the workflow priority from the workflow or its package.
     *
     * @param workflow The workflow definition to search.
     * @return The workflow or package priority.
     */
    public static int findWorkflowPriority(WorkflowProcess workflow) {
        String priorityStr = workflow.getProcessHeader().getPriority();
        return priorityStr != null
            ? Integer.parseInt(priorityStr) : DEFAULT_PRIORITY;
    }

    /**
     * Finds a workflow definition within a package.
     *
     * @param pkg The package to search.
     * @param processDefinitionId The ID of the process definition required.
     * @return The process definition.
     * @throws WMInvalidProcessDefinitionException if the process definition
     * could not be found in the package.
     */
    public static WorkflowProcess findWorkflowProcess(XPDLPackage pkg,
        String processDefinitionId) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("Searching package '" + pkg.getId() + "' for workflow '" +
                processDefinitionId + '\'');
        }
        for (int i = 0, n = pkg.getWorkflowProcess().length; i < n; i++) {
            WorkflowProcess wf = pkg.getWorkflowProcess(i);
            if (wf.getId().equals(processDefinitionId)) {
                return wf;
            }
        }

        for (int i = 0, n = pkg.getExternalPackage().length; i < n; i++) {
            ExternalPackage extPkg = pkg.getExternalPackage(i);
            // BUG: this can throw an exception before all the external
            // packages have been searched.
            WorkflowProcess wf = findWorkflowProcess(extPkg.getPackage(),
                processDefinitionId);
            if (wf != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Workflow '" + processDefinitionId +
                        "' found in external package '" +
                        extPkg.getHref() + '\'');
                }
                break;
            }
        }
        throw new Exception(processDefinitionId);
    }

    public static String[] getResponsibles(WorkflowProcess workflow) {
        RedefinableHeader rhdr = workflow.getRedefinableHeader();
        String[] responsibles = null;
        if (rhdr != null)
            responsibles = rhdr.getResponsible();
        if (responsibles == null || responsibles.length == 0) {
            rhdr = workflow.getPackage().getRedefinableHeader();
            if (rhdr != null)
                responsibles = rhdr.getResponsible();
        }
        return responsibles;
    }

    private WorkflowUtilities() {
    }
}