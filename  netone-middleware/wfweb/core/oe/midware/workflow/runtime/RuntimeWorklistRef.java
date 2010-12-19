package oe.midware.workflow.runtime;

/**
 * 运行期活动参考
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */                                                   
public interface RuntimeWorklistRef {
	// 任务执行中 - 通常是任务分配后
	String[] STATUS_RUNNING = { "01", "执行中" };

	// 任务正常执行完毕
	String[] STATUS_DONE = { "02", "完毕" };

	// 任务异常
	String[] STATUS_EXCEPTION = { "03", "异常" };

	// 取消- 活动被撤销的时候的状态。处于该状态的活动和Normal 的活动一样都存放在workList中
	// 唯一的区别在于处于Quash状态的活动是能不被执行的,只能查看
	String[] STATUS_QUASH = { "04", "取消" };

	String[] STATUS_IGNORE = { "05", "忽略" };

	String[] STATUS_SUBFLOW_WAITING = { "06", "等待子流程" };

	String[] STATUS_SUBFLOW_STOP = { "07", "暂停" };

	// 活动状态名列表
	String[][] STATUSLIST = { { "01", "执行中" }, { "02", "完毕" }, { "03", "异常" },
			{ "04", "取消" }, { "05", "忽略" }, { "06", "等待子流程" }, { "07", "暂停" } };

}
