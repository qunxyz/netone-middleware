package com.jl.entity;

/**
 * 行政区划
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class Area {

	/** 区域ID */
	private String areaId;
	/** 区域编码 */
	private String areaCode;
	/** 区域名称 */
	private String areaName;
	/** 扩展 */
	private String extension;
	/** 备注 */
	private String note;
	/** 父区域ID */
	private String parentAreaId;
	/** 父级区域 */
	private Area parentArea;
	/** 树级别 */
	private int NLevel;
	/** 树级别编码 */
	private String NLevelCode;
	/** 树级别名称 */
	private String NLevelName;
	/** 树级别具体分类划分 */
	private String level;
	/** 地图信息 */
	private String map;
	/** ext tree 使用 */
	private String id;
	private String text;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public Area getParentArea() {
		return parentArea;
	}

	public void setParentArea(Area parentArea) {
		this.parentArea = parentArea;
	}

	public int getNLevel() {
		return NLevel;
	}

	public void setNLevel(int level) {
		NLevel = level;
	}

	public String getNLevelCode() {
		return NLevelCode;
	}

	public void setNLevelCode(String levelCode) {
		NLevelCode = levelCode;
	}

	public String getNLevelName() {
		return NLevelName;
	}

	public void setNLevelName(String levelName) {
		NLevelName = levelName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

}
