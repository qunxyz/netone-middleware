//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.dim;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-15-2006
 * 
 * XDoclet definition:
 * @struts.form name="dimeditForm"
 */
public class DimeditForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods
	private  String datasetsel;
	private  String targetsel;
	private  String dimname;
	private  String sqltype;
	private  String desc;
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

	public String getDatasetsel() {
		return datasetsel;
	}

	public void setDatasetsel(String datasetsel) {
		this.datasetsel = datasetsel;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDimname() {
		return dimname;
	}

	public void setDimname(String dimname) {
		this.dimname = dimname;
	}

	public String getSqltype() {
		return sqltype;
	}

	public void setSqltype(String sqltype) {
		this.sqltype = sqltype;
	}

	public String getTargetsel() {
		return targetsel;
	}

	public void setTargetsel(String targetsel) {
		this.targetsel = targetsel;
	}

}

