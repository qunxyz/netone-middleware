//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.xml.XmlHandlerImpl;
import oe.bi.datasource.DataModelDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




/**
 * MyEclipse Struts Creation date: 07-11-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class DataModelListAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods

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
		try {
			DataModelDao dm = (DataModelDao) BiEntry.fetchBi("dataModelDao");
			XmlHandlerImpl  xmlhandler=(XmlHandlerImpl)BiEntry.fetchBi("xmlHandler");
			String flag = request.getParameter("flag");
			if ("delete".equals(flag)) {				
				String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
				.getDatamodelpath();
				String id = request.getParameter("id");
				xmlhandler.dropXml(rootpath+id+".xml");
			}

			String[][] modelList = dm.listAll("");
			request.setAttribute("modelList", modelList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
