package oesee.teach.java.io.xml;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.DOMReader;

/**
 * 读xml信息
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ReadXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readXMLElementBySAX();
	}

	public static void readXMLElementBySAX() {

		String curfile = ReadXML.class.getResource("").getPath()
				+ "xmldemo.xml";

		SAXReader reader = new SAXReader();
		Document document;
		try {
			
			document = reader.read(curfile);
			Element root = document.getRootElement();
			// 遍历第一层的节点信息
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				System.out.println(element.getName());
				System.out.println(element.getStringValue());
			}

			// 遍历所有第一层中所有标签为 team 的数据
			for (Iterator i = root.elementIterator("team"); i.hasNext();) {
				Element foo = (Element) i.next();
				System.out.println(foo.asXML());
				System.out.println(foo.getStringValue());
				System.out.println(foo.getPath());

			}
			// 遍历根节点的所有属性
			for (Iterator i = root.attributeIterator(); i.hasNext();) {
				Attribute attribute = (Attribute) i.next();
				System.out.println(attribute.getValue());
				// do something
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readXMLAttribute() {
		String curpath = ReadXML.class.getResource("").getPath();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(curpath + "xmldemo.xml");
			Element root = document.getRootElement();

			for (Iterator i = root.attributeIterator(); i.hasNext();) {
				Attribute attribute = (Attribute) i.next();
				System.out.println(attribute.getValue());
				System.out.println(attribute.getName());

			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
