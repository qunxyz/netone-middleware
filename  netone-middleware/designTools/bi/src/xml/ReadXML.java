package xml;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
	 * @throws DocumentException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws DocumentException,
			IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		// readXMLElementBySAX();

		String curfile = ReadXML.class.getResource("").getPath() + "MyXml.xml";
		SAXReader reader = new SAXReader();
		Document document;
		document = reader.read(curfile);
		readXMLElementBySAX(document);

	}

	public static void readXMLElementBySAX(Document document)
			throws IllegalAccessException, InvocationTargetException {

		Element root = document.getRootElement();
		// 遍历第一层的节点信息
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
		}

		// 遍历所有第一层中所有标签为 team 的数据
		for (Iterator i = root.elementIterator("team"); i.hasNext();) {
			Element foo = (Element) i.next();
		}
		// 遍历根节点的所有属性
		Root xx = new Root();
		for (Iterator i = root.attributeIterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			String name = attribute.getName();
			String value = attribute.getValue();
			BeanUtils.setProperty(xx, name, value);

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
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
