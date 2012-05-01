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
		// 定义保存流程实例ID变量
		String runtimeId = null;
		// 定义保存当前活动节点实例ID变量
		String workItemId = null;
		// 创建流程实例BUSSWF.BUSSWF.OESCRIPTDEMO 对应着 中间件平台上的 OeScript测试)
		runtimeId = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");
		// WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
		// console.runProcess(runtimeId);
		// 启动流程
		wf.run(runtimeId);
		// 节点的ID
		String activivtyId = "trackAction1237274662875";

		List worklist = view.fetchRunningWorklist(runtimeId, activivtyId); // 获取该活动节点的实例对象

		for (Iterator iter = worklist.iterator(); iter.hasNext();) {
			TWfWorklist work = (TWfWorklist) iter.next();
			workItemId = work.getWorkcode();

		}
		// 将流程实例Id与流程活动节点实例ID打印到控制台
		System.out.println("runtimeid: " + runtimeId + "  workItemId : "
				+ workItemId);

		/*-
		 * 查询启动巡检任务的bean服务并启动
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
