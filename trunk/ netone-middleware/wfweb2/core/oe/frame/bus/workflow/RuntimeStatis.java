package oe.frame.bus.workflow;

/**
 * ���������ڼ�����ͳ�ƹ���
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeStatis {
	/**
	 * ���л��״̬ͳ��
	 * 
	 * @return
	 */
	Object worklistInstanceByStatus();

	/**
	 * �������̵�״̬ͳ��
	 * 
	 * @return
	 */
	Object processInstanceByStatus();

	/**
	 * ��������ʵ����ͳ��
	 * 
	 * @return
	 */
	Object processInstanceByProcessId();

	/**
	 * ���лʵ����ͳ��
	 * 
	 * @return
	 */
	Object worklistInstanceByAvtivityId();

	/**
	 * ����ִ��ʱ���ͳ�ƣ�starttime �� endtime��ʱ��Σ�
	 * 
	 * @return
	 */
	Object processInstanceByTimeUseByProcessId();

	/**
	 * �ʹ��ʱ���ͳ�ƣ�starttime �� endtime��ʱ��Σ�
	 * 
	 * @return
	 */
	Object worklistInstanceByTimeUseByActivityId();

	/**
	 * ָ������ִ��ʱ���ͳ�ƣ�starttime �� endtime��ʱ��Σ�
	 * 
	 * @param processId
	 * @return
	 */
	Object processInstanceByTimeUse(String processId);

	/**
	 * ָ�����̵Ļִ��ʱ���ͳ�ƣ�starttime �� endtime��ʱ��Σ�
	 * 
	 * @param processId
	 * @return
	 */
	Object worklistInstanceByTimeUseByActivityId(String processId);

}
