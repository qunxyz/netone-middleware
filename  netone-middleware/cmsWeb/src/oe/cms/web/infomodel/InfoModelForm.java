//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 08-03-2006
 * 
 * XDoclet definition:
 * 
 * @struts.form name="infomodelFormForm"
 */
public class InfoModelForm extends ActionForm {

	/** identifier field */
	private Long modelid;

	/** nullable persistent field */
	private String modelname;

	private String naturalname;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private String extendattribute;

	/** nullable persistent field */
	private String userid;

	/** persistent field */
	private String accessmode;

	/** nullable persistent field */
	private String infoxml;

	private Long hit;

	private String levels;

	private String wintime;

	public String getAccessmode() {
		return accessmode;
	}

	public void setAccessmode(String accessmode) {
		this.accessmode = accessmode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getInfoxml() {
		return infoxml;
	}

	public void setInfoxml(String infoxml) {
		this.infoxml = infoxml;
	}

	public Long getModelid() {
		return modelid;
	}

	public void setModelid(Long modelid) {
		this.modelid = modelid;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getWintime() {
		return wintime;
	}

	public void setWintime(String wintime) {
		this.wintime = wintime;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

}
