package oe.bi.datasource.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据集对象
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class DataSet implements Serializable {
	/** 数据集对象 */
	private String datasetid;

	/** 数据集名称 */
	private String datasetname;// tablename chinese

	/** 数据集描述 */
	private String description;

	/** 数据集的元素数组 */
	private List dataColumns = new ArrayList();// 元素为DataColumnObj

	public void addDataColumns(DataColumn dataColumnObj) {
		dataColumns.add(dataColumnObj);
	}

	public List getDataColumns() {
		return dataColumns;
	}

	public void setDataColumns(List dataColumns) {
		this.dataColumns = dataColumns;
	}

	public String getDatasetname() {
		return datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatasetid() {
		return datasetid;
	}

	public void setDatasetid(String datasetid) {
		this.datasetid = datasetid;
	}

}
