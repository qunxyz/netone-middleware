package oe.netone.bi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;


import com.jl.common.resource.Resource;
import com.jl.common.workflow.WfEntry;

public class TodoFlow {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// String runtimeid = WfEntry.iv().newProcess("BUSSWF.BUSSWF.BIFLOW",
		// "",
		// "", "", "", "");
		// WfEntry.iv().runProcess(runtimeid);
		//
		// Activity act = WfEntry.iv().loadProcess("BUSSWF.BUSSWF.BIFLOW")
		// .getActivity("trackAction1321772692492");
		//		
		// String graphMode=act.getExtendedAttributes().get("graphMode");
		// String digope=act.getExtendedAttributes().get("digope");
		// String dyform=act.getExtendedAttributes().get("dyform");
		//	    
		// String
		// extendFaceDimCondition=act.getExtendedAttributes().get("extendFaceDimCondition");
		// String
		// faceDimColumn=act.getExtendedAttributes().get("faceDimColumn");
		// String xdimcolumn=act.getExtendedAttributes().get("xdimcolumn");
		// String targetColumn=act.getExtendedAttributes().get("targetColumn");
		//	    
		//	    
		//	    
		// System.out.println(graphMode);
		//		
		// WfEntry.iv().setVarValue(runtimeid, "digvalue", "xxxx");
		// a
		// String xxxx=WfEntry.iv().getVarByRuntimeId(runtimeid, "digvalue");
		TodoFlow todofloe = new TodoFlow();
		List bicollo = todofloe.BICollo("40288de533c0fa830133c1056fba0003",
				"trackAction1321772694523");
		List ss = todofloe.hybrid("40288de533c0fa830133c1056fba0003");

	}

	// 获取所有节点的配置信息
	public List BICollo(String runtimeid, String activityid) throws Exception {
		List list = new ArrayList();
		String NATURALNAME = WfEntry.iv().getProcessIdByRuntimeId(runtimeid);

		Activity act = WfEntry.iv().loadProcess(NATURALNAME).getActivity(
				activityid);
		BICollocate bicollocate = new BICollocate();

		bicollocate
				.set_bFormcode(act.getExtendedAttributes().get("_bFormcode")); // 对应的表单
		bicollocate.set_bCutDimColumn(act.getExtendedAttributes().get(
				"_bCutDimColumn")); // 切片字段
		bicollocate.set_bCutDimColumnValue(act.getExtendedAttributes().get(
				"_bCutDimColumnValue")); // 切片值
		String ext=act.getExtendedAttributes().get(
				"_bExtendCondition");
		String key=StringUtils.substringBetween(ext, "$:","#");
		if(StringUtils.isNotEmpty(key)){
			String keyvalue=WfEntry.iv().getVarByRuntimeId(runtimeid, key);
			if(StringUtils.isNotEmpty(keyvalue)){
				boolean isNumber=keyvalue.matches("(-)?([0-9]+)\\.?([0-9]*)");
				if(!isNumber){
					keyvalue="'"+keyvalue+"'";
				}
			}
			ext=StringUtils.replace(ext, "$:"+key+"#", keyvalue);
		}
		
		bicollocate.set_bExtendCondition(ext); // 扩展条件 （通常需要写入一个SQL子条件）
		bicollocate.set_bXDimColumn(act.getExtendedAttributes().get(
				"_bXDimColumn")); // X维度坐标轴
		bicollocate.set_bTargetColumns(act.getExtendedAttributes().get(
				"_bTargetColumns")); // 指标字段（多个用逗号隔开）
		bicollocate.set_bNextConnectColumn(act.getExtendedAttributes().get(
				"_bNextConnectColumn"));// 下一个字段（通常用来做钻取分析等应用，该字段需要配置上相关变量，实现与下一个环节的数据对接）
		bicollocate.set_bDisplayMode(act.getExtendedAttributes().get(
				"_bDisplayMode")); // 展示模式（chart-pie，chart-bar,
		// char-line,table）
		list.add(bicollocate);
		return list;
	}

	// 获取所有流程节点
	public List hybrid(String runtimeid) throws Exception {
		List addlist = new ArrayList();
		
		String NATURALNAME = WfEntry.iv().getProcessIdByRuntimeId(runtimeid);

		Activity[] list = WfEntry.iv().loadProcess(NATURALNAME).getActivity();
		for (int i = 0; i < list.length; i++) {
			column col = new column();
			col.setColumnname(list[i].getName());
			col.setColumnType(list[i].getId());
			addlist.add(col);
		}
		return addlist;
	}

	// 流程扭转，由人工指派下一个环节的活动节点
	public void nextByManual(String runtimeid,String activityid, String activityid1)
			throws Exception {
		List list = WfEntry.iv().listAllRunningWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist name = (TWfWorklist) iterator.next();
			if (activityid1.equals(name.getActivityid())) {
				System.out.println(name.getWorkcode());
				WfEntry.iv().nextByManual(name.getWorkcode(), activityid, "");
				WorkflowConsole console = (WorkflowConsole) RmiEntry
						.iv("wfhandle");
				break;
			}
		}

	}

	// 砖去值传下取
	public void earndate(String runtimeid, String value,String strkey) throws Exception {
		WfEntry.iv().setVarValue(runtimeid, strkey, value);

	}

	// 流程扭转,自动扭转由工作流的路由逻辑控制
	public void nextByAuto(String runtimeid,String activityid) throws Exception {
		List list = WfEntry.iv().listAllRunningWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist name = (TWfWorklist) iterator.next();
			if (activityid.equals(name.getActivityid())) {


				WorkflowConsole console = (WorkflowConsole) RmiEntry
						.iv("wfhandle");
				console.commitActivity(name.getWorkcode());
				break;
			}
		}
	}

}
