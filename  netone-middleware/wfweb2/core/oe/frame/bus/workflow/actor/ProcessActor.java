package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * ����ִ����
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessActor {
	// /////����ִ�в���////////
	/**
	 * ��������ʵ��
	 * 
	 * @param proc
	 *            ����ģ�����
	 * @return Object ����ʵ��
	 */
	public TWfRuntime newProcess(WorkflowProcess proc);

	/**
	 * ��������
	 * 
	 * @param runtime
	 *            ����ʵ������
	 * @return boolean �Ƿ��Ѿ������ɹ�
	 */
	public void runProcess(TWfRuntime runtime);

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentworklist)
	 * @return boolean
	 */
	public void commitActivity(TWfWorklist currentworklist);

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, Activity act);

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, List act);

}
