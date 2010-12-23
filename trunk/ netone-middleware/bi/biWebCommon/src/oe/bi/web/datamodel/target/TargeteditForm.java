//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel.target;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-14-2006
 * 
 * XDoclet definition:
 * @struts.form name="targeteditForm"
 */
public class TargeteditForm extends ActionForm {
	
	private  String datasetsel;
	private  String targetsel;
	private  String target;
	private  String tgname;
	private  String alarm;
	private  String desc;
	// --------------------------------------------------------- Instance Variables

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

	public String getDatasetsel() {
		return datasetsel;
	}

	public void setDatasetsel(String datasetsel) {
		this.datasetsel = datasetsel;
	}

	public String getTargetsel() {
		return targetsel;
	}

	public void setTargetsel(String targetsel) {
		this.targetsel = targetsel;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTgname() {
		return tgname;
	}

	public void setTgname(String tgname) {
		this.tgname = tgname;
	}

}

