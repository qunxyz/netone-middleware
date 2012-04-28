package com.jl.web.controller.groupTerminal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.JSONUtil2;
import com.jl.common.TimeUtil;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfActivePass;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.WfEntry;
import com.jl.entity.GroupTerminal;
import com.jl.entity.GroupTerminalDetail;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.GroupTerminalService;
import com.jl.web.controller.AbstractAction;

public class GroupTerminalAction extends AbstractAction {

	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.getClientPermissions(request, response);
		return mapping.findForward("onMainView");
	}

	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		User user = getOnlineUser(request);
		Map conditionMap = new HashMap();
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ҳ��
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		if (StringUtils.isNotEmpty(user.getUserCode())) {
			conditionMap.put("clientId", user.getUserCode());
		}
		try {
			obj = (PageInfo) ins.queryForPage(conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map group = (Map) iterator.next();

				String groupTerminalId = (String) group.get("groupTerminalId");

				String clientId = (String) group.get("clientId");

				String runtimeid = WfEntry.iv().getSession(groupTerminalId);
				if (runtimeid != null && StringUtils.isNotEmpty(runtimeid)) {

					TWfRuntime runtime = WfEntry.iv().useCoreView()
							.loadRuntime(runtimeid);
					// ���̽��� runtime.isDone()�Ѱ��
					// �Ƿ��Ѿ��ύ���� runtime.isRunning()�Ѵ���

					if (runtime.isReady() == true) {
						if (clientId.equals(user.getUserCode())) {
							group.put("status", "<font color='red'>δ�ύ</font>");
							group.put("RUN", false);// �༭
						} else {
							group.put("status", "<font color='red'>δ�ύ</font>");
							group.put("RUN", true);// �鿴
						}
					}
					if (runtime.isDone() == true) {
						group.put("status", "<font color='#1285C9'>�Ѱ��</font>");
						group.put("RUN", true);// �鿴
					}
					if (runtime.isRunning() == true) {
						group.put("status", "<font color='green'>������</font>");
						group.put("RUN", true);// �鿴
					}
				} else {
					if (clientId.equals(user.getUserCode())) {
						group.put("status", "<font color='red'>δ�ύ</font>");
						group.put("RUN", false);// �༭
					} else {
						group.put("status", "<font color='red'>δ�ύ</font>");
						group.put("RUN", true);// �鿴
					}
				}
				group.put("runtimeid", runtimeid);

				String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ActionForward onEditViewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = BaseService.getOnlineUser(request);// ��ȡ��ǰ�û�
		request.setAttribute("onlineName", user.getUserName());

		String groupTerminalId = request.getParameter("groupTerminalId");
		String workcode = request.getParameter("workcode");
		return mapping.findForward("onEditViewMain");
	}

	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String groupTerminalId = request.getParameter("groupTerminalId");
		String workcode = request.getParameter("workcode");
		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// ��ȡ��ǰ�û�
			if (StringUtils.isNotEmpty(groupTerminalId)) {
				groupMap = ins.load(groupTerminalId);
			}
			request.setAttribute("cid", user.getUserCode());
			request.setAttribute("cname", user.getUserName());
			request.setAttribute("groupTerminal", groupMap);
			String clientId = (String) groupMap.get("clientId");

			if (StringUtils.isNotEmpty(workcode)) {
				TWfWorklist twf = WfEntry.iv().loadWorklist(workcode);
				request.setAttribute("runtimeid", twf.getRuntimeid());

			} else {
				request.setAttribute("processid", "BUSSWF.BUSSWF.NDYD.JTZTSP");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("onEditView");
	}

	public ActionForward onQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String groupTerminalId = request.getParameter("groupTerminalId");

		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// ��ȡ��ǰ�û�
			if (StringUtils.isNotEmpty(groupTerminalId)) {
				groupMap = ins.load(groupTerminalId);
			}
			request.setAttribute("cid", user.getUserCode());
			request.setAttribute("cname", user.getUserName());
			request.setAttribute("groupTerminal", groupMap);
			String clientId = (String) groupMap.get("clientId");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("onQuery");
	}

	public void onGroupTerminalDetailList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String groupTerminalId = request.getParameter("groupTerminalId");
		try {
			if (StringUtils.isEmpty(groupTerminalId)) {
				return;
			}
			Collection result = ins.loadGroupTerminalDetail(groupTerminalId);
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				GroupTerminalDetail groupDetail = (GroupTerminalDetail) iterator
						.next();
				String jsonStr = JSONUtil2.fromBean(groupDetail, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}

			super.writeJsonStr(response, super.buildJsonStr(jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");

		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		GroupTerminal groupTerminal = this.getGroupTerminalBean(request);
		String groupTerminalDetailInfo = request
				.getParameter("groupTerminalDetailInfo");
		try {
			String jsonx = ins.saveAndUpdate(groupTerminal,
					groupTerminalDetailInfo);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "������Ϣʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		return null;
	}

	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String groupTerminalId = request.getParameter("groupTerminalId");
		try {
			String jsonx = ins.delete(groupTerminalId);
			json = JSONObject.fromObject(jsonx);

		} catch (Exception e) {
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * �ͻ�ѡ��ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onEditViewClient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = BaseService.getOnlineUser(request);// ��ȡ��ǰ�û�
		request.setAttribute("onlineName", user.getUserName());
		return mapping.findForward("onEditViewClient");
	}

	/**
	 * ��ȡ���Žڵ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void onGetWfNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String groupTerminalId = request.getParameter("groupTerminalId");
		String runtimeid = request.getParameter("runtimeid");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(groupTerminalId)) {
				User user = getOnlineUser(request);
				Map map = ins.newWfNode(runtimeid, user, groupTerminalId, user
						.getUserName()
						+ "[" + user.getUserCode() + "]");
				json = JSONObject.fromObject(map);
			}
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �½�����
	public void onNewEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String activityid = request.getParameter("activityid");
		String limittimes = request.getParameter("limittime");
		String processlen = request.getParameter("processlen");
		String operatemode = request.getParameter("operatemode");
		String issync_ = request.getParameter("issync");
		String notice = request.getParameter("notice");
		String[] userCode_ = userCodes.split(",");
		JSONObject json = new JSONObject();
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			if (StringUtils.isNotEmpty(notice)) {
				if (notice.equals("0")) {// 0����1������
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, "", "");
					}
				}
			}

			String jsonx = ins.newEnd(activityid, workcode, user.getUserName()
					+ "[" + user.getUserCode() + "]", userCodes, userNames,
					limittimes, processlen, issync, operatemode);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �½�����
	public void onAuditEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String limittimes = request.getParameter("limittime");
		String activityid = request.getParameter("activityid");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String[] userCode_ = userCodes.split(",");
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			if (StringUtils.isNotEmpty(notice)) {
				if (notice.equals("0")) {// 0����1������
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, "", "");
					}
				}
			}
			String jsonx = ins.auditEnd(activityid, workcode, user
					.getUserName()
					+ "[" + user.getUserCode() + "]", userCodes, userNames,
					limittimes, issync);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �������
	public void onSaveYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupTerminalService ins = (GroupTerminalService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("groupTerminalService");
		String workcode = request.getParameter("workcode");
		String yijian = request.getParameter("yijian");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(yijian)) {
				yijian = yijian.trim();
			}
			User user = getOnlineUser(request);
			String jsonx = ins.saveYijian(workcode, user.getUserCode(), yijian);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// �������
	public void onLoadYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(workcode)) {
				workcode = workcode.trim();
			}
			// ����WORKCODE ��ȡ��� �������
			User user = getOnlineUser(request);
			// ����WORKCODE ��ȡ��� �������
			TWfParticipant worklist = WfEntry.iv().loadParticipantinfo(
					workcode, user.getUserCode());
			String jsonx = "";
			if (worklist != null) {
				jsonx = worklist.getAuditnode();
			}

			json.put("yinjian", jsonx);
		} catch (Exception e) {
			json.put("tip", "ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * ���ҳ�� ͨ�������͡��˰�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward onAuditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("onAuditView");
	}

	private GroupTerminal getGroupTerminalBean(HttpServletRequest request) {
		GroupTerminal groupTerminal = new GroupTerminal();
		String groupTerminalId = request.getParameter("groupTerminalId");
		String subjectId = request.getParameter("subjectId");
		String customerName = request.getParameter("customerName");
		String customerPhone = request.getParameter("customerPhone");
		String clientId = request.getParameter("clientId");
		String phone = request.getParameter("phone");
		String groupName = request.getParameter("groupName");
		String groupId = request.getParameter("groupId");
		String groupValueLevel = request.getParameter("groupValueLevel");
		String post = request.getParameter("post");
		String postLevel = request.getParameter("postLevel");
		String phoneManufacturers = request.getParameter("phoneManufacturers");
		String phoneModels = request.getParameter("phoneModels");
		String networkTime = request.getParameter("networkTime");
		String phonePrice = request.getParameter("phonePrice");
		String phoneAmount = request.getParameter("phoneAmount");
		String applicationProject = request.getParameter("applicationProject");
		String storedOrGive = request.getParameter("storedOrGive");
		String monthConsumption = request.getParameter("monthConsumption");
		String monthAmount = request.getParameter("monthAmount");
		String preferentialSchemes = request
				.getParameter("preferentialSchemes");
		String activityTitleAndDueTime = request
				.getParameter("activityTitleAndDueTime");
		String applyNote = request.getParameter("applyNote");

		if (StringUtils.isNotEmpty(groupTerminalId)) {
			groupTerminal.setGroupTerminalId(groupTerminalId);
		}
		if (StringUtils.isNotEmpty(subjectId)) {
			groupTerminal.setSubjectId(subjectId);
		}
		if (StringUtils.isNotEmpty(customerName)) {
			groupTerminal.setCustomerName(customerName);
		}
		if (StringUtils.isNotEmpty(customerPhone)) {
			groupTerminal.setCustomerPhone(customerPhone);
		}
		if (StringUtils.isNotEmpty(clientId)) {
			groupTerminal.setClientId(clientId);
		}
		if (StringUtils.isNotEmpty(phone)) {
			groupTerminal.setPhone(phone);
		}
		if (StringUtils.isNotEmpty(groupName)) {
			groupTerminal.setGroupName(groupName);
		}
		if (StringUtils.isNotEmpty(groupId)) {
			groupTerminal.setGroupId(groupId);
		}
		if (StringUtils.isNotEmpty(groupValueLevel)) {
			groupTerminal.setGroupValueLevel(groupValueLevel);
		}
		if (StringUtils.isNotEmpty(post)) {
			groupTerminal.setPost(post);
		}
		if (StringUtils.isNotEmpty(postLevel)) {
			groupTerminal.setPostLevel(postLevel);
		}
		if (StringUtils.isNotEmpty(phoneManufacturers)) {
			groupTerminal.setPhoneManufacturers(phoneManufacturers);
		}
		if (StringUtils.isNotEmpty(phoneModels)) {
			groupTerminal.setPhoneModels(phoneModels);
		}
		if (StringUtils.isNotEmpty(networkTime)) {
			groupTerminal
					.setNetworkTime(TimeUtil.parseDate(networkTime.trim()));
		}
		if (StringUtils.isNotEmpty(phonePrice)) {
			groupTerminal.setPhonePrice(phonePrice);
		}
		if (StringUtils.isNotEmpty(phoneAmount)) {
			groupTerminal.setPhoneAmount(Integer.valueOf(phoneAmount.trim()));
		}
		if (StringUtils.isNotEmpty(applicationProject)) {
			groupTerminal.setApplicationProject(applicationProject);
		}
		if (StringUtils.isNotEmpty(storedOrGive)) {
			groupTerminal.setStoredOrGive(storedOrGive);
		}
		if (StringUtils.isNotEmpty(monthConsumption)) {
			groupTerminal.setMonthConsumption(monthConsumption);
		}
		if (StringUtils.isNotEmpty(monthAmount)) {
			groupTerminal.setMonthAmount(monthAmount);
		}
		if (StringUtils.isNotEmpty(preferentialSchemes)) {
			groupTerminal.setPreferentialSchemes(Integer
					.valueOf(preferentialSchemes.trim()));
		}
		if (StringUtils.isNotEmpty(activityTitleAndDueTime)) {
			groupTerminal.setActivityTitleAndDueTime(activityTitleAndDueTime);
		}
		if (StringUtils.isNotEmpty(applyNote)) {
			groupTerminal.setApplyNote(applyNote);
		}
		return groupTerminal;
	}

	public ActionForward onShowView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String chooseFlag = request.getParameter("chooseresult");
//		List processList = new ArrayList();
//		String tip = "";
//		String workcode = request.getParameter("workcode");
//		String operatemode = request.getParameter("operatemode");
//		String processid = "BUSSWF.BUSSWF.NDYD.JTZTSP";
//		String activityid = "xxx";// activityidͨ��workcode ���Ի��
//		String runtimeid = "";// ����ʵ��id
//
//		TWfWorklist bean = (TWfWorklist) WfEntry.iv().loadWorklist(workcode);
//		activityid = bean.getActivityid();
//		runtimeid = bean.getRuntimeid();
//		List<Map> result = new ArrayList();
//		String userCodes = "";
//		String userNames = "";
//
//		User user = getOnlineUser(request);
//		String node = user.getDepartmentId();
//
//		if ("03".equals(operatemode)) {// ����
//			request.setAttribute("helpTip", "������ʾ: ������,������ϡ�");
//			request.setAttribute("processEndTip", "������ϡ�");
//			request.setAttribute("processTitle", "�������");
//			return mapping.findForward("onShowEndView");
//		}
//
//		if (chooseFlag.equals("0")) {
//
//			// ������е���һ���ڵ�
//			List resultList = WfEntry.iv().listNextRouteActive(processid,
//					activityid, runtimeid);
//
//			if (resultList.size() > 0) {
//				for (Iterator iterator = resultList.iterator(); iterator
//						.hasNext();) {
//					TWfActive object = (TWfActive) iterator.next();
//
//					Map tempMap = new HashMap();
//
//					tempMap.put("pmode", object.getParticipantmode());
//					tempMap.put("autoroute", object.isAutoroute());
//					tempMap.put("needsync", object.isNeedsync());
//					tempMap.put("singleman", object.isSingleman());
//
//					tempMap.put("name", object.getName());
//					tempMap.put("type", object.getParticipantmode());
//					String participant = object.getParticipant();
//					participant = participant == null ? "" : participant;
//					if (TWfConsoleIfc._PARTICIPANT_MODE_ROLE.equals(object
//							.getParticipantmode())) {// ��ɫѡ��
//						tempMap.put("value", "0");
//						List<Client3A> list = SecurityEntry.iv()
//								.listUserByRoleId(participant);
//						setWfUser(getUsers_(list), tempMap);
//					} else if (TWfConsoleIfc._PARTICIPANT_MODE_TEAM
//							.equals(object.getParticipantmode())) {// ��ѡ��
//						tempMap.put("value", "0");
//						List<Client3A> list = SecurityEntry.iv()
//								.listUserByTeamId(participant);
//						setWfUser(getUsers_(list), tempMap);
//					} else if (TWfConsoleIfc._PARTICIPANT_MODE_HUMAN
//							.equals(object.getParticipantmode())) {// ��Աѡ��
//						tempMap.put("value", "0");
//
//						String[] humens = participant.split(",");
//						setWfUser(humens, tempMap);
//					} else if (TWfConsoleIfc._PARTICIPANT_MODE_DEPT
//							.equals(object.getParticipantmode())) {// ����ѡ��ģʽ
//						tempMap.put("value", participant);
//					}
//					tempMap.put("activeids", object.getId());
//					result.add(tempMap);
//				}
//			} else {
//				request.setAttribute("helpTip", "������ʾ: ���̽���,�������,�������̡�");
//				request.setAttribute("processEndTip", "���̽�����");
//				request.setAttribute("processTitle", "�鵵");
//				return mapping.findForward("onShowEndView");
//			}
//
//			request.setAttribute("helpTip", "������ʾ����ѡ����һ�����̽ڵ㼰����ýڵ���Ա��");
//			if (result.size() > 1) {
//				request.setAttribute("processTitle", "���̷�֧");
//			} else {
//				request.setAttribute("processTitle", "��Աѡ��");
//			}
//			//result.addAll(listTrackAction("0"));
//			request.setAttribute("processList", result);
//			return mapping.findForward("onShowView");
//		} else if (chooseFlag.equals("1")) {
//			// /////////////������п��˰�ڵ�//////////////////
//			processList = WfEntry.iv().listNextBackActive(runtimeid);
//
//			for (Iterator iterator = processList.iterator(); iterator.hasNext();) {
//				TWfActivePass object = (TWfActivePass) iterator.next();
//
//				Map tempMap = new HashMap();
//				tempMap.put("autoroute", object.isAutoroute());
//				tempMap.put("needsync", object.isNeedsync());
//				tempMap.put("singleman", object.isSingleman());
//				tempMap.put("name", object.getName());
//				tempMap.put("type", object.getParticipantmode());
//				tempMap.put("pmode", TWfConsoleIfc._PARTICIPANT_MODE_HUMAN);
//				if (true) {// �˰�
//					tempMap.put("value", "0");
//
//					String[] humens = object.getParticipantOld();
//					String[] assistants = object.getAssistant();
//					String[] readers = object.getReader();
//					StringBuffer usercodeSB = new StringBuffer();
//					StringBuffer usernameSB = new StringBuffer();
//
//					for (int i = 0; i < humens.length; i++) {
//						usercodeSB.append(StringUtils.substringBetween(
//								humens[i], "[", "]")
//								+ ",");
//						usernameSB.append(StringUtils.substringBefore(
//								humens[i], "[")
//								+ ",");
//					}
//
//					for (int i = 0; i < assistants.length; i++) {
//						usercodeSB.append(StringUtils.substringBetween(
//								assistants[i], "[", "]")
//								+ ",");
//						usernameSB.append(StringUtils.substringBefore(
//								assistants[i], "[")
//								+ ",");
//					}
//
//					for (int i = 0; i < readers.length; i++) {
//						usercodeSB.append(StringUtils.substringBetween(
//								readers[i], "[", "]")
//								+ ",");
//						usernameSB.append(StringUtils.substringBefore(
//								readers[i], "[")
//								+ ",");
//					}
//
//					tempMap
//							.put("usercode", StringUtils.substring(usercodeSB
//									.toString(), 0, usercodeSB.toString()
//									.length() - 1));
//					tempMap
//							.put("username", StringUtils.substring(usernameSB
//									.toString(), 0, usernameSB.toString()
//									.length() - 1));
//
//				}
//				tempMap.put("activeids", object.getId());
//				result.add(tempMap);
//			}
//			// result.addAll(listTrackAction(node));
//			request.setAttribute("helpTip",
//					"������ʾ��������Ҫ�ǽ����˻ص�ָ���㣬ȷ����������ɡ�������޸ĵ����һ������ȡ����");
//			request.setAttribute("processTitle", "�˰�");
//			request.setAttribute("processList", result);
//			return mapping.findForward("onShowView");
//
//		} else if (chooseFlag.equals("2")) {
//			// /////////////����ذ�ڵ�//////////////////
//			processList = WfEntry.iv().listNextJumpActive(processid, runtimeid);
//
//			for (Iterator iterator = processList.iterator(); iterator.hasNext();) {
//				TWfActive object = (TWfActive) iterator.next();
//
//				Map tempMap = new HashMap();
//				tempMap.put("autoroute", object.isAutoroute());
//				tempMap.put("needsync", object.isNeedsync());
//				tempMap.put("singleman", object.isSingleman());
//				tempMap.put("name", object.getName());
//				tempMap.put("type", object.getParticipantmode());
//				tempMap.put("pmode", object.getParticipantmode());
//				String participant = object.getParticipant();
//				participant = participant == null ? "" : participant;
//
//				if (TWfConsoleIfc._PARTICIPANT_MODE_ROLE.equals(object
//						.getParticipantmode())) {// ��ɫѡ��
//					tempMap.put("value", "0");
//					List<Client3A> list = SecurityEntry.iv().listUserByRoleId(
//							participant);
//					setWfUser(getUsers_(list), tempMap);
//				} else if (TWfConsoleIfc._PARTICIPANT_MODE_TEAM.equals(object
//						.getParticipantmode())) {// ��ѡ��
//					tempMap.put("value", "0");
//					List<Client3A> list = SecurityEntry.iv().listUserByRoleId(
//							participant);
//					setWfUser(getUsers_(list), tempMap);
//				} else if (TWfConsoleIfc._PARTICIPANT_MODE_HUMAN.equals(object
//						.getParticipantmode())) {// ��Աѡ��
//					tempMap.put("value", "0");
//					String[] humens = participant.split(",");
//					setWfUser(humens, tempMap);
//				} else if (TWfConsoleIfc._PARTICIPANT_MODE_DEPT.equals(object
//						.getParticipantmode())) {// ����ѡ��ģʽ
//					tempMap.put("value", participant);
//				}
//				tempMap.put("activeids", object.getId());
//				result.add(tempMap);
//			}
//
//			// result.addAll(listTrackAction(node));
//			request.setAttribute("helpTip",
//					"������ʾ��������Ҫ�ǽ������͵�ָ���㣬ȷ����������ɡ�������޸ĵ����һ������ȡ����");
//			request.setAttribute("processTitle", "����");
//			request.setAttribute("processList", result);
//			return mapping.findForward("onShowView");
//		}
		return null;
	}

	// ϵͳ�Զ�����ҳ��
	public ActionForward onAutorouteView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("onAutorouteView");
	}

	/**
	 * ��ʾ����ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "����");
		tempMap.put("type", "department");
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("activeids", GroupTerminalService.trackActionSpecialType1);
		list.add(tempMap);

		Map tempMap2 = new HashMap();
		tempMap2.put("name", "����");
		tempMap2.put("type", "department");
		tempMap2.put("value", node);
		tempMap2.put("singleman", false);
		tempMap2.put("activeids", GroupTerminalService.trackActionSpecialType2);
		list.add(tempMap2);
		return list;
	}

	private void setWfUser(String[] humens, Map tempMap) {
		StringBuffer usercodeSB = new StringBuffer();
		StringBuffer usernameSB = new StringBuffer();

		for (int i = 0; i < humens.length; i++) {
			usercodeSB.append(StringUtils.substringBetween(humens[i], "[", "]")
					+ ",");
			usernameSB
					.append(StringUtils.substringBefore(humens[i], "[") + ",");
		}

		if (humens.length > 0) {
			tempMap.put("usercode", StringUtils.substring(
					usercodeSB.toString(), 0,
					usercodeSB.toString().length() - 1));
			tempMap.put("username", StringUtils.substring(
					usernameSB.toString(), 0,
					usernameSB.toString().length() - 1));
		}
	}

	private String[] getUsers_(List<Client3A> list) {
		String split = "";
		StringBuffer sb = new StringBuffer();
		for (Client3A client3A : list) {
			String usercode = client3A.getClientId();
			String username = client3A.getName();

			sb.append(split + username + "[" + usercode + "]");
			split = ",";
		}
		return sb.toString().split(",");
	}

}
