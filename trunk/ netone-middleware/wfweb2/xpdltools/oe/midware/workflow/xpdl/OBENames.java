package oe.midware.workflow.xpdl;

/**
 * Provides string constants for the OBE XPDL extension tag names.
 *
 * @author Adrian Price
 */
public interface OBENames {
    String OBE = "OBE";
    String OBE_VERSION = "1.0";
    String OBE_NAME = "Open Business Engine";
    /** Namespace prefix to use for OBE extensions. */
    String OBE_NS_PREFIX = "obe";
    String OBE_URL = "http://www.openbusinessengine.org/";
    /** The OBE namespace URI. */
    String OBE_NS_URI = OBE_URL + "2003/OBE1.0";
    String OBE_APIDOC_URL = OBE_URL + OBE + OBE_VERSION + "/docs/api/";
    String OBE_AUTHOR = OBE_NAME + '(' + OBE_URL + ')';

    String OBE_EVENT = "obe.Event";
    String OBE_EVENT_TYPES = "obe.EventTypes";
    String EVENT_TYPES = "EventTypes";
    String EVENT_TYPE = "EventType";
    String EVENT = "Event";
    String OBE_BOUNDS = "obe.Bounds";
    String BOUNDS = "Bounds";
    String X = "x";
    String Y = "y";
    String WIDTH = "width";
    String HEIGHT = "height";
    String OBE_LOOP = "obe.Loop";
    String LOOP = "Loop";
    String WHILE = "While";
    String UNTIL = "Until";
    String FOR_EACH = "ForEach";
    String IN = "In";
    String OBE_CALENDAR = "obe.Calendar";
    String ASSIGNMENT_STRATEGY = "AssignmentStrategy";
    String EXPAND_GROUPS = "ExpandGroups";
    String OBE_ASSIGNMENT_STRATEGY = "obe.AssignmentStrategy";
    String OBE_COMPLETION_STRATEGY = "obe.CompletionStrategy";
    String OBE_SCRIPT = "obe.Script";
    String OBE_TOOL_MODE = "obe.ToolMode";
    String OBE_TARGET = "obe.Target";
    String OBE_FORK = "obe.Fork";
}