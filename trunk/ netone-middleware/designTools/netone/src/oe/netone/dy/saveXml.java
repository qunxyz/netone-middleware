package oe.netone.dy;


import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.netone.bi.DataBI;
import oe.netone.bi.updatexml;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class saveXml {

 public static void writeText(String path, String text) {
  try {
    FileOutputStream o = new FileOutputStream(path);
    o.write(text.getBytes("utf-8"));
    o.close();
  } catch(Exception e) {}
 }
 
  public void savexmlfile(String content,String name){
  saveXml.writeText(name, content);

 }
  //BI 创建的时候用的
  public static void rewriteText(String ename,String cname, String text,String naturalname) {
	  String cString="<?xml version='1.0' encoding='UTF-8'?>";
	  String aString=text.replace("&lt;", "<");
	  String bString=cString+aString.replace("&gt;", ">");
	  DataBI dbi=new DataBI();
	  dbi.AddCatalog(ename,cname,bString,naturalname);

	 }
  //修改的时候用的
  public static void rewriteText1(String xmlid,String cname, String text) throws Exception {
	  String cString="<?xml version='1.0' encoding='UTF-8'?>";
	  String aString=text.replace("&lt;", "<");
	  String bString=cString+aString.replace("&gt;", ">");
	  updatexml.rexml(xmlid, cname, bString);

	 }
  //DM创建的时候用的
  public static void dmwriteText(String ename,String cname, String text,String nameID) {
	  String cString="<?xml version='1.0' encoding='UTF-8'?>";
	  String aString=text.replace("&lt;", "<");
	  String bString=cString+aString.replace("&gt;", ">");
	  DataBI dbi=new DataBI();
	  dbi.AddCatalog1(ename,cname,bString,nameID);
	  

	 }
//DM修改的时候用的
  public static void UpdatedmwriteText(String ename,String text) {
	  String cString="<?xml version='1.0' encoding='UTF-8'?>";
	  String aString=text.replace("&lt;", "<");
	  String bString=cString+aString.replace("&gt;", ">");
	    ResourceRmi rsrmi = null;
		Serializable idcreated=null;  
		try {
			// 读取名为resource的rmi服务
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				 UmsProtectedobject upo = rsrmi.loadResourceByNatural(ename);
			     upo.setExtendattribute(bString);
			     rsrmi.updateResource(upo);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}		 
	 }
}
