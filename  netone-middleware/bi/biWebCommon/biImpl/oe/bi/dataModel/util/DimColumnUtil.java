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
	 * ����SQL���
	 * 
	 * @param treemodelStr
	 * @return ����SQL���
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
					tablename += treemodel.getTableName(); // ������
					colname += treemodel.getTableName() + "."
							+ treemodel.getColumnid() + ","
							+ treemodel.getTableName() + "."
							+ treemodel.getColumnname(); // �ֶ�����
					cond += treemodel.getCondition(); // ����
					// ��С����?�Ӵ�С?
				} else {
					tablename += treemodel.getTableName() + ",";// ������
					colname += treemodel.getTableName() + "."
							+ treemodel.getColumnid() + ","
							+ treemodel.getTableName() + "."
							+ treemodel.getColumnname() + ",";// �ֶ�����
					// cond+=treemodel.getTableName()+"."+treemodel.getSubid()+"="+treemodeladd.getTableName()+"."+treemodeladd.getColumnid()+"
					// and ";
					if (treemodel.getCondition() == null
							|| treemodel.getCondition().equals(""))
						cond += " 1=1  and ";// ����
					else
						cond += treemodel.getCondition() + " and ";// ����
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
	 * ����SQL���ֶ���Ϣ
	 * 
	 * @param columnName
	 * @param tableName
	 * @param condition
	 * @return ����ֵǰN-1�����ֶ���Ϣ,��N����SQL
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
	 * ����ά���ַ���,�ָ���ϳ�List����(���ݴ�List����ϳ�SQL���)
	 * 
	 * @param treemodelStr
	 * @return List�б� List�б���װ��TreeModel����
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
						tmObj.setIndex(stindex.nextToken()); // ȡ��indexֵ
					}

					StringTokenizer stS = new StringTokenizer(stL.nextToken(),
							"]");

					String sqlStr = stS.nextToken();
					StringTokenizer stSql = new StringTokenizer(sqlStr, ":");
					if (stSql.hasMoreTokens()) {
						tmObj.setTableName(stSql.nextToken()); // ȡ�ñ�����

						StringTokenizer stCol = new StringTokenizer(stSql
								.nextToken(), ",");
						if (stCol.hasMoreTokens()) {
							tmObj.setColumnid(stCol.nextToken()); // ȡ�ֶ�IDֵ
							tmObj.setColumnname(stCol.nextToken());// ȡ�ֶ�NAMEֵ
							// tmObj.setSubid(stCol.nextToken());
						}
					}

					StringTokenizer stCond = new StringTokenizer(stL
							.nextToken(), "]");
					if (stCond.hasMoreTokens()) {
						tmObj.setCondition(stCond.nextToken()); // ȡ����ֵ
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
