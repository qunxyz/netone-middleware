package oe.cav.bean.logic.dy.bus;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.BussObj;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.dy.bus.reference.BussDaoReference;
import oe.cav.bean.logic.tools.SQLTools;
import oe.cms.service.CmsService;
import oe.frame.orm.QueryInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��̬���е� SQL��ѯ���ֵ�������
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class BussDaoCoreTools {

	static Log log = LogFactory.getLog(BussDaoCoreTools.class);

	/**
	 * ����SQL����������������Ŀǰ֧�� Mysql��Oracle
	 * 
	 * @param sql
	 * @param db
	 * @param from
	 * @param to
	 * @return
	 */
	public static String reBuildSql(String sql, String db, int from, int to) {
		if (from == -1 || to == -1) {
			return sql;
		}
		if ("MYSQL".equalsIgnoreCase(db)) {
			sql = sql + " limit " + from + "," + to;
		} else if ("ORACLE".equalsIgnoreCase(db)) {
			sql = sql + " rows>= " + from + " and rows<" + to;
		}
		return sql;
	}

	/**
	 * ���ú���JDBC����ö�̬SQL������
	 * 
	 * @param finalSQL
	 * @param valuelist
	 * @param obj
	 * @param from
	 * @param to
	 * @return
	 */
	public static List fetchData(String finalSQL, List valuelist, BussObj obj,
			int from, int to) {
		List list = new ArrayList();
		Connection conn = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = SQLTools.getConn(null);
			finalSQL = reBuildSql(finalSQL, conn.getMetaData()
					.getDatabaseProductName(), from, to);
			ps = conn.prepareStatement(finalSQL);
			int i = 1;
			// ���ò�����Ӧ��ֵ
			for (Iterator itr = valuelist.iterator(); itr.hasNext();) {
				Object value = itr.next();
				ps.setObject(i, value);
				i++;
			}
			// ִ�в�ѯ
			rs = ps.executeQuery();
			Map columnidvalue = obj.getColumnIdValue();
			Map columnidtype = obj.getColumnIdType();
			while (rs.next()) {
				Map columnidvaluemap = new HashMap();
				Map columnidtypemap = new HashMap();
				for (Iterator iter = columnidvalue.keySet().iterator(); iter
						.hasNext();) {
					String columnid = (String) iter.next();
					String columntype = columnidtype.get(columnid).toString();
					columnidvaluemap.put(columnid, rs.getObject(columnid));
					columnidtypemap.put(columnid, columntype);
				}
				// ��װBussObj����
				BussObj bo = new BussObj();
				bo.setSystemid(obj.getSystemid());
				bo.setFormcode(obj.getFormcode());
				bo.setColumnIdValue(columnidvaluemap);
				bo.setColumnIdType(columnidtypemap);
				bo.setPrimarkeys(obj.getPrimarkeys());
				bo.setExtendattribute(obj.getExtendattribute());
				list.add(bo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * ���ú���JDBC����ö�̬SQL����������
	 * 
	 * @param finalSQL
	 * @param valuelist
	 * @param obj
	 * @return
	 */
	public static long fetchDataNum(String finalSQL, List valuelist, BussObj obj) {

		Connection conn = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = SQLTools.getConn(null);
			ps = conn.prepareStatement(finalSQL);
			int i = 1;
			// ���ò�����Ӧ��ֵ
			for (Iterator itr = valuelist.iterator(); itr.hasNext();) {
				Object value = itr.next();
				ps.setObject(i, value);
				i++;
			}
			// ִ�в�ѯ
			rs = ps.executeQuery();
			return rs.getLong("total");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * ���춯̬SQL��ѯ���
	 * 
	 * @param obj
	 *            ҵ�����
	 * @param conditionPre
	 *            ��������
	 * @param isCount
	 *            �Ƿ�Ϊͳ��
	 * @return
	 */
	public static List sqlAndParam(BussObj obj, String conditionPre,
			boolean isCount) {

		List valuelist = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Map columnidvalue = obj.getColumnIdValue();
		Map extendattribute = obj.getExtendattribute();
		QueryInfo qi = null;
		if (extendattribute != null) {
			qi = (QueryInfo) extendattribute.get("QUERYINFO");
		}
		for (Iterator iter = columnidvalue.keySet().iterator(); iter.hasNext();) {
			String columnid = (String) iter.next();
			Object columnvalue = columnidvalue.get(columnid);
			if (columnid.equals("extendattribute")
					|| columnid.equals("EXTENDATTRIBUTE")) {
				continue;
			}
			// �����ֶβ�������������,������ܿ���ֻ�ڹ�������ʹ�õ�
			if (columnid.indexOf(BussDaoReference._EXT) != -1) {
				continue;
			}
			// ������ѯ������,��ֵ����valuelist
			if (columnvalue != null && !columnvalue.equals("")) {
				String paramStr = BussDaoReference.buildConditionColumn(
						columnid, qi);
				sb.append(paramStr);
				if (paramStr.indexOf(QueryInfo._LIKE) > 0) {
					valuelist.add(BussDaoReference._LIKELEFT + columnvalue
							+ BussDaoReference._LIKERIGHT);
				} else {
					valuelist.add(columnvalue);
				}
			}
		}
		String condition = BussDaoReference._WHERE
				+ BussDaoReference._PRE_CONDITION;

		conditionPre = conditionPre == null ? "" : " " + conditionPre;

		String headQuery = null;
		if (isCount) {
			headQuery = BussDaoReference._SELECTCOUNT;
		} else {
			headQuery = BussDaoReference._SELECT;
		}
		// ��ѯ�������
		String finalSQL = headQuery + BussDaoReference._FROM
				+ obj.getFormcode() + condition + sb + conditionPre;

		log.info(finalSQL);

		List retInfo = new ArrayList();
		retInfo.add(finalSQL);
		retInfo.addAll(valuelist);
		return retInfo;

	}

	public static void addToResourceFck(TCsBus bus, List columnlist,
			String tablename, String formcode) {
		List listFck = new ArrayList();
		for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
			TCsColumn element = (TCsColumn) iter.next();
			if (ColumnExtendInfo._HTML_TYPE_FCK_ITEM.equals(element
					.getHtmltype())) {
				listFck.add(element);
			}
		}
		if (listFck.size() == 0) {
			// û��Portal��
			return;
		}

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setName(bus.getLsh());
		upo.setNaturalname(bus.getLsh());
		upo.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGEGROUP);
		upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// �Ƚ��ñ���¼��Ϊһ��ҳ���������Դ  
			String parentName = "FCK.FCK.DYFORM." + tablename;
			rsrmi.addResource(upo, parentName);

			// �����ֶ�����,��������Portal�ֶ�,��ô�Զ���ҳ��Դ��ȥע���µ�ҳ

			String parentNameItem = parentName + "."
					+ bus.getLsh().toUpperCase();
			StringBuffer but = new StringBuffer();
			for (Iterator iter = listFck.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();

				String naturalname = element.getColumnid();
				String name = element.getColumname();
				// pageinfo�б�����ҳ�����Ĵ���������Ϣ,�����ʽΪname:value;name:value
				String pageinfo = element.getValuelist();
				// ��ȡwidth,height, ���и������Ϣ�Ժ���Լ�����չ��ȥ
				String width = StringUtils.substringBetween(pageinfo, "width:",
						";");
				if (width == null || width.equals("")) {
					width = "200";
				}
				String height = StringUtils.substringBetween(pageinfo,
						"height:", ";");
				if (height == null || height.equals("")) {
					height = "200";
				}

				UmsProtectedobject upoFck = new UmsProtectedobject();
				upoFck.setName(element.getColumnid().toUpperCase());
				upoFck.setNaturalname(element.getColumnid().toUpperCase());
				upoFck
						.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGEGROUP);
				upoFck
						.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
				Serializable fckid = rsrmi.addResource(upoFck, parentNameItem);

				but.append(";" + fckid+":"+element.getColumnid());

			}

			// ���ü�¼�е�����ҳ����Ϣ������ü�¼����չ������
			if (but.length() > 0) {
				String ext = bus.getExtendattribute();
				bus.setExtendattribute(ext + "dyfck[" + but.substring(1) + "]");
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
	}

	public static void addToResourcePortal(TCsBus bus, List columnlist,
			String tablename, String formcode) {
		List listPortal = new ArrayList();
		for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
			TCsColumn element = (TCsColumn) iter.next();
			if (ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM.equals(element
					.getHtmltype())) {
				listPortal.add(element);
			}
		}
		if (listPortal.size() == 0) {
			// û��Portal��
			return;
		}

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setName(bus.getLsh());
		upo.setNaturalname(bus.getLsh());
		upo.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGEGROUP);
		upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// �Ƚ��ñ���¼��Ϊһ��ҳ���������Դ
			String parentName = "PORTALPG.PORTALPG.DYFORM." + tablename;
			rsrmi.addResource(upo, parentName);

			// �����ֶ�����,��������Portal�ֶ�,��ô�Զ���ҳ��Դ��ȥע���µ�ҳ
			CmsService serv = (CmsService) RmiEntry.iv("cmshandle");
			String parentNameItem = parentName + "."
					+ bus.getLsh().toUpperCase();
			StringBuffer but = new StringBuffer();
			for (Iterator iter = listPortal.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();

				String naturalname = element.getColumnid();
				String name = element.getColumname();
				// pageinfo�б�����ҳ�����Ĵ���������Ϣ,�����ʽΪname:value;name:value
				String pageinfo = element.getValuelist();
				// ��ȡwidth,height, ���и������Ϣ�Ժ���Լ�����չ��ȥ
				String width = StringUtils.substringBetween(pageinfo, "width:",
						";");
				if (width == null || width.equals("")) {
					width = "200";
				}
				String height = StringUtils.substringBetween(pageinfo,
						"height:", ";");
				if (height == null || height.equals("")) {
					height = "200";
				}
				
				String mode = StringUtils.substringBetween(pageinfo,
						"mode:", ";");
				if (mode == null || mode.equals("")) {
					mode = "1";
				}
				String cellid = serv.addpage(parentNameItem, name, naturalname,
						bus.getParticipant(), width, height);
				String naturalnamePage = parentNameItem + "."
						+ element.getColumnid().toUpperCase();
				but.append(";" + cellid + ":" + naturalnamePage);

			}

			// ���ü�¼�е�����ҳ����Ϣ������ü�¼����չ������
			if (but.length() > 0) {
				String ext = bus.getExtendattribute();
				bus.setExtendattribute(ext + "dyportal[" + but.substring(1) + "]");
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
	}

}
