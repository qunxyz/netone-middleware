package oe.cms.web.appframe;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
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

import oe.frame.web.util.WebTip;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class CreateSubSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CreateSubSvl() {
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
		List lisxt=new ArrayList();
		String id = request.getParameter("chkid");
		String pagename=request.getParameter("pagename");
		request.setAttribute("pagename", pagename);
		String processid = "";
		ResourceRmi resourceRmi = null;
		try {
			
			resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(id);
			
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
			
			Activity[] act = list.getActivity();
			//如果原先已经有参与节点配置
			List sub=resourceRmi.subResource(id);
			for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator.next();
				boolean isUseful=false;
				for(int i=0;i<act.length;i++){
					String key=StringUtils.substringAfterLast(object.getNaturalname(), ".");
					if(key.equalsIgnoreCase(act[i].getId())){
						isUseful=true;
						object.setName(act[i].getName());
						break;
					}
				}
				if(!isUseful){
					//System.out.println("del:"+object.getId());
					//resourceRmi.dropResource(object.getId());
				}else{
					System.out.println("update:"+object.getId());
					//更新名字
					resourceRmi.updateResource(object);
				}
			}
			
			for (int i = 0; i < act.length; i++) {
				if (act[i].getImplementation() != null) {// 空节点不需要进入
					UmsProtectedobject upobj = new UmsProtectedobject();
					upobj.setNaturalname(act[i].getId());
					upobj.setName(act[i].getName());
					upobj.setParentdir(id);
					upobj.setObjecttype("act");
					upobj.setAppid(upo.getAppid());
					upobj.setDescription("pt");
					resourceRmi.addResource(upobj);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		UmsProtectedobject upox=new UmsProtectedobject();
		upox.setParentdir(id);
		request.setAttribute("listxz", resourceRmi.queryObjectProtectedObj(upox, null, 0, 100, " and (description!='var' or description is null)" ));
		request.setAttribute("processid", processid);
		String url = "/PagelistRightSvl?pagename=appframelist&appname="+processid;
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
