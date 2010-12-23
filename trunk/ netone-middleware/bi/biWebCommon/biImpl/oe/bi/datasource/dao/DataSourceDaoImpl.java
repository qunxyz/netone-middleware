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
	 * 根据数据源id返回数据源DatasourceObj对象
	 * 
	 * @param id
	 *            数据源id
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
	 * 新增一个数据源
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
	 * 修改数据源
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
	 * 删除数据源
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
	 * 根据条件,查找数据源,返回List 表,List内为DatasourceObj对象
	 * 
	 * @param condition
	 *            查询条件
	 */
	public List query(String condition) {
		List dataSourceList = new ArrayList();
		try {
			String _currentPath = getClass().getClassLoader().getResource("")
					.getPath();

			File fRule = new File(_currentPath
					+ "/com/newland/bi/datasource/dao/DbSources_Rule.xml");
			// 加载XML模板
			Digester digester = DigesterLoader.createDigester(fRule.toURL());

			digester.push(dataSourceList);
			// 解析XML文档
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
	 * 写XML文档
	 * 
	 * @param fileName
	 *            文档名,文件名是不包含路径和扩展名的,程序会自动加入扩展名.xml 同时
	 *            根据core-document.xml文档中的xmlpath中定义的路径信息并入路径
	 * @param list
	 *            需要写入的数值,该数值是DatasourceObj对象
	 * 
	 * 补充说明: 如果写入的数据过多,会出现内存溢出
	 * 
	 */
	private void writeXml(String fileName, List list) {
		// TODO Auto-generated method stub
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element DbSources = doc.createElement("DbSources");// 根结点
			doc.appendChild(DbSources);
			for (int i = 0; i < list.size(); i++) {
				Datasource daObj = (Datasource) list.get(i);
				Element eDbSource = doc.createElement("DbSource");
				eDbSource.setAttribute("dsid", daObj.getDsid());// 数据源ID
				eDbSource.setAttribute("dsname", daObj.getDsname());// 数据源名称
				eDbSource.setAttribute("dstype", daObj.getDstype());// 数据源类型
				eDbSource.setAttribute("dsurl", daObj.getDsurl());// 数据源地址
				eDbSource.setAttribute("dsdriver", daObj.getDsdriver());// 数据源驱动
				eDbSource.setAttribute("dsflit", daObj.getDsflit());// 数据源条件
				eDbSource.setAttribute("username", daObj.getUsername());// 数据源用户名
				eDbSource.setAttribute("password", daObj.getPassword());// 数据源密码
				eDbSource.setAttribute("extendattribute", daObj
						.getExtendattribute());// 数据源附加条件
				eDbSource.setAttribute("description", daObj.getDescription());// 数据源描述
				DbSources.appendChild(eDbSource);
			}

			saveXmlFile(doc, fileName); // 保存为XML文件
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}

	}

	/**
	 * 保存XML文档 saveXmlFile
	 * 
	 * @param xDoc
	 * @param filename
	 * @return
	 */
	private static boolean saveXmlFile(Document xDoc, String filename) {
		try {
			// 首先创建一个TransformerFactory对象,再由此创建Transformer对象。
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			// 获取Transformser对象的输出属性,亦即XSLT引擎的缺省输出属性,这是一个
			// java.util.Properties对象。
			Properties properties = t.getOutputProperties();

			// 设置新的输出属性:输出字符编码为GB2312,这样可以支持中文字符,XSLT引擎所输出
			// 的XML文档如果包含了中文字符,可以正常显示,不会出现所谓的"汉字问题"。
			// 请留意OutputKeys类的字符串常数OutputKeys.ENCODING。
			properties.setProperty(OutputKeys.ENCODING, "GB2312");

			// 更新XSLT引擎的输出属性。
			t.setOutputProperties(properties);

			// 调用XSLT引擎,按照输出属性中的设置,输出DOM Tree中的内容到输出介质中。
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