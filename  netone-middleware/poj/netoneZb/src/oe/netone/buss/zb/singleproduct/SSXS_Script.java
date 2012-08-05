package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class SSXS_Script extends OeScript {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void queRen() {
		try {
			System.out.println("____________________新建___________________");
			oe.midware.workflow.engine.rule2.func.STools st = new oe.midware.workflow.engine.rule2.func.STools();
			if ("$(column4)" == null || "$(column4)" == "") {
				dy.set("$(lsh)" + ":" + "$(formcode)", "column4",
						new SimpleDateFormat("yyyy-MM-dd")
								.format(new java.util.Date()));
			}
			Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			String sql = "update DY_371337952339241 set column21=(select count(*) from DY_371337952339238 where fatherlsh='$(lsh)') where lsh='$(lsh)'";
			db.execute(con, sql);
			db.close(con);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			sql = "update DY_371337952339241 set column22=(select count(*) from DY_371337952339240 where fatherlsh='$(lsh)') where lsh='$(lsh)'";
			db.execute(con, sql);
			db.close(con);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			sql = "update DY_371337952339241 set column23=(select count(*) from DY_371337952339236 where fatherlsh='$(lsh)') where lsh='$(lsh)'";
			db.execute(con, sql);
			db.close(con);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			sql = "update DY_371337952339241 set column24=(select count(*) from DY_371337952339239 where fatherlsh='$(lsh)') where lsh='$(lsh)'";
			db.execute(con, sql);
			db.close(con);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			sql = "update DY_371337952339241 set column25=(select count(*) from DY_371337952339237 where fatherlsh='$(lsh)') where lsh='$(lsh)'";
			db.execute(con, sql);
			db.close(con);
			dy.set("$(lsh)" + ":" + "$(formcode)", "column5", "$(participant)");
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a1 = dateformat.format(new java.util.Date());
			dy.set("$(lsh)" + ":" + "$(formcode)", "column6", a1);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			String date = sdf.format(new java.util.Date());
			String code = "XS";
			String column3 = dy.get("$(lsh)" + ":" + "$(formcode)", "column3");
			List listx2 = new ArrayList();
			listx2.add(column3);
			List list2 = db
					.queryData_p(
							con,
							"SELECT IFNULL(count(*),0) as countx  FROM DY_371337952339241 WHERE column3 =? and column3 is not null ",
							listx2);
			boolean isneedNew = false;
			if (list2.size() > 0) {
				Map map = (Map) list2.get(0);
				Integer countx = Integer.valueOf(map.get("countx").toString());
				System.out.println(countx + ":isneedNew:" + isneedNew);
				if (countx.intValue() > 1) {
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
				List list = db.queryData(con,
						"SELECT max(column3) as maxcode FROM DY_371337952339241 WHERE column3 like  \'"
								+ code + date + "%\' ");
				String _code = null;
				if (list.size() > 0) {
					Map map = (Map) list.get(0);
					_code = (String) map.get("maxcode");
				}
				if (_code != null) {
					String queneNumber = org.apache.commons.lang.StringUtils
							.substring(_code, _code.length() - 3, _code
									.length());
					Integer x = new Integer(Integer.parseInt(queneNumber) + 1);
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

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db
					.queryData(
							con,
							"select t2.*,ifnull(t2.column15,0) as vcol15,t.column13 as clientId,t.STATUSINFO as status  from DY_371337952339241 t left join DY_371337952339238 t2 on t.lsh=t2.fatherlsh where  t.lsh='$(lsh)'  ");

			double sum = 0.0;
			String clientId = "";
			String status = "";
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				Double vcol15 = new Double("0");
				if (map.get("vcol15") != null) {
					vcol15 = Double.valueOf(map.get("vcol15").toString());
				}
				clientId = (String) map.get("clientId").toString();
				sum += vcol15.doubleValue();
				status = (String) map.get("status");
			}
			System.out.println("会员卡号：" + clientId);
			db.close(con);

			if (list.size() > 0) {
				System.out.println("状态：" + status);
				con = db.con("DATASOURCE.DATASOURCE.DYFORM");
				List listx = new ArrayList();
				listx.add(clientId);
				// 判断是否有旧料回收
				oe.cav.bean.logic.bus.TCsBus busx_1 = new oe.cav.bean.logic.bus.TCsBus();
				busx_1.setFatherlsh("$(lsh)");
				busx_1.setFormcode("1dde2f9fa81711e19b54fb13b166e993_");
				List listx_1 = st.dy_.queryData(busx_1, 0, 99999999, "");
				if (listx_1.size() > 0) {
					sql = "update DY_131337490209098 set column31=ifnull(column31,0)+ifnull((select sum(column19) from DY_371337952339234 where fatherlsh='$(lsh)'),0),column33=ifnull(column33,0)+1,column30=ifnull(column30,0)+ifnull((select column18*(select column5 from DY_381336140843612 where column4 = (select column19 from DY_371337952339238 where fatherlsh='$(lsh)' limit 1)) from DY_371337952339234 where fatherlsh='$(lsh)'),0)-ifnull((select sum(column19) from DY_371337952339234 where fatherlsh='$(lsh)'),0) WHERE column25='是' and column27=? ";
				} else {
					sql = "update DY_131337490209098 set column31=ifnull(column31,0)+ifnull((select sum(column19) from DY_371337952339234 where fatherlsh='$(lsh)'),0),column33=ifnull(column33,0)+1,column30=ifnull(column30,0)+ifnull((select sum(column35) from DY_371337952339238 where column36='01' and fatherlsh='$(lsh)'),0)-ifnull((select sum(column19) from DY_371337952339234 where fatherlsh='$(lsh)'),0) WHERE column25='是' and column27=? ";
				}
				db.execute_p(con, sql, listx);
				db.close(con);
				System.out.println("更新会员积分 确认成功");
			} else {
				System.out.println("更新会员积分 确认失败！已确认");
			}

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			list = db
					.queryData(
							con,
							"select t.column3 TMH from DY_371337952339238 t,DY_371337952339241 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");
			db.close(con);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			StringBuffer par = new StringBuffer();
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				par.append(split + "'" + object.get("TMH").toString() + "'");
				split = ",";
			}
			if (list.size() > 0) {
				sql = "update DY_371337952339238 set STATUSINFO='01',column22='已售出' where fatherlsh='$(lsh)'";
				db.execute(con, sql);
				db.close(con);
				con = db.con("DATASOURCE.DATASOURCE.DYFORM");
				sql = "update DY_661338441749388 set STATUSINFO='03',column24='已售出' where column3 in ("
						+ par.toString() + ") and STATUSINFO='01'";
				db.execute(con, sql);
				con = db.con("DATASOURCE.DATASOURCE.DYFORM");
				sql = "update DY_271334208897441 set column82='已售出' where column4 in ("
						+ par.toString() + ")";
				db.execute(con, sql);
			}
			db.close(con);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			sql = "update DY_371337952339241 set STATUSINFO='01' where lsh = '$(lsh)'";
			db.execute(con, sql);
			db.close(con);

			/** 主表单* */
			String lsh = "$(lsh)";

			/** 商品提醒配置* */
			oe.cav.bean.logic.bus.TCsBus busx0 = new oe.cav.bean.logic.bus.TCsBus();
			busx0.setFatherlsh("1");
			busx0.setFormcode("4313df5dc03911e19e849de029b8abb5_");
			List listx0 = st.dy_.queryData(busx0, 0, 99999999, "");
			System.out.println("长度:" + listx0.size());
			for (int i = 0; i < listx0.size(); i++) {
				oe.cav.bean.logic.bus.TCsBus x0 = (oe.cav.bean.logic.bus.TCsBus) listx0
						.get(i);
				/** * */
				String p1 = x0.getColumn3();
				/** *角色 * */
				String p2 = x0.getColumn4();
				System.out.println(p1);

				/** 销售明细表单* */
				oe.cav.bean.logic.bus.TCsBus busx = new oe.cav.bean.logic.bus.TCsBus();
				busx.setFatherlsh(lsh);
				busx.setFormcode("1dde2f9fa81711e19b54fb13b166e993_");
				List listx = st.dy_.queryData(busx, 0, 99999999, "");
				for (int j = 0; j < listx.size(); j++) {
					oe.cav.bean.logic.bus.TCsBus x = (oe.cav.bean.logic.bus.TCsBus) listx
							.get(j);
					String pcode = x.getColumn3();
					System.out.println("pcode:" + pcode);

					/** 生成商品提醒信息 */
					if (org.apache.commons.lang.StringUtils.contains(pcode, p1)) {
						oe.cav.bean.logic.bus.TCsBus bus2 = new oe.cav.bean.logic.bus.TCsBus();
						bus2.setFatherlsh("1");
						bus2.setColumn3("");
						bus2.setColumn4(lsh);
						bus2.setColumn5(pcode);
						bus2.setColumn6("," + p2 + ",");
						bus2.setFormcode("d000dcffc1ee11e19e849de029b8abb5_");
						bus2
								.setTimex((new Timestamp(System
										.currentTimeMillis()).toString()));
						bus2.setBelongx("");
						bus2.setParticipant("$(participant)");
						bus2.setCreated("");
						bus2.setLsh(java.util.UUID.randomUUID().toString()
								.replace("-", ""));
						String lshxxxx = st.dy_.addData(
								"d000dcffc1ee11e19e849de029b8abb5_", bus2);
						System.out.println("插入:" + lshxxxx);
					}

				}
			}

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list_1 = db
					.queryData(
							con,
							"select t.column3 TMH from DY_371337952339240 t,DY_371337952339241 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");
			db.close(con);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			par = new StringBuffer();
			split = "";
			for (Iterator iterator = list_1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				par.append(split + "'" + object.get("TMH").toString() + "'");
				split = ",";
			}
			if (list_1.size() > 0) {
				sql = "update DY_371337952339238 set STATUSINFO='02',column22='已销退' where column3 in ("
						+ par.toString() + ") and STATUSINFO='01'";
				db.execute(con, sql);
				db.close(con);
				con = db.con("DATASOURCE.DATASOURCE.DYFORM");
				sql = "update DY_661338441749388 set STATUSINFO='01',column24='首饰销退,已退回' where column3 in ("
						+ par.toString() + ") and STATUSINFO='03'";
				db.execute(con, sql);
			}
			db.close(con);

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list_jlhs = db

					.queryData(

							con,

							"select t.*,t2.column3 DH,t2.column4 RQ,t2.column8 FXS from DY_371337952339239 t,DY_371337952339241 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");
			db.close(con);
			boolean flag = true;

			String lshxxxxf = "";

			String lshxxxxr = "";

			for (Iterator iterator = list_jlhs.iterator(); iterator.hasNext();) {

				Map object = (Map) iterator.next();

				if (flag) {

					oe.cav.bean.logic.bus.TCsBus busr = new oe.cav.bean.logic.bus.TCsBus();

					busr.setFatherlsh("1");

					busr.setColumn3("SSHS_" + (String) object.get("DH"));

					busr.setColumn4("$(participant)");

					busr
							.setColumn5((String) (new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss")
									.format(new java.util.Date())));

					busr.setColumn6("");

					busr.setColumn8((String) object.get("RQ"));

					busr.setColumn9((String) object.get("column25"));

					busr.setColumn10("旧料回收");

					busr.setColumn12((String) object.get("FXS"));

					busr.setColumn13("$(participant)");

					busr
							.setColumn14((String) (new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss")
									.format(new java.util.Date())));

					busr.setColumn15("确认");

					busr.setStatusinfo("01");

					busr.setFormcode("668ca03cad4b11e1bbb551abdbadc425_");

					busr.setTimex((new Timestamp(System.currentTimeMillis())
							.toString()));

					busr.setBelongx("");

					busr.setParticipant("$(participant)");

					busr.setCreated("");

					busr.setLsh((String) java.util.UUID.randomUUID().toString()
							.replace("-", ""));

					lshxxxxf = st.dy_.addData(
							"668ca03cad4b11e1bbb551abdbadc425_", busr);

					flag = false;

					System.out.println("插入:" + lshxxxxf);

				}

				oe.cav.bean.logic.bus.TCsBus busz = new oe.cav.bean.logic.bus.TCsBus();

				busz.setFatherlsh(lshxxxxf);

				busz.setColumn3((String) object.get("column31"));

				busz.setColumn4((String) object.get("column23"));

				// busz.setColumn5(object.get("column5")==null?"0":object.get("column5").toString());

				busz.setColumn6((String) object.get("column22"));

				// busz.setColumn8((String)object.get("column9"));

				// busz.setColumn9((String)object.get("column10"));

				// busz.setColumn11(object.get("column12")==null?"0":object.get("column12").toString());

				busz.setColumn12(object.get("column29") == null ? "0" : object
						.get("column29").toString());

				// busz.setColumn13((String)object.get("column14"));

				// busz.setColumn14((String)object.get("column15"));

				// busz.setColumn15((String)object.get("column16"));

				// busz.setColumn16((String)object.get("column17"));

				// busz.setColumn17((String)object.get("column18"));

				busz.setColumn22((String) object.get("dl015"));

				busz.setColumn24("旧料回收商品");

				// busz.setColumn28(object.get("column24")==null?"0":object.get("column24").toString());
				//
				// busz.setColumn29(object.get("column25")==null?"0":object.get("column25").toString());
				//
				// busz.setColumn30(object.get("column26")==null?"0":object.get("column26").toString());
				//
				// busz.setColumn31(object.get("column27")==null?"0":object.get("column27").toString());
				//
				// busz.setColumn32(object.get("column28")==null?"0":object.get("column28").toString());
				//
				// busz.setColumn33(object.get("column30")==null?"0":object.get("column30").toString());
				//
				// busz.setColumn34(object.get("column32")==null?"0":object.get("column32").toString());
				//
				// busz.setColumn35(object.get("column31")==null?"0":object.get("column31").toString());
				//
				// busz.setColumn37(object.get("column33")==null?"0":object.get("column33").toString());
				//
				// busz.setColumn38(object.get("column34")==null?"0":object.get("column34").toString());

				busz.setColumn39(object.get("column20") == null ? "0" : object
						.get("column20").toString());

				// busz.setColumn40(object.get("column36")==null?"0":object.get("column36").toString());
				//
				// busz.setColumn41(object.get("column37")==null?"0":object.get("column37").toString());
				//
				// busz.setColumn43(object.get("column31")==null?"0":object.get("column31").toString());

				busz.setStatusinfo("01");

				busz.setFormcode("84bbd11aad4711e1bbb551abdbadc425_");

				busz.setTimex((new Timestamp(System.currentTimeMillis())
						.toString()));

				busz.setBelongx("");

				busz.setParticipant("$(participant)");

				busz.setCreated("");

				busz.setLsh(java.util.UUID.randomUUID().toString().replace("-",
						""));

				lshxxxxr = st.dy_.addData("84bbd11aad4711e1bbb551abdbadc425_",
						busz);

				System.out.println("插入:" + lshxxxxr);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
