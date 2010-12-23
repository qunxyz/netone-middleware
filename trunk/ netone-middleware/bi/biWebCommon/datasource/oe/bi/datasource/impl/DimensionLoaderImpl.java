package oe.bi.datasource.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.datasource.DimensionLoader;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;


public class DimensionLoaderImpl implements DimensionLoader {

	public List loadDimension(String view, NodeObj node) {

		List listRs = new ArrayList();
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = "";
			if (node == null) {
				naturalname = view + "." + view;

			} else {
				naturalname = StringUtils.substringBetween(node.getNodeid(),
						"[", "]");
			}
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(naturalname);

			List list = rsrmi.subResource(upo.getId());

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject element = (UmsProtectedobject) iter.next();
				NodeObj nodex = new NodeObj();
				nodex.setColumnname(element.getNaturalname());
				nodex.setNodeid(element.getName() + "["
						+ element.getNaturalname() + "]");
				nodex.setNodename(element.getName());
				nodex.setLevelname(element.getObjecttype());
				listRs.add(nodex);
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listRs;
	}

	public List getAllDimension(String view, NodeObj node, String allLevelname) {
		return loadDimension(view, node);
	}

}
