/**
 * 
 */
package com.jl.common.resource;

/**
 * 资源节点
 * 
 * @version 1.0.0
 * @history
 */
public final class ResourceNode {

	/** 当前节点 */
	private String node_;

	/** 根节点 */
	private String nodeid;
	/** 根节点编码 */
	private String nodecode;
	/** 根节点名称 */
	private String nodename;
	/** 父根节点ID */
	private String parentnode;

	public String getNode_() {
		return node_;
	}

	public void setNode_(String node_) {
		this.node_ = node_;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodecode() {
		return nodecode;
	}

	public void setNodecode(String nodecode) {
		this.nodecode = nodecode;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getParentnode() {
		return parentnode;
	}

	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}

}
