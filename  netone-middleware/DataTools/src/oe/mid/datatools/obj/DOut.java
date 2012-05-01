package oe.mid.datatools.obj;

public class DOut {

	String id;

	DSQL sql;

	DSource source;

	public DSQL getSql() {
		return sql;
	}

	public void setSql(DSQL sql) {
		this.sql = sql;
	}

	public DSource getSource() {
		return source;
	}

	public void setSource(DSource source) {
		this.source = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
