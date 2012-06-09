package com.report.servlet;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import reportread.Read_itf;

public class Getrepface {

	public static void main(String[] args) throws Exception {
		readxmlByName("REPORTDS.REPORTDS.522B82AF88604DDC9433A4F5844775E8");
	}

	public static String readxmlByName(String name) throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(name);
		if (upo != null) {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(upo.getExtendattribute()));

			Document dom = null;
			try {
				dom = builder.parse(is);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Read_itf> itflist = readXMLElementBySAX(dom);

			org.dom4j.Document document = DocumentHelper.createDocument();

			org.dom4j.Element repfolder = document.addElement("Report");
			for (int i = 0; i < itflist.size(); i++) {
				Read_itf item = itflist.get(i);

				repfolder.addElement("Interface")
						.addAttribute("columns", item.getColumns())
						.addAttribute("displayname", item.getDisplayname())
						.addAttribute("remark", item.getRemark())
						.addAttribute("stringtype", item.getStringtype())
						.addAttribute("bindclr", "ÎÞ");

			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			ByteOutputStream bytex = new ByteOutputStream();
			XMLWriter writer = new XMLWriter(bytex, format);
			writer.write(document);
			byte[] bytexxx = bytex.getBytes();
			String rs = new String(bytexxx);
			writer.close();
			return rs.trim();

		}
		return null;
	}

	public static List<Read_itf> readXMLElementBySAX(Document document)
			throws IllegalAccessException, InvocationTargetException {

		List<Read_itf> itflist = new ArrayList<Read_itf>();
		NodeList items = document.getElementsByTagName("Interface");// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½personï¿½Úµï¿½

		for (int i = 0; i < items.getLength(); i++) {

			Read_itf itf = new Read_itf();

			Element personNode = (Element) items.item(i);

			itf.setColumns(personNode.getAttribute("columns"));
			itf.setDisplayname(personNode.getAttribute("displayname"));
			itf.setStringtype(personNode.getAttribute("stringtype"));
			itf.setRemark(personNode.getAttribute("remark"));
			itf.setUsing(personNode.getAttribute("using"));
			itflist.add(itf);
		}
		return itflist;

	}
}
