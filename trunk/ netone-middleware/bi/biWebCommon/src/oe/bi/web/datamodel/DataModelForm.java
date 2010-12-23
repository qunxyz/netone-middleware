//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-11-2006
 * 
 * XDoclet definition:
 * @struts.form name="dataModelForm"
 */
public class DataModelForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods
	/** dataModelText property */
	private String dataModelText;

	private String dataModelId;


	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public String getDataModelText() {
		return dataModelText;
	}

	public void setDataModelText(String dataModelText) {
		this.dataModelText = dataModelText;
	}

	public String getDataModelId() {
		return dataModelId;
	}

	public void setDataModelId(String dataModelId) {
		this.dataModelId = dataModelId;
	}

}

