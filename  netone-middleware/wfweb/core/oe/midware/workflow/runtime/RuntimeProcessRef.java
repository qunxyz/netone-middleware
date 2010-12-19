package oe.midware.workflow.runtime;

/**
 * 运行期流程参考
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeProcessRef {
	/** user-defined area begin */

	String _QUERY_WORKLIST_APPEND_CONDITION = " order by starttime desc";

	// 准备中
	static String[] STATUS_READY = { "00", "准备中" };

	// 流程中的活动在正常执行中的状态
	static String[] STATUS_RUNNING = { "01", "执行中" };

	// 流程正常执行完毕后的状态
	static String[] STATUS_END = { "02", "完毕" };

	// 异常- 通常是由于流程引擎出现了故障而导致流程出现异常的状态
	static String[] STATUS_EXCEPTION = { "03", "异常" };

	// 撤销流程
	static String[] STATUS_QUASH = { "04", "撤销" };

	// 流程状态名列表
	static String[][] STATUSLIST = { { "00", "准备中" }, { "01", "执行中" },
			{ "02", "完毕" }, { "03", "异常" }, { "04", "撤销" } };

	// 主流程
	static String[] TYPE_MAIN_FLOW = { "01", "主流程" };

	// 子流程
	static String[] TYPE_SUB_FLOW = { "02", "子流程" };

	// 同步子流程
	static String[] TYPE_SYNC_SUB_FLOW = { "03", "同步子流程" };

	static String[][] TYPELIST = { { "01", "主流程" }, { "02", "子流程" },
			{ "03", "同步子流程" } };

}
