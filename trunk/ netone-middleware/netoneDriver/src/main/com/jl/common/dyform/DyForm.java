package com.jl.common.dyform;

import oe.cav.bean.logic.form.TCsForm;

/**
 * 动态表单Metainfo
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public final class DyForm extends TCsForm {
	/**
	 * 需要在列表中默认查询展示的字段
	 */
	private DyFormColumn[] queryColumn_;
	/**
	 * 列表中需要展示的字段
	 */
	private DyFormColumn[] listColumn_;

	/**
	 * 列表中需要展示的字段
	 */
	private DyFormColumn[] allColumn_;
	/**
	 * 表单头部的html嵌入信息
	 */
	private String htmltitleinfo_;

	private String htmlendinfo_;
	/**
	 * 所有子表单
	 */
	private DyForm[] subform_;
	/**
	 * 样式数据
	 */
	private String styleinfo_;

	/**
	 * 样式数据URL
	 */
	private String styleinfourl_;
	/**
	 * 分页中每页条数
	 */
	private int eachPageSize_ = 20;
	/**
	 * 是否为子表单
	 */
	private boolean isSub;
	/**
	 * 子表单模式 <br>
	 * 1 集成展示-多条子表单记录（默认模式）<br>
	 * 2 链接展示-多条子表单记录(需要保存主表单) <br>
	 * 3 链接展示-单子表单记录(需要保存主表单、且系统控制只能一条) 4
	 * 集成展示-多条子表单记录(需要保存主表单、且系统控制只能一条，并且子表单不允许再有子表单)
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
