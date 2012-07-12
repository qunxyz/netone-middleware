package com.lucaslee.report.model;

/**
 * 定义：表示表格单元
 */
public class TableCell extends Rectangle {
	/**
	 * 单元的跨行数，对应HTML中
	 * <TD>的rowspan属性
	 */
	private int rowSpan = 1;

	/**
	 * 单元的跨列数，对应HTML中
	 * <TD>的colspan属性
	 */
	private int colSpan = 1;

	/** 单元的内容 */
	private Object content = "";

	/** 单元是否隐藏的属性，即在toHTMLCode生成的HTML文件中不显示 */
	private boolean isHidden = false;

	/** 样式表类别。（对应HTML中的class属性，也可用于pdf等格式） */
	private String cssClass = null;

	/** 是否不允许换行的属性 */
	private boolean noWrap = true;

	/** HTML中的样式 */
	private String cssStyle = "";

	public TableCell() {
		this.setAlign(Rectangle.ALIGN_LEFT);
	}

	/**
	 * 
	 * @param str
	 *            单元格内容
	 */
	public TableCell(String str) {
		super();
		this.content = str;
	}

	/**
	 * 
	 * @param str
	 *            单元格内容
	 * @param align
	 *            单元格内容            
	 */
	public TableCell(String str,int align) {
		super();
		this.setAlign(align);
		this.content = str;
	}
	
	/**
	 * 获得单元的内容
	 * 
	 * @return
	 */
	public Object getContent() {
		return this.content;
	}

	/**
	 * 设置单元的内容
	 * 
	 * @param o
	 */
	public void setContent(Object o) {
		this.content = o;
	}

	/**
	 * 设置单元的跨行数
	 * 
	 * @param i
	 */
	public void setRowSpan(int i) {
		this.rowSpan = i;
	}

	/**
	 * 设置单元的跨行数
	 * 
	 * @param i
	 */
	public void setColSpan(int i) {
		this.colSpan = i;
	}

	/**
	 * 设置样式表类别
	 * 
	 * @param s
	 */
	public void setCssClass(String s) {
		this.cssClass = s;
	}

	/**
	 * 获得单元的跨行数
	 * 
	 * @return int
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/**
	 * 获得单元的跨列数
	 * 
	 * @return int
	 */
	public int getColSpan() {
		return this.colSpan;
	}

	/**
	 * 获得单元是否隐藏的属性
	 * 
	 * @return
	 */
	public boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * 获得样式表类别
	 * 
	 * @return
	 */
	public String getCssClass() {
		return this.cssClass;
	}

	/**
	 * 设置单元是否隐藏的属性
	 * 
	 * @param b
	 */
	public void setIsHidden(boolean b) {
		isHidden = b;
	}

	/**
	 * 获得是否允许换行的属性
	 * 
	 * @return
	 */
	public boolean getNoWrap() {
		return noWrap;
	}

	/**
	 * 设置是否允许换行的属性
	 * 
	 * @param noWrap
	 */
	public void setNoWrap(boolean noWrap) {
		this.noWrap = noWrap;
	}

	/**
	 * 获得HTML中的样式
	 * 
	 * @return
	 */
	public String getCssStyle() {
		return cssStyle;
	}

	/**
	 * 设置HTML中的样式
	 * 
	 * @param cssStyle
	 */
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	@Override
	public String toString() {
		return this.getContent() == null ? null : this.getContent().toString();
	}

}