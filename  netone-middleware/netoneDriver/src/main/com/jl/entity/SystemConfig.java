package com.jl.entity;
/**
 * 
 * ϵͳ���Ƶ� ���Ʒ���,��ʽ
 * 
 * @author chenlx
 * 
 * @version  2010��12��2��
 */
public class SystemConfig extends AbstractEntity {
	private String sid;
	private String name;
	private String type;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
