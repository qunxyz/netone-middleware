package oe.cav.web.workflow.resource.soa3;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.client.ActiveBind;
import oe.midware.workflow.client.ScriptObject;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.client.TaskObject;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.service.soatools.ActiveBindDao;
import oe.midware.workflow.service.soatools.ActiveBindDaoImpl;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 流程创建和修改3
 */
public class ShowAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ShowForm sf = (ShowForm) form;
		request.setAttribute("sf", sf);

		String task = request.getParameter("task");
		// 流程第一个页面
		if ("zero".equals(task)) {
			sf.setAppid(request.getParameter("appid"));
			sf.setId(request.getParameter("id"));
			sf.setPagename(request.getParameter("pagename"));

			String chkid = request.getParameter("chkid");
			// 进入修改流程
			if (StringUtils.isNotEmpty(chkid)) {
				sf.setChkid(chkid);
				try {
					ResourceRmi resourceRmi = (ResourceRmi) RmiEntry
							.iv("resource");
					UmsProtectedobject upo = resourceRmi
							.loadResourceById(chkid);
					sf.setName(upo.getName());
					sf.setNaturalname(upo.getNaturalname());
					sf.setDescription(upo.getDescription());
					String extendattribute = upo.getExtendattribute();
					ActiveBindDao xmldao = new ActiveBindDaoImpl();
					SoaObj soa = xmldao.fromXml(extendattribute);
					if (soa != null) {
						ActiveBind ab = soa.getActivity();
						TaskObject taskobj = soa.getTask();
						ScriptObject script = soa.getScript();
						if (ab != null) {
							sf.setSyn(String.valueOf(ab.isSync()));
						}
						if (taskobj != null) {
							String starttime = taskobj.getStarttime();
							String endtime = taskobj.getEndtime();
							String choicemode = taskobj.getChoicemode();
							String loopmode = taskobj.getLoopmode();
							String actionurl = starttime + "@" + choicemode
									+ "@" + loopmode + "@" + endtime;
							sf.setActionurl(actionurl);
						}
						if (script != null) {
							sf.setCdata(script.getCdata());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return mapping.findForward("zero");
			// 流程第二个页面
		} else if ("first".equals(task)) {
			return mapping.findForward("first");
			// 流程第三个页面
		} else if ("step".equals(task)) {
			return mapping.findForward("step");
			// 流程第四个页面
		} else if ("next".equals(task)) {
			return mapping.findForward("next");
			// 流程第五个页面
		} else if ("done".equals(task)) {
			return mapping.findForward("done");
			// 结束
		} else if ("final".equals(task)) {
			try {
				UmsProtectedobject upo = new UmsProtectedobject();
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				// 修改保护对象
				if (StringUtils.isNotEmpty(sf.getChkid())) {
					upo = resourceRmi.loadResourceById(sf.getChkid());
					upo.setDescription(sf.getDescription());
				} else {
					// 添加保护对象
					upo.setNaturalname(sf.getNaturalname());
					upo.setName(sf.getName());
					upo.setDescription(sf.getDescription());
					if (StringUtils.isEmpty(sf.getId())) {
						upo.setParentdir("0");
					} else {
						upo.setParentdir(sf.getId());
					}
					if (StringUtils.isNotEmpty(sf.getAppid())) {
						upo.setAppid(Long.valueOf(sf.getAppid()));
					}
					upo.setInclusion("0");
					String id = resourceRmi.addResource(upo).toString();
					upo.setId(id);
				}

				// 添加,修改ActiveBind
				String sync = sf.getSyn();
				ActiveBind ab = new ActiveBind();
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				ab.setSync(Boolean.parseBoolean(sync));

				// 添加,修改TaskObject
				String actionurl = sf.getActionurl();
				TaskObject taskobj = new TaskObject();
				String[] attributeArray = StringUtils.split(actionurl, "@");
				String starttime = attributeArray[0];
				String endtime = attributeArray[3];
				String chooseMode = attributeArray[1];
				String loopmode = attributeArray[2];
//				loopmode = StringUtils.substringBeforeLast(loopmode, "@");
				taskobj.setActivityname(ab.getName());
				taskobj.setChoicemode(chooseMode);
				taskobj.setName("");
				taskobj.setLoopmode(loopmode);
				taskobj.setStarttime(starttime);
				taskobj.setEndtime(endtime);

				// 添加,修改ScriptObject
				ScriptObject scriptobj = new ScriptObject();
				scriptobj.setActivityname(ab.getName());
				scriptobj.setName("");
				scriptobj.setCdata(sf.getCdata());
				xmldao.create(upo.getId(), ab, taskobj, scriptobj);

				String naturalname = upo.getNaturalname();
				if (StringUtils.isEmpty(sf.getChkid())) {
					upo.setNaturalname(null);
					upo.setExtendattribute(null);
					List list = resourceRmi.fetchResource(upo, null);
					if (list != null && list.size() == 1) {
						naturalname = ((UmsProtectedobject) list.get(0))
								.getNaturalname();
					} else {
						naturalname = "";
					}
				}
				if ("1".equals(sf.getShow())) {
					// 发送消息
					OnlineUserMgr oum = new DefaultOnlineUserMgr();
					String loginname = oum.getOnlineUser(request)
							.getLoginname();
					MessageHandle msgHandle = (MessageHandle) RmiEntry
							.iv("msghandle");
					// 发送消息
					String[] user = sf.getRecevier().split(",");
					for (String u : user) {
						UmsBussformworklist ubf = new UmsBussformworklist();
						ubf.setParticipant(StringUtils.trim(StringUtils
								.substringBefore(u, "[")));
						ubf.setExtattr1(sf.getTitle());
						ubf.setExtattr2(sf.getContent());
						ubf.setExtattr3(sf.getExtattr3());
						ubf.setExtattr4(naturalname);
						ubf.setSender(loginname);
						ubf.setStatusinfo("2");
						msgHandle.send(ubf);
					}
				}
				request.setAttribute("success", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("done");
		} else {
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "0");
			PrintWriter out = null;
			StringBuffer sb = new StringBuffer(
					"<select onclick='document.all.tmptxt.value=this.value'>");
			String tmpcheck = request.getParameter("tmpcheck");
			String extend = request.getParameter("extend");
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi
						.loadResourceByNatural(extend);
				extend = upo.getExtendattribute();
				if ("dy".equals(tmpcheck)) {
					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
					List list = dy.fetchColumnList(extend);
					if (list != null && list.size() > 0) {
						for (Iterator iter = list.iterator(); iter.hasNext();) {
							TCsColumn element = (TCsColumn) iter.next();
							sb = sb.append("<option value="
									+ element.getColumnid() + ">");
							sb = sb.append(element.getColumname() + "{"
									+ element.getHtmltype() + "}");
							sb = sb.append("</option>");
						}
						sb.append("</select><input type='text' name='tmptxt'>");
					}
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				} else if ("wf".equals(tmpcheck)) {
					WorkflowView wfview = null;
					wfview = (WorkflowView) RmiEntry.iv("wfview");
					WorkflowProcess wplist = wfview
							.fetchWorkflowProcess(extend);
					DataField[] df = wplist.getDataField();
					for (int i = 0; i < df.length; i++) {
						sb = sb.append("<option value=" + df[i].getId() + ">");
						sb = sb.append(df[i].getName() + "{"
								+ df[i].getDataType().getType() + "}");
						sb = sb.append("</option>");
					}
					sb.append("</select><input type='text' name='tmptxt'>");
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				} else if ("be".equals(tmpcheck)) {
					String[] extendDetail = null;
					if (extend != null) {
						extendDetail = StringUtils.split(extend, "#");
						if (extendDetail.length != 2) {
							return null;
						}
					}
					BeanService beansv = (BeanService) RmiEntry
							.ivCore(extendDetail[0]);
					SoaBean beaIn = beansv.inParamDescription(extendDetail[1]);
					SoaBean beaOut = beansv.outParamDescription(extendDetail[1]);
					for (Iterator iter = beaIn.getClassproperty().keySet()
							.iterator(); iter.hasNext();) {
						String columnid = (String) iter.next();
						ClassProperty objpro = beaIn.getClassproperty().get(
								columnid);
						sb = sb.append("<option value=" + columnid + ">");
						sb = sb.append(objpro.getName() + "{"
								+ objpro.getType() + "}");
						sb = sb.append("</option>");
					}
					sb
							.append("</select><input type='text' name='tmptxt'><select onclick='document.all.tmptxt2.value=this.value'>");
					for (Iterator iter = beaOut.getClassproperty().keySet()
							.iterator(); iter.hasNext();) {
						String columnid = (String) iter.next();
						ClassProperty objpro = beaOut.getClassproperty().get(
								columnid);
						sb = sb.append("<option value=" + columnid + ">");
						sb = sb.append(objpro.getName() + "{"
								+ objpro.getType() + "}");
						sb = sb.append("</option>");
					}
					sb.append("</select><input type='text' name='tmptxt2'>");
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
