package com.jl.common;

import java.util.Map;

/**
 * <p>
 * <li>ϵͳȫ�ֳ�����</li>
 * </p>
 * <description></description>
 * 
 * @author chenlx
 * @version 1.0
 * @date 2009-7-23
 */
public class ApplicationContext {

	/** ��� */
	private Map specifications;
	/** ��λ */
	private Map units;
	/** ��ˮ2���ֹ�1 */
	private Map storageTag;
	/** ��־�ֶ�ӳ�� */
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
