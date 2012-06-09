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

public class XMLAnalysis {

	public static Allocation readXML(String str) {
		Allocation allocation = new Allocation();

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

		NodeList items = dom.getElementsByTagName("Node");// 解析Node这个节点

		for (int i = 0; i < items.getLength(); i++) {

			// �õ���һ��person�ڵ�
			Element personNode = (Element) items.item(i);
			// ��ȡperson�ڵ��id����
			allocation.setDate((String) personNode.getAttribute("Date"));
			allocation.setInteractive((String) personNode
					.getAttribute("interactive"));
			allocation.setModeltype((String) personNode
					.getAttribute("modeltype"));
		}
		return allocation;
	}

}