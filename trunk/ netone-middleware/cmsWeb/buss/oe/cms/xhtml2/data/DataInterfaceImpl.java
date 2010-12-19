package oe.cms.xhtml2.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oe.cms.xhtml2.DataInterface;
import oe.cms.xhtml2.data.core.ReadNetInfo;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.DbTools;

import org.apache.commons.lang.StringUtils;

public final class DataInterfaceImpl implements DataInterface {

	public String insect(String arg0, String arg1) {
		return ReadNetInfo.netParser(arg0, arg1);
	}

	public String[] regular(String info, String regularRule, String regularRule1) {
		// TODO Auto-generated method stub
		Pattern pat = Pattern.compile(regularRule);
		Matcher mat = pat.matcher(info);
		List list = new ArrayList();
		if (mat.find()) {
			String infonext = mat.group();

			Pattern patx = Pattern.compile(regularRule1);
			Matcher matx = patx.matcher(infonext);
			while (matx.find()) {
				String infonextx = matx.group();
				list.add(infonextx);
			}

		}
		return (String[]) list.toArray(new String[0]);
	}

	public String[] regular(String info, String rule) {

		Pattern pat = Pattern.compile(rule);
		Matcher mat = pat.matcher(info);
		List list = new ArrayList();
		if (mat.find()) {
			String infonext = mat.group();
			list.add(infonext);
		}
		return (String[]) list.toArray(new String[0]);
	}

	public String[][] executeSql(String arg0) {
		if (arg0 == null || arg0.equals("")) {
			return new String[0][0];
		}

		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DbTools.superQueryOri(con, arg0);

	}

	public String[][] executePrepareSql(String sqlexpression, String[] paramer) {
		if (sqlexpression == null || sqlexpression.equals("")) {
			return new String[0][0];
		}
		int maxParamerLen = paramer.length;
		int para = StringUtils.indexOf(sqlexpression, "?");
		int i = -1;
		while (para != -1) {
			i++;
			if (i >= maxParamerLen) {
				System.err.println("输入参数和SQL中的参数不一致：" + sqlexpression);
				return new String[0][0];
			}
			sqlexpression = StringUtils.replaceOnce(sqlexpression, "?",
					paramer[i]);
			para = StringUtils.indexOf(sqlexpression, "?");
		}
		return executeSql(sqlexpression);
	}

	public String[][] useTempDb(String sql) {
		if (sql == null || "".equals(sql)) {
			return new String[0][0];
		}
		sql = sql.trim();
		String findAhead = sql.substring(0, 6);
		findAhead = findAhead.toUpperCase();

		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ("SELECT".equals(findAhead)) {
			return DbTools.superQueryOri(con, sql);
		} else {
			String data = DbTools.serialdo(con, sql);
			return new String[][] { { data } };
		}

	}

}
