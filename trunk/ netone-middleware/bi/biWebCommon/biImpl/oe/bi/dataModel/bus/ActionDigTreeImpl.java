package oe.bi.dataModel.bus;

import java.util.Iterator;
import java.util.List;

import oe.bi.BiEntry;
import oe.bi.BiTree;
import oe.bi.DataList;
import oe.bi.RootPath;
import oe.bi.SQLTree;
import oe.bi.TimeTree;
import oe.bi.dataModel.bus.ActionDigTree;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.ext.SqlTypes;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.datasource.DataModelDao;
import oe.bi.exceptions.TreeModelException;
import oe.bi.view.obj.GraphModel;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ActionDigTreeImpl implements ActionDigTree  {

	public String fetchTreeElement(String datamodelId, String dimcolumnId,
			String nodeid, String levelid) throws TreeModelException{
		DataModelDao datamodelDao = (DataModelDao) BiEntry
				.fetchBi("dataModelDao");
		try {
			DataModel dataModel = (DataModel) datamodelDao
					.fetchDataModel(datamodelId);
			DimColumn dimColumn = (DimColumn) dataModel.getDimColumns().get(
					dimcolumnId);
			String treeModel = dimColumn.getTreeModel();
			if (TreeModel._TIME_TREE[0][0].equals(treeModel)) {
				TimeTree timeTree = (TimeTree) BiEntry.fetchBi("timeTree");
				return timeTree.fetchTimeInfo(datamodelId, dimcolumnId, nodeid,
						levelid);
			} else if (TreeModel._SQL_TREE[0][0].equals(treeModel)) {
				SQLTree sqlTree = (SQLTree) BiEntry.fetchBi("sqlTree");
				return sqlTree.fetchTreeInfo(datamodelId, dimcolumnId, nodeid,
						dimColumn.getExtendattribute(), Integer
								.parseInt(levelid));
			} else if (TreeModel._DATA_LIST[0][0].equals(treeModel)) {
				DataList dataList = (DataList) BiEntry.fetchBi("dataList");
				return dataList.fetchDataInfo(datamodelId, dimcolumnId,
						dimColumn.getExtendattribute(), Integer
								.parseInt(levelid));

			} else {
				throw new TreeModelException("treeModel");
				// return normalTree(datamodelId, dimcolumnId, nodeid);
			}

		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BiTree.HEAD_XML + BiTree.END_XML;
		}
	}

	public String fetchTreeElement(String datamodelId, String dimcolumnId,
			String dimensionlevel, String nodeid, String levelid,
			GraphModel graphModel) throws TreeModelException{
		DataModelDao datamodelDao = (DataModelDao) BiEntry
				.fetchBi("dataModelDao");
		DataModel dataModel = null;
		try {
			dataModel = (DataModel) datamodelDao.fetchDataModel(datamodelId);
		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xDimensionId = graphModel.getXOffsetDimension();
		DimColumn dimColumn = (DimColumn) dataModel.getDimColumns().get(
				xDimensionId);
		String sqlType = dimColumn.getSqltype();
		String dimensionvalue = graphModel.getXoffsetDimensionForcastValue();

		if (dimensionvalue == null || dimensionvalue.equals("")) {
			return BiTree.HEAD_XML + BiTree.END_XML;
		}
		String[] dimValues = dimensionvalue.split(",");
		String levelId = graphModel.getForcastLevelid();
		for (int i = 0; i < dimValues.length; i++) {
			if (SqlTypes._DIM_TYPE_DATE[0].equals(sqlType)) {
				dimValues[i] = dimValues[i] + "\" text=\"";
			} else {
				dimValues[i] = dimValues[i] + DigTreeBuilder._NODE_KEY_SPLIT
						+ "\" text=\"";
			}
		}
		String treeinfo = fetchTreeElement(datamodelId, dimcolumnId, nodeid,
				levelid);
		treeinfo = StringUtils.replace(treeinfo, "analysis=0", "analysis=1");
		int treelevelvalue = fetchTreeLevel(treeinfo);
		int levelidvalue = Integer.parseInt(dimensionlevel);
		if (treelevelvalue == levelidvalue) {
			boolean containChoiceValue = false;
			for (int i = 0; i < dimValues.length; i++) {
				if (StringUtils.contains(treeinfo, dimValues[i])) {
					containChoiceValue = true;
					break;
				}
			}
			if (containChoiceValue) {
				int indexOfFirstDim = StringUtils.indexOf(treeinfo,
						dimValues[0]);
				int indexOfLastDim = StringUtils.indexOf(treeinfo,
						dimValues[dimValues.length - 1]);
				int lastLimit = indexOfFirstDim > indexOfLastDim ? indexOfFirstDim
						: indexOfLastDim;
				for (int i = 0; i < dimValues.length; i++) {
					treeinfo = StringUtils.replaceOnce(treeinfo, dimValues[i]
							+ "{cb}0{tt}", dimValues[i] + "{cb}3{tt}");
				}

				String firstSubstring = StringUtils.substring(treeinfo, 0,
						lastLimit);
				String secondSubstring = StringUtils.substring(treeinfo,
						lastLimit);

				firstSubstring = StringUtils.replace(firstSubstring,
						"{cb}0{tt}", "{cb}2{tt}");

				return firstSubstring + secondSubstring;
			} else {
				return treeinfo;
			}
		} else if (treelevelvalue > levelidvalue) {
			return BiTree.HEAD_XML + BiTree.END_XML;
		} else {
			return StringUtils.replace(treeinfo, "{cb}0{tt}", "{cb}2{tt}");
		}
	}

	private int fetchTreeLevel(String treeinfo) {
		int levelidindex = StringUtils.indexOf(treeinfo,
				DigTreeBuilder._SRC_LEVEL + "=")
				+ (DigTreeBuilder._SRC_LEVEL + "=").length();
		String curlevelid = StringUtils.substring(treeinfo, levelidindex,
				levelidindex + 1);
		return Integer.parseInt(curlevelid);

	}

	private String normalTree(String datamodelId, String dimcolumnId,
			String nodeid) {
		Document document = DocumentHelper.createDocument();

		RootPath rootpath = (RootPath) BiEntry.fetchBi("rootpath");

		String daModelPath = rootpath.getDatamodelpath() + "tree/";

		SAXReader reader = new SAXReader();

		try {
			Document doc = reader.read(daModelPath + datamodelId + "_"
					+ dimcolumnId + ".xml");
			List list = doc.selectNodes("//tree[@id='" + nodeid + "']/*");

			Element root = document.addElement("tree");

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Element e = (Element) iter.next();

				// root.add(e);
				Element ree = root.addElement("tree");
				ree.addAttribute(DigTreeBuilder._NODE_ID, e
						.attributeValue(DigTreeBuilder._NODE_ID));
				ree.addAttribute(DigTreeBuilder._NODE_TEXT, e
						.attributeValue(DigTreeBuilder._NODE_TEXT));
				ree.addAttribute(DigTreeBuilder._NODE_ACTION, e
						.attributeValue(DigTreeBuilder._NODE_ACTION));
				ree.addAttribute(DigTreeBuilder._NODE_SRC, e
						.attributeValue(DigTreeBuilder._NODE_SRC));
				ree.addAttribute(DigTreeBuilder._NODE_ONLOAD, e
						.attributeValue(DigTreeBuilder._NODE_ONLOAD));
				ree.addAttribute(DigTreeBuilder._NODE_LEVELID, e
						.attributeValue(DigTreeBuilder._NODE_LEVELID));

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document.asXML();
	}

}
