package com.jl.common.workflow;

import java.util.HashMap;
import java.util.Map;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;

/**
 * ��Sample����Ը߼�������Ӧ���������
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Sample3 {

	public static void main(String[] args) throws Exception {
		//ĳ���ⲿ����
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
//		//�@��ȫ�����k�΄�
//		WfEntry.iv().worklist(clientId);
//		//�������k
//		WfEntry.iv().worklistDone(clientId);
//		// ��ô��� listtype={01 ���졢02�԰�δ�鵵��03 �Ѱ��ҹ鵵��04ȫ������}
//		WfEntry.iv().worklist(clientId, processid, true, limit, listtype)
//		// ��ô��ĵ�
//		WfEntry.iv().worklist(clientId, processid, false, limit, "01");	
//		
//		
//		WlEntry.iv().count(clientid, appname, mode, listtype, query)

	}
}
