package oe.mid.soa.wf;

import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * 手工提交执行流程<br>
 * 用例说明,应用工作流RMI服务创建启动流程,当流程成功启动后,通过查询以被激活的工作流节点 然后用程序提交节点,让流程继续运行直到流程执行结束
 * 
 * @author chen.jia.xun
 * 
 */
public class AutoExecuteSample {

	static String _PROCESS_ID = "BUSSWF.BUSSWF.OESCRIPTDEMO";

	public static void main(String[] args) throws Exception {
		// 流程控制台操作句柄
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		// 流程视图操作句柄
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");

		// 流程控制,创建流程实例
		String runtimeid = console.newProcess(_PROCESS_ID);
		// 流程控制,启动流程
		console.runProcess(runtimeid);
		
		// 流程应用,获得流程运行实例
		TWfRuntime runtimeObj = view.loadRuntime(runtimeid);

		// 流程应用,获得流程活动节点,自动提交直到流程结束
		List list = view.fetchRunningWorklist(runtimeid);
		while (list.size() > 0) {
			System.out.println();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TWfWorklist element = (TWfWorklist) iter.next();
				// 流程应用,提交活动
				console.commitActivity(element.getWorkcode());
			
			}
			list = view.fetchRunningWorklist(runtimeid);
		}
	}
}
