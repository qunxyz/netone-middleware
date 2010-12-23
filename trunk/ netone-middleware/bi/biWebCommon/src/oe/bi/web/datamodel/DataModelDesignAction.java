//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.datasource.DataModelDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/** 
 * MyEclipse Struts
 * Creation date: 07-04-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DataModelDesignAction extends Action {

	// --------------------------------------------------------- Instance Variables
	
	static Log log = LogFactory.getLog(DataModelDesignAction.class);
	
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
		
		
		String datamodelid = request.getParameter("datamodelid");
		
		if(datamodelid==null || datamodelid.equals("")){
			datamodelid = "saleModel";
		}
		
		DataModelDao dataModelDao=(DataModelDao)BiEntry.fetchBi("dataModelDao");
		try {
			DataModel dataModel=dataModelDao.fetchDataModel(datamodelid);
			
			
			
		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}

