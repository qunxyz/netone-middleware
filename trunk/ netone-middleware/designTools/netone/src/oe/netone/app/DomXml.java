package oe.netone.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 
 * ʹ��Dom����xml�ļ�
 * 
 * 
 */

public class DomXml {

	public static List<Person> readXML(String str) {

		List<Person> persons = new ArrayList<Person>();

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

			Person person = new Person();

			// �õ���һ��person�ڵ�

			Element personNode = (Element) items.item(i);

			// ��ȡperson�ڵ��id����ֵ
			person.setFormcode(new String(personNode.getAttribute("yingyongform")));
			person.setProcessid(new String(personNode.getAttribute("yingyongwork")));
 
			persons.add(person);
		}
		return persons;
	}
	
	public static  String  superNormalize(Node parent) {

        // We'll need this to create new Text objects
        Document factory = parent.getOwnerDocument();
        String data=null;
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
    } // end superNormalize()

}
