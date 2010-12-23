//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.datamodel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-19-2006
 * 
 * XDoclet definition:
 * @struts.form name="designdatamodelForm"
 */
public class DesigndatamodelForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods
   private String  modelname;
   private String  tablename;
   private String  columnfield;
   private String  datasets;
   private String  linkers;
   private String  targetColumns;
   private String  dimColumns;
   private String  optimizes;
   private String  datamodelXmlStr;
   private String  modelId;
	public String getColumnfield() {
	return columnfield;
}

public void setColumnfield(String columnfield) {
	this.columnfield = columnfield;
}

public String getDatasets() {
	return datasets;
}

public void setDatasets(String datasets) {
	this.datasets = datasets;
}

public String getDimColumns() {
	return dimColumns;
}

public void setDimColumns(String dimColumns) {
	this.dimColumns = dimColumns;
}

public String getLinkers() {
	return linkers;
}

public void setLinkers(String linkers) {
	this.linkers = linkers;
}

public String getModelname() {
	return modelname;
}

public void setModelname(String modelname) {
	this.modelname = modelname;
}

public String getOptimizes() {
	return optimizes;
}

public void setOptimizes(String optimizes) {
	this.optimizes = optimizes;
}

public String getTablename() {
	return tablename;
}

public void setTablename(String tablename) {
	this.tablename = tablename;
}

public String getTargetColumns() {
	return targetColumns;
}

public void setTargetColumns(String targetColumns) {
	this.targetColumns = targetColumns;
}

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

	public String getDatamodelXmlStr() {
		return datamodelXmlStr;
	}

	public void setDatamodelXmlStr(String datamodelXmlStr) {
		this.datamodelXmlStr = datamodelXmlStr;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

}

