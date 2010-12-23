package oe.bi.dataModel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author wang-ting-jie
 * 
 */
public class DimColumnUtil {
	private static Log _log = LogFactory.getLog(DimColumnUtil.class);

	/**
	 * 构造SQL语句
	 * 
	 * @param treemodelStr
	 * @return 返回SQL语句
	 */
	public static String[] getTreeModelSql(String treemodelStr) {
		// [index][dataset:columnid,columnname,subid][condition];[index][dataset:columnid,columnname,subid][condition];
		try {
			List treemodelList = fetchTreeModel(treemodelStr);
			String tablename = "";
			String colname = "";
			String cond = "";
			for (int i = 0; i < treemodelList.size(); i++) {
				TreeModel treemodel = (TreeModel) treemodelList.get(i);
				if (i == treemodelList.size() - 1) {
					tablename += treemodel.getTableName(); // 表名称
					colname += treemodel.getTableName() + "."
							+ treemodel.getColumnid() + ","
							+ treemodel.getTableName() + "."
							+ treemodel.getColumnname(); // 字段名称
					cond += treemodel.getCondition(); // 条件
					// 从小到大?从大到小?
				} else {
					tablename += treemodel.getTableName() + ",";// 表名称
					colname += treemodel.getTableName() + "."
							+ treemodel.getColumnid() + ","
							+ treemodel.getTableName() + "."
							+ treemodel.getColumnname() + ",";// 字段名称
					// cond+=treemodel.getTableName()+"."+treemodel.getSubid()+"="+treemodeladd.getTableName()+"."+treemodeladd.getColumnid()+"
					// and ";
					if (treemodel.getCondition() == null
							|| treemodel.getCondition().equals(""))
						cond += " 1=1  and ";// 条件
					else
						cond += treemodel.getCondition() + " and ";// 条件
				}
			}
			String[] sqlInfo = makeSQL(colname, tablename, cond);
			if (_log.isDebugEnabled()) {
				for (int i = 0; i < sqlInfo.length; i++) {
					_log.debug("SQL:" + sqlInfo[i]);
				}
			}
			return sqlInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 构建SQL和字段信息
	 * 
	 * @param columnName
	 * @param tableName
	 * @param condition
	 * @return 返回值前N-1个是字段信息,第N个是SQL
	 */
	private static String[] makeSQL(String columnName, String tableName,
			String condition) {

		String sql = "select " + columnName + " from " + tableName + " where  "
				+ condition;

		String[] columnidTmp = columnName.split(",");

		String[] columnid = new String[columnidTmp.length + 1];

		for (int i = 0; i < columnidTmp.length; i++) {
			columnid[i] = columnidTmp[i];
		}
		columnid[columnid.length - 1] = sql;
		return columnid;
	}

	/**
	 * 根据维度字符串,分割组合成List对象(根据此List可组合成SQL语句)
	 * 
	 * @param treemodelStr
	 * @return List列表 List列表里装载TreeModel对象
	 */
	public static List fetchTreeModel(String treemodelStr) {
		try {
			List treeModelList = new ArrayList();
			StringTokenizer st = new StringTokenizer(treemodelStr, ";");
			// [1][t_bi_area:areaid,areaname][];[2][t_bi_shop:shopid,areaname][t_bi_area.areaid=t_bi_shop.areaid];
			while (st.hasMoreTokens()) {

				TreeModel tmObj = new TreeModel();
				StringTokenizer stL = new StringTokenizer(st.nextToken(), "[");

				if (stL.hasMoreTokens()) {

					StringTokenizer stindex = new StringTokenizer(stL
							.nextToken(), "]");
					if (stindex.hasMoreTokens()) {
						tmObj.setIndex(stindex.nextToken()); // 取得index值
					}

					StringTokenizer stS = new StringTokenizer(stL.nextToken(),
							"]");

					String sqlStr = stS.nextToken();
					StringTokenizer stSql = new StringTokenizer(sqlStr, ":");
					if (stSql.hasMoreTokens()) {
						tmObj.setTableName(stSql.nextToken()); // 取得表名称

						StringTokenizer stCol = new StringTokenizer(stSql
								.nextToken(), ",");
						if (stCol.hasMoreTokens()) {
							tmObj.setColumnid(stCol.nextToken()); // 取字段ID值
							tmObj.setColumnname(stCol.nextToken());// 取字段NAME值
							// tmObj.setSubid(stCol.nextToken());
						}
					}

					StringTokenizer stCond = new StringTokenizer(stL
							.nextToken(), "]");
					if (stCond.hasMoreTokens()) {
						tmObj.setCondition(stCond.nextToken()); // 取条件值
					}
				}
				treeModelList.add(tmObj);
			}
			return treeModelList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		DimColumnUtil d = new DimColumnUtil();

		// [1][t_bi_area:areaid,areaname][];[2][t_bi_shop:shopid,areaname][t_bi_area.areaid=t_bi_shop.areaid];
		// String
		// sql=d.getTreeModelSql("[index][dataset:columnid,columnname,subid][condition];[index1][dataset1:columnid1,columnname1,subid1][condition1];[index2][dataset2:columnid2,columnname2,\"\"][condition1];");
		String[] sql = d
				.getTreeModelSql("[1][t_bi_area:areaid,areaname][];[2][t_bi_shop:shopid,areaname][t_bi_area.areaid=t_bi_shop.areaid];");
		System.out.println("sql=======" + sql);
	}
}
