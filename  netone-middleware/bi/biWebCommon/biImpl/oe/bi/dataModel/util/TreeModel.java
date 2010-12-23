package oe.bi.dataModel.util;


/**
 * 
 * @author wang-ting-jie
 *
 */
public class TreeModel {
	private String index;

	private String tableName;

	private String columnid;

	private String columnname;

	private String subid;

	private String condition;

	/**
	 * TreeModel对象,根据此维度对象,构造出SQL语句
	 * 
	 */
	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSubid() {
		return subid;
	}

	public void setSubid(String subid) {
		this.subid = subid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
