package oe.mid.datatools.obj;

import java.util.List;

public class DIn {

	String id;

	List<DColumn> columns;

	List<DColumnref> columnrefs;

	int commit;

	DSource source;

	DOut out;

	public DSource getSource() {
		return source;
	}

	public void setSource(DSource source) {
		this.source = source;
	}

	public int getCommit() {
		return commit;
	}

	public void setCommit(int commit) {
		this.commit = commit;
	}

	public List<DColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DColumn> columns) {
		this.columns = columns;
	}

	public List<DColumnref> getColumnrefs() {
		return columnrefs;
	}

	public void setColumnrefs(List<DColumnref> columnrefs) {
		this.columnrefs = columnrefs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DOut getOut() {
		return out;
	}

	public void setOut(DOut out) {
		this.out = out;
	}

}
