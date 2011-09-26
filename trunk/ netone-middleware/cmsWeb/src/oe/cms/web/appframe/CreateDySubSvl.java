package oe.cms.web.appframe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class CreateDySubSvl extends HttpServlet {

	final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
			"busstype", "busstip" };

	/**
	 * Constructor of the object.
	 */
	public CreateDySubSvl() {
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
		List lisxt = new ArrayList();
		String id = request.getParameter("chkid");
		String pagename = request.getParameter("pagename");
		request.setAttribute("pagename", pagename);
		String processid = "";
		ResourceRmi resourceRmi = null;
		String formcode = "";
		try {

			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(id);
			formcode = upo.getActionurl();
			String[] formx = StringUtils.split(formcode, ";");
			if (formx.length == 2) {
				formcode = StringUtils.substringBetween(formx[1], "[", "]");
			}
			String extendattribute = upo.getActionurl();

			if (extendattribute != null && !extendattribute.equals("")) {
				String obj[] = extendattribute.split(";");
				if (obj.length == 2) {
					processid = StringUtils.substringBetween(obj[0], "[", "]");
				}
			}

			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WorkflowProcess list = wfview.fetchWorkflowProcess(processid);

			DataField[] data = list.getDataField();
			for (int i = 0; i < data.length; i++) {
				String fileid = data[i].getId();
				boolean isKeyvar = false;
				for (int j = 0; j < _CORE_KEY_VAR.length; j++) {
					// 需要忽略掉流程内部关键变量
					if (_CORE_KEY_VAR[j].equalsIgnoreCase(fileid)) {
						isKeyvar = true;
						break;
					}
				}
				if (isKeyvar)
					continue;
				if (fileid.startsWith("r_")) {
					// 说明是内部路由变量忽略
					continue;
				}
				UmsProtectedobject upobj = new UmsProtectedobject();
				upobj.setNaturalname(fileid);
				upobj.setName(data[i].getName());
				upobj.setParentdir(id);
				upobj.setDescription("var");
				upobj.setAppid(upo.getAppid());
				upobj.setExtendattribute(formcode);
				resourceRmi.addResource(upobj);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		UmsProtectedobject upox = new UmsProtectedobject();
		upox.setParentdir(id);
		upox.setDescription("var");
		request.setAttribute("listxz", resourceRmi.queryObjectProtectedObj(
				upox, null, 0, 100, ""));

		String url = "/PagelistRightSvl?pagename=appframelistdy&appname="
				+ processid;
		request.getRequestDispatcher(url).forward(request, response);

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

		this.doGet(request, response);
	}

}
