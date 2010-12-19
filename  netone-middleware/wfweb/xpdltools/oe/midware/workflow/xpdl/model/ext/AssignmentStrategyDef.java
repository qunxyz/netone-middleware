package oe.midware.workflow.xpdl.model.ext;

import oe.midware.workflow.util.AbstractBean;

/**
 * Defines the strategy for work item assignment.
 *
 * @author Adrian Price
 */
public final class AssignmentStrategyDef extends AbstractBean {
    static final long serialVersionUID = -6818952189605162164L;
    /**
     * The default work item assignment strategy.  The default expands groups
     * and assigns work items to all users.
     */
    public static final AssignmentStrategyDef DEFAULT
        = new AssignmentStrategyDef("ALL",
            "Expand groups and assigns work items to all group members", true);
    private String _id;
    private String _description;
    private boolean _expandGroups = true;

    public AssignmentStrategyDef() {
    }

    public AssignmentStrategyDef(String id, String description,
        boolean expandGroups) {

        _id = id;
        _description = description;
        _expandGroups = expandGroups;
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

    public boolean getExpandGroups() {
        return _expandGroups;
    }

    public void setExpandGroups(boolean expandGroups) {
        _expandGroups = expandGroups;
    }

    public String toString() {
        return "AssignmentStrategyDef[id=" + _id +
            ", description=" + _description +
            ", expandGroups=" + _expandGroups +
            ']';
    }
}