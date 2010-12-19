//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.frame.orm.OrmerEntry;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.cfg.TCmsInfomodel;

/**
 * 个人空间的模板选择
 * 
 * @author chen.jia.xun(Robanco) 2006-08-18
 * 
 */

public class TemplateSelectAction extends Action {

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		EnvService ser = null;
		try {
			ser = (EnvService) RmiEntry.iv("envinfo");
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
		String modelid = "1214902934969";

		if (modelid != null) {
			String[] modelArr = modelid.split(",");
			String[][] listmodel = new String[modelArr.length][3];
			for (int i = 0; i < modelArr.length; i++) {
				if (modelArr[i] != null && !modelArr[i].equals("")) {
					TCmsInfomodel tifm = (TCmsInfomodel) OrmerEntry
							.fetchOrmer().fetchQuerister().loadObject(
									TCmsInfomodel.class, new Long(modelArr[i]));
					listmodel[i][0] = tifm.getModelid().toString();
					listmodel[i][1] = tifm.getModelname();
					listmodel[i][2] = tifm.getDescription();
				}
			}
			request.setAttribute("templatelist", listmodel);
			request.setAttribute("defaultvalue", listmodel[0][2]);

		}
		String onlyview = request.getParameter("onlyview");
		if (onlyview != null) {
			request.setAttribute("butview", "hide");
		}
		return mapping.getInputForward();

	}
}
