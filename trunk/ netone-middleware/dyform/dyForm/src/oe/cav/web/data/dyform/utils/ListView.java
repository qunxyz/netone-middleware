package oe.cav.web.data.dyform.utils;

import java.util.List;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.DynaUIForm;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public class ListView {

	private ActionEvent ae;

	private String lsh;

	private String formcode;

	private String[] but1;

	private String[] but2;

	private String condition;

	private Object searchobj;

	private DynaUIForm dyform;

	private String contextpath;

	private List subForm;

	public String getContextpath() {
		return contextpath;
	}

	public void setContextpath(String contextpath) {
		this.contextpath = contextpath;
	}

	public DynaUIForm getDyform() {
		return dyform;
	}

	public void setDyform(DynaUIForm dyform) {
		this.dyform = dyform;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String[] getBut1() {
		return but1;
	}

	public void setBut1(String[] but1) {
		this.but1 = but1;
	}

	public String[] getBut2() {
		return but2;
	}

	public void setBut2(String[] but2) {
		this.but2 = but2;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public Object getSearchobj() {
		return searchobj;
	}

	public void setSearchobj(Object searchobj) {
		this.searchobj = searchobj;
	}

	public List getSubForm() {
		return subForm;
	}

	public void setSubForm(List subForm) {
		this.subForm = subForm;
	}

	public ActionEvent getAe() {
		return ae;
	}

	public void setAe(ActionEvent ae) {
		this.ae = ae;
	}

}
