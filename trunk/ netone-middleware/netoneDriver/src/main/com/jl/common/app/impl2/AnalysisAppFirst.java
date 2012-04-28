package com.jl.common.app.impl2;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AnalysisAppFirst {

		public static AppFirst readXML(String str) {
			AppFirst appFirst = new AppFirst();
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

//			Element root = dom.getDocumentElement();

			NodeList items = dom.getElementsByTagName("control");// ��������person�ڵ�
	      
			for (int i = 0; i < items.getLength(); i++) {

				

				// �õ���һ��person�ڵ�

				Element personNode = (Element) items.item(i);

				// ��ȡperson�ڵ��id����
				String fomr=new String(personNode.getAttribute("yingyongform"));
				String work=new String(personNode.getAttribute("yingyongwork"));
			      appFirst.setFormname((String)fomr.subSequence(0, fomr.indexOf("[")));
			      appFirst.setFormcode(StringUtils.substringBetween(fomr, "[", "]"));
			      appFirst.setProcessname((String)work.subSequence(0, work.indexOf("[")));
				  appFirst.setProcessid(StringUtils.substringBetween(work, "[", "]"));
				  appFirst.setWorklistDefaultColumn(personNode.getAttribute("worklistDefaultColumn"));
				  appFirst.setWorklistsize(personNode.getAttribute("worklistsize"));
				  
				  NodeList formitem = dom.getElementsByTagName("formtitle");// ��������person�ڵ�			
					String formtitle=superNormalize((Node)formitem.item(0));
					 appFirst.setFormtitle(formtitle);
					NodeList formitem1 = dom.getElementsByTagName("formendtitle");// ��������person�ڵ�			
					String formendtitle=superNormalize((Node)formitem1.item(0));
					appFirst.setFormendtitle(formendtitle);
			}
			return appFirst;
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
	            System.out.println(data);
	          
	        } // end while
	        return data;
	    }else{
			return data;
	    }
		}
}


