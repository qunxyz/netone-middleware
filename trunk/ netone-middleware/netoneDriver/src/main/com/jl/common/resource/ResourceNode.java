/**
 * 
 */
package com.jl.common.resource;

/**
 * ��Դ�ڵ�
 * 
 * @version 1.0.0
 * @history
 */
public final class ResourceNode {

	/** ��ǰ�ڵ� */
	private String node_;

	/** ���ڵ� */
	private String nodeid;
	/** ���ڵ���� */
	private String nodecode;
	/** ���ڵ����� */
	private String nodename;
	/** �����ڵ�ID */
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
