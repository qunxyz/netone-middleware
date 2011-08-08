package oe.frame.bus.workflow;

/**
 * 节点扩展属性
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ActivityExtendAttribute {

	String _SUBFLOW_ID = "subflowId";

	String _SUBFLOW_NAME = "subflowName";

	String _SUBFLOW_SYNCMODE = "syncMode";

	String _TOOLS_SCRIPT = "script";

	String _TOOLS_URLBIND = "urlBind";

	String _TOOLS_PARTICIPANT = "participant";

	String _VALUE_SUBFLOW_SYNCMODE_SYNC = "syncer";

	String _VALUE_SUBFLOW_SYNCMODE_DISYNC = "disyncer";

}
