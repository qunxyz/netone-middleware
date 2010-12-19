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
	 * 创建对象
	 */
	public boolean create(Object obj) {

		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {

			createFirstFile(className);//判断是否已有该类的XML文件,没有则创建

			SAXReader reader = new SAXReader();

			//读取XML文件
			Document document = reader.read(rootpath + className + ".xml");
			//根结点
			Element root = document.getRootElement();
			//新增一个className名字的结点
			Element objElement = root.addElement(className);
			//把obj对象里的属性添加到此结点
			objElement = objToElement(obj, objElement, "add");

			saveXmlFile(document, rootpath + className + ".xml");
			return true;
		} catch (DocumentException e) {
			return false;
		}
	}

	/**
	 * 删除对象
	 */
	public boolean drop(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		SAXReader reader = new SAXReader();
		try {
			//取得obj对象的唯一ID即xmlseriid的值
			String xmlseriid = BeanUtils.getProperty(obj, "xmlseriid");
			//读取XML文件
			Document document = reader.read(rootpath + className + ".xml");
			//取得此ID值对应的结点
			Node deleteNode = document.selectSingleNode("//" + className
					+ "[@xmlseriid='" + xmlseriid + "']");
			//删除
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
	 * 更新对象
	 */
	public boolean update(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		SAXReader reader = new SAXReader();
		try {
			//			取得obj对象的唯一ID即xmlseriid的值
			String xmlseriid = BeanUtils.getProperty(obj, "xmlseriid");
			//读取XML文件
			Document document = reader.read(rootpath + className + ".xml");
			//			取得此ID值对应的结点
			Element updateElement = (Element) document.selectSingleNode("//"
					+ className + "[@xmlseriid='" + xmlseriid + "']");

			//更新此结点
			updateElement = objToElement(obj, updateElement, "update");

			saveXmlFile(document, rootpath + className + ".xml");

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 根据类名称判断关于此类的XML文件是否已经被创建 如果已创建,则返回,未创建则新建一个XML文件
	 * 
	 * @param className
	 *            类名称
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
	 * 将obj对象里的属性添加到到 element结点中
	 * 
	 * @author wang ting jie
	 * @param obj
	 *            类对象
	 * @param element
	 *            空的树结点
	 * @param flag
	 *   flag为add时,新增结点,flag为update时,更新结点
	 * @return element树结点
	 */
	private Element objToElement(Object obj, Element element, String flag) {
		try {
			// 获取属性列表
			Map map = BeanUtils.describe(obj);

			String fieldName = "", fieldValue = "";
			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				// 属性名称
				fieldName = itr.next().toString();
				// 属性值
				fieldValue = BeanUtils.getProperty(obj, fieldName) == null ? ""
						: BeanUtils.getProperty(obj, fieldName);

				if (!"class".equals(fieldName)) {
					if ("add".equals(flag))  //增加
						element.addAttribute(fieldName, fieldValue);
					else if ("update".equals(flag))   //更新
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
	 * 保存XML文件
	 * 
	 * @param xDoc
	 * @param filename
	 * @return
	 */
	private boolean saveXmlFile(Document xDoc, String filename) {
		try {

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");// 输出格式

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
