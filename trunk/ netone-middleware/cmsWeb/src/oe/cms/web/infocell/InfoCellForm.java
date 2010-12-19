//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocell;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InfoCellForm extends ActionForm {
	/** identifier field */
	private String cellid;

	/** nullable persistent field */
	private String cellname;

	/** nullable persistent field */
	private String body;

	/** nullable persistent field */
	private String extendattribute;

	private String belongto; // 归属组

	private String intime; // 初始化时间

	/***************************************************************************
	 * 
	 * 工具栏属性
	 **************************************************************************/

	private String cellgroup; // 选择资讯元 下拉框的归属组

	private String cellTool; // 资讯元列表

	private String fiId;

	private String fiColumnId;

	private String sysid;

	private String formid;

	private String wfid;

	private String select;

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getCellTool() {
		return cellTool;
	}

	public void setCellTool(String cellTool) {
		this.cellTool = cellTool;
	}

	public String getFiColumnId() {
		return fiColumnId;
	}

	public void setFiColumnId(String fiColumnId) {
		this.fiColumnId = fiColumnId;
	}

	public String getFiId() {
		return fiId;
	}

	public void setFiId(String fiId) {
		this.fiId = fiId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getCellname() {
		return cellname;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
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

	public String getCellgroup() {
		return cellgroup;
	}

	public void setCellgroup(String cellgroup) {
		this.cellgroup = cellgroup;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getWfid() {
		return wfid;
	}

	public void setWfid(String wfid) {
		this.wfid = wfid;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

}
