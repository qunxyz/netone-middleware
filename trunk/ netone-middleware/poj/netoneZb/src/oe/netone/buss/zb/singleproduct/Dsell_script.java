package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

/**
 * 分销管理SOA脚本
 * @author robanco
 *
 */
public class Dsell_script extends OeScript{
	/**
	 * 分销入库获取单品条码脚本
	 * 对应的资源目录： SOASCRIPT.SOASCRIPT.ZB.FXGL.DPRK
	 * @return
	 */
	public String todo1(){
		String sql="select sub.*,father.column14 bigtype from DY_271334208897441 sub,DY_271334208897439 father   where father.STATUSINFO='01' and sub.STATUSINFO='01' and sub.fatherlsh=father.lsh and sub.column4 like '$(q)%'";

		Connection con =db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list=db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try{
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}
	
	/**
	 * 分销退货获取单品条码脚本
	 * 对应的资源目录： SOASCRIPT.SOASCRIPT.ZB.FXGL.FXTH
	 * @return
	 */
	public String todo2(){
		String sql="select sub.*,fa.column9 fzx from DY_661338441749388 sub,DY_661338441749389 fa where fa.STATUSINFO='01' and sub.STATUSINFO='01' and sub.fatherlsh=fa.lsh and sub.column3 like '$(q)%'";

		Connection con =db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list=db.queryData(con, sql);
		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {
			try{
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		db.close(con);
		return "[" + jsonBuffer.toString() + "]";
	}

}
