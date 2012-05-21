package oe.netone.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jl.common.app.impl2.AppThree;
/**
 * 
 * ʹ��Dom����xml�ļ�
 * 
 * 
 */

public class Xmldaiban {

	public static List<AppThree> readXML(String str) {

		List<AppThree> persons = new ArrayList<AppThree>();

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
			AppThree renyuan=new AppThree();
            renyuan.setFormcolumn(new String(personNode.getAttribute("DivBlock"))); 
            renyuan.setNaturalname(new String(personNode.getAttribute("naturalname")));       
            renyuan.setScript(new String(personNode.getAttribute("script")));
            renyuan.setIshidden(new String(personNode.getAttribute("ishidden")));
            persons.add(renyuan);
		}
		return persons;
	}
}
