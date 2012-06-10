package com.jl.common.netone.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jl.common.netone.UmsProtecte;
import com.jl.common.report.parse.AddRs;
 

public class Dyviewxml {
   public static void main(String[] args) {
	UmsProtecte uProtecte=new UmsProtecte();
	UmsProtectedobject upobju=uProtecte.loadUmsProtecteNaturalname("DYVIEWSCIRPT.DYVIEWSCIRPT.DDDSSS");
	try {
		Dyviewscript dy=readXML(upobju.getExtendattribute());
		System.out.println(dy.getScript());
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	}
   
   public static Dyviewscript readXML(String str) throws DocumentException, UnsupportedEncodingException {

	   Dyviewscript dyscript = new Dyviewscript();
 
		InputStream input = new ByteArrayInputStream(str.getBytes("utf-8"));
		SAXReader reader = new SAXReader();
		Document document = reader.read(input);
		Element root = document.getRootElement();
		
		List<Putcolumnsobj> putlist=new ArrayList<Putcolumnsobj>();
		for (Iterator i = root.elementIterator("Input"); i.hasNext();) {
			Element element = (Element) i.next();
			Putcolumnsobj putobj=new Putcolumnsobj();
			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				try {
					BeanUtils.setProperty(putobj, name, changeint(name, value));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			putlist.add(putobj);
		}
		dyscript.setInput(putlist);
		
		List<Putcolumnsobj> Outputlist=new ArrayList<Putcolumnsobj>();
		for (Iterator i = root.elementIterator("Output"); i.hasNext();) {
			Element element = (Element) i.next();
			Putcolumnsobj putobj=new Putcolumnsobj();
			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				try {
					BeanUtils.setProperty(putobj, name, changeint(name, value));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Outputlist.add(putobj);
		}
		dyscript.setOutput(Outputlist);
		
		for (Iterator i = root.elementIterator("Script"); i.hasNext();) {
			Element fooinit = (Element) i.next();
			String sqlstr = fooinit.getData().toString();
			dyscript.setScript(sqlstr);
		}
		return dyscript;
		
	}
   public static Object changeint(String name, String cvalue) {
		if (name == "columns" || name == "displayname" || name == "stringtype" || name == "remark"
				|| name == "using" ) {
			return  cvalue;
		} else {
			return cvalue;
		}
	} 
}
