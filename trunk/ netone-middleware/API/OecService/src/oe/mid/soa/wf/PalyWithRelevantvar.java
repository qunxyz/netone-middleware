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
 * 流程执行,并且通过程序控制相关数据的参数,来改变流程的执行逻辑顺序
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

		// 更新相关
		TWfRelevantvar rev = view.fetchRelevantVar(runtimeid,_RELEVANTVAR_ID);
		rev.setValuenow(_RELEVANTVAR_VALUE);
		console.updateRelevantvar(rev);

		// 流程应用,获得流程活动节点,自动提交直到流程结束
		List list = view.fetchRunningWorklist(runtimeid);
		while (list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TWfWorklist element = (TWfWorklist) iter.next();
				// 流程应用,提交活动
				console.commitActivity(element.getWorkcode());
			}
			list = view.fetchRunningWorklist(runtimeid);
		}
	}
}
