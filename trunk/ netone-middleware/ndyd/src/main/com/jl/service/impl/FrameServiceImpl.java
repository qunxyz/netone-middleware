/**
 * 
 */
package com.jl.service.impl;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;
import com.jl.common.dyform.DyAnalysisXml;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormData;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.WfEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.FrameService;
import com.jl.web.controller.FrameAction;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class FrameServiceImpl extends BaseService implements FrameService {

	private final String $DYFORM = "_DYFORM";

	private final Logger LOG = Logger.getLogger(FrameServiceImpl.class);

	private final boolean debug = true;

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String saveAndUpdate(HttpServletRequest request, String participant,
			String formcode, DyFormData form, String subform, String fatherlsh)
			throws Exception {
		// long start = new Date().getTime();
		JSONObject json = new JSONObject();
		String id = form.getLsh();
		form.setFatherlsh(fatherlsh);
		boolean isnew = false;
		if (StringUtils.isNotEmpty(id)) {// ����Id ���ж��� �����޸Ļ��ǲ���
			boolean success = DyEntry.iv().modifyData(formcode, form);
			if (!success) {
				json.put("error", "yes");
				json.put("tip", "����ʧ��,�������ֶ���Ϣ������Ҫ��.");
				return json.toString();
			}
		} else {
			isnew = true;
			form.setParticipant(StringUtils.substringBetween(participant, "[",
					"]"));
			id = DyEntry.iv().addData(formcode, form);
			if (id == null) {
				json.put("error", "yes");
				json.put("tip", "����ʧ��,�������ֶ���Ϣ������Ҫ��.");
				return json.toString();
			}
		}

		// START ǰ�ýű�
		DyAnalysisXml dayx = new DyAnalysisXml();
		if (isnew) {
			TCsBus bux = new TCsBus();
			BeanUtils.copyProperties(bux, form);
			dayx.scriptPre(formcode, bux, "PNewSave");
		} else {
			dayx.script(formcode, id, "PUpdateSave"); // ��������
		}
		// END ǰ�ýű�

		// �ӱ�����
		if (subform != null && !"[]".equals(subform)) {
			// ������
			JSONArray jsonArray = JSONArray.fromObject(subform);

			List result = new ArrayList();
			Map formcodeMap = new HashMap();// ��Ÿ���form����
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				DyFormData subdata = new DyFormData();
				BeanUtils.copyProperties(subdata, obj);
				subdata.setParticipant(participant);
				subdata.setFatherlsh(id);
				result.add(subdata);

				formcodeMap.put(subdata.getFormcode(), subdata.getFormcode());
			}

			// ɾ���ӱ�
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			DyForm[] subdyforms = dyform.getSubform_();
			if (subdyforms != null) {
				for (int i = 0; i < subdyforms.length; i++) {
					DyForm subdyform = subdyforms[i];
					if (formcodeMap.containsKey(subdyform.getFormcode())) {// ������form������ɾ��
						DyEntry.iv().deleteAll(subdyform.getFormcode(), id);
					}
				}
			}

			// �����
			if (result.size() > 0)
				DyEntry.iv().addAll(result);
		}

		// START ���ýű�
		// System.out.println("new data coming:" + lsh);
		if (isnew) {
			dayx.script(formcode, id, "NewSave");
		} else {
			dayx.script(formcode, id, "UpdateSave"); // ��������
		}
		// END ���ýű�

		// long end = new Date().getTime();
		// System.out.println("���滨��ʱ�䣺" + (double) (end - start) / 1000 + "��");
		json.put("tip", "����ɹ�");
		json.put("lsh", id);
		request.setAttribute("$lsh", id);
		// WebCache.removeCache($DYFORM + formcode + id + "true");
		// WebCache.removeCache($DYFORM + formcode + id + "false");
		return json.toString();
	}

	public String delete(HttpServletRequest request, String formcode, String lsh)
			throws Exception {
		JSONObject json = new JSONObject();

		// START ǰ�ýű�
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END ǰ�ýű�

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "�õ�δ����,��������!");
		} else {
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			DyForm[] subdyforms = dyform.getSubform_();
			if (subdyforms != null) {
				for (int i = 0; i < subdyforms.length; i++) {
					DyForm subdyform = subdyforms[i];
					DyEntry.iv().deleteAll(subdyform.getFormcode(), lsh);
				}
			}
			boolean success = DyEntry.iv().deleteData(formcode, lsh);
			if (success) {
				String runtimeidx = WfEntry.iv().getSession(lsh);
				if (runtimeidx != null) {
					WfEntry.iv().deleteProcess(runtimeidx);
				}
				json.put("tip", "���ϳɹ�!");
			} else {
				json.put("error", "yes");
				json.put("tip", "����ʧ��!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START ���ýű�
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END ���ýű�

		return json.toString();
	}

	public String deleteByLogic(HttpServletRequest request, String formcode,
			String lsh) throws Exception {
		JSONObject json = new JSONObject();

		// START ǰ�ýű�
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END ǰ�ýű�

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "�õ�δ����,����鵵!");
		} else {
			boolean success = DyEntry.iv().deleteDataByLogic(formcode, lsh);
			if (success) {
				String runtimeid = WfEntry.iv().getSession(lsh);
				if (runtimeid != null && !runtimeid.equals("")) {
					String clientId = WfEntry.iv().getVarByRuntimeId(runtimeid,
							TWfConsoleIfc._DEFAULT_REV_KEY_CUSTOMER);
					List list = WfEntry.iv().listAllRunningWorklistByRuntimeid(
							runtimeid);
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						TWfWorklist object = (TWfWorklist) iterator.next();
						String error = WfEntry.iv().nextToEnd(
								object.getWorkcode(), clientId);
						if (StringUtils.isNotEmpty(error)) {
							json.put("error", "yes");
							json.put("tip", "�鵵ʧ��(" + error + ")");
							return json.toString();
						}
					}
				}
				json.put("tip", "�鵵�ɹ�!");
			} else {
				json.put("error", "yes");
				json.put("tip", "�鵵ʧ��!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START ���ýű�
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END ���ýű�

		return json.toString();
	}

	public String load(String workcode, String naturalname, DyForm dyform,
			String lsh, boolean isedit, Map subformmode, String userinfo,
			String parameter) throws Exception {
		// long start = new Date().getTime();
		StringBuffer html = new StringBuffer();
		userinfo = userinfo.replace("//", "/");
		html.append(load_(dyform, isedit, subformmode, userinfo, workcode,
				naturalname, lsh, parameter));
		// long end = new Date().getTime();
		// System.out.println("���ػ���ʱ�䣺" + (double) (end - start) / 1000 + "��");
		return html.toString();
	}

	private String load_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter) throws Exception {
		StringBuffer html = new StringBuffer();
		DyForm[] subdyforms = dyform.getSubform_();
		Boolean ishidden = false;// �Ƿ�����
		if (subformmode != null && subformmode.containsKey("MAINFORM")) {
			String submode = (String) subformmode.get("MAINFORM");
			if ("0".equals(submode)) {// �༭
				isedit = true;
				ishidden = false;
			} else if ("1".equals(submode)) {// ֻ��
				isedit = false;
				ishidden = false;
			} else if ("2".equals(submode)) {// ����
				ishidden = true;
			} else {
				ishidden = false;
			}
		}

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		if (!ishidden) {
			html.append(DyFormBuildHtml.buildForm(dyform, isedit, userinfo,
					naturalname, lsh, false, false, parameter, hiddenid
							+ hiddenunid + hiddenlsh));
		} else {
			html.append("<div style='display:none'>"
					+ DyFormBuildHtml.buildForm(dyform, isedit, userinfo,
							naturalname, lsh, false, false, parameter, hiddenid
									+ hiddenunid + hiddenlsh) + "</div>");
		}
		if (subdyforms != null && subdyforms.length > 0) {
			Boolean issubedit = true;// �Ƿ�ɱ༭
			Boolean issubhidden = false;// �Ƿ�����
			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				if (subformmode == null) {
					issubedit = true;
				} else if (subformmode.containsKey(-1)) {
					String submode = (String) subformmode.get(-1);
					if ("0".equals(submode)) {// �༭
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// ֻ��
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// ����
						issubhidden = true;
					} else {
						issubedit = true;
						issubhidden = false;
					}
				} else {
					Object submode = subformmode.get(i);
					if (submode != null
							&& submode.getClass().getName().equals(
									"java.lang.Boolean")) {
						issubedit = true;
						issubhidden = false;
					} else {
						String submodex = (String) subformmode.get(i);
						if ("0".equals(submodex)) {// �༭
							issubedit = true;
							issubhidden = false;
						} else if ("1".equals(submodex)) {// ֻ��
							issubedit = false;
							issubhidden = false;
						} else if ("2".equals(submodex)) {// ����
							issubhidden = true;
						} else {
							issubedit = true;
							issubhidden = false;
						}

					}
				}

				if (issubedit == null)
					issubedit = true;

				String submode = subdyform.getSubmode();
				if ("2".equals(submode)) {// 2:����չʾ-�����ӱ���¼(��Ҫ��������)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("3".equals(submode)) {// 3:����չʾ-���ӱ���¼(��Ҫ������������ϵͳ����ֻ��һ��)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("4".equals(submode)) {// 4:����չʾ-���ӱ���¼(ϵͳ����ֻ��һ��)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								true, parameter, ""));
				} else if ("5".equals(submode)) {// 5:����չʾ-���ӱ���¼(ϵͳ����ֻ��һ��)
					// ����ʾ����
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								false, parameter, ""));
				} else if ("9".equals(submode)) {// 1+:����չʾ-���ֱ�������¼
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildSubForms(subdyform,
								lsh, issubedit, userinfo, parameter));
				} else {// 1:����չʾ-�����ӱ���¼��Ĭ��ģʽ��
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildSubForm(subdyform,
								lsh, issubedit, userinfo, parameter));
				}
			}
		}
		return html.toString();
	}

	public Map newWfNode(HttpServletRequest request, String naturalname,
			String processid, String runtimeid, User user, String id,
			String participant_) throws Exception {
		Map wfmap = new HashMap();
		String clientId = participant_;
		String mode = naturalname;
		String ddid = id;
		String ddurl = "frame.do?method=onEditViewMain&naturalname="
				+ naturalname + "&lsh=";
		boolean isspecial = false;//
		if (StringUtils.isEmpty(runtimeid) || "null".equals(runtimeid)) {
			String runtimeidx = WfEntry.iv().getSession(id);
			if (runtimeidx != null && !runtimeidx.equals("")) {
				runtimeid = runtimeidx;
				isspecial = true;
			} else {
				runtimeid = WfEntry.iv().newProcess(processid, clientId, mode,
						"", ddid, ddurl);
				WfEntry.iv().runProcess(runtimeid);
			}
		}
		List listx = WfEntry.iv().useCoreView().fetchRunningWorklist(runtimeid);
		TWfWorklist TWfWorklistx = (TWfWorklist) listx.get(0);

		String workcode = TWfWorklistx.getWorkcode();
		// ������е���һ���ڵ�
		List xx = WfEntry.iv().listNextRouteActive(processid,
				TWfWorklistx.getActivityid(), runtimeid, user.getUserCode());
		if (isspecial == false) {
			saveYijian(request, workcode, user.getUserCode(), "�½�");
		}
		if (xx.size() == 1) {
			TWfActive Activity = (TWfActive) xx.get(0);
			String participant = Activity.getParticipant();

			boolean isNeedReader = false;
			boolean isNeedAssistant = false;
			if (Activity.isNeedReader() == true) {
				isNeedReader = Activity.isNeedReader();
			}
			if (Activity.isNeedAssistant() == true) {
				isNeedAssistant = Activity.isNeedAssistant();
			}

			wfmap.put("pmode", Activity.getParticipantmode());
			wfmap.put("autoroute", Activity.isAutoroute());
			wfmap.put("needsync", Activity.isNeedsync());
			wfmap.put("singleman", Activity.isSingleman());
			wfmap.put("needtree", Activity.isNeedTree());
			wfmap.put("name", Activity.getName());
			wfmap.put("type", Activity.getParticipantmode());
			participant = participant == null ? "" : participant;
			FrameAction.setWfUser(participant, wfmap);
			wfmap.put("value", "0");
			wfmap.put("activeids", Activity.getId());

			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
			wfmap.put("ismultinode", false);// ����֧
		} else {
			wfmap.put("ismultinode", true);// ���֧
			wfmap.put("commiter", user.getUserCode());
			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
		}
		wfmap.put("isspecial", isspecial);
		return wfmap;
	}

	// �ύ ��������
	public String newEnd(HttpServletRequest request, String naturalname,
			String cachekey, String activityid, String filteractiveids,
			String workcode, String participant, String userCodes,
			String userNames, String limittimes, String processlen,
			boolean issync, String operatemode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "�ύ�ɹ�");
		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = worklist.getRuntimeid();
		// String yijian = worklist.getExtendattribute();

		// ԭ����operatemode �жϣ����Ƿ��ִ��ݹ�����operatemode��������Ϊ��
		if ("03".equals(operatemode)) {
			json.put("tip", "���ĳɹ�");
			return json.toString();
		} else if ("04".equals(operatemode)) {
			json.put("tip", "�׶��Իظ��ɹ�");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "�׶��Իظ�ʧ��(" + error + ")");
			}
			return json.toString();

		} else if ("end".equals(operatemode)
				|| FrameService.trackActionSpecialTypeEND
						.contains(filteractiveids)) {
			json.put("tip", "�鵵�ɹ�");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "�鵵ʧ��(" + error + ")");
			}
			return json.toString();
		}

		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// ���̻�ڵ�
		Map<String, StringBuffer> specialActivityMap = new HashMap();// �����ڵ�
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		boolean end = false;
		String filteractiveids_ = filteractiveids + ",";

		if (StringUtils.isNotEmpty(processlen)) {
			Integer len = Integer.parseInt(processlen);
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType1,
					"");
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType2,
					"");
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType3,
					"");
			filteractiveids_.replaceAll(FrameService.trackActionSpecialType4,
					"");
			// filteractiveids_.replaceAll(FrameService.trackActionSpecialTypeEND,
			// "");
			filteractiveids_ = StringUtils.substring(filteractiveids_, 0,
					filteractiveids_.length() - 1);
			String[] aids = filteractiveids_.split(",");

			if (specialActivityMap
					.containsKey(FrameService.trackActionSpecialType3)) {
				if (aids.length <= 0) {
					json.put("tip", "���ڷ�֧����ָ����Ա!");
					json.put("error", "yes");
					return json.toString();
				}
			} else if (specialActivityMap
					.containsKey(FrameService.trackActionSpecialType4)) {

			} else {
				if (len == 0) {
					end = true;
				} else if (aids.length != activityMap.size()) {
					json.put("tip", "���ڶ������,�������з�֧����ָ����Ա!");
					json.put("error", "yes");
					return json.toString();
				}
			}

		}

		if (end == false) {
			if (!FrameService.trackActionSpecialType4.contains(filteractiveids)) {
				if (!specialActivityMap
						.containsKey(FrameService.trackActionSpecialType3)) {
					String error = "";
					if (activityMap.size() == 1) {
						error = nextNormalByManual(activityMap, workcode,
								participant);
					} else {
						error = nextNormalByAuto(workcode, participant);
					}
					if (StringUtils.isNotEmpty(error)) {
						json.put("error", "yes");
						json.put("tip", "�ύʧ��(" + error + ")");
						return json.toString();
					}
				}
			}

			List listWorklistnext = WfEntry.iv().useCoreView()
					.fetchRunningWorklist(runtimeid);

			// ��������
			for (Iterator iteratorX = listWorklistnext.iterator(); iteratorX
					.hasNext();) {
				TWfWorklist object = (TWfWorklist) iteratorX.next();
				String activityid_for_check = object.getActivityid();

				if (specialActivityMap
						.containsKey(FrameService.trackActionSpecialType3)
						|| specialActivityMap
								.containsKey(FrameService.trackActionSpecialType4)) {
					ready_to_specify_reader(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				} else {
					ready_to_specify(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				}
			}

			if (FrameService.trackActionSpecialType4.contains(filteractiveids)) {
				json.put("tip", "�鵵�ɹ�");
				// WebCache.removeCache($DYFORM + cachekey);
				String error = WfEntry.iv().nextToEnd(workcode, participant);
				if (StringUtils.isNotEmpty(error)) {
					json.put("error", "yes");
					json.put("tip", "�鵵ʧ��(" + error + ")");
				}
				// return json.toString();
			}

		} else {
			// WebCache.removeCache($DYFORM + cachekey+"false");
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "�ύʧ��(" + error + ")");
				return json.toString();
			}
		}

		return json.toString();
	}

	// �˰졢����
	public String auditEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames, String limittimes,
			boolean issync) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "�ύ�ɹ�");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// ���̻�ڵ�
		Map<String, StringBuffer> specialActivityMap = new HashMap();// �����ڵ�
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		// ��������
		TWfWorklist TWfWorklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = TWfWorklist.getRuntimeid();

		String error = "";
		if (activityMap.size() == 1) {
			error = nextNormalByManual(activityMap, workcode, participant);
		} else {
			error = nextNormalByAuto(workcode, participant);
		}
		if (StringUtils.isNotEmpty(error)) {
			json.put("error", "yes");
			json.put("tip", "�ύʧ��(" + error + ")");
			return json.toString();
		}

		String[] workcodearr = WfEntry.iv().getRunningWorkCodeByRuntimeid(
				runtimeid);
		for (int i = 0; i < workcodearr.length; i++) {
			String runningWorkcode = workcodearr[i];
			TWfWorklist worklist = WfEntry.iv().loadWorklist(runningWorkcode);

			ready_to_specify(naturalname, activityMap, specialActivityMap,
					limitMap, worklist.getActivityid(), worklist, participant,
					issync, "02");
		}
		return json.toString();
	}

	public String assignEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "�ύ�ɹ�");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = "".split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// ���̻�ڵ�
		Map<String, StringBuffer> specialActivityMap = new HashMap();// �����ڵ�
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String error = WfEntry.iv().nextToZhuanbang(workcode, participant);
		if (StringUtils.isNotEmpty(error)) {
			json.put("error", "yes");
			json.put("tip", "�ύʧ��(" + error + ")");
			return json.toString();
		}
		assign_to_specify(naturalname, activityMap, worklist.getActivityid(),
				worklist, participant);
		return json.toString();
	}

	private void setActivityMap(Map<String, StringBuffer> activityMap,
			Map<String, StringBuffer> specialActivityMap,
			Map<String, String> limitMap, String[] activityid_,
			String[] usercode_, String[] username_, String[] limittimes_) {
		for (int i = 0; i < usercode_.length; i++) {
			String tmp_activityid_ = activityid_[i];
			if (FrameService.trackActionSpecialType1
					.equalsIgnoreCase(tmp_activityid_)
					|| FrameService.trackActionSpecialType2
							.equalsIgnoreCase(tmp_activityid_)
					|| FrameService.trackActionSpecialType3
							.equalsIgnoreCase(tmp_activityid_)
					|| FrameService.trackActionSpecialType4
							.equalsIgnoreCase(tmp_activityid_)) {// ����
				if (specialActivityMap.containsKey(tmp_activityid_)) {
					StringBuffer sbx = specialActivityMap.get(tmp_activityid_);
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				} else {
					StringBuffer sbx = new StringBuffer();
					sbx.append(username_[i] + "[" + usercode_[i] + "]" + ",");
					specialActivityMap.put(tmp_activityid_, sbx);
				}
			} else {// ����
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
	}

	public String saveYijian(HttpServletRequest request, String workcode,
			String userCode, String yijian) throws Exception {
		JSONObject json = new JSONObject();
		WfEntry.iv().saveAuditNote(workcode, userCode, yijian);
		json.put("tip", "�����д�ɹ�");
		return json.toString();
	}

	public PageInfo queryForPage(DyFormData dydata, int from, int to,
			String condition) {
		PageInfo obj = new PageInfo();
		try {
			List list = DyEntry.iv().queryData(dydata, from, to, condition);
			int total = DyEntry.iv().queryDataNum(dydata, condition);

			obj.setTotalRows(total);
			obj.setResultList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	// ��һ�� ��������
	private String nextNormalByManual(Map activityMap, String workcode,
			String participant) throws Exception {
		String error = "";
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			error = WfEntry.iv().nextByManual(workcode, activityidx,
					participant);
			if (StringUtils.isNotEmpty(error)) {
				return error;
			}
		}
		return error;
	}

	// ��һ���Զ� ��������
	private String nextNormalByAuto(String workcode, String participant)
			throws Exception {
		return WfEntry.iv().nextByAuto(workcode, participant);
	}

	// ��һ�� ��������
	@Deprecated
	private void nextAssistantByManual(Map specialActivityMap, String workcode,
			String participant, String yijian) throws Exception {
		for (Iterator iterator = specialActivityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (FrameService.trackActionSpecialType1.equals(activityidx)) {// ����
				// WfEntry.iv().nextByAuto(workcode, participant);
			}
		}
	}

	// ��ʼ����
	private void ready_to_specify(String naturalname, Map activityMap,
			Map specialActivityMap, Map<String, String> limitMap,
			String activityid, TWfWorklist worklist, String participant,
			boolean issync, String opemode) throws Exception {
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (activityidx != null && activityidx.equals(activityid)) {
				String person = activityMap.get(activityidx).toString();
				String limit = limitMap.get(activityidx).toString();

				if (limit == null || StringUtils.isEmpty(limit))
					limit = "0";

				person = StringUtils.substring(person.toString(), 0, person
						.toString().length() - 1);
				String[] persons = person.split(",");

				TWfActive actinfo = WfEntry.iv().loadRuntimeActive(
						worklist.getProcessid(), worklist.getActivityid(),
						naturalname, "", worklist.getRuntimeid());

				for (int i = 0; i < persons.length; i++) {
					WfEntry.iv().specifyParticipantByWorkcode(participant,
							worklist.getWorkcode(), persons[i],
							actinfo.isNeedsync(), opemode);
					WfEntry.iv().specifyLimit(worklist.getWorkcode(),
							persons[i], Long.valueOf(limit));
				}

				for (Iterator iteratorx = specialActivityMap.keySet()
						.iterator(); iteratorx.hasNext();) {
					String activityidxx = (String) iteratorx.next();
					String personx = specialActivityMap.get(activityidxx)
							.toString();
					String limitx = limitMap.get(activityidxx).toString();

					if (limitx == null || StringUtils.isEmpty(limitx))
						limitx = "0";

					personx = StringUtils.substring(personx.toString(), 0,
							personx.toString().length() - 1);
					String[] personxs = personx.split(",");

					for (int i = 0; i < personxs.length; i++) {
						if (FrameService.trackActionSpecialType1
								.equals(activityidxx)) {// ����
							WfEntry.iv().specifyAssistantByWorkcode(
									participant, worklist.getWorkcode(),
									personxs[i], issync, opemode);
						} else if (FrameService.trackActionSpecialType2
								.equals(activityidxx)) {// ����
							WfEntry.iv().specifyReaderByWorkcode(participant,
									worklist.getWorkcode(), personxs[i],
									opemode);
						} else if (FrameService.trackActionSpecialType4
								.equals(activityidxx)) {// �鵵����
							WfEntry.iv().specifyReaderByWorkcode(participant,
									worklist.getWorkcode(), personxs[i],
									opemode);
						}
						WfEntry.iv().specifyLimit(worklist.getWorkcode(),
								personxs[i], Long.valueOf(limitx));
					}
				}

			}
		}
	}

	// ��ʼ���� �Ķ�
	private void ready_to_specify_reader(String naturalname, Map activityMap,
			Map specialActivityMap, Map<String, String> limitMap,
			String activityid, TWfWorklist worklist, String participant,
			boolean issync, String opemode) throws Exception {

		for (Iterator iteratorx = specialActivityMap.keySet().iterator(); iteratorx
				.hasNext();) {
			String activityidxx = (String) iteratorx.next();
			String personx = specialActivityMap.get(activityidxx).toString();
			String limitx = limitMap.get(activityidxx).toString();

			if (limitx == null || StringUtils.isEmpty(limitx))
				limitx = "0";

			personx = StringUtils.substring(personx.toString(), 0, personx
					.toString().length() - 1);
			String[] personxs = personx.split(",");

			for (int i = 0; i < personxs.length; i++) {
				if (FrameService.trackActionSpecialType1.equals(activityidxx)) {// ����
					WfEntry.iv().specifyAssistantByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], issync,
							opemode);
				} else if (FrameService.trackActionSpecialType2
						.equals(activityidxx)) {// ����
					WfEntry.iv().specifyReaderByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				} else if (FrameService.trackActionSpecialType3
						.equals(activityidxx)) {// �Ķ�
					WfEntry.iv().distributedSubmit(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				} else if (FrameService.trackActionSpecialType4
						.equals(activityidxx)) {// �鵵����
					WfEntry.iv().specifyReaderByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				}
				WfEntry.iv().specifyLimit(worklist.getWorkcode(), personxs[i],
						Long.valueOf(limitx));
			}
		}

	}

	// ת�����
	private void assign_to_specify(String naturalname, Map activityMap,
			String activityid, TWfWorklist worklist, String participant)
			throws Exception {
		for (Iterator iterator = activityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (activityidx != null && activityidx.equals(activityid)) {
				String person = activityMap.get(activityidx).toString();

				person = StringUtils.substring(person.toString(), 0, person
						.toString().length() - 1);
				String[] persons = person.split(",");

				for (int i = 0; i < persons.length; i++) {
					WfEntry.iv().specifyzhuanbangByWorkcode(participant,
							worklist.getWorkcode(), persons[i]);
				}

			}
		}
	}

	public String findResourceTree(String naturalname) throws Exception {
		String str = "";
		List set = SecurityEntry.iv().listDirRs(naturalname);
		str = buildResourceJsonStr(set);
		return str;
	}

	public PageInfo findResourceNodeInfo(String type, String naturalname,
			String condition, String start, String limit) throws Exception {
		PageInfo pageinfo = new PageInfo();
		int startindex = 0;
		if (StringUtils.isNotEmpty(start)) {
			startindex = Integer.parseInt(start);
		}
		int pagesize = 15;
		if (StringUtils.isNotEmpty(limit)) {
			pagesize = Integer.parseInt(limit);
		}
		List list = SecurityEntry.iv().listRsInDir(naturalname, type,
				startindex, pagesize, condition);
		Long total_ = SecurityEntry.iv().countRsInDir(naturalname, type,
				condition);
		int total = total_.intValue();
		pageinfo.setTotalRows(total);
		pageinfo.setResultList(list);
		return pageinfo;
	}

	public ResourceNode getResourceNode(String naturalname, String node)
			throws Exception {
		String v1 = "";
		String v2 = "";
		if (StringUtils.isNotEmpty(naturalname)) {
			naturalname = URLDecoder.decode(naturalname, "UTF-8");
			v1 = StringUtils.substringBefore(naturalname, "[");
			v2 = StringUtils.substringBetween(naturalname, "[", "]");
		}
		ResourceNode recnode = new ResourceNode();
		recnode.setNode_(v2);
		recnode.setNodeid(v2);
		recnode.setNodecode(v2);
		recnode.setNodename(v1);
		recnode.setParentnode(node);
		return recnode;
	}

	private String buildResourceJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Resource d = (Resource) itr.next();
			d.setId(d.getResourceid());
			d.setText(d.getResourcename());
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	public String dyformDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		String condition = request.getParameter("condition");
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		// DyFormColumn _formx[] = dyform.getAllColumn_();

		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh("1");
		dydata.setFormcode(formcode);

		List<DyFormData> list = DyEntry.iv().queryData(dydata, 0, 999999,
				condition);

		List<TableCell> headerList = new ArrayList<TableCell>();
		headerList.add(new TableCell("" + "�ĵ�ID"));
		Map listmap = new HashMap();
		for (int i = 0; i < dyform.getQueryColumn_().length; i++) {
			DyFormColumn _qc1 = dyform.getQueryColumn_()[i];
			headerList.add(new TableCell("" + _qc1.getColumname()));
			listmap.put(_qc1.getColumnid(), _qc1.getColumnid());
		}
		headerList.add(new TableCell("" + "�����������"));

		Table t = new Table();
		ReportExt reportExt = new ReportExt();
		Method[] ms = DyFormData.class.getMethods();
		for (int i = 0; i < list.size(); i++) {
			DyFormData DyFormData = list.get(i);
			TableRow trdata = new TableRow();
			trdata.addCell(new TableCell("" + DyFormData.getLsh()));
			for (Method m : ms) {
				if (m.getName().startsWith("get")
						&& m.getParameterTypes().length == 0
						&& !m.getName().equals("get")
						&& !m.getName().equals("getClass")) {
					char[] fieldch = m.getName().substring(3).toCharArray();
					fieldch[0] = Character.toLowerCase(fieldch[0]);
					String field = new String(fieldch);

					if (listmap.containsKey(field)) {
						Object value = m.invoke(DyFormData, null);
						value = value == null ? "" : value;

						trdata.addCell(new TableCell("" + value));
					}
				}
			}

			// S �����������
			Map relevantvar_tmp = (Map) commonDAO.findForObject(
					"Dyform.select_wf_relevantvar_tmp", DyFormData.getLsh());
			StringBuffer dealdetail = new StringBuffer();
			if (relevantvar_tmp != null) {
				String runtimeid = (String) relevantvar_tmp.get("runtimeid");
				List<TWfParticipant> listx = WfEntry.iv()
						.listAllParticipantinfo(runtimeid, true);
				for (TWfParticipant wfParticipant : listx) {
					dealdetail.append(wfParticipant.getUsername() + ":"
							+ wfParticipant.getCreatetime() + "#"
							+ wfParticipant.getAuditnode() + "\n");
				}
			}
			trdata.addCell(new TableCell("" + dealdetail.toString()));
			// E �����������

			t.addRow(trdata);
		}

		Report reportX = reportExt.setSimpleColHeader(t, headerList);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format("excel", "��������" + currentTimeMillis, reportX,
				response);
		return null;
	}

	public String dyformDealDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		String condition = request.getParameter("condition");
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		// DyFormColumn _formx[] = dyform.getAllColumn_();

		DyFormData dydata = new DyFormData();
		dydata.setFatherlsh("1");
		dydata.setFormcode(formcode);

		List<DyFormData> list = DyEntry.iv().queryData(dydata, 0, 999999,
				condition);

		List<TableCell> headerList = new ArrayList<TableCell>();
		headerList.add(new TableCell(""));
		headerList.add(new TableCell("������"));
		headerList.add(new TableCell("��ʼʱ��"));
		headerList.add(new TableCell("����ʱ��"));
		headerList.add(new TableCell("����"));
		headerList.add(new TableCell("�ĵ�ID"));
		headerList.add(new TableCell("��ʱ����"));
		headerList.add(new TableCell("��������"));

		Table t = new Table();
		ReportExt reportExt = new ReportExt();
		Method[] ms = DyFormData.class.getMethods();
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			DyFormData DyFormData = list.get(i);
			// trdata.addCell(new TableCell("" + DyFormData.getLsh()));

			Map relevantvar_tmp = (Map) commonDAO.findForObject(
					"Dyform.select_wf_relevantvar_tmp", DyFormData.getLsh());

			if (relevantvar_tmp != null) {
				String runtimeid = (String) relevantvar_tmp.get("runtimeid");
				String d0 = (String) relevantvar_tmp.get("d0");

				List<TWfParticipant> listx = WfEntry.iv()
						.listAllParticipantinfo(runtimeid, true);
				for (TWfParticipant wfParticipant : listx) {
					TableRow trdata = new TableRow();

					trdata.addCell(new TableCell("" + (++index)));// 0
					trdata.addCell(new TableCell(""
							+ wfParticipant.getUsername()));// 1
					trdata.addCell(new TableCell(""
							+ wfParticipant.getCreatetime()));// 2
					trdata.addCell(new TableCell(""
							+ wfParticipant.getDonetime()));// 3
					trdata.addCell(new TableCell(""
							+ wfParticipant.getActname()));// 4
					trdata.addCell(new TableCell("" + DyFormData.getLsh()));// 5
					trdata.addCell(new TableCell(""
							+ getBetweenDayNumber(
									wfParticipant.getCreatetime(),
									wfParticipant.getDonetime())));// 6
					trdata.addCell(new TableCell("" + d0));// 7
					t.addRow(trdata);
				}
			}
		}

		Report reportX = reportExt.setSimpleColHeader(t, headerList);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format("excel", "������������" + currentTimeMillis, reportX,
				response);

		return null;
	}

	/**
	 * ��������ʱ���֮����Ӳ�
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int getBetweenDayNumber(String dateA, String dateB) {
		if (StringUtils.isEmpty(dateA) || StringUtils.isEmpty(dateB))
			return -1;

		long dayNumber = 0;
		// 1Сʱ=60����=3600��=3600000
		long mins = 60L * 1000L;
		// long day= 24L * 60L * 60L * 1000L;��������֮��
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date d1 = df.parse(dateA);
			java.util.Date d2 = df.parse(dateB);
			dayNumber = (d2.getTime() - d1.getTime()) / mins;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) dayNumber;
	}

	public String saveConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();

		DyFormData data = new DyFormData();
		data.setLsh(lsh);
		data.setStatusinfo("01");
		data.setFatherlsh("1");
		data.setFormcode(formcode);
		User user = getOnlineUser(request);

		int count = DyEntry.iv().queryDataNum(data, " or statusinfo = '02' ");
		if (count > 0) {
			json.put("tip", "�����״̬,���ܽ�����������!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");
			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "ȷ�ϳɹ�!");
			} else {
				json.put("tip", "ȷ��ʧ��!");
				json.put("error", "yes");
			}
		}

		// START �ű��¼�
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Yesaffirm");// ȷ��
		}
		// END �ű��¼�

		return json.toString();
	}

	public String saveUnConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();

		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();

		DyFormData data = new DyFormData();
		data.setLsh(lsh);
		data.setStatusinfo("02");
		data.setFatherlsh("1");
		data.setFormcode(formcode);
		User user = getOnlineUser(request);

		int count = DyEntry.iv().queryDataNum(data, " or statusinfo = '01' ");
		if (count > 0) {
			json.put("tip", "�����״̬,���ܽ�����������!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");

			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "��ȷ�ϳɹ�!");
			} else {
				json.put("tip", "��ȷ��ʧ��!");
				json.put("error", "yes");
			}
		}

		// START �ű��¼�
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Onaffirm");// ��ȷ��
		}
		// END �ű��¼�

		return json.toString();
	}

	public String queryDyReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sql = request.getParameter("sql");
		List list = new ArrayList();
		if (StringUtils.isNotEmpty(sql)) {
			list = (List) commonDAO.select("Dyform.select_wf_info", sql);
		}
		List<TableCell> headerList = new ArrayList<TableCell>();
		Map field = (LinkedHashMap) list.get(0);
		for (Iterator iterator2 = field.keySet().iterator(); iterator2
				.hasNext();) {
			String key = (String) iterator2.next();
			headerList.add(new TableCell(""
					+ new String(key.getBytes("GBK"), "UTF-8")));
		}

		Table t = new Table();
		ReportExt reportExt = new ReportExt();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (LinkedHashMap) iterator.next();
			TableRow row = new TableRow();
			for (Iterator iterator2 = object.keySet().iterator(); iterator2
					.hasNext();) {
				String key = (String) iterator2.next();
				row.addCell(new TableCell("" + object.get(key)));
			}
			t.addRow(row);
		}

		Report reportX = reportExt.setSimpleColHeader(t, headerList);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport
				.format("excel", "����" + currentTimeMillis, reportX, response);
		return null;
	}

}
