package oe.mid.soa.wf;

import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * ����ִ��,����ͨ���������������ݵĲ���,���ı����̵�ִ���߼�˳��
 * 
 * @author chen.jia.xun
 * 
 */
public class PalyWithRelevantvar {
	static String _PROCESS_ID = "";

	static String _ACTIVITY_ID = "";

	static String _RELEVANTVAR_ID = "";

	static String _RELEVANTVAR_VALUE = "1";

	public static void main(String[] args) throws Exception {
		// ���̿���̨�������
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		// ������ͼ�������
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");

		// ���̿���,��������ʵ��
		String runtimeid = console.newProcess(_PROCESS_ID);
		// ���̿���,��������
		console.runProcess(runtimeid);
		// ����Ӧ��,�����������ʵ��
		TWfRuntime runtimeObj = view.loadRuntime(runtimeid);

		// �������
		TWfRelevantvar rev = view.fetchRelevantVar(runtimeid,_RELEVANTVAR_ID);
		rev.setValuenow(_RELEVANTVAR_VALUE);
		console.updateRelevantvar(rev);

		// ����Ӧ��,������̻�ڵ�,�Զ��ύֱ�����̽���
		List list = view.fetchRunningWorklist(runtimeid);
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
