package oe.cav.web.workflow.resource.soax;

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
 * SOA过程定制
 * 
 * 注意：为了适应页面上的流程应用，在SOA过程中编辑的OeScript脚本中的双引号要改成单引号
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class ShowAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ShowForm sf = (ShowForm) form;
		request.setAttribute("sf", sf);

		String task = request.getParameter("task");

		// 流程第一个页面
		if ("first".equals(task)) {
			String chkid = request.getParameter("chkid");
			if (StringUtils.isNotEmpty(chkid)) {
				sf.setChkid(chkid);
			} else {
				chkid = sf.getChkid();
			}
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(chkid);
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
						String actionurl = starttime + "@" + choicemode + "@"
								+ loopmode + "@" + endtime;
						sf.setActionurl(actionurl);
					}
					if (script != null) {
						String cdata = script.getCdata();
						sf.setCdata(cdata);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("first");
		
		} else if ("final".equals(task)) {
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(sf
						.getChkid());

				ActiveBind ab = new ActiveBind();
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				ab.setSync(Boolean.parseBoolean(sf.getSyn()));

				TaskObject taskobj = null;

				// 添加,修改ScriptObject
				ScriptObject scriptobj = new ScriptObject();
				scriptobj.setActivityname("");
				scriptobj.setName("");
				String cdata = sf.getCdata();
				// cdata=WebStr.gbkToiso8859( cdata);
				scriptobj.setCdata(cdata);
				xmldao.create(upo.getId(), ab, taskobj, scriptobj);

				request.setAttribute("success", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("done");
		} else if ("inout".equals(task)) {
			//用于自动输入工作流参数
			INOUTTools.todo(response, request);
		} else {
			//自动加入 中间件内部对象
			NetoneObjTools.todo(response, request);
		}
		return null;
	}
}
