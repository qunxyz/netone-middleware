package oe.bi.dao.ui.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * 维度树中的节点对象
 * 
 * @author chen.jia.xun
 * 
 */
public class NodeObj implements Serializable{

	private String treeModelId;

	/**
	 * 节点ID
	 */
	private String nodeid;

	/**
	 * 节点名
	 */
	private String nodename;

	/**
	 * 父节点ID
	 */
	private String fathernodeid;

	/**
	 * 层级水平名字
	 */
	private String levelname;

	/**
	 * 字段名
	 */
	private String columnname;

	/**
	 * 节点扩展属性
	 */
	private Map extendAttribute;

	public Map getExtendAttribute() {
		return extendAttribute;
	}

	public void setExtendAttribute(Map extendAttribute) {
		this.extendAttribute = extendAttribute;
	}

	public String getFathernodeid() {
		return fathernodeid;
	}

	public void setFathernodeid(String fathernodeid) {
		this.fathernodeid = fathernodeid;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getTreeModelId() {
		return treeModelId;
	}

	public void setTreeModelId(String treeModelId) {
		this.treeModelId = treeModelId;
	}

}
