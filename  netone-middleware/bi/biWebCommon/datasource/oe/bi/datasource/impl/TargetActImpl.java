package oe.bi.datasource.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.dao.ui.obj.TargetObj;
import oe.bi.datasource.TargetAct;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.reference.XMLReference;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;


public class TargetActImpl implements TargetAct {

	/**
	 * 
	 */
	public String[][] targetGroupList(NodeObj node) {

		try {
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			List list = dfd.listDyObjFromLevel(node.getLevelname());
			String info[][] = new String[list.size()][3];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TCsForm element = (TCsForm) iter.next();
				info[i][0] = element.getFormcode();
				info[i][1] = element.getFormname();
				info[i][2] = element.getTimelevel();
				i++;
			}
			return info;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List targetElementList(String formcode) {
		List listRs = new ArrayList();
		try {
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			DyObj obj = dfd.fromDy(formcode);
			List list = obj.getColumn();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();
				String columnid = element.getColumnid();
				String type=element.fetchType();
				if (!element.isUseable()
						|| XMLReference.DIMETION_BELONGX
								.equalsIgnoreCase(columnid)
						|| XMLReference.DIMETION_TIMEX
								.equalsIgnoreCase(columnid)||"number".equalsIgnoreCase(type)) {
					continue;
				}
				TargetObj to = new TargetObj();
				to.setGroupid(formcode);
				to.setTragetid(element.getColumncode());
				to.setTragetname(element.getColumname());
				to.setNativeid(columnid);
				to.setTragetJavatype(element.fetchType());
				listRs.add(to);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listRs;
	}

}
