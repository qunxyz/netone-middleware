package oe.netone.bi;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Zqxml {
	public static ZqApplicatioe readXML(String str) {

		ZqApplicatioe ZqApplicatioe = new ZqApplicatioe();
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

		// Element root = dom.getDocumentElement();

		NodeList items = dom.getElementsByTagName("control");// ��������person�ڵ�

		for (int i = 0; i < items.getLength(); i++) {
			Element personNode = (Element) items.item(i);

			// ��ȡperson�ڵ��id����ֵ
			ZqApplicatioe.setCorrwdvalue(new String(personNode
					.getAttribute("corrwdvalue")));
			ZqApplicatioe.setCorrzbvalue(new String(personNode
					.getAttribute("corrzbvalue")));
			ZqApplicatioe
					.setFname(new String(personNode.getAttribute("fname")));
			ZqApplicatioe.setWdvalue(new String(personNode
					.getAttribute("wdvalue")));
			ZqApplicatioe.setZbvalue(new String(personNode
					.getAttribute("zbvalue")));
			ZqApplicatioe.setComDwd(new String(personNode
					.getAttribute("comDwd")));
			ZqApplicatioe.setComDzb(new String(personNode
					.getAttribute("comDzb")));
			ZqApplicatioe
					.setWdzhi(new String(personNode.getAttribute("wdzhi")));
			ZqApplicatioe.setPformcode(new String(personNode
					.getAttribute("pformcode")));
			ZqApplicatioe.setCformcode(new String(personNode
					.getAttribute("cformcode")));
			ZqApplicatioe.setZqid(new String(personNode.getAttribute("zqid")));
			ZqApplicatioe
					.setPname(new String(personNode.getAttribute("pname")));
		}
		return ZqApplicatioe;
	}
}
