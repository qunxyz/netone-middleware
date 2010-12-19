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

	// ������ת����
	private int direction;

	// ������ת�����̵��Ե� ��ǰ� workcode
	private Long workcode;

	// ���̵��Ե���ת��ѡ��
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
	 * @return ���� processid��
	 */
	public String getProcessid() {
		
		return processid;
	}

	/**
	 * @param processid
	 *            Ҫ���õ� processid��
	 */
	public void setProcessid(String processid) {
		this.processid = processid;
	}

	/**
	 * @return ���� endTime��
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            Ҫ���õ� endTime��
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return ���� startTime��
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            Ҫ���õ� startTime��
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return ���� state��
	 */
	public String getStatusnow() {
		return statusnow;
	}

	/**
	 * @param state
	 *            Ҫ���õ� state��
	 */
	public void setStatusnow(String statusnow) {
		this.statusnow = statusnow;
	}

	/**
	 * @return ���� kind��
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            Ҫ���õ� kind��
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return ���� direction��
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            Ҫ���õ� direction��
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @return ���� workcode��
	 */
	public Long getWorkcode() {
		return workcode;
	}

	/**
	 * @param workcode
	 *            Ҫ���õ� workcode��
	 */
	public void setWorkcode(Long workcode) {
		this.workcode = workcode;
	}

	/**
	 * @return ���� activityid��
	 */
	public String getActivityid() {
		return activityid;
	}

	/**
	 * @param activityid
	 *            Ҫ���õ� activityid��
	 */
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
}
