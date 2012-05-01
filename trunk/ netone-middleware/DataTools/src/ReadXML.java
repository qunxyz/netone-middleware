

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.DOMReader;

/**
 * ��xml��Ϣ
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ReadXML {

	/**
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		// readXMLElementBySAX();

		/*String curfile = ReadXML.class.getResource("").getPath()
				+ "data.xml";

		SAXReader reader = new SAXReader();
		Document document;

		document = reader.read(curfile);*/
		
		readXMLElementBySAX();
		
		//System.out.println(StringUtils.replace(document.asXML(), "encoding=\"UTF-8\"?", ""));

	}

	public static void readXMLElementBySAX() {

		String curfile = ReadXML.class.getResource("").getPath()
				+ "../data.xml";

		SAXReader reader = new SAXReader();
	
		Document document;
		try {
			document = reader.read(curfile);
			Element root = document.getRootElement();
			// ������һ��Ľڵ���Ϣ
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				System.out.println(element.getName());
				System.out.println(element.getStringValue());
			}

			// �������е�һ�������б�ǩΪ team ������
			for (Iterator i = root.elementIterator("team"); i.hasNext();) {
				Element foo = (Element) i.next();
				System.out.println(foo.asXML());
				System.out.println(foo.getStringValue());
				System.out.println(foo.getPath());

			}
			// �������ڵ����������
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
