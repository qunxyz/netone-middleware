package com.jl.entity;

/**
 * ��������
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class Area {

	/** ����ID */
	private String areaId;
	/** ������� */
	private String areaCode;
	/** �������� */
	private String areaName;
	/** ��չ */
	private String extension;
	/** ��ע */
	private String note;
	/** ������ID */
	private String parentAreaId;
	/** �������� */
	private Area parentArea;
	/** ������ */
	private int NLevel;
	/** ��������� */
	private String NLevelCode;
	/** ���������� */
	private String NLevelName;
	/** �����������໮�� */
	private String level;
	/** ��ͼ��Ϣ */
	private String map;
	/** ext tree ʹ�� */
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
