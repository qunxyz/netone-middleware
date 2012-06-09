package oe.netone.dy;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 
 * ʹ��Dom����xml�ļ�
 * 
 * 
 */

public class XmlAnalysic {

	public static List<SQLData> readXML(String str) {

		SAXReader reader = new SAXReader();
		List<SQLData> persons = new ArrayList<SQLData>();

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
			dom =(Document) reader.read(is);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

//		Element root = dom.getDocumentElement();

		NodeList items = dom.getElementsByTagName("control");// ��������person�ڵ�
      
		for (int i = 0; i < items.getLength(); i++) {

			

			// �õ���һ��person�ڵ�

			Element personNode = (Element) items.item(i);

			// ��ȡperson�ڵ��id����ֵ
			SQLData renyuan=new SQLData();
			renyuan.setQudong(new String(personNode.getAttribute("qudong")));
			renyuan.setLujing(new String(personNode.getAttribute("lujing")));
			renyuan.setName(new String(personNode.getAttribute("root")));
			renyuan.setPaw(new String(personNode.getAttribute("paw")));
            persons.add(renyuan);
		}
		return persons;
	}
}
