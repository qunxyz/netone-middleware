package com.lucaslee.report.model;

/**
 * ���壺��ʾ���Ԫ
 */
public class TableCell extends Rectangle {
	/**
	 * ��Ԫ�Ŀ���������ӦHTML��
	 * <TD>��rowspan����
	 */
	private int rowSpan = 1;

	/**
	 * ��Ԫ�Ŀ���������ӦHTML��
	 * <TD>��colspan����
	 */
	private int colSpan = 1;

	/** ��Ԫ������ */
	private Object content = "";

	/** ��Ԫ�Ƿ����ص����ԣ�����toHTMLCode���ɵ�HTML�ļ��в���ʾ */
	private boolean isHidden = false;

	/** ��ʽ����𡣣���ӦHTML�е�class���ԣ�Ҳ������pdf�ȸ�ʽ�� */
	private String cssClass = null;

	/** �Ƿ������е����� */
	private boolean noWrap = true;

	/** HTML�е���ʽ */
	private String cssStyle = "";

	public TableCell() {
		this.setAlign(Rectangle.ALIGN_LEFT);
	}

	/**
	 * 
	 * @param str
	 *            ��Ԫ������
	 */
	public TableCell(String str) {
		super();
		this.content = str;
	}

	/**
	 * 
	 * @param str
	 *            ��Ԫ������
	 * @param align
	 *            ��Ԫ������            
	 */
	public TableCell(String str,int align) {
		super();
		this.setAlign(align);
		this.content = str;
	}
	
	/**
	 * ��õ�Ԫ������
	 * 
	 * @return
	 */
	public Object getContent() {
		return this.content;
	}

	/**
	 * ���õ�Ԫ������
	 * 
	 * @param o
	 */
	public void setContent(Object o) {
		this.content = o;
	}

	/**
	 * ���õ�Ԫ�Ŀ�����
	 * 
	 * @param i
	 */
	public void setRowSpan(int i) {
		this.rowSpan = i;
	}

	/**
	 * ���õ�Ԫ�Ŀ�����
	 * 
	 * @param i
	 */
	public void setColSpan(int i) {
		this.colSpan = i;
	}

	/**
	 * ������ʽ�����
	 * 
	 * @param s
	 */
	public void setCssClass(String s) {
		this.cssClass = s;
	}

	/**
	 * ��õ�Ԫ�Ŀ�����
	 * 
	 * @return int
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/**
	 * ��õ�Ԫ�Ŀ�����
	 * 
	 * @return int
	 */
	public int getColSpan() {
		return this.colSpan;
	}

	/**
	 * ��õ�Ԫ�Ƿ����ص�����
	 * 
	 * @return
	 */
	public boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * �����ʽ�����
	 * 
	 * @return
	 */
	public String getCssClass() {
		return this.cssClass;
	}

	/**
	 * ���õ�Ԫ�Ƿ����ص�����
	 * 
	 * @param b
	 */
	public void setIsHidden(boolean b) {
		isHidden = b;
	}

	/**
	 * ����Ƿ������е�����
	 * 
	 * @return
	 */
	public boolean getNoWrap() {
		return noWrap;
	}

	/**
	 * �����Ƿ������е�����
	 * 
	 * @param noWrap
	 */
	public void setNoWrap(boolean noWrap) {
		this.noWrap = noWrap;
	}

	/**
	 * ���HTML�е���ʽ
	 * 
	 * @return
	 */
	public String getCssStyle() {
		return cssStyle;
	}

	/**
	 * ����HTML�е���ʽ
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