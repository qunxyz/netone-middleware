package com.jl.common;

import java.util.Map;

/**
 * <p>
 * <li>系统全局常量类</li>
 * </p>
 * <description></description>
 * 
 * @author chenlx
 * @version 1.0
 * @date 2009-7-23
 */
public class ApplicationContext {

	/** 规格 */
	private Map specifications;
	/** 单位 */
	private Map units;
	/** 流水2、手工1 */
	private Map storageTag;
	/** 日志字段映射 */
	private Map logField;

	public Map getLogField() {
		return logField;
	}

	public void setLogField(Map logField) {
		this.logField = logField;
	}

	public Map getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Map specifications) {
		this.specifications = specifications;
	}

	public Map getUnits() {
		return units;
	}

	public void setUnits(Map units) {
		this.units = units;
	}

	public Map getStorageTag() {
		return storageTag;
	}

	public void setStorageTag(Map storageTag) {
		this.storageTag = storageTag;
	}

}
