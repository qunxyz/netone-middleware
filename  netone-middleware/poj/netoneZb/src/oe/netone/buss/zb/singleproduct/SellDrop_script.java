/**
 * 
 */
package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

/**
 * 首饰销退_后台脚本
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-26 上午10:57:09
 * @history
 */
public class SellDrop_script extends OeScript {

	// 新建保存
	public void addSave() {
		dy.set("$(lsh)" + ":" + "$(formcode)", "column5", "$(participant)");
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String a1 = dateformat.format(new java.util.Date());
		dy.set("$(lsh)" + ":" + "$(formcode)", "column6", a1);

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "XT";
		String column3 = dy.get("$(lsh)" + ":" + "$(formcode)", "column3");
		List listx2 = new ArrayList();
		listx2.add(column3);
		List list2 = db
				.queryData_p(
						con,
						"SELECT IFNULL(count(*),0) as countx  FROM DY_621338820565627  WHERE column3 =? and column3 is not null ",
						listx2);
		boolean isneedNew = false;
		if (list2.size() > 0) {
			Map map = (Map) list2.get(0);
			Integer countx = Integer.valueOf(map.get("countx").toString());
			System.out.println(countx + ":isneedNew:" + isneedNew);
			if (countx > 1) {
				isneedNew = true;
			}
		} else {
			isneedNew = true;
		}
		System.out.println("isneedNew:" + isneedNew);
		if (isneedNew) {
			List listx = new ArrayList();
			listx.add(code + date);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db
					.queryData(
							con,
							"SELECT max(column3) as maxcode FROM DY_621338820565627  WHERE column3 like  \'"
									+ code + date + "%\' ");
			String _code = null;
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				_code = (String) map.get("maxcode");
			}
			if (_code != null) {
				String queneNumber = org.apache.commons.lang.StringUtils
						.substring(_code, _code.length() - 3, _code.length());
				Integer x = Integer.parseInt(queneNumber) + 1;
				queneNumber = x.toString();
				if (queneNumber.length() == 1) {
					/** 1位补2个0* */
					queneNumber = "00" + queneNumber;
				} else if (queneNumber.length() == 2) {
					/** 2位补1个0* */
					queneNumber = "0" + queneNumber;
				}
				_code = date + queneNumber;
			} else {
				/** NEW */
				_code = date + "001";
			}
			code += _code;
			dy.set("$(lsh)" + ":" + "$(formcode)", "column3", code);
		}
		db.close(con);
	}

	// 编辑保存事件
	public void editSave() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "XT";
		String column3 = dy.get("$(lsh)" + ":" + "$(formcode)", "column3");
		List listx2 = new ArrayList();
		listx2.add(column3);
		List list2 = db
				.queryData_p(
						con,
						"SELECT IFNULL(count(*),0) as countx  FROM DY_621338820565627  WHERE column3 =? and column3 is not null ",
						listx2);
		boolean isneedNew = false;
		if (list2.size() > 0) {
			Map map = (Map) list2.get(0);
			Integer countx = Integer.valueOf(map.get("countx").toString());
			System.out.println(countx + ":isneedNew:" + isneedNew);
			if (countx > 1) {
				isneedNew = true;
			}
		} else {
			isneedNew = true;
		}
		System.out.println("isneedNew:" + isneedNew);
		if (isneedNew) {
			List listx = new ArrayList();
			listx.add(code + date);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db
					.queryData(
							con,
							"SELECT max(column3) as maxcode FROM DY_621338820565627  WHERE column3 like  \'"
									+ code + date + "%\' ");
			String _code = null;
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				_code = (String) map.get("maxcode");
			}
			if (_code != null) {
				String queneNumber = org.apache.commons.lang.StringUtils
						.substring(_code, _code.length() - 3, _code.length());
				Integer x = Integer.parseInt(queneNumber) + 1;
				queneNumber = x.toString();
				if (queneNumber.length() == 1) {
					/** 1位补2个0* */
					queneNumber = "00" + queneNumber;
				} else if (queneNumber.length() == 2) {
					/** 2位补1个0* */
					queneNumber = "0" + queneNumber;
				}
				_code = date + queneNumber;
			} else {
				/** NEW */
				_code = date + "001";
			}
			code += _code;
			dy.set("$(lsh)" + ":" + "$(formcode)", "column3", code);
		}
		db.close(con);
	}

	// 获取首饰销退单号 SOASCRIPT.SOASCRIPT.ZB.GETINDENTCODE2
	public static String GETINDENTCODE2() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
				"yyyyMMdd");

		String date = DATE_FORMAT.format(new java.util.Date());
		String code = "XS";

		List list = db.queryData(con,
				"SELECT max(column3) as maxcode FROM DY_621338820565627   WHERE column3 LIKE '"
						+ code + date + "%';");

		String _code = null;
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			_code = (String) map.get("maxcode");
		}

		if (_code != null && !"".equals(_code)) {
			String queneNumber = org.apache.commons.lang.StringUtils.substring(
					_code, _code.length() - 3, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {
				/** 1位补2个0* */
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {
				/** 2位补1个0* */
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {
			/** New* */
			_code = date + "001";
		}

		code += _code;
		db.close(con);
		System.out.println("销退单号：" + code);
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("maxcode", code);
		return json.toString();
	}

	// 取得会员信息2 SOASCRIPT.SOASCRIPT.ZB.GETVIPINFO2
	public static String GETVIPINFO2() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"select * from DY_131337490209098 WHERE column25='是' and column3 like '%$(q)%' ");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	// 根据积分取得会员等级 SOASCRIPT.SOASCRIPT.ZB.GETVIPLEVELBYPOINT
	public static String GETVIPLEVELBYPOINT() {
		String sql = "select * from  DY_381336140843610 where column6<=$(q) order by column6 desc limit 1 ";

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			System.out.println("testSize1:" + i);
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				System.out.println("testSize2:" + jsonStr);
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(con);
		return "" + jsonBuffer.toString() + "";
	}
}
