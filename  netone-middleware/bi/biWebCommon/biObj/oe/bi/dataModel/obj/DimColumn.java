package oe.bi.dataModel.obj;

import java.io.Serializable;

/**
 * @author chen.jia.xun (Robanco)
 *
 */
public class DimColumn implements Serializable{
	private String id;
	
	private String sqltype;

	private String name;

	private String extendattribute;

	private String description;

	private String treeModel;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(String treeModel) {
		this.treeModel = treeModel;
	}

	public String getSqltype() {
		return sqltype;
	}

	public void setSqltype(String sqltype) {
		this.sqltype = sqltype;
	}


	

}
