package oe.mid.soa.wf;

import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * ����ִ��,����˵��,Ӧ�ù�����RMI���񴴽���������,�����̳ɹ�������,ͨ����ѯ�Ա�����Ĺ������ڵ�,����ֱ����ת�����̵�β�ڵ�,ֱ�ӽ�������
 * 
 * @author chen.jia.xun
 * 
 */
public class ExecuteByManualSample {
	static String _PROCESS_ID = "";

	static String _END_ACTIVITY_ID = "";

	public static void main(String[] args) throws Exception {
		// ���̿���̨�������
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		//console.exeScript(xxxx, xxxx);
		
		// ������ͼ�������
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");

		// ���̿���,��������ʵ��
		String runtimeid = console.newProcess(_PROCESS_ID);
		// ���̿���,��������
		console.runProcess(runtimeid);
		// ����Ӧ��,�����������ʵ��
		TWfRuntime runtimeObj = view.loadRuntime(runtimeid);
		// ����Ӧ��,������̻�ڵ�,�Զ��ύֱ�����̽���
		List list = view.fetchRunningWorklist(runtimeid);

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TWfWorklist element = (TWfWorklist) iter.next();
			// ����Ӧ��,ֱ���������ύ�������ڵ�
			console.commitActivityByManual(element.getWorkcode(),
					_END_ACTIVITY_ID);
		}

		list = view.fetchRunningWorklist(runtimeid);
		while (list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TWfWorklist element = (TWfWorklist) iter.next();
				// ����Ӧ��,�ύ�
				console.commitActivity(element.getWorkcode());
			}
			list = view.fetchRunningWorklist(runtimeid);
		}

	}
}
