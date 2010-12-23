//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.summarydata;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SummaryDataForm extends ActionForm {

	// --------------------------------------------------------- Instance
	// Variables
	// First.jsp
	public String datasource;

	public String formname;

	public String formnaturalname;

	public String url;

	public String driver;

	public String loginname;

	public String password;

	// Step.jsp
	public String col;

	public String tablename;

	public String sqlvalue;

	public String sqlview;

	// Step2.jsp
	public String level;

	public String dimdata;

	public String selpoint;

	public String chinesename;

	public String cname;

	public String ctype;

	// Synbefore.jsp
	public String chkid;

	public String isall;

	public String stepmode;

	public String limitcount;

	public String limittime;

	public String limitnumber;

	// --------------------------------------------------------- Methods

	public String getLimitcount() {
		return limitcount;
	}

	public void setLimitcount(String limitcount) {
		this.limitcount = limitcount;
	}

	public String getLimittime() {
		return limittime;
	}

	public void setLimittime(String limittime) {
		this.limittime = limittime;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

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

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSqlvalue() {
		return sqlvalue;
	}

	public void setSqlvalue(String sqlvalue) {
		this.sqlvalue = sqlvalue;
	}

	public String getSqlview() {
		return sqlview;
	}

	public void setSqlview(String sqlview) {
		this.sqlview = sqlview;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getSelpoint() {
		return selpoint;
	}

	public void setSelpoint(String selpoint) {
		this.selpoint = selpoint;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getFormnaturalname() {
		return formnaturalname;
	}

	public void setFormnaturalname(String formnaturalname) {
		this.formnaturalname = formnaturalname;
	}

	public String getDimdata() {
		return dimdata;
	}

	public void setDimdata(String dimdata) {
		this.dimdata = dimdata;
	}

	public String getChkid() {
		return chkid;
	}

	public void setChkid(String chkid) {
		this.chkid = chkid;
	}

	public String getIsall() {
		return isall;
	}

	public void setIsall(String isall) {
		this.isall = isall;
	}

	public String getStepmode() {
		return stepmode;
	}

	public void setStepmode(String stepmode) {
		this.stepmode = stepmode;
	}

	public String getLimitnumber() {
		return limitnumber;
	}

	public void setLimitnumber(String limitnumber) {
		this.limitnumber = limitnumber;
	}

}
