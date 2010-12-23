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
	 * дXML�ĵ�(��Ҫά������Ŀ¼,XML�ĵ���ID�ɷ����Զ���д)
	 * 
	 * @param path
	 *            ·����
	 * @param list
	 *            ��Ҫд�����ֵ,����ֵ��DataModelObj����
	 * 
	 * ����˵��: xml�ĵ���ID�������Զ����ɣ���System.currentTimeMillis
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
	 *            ·����
	 * @param obj
	 *            ��Ҫд�����ֵ,����ֵ��DataModelObj����
	 * 
	 * ����˵��: xml�ĵ���ID�������Զ����ɣ���System.currentTimeMillis
	 */
	public void writeXml(String pathname, Object obj) {
		String id = "";
		try {
			id = String.valueOf(System.currentTimeMillis());

			if (!(obj instanceof DataModel)) {
				throw new RuntimeException("Object������DataModel����...");
			}

			DataModel dmObj = (DataModel) obj;

			// ��������file.name�ļ�
			Document document = getIndexFileNameDoc();
			Element indexNode = document.getRootElement();

			if (dmObj.getModelid() == null || dmObj.getModelid().equals("")) {
				dmObj.setModelid(id);
				indexNode.addElement("index").addAttribute("id", id)
						.addAttribute("name", dmObj.getModelname());
				// ����һ�����
			} else {
				id = dmObj.getModelid();
				Node node = document.selectSingleNode("/indexs/index[@id='"
						+ id + "']");
				if (node != null) {
					Element e = (Element) node;
					e.attribute("name").setValue(dmObj.getModelname()); // �༭���
				} else {
					indexNode.addElement("index").addAttribute("id", id)
							.addAttribute("name", dmObj.getModelname());// �������
				}
			}
			saveXmlFile(document, pathname + "index.info");

			// ����ģ���ļ�
			Document doc = DocumentHelper.createDocument();

			Element eDataModelObjs = doc.addElement("DataModels");// �����

			Element eDataModelObj = eDataModelObjs.addElement("DataModel"); // ����DataModelObj���
			eDataModelObj.addAttribute("modelid", dmObj.getModelid());// ���DataModelObj����modelid����
			eDataModelObj.addAttribute("modelname", dmObj.getModelname());
			eDataModelObj.addAttribute("extendattribute", dmObj
					.getExtendattribute());
			eDataModelObj.addAttribute("description", dmObj.getDescription());// ���DataModelObj����description����

			// ����DataSetObjs���
			eDataModelObj = writeDataSetXml(eDataModelObj, dmObj);

			// ����Linkers���
			eDataModelObj = writeLinkXml(eDataModelObj, dmObj);

			// ����TargetColumnObj���
			eDataModelObj = writeTargetColumnXml(eDataModelObj, dmObj);

			// ����DimColumns���
			eDataModelObj = writeDimColumnXml(eDataModelObj, dmObj);

			// ����Optimizes���
			eDataModelObj = writeOptimizeXml(eDataModelObj, dmObj);
			// ����ΪXML�ļ�
			saveXmlFile(doc, pathname + id + ".xml");

		} catch (Exception ex) {
			dropXml(pathname + id + ".xml");
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * ��ȡ�����ļ�XML��Document����
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
	 * ����XML�ĵ� saveXmlFile
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

	/**
	 * ���ָ��Ŀ¼�µ�XML����Ŀ¼��Ϣ
	 * 
	 * @param path
	 *            ·���� ����˵��: xml�ĵ���ID�������Զ����ɣ���Systemcurrentime
	 * 
	 * @return ����ֵ�� [i][0]��modelID,[i][1]��modelname
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
	 * ��Xml�ĵ��е�ĳ������(��Ҫά������Ŀ¼)
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
			// ����XMLģ��
			Digester digester = DigesterLoader.createDigester(fRule.toURL());

			digester.push(dataModalList);
			// ����XML�ĵ�

			InputStream input = new FileInputStream(xmlfullname);
			digester.parse(input);
			return dataModalList.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * ɾ��XML�ĵ�(��Ҫά������Ŀ¼)
	 * 
	 * @param xmlname
	 */
	public void dropXml(String xmlfullname) {
		try {
			// ��ȡ����XML�ļ���Document����
			Document document = this.getIndexFileNameDoc();

			Element root = document.getRootElement();

			// �����ļ�·����ȡ����IDֵ
			String id = xmlfullname.substring(xmlfullname.lastIndexOf("/") + 1,
					xmlfullname.lastIndexOf("."));

			// ȡ������IDֵ��Ӧ�Ľڵ�
			Node no = document.selectSingleNode("/indexs/index[@id='" + id
					+ "']");

			if (no != null) {
				if (root.remove(no)) // ɾ���ڵ�
				{
					String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
							.getDatamodelpath();
					saveXmlFile(document, rootpath + "index.info");// ����д���ļ�

					File file = new File(xmlfullname);
					if (file.exists())
						file.delete(); // ɾ���ļ�
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException("ɾ���ļ�����." + ex.getMessage());
		}

	}

	/**
	 * ����DataSetObjs���
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
			eDataSetObj.addAttribute("id", dsObj.getId());// ���eDataSetObj����id����
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
		Element eLinkers = eDataModelObj.addElement("Linkers"); // ����Linkers���
		Map linkMap = dmObj.getLinkers();
		for (Iterator it = linkMap.keySet().iterator(); it.hasNext();) {
			Element eLinker = eLinkers.addElement("Linker");
			String name = it.next().toString();
			Linker linkObj = (Linker) linkMap.get(name);
			eLinker.addAttribute("id", linkObj.getId());// ���eLinker����id����
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
		Element eTargetColumnObjs = eDataModelObj.addElement("TargetColumns"); // ����TargetColumnObjs���
		Map tcMap = dmObj.getTargetColumns();
		for (Iterator it = tcMap.keySet().iterator(); it.hasNext();) {
			Element eTargetColumnObj = eTargetColumnObjs
					.addElement("TargetColumn");// ����TargetColumnObj���
			String name = it.next().toString();

			TargetColumn tColumnObj = (TargetColumn) tcMap.get(name);
			eTargetColumnObj.addAttribute("id", tColumnObj.getId());// ���TargetColumnObj����id����
			eTargetColumnObj.addAttribute("name", tColumnObj.getName());
			eTargetColumnObj.addAttribute("sqltype", tColumnObj.getSqltype());
			eTargetColumnObj.addAttribute("alarm", tColumnObj.getAlarm());
			eTargetColumnObj.addAttribute("extendattribute", tColumnObj
					.getExtendattribute());
			eTargetColumnObj.addAttribute("description", tColumnObj
					.getDescription());
			// ���TargetColumnObj����description����
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
		Element eDimColumns = eDataModelObj.addElement("DimColumns"); // ����DimColumns���
		Map dcMap = dmObj.getDimColumns();
		for (Iterator it = dcMap.keySet().iterator(); it.hasNext();) {
			Element eDimColumn = eDimColumns.addElement("DimColumn");// ����DimColumn���
			String name = it.next().toString();

			DimColumn dimColumnObj = (DimColumn) dcMap.get(name);
			eDimColumn.addAttribute("id", dimColumnObj.getId());// ���DimColumn����id����
			eDimColumn.addAttribute("name", dimColumnObj.getName());
			eDimColumn.addAttribute("sqltype", dimColumnObj.getSqltype());
			eDimColumn.addAttribute("treeModel", dimColumnObj.getTreeModel());
			eDimColumn.addAttribute("extendattribute", dimColumnObj
					.getExtendattribute());
			eDimColumn.addAttribute("description", dimColumnObj
					.getDescription());
			// ���dimColumnObj����description����
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
		Element eOptimizes = eDataModelObj.addElement("Optimizes"); // ����Optimizes���
		Map dcMap = dmObj.getOptimizes();
		for (Iterator it = dcMap.keySet().iterator(); it.hasNext();) {
			Element eOptimize = eOptimizes.addElement("Optimize");// ����Optimize���
			String name = it.next().toString();

			OptimizeTable OptimizeObj = (OptimizeTable) dcMap.get(name);
			eOptimize.addAttribute("id", OptimizeObj.getId());// ���Optimize����id����
			eOptimize.addAttribute("name", OptimizeObj.getName());
			eOptimize.addAttribute("targetmap", OptimizeObj.getTargetmap());
			eOptimize.addAttribute("dimensionmap", OptimizeObj
					.getDimensionmap());
			eOptimize.addAttribute("extendattribute", OptimizeObj
					.getExtendattribute());
			eOptimize.addAttribute("description", OptimizeObj.getDescription());
			// ���dimColumnObj����description����
		}
		return eDataModelObj;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		XmlHandlerImpl xImpl = new XmlHandlerImpl();
		// ���ļ�
		DataModel l = (DataModel) xImpl
				.readXml("D:\\project\\newLand\\project\\bidm\\salemodel5.xml");
		// д�ļ�
		xImpl.writeXml("D:\\project\\newLand\\project\\bidm\\", l);

		String rootpath = ((RootPath) BiEntry.fetchBi("rootpath"))
				.getDatamodelpath();
		// ��ȡ�����ļ�
		String[][] test = xImpl.readDisplayInfo(rootpath);


		// ɾ���ļ�
		// xImpl.dropXml("D:/project/newLand/project/bidm/1152085748625.xml");

	}

}
