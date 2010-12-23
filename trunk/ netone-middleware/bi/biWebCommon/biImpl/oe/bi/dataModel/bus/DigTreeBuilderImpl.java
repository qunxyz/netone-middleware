package oe.bi.dataModel.bus;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.common.db.DBHandler;
import oe.bi.common.xml.XmlHandlerImpl;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.dataModel.util.*;
import oe.bi.datasource.obj.Datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class DigTreeBuilderImpl implements DigTreeBuilder {

	private Log _log = LogFactory.getLog(DigTreeBuilderImpl.class);

	public void buildTree(DataModel datamodel, Datasource datasource) {

		// Datasource ds=(Datasource)BiEntry.fetchBi("dataSource");
		RootPath rootpath = (RootPath) BiEntry.fetchBi("rootpath");
		String daModelPath = rootpath.getDatamodelpath() + "tree/";
		DimColumnUtil dcUtil = new DimColumnUtil();
		Map dimColumnMap = datamodel.getDimColumns();
		for (Iterator it = dimColumnMap.keySet().iterator(); it.hasNext();) {
			try {
				String name = it.next().toString();
				DimColumn dimcolumn = (DimColumn) dimColumnMap.get(name);
				String treeModel = dimcolumn.getTreeModel();
				if (TreeModel._TIME_TREE[0][0].equals(treeModel)) {
					continue;// 时间类型无需建钻取树
				}
				if (TreeModel._SQL_TREE.equals(treeModel)) {
					continue;// SQL类型无需建钻取树
				}
				// String[] sqlInfo = dcUtil.getTreeModelSql(treeModel);
				String[] sql = makeTreeModeltoSQL(treeModel);
				generateDigTree(datasource, sql, daModelPath
						+ datamodel.getModelid() + "_" + dimcolumn.getId()
						+ ".xml", datamodel.getModelid(), dimcolumn.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String[] makeTreeModeltoSQL(String treeModel) {
		StringTokenizer st = new StringTokenizer(treeModel, "[");
		List listInfo = new ArrayList();
		while (st.hasMoreTokens()) {
			StringTokenizer stNext = new StringTokenizer(st.nextToken(), "]");
			if (stNext.hasMoreTokens()) {
				String info = (String) stNext.nextToken();
				listInfo.add(info);
			}
		}
		if (listInfo.size() < 2) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 2; i < listInfo.size(); i++) {
			buf.append("," + (String) listInfo.get(i));
		}
		String columns = buf.substring(1);
		String tableName = (String) listInfo.get(0);
		if (tableName == null || tableName.equals("")) {
			return null;
		}
		String condtionTmp = (String) listInfo.get(1);
		String condition = condtionTmp == null ? "" : " where " + condtionTmp;
		String sql = "select " + columns + " from " + tableName + condition;

		String[] columnArray = columns.split(",");
		String[] sqlInfo = new String[columnArray.length + 1];
		System.arraycopy(columnArray, 0, sqlInfo, 0, columnArray.length);
		sqlInfo[columnArray.length] = sql;
		if (_log.isDebugEnabled()) {
			for (int i = 0; i < sqlInfo.length; i++) {
				_log.debug(sqlInfo[i]);
			}
		}
		return sqlInfo;
	}

	/**
	 * 创建钻取信息
	 * 
	 * @param dsObj
	 *            数据源
	 * @param digExpression
	 *            SQL表达式
	 * @param xmlUrl
	 *            保存XML文件的地址
	 */
	private void generateDigTree(Datasource dsObj, String[] digExpression,
			String xmlUrl, String datamodelid, String dimesionid) {
		// TODO Auto-generated method stub
		Statement st = null;
		ResultSet rs = null;
		try {
			DBHandler dbImpl = (DBHandler) BiEntry.fetchBi("dBHandler");
			st = dbImpl.fetchHanderStatement(dsObj);
			rs = st.executeQuery(digExpression[digExpression.length - 1]);
			int colCount = rs.getMetaData().getColumnCount();
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");// 根结点
			root.addAttribute("id", "root");
			Element eParent = root;

			while (rs.next()) {
				String id = "";
				for (int i = 1; i <= colCount; i = i + 2) {
					id += rs.getString(i) + DigTreeBuilder._NODE_KEY_SPLIT;
					Node node = eParent.selectSingleNode("tree[@id='" + id
							+ "']");
					if (node == null) {
						String value = rs.getString(i + 1);
						String text = "{cb}0" + "{tt}" + value;
						String action = "javascript:nodeAction('" + id + "','"
								+ digExpression[i - 1] + "')";
						String srcValue = DigTreeBuilder._SRC_DATAMODELID + "="
								+ datamodelid + "&"
								+ DigTreeBuilder._SRC_DIMENSIONID + "="
								+ dimesionid + "&"
								+ DigTreeBuilder._SRC_DIMENSIONVALUE + "=" + id
								+ "&" + "analysis=0";
						String src = "/biWeb/servlet/EtlTreeSvl?" + srcValue;
						String onload = "treesonload();";
						String levelId = digExpression[i - 1];

						Element e = eParent.addElement("tree").addAttribute(
								DigTreeBuilder._NODE_ID, id).addAttribute(
								DigTreeBuilder._NODE_TEXT, text).addAttribute(
								DigTreeBuilder._NODE_ACTION, action)
								.addAttribute(DigTreeBuilder._NODE_SRC, src)
								.addAttribute(DigTreeBuilder._NODE_ONLOAD,
										onload).addAttribute(
										DigTreeBuilder._NODE_LEVELID, levelId);

						eParent = e; // 将父节点赋给当前结点
					} else {
						eParent = (Element) node; // 将父节点赋给当前结点
					}
				}
				eParent = root; // 将父节点赋给根结点
			}
			this.saveXmlFile(doc, xmlUrl);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存XML文件
	 * 
	 * @param xDoc
	 * @param filename
	 *            文件路径
	 * @return
	 */
	private boolean saveXmlFile(Document xDoc, String filename) {
		XMLWriter writer = null;
		try {

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");

			writer = new XMLWriter(new FileWriter(filename), format);
			writer.write(xDoc);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XmlHandlerImpl xImpl = new XmlHandlerImpl();
		// List l = xImpl.readXml("SaleModel.xml");
		// DigTreeBuilderImpl dbTreeImpl = new DigTreeBuilderImpl();
		// dbTreeImpl.buildTree((DataModel) l.get(0));
	}

}
