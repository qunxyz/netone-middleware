package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oescript.parent.OeScript;

public class FXDB_Script extends OeScript {
	public void queRen(){
		try{
			System.out.println("_________________分销调拨确认_______________________");
					dy.set("$(lsh)"+":"+"$(formcode)","column7", "确认");
					dy.set("$(lsh)"+":"+"$(formcode)","column13" , "$(participant)");
					SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String a1=dateformat.format(new java.util.Date());


							dy.set("$(lsh)"+":"+"$(formcode)","column14" , a1);


					Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

							oe.midware.workflow.engine.rule2.func.STools st=new oe.midware.workflow.engine.rule2.func.STools();

							List list = db

									.queryData(

											con,

											"select t.*,t2.column8 CFXS,t2.column9 CGZ,t2.column10 RFXS,t2.column11 RGZ,t2.column3 DH,t2.column4 RQ from DY_701342253696596 t,DY_671340788796691 t2 where t.fatherlsh=t2.lsh and t2.lsh='$(lsh)'");

							con = db.con("DATASOURCE.DATASOURCE.DYFORM");	

							StringBuffer par = new StringBuffer("");

							String split = "";

							boolean flag = true;

							String lshxxxxf = "";

							String lshxxxxr = "";

							String partcipantz="$(participant)";

							for (Iterator iterator = list.iterator(); iterator.hasNext();) {

								Map object = (Map) iterator.next();

								par.append(split +"'"+ (String)object.get("column3")+"'");

								split = ",";

								if(flag){

									oe.cav.bean.logic.bus.TCsBus busr = new oe.cav.bean.logic.bus.TCsBus();

									busr.setFatherlsh("1");

									busr.setColumn3("DB_"+(String)object.get("RFXS")+"_"+(String)object.get("CFXS")+"_"+(String)object.get("DH")+"_"+(String)(new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date())));

									busr.setColumn4("$(participant)");

									busr.setColumn5((String)(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));

									busr.setColumn6("");

									busr.setColumn8((String)object.get("RQ"));

									busr.setColumn9((String)object.get("RGZ"));

									busr.setColumn10("调拨入库单_调入分销商编码:"+(String)object.get("RFXS")+"_调出分销商编码:"+(String)object.get("CFXS"));

									busr.setColumn12((String)object.get("RFXS"));

									busr.setColumn13("$(participant)");

									busr.setColumn14((String)(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));

					                                busr.setColumn15("确认");

									busr.setStatusinfo("01");

									busr.setFormcode("668ca03cad4b11e1bbb551abdbadc425_");
									busr.setTimex((new Timestamp(System.currentTimeMillis()).toString()));

									busr.setBelongx("");

									String clientDeptCode=(String)object.get("RFXS");
									String sqlx="select usercode from iss.t_user where departmentid in (select departmentId from iss.t_department where departmentCode='"+clientDeptCode+"') limit 0,1";
									Connection conx = db.con("DATASOURCE.DATASOURCE.DYFORM");	
									List listx=db.queryData(conx, sqlx);
									if(listx.size()==1){
										String usercode=(String)((Map)listx.get(0)).get("usercode");
										partcipantz=usercode;
									}

									busr.setParticipant(partcipantz);

									busr.setCreated("");
									busr.setLsh((String)java.util.UUID.randomUUID().toString().replace("-",""));

									lshxxxxf =st.dy_.addData("668ca03cad4b11e1bbb551abdbadc425_", busr);

									flag=false;

									System.out.println("插入:"+lshxxxxf);

								}

								oe.cav.bean.logic.bus.TCsBus busz = new oe.cav.bean.logic.bus.TCsBus();

								busz.setFatherlsh(lshxxxxf);

								busz.setColumn3((String)object.get("column3"));

								busz.setColumn4((String)object.get("column4"));
			 				busz.setColumn5(object.get("column5")==null?"0":object.get("column5").toString());
								busz.setColumn6((String)object.get("column6"));

								busz.setColumn8((String)object.get("column9"));

								busz.setColumn9((String)object.get("column10"));

								busz.setColumn10((String)object.get("column11"));
								busz.setColumn11(object.get("column12")==null?"0":object.get("column12").toString());
								busz.setColumn12(object.get("column13")==null?"0":object.get("column13").toString());
								busz.setColumn13((String)object.get("column14"));

								busz.setColumn14((String)object.get("column15"));

								busz.setColumn15((String)object.get("column16"));

								busz.setColumn16((String)object.get("column17"));

								busz.setColumn17((String)object.get("column18"));

								busz.setColumn19((String)object.get("column20"));

								busz.setColumn20(object.get("column21")==null?"调拨备注:总公司调拨商品":(String)object.get("column20")+"|调拨备注:总公司调拨商品");

								busz.setColumn22((String)object.get("column8"));

								busz.setColumn23((String)object.get("column19"));

					                        busz.setColumn24("总公司调拨入库");
			busz.setColumn26((String)object.get("column22"));

			busz.setColumn25((String)object.get("column23"));

								busz.setStatusinfo("01");

								busz.setFormcode("84bbd11aad4711e1bbb551abdbadc425_");
								busz.setTimex((new Timestamp(System.currentTimeMillis()).toString()));

								busz.setBelongx("");

								busz.setParticipant(partcipantz);

								busz.setCreated("");
								busz.setLsh(java.util.UUID.randomUUID().toString().replace("-",""));

								lshxxxxr =st.dy_.addData("84bbd11aad4711e1bbb551abdbadc425_", busz);

								System.out.println("插入:"+lshxxxxr);

							}

							if (list.size()>0){					String sql = "update DY_661338441749388 set STATUSINFO='04',column24='已调拨出库' where column4 in ("+par.toString()+") and STATUSINFO='01' and column24!='总公司调拨入库'";

								db.execute(con, sql);

							}

							db.close(con);

			System.out.println("_________________分销调拨确认_______________________");
					}catch(Exception e){
						e.printStackTrace();
					
	}
	}
}
	
