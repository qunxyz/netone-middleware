package oe.cav.web.util;

public class SearchObj {
	private String sql;

	private Object searchobj;

	/**
	 * @return ���� sql��
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            Ҫ���õ� sql��
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return ���� workflowBean��
	 */
	public Object getSearchobj() {
		return searchobj;
	}

	/**
	 * @param workflowBean
	 *            Ҫ���õ� workflowBean��
	 */
	public void setSearchobj(Object searchobj) {
		this.searchobj = searchobj;
	}

}
