package oe.bi.datasource.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.bi.BiEntry;
import oe.bi.BiTree;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.dataModel.bus.tree.util.MakeXMLTree2;
import oe.bi.datasource.DimensionAct;
import oe.bi.datasource.DimensionLoader;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.seucore.obj.db.UmsApplication;



public class DimensionActImpl implements DimensionAct {

	public String[][] listTree() {

		ApplicationRmi app = null;
		List list = null;
		try {
			// 读取名为resource的rmi服务
			app = (ApplicationRmi) RmiEntry.iv("application");
			UmsApplication upo = new UmsApplication();
			upo.setApptype("DYFORM");
			list = app.queryObjects(upo, null);

		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null) {
			String[][] treelist = new String[list.size()][2];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsApplication element = (UmsApplication) iter.next();
				treelist[i][0] = element.getNaturalname();
				treelist[i][1] = element.getName();
				i++;
			}
			return treelist;
		}
		return null;
	}

	public String subTree(String treeModelId, NodeObj curNode) {
		String str = "";
		DimensionLoader dimensionLoader = (DimensionLoader) BiEntry
				.fetchBi("dimensionLoader");
		List nodeList = dimensionLoader.loadDimension(treeModelId, curNode);

		if (nodeList != null && nodeList.size() > 0) {
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < nodeList.size(); i++) {
				NodeObj node = (NodeObj) nodeList.get(i);
				String preInfo = MakeXMLTree2.makeXMLInfo(treeModelId, node
						.getColumnname(), node.getNodeid(), node.getNodename(),
						node.getLevelname());
				buf.append(preInfo);
			}
			str = BiTree.HEAD_XML + buf.toString() + BiTree.END_XML;
		} else { // 空节点
			str = BiTree.HEAD_XML + BiTree.END_XML;
		}

		return str;
	}

	public void subTreeCount(NodeObj curNode, int loop, Integer count) {
		DimensionLoader dimensionLoader = (DimensionLoader) BiEntry
				.fetchBi("dimensionLoader");
		List nodeList = dimensionLoader.loadDimension(curNode.getTreeModelId(),
				curNode);
		if (loop != 1 && nodeList != null && nodeList.size() > 0) {
			for (int i = 0; i < nodeList.size(); i++) {
				NodeObj nodeobj = (NodeObj) nodeList.get(i);
				subTreeCount(nodeobj, loop - 1, count);
			}
		}
		count = new Integer(count.intValue() + nodeList.size());
	}

	public String timeTree(NodeObj curNode) {
		// TimeTree timeTree = (TimeTree) BiEntry.fetchBi("timeTree");
		// return timeTree.fetchTimeInfo("1", curNode.getColumnname(), curNode
		// .getNodeid(), curNode.getLevelname()); // TODO Auto-generated
		return "";
		// method stub
	}

	public static void main(String[] args) {
		DimensionActImpl dd = new DimensionActImpl();
		dd.listTree();
	}
}
