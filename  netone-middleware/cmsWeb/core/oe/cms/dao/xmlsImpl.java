package oe.cms.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cms.RootPath;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;



public class xmlsImpl implements xmls {

	/**
	 * ��������
	 */
	public boolean create(Object obj) {

		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {

			createFirstFile(className);//�ж��Ƿ����и����XML�ļ�,û���򴴽�

			SAXReader reader = new SAXReader();

			//��ȡXML�ļ�
			Document document = reader.read(rootpath + className + ".xml");
			//�����
			Element root = document.getRootElement();
			//����һ��className���ֵĽ��
			Element objElement = root.addElement(className);
			//��obj�������������ӵ��˽��
			objElement = objToElement(obj, objElement, "add");

			saveXmlFile(document, rootpath + className + ".xml");
			return true;
		} catch (DocumentException e) {
			return false;
		}
	}

	/**
	 * ɾ������
	 */
	public boolean drop(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		SAXReader reader = new SAXReader();
		try {
			//ȡ��obj�����ΨһID��xmlseriid��ֵ
			String xmlseriid = BeanUtils.getProperty(obj, "xmlseriid");
			//��ȡXML�ļ�
			Document document = reader.read(rootpath + className + ".xml");
			//ȡ�ô�IDֵ��Ӧ�Ľ��
			Node deleteNode = document.selectSingleNode("//" + className
					+ "[@xmlseriid='" + xmlseriid + "']");
			//ɾ��
			if (deleteNode != null
					&& document.getRootElement().remove(deleteNode)) {
				saveXmlFile(document, rootpath + className + ".xml");
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ���¶���
	 */
	public boolean update(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		SAXReader reader = new SAXReader();
		try {
			//			ȡ��obj�����ΨһID��xmlseriid��ֵ
			String xmlseriid = BeanUtils.getProperty(obj, "xmlseriid");
			//��ȡXML�ļ�
			Document document = reader.read(rootpath + className + ".xml");
			//			ȡ�ô�IDֵ��Ӧ�Ľ��
			Element updateElement = (Element) document.selectSingleNode("//"
					+ className + "[@xmlseriid='" + xmlseriid + "']");

			//���´˽��
			updateElement = objToElement(obj, updateElement, "update");

			saveXmlFile(document, rootpath + className + ".xml");

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * �����������жϹ��ڴ����XML�ļ��Ƿ��Ѿ������� ����Ѵ���,�򷵻�,δ�������½�һ��XML�ļ�
	 * 
	 * @param className
	 *            ������
	 */
	public void createFirstFile(String className) {

		String rootpath = RootPath.getDatamodelpath();

		File file = new File(rootpath + className + ".xml");

		if (!file.exists()) {
			Document document = DocumentHelper.createDocument();
	        document.addElement( className+"s");
	        saveXmlFile(document,rootpath + className + ".xml");
		}
	}

	/**
	 * ��obj�������������ӵ��� element�����
	 * 
	 * @author wang ting jie
	 * @param obj
	 *            �����
	 * @param element
	 *            �յ������
	 * @param flag
	 *   flagΪaddʱ,�������,flagΪupdateʱ,���½��
	 * @return element�����
	 */
	private Element objToElement(Object obj, Element element, String flag) {
		try {
			// ��ȡ�����б�
			Map map = BeanUtils.describe(obj);

			String fieldName = "", fieldValue = "";
			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				// ��������
				fieldName = itr.next().toString();
				// ����ֵ
				fieldValue = BeanUtils.getProperty(obj, fieldName) == null ? ""
						: BeanUtils.getProperty(obj, fieldName);

				if (!"class".equals(fieldName)) {
					if ("add".equals(flag))  //����
						element.addAttribute(fieldName, fieldValue);
					else if ("update".equals(flag))   //����
						element.attribute(fieldName).setValue( fieldValue);					
				}
			}

			return element;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * ����XML�ļ�
	 * 
	 * @param xDoc
	 * @param filename
	 * @return
	 */
	private boolean saveXmlFile(Document xDoc, String filename) {
		try {

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");// �����ʽ

			XMLWriter writer = new XMLWriter(new FileWriter(filename), format);
			writer.write(xDoc);
			writer.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}


}
