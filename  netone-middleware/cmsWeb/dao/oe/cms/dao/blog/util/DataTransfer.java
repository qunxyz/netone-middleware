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
 * ����Ǩ�Ƴ���
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

		// rs�õ���������б�
		ResultSet rs = null;
		// rsCnt�õ������������
		ResultSet rsCnt = null;
		// sqlͨ�����������������sql���
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
		// sqlCnt���ȡ�����������sql���

		StringBuffer but = new StringBuffer();
		// ������ݿ�����Ӵ�
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
			// iCntΪsqlCnt���ִ�н��
			int iCnt = 0;
			if (rsCnt.next()) {
				iCnt = rsCnt.getInt("cnt");
				log.debug("sql����:" + iCnt);
			}
			ResultSetMetaData metaData = rs.getMetaData();
			// fields �����ֶδ� field1,field2...��ʽ
			String fields = "";
			// sqlValue ֵ�Ľ����value1,value2...��ʽ

			for (int j = 1; j <= metaData.getColumnCount(); j++) {
				if (!metaData.getColumnName(j).equalsIgnoreCase("body")) {
					fields = fields + "," + metaData.getColumnName(j);
				}
			}
			fields = fields.substring(1);
			// iSqlΪinsert�����
			String[] iSql = new String[iCnt];
			// ��sql�Ľ�������б���,�����insert��ʽ��sql��丳���б�
			// dataTypeΪ��������,�������͵��ж�
			String dataType = null;
			// i����rs��¼�����±�ֵ
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
						// �ж�ֵ�Ƿ�Ϊ�ղ����ַ���ת���ͽ�ȡ
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
			log.debug("ִ�д�����Ϣ:" + e.getMessage());
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
	 * ���ResultSet @param rs @return viod;
	 */
	private static void releaseRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			log.debug("�ر�ResultSet������Ϣ:" + e.getMessage());
		}
	}

	/*
	 * ���Connection @param conn @return viod;
	 */
	private static void releaseConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.debug("�ر�Connection������Ϣ:" + e.getMessage());
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
		// ����ֵ���Է�ʽ
		// System.out.println("����ֵ1"+dt.parserTableRecordToSqlinfo("nmc_area",
		// ""));
		// System.out.println("����ֵ2"
		// + dt.parserTableRecordToSqlinfo("cm_msc_info", null));

	}
}
