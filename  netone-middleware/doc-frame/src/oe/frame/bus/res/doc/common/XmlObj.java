package oe.frame.bus.res.doc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * XMLÄ£°å¶ÔÏó
 * 
 * @author hotchaoyi
 * 
 */
public class XmlObj {
	private String id;

	private String name;

	private String value;

	private String type;

	private String htmltype;

	private boolean isreadonly;

	private boolean ismanager;

	private String checkmode;

	private short width;

	private Map extendattribute;

	private List childVars = new ArrayList();

	public void addChildVars(XmlObj xmlObj) {
		childVars.add(xmlObj);
	}

	public List getChildVars() {
		return childVars;
	}

	public void setChildVars(List childVars) {
		this.childVars = childVars;
	}

	public String getCheckmode() {
		return checkmode;
	}

	public void setCheckmode(String checkmode) {
		this.checkmode = checkmode;
	}

	public Map getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(Map extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getHtmltype() {
		return htmltype;
	}

	public void setHtmltype(String htmltype) {
		this.htmltype = htmltype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIsmanager() {
		return ismanager;
	}

	public void setIsmanager(boolean ismanager) {
		this.ismanager = ismanager;
	}

	public boolean isIsreadonly() {
		return isreadonly;
	}

	public void setIsreadonly(boolean isreadonly) {
		this.isreadonly = isreadonly;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

}
