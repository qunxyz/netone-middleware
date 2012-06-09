package oe.netone.dy;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.jl.common.workflow.DbTools;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class Wrxml {

	/**
	 * @param args
	 */

	public void writexml(String fc, String xinfo, String pid) throws Exception {
		String cString = "<?xml version='1.0' encoding='UTF-8'?>";
		String aString = xinfo.replace("&lt;", "<");
		String bString = cString + aString.replace("&gt;", ">");

		Wrxml.writeXML(fc, bString, pid);
	}

	public String readxml(String fc, String pid) throws Exception {
		String xmlinfo2 = Wrxml.readXML(fc, pid);
		return xmlinfo2;
	}

	/**
	 * 读取xml信息
	 * 
	 * @param formcode
	 * @return
	 * @throws Exception
	 */
	public static String readXML(String formcode, String pid) throws Exception {
		return load(formcode, pid).getActionurl();
	}

	/**
	 * 写入xml信息
	 * 
	 * @param formcode
	 * @param xml
	 * @throws Exception
	 */
	public static void writeXML(String formcode, String xml, String pid)
			throws Exception {
		UmsProtectedobject upo = load(formcode, pid);
		upo.setActionurl(xml);
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		boolean fal = rs.updateResource(upo);
		System.out.println("*****" + fal);
	}

	private static UmsProtectedobject load(String formcode, String parentid)
			throws Exception {
		// 读取xml信息
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setParentdir(parentid);
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");

		List list = rs.queryObjectProtectedObj(upo, null, 0, 10, "");
		if (list != null && list.size() != 1) {
			throw new RuntimeException("表单资源不唯一");
		}

		return (UmsProtectedobject) list.get(0);
	}

	/**
	 * 在源来的xml数据上往上添加数据
	 * 
	 * @param formcode
	 * @param xml
	 * @throws Exception
	 */
	public void Updatexml(String fc, String xinfo, String pid) throws Exception {
		String rs = null;
		String xml = load(fc, pid).getActionurl();

		Document doc = null;
		doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();

		Document doc1 = null;
		doc1 = DocumentHelper.parseText(xinfo);
		Element rootElt1 = doc1.getRootElement();
		rootElt.add(rootElt1);
		rs = doc.asXML();

		rs = rs.replace("&lt;", "<");
		rs = rs.replace("&gt;", ">");
		Wrxml.writeXML(fc, rs, pid);
	}
}
