package oe.cms.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cms.RootPath;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class xmlqImpl implements xmlq {

	/**
	 * ��ȡobj����XML�ļ���Ķ�������
	 */
	public long findXmlNumber(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {

			SAXReader reader = new SAXReader();
			// ���ݶ���Obj��ȡ�ļ���Document����
			Document document = reader.read(rootpath + className + ".xml");

			List list = document.selectNodes("//" + className);

			return list.size();

		} catch (DocumentException e) {
			return 0;
		}
	}



	/**
	 * ����key��ȡobj����
	 */
	public Object loadObject(Class objClass, Serializable key) {
		String fullName = objClass.getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {
			// ʵ��������
			Object obj = objClass.newInstance();

			SAXReader reader = new SAXReader();

			Document document = reader.read(rootpath + className + ".xml");
			// ����xmlseriidȡ��Ψһ����Ľ��
			Element element = (Element) document.selectSingleNode("//"
					+ className + "[@xmlseriid='" + key + "']");

			String name = "", value = "";
			// ������ֵ���������
			for (Iterator i = element.attributeIterator(); i.hasNext();) {
				Attribute attribute = (Attribute) i.next();
				name = attribute.getName();
				value = attribute.getValue();
				BeanUtils.setProperty(obj, name, value);
			}
			return obj;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * ����obj����,ȡ����obj��������ϵĶ����б�List
	 */
	public List findXmls(Object obj) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {

			SAXReader reader = new SAXReader();

			Document document = reader.read(rootpath + className + ".xml");
			// ��ȡ����������б�
			Map map = BeanUtils.describe(obj);

			String fieldName = "", fieldValue = "", xmlPath = "";
			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				// ��������
				fieldName = itr.next().toString();
				// ����ֵ
				fieldValue = BeanUtils.getProperty(obj, fieldName);

				if (!"class".equals(fieldName) && fieldValue != null
						&& !fieldValue.equals(""))

					xmlPath += "[@" + fieldName + "='" + fieldValue + "']";
			}
			// ���ط��ϴ˶���������List�б�
			List list = document.selectNodes("//" + className + xmlPath);

			return nodeListToObjList(obj,list);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * ����obj����,����Map�б�,ȡ����obj��������ϵĶ����б�List
	 * @param obj      Object����
	 * @param conditionkey   ��keyΪobj���������,ֵvalueΪ�ж�����(<,>,=,<=,>=,like)
	 */
	public List findXmls(Object obj, Map conditionkey) {
		String fullName = obj.getClass().getName();
		String className = fullName.substring(fullName.lastIndexOf(".") + 1);
		String rootpath = RootPath.getDatamodelpath();
		try {

			SAXReader reader = new SAXReader();

			Document document = reader.read(rootpath + className + ".xml");
			// ��ȡ����������б�
			Map map = BeanUtils.describe(obj);

			String fieldName = "", fieldValue = "", xmlPath = "",condition="";
			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				// ��������
				fieldName = itr.next().toString();
				// ����ֵ
				fieldValue = BeanUtils.getProperty(obj, fieldName);
				condition=conditionkey.get(fieldName)==null||conditionkey.get(fieldName).equals("")?"=":conditionkey.get(fieldName).toString();
				if (!"class".equals(fieldName) && fieldValue != null
						&& !fieldValue.equals("")){
                    if("like".equals(condition))
					xmlPath += "[contains(@" + fieldName + ",'" + fieldValue + "')]";
                    else
                    	xmlPath += "[@" + fieldName +condition+"'" + fieldValue + "']";
				}
			}
			// ���ط��ϴ˶���������List�б�
			List list = document.selectNodes("//" + className + xmlPath);

			return nodeListToObjList(obj,list);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param list  list �������������(Node)
	 * @return
	 */
	private List nodeListToObjList(Object obj, List list) {
		String name = "", value = "";
		List objList=new ArrayList();
		try {
			
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object object = obj.getClass().newInstance();
				Element element = (Element) iter.next();
				for (Iterator iterAttribute = element.attributeIterator(); iterAttribute
						.hasNext();) {
					Attribute attribute = (Attribute) iterAttribute.next();
					name = attribute.getName();
					value = attribute.getValue();

					BeanUtils.setProperty(object, name, value);

				}
				objList.add(object);
			}
			return objList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public List superfindXml(String sql, Class classes) {
		return null;
	}

	public List superfindXml(String sql, String[] columname) {
		return null;
	}

	public List superQuery(String sql) {
		return null;
	}

	public static void main(String[] args) {
//		xmlqImpl x = new xmlqImpl();
//		XmlTest c = new XmlTest();
//		c.setTest1("40");
//		c.setTest2("3");
//		//c.setTest3("sss");
//		//c.setTest4("3");
//	//	c.setXmlseriid("1154680202671");
//
//		Map map=new HashMap();
//		map.put("test1","<");
//		map.put("test2","like");
//		List list=x.findXmls(c,map);
//		for(int i=0;i<list.size();i++){
//			XmlTest xt=(XmlTest)list.get(i);
//			System.out.println("xt.getTest1()====" +xt.getTest1());
//			System.out.println("xt.getTest2()====" +xt.getTest2());
//			System.out.println("xt.getTest3()====" +xt.getTest3());
//			System.out.println("xt.getTest4()====" +xt.getTest4());
//		}
	}
}
