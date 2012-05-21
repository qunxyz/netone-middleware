package oe.netone.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
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

public class Xmlrenyuan {

	public static List<Renyuan> readXML(String str) {

		List<Renyuan> persons = new ArrayList<Renyuan>();

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

		NodeList items = dom.getElementsByTagName("control");// ��������person�ڵ�
      
		for (int i = 0; i < items.getLength(); i++) {

			

			// �õ���һ��person�ڵ�

			Element personNode = (Element) items.item(i);

			// ��ȡperson�ڵ��id����ֵ
            Renyuan renyuan=new Renyuan();
            renyuan.setObjecttype(new String(personNode.getAttribute("participantmode")));      
            renyuan.setDivBlock(new String(personNode.getAttribute("DivBlock")));
            renyuan.setNeedSerilaizer(new String(personNode.getAttribute("needSerilaizer")));
            renyuan.setNeedtree(new String(personNode.getAttribute("needtree")));
            renyuan.setNeedcheck1(new String(personNode.getAttribute("needcheck")));
            renyuan.setDescription(new String(personNode.getAttribute("description")));
            renyuan.setIsmanual(new String(personNode.getAttribute("ismanual"))); 
            renyuan.setChoicepage(new String(personNode.getAttribute("choicepage")));  
            persons.add(renyuan);
		}
		return persons;
	}
}
