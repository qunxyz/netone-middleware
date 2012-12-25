package com.jl.common.message;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import com.jl.common.app.AppEntry;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfRelevant;
import com.jl.common.workflow.WfEntry;

public final class CuibangSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CuibangSvl() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ope = request.getParameter("ope");
		String appname = request.getParameter("appname");
		String workcode = request.getParameter("workcode");

		try {

			if ("new".equals(ope)) {
				String username = SecurityEntry.iv().onlineUser(request)
						.getName();
				List list = AppEntry.iv().wf2dyformBindCfg(appname);
				String first_rev = "";
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TWfRelevant object = (TWfRelevant) iterator.next();
					first_rev = object.getRevid();
					break;
				}
				WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
				TWfWorklist wf = wfview.loadWorklist(workcode);
				TWfRelevantvar revtemp = wfview.fetchRelevantVar(wf
						.getRuntimeid(), first_rev);
				String appnametip=AppEntry.iv().loadApp(appname).getName();
				String noticetitle = revtemp != null ? revtemp.getValuenow()
						: "";

				String context = Message.msg_app+"催办提醒:"+Message.msg_head+appnametip+"待办任务.文件标题:"+ noticetitle;
				request.setAttribute("context", context);
				request.setAttribute("workcode", workcode);
				request.setAttribute("username", ", 发送人:" + username + Message.msg_end);
				request.setAttribute("usernamex",username);
				// 获得所有 可被催办的对象信息
				StringBuffer butx = new StringBuffer();
				String runtimeid = WfEntry.iv().loadWorklist(workcode)
						.getRuntimeid();
				List listx = WfEntry.iv().listAllRunningWorklistByRuntimeid(
						runtimeid);
				for (Iterator iteratorx = listx.iterator(); iteratorx.hasNext();) {
					TWfWorklist object = (TWfWorklist) iteratorx.next();
					String workcodthis = object.getWorkcode();

					String sql = "select usercode usercode,username username from t_wf_participant where workcode='"
							+ workcodthis
							+ "' and statusnow='01' and types!='03'";
					List listz = WfEntry.iv().useCoreView().coreSqlview(sql);
					for (Iterator iterator = listz.iterator(); iterator
							.hasNext();) {
						Map objectx = (Map) iterator.next();
						String usernamez = (String) objectx.get("username");
						butx.append(usernamez + ",");
					}
				}
				request.setAttribute("cuibangto", butx.toString());

				request.getRequestDispatcher("/workflow/cuiban.jsp").forward(
						request, response);

			} else {
				String context = request.getParameter("context");
				String usernamex=request.getParameter("username");

				context=context+usernamex;
				String runtimeid = WfEntry.iv().loadWorklist(workcode)
						.getRuntimeid();
				List listx = WfEntry.iv().listAllRunningWorklistByRuntimeid(
						runtimeid);
				for (Iterator iteratorx = listx.iterator(); iteratorx.hasNext();) {
					TWfWorklist object = (TWfWorklist) iteratorx.next();
					String workcodthis = object.getWorkcode();

					String sql = "select usercode usercode,username username from t_wf_participant where workcode='"
							+ workcodthis
							+ "' and statusnow='01' and types!='03'";
					List list = WfEntry.iv().useCoreView().coreSqlview(sql);
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						Map objectx = (Map) iterator.next();
						String usercode = (String) objectx.get("usercode");
						String username = (String) objectx.get("username");
						Message.toMessageByUser(usercode, WebStr.encode(
								request, context));

						Client3A userX = SecurityEntry.iv().onlineUser(request);
						String commiter = userX.getName() + "["
								+ userX.getClientId() + "]";
						WfEntry.iv().specifyCuibangByWorkcode(commiter,
								workcodthis, username + "[" + usercode + "]");
					}
				}

				WebTip.htmlInfo("催办成功", true, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
