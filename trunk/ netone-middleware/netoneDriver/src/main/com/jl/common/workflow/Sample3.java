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

/**
 * ��Sample����Ը߼�������Ӧ���������
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
		
		WfEntry.iv().outTimeAlarm();

	}
}
