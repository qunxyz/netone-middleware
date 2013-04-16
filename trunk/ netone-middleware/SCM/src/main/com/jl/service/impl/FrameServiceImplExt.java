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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
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
import com.jl.common.dyform.DyFormBuildHtmlExt;
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
import com.jl.web.controller.FrameActionExt;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class FrameServiceImplExt extends BaseService implements FrameService {

	private final String $DYFORM = "_DYFORM";

	private final Logger LOG = Logger.getLogger(FrameServiceImplExt.class);

	private final boolean debug = true;

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String saveAndUpdate(HttpServletRequest request, String participant,
			String formcode, DyFormData form, String subform, String fatherlsh)
			throws Exception {
		// System.out.println(subform);
		// long start = new Date().getTime();
		JSONObject json = new JSONObject();
		String id = form.getLsh();
		form.setFatherlsh(fatherlsh);
		boolean isnew = false;
		if (StringUtils.isNotEmpty(id)) {// 根据Id 来判断是 否是修改还是插入
			boolean success = DyEntry.iv().modifyData(formcode, form);
			if (!success) {
				json.put("error", "yes");
				json.put("tip", "保存失败,表单输入字段信息不符合要求.");
				return json.toString();
			}
		} else {
			isnew = true;
			form.setParticipant(StringUtils.substringBetween(participant, "[",
					"]"));
			id = DyEntry.iv().addData(formcode, form);
			if (id == null) {
				json.put("error", "yes");
				json.put("tip", "保存失败,表单输入字段信息不符合要求.");
				return json.toString();
			}
		}

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		if (isnew) {
			TCsBus bux = new TCsBus();
			BeanUtils.copyProperties(bux, form);
			dayx.scriptPre(formcode, bux, "PNewSave");
		} else {
			dayx.script(formcode, id, "PUpdateSave"); // 正常保存
		}
		// END 前置脚本

		// 子表单操作
		if (subform != null && !"[]".equals(subform)) {
			// 新增表单
			JSONArray jsonArray = JSONArray.fromObject(subform);

			List result = new ArrayList();
			Map formcodeMap = new HashMap();// 存放更新form数据
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				DyFormData subdata = new DyFormData();
				BeanUtils.copyProperties(subdata, obj);
				subdata.setParticipant(participant);
				subdata.setFatherlsh(id);

				if (obj.containsKey("lsh")) {
					String $$lsh = subdata.getLsh();
					if ($$lsh.contains("_NEW_")) {// 新增
						String d = $$lsh.replace("_NEW_", "");
						DyEntry.iv().addData(subdata.getFormcode(), subdata);
					} else if ($$lsh.contains("_DEL_")) {// 删除
						String d = $$lsh.replace("_DEL_", "");
						DyEntry.iv().deleteData(subdata.getFormcode(), d);
					} else if ("null".equalsIgnoreCase($$lsh)) {// 删除所有 再新增
						result.add(subdata);
						formcodeMap.put(subdata.getFormcode(), subdata
								.getFormcode());
					} else {// 修改
						DyEntry.iv().modifyData(subdata.getFormcode(), subdata);
					}
				}

			}

			// 保存表单
			if (result.size() > 0) {
				// 删除子表单
				DyForm dyform = DyEntry.iv().loadForm(formcode);
				DyForm[] subdyforms = dyform.getSubform_();
				if (subdyforms != null) {
					for (int i = 0; i < subdyforms.length; i++) {
						DyForm subdyform = subdyforms[i];
						if (formcodeMap.containsKey(subdyform.getFormcode())) {// 不更新form数据则不删除
							DyEntry.iv().deleteAll(subdyform.getFormcode(), id);
						}
					}
				}

				DyEntry.iv().addAll(result);
			}

		}

		// START 后置脚本
		// System.out.println("new data coming:" + lsh);
		if (isnew) {
			dayx.script(formcode, id, "NewSave");
		} else {
			dayx.script(formcode, id, "UpdateSave"); // 正常保存
		}
		// END 后置脚本

		// long end = new Date().getTime();
		// System.out.println("保存花费时间：" + (double) (end - start) / 1000 + "秒");
		json.put("tip", "保存成功");
		json.put("lsh", id);
		request.setAttribute("$lsh", id);
		// WebCache.removeCache($DYFORM + formcode + id + "true");
		// WebCache.removeCache($DYFORM + formcode + id + "false");
		return json.toString();
	}

	public String delete(HttpServletRequest request, String formcode, String lsh)
			throws Exception {
		JSONObject json = new JSONObject();

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END 前置脚本

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "该单未保存,无须删除!");
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
				json.put("tip", "删除成功!");
			} else {
				json.put("error", "yes");
				json.put("tip", "删除失败!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START 后置脚本
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END 后置脚本

		return json.toString();
	}

	public String deleteByLogic(HttpServletRequest request, String formcode,
			String lsh) throws Exception {
		JSONObject json = new JSONObject();

		// START 前置脚本
		DyAnalysisXml dayx = new DyAnalysisXml();
		dayx.script(formcode, lsh, "PDelete");
		// END 前置脚本

		if (StringUtils.isEmpty(lsh)) {
			json.put("tip", "该单未保存,无须归档!");
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
							json.put("tip", "归档失败(" + error + ")");
							return json.toString();
						}
					}
				}
				json.put("tip", "归档成功!");
			} else {
				json.put("error", "yes");
				json.put("tip", "归档失败!");
			}
			// WebCache.removeCache($DYFORM + formcode + lsh + "true");
			// WebCache.removeCache($DYFORM + formcode + lsh + "false");
		}

		// START 后置脚本
		if (!json.containsKey("error")) {
			dayx.script(formcode, lsh, "Delete");
		}
		// END 后置脚本

		return json.toString();
	}

	public String load(String workcode, String naturalname, DyForm dyform,
			String lsh, boolean isedit, Map subformmode, String userinfo,
			String parameter, User user, boolean isprint) throws Exception {
		// long start = new Date().getTime();
		StringBuffer html = new StringBuffer();
		userinfo = userinfo.replace("//", "/");
		if (isprint) {
			html.append(loadPrint_(dyform, isedit, subformmode, userinfo,
					workcode, naturalname, lsh, parameter, user));
		} else {
			if (config.containsKey("framestyle")
					&& "html".equals(config.getString("framestyle"))) {
				html.append(loadHtml_(dyform, isedit, subformmode, userinfo,
						workcode, naturalname, lsh, parameter, user));
			} else {
				html.append(load_(dyform, isedit, subformmode, userinfo,
						workcode, naturalname, lsh, parameter, user));
			}
		}
		System.out.println(html.toString());
		// long end = new Date().getTime();
		// System.out.println("加载花费时间：" + (double) (end - start) / 1000 + "秒");
		return html.toString();
	}

	private Map getJsMap(String form, String id, String extjs, String mode) {
		Map map = new HashMap();
		map.put("form", "<div id=\"" + id + "_form\">" + form + "</div>");
		map.put("id", id);
		map.put("extjs", extjs);
		map.put("mode", mode);
		return map;
	}

	private String load_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter, User user) throws Exception {
		StringBuffer html = new StringBuffer();

		StringBuffer htmlSouth = new StringBuffer();
		StringBuffer htmlCenter = new StringBuffer();
		StringBuffer htmlCenterInnerEast = new StringBuffer();

		List<Map> listmaps = new ArrayList<Map>();

		DyForm[] subdyforms = dyform.getSubform_();
		// List tmptabList = new ArrayList();
		Boolean ishidden = false;// 是否隐藏
		// if (subformmode != null && subformmode.containsKey("MAINFORM")) {
		// String submode = (String) subformmode.get("MAINFORM");
		// if ("0".equals(submode)) {// 编辑
		// isedit = true;
		// ishidden = false;
		// } else if ("1".equals(submode)) {// 只读
		// isedit = false;
		// ishidden = false;
		// } else if ("2".equals(submode)) {// 隐藏
		// ishidden = true;
		// } else {
		// ishidden = false;
		// }
		// }

		String ids = "frame" + DyFormBuildHtmlExt.uuid();
		if (!ishidden) {
			listmaps.add(getJsMap(DyFormBuildHtmlExt.buildMainForm(dyform,
					isedit, userinfo, naturalname, lsh, false, false,
					parameter, user), ids, DyFormComp.getExtPanel(ids, null,
					null, ids, "", ""), "center"));
		} else {
			listmaps.add(getJsMap("<div style='display:none'>"
					+ DyFormBuildHtmlExt.buildMainForm(dyform, isedit,
							userinfo, naturalname, lsh, false, false,
							parameter, user) + "</div>", ids, DyFormComp
					.getExtPanel(ids, null, null, ids, "", ""), "center"));
		}

		boolean isCenterInnerEast = false;
		if (subdyforms != null && subdyforms.length > 0) {
			Boolean issubedit = true;// 是否可编辑
			Boolean issubhidden = false;// 是否隐藏

			List<String> formname = new ArrayList<String>();
			List<Map> formlist = new ArrayList<Map>();

			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				// if (subformmode == null) {
				// issubedit = true;
				// } else if (subformmode.containsKey(-1)) {
				// String submode = (String) subformmode.get(-1);
				// if ("0".equals(submode)) {// 编辑
				// issubedit = true;
				// issubhidden = false;
				// } else if ("1".equals(submode)) {// 只读
				// issubedit = false;
				// issubhidden = false;
				// } else if ("2".equals(submode)) {// 隐藏
				// issubhidden = true;
				// } else {
				// issubedit = true;
				// issubhidden = false;
				// }
				// } else {
				// String submode = (String) subformmode.get(i);
				// if ("0".equals(submode)) {// 编辑
				// issubedit = true;
				// issubhidden = false;
				// } else if ("1".equals(submode)) {// 只读
				// issubedit = false;
				// issubhidden = false;
				// } else if ("2".equals(submode)) {// 隐藏
				// issubhidden = true;
				// } else {
				// issubedit = true;
				// issubhidden = false;
				// }
				// }

				if (issubedit == null)
					issubedit = true;

				// 特殊处理
				if (isedit == false)
					issubedit = false;

				String submode = subdyform.getSubmode();
				if ("2".equals(submode)) {// 2:链接展示-多条子表单记录(需要保存主表单)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildLinkForm(
							subdyform, lsh, issubedit, userinfo, submode,
							workcode, naturalname, parameter, user), ids,
							DyFormComp
									.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("3".equals(submode)) {// 3:链接展示-单子表单记录(需要保存主表单、且系统控制只能一条)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildLinkForm(
							subdyform, lsh, issubedit, userinfo, submode,
							workcode, naturalname, parameter, user), ids,
							DyFormComp
									.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("4".equals(submode)) {// 4:集成展示-单子表单记录(系统控制只能一条)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, true, parameter, user), ids, DyFormComp
							.getExtPanel(ids, subdyform.getFormname(), null,
									ids, "", ""), "center"));
				} else if ("5".equals(submode)) {// 5:集成展示-单子表单记录(系统控制只能一条)
					// 不显示标题
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, false, parameter, user), ids, DyFormComp
							.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("1".equals(submode)) {// 1:集成展示-多条子表单记录（默认模式）
					ids = subdyform.getFormcode();

					Map subformmap = DyFormBuildHtmlExt.buildSubForm_(
							subdyform, lsh, isedit, userinfo, parameter, user);
					listmaps.add(getJsMap(subformmap.get("html").toString(),
							ids, DyFormComp.getExtPanel(ids, null, "", ids, "",
									subformmap.get("js").toString()
											+ ",autoScroll:true"), "center"));

				} else if ("6".equals(submode)) {// 6:集成展示-主表单右边表单
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildFormMode6(
							subdyform, isedit, userinfo, naturalname, lsh,
							true, false, parameter, user), ids, DyFormComp
							.getExtPanel(ids, "", null, ids, "", ""), "east"));
				} else if ("7".equals(submode)) {// 7:集成展示-多条子表单记录(选项卡模式)
					ids = subdyform.getFormcode();

					Map subformmap = DyFormBuildHtmlExt.buildSubForm_(
							subdyform, lsh, isedit, userinfo, parameter, user);
					// if
					// (subdyform.getFormcode().equals("82d995527be911e29806c59ba34f7180_")
					// ||
					// subdyform.getFormcode().equals("16d5ccb67bea11e29806c59ba34f7180_")
					// ||
					// subdyform.getFormcode().equals("3b682d187beb11e29806c59ba34f7180_")){
					// tmptabList.add(subformmap.get("html").toString());
					// } else {
					formname
							.add("<font style=\"font-size:12px\" onmouseover=\"javascript:this.color=&quot;red&quot;;\" onmouseout=\"javascript:this.color=&quot;white&quot;;\" >"
									+ subdyform.getFormname()
									+ "("
									+ subformmap.get("count") + ")" + "</font>");
					formlist.add(getJsMap(subformmap.get("html").toString(),
							ids, DyFormComp.getExtPanel(ids, null, null, ids,
									"", subformmap.get("js").toString()
											+ ",autoScroll:true"), "mode7"));
					// }

				} else if ("8".equals(submode)) {// 8:集成展示-单条子表单记录(选项卡模式)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					// if
					// (subdyform.getFormcode().equals("82d995527be911e29806c59ba34f7180_")
					// ||
					// subdyform.getFormcode().equals("16d5ccb67bea11e29806c59ba34f7180_")
					// ||
					// subdyform.getFormcode().equals("3b682d187beb11e29806c59ba34f7180_")){
					// tmptabList.add(DyFormBuildHtmlExt.buildForm(
					// subdyform, issubedit, userinfo, naturalname, lsh,
					// true, false, parameter, user));
					// } else {
					formname
							.add("<font style=\"font-size:12px\" onmouseover=\"javascript:this.color=&quot;red&quot;;\" onmouseout=\"javascript:this.color=&quot;white&quot;;\" >"
									+ subdyform.getFormname() + "</font>");
					formlist.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, false, parameter, user), ids, "", "mode8"));
					// }
				} else {
					// not do
				}

			}
			// if (tmptabList.size()>0){
			// ids = "frame" + DyFormBuildHtmlExt.uuid();
			// StringBuffer ss = new StringBuffer();
			// for (Iterator iterator = tmptabList.iterator(); iterator
			// .hasNext();) {
			// String map = (String) iterator.next();
			// ss.append(map);
			// }
			//				
			// formname.add("<font style=\"font-size:16px\" >"
			// +"111111"+"</font>");
			// formlist.add(getJsMap(ss.toString(), ids, "", ""));
			// }
			// 最终输出7,8 选项卡模式
			if (formlist.size() > 0) {
				ids = "frame" + DyFormBuildHtmlExt.uuid();

				listmaps.add(getJsMap(DyFormComp.getExtTabs_(ids, formname,
						formlist), ids, DyFormComp.getExtPanel(ids, null, "$"
						+ ids, null, "", ""), "center"));
			}
		}

		// // 附件
		// ids = "frame" + DyFormBuildHtmlExt.uuid();
		// StringBuffer fileframe = new StringBuffer();
		// fileframe.append("<iframe id='fileMainFrame' name='fileMainFrame' ");
		// fileframe.append(" src='/scm/file.do?method=onMainView&d_unid=" + lsh
		// + "' ");
		// fileframe.append(" scrolling='auto' frameborder='0' ");
		// fileframe.append(" style='width:900px;'> ");
		// fileframe.append("</iframe>");
		//
		// listmaps.add(getJsMap(fileframe.toString(), ids, DyFormComp
		// .getExtPanel(ids, null, null, ids, "", ""), "center"));
		// //end 附件

		// 表单重新布局排版
		StringBuffer layoutcenter = new StringBuffer();
		StringBuffer layouteast = new StringBuffer();
		StringBuffer layoutwest = new StringBuffer();
		StringBuffer layoutsouth = new StringBuffer();
		StringBuffer layoutnorth = new StringBuffer();

		for (Iterator iterator = listmaps.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			String form = (String) map.get("form");
			String id = (String) map.get("id");
			String extjs = (String) map.get("extjs");
			String mode = (String) map.get("mode");

			html.append(form + extjs);
			if ("center".equals(mode)) {
				layoutcenter.append("$" + id + ",");
			} else if ("east".equals(mode)) {
				layouteast.append("$" + id + ",");
			} else if ("west".equals(mode)) {
				layoutwest.append("$" + id + ",");
			} else if ("south".equals(mode)) {
				layoutsouth.append("$" + id + ",");
			} else if ("north".equals(mode)) {
				// layoutnorth.append(id + ",");
			}

		}

		String center = DyFormComp.getExtPanelAttr("center", "center", "panel",
				StringUtils.substringBeforeLast(layoutcenter.toString(), ","),
				"", "");
		String east = DyFormComp
				.getExtPanelAttr(
						"east",
						"east",
						"panel",
						StringUtils.substringBeforeLast(layouteast.toString(),
								","),
						"",
						"title:\"操作选项\",split:true,width:200,collapsible: true,frame:true,titleCollapse:true,animCollapse:true,collapseFirst:true");
		String west = DyFormComp.getExtPanelAttr("west", "west", "panel",
				StringUtils.substringBeforeLast(layoutwest.toString(), ","),
				"", "");
		String south = DyFormComp.getExtPanelAttr("south", "south", "panel",
				StringUtils.substringBeforeLast(layoutsouth.toString(), ","),
				null, "");
		// String north = layoutnorth.toString().substring(0,
		// layoutnorth.length() - 1);

		StringBuffer btnStr = new StringBuffer();

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(dyform.getFormcode());
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();

		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".MODI")) {
			btnStr
					.append("{text:' 保 存 ',id:'ext_b_add',iconCls:'addIcon',handler:function(){ _save();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".ADD")) {
			btnStr
					.append("{text:' 继续添加 ',id:'ext_b_add_continue',iconCls:'addIcon',handler:function(){ _continueAdd();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".DELE")) {
			btnStr
					.append("{text:' 删 除 ',id:'ext_b_delete',iconCls:'deleteIcon',handler:function(){ _delete();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".PASS")) {
			btnStr
					.append("{text:' 通 过 ',id:'ext_b_pass',iconCls:'addIcon',handler:function(){ _pass();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".UNPASS")) {
			btnStr
					.append("{text:' 不通过 ',id:'ext_b_unpass',iconCls:'deleteIcon',handler:function(){ _unpass();}},");
		}
		btnStr
				.append("{text:' 打 印 ',id:'ext_b_delete',iconCls:'print',handler: function(){_print();}},");

		// start 导入EXCEL
		String excelObjName = naturalname_dyform + ".EXCEL";
		UmsProtectedobject uponext = new UmsProtectedobject();
		uponext.setNaturalname(excelObjName);
		List listg = rs.queryObjectProtectedObj(uponext, null, 0, 1, "");
		if (listg != null && listg.size() == 1) {
			String actionurl = ((UmsProtectedobject) listg.get(0))
					.getActionurl();
			System.out.println("actionurl excel导入明细:" + actionurl);
			if (StringUtils.isNotEmpty(actionurl))
				btnStr
						.append("{text:' EXCEL导入明细 ',id:'ext_b_import',iconCls:'download',handler: function(){_import('"
								+ actionurl + "');}},");
		}
		// end 导入EXCEL

		btnStr
				.append("{text:' 关 闭 ',id:'ext_b_cancel',iconCls:'exitIcon',handler: function(){window.close();}}");

		String north = DyFormComp.getExtPanelAttr("north", "north", "toolbar",
				btnStr.toString(), null, "");
		String viewport = DyFormComp.getExtBorderViewport(center, east, west,
				south, north);
		// System.out.println("--------");
		// System.out.println(html.toString() + viewport);
		// System.out.println("--------");
		return html.toString() + viewport;
	}

	private String loadExt_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter, User user) throws Exception {
		StringBuffer html = new StringBuffer();

		StringBuffer htmlSouth = new StringBuffer();
		StringBuffer htmlCenter = new StringBuffer();
		StringBuffer htmlCenterInnerEast = new StringBuffer();

		List<Map> listmaps = new ArrayList<Map>();

		DyForm[] subdyforms = dyform.getSubform_();

		Boolean ishidden = false;// 是否隐藏
		if (subformmode != null && subformmode.containsKey("MAINFORM")) {
			String submode = (String) subformmode.get("MAINFORM");
			if ("0".equals(submode)) {// 编辑
				isedit = true;
				ishidden = false;
			} else if ("1".equals(submode)) {// 只读
				isedit = false;
				ishidden = false;
			} else if ("2".equals(submode)) {// 隐藏
				ishidden = true;
			} else {
				ishidden = false;
			}
		}

		String ids = "frame" + DyFormBuildHtmlExt.uuid();
		if (!ishidden) {
			listmaps.add(getJsMap(DyFormBuildHtmlExt.buildMainForm(dyform,
					isedit, userinfo, naturalname, lsh, false, false,
					parameter, user), ids, DyFormComp.getExtPanel(ids, null,
					null, ids, "", ""), "center"));
		} else {
			listmaps.add(getJsMap("<div style='display:none'>"
					+ DyFormBuildHtmlExt.buildMainForm(dyform, isedit,
							userinfo, naturalname, lsh, false, false,
							parameter, user) + "</div>", ids, DyFormComp
					.getExtPanel(ids, null, null, ids, "", ""), "center"));
		}

		boolean isCenterInnerEast = false;
		if (subdyforms != null && subdyforms.length > 0) {
			Boolean issubedit = true;// 是否可编辑
			Boolean issubhidden = false;// 是否隐藏

			List<String> formname = new ArrayList<String>();
			List<Map> formlist = new ArrayList<Map>();

			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				if (subformmode == null) {
					issubedit = true;
				} else if (subformmode.containsKey(-1)) {
					String submode = (String) subformmode.get(-1);
					if ("0".equals(submode)) {// 编辑
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// 只读
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// 隐藏
						issubhidden = true;
					} else {
						issubedit = true;
						issubhidden = false;
					}
				} else {
					String submode = (String) subformmode.get(i);
					if ("0".equals(submode)) {// 编辑
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// 只读
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// 隐藏
						issubhidden = true;
					} else {
						issubedit = true;
						issubhidden = false;
					}
				}

				if (issubedit == null)
					issubedit = true;

				// 特殊处理
				if (isedit == false)
					issubedit = false;

				String submode = subdyform.getSubmode();
				if ("2".equals(submode)) {// 2:链接展示-多条子表单记录(需要保存主表单)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildLinkForm(
							subdyform, lsh, issubedit, userinfo, submode,
							workcode, naturalname, parameter, user), ids,
							DyFormComp
									.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("3".equals(submode)) {// 3:链接展示-单子表单记录(需要保存主表单、且系统控制只能一条)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildLinkForm(
							subdyform, lsh, issubedit, userinfo, submode,
							workcode, naturalname, parameter, user), ids,
							DyFormComp
									.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("4".equals(submode)) {// 4:集成展示-单子表单记录(系统控制只能一条)
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, true, parameter, user), ids, DyFormComp
							.getExtPanel(ids, subdyform.getFormname(), null,
									ids, "", ""), "center"));
				} else if ("5".equals(submode)) {// 5:集成展示-单子表单记录(系统控制只能一条)
					// 不显示标题
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, false, parameter, user), ids, DyFormComp
							.getExtPanel(ids, null, null, ids, "", ""),
							"center"));
				} else if ("1".equals(submode)) {// 1:集成展示-多条子表单记录（默认模式）
					ids = subdyform.getFormcode();

					Map subformmap = DyFormBuildHtmlExt.buildExtSubForm(ids,
							subdyform, lsh, isedit, userinfo, parameter, user);
					listmaps.add(getJsMap(subformmap.get("html").toString(),
							ids, DyFormComp.getExtPanel(ids, null, "$" + ids
									+ "Grid", null, "", subformmap.get("js")
									.toString()
									+ ",autoScroll:true"), "center"));

				} else if ("6".equals(submode)) {// 6:集成展示-主表单右边表单
					ids = "frame" + DyFormBuildHtmlExt.uuid();
					listmaps.add(getJsMap(DyFormBuildHtmlExt.buildFormMode6(
							subdyform, isedit, userinfo, naturalname, lsh,
							true, false, parameter, user), ids, DyFormComp
							.getExtPanel(ids, "", null, ids, "", ""), "east"));
				} else if ("7".equals(submode)) {// 7:集成展示-多条子表单记录(选项卡模式)
					ids = subdyform.getFormcode();
					Map subformmap = DyFormBuildHtmlExt.buildExtSubForm(ids,
							subdyform, lsh, isedit, userinfo, parameter, user);
					formname.add(subdyform.getFormname() + "("
							+ subformmap.get("count") + ")");
					formlist.add(getJsMap(subformmap.get("html").toString(),
							ids, DyFormComp.getExtPanel(ids, null, "$" + ids
									+ "Grid", null, "", subformmap.get("js")
									.toString()
									+ ",autoScroll:true"), "mode7"));

				} else if ("8".equals(submode)) {// 8:集成展示-单条子表单记录(选项卡模式)
					ids = "frame" + DyFormBuildHtmlExt.uuid();

					formname.add(subdyform.getFormname());
					formlist.add(getJsMap(DyFormBuildHtmlExt.buildForm(
							subdyform, issubedit, userinfo, naturalname, lsh,
							true, true, parameter, user), ids, "", "mode8"));
				} else {
					// not do
				}
			}
			// 最终输出7,8 选项卡模式
			if (formlist.size() > 0) {
				ids = "frame" + DyFormBuildHtmlExt.uuid();
				listmaps.add(getJsMap(DyFormComp.getExtTabs(ids, formname,
						formlist), ids, DyFormComp.getExtPanel(ids, null, "$"
						+ ids, null, "", ""), "center"));
			}
		}

		// 表单重新布局排版
		StringBuffer layoutcenter = new StringBuffer();
		StringBuffer layouteast = new StringBuffer();
		StringBuffer layoutwest = new StringBuffer();
		StringBuffer layoutsouth = new StringBuffer();
		StringBuffer layoutnorth = new StringBuffer();

		for (Iterator iterator = listmaps.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			String form = (String) map.get("form");
			String id = (String) map.get("id");
			String extjs = (String) map.get("extjs");
			String mode = (String) map.get("mode");

			html.append(form + extjs);
			if ("center".equals(mode)) {
				layoutcenter.append("$" + id + ",");
			} else if ("east".equals(mode)) {
				layouteast.append("$" + id + ",");
			} else if ("west".equals(mode)) {
				layoutwest.append("$" + id + ",");
			} else if ("south".equals(mode)) {
				layoutsouth.append("$" + id + ",");
			} else if ("north".equals(mode)) {
				// layoutnorth.append(id + ",");
			}

		}

		String center = DyFormComp.getExtPanelAttr("center", "center", "panel",
				StringUtils.substringBeforeLast(layoutcenter.toString(), ","),
				"", "");
		String east = DyFormComp
				.getExtPanelAttr(
						"east",
						"east",
						"panel",
						StringUtils.substringBeforeLast(layouteast.toString(),
								","),
						"",
						"title:\"操作选项\",split:true,width:200,collapsible: true,frame:true,titleCollapse:true,animCollapse:true,collapseFirst:true");
		String west = DyFormComp.getExtPanelAttr("west", "west", "panel",
				StringUtils.substringBeforeLast(layoutwest.toString(), ","),
				"", "");
		String south = DyFormComp.getExtPanelAttr("south", "south", "panel",
				StringUtils.substringBeforeLast(layoutsouth.toString(), ","),
				null, "");
		// String north = layoutnorth.toString().substring(0,
		// layoutnorth.length() - 1);

		StringBuffer btnStr = new StringBuffer();

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(dyform.getFormcode());
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".MODI")) {
			btnStr
					.append("{text:' 保 存 ',id:'ext_b_add',iconCls:'addIcon',handler:function(){ _save();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".ADD")) {
			btnStr
					.append("{text:' 继续添加 ',id:'ext_b_add_continue',iconCls:'addIcon',handler:function(){ _continueAdd();}},");
		}
		if (SecurityEntry.iv().permission(user.getUserCode(),
				naturalname_dyform + ".DELE")) {
			btnStr
					.append("{text:' 删 除 ',id:'ext_b_delete',iconCls:'deleteIcon',handler:function(){ _delete();}},");
		}
		btnStr
				.append("{text:' 打 印 ',id:'ext_b_delete',iconCls:'print',handler: function(){_print();}},");
		btnStr
				.append("{text:' 关 闭 ',id:'ext_b_cancel',iconCls:'exitIcon',handler: function(){window.close();}}");

		String north = DyFormComp.getExtPanelAttr("north", "north", "toolbar",
				btnStr.toString(), null, "");
		String viewport = DyFormComp.getExtBorderViewport(center, east, west,
				south, north);
		// System.out.println("--------");
		// System.out.println(html.toString() + viewport);
		// System.out.println("--------");
		return html.toString() + viewport;
	}

	private String loadHtml_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter, User user) throws Exception {
		StringBuffer html = new StringBuffer();
		DyForm[] subdyforms = dyform.getSubform_();
		Boolean ishidden = false;// 是否隐藏
		if (subformmode != null && subformmode.containsKey("MAINFORM")) {
			String submode = (String) subformmode.get("MAINFORM");
			if ("0".equals(submode)) {// 编辑
				isedit = true;
				ishidden = false;
			} else if ("1".equals(submode)) {// 只读
				isedit = false;
				ishidden = false;
			} else if ("2".equals(submode)) {// 隐藏
				ishidden = true;
			} else {
				ishidden = false;
			}
		}

		String hiddenid = DyFormComp.getHiddenInput("naturalname", naturalname);
		String hiddenunid = DyFormComp.getHiddenInput("unid", lsh);
		String hiddenlsh = DyFormComp.getHiddenInput("lsh", lsh);
		String ids = "frame" + DyFormBuildHtmlExt.uuid();
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
			Boolean issubedit = true;// 是否可编辑
			Boolean issubhidden = false;// 是否隐藏

			List<String> formname = new ArrayList<String>();
			List<String> formlist = new ArrayList<String>();

			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				if (subformmode == null) {
					issubedit = true;
				} else if (subformmode.containsKey(-1)
						&& subformmode.get(-1).getClass().getName().equals(
								"java.lang.String")) {
					String submode = (String) subformmode.get(-1);
					if ("0".equals(submode)) {// 编辑
						issubedit = true;
						issubhidden = false;
					} else if ("1".equals(submode)) {// 只读
						issubedit = false;
						issubhidden = false;
					} else if ("2".equals(submode)) {// 隐藏
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
						// 旧版本配置的做法
						issubedit = (Boolean) submode;
						issubhidden = false;
					} else {
						// 新版本配置的做法
						String submodex = (String) subformmode.get(i);
						if ("0".equals(submodex)) {// 编辑
							issubedit = true;
							issubhidden = false;
						} else if ("1".equals(submodex)) {// 只读
							issubedit = false;
							issubhidden = false;
						} else if ("2".equals(submodex)) {// 隐藏
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
				if ("2".equals(submode)) {// 2:链接展示-多条子表单记录(需要保存主表单)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("3".equals(submode)) {// 3:链接展示-单子表单记录(需要保存主表单、且系统控制只能一条)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildLinkForm(subdyform,
								lsh, issubedit, userinfo, submode, workcode,
								naturalname, parameter));
				} else if ("4".equals(submode)) {// 4:集成展示-单子表单记录(系统控制只能一条)
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								true, parameter, ""));
				} else if ("5".equals(submode)) {// 5:集成展示-单子表单记录(系统控制只能一条)
					// 不显示标题
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildForm(subdyform,
								issubedit, userinfo, naturalname, lsh, true,
								false, parameter, ""));
				} else if ("7".equals(submode)) {// 7:集成展示-多条子表单记录(选项卡模式)
					ids = subdyform.getFormcode();
					formname.add(subdyform.getFormname());
					Map mp = DyFormBuildHtmlExt.buildSubForm_(
							subdyform, lsh, isedit, userinfo, parameter, user);
					formlist.add((String) mp.get("html"));
				} else if ("8".equals(submode)) {// 8:集成展示-单条子表单记录(选项卡模式)
					ids = "frame" + DyFormBuildHtmlExt.uuid();

					formname.add(subdyform.getFormname());
					formlist.add(DyFormBuildHtml.buildForm(subdyform,
							issubedit, userinfo, naturalname, lsh, true, false,
							parameter, ""));
				} else if ("9".equals(submode)) {// 1+:集成展示-布局表单多条记录
					if (!issubhidden)
						html.append(DyFormBuildHtml.buildSubForms(subdyform,
								lsh, issubedit, userinfo, parameter));
				} else {// 1:集成展示-多条子表单记录（默认模式）
					if (!issubhidden){
						
						Map subformmap = DyFormBuildHtmlExt.buildSubForm_(
								subdyform, lsh, isedit, userinfo, parameter, user);
						html.append((String) subformmap.get("html"));
					}
				}
			}
			// 最终输出7,8 选项卡模式
			if (formlist.size() > 0) {
				ids = "frame" + DyFormBuildHtmlExt.uuid();
				html.append("<div style='width:908px;padding:0px;margin:0px;'>"
						+ DyFormComp.getTabs(formname, formlist) + "</div>");
			}
		}
		return html.toString();
	}

	private String loadPrint_(DyForm dyform, boolean isedit, Map subformmode,
			String userinfo, String workcode, String naturalname, String lsh,
			String parameter, User user) throws Exception {
		StringBuffer html = new StringBuffer();
		DyForm[] subdyforms = dyform.getSubform_();
		isedit = false;
		html.append(DyFormBuildHtmlExt.buildMainForm(dyform, isedit, userinfo,
				naturalname, lsh, false, false, parameter, user));
		if (subdyforms != null && subdyforms.length > 0) {
			Boolean issubedit = false;

			for (int i = 0; i < subdyforms.length; i++) {
				DyForm subdyform = subdyforms[i];
				issubedit = false;

				String submode = subdyform.getSubmode();
				if ("2".equals(submode)) {// 2:链接展示-多条子表单记录(需要保存主表单)
					html.append(DyFormBuildHtmlExt.buildLinkForm(subdyform,
							lsh, issubedit, userinfo, submode, workcode,
							naturalname, parameter, user));
				} else if ("3".equals(submode)) {// 3:链接展示-单子表单记录(需要保存主表单、且系统控制只能一条)
					html.append(DyFormBuildHtmlExt.buildLinkForm(subdyform,
							lsh, issubedit, userinfo, submode, workcode,
							naturalname, parameter, user));
				} else if ("4".equals(submode)) {// 4:集成展示-单子表单记录(系统控制只能一条)
					html.append(DyFormBuildHtmlExt.buildForm(subdyform,
							issubedit, userinfo, naturalname, lsh, true, true,
							parameter, user));
				} else if ("5".equals(submode)) {// 5:集成展示-单子表单记录(系统控制只能一条)
					// 不显示标题
					html.append(DyFormBuildHtmlExt.buildForm(subdyform,
							issubedit, userinfo, naturalname, lsh, true, false,
							parameter, user));
				} else if ("1".equals(submode)) {// 1:集成展示-多条子表单记录（默认模式）
					html.append(DyFormBuildHtmlExt.buildSubForm(subdyform, lsh,
							issubedit, userinfo, parameter, user));
				} else if ("6".equals(submode)) {// 6:集成展示-主表单右边表单
				} else if ("7".equals(submode)) {// 7:集成展示-多条子表单记录(选项卡模式)
					html.append(DyFormBuildHtmlExt.buildSubForm(subdyform, lsh,
							issubedit, userinfo, parameter, user));
				} else if ("8".equals(submode)) {// 8:集成展示-单条子表单记录(选项卡模式)
					html.append(DyFormBuildHtmlExt.buildForm(subdyform,
							issubedit, userinfo, naturalname, lsh, true, true,
							parameter, user));
				} else {
					// not do
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
		// 获得所有的下一步节点
		List xx = WfEntry.iv().listNextRouteActive(processid,
				TWfWorklistx.getActivityid(), runtimeid, user.getUserCode());
		if (isspecial == false) {
			saveYijian(request, workcode, user.getUserCode(), "新建");
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
			FrameActionExt.setWfUser(participant, wfmap);
			wfmap.put("value", "0");
			wfmap.put("activeids", Activity.getId());

			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
			wfmap.put("ismultinode", false);// 单分支
		} else {
			wfmap.put("ismultinode", true);// 多分支
			wfmap.put("commiter", user.getUserCode());
			wfmap.put("runtimeid", runtimeid);
			wfmap.put("workcode", workcode);
		}
		return wfmap;
	}

	// 提交 正常流程
	public String newEnd(HttpServletRequest request, String naturalname,
			String cachekey, String activityid, String filteractiveids,
			String workcode, String participant, String userCodes,
			String userNames, String limittimes, String processlen,
			boolean issync, String operatemode) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "提交成功");
		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String runtimeid = worklist.getRuntimeid();
		String yijian = worklist.getExtendattribute();

		// 原先用operatemode 判断，但是发现传递过来的operatemode参数总是为空
		if ("03".equals(operatemode)) {
			json.put("tip", "抄阅成功");
			return json.toString();
		} else if ("04".equals(operatemode)) {
			json.put("tip", "阶段性回复成功");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "阶段性回复失败(" + error + ")");
			}
			return json.toString();

		} else if ("end".equals(operatemode)
				|| filteractiveids
						.contains(FrameService.trackActionSpecialTypeEND)) {
			json.put("tip", "归档成功");
			// WebCache.removeCache($DYFORM + cachekey);
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "归档失败(" + error + ")");
			}
			return json.toString();
		}

		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
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
			// filteractiveids_.replaceAll(FrameService.trackActionSpecialTypeEND,
			// "");
			filteractiveids_ = StringUtils.substring(filteractiveids_, 0,
					filteractiveids_.length() - 1);
			String[] aids = filteractiveids_.split(",");

			if (specialActivityMap
					.containsKey(FrameService.trackActionSpecialType3)) {
				if (aids.length <= 0) {
					json.put("tip", "请在分支流程指派人员!");
					json.put("error", "yes");
					return json.toString();
				}
			} else {
				if (len == 0) {
					end = true;
				} else if (aids.length != activityMap.size()) {
					json.put("tip", "存在多个流程,请在所有分支流程指派人员!");
					json.put("error", "yes");
					return json.toString();
				}
			}

		}

		if (end == false) {
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
					json.put("tip", "提交失败(" + error + ")");
					return json.toString();
				}
			}

			List listWorklistnext = WfEntry.iv().useCoreView()
					.fetchRunningWorklist(runtimeid);

			// 正常流程
			for (Iterator iteratorX = listWorklistnext.iterator(); iteratorX
					.hasNext();) {
				TWfWorklist object = (TWfWorklist) iteratorX.next();
				String activityid_for_check = object.getActivityid();

				if (specialActivityMap
						.containsKey(FrameService.trackActionSpecialType3)) {
					ready_to_specify_reader(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				} else {
					ready_to_specify(naturalname, activityMap,
							specialActivityMap, limitMap, activityid_for_check,
							object, participant, issync, "01");
				}
			}
		} else {
			// WebCache.removeCache($DYFORM + cachekey+"false");
			String error = WfEntry.iv().nextToEnd(workcode, participant);
			if (StringUtils.isNotEmpty(error)) {
				json.put("error", "yes");
				json.put("tip", "提交失败(" + error + ")");
				return json.toString();
			}
		}

		return json.toString();
	}

	// 退办、特送
	public String auditEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames, String limittimes,
			boolean issync) throws Exception {
		JSONObject json = new JSONObject();
		json.put("tip", "提交成功");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = limittimes.split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		// 正常流程
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
			json.put("tip", "提交失败(" + error + ")");
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
		json.put("tip", "提交成功");
		String[] usercode_ = userCodes.split(",");
		String[] username_ = userNames.split(",");
		String[] activityid_ = activityid.split(",");
		String[] limittimes_ = "".split(",");

		Map<String, StringBuffer> activityMap = new HashMap();// 流程活动节点
		Map<String, StringBuffer> specialActivityMap = new HashMap();// 特殊活动节点
		Map<String, String> limitMap = new HashMap();//
		setActivityMap(activityMap, specialActivityMap, limitMap, activityid_,
				usercode_, username_, limittimes_);

		TWfWorklist worklist = WfEntry.iv().loadWorklist(workcode);
		String error = WfEntry.iv().nextToZhuanbang(workcode, participant);
		if (StringUtils.isNotEmpty(error)) {
			json.put("error", "yes");
			json.put("tip", "提交失败(" + error + ")");
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
	}

	public String saveYijian(HttpServletRequest request, String workcode,
			String userCode, String yijian) throws Exception {
		JSONObject json = new JSONObject();
		WfEntry.iv().saveAuditNote(workcode, userCode, yijian);
		json.put("tip", "意见填写成功");
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

	// 下一步 正常流程
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

	// 下一步自动 正常流程
	private String nextNormalByAuto(String workcode, String participant)
			throws Exception {
		return WfEntry.iv().nextByAuto(workcode, participant);
	}

	// 下一步 抄送流程
	@Deprecated
	private void nextAssistantByManual(Map specialActivityMap, String workcode,
			String participant, String yijian) throws Exception {
		for (Iterator iterator = specialActivityMap.keySet().iterator(); iterator
				.hasNext();) {
			String activityidx = (String) iterator.next();
			if (FrameService.trackActionSpecialType1.equals(activityidx)) {// 抄送
				// WfEntry.iv().nextByAuto(workcode, participant);
			}
		}
	}

	// 开始分配
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
								.equals(activityidxx)) {// 抄送
							WfEntry.iv().specifyAssistantByWorkcode(
									participant, worklist.getWorkcode(),
									personxs[i], issync, opemode);
						} else if (FrameService.trackActionSpecialType2
								.equals(activityidxx)) {// 抄阅
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

	// 开始分配 阅读
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
				if (FrameService.trackActionSpecialType1.equals(activityidxx)) {// 抄送
					WfEntry.iv().specifyAssistantByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], issync,
							opemode);
				} else if (FrameService.trackActionSpecialType2
						.equals(activityidxx)) {// 抄阅
					WfEntry.iv().specifyReaderByWorkcode(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				} else if (FrameService.trackActionSpecialType3
						.equals(activityidxx)) {// 阅读
					WfEntry.iv().distributedSubmit(participant,
							worklist.getWorkcode(), personxs[i], opemode);
				}
				WfEntry.iv().specifyLimit(worklist.getWorkcode(), personxs[i],
						Long.valueOf(limitx));
			}
		}

	}

	// 转办分配
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
		headerList.add(new TableCell("" + "文档ID"));
		Map listmap = new HashMap();
		for (int i = 0; i < dyform.getQueryColumn_().length; i++) {
			DyFormColumn _qc1 = dyform.getQueryColumn_()[i];
			headerList.add(new TableCell("" + _qc1.getColumname()));
			listmap.put(_qc1.getColumnid(), _qc1.getColumnid());
		}
		headerList.add(new TableCell("" + "工单处理过程"));

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

			// S 工单处理过程
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
			// E 工单处理过程

			t.addRow(trdata);
		}

		Report reportX = reportExt.setSimpleColHeader(t, headerList);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format("excel", "工单详情" + currentTimeMillis, reportX,
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
		headerList.add(new TableCell("处理人"));
		headerList.add(new TableCell("开始时间"));
		headerList.add(new TableCell("结束时间"));
		headerList.add(new TableCell("环节"));
		headerList.add(new TableCell("文档ID"));
		headerList.add(new TableCell("历时分钟"));
		headerList.add(new TableCell("工单名称"));

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
		groupReport.format("excel", "工单处理详情" + currentTimeMillis, reportX,
				response);

		return null;
	}

	/**
	 * 计算两个时间点之间分钟差
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int getBetweenDayNumber(String dateA, String dateB) {
		if (StringUtils.isEmpty(dateA) || StringUtils.isEmpty(dateB))
			return -1;

		long dayNumber = 0;
		// 1小时=60分钟=3600秒=3600000
		long mins = 60L * 1000L;
		// long day= 24L * 60L * 60L * 1000L;计算天数之差
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

		int count = DyEntry.iv().queryDataNum(data, " and statusinfo = '02' ");
		if (count > 0) {
			json.put("tip", "已提交状态,不能再次提交!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");
			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "提交成功!");
			} else {
				json.put("tip", "提交失败!");
				json.put("error", "yes");
			}
		}

		// START 脚本事件
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Yesaffirm");// 确认
		}
		// END 脚本事件

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

		int count = DyEntry.iv().queryDataNum(data, " and statusinfo = '01' ");
		if (count > 0) {
			json.put("tip", "已审核状态,不能进行其他操作!");
			json.put("error", "yes");
		} else {
			// data.setParticipant(user.getUserCode() + "[" +
			// user.getUserName()
			// + "]");

			boolean succ = DyEntry.iv().modifyData(formcode, data);
			if (succ) {
				json.put("tip", "反确认成功!");
			} else {
				json.put("tip", "反确认失败!");
				json.put("error", "yes");
			}
		}

		// START 脚本事件
		if (!json.containsKey("error")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, lsh, "Onaffirm");// 反确认
		}
		// END 脚本事件

		return json.toString();
	}

}
