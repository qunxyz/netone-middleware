package com.jl.entity;

import java.io.Serializable;

public class leaderViewPojo implements Serializable{
	private String lsh;
	private String naturalname;
	private String naturalname2;
	private String workcode;
	private String formtitle;
	private String actname;
	private String starttime;
	private String usercode;
	private String username;
	private String commiter;
	private int flag;
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getNaturalname() {
		return naturalname;
	}
	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getNaturalname2() {
		return naturalname2;
	}
	public void setNaturalname2(String naturalname2) {
		this.naturalname2 = naturalname2;
	}
	public String getWorkcode() {
		return workcode;
	}
	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public String getActname() {
		return actname;
	}
	public void setActname(String actname) {
		this.actname = actname;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getCommiter() {
		return commiter;
	}
	public void setCommiter(String commiter) {
		this.commiter = commiter;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
