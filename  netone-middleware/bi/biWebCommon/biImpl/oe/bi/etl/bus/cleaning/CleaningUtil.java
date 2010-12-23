package oe.bi.etl.bus.cleaning;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import oe.bi.etl.bus.extractcore.UniformView;
import oe.bi.exceptions.ErrorDataModelException;

import org.apache.commons.lang.StringUtils;


public class CleaningUtil {

	public static String cleanOpe(String sql) throws ErrorDataModelException{
		String tablelistTmp = StringUtils.substringBetween(sql, " from ",
				" where ");
		String[] tableName = tablelistTmp.split(",");

		String columnInfo = StringUtils.substringBetween(sql, "select ",
				" from ");

		Map tableInfo = new HashMap();
		// 获得字段部分的表名
		for (int i = 0; i < tableName.length; i++) {
			if (StringUtils.contains(columnInfo, tableName[i] + ".")) {
				tableInfo.put(tableName[i], tableName[i]);
			}
		}

		String conInfo = StringUtils.substringAfter(sql,
				UniformView._DAFUALT_LINK_TO);

		for (int i = 0; i < tableName.length; i++) {
			if (StringUtils.contains(conInfo, tableName[i] + ".")) {
				tableInfo.put(tableName[i], tableName[i]);
			}
		}

		String linkinfo = StringUtils.substringBetween(sql,
				UniformView._DAFUALT_LINK_FROM, UniformView._DAFUALT_LINK_TO);

		if (tableInfo.size() == 1) {
			if (tableName.length == 1) {
				return sql;
			} else {
				String key = (String) tableInfo.keySet().iterator().next();
				// 清除表连接
				String sqlFirst = StringUtils.replaceOnce(sql, tablelistTmp,
						key);
				return sqlFirst.replaceFirst(linkinfo, " and ");
			}
		} else if ((tableInfo.size() > 1)) {
			StringBuffer tablelist = new StringBuffer();
			for (Iterator itr = tableInfo.keySet().iterator(); itr.hasNext();) {
				String keyPres = (String) itr.next();
				if (linkinfo.indexOf(keyPres) == -1) {
					throw new ErrorDataModelException("缺少关联表：" + keyPres);
				}
				tablelist.append("," + keyPres);
			}

			return sql.replaceFirst(UniformView._DAFUALT_TABLE, tablelist
					.substring(1).toString());
		}
		throw new ErrorDataModelException("");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
