package oe.midware.doc.common;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import oe.frame.bus.res.doc.common.FileRootInfo;
import oe.frame.bus.res.doc.common.XmlHandler;
import oe.frame.bus.res.doc.common.XmlObj;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

public class XmlHandlerImp implements XmlHandler {

	private FileRootInfo fileRootInfo;

	private List xmlColumnInfo;

	private Log _log = LogFactory.getLog(XmlHandlerImp.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rjsoft.mfdoc.common.XmlHandle#writeXml(java.lang.String,
	 *      java.util.List)
	 */
	public void writeXml(String xmlname, List list) {

		String filePathName = fileRootInfo.getXmlpath() + xmlname + ".xml";
		_log.debug(filePathName);

		if (list != null && list.size() > 0) {
			// 根节点
			Document document = DocumentHelper.createDocument();
			Element docVarBeansElement = document.addElement(DOC_VAR_BEANS);

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				XmlObj xmlObj = (XmlObj) iter.next();
				List childVarList = xmlObj.getChildVars();
				Element currentEl = createElement(xmlColumnInfo,
						docVarBeansElement, xmlObj, DOC_VAR_BEAN);
				if (childVarList != null && childVarList.size() > 0) {
					// 对于for中嵌套的变量
					Element childVarsEl = currentEl.addElement(CHILD_VARS);
					for (Iterator iterator = childVarList.iterator(); iterator
							.hasNext();) {
						XmlObj chileObj = (XmlObj) iterator.next();
						createElement(xmlColumnInfo, childVarsEl, chileObj,
								CHILD_VAR);
					}
				}
			}
			XMLWriter writer = null;
			try {
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("GBK");
				writer = new XMLWriter(new FileWriter(new File(filePathName)),
						format);
				writer.write(document);

			} catch (IOException e) {
				_log.error(xmlname + "读取文件出错");
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rjsoft.mfdoc.common.XmlHandle#readXml(java.lang.String)
	 */
	public List readXml(String xmlname) {
		String filePathName = fileRootInfo.getXmlpath() + xmlname + ".xml";
		_log.debug(filePathName);
		List varsList = new ArrayList();
		File file = new File(filePathName);
		if (!(file.exists())) {
			_log.error(filePathName + "XML参考信息未创建");
			return null;
		} else {
			URL rules = null;
			try {
				String _currentPath = this.getClass().getClassLoader()
						.getResource("").getPath();
				_log.debug(_currentPath);
				rules = new URL("file:" + _currentPath + "columnRules.xml");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			_log.debug(rules.getPath());
			Digester digester = DigesterLoader.createDigester(rules);
			digester.push(varsList);
			InputStream input = null;
			try {
				input = new FileInputStream(filePathName);
				digester.parse(input);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return varsList;
	}

	/**
	 * @param xmlTagList
	 * @param docVarBeansElement
	 * @param obj
	 */
	private Element createElement(List xmlTagList, Element docVarBeansElement,
			XmlObj xmlObj, String nextEltag) {
		Element docVarBeanElement;
		docVarBeanElement = docVarBeansElement.addElement(nextEltag);
		for (Iterator xmliter = xmlTagList.iterator(); xmliter.hasNext();) {
			String attributeName = (String) xmliter.next();
			String value = "";
			try {
				value = BeanUtils.getProperty(xmlObj, attributeName);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			docVarBeanElement.addAttribute(attributeName, value);
		}
		return docVarBeanElement;
	}

	public FileRootInfo getFileRootInfo() {
		return fileRootInfo;
	}

	public void setFileRootInfo(FileRootInfo fileRootInfo) {
		this.fileRootInfo = fileRootInfo;
	}

	public FileRootInfo fetchFileRootInfo() {
		// TODO Auto-generated method stub
		return this.fileRootInfo;
	}

	public List getXmlColumnInfo() {
		return xmlColumnInfo;
	}

	public void setXmlColumnInfo(List xmlColumnInfo) {
		this.xmlColumnInfo = xmlColumnInfo;
	}

}
