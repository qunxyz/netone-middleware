package com.jl.common.app;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * Ӧ��װ��ʵ��
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public final class AppObj extends UmsProtectedobject {

	private String dyformCode_;
	private String dyformName_;
	private String workflowCode_;
	private String workflowName_;
    //Ĭ�ϵĴ����ֶ���ʾ����
	private String worklistColumn;
	//Ĭ�ϵĴ����ֶεĳ���
	private String worklistsize;
	//��ͷ��Ϣ
	private String formtitle;
	//��β��Ϣ
	private String formendtitle;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWorklistColumn() {
		return worklistColumn;
	}

	public void setWorklistColumn(String worklistColumn) {
		this.worklistColumn = worklistColumn;
	}

	public String getDyformCode_() {
		return dyformCode_;
	}

	public void setDyformCode_(String dyformCode_) {
		this.dyformCode_ = dyformCode_;
	}

	public String getDyformName_() {
		return dyformName_;
	}

	public void setDyformName_(String dyformName_) {
		this.dyformName_ = dyformName_;
	}

	public String getWorkflowCode_() {
		return workflowCode_;
	}

	public void setWorkflowCode_(String workflowCode_) {
		this.workflowCode_ = workflowCode_;
	}

	public String getWorkflowName_() {
		return workflowName_;
	}

	public void setWorkflowName_(String workflowName_) {
		this.workflowName_ = workflowName_;
	}

	public String getWorklistsize() {
		return worklistsize;
	}

	public void setWorklistsize(String worklistsize) {
		this.worklistsize = worklistsize;
	}

	public String getFormtitle() {
		return formtitle;
	}

	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}

	public String getFormendtitle() {
		return formendtitle;
	}

	public void setFormendtitle(String formendtitle) {
		this.formendtitle = formendtitle;
	}

}
