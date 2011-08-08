package oe.frame.bus.workflow.actor.process;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * ����ͬ������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessSyncCheckActor {
	/**
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @param worklist
	 *            ��ǰ�
	 * @param listworklist
	 *            ��·�ɵ��Ŀɷ����Ļ
	 * @return
	 */
	List execute(TWfWorklist worklist, List listworklist);

}
