package com.jl.common.workflow;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppHandleIfc;

/**
 * 该Sample在针对高级的流程应用相关例子
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Sample2 {

	public static void main(String[] args) throws Exception {

		List list = WfEntry.iv().useCoreView().coreSqlview(
				"select count(*) countx from t_wf");
		Map map = (Map) list.get(0);
		Integer intx = (Integer) map.get("countx");

		// ////////获得下一个节点的参与者，注：流程的参与者参考WFMC规范有6中，humen、role、department、system、resource、resourceset
		String processid = "BUSSWF.BUSSWF.NDYD.JTZTSP";
		String activityid = "xxx";// activityid通过workcode 可以获得
		String runtimeid = "";// 流程实例id
		// 获得所有的下一步节点
		List xx = WfEntry.iv().listNextRouteActive(processid, activityid,
				runtimeid,"");

		// 注意下一个节点可能有多个
		for (Iterator iterator = xx.iterator(); iterator.hasNext();) {
			Activity object = (Activity) iterator.next();
			String participant = object.getExtendedAttributes().get(
					AppHandleIfc._ACTIVITY_EXT_PARTICIPANT);
			String participantvalue[] = null;
			if (participant != null && !participant.equals("")) {
				participantvalue = StringUtils.split(participant, "$");
				String type = participantvalue[0];// 参与者类型
				String value = participantvalue[1];// 具体参与者
			}
		}
		// ///////////////////////////////////////////////////////

		// ////////填写审批意见和获得审批意见//////////////////////////
		// 获得所有审批意见
		List listx = WfEntry.iv().listAllDoneWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			String tip = object.getExtendattribute(); // 某条审批意见
		}
		// 填写审批意见
		String tip = "";
		String workcode = "";
		String participantx="";
		WfEntry.iv().saveAuditNote(workcode, participantx, tip);
		// ///////////////////////////////////////////////////////

		// /////////////获得所有可退办节点//////////////////
		List worklist = WfEntry.iv().listNextBackActive(runtimeid);
		for (Iterator iterator = worklist.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			String humen = object.getParticipant();// 该节点先前的办理者
													// 中文名[usercode],中文名2[usercode]

			String activityidx = object.getAct().getId();
			Activity actpre = WfEntry.iv().useCoreView().loadProcess(processid)
					.getActivity(activityidx);
			String participant = actpre.getExtendedAttributes().get(
					AppHandleIfc._ACTIVITY_EXT_PARTICIPANT);
			String participantvalue[] = null;
			if (participant != null && !participant.equals("")) {
				participantvalue = StringUtils.split(participant, "$");
				String type = participantvalue[0];// 参与者类型
				String value = participantvalue[1];// 具体参与者
			}
		}

		// /////////////获得特办节点//////////////////
		//List listxx = WfEntry.iv().listNextJumpActive(processid, runtimeid,"","");

	}
}
