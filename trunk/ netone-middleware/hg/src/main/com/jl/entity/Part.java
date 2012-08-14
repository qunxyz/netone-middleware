package com.jl.entity;

/**
 * 行政区划
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class Part {

	/** 区域ID */
	private String partId;
	/** 区域编码 */
	private String partCode;
	/** 区域名称 */
	private String partName;
	/** 扩展 */
	private String extension;
	/** 备注 */
	private String note;
	/** 父区域ID */
	private String parentPartId;
	/** 父级区域 */
	private Part parentPart;
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

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
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

	public String getParentPartId() {
		return parentPartId;
	}

	public void setParentPartId(String parentPartId) {
		this.parentPartId = parentPartId;
	}

	public Part getParentPart() {
		return parentPart;
	}

	public void setParentPart(Part parentPart) {
		this.parentPart = parentPart;
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

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
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

}
