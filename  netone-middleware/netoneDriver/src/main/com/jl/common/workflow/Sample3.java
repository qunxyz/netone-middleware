package com.jl.common.workflow;

import java.util.HashMap;
import java.util.Map;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;

/**
 * 该Sample在针对高级的流程应用相关例子
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Sample3 {

	public static void main(String[] args) throws Exception {
		//某个外部对象
		Map obj=new HashMap();
		obj.put("xxx", 999);
		
		String script="if(_param.length==1){Map map=(Map)_param[0];System.out.println(map.get(\"xxx\"));};";
		Object []objArr={obj};
		
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.exeScript(script,objArr);



//		com.jl.common.app.AppObj obj = AppEntry.iv().loadApp(
//				"APPFRAME.APPFRAME.NDYD");
//
//		String processid = obj.getWorkflowCode_();
//		// String runtimeid = WfEntry.iv().newProcess(processid,
//		// "adminx[adminx]",
//		// "APPFRAME.APPFRAME.NDYD", "", "", "");
//		// List listx = WfEntry.iv().listNextJumpActive(processid, runtimeid);
//		//
//		// List list = WfEntry.iv().useCoreView().coreSqlview(
//		// "select count(*) countx from t_wf");
//
//		boolean xx = WfEntry.iv().isAndBranchMode(processid,
//				"trackAction1307596203953");
		
		//WebCache.removeCache("NA_APPFRAME.APPFRAME.JTZDSP.trackAction1307596177031");
		
//		WfEntry.iv().outTimeAlarm();
//		
//		//獲得全部代辦任務
//		WfEntry.iv().worklist(clientId);
//		//所有已辦
//		WfEntry.iv().worklistDone(clientId);
//		// 获得待办 listtype={01 代办、02以办未归档、03 已办且归档、04全部工单}
//		WfEntry.iv().worklist(clientId, processid, true, limit, listtype)
//		// 获得待阅单
//		WfEntry.iv().worklist(clientId, processid, false, limit, "01");	
//		
//		
//		WlEntry.iv().count(clientid, appname, mode, listtype, query)

	}
}
