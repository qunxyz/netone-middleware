package oe.cms.dao.blog.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.DbTools;
import oe.frame.web.WebHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据迁移程序
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:xanvaxp@hotmail.com<br>
 *         belong to:www.oesee.org
 * 
 * @version 1.0
 * 
 */
public class DataTransfer {
	private static Log log = LogFactory.getLog(DataTransfer.class);

	public static String parserTableRecordToSqlinfo(String tablename,
			String condition) {

		// rs得到结果集的列表
		ResultSet rs = null;
		// rsCnt得到结果集的总数
		ResultSet rsCnt = null;
		// sql通过传进来组成条件的sql语句
		String sql = null;
		String sqlCnt = null;
		if (tablename == null || tablename.equals("")) {
			return null;
		}
		sql = "select * from " + tablename;
		sqlCnt = "select count(*) cnt from " + tablename;
		if (condition != null && !condition.equals("")) {
			sql = sql + " where " + condition;
			sqlCnt = sqlCnt + " where " + condition;
		}
		log.debug("sql=" + sql);
		// sqlCnt组成取结果集总数的sql语句

		StringBuffer but = new StringBuffer();
		// 获得数据库的连接串
		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (con != null) {
				rs = con.createStatement().executeQuery(sql);
				rsCnt = con.createStatement().executeQuery(sqlCnt);
			}
			// iCnt为sqlCnt语句执行结果
			int iCnt = 0;
			if (rsCnt.next()) {
				iCnt = rsCnt.getInt("cnt");
				log.debug("sql总数:" + iCnt);
			}
			ResultSetMetaData metaData = rs.getMetaData();
			// fields 定义字段串 field1,field2...格式
			String fields = "";
			// sqlValue 值的结果集value1,value2...格式

			for (int j = 1; j <= metaData.getColumnCount(); j++) {
				if (!metaData.getColumnName(j).equalsIgnoreCase("body")) {
					fields = fields + "," + metaData.getColumnName(j);
				}
			}
			fields = fields.substring(1);
			// iSql为insert的语句
			String[] iSql = new String[iCnt];
			// 对sql的结果集进行遍历,并组成insert形式的sql语句赋给列表集
			// dataType为数据类型,用于类型的判断
			String dataType = null;
			// i定义rs记录数的下标值
			int i = 0;
			while (rs.next()) {
				iSql[i] = "insert into " + tablename + "(" + fields
						+ ") values(";
				String[] sqlValue = new String[metaData.getColumnCount()];
				for (int n = 0; n < metaData.getColumnCount(); n++) {
					if (metaData.getColumnName(n + 1).equalsIgnoreCase("body")) {
						continue;
					}
					dataType = metaData.getColumnTypeName(n + 1);
					String columnName = metaData.getColumnName(n + 1);
					// log.debug("dataType="+dataType);
					if (dataType.indexOf("CHAR") > 0) {
						if (rs.getString(columnName) != null) {
							sqlValue[n] = "'" + rs.getString(columnName) + "'";
						} else {
							sqlValue[n] = "''";
						}
					} else if (dataType.equals("NUMBER")) {
						if (rs.getObject(columnName) != null) {
							sqlValue[n] = ""
									+ rs.getObject(columnName).toString() + "";
						} else {
							sqlValue[n] = "0";
						}
					} else if (dataType.equals("DATE")) {
						// 判断值是否为空才做字符串转化和截取
						if (rs.getTimestamp(columnName) != null) {
							sqlValue[n] = "to_date('"
									+ rs.getTimestamp(columnName).toString()
											.substring(0, 19)
									+ "','YYYY-MM-DD HH24:Mi:SS')";
						} else {
							sqlValue[n] = "''";
						}
					} else {

						sqlValue[n] = "'" + rs.getString(n + 1) + "'";
					}
					iSql[i] = iSql[i] + sqlValue[n] + ",";
				}
				iSql[i] = iSql[i].substring(0, iSql[i].length() - 1) + ")";
				log.debug("iSql" + i + "=" + iSql[i]);
				but.append(iSql[i] + "\n\r;");
				i++;
			}
		} catch (SQLException e) {
			log.debug("执行错误信息:" + e.getMessage());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			releaseRs(rs);
			releaseRs(rsCnt);
			try {
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return but.toString();
	}

	/*
	 * 获得ResultSet @param rs @return viod;
	 */
	private static void releaseRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			log.debug("关闭ResultSet错误信息:" + e.getMessage());
		}
	}

	/*
	 * 获得Connection @param conn @return viod;
	 */
	private static void releaseConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.debug("关闭Connection错误信息:" + e.getMessage());
		}
	}

	public static void sqlinfoSerial(InputStream input, String bkpath,
			String tablename) {
		// WebHandler webHandler = (WebHandler)
		// EnvEntry.fetchBean("webHandler");
		//
		// try {
		// webHandler.fetchFileTransfers()
		// .uploadFile(input, bkpath, tablename);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) {
		// DataTransfer dt = new DataTransferImpl();
		// 正常值测试方式
		// System.out.println("正常值1"+dt.parserTableRecordToSqlinfo("nmc_area",
		// ""));
		// System.out.println("正常值2"
		// + dt.parserTableRecordToSqlinfo("cm_msc_info", null));

	}
}
