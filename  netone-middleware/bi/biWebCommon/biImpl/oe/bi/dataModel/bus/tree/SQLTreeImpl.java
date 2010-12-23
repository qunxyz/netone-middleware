package oe.bi.dataModel.bus.tree;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import oe.bi.BiEntry;
import oe.bi.BiTree;
import oe.bi.SQLTree;
import oe.bi.common.db.DBHandler;
import oe.bi.dataModel.bus.ActionDigTree;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.bus.tree.util.MakeXMLTree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SQLTreeImpl implements BiTree, SQLTree {
	private static Log _log = LogFactory.getLog(SQLTreeImpl.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String fetchTreeInfo(String datamodelid, String columnid,
			String node, String sqltreeinfo, int level) {
		if (sqltreeinfo == null || sqltreeinfo.equals("")) {
			_log.warn("没有钻取树");
			return HEAD_XML + END_XML;
		}
		String[] levelInfo = sqltreeinfo.split(";");
		if (levelInfo == null || levelInfo.length == 0) {
			_log.warn("没有钻取树");
			return HEAD_XML + END_XML;
		}
		if (level >= levelInfo.length) {
			return HEAD_XML + END_XML;
		}
		String info = levelInfo[level];
		String[] infos = fetchSQLTreeInfo(info);
		if (infos == null) {
			return HEAD_XML + END_XML;
		}
		String sql = makeSQL(infos, level);
		Object[][] objData = fetchSQLdata(sql, node);
		return generateTree(datamodelid, columnid, objData, level);

	}

	private String generateTree(String datamodelid, String columnid,
			Object[][] objdata, int level) {
		StringBuffer buf = new StringBuffer();
		++level;
		for (int i = 0; i < objdata.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					objdata[i][0].toString(), objdata[i][1].toString(), String
							.valueOf(level));
			buf.append(preInfo);
		}
		return HEAD_XML + buf + END_XML;

	}

	private Object[][] fetchSQLdata(String sql, Object nodeValue) {
		_log.debug(sql);
		DBHandler dbHandler = (DBHandler) BiEntry.fetchBi("dBHandler");
		PreparedStatement ps = dbHandler
				.fetchHanderPreparedStatement(null, sql);
		try {
			if (ActionDigTree._ROOT_NODE.equals(nodeValue)) {
				ps.setObject(1, nodeValue);
			} else {
				String noteValue = (String) nodeValue;
				String[] noteRealValue = noteValue
						.split(DigTreeBuilder._NODE_KEY_SPLIT);
				ps.setObject(1, noteRealValue[noteRealValue.length - 1]);
			}
		} catch (SQLException e) {
			_log.error(sql + ": key=" + nodeValue + " 错误");
			e.printStackTrace();
			return new Object[0][2];
		}
		try {
			ResultSet rs = (ResultSet) ps.executeQuery();
			List listRs = new ArrayList();
			while (rs.next()) {
				Object[] infos = new Object[2];
				if (ActionDigTree._ROOT_NODE.equals(nodeValue)) {
					infos[0] = rs.getObject(1) + DigTreeBuilder._NODE_KEY_SPLIT;
				} else {
					infos[0] = nodeValue.toString() + rs.getObject(1)
							+ DigTreeBuilder._NODE_KEY_SPLIT;
				}
				infos[1] = rs.getObject(2);
				listRs.add(infos);
			}
			return (Object[][]) listRs.toArray(new Object[0][2]);
		} catch (SQLException e) {
			_log.error(sql + ": key=" + nodeValue + " 错误");
			e.printStackTrace();
			return new Object[0][2];
		}
	}

	private String[] fetchSQLID(String sql, Object nodeValue) {
		_log.debug(sql);
		DBHandler dbHandler = (DBHandler) BiEntry.fetchBi("dBHandler");
		PreparedStatement ps = dbHandler
				.fetchHanderPreparedStatement(null, sql);
		try {
			if (ActionDigTree._ROOT_NODE.equals(nodeValue)) {
				ps.setObject(1, nodeValue);
			} else {
				String noteValue = (String) nodeValue;
				String[] noteRealValue = noteValue
						.split(DigTreeBuilder._NODE_KEY_SPLIT);
				ps.setObject(1, noteRealValue[noteRealValue.length - 1]);
			}
		} catch (SQLException e) {
			_log.error(sql + ": key=" + nodeValue + " 错误");
			e.printStackTrace();
			return new String[0];
		}
		try {
			ResultSet rs = (ResultSet) ps.executeQuery();
			List listRs = new ArrayList();
			while (rs.next()) {
				Object infos = null;
				if (ActionDigTree._ROOT_NODE.equals(nodeValue)) {
					infos = rs.getObject(1) + DigTreeBuilder._NODE_KEY_SPLIT;
				} else {
					infos = nodeValue.toString() + rs.getObject(1)
							+ DigTreeBuilder._NODE_KEY_SPLIT;
				}
				String infoValue = infos == null ? "" : infos.toString();
				listRs.add(infoValue);
			}
			return (String[]) listRs.toArray(new String[0]);
		} catch (SQLException e) {
			_log.error(sql + ": key=" + nodeValue + " 错误");
			e.printStackTrace();
			return new String[0];
		}
	}

	private String makeSQL(String[] infos, int level) {
		if (level == 0) {
			return "select " + infos[1] + " from " + infos[0] + " where "
					+ infos[2] + " or " + infos[3] + "=?";
		} else {
			return "select " + infos[1] + " from " + infos[0] + " where "
					+ infos[2] + " and " + infos[3] + "=?";
		}
	}

	private String[] fetchSQLTreeInfo(String info) {
		String[] infos = new String[4];
		StringTokenizer st = new StringTokenizer(info, "[");
		if (st.hasMoreTokens()) {
			StringTokenizer stNext = new StringTokenizer(st.nextToken(), "]");
			if (!stNext.hasMoreTokens()) {
				_log.warn("没有钻取树定义错误缺少第一个[]定义");
				return null;
			}
			infos[0] = stNext.nextToken();
		}
		if (st.hasMoreTokens()) {
			StringTokenizer stNext = new StringTokenizer(st.nextToken(), "]");
			if (!stNext.hasMoreTokens()) {
				_log.warn("没有钻取树定义错误缺少第二个[]定义");
				return null;
			}
			infos[1] = stNext.nextToken();
		}
		if (st.hasMoreTokens()) {
			StringTokenizer stNext = new StringTokenizer(st.nextToken(), "]");
			if (stNext.hasMoreTokens()) {
				infos[2] = stNext.nextToken();
			} else {
				infos[2] = "1=1";
			}
		}
		if (st.hasMoreTokens()) {
			StringTokenizer stNext = new StringTokenizer(st.nextToken(), "]");
			if (!stNext.hasMoreTokens()) {
				_log.warn("没有钻取树定义错误缺少第四个[]定义");
				return null;
			}
			infos[3] = stNext.nextToken();
		}
		return infos;
	}

	public String[] fetchElement(String datamodelid, String columnid,
			String node, String sqltreeinfo, int level) {
		if (sqltreeinfo == null || sqltreeinfo.equals("")) {
			_log.warn("没有钻取树");
			return new String[0];
		}
		String[] levelInfo = sqltreeinfo.split(";");
		if (levelInfo == null || levelInfo.length == 0) {
			_log.warn("没有钻取树");
			return new String[0];
		}
		if (level >= levelInfo.length) {
			return new String[0];
		}
		String info = levelInfo[level];
		String[] infos = fetchSQLTreeInfo(info);
		if (infos == null) {
			return new String[0];
		}
		String sql = makeSQL(infos, level);
		return fetchSQLID(sql, node);
	}
}
