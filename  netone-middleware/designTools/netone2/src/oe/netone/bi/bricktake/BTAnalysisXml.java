package oe.netone.bi.bricktake;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oe.netone.app.Person;
import oe.netone.bi.gd.ObjectGraph;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BTAnalysisXml{
	public static List<ChartValue> readXML(String str) {

		List<ChartValue> cvList = new ArrayList<ChartValue>();
        ObjectGraph oGraph=new ObjectGraph();
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

		//Element root = dom.getDocumentElement();

		NodeList formcode = dom.getElementsByTagName("Chart");//解析Chart节点表单
		NodeList Bri = dom.getElementsByTagName("TwoBrickTake");//解析Chart节点表单
		
      
		for (int i = 0; i < formcode.getLength(); i++) {
			ChartValue cValue=new ChartValue();
			Element personNode = (Element) formcode.item(i);
			Element personBri  = (Element) Bri.item(0);
			cValue.setId(new String(personNode.getAttribute("ID")));
			if(i!=formcode.getLength()-1){
			cValue.setValue(new String(personNode.getAttribute("Value")));
			}
			cValue.setType(new String(personNode.getAttribute("type")));
			cValue.setBTModel(new String(personBri.getAttribute("BTModel")));
			cvList.add(cValue);
		}
	
	 
		return cvList ;
	}
	
	
}