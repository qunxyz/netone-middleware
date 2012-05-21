package oe.netone.bi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.derby.impl.sql.compile.FromBaseTable;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.reference.GetDateTime;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;


import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.Resource;
import com.jl.common.workflow.DbTools;
import com.sun.org.apache.xpath.internal.functions.Function;
import com.sun.swing.internal.plaf.metal.resources.metal;

public class ManageBI {
	public static List allColumn(String formcode) throws Exception {
		List wdList = new ArrayList();
		TCsColumn initColumn = new TCsColumn();
		initColumn.setColumname("请选择维度字段");
		initColumn.setColumncode("belongx");
		initColumn.setColumnid("belongx");
		initColumn.setViewtype("00");

		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		wdList = dy.fetchColumnList(formcode);
		wdList.add(0, initColumn);
		for (int i = 0; i < wdList.size(); i++) {
			System.out.println(((TCsColumn) wdList.get(i)).getExt9viewtype()
					.toString());
		}
		return wdList;
	}

	public static String getstarttime(String tablename) throws Exception {
		String starttime = null;
		List dataList = new ArrayList();
		String sqlString = "select Timex from dyform." + tablename
				+ " order by Timex";
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		while (rSet.next()) {
			dataList.add(rSet.getString(1));
		}
		connDB.close();
		starttime = dataList.get(dataList.size() - 1).toString();
		return starttime;

	}

	public static String getkvdata(String columnstr, String formcode)
			throws Exception {
		String kvString = "";
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		busForm.setColumnid(columnstr);
		List listmame = dys.queryObjects(busForm);
		for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
			TCsColumn columnname = (TCsColumn) iterator1.next();

			int startnum = columnname.toString().indexOf("=");
			int endnum = columnname.toString().indexOf("]");
			String str = columnname.toString().substring(startnum + 1, endnum);

			if (str.equals(columnstr)) {
				kvString = columnname.getValuelist();

			}
		}
		System.out.println(kvString);
		return kvString;
	}

	public static List getzhutiData(String columnstr, String Formcode)
			throws Exception {
		String tablename;
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		ResourceInfo rsInfo = new ResourceInfo();
		TCsForm busForm = dysc.loadForm(Formcode);
		tablename = busForm.getTablename();

		List dataList = new ArrayList();
		dataList.add("请选择维度值");
		String sqlString = "select distinct " + columnstr + " from dyform."
				+ tablename + "";
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		while (rSet.next()) {
			dataList.add(rSet.getString(1));
		}
		connDB.close();
		return dataList;
	}

	public static List getData(String columnstr, String tablename)

	throws Exception {
		List dataList = new ArrayList();
		dataList.add("请选择维度值");
		String sqlString = "select distinct " + columnstr + " from dyform."
				+ tablename + "";
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		while (rSet.next()) {
			dataList.add(rSet.getString(1));
		}
		connDB.close();
		System.out.println(dataList.toString());
		return dataList;
	}

	public List getzqvalue(String zcolumn, String tablename) {
		List list = new ArrayList();
		String sqlString = "select distinct " + zcolumn + " from dyform."
				+ tablename + "";
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				xyData xyobjcect = new xyData();
				xyobjcect.setXdata(rSet.getString(1));
				list.add(xyobjcect);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List getzqzhibiao(String zcolumn, String zbstring, String tablename,
			String zxdata) {
		List list = new ArrayList();
		String sqlString = "select " + zcolumn + "," + zbstring
				+ " from dyform." + tablename + " where " + zcolumn + "='"
				+ zxdata + "'";
		System.out.println(sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				xyData xyobjcect = new xyData();
				xyobjcect.setXdata(rSet.getString(1));
				xyobjcect.setYdata(rSet.getString(2));
				list.add(xyobjcect);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 主题 BI 使用
	public static List ZhuTiBIzhibiao(String xdata, List zbList,
			String Formcode, String limit, String limitcolumn,
			String childrenstring) throws MalformedURLException,
			RemoteException, NotBoundException {

		if (childrenstring == null) {
			childrenstring = "";
		}
		String tablename;
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		ResourceInfo rsInfo = new ResourceInfo();
		TCsForm busForm = dysc.loadForm(Formcode);
		tablename = busForm.getTablename();
		String str = null;
		int j = 1;
		StringBuffer fdBuffer = new StringBuffer();
		String sqlString = null;
		ArrayList bArrayList = new ArrayList();
		List list = new ArrayList();
		for (int i = 0; i < zbList.size(); i++) {
			if (i != zbList.size() - 1) {

				fdBuffer.append(zbList.get(i));
				fdBuffer.append(",");

			} else {
				j = i + 2;
				fdBuffer.append(zbList.get(i));
			}
		}
		if (limit.equals("")) {
			sqlString = "select " + xdata + "," + fdBuffer.toString()
					+ " from dyform." + tablename + "";
		} else {
			sqlString = "select  " + xdata + " ," + fdBuffer.toString()
					+ " from dyform." + tablename + " where " + limitcolumn
					+ "='" + limit + "'" + childrenstring + " order by TIMEX";
		}
		System.out.println("......" + sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				if (j == 1) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					list.add(xyobjcect);
				}
				if (j == 2) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					list.add(xyobjcect);
				}
				if (j == 3) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					list.add(xyobjcect);
				}
				if (j == 4) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					list.add(xyobjcect);
				}
				if (j == 5) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					list.add(xyobjcect);
				}
				if (j == 6) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					list.add(xyobjcect);
				}
				if (j == 7) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					list.add(xyobjcect);
				}
				if (j == 8) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					list.add(xyobjcect);
				}
				if (j == 9) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					xyobjcect.setY7data(rSet.getString(9));
					list.add(xyobjcect);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connDB.close();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(((xyData) list.get(i)).xdata.toString());
		}
		return list;
	}

	// xdata X维度坐标轴 zbList 指标字段 limitcolumn 切片字段 limit 切片值
	public static List getzhibiao(String xdata, List zbList, String tablename,
			String limit, String limitcolumn, String childrenstring) {
		String str = null;
		int j = 1;
		StringBuffer fdBuffer = new StringBuffer();
		String sqlString = null;
		ArrayList bArrayList = new ArrayList();
		List list = new ArrayList();
		for (int i = 0; i < zbList.size(); i++) {
			if (i != zbList.size() - 1) {

				fdBuffer.append(zbList.get(i));
				fdBuffer.append(",");

			} else {
				j = i + 2;
				fdBuffer.append(zbList.get(i));
			}
		}
		if (limit.equals("")) {
			sqlString = "select " + xdata + "," + fdBuffer.toString()
					+ " from dyform." + tablename + "";
		} else {
			sqlString = "select  " + xdata + " ," + fdBuffer.toString()
					+ " from dyform." + tablename + " where " + limitcolumn
					+ "='" + limit + "'" + childrenstring + " order by TIMEX";
		}
		System.out.println("......" + sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				if (j == 1) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					list.add(xyobjcect);
				}
				if (j == 2) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					list.add(xyobjcect);
				}
				if (j == 3) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					list.add(xyobjcect);
				}
				if (j == 4) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					list.add(xyobjcect);
				}
				if (j == 5) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					list.add(xyobjcect);
				}
				if (j == 6) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					list.add(xyobjcect);
				}
				if (j == 7) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					list.add(xyobjcect);
				}
				if (j == 8) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					list.add(xyobjcect);
				}
				if (j == 9) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					xyobjcect.setY7data(rSet.getString(9));
					list.add(xyobjcect);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connDB.close();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(((xyData) list.get(i)).xdata.toString());
		}
		return list;

	}

	public static List<PAnalysis> ExpendXydata(String xData,String yData, String column,
			String tiaojian, String tablename) throws Exception {
		String sqlString = null;
		List<PAnalysis> dtList = new ArrayList<PAnalysis>();
		if (tiaojian.equals("") || tiaojian== null) {
			sqlString = "select "+xData+"," + yData + " from dyform." + tablename;
		} else {
			sqlString = "select "+xData+"," + yData + " from dyform." + tablename 
					+ " where " + column + "='" + tiaojian + "'";
		}
		System.out.println("www" + sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		while (rSet.next()) {
			PAnalysis xyInfo = new PAnalysis();
			xyInfo.setDimdatetime((rSet.getString(1)));
			xyInfo.setTarget(Double.parseDouble((rSet.getString(2))));
			dtList.add(xyInfo);
		}
		connDB.close();
		return dtList;
	}

	public static List Alldatetime(String formcode) throws Exception {
		List dtList = new ArrayList();
		dtList.add("请选择时间");
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh("1");
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List list = new ArrayList();
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		List listmame = dys.queryObjects(busForm);
		try {

			int index = DyEntry.iv().queryDataNum(dydata, "");
			list = DyEntry.iv().queryData(dydata, 0, index, "");
			StringBuffer but = new StringBuffer();
			int l = list.size();

			int i = 1;
			if (l != 0) {
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DyFormData name = (DyFormData) iterator.next();
					dtList.add(name.getTimex());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		return dtList;
	}

	public static List gettablename(String formcode) throws Exception {
		List list = new ArrayList();
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		ResourceInfo rsInfo = new ResourceInfo();
		TCsForm busForm = dysc.loadForm(formcode);
		rsInfo.tablename = busForm.getTablename();
		System.out.println(rsInfo.tablename);
		rsInfo.extendattribute = busForm.getExtendattribute();
		rsInfo.rname = busForm.getFormname();
		list.add(rsInfo);

		return list;
	}

	public static List getXYdata(String xData, String yData, String tablename,
			String limit) throws Exception {
		String sqlString = null;
		List list = new ArrayList();

		if (limit.equals("请选择维度值")) {
			sqlString = "select " + xData + "," + yData + " from " + tablename
					+ "";
		} else {
			sqlString = "select " + xData + "," + yData + " from " + tablename
					+ " where " + xData + "='" + limit + "'";
		}
		System.out.println(sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rs = connDB.doQuery(sqlString);
		while (rs.next()) {
			xyData xyobjcect = new xyData();
			xyobjcect.xdata = rs.getString(1);
			xyobjcect.ydata = rs.getString(2);
			list.add(xyobjcect);
		}
		connDB.close();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.toArray().toString());
		}
		return list;
	}

	public static List selectBilv() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		String sqlString = "select * from netone.ums_protectedobject  where NATURALNAME like'%ETL.ETL.%'";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.rname = (String) object.get("NAME");
			rInfo.objecttype = (String) object.get("objectType");
			rInfo.rcode = (String) object.get("NATURALNAME");
			rInfo.FID = (String) object.get("ID");
			rInfo.extendattribute = (String) object.get("extendattribute");

			rInfo.dateTime = ((Timestamp) object.get("created")).toString();

			if (((String) object.get("ACTIVE")).toString().equals("1")) {
				rInfo.setACTIVE("有效");
			} else {
				rInfo.setACTIVE("无效");
			}
			aList.add(rInfo);
		}
		for (int j = 0; j < aList.size(); j++) {
			System.out.println(((ResourceInfo) aList.get(j)).getDateTime());
		}

		return aList;
	}

	public static List selectDMlv() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		String sqlString = "select * from netone.ums_protectedobject  where NATURALNAME like'%TRANS.TRANS.%'";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.rname = (String) object.get("NAME");
			rInfo.objecttype = (String) object.get("objectType");
			rInfo.rcode = (String) object.get("NATURALNAME");
			rInfo.FID = (String) object.get("ID");
			rInfo.extendattribute = (String) object.get("extendattribute");
			rInfo.dateTime = ((Timestamp) object.get("created")).toString();
			if (((String) object.get("ACTIVE")).toString().equals("1")) {
				rInfo.setACTIVE("有效");
			} else {
				rInfo.setACTIVE("无效");
			}
			aList.add(rInfo);
		}
		for (int j = 0; j < aList.size(); j++) {
			System.out.println(((ResourceInfo) aList.get(j)).getFID());
		}

		return aList;
	}

	public static List selectstylelv() throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		String sqlString = "select * from netone.ums_protectedobject  where NATURALNAME like'%CSSFILE.CSSFILE.%'";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.rname = (String) object.get("NAME");
			rInfo.objecttype = (String) object.get("objectType");
			rInfo.rcode = (String) object.get("NATURALNAME");
			rInfo.FID = (String) object.get("ID");
			rInfo.extendattribute = (String) object.get("extendattribute");
			rInfo.dateTime = ((Timestamp) object.get("created")).toString();
			if (((String) object.get("ACTIVE")).toString().equals("1")) {
				rInfo.setACTIVE("有效");
			} else {
				rInfo.setACTIVE("无效");
			}
			aList.add(rInfo);
		}
		for (int j = 0; j < aList.size(); j++) {
			System.out.println(((ResourceInfo) aList.get(j)).getRname());
		}

		return aList;
	}

	public static List getChirdrenForm(String natrname)
			throws MalformedURLException, RemoteException, NotBoundException {
		List arrlist = new ArrayList();
		String paramNa = natrname;// 参数中传递过来表单的 naturalname
		// 查询 该naturalname 对应的 数据仓库层次信息的 naturalname
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setDescription(paramNa);
		List list = rsrmi.queryObjectProtectedObj(upo, null, 0, 10, "");
		if (list != null && list.size() > 0) {
			UmsProtectedobject upox = (UmsProtectedobject) list.get(0);
			// 根据定位到的层次信息，获取下级的钻取资源

			List listx = rsrmi.subResource(upox.getId());
			for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				// 获得钻取得表单资源名
				String dyform_naturalname = object.getDescription();
				// 从表单资源名获得表名
				String tablename = StringUtils.substringAfterLast(
						dyform_naturalname, ".");
				String formcode = object.getExtendattribute();
				ZqApplicatioe za = new ZqApplicatioe();
				za.setCformname(tablename);
				za.setExtendattrith(formcode);
				System.out.println(tablename);
				arrlist.add(za);

			}
		}

		// for (int i = 0; i < arrlist.size(); i++) {
		//			
		// }
		return arrlist;
	}

	public static void main(String[] args) throws Exception {
		// getChirdrenForm("BIAPPLICATION.BIAPPLICATION");
		// getData("column5","DY_631323852044811");
		getkvdata("column4", "505fa78e262f11e1ac4845aeec2e338d_");
		// column3505fa78e262f11e1ac4845aeec2e338d_
		// allColumn("83253c39e76a11e094df8d7a79faa8aa_");
		// gettablename("e926fdb4f3ed11e08f3ea5c186418503_");
		// getChirdrenForm("BUSSFORM.BUSSFORM");
		// selectBilv();
		// List ist = allColumn("849fcdd3df5e11e0a4d69b65aec28f19_");
		// getzhibiao("column3", null, "新飞飞", "DY_271316065779014", "请选择维度值");
	}
}
