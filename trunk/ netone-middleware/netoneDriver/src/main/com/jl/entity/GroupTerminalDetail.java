/**
 * 
 */
package com.jl.entity;

import java.util.Date;


public class GroupTerminalDetail extends AbstractEntity {
	private String groupTerminalDetailId;
	private String groupTerminalId;
	private String lastlastlastMonth;
	private String monthBeforeLast;
	private String lastMonth;
	
	public String getGroupTerminalDetailId() {
		return groupTerminalDetailId;
	}
	public void setGroupTerminalDetailId(String groupTerminalDetailId) {
		this.groupTerminalDetailId = groupTerminalDetailId;
	}
	public String getGroupTerminalId() {
		return groupTerminalId;
	}
	public void setGroupTerminalId(String groupTerminalId) {
		this.groupTerminalId = groupTerminalId;
	}
	public String getLastlastlastMonth() {
		return lastlastlastMonth;
	}
	public void setLastlastlastMonth(String lastlastlastMonth) {
		this.lastlastlastMonth = lastlastlastMonth;
	}
	public String getMonthBeforeLast() {
		return monthBeforeLast;
	}
	public void setMonthBeforeLast(String monthBeforeLast) {
		this.monthBeforeLast = monthBeforeLast;
	}
	public String getLastMonth() {
		return lastMonth;
	}
	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}
	
}
