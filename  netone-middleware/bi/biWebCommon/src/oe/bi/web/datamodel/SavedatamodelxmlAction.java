//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.FileHandler;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.datasource.DataModelDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 07-13-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class SavedatamodelxmlAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		FileHandler filehandler = (FileHandler) BiEntry.fetchBi("fileHandler");
		DataModelDao dmDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
		.getDatamodelpath();

		String xmlStr = request.getParameter("xmlstr");
		
		String modelId = String.valueOf(System.currentTimeMillis());

		
		// 先保存文件,再通过此文件获取DataModel对象,更改此对象,再保存此对象
		filehandler.writeFile(rootpath + modelId + ".xml", xmlStr);
		DataModel datamodel = null;
		try {
			datamodel=dmDao.fetchDataModel(modelId);
			datamodel.setModelid(modelId);
			dmDao.create(datamodel);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("saveFlag","false");
			e.printStackTrace();
		}

	return mapping.getInputForward();
}

}

