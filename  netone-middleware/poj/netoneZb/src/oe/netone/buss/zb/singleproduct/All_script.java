/**
 * 
 */
package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-4 ����02:42:35
 * @history
 */
public class All_script extends OeScript {
	/**
	 * ��Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.DPXX
	 * 
	 * @return
	 */
	public String DPXX() {
		String sql = "select * from DY_271334208897441 where column4 like '$(q)%'";
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ���ݲɹ�����ȡ�òɹ���Ϣ SOASCRIPT.SOASCRIPT.ZB.GETPAYMENTINFO
	 * 
	 * @return
	 */
	public String GETPAYMENTINFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		String condition = "";
		String q = "$(q)";
		q = q.trim();
		condition = " and column3 like '%" + q + "%'";
		List list = db.queryData(con,
				"select * from DY_661338441749384 where 1=1 " + condition);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ����������ȡ�õ�Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.DPXX2
	 * 
	 * @return
	 */
	public String DPXX2() {
		String sql = "select t2.* from DY_271334208897441 t2 left join DY_271334208897439 t on t2.fatherlsh=t.lsh where t2.column4 like '%$(q)%' and t.STATUSINFO='01' and t2.STATUSINFO='01' ";

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ���ݻ���ȡ�û�Ա�ȼ� SOASCRIPT.SOASCRIPT.ZB.GETVIPLEVELBYPOINT
	 * 
	 * @return
	 */
	public String GETVIPLEVELBYPOINT() {
		String sql = "select * from  DY_381336140843610 where column6<=$(q) order by column6 desc limit 1 ";

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
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

	/**
	 * ��ȡ�������۵��� SOASCRIPT.SOASCRIPT.ZB.GETINDENTCODE
	 * 
	 * @return
	 */
	public String GETINDENTCODE() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
				"yyyyMMdd");

		String date = DATE_FORMAT.format(new java.util.Date());
		String code = "XS";

		List list = db.queryData(con,
				"SELECT max(column3) as maxcode FROM DY_371337952339241  WHERE column3 LIKE '"
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
				/** 1λ��2��0* */
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {
				/** 2λ��1��0* */
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {
			/** New* */
			_code = date + "001";
		}

		code += _code;
		db.close(con);
		System.out.println("���۵��ţ�" + code);
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("maxcode", code);
		return json.toString();

	}

	/**
	 * ȡ�û�Ա��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETVIPINFO
	 * 
	 * @return
	 */
	public String GETVIPINFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(con,
						"select * from DY_131337490209098 WHERE column25='��' and column3='$(q)' ");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
				break;
			}
		}
		db.close(con);
		return "" + jsonBuffer.toString() + "";
	}

	/**
	 * ���������Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETPRODUCTBYFXJH
	 * 
	 * @return
	 */
	public String GETPRODUCTBYFXJH() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"select ins.*,ins_main.column14 as bigcate,sub.column5 as sellprice from DY_661338441749388 sub left join DY_271334208897441 ins on ins.column4=sub.column3 left join DY_271334208897439 ins_main on ins_main.lsh=ins.fatherlsh left join DY_661338441749389 father on father.lsh=sub.fatherlsh where father.STATUSINFO='01' and sub.STATUSINFO='01' and sub.column3 like '%$(q)%' and father.column12='$(sr_clientid)' ");
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

	/**
	 * ���ݿͻ�����ȡ���Ż���Ϣ SOASCRIPT.SOASCRIPT.ZB.GETSPECIALOFFERSINFOBYLEVEL
	 * 
	 * @return
	 */
	public String GETSPECIALOFFERSINFOBYLEVEL() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select * from DY_571339077904884  where column6 = '$(q)' order by column8 asc limit 1 ");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "" + jsonBuffer.toString() + "";
	}

	/**
	 * ��ȡ�������˵��� SOASCRIPT.SOASCRIPT.ZB.GETINDENTCODE2
	 * 
	 * @return
	 */
	public String GETINDENTCODE2() {
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
				/** 1λ��2��0* */
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {
				/** 2λ��1��0* */
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {
			/** New* */
			_code = date + "001";
		}

		code += _code;
		db.close(con);
		System.out.println("���˵��ţ�" + code);
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("maxcode", code);
		return json.toString();
	}

	/**
	 * ��ȡ���۲�Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETPRODUCTBYINDENT �����˿�����ʹ��
	 * 
	 * @return
	 */
	public String GETPRODUCTBYINDENT() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"select ins.column59 jgf,ins.column4 code,ins.column7 cpmc,ins.column40 zsdj,ins.column11 kh,ins.column12 zsh,sub.column19 cpdl,sub.column11 ybj,sub.column15 ssj,sub.column7 jj,sub.column8  gffs,sub.column18 gz from DY_371337952339241 father left join DY_371337952339238  sub on father.lsh=sub.fatherlsh left join DY_271334208897441 ins on ins.column4=sub.column3  where father.STATUSINFO='01' and sub.STATUSINFO='01' and sub.column3 like '%$(q)%' and father.column8='$(sr_clientid)' ");
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

	/**
	 * ȡ�û�Ա��Ϣ2 SOASCRIPT.SOASCRIPT.ZB.GETVIPINFO2
	 * 
	 * @return
	 */
	public String GETVIPINFO2() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"select * from DY_131337490209098 WHERE column25='��' and column3 like '%$(q)%' ");
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

	/**
	 * ��ȡ��Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETPRESENTINFO
	 * 
	 * @return
	 */
	public String GETPRESENTINFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(con,
						"select * from DY_381336140843598 where column4 like '%$(q)%' and column12='1'");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ��ȡ��Ʒ�����Ʒ��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETPRESENTININFO
	 * 
	 * @return
	 */
	public String GETPRESENTININFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select t.* from DY_381336140843594 t left join DY_381336140843595  t2 on t.fatherlsh=t2.lsh where t.column3 like '%$(q)%' and t2.statusinfo='01'");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ��λ������Ϣ SOASCRIPT.SOASCRIPT.ZB.GETGONGZILIST
	 * 
	 * @return
	 */
	public String GETGONGZILIST() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"select * from DY_491341112536755 where column3='$(q)'");
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
		return "" + jsonBuffer.toString() + "";
	}

	/**
	 * ������� SOASCRIPT.SOASCRIPT.ZB.FXKC
	 * 
	 * @return
	 */
	public String FXKC() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select t.*,t2.column9 GZ from DY_661338441749388 t,DY_661338441749389 t2 where t.fatherlsh=t2.lsh and t2.column12='$(q)' and t.STATUSINFO='01'");
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

	/**
	 * �����Ʒ���� SOASCRIPT.SOASCRIPT.ZB.GETPRODUCTTIPS
	 * 
	 * @return
	 * @throws Exception
	 */
	public String GETPRODUCTTIPS() throws Exception {
		oe.midware.workflow.engine.rule2.func.STools st = new oe.midware.workflow.engine.rule2.func.STools();
		List roles = st.rs_.getUserRoles("0000", "$(sr_participant)");

		StringBuffer conditions = new StringBuffer();
		for (int i = 0; i < roles.size(); i++) {
			oe.security3a.seucore.obj.db.UmsRole r = (oe.security3a.seucore.obj.db.UmsRole) roles
					.get(i);
			conditions.append(" or  column6 like '%[" + r.getId() + "]%'");

		}

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select * from DY_671340788796713  where column3 not like '%[$(sr_participant)]%' and 1=1  ");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ȡ����Ʒ���� SOASCRIPT.SOASCRIPT.ZB.UPDATEPRODUCTTIPS
	 * 
	 * @return
	 */
	public String UPDATEPRODUCTTIPS() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		String sql = " update DY_671340788796713 set column3=CONCAT(ifnull(column3,''),'[$(sr_participant)]') where column5='$(q)' ";
		int result = db.execute(con, sql);

		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		if (result > 0) {
			json.put("tips", "�����ɹ�!");
		} else {
			json.put("tips", "����ʧ��!");
			json.put("error", "yes");
		}
		db.close(con);
		return "" + json.toString() + "";
	}

	/**
	 * ����������Ƿ��ظ� SOASCRIPT.SOASCRIPT.ZB.CHECKPRODUCTCODEISREPEAT
	 * 
	 * @return
	 */
	public String CHECKPRODUCTCODEISREPEAT() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select ifnull(count(*),0) as countx from $(sr_table) where fatherlsh!='$(sr_lsh)' and $(sr_pcodecol)='$(sr_pcode)' ");
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		Map map = (Map) list.get(0);
		json.put("count", map.get("countx").toString());
		return "" + json.toString() + "";
	}

	/**
	 * ��ȡ�����̵���Ϣ SOASCRIPT.SOASCRIPT.ZB.GETWXPDINFO
	 * 
	 * @return
	 */
	public String GETWXPDINFO() {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select rk.*,sub.column5 fxprice,sub.column20 note from DY_661338441749389  father ");
		sb
				.append(" left join DY_661338441749388 sub on father.lsh=sub.fatherlsh ");

		sb
				.append(" left join DY_271334208897441 rk on rk.column4=sub.column3 ");

		sb.append(" where  1=1 ");
		sb.append(" and father.column12='$(sr_clientid)' ");

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sb.toString());
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ���ʹ��� SOASCRIPT.SOASCRIPT.ZB.GZGL
	 * 
	 * @return
	 */
	public String GZGL() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select t.column3 PR,t.column4 ZM,t.column5 JR,t2.column3 WDK,t2.column4 CD,t2.column5 BJ,t2.column6 SJ,t2.column7 LJ from DY_491341112536756 t,DY_491341112536757 t2 where t.column8='01' and t2.column10='01'");
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
		return jsonBuffer.toString();
	}

	/**
	 * ��������������ͳ�� SOASCRIPT.SOASCRIPT.ZB.SSRKJHTJ
	 * 
	 * @return
	 */
	public String SSRKJHTJ() {
		String[][] keyvalue = { { "1", "proudct1" }, { "2", "proudct12" },
				{ "3", "proudct13" } };
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < keyvalue.length; i++) {
			but.append(keyvalue[i][0] + "-" + keyvalue[i][1] + ",");
		}
		return but.toString();
	}

	/**
	 * ��ȡ��ʯ��Ϣ SOASCRIPT.SOASCRIPT.ZB.GETNSTONE
	 * 
	 * @return
	 */
	public String GETNSTONE() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"select * from DY_491341112536759  where column3 not in ( select column80 from DY_271334208897441 where column80 is not null) and column3 like '%$(q)%' ");
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

	/**
	 * ��������ȡ��Ʒ����ű� ��Ӧ����ԴĿ¼�� SOASCRIPT.SOASCRIPT.ZB.FXGL.DPRK
	 * 
	 * @return
	 */
	public String DPRK() {
		String sql = "select sub.column4 code,sub.column7 cpmc,sub.column34 sj,sub.column11 kh,sub.column12 zsh,sub.column13 ybh,sub.column16  zz,sub.column17 jz,sub.column24 sc,sub.column37 zs,sub.column58 xz,sub.column55 ys,sub.column56 jd,sub.column57 qg,father.column14 cpdl from DY_271334208897441 sub left join DY_271334208897439 father on  sub.fatherlsh=father.lsh     where father.STATUSINFO='01' and sub.STATUSINFO='01'  and sub.column4 like '%$(q)%'";

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * �����˻���ȡ��Ʒ����ű� ��Ӧ����ԴĿ¼�� SOASCRIPT.SOASCRIPT.ZB.FXGL.FXTH
	 * 
	 * @return
	 */
	public String FXTH() {
		String sql = "select sub.*,fa.column9 fzx from DY_661338441749388 sub,DY_661338441749389 fa where fa.STATUSINFO='01' and sub.STATUSINFO='01' and sub.fatherlsh=fa.lsh and sub.column3 like '$(q)%'";

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con, sql);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try {
				String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

	/**
	 * ���ݵ�ǰ�û����ҷ�������Ϣ SOASCRIPT.SOASCRIPT.ZB.GETFCLIENTBYUSER
	 * 
	 * @return
	 */
	public String GETFCLIENTBYUSER() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		List list = db
				.queryData(
						con,
						"SELECT column4 AS fclientcode,column3 AS fclientname FROM DY_61336130537483 WHERE column21 LIKE '%$(sr_participant)[%' ");
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
}
