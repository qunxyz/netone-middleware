package oesee.teach.java.io.xml;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.DOMReader;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

/**
 * ∂¡xml–≈œ¢
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class CopyOfReadXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readXMLAttribute();
	}

	public static void readXMLAttribute() {
		String curpath = CopyOfReadXML.class.getResource("").getPath();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			String text="<?xml version=\"1.0\" encoding=\"UTF-8\"?><node><note><![CDATA[xxxxx]]></note></node>";
			document =DocumentHelper.parseText(text);
			System.out.println(document.get);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
