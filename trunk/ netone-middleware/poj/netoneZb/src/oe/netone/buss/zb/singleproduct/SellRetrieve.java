package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.HashMap;
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
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ_988','column4':'千足金项链
		// ','column5':'100.00','column6':'0','column7':'843','column9':'10','column10':'84.3','column11':'927.3','column12':'75%','column13':'90%','column14':'67.5%','column15':'625.93','column19':'dl006','column20':'20','column21':'20','column23':'1056YQ2945','column26':'8.43','column28':'lx001','column32':'8.43','column35':'1350.00','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'黄金回收','column8':'dl006','column11':'CS01','column13':'400','column20':'15','column28':'0','column29':'9','column30':'99'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ8933','column4':'黄金08','column5':'390.00','column6':'20','column7':'3120','column9':'20','column10':'160','column11':'3120','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'2286.00','column19':'dl006','column20':'088','column21':'23','column23':'010','column26':'8','column28':'lx001','column32':'8','column35':'228.60','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'黄金回收','column8':'dl006','column12':'1','column13':'390','column20':'13','column21':'1352.0','column28':'0','column29':'7','column30':'99'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ8933','column4':'黄金08','column5':'390.00','column6':'20','column7':'3120','column9':'20','column10':'160','column11':'3120','column12':'75.00%','column13':'90%','column14':'67.50%','column15':'2286.00','column19':'dl006','column20':'088','column21':'23','column23':'010','column26':'8','column28':'lx001','column32':'8','column35':'228.60','column36':'01'}]";

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
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ_4568','column4':'千足金项链','column5':'390.00','column6':'0','column7':'3288','column9':'10','column10':'84.3','column11':'3287.7','column12':'75%','column13':'90%','column14':'67.5%','column15':'675.00','column19':'dl006','column20':'10','column21':'10','column23':'1056YQ2945','column26':'8.43','column28':'lx001','column32':'8.43','column35':'67.50','column36':'01'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'PT950_PT950','column8':'dl007','column11':'PT950','column13':'309','column20':'20','column21':'2514.32','column28':'1','column29':'11'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'BJ2222','column4':'BJ020202','column5':'390.00','column6':'10','column7':'3288','column9':'10','column10':'84.3','column11':'4000','column12':'75%','column13':'90%','column14':'67.5%','column15':'2700.00','column19':'dl007','column20':'100','column21':'122','column23':'CD896','column26':'15','column28':'lx001','column32':'15','column35':'270.00','column36':'01','column37':'0'}]";

		// String rejson =
		// "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'91.00','column28':'1','column29':'7'},{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'26.00','column28':'1','column29':'1'}]";
		// String selljson =
		// "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ08502','column4':'黄金02','column5':'100.00','column6':'20','column7':'810','column9':'10','column10':'81','column11':'810','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'708.50','column19':'dl006','column20':'Q123','column21':'C120','column23':'Y862','column26':'8.1','column28':'lx001','column32':'8.1','column35':'70.85','column36':'01','column37':'0'}]";

		String rejson = "[{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'91.00','column28':'1','column29':'7'},{'formcode':'e17cb211a84911e19b54fb13b166e993_','column3':'hj','column8':'dl006','column13':'390','column20':'13','column21':'39.00','column28':'0','column29':'1'}]";
		String selljson = "[{'formcode':'1dde2f9fa81711e19b54fb13b166e993_','column3':'HJ08502','column4':'黄金02','column5':'100.00','column6':'20','column7':'810','column9':'10','column10':'81','column11':'810','column12':'75.00%','column13':'100%','column14':'75.00%','column15':'708.50','column19':'dl006','column20':'Q123','column21':'C120','column23':'Y862','column26':'8.1','column28':'lx001','column32':'8.1','column35':'70.85','column36':'01','column37':'0'}]";

		/**
		 * 销售明细 <BR>
		 * 条形码 column3 <BR>
		 * 大类 column19 <BR>
		 * 实售折扣 column14
		 */

		/**
		 * 回收 回收类型 column3<BR>
		 * 实测成色 column11 <BR>
		 * 大类 column8 <BR>
		 * 是否公司 column28 <BR>
		 * 金重 column29 <BR>
		 * 回收单价 column13 <BR>
		 * 工费单价 column20 <BR>
		 * 折扣率 column30 <BR>
		 * 损耗 column12
		 */

		net.sf.json.JSONArray selljson_ = net.sf.json.JSONArray
				.fromObject(selljson);
		net.sf.json.JSONArray rejson_ = net.sf.json.JSONArray
				.fromObject(rejson);
		StringBuffer pricecount = new StringBuffer();
		pricecount.append("^^");

		Map dlmap = new HashMap();

		Map sellMap = new HashMap();
		Map reMap = new HashMap();
		/** 非公司 * */
		Map reMap2 = new HashMap();
		Map rePriceMap = new HashMap();

		Map sellpurity_Map = new HashMap();
		Map repurity_Map = new HashMap();

		/** 实售折扣率 */
		Map discount_Map = new HashMap();

		if (rejson_.size() < 0)
			return "{'price':0}";

		for (int j = 0; j < selljson_.size(); j++) {
			net.sf.json.JSONObject obj0 = selljson_.getJSONObject(j);

			/** 销售 */
			/** 条形码 code */
			String code_ = "";
			if (obj0.containsKey("column3")) {
				code_ = obj0.getString("column3");
			}
			/** 材料成色 purity */
			String purity_ = "";
			/** 大类 bigcate */
			String bigcate_ = "";
			if (obj0.containsKey("column19")) {
				bigcate_ = obj0.getString("column19");
			}
			/** 金重 kimjoong */
			String kimjoong_ = "0";
			/** column14 实售折扣 */
			String discount_ = "";
			if (obj0.containsKey("column14")) {
				discount_ = obj0.getString("column14");
			}

			if (org.apache.commons.lang.StringUtils.isNotEmpty(discount_)) {
				discount_Map.put(bigcate_, discount_.replace("%", ""));
			} else {
				discount_Map.put(bigcate_, "100");
			}

			/** 销售 */
			/** 条形码 */
			/** 材料成色 column52 */
			/** 大类 */
			/** 金重 column17 */

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
			/** 铂金(克) dl007 */
			if ("dl007".equals(bigcate_)) {
				if (sellpurity_Map.containsKey(purity_)) {
					Double jz_ = (Double) sellpurity_Map.get(purity_);
					sellpurity_Map.put(purity_, jz_ + jz);
				} else {
					sellpurity_Map.put(purity_, jz);
				}
			}
		}

		for (int i = 0; i < rejson_.size(); i++) {
			net.sf.json.JSONObject obj0 = rejson_.getJSONObject(i);
			/** 回收 * */
			/** 回收类型 type* */
			String type = "";
			if (obj0.containsKey("column3")) {
				type = obj0.getString("column3");
			}
			/** 实测成色 purity* */
			String purity = "";
			if (obj0.containsKey("column11")) {
				purity = obj0.getString("column11");
			}
			/** 大类 bigcate* */
			String bigcate = "";
			if (obj0.containsKey("column8")) {
				bigcate = obj0.getString("column8");
			}
			/** 是否公司 iscompany* */
			String iscompany = "";
			if (obj0.containsKey("column28")) {
				iscompany = obj0.getString("column28");
			}
			/** 金重 kimjoong* */
			String kimjoong = "0";
			if (obj0.containsKey("column29")) {
				kimjoong = obj0.getString("column29");
			}
			/** 当日金价 huishouprice* */
			String huishouprice = "0";
			if (obj0.containsKey("column13")) {
				huishouprice = obj0.getString("column13");
			}
			/** 工费单价 回收单价 gongfeiprice* */
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
			/** 铂金(克) dl007 */
			if ("dl007".equals(bigcate)) {
				if (repurity_Map.containsKey(purity)) {
					Double jz_ = (Double) repurity_Map.get(purity);
					repurity_Map.put(purity, jz_ + jz);
				} else {
					repurity_Map.put(purity, jz);
				}
			}
		}

		Double pprice = 0.0;
		String rePrice = "";
		for (java.util.Iterator iterator = reMap.keySet().iterator(); iterator
				.hasNext();) {
			String bigcate = (String) iterator.next();

			net.sf.json.JSONObject reObject = (net.sf.json.JSONObject) rePriceMap
					.get(bigcate);
			/** 当日金价 */
			String price_ = "0";
			if (reObject.containsKey("column13")) {
				price_ = reObject.getString("column13");
			}

			/** 回收单价 */
			String price2_ = "0";
			if (reObject.containsKey("column20")) {
				price2_ = reObject.getString("column20");
			}

			/**
			 * * 折扣率(%) String discount_ = reObject.getString("column30");
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
			if (reObject.containsKey("column12")) {
				damage = reObject.getString("column12");
			}
			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}

			String discount = (String) discount_Map.get(bigcate);

			/** 公司 */
			if (sellMap.containsKey(bigcate)) {
				/** 销售存在大类* */
				/** 黄金(克) dl006* */
				if ("dl006".equals(bigcate)) {

					if (sellJz > reJz) {
						/** 回收金重乘以回收单价（15或13元，这个回收单价是指工费回收单价）+实际添金的重量（=卖出的金重-回收金重）×当天实际金价×相关折扣* */
						pprice += reJz * Double.valueOf(price2_)
								+ (sellJz - reJz) * Double.valueOf(price_)
								* Double.valueOf(discount) / 100;
						rePrice = "" + reJz * Double.valueOf(price2_);
						pricecount.append("+" + reJz + "*"
								+ Double.valueOf(price2_) + "+" + "(" + sellJz
								+ "-" + reJz + ")*" + Double.valueOf(price_)
								+ "*" + Double.valueOf(discount) + "/100");
					} else {
						/** 卖出金重乘以回收单价（15或13元，这个回收单价是指工费回收单价）-余金重（=回收金重-卖出金重）×回收单价（只能收到输入* */
						pprice += sellJz * Double.valueOf(price2_)
								+ (reJz - sellJz) * Double.valueOf(price2_);
						rePrice = "" + sellJz * Double.valueOf(price2_);
						pricecount.append("+" + sellJz + "*"
								+ Double.valueOf(price2_) + "+(" + reJz + "-"
								+ sellJz + ")*" + Double.valueOf(price2_));
					}

				}

				/** K金(克) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * 只能按金重回收。金重×回收单价（由师傅来定）。 */
					pprice += reJz * Double.valueOf(price2_);
					rePrice = "" + reJz * Double.valueOf(price2_);
					pricecount.append("+" + reJz + "*"
							+ Double.valueOf(price2_));
				}

				/** 镶嵌类 dl001 钻石 */
				if ("dl001".equals(bigcate)) {
					if (sellJz <= reJz) {
						/**
						 * *
						 * 等值兑换，消费方式为原价相减（不返现金）×当天折扣；原则：正价不能换一口价，一口价可以换正价。一口价换正价的方式：正价先按当天折扣打折 –
						 * “一扣价”。
						 */
						pprice += (reJz - sellJz) * Double.valueOf(discount)
								/ 100;
						rePrice = "" + (reJz - sellJz)
								* Double.valueOf(discount) / 100;

						pricecount.append("+" + "(" + reJz + "-" + sellJz
								+ ")* " + Double.valueOf(discount) + "/100");
					}
				}

			} else {
				/** 销售不存在大类* */
				/** 黄金(克) dl006* */
				if ("dl006".equals(bigcate)) {

					/** 卖出金重乘以回收单价（15或13元，这个回收单价是指工费回收单价）-余金重（=回收金重-卖出金重）×回收单价（只能收到输入* */
					pprice += sellJz * Double.valueOf(price2_)
							+ (reJz - sellJz) * reJz;
					rePrice = "" + sellJz * Double.valueOf(price2_);
					pricecount.append("+" + sellJz + "*" + price2_ + "+" + "("
							+ reJz + "-" + sellJz + ")*" + reJz);
				}
			}

		}

		for (java.util.Iterator iterator = reMap2.keySet().iterator(); iterator
				.hasNext();) {
			String bigcate = (String) iterator.next();

			net.sf.json.JSONObject reObject = (net.sf.json.JSONObject) rePriceMap
					.get(bigcate);
			/** 当日金价 */
			String price_ = "0";
			if (reObject.containsKey("column13")) {
				price_ = reObject.getString("column13");
			}

			/** 回收单价 */
			String price2_ = "0";
			if (reObject.containsKey("column20")) {
				price2_ = reObject.getString("column20");
			}

			/**
			 * * 折扣率(%) String discount_ = reObject.getString("column30");
			 */

			String discount_ = "100";
			if (reObject.containsKey("column30")) {
				discount_ = reObject.getString("column30");
			}

			Double reJz = (Double) reMap.get(bigcate);
			Double reJz2 = (Double) reMap2.get(bigcate);
			Double sellJz = (Double) sellMap.get(bigcate);
			/** 剩余金重 */
			Double sellJz2 = sellJz - reJz;

			String iscompany = "";
			if (reObject.containsKey("column28")) {
				iscompany = reObject.getString("column28");
			}

			String damage = "0";
			if (reObject.containsKey("column12")) {
				damage = reObject.getString("column12");
			}
			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}

			String discount = (String) discount_Map.get(bigcate);

			/** 非公司 */
			if (sellMap.containsKey(bigcate)) {
				/** 销售存在大类* */
				/** 黄金(克) dl006* */
				if ("dl006".equals(bigcate)) {

					if (sellJz2 > reJz2) {
						/** 销售金重-回收净重（=回收金重×成色-回收金重×损耗@2%/g）×当天黄金金价×相关折扣+工费（=回收净重×13或15元/g） */
						pprice += (sellJz2 - (reJz2 * Double.valueOf(discount_)
								/ 100 - reJz2 * damage_ * 0.02))
								* Double.valueOf(price_)
								* Double.valueOf(discount)
								/ 100
								+ (reJz2 * Double.valueOf(discount_) / 100 - reJz2
										* damage_ * 0.02)
								* Double.valueOf(price2_);
						rePrice = ""
								+ (reJz2 * Double.valueOf(discount_) / 100 - reJz2
										* damage_ * 0.02)
								* Double.valueOf(price2_);
						pricecount.append("+" + "(" + sellJz2 + "-(" + reJz2
								+ "*" + Double.valueOf(discount_) + "/100-"
								+ reJz2 + "*" + damage_ + "*0.02))*"
								+ Double.valueOf(price_) + "*"
								+ Double.valueOf(discount) + "/100+(" + reJz2
								+ "*" + Double.valueOf(discount_) + "/100-"
								+ reJz2 + "*" + damage_ + "*0.02" + ")*"
								+ Double.valueOf(price2_));
					} else {
						/** 销售金重-回收净重（=回收金重×成色-回收金重×损耗@2%/g）×当天回收价（由师傅定）-工费（=销售金重×13或15元/g） */
						pprice += (sellJz2 - (reJz2 * Double.valueOf(discount_)
								/ 100 - reJz2 * damage_ * 0.02))
								* Double.valueOf(price2_)
								- (sellJz2 * Double.valueOf(price2_));

						rePrice = "" + sellJz2 * Double.valueOf(price2_)
								* Double.valueOf(price2_);

						pricecount.append("+" + "(" + sellJz2 + "-(" + reJz2
								+ "*" + Double.valueOf(discount_) + "/100-"
								+ reJz2 + "*" + damage_ + "*0.02" + "))*"
								+ Double.valueOf(price2_) + "-(" + sellJz2
								+ "*" + Double.valueOf(price2_) + ")");
					}

				}

				/** K金(克) dl009 */
				if ("dl009".equals(bigcate)) {
					/** * 只能按金重回收。金重×回收单价（由师傅来定）。 */
					pprice += reJz * Double.valueOf(price2_);
					pricecount.append("+" + reJz + "*"
							+ Double.valueOf(price2_));
				}

			} else {
				/** 销售不存在大类* */
				/** 黄金(克) dl006* */
				if ("dl006".equals(bigcate)) {

					/** 卖出金重乘以回收单价（15或13元，这个回收单价是指工费回收单价）-余金重（=回收金重-卖出金重）×回收单价（只能收到输入 */
					pprice += sellJz2 * Double.valueOf(price2_)
							+ (reJz - sellJz2) * reJz;

					rePrice = "" + sellJz2 * Double.valueOf(price2_);

					pricecount.append("+" + sellJz2 + "*"
							+ Double.valueOf(price_) + "+(" + reJz + "-"
							+ sellJz2 + ")*" + reJz);
				}
			}

		}

		for (int i = 0; i < rejson_.size(); i++) {
			net.sf.json.JSONObject obj0 = rejson_.getJSONObject(i);
			/*******************************************************************
			 * 回收 /** 回收类型 type
			 ******************************************************************/
			String type = "";
			if (obj0.containsKey("column3")) {
				type = obj0.getString("column3");
			}
			/** 实测成色 purity* */
			String purity = "";
			if (obj0.containsKey("column11")) {
				purity = obj0.getString("column11");
			}
			/** 大类 bigcate* */
			String bigcate = "";
			if (obj0.containsKey("column8")) {
				bigcate = obj0.getString("column8");
			}
			/** 是否公司 iscompany* */
			String iscompany = "";
			if (obj0.containsKey("column28")) {
				iscompany = obj0.getString("column28");
			}
			/** 金重 kimjoong* */
			String kimjoong = "";
			if (obj0.containsKey("column29")) {
				kimjoong = obj0.getString("column29");
			}
			/** 回收单价 huishouprice* */
			String price_ = "0";
			if (obj0.containsKey("column13")) {
				price_ = obj0.getString("column13");
			}
			/** 工费单价 gongfeiprice* */
			String price2_ = "0";
			if (obj0.containsKey("column20")) {
				price2_ = obj0.getString("column20");
			}
			/** 损耗 damage * */
			String damage = "0";
			if (obj0.containsKey("column12")) {
				damage = obj0.getString("column12");
			}

			Double damage_ = 0.0;
			if (org.apache.commons.lang.StringUtils.isNotEmpty(damage)) {
				damage_ = Double.valueOf(damage);
			}
			String discount = (String) discount_Map.get(bigcate);
			if (!"1".equals(iscompany)) {

			}

			/** 铂金(克) dl007 */
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
							if (sell1 > re1) {
								/**
								 * [回收金重-（回收的金重×损耗）}]×当天实际950铂金金价×相关折扣 +
								 * 工费（=回收净重×回收单价-25元、23元*
								 */
								pprice += (re1 - (re1 * damage_))
										* Double.valueOf(price_)
										* Double.valueOf(discount)
										/ 100
										+ ((re1 - (re1 * damage_)) * Double
												.valueOf(price2_));
								rePrice = ""
										+ ((re1 - (re1 * damage_)) * Double
												.valueOf(price2_));
								pricecount
										.append("+" + "(" + re1 + "-(" + re1
												+ "*" + damage_ + "))*"
												+ Double.valueOf(price_) + "*"
												+ Double.valueOf(discount)
												+ "/100+((" + re1 + "-(" + re1
												+ "*" + damage_ + "))*"
												+ Double.valueOf(price2_) + ")");
							} else {
								/**
								 * 销售的金重小于回收的金重，[回收净重{=回收金重-（回收的金重×损耗）}
								 * –销售的金重]×回收单价（咨询师傅定，pt950回收单价）-
								 * 工费（=销售金重×回收单价-25元、23元）。
								 */
								pprice += (((re1 - (re1 * damage_)) - sell1)
										* Double.valueOf(price2_) - sell1
										* Double.valueOf(price2_));
								rePrice = "" + sell1 * Double.valueOf(price2_);
								pricecount.append("+" + "(((" + re1 + "-("
										+ re1 + "*" + damage_ + "))-" + sell1
										+ ")*" + Double.valueOf(price2_) + "-"
										+ sell1 + "*" + Double.valueOf(price2_)
										+ ")");
							}
						}
						if ("PT950_PT999".equals(type)) {
							if (sell1 > re1) {
								/**
								 * {销售金重-回收净重[=回收金重×95%@是成色 – 回收金重×损耗（5%
								 * 可以用固定的值）] } × 当天PT999的单价 × 相关折扣 +
								 * 工费（回收净重×30元或28元）*
								 */
								pprice += (sell1 - (re1 * 95 / 100 - re1 * 5 / 100))
										* Double.valueOf(price2_)
										* Double.valueOf(discount)
										/ 100
										+ (re1 * 95 / 100 - re1 * 5 / 100)
										* Double.valueOf(price2_);

								rePrice = "" + (re1 * 95 / 100 - re1 * 5 / 100)
										* Double.valueOf(price2_);

								pricecount.append("+" + "(" + sell1 + "-("
										+ re1 + "*95/100-" + re1 + "*5/100))*"
										+ Double.valueOf(price2_) + "*"
										+ Double.valueOf(discount) + "/100+("
										+ re1 + "*95/100-" + re1 + "*5/100)*"
										+ Double.valueOf(price2_));
							} else {
								/**
								 * {回收净重[=回收金重×95%@是成色 – 回收金重×损耗（5% 可以用固定的值）] –
								 * 销售金重 } × 回收单价（由师傅确定，pt950的回收单价） -
								 * 工费（销售金重×30元或28元）
								 */
								pprice += ((re1 * 95 / 100 - re1 * 5 / 100) - sell1)
										* Double.valueOf(price2_)
										- sell1
										* Double.valueOf(price2_);

								rePrice = "" + sell1 * Double.valueOf(price2_);

								pricecount.append("+" + "((" + re1 + "*95/100-"
										+ re1 + "*5/100)-" + sell1 + ")*"
										+ Double.valueOf(price2_) + "-" + sell1
										+ "*" + Double.valueOf(price2_));
							}

						}
						if ("PT999_PT950".equals(type)) {
							if (sell1 > re1) {
								/**
								 * {销售金重-回收净重 } × 当天PT950的单价 × 相关折扣 +
								 * 工费（回收净重×25元或23元）
								 */
								pprice += (sell1 - (re1 * 99.9 / 100 - re1 * 0.1 / 100))
										* Double.valueOf(price2_)
										* Double.valueOf(discount)
										/ 100
										+ (re1 * 99.9 / 100 - re1 * 0.1 / 100)
										* Double.valueOf(price2_);

								rePrice = ""
										+ (re1 * 99.9 / 100 - re1 * 0.1 / 100)
										* Double.valueOf(price2_);

								pricecount.append("+" + "(" + sell1 + "-("
										+ re1 + "*99.9/100-" + re1
										+ "*0.1/100))*"
										+ Double.valueOf(price2_) + "*"
										+ Double.valueOf(discount) + "/100+("
										+ re1 + "*99.9/100-" + re1
										+ "*0.1/100)*"
										+ Double.valueOf(price2_));
							} else {
								/**
								 * {回收净重 – 销售金重 } × 回收单价（由师傅确定，pt999的回收单价） -
								 * 工费（销售金重×25元或23元）
								 */
								pprice += ((re1 * 99.9 / 100 - re1 * 0.1 / 100) - sell1)
										* Double.valueOf(price2_)
										- sell1
										* Double.valueOf(price2_);

								rePrice = "" + sell1 * Double.valueOf(price2_);

								pricecount.append("+" + "((" + re1
										+ "*99.9/100-" + re1 + "*0.1/100)-"
										+ sell1 + ")*"
										+ Double.valueOf(price2_) + "-" + sell1
										+ "*" + Double.valueOf(price2_));
							}
						}
						if ("PT999_PT999".equals(type)) {
							if (sell1 > re1) {
								/**
								 * {销售金重-回收净重（=回收金重-回收金重×损耗） } × 当天PT999的单价 ×
								 * 相关折扣 + 工费（回收净重×28元或30元）
								 */
								pprice += (re1 - (re1 * damage_))
										* Double.valueOf(price2_)
										* Double.valueOf(discount)
										/ 100
										+ ((re1 - (re1 * damage_)) * Double
												.valueOf(price2_));
								rePrice = ""
										+ ((re1 - (re1 * damage_)) * Double
												.valueOf(price2_));

								pricecount
										.append("+" + "(" + re1 + "-(" + re1
												+ "*" + damage_ + "))*"
												+ Double.valueOf(price2_) + "*"
												+ Double.valueOf(discount)
												+ "/100+((" + re1 + "-(" + re1
												+ "*" + damage_ + "))*"
												+ Double.valueOf(price2_) + ")");
							} else {
								/**
								 * {回收净重（=回收金重-回收金重×损耗） – 销售金重 } ×
								 * 回收单价（由师傅确定，pt999的回收单价） - 工费（销售金重×30元或28元）
								 */
								pprice += (((re1 - (re1 * damage_)) - sell1)
										* Double.valueOf(price2_) - sell1
										* Double.valueOf(price2_));

								rePrice = "" + sell1 * Double.valueOf(price2_);

								pricecount.append("+" + "(((" + re1 + "-("
										+ re1 + "*" + damage_ + "))-" + sell1
										+ ")*" + Double.valueOf(price2_) + "-"
										+ sell1 + "*" + Double.valueOf(price2_)
										+ ")");

							}
						}

					}
				}

			}
		}

		System.out.println("公式:"
				+ pricecount.toString().replace("^^+", "").replaceAll("\\*",
						"x"));
		System.out.println("{'price':" + pprice + ",'reprice':'" + rePrice
				+ "'}");

		return "{'price':" + pprice + ",'reprice':'" + rePrice + "'}";

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		todo();
	}

}
