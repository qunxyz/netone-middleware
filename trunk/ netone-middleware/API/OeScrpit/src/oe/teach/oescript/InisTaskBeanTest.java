package oe.teach.oescript;

import java.util.Iterator;
import java.util.List;

import oe.mid.soa.bean.BeanService;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oescript.parent.OeScript;

public class InisTaskBeanTest extends OeScript {
	public static void main(String[] args) {
		try {
			invokeForWorkflow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void invokeForWorkflow() throws Exception {
		// ���屣������ʵ��ID����
		String runtimeId = null;
		// ���屣�浱ǰ��ڵ�ʵ��ID����
		String workItemId = null;
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		runtimeId = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");
		// WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
		// console.runProcess(runtimeId);
		// ��������
		wf.run(runtimeId);
		// �ڵ��ID
		String activivtyId = "trackAction1237274662875";

		List worklist = view.fetchRunningWorklist(runtimeId, activivtyId); // ��ȡ�û�ڵ��ʵ������

		for (Iterator iter = worklist.iterator(); iter.hasNext();) {
			TWfWorklist work = (TWfWorklist) iter.next();
			workItemId = work.getWorkcode();

		}
		// ������ʵ��Id�����̻�ڵ�ʵ��ID��ӡ������̨
		System.out.println("runtimeid: " + runtimeId + "  workItemId : "
				+ workItemId);

		/*-
		 * ��ѯ����Ѳ�������bean��������
		 */
		// String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.INISTASK");
		//		
		// System.out.println("task bean id : " + beanid);
		//		 
		// Object obj = bean.getInobj(beanid);
		// bean.setIn("taskName", "taskTest", obj);
		// bean.setIn("runtimeId", "1111", obj);
		// bean.setIn("workItemId", "2222", obj);
		// bean.setIn("paramStr", "ParamTest:pt[8888]", obj);
		// bean.setIn("model", "one", obj);
		// bean.submit(obj);
		// String rs = bean.run(beanid);
		// System.out.println(rs);
		// wf.commitAct(workItemId);
	}
}
