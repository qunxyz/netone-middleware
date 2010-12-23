//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.dim;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-16-2006
 * 
 * XDoclet definition:
 * @struts.form name="dimtreeForm"
 */
public class DimtreeForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/** timetree property */
	private String timetreedataset;

	/** tree property */
	private String treedataset;
	
	private String treetargetCol;

	// --------------------------------------------------------- Methods

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

	public String getTimetreedataset() {
		return timetreedataset;
	}

	public void setTimetreedataset(String timetreedataset) {
		this.timetreedataset = timetreedataset;
	}

	public String getTreedataset() {
		return treedataset;
	}

	public void setTreedataset(String treedataset) {
		this.treedataset = treedataset;
	}

	public String getTreetargetCol() {
		return treetargetCol;
	}

	public void setTreetargetCol(String treetargetCol) {
		this.treetargetCol = treetargetCol;
	}



}

