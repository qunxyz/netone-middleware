package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class SSRK_Script extends OeScript {
	public void queRen() {
		try {
			dy.set("$(lsh)" + ":" + "$(formcode)", "column19", "确认");
			dy
					.set("$(lsh)" + ":" + "$(formcode)", "column16",
							"$(participant)");
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a1 = dateformat.format(new java.util.Date());
			dy.set("$(lsh)" + ":" + "$(formcode)", "column17", a1);

			Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

			oe.midware.workflow.engine.rule2.func.STools st = new oe.midware.workflow.engine.rule2.func.STools();

			List list = db

					.queryData(

							con,

							"select t.*,t2.column15 FXS,t2.column14 DL,t2.column3 DH,t2.column5 ZDR,t2.column6 ZDSJ,t2.column9 RKRQ from DY_271334208897441 t,DY_271334208897439 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");

			con = db.con("DATASOURCE.DATASOURCE.DYFORM");

			StringBuffer par = new StringBuffer("");

			String split = "";

			boolean flag = true;

			String lshxxxxf = "";

			String lshxxxxr = "";

			System.out.println(list.size());

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {

				Map object = (Map) iterator.next();

				par.append(split + "'" + (String) object.get("column4") + "'");

				split = ",";

				if (flag) {

					oe.cav.bean.logic.bus.TCsBus busr = new oe.cav.bean.logic.bus.TCsBus();

					busr.setFatherlsh("1");
					busr.setColumn3((String) object.get("DH"));
					
					busr.setColumn4((String) object.get("ZDR"));
					
					busr.setColumn5((String) object.get("ZDSJ"));
					
					busr.setColumn8((String) object.get("RKRQ"));

					busr.setColumn12((String) object.get("FXS"));

					busr.setStatusinfo("01");

					busr.setFormcode("668ca03cad4b11e1bbb551abdbadc425_");

					busr.setTimex((new Timestamp(System.currentTimeMillis())
							.toString()));

					busr.setBelongx("");

					busr.setParticipant("$(participant)");

					busr.setCreated("");

					busr.setLsh(java.util.UUID.randomUUID().toString().replace(
							"-", ""));

					lshxxxxf = st.dy_.addData(
							"668ca03cad4b11e1bbb551abdbadc425_", busr);

					flag = false;

					System.out.println("插入:" + lshxxxxf);

				}

				oe.cav.bean.logic.bus.TCsBus busz = new oe.cav.bean.logic.bus.TCsBus();

				busz.setFatherlsh(lshxxxxf);

				busz.setColumn3((String) object.get("column4"));

				busz.setColumn4((String) object.get("column7"));

				busz.setColumn5(object.get("column34") == null ? "0" : object
						.get("column34").toString());

				busz.setColumn8((String) object.get("column11"));

				busz.setColumn9((String) object.get("column12"));

				busz.setColumn11(object.get("column96") == null ? "0" : object
						.get("column96").toString());

				busz.setColumn12(object.get("column17") == null ? "0" : object
						.get("column17").toString());
				busz.setColumn13((String) object.get("column24"));
				busz.setColumn14((String) object.get("column37"));

				busz.setColumn15((String) object.get("column58"));

				busz.setColumn16((String) object.get("column55"));

				busz.setColumn17((String) object.get("column56"));

				busz.setColumn22((String) object.get("DL"));
				busz.setColumn24("总公司库存商品");
				busz.setColumn25((String) object.get("column36"));

				busz.setColumn26((String) object.get("column84"));

				busz.setColumn28(object.get("column72") == null ? "0" : object
						.get("column72").toString());

				busz.setColumn29(object.get("column69") == null ? "0" : object
						.get("column69").toString());

				busz.setColumn30(object.get("column71") == null ? "0" : object
						.get("column71").toString());

				busz.setColumn31(object.get("column78") == null ? "0" : object
						.get("column78").toString());

				busz.setColumn32(object.get("column73") == null ? "0" : object
						.get("column73").toString());

				busz.setColumn33(object.get("column75") == null ? "0" : object
						.get("column75").toString());

				busz.setColumn34(object.get("column79") == null ? "0" : object
						.get("column79").toString());

				busz.setColumn35(object.get("column74") == null ? "0" : object
						.get("column74").toString());

				busz.setColumn37(object.get("column30") == null ? "0" : object
						.get("column30").toString());

				busz.setColumn38(object.get("column20") == null ? "0" : object
						.get("column20").toString());

				busz.setColumn39(object.get("column59") == null ? "0" : object
						.get("column59").toString());

				busz.setColumn40(object.get("column89") == null ? "0" : object
						.get("column89").toString());

				busz.setColumn41(object.get("column21") == null ? "" : object
						.get("column21").toString());

				busz.setColumn43(object.get("column76") == null ? "" : object
						.get("column76").toString());

				busz.setColumn44(object.get("column97") == null ? "" : object
						.get("column97").toString());

				busz.setColumn45(object.get("column98") == null ? "" : object
						.get("column98").toString());

				busz.setColumn46(object.get("column52") == null ? "" : object
						.get("column52").toString());

				busz.setStatusinfo("01");

				busz.setFormcode("84bbd11aad4711e1bbb551abdbadc425_");

				busz.setTimex((new Timestamp(System.currentTimeMillis())
						.toString()));

				busz.setBelongx("");

				busz.setParticipant("$(participant)");

				busz.setCreated("");

				busz.setLsh(java.util.UUID.randomUUID().toString().replace("-",
						""));
				System.out.println(busz.getLsh());
				lshxxxxr = st.dy_.addData("84bbd11aad4711e1bbb551abdbadc425_",
						busz);

				System.out.println("插入:" + lshxxxxr);

			}

			if (list.size() > 0) {

				String sql = "update DY_271334208897441 set STATUSINFO='03',column82='已发货' where column4 in ("
						+ par.toString() + ")";
				db.execute(con, sql);

			}

			db.close(con);

			System.out.println("首饰入库成功!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
