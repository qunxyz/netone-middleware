package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class SSRK_Script extends OeScript {
	public void queRen(){
		try {
			dy.set("$(lsh)"+":"+"$(formcode)","column19" , "确认");
			dy.set("$(lsh)"+":"+"$(formcode)","column16" , "$(participant)");
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String a1=dateformat.format(new java.util.Date());
			dy.set("$(lsh)"+":"+"$(formcode)","column17" , a1);


						Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

						oe.midware.workflow.engine.rule2.func.STools st=new oe.midware.workflow.engine.rule2.func.STools();

						List list = db

								.queryData(

										con,

										"select t.*,t2.column15 FXS,t2.column14 DL from DY_271334208897441 t,DY_271334208897439 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");

						con = db.con("DATASOURCE.DATASOURCE.DYFORM");	

						StringBuffer par = new StringBuffer("");

						String split = "";

						boolean flag = true;

						String lshxxxxf = "";

						String lshxxxxr = "";


						for (Iterator iterator = list.iterator(); iterator.hasNext();) {

							Map object = (Map) iterator.next();

							par.append(split +"'"+ (String)object.get("column4")+"'");

							split = ",";

							if(flag){

								oe.cav.bean.logic.bus.TCsBus busr = new oe.cav.bean.logic.bus.TCsBus();

								busr.setFatherlsh("1");

								busr.setColumn12((String)object.get("FXS"));

								busr.setStatusinfo("01");

								busr.setFormcode("668ca03cad4b11e1bbb551abdbadc425_");

								busr.setTimex((new Timestamp(System.currentTimeMillis()).toString()));

								busr.setBelongx("");

								busr.setParticipant("$(participant)");

								busr.setCreated("");

								busr.setLsh(java.util.UUID.randomUUID().toString().replace("-",""));

								lshxxxxf =st.dy_.addData("668ca03cad4b11e1bbb551abdbadc425_", busr);

								flag=false;

								System.out.println("插入:"+lshxxxxf);

							}

							oe.cav.bean.logic.bus.TCsBus busz = new oe.cav.bean.logic.bus.TCsBus();

							busz.setFatherlsh(lshxxxxf);

							busz.setColumn3((String)object.get("column4"));

							busz.setColumn4((String)object.get("column7"));

							busz.setColumn5(object.get("column34")==null?"0":object.get("column34").toString());

							busz.setColumn8((String)object.get("column11"));

							busz.setColumn9((String)object.get("column12"));

							busz.setColumn10((String)object.get("column13"));

							busz.setColumn11(object.get("column16")==null?"0":object.get("column16").toString());

							busz.setColumn12(object.get("column17")==null?"0":object.get("column17").toString());

							busz.setColumn15((String)object.get("column58"));

							busz.setColumn16((String)object.get("column55"));

							busz.setColumn17((String)object.get("column56"));

							busz.setColumn22((String)object.get("DL"));

							busz.setColumn23((String)object.get("column57"));
			busz.setColumn26((String)object.get("column84"));
			busz.setColumn25((String)object.get("column36"));

						                busz.setColumn24("总公司库存商品");

							busz.setStatusinfo("01");

							busz.setFormcode("84bbd11aad4711e1bbb551abdbadc425_");

							busz.setTimex((new Timestamp(System.currentTimeMillis()).toString()));

							busz.setBelongx("");

							busz.setParticipant("$(participant)");

							busz.setCreated("");

							busz.setLsh(java.util.UUID.randomUUID().toString().replace("-",""));

							lshxxxxr =st.dy_.addData("84bbd11aad4711e1bbb551abdbadc425_", busz);

							System.out.println("插入:"+lshxxxxr);

						}

						if (list.size()>0){

							String sql = "update DY_271334208897441 set STATUSINFO='03',column82='已发货' where column4 in ("+par.toString()+")";
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
