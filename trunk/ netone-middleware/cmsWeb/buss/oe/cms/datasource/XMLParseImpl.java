package oe.cms.datasource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cms.cfg.CellInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParseImpl implements XMLParser {

	public List toOBj(String xml) {
		List list = new ArrayList();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			StringReader re = new StringReader(xml);
			document = reader.read(re);
			Element root = document.getRootElement();

			for (Iterator i = root.elementIterator("CellInfo"); i.hasNext();) {
				Element foo = (Element) i.next();

				String extendattribute = foo.attributeValue("extendattribute");
				String infocellid = foo.attributeValue("infoCellid");
				String xoffset = foo.attributeValue("xoffset");
				String yoffset = foo.attributeValue("yoffset");
				String id = foo.attributeValue("id");

				CellInfo cellinfo = new CellInfo();
				cellinfo.setId(id);
				cellinfo.setExtendattribute(extendattribute);
				cellinfo.setInfoCellid(infocellid);
				cellinfo.setXoffset(xoffset);
				cellinfo.setYoffset(yoffset);
				list.add(cellinfo);

			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public String toXML(Object[] obj) {
		if (obj == null || obj.length == 0) {
			return _XML_HEAD + _DEFAULT_BODY;
		}
		// 取得类名称
		String className = obj[0].getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			buf.append(toXML(obj[i], className));
		}
		return _XML_HEAD + "<" + className + "s>\n" + buf + "</" + className
				+ "s>";
	}

	/**
	 * 根据类对象返回此类对象的xml字符串
	 * 
	 * @author wang ting jie
	 * @param obj
	 *            类对象
	 * @return Xml字符串
	 */
	private String toXML(Object obj, String className) {
		if (obj == null) {
			return "";
		}
		try {
			// 获取属性列表
			Map map = BeanUtils.describe(obj);

			StringBuffer xmlStr = new StringBuffer();

			xmlStr.append("<" + className + " ");

			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				// 属性名称
				String fieldName = itr.next().toString();

				if (!"class".equals(fieldName)) {
					// 属性值
					String fieldValue = BeanUtils.getProperty(obj, fieldName) == null ? ""
							: BeanUtils.getProperty(obj, fieldName);
					xmlStr.append(fieldName + "=\"" + fieldValue + "\" ");
				}
			}
			xmlStr.append("/>\n");
			return xmlStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

}
