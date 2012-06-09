package oe.netone.bi.pa;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLDevise{
	
	public static Devise readXML(String str) {
		Devise devise = new Devise();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   InputSource is = new InputSource();   
		   is.setCharacterStream(new StringReader(str));   

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

//		Element root = dom.getDocumentElement();

		NodeList items = dom.getElementsByTagName("devise");// 解析devise这个节点
      
		for (int i = 0; i < items.getLength(); i++) {
 
			Element personNode = (Element) items.item(i);
			devise.setLayouttype((String)personNode.getAttribute("layouttype"));
			devise.setShowtype((String)personNode.getAttribute("showtype"));
			devise.setRow((String)personNode.getAttribute("row"));
			devise.setStand((String)personNode.getAttribute("stand"));
		}
		return devise;
	}
	
}