package oe.teach.oescript.demo1;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

public class SoaScript2 extends OeScript {
	public static void main(String[] args) {
		// test_demo4();
		// test_demo2();
		// test_demo1();
		String startdate = "2012-02-02 00:00:00";
		java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Calendar datex = java.util.Calendar.getInstance();
		try {
			datex.setTime(DATE_FORMAT.parse(startdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		datex.set(java.util.Calendar.DATE,
				datex.get(java.util.Calendar.DATE) - 2);
		String beforeDate = DATE_FORMAT.format(datex.getTime())
				+ " 00:00:00.000";
		System.out.println(beforeDate);
	}

	/**
	 * 原始的JDBC
	 */
	public static Object test_demo1() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db.queryData(con,
				"select count(*) as cou from t_Organization");
		System.out.println(db.getl(list, 0, "cou"));
		db.close(con);
		return db.get(list, 0, "cou");
	}

	public static String test_demo2() {
		return "";
	}

	/**
	 * 客户信息 FTypeID:客户分类ID F_104:付款方式
	 */
	public static String test_demo3() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String condition = "";
		String q = "$(q)";
		if (q != null && !"".equals(q)) {
			q = q.trim();
			condition = " and (t1.FShortNumber like '%" + q
					+ "%' or t1.FName like '%" + q + "%')";
		}
		List list = db
				.queryData(
						con,
						"select t1.FItemID,t1.FNumber,t1.FTypeID,t1.FShortNumber,t1.FName,t1.FAddress,t1.FPhone,t1.FContact,t1.F_104,t2.FName from t_Organization t1 left join t_SetDL t2 on t1.F_104=t2.FItemID where t1.FDeleted=0 "
								+ condition);
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

	// 检查客户编号是否存在
	public static String checkOrgFNumberIsExist() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String q = "$(q)";
		List listx = new ArrayList();
		listx.add(q);
		List list = db
				.queryData_p(
						con,
						" select count(*) as c from t_Organization where  FShortNumber=?",
						listx);
		Map map = (Map) list.get(0);
		Integer x = (Integer) map.get("c");
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("count", x);
		return json.toString();
	}

	// 产品信息
	public static String test_demo4() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String condition = "";
		String q = "$(q)";
		if (q != null && !"".equals(q)) {
			q = q.trim();
			condition = " and (FShortNumber like '%" + q
					+ "%' or FName like '%" + q + "%')";
		}
		List list = db
				.queryData(
						con,
						"select FItemID,FModel,FName,FOrderPrice,FNumber,FShortNumber,FUnitID,FUnitGroupID,FEquipmentNum from t_ICItem where FDeleted=0 "
								+ condition);
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

	public static String test_demo44() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String condition = "";
		String q = "$(q)";
		List list = db
				.queryData(
						con,
						"select FItemID,FModel,FName,FNumber,FShortNumber,FUnitID,FUnitGroupID,FEquipmentNum from t_ICItem where FDeleted=0 and FItemID="
								+ q);
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

	// 结算期限
	public static String setstxx() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db.queryData(con,
				"select FItemID,FName,FID,FDay from t_SetDL where FDeleted=0");
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

	// 根据单位组内码查找单位 SOASCRIPT.SOASCRIPT.HG.QUERYUNITBYUNIDGROUPID
	public static String test_demo6() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String condition = "";
		String q = "$(q)";
		q = q.trim();
		condition = " and FUnitGroupID=" + q;
		List list = db.queryData(con,
				"select FMeasureUnitID,FName from t_MeasureUnit where 1=1 "
						+ condition);
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

	// 根据物料ID查找价格（从价格方案查找最后价格）
	public static String test_demo7() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String q = "$(q)";
		q = q.trim();
		StringBuffer sql = new StringBuffer();
		sql
				.append(" SELECT  top 1 FPrice from ICPrcPlyEntry where FinterID=3 and FItemid="
						+ q.split("\\|")[0]
						+ " and FRelatedID="
						+ q.split("\\|")[1]
						+ " and FUnitID="
						+ q.split("\\|")[2]
						+ " order by FEndDate desc,FBegDate desc  ");

		List list = db.queryData(con, sql.toString());
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

	// 根据物料单位ID返回计量单位系数
	public static String test_demo8() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String q = "$(q)";
		q = q.trim();
		List list = db.queryData(con,
				"select FCoefficient from t_MeasureUnit where FMeasureUnitID= "
						+ q);
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

	// 结算期限
	public static String test_demo5() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db
				.queryData(con,
						"select FItemID,FID,FName,FDay from t_SetDL where FDeleted=0 order by FName");
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

	// 促销写入K3 价格策略 过时
	public static void xxx() {
		String runtimeid = "";

		String bussid = wf.get(runtimeid, "bussid");
		if (!"".equals(bussid)) {
			String formcode = "7806a31e97f811e1b01667a74cfdf95c_";
			TCsBus bus = new TCsBus();
			bus.setFatherlsh("1");
			bus.setLsh(bussid);
			List list = dy.queryData(formcode, bus, 0, 1, "");
			String clientId = null;
			String ptypeid = null;
			if (list.size() > 0) {
				TCsBus data = (TCsBus) list.get(0);
				/** 客户ID */
				clientId = data.getColumn10();
				if ("".equals(clientId)) {
					System.err.println("客户ID为空");
					return;
				}
				/** 客户分类ID */
				ptypeid = data.getColumn11();
				if ("".equals(ptypeid)) {
					System.err.println("客户分类ID为空");
					return;
				}
				TCsBus bus2 = new TCsBus();
				bus2.setFatherlsh(bussid);
				List listx = dy.queryData("5fe85cb097f911e1b01667a74cfdf95c_",
						bus2, 0, 999999999, "");
				for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
					TCsBus object = (TCsBus) iterator.next();
					/**
					 * 19 物料ID 5单价 18单位 6+10 现折 7+11 期折 8+12 搭赠 9+20 其他 15备注
					 * 厂家预定销量 字段不明 不知写哪个字段 14 业务抽成 16 销货量(从) 17 销货量（到）
					 */
					Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
					StringBuffer sql = new StringBuffer();
					sql
							.append(" SELECT  top 1 FEntryID,FBegDate,FEndDate from ICPrcPlyEntry where FinterID=3 and FItemid="
									+ object.getColumn19()
									+ " and FRelatedID="
									+ ptypeid
									+ " and FUnitID="
									+ object.getColumn5()
									+ " order by FEndDate desc,FBegDate desc  ");
					List listprcply = db.queryData(con, sql.toString());
					Map map = (Map) listprcply.get(0);
					String FEntryID = (String) map.get("FEntryID");

					String startdate = "2013-01-01";
					java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
							"yyyy-MM-dd");
					java.util.Calendar datex = java.util.Calendar.getInstance();
					datex.set(java.util.Calendar.DATE, datex
							.get(java.util.Calendar.DATE) - 1);
					try {
						datex.setTime(DATE_FORMAT.parse(startdate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String beforeDate = DATE_FORMAT.format(datex.getTime())
							+ " 00:00:00.000";

					Double v1 = Double.valueOf(object.getColumn6().toString())
							+ Double.valueOf(object.getColumn10().toString());
					Double v2 = Double.valueOf(object.getColumn7().toString())
							+ Double.valueOf(object.getColumn11().toString());
					Double v3 = Double.valueOf(object.getColumn8().toString())
							+ Double.valueOf(object.getColumn12().toString());
					Double v4 = Double.valueOf(object.getColumn9().toString())
							+ Double.valueOf(object.getColumn20().toString());
					Double v5 = Double.valueOf(object.getColumn14().toString());
					Double v6 = Double.valueOf(object.getColumn16().toString());
					Double v7 = Double.valueOf(object.getColumn17().toString());
					/**
					 * 写入价格政策表 FEntryID为自增长主键 FInterID:方案ID 2:客户价格;3:客户类别价 int
					 * FItemID:客户价格为物料IDint FRelatedID:客户ID或客户分类ID select
					 * finterID from t_submessage where
					 * FTypeID=501;t_Organization:FTypeID int FUnitID:单位ID
					 * select FMeasureUnitID from t_MeasureUnit; int
					 * FCuryID:货币ID 默认为1 人民币 int FPrice:价格 dicimal(28,10)
					 * FBegDate:报备时间 datetime FEndDate:结束时间 datetime
					 * FChecked:是否检查 默认为1 bit FIndex：排序号 int FNote：备注 默认为OA写入
					 * nvarchar(255)
					 * 
					 * FBegQty 销货量(从) FEndQty 销货量（到）
					 * 
					 * fare1pg：瓶盖 默认为NULL dicimal(28,10) fare2xf：现返 默认为NULL
					 * dicimal(28,10) fare3qf：期返 默认为NULL dicimal(28,10)
					 * fare4cs：促销 默认为NULL dicimal(28,10) fare5dz：搭赠 默认为NULL
					 * dicimal(28,10) fare6zc：专场 默认为NULL dicimal(28,10)
					 * fare7qd：青岛终端 默认为NULL dicimal(28,10) fare8kp：开票 默认为NULL
					 * dicimal(28,10) fare9bz：包装 默认为NULL dicimal(28,10)
					 * fare10qt：其他 默认为NULL dicimal(28,10) fare11yw：业务员抽成 默认为NULL
					 * dicimal(28,10) fare12zj：总计 默认为NULL dicimal(28,10)
					 * fare13dzfa：捐赠方案 默认为NULL nchar(200)
					 */
					String sql1 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,fare2xf,fare3qf,fare5dz,fare10qt,fare11yw,FBegQty,FEndQty) values (3,"
							+ object.getColumn19()
							+ ","
							+ ptypeid
							+ ","
							+ object.getColumn18()
							+ ",1,"
							+ object.getColumn5()
							+ ",'"
							+ beforeDate
							+ "','2100-01-01 00:00:00.000',1,1,"
							+ object.getColumn15()
							+ "+'[OA引入]',"
							+ v1
							+ ","
							+ v2
							+ ","
							+ v3
							+ ","
							+ v4
							+ ","
							+ v5
							+ ","
							+ v6
							+ "," + v7 + ");";
					String sql2 = "update ICPrcPlyEntry set FEndDate='"
							+ beforeDate
							+ "',FNote=FNote+'[OA更新]' where FEntryID="
							+ FEntryID;

					db.execute(con, sql1);
					db.execute(con, sql2);

					db.close(con);

				}
			}

		} else {
			System.err.print("bussid为空!");
		}

	}

	// 促销写入K3 价格策略
	public static void xxx2() {
		String runtimeid = "297ea5d037992ca601379cfc336500a8";

String bussid = wf.get(runtimeid, "bussid");
if (!"".equals(bussid)) {
	String formcode = "BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733786";
	TCsBus bus = new TCsBus();
	bus.setFatherlsh("1");
	bus.setLsh(bussid);
	List list = dy.queryData(formcode, bus, 0, 1, "");
	String clientId = null;
	String ptypeid = null;
	if (list.size() > 0) {
		TCsBus data = (TCsBus) list.get(0);
		/** 客户ID */
		clientId = data.getColumn10();
		if ("".equals(clientId)) {
			System.err.println("客户ID为空");
			return;
		}
		/** 客户分类ID */
		ptypeid = data.getColumn11();
		if ("".equals(ptypeid)) {
			System.err.println("客户分类ID为空");
			return;
		}
		TCsBus bus2 = new TCsBus();
		bus2.setFatherlsh(bussid);
		List listx = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733787", bus2, 0,
				999999999, "");

		TCsBus bus3 = new TCsBus();
		bus3.setFatherlsh(bussid);
		List listx2 = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733788", bus3, 0,
				1, "");
		TCsBus data2 = (TCsBus) listx2.get(0);
		/** 促销开始时间 */
		String startdate = data2.getColumn7();
		String enddate = data2.getColumn8();

		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		Connection querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			/**
			 * 19 物料ID 5单价 18单位 6+10 现折 7+11 期折 8+12 搭赠 9+20 其他 15备注
			 * 厂家预定销量 字段不明 不知写哪个字段 14 业务抽成 16 销货量(从) 17 销货量（到）
			 */
			StringBuffer sql = new StringBuffer();
			sql
					.append(" SELECT  top 1 FEntryID,FPrice,FBegDate,FEndDate from ICPrcPlyEntry where FinterID=3 and FItemid="
							+ object.getColumn19()
							+ " and FRelatedID="
							+ ptypeid
							+ " and FUnitID="
							+ object.getColumn18()
							+ " order by FEndDate desc,FBegDate desc  ");
			querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List listprcply = db.queryData(querycon, sql.toString());
			Map map = (Map) listprcply.get(0);
			Integer FEntryID = (Integer) map.get("FEntryID");
			java.math.BigDecimal PrePrice = (java.math.BigDecimal) map
					.get("FPrice");

			java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Calendar datex = java.util.Calendar.getInstance();
			try {
				datex.setTime(DATE_FORMAT.parse(startdate));
				datex.set(java.util.Calendar.DATE, datex
						.get(java.util.Calendar.DATE) - 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String beforeDate = DATE_FORMAT.format(datex.getTime())
					+ " 00:00:00.000";

			java.util.Calendar datex2 = java.util.Calendar
					.getInstance();
			try {
				datex2.setTime(DATE_FORMAT.parse(enddate));
				datex2.set(java.util.Calendar.DATE, datex2
						.get(java.util.Calendar.DATE) + 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String afterDate = DATE_FORMAT.format(datex2.getTime())
					+ " 00:00:00.000";

			Double v1 = Double
					.valueOf(object.getColumn6() == null ? "0" : object
							.getColumn6().toString())
					+ Double.valueOf(object.getColumn10() == null ? "0"
							: object.getColumn10().toString());
			Double v2 = Double
					.valueOf(object.getColumn7() == null ? "0" : object
							.getColumn7().toString())
					+ Double.valueOf(object.getColumn11() == null ? "0"
							: object.getColumn11().toString());
			Double v3 = Double
					.valueOf(object.getColumn8() == null ? "0" : object
							.getColumn8().toString())
					+ Double.valueOf(object.getColumn12() == null ? "0"
							: object.getColumn12().toString());
			Double v4 = Double
					.valueOf(object.getColumn9() == null ? "0" : object
							.getColumn9().toString())
					+ Double.valueOf(object.getColumn20() == null ? "0"
							: object.getColumn20().toString());
			// Double v5 =
			// Double.valueOf(object.getColumn14().toString());
			Double v6 = Double
					.valueOf(object.getColumn16() == null ? "0"
							: object.getColumn16().toString());
			Double v7 = Double
					.valueOf(object.getColumn17() == null ? "0"
							: object.getColumn17().toString());
			/** 价格 */
			Double vPrice = Double
					.valueOf(object.getColumn19() == null ? "0"
							: object.getColumn19().toString());
			/**
			 * 写入价格政策表 FEntryID为自增长主键 FInterID:方案ID 2:客户价格;3:客户类别价 int
			 * FItemID:客户价格为物料IDint FRelatedID:客户ID或客户分类ID select
			 * finterID from t_submessage where
			 * FTypeID=501;t_Organization:FTypeID int FUnitID:单位ID
			 * select FMeasureUnitID from t_MeasureUnit; int
			 * FCuryID:货币ID 默认为1 人民币 int FPrice:价格 dicimal(28,10)
			 * FBegDate:报备时间 datetime FEndDate:结束时间 datetime
			 * FChecked:是否检查 默认为1 bit FIndex：排序号 int FNote：备注 默认为OA写入
			 * nvarchar(255)
			 * 
			 * FBegQty 销货量(从) FEndQty 销货量（到）
			 * 
			 * fare1pg：瓶盖 默认为NULL dicimal(28,10) fare2xf：现返 默认为NULL
			 * dicimal(28,10) fare3qf：期返 默认为NULL dicimal(28,10)
			 * fare4cs：促销 默认为NULL dicimal(28,10) fare5dz：搭赠 默认为NULL
			 * dicimal(28,10) fare6zc：专场 默认为NULL dicimal(28,10)
			 * fare7qd：青岛终端 默认为NULL dicimal(28,10) fare8kp：开票 默认为NULL
			 * dicimal(28,10) fare9bz：包装 默认为NULL dicimal(28,10)
			 * fare10qt：其他 默认为NULL dicimal(28,10) fare11yw：业务员抽成 默认为NULL
			 * dicimal(28,10) fare12zj：总计 默认为NULL dicimal(28,10)
			 * fare13dzfa：捐赠方案 默认为NULL nchar(200)
			 */
			List list1 = new ArrayList();
			list1.add(3);
			list1.add((vPrice - v1 - v2 - v3 - v4));
			list1.add(ptypeid);
			list1.add(object.getColumn18());
			list1.add(1);
			list1.add(object.getColumn5());
			list1.add(startdate);
			list1.add(enddate);
			list1.add(1);
			list1.add(1);
			list1.add((object.getColumn15() == null ? "" : object
					.getColumn15())
					+ "[OA引入]");
			list1.add(v6);
			list1.add(v7);
			String sql1 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			List list2 = new ArrayList();
			list2.add(beforeDate);
			list2.add("[OA更新]");
			list2.add(FEntryID);
			String sql2 = "update ICPrcPlyEntry set FEndDate=?,FNote=FNote+? where FEntryID=?";

			List list3 = new ArrayList();
			list3.add(3);
			list3.add(object.getColumn19());
			list3.add(ptypeid);
			list3.add(object.getColumn18());
			list3.add(1);
			list3.add(PrePrice);
			list3.add(afterDate);
			list3.add("2100-01-01 00:00:00.000");
			list3.add(1);
			list3.add(1);
			list3.add((object.getColumn15() == null ? "" : object
					.getColumn15())
					+ "[OA引入]");
			list3.add(v6);
			list3.add(v7);
			String sql3 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			db.execute_p(con, sql1, list1);
			db.execute_p(con, sql2, list2);
			db.execute_p(con, sql3, list3);
		}
		db.close(con);
	}

} else {
	System.err.print("bussid为空!");
}

	}

	// 新品写入K3 价格策略
	public static void xxx3() {
		String runtimeid = "297ea5d037992ca601379cde8f5c0085";

String bussid = wf.get(runtimeid, "bussid");
if (!"".equals(bussid)) {
	String formcode = "BUSSFORM.BUSSFORM.BNHGYW.DY_571336475650683";
	TCsBus bus = new TCsBus();
	bus.setFatherlsh("1");
	bus.setLsh(bussid);
	List list = dy.queryData(formcode, bus, 0, 1, "");
	String clientId = null;
	String ptypeid = null;
	String beginDate = null;
	if (list.size() > 0) {
		TCsBus data = (TCsBus) list.get(0);
		/** 客户ID */
		clientId = data.getColumn10();
		if ("".equals(clientId)) {
			System.err.println("客户ID为空");
			return;
		}
		/** 客户分类ID */
		ptypeid = data.getColumn11();
		if ("".equals(ptypeid)) {
			System.err.println("客户分类ID为空");
			return;
		}
		beginDate = data.getColumn3();

		TCsBus bus2 = new TCsBus();
		bus2.setFatherlsh(bussid);
		List listx = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_571336475650684", bus2, 0,
				999999999, "");

		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			/** 价格 */
			Double vPrice = Double.valueOf(object.getColumn8()
					.toString());

			List list3 = new ArrayList();
			list3.add(3);
			list3.add(object.getColumn4());
			list3.add(ptypeid);
			/** 单位 */
			list3.add(object.getColumn13());
			list3.add(1);
			list3.add(vPrice);
			list3.add(beginDate);
			list3.add("2100-01-01 00:00:00.000");
			list3.add(1);
			list3.add(1);
			list3.add((object.getColumn12() == null ? "" : object
					.getColumn12())
					+ "[OA引入新品]");
			list3.add(0);
			list3.add(0);
			String sql3 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			db.execute_p(con, sql3, list3);

		}
		db.close(con);
	}

} else {
	System.err.print("bussid为空!");
}

	}

	// 新客户
	public static void xxx4() {
		String runtimeid = "";

String bussid = wf.get(runtimeid, "bussid");
if (!"".equals(bussid)) {
	String formcode = "BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789";
	oe.cav.bean.logic.bus.TCsBus bus = new oe.cav.bean.logic.bus.TCsBus();
	bus.setFatherlsh("1");
	bus.setLsh(bussid);
	List list = dy.queryData(formcode, bus, 0, 1, "");
	if (list.size() > 0) {
		oe.cav.bean.logic.bus.TCsBus data = (oe.cav.bean.logic.bus.TCsBus) list
				.get(0);
		/** 客户编号 */
		String column3 = data.getColumn3();
		/** 客户名称* */
		String column4 = data.getColumn4();
		/** 公司地址 */
		String column5 = data.getColumn5();
		/** 客户层级 */
		String column6 = data.getColumn6();
		/** 联系人 */
		String column12 = data.getColumn12();
		/** 联系电话 */
		String column13 = data.getColumn13();
		/** 备注 */
		String column19 = data.getColumn19();
		/** 分属部门 */
		String column21 = data.getColumn21();
		/** 付款方式 */
		String column22 = data.getColumn22();
		/** 客户分类 */
		String column23 = data.getColumn23();

		if ("".equals(column3)) {
			System.err.println("客户编号不能为空!");
			return;
		}

		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		Connection querycon = db.con("DATASOURCE.DATASOURCE.HGDB");

		List x = db.queryData(querycon,
				"select * from t_item where FItemID=" + column6);
		String FFullNumber = (String) db.get(x, 0, "FFullNumber");
		Integer FLevel = db.getn(x, 0, "FLevel");

		/** t_item */
		List listx = new ArrayList();
		listx.add(1);
		listx.add(column3);
		listx.add(column6);
		listx.add(FLevel + 1);
		listx.add(1);
		listx.add(column4);
		listx.add(FFullNumber + "." + column3);
		listx.add(0);
		listx.add(column3);
		String sql1 = "insert into t_item (FItemClassID,FNumber,FParentID,FLevel,FDetail,FName,FFullNumber,FDeleted,FShortNumber) values (?,?,?,?,?,?,?,?,?)";
		db.execute_p(con, sql1, listx);

		/** 客户表 */
		List listx2 = new ArrayList();
		listx2.add(column3);
		querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
		List x1 = db.queryData_p(querycon,
				"select FItemID from t_item where FNumber=?", listx2);
		Integer FItemID = db.getn(x1, 0, "FItemID");

		/** 付款方式 */
		List listxx = new ArrayList();
		listxx.add(column22);
		querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
		List x2 = db
				.queryData_p(
						querycon,
						"select FItemID,FID,FName,FDay from t_SetDL where FItemID=?",
						listxx);
		String FName = (String) db.get(x2, 0, "FName");

		String sql2 = "insert into t_Organization (FItemID,FAddress,FName,FShortNumber,FNumber,FParentID,FPhone,FContact,FDepartment,Femployee,FDeleted,FTypeID,FStatus,FRegionID,FTrade,FCyID,FSetID,FRegion,F_104,F_109) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List listx3 = new ArrayList();
		listx3.add(FItemID);
		listx3.add(column5);
		listx3.add(column4);
		listx3.add(column3);
		listx3.add(FFullNumber + "." + column3);
		listx3.add(column6);
		listx3.add(column13);
		listx3.add(column12);
		listx3.add(column21);
		/** 负责人 */
		listx3.add(-3);
		listx3.add(0);
		listx3.add(column23);
		listx3.add(1072);
		listx3.add(0);
		listx3.add(0);
		listx3.add(0);
		listx3.add(0);
		listx3.add(0);
		listx3.add(column22);
		listx3.add(FName);

		db.execute_p(con, sql2, listx3);

		db.close(con);
		db.close(querycon);
	} else {
		System.err.print("无数据!");
	}

} else {
	System.err.print("bussid为空!");
}

	}

	/** 客户 */
	/** select * from t_item where FItemClassID=1 and FDeleted=0;* */
	/** 只有FDetail=1的记录 */
	/** select * from t_Organization where FDeleted=0;* */

	// 客户分类
	public String getClientCate() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db
				.queryData(
						con,
						"select * from t_item where FItemClassID=1 and FDeleted=0 and fDetail=0 order by FFullNumber;");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Integer FLevel = (Integer) map.get("FLevel");
			StringBuffer str = new StringBuffer();
			if (FLevel > 1) {
				for (int j = 0; j < FLevel; j++) {
					str.append("-");
				}
			}
			map.put("FName", str.toString() + map.get("FName"));
			String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";

	}

	// 获取辅助资料 FTypeID：501:客户分类
	public String getSubmessageInfo() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		String q = "$(q)";
		List list = db.queryData(con,
				"select * from t_submessage where FTypeID=" + q);
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

	// 获取客户级别
	public String getClientLevel() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db
				.queryData(
						con,
						"select * from t_item where FItemClassID=1 and FDeleted=0 and fDetail=0 order by FFullNumber;");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Integer FLevel = (Integer) map.get("FLevel");
			StringBuffer str = new StringBuffer();
			if (FLevel > 1) {
				for (int j = 0; j < FLevel; j++) {
					str.append("-");
				}
			}
			map.put("FName", str.toString() + map.get("FName"));
			String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";

	}

	// 获取部门级别
	public String getDeptLevel() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = db
				.queryData(
						con,
						"select * from t_item where FItemClassID=2 and FDeleted=0 and fDetail=0 order by FFullNumber;");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Integer FLevel = (Integer) map.get("FLevel");
			StringBuffer str = new StringBuffer();
			if (FLevel > 1) {
				for (int j = 0; j < FLevel; j++) {
					str.append("-");
				}
			}
			map.put("FName", str.toString() + map.get("FName"));
			String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";

	}

	public void test() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = new ArrayList();

		list.add(1);
		list.add(java.util.UUID.randomUUID().toString());
		list.add(7155);
		list.add(1);
		list.add(1);
		list.add("111");

		db
				.execute_p(
						con,
						"insert into t_item (FItemClassID,FNumber,FParentID,FDetail,FLevel,FName) values (?,?,?,?,?,?)",
						list);

		db.close(con);

	}

	public void test2() {
		String runtimeid = "";

		String bussid = wf.get(runtimeid, "bussid");
		String userinfo = wf.get(runtimeid, "customer");
		String usercode = org.apache.commons.lang.StringUtils.substringBetween(
				userinfo, "[", "]");
		String username = org.apache.commons.lang.StringUtils.substringBefore(
				userinfo, "[");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String currdate = sdf.format(new java.util.Date());

		TCsBus bus = new TCsBus();
		bus.setFatherlsh("1");
		bus.setLsh(bussid);
		// 业务主管
		List list = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column10", username, object);
			dy.setIn("column11", currdate, object);
			dy.submit(object);
		}

		// 负责人
		List list1 = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column14", username, object);
			dy.setIn("column18", currdate, object);
			dy.submit(object);
		}

		// 总经理
		List list2 = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column9", username, object);
			dy.setIn("column20", currdate, object);
			dy.submit(object);
		}
	}

	// 核销提醒功能
	public String testx() {
Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

List list = db
		.queryData(
				con,
				"select t2.column3,t2.lsh from DY_991336361733788 t left join DY_991336361733786 t2 on t.fatherlsh=t2.lsh where date_sub(date(t.column9),interval 3 day) = date(now()) ");

db.close(con);
java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
String split = "";
for (int i = 0; i < list.size(); i++) {
	Map map = (Map) list.get(i);

	map
			.put(
					"url",
					"<div><a href='frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.HGMY.11111111&lsh="
							+ map.get("lsh")
							+ "' target='_blank'>"
							+ map.get("column3") + "</a></div>");

	String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
	jsonBuffer.append(split);
	jsonBuffer.append(jsonStr);
	split = ",";
}
return "[" + jsonBuffer.toString() + "]";

	}
	
	//促销
	public String getCUXIAO(){
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		
		/*-
		 * 构造单号 以时间的年月日后留3位
		 */
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		"yyyy-MM-dd");
String date = sdf.format(new java.util.Date());
String code="CX";

		List listx = new ArrayList();
		listx.add(code + date);
		List list = db
				.queryData_p(
						con,
						"SELECT max(column3) FROM DY_991336361733786 WHERE column3 LIKE '?%'",listx);
		String _code = null;
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 3, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1位补2个0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2位补1个0
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {// New
			_code = date + "001";
		}
		code += _code;
		

		db.close(con);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			map
					.put(
							"url",
							"<div><a href='frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.HGMY.11111111&lsh="
									+ map.get("lsh")
									+ "' target='_blank'>"
									+ map.get("column3") + "</a></div>");

			String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		return "[" + jsonBuffer.toString() + "]";		
	}
}
