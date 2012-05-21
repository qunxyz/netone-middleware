package oe.netone.dy;

import java.sql.Date;

import jxl.write.DateTime;

public class ResourceInfo  {
   public String rcode;
   public String rname;
   public String  extendattribute;
   public String model;
   public  String dateTime;
   public String  objecttype;
   public String parentid;
   public String tablename;
   public String FID;
   public String ACTIVE;
   public String inclusion;
   


public String getFID() {
	return FID;
}
public void setFID(String fid) {
	FID = fid;
}
public String getParentid() {
	return parentid;
}
public void setParentid(String parentid) {
	this.parentid = parentid;
}
public String getRcode() {
	return rcode;
}
public void setRcode(String rcode) {
	this.rcode = rcode;
}
public String getRname() {
	return rname;
}
public void setRname(String rname) {
	this.rname = rname;
}

public ResourceInfo()
{super();
	}
public String getExtendattribute() {
	return extendattribute;
}
public void setExtendattribute(String extendattribute) {
	this.extendattribute = extendattribute;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}

public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}
public String getObjecttype() {
	return objecttype;
}
public void setObjecttype(String objecttype) {
	this.objecttype = objecttype;
}
public String getTablename() {
	return tablename;
}
public void setTablename(String tablename) {
	this.tablename = tablename;
}
public String getACTIVE() {
	return ACTIVE;
}
public void setACTIVE(String active) {
	ACTIVE = active;
}
public String getInclusion() {
	return inclusion;
}
public void setInclusion(String inclusion) {
	this.inclusion = inclusion;
}
}