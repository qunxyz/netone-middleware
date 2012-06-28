/**
 * 
 */
package oe.netone.buss.zb.member;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

/**
 * 赠品资料后台脚本
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-28 下午03:46:32
 * @history
 */
public class PresentInfo_script extends OeScript {

	/**
	 * 获取赠品信息 SOASCRIPT.SOASCRIPT.ZB.GETPRESENTINFO
	 * 
	 * @return
	 */
	public String GETPRESENTINFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(con,
						"select * from DY_381336140843598 where column4 like '%$(q)%' and column12='1'");
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

}
