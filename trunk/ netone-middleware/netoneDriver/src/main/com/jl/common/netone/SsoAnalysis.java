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
				// �õ���XML�ļ��ĸ��ڵ�
				Element root=doc.getRootElement();
				// �õ���Ҫ���ҵĽڵ�
				Iterator config = root.elementIterator("config");
				// ����TDataGrid�ڵ�
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
				System.out.println("xml�д�");
				e.printStackTrace();
			} 
			
		}
		return ssoobj;
	}
}