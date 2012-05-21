package oe.netone.phone;

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
    /**
	 *  解析手机配置中的xml数据  
	 *  xuwei（2012-2-27）
	 *  
	 */

public class XmlAnalysis {
	public static List<PhoneData> readXML(String str) {

		List<PhoneData> pdList = new ArrayList<PhoneData>();

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

		NodeList items = dom.getElementsByTagName("PhoneUrl"); 
		for (int i = 0; i < items.getLength(); i++) {
 			Element personNode = (Element) items.item(i); 
			PhoneData pData=new PhoneData();
 			pData.setName(new String(personNode.getAttribute("name")));
 			pdList.add(pData);
		}
		return pdList;
	}

}
