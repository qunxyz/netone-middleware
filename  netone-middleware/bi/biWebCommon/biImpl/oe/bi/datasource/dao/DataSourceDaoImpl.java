package oe.bi.datasource.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oe.bi.datasource.dao.DataSourceDao;
import oe.bi.datasource.obj.Datasource;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @version 1.0.0 2006-06-15
 * @author wang-ting-jie
 * 
 */
public class DataSourceDaoImpl implements DataSourceDao {

	/**
	 * ��������Դid��������ԴDatasourceObj����
	 * 
	 * @param id
	 *            ����Դid
	 */
	public Datasource load(String id) {
		// TODO Auto-generated method stub
		List dataSourceList = query("");
		for (int i = 0; i < dataSourceList.size(); i++) {
			Datasource dsObj = (Datasource) dataSourceList.get(i);
			if (dsObj.getDsid() != null && dsObj.getDsid().equals(id))
				return dsObj;
		}
		return null;
	}

	/**
	 * ����һ������Դ
	 * 
	 * @param DatasourceObj
	 *            dsObj
	 */
	public void create(Datasource dsObj) {
		// TODO Auto-generated method stub
		String _currentPath = getClass().getClassLoader().getResource("")
				.getPath();
		List dataSourceList = query("");
		dataSourceList.add(dsObj);
		this.writeXml(_currentPath
				+ "/com/newland/bi/datasource/dao/DbSources.xml",
				dataSourceList);
	}

	/**
	 * �޸�����Դ
	 * 
	 * @param DatasourceObj
	 *            dsObj
	 */
	public void update(Datasource dsObj) {
		// TODO Auto-generated method stub
		String _currentPath = getClass().getClassLoader().getResource("")
				.getPath();
		List dataSourceList = query("");
		for (int i = 0; i < dataSourceList.size(); i++) {
			Datasource obj = (Datasource) dataSourceList.get(i);
			if (obj.getDsid().equals(dsObj.getDsid())) {
				dataSourceList.remove(i);
				dataSourceList.add(i, dsObj);
				break;
			}
		}
		this.writeXml(_currentPath
				+ "/com/newland/bi/datasource/dao/DbSources.xml",
				dataSourceList);

	}

	/**
	 * ɾ������Դ
	 * 
	 * @param DatasourceObj
	 *            dsObj
	 */
	public void drop(Datasource dsObj) {
		// TODO Auto-generated method stub
		String _currentPath = getClass().getClassLoader().getResource("")
				.getPath();
		List dataSourceList = query("");
		dataSourceList.remove(dsObj);
		this.writeXml(_currentPath
				+ "/com/newland/bi/datasource/dao/DbSources.xml",
				dataSourceList);

	}

	/**
	 * ��������,��������Դ,����List ��,List��ΪDatasourceObj����
	 * 
	 * @param condition
	 *            ��ѯ����
	 */
	public List query(String condition) {
		List dataSourceList = new ArrayList();
		try {
			String _currentPath = getClass().getClassLoader().getResource("")
					.getPath();

			File fRule = new File(_currentPath
					+ "/com/newland/bi/datasource/dao/DbSources_Rule.xml");
			// ����XMLģ��
			Digester digester = DigesterLoader.createDigester(fRule.toURL());

			digester.push(dataSourceList);
			// ����XML�ĵ�
			InputStream input = new FileInputStream(_currentPath
					+ "/com/newland/bi/datasource/dao/DbSources.xml");
			digester.parse(input);
			return dataSourceList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * дXML�ĵ�
	 * 
	 * @param fileName
	 *            �ĵ���,�ļ����ǲ�����·������չ����,������Զ�������չ��.xml ͬʱ
	 *            ����core-document.xml�ĵ��е�xmlpath�ж����·����Ϣ����·��
	 * @param list
	 *            ��Ҫд�����ֵ,����ֵ��DatasourceObj����
	 * 
	 * ����˵��: ���д������ݹ���,������ڴ����
	 * 
	 */
	private void writeXml(String fileName, List list) {
		// TODO Auto-generated method stub
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element DbSources = doc.createElement("DbSources");// �����
			doc.appendChild(DbSources);
			for (int i = 0; i < list.size(); i++) {
				Datasource daObj = (Datasource) list.get(i);
				Element eDbSource = doc.createElement("DbSource");
				eDbSource.setAttribute("dsid", daObj.getDsid());// ����ԴID
				eDbSource.setAttribute("dsname", daObj.getDsname());// ����Դ����
				eDbSource.setAttribute("dstype", daObj.getDstype());// ����Դ����
				eDbSource.setAttribute("dsurl", daObj.getDsurl());// ����Դ��ַ
				eDbSource.setAttribute("dsdriver", daObj.getDsdriver());// ����Դ����
				eDbSource.setAttribute("dsflit", daObj.getDsflit());// ����Դ����
				eDbSource.setAttribute("username", daObj.getUsername());// ����Դ�û���
				eDbSource.setAttribute("password", daObj.getPassword());// ����Դ����
				eDbSource.setAttribute("extendattribute", daObj
						.getExtendattribute());// ����Դ��������
				eDbSource.setAttribute("description", daObj.getDescription());// ����Դ����
				DbSources.appendChild(eDbSource);
			}

			saveXmlFile(doc, fileName); // ����ΪXML�ļ�
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}

	}

	/**
	 * ����XML�ĵ� saveXmlFile
	 * 
	 * @param xDoc
	 * @param filename
	 * @return
	 */
	private static boolean saveXmlFile(Document xDoc, String filename) {
		try {
			// ���ȴ���һ��TransformerFactory����,���ɴ˴���Transformer����
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			// ��ȡTransformser������������,�༴XSLT�����ȱʡ�������,����һ��
			// java.util.Properties����
			Properties properties = t.getOutputProperties();

			// �����µ��������:����ַ�����ΪGB2312,��������֧�������ַ�,XSLT���������
			// ��XML�ĵ���������������ַ�,����������ʾ,���������ν��"��������"��
			// ������OutputKeys����ַ�������OutputKeys.ENCODING��
			properties.setProperty(OutputKeys.ENCODING, "GB2312");

			// ����XSLT�����������ԡ�
			t.setOutputProperties(properties);

			// ����XSLT����,������������е�����,���DOM Tree�е����ݵ���������С�
			t.transform(new DOMSource(xDoc), new StreamResult(
					new FileOutputStream(filename)));

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		DataSourceDaoImpl d = new DataSourceDaoImpl();
		List l = d.query("");
		d.writeXml("C:/bbbb.xml", l);
		System.out.println("size=====" + l.size());
		for (int i = 0; i < l.size(); i++) {
			Datasource dsObj = (Datasource) l.get(i);
			System.out.println("getDescription=====" + dsObj.getDescription());
			System.out.println("getDsflit=====" + dsObj.getDsflit());
			System.out.println("getDsid=====" + dsObj.getDsid());
			System.out.println("getDsname=====" + dsObj.getDsname());
			System.out.println("getDstype=====" + dsObj.getDstype());
			System.out.println("getDsurl=====" + dsObj.getDsurl());
			System.out.println("getExtendattribute====="
					+ dsObj.getExtendattribute());
			System.out.println("getPassword=====" + dsObj.getPassword());
			System.out.println("getUsername=====" + dsObj.getUsername());
		}
	}

}