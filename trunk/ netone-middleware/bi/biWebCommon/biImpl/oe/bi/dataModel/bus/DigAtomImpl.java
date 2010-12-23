package oe.bi.dataModel.bus;

import java.util.ArrayList;
import java.util.List;

import oe.bi.BiEntry;
import oe.bi.RootPath;
import oe.bi.dataModel.bus.DigAtom;
import oe.bi.dataModel.bus.util.FlitDimensionValue;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


public class DigAtomImpl implements DigAtom {

	public String[] fetchAtom(String datamodelId, String dimcolumnId,
			String noteid) {
		String[] atom = fetchAtomCore(datamodelId, dimcolumnId, noteid);
		return FlitDimensionValue.filtDimensionValue(atom);
	}



	/**
	 * 
	 */
	private String[] fetchAtomCore(String datamodelId, String dimcolumnId,
			String noteid) {

		RootPath rootpath = (RootPath) BiEntry.fetchBi("rootpath");

		String daModelPath = rootpath.getDatamodelpath() + "tree/";

		SAXReader reader = new SAXReader();

		try {
			Document doc = reader.read(daModelPath + datamodelId + "_"
					+ dimcolumnId + ".xml");

			Node node = doc.selectSingleNode("//tree[@id='" + noteid + "']");

			if (node == null)
				throw new RuntimeException("找不到此节点..");

			List digList = new ArrayList();

			digList = treeWalk((Element) node, digList);

			if (digList.size() == 0) {// 如果已经是原子节点了，那么直接返回该节点
				return new String[] { noteid };
			}

			String s[] = new String[digList.size()];

			for (int i = 0; i < digList.size(); i++) {
				s[i] = digList.get(i).toString();
			}
			return s;
		} catch (DocumentException e) {

			e.printStackTrace();
			throw new RuntimeException(e.getMessage());

		}

	}

	/**
	 * 
	 * @param element
	 * @param list
	 * @return
	 */
	public static List treeWalk(Element element, List list) {

		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			// System.out.println("conunt====="+element.nodeCount());
			Node node = element.node(i);

			if (node instanceof Element) {
				Element e = (Element) node;

				if (e.nodeCount() > 0) {
					treeWalk(e, list);
				} else {
					list.add(e.attributeValue("id"));
				}
			}
		}
		return list;

	}

}
