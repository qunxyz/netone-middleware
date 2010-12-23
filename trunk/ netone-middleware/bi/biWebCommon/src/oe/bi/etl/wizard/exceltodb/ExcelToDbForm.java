//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.exceltodb;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExcelToDbForm extends ActionForm {

	// --------------------------------------------------------- Instance
	// Variables
	// First.jsp
	public String datasource;

	public String url;

	public String driver;

	public String loginname;

	public String password;

	public String filename;

	// Step.jsp
	public String sheetname;

	// Step2.jsp
	public String tablename;

	public String checkall;

	public String checkedkey;

	// Done.jsp
	public String sqlview;

	// --------------------------------------------------------- Methods

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

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getCheckall() {
		return checkall;
	}

	public void setCheckall(String checkall) {
		this.checkall = checkall;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCheckedkey() {
		return checkedkey;
	}

	public void setCheckedkey(String checkedkey) {
		this.checkedkey = checkedkey;
	}

	public String getSqlview() {
		return sqlview;
	}

	public void setSqlview(String sqlview) {
		this.sqlview = sqlview;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

}
