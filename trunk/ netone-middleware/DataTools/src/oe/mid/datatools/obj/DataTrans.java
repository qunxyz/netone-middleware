package oe.mid.datatools.obj;

import java.util.List;
import java.util.Map;

public class DataTrans {

	Map<String, DSource> source;

	Map<String, DSQL> sql;

	List<DIn> in;

	Map<String, DOut> out;

	public Map<String, DSource> getSource() {
		return source;
	}

	public void setSource(Map<String, DSource> source) {
		this.source = source;
	}

	public Map<String, DSQL> getSql() {
		return sql;
	}

	public void setSql(Map<String, DSQL> sql) {
		this.sql = sql;
	}

	public List<DIn> getIn() {
		return in;
	}

	public void setIn(List<DIn> in) {
		this.in = in;
	}

	public Map<String, DOut> getOut() {
		return out;
	}

	public void setOut(Map<String, DOut> out) {
		this.out = out;
	}
}
