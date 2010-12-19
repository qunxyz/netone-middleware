package oe.frame.bus.workflow;

/**
 * 流程允许期间数据统计管理
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeStatis {
	/**
	 * 所有活动的状态统计
	 * 
	 * @return
	 */
	Object worklistInstanceByStatus();

	/**
	 * 所有流程的状态统计
	 * 
	 * @return
	 */
	Object processInstanceByStatus();

	/**
	 * 所有流程实例的统计
	 * 
	 * @return
	 */
	Object processInstanceByProcessId();

	/**
	 * 所有活动实例的统计
	 * 
	 * @return
	 */
	Object worklistInstanceByAvtivityId();

	/**
	 * 流程执行时间的统计（starttime 到 endtime的时间段）
	 * 
	 * @return
	 */
	Object processInstanceByTimeUseByProcessId();

	/**
	 * 活动使用时间的统计（starttime 到 endtime的时间段）
	 * 
	 * @return
	 */
	Object worklistInstanceByTimeUseByActivityId();

	/**
	 * 指定流程执行时间的统计（starttime 到 endtime的时间段）
	 * 
	 * @param processId
	 * @return
	 */
	Object processInstanceByTimeUse(String processId);

	/**
	 * 指定流程的活动执行时间的统计（starttime 到 endtime的时间段）
	 * 
	 * @param processId
	 * @return
	 */
	Object worklistInstanceByTimeUseByActivityId(String processId);

}
