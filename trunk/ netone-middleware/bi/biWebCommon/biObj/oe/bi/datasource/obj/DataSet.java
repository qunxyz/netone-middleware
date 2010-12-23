package oe.bi.datasource.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ݼ�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class DataSet implements Serializable {
	/** ���ݼ����� */
	private String datasetid;

	/** ���ݼ����� */
	private String datasetname;// tablename chinese

	/** ���ݼ����� */
	private String description;

	/** ���ݼ���Ԫ������ */
	private List dataColumns = new ArrayList();// Ԫ��ΪDataColumnObj

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
