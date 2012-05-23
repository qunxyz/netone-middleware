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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class AnalysisAppSecond {

		public static AppSecond readXML(String str) {
			AppSecond appSecond = new AppSecond();

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
	      

				// �õ���һ��person�ڵ�

				Element personNode = (Element) items.item(0);

				// ��ȡperson�ڵ��id����ֵ
				appSecond.setIsmanual(new String(personNode.getAttribute("ismanual")));
				appSecond.setNaturalname(new String(personNode.getAttribute("naturalname")));
				appSecond.setName(new String(personNode.getAttribute("name")));
				appSecond.setParticipantmode(new String(personNode.getAttribute("participantmode")));
				appSecond.setParticipantvalue(new String(personNode.getAttribute("participantvalue")));
				appSecond.setSyncmode(new String(personNode.getAttribute("syncmode")));
				appSecond.setCoworkmode(new String(personNode.getAttribute("coworkmode")));
				appSecond.setNeedtree(new String(personNode.getAttribute("needtree")));
				appSecond.setFobidzb(new String(personNode.getAttribute("fobidzb")));
				appSecond.setNeedformedit(new String(personNode.getAttribute("needformedit")));
				appSecond.setFormedit(new String(personNode.getAttribute("formedit")));
				appSecond.setSubfrommode(new String(personNode.getAttribute("subfrommode")));
				appSecond.setZibiaodanmoshi(new String(personNode.getAttribute("zibiaodanmoshi")));
				appSecond.setFilemanage(new String(personNode.getAttribute("filemanage")));
				appSecond.setFiletext(new String(personNode.getAttribute("filetext")));
				appSecond.setMAINFORM(new String(personNode.getAttribute("MAINFORM")));
			return appSecond;
		}
	}
