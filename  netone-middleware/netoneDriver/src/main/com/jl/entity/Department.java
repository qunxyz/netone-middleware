package com.jl.entity;

/**
 * 部门/公司
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-8 上午10:56:08
 * @history
 */
public class Department extends AbstractEntity{

	/** 部门/公司ID */
	private String departmentId;
	/** 部门/公司编码 */
	private String departmentCode;
	/** 部门/公司名称 */
	private String departmentName;
	/** 扩展 */
	private String extension;
	/** 备注 */
	private String note;
	/** 父部门/公司ID */
	private String parentDepartmentId;
	/** 父级部门/公司 */
	private Department parentDepartment;
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
	
	private int orders;

	/** ext tree 使用 */
	private String id;
	private String text;
	
	//extend
	private String areaId;
	
	private String levelName;
	
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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

	public String getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
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

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

}
