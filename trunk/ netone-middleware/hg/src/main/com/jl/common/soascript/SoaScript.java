package com.jl.common.soascript;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;

public class SoaScript extends OeScript {
	public static void main(String[] args) throws Exception {
		// test_demo4();
		// test_demo2();
		// test_demo1();
		// String startdate = "2012-02-02 00:00:00";
		// java.text.SimpleDateFormat DATE_FORMAT = new
		// java.text.SimpleDateFormat(
		// "yyyy-MM-dd");
		// java.util.Calendar datex = java.util.Calendar.getInstance();
		// try {
		// datex.setTime(DATE_FORMAT.parse(startdate));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// datex.set(java.util.Calendar.DATE,
		// datex.get(java.util.Calendar.DATE) - 2);
		// String beforeDate = DATE_FORMAT.format(datex.getTime())
		// + " 00:00:00.000";
		// System.out.println(beforeDate);

		listtable1();
		listtable2();
		listtable3();
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

	// 产品信息 SOASCRIPT.SOASCRIPT.HG.PRODUCT
	public static String PRODUCT() {
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
						"select FItemID,FModel,FName,FOrderPrice,FNumber,FShortNumber,FUnitID,FUnitGroupID,FEquipmentNum from t_ICItem where FDeleted=0  and FItemID in ( select distinct FItemID from ICPrcPlyEntry where finterid=3  and FRelatedID=$(sr_FRelatedID))"
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

	// 新品产品信息 SOASCRIPT.SOASCRIPT.HG.NEWPRODUCT
	public static String NEWPRODUCT() {
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
						"select FItemID,FModel,FName,FOrderPrice,FNumber,FShortNumber,FUnitID,FUnitGroupID,F105 from t_ICItem where FDeleted=0 and FItemID not in (select distinct t2.FItemID from ICStockBill t1 left join ICstockBillEntry t2 on t1.FInterID=t2.FInterID where t1.FSupplyID=$(sr_supplyid)) "
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
		List list = db
				.queryData(con,
						"select FItemID,FName,FID,FDay from t_SetDL where FDeleted=0 order by FName ");
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

		java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Calendar datex = java.util.Calendar.getInstance();
		datex.setTime(new java.util.Date());
		String currDate = DATE_FORMAT.format(datex.getTime()) + " 00:00:00.000";

		StringBuffer sql2 = new StringBuffer();
		sql2
				.append(" SELECT  top 1 FPrice from ICPrcPlyEntry where FinterID=3 and FItemid="
						+ q.split("\\|")[0]
						+ " and FRelatedID="
						+ q.split("\\|")[1]
						+ " and FUnitID="
						+ q.split("\\|")[2]
						+ " and FBegDate>='"
						+ currDate
						+ "' and FEndDate<='"
						+ currDate
						+ "' order by FEndDate desc,FBegDate desc  ");

		Connection con2 = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list2 = db.queryData(con2,
				" SELECT  count(*) as cou from ICPrcPlyEntry where FinterID=3 and FItemid="
						+ q.split("\\|")[0] + " and FRelatedID="
						+ q.split("\\|")[1] + " and FUnitID="
						+ q.split("\\|")[2] + " and FBegDate>='" + currDate
						+ "' and FEndDate<='" + currDate + "' ");
		int x = db.getn(list2, 0, "cou");

		db.close(con2);

		List list = new ArrayList();
		if (x > 0) {
			list = db.queryData(con, sql2.toString());
		} else {
			list = db.queryData(con, sql.toString());
		}

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

	// 促销写入K3 价格策略 过时
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
					} catch (java.text.ParseException e) {
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
					} catch (java.text.ParseException e) {
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
							.valueOf(object.getColumn5() == null ? "0" : object
									.getColumn5().toString());
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
					list1.add(object.getColumn19());
					list1.add(ptypeid);
					list1.add(object.getColumn18());
					list1.add(1);
					list1.add((vPrice - v1 - v2 - v3 - v4));
					System.out
							.println("价格:"
									+ (vPrice + "-" + v1 + "-" + v2 + "-" + v3
											+ "-" + v4));
					list1.add(startdate);
					list1.add(enddate);
					list1.add(1);
					list1.add(1);
					list1.add((object.getColumn15() == null ? "" : object
							.getColumn15())
							+ "[OA促销引入]");
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

	// 促销写入K3 价格策略
	public static void CX_manage() {
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
				// clientId = data.getColumn10();
				// if ("".equals(clientId)) {
				// System.err.println("客户ID为空");
				// return;
				// }
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
							.valueOf(object.getColumn5() == null ? "0" : object
									.getColumn5().toString());
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
					list1.add(8);
					list1.add(object.getColumn19());
					list1.add(ptypeid);
					list1.add(object.getColumn18());
					list1.add(1);
					list1.add((vPrice - v1 - v2 - v3 - v4));
					list1.add(startdate);
					list1.add(enddate);
					list1.add(1);
					list1.add(1);
					list1.add((object.getColumn15() == null ? "" : object
							.getColumn15())
							+ "[OA促销引入]");
					list1.add(v6);
					list1.add(v7);
					String sql1 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

					db.execute_p(con, sql1, list1);
				}
				db.close(con);
			}

		} else {
			System.err.print("bussid为空!");
		}
		Connection cons = db.con("DATASOURCE.DATASOURCE.DYFORM");

		String sql111 = "UPDATE dyform.DY_991336361733786 SET STATUSINFO='01' WHERE lsh='"
				+ bussid + "';";

		db.execute(cons, sql111);
		db.close(cons);
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

				String sql2 = "insert into t_Organization (FItemID,FAddress,FName,FShortNumber,FNumber,FParentID,FPhone,FContact,FDepartment,Femployee,FDeleted,FTypeID,FStatus,FRegionID,FTrade,FCyID,FSetID,FRegion,F_104,F_109,FARAccountID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
				listx3.add(1347);

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
						"select t2.column3,t2.lsh from DY_991336361733788 t left join DY_991336361733786 t2 on t.fatherlsh=t2.lsh where t2.STATUSINFO='00' and t.column9 is not null and DATE_SUB(DATE(t.column9),INTERVAL 3 DAY) <=  DATE(NOW())  ");

		db.close(con);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			map
					.put(
							"url",
							"<div><a href='frame.do?method=onEditViewMain&hx=1&naturalname=APPFRAME.APPFRAME.HGMY.11111111&lsh="
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

	// 促销
	public String getCUxxxxxxxxxxxxXIAO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "CX";

		List listx = new ArrayList();
		listx.add(code + date);
		List list = db.queryData(con,
				"SELECT column3 as maxcode FROM DY_991336361733786 where column3 like  \'"
						+ code + date + "%\' ");
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			String jsonStr = net.sf.json.JSONObject.fromObject(map).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		db.close(con);
		System.out.println(jsonBuffer.toString());
		return "[" + jsonBuffer.toString() + "]";
	}

	// 促销
	public String getCUXIAO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "CX";

		List list = db.queryData(con,
				"SELECT max(column3) as maxcode FROM DY_991336361733786 WHERE column3 like  \'"
						+ code + date + "%\' ");
		String _code = null;
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			_code = (String) map.get("maxcode");
		}
		if (_code != null) {
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
			/** NEW */
			_code = date + "001";
		}
		code += _code;
		db.close(con);
		return "{\"code\":\"" + code + "\"}";
	}

	// 新品
	public String getXinpin() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "XP";

		List listx = new ArrayList();
		listx.add(code + date);
		List list = db.queryData(con,
				"SELECT max(column4) as maxcode FROM DY_571336475650683 WHERE column4 like  \'"
						+ code + date + "%\'");
		String _code = null;
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			_code = (String) map.get("maxcode");
		}
		if (_code != null) {
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
			/** NEW */
			_code = date + "001";
		}
		code += _code;
		db.close(con);
		return "{\"code\":\"" + code + "\"}";
	}

	public void updateCUXIAOCode() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "CX";
		String column3 = dy.get("$(lsh)" + ":" + "$(formcode)", "column3");
		List listx2 = new ArrayList();
		listx2.add(column3);
		List list2 = db
				.queryData_p(
						con,
						"SELECT IFNULL(count(*),0) as countx  FROM DY_991336361733786 WHERE column3 =? and column3 is not null ",
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
			List list = db.queryData(con,
					"SELECT max(column3) as maxcode FROM DY_991336361733786 WHERE column3 like  \'"
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

	public void updateXinpinCode() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String code = "XP";
		String column4 = dy.get("$(lsh)" + ":" + "$(formcode)", "column4");
		List listx2 = new ArrayList();
		listx2.add(column4);
		List list2 = db
				.queryData_p(
						con,
						"SELECT IFNULL(count(*),0) as countx FROM DY_571336475650683 WHERE column4 =? and column4 is not null ",
						listx2);
		boolean isneedNew = false;
		if (list2.size() > 0) {
			Map map = (Map) list2.get(0);
			Integer countx = (Integer) map.get("countx");
			if (countx > 1) {
				isneedNew = true;
			}
		} else {
			isneedNew = true;
		}
		if (isneedNew) {
			List listx = new ArrayList();
			listx.add(code + date);
			con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db.queryData(con,
					"SELECT max(column4) as maxcode FROM DY_571336475650683 WHERE column4 like  \'"
							+ code + date + "%\'");
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
			dy.set("$(lsh)" + ":" + "$(formcode)", "column4", code);
		}
		db.close(con);
	}

	// 新品进场
	public static void listtable1() throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// 报表管理器
		ReportManager rm = new ReportManager();
		// 获得原始数据表格
		Table t = new Table();

		List x = new ArrayList();
		String lsh = "d8bf0c2580554d5d846cdfdb0bad6fe1";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"SELECT * from DY_571336475650683 where lsh='" + lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(9);
		TableCell htc = new TableCell("新品进场价格申请表", Rectangle.ALIGN_CENTER);
		htc.setColSpan(9);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);
		t.addRow(thr);

		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column7");
			mcol6 = (String) map.get("column8");
			mcol7 = (String) map.get("column9");
		}

		// 0
		TableRow tr0 = new TableRow();
		if (org.apache.commons.lang.StringUtils.isNotEmpty(mcol1)) {
			mcol1 = com.jl.common.TimeUtil.formatDate(com.jl.common.TimeUtil
					.parseDate(mcol1), "yyyy年MM月dd日");
		}

		TableCell tc0 = new TableCell("申请时间:" + mcol1);
		tc0.setColSpan(5);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc4 = new TableCell("公司方案编号:" + mcol2);
		tc4.setColSpan(4);
		tr0.addCell(tc4);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		t.addRow(tr0);

		// 00
		TableRow thr0 = new TableRow(9);
		TableCell htc0 = new TableCell("单位:元/箱", Rectangle.ALIGN_RIGHT);
		htc0.setColSpan(9);
		thr0.setCell(0, htc0);
		thr0.setType(MY_RIGHT_STYLE1);
		t.addRow(thr0);

		// 1
		TableRow tr1 = new TableRow();

		TableCell tc00_ = new TableCell("网点", Rectangle.ALIGN_CENTER);
		tc00_.setAlign(tc00_.ALIGN_CENTER);
		tc00_.setCssClass(MY_DATA_STYLE2);
		tc00_.setRowSpan(2);
		tr1.addCell(tc00_);

		TableCell tc00 = new TableCell("名称:" + mcol3);
		tc00.setColSpan(4);
		tr1.addCell(tc00);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		TableCell tc44 = new TableCell("地址:" + mcol4);
		tc44.setColSpan(4);
		tr1.addCell(tc44);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		tr1.setType(MY_DATA_STYLE1);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc000_ = new TableCell("网点");
		tc000_.setAlign(tc000_.ALIGN_CENTER);
		tc000_.setIsHidden(true);
		tr2.addCell(tc000_);

		TableCell tc000 = new TableCell("联系人:" + mcol5);
		tc000.setColSpan(2);
		tr2.addCell(tc000);
		tr2.addCell(hiddencell);

		TableCell tc444 = new TableCell("联系方式:" + mcol6);
		tc444.setColSpan(3);
		tr2.addCell(tc444);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc666 = new TableCell("结款方式:" + getSETTLEMENTPERIOD(mcol7));
		tc666.setColSpan(3);
		tr2.addCell(tc666);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.setType(MY_DATA_STYLE1);
		t.addRow(tr2);

		con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listx = db.queryData(con,
				"SELECT * from DY_571336475650684 where fatherlsh='" + lsh
						+ "' ");
		db.close(con);

		TableRow trx = new TableRow();
		trx.setType(MY_HEADER_STYLE1);
		trx.addCell(new TableCell("新增品种", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("规格", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("条形码", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("出场价", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("开单价", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("业务员抽成", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("促销员抽成", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("公司毛利率", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("备注", Rectangle.ALIGN_CENTER));
		t.addRow(trx);

		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String col3 = (String) object.get("column3");
			String col5 = (String) object.get("column5");
			String col6 = (String) object.get("column6");
			String col7 = (String) object.get("column7").toString();
			String col8 = (String) object.get("column8").toString();
			String col9 = (String) object.get("column9").toString();
			String col10 = (String) object.get("column10").toString();
			String col11 = (String) object.get("column11").toString();
			String col12 = (String) object.get("column12");

			TableRow tr = new TableRow();
			tr.addCell(new TableCell(col3));
			tr.addCell(new TableCell(col5));
			tr.addCell(new TableCell(col6));
			tr.addCell(new TableCell(col7));
			tr.addCell(new TableCell(col8));
			tr.addCell(new TableCell(col9));
			tr.addCell(new TableCell(col10));
			tr.addCell(new TableCell(col11));
			tr.addCell(new TableCell(col12));
			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);
		}

		TableRow trx_ = new TableRow();
		TableCell tcx_1 = new TableCell("分销商或业务员确认:");
		tcx_1.setColSpan(3);
		trx_.addCell(tcx_1);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_2 = new TableCell("主管确认:");
		tcx_2.setColSpan(2);
		trx_.addCell(tcx_2);
		trx_.addCell(hiddencell);

		TableCell tcx_3 = new TableCell("财务确认:");
		tcx_3.setColSpan(2);
		trx_.addCell(tcx_3);
		trx_.addCell(hiddencell);

		TableCell tcx_4 = new TableCell("总经理确认:");
		tcx_4.setColSpan(2);
		trx_.addCell(tcx_4);
		trx_.addCell(hiddencell);

		t.addRow(trx_);

		TableRow trx2_ = new TableRow();
		TableCell tcx_1_2 = new TableCell("日期:");
		tcx_1_2.setColSpan(3);
		trx2_.addCell(tcx_1_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_2_2 = new TableCell("日期:");
		tcx_2_2.setColSpan(2);
		trx2_.addCell(tcx_2_2);
		trx2_.addCell(hiddencell);

		TableCell tcx_3_2 = new TableCell("日期:");
		tcx_3_2.setColSpan(2);
		trx2_.addCell(tcx_3_2);
		trx2_.addCell(hiddencell);

		TableCell tcx_4_2 = new TableCell("日期:");
		tcx_4_2.setColSpan(2);
		trx2_.addCell(tcx_4_2);
		trx2_.addCell(hiddencell);

		t.addRow(trx2_);

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// 输出文件
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream("xxx.xls");

			// 执行EXCEL格式报表的输出
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// 大、粗字体
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("宋体");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 10);
					fontNormal.setFontName("宋体");
					// *****************end定义字体*****************

					// ***************设置EXCEL报表的样式表******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_HEADER_STYLE1, style);

				}
			};
			new ExcelPrinter().print(report, css, fo, true);
			System.out.println("生成成功！");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// 促销
	public static void listtable2() throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// 报表管理器
		ReportManager rm = new ReportManager();
		// 获得原始数据表格
		Table t = new Table();

		List x = new ArrayList();
		String lsh = "c5f422d673024f8eaaff7863d8dce68e";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"SELECT * from DY_991336361733786  where lsh='" + lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(14);
		TableCell htc = new TableCell("促销活动申请表");
		htc.setColSpan(13);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);

		TableCell htc2 = new TableCell("单位：（元/件）");
		htc2.setCssClass(MY_RIGHT_STYLE1);
		thr.setCell(13, htc2);
		t.addRow(thr);

		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";
		String clientid = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column7");
			mcol6 = (String) map.get("column8");
			mcol7 = (String) map.get("column9");
			clientid = (String) map.get("column10");
		}

		Connection conxx = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listxx = db.queryData(conxx,
				"SELECT * from DY_991336361733788    where fatherlsh='" + lsh
						+ "'  ");
		db.close(conxx);

		String cxStartdate = "";// 促销开始时间
		String cxEnddate = "";// 促销结束时间
		String cxCheckdate = "";// 核销时间
		if (listxx.size() > 0) {
			Map map = (Map) listxx.get(0);
			cxStartdate = (String) map.get("column7").toString();
			cxEnddate = (String) map.get("column8").toString();
			cxCheckdate = (String) map.get("column9").toString();
		}

		// 0
		TableRow tr0 = new TableRow();

		TableCell tc0 = new TableCell("公司方案编号:" + mcol1);
		tc0.setColSpan(7);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc4 = new TableCell("厂家方案编号:" + mcol2);
		tc4.setColSpan(7);
		tr0.addCell(tc4);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		t.addRow(tr0);

		// 1
		TableRow tr1 = new TableRow();

		TableCell tc00_ = new TableCell("促销网点", Rectangle.ALIGN_CENTER);
		tc00_.setAlign(tc00_.ALIGN_CENTER);
		tc00_.setCssClass(MY_DATA_STYLE2);
		tc00_.setRowSpan(2);
		tr1.addCell(tc00_);

		TableCell tc00 = new TableCell("名称:" + mcol3);
		tc00.setColSpan(6);
		tr1.addCell(tc00);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		TableCell tc44 = new TableCell("地址:" + mcol4);
		tc44.setColSpan(7);
		tr1.addCell(tc44);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		tr1.setType(MY_DATA_STYLE1);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc000_ = new TableCell("促销网点");
		tc000_.setAlign(tc000_.ALIGN_CENTER);
		tc000_.setIsHidden(true);
		tr2.addCell(tc000_);

		TableCell tc000 = new TableCell("联系人:" + mcol5);
		tc000.setColSpan(5);
		tr2.addCell(tc000);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc444 = new TableCell("联系方式:" + mcol6);
		tc444.setColSpan(5);
		tr2.addCell(tc444);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc666 = new TableCell("结款方式:" + getSETTLEMENTPERIOD(mcol7));
		tc666.setColSpan(3);
		tr2.addCell(tc666);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.setType(MY_DATA_STYLE1);
		t.addRow(tr2);

		con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listx = db.queryData(con,
				"SELECT * from DY_991336361733787  where fatherlsh='" + lsh
						+ "' ");
		db.close(con);

		// 促销品种 规格 单价 公司折让 厂家折让 厂家预定销量 业务抽成 备注
		// 现折 期折 搭赠 其它 现折 期折 搭赠 其它

		TableRow trx = new TableRow();
		trx.setType(MY_HEADER_STYLE1);
		TableCell cl1 = new TableCell("促销品种", Rectangle.VALIGN_MIDDLE);
		cl1.setRowSpan(2);
		trx.addCell(cl1);
		TableCell cl2 = new TableCell("规格", Rectangle.VALIGN_MIDDLE);
		cl2.setRowSpan(2);
		trx.addCell(cl2);
		TableCell cl3 = new TableCell("单价", Rectangle.VALIGN_MIDDLE);
		cl3.setRowSpan(2);
		trx.addCell(cl3);
		TableCell ttc = new TableCell("公司折让", Rectangle.VALIGN_MIDDLE);
		ttc.setRowSpan(1);
		ttc.setColSpan(4);
		trx.addCell(ttc);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		TableCell ttc2 = new TableCell("厂家折让", Rectangle.VALIGN_MIDDLE);
		ttc2.setRowSpan(1);
		ttc2.setColSpan(4);
		trx.addCell(ttc2);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);

		TableCell tcclll0 = new TableCell("厂家预定销量", Rectangle.VALIGN_MIDDLE);
		tcclll0.setRowSpan(2);
		trx.addCell(tcclll0);
		TableCell tcclll1 = new TableCell("业务抽成", Rectangle.VALIGN_MIDDLE);
		tcclll1.setRowSpan(2);
		trx.addCell(tcclll1);
		TableCell tcclll2 = new TableCell("备注", Rectangle.VALIGN_MIDDLE);
		tcclll2.setRowSpan(2);
		trx.addCell(tcclll2);
		t.addRow(trx);

		TableRow trx2 = new TableRow();
		trx2.setType(MY_HEADER_STYLE1);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(new TableCell("现折"));
		trx2.addCell(new TableCell("期折"));
		trx2.addCell(new TableCell("搭赠"));
		trx2.addCell(new TableCell("其它"));
		trx2.addCell(new TableCell("现折"));
		trx2.addCell(new TableCell("期折"));
		trx2.addCell(new TableCell("搭赠"));
		trx2.addCell(new TableCell("其它"));
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		t.addRow(trx2);

		double[] sumx = new double[4];
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String col3 = (String) object.get("column3") == null ? "" : object
					.get("column3").toString();
			String col4 = (String) object.get("column4") == null ? "" : object
					.get("column4").toString();
			String col5 = object.get("column5") == null ? "0"
					: ((BigDecimal) object.get("column5")).toString();
			String col6 = object.get("column6") == null ? "0"
					: ((BigDecimal) object.get("column6")).toString();
			String col7 = object.get("column7") == null ? "0"
					: ((BigDecimal) object.get("column7")).toString();
			String col8 = object.get("column8") == null ? "0"
					: ((BigDecimal) object.get("column8")).toString();
			String col9 = object.get("column9") == null ? "0"
					: ((BigDecimal) object.get("column9")).toString();
			String col10 = object.get("column10") == null ? "0"
					: ((BigDecimal) object.get("column10")).toString();
			String col11 = object.get("column11") == null ? "0"
					: ((BigDecimal) object.get("column11")).toString();
			String col12 = object.get("column12") == null ? "0"
					: ((BigDecimal) object.get("column12")).toString();
			String col20 = object.get("column20") == null ? "0"
					: ((BigDecimal) object.get("column20")).toString();

			String col13 = object.get("column13") == null ? "0"
					: ((BigDecimal) object.get("column13")).toString();
			String col14 = object.get("column14") == null ? "0"
					: ((BigDecimal) object.get("column14")).toString();
			String col15 = object.get("column15") == null ? "" : object.get(
					"column15").toString();

			TableRow tr = new TableRow();
			tr.addCell(new TableCell(col3));
			tr.addCell(new TableCell(col4));
			tr.addCell(new TableCell(col5));

			tr.addCell(new TableCell(col6));
			tr.addCell(new TableCell(col7));
			tr.addCell(new TableCell(col8));
			tr.addCell(new TableCell(col9));

			tr.addCell(new TableCell(col10));
			tr.addCell(new TableCell(col11));
			tr.addCell(new TableCell(col12));
			tr.addCell(new TableCell(col20));

			tr.addCell(new TableCell(col13));
			tr.addCell(new TableCell(col14));
			tr.addCell(new TableCell(col15));
			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);

			sumx[0] += Double.valueOf(col6) + Double.valueOf(col10);
			sumx[1] += Double.valueOf(col7) + Double.valueOf(col11);
			sumx[2] += Double.valueOf(col8) + Double.valueOf(col12);
			sumx[3] += Double.valueOf(col9) + Double.valueOf(col20);
		}

		TableRow trx2_00 = new TableRow();
		TableCell tcx_1_200 = new TableCell("合  计", Rectangle.ALIGN_CENTER);
		tcx_1_200.setColSpan(2);
		trx2_00.addCell(tcx_1_200);
		trx2_00.addCell(hiddencell);

		TableCell tcx_2_200 = new TableCell("现折:" + sumx[0]);
		tcx_2_200.setColSpan(3);
		trx2_00.addCell(tcx_2_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_3_200 = new TableCell("期折:" + sumx[1]);
		tcx_3_200.setColSpan(3);
		trx2_00.addCell(tcx_3_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_4_200 = new TableCell("搭赠:" + sumx[2]);
		tcx_4_200.setColSpan(3);
		trx2_00.addCell(tcx_4_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_5_200 = new TableCell("其它:" + sumx[3]);
		tcx_5_200.setColSpan(3);
		trx2_00.addCell(tcx_5_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		t.addRow(trx2_00);

		TableRow trx000_ = new TableRow();
		TableCell c00 = new TableCell("促销时间:" + cxStartdate + "-" + cxEnddate);
		c00.setColSpan(8);
		trx000_.addCell(c00);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);

		TableCell c01 = new TableCell("核销时间:" + cxCheckdate);
		c01.setColSpan(6);
		trx000_.addCell(c01);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);

		t.addRow(trx000_);

		TableRow thr00000 = new TableRow(14);
		TableCell htc00 = new TableCell("★核销需要资料：厂家费用申请及审批表");
		htc00.setColSpan(14);
		thr00000.setCell(0, htc00);
		t.addRow(thr00000);

		TableRow trx_ = new TableRow();
		TableCell tcx_1_ = new TableCell("分销商或业务员确认:");
		tcx_1_.setColSpan(4);
		trx_.addCell(tcx_1_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_2_ = new TableCell("主管确认:");
		tcx_2_.setColSpan(3);
		trx_.addCell(tcx_2_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_3_ = new TableCell("财务确认:");
		tcx_3_.setColSpan(3);
		trx_.addCell(tcx_3_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_4_ = new TableCell("总经理确认:");
		tcx_4_.setColSpan(4);
		trx_.addCell(tcx_4_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		t.addRow(trx_);

		TableRow trx2_ = new TableRow();
		TableCell tcx_1_2 = new TableCell("日期:");
		tcx_1_2.setColSpan(4);
		trx2_.addCell(tcx_1_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_2_2 = new TableCell("日期:");
		tcx_2_2.setColSpan(3);
		trx2_.addCell(tcx_2_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_3_2 = new TableCell("日期:");
		tcx_3_2.setColSpan(3);
		trx2_.addCell(tcx_3_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_4_2 = new TableCell("日期:");
		tcx_4_2.setColSpan(4);
		trx2_.addCell(tcx_4_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		t.addRow(trx2_);

		// 销售量统计
		TableRow thr000001 = new TableRow(14);
		TableCell htc001 = new TableCell("销售量统计");
		htc001.setColSpan(14);
		thr000001.setCell(0, htc001);
		t.addRow(thr000001);

		TableRow tr11 = new TableRow();
		TableCell cell11 = new TableCell("产品编号");
		cell11.setColSpan(2);
		tr11.addCell(cell11);
		tr11.addCell(hiddencell);

		TableCell cell211 = new TableCell("产品名称");
		cell211.setColSpan(2);
		tr11.addCell(cell211);
		tr11.addCell(hiddencell);

		TableCell cell311 = new TableCell("规格");
		cell311.setColSpan(2);
		tr11.addCell(cell311);
		tr11.addCell(hiddencell);

		TableCell cell411 = new TableCell("单价");
		cell411.setColSpan(2);
		tr11.addCell(cell411);
		tr11.addCell(hiddencell);

		TableCell cell511 = new TableCell("数量");
		cell511.setColSpan(2);
		tr11.addCell(cell511);
		tr11.addCell(hiddencell);

		TableCell cell611 = new TableCell("金额");
		cell611.setColSpan(2);
		tr11.addCell(cell611);
		tr11.addCell(hiddencell);

		tr11.addCell(new TableCell(""));
		tr11.addCell(new TableCell(""));

		tr11.setType(MY_DATA_STYLE1);
		t.addRow(tr11);

		StringBuffer sqlstr = new StringBuffer();
		sqlstr
				.append(" select FShortNumber as FItemIDName,Fname as FItemName,FModel as FItemModel,isnull(u1.FConsignPrice,0) FConsignPrice,isnull(u1.Fauxqty,0) Fauxqty,isnull(u1.FConsignAmount,0) FConsignAmount ");
		sqlstr.append(" from ICStockBill v1  ");
		sqlstr
				.append(" Inner Join ICStockBillEntry u1 on v1.FInterID=u1.FInterID ");
		sqlstr
				.append(" left outer join t_ICItem t3192 on u1.FItemID=t3192.FItemID ");
		sqlstr.append(" where v1.FTranType=21 and v1.FSupplyID="
				+ (("".equals(clientid)) ? "" : clientid) + " and v1.fdate>='"
				+ cxStartdate + "' " + " and v1.fdate<='" + cxEnddate + "' ");
		System.out.println(sqlstr.toString());
		Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");

		List selllist = db.queryData(hgcon, sqlstr.toString());
		for (Iterator iterator = selllist.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String FItemIDName = (String) object.get("FItemIDName");
			String FItemName = (String) object.get("FItemName");
			String FItemModel = (String) object.get("FItemModel");
			String FConsignPrice = (String) object.get("FConsignPrice");
			String Fauxqty = (String) object.get("Fauxqty");
			String FConsignAmount = (String) object.get("FConsignAmount");

			TableRow tr = new TableRow();

			TableCell cell = new TableCell(FItemIDName);
			cell.setColSpan(2);
			tr.addCell(cell);
			tr.addCell(hiddencell);

			TableCell cell2 = new TableCell(FItemName);
			cell2.setColSpan(2);
			tr.addCell(cell2);
			tr.addCell(hiddencell);

			TableCell cell3 = new TableCell(FItemModel);
			cell3.setColSpan(2);
			tr.addCell(cell3);
			tr.addCell(hiddencell);

			TableCell cell4 = new TableCell(FConsignPrice);
			cell4.setColSpan(2);
			tr.addCell(cell4);
			tr.addCell(hiddencell);

			TableCell cell5 = new TableCell(Fauxqty);
			cell5.setColSpan(2);
			tr.addCell(cell5);
			tr.addCell(hiddencell);

			TableCell cell6 = new TableCell(FConsignAmount);
			cell6.setColSpan(2);
			tr.addCell(cell6);
			tr.addCell(hiddencell);

			tr.addCell(new TableCell(""));
			tr.addCell(new TableCell(""));

			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);
		}
		db.close(hgcon);

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// 输出文件
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream("xxx2.xls");

			// 执行EXCEL格式报表的输出
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// 大、粗字体
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("宋体");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 10);
					fontNormal.setFontName("宋体");
					// *****************end定义字体*****************

					// ***************设置EXCEL报表的样式表******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_HEADER_STYLE1, style);

				}
			};
			new ExcelPrinter().print(report, css, fo, true);
			System.out.println("生成成功！");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// 新用户申请
	public static void listtable3() throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// 报表管理器
		ReportManager rm = new ReportManager();
		// 获得原始数据表格
		Table t = new Table();

		List x = new ArrayList();
		String lsh = "2a5964344ebd48ff83b3af65c56989fd";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"SELECT * from DY_991336361733789  where lsh='" + lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(8);
		TableCell htc = new TableCell("新用户申请表", Rectangle.ALIGN_CENTER);
		htc.setColSpan(8);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);
		t.addRow(thr);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";
		String mcol8 = "";
		String mcol9 = "";
		String mcol10 = "";
		String mcol11 = "";
		String mcol12 = "";
		String mcol13 = "";
		String mcol14 = "";
		String mcol15 = "";
		String mcol16 = "";
		String mcol17 = "";
		String mcol18 = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column21");
			mcol6 = (String) map.get("column22");
			mcol7 = (String) map.get("column23");
			mcol8 = (String) map.get("column12");
			mcol9 = (String) map.get("column13");
			mcol10 = (String) map.get("column16");
			mcol11 = (String) map.get("column10");
			mcol12 = (String) map.get("column14");
			mcol13 = (String) map.get("column9");
			mcol14 = (String) map.get("column17");
			mcol15 = (String) map.get("column11");
			mcol16 = (String) map.get("column18");
			mcol17 = (String) map.get("column20");
			mcol18 = (String) map.get("column19");
		}
		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);
		// 0
		TableRow tr0 = new TableRow();
		TableCell tc0 = new TableCell("客户编号:" + mcol1);
		tc0.setColSpan(4);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc0_ = new TableCell("客户名称:" + mcol2);
		tc0_.setColSpan(4);
		tr0.addCell(tc0_);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.setType(MY_DATA_STYLE1);

		t.addRow(tr0);

		// 1
		TableRow tr1 = new TableRow();
		TableCell tc1 = new TableCell("公司地址:" + mcol3);
		tc1.setColSpan(8);
		tr1.addCell(tc1);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.setType(MY_DATA_STYLE1);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc2 = new TableCell("客户层级:" + GETCLIENTLEVEL(mcol4));
		tc2.setColSpan(2);
		tr2.addCell(tc2);
		tr2.addCell(hiddencell);

		TableCell tc2_ = new TableCell("分属部门:" + GETDEPTLEVEL(mcol5));
		tc2_.setColSpan(2);
		tr2.addCell(tc2_);
		tr2.addCell(hiddencell);

		TableCell tc2_2 = new TableCell("付款方式:" + getSETTLEMENTPERIOD(mcol6));
		tc2_2.setColSpan(2);
		tr2.addCell(tc2_2);
		tr2.addCell(hiddencell);

		TableCell tc2_3 = new TableCell("客户分类:" + getSubmessByFTypeID(mcol7));
		tc2_3.setColSpan(2);
		tr2.addCell(tc2_3);
		tr2.addCell(hiddencell);

		tr2.setType(MY_DATA_STYLE1);

		t.addRow(tr2);

		// 3
		TableRow tr3 = new TableRow();
		TableCell tc3 = new TableCell("联系人:" + mcol8);
		tc3.setColSpan(4);
		tr3.addCell(tc3);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);

		TableCell tc3_ = new TableCell("联系电话:" + mcol9);
		tc3_.setColSpan(4);
		tr3.addCell(tc3_);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);

		tr3.setType(MY_DATA_STYLE1);

		t.addRow(tr3);

		// 4
		TableRow tr4 = new TableRow();
		TableCell tc4 = new TableCell("申请人:" + mcol10);
		tc4.setColSpan(2);
		tr4.addCell(tc4);
		tr4.addCell(hiddencell);

		TableCell tc4_ = new TableCell("业务主管:" + mcol11);
		tc4_.setColSpan(2);
		tr4.addCell(tc4_);
		tr4.addCell(hiddencell);

		TableCell tc4_2 = new TableCell("负责人:" + mcol12);
		tc4_2.setColSpan(2);
		tr4.addCell(tc4_2);
		tr4.addCell(hiddencell);

		TableCell tc4_3 = new TableCell("总经理:" + mcol13);
		tc4_3.setColSpan(2);
		tr4.addCell(tc4_3);
		tr4.addCell(hiddencell);

		tr4.setType(MY_DATA_STYLE1);

		t.addRow(tr4);

		// 5
		TableRow tr5 = new TableRow();
		TableCell tc5 = new TableCell("申请时间:" + mcol14);
		tc5.setColSpan(2);
		tr5.addCell(tc5);
		tr5.addCell(hiddencell);

		TableCell tc5_ = new TableCell("审核时间:" + mcol15);
		tc5_.setColSpan(2);
		tr5.addCell(tc5_);
		tr5.addCell(hiddencell);

		TableCell tc5_2 = new TableCell("审核时间:" + mcol16);
		tc5_2.setColSpan(2);
		tr5.addCell(tc5_2);
		tr5.addCell(hiddencell);

		TableCell tc5_3 = new TableCell("审核时间:" + mcol17);
		tc5_3.setColSpan(2);
		tr5.addCell(tc5_3);
		tr5.addCell(hiddencell);

		tr5.setType(MY_DATA_STYLE1);

		t.addRow(tr5);

		// 6
		TableRow tr6 = new TableRow();
		TableCell tc6 = new TableCell("备注:" + mcol18);
		tc6.setColSpan(8);
		tr6.addCell(tc6);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.setType(MY_DATA_STYLE1);
		t.addRow(tr6);

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// 输出文件
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream("xxx3.xls");

			// 执行EXCEL格式报表的输出
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// 大、粗字体
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("宋体");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 10);
					fontNormal.setFontName("宋体");
					// *****************end定义字体*****************

					// ***************设置EXCEL报表的样式表******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_HEADER_STYLE1, style);

				}
			};
			new ExcelPrinter().print(report, css, fo, true);
			System.out.println("生成成功！");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// 结算期限
	public static String getSETTLEMENTPERIOD(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db.queryData(hgcon,
					"select FItemID,FName,FID,FDay from t_SetDL where FDeleted=0 and FItemID="
							+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// 获取辅助资料ByFTypeID
	public static String getSubmessByFTypeID(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db.queryData(hgcon,
					"select * from t_submessage where ftypeid=501 and FInterID="
							+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// 获取客户级别
	public static String GETCLIENTLEVEL(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db
					.queryData(
							hgcon,
							"select * from t_item where FItemClassID=1 and FDeleted=0 and fDetail=0 and FItemID="
									+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// 获取部门级别
	public static String GETDEPTLEVEL(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db
					.queryData(
							hgcon,
							"select * from t_item where FItemClassID=2 and FDeleted=0 and fDetail=0  and FItemID="
									+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// 更新核销状态 置为01
	public static String updateCXstatus() {
		String runtimeid = "";

		String bussid = wf.get(runtimeid, "bussid");
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		String sql1 = "UPDATE dyform.DY_991336361733786 SET STATUSINFO='01' WHERE lsh='"
				+ bussid + "';";

		db.execute(con, sql1);
		db.close(con);
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("tip", "核销成功");
		return json.toString();
	}

}
