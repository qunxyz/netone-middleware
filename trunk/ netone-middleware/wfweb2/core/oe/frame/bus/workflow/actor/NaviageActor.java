package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
/**
 * ���̵�����,����Ѱ��������,��ǰ�ڵ����һ���ڵ�
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 *
 */
public interface NaviageActor {
	
	List execute(TWfWorklist worklist);

}
