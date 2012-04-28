package com.jl.common.dyform;

import oe.cav.bean.logic.form.TCsForm;

/**
 * ��̬��Metainfo
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public final class DyForm extends TCsForm {
	/**
	 * ��Ҫ���б���Ĭ�ϲ�ѯչʾ���ֶ�
	 */
	private DyFormColumn[] queryColumn_;
	/**
	 * �б�����Ҫչʾ���ֶ�
	 */
	private DyFormColumn[] listColumn_;

	/**
	 * �б�����Ҫչʾ���ֶ�
	 */
	private DyFormColumn[] allColumn_;
	/**
	 * ��ͷ����htmlǶ����Ϣ
	 */
	private String htmltitleinfo_;

	private String htmlendinfo_;
	/**
	 * �����ӱ�
	 */
	private DyForm[] subform_;
	/**
	 * ��ʽ����
	 */
	private String styleinfo_;

	/**
	 * ��ʽ����URL
	 */
	private String styleinfourl_;
	/**
	 * ��ҳ��ÿҳ����
	 */
	private int eachPageSize_ = 20;
	/**
	 * �Ƿ�Ϊ�ӱ�
	 */
	private boolean isSub;
	/**
	 * �ӱ�ģʽ <br>
	 * 1 ����չʾ-�����ӱ���¼��Ĭ��ģʽ��<br>
	 * 2 ����չʾ-�����ӱ���¼(��Ҫ��������) <br>
	 * 3 ����չʾ-���ӱ���¼(��Ҫ������������ϵͳ����ֻ��һ��) 4
	 * ����չʾ-�����ӱ���¼(��Ҫ������������ϵͳ����ֻ��һ���������ӱ������������ӱ�)
	 * 
	 */
	private String submode;

	public boolean isSub() {
		return isSub;
	}

	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}

	public String getSubmode() {
		return submode;
	}

	public void setSubmode(String submode) {
		this.submode = submode;
	}

	public DyFormColumn[] getQueryColumn_() {
		return queryColumn_;
	}

	public void setQueryColumn_(DyFormColumn[] queryColumn_) {
		this.queryColumn_ = queryColumn_;
	}

	public DyFormColumn[] getListColumn_() {
		return listColumn_;
	}

	public void setListColumn_(DyFormColumn[] listColumn_) {
		this.listColumn_ = listColumn_;
	}

	public String getHtmltitleinfo_() {
		return htmltitleinfo_;
	}

	public void setHtmltitleinfo_(String htmltitleinfo_) {
		this.htmltitleinfo_ = htmltitleinfo_;
	}

	public DyForm[] getSubform_() {
		return subform_;
	}

	public void setSubform_(DyForm[] subform_) {
		this.subform_ = subform_;
	}

	public String getStyleinfo_() {
		return styleinfo_;
	}

	public void setStyleinfo_(String styleinfo_) {
		this.styleinfo_ = styleinfo_;
	}

	public int getEachPageSize_() {
		return eachPageSize_;
	}

	public void setEachPageSize_(int eachPageSize_) {
		this.eachPageSize_ = eachPageSize_;
	}

	public String getStyleinfourl_() {
		return styleinfourl_;
	}

	public void setStyleinfourl_(String styleinfourl_) {
		this.styleinfourl_ = styleinfourl_;
	}

	public DyFormColumn[] getAllColumn_() {
		return allColumn_;
	}

	public void setAllColumn_(DyFormColumn[] allColumn_) {
		this.allColumn_ = allColumn_;
	}

	public String getHtmlendinfo_() {
		return htmlendinfo_;
	}

	public void setHtmlendinfo_(String htmlendinfo_) {
		this.htmlendinfo_ = htmlendinfo_;
	}

}
