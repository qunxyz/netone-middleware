/**
 * 
 */
package oe.netone.buss.zb.member;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-28 下午04:12:22
 * @history
 */
public class PresentDrop_script extends OeScript {

	/**
	 * 获取赠品入库赠品信息 SOASCRIPT.SOASCRIPT.ZB.GETPRESENTINFO
	 * 
	 * @return
	 */
	public String GETPRESENTININFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"select t.* from DY_381336140843594 t left join DY_381336140843595  t2 on t.fatherlsh=t2.lsh where t.column3 like '%$(q)%' and t2.statusinfo='01'");
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
