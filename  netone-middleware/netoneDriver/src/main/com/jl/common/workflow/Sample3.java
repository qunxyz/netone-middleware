package com.jl.common.workflow;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.app.AppObj;

import oe.frame.web.WebCache;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.worklist.WlEntry;

/**
 * 该Sample在针对高级的流程应用相关例子
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Sample3 {

	public static void main(String[] args) throws Exception {

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
