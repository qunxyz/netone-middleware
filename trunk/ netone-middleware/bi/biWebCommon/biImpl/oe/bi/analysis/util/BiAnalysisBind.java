package oe.bi.analysis.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import oe.bi.view.obj.ViewModel;


/**
 * 结果邦定
 * 
 * 将需要进行合并的数据构建成内存数据库中的记录信息，然后通过 SQL来实现数据合并操作
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class BiAnalysisBind {
	static Random random = new Random(System.currentTimeMillis());

	public static ViewModel bindViewModel(List list, String orderinfo) {
		if (list == null || list.size() == 0) {
			return new ViewModel();
		}
		if (list.size() == 1) {
			return (ViewModel) list.get(0);
		}
		


		List tableList = new ArrayList();
		List targetIdList = new ArrayList();
		List targetNameList = new ArrayList();
		String tablename = "table" + System.currentTimeMillis()
				+ random.nextInt(1000);

		for (int i = 0; i < list.size(); i++) {
			ViewModel preView = (ViewModel) list.get(i);
			String tableNamePre = tablename + i;
			// 保存全部值ID
			String[] targetIdArr = preView.getTargetid();
			for (int j = 0; j < targetIdArr.length; j++) {
				targetIdList.add(tableNamePre + "." + targetIdArr[j]);
			}

			// 保存全部值名称
			List targetNamePre = Arrays.asList(preView.getTargetname());
			targetNameList.addAll(targetNamePre);
			// 创建零时表
			tableList.add(tableNamePre);
			createTempTable(tableNamePre, targetIdArr);
			// 创建零时表中的记录
			String[][] dim = preView.getDimensionvalue();
			double[][] target = preView.getTargetvalue();
			StringBuffer targetIdListValue = new StringBuffer();
			for (int j = 0; j < targetIdArr.length; j++) {
				targetIdListValue.append("," + targetIdArr[j]);
			}
			createTempRecord(tableNamePre, targetIdListValue.toString(), dim,
					target);
		}

		// 合并表
		Map mapBind = new HashMap();
		bindRecord(tableList, targetIdList, orderinfo, mapBind);
		double[][] target = (double[][]) mapBind.get("TARGET");
		String[][] dim = (String[][]) mapBind.get("DIM");

		// 创建新模型(在第一个模型的基础上改造实现)

		ViewModel viewModel = (ViewModel) list.get(0);
		viewModel.setTargetid((String[]) targetIdList.toArray(new String[0]));
		viewModel.setTargetname((String[]) targetNameList
				.toArray(new String[0]));
		viewModel.setTargetvalue(target);
		viewModel.setDimensionvalue(dim);

		return viewModel;
	}

	private static void bindRecord(List tableList, List targetIdList,
			String orderinfo, Map bininfo) {
		StringBuffer butSelect = new StringBuffer();
		StringBuffer butFrom = new StringBuffer();
		StringBuffer butWhere = new StringBuffer();
		for (int i = 0; i < tableList.size(); i++) {
			String preTable = (String) tableList.get(i);
			butFrom.append("," + preTable);
			if (i + 1 < tableList.size()) {
				String preTableNext = (String) tableList.get(i + 1);
				butWhere.append(" and " + preTable + ".keyx" + "="
						+ preTableNext + ".keyx");
			}
		}
		for (int i = 0; i < targetIdList.size(); i++) {
			butSelect.append("," + targetIdList.get(i));
		}
		String butSelectValue = (String) tableList.get(0) + ".keyx" + butSelect;

		String sql = "select " + butSelectValue + " from "
				+ butFrom.substring(1) + " where 1=1 " + butWhere + orderinfo;

		Connection con = null;
		try {
			con = BindDbUse.fetchConnection();
			ResultSet rs = con.createStatement().executeQuery(sql);
			List listRs = new ArrayList();
			List listDim = new ArrayList();
			while (rs.next()) {
				double[] valuePre = new double[targetIdList.size()];
				
				String[] dimPre = rs.getString(1).split(",");
				listDim.add(dimPre);
				for (int i = 2; i <= targetIdList.size() + 1; i++) {
					
					valuePre[i - 2] = rs.getDouble(i);
				}
				
				listRs.add(valuePre);
			}
			double[][] target = (double[][]) listRs
					.toArray(new double[0][targetIdList.size()]);
			String[][] dim = (String[][]) listDim.toArray(new String[0][2]);
			bininfo.put("TARGET", target);
			bininfo.put("DIM", dim);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void createTempRecord(String tableNamePre,
			String targetIdListValue, String[][] dim, double[][] target) {
		Connection con = null;
		try {
			con = BindDbUse.fetchConnection();
			Statement st = con.createStatement();
			String preInsertHead = "insert into " + tableNamePre + "(keyx";
			for (int i = 0; i < dim.length; i++) {
				StringBuffer key = new StringBuffer();
				for (int j = 0; j < dim[i].length; j++) {
					key.append(dim[i][j] + ",");
				}
				StringBuffer targetValue = new StringBuffer("'"
						+ key.toString() + "'");
				for (int j = 0; j < target[i].length; j++) {
					targetValue.append("," + target[i][j]);
				}
				String insert = preInsertHead + targetIdListValue + ")values("
						+ targetValue + ")";
				st.execute(insert);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void createTempTable(String tableNamePre,
			String[] targetIdPre) {

		StringBuffer tableScriptTmp = new StringBuffer("create table "
				+ tableNamePre + "(");
		for (int i = 0; i < targetIdPre.length; i++) {
			String preTargetColumn = targetIdPre[i] + " double,";
			tableScriptTmp.append(preTargetColumn);
		}
		String tableScript = tableScriptTmp + "keyx varchar(1000)" + ")";
		Connection con = null;
		try {
			con = BindDbUse.fetchConnection();
			con.createStatement().execute(tableScript);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] arg) {
		// 模型1
		ViewModel view = new ViewModel();
		String[][] dimValue = { { "a", "b", "c" }, { "e", "f", "g" },
				{ "e", "g", "f" }, { "f", "e", "g" } };
		view.setDimensionvalue(dimValue);
		double[][] targetValue1 = { { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 },
				{ 4, 4, 4 } };
		view.setTargetvalue(targetValue1);
		view.setTargetid(new String[] { "A", "B", "C" });
		view.setTargetname(new String[] { "A", "B", "C" });

		// 模型2
		ViewModel view2 = new ViewModel();
		String[][] dimValue2 = { { "e", "g", "f" }, { "a", "b", "c" },
				{ "e", "f", "g" } };
		view2.setDimensionvalue(dimValue2);
		double[][] targetValue2 = { { 2, 4, 4 }, { 3, 3, 5 }, { 3, 3, 5 } };
		view2.setTargetvalue(targetValue2);
		view2.setTargetid(new String[] { "E", "F", "G" });
		view2.setTargetname(new String[] { "E", "F", "G" });

		List list = new ArrayList();
		list.add(view);
		list.add(view2);
		ViewModel vx = bindViewModel(list, " order by A desc");

		String[][] dimvalue = vx.getDimensionvalue();
		for (int i = 0; i < dimvalue.length; i++) {
			for (int j = 0; j < dimvalue[i].length; j++) {
				System.out.print(dimvalue[i][j]);
			}
			System.out.println();
		}
	}
}
