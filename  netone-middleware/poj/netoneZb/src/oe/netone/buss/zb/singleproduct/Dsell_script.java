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
	 * 通用条码获取脚本
	 * @return
	 */
	public String todo1(){
		String sql="$(sql) '%$(q)%'";

		Connection con =db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list=db.queryData(con, sql);

		java.lang.StringBuffer jsonBuffer = new java.lang.StringBuffer();
		String split = "";
		for (int i = 0; i < list.size(); i++) {

			try{
			String jsonStr = net.sf.json.JSONObject.fromObject(list.get(i))
					.toString();
			System.out.println("testSize2:"+jsonStr);
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
