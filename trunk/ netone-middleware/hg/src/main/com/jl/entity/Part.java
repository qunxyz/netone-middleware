package com.jl.entity;

/**
 * ��������
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class Part {

	/** ����ID */
	private String partId;
	/** ������� */
	private String partCode;
	/** �������� */
	private String partName;
	/** ��չ */
	private String extension;
	/** ��ע */
	private String note;
	/** ������ID */
	private String parentPartId;
	/** �������� */
	private Part parentPart;
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
