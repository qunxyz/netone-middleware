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
 * ��Sample����Ը߼�������Ӧ���������
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

		// ////////�����һ���ڵ�Ĳ����ߣ�ע�����̵Ĳ����߲ο�WFMC�淶��6�У�humen��role��department��system��resource��resourceset
		String processid = "BUSSWF.BUSSWF.NDYD.JTZTSP";
		String activityid = "xxx";// activityidͨ��workcode ���Ի��
		String runtimeid = "";// ����ʵ��id
		// ������е���һ���ڵ�
		List xx = WfEntry.iv().listNextRouteActive(processid, activityid,
				runtimeid,"");

		// ע����һ���ڵ�����ж��
		for (Iterator iterator = xx.iterator(); iterator.hasNext();) {
			Activity object = (Activity) iterator.next();
			String participant = object.getExtendedAttributes().get(
					AppHandleIfc._ACTIVITY_EXT_PARTICIPANT);
			String participantvalue[] = null;
			if (participant != null && !participant.equals("")) {
				participantvalue = StringUtils.split(participant, "$");
				String type = participantvalue[0];// ����������
				String value = participantvalue[1];// ���������
			}
		}
		// ///////////////////////////////////////////////////////

		// ////////��д��������ͻ���������//////////////////////////
		// ��������������
		List listx = WfEntry.iv().listAllDoneWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			String tip = object.getExtendattribute(); // ĳ���������
		}
		// ��д�������
		String tip = "";
		String workcode = "";
		String participantx="";
		WfEntry.iv().saveAuditNote(workcode, participantx, tip);
		// ///////////////////////////////////////////////////////

		// /////////////������п��˰�ڵ�//////////////////
		List worklist = WfEntry.iv().listNextBackActive(runtimeid);
		for (Iterator iterator = worklist.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			String humen = object.getParticipant();// �ýڵ���ǰ�İ�����
													// ������[usercode],������2[usercode]

			String activityidx = object.getAct().getId();
			Activity actpre = WfEntry.iv().useCoreView().loadProcess(processid)
					.getActivity(activityidx);
			String participant = actpre.getExtendedAttributes().get(
					AppHandleIfc._ACTIVITY_EXT_PARTICIPANT);
			String participantvalue[] = null;
			if (participant != null && !participant.equals("")) {
				participantvalue = StringUtils.split(participant, "$");
				String type = participantvalue[0];// ����������
				String value = participantvalue[1];// ���������
			}
		}

		// /////////////����ذ�ڵ�//////////////////
		//List listxx = WfEntry.iv().listNextJumpActive(processid, runtimeid,"","");

	}
}
