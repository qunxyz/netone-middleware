package oe.netone.bi.gd;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oe.netone.app.Person;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GraphicXML{

	public static ObjectGraph readXML(String str) {

		List<Person> persons = new ArrayList<Person>();
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

		NodeList formcode = dom.getElementsByTagName("Formcode");//解析Formcode节点表单
      
		for (int i = 0; i < formcode.getLength(); i++) {
			Element personNode = (Element) formcode.item(i);
			oGraph.setFormcode(new String(personNode.getAttribute("formcode")));
		}
		
		NodeList Graphic = dom.getElementsByTagName("Graphic");//解析Graphic 配置信息
		for (int i = 0; i < Graphic.getLength(); i++) {
			Element personNode = (Element) Graphic.item(i);
			oGraph.setQiepianweidu(new String(personNode.getAttribute("qiepianweidu")));
			oGraph.setQiepianweiduzhi(new String(personNode.getAttribute("qiepianweiduzhi")));
			oGraph.setZhankaiX(new String(personNode.getAttribute("zhankaiX")));
			oGraph.setXuanzhezhibiao(new String(personNode.getAttribute("xuanzhezhibiao")));
			oGraph.setTubiaotype(new String(personNode.getAttribute("tubiaotype")));
			NodeList OG=dom.getElementsByTagName("Graphic");//解析xml上的
			String kuozhantiaojian=superNormalize((Node)OG.item(0));
			oGraph.setKuozhantiaojian(kuozhantiaojian);
		}
		
		NodeList Forecast=dom.getElementsByTagName("Forecast"); //解析Forecast 预测上的数据
		for(int j=0;j<Forecast.getLength();j++){
			Element personNode = (Element) Forecast.item(j);
			oGraph.setPredictionbegan(new String(personNode.getAttribute("predictionbegan")));
			oGraph.setPredictionend(new String(personNode.getAttribute("predictionend")));
			oGraph.setPredictiontype(new String(personNode.getAttribute("Predictiontype")));
		}
		return oGraph;
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