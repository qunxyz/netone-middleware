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
	 * ԭʼ��JDBC
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
	 * �ͻ���Ϣ FTypeID:�ͻ�����ID F_104:���ʽ
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

	// ���ͻ�����Ƿ����
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

	// ��Ʒ��Ϣ
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

	// ��������
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

	// ���ݵ�λ��������ҵ�λ SOASCRIPT.SOASCRIPT.HG.QUERYUNITBYUNIDGROUPID
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

	// ��������ID���Ҽ۸񣨴Ӽ۸񷽰��������۸�
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

	// �������ϵ�λID���ؼ�����λϵ��
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

	// ��������
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

	// ����д��K3 �۸���� ��ʱ
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
				/** �ͻ�ID */
				clientId = data.getColumn10();
				if ("".equals(clientId)) {
					System.err.println("�ͻ�IDΪ��");
					return;
				}
				/** �ͻ�����ID */
				ptypeid = data.getColumn11();
				if ("".equals(ptypeid)) {
					System.err.println("�ͻ�����IDΪ��");
					return;
				}
				TCsBus bus2 = new TCsBus();
				bus2.setFatherlsh(bussid);
				List listx = dy.queryData("5fe85cb097f911e1b01667a74cfdf95c_",
						bus2, 0, 999999999, "");
				for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
					TCsBus object = (TCsBus) iterator.next();
					/**
					 * 19 ����ID 5���� 18��λ 6+10 ���� 7+11 ���� 8+12 ���� 9+20 ���� 15��ע
					 * ����Ԥ������ �ֶβ��� ��֪д�ĸ��ֶ� 14 ҵ���� 16 ������(��) 17 ������������
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
					 * д��۸����߱� FEntryIDΪ���������� FInterID:����ID 2:�ͻ��۸�;3:�ͻ����� int
					 * FItemID:�ͻ��۸�Ϊ����IDint FRelatedID:�ͻ�ID��ͻ�����ID select
					 * finterID from t_submessage where
					 * FTypeID=501;t_Organization:FTypeID int FUnitID:��λID
					 * select FMeasureUnitID from t_MeasureUnit; int
					 * FCuryID:����ID Ĭ��Ϊ1 ����� int FPrice:�۸� dicimal(28,10)
					 * FBegDate:����ʱ�� datetime FEndDate:����ʱ�� datetime
					 * FChecked:�Ƿ��� Ĭ��Ϊ1 bit FIndex������� int FNote����ע Ĭ��ΪOAд��
					 * nvarchar(255)
					 * 
					 * FBegQty ������(��) FEndQty ������������
					 * 
					 * fare1pg��ƿ�� Ĭ��ΪNULL dicimal(28,10) fare2xf���ַ� Ĭ��ΪNULL
					 * dicimal(28,10) fare3qf���ڷ� Ĭ��ΪNULL dicimal(28,10)
					 * fare4cs������ Ĭ��ΪNULL dicimal(28,10) fare5dz������ Ĭ��ΪNULL
					 * dicimal(28,10) fare6zc��ר�� Ĭ��ΪNULL dicimal(28,10)
					 * fare7qd���ൺ�ն� Ĭ��ΪNULL dicimal(28,10) fare8kp����Ʊ Ĭ��ΪNULL
					 * dicimal(28,10) fare9bz����װ Ĭ��ΪNULL dicimal(28,10)
					 * fare10qt������ Ĭ��ΪNULL dicimal(28,10) fare11yw��ҵ��Ա��� Ĭ��ΪNULL
					 * dicimal(28,10) fare12zj���ܼ� Ĭ��ΪNULL dicimal(28,10)
					 * fare13dzfa���������� Ĭ��ΪNULL nchar(200)
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
							+ "+'[OA����]',"
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
							+ "',FNote=FNote+'[OA����]' where FEntryID="
							+ FEntryID;

					db.execute(con, sql1);
					db.execute(con, sql2);

					db.close(con);

				}
			}

		} else {
			System.err.print("bussidΪ��!");
		}

	}

	// ����д��K3 �۸����
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
		/** �ͻ�ID */
		clientId = data.getColumn10();
		if ("".equals(clientId)) {
			System.err.println("�ͻ�IDΪ��");
			return;
		}
		/** �ͻ�����ID */
		ptypeid = data.getColumn11();
		if ("".equals(ptypeid)) {
			System.err.println("�ͻ�����IDΪ��");
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
		/** ������ʼʱ�� */
		String startdate = data2.getColumn7();
		String enddate = data2.getColumn8();

		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		Connection querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			/**
			 * 19 ����ID 5���� 18��λ 6+10 ���� 7+11 ���� 8+12 ���� 9+20 ���� 15��ע
			 * ����Ԥ������ �ֶβ��� ��֪д�ĸ��ֶ� 14 ҵ���� 16 ������(��) 17 ������������
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
			/** �۸� */
			Double vPrice = Double
					.valueOf(object.getColumn19() == null ? "0"
							: object.getColumn19().toString());
			/**
			 * д��۸����߱� FEntryIDΪ���������� FInterID:����ID 2:�ͻ��۸�;3:�ͻ����� int
			 * FItemID:�ͻ��۸�Ϊ����IDint FRelatedID:�ͻ�ID��ͻ�����ID select
			 * finterID from t_submessage where
			 * FTypeID=501;t_Organization:FTypeID int FUnitID:��λID
			 * select FMeasureUnitID from t_MeasureUnit; int
			 * FCuryID:����ID Ĭ��Ϊ1 ����� int FPrice:�۸� dicimal(28,10)
			 * FBegDate:����ʱ�� datetime FEndDate:����ʱ�� datetime
			 * FChecked:�Ƿ��� Ĭ��Ϊ1 bit FIndex������� int FNote����ע Ĭ��ΪOAд��
			 * nvarchar(255)
			 * 
			 * FBegQty ������(��) FEndQty ������������
			 * 
			 * fare1pg��ƿ�� Ĭ��ΪNULL dicimal(28,10) fare2xf���ַ� Ĭ��ΪNULL
			 * dicimal(28,10) fare3qf���ڷ� Ĭ��ΪNULL dicimal(28,10)
			 * fare4cs������ Ĭ��ΪNULL dicimal(28,10) fare5dz������ Ĭ��ΪNULL
			 * dicimal(28,10) fare6zc��ר�� Ĭ��ΪNULL dicimal(28,10)
			 * fare7qd���ൺ�ն� Ĭ��ΪNULL dicimal(28,10) fare8kp����Ʊ Ĭ��ΪNULL
			 * dicimal(28,10) fare9bz����װ Ĭ��ΪNULL dicimal(28,10)
			 * fare10qt������ Ĭ��ΪNULL dicimal(28,10) fare11yw��ҵ��Ա��� Ĭ��ΪNULL
			 * dicimal(28,10) fare12zj���ܼ� Ĭ��ΪNULL dicimal(28,10)
			 * fare13dzfa���������� Ĭ��ΪNULL nchar(200)
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
					+ "[OA����]");
			list1.add(v6);
			list1.add(v7);
			String sql1 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			List list2 = new ArrayList();
			list2.add(beforeDate);
			list2.add("[OA����]");
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
					+ "[OA����]");
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
	System.err.print("bussidΪ��!");
}

	}

	// ��Ʒд��K3 �۸����
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
		/** �ͻ�ID */
		clientId = data.getColumn10();
		if ("".equals(clientId)) {
			System.err.println("�ͻ�IDΪ��");
			return;
		}
		/** �ͻ�����ID */
		ptypeid = data.getColumn11();
		if ("".equals(ptypeid)) {
			System.err.println("�ͻ�����IDΪ��");
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
			/** �۸� */
			Double vPrice = Double.valueOf(object.getColumn8()
					.toString());

			List list3 = new ArrayList();
			list3.add(3);
			list3.add(object.getColumn4());
			list3.add(ptypeid);
			/** ��λ */
			list3.add(object.getColumn13());
			list3.add(1);
			list3.add(vPrice);
			list3.add(beginDate);
			list3.add("2100-01-01 00:00:00.000");
			list3.add(1);
			list3.add(1);
			list3.add((object.getColumn12() == null ? "" : object
					.getColumn12())
					+ "[OA������Ʒ]");
			list3.add(0);
			list3.add(0);
			String sql3 = "insert into ICPrcPlyEntry (FInterID,FItemID,FRelatedID,FUnitID,FCuryID,FPrice,FBegDate,FEndDate,FChecked,FIndex,FNote,FBegQty,FEndQty) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			db.execute_p(con, sql3, list3);

		}
		db.close(con);
	}

} else {
	System.err.print("bussidΪ��!");
}

	}

	// �¿ͻ�
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
		/** �ͻ���� */
		String column3 = data.getColumn3();
		/** �ͻ�����* */
		String column4 = data.getColumn4();
		/** ��˾��ַ */
		String column5 = data.getColumn5();
		/** �ͻ��㼶 */
		String column6 = data.getColumn6();
		/** ��ϵ�� */
		String column12 = data.getColumn12();
		/** ��ϵ�绰 */
		String column13 = data.getColumn13();
		/** ��ע */
		String column19 = data.getColumn19();
		/** �������� */
		String column21 = data.getColumn21();
		/** ���ʽ */
		String column22 = data.getColumn22();
		/** �ͻ����� */
		String column23 = data.getColumn23();

		if ("".equals(column3)) {
			System.err.println("�ͻ���Ų���Ϊ��!");
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

		/** �ͻ��� */
		List listx2 = new ArrayList();
		listx2.add(column3);
		querycon = db.con("DATASOURCE.DATASOURCE.HGDB");
		List x1 = db.queryData_p(querycon,
				"select FItemID from t_item where FNumber=?", listx2);
		Integer FItemID = db.getn(x1, 0, "FItemID");

		/** ���ʽ */
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
		/** ������ */
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
		System.err.print("������!");
	}

} else {
	System.err.print("bussidΪ��!");
}

	}

	/** �ͻ� */
	/** select * from t_item where FItemClassID=1 and FDeleted=0;* */
	/** ֻ��FDetail=1�ļ�¼ */
	/** select * from t_Organization where FDeleted=0;* */

	// �ͻ�����
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

	// ��ȡ�������� FTypeID��501:�ͻ�����
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

	// ��ȡ�ͻ�����
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

	// ��ȡ���ż���
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
		// ҵ������
		List list = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column10", username, object);
			dy.setIn("column11", currdate, object);
			dy.submit(object);
		}

		// ������
		List list1 = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column14", username, object);
			dy.setIn("column18", currdate, object);
			dy.submit(object);
		}

		// �ܾ���
		List list2 = dy.queryData(
				"BUSSFORM.BUSSFORM.BNHGYW.DY_991336361733789 ", bus, 0, 1, "");
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column9", username, object);
			dy.setIn("column20", currdate, object);
			dy.submit(object);
		}
	}

	// �������ѹ���
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
	
	//����
	public String getCUXIAO(){
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		
		/*-
		 * ���쵥�� ��ʱ��������պ���3λ
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
			if (queneNumber.length() == 1) {// 1λ��2��0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2λ��1��0
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
