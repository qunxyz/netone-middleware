package com.jl.common.netone;

import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class SsoAnalysis {
	public Ssoobj SsoAsisxml(String  naturalname){
		Ssoobj ssoobj=new Ssoobj();
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upbj=up.loadUmsProtecteNaturalname(naturalname);
		if(upbj.getExtendattribute()!=null && upbj.getExtendattribute()!=""){
 		Document doc=null;
			try {
				doc=DocumentHelper.parseText(upbj.getExtendattribute());
				// 得到该XML文件的根节点
				Element root=doc.getRootElement();
				// 得到你要查找的节点
				Iterator config = root.elementIterator("config");
				// 遍历TDataGrid节点
				while (config.hasNext()) {
					Element recordEle = (Element) config.next();
					ssoobj.setNewurl(recordEle.attributeValue("NewUrl"));
					ssoobj.setUpdateurl(recordEle.attributeValue("UpdateUrl"));
					ssoobj.setDelurl(recordEle.attributeValue("DelUrl"));
					ssoobj.setPws(recordEle.attributeValue("pws"));
					ssoobj.setLogin(recordEle.attributeValue("login"));
				 }
		 
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("xml有错");
				e.printStackTrace();
			} 
			
		}
		return ssoobj;
	}
}