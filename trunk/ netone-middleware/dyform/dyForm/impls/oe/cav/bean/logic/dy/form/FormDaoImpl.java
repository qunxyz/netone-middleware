package oe.cav.bean.logic.dy.form;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.ColumnDao;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.FormExtendInfo;
import oe.cav.bean.logic.form.TCsForm;

import oe.cav.bean.logic.tools.DbScriptTools;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.DyObjFromDatabase;
import oe.cav.bean.logic.tools.DyObjFromXml;
import oe.cav.bean.logic.tools.DyObjToXML;
import oe.cav.bean.logic.tools.XmlPools;
import oe.cav.bean.logic.tools.reference.DyReference;
import oe.cav.bean.logic.tools.reference.XMLReference;
import oe.cav.bean.logic.tools.reference.XmlWriter;
import oe.frame.orm.util.IdServer;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FormDaoImpl implements FormDao {

	private DyObjToXML dyObjToXML;

	private DyObjFromXml dyObjFromXml;

	private DyObjFromDatabase dyObjFromDatabase;

	static Log log = LogFactory.getLog(FormDaoImpl.class);

	public boolean create(TCsForm form, String belongname) {
		if (form == null || form.equals("")) {
			return false;
		} else {

			String systemid = form.getSystemid();
			if (systemid == null) {
				systemid = "";
			}
			// Db Sys
			String[] info = null;
			if(StringUtils.isEmpty(form.getSqlinfo())||"*".equals(form.getSqlinfo())){
				info=DbScriptTools.create(systemid);
			}else{
				info=DbScriptTools.createV(systemid,form.getSqlinfo(),form.getTablename());
			}
			form.setDescription(info[0]);
			form.setDesigner(info[1]);
			if (form.getTypeinfo() == null) {
				form.setTypeinfo("BUSSENV.BUSSENV.DYSER.DYFORM");// 表明是默认是动态表单创建
			}
			// xml文档操作
			form.setFormcode(IdServer.uuid() + "_");
			String formname = form.getFormname();
			String[] table = { info[0] };

			DyObj[] dyobx = dyObjFromDatabase.parser(systemid, table);

			dyobx[0].getFrom().setSqlinfo(form.getSqlinfo());
			dyobx[0].getFrom().setTimelevel(form.getTimelevel());
			dyobx[0].getFrom().setDimlevel(form.getDimlevel());
			dyobx[0].getFrom().setStyleinfo(form.getStyleinfo());
			dyobx[0].getFrom().setSubform(form.getSubform());
			List columnlist = dyobx[0].getColumn();
			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn column = (TCsColumn) iter.next();
				String columnname = column.getColumname();
				columnname=columnname.toUpperCase();
				if (XMLReference.DIMETION_BELONGX.equals(columnname)) {
					column.setUseable(true);
					column.setHtmltype(ColumnExtendInfo._HTML_TYPE_TREE);
					column.setViewtype(ColumnExtendInfo._TYPE_TREE);

					column.setColumname(StringUtils.substringBefore(form
							.getDimdata(), "["));

					column.setColumncode(column.getColumncode().toLowerCase());
					column.setColumnid(column.getColumnid().toLowerCase());
					column.setValuelist(form.getDimdata());

				} else if (XMLReference.DIMETION_TIMEX.equals(columnname)) {
					column.setUseable(true);

					if ("1d".equals(form.getTimelevel())
							|| "1m".equals(form.getTimelevel())
							|| "1y".equals(form.getTimelevel())) {
						column.setViewtype(ColumnExtendInfo._TYPE_DATE);
						column.setHtmltype(ColumnExtendInfo._HTML_TYPE_DATE);
					} else if ("1h".equals(form.getTimelevel())) {
						column.setViewtype(ColumnExtendInfo._TYPE_DATETIME);
						column
								.setHtmltype(ColumnExtendInfo._HTML_TYPE_DATETIME);
					}

					column.setColumname("日期");
					column.setColumncode(column.getColumncode().toLowerCase());
					column.setColumnid(column.getColumnid().toLowerCase());

				} else {
					column.setUseable(false);
				}
				column.setColumnid(columnname);
			}
			if (dyobx == null || dyobx.length <= 0) {
				return false;
			} else {
				TCsForm tcf = dyobx[0].getFrom();
				tcf.setFormname(formname);
				tcf.setFormcode(form.getFormcode());
				tcf.setTypeinfo(DyReference.DY);

				XmlWriter.write(dyObjToXML.parser(dyobx[0]), XmlPools
						.writedPath(form.getFormcode()));

				// 注册入资源
				ResourceRmi rsrmi = null;
				try {
					// 读取名为resource的rmi服务
					rsrmi = (ResourceRmi) RmiEntry.iv("resource");
					UmsProtectedobject upo = new UmsProtectedobject();
					upo.setNaturalname(tcf.getDescription());
					upo.setName(tcf.getFormname());
					upo.setActionurl("");
					upo.setExtendattribute(tcf.getFormcode());
					upo.setObjecttype("DYFROM");
					if (belongname == null || belongname.equals("")) {
						belongname = "BUSSFORM.BUSSFORM";
					}
					rsrmi.addResource(upo, belongname);
					
					String [][]ope_role={{"add","新增"},{"modi","修改"},{"conf","确认"},{"uconf","反确认"},{"que","查询"}};
					for (int i = 0; i < ope_role.length; i++) {
						UmsProtectedobject upo1 = new UmsProtectedobject();
						upo1.setNaturalname(ope_role[i][0]);
						upo1.setName(ope_role[i][1]);
						upo1.setActionurl("");
						upo1.setObjecttype("DYFROM");
						rsrmi.addResource(upo1, belongname+"."+tcf.getDescription());
					}

//					// 在注册一个到页中,用于管理所有的表单记录
//					UmsProtectedobject upo1 = new UmsProtectedobject();
//					upo1.setNaturalname(tcf.getDescription());
//					upo1.setName(tcf.getFormname());
//					upo1.setActionurl("");
//					upo1.setInclusion("1");// 是目录
//					upo1.setExtendattribute(tcf.getFormcode());
//					upo1.setObjecttype("DYFROM");
//					rsrmi.addResource(upo1, "PORTALPG.PORTALPG.DYFORM");
//
//					// 在注册一个到多彩文档中,用于管理所有的表单记录
//					UmsProtectedobject upo2 = new UmsProtectedobject();
//					upo2.setNaturalname(tcf.getDescription());
//					upo2.setName(tcf.getFormname());
//					upo2.setActionurl("");
//					upo2.setInclusion("1");// 是目录
//					upo2.setExtendattribute(tcf.getFormcode());
//					upo2.setObjecttype("DYFROM");
//					rsrmi.addResource(upo2, "FCK.FCK.DYFORM");

				} catch (NotBoundException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return true;
			}
		}
	}

	public boolean update(TCsForm form) {
		if (form == null || form.equals("")) {
			return false;
		} else {
			// 数据库操作
			String systemid = form.getSystemid();
			// OrmerEntry.fetchOrmer(systemid).fetchSerializer().update(form);
			// xml文档操作
			String formcode = form.getFormcode();

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			dfo.setSystemid(systemid);
			dfo.setFrom(form);

			List list = dfo.getColumn();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();
				if (element.getColumncode().equalsIgnoreCase("belongx")) {
					element.setColumname(StringUtils.substringBefore(form
							.getDimdata(), "["));
					element
							.setColumncode(element.getColumncode()
									.toLowerCase());
					element.setColumnid(element.getColumnid().toLowerCase());
					element.setValuelist(form.getDimdata());
				}

			}

			XmlWriter.write(dyObjToXML.parser(dfo), XmlPools
					.writedPath(formcode));
			return true;
		}
	}

	public boolean drop(TCsForm form) {
		if (form == null || form.equals("")) {
			return false;
		} else {
			// 数据库操作
			form.setStatusinfo(FormExtendInfo._STATUS_LOSE);
			// OrmerEntry.fetchOrmer(systemid).fetchSerializer().update(form);
			// xml文档操作
			String formcode = form.getFormcode();
			String path = System.getProperty("user.dir") + "/dy/";
			File file = new File(path + formcode + ".xml");
			if (file.exists()) {
				File delFile = new File(path + formcode + ".xml$Del");
				return file.renameTo(delFile);

			} else {
				return false;
			}
		}
	}

	public List fetchColumnList(String formcode) {
		ColumnDao columnDao = (ColumnDao) FormEntry.fetchBean("columnDao");
		TCsColumn column = new TCsColumn();
		column.setFormcode(formcode);
		return columnDao.queryObjects(column);
	}

	public Map fetchTitleInfos(String formcode) {

		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		TCsForm form = dfo.getFrom();
		String formKeyName = form.getListinfo();
		if (formKeyName == null || formKeyName.equals("")) {
			formKeyName = "1,2,3,4,5,6,7,8";
		}
		formKeyName = formKeyName.replaceAll(",n", "");
		List columnlist = dfo.getColumn();
		Map columntitle = new HashMap();
		for (Iterator itr = columnlist.iterator(); itr.hasNext();) {
			TCsColumn columnPre = (TCsColumn) itr.next();
			if (!columnPre.isUseable()) {
				continue;
			}
			if (ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM.equals(columnPre
					.getHtmltype())) {
				continue;
			}
			if (ColumnExtendInfo._HTML_TYPE_FCK_ITEM.equals(columnPre
					.getHtmltype())) {
				continue;
			}
			String[] columnInfo = { columnPre.getColumncode(),
					columnPre.getColumname() };
			columntitle.put(columnPre.getIndexvalue().toString(), columnInfo);
		}
		columntitle.put("0", form.getListinfo());

		return columntitle;
	}

	public TCsForm loadObject(String key) {
		if (key == null || key.equals("")) {
			return null;
		}
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(key).toString());
		return dfo.getFrom();

	}

	public List queryObjects(TCsForm obj) {
		String pathinfo = System.getProperty("user.dir") + "/dy/";
		String systemid = obj.getSystemid();
		File fi = new File(pathinfo);

		fi.getPath();
		FileFilter filter = new FileFilter(".xml");
		String[] list = fi.list(filter);

		List listObj = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File file = new File(pathinfo + list[i]);
				URL url = null;
				try {
					url = file.toURL();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("对象中的参数无效");
				}

				DyObj dfo = dyObjFromXml.parser(url.toString());
				TCsForm form = dfo.getFrom();
				form.setCreated(new Timestamp(file.lastModified()).toString());
				listObj.add(form);
			}
		}
		return listObj;
	}

	public List queryObjects(TCsForm obj, String conditionPre) {
		return queryObjects(obj);
	}

	public List queryObjects(TCsForm obj, int from, int to) {
		return queryObjects(obj, from, to, "");
	}

	public List queryObjects(TCsForm obj, int from, int to, String conditionPre) {
		if (obj == null || obj.equals("")) {
			return new ArrayList();
		} else {
			if (from > to || from < 0 || to < 0) {
				return new ArrayList();
			} else {
				List list = queryObjects(obj, conditionPre);
				if (list != null && list.size() >= from) {
					if (list.size() >= to) {
						return list.subList(from, to);
					} else {
						return list.subList(from, list.size());
					}
				} else {

					return new ArrayList();
				}
			}
		}
	}

	public long queryObjectsNumber(TCsForm obj) {
		return queryObjects(obj).size();
	}

	public long queryObjectsNumber(TCsForm obj, String conditionPre) {
		return queryObjects(obj).size();
	}

	public DyObjFromDatabase getDyObjFromDatabase() {
		return dyObjFromDatabase;
	}

	public void setDyObjFromDatabase(DyObjFromDatabase dyObjFromDatabase) {
		this.dyObjFromDatabase = dyObjFromDatabase;
	}

	public DyObjFromXml getDyObjFromXml() {
		return dyObjFromXml;
	}

	public void setDyObjFromXml(DyObjFromXml dyObjFromXml) {
		this.dyObjFromXml = dyObjFromXml;
	}

	public DyObjToXML getDyObjToXML() {
		return dyObjToXML;
	}

	public void setDyObjToXML(DyObjToXML dyObjToXML) {
		this.dyObjToXML = dyObjToXML;
	}

	public TCsForm loadObjectUrl(String key) {
		if (key == null || key.equals("")) {
			return null;
		}
		DyObj dfo = dyObjFromXml.parser(key);
		return dfo.getFrom();
	}

	public List fetchColumnListUrl(String url) {
		ColumnDao columnDao = (ColumnDao) FormEntry.fetchBean("columnDao");
		return columnDao.queryObjectsUrl(url);
	}

	public List listByLevel(String level) {
		log.debug("level to match:" + level);
		level = StringUtils.substringBetween(level, "[", "]");
		if (level == null) {
			return new ArrayList();
		}

		String pathinfo = System.getProperty("user.dir") + "/dy/";
		File fi = new File(pathinfo);

		fi.getPath();
		FileFilter filter = new FileFilter(".xml");
		String[] list = fi.list(filter);

		List listObj = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File file = new File(pathinfo + list[i]);
				log.debug(pathinfo + list[i]);
				URL url = null;
				try {
					url = file.toURL();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("对象中的参数无效");
				}
				try {
					DyObj dfo = dyObjFromXml.parser(url.toString());
					TCsForm form = dfo.getFrom();
					log.debug("   " + level + "," + form.getDimlevel());
					String formlevel = StringUtils.substringBetween(form
							.getDimlevel(), "[", "]");
					if (level.equals(formlevel)) {
						form.setCreated(new Timestamp(file.lastModified())
								.toString());
						listObj.add(form);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return listObj;
	}

	public String formDescription(String formcode) throws RemoteException {

		SAXReader reader = new SAXReader();
		try {
			Document xml = reader.read(XmlPools.fetchXML(formcode).toString());
			return xml.asXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

}
