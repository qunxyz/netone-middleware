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

package oe.midware.workflow.xpdl.model.activity;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import oe.midware.workflow.xpdl.PackageVisitor;
import oe.midware.workflow.xpdl.model.ext.AssignmentStrategyDef;
import oe.midware.workflow.xpdl.model.ext.ToolMode;
import oe.midware.workflow.xpdl.model.ext.CalendarRef;
import oe.midware.workflow.xpdl.model.misc.*;
import oe.midware.workflow.xpdl.model.transition.TransitionRestriction;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * Models an XPDL activity definition.
 */
public final class Activity extends AbstractWFElement implements CalendarRef {
    static final long serialVersionUID = -7464664489372049213L;
    private static final Deadline[] EMPTY_DEADLINE = {};
    private static final TransitionRestriction[] EMPTY_TRANSITION_RESTR = {};
    public static final String BOUNDS = "bounds";
    public static final String WORKFLOW_PROCESS = "workflowProcess";
    public static final String LIMIT = "limit";
    public static final String IMPLEMENTAION = "implementation";
    public static final String ROUTE = "route";
    public static final String BLOCK_ACTIVITY = "blockActivity";
    public static final String PERFORMER = "performer";
    public static final String START_MODE = "startMode";
    public static final String FINISH_MODE = "finishMode";
    public static final String TOOL_MODE = "toolMode";
    public static final String ASSIGNMENT_STRATEGY = "assignmentStrategy";
    public static final String COMPLETION_STRATEGY = "completionStrategy";
    public static final String CALENDAR = "calendar";
    public static final String PRIORITY = "priority";
    public static final String DEADLINE = "deadline";
    public static final String SIMULATION_INFORMATION = "simulationInformation";
    public static final String DOCUMENTATION = "documentation";
    public static final String ICON = "icon";
    public static final String TRANSITION_RESTRICTION = "transitionRestriction";
    public static final String AFFERENT_TRANSITION = "afferentTransition";
    public static final String EFFERENT_TRANSITION = "efferentTransition";
    private static final String[] _indexedPropertyNames = {
        DEADLINE, TRANSITION_RESTRICTION
    };
    private static final Object[] _indexedPropertyValues = {
        EMPTY_DEADLINE, EMPTY_TRANSITION_RESTR
    };
    private static final int DEADLINE_IDX = 0;
    private static final int TRANSITION_RESTRICTION_IDX = 1;

    private Rectangle _bounds;
    private WorkflowProcess _workflowProcess;
    private Duration _limit;
    private Implementation _implementation;
    private Route _route;
    private BlockActivity _blockActivity;
    private String _performer;
    private AutomationMode _startMode;
    private AutomationMode _finishMode;
    private ToolMode _toolMode;
    private AssignmentStrategyDef _assignmentStrategy;
    private String _completionStrategy;
    private String _calendar;
    private String _priority;
    private SimulationInformation _simulationInformation;
    private URL _documentation;
    private URL _icon;
    private Map _afferentTransitions = new HashMap();
    private Map _efferentTransitions = new HashMap();

    public Activity() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Construct a new Activity with the given ID and name.
     *
     * @param id The Activity ID
     * @param name The Activity name
     */
    public Activity(String id, String name, WorkflowProcess workflowProcess) {
        super(_indexedPropertyNames, _indexedPropertyValues, id, name);

        _workflowProcess = workflowProcess;
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        if (_implementation != null)
            _implementation.accept(visitor);
    }

    public Rectangle getBounds() {
        return _bounds;
    }

    public void setBounds(Rectangle bounds) {
        _bounds = bounds;
    }

    /**
     * Get the WorkflowProcess in which this activity is declared.
     *
     * @return The WorkflowProcess
     */
    public WorkflowProcess getWorkflowProcess() {
        return _workflowProcess;
    }

    /**
     * Get the time limit allowed for the execution of the activity.
     *
     * @return The time limit
     */
    public Duration getLimit() {
        return _limit;
    }

    /**
     * Set the time limit allowed for the execution of the activity.
     *
     * @param limit The time limit
     */
    public void setLimit(Duration limit) {
        _limit = limit;
    }

    /**
     * Get the implementation object for the activity.  If this method returns
     * null then the <code>getRoute()</code> must return a valid Route object.
     *
     * @return The Implementation object
     */
    public Implementation getImplementation() {
        return _implementation;
    }

    /**
     * Set the implementation object for the activity.
     *
     * @param implementation The new Implementation object
     */
    public void setImplementation(Implementation implementation) {
        _implementation = implementation;
    }

    /**
     * Get the Route object for the activity.  If the <code>getImplementation()</code>
     * method returns null then this method must return a valid Route object.
     *
     * @return The Route object
     */
    public Route getRoute() {
        return _route;
    }

    /**
     * Set the Route object for the activity.
     *
     * @param route The new Route object
     */
    public void setRoute(Route route) {
        _route = route;
    }

    public BlockActivity getBlockActivity() {
        return _blockActivity;
    }

    public void setBlockActivity(BlockActivity blockActivity) {
        _blockActivity = blockActivity;
    }

    /**
     * Get the ID of the activity performer.
     *
     * @return The ID of the performer
     */
    public String getPerformer() {
        return _performer;
    }

    /**
     * Set the ID of the activity performer.
     *
     * @param performer The ID of the performer
     */
    public void setPerformer(String performer) {
        _performer = performer;
    }

    /**
     * Get the start mode for the activity.  The start mode is used to determine
     * if the activity should start automatically or manually. The default value
     * is <code>AutomationMode.AUTOMATIC</code>.
     *
     * @return The start mode
     */
    public AutomationMode getStartMode() {
        return _startMode;
    }

    /**
     * Set the start mode for the activity.  The start mode is used to determine
     * if the activity should start automatically or manually.
     *
     * @param startMode The new start mode
     */
    public void setStartMode(AutomationMode startMode) {
        _startMode = startMode;
    }

    /**
     * Get the finish mode for the activity.  The finish mode is used to
     * determine if the activity should complete automatically or manually. The
     * default value is <code>AutomationMode.AUTOMATIC</code>.
     *
     * @return The finish mode
     */
    public AutomationMode getFinishMode() {
        return _finishMode;
    }

    /**
     * Set the finish mode for the activity.  The finish mode is used to
     * determine if the activity should complete automatically or manually.
     *
     * @param finishMode The new finish mode
     */
    public void setFinishMode(AutomationMode finishMode) {
        _finishMode = finishMode;
    }

    /**
     * Returns the tool invocation mode.
     *
     * @return The tool invocation mode.
     */
    public ToolMode getToolMode() {
        return _toolMode;
    }

    /**
     * Sets the tool invocation mode.
     *
     * @param toolMode The tool invocation mode.
     */
    public void setToolMode(ToolMode toolMode) {
        _toolMode = toolMode;
    }

    /**
     * Returns the work item assignment strategy for this activity.
     *
     * @return Assignment strategy definition, which must have a matching
     * implementation registered in the AssignmentStrategyRepository.
     */
    public AssignmentStrategyDef getAssignmentStrategy() {
        return _assignmentStrategy;
    }

    /**
     * Sets the activity assignment strategy.
     *
     * @param strategy Assignment strategy definition, which must have a
     * matching implementation registered in the AssignmentStrategyRepository.
     */
    public void setAssignmentStrategy(AssignmentStrategyDef strategy) {
        _assignmentStrategy = strategy;
    }

    /**
     * Returns the completion strategy for this activity.
     *
     * @return Activity completion strategy tag name, which must have a matching
     * implementation in the ???Repository.
     */
    public String getCompletionStrategy() {
        return _completionStrategy;
    }

    /**
     * Sets the completion strategy for this activity.
     *
     * @param strategy Activity completion strategy tag name, which must have a
     * matching implementation in the ???Repository.
     */
    public void setCompletionStrategy(String strategy) {
        _completionStrategy = strategy;
    }

    /**
     * Get the priority of the activity.
     *
     * @return The priority
     */
    public String getPriority() {
        return _priority;
    }

    /**
     * Set the priority of the activity.
     *
     * @param priority The new priority
     */
    public void setPriority(String priority) {
        _priority = priority;
    }

    /**
     * Returns the business calendar name.
     *
     * @return The business calendar tag name, which must have a matching
     * entry in the OBE Calendar Factory.
     */
    public String getCalendar() {
        return _calendar;
    }

    /**
     * Sets the business calendar name.
     *
     * @param calendar The business calendar tag name, which must have a
     * matching entry in the OBE Calendar Factory.
     */
    public void setCalendar(String calendar) {
        _calendar = calendar;
    }

    public void add(Deadline deadline) throws PropertyVetoException {
        add(DEADLINE_IDX, deadline);
    }

    public void remove(Deadline deadline) throws PropertyVetoException {
        remove(DEADLINE_IDX, deadline);
    }

    /**
     * Gets the activity deadlines.
     *
     * @return The deadline
     */
    public Deadline[] getDeadline() {
        return (Deadline[])get(DEADLINE_IDX);
    }

    /**
     * Gets an activity deadline.
     *
     * @param i The deadline index.
     * @return The deadline
     */
    public Deadline getDeadline(int i) {
        return (Deadline)get(DEADLINE_IDX, i);
    }

    /**
     * Sets the activity deadlines.
     *
     * @param deadlines
     */
    public void setDeadline(Deadline[] deadlines) throws PropertyVetoException {
        set(DEADLINE_IDX, deadlines == null ?  EMPTY_DEADLINE : deadlines);
    }

    /**
     * Sets an activity deadline.
     *
     * @param i The deadline index.
     * @param deadline
     */
    public void setDeadline(int i, Deadline deadline)
        throws PropertyVetoException {

        set(DEADLINE_IDX, deadline);
    }

    /**
     * Get the SimulationInformation for the activity.  This information can be
     * used to make estimations for the execution time of an activity which can
     * then be used to test a workflow definition timing.
     *
     * @return The SimulationInformation
     */
    public SimulationInformation getSimulationInformation() {
        return _simulationInformation;
    }

    /**
     * Set the SimulationInformation for the activity.
     *
     * @param simulationInformation The new SimulationInformation
     */
    public void setSimulationInformation(
        SimulationInformation simulationInformation) {
        _simulationInformation = simulationInformation;
    }

    /**
     * Get the URL for the documentation for the activity.  This URL should
     * point to documentation which can be used by developers, administrators
     * and other users to understand what the activity does.
     *
     * @return The URL for the activity documentation
     */
    public URL getDocumentation() {
        return _documentation;
    }

    /**
     * Get the URL for the documentation for the activity.
     *
     * @param documentation The new URL for the activity documentation
     */
    public void setDocumentation(URL documentation) {
        _documentation = documentation;
    }

    /**
     * Get the URL for the icon for the activity.  The icon is used to represent
     * the activity in a graphical representatioin of the workflow process.
     *
     * @return The URL for the activity's icon
     */
    public URL getIcon() {
        return _icon;
    }

    /**
     * Set the URL for the icon for the activity.
     *
     * @param icon The new URL for the activity's icon
     */
    public void setIcon(URL icon) {
        _icon = icon;
    }

    public void add(TransitionRestriction restriction)
        throws PropertyVetoException {

        add(TRANSITION_RESTRICTION_IDX, restriction);
    }

    public void remove(TransitionRestriction restriction)
        throws PropertyVetoException {

        remove(TRANSITION_RESTRICTION_IDX, restriction);
    }

    /**
     * Get a List of transition restrictions for transitions which connect to
     * this activity.
     *
     * @return a List of transition restrictions
     */
    public TransitionRestriction[] getTransitionRestriction() {
        return (TransitionRestriction[])get(TRANSITION_RESTRICTION_IDX);
    }

    public TransitionRestriction getTransitionRestriction(int i) {
        return (TransitionRestriction)get(TRANSITION_RESTRICTION_IDX, i);
    }

    public void setTransitionRestriction(TransitionRestriction[] restrictions)
        throws PropertyVetoException {

        set(TRANSITION_RESTRICTION_IDX,
            restrictions == null ? EMPTY_TRANSITION_RESTR : restrictions);
    }

    public void setTransitionRestriction(int i,
        TransitionRestriction restriction) throws PropertyVetoException {

        set(TRANSITION_RESTRICTION_IDX, i, restriction);
    }

    public Map getAfferentTransitions() {
        return _afferentTransitions;
    }

    public Map getEfferentTransitions() {
        return _efferentTransitions;
    }

    public String toString() {
        return "Activity[id='" + getId() +
            ", name=" + (getName() == null ? null : '\'' + getName() + '\'') +
            ']';
    }

    public boolean isExitActivity() {
        return _efferentTransitions.size() == 0;
    }

    public boolean isStartActivity() {
        return _afferentTransitions.size() == 0;
    }
}