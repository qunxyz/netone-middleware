package com.jl.web.controller.shopping;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.TimeUtil;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.TWfWorklistExt;
import com.jl.common.workflow.WfEntry;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.ShoppingService;
import com.jl.web.controller.AbstractAction;

public class ShoppingAction extends AbstractAction {

	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.getClientPermissions(request, response);
		return mapping.findForward("onMainView");
	}

	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ivService.queryShoppingForPage(request, response);
	}

	public void onShoppingDetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ivService.queryShoppingDetail(request, response);
	}

	// 新增或修改页面
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		User user = BaseService.getOnlineUser(request);// 获取当前用户
		request.setAttribute("onlineName", user.getUserName());

		String workcode = request.getParameter("workcode");
		if (StringUtils.isNotEmpty(workcode)) {
			String runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			request.setAttribute("runtimeid", runtimeid);
		}

		ivService.loadShopping(request, response);
		return mapping.findForward("onEditView");
	}

	// 保存
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShoppingService ins = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ins.saveShoppingAndDetail(request, response);
		return null;
	}

	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ivService.delete(request, response);
	}

	public void onAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ivService.audit(request, response);
	}

	public ActionForward onShoppingDetailMainView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ShoppingService ivService = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		User user = BaseService.getOnlineUser(request);// 获取当前用户
		request.setAttribute("onlineName", user.getUserName());

		ivService.loadShopping(request, response);
		return mapping.findForward("onShoppingDetailMainView");
	}

	public void bussTipListView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String workcode = request.getParameter("workcode");
		String runtimeid = request.getParameter("runtimeid");
		String string = "";

		try {
			if (StringUtils.isEmpty(workcode)) {
				if (StringUtils.isEmpty(runtimeid)) {
					return;
				}
			} else {
				runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			}
			List list = WfEntry.iv().listAllParticipantinfo(runtimeid,true);

			if (list.size() != 0) {
				// TWfWorklistExt o = (TWfWorklistExt) list.get(0);
				// if(StringUtils.isNotEmpty(o.getCustomer()))
				// {

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TWfParticipant object = (TWfParticipant) iterator.next();
					if (StringUtils.isNotEmpty(object.getAuditnode())) {
						string += "<div style=\"padding:10px\">"
								+ object.getAuditnode()
								+ "</div><div style=\"text-align:center;border-bottom:1px dashed #ddd\">"
								+ object.getUsername()
								+ "  "
								+ StringUtils.substringBefore(object
										.getDonetime(), ".") + "</div>";
					}
				}
				// }
			}

			if (StringUtils.isEmpty(string)) {
				string = "<div style=\"padding:10px\">无</div>";
			}
			// System.out.println("<div style=\"padding:20px\">无</div><div
			// id=\"whodate\" style=\"text-align:center\"></div>");
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(string);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryActive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		try {
//			String workcode = request.getParameter("workcode");
//			if (StringUtils.isEmpty(workcode)) {
//				// System.out.println("workcode为空!");
//				return;
//			}
//			String runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
//			List list = WfEntry.iv().listAllParticipantinfo(runtimeid);
//
//			String title = "<tr><td nowrap='nowrap' class='label_nd_1' width='20%'>日期</td><td nowrap='nowrap' class='label_nd_1' width='20%'>提交者</td><td nowrap='nowrap' class='label_nd_1' width='40%'>流程节点</td><td nowrap='nowrap' class='label_nd_1' width='20%'>执行者</td><td nowrap='nowrap' class='label_nd_1' width='20%'>完成时间</td></tr>";
//
//			String showHtmlView = "<table  width=\"100%\" border=1>" + title;
//			for (int i = 0; i < list.size(); i++) {
//				TWfParticipant object = (TWfParticipant) list.get(i);
//				String opemode = "";
//				if ("02".equals(object.getTypes())) {
//					opemode = "[抄送]";
//				}
//				if ("03".equals(object.getTypes())) {
//					opemode = "[抄阅]";
//				}
//				String sync = object.isSync() ? "[同步]" : "";
//				TWfWorklist wlx = WfEntry.iv().loadWorklist(
//						object.getWorkcode());
//
//				String createtime = object.getCreatetime().substring(0, 16);
//				String donetime = object.getDonetime();
//				donetime = donetime == null || "".equals(donetime) ? "&nbsp;"
//						: donetime.substring(0, 16);
//				showHtmlView += "<tr>"
//						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
//						+ createtime
//						+ "</td>"
//						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
//						+ object.getCommitername()
//						+ "</td>"
//						+ "<td nowrap=\"nowrap\" width=\"40%\"  class=\"label_nd_2\">"
//						+ WfEntry.iv().loadActive(wlx.getProcessid(),
//								wlx.getActivityid()).getName()
//						+ "</td>"
//						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
//						+ object.getUsername()
//						+ opemode
//						+ sync
//						+ "</td>"
//						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
//						+ donetime + "</td>";
//
//				showHtmlView += "</tr>";
//			}
//			showHtmlView += "</table>";
//			// System.out.println(showHtmlView);
//			response.setContentType("text/json;charset=UTF-8");
//			PrintWriter pw = response.getWriter();
//			pw.write(showHtmlView);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void onGetWFInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ShoppingService configServer = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		configServer.loadWFInfoToSelect(request, response);
	}

	public ActionForward onRollBackView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onRollBackView");
	}

	public ActionForward onRollBackEvent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ShoppingService ins = (ShoppingService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("shoppingService");
		ins.rollBackEvent(request, response);
		return null;

	}

}
