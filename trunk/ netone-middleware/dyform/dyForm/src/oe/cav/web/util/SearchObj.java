package oe.cav.web.util;

public class SearchObj {
	private String sql;

	private Object searchobj;

	/**
	 * @return 返回 sql。
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            要设置的 sql。
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return 返回 workflowBean。
	 */
	public Object getSearchobj() {
		return searchobj;
	}

	/**
	 * @param workflowBean
	 *            要设置的 workflowBean。
	 */
	public void setSearchobj(Object searchobj) {
		this.searchobj = searchobj;
	}

}
