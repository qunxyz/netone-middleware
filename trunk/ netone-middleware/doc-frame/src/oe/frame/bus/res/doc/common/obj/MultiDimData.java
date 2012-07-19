package oe.frame.bus.res.doc.common.obj;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiDimData {

	private Set digcolumn;

	/**
	 * columnId 对应 columnName
	 */
	private Map dataColumnName;

	/**
	 * columnId 对应 columnType
	 */
	private Map dataColumnType;

	/**
	 * Value List ,Element(record) is Map (columnid , columnValue)
	 */
	private List datavalue;

	private String start_time_column;

	private String level_column;

	public Map getDataColumnName() {
		return dataColumnName;
	}

	public void setDataColumnName(Map dataColumnName) {
		this.dataColumnName = dataColumnName;
	}

	public Map getDataColumnType() {
		return dataColumnType;
	}

	public void setDataColumnType(Map dataColumnType) {
		this.dataColumnType = dataColumnType;
	}

	public List getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(List datavalue) {
		this.datavalue = datavalue;
	}

	public String getLevel_column() {
		return level_column;
	}

	public void setLevel_column(String level_column) {
		this.level_column = level_column;
	}

	public String getStart_time_column() {
		return start_time_column;
	}

	public void setStart_time_column(String start_time_column) {
		this.start_time_column = start_time_column;
	}

	public Set getDigcolumn() {
		return digcolumn;
	}

	public void setDigcolumn(Set digcolumn) {
		this.digcolumn = digcolumn;
	}

}
