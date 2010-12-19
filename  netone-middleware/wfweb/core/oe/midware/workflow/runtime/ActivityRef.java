package oe.midware.workflow.runtime;

/**
 * 活动对象参考
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ActivityRef {
	String ACT_SUBFLOW = "SubFlow";

	String ACT_TOOL = "Tool";

	String ACT_NORMAL = "NO";

	String ACT_ROUTE = "ROUTE";

	String[] ACT_SUBFLOW_KEY = { "00", "子流程" };

	String[] ACT_TOOLS_KEY = { "01", "普通活动" };

	String[] ACT_NORMAL_KEY = { "02", "普通活动" };

	String[] ACT_ROUTE_KEY = { "03", "空活动" };

	String[] ACT_SUBFLOW_SYNC_KEY = { "04", "等待同步子流程" };

	String FLOW_SYNCHR = "SYNCHR";

	String PROCESS_ID_SPLIT = "_";

	String ACT_STATIC_XOFFSET = "XOffset";

	String ACT_STATIC_YOFFSET = "YOffset";
}
