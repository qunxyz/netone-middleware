package oe.bi.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.xml.XmlHandler;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DataSet;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.Linker;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.dataModel.obj.TargetColumn;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 * 
 * @author wang-ting-jie
 * 
 */
public class XmlHandlerImpl implements XmlHandler {

	/**
	 * 写XML文档(需要维护索引目录,XML文档的ID由方法自动填写)
	 * 
	 * @param path
	 *            路径名
	 * @param list
	 *            需要写入的数值,该数值是DataModelObj对象
	 * 
	 * 补充说明: xml文档的ID，程序自动生成，用System.currentTimeMillis
	 * 
	 */
	public void writeXml(String path, Object[] list) {
		try {
			for (int i = 0; i < list.length; i++) {
				DataModel dmObj = (DataModel) list[i];
				writeXml(path, dmObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @param path
	 *            路径名
	 * @param obj
	 *            需要写入的数值,该数值是DataModelObj对象
	 * 
	 * 补充说明: xml文档的ID，程序自动生成，用System.currentTimeMillis
	 */
	public void writeXml(String pathname, Object obj) {
		String id = "";
		try {
			id = String.valueOf(System.currentTimeMillis());

			if (!(obj instanceof DataModel)) {
				throw new RuntimeException("Object对象不是DataModel类型...");
			}

			DataModel dmObj = (DataModel) obj;

			// 更新索引file.name文件
			Document document = getIndexFileNameDoc();
			Element indexNode = document.getRootElement();

			if (dmObj.getModelid() == null || dmObj.getModelid().equals("")) {
				dmObj.setModelid(id);
				indexNode.addElement("index").addAttribute("id", id)
						.addAttribute("name", dmObj.getModelname());
				// 新增一个结点
			} else {
				id = dmObj.getModelid();
				Node node = document.selectSingleNode("/indexs/index[@id='"
						+ id + "']");
				if (node != null) {
					Element e = (Element) node;
					e.attribute("name").setValue(dmObj.getModelname()); // 编辑结点
				} else {
					indexNode.addElement("index").addAttribute("id", id)
							.addAttribute("name", dmObj.getModelname());// 新增结点
				}
			}
			saveXmlFile(document, pathname + "index.info");

			// 创建模型文件
			Document doc = DocumentHelper.createDocument();

			Element eDataModelObjs = doc.addElement("DataModels");// 根结点

			Element eDataModelObj = eDataModelObjs.addElement("DataModel"); // 创建DataModelObj结点
			eDataModelObj.addAttribute("modelid", dmObj.getModelid());// 添加DataModelObj结点的modelid属性
			eDataModelObj.addAttribute("modelname", dmObj.getModelname());
			eDataModelObj.addAttribute("extendattribute", dmObj
					.getExtendattribute());
			eDataModelObj.addAttribute("description", dmObj.getDescription());// 添加DataModelObj结点的description属性

			// 创建DataSetObjs结点
			eDataModelObj = writeDataSetXml(eDataModelObj, dmObj);

			// 创建Linkers结点
			eDataModelObj = writeLinkXml(eDataModelObj, dmObj);

			// 创建TargetColumnObj结点
			eDataModelObj = writeTargetColumnXml(eDataModelObj, dmObj);

			// 创建DimColumns结点
			eDataModelObj = writeDimColumnXml(eDataModelObj, dmObj);

			// 创建Optimizes结点
			eDataModelObj = writeOptimizeXml(eDataModelObj, dmObj);
			// 保存为XML文件
			saveXmlFile(doc, pathname + id + ".xml");

		} catch (Exception ex) {
			dropXml(pathname + id + ".xml");
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 获取索引文件XML的Document对象
	 * 
	 * @return
	 */
	private Document getIndexFileNameDoc() {
		try {
			String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
					.getDatamodelpath();
			String path = rootpath + "index.info";
			SAXReader reader = new SAXReader();
			Document document = reader.read(path);
			return document;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 保存XML文档 saveXmlFile
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

	/**
	 * 获得指定目录下的XML索引目录信息
	 * 
	 * @param path
	 *            路径名 补充说明: xml文档的ID，程序自动生成，用Systemcurrentime
	 * 
	 * @return 返回值中 [i][0]是modelID,[i][1]是modelname
	 */
	public String[][] readDisplayInfo(String path) {
		try {
			Document doc = getIndexFileNameDoc();
			List list = doc.selectNodes("/indexs/index");
			String str[][] = new String[list.size()][2];
			for (int i = 0; i < list.size(); i++) {
				Element e = (Element) list.get(i);
				str[i][0] = e.attributeValue("id");
				str[i][1] = e.attributeValue("name");
			}

			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 读Xml文档中的某个对象(需要维护索引目录)
	 * 
	 * @param xmlname
	 * @return
	 */
	public Object readXml(String xmlfullname) {
		List dataModalList = new ArrayList();
		try {
			String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
					.getDatamodelpath();

			File fRule = new File(rootpath + "modelrule.info");
			// 加载XML模板
			Digester digester = DigesterLoader.createDigester(fRule.toURL());

			digester.push(dataModalList);
			// 解析XML文档

			InputStream input = new FileInputStream(xmlfullname);
			digester.parse(input);
			return dataModalList.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 删除XML文档(需要维护索引目录)
	 * 
	 * @param xmlname
	 */
	public void dropXml(String xmlfullname) {
		try {
			// 获取索引XML文件的Document对象
			Document document = this.getIndexFileNameDoc();

			Element root = document.getRootElement();

			// 根据文件路径获取索引ID值
			String id = xmlfullname.substring(xmlfullname.lastIndexOf("/") + 1,
					xmlfullname.lastIndexOf("."));

			// 取得索引ID值对应的节点
			Node no = document.selectSingleNode("/indexs/index[@id='" + id
					+ "']");

			if (no != null) {
				if (root.remove(no)) // 删除节点
				{
					String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
							.getDatamodelpath();
					saveXmlFile(document, rootpath + "index.info");// 重新写入文件

					File file = new File(xmlfullname);
					if (file.exists())
						file.delete(); // 删除文件
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException("删除文件出错." + ex.getMessage());
		}

	}

	/**
	 * 创建DataSetObjs结点
	 * 
	 * @param eDataModelObj
	 * @param dmObj
	 * @return
	 */
	private Element writeDataSetXml(Element eDataModelObj, DataModel dmObj) {
		Element eDataSetObjs = eDataModelObj.addElement("DataSets");
		Map dsMap = dmObj.getDataSets();
		for (Iterator it = dsMap.keySet().iterator(); it.hasNext();) {
			Element eDataSetObj = eDataSetObjs.addElement("DataSet");
			String name = it.next().toString();
			DataSet dsObj = (DataSet) dsMap.get(name);
			eDataSetObj.addAttribute("id", dsObj.getId());// 添加eDataSetObj结点的id属性
			eDataSetObj.addAttribute("name", dsObj.getName());
			eDataSetObj.addAttribute("filtcondition", dsObj.getFiltcondition());
			eDataSetObj.addAttribute("description", dsObj.getDescription());
			eDataSetObj.addAttribute("extendattribute", dsObj
					.getExtendattribute());
		}
		return eDataModelObj;
	}

	/**
	 * 
	 * @param eDataModelObj
	 * @param dmObj
	 * @return
	 */
	private Element writeLinkXml(Element eDataModelObj, DataModel dmObj) {
		Element eLinkers = eDataModelObj.addElement("Linkers"); // 创建Linkers结点
		Map linkMap = dmObj.getLinkers();
		for (Iterator it = linkMap.keySet().iterator(); it.hasNext();) {
			Element eLinker = eLinkers.addElement("Linker");
			String name = it.next().toString();
			Linker linkObj = (Linker) linkMap.get(name);
			eLinker.addAttribute("id", linkObj.getId());// 添加eLinker结点的id属性
			eLinker.addAttribute("name", linkObj.getName());
			eLinker.addAttribute("formDataSet", linkObj.getFormDataSet());
			eLinker.addAttribute("toDataSet", linkObj.getToDataSet());
			eLinker.addAttribute("description", linkObj.getDescription());
			eLinker.addAttribute("extendattribute", linkObj
					.getExtendattribute());
		}
		return eDataModelObj;
	}

	/**
	 * 
	 * @param eDataModelObj
	 * @param dmObj
	 * @return
	 */
	private Element writeTargetColumnXml(Element eDataModelObj, DataModel dmObj) {
		Element eTargetColumnObjs = eDataModelObj.addElement("TargetColumns"); // 创建TargetColumnObjs结点
		Map tcMap = dmObj.getTargetColumns();
		for (Iterator it = tcMap.keySet().iterator(); it.hasNext();) {
			Element eTargetColumnObj = eTargetColumnObjs
					.addElement("TargetColumn");// 创建TargetColumnObj结点
			String name = it.next().toString();

			TargetColumn tColumnObj = (TargetColumn) tcMap.get(name);
			eTargetColumnObj.addAttribute("id", tColumnObj.getId());// 添加TargetColumnObj结点的id属性
			eTargetColumnObj.addAttribute("name", tColumnObj.getName());
			eTargetColumnObj.addAttribute("sqltype", tColumnObj.getSqltype());
			eTargetColumnObj.addAttribute("alarm", tColumnObj.getAlarm());
			eTargetColumnObj.addAttribute("extendattribute", tColumnObj
					.getExtendattribute());
			eTargetColumnObj.addAttribute("description", tColumnObj
					.getDescription());
			// 添加TargetColumnObj结点的description属性
		}
		return eDataModelObj;
	}

	/**
	 * 
	 * @param eDataModelObj
	 * @param dmObj
	 * @return
	 */
	private Element writeDimColumnXml(Element eDataModelObj, DataModel dmObj) {
		Element eDimColumns = eDataModelObj.addElement("DimColumns"); // 创建DimColumns结点
		Map dcMap = dmObj.getDimColumns();
		for (Iterator it = dcMap.keySet().iterator(); it.hasNext();) {
			Element eDimColumn = eDimColumns.addElement("DimColumn");// 创建DimColumn结点
			String name = it.next().toString();

			DimColumn dimColumnObj = (DimColumn) dcMap.get(name);
			eDimColumn.addAttribute("id", dimColumnObj.getId());// 添加DimColumn结点的id属性
			eDimColumn.addAttribute("name", dimColumnObj.getName());
			eDimColumn.addAttribute("sqltype", dimColumnObj.getSqltype());
			eDimColumn.addAttribute("treeModel", dimColumnObj.getTreeModel());
			eDimColumn.addAttribute("extendattribute", dimColumnObj
					.getExtendattribute());
			eDimColumn.addAttribute("description", dimColumnObj
					.getDescription());
			// 添加dimColumnObj结点的description属性
		}
		return eDataModelObj;
	}

	/**
	 * 
	 * @param eDataModelObj
	 * @param dmObj
	 * @return
	 */
	private Element writeOptimizeXml(Element eDataModelObj, DataModel dmObj) {
		Element eOptimizes = eDataModelObj.addElement("Optimizes"); // 创建Optimizes结点
		Map dcMap = dmObj.getOptimizes();
		for (Iterator it = dcMap.keySet().iterator(); it.hasNext();) {
			Element eOptimize = eOptimizes.addElement("Optimize");// 创建Optimize结点
			String name = it.next().toString();

			OptimizeTable OptimizeObj = (OptimizeTable) dcMap.get(name);
			eOptimize.addAttribute("id", OptimizeObj.getId());// 添加Optimize结点的id属性
			eOptimize.addAttribute("name", OptimizeObj.getName());
			eOptimize.addAttribute("targetmap", OptimizeObj.getTargetmap());
			eOptimize.addAttribute("dimensionmap", OptimizeObj
					.getDimensionmap());
			eOptimize.addAttribute("extendattribute", OptimizeObj
					.getExtendattribute());
			eOptimize.addAttribute("description", OptimizeObj.getDescription());
			// 添加dimColumnObj结点的description属性
		}
		return eDataModelObj;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		XmlHandlerImpl xImpl = new XmlHandlerImpl();
		// 读文件
		DataModel l = (DataModel) xImpl
				.readXml("D:\\project\\newLand\\project\\bidm\\salemodel5.xml");
		// 写文件
		xImpl.writeXml("D:\\project\\newLand\\project\\bidm\\", l);

		String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
				.getDatamodelpath();
		// 读取所有文件
		String[][] test = xImpl.readDisplayInfo(rootpath);


		// 删除文件
		// xImpl.dropXml("D:/project/newLand/project/bidm/1152085748625.xml");

	}

}
