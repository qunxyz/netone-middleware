package com.jl.common.app.impl2;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AnalysisAppThree {

	public static AppThree readXML(String str) {


		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   InputSource is = new InputSource();   
		   if(StringUtils.isEmpty(str)){
			   str="";
		   }
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
      
			AppThree appThree = new AppThree();

			// �õ���һ��person�ڵ�

			Element personNode = (Element) items.item(0);

			// ��ȡperson�ڵ��id����ֵ
			appThree.setNaturalname(new String(personNode.getAttribute("naturalname")));
			appThree.setName(new String(personNode.getAttribute("name")));
			appThree.setFormcolumn(new String(personNode.getAttribute("DivBlock")));

			appThree.setIshidden(new String(personNode.getAttribute("ishidden")));
			appThree.setWorklistColumnLength(new String(personNode.getAttribute("worklistColumnLength")));
			appThree.setLength(new String(personNode.getAttribute("Length")));
			appThree.setActname(new String(personNode.getAttribute("actname")));
			appThree.setCommitername(new String(personNode.getAttribute("commitername")));
			appThree.setStarttime(new String(personNode.getAttribute("starttime")));
			NodeList formscript = dom.getElementsByTagName("script");// ��������person�ڵ�			
			String script=superNormalize((Node)formscript.item(0));
			appThree.setScript(script);
		return appThree;
	}
	
	public static  String  superNormalize(Node parent) {
		   String data=null;
     // We'll need this to create new Text objects
	if(parent!=null){
     Document factory = parent.getOwnerDocument();
  
     Node current = parent.getFirstChild();
     
     while (current != null) {

         int type = current.getNodeType();
         if (type == Node.CDATA_SECTION_NODE) {
             // Convert CDATA section to a text node
             CDATASection cdata = (CDATASection) current;
              data = cdata.getData();
             Text newNode = factory.createTextNode(data);
             parent.replaceChild(newNode, cdata);
             current = newNode;
         }
         // Recheck in case we changed type above
         type = current.getNodeType();
         if (type == Node.TEXT_NODE) {
             // If previous node is a text node, then append this
             // node's data to that node, and delete this node
             Node previous = current.getPreviousSibling();
             if (previous != null) {
                 int previousType = previous.getNodeType();
                 if (previousType == Node.TEXT_NODE) {
                     Text previousText = (Text) previous;
                     Text currentText = (Text) current;
                      data = currentText.getData();
                     previousText.appendData(data);
                     parent.removeChild(current);
                     current = previous;
                 }
             }
		         } // end if
		         else {// recurse
		             superNormalize(current);
		         }
		
		         // increment node
		         current = current.getNextSibling();
		       
		     } // end while
		     return data;
		 }else{
				return data;
		 }
			}
}
