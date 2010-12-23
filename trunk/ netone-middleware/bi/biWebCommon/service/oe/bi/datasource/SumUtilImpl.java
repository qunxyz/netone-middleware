package oe.bi.datasource;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oe.bi.wizard.ExcelToDb.ExcelToDbDao;
import oe.bi.wizard.ExcelToDb.ExcelToDbDaoImpl;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.reference.XMLReference;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 工具类集合
 * 
 */
public class SumUtilImpl extends UnicastRemoteObject implements SumUtilIfc {
	public SumUtilImpl() throws RemoteException {
		super();
	}

	public static final String _COLUMNX = ",LSH,FORMCODE,PARTICIPANT,FATHERLSH,STATUSINFO,EXTENDATTRIBUTE,HIT,CREATED,";

	/**
	 * 检查字段
	 */
	public String checkColumnlist(List list) {
		String returnRs = "";
		boolean belongxHave = false;
		boolean createHave = false;
		boolean idHave = false;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			String columname = ","
					+ ((String) element.get("name")).toUpperCase() + ",";
			if (("," + XMLReference.DIMETION_BELONGX + ",").equals(columname)) {
				belongxHave = true;
			} else if (("," + XMLReference.DIMETION_TIMEX + ",")
					.equals(columname)) {
				createHave = true;
			} else if (_COLUMNX.contains(columname)) {
				returnRs = "包含非法别名:" + columname;
			}
		}

		if (!belongxHave) {
			returnRs += " 缺少BELONGX别名";
		}
		if (!createHave) {
			returnRs += " 缺少TIMEX别名";
		}
		return returnRs;
	}

	/**
	 * 删除表记录
	 * 
	 * @param tablename
	 */
	public void deleteTable(String tablename) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = OrmerEntry.fetchDS().getConnection();
			StringBuffer sb = new StringBuffer("delete from " + tablename);
			ps = conn.prepareStatement(sb.toString());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行同步SQL语句(全部添加)
	 */
	public String searchToAdd(String sql, String cname, String tablename,
			String formcode) {
		Connection conRead = null;
		Connection conWrited = null;
		PreparedStatement psWrited = null;
		ResultSet rs = null;
		PreparedStatement psRead = null;
		String[] cnames = StringUtils.split(cname, ",");
		StringBuffer sb = new StringBuffer(
				"Insert into "
						+ tablename
						+ "(LSH,FORMCODE,PARTICIPANT,EXTENDATTRIBUTE,CREATED,FATHERLSH,STATUSINFO");

		int timexindex = -1;
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + cnames[i]);
			if ("timex".equalsIgnoreCase(cnames[i])) {
				timexindex = i + 8; // 7 是从LSH到 STATUSINFO的位置
			}
		}
		sb.append(")values(?,?,?,?,?,?,?");
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + "?");
		}
		sb.append(")");
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm form = dfd.loadForm(formcode);
			String systemid = form.getSystemid();

			conRead = getConn(systemid);
			conWrited = OrmerEntry.fetchDS().getConnection();
			psWrited = conWrited.prepareStatement(sb.toString());
			psRead = conRead
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = psRead.executeQuery();
			int j = 0;

			final String fatherlsh = "1";
			final String statusinfo = "00";
			final String participant = "dyform";

			while (rs.next()) {
				psWrited.setString(1, IdServer.uuid());
				psWrited.setString(2, formcode);
				psWrited.setString(3, participant);
				psWrited.setString(4, "");
				psWrited.setString(5, new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
				psWrited.setString(6, fatherlsh);
				psWrited.setString(7, statusinfo);
				for (int i = 0; i < cnames.length; i++) {
					Object obj = rs.getObject(cnames[i]);
					if (i + 8 == timexindex) {// 说明是timex字段
						if (obj == null) {
							obj = new Date();
						}
					}
					psWrited.setObject(i + 8, obj);
				}
				j++;
				psWrited.addBatch();
				if (j % 100 == 0) {
					psWrited.executeBatch();
				}
			}
			psWrited.executeBatch();
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psWrited != null) {
					psWrited.close();
				}
				if (psRead != null) {
					psRead.close();
				}
				if (conRead != null) {
					conRead.close();
				}
				if (conWrited != null) {
					conWrited.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}
	
	
	

	/**
	 * 执行同步SQL语句(根据时间)
	 */
	public String searchToAddByTime(String sql, String cname, String tablename,
			String formcode, String limittime) {
		Connection conRead = null;
		Connection conWrited = null;
		PreparedStatement psWrited = null;
		ResultSet rs = null;
		PreparedStatement psRead = null;
		String[] cnames = StringUtils.split(cname, ",");
		StringBuffer sb = new StringBuffer(
				"Insert into "
						+ tablename
						+ "(LSH,FORMCODE,PARTICIPANT,EXTENDATTRIBUTE,CREATED,FATHERLSH,STATUSINFO");

		int timexindex = -1;
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + cnames[i]);
			if ("timex".equalsIgnoreCase(cnames[i])) {
				timexindex = i + 8; // 7 是从LSH到 STATUSINFO的位置
			}
		}
		sb.append(")values(?,?,?,?,?,?,?");
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + "?");
		}
		sb.append(")");
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm form = dfd.loadForm(formcode);
			String systemid = form.getSystemid();

			conRead = getConn(systemid);
			conWrited = OrmerEntry.fetchDS().getConnection();
			psWrited = conWrited.prepareStatement(sb.toString());
			psRead = conRead
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = psRead.executeQuery();
			int j = 0;

			final String fatherlsh = "1";
			final String statusinfo = "00";
			final String participant = "dyform";

			int poor = 0;
			while (rs.next()) {
				psWrited.setString(1, IdServer.uuid());
				psWrited.setString(2, formcode);
				psWrited.setString(3, participant);
				psWrited.setString(4, "");
				psWrited.setString(5, new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
				psWrited.setString(6, fatherlsh);
				psWrited.setString(7, statusinfo);
				boolean b = true;
				for (int i = 0; i < cnames.length; i++) {
					Object obj = rs.getObject(cnames[i]);
					if (i + 8 == timexindex) {// 说明是timex字段
						if (obj == null) {
							obj = new Date();
						}
						if (StringUtils.isNotEmpty(limittime)) {
							if (obj.toString().compareTo(limittime) < 0) {
								b = false;
							}
						}
					}
					psWrited.setObject(i + 8, obj);
				}
				if (b) {
					j++;
					psWrited.addBatch();
					if (j % 100 == 0) {
						psWrited.executeBatch();
					}
				}
			}
			psWrited.executeBatch();
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psWrited != null) {
					psWrited.close();
				}
				if (psRead != null) {
					psRead.close();
				}
				if (conRead != null) {
					conRead.close();
				}
				if (conWrited != null) {
					conWrited.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}

	/**
	 * 执行同步SQL语句(根据后N条)
	 */
	public String searchToAddByCount(String sql, String cname,
			String tablename, String formcode, String limitcount) {
		Connection conRead = null;
		Connection conWrited = null;
		PreparedStatement psWrited = null;
		ResultSet rs = null;
		PreparedStatement psRead = null;
		String[] cnames = StringUtils.split(cname, ",");
		StringBuffer sb = new StringBuffer(
				"Insert into "
						+ tablename
						+ "(LSH,FORMCODE,PARTICIPANT,EXTENDATTRIBUTE,CREATED,FATHERLSH,STATUSINFO");

		int timexindex = -1;
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + cnames[i]);
			if ("timex".equalsIgnoreCase(cnames[i])) {
				timexindex = i + 8; // 7 是从LSH到 STATUSINFO的位置
			}
		}
		sb.append(")values(?,?,?,?,?,?,?");
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + "?");
		}
		sb.append(")");
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm form = dfd.loadForm(formcode);
			String systemid = form.getSystemid();

			conRead = getConn(systemid);
			conWrited = OrmerEntry.fetchDS().getConnection();
			psWrited = conWrited.prepareStatement(sb.toString());
			psRead = conRead
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = psRead.executeQuery();
			int j = 0;

			final String fatherlsh = "1";
			final String statusinfo = "00";
			final String participant = "dyform";

			int poor = 0;
			if (rs.last()) {
				int count = rs.getRow();
				int limit = Integer.valueOf(limitcount);
				if (count >= limit) {
					poor = count - limit;
				}
				rs.beforeFirst();
				for (int i = 0; i < poor; i++) {
					rs.next();
				}
			}
			while (rs.next()) {
				psWrited.setString(1, IdServer.uuid());
				psWrited.setString(2, formcode);
				psWrited.setString(3, participant);
				psWrited.setString(4, "");
				psWrited.setString(5, new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
				psWrited.setString(6, fatherlsh);
				psWrited.setString(7, statusinfo);
				for (int i = 0; i < cnames.length; i++) {
					Object obj = rs.getObject(cnames[i]);
					if (i + 8 == timexindex) {// 说明是timex字段
						if (obj == null) {
							obj = new Date();
						}
					}
					psWrited.setObject(i + 8, obj);
				}
				j++;
				psWrited.addBatch();
				if (j % 100 == 0) {
					psWrited.executeBatch();
				}
			}
			psWrited.executeBatch();
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psWrited != null) {
					psWrited.close();
				}
				if (psRead != null) {
					psRead.close();
				}
				if (conRead != null) {
					conRead.close();
				}
				if (conWrited != null) {
					conWrited.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}

	/**
	 * 执行同步SQL语句(根据手工选择)
	 */
	public String searchToAddByNumber(String sql, String cname,
			String tablename, String formcode, String limitnumber) {
		Connection conRead = null;
		Connection conWrited = null;
		PreparedStatement psWrited = null;
		ResultSet rs = null;
		PreparedStatement psRead = null;
		String[] cnames = StringUtils.split(cname, ",");
		StringBuffer sb = new StringBuffer(
				"Insert into "
						+ tablename
						+ "(LSH,FORMCODE,PARTICIPANT,EXTENDATTRIBUTE,CREATED,FATHERLSH,STATUSINFO");

		int timexindex = -1;
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + cnames[i]);
			if ("timex".equalsIgnoreCase(cnames[i])) {
				timexindex = i + 8; // 7 是从LSH到 STATUSINFO的位置
			}
		}
		sb.append(")values(?,?,?,?,?,?,?");
		for (int i = 0; i < cnames.length; i++) {
			sb.append("," + "?");
		}
		sb.append(")");
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm form = dfd.loadForm(formcode);
			String systemid = form.getSystemid();

			conRead = getConn(systemid);
			conWrited = OrmerEntry.fetchDS().getConnection();
			psWrited = conWrited.prepareStatement(sb.toString());
			psRead = conRead
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = psRead.executeQuery();
			int j = 0;

			final String fatherlsh = "1";
			final String statusinfo = "00";
			final String participant = "dyform";

			if (StringUtils.isNotEmpty(limitnumber)) {
				String[] checkmap = StringUtils.split(limitnumber, ",");
				while (rs.next()) {
					for (int k = 0; k < checkmap.length; k++) {
						if (rs.getRow() == Integer.valueOf(checkmap[k])) {
							psWrited.setString(1, IdServer.uuid());
							psWrited.setString(2, formcode);
							psWrited.setString(3, participant);
							psWrited.setString(4, "");
							psWrited.setString(5, new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss").format(new Date())
									.toString());
							psWrited.setString(6, fatherlsh);
							psWrited.setString(7, statusinfo);
							for (int i = 0; i < cnames.length; i++) {
								Object obj = rs.getObject(cnames[i]);
								if (i + 8 == timexindex) {// 说明是timex字段
									if (obj == null) {
										obj = new Date();
									}
								}
								psWrited.setObject(i + 8, obj);
							}
							j++;
							psWrited.addBatch();
							if (j % 100 == 0) {
								psWrited.executeBatch();
							}
						}
					}
				}
			}
			psWrited.executeBatch();
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psWrited != null) {
					psWrited.close();
				}
				if (psRead != null) {
					psRead.close();
				}
				if (conRead != null) {
					conRead.close();
				}
				if (conWrited != null) {
					conWrited.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}

	/**
	 * 获取数据库连接
	 */
	private Connection getConn(String driver, String url, String username,
			String password) {
		Connection conn = null;
		try {
			Class.forName(StringUtils.trim(driver));
			conn = DriverManager.getConnection(StringUtils.trim(url),
					StringUtils.trim(username), StringUtils.trim(password));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取数据库连接
	 */
	private Connection getConn(String systemid) {

		if (StringUtils.isEmpty(systemid)) {
			try {
				return OrmerEntry.fetchDS().getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Connection conn = null;
		try {
			ResourceRmi rri = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rri.loadResourceByNatural(systemid);
			String ext = upo.getExtendattribute();
			String[] info = StringUtils.split(ext, "#");
			Class.forName(StringUtils.trim(info[0]));
			conn = DriverManager.getConnection(StringUtils.trim(info[1]),
					StringUtils.trim(info[2]), StringUtils.trim(info[3]));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取某个数据库名
	 */
	public String getDatabaseName(String driver, String url) {
		String databaseName = "";
		if (StringUtils.isNotEmpty(url)) {
			url = StringUtils.trim(url);
			if ("com.mysql.jdbc.Driver".equals(driver)) {
				databaseName = StringUtils.substringBefore(StringUtils
						.substringAfterLast(url, "/"), "?");
			} else if ("com.microsoft.jdbc.sqlserver.SQLServerDriver"
					.equals(driver)) {
				databaseName = StringUtils.substringAfterLast(url, "=");
			} else if ("oracle.jdbc.driver.OracleDriver".equals(driver)) {
				databaseName = StringUtils.substringAfterLast(url, ":");
			}
		}
		return databaseName;
	}

	/**
	 * 获取某个数据库所有表名
	 */
	public List getAllTableName(String[] driver, String databaseName) {
		Connection conn = null;
		if (driver.length == 1) {
			conn = this.getConn(driver[0]);
		} else if (driver.length == 4) {
			conn = this.getConn(driver[0], driver[1], driver[2], driver[3]);
		} else {
			return null;
		}
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			rs = conn.getMetaData().getTables(null, "%", "%",
					new String[] { "TABLE" });
			while (rs.next()) {
				list.add(rs.getString("TABLE_NAME"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			list = null;
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获取某个表的字段名和字段类型
	 */
	public List getColumnNameByTableName(String[] driver, String tableName) {
		Connection conn = null;
		if (driver.length == 1) {
			conn = this.getConn(driver[0]);
		} else if (driver.length == 4) {
			conn = this.getConn(driver[0], driver[1], driver[2], driver[3]);
		} else {
			return null;
		}

		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		try {
			rs = conn.getMetaData().getColumns(null, "%", tableName, "%");
			while (rs.next()) {
				Map<String, String> hashMap = new LinkedHashMap<String, String>();
				String columnname = rs.getString("COLUMN_NAME");

				hashMap.put("name", columnname);
				hashMap.put("type", rs.getString("TYPE_NAME"));
				list.add(hashMap);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			list = null;
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 根据sql获取sql查出的字段名和字段类型
	 */
	public Map getColumnNameBySQL(String[] driver, String SQL) {

		Connection conn = null;
		if (driver.length == 1) {
			conn = this.getConn(driver[0]);
		} else if (driver.length == 4) {
			conn = this.getConn(driver[0], driver[1], driver[2], driver[3]);
		} else {
			return null;
		}
		Map map = new HashMap();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		try {
			Statement st = conn.createStatement();
			boolean todo = st.execute(SQL);
			if (!todo) {
				return null;
			}
			ResultSetMetaData rsmeta = st.getResultSet().getMetaData();
			int columnLen = rsmeta.getColumnCount();
			for (int i = 1; i <= columnLen; i++) {
				Map<String, String> hashMap = new LinkedHashMap<String, String>();
				String columnname = rsmeta.getColumnName(i);
				String type = rsmeta.getColumnTypeName(i);
				hashMap.put("name", columnname);
				hashMap.put("type", type);
				list.add(hashMap);
				sb1.append(columnname + ",");
				sb2.append(type + ",");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("column", list);
		map.put("name", sb1.toString());
		map.put("types", sb2.toString());
		return map;
	}

	/**
	 * 根据sql查出结果
	 */
	public List getResultBySQL(String sql, String formcode) {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm form = dfd.loadForm(formcode);
			String systemid = form.getSystemid();

			conn = getConn(systemid);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List colist = new ArrayList();
			ResultSetMetaData rsmeta = ps.getResultSet().getMetaData();
			int columnLen = rsmeta.getColumnCount();
			for (int i = 1; i <= columnLen; i++) {
				String columnname = rsmeta.getColumnName(i);
				colist.add(columnname);
			}
			list.add(colist);
			while (rs.next()) {
				List tmplist = new ArrayList();
				for (Iterator iterator = colist.iterator(); iterator.hasNext();) {
					String columnname = (String) iterator.next();
					Map map = new LinkedHashMap();
					if ("belongx".equalsIgnoreCase(columnname)) {
						map.put("key", rs.getRow());
					}
					map.put("value", rs.getString(columnname));
					tmplist.add(map);
				}
				list.add(tmplist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean excelInAdddata(String insertsql, String filename,
			String sheetname, String[] checkedkeys) throws RemoteException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String ds_excel = "ds_config_excel.xml";
			conn = OrmerEntry.fetchDS(ds_excel).getConnection();
			ExcelToDbDao etd = new ExcelToDbDaoImpl();
			URL url = new URL(filename);
			ps = conn.prepareStatement(insertsql);
			int j = 0;
			List list = etd.getList(url, sheetname);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Map map = (Map) iter.next();
				for (int i = 0; i < checkedkeys.length; i++) {
					String value = map.get(checkedkeys[i]).toString();
					ps.setString(i + 1, value);
				}
				j++;
				ps.addBatch();
				if (j % 100 == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Map excelInSqlExe(String sql) throws RemoteException {
		Map data = new HashMap();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String ds_excel = "ds_config_excel.xml";
			conn = OrmerEntry.fetchDS(ds_excel).getConnection();
			ps = conn.prepareStatement(sql);
			// 查询
			if (ps.execute()) {

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				List<String> columnlist = new ArrayList<String>();
				rs = ps.getResultSet();
				ResultSetMetaData metaData = rs.getMetaData();
				while (rs.next()) {
					Map map = new LinkedHashMap();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						Map<String, Object> preRecord = new LinkedHashMap<String, Object>();
						String columnname = metaData.getColumnName(i);
						if (!columnlist.contains(columnname)) {
							columnlist.add(columnname);
						}
						Object value = rs.getObject(columnname);
						preRecord.put("key", columnname);
						preRecord.put("value", value);
						map.put(i, preRecord);
					}
					list.add(map);
				}
				data.put("list", list);
				data.put("columnlist", columnlist);

				return data;
				// 非查询
			} else {

				List alllist = new ArrayList();
				alllist.add(ps.getUpdateCount());
				data.put("list", alllist);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void excelTableCreate(String tablename, String[] checkedkeys)
			throws RemoteException {

		String sql = "create table " + tablename + "(";
		for (int i = 0; i < checkedkeys.length; i++) {
			String name = StringUtils
					.substringBetween(checkedkeys[i], "[", "]");
			if (i == 0) {
				sql = sql + name + " varchar(100)";
			} else {
				sql = sql + "," + name + " varchar(100)";
			}
		}
		sql = sql + ")";
		excelInSqlExe(sql);

	}

	public List exportDyData(String sql, Map map1) throws RemoteException {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = OrmerEntry.fetchDS().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> mapelement = new LinkedHashMap<String, String>();
				for (Iterator iterx = map1.keySet().iterator(); iterx.hasNext();) {
					String elementName = (String) iterx.next();
					String othername = StringUtils.substringBetween(map1.get(
							elementName).toString(), "[", "]");
					mapelement.put(elementName, rs.getString(othername));
				}
				list.add(mapelement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
