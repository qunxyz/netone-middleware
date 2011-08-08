package oe.cav.web.workflow.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.lang.StringUtils;

public class CopyFlowSvl extends HttpServlet {

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
		String processid = request.getParameter("processid");
		String newprocessid = request.getParameter("newprocessid");
		String name = request.getParameter("name");

		WorkflowDesign wfdesign = null;
		WorkflowView wfview = null;
		String tScript = "";
		try {
			wfdesign = (WorkflowDesign) RmiEntry.iv("wfdesign");
			String xpdlContent = wfdesign.xpdldescription(processid);

			wfview = (WorkflowView) RmiEntry.iv("wfview");
			String processname = wfview.loadProcess(processid).getName();
			if (name != null && !name.equals("")) {
				name = WebStr.encode(request, name);
				processname = StringUtils.substringBefore(processname, "[")
						+ "[" + name + "]";
			}

			xpdlContent = StringUtils.replace(xpdlContent, processid,
					newprocessid);

			wfdesign.saveOpe(xpdlContent, newprocessid, processname);
			// 将流程注册入资源
			String naturalname = regeditToRS(processname, newprocessid);

			Security ser = new Security(request);
			ser.log(naturalname.toUpperCase(), "add", "copy flow info");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebTip.htmlInfo("拷贝成功", true, true, response);
	}

	private String regeditToRS(String name, String processid) {
		String natrualname = StringUtils.substringAfterLast(processid, ".");
		String nameView = StringUtils.substringBetween(name, "[", "]");
		// 注册入资源
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(natrualname);

			upo.setName(nameView);
			upo.setActionurl("");
			upo.setExtendattribute(processid);
			upo.setObjecttype("WF");

			String path = StringUtils.substringBeforeLast(processid, ".");

			rsrmi.addResource(upo, path);

			return path + "." + natrualname;

		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
