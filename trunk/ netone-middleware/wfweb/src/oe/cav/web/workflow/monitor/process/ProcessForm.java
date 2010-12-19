package oe.cav.web.workflow.monitor.process;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class ProcessForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private String processid;

	private String kind;

	private String statusnow;

	private String startTime;

	private String endTime;

	private String runtimeid;

	// private String[] datafieldid;

	// 流程跳转方向
	private int direction;

	// 流程跳转和流程调试的 当前活动 workcode
	private Long workcode;

	// 流程调试的跳转点选择
	private String activityid;

	/**
	 * @return Returns the runtimeid.
	 */
	public String getRuntimeid() {
		return runtimeid;
	}

	/**
	 * @param runtimeid
	 *            The runtimeid to set.
	 */
	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	/**
	 * @return 返回 processid。
	 */
	public String getProcessid() {
		
		return processid;
	}

	/**
	 * @param processid
	 *            要设置的 processid。
	 */
	public void setProcessid(String processid) {
		this.processid = processid;
	}

	/**
	 * @return 返回 endTime。
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime。
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 返回 startTime。
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            要设置的 startTime。
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return 返回 state。
	 */
	public String getStatusnow() {
		return statusnow;
	}

	/**
	 * @param state
	 *            要设置的 state。
	 */
	public void setStatusnow(String statusnow) {
		this.statusnow = statusnow;
	}

	/**
	 * @return 返回 kind。
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            要设置的 kind。
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return 返回 direction。
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            要设置的 direction。
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return 返回 workcode。
	 */
	public Long getWorkcode() {
		return workcode;
	}

	/**
	 * @param workcode
	 *            要设置的 workcode。
	 */
	public void setWorkcode(Long workcode) {
		this.workcode = workcode;
	}

	/**
	 * @return 返回 activityid。
	 */
	public String getActivityid() {
		return activityid;
	}

	/**
	 * @param activityid
	 *            要设置的 activityid。
	 */
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
}
