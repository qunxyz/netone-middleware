package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * �Զ�ִ���� ��;:������������,ִ���Զ����,����:�ջ�Զ��ύ(ִ������Ĺ���ű�),�Զ�����������, �Զ�ִ�л�еĹ���ű�,����ִ�н���
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface AutoActor {
	/**
	 * 
	 * @param list
	 *            ��Ҫ�Զ����еĻ�б�
	 */
	void execute(List list);
}
