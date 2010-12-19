package oe.cav.web.workflow.resource.soa;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 建立子节点以提供新建和修改
 */
public class CreateSubinfoAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("chkid");
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(id);
			String extendattribute = upo.getExtendattribute();
		
			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WorkflowProcess list = wfview.fetchWorkflowProcess(extendattribute);
			Activity[] act = list.getActivity();
			for (int i = 0; i < act.length; i++) {
				UmsProtectedobject upobj = new UmsProtectedobject();
				upobj.setNaturalname(act[i].getId());
				upobj.setName(act[i].getName());
				upobj.setParentdir(id);
				upobj.setObjecttype(upo.getObjecttype());
				upobj.setAppid(upo.getAppid());
				resourceRmi.addResource(upobj);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		WebTip.htmlInfo("装载成功!接着可进行[服务配置]", true, response);
		return null;
	}
}
