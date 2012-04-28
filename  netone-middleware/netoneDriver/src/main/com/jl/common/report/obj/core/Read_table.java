package com.jl.common.report.obj.core;
import java.util.List;

import com.jl.common.report.obj.core.table.Read_tcol;
import com.jl.common.report.obj.core.table.Read_td;
import com.jl.common.report.obj.core.table.Read_tr;



public class Read_table {
	private String id;
	private int itemwidth;
	private int itemheight;
	private int rows;
	private int cols;
	private String ishead;
	private List<Read_tr> trlist;
	private List<Read_tcol> tcollist;
	private List<Read_td> tdlist;
	private String CSS;
	
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public String getIshead() {
		return ishead;
	}
	public void setIshead(String ishead) {
		this.ishead = ishead;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemwidth() {
		return itemwidth;
	}
	public void setItemwidth(int itemwidth) {
		this.itemwidth = itemwidth;
	}
	public int getItemheight() {
		return itemheight;
	}
	public void setItemheight(int itemheight) {
		this.itemheight = itemheight;
	}
	
	public List<Read_tr> getTrlist() {
		return trlist;
	}
	public void setTrlist(List<Read_tr> trlist) {
		this.trlist = trlist;
	}
	public List<Read_tcol> getTcollist() {
		return tcollist;
	}
	public void setTcollist(List<Read_tcol> tcollist) {
		this.tcollist = tcollist;
	}
	public List<Read_td> getTdlist() {
		return tdlist;
	}
	public void setTdlist(List<Read_td> tdlist) {
		this.tdlist = tdlist;
	}
	public String getCSS() {
		return CSS;
	}
	public void setCSS(String cSS) {
		CSS = cSS;
	}
	
	
}
