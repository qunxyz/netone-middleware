package oesee.teach.java.io.xml;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * дXML��Ϣ
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class WriteXML {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Document doc = createDocument();
		write(doc);

	}

	public static Document createDocument() {
		Document document = DocumentHelper.createDocument();
		// ��ø�Ԫ��
		Element root = document.addElement("root");
		// ���Ԫ��

		root.addElement("author").addAttribute("name", "James").addAttribute(
				"location", "UK").addText("James Strachan ̫��");
		// ���Ԫ��
		root.addElement("author").addAttribute("name", "Bob").addAttribute(
				"location", "US").addText("Bob McWhirter");

		return document;
	}

	public static void write(Document document) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		XMLWriter writer = new XMLWriter(System.out, format);
		writer.write(document);
		writer.close();

	}

}
