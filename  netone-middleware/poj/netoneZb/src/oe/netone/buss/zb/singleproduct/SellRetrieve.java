package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class SellRetrieve extends OeScript {

	public static String todo() {
		// String selljson = "$(sr_selljson)";
		// String rejson = "$(sr_rejson)";

		// String selljson =
		// "[{'column3':'123321','column19':'dl006','column14':'75%'}]";
		// String rejson =
		// "[{'column3':'','column11':'PT950','column8':'dl006','column28':'1','column29':'10','column13':'100','column20':'200','column12':''}]";
		// String selljson =
		// "[{'column3':'123321','column19':'dl007','column14':'75%'}]";
		// String rejson =
		// "[{'column3':'PT950_PT950','column11':'PT950','column8':'dl007','column28':'1','column29':'10','column13':'100','column20':'200','column12':''}]";

		// String rejson = "[]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column12':'1'}]";

		// String
		// rejson="[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column21':'1'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column4':'11','column5':'PT999','column6':'BS01','column8':'dl006','column9':'99','column10':'99','column11':'CS01','column12':'99','column13':'99','column14':'99','column15':'9','column16':'9','column17':'9','column20':'99','column21':'99','column22':'99','column26':'99','column27':'001','column28':'1','column29':'99','column30':'99'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ_988','column4':'ǧ�������
		// ','column5':'100.00','column6':'0','column7':'843','column9':'10','column10':'84.3','column11':'927.3','column12':'75%','column13':'90%','column14':'67.5%','column15':'625.93','column19':'dl006','column20':'20','column21':'20','column23':'1056YQ2945','column26':'8.43','column28':'lx001','column32':'8.43','column35':'1350.00','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'�ƽ����','column8':'dl006','column11':'CS01','column13':'400','column20':'15','column28':'0','column29':'9','column30':'99'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ8933','column4':'�ƽ�08','column5':'390.00','column6':'20','column7':'3120','column9':'20','column10':'160','column11':'3120','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'2286.00','column19':'dl006','column20':'088','column21':'23','column23':'010','column26':'8','column28':'lx001','column32':'8','column35':'228.60','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'�ƽ����','column8':'dl006','column12':'1','column13':'390','column20':'13','column21':'1352.0','column28':'0','column29':'7','column30':'99'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ8933','column4':'�ƽ�08','column5':'390.00','column6':'20','column7':'3120','column9':'20','column10':'160','column11':'3120','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'2286.00','column19':'dl006','column20':'088','column21':'23','column23':'010','column26':'8','column28':'lx001','column32':'8','column35':'228.60','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT999_PT999','column8':'dl007','column11':'PT999','column13':'99','column20':'12','column28':'1','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column5':'150.00','column6':'10','column7':'2250','column9':'10','column10':'150','column11':'2250','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'1678.75','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column28':'lx003','column32':'15','column35':'167.88','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT999','column4':'11','column8':'dl007','column11':'PT999','column13':'390','column20':'13','column28':'1','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column6':'10','column9':'10','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT950','column4':'``','column8':'dl007','column9':'11','column11':'PT950','column13':'390','column20':'13','column28':'1','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column6':'10','column9':'10','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT950','column4':'11','column8':'dl007','column11':'PT950','column13':'390','column20':'13','column28':'1','column29':'13'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column5':'390.00','column6':'10','column7':'5850','column9':'10','column10':'150','column11':'5850','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'4108.75','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column28':'lx001','column32':'15','column35':'410.88','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT999_PT950','column4':'11','column8':'dl007','column9':'11','column10':'1','column11':'PT950','column13':'309','column20':'13','column23':'11','column28':'1','column29':'13'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ1111','column4':'BJ0101','column5':'390.00','column6':'10','column7':'5850','column9':'10','column10':'150','column11':'3000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2025.00','column19':'dl007','column20':'10','column21':'10','column23':'AB0123','column26':'12','column28':'lx001','column32':'12','column35':'202.50','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT950','column8':'dl007','column10':'11','column11':'PT950','column13':'390','column20':'13','column22':'11','column28':'1','column29':'13'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column5':'390.00','column6':'10','column7':'3288','column9':'10','column10':'84.3','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column28':'lx001','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT999_PT950','column4':'11111','column8':'dl007','column11':'PT999','column12':'0','column13':'390','column20':'13','column21':'0.0','column26':'11','column28':'0','column29':'10','column30':'90'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column6':'10','column9':'10','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'143','column28':'0','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ_4568','column4':'ǧ�������','column5':'390.00','column6':'0','column7':'3288','column9':'10','column10':'84.3','column11':'3287.7','column12':'75%','column13':'90%','column14':'67.5%','column15':'675.00','column19':'dl006','column20':'10','column21':'10','column23':'1056YQ2945','column26':'8.43','column28':'lx001','column32':'8.43','column35':'67.50','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT950','column8':'dl007','column11':'PT950','column13':'309','column20':'20','column21':'2514.32','column28':'1','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column5':'390.00','column6':'10','column7':'3288','column9':'10','column10':'84.3','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column28':'lx001','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'91.00','column28':'1','column29':'7'},{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'26.00','column28':'1','column29':'1'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ08502','column4':'�ƽ�02','column5':'100.00','column6':'20','column7':'810','column9':'10','column10':'81','column11':'810','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'708.50','column19':'dl006','column20':'Q123','column21':'C120','column23':'Y862','column26':'8.1','column28':'lx001','column32':'8.1','column35':'70.85','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'91.00','column28':'1','column29':'7'},{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'39.00','column28':'0','column29':'1'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ08502','column4':'�ƽ�02','column5':'100.00','column6':'20','column7':'810','column9':'10','column10':'81','column11':'810','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'708.50','column19':'dl006','column20':'Q123','column21':'C120','column23':'Y862','column26':'8.1','column28':'lx001','column32':'8.1','column35':'70.85','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column12':'0.18','column13':'390','column20':'20','column21':'180.00','column26':'111','column28':'0','column29':'9','column30':'99','column31':'1111'},{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column12':'20','column13':'390','column20':'20','column21':'40.00','column28':'0','column29':'2','column30':'99','column31':'1111'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'hj_test01','column4':'hj_test01','column5':'410.00','column6':'0','column7':'4100','column9':'10','column10':'100','column11':'4100','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'3175.00','column19':'dl006','column20':'hj_test01','column21':'hj_test01','column23':'10','column26':'10','column28':'lx001','column32':'10','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'equal','column8':'dl001','column21':'0.00','column26':'0.000','column28':'1','column29':'11','column33':'2000'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'xqzs001','column4':'xqzs001','column6':'12','column9':'0','column11':'2134','column12':'75%','column13':'100%','column14':'75%','column15':'1600.50','column19':'dl001','column20':'xqzs001','column21':'xqzs001','column23':'112','column24':'12','column26':'12','column27':'12','column32':'12','column36':'01','column37':'13'}]";

		String rejson = "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column20':'20','column21':'180.00','column26':'0.000','column28':'1','column29':'9'}]";
		String selljson = "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ20128702','column4':'HJTEST','column5':'410.00','column6':'10','column7':'4510','column9':'10','column10':'110','column11':'4510','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'3502.50','column19':'dl006','column20':'HJTEST','column21':'HJTEST','column23':'HJTEST','column26':'11','column28':'lx001','column32':'11','column35':'350.25','column36':'01','column37':'0'}]";
		/**
		 * ������ϸ <BR>
		 * ������ column3 <BR>
		 * ���� column19 <BR>
		 * ʵ���ۿ� column14 <BR>
		 * ������ column5 <BR>
		 * ��� column11 <BR>
		 * ��Ʒ���� column6
		 */

		/**
		 * ���� �������� column3 <BR>
		 * ʵ���ɫ column11 <BR>
		 * ���� column8 <BR>
		 * �Ƿ�˾ column28 <BR>
		 * ���� column29 <BR>
		 * ���յ��� column13 <BR>
		 * ���ѵ��� column20 <BR>
		 * �ۿ��� column30 <BR>
		 * ��� column12 <BR>
		 * ��� column33
		 */
		net.sf.json.JSONArray selljson_ = net.sf.json.JSONArray
				.fromObject(selljson);
		net.sf.json.JSONArray rejson_ = net.sf.json.JSONArray
				.fromObject(rejson);

		/** * ���� */
		Double rejgStr = 0.0;
		StringBuffer pricecount = new StringBuffer();
		pricecount.append("^^");

		Map dlmap = new HashMap();

		Map sellMap = new HashMap();
		Map reMap = new HashMap();
		/** �ǹ�˾ * */
		Map reMap2 = new HashMap();
		Map rePriceMap = new HashMap();

		Map sellpurity_Map = new HashMap();
		Map repurity_Map = new HashMap();

		/** ���۱�� */
		Map sellbpriceMap = new HashMap();
		/** * ���ձ�� */
		Map rebpriceMap = new HashMap();

		/** ʵ���ۿ��� */
		Map discount_Map = new HashMap();

		/** ���۵����� */
		Map sellJJMap = new HashMap();

		/** ��Ʒ���� */
		Map jppriceMap = new HashMap();

		if (rejson_.size() < 0)
			return "{'price':0}";

		/** ������� */
		Connection conx = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listx = db.queryData(conx,
				"select * from dyform.DY_71344176619324 ");
		db.close(conx);
		Map damageMap = new HashMap();
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			damageMap.put(object.get("column3"), object.get("column4"));
		}

		for (int j = 0; j < selljson_.size(); j++) {
			net.sf.json.JSONObject obj0 = selljson_.getJSONObject(j);

			/** ���� */
			/** ������ code */
			String code_ = "";
			if (obj0.containsKey("column3")) {
				code_ = obj0.getString("column3");
			}
			/** ���ϳ�ɫ purity */
			String purity_ = "";
			/** ���� bigcate */
			String bigcate_ = "";
			if (obj0.containsKey("column19")) {
				bigcate_ = obj0.getString("column19");
			}
			/** ���� kimjoong */
			String kimjoong_ = "0";
			/** column14 ʵ���ۿ� */
			String discount_ = "";
			if (obj0.containsKey("column14")) {
				discount_ = obj0.getString("column14");
			}

			/** ��� */
			String jj = "0";
			if (obj0.containsKey("column5")) {
				jj = obj0.getString("column5");
			}
			/** �浱���� */
			sellJJMap.put(bigcate_, jj);

			if (org.apache.commons.lang.StringUtils.isNotEmpty(discount_)) {
				discount_Map.put(bigcate_, discount_.replace("%", ""));
			} else {
				discount_Map.put(bigcate_, "100");
			}

			/** ���� */
			/** ������ */
			/** ���ϳ�ɫ column52 */
			/** ���� */
			/** ���� column17 */

			Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
			List list = db
					.queryData(
							con,
							"select IFNULL(column17,0) as jz,IFNULL(column52,'') as purity from dyform.DY_271334208897441  where column4='"
									+ code_ + "' limit 1 ");
			db.close(con);

			if (list.size() > 0) {
				Map xxx = (Map) list.get(0);
				kimjoong_ = xxx.get("jz").toString();
				purity_ = xxx.get("purity").toString();
			}

			boolean x = org.apache.commons.lang.math.NumberUtils
					.isNumber(kimjoong_);
			double jz = 0.0;
			if (x) {
				jz = Double.valueOf(kimjoong_);
			}
			if (sellMap.containsKey(bigcate_)) {
				Double jz_ = (Double) sellMap.get(bigcate_);
				sellMap.put(bigcate_, jz_ + jz);
			} else {
				sellMap.put(bigcate_, jz);
			}
			/** ����(��) dl007 */
			if ("dl007".equals(bigcate_)) {
				if (sellpurity_Map.containsKey(purity_)) {
					Double jz_ = (Double) sellpurity_Map.get(purity_);
					sellpurity_Map.put(purity_, jz_ + jz);
				} else {
					sellpurity_Map.put(purity_, jz);
				}
			}

			Double bprice = 0.0;
			if (obj0.containsKey("column11")) {
				bprice = Double.valueOf(obj0.getString("column11"));
			}

			/** ��Ƕ�� dl001 */
			if ("dl001".equals(bigcate_)) {
				if (sellbpriceMap.containsKey("column11")) {
					Double bprice_ = (Double) sellbpriceMap.get(bigcate_);
					sellbpriceMap.put(bigcate_, bprice_ + bprice);
				} else {
					sellbpriceMap.put(bigcate_, bprice);
				}
			}

			Double jpprice = 0.0;
			if (obj0.containsKey("column6")) {
				jpprice = Double.valueOf(obj0.getString("column6"));
			}
			/** �ƽ� ���� */
			if ("dl006".equals(bigcate_) || "dl007".equals(bigcate_)) {
				if (jppriceMap.containsKey("column6")) {
					Double jpprice_ = (Double) jppriceMap.get(bigcate_);
					jppriceMap.put(bigcate_, jpprice_ + jpprice);
				} else {
					jppriceMap.put(bigcate_, jpprice);
				}
			}

		}

		for (int i = 0; i < rejson_.size(); i++) {
			net.sf.json.JSONObject obj0 = rejson_.getJSONObject(i);
			/** ���� * */
			/** �������� type* */
			String type = "";
			if (obj0.containsKey("column3")) {
				type = obj0.getString("column3");
			}
			/** ʵ���ɫ purity* */
			String purity = "";
			if (obj0.containsKey("column11")) {
				purity = obj0.getString("column11");
			}
			/** ���� bigcate* */
			String bigcate = "";
			if (obj0.containsKey("column8")) {
				bigcate = obj0.getString("column8");
			}
			/** �Ƿ�˾ iscompany* */
			String iscompany = "";
			if (obj0.containsKey("column28")) {
				iscompany = obj0.getString("column28");
			}
			/** ���� kimjoong* */
			String kimjoong = "0";
			if (obj0.containsKey("column29")) {
				kimjoong = obj0.getString("column29");
			}
			/** ���ս�� huishouprice* */
			String huishouprice = "0";
			if (obj0.containsKey("column13")) {
				huishouprice = obj0.getString("column13");
			}
			/** ���ѵ��� gongfeiprice* */
			String gongfeiprice = "0";
			if (obj0.containsKey("column20")) {
				gongfeiprice = obj0.getString("column20");
			}

			boolean x = org.apache.commons.lang.math.NumberUtils
					.isNumber(kimjoong);
			double jz = 0.0;
			if (x) {
				jz = Double.valueOf(kimjoong);
			}
			if ("1".equals(iscompany)) {
				if (reMap.containsKey(bigcate)) {
					Double jz_ = (Double) reMap.get(bigcate);
					reMap.put(bigcate, jz_ + jz);
				} else {
					reMap.put(bigcate, jz);
				}
			} else {
				if (reMap2.containsKey(bigcate)) {
					Double jz_ = (Double) reMap2.get(bigcate);
					reMap2.put(bigcate, jz_ + jz);
				} else {
					reMap2.put(bigcate, jz);
				}
			}

			boolean y = org.apache.commons.lang.math.NumberUtils
					.isNumber(huishouprice);
			rePriceMap.put(bigcate, obj0);
			/** ����(��) dl007 */
			if ("dl007".equals(bigcate)) {
				if (repurity_Map.containsKey(purity)) {
					Double jz_ = (Double) repurity_Map.get(purity);
					repurity_Map.put(purity, jz_ + jz);
				} else {
					repurity_Map.put(purity, jz);
				}
			}

			Double bprice = 0.0;
			if (obj0.containsKey("column33")) {
				bprice = Double.valueOf(obj0.getString("column33"));
			}

			/** ��Ƕ�� dl001 */
			if ("dl001".equals(bigcate)) {
				if (rebpriceMap.containsKey(bigcate)) {
					Double bprice_ = (Double) rebpriceMap.get(bigcate);
					rebpriceMap.put(bigcate, bprice_ + bprice);
				} else {
					rebpriceMap.put(bigcate, bprice);
				}
			}

			/** ���� */
			if (obj0.containsKey("column12")) {
				damageMap.put(bigcate, obj0.get("column12"));
			}

		}

		Double pprice = 0.0;
		String rePrice = "";
		for (java.util.Iterator iterator = reMap.keySet().iterator(); iterator
				.hasNext();) {
			String bigcate = (String) iterator.next();

			net.sf.json.JSONObject reObject = (net.sf.json.JSONObject) rePriceMap
					.get(bigcate);
			/** ���ս�� */
			String price_ = "0";
			if (reObject.containsKey("column13")) {
				price_ = reObject.getString("column13");
			}

			/** ���ѵ��� */
			String price2_ = "0";
			if (reObject.containsKey("column20")) {
				price2_ = reObject.getString("column20");
			}

			/**
			 * * �ۿ���(%) String discount_ = reObject.getString("column30");
			 */

			String discount_ = "100";
			if (reObject.containsKey("column30")) {
				discount_ = reObject.getString("column30");
			}

			Double reJz = (Double) reMap.get(bigcate);
			Double reJz2 = (Double) reMap2.get(bigcate);
			Double sellJz = (Double) sellMap.get(bigcate);

			String iscompany = "";
			if (reObject.containsKey("column28")) {
				iscompany = reObject.getString("column28");
			}

			String damage = "0";
			if (damageMap.containsKey(bigcate)) {
				damage = (String) damageMap.get(bigcate);
			}
			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}

			String discount = (String) discount_Map.get(bigcate);

			/** ���ս�� */
			String jj = (String) sellJJMap.get(bigcate);

			/** * ��Ʒ���� */
			Double jpprice = (Double) jppriceMap.get(bigcate);
			if (jpprice == null)
				jpprice = 0.0;

			/** ��˾ */
			if (sellMap.containsKey(bigcate)) {
				/** ���۴��ڴ���* */
				/** �ƽ�(��) dl006* */
				if ("dl006".equals(bigcate)) {

					if (sellJz >= reJz) {
						/** ���ս��س��Թ��ѵ���+ʵ������������=�����Ľ���-���ս��أ������۵���ʵ�ʽ�ۡ�����ۿ�+��Ʒ���� */
						pprice += reJz * Double.valueOf(price2_)
								+ (sellJz - reJz) * Double.valueOf(jj)
								* Double.valueOf(discount) / 100 + jpprice;
						rePrice = "" + reJz * Double.valueOf(price2_);
						pricecount.append("+" + reJz + "*"
								+ Double.valueOf(price2_) + "+" + "(" + sellJz
								+ "-" + reJz + ")*" + Double.valueOf(jj) + "*"
								+ Double.valueOf(discount) + "/100+" + jpprice);
					} else {
						/** �������س��Թ��ѵ���-����أ�=���ս���-�������أ������ս��+��Ʒ���� */
						pprice += sellJz * Double.valueOf(price2_)
								- (reJz - sellJz) * Double.valueOf(price_)
								+ jpprice;
						rePrice = "" + sellJz * Double.valueOf(price2_);
						pricecount.append("+" + sellJz + "*"
								+ Double.valueOf(price2_) + "-(" + reJz + "-"
								+ sellJz + ")*" + Double.valueOf(price2_) + "+"
								+ jpprice);
					}

				}

				/** K��(��) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * ֻ�ܰ����ػ��ա����ء����յ��ۣ���ʦ���������� */
					pprice += reJz * Double.valueOf(price2_);
					rePrice = "" + reJz * Double.valueOf(price2_);
					pricecount.append("+" + reJz + "*"
							+ Double.valueOf(price2_));
				}

				/** ��Ƕ�� dl001 ��ʯ */
				if ("dl001".equals(bigcate)) {
					/**
					 * *
					 * ��ֵ�һ������ѷ�ʽΪԭ������������ֽ𣩡������ۿۣ�ԭ�����۲��ܻ�һ�ڼۣ�һ�ڼۿ��Ի����ۡ�һ�ڼۻ����۵ķ�ʽ�������Ȱ������ۿ۴��� �C
					 * ��һ�ۼۡ���
					 */
					// pprice += (reJz - sellJz) * Double.valueOf(discount)
					// / 100;
					// rePrice = "" + (reJz - sellJz)
					// * Double.valueOf(discount) / 100;
					//
					// pricecount.append("+" + "(" + reJz + "-" + sellJz
					// + ")*" + Double.valueOf(discount) + "/100");
					Double sellbprice = (Double) sellbpriceMap.get(bigcate);
					if (sellbprice == null)
						sellbprice = 0.0;
					Double rebprice = (Double) rebpriceMap.get(bigcate);
					if (rebprice == null)
						rebprice = 0.0;

					if (sellbprice >= rebprice) {
						pprice += (sellbprice - rebprice)
								* Double.valueOf(discount) / 100;
						rePrice = "" + (sellbprice - rebprice)
								* Double.valueOf(discount) / 100;
						pricecount.append("+" + "(" + sellbprice + "-"
								+ rebprice + ")*" + Double.valueOf(discount)
								+ "/100");
					} else {
						Double pppprice = sellbprice - rebprice;
						if (pppprice < 0) {
							pppprice = 0.0;
						}
						pprice += pppprice * Double.valueOf(discount) / 100;
						rePrice = "" + pppprice * Double.valueOf(discount)
								/ 100;
						pricecount.append("+" + "(" + sellbprice + "-"
								+ rebprice + ")*" + Double.valueOf(discount)
								+ "/100");
					}
				}

			} else {
				/** ���۲����ڴ���* */
				/** �ƽ�(��) dl006* */
				if ("dl006".equals(bigcate)) {

					/** ������*���ս��-���ѽ�� (ע�������� = ����) */
					pprice += reJz * Double.valueOf(price_) - reJz
							* Double.valueOf(price2_);
					rePrice = "" + reJz * Double.valueOf(price2_);
					rejgStr = reJz;
					pricecount.append("+" + reJz + "*" + Double.valueOf(price_)
							+ "-" + reJz + "*" + Double.valueOf(price2_));
				}

				/** K��(��) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * ֻ�ܰ����ػ��ա����ء����յ��ۣ���ʦ���������� */
					pprice += reJz * Double.valueOf(price2_);
					rePrice = "" + reJz * Double.valueOf(price2_);
					pricecount.append("+" + reJz + "*"
							+ Double.valueOf(price2_));
				}
			}

		}

		for (java.util.Iterator iterator = reMap2.keySet().iterator(); iterator
				.hasNext();) {
			String bigcate = (String) iterator.next();

			net.sf.json.JSONObject reObject = (net.sf.json.JSONObject) rePriceMap
					.get(bigcate);
			/** ���յ��� */
			String price_ = "0";
			if (reObject.containsKey("column13")) {
				price_ = reObject.getString("column13");
			}

			/** ���ѵ��� */
			String price2_ = "0";
			if (reObject.containsKey("column20")) {
				price2_ = reObject.getString("column20");
			}

			/**
			 * * �ۿ���(%) String discount_ = reObject.getString("column30");
			 */
			String discount_ = "100";
			if (reObject.containsKey("column30")) {
				discount_ = reObject.getString("column30");
			}

			Double reJz = (Double) reMap.get(bigcate);
			Double reJz2 = (Double) reMap2.get(bigcate);
			Double sellJz = (Double) sellMap.get(bigcate);
			/** ʣ����� */
			if (reJz == null)
				reJz = 0.0;
			if (reJz2 == null)
				reJz2 = 0.0;
			if (sellJz == null)
				sellJz = 0.0;
			Double sellJz2 = sellJz - reJz;

			String iscompany = "";
			if (reObject.containsKey("column28")) {
				iscompany = reObject.getString("column28");
			}

			String damage = "0";
			if (damageMap.containsKey(bigcate)) {
				damage = (String) damageMap.get(bigcate);
			}
			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}

			/** ���ս�� */
			String jj = (String) sellJJMap.get(bigcate);

			/** * ��Ʒ���� */
			Double jpprice = (Double) jppriceMap.get(bigcate);
			if (jpprice == null)
				jpprice = 0.0;

			String discount = (String) discount_Map.get(bigcate);

			/** �ǹ�˾ */
			if (sellMap.containsKey(bigcate)) {
				/** ���۴��ڴ���* */
				/** �ƽ�(��) dl006* */
				if ("dl006".equals(bigcate)) {

					/** ���վ��� */
					Double reNetweight = reJz2 * Double.valueOf(discount_)
							/ 100 - damage_ / 100;

					if (sellJz2 >= reNetweight) {
						/** ���۽���-���վ��أ�=���ս��ء���ɫ-���ս��ء����@2%/g�����������ۻƽ��ۡ�����ۿ�+���ѣ�=���վ��ء����ѵ���13��15Ԫ/g��+��Ʒ���� */
						pprice += (sellJz2 - (reJz2 * Double.valueOf(discount_)
								/ 100 - damage_ / 100))
								* Double.valueOf(jj)
								* Double.valueOf(discount)
								/ 100
								+ (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100)
								* Double.valueOf(price2_) + jpprice;
						rejgStr += (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100);
						rePrice = ""
								+ (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100)
								* Double.valueOf(price2_);
						pricecount.append("+" + "(" + sellJz2 + "-(" + reJz2
								+ "*" + Double.valueOf(discount_) + "/100-"
								+ damage_ + "/100)*" + Double.valueOf(jj) + "*"
								+ Double.valueOf(discount) + "/100+(" + reJz2
								+ "*" + Double.valueOf(discount_) + "/100-"
								+ damage_ + "/100" + ")*"
								+ Double.valueOf(price2_) + "+" + jpprice);
					} else {
						/** ���վ��أ�=���ս��ء���ɫ-���ս��ء����@2%/g��-���۽��ء����ս�ۣ���ʦ������-���ѣ�=���۽��ء����ѵ���13��15Ԫ/g��-��Ʒ���� */
						pprice += ((reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100) - sellJz2)
								* Double.valueOf(price_)
								- (sellJz2 * Double.valueOf(price2_)) - jpprice;
						rejgStr += (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100);
						rePrice = "" + sellJz2 * Double.valueOf(price2_);

						pricecount
								.append("+" + "((" + reJz2 + "*"
										+ Double.valueOf(discount_) + "/100-"
										+ damage_ + "/100" + ")-" + sellJz2
										+ ")*" + Double.valueOf(price_) + "-("
										+ sellJz2 + "*"
										+ Double.valueOf(price2_) + ")" + "-"
										+ jpprice);
					}

				}

				/** K��(��) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * ֻ�ܰ����ػ��ա����ء����յ��ۣ���ʦ���������� */
					pprice += reJz2 * Double.valueOf(price2_);
					rePrice = "" + reJz2 * Double.valueOf(price2_);
					pricecount.append("+" + reJz2 + "*"
							+ Double.valueOf(price2_));
				}

			} else {
				/** ���۲����ڴ���* */
				/** �ƽ�(��) dl006* */
				if ("dl006".equals(bigcate)) {

					/** ������(=����*��ɫ-����*���)*���ս��-���ѽ�� */
					pprice += (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100)
							* Double.valueOf(price_)
							- reJz2
							* Double.valueOf(price2_);
					rejgStr += (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100);

					rePrice = ""
							+ (reJz2 * Double.valueOf(discount_) / 100 - damage_ / 100)
							* Double.valueOf(price2_);

					pricecount.append("+" + "(" + reJz2 + "*"
							+ Double.valueOf(discount_) + "/100-" + damage_
							+ "/100)*" + Double.valueOf(price_) + "-" + reJz2
							+ "*" + Double.valueOf(price2_)

					);
				}

				/** K��(��) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * ֻ�ܰ����ػ��ա����ء����յ��ۣ���ʦ���������� */
					pprice += reJz2 * Double.valueOf(price2_);
					rePrice = "" + reJz2 * Double.valueOf(price2_);
					pricecount.append("+" + reJz2 + "*"
							+ Double.valueOf(price2_));
				}
			}

		}

		for (int i = 0; i < rejson_.size(); i++) {
			net.sf.json.JSONObject obj0 = rejson_.getJSONObject(i);
			/*******************************************************************
			 * ���� /** �������� type
			 ******************************************************************/
			String type = "";
			if (obj0.containsKey("column3")) {
				type = obj0.getString("column3");
			}
			/** ʵ���ɫ purity* */
			String purity = "";
			if (obj0.containsKey("column11")) {
				purity = obj0.getString("column11");
			}
			/** ���� bigcate* */
			String bigcate = "";
			if (obj0.containsKey("column8")) {
				bigcate = obj0.getString("column8");
			}
			/** �Ƿ�˾ iscompany* */
			String iscompany = "";
			if (obj0.containsKey("column28")) {
				iscompany = obj0.getString("column28");
			}
			/** ���� kimjoong* */
			String kimjoong = "";
			if (obj0.containsKey("column29")) {
				kimjoong = obj0.getString("column29");
			}
			/** ���յ��� huishouprice* */
			String price_ = "0";
			if (obj0.containsKey("column13")) {
				price_ = obj0.getString("column13");
			}
			/** ���ѵ��� gongfeiprice* */
			String price2_ = "0";
			if (obj0.containsKey("column20")) {
				price2_ = obj0.getString("column20");
			}
			/** ��� damage * */
			String damage = "0";
			if (damageMap.containsKey(bigcate)) {
				damage = (String) damageMap.get(bigcate);
			}
			/** ���ս�� */
			String jj = (String) sellJJMap.get(bigcate);

			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}
			String discount = (String) discount_Map.get(bigcate);

			/** * ��Ʒ���� */
			Double jpprice = (Double) jppriceMap.get(bigcate);
			if (jpprice == null)
				jpprice = 0.0;

			/** ���ճ�ɫ ���ɫ */
			String rediscount = "100";
			if (obj0.containsKey("column30")) {
				rediscount = (String) obj0.get("column30");
			}

			if ("0".equals(iscompany)) {

				/** ����(��) dl007 */
				if ("dl007".equals(bigcate)) {

					if (type.contains("_")) {
						String[] x = type.split("_");
						if (x.length == 2) {
							String re_ = x[0];
							String sell_ = x[1];

							Double re1 = new Double(0);
							if (repurity_Map.containsKey(re_)) {
								re1 = (Double) repurity_Map.get(re_);
							}
							Double sell1 = new Double(0);
							if (sellpurity_Map.containsKey(sell_)) {
								sell1 = (Double) sellpurity_Map.get(sell_);
							}

							Double d1 = Double.valueOf(rediscount);

							if ("PT950_PT950".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 - damage_ / 100;

								if (sell1 >= reNetweight) {
									/**
									 * [���۵Ľ���-���վ���{=���ս���-�����յĽ��ء���ģ�}]������ʵ��950�����ۡ�����ۿ� +
									 * ���ѣ�=���վ��ء����յ���-25Ԫ��23Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount) / 100
											+ (re1 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;
									rejgStr += (re1 - damage_ / 100);
									rePrice = ""
											+ ((re1 - damage_ / 100) * Double
													.valueOf(price2_));
									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "-" + damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+(" + re1 + "-" + damage_
											+ "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * ���۵Ľ���С�ڻ��յĽ��أ�(���ѣ�=���۽��ء����ѵ���)+��Ʒ����)-[���վ���{=���ս���-�����յĽ��ء���ģ�}
									 * �C���۵Ľ���]�����յ��ۣ���ѯʦ������pt950���յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 - (damage_ / 100)) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 - (damage_ / 100));
									rePrice = "" + sell1
											* Double.valueOf(price2_);
									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "-"
											+ damage_ + "/100)-" + sell1 + ")*"
											+ Double.valueOf(price_));
								}
							}
							if ("PT950_PT999".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 * d1 / 100 - damage_
										/ 100;

								if (sell1 >= reNetweight) {
									/**
									 * {���۽���-���վ���[=���ս��ء�95%@�ǳ�ɫ �C ���ս��ء���ģ�5%
									 * �����ù̶���ֵ��] } �� ����PT999�ĵ��� �� ����ۿ� +
									 * ���ѣ����վ��ء����ѵ���30Ԫ��28Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 * d1 / 100 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ (re1 * d1 / 100 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;

									rejgStr += (re1 * d1 / 100 - damage_ / 100);
									rePrice = ""
											+ (re1 * d1 / 100 - damage_ / 100)
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "*" + d1 + "/100-"
											+ damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+(" + re1 + "*" + d1
											+ "/100-" + damage_ + "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * �����ѣ����۽��ء����ѵ���30Ԫ��28Ԫ��+��Ʒ���ѣ�-{���վ���[=���ս��ء�95%@�ǳ�ɫ �C
									 * ���ս��ء���ģ�5% �����ù̶���ֵ��] �C ���۽��� } ��
									 * ���ս�ۣ���ʦ��ȷ����pt950�Ļ��յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 * d1 / 100 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 * d1 / 100 - damage_ / 100);
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "*" + d1
											+ "/100-" + damage_ + "/100)-"
											+ sell1 + ")*"
											+ Double.valueOf(price_));
								}

							}
							if ("PT999_PT950".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 * d1 / 100 - damage_
										/ 100;
								if (sell1 >= reNetweight) {
									/**
									 * {���۽���-���վ��� } �� ����PT950�ĵ��� �� ����ۿ� +
									 * ���ѣ����վ��ء����ѵ���25Ԫ��23Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 * d1 / 100 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ (re1 * d1 / 100 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;
									rejgStr += (re1 * d1 / 100 - damage_ / 100);
									rePrice = ""
											+ (re1 * d1 / 100 - damage_ / 100)
											* Double.valueOf(price_);

									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "*" + d1 + "/100-"
											+ damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+(" + re1 + "*" + d1
											+ "/100-" + damage_ + "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * �����ѣ����۽��ء����ѵ���25Ԫ��23Ԫ��+��Ʒ���ѣ�-{���վ��� �C ���۽��� } ��
									 * ���յ��ۣ���ʦ��ȷ����pt999�Ļ��յ��ۣ� -
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 * d1 / 100 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 * d1 / 100 - damage_ / 100);
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "*" + d1
											+ "/100-" + damage_ + "/100)-"
											+ sell1 + ")*"
											+ Double.valueOf(price_));
								}
							}
							if ("PT999_PT999".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 - (damage_ / 100);
								if (sell1 >= reNetweight) {
									/**
									 * {���۽���-���վ��أ�=���ս���-���ս��ء���ģ� } �� ����PT999�ĵ��� ��
									 * ����ۿ� + ���ѣ����վ��ء����ѵ���28Ԫ��30Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 - (damage_ / 100)))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ ((re1 - damage_ / 100) * Double
													.valueOf(price2_))
											+ jpprice;
									rePrice = ""
											+ ((re1 - (damage_ / 100)) * Double
													.valueOf(price2_));
									rejgStr += (re1 - (damage_ / 100));
									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "-" + damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+((" + re1 + "-" + damage_
											+ "/100)*"
											+ Double.valueOf(price2_) + ")"
											+ "+" + jpprice);
								} else {
									/**
									 * (���ѣ����۽��ء�30Ԫ��28Ԫ��+��Ʒ����)-{���վ��أ�=���ս���-���ս��ء���ģ� �C
									 * ���۽��� } �� ���յ��ۣ���ʦ��ȷ����pt999�Ļ��յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 - damage_ / 100);
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "-"
											+ damage_ + "/100)-" + sell1 + ")*"
											+ Double.valueOf(price_));

								}
							}
						}
					}
				}
			}

			if ("1".equals(iscompany)) {

				/** ����(��) dl007 */
				if ("dl007".equals(bigcate)) {
					if (type.contains("_")) {
						String[] x = type.split("_");
						if (x.length == 2) {
							String re_ = x[0];
							String sell_ = x[1];

							Double re1 = new Double(0);
							if (repurity_Map.containsKey(re_)) {
								re1 = (Double) repurity_Map.get(re_);
							}
							Double sell1 = new Double(0);
							if (sellpurity_Map.containsKey(sell_)) {
								sell1 = (Double) sellpurity_Map.get(sell_);
							}

							if ("PT950_PT950".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 - damage_ / 100;
								if (sell1 >= reNetweight) {
									/**
									 * [���۵Ľ���-���վ���{=���ս���-�����յĽ��ء���ģ�}]������ʵ��950�����ۡ�����ۿ� +
									 * ���ѣ�=���վ��ء����յ���-25Ԫ��23Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount) / 100
											+ (re1 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;
									rejgStr += (re1 - damage_ / 100);
									rePrice = ""
											+ ((re1 - damage_ / 100) * Double
													.valueOf(price2_));
									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "-" + damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+(" + re1 + "-" + damage_
											+ "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * �����ѣ�=���۽��ء����ѵ��ۣ�+��Ʒ���ѣ�-���۵Ľ���С�ڻ��յĽ��أ�[���վ���{=���ս���-�����յĽ��ء���ģ�}
									 * �C���۵Ľ���]�����յ��ۣ���ѯʦ������pt950���յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 - (damage_ / 100));
									rePrice = "" + sell1
											* Double.valueOf(price2_);
									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "-"
											+ damage_ + "/100)-" + sell1 + ")*"
											+ Double.valueOf(price_));
								}
							}
							if ("PT950_PT999".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 * 95 / 100 - damage_
										/ 100;
								if (sell1 >= reNetweight) {
									/**
									 * {���۽���-���վ���[=���ս��ء�95%@�ǳ�ɫ �C ���ս��ء���ģ�5%
									 * �����ù̶���ֵ��] } �� ����PT999�ĵ��� �� ����ۿ� +
									 * ���ѣ����վ��ء����ѵ���30Ԫ��28Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 * 95 / 100 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ (re1 * 95 / 100 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;

									rejgStr += (re1 * 95 / 100 - damage_ / 100);
									rePrice = ""
											+ (re1 * 95 / 100 - damage_ / 100)
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "*95/100-" + damage_
											+ "/100))*" + Double.valueOf(jj)
											+ "*" + Double.valueOf(discount)
											+ "/100+(" + re1 + "*95/100-"
											+ damage_ + "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * (���ѣ����۽��ء����ѵ���30Ԫ��28Ԫ��+��Ʒ����)-{���վ���[=���ս��ء�95%@�ǳ�ɫ �C
									 * ���ս��ء���ģ�5% �����ù̶���ֵ��] �C ���۽��� } ��
									 * ���ս�ۣ���ʦ��ȷ����pt950�Ļ��յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 * 95 / 100 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 * 95 / 100 - damage_ / 100);
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1
											+ "*95/100-" + damage_ + "/100)-"
											+ sell1 + ")*"
											+ Double.valueOf(price_));
								}

							}
							if ("PT999_PT950".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 * 99.9 / 100 - damage_
										/ 100;
								if (sell1 >= reNetweight) {
									/**
									 * {���۽���-���վ��� } �� ����PT950�ĵ��� �� ����ۿ� +
									 * ���ѣ����վ��ء����ѵ���25Ԫ��23Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 * 99.9 / 100 - damage_ / 100))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ (re1 * 99.9 / 100 - damage_ / 100)
											* Double.valueOf(price2_) + jpprice;
									rejgStr += (re1 * 99.9 / 100 - damage_ / 100);
									rePrice = ""
											+ (re1 * 99.9 / 100 - damage_ / 100)
											* Double.valueOf(price_);

									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "*99.9/100-" + damage_
											+ "/100))*" + Double.valueOf(jj)
											+ "*" + Double.valueOf(discount)
											+ "/100+(" + re1 + "*99.9/100-"
											+ damage_ + "/100)*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice);
								} else {
									/**
									 * �����ѣ����۽��ء����ѵ���25Ԫ��23Ԫ��+��Ʒ����)- {���վ��� �C ���۽��� } ��
									 * ���յ��ۣ���ʦ��ȷ����pt999�Ļ��յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 * 99.9 / 100 - damage_ / 100) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 * 99.9 / 100 - damage_ / 100);
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1
											+ "*99.9/100-" + damage_ + "/100)-"
											+ sell1 + ")*"
											+ Double.valueOf(price_));
								}
							}
							if ("PT999_PT999".equals(type)) {
								/** ���վ��� */
								Double reNetweight = re1 - (damage_ / 100);
								if (sell1 > reNetweight) {
									/**
									 * {���۽���-���վ��أ�=���ս���-���ս��ء���ģ� } �� ����PT999�ĵ��� ��
									 * ����ۿ� + ���ѣ����վ��ء����ѵ���28Ԫ��30Ԫ��+��Ʒ����
									 */
									pprice += (sell1 - (re1 - (damage_ / 100)))
											* Double.valueOf(jj)
											* Double.valueOf(discount)
											/ 100
											+ ((re1 - (damage_ / 100)) * Double
													.valueOf(price2_))
											+ jpprice;
									rePrice = ""
											+ ((re1 - (damage_ / 100)) * Double
													.valueOf(price2_));
									rejgStr += (re1 - (damage_ / 100));
									pricecount.append("+" + "(" + sell1 + "-("
											+ re1 + "-" + damage_ + "/100))*"
											+ Double.valueOf(jj) + "*"
											+ Double.valueOf(discount)
											+ "/100+((" + re1 + "-" + damage_
											+ "/100)*"
											+ Double.valueOf(price2_) + ")"
											+ "+" + jpprice);
								} else {
									/**
									 * (���ѣ����۽��ء�30Ԫ��28Ԫ��+��Ʒ����)-{���վ��أ�=���ս���-���ս��ء���ģ� �C
									 * ���۽��� } �� ���յ��ۣ���ʦ��ȷ����pt999�Ļ��յ��ۣ�
									 * 
									 */
									pprice += (sell1 * Double.valueOf(price2_) + jpprice)
											- ((re1 - (damage_ / 100)) - sell1)
											* Double.valueOf(price_);
									rejgStr += (re1 - (damage_ / 100));
									rePrice = "" + sell1
											* Double.valueOf(price2_);

									pricecount.append("+" + "(" + sell1 + "*"
											+ Double.valueOf(price2_) + "+"
											+ jpprice + ")-((" + re1 + "-"
											+ damage_ + "/100)-" + sell1 + ")*"
											+ Double.valueOf(price_));

								}
							}

						}
					}
				}

			}
		}

		System.out.println("��ʽ:"
				+ pricecount.toString().replace("^^+", "").replaceAll("\\*",
						"x"));
		System.out.println("{'price':" + pprice + ",'reprice':'" + rePrice
				+ "','rejgStr':'" + ((rejgStr == 0.0) ? "" : "" + rejgStr)
				+ "'}");
		return "{'price':" + pprice + ",'reprice':'" + rePrice
				+ "','rejgStr':'" + ((rejgStr == 0.0) ? "" : "" + rejgStr)
				+ "'}";

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		todo();
	}

}
