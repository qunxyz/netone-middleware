/**
 * 
 */
package com.jl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.app.AppHandleIfc;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.WfEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.GroupTerminal;
import com.jl.entity.GroupTerminalDetail;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.GroupTerminalService;

public class GroupTerminalServiceImpl extends BaseService implements
		GroupTerminalService {

	private final Logger LOG = Logger.getLogger(GroupTerminalServiceImpl.class);

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String saveAndUpdate(GroupTerminal groupTerminal,
			String groupTerminalDetailInfo) throws Exception {
		JSONObject json = new JSONObject();
		String id = groupTerminal.getGroupTerminalId();
		if (StringUtils.isNotEmpty(id)) {// 根据Id 来判断是 否是修改还是插入
			groupTerminal = (GroupTerminal) commonDAO.update(
					"GroupTerminal.updateGroupTerminal", groupTerminal);
		} else {
			groupTerminal.setStatus(0);
			groupTerminal = (GroupTerminal) commonDAO.insert(
					"GroupTerminal.insertGroupTerminal", groupTerminal);
		}
		GroupTerminalDetail groupDetail = new GroupTerminalDetail();
		groupDetail.setGroupTerminalId(groupTerminal.getGroupTerminalId());
		this.commonDAO.delete("GroupTerminal.deleteAllByGroupTerminalId",
				groupTerminal.getGroupTerminalId());
		JSONArray jsonArray = JSONArray.fromObject(groupTerminalDetailInfo);
		List result = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String lastlastlastMonth = obj.getString("lastlastlastMonth");
			String monthBeforeLast = obj.getString("monthBeforeLast");
			String lastMonth = obj.getString("lastMonth");
			GroupTerminalDetail groupDetail_ = new GroupTerminalDetail();
			groupDetail_.setLastlastlastMonth(lastlastlastMonth);
			groupDetail_.setMonthBeforeLast(monthBeforeLast);
			groupDetail_.setLastMonth(lastMonth);
			groupDetail_.setGroupTerminalId(groupTerminal.getGroupTerminalId());
			result.add(groupDetail_);
		}
		this.commonDAO.insertBatch("GroupTerminal.insertGroupTerminalDetail",
				result);
		json.put("tip", "保存成功");
		json.put("groupTerminalId", groupTerminal.getGroupTerminalId());
		return json.toString();
	}

	public String delete(String groupTerminalId) throws Exception {
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(groupTerminalId)) {
			json.put("tip", "该单未保存,无须作废!");
		} else {
			commonDAO.delete("GroupTerminal.deleteAllByGroupTerminalId",
					groupTerminalId);
			commonDAO.delete("GroupTerminal.deleteGroupTerminal",
					groupTerminalId);

			String runtimeidx = WfEntry.iv().getSession(groupTerminalId);
			if (runtimeidx != null) {
				WfEntry.iv().stopProcess(runtimeidx);
			}
			json.put("tip", "作废成功!");
		}
		return json.toString();
	}

	public Map load(String groupTerminalId) throws Exception {
		Map group = (Map) this.commonDAO.findForObject(
				"GroupTerminal.selectGroupTerminalById", groupTerminalId);
		return group;
	}

	public Collection loadGroupTerminalDetail(String groupTerminalId)
			throws Exception {
		Collection result = this.commonDAO.select(
				"GroupTerminal.loadGroupTerminalDetail", groupTerminalId);
		return result;
	}

	public Map newWfNode(String runtimeid, User user, String id,
			String participant_) throws Exception {
		Map wfmap = new HashMap();
		String processid = "BUSSWF.BUSSWF.NDYD.JTZTSP";
		String clientId = participant_;
		String mode = "APPFRAME.APPFRAME.NDYD";
		String ddid = id;
		String ddurl = "groupTerminal/groupTerminal.do?method=onEditViewMain&groupTerminalId=";

		// if (workcode == null) {
		// runtimeid = WfEntry.iv().getSession(id);
		// if (runtimeid != null) {
		// boolean isrun = WfEntry.iv().isWorkflowRunning(runtimeid);
		// if (isrun == true) {
		// } else {
		// runtimeid = WfEntry.iv().newProcess(processid, clientId,
		// mode, "", ddid, ddurl);
		// WfEntry.iv().runProcess(runtimeid);
		// }
		// } else {
		//				
		// }
		// }
		if (StringUtils.isEmpty(runtimeid) || "null".equals(runtimeid)) {
			runtimeid = WfEntry.iv().newProcess(processid, clientId, mode, "",
					ddid, ddurl);
			WfEntry.iv().runProcess(runtimeid);
		}
		List listx = WfEntry.iv().useCoreView().fetchRunningWorklist(runtimeid);
		TWfWorklist TWfWorklistx = (TWfWorklist) listx.get(0);

		String workcode = TWfWorklistx.getWorkcode();
		// 获得所有的下一步节点
		List xx = WfEntry.iv().listNextRouteActive(processid,
				TWfWorklistx.getActivityid(), runtimeid, user.getUserCode());
		TWfActive Activity = (TWfActive) xx.get(0);
		String participant = Activity.getParticipant();

		wfmap.put("autoroute", Activity.isAutoroute());
		wfmap.put("needsync", Activity.isNeedsync());
		wfmap.put("singleman", Activity.isSingleman());
		wfmap.put("name", Activity.getName());
		wfmap.put("type", Activity.getParticipantmode());
		wfmap.put("activeids", Activity.getId());
		if (AppHandleIfc._PARTICIPANT_MODE_HUMAN.equals(Activity
				.getParticipantmode())) {// 人员选择
			wfmap.put("value", user.getDepartmentId());

			String[] humens = Activity.getParticipant().split(",");
			StringBuffer usercodeSB = new StringBuffer();
			StringBuffer usernameSB = new StringBuffer();

			for (int i = 0; i < humens.length; i++) {
				usercodeSB.append(StringUtils.substringBetween(humens[i], "[",
						"]")
						+ ",");
				usernameSB.append(StringUtils.substringBefore(humens[i], "[")
						+ ",");
			}

			if (humens.length > 0) {
				wfmap.put("usercode", StringUtils.substring(usercodeSB
						.toString(), 0, usercodeSB.toString().length() - 1));
				wfmap.put("username", StringUtils.substring(usernameSB
						.toString(), 0, usernameSB.toString().length() - 1));
			}
		} else if (AppHandleIfc._PARTICIPANT_MODE_GROUP.equals(Activity
				.getParticipantmode())) {// 部门选择模式
			wfmap.put("value", Activity.getParticipant());
		}

		saveYijian(workcode, user.getUserCode(), "新建");
		wfmap.put("runtimeid", runtimeid);
		wfmap.put("workcode", workcode);
		return wfmap;
	}

	public String newEnd(String activityid, String workcode,
			String participant, String userCodes, String userNames,
			String limittimes, String processlen, boolean issync,
			String operatemode) throws Exception {
		JSONObject json = new JSONObject();
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		for (int i = 0; i < usercode_.length; i++) {
			String tmp_activityid_ = activityid_[i];
			if (GroupTerminalService.trackActionSpecialType1
					.equalsIgnoreCase(tmp_activityid_)
					|| GroupTerminalService.trackActionSpecialType2
							.equalsIgnoreCase(tmp_activityid_)) {// 特殊
				if (specialActivityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = specialActivityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				}
			} else {// 正常
				if (activityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = activityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				}
			}
			limitMap.put(tmp_activityid_, limittimes_[i]);
		}

		boolean end = false;
		if (StringUtils.isNotEmpty(processlen)) {
			Integer len = Integer.parseInt(processlen);
			if (len == 0) {
				end = true;
			} else if ((len - 2) != activityMap.size()) {
				json.put("tip", "存在多个流程,请在所有分支流程指派人员!");
				json.put("error", "yes");
				return json.toString();
			}
		}

		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = worklist.getRuntimeid();
		String yijian = worklist.getExtendattribute();

//		boolean commitRs = WfEntry.iv().next(workcode, participant, yijian);
		// saveYijian(workcode, yijian);

		// 原先用operatemode 判断，但是发现传递过来的operatemode参数总是为空
		if ("03".equals(operatemode)) {
			json.put("tip", "完成");
			return json.toString();
		}

//		if (!commitRs) {
//			delay_to_specify(activityMap, specialActivityMap, limitMap,
//					worklist, participant, runtimeid, issync);
//			json.put("tip", "完成");
//			return json.toString();
//		}

		if (end == false) {
			List listWorklistnext = WfEntry.iv().useCoreView()
					.fetchRunningWorklist(runtimeid);

			// 正常流程
			for (Iterator iteratorX = listWorklistnext.iterator(); iteratorX
					.hasNext();) {
				TWfWorklist object = (TWfWorklist) iteratorX.next();
				String activityid_for_check = object.getActivityid();

				ready_to_specify(activityMap, specialActivityMap, limitMap,
						activityid_for_check, object, participant, issync);
			}

		}

		json.put("tip", "完成");
		return json.toString();
	}

	public String auditEnd(String activityid, String workcode,
			String participant, String userCodes, String userNames,
			String limittimes, boolean issync) throws Exception {
		JSONObject json = new JSONObject();
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		for (int i = 0; i < usercode_.length; i++) {
			String tmp_activityid_ = activityid_[i];
			if (GroupTerminalService.trackActionSpecialType1
					.equalsIgnoreCase(tmp_activityid_)
					|| GroupTerminalService.trackActionSpecialType2
							.equalsIgnoreCase(tmp_activityid_)) {// 特殊
				if (specialActivityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = specialActivityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				}
			} else {// 正常
				if (activityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = activityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					activityMap.put(tmp_activityid_, sbx);
				}
			}
			limitMap.put(tmp_activityid_, limittimes_[i]);
		}

		// 正常流程
		TWfWorklist TWfWorklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = TWfWorklist.getRuntimeid();

		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
//			WfEntry.iv().jump(workcode, participant, activityidx);
		}

		String[] workcodearr = WfEntry.iv().getRunningWorkCodeByRuntimeid(
				runtimeid);
		for (int i = 0; i < workcodearr.length; i++) {
			String runningWorkcode = workcodearr[i];
			TWfWorklist worklist = WfEntry.iv().loadWorklist(runningWorkcode);

			ready_to_specify(activityMap, specialActivityMap, limitMap,
					worklist.getActivityid(), worklist, participant, issync);
		}

		json.put("tip", "完成");
		return json.toString();
	}

	public String saveYijian(String workcode, String userCode, String yijian)
			throws Exception {
		JSONObject json = new JSONObject();
		WfEntry.iv().saveAuditNote(workcode, userCode, yijian);
		json.put("tip", "意见填写成功");
		return json.toString();
	}

	public PageInfo queryForPage(Map map, PageInfo obj) {
		try {
			obj = commonDAO.selectForPage("GroupTerminal.findPageCount",
					"GroupTerminal.findForPage", map, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private void delay_to_specify(Map activityMap, Map specialActivityMap,
			Map<String, String> limitMap, TWfWorklist worklist,
			String participant, String runtimeid, boolean issync)
			throws Exception {
//		for (Iterator iterator = activityMap.keySet().iterator(); iterator
//				.hasNext();) {
//			String activityidx = (String) iterator.next();
//			String person = activityMap.get(activityidx).toString();
//
//			person = StringUtils.substring(person.toString(), 0, person
//					.toString().length() - 1);
//
//			TWfActive actx = WfEntry.iv().loadActive(worklist.getProcessid(),
//					activityidx);
//			WfEntry.iv().specifyParticipantByActivity(participant, activityidx,
//					runtimeid, person, actx.isNeedsync());
//			WfEntry.iv().specifyLimit(worklist.getWorkcode(), person,
//					Long.valueOf(limitMap.get(activityidx)));
//
//			for (Iterator iteratorx = specialActivityMap.keySet().iterator(); iteratorx
//					.hasNext();) {
//				String activityidxx = (String) iteratorx.next();
//				String personx = specialActivityMap.get(activityidxx)
//						.toString();
//
//				personx = StringUtils.substring(personx.toString(), 0, personx
//						.toString().length() - 1);
//
//				if (GroupTerminalService.trackActionSpecialType1
//						.equals(activityidxx)) {// 抄送
//					WfEntry.iv().specifyAssistantByActivity(participant,
//							activityidx, runtimeid, personx, issync);
//				} else if (GroupTerminalService.trackActionSpecialType2
//						.equals(activityidxx)) {// 抄阅
//					WfEntry.iv().specifyReaderByActivity(participant,
//							activityidx, runtimeid, personx);
//				}
//				WfEntry.iv().specifyLimit(worklist.getWorkcode(), personx,
//						Long.valueOf(limitMap.get(activityidxx)));
//			}
//		}
	}

	private void ready_to_specify(Map activityMap, Map specialActivityMap,
			Map<String, String> limitMap, String activityid,
			TWfWorklist worklist, String participant, boolean issync)
			throws Exception {
//		for (Iterator iterator = activityMap.keySet().iterator(); iterator
//				.hasNext();) {
//			String activityidx = (String) iterator.next();
//			if (activityidx != null && activityidx.equals(activityid)) {
//				String person = activityMap.get(activityidx).toString();
//
//				person = StringUtils.substring(person.toString(), 0, person
//						.toString().length() - 1);
//
//				TWfActive actinfo = WfEntry.iv().loadActive(
//						worklist.getProcessid(), activityid);
//
//				WfEntry.iv().specifyParticipantByWorkcode(participant,
//						worklist.getWorkcode(), person, actinfo.isNeedsync());
//				WfEntry.iv().specifyLimit(worklist.getWorkcode(), person,
//						Long.valueOf(limitMap.get(activityidx)));
//
//				for (Iterator iteratorx = specialActivityMap.keySet()
//						.iterator(); iteratorx.hasNext();) {
//					String activityidxx = (String) iteratorx.next();
//					String personx = specialActivityMap.get(activityidxx)
//							.toString();
//
//					personx = StringUtils.substring(personx.toString(), 0,
//							personx.toString().length() - 1);
//
//					if (GroupTerminalService.trackActionSpecialType1
//							.equals(activityidxx)) {// 抄送
//						WfEntry.iv().specifyAssistantByWorkcode(participant,
//								worklist.getWorkcode(), personx, issync);
//					} else if (GroupTerminalService.trackActionSpecialType2
//							.equals(activityidxx)) {// 抄阅
//						WfEntry.iv().specifyReaderByWorkcode(participant,
//								worklist.getWorkcode(), personx);
//					}
//					WfEntry.iv().specifyLimit(worklist.getWorkcode(), personx,
//							Long.valueOf(limitMap.get(activityidxx)));
//				}
//
//			}
//		}
	}

}
