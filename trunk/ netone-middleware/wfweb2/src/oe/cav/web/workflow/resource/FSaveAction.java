package oe.cav.web.workflow.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.midware.workflow.service.WorkflowDesign;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���̱���
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class FSaveAction extends Action {

	// ��Ѷģ���б�
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String xpdlContent = request.getParameter("xpdlContent");// �ļ�����

		String processid = request.getParameter("processid");
		String processname = request.getParameter("description");

		// ����������Ϣ��Ҫת����
		xpdlContent = WebStr.encode(request, xpdlContent);
		processname = WebStr.encode(request, processname);

		WorkflowDesign wfdesign;
		String tScript = "";
		try {
			String natrualname = StringUtils.substringBefore(processname, "[");
			String nameView = StringUtils.substringBetween(processname, "[",
					"]");

			// ������ע������Դ
			String realProcessid = regeditToRS(natrualname, nameView, processid);
			
			Security ser = new Security(request);
			ser.log(realProcessid, "add", "add flow info");

			wfdesign = (WorkflowDesign) RmiEntry.iv("wfdesign");
			xpdlContent = StringUtils.replace(xpdlContent, processid,
					realProcessid);
			wfdesign.saveOpe(xpdlContent, realProcessid, nameView);
			tScript = wfdesign.viewOpe(realProcessid);
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

		request.setAttribute("tScript", tScript);
		return mapping.getInputForward();

	}

	private String regeditToRS(String naturalname, String nameView,
			String processid) {

		// ע������Դ
		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(naturalname);

			upo.setName(nameView);
			upo.setActionurl("");

			String path = StringUtils.substringBeforeLast(processid, ".");
			if (path == null || path.equals("")) {
				path = "BUSSWF.BUSSWF";
			}
			String filename = path + "." + naturalname;
			filename = filename.toUpperCase();
			upo.setExtendattribute(filename);
			upo.setObjecttype("WF");
			rsrmi.addResource(upo, path);
			return filename;
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

}
