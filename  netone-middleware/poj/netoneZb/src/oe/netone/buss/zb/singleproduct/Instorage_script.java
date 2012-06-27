/**
 * 
 */
package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

/**
 * 首饰入库后台脚本
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-26 上午09:52:23
 * @history
 */
public class Instorage_script extends OeScript {

	/**
	 * 入库 采购单数据脚本<BR>
	 * 根据采购单号取得采购信息
	 * 
	 * @return
	 */
	public String GETPAYMENTINFO() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		String condition = "";
		String q = "$(q)";
		q = q.trim();
		condition = " and column3 like '%" + q + "%'";
		List list = db.queryData(con,
				"select * from DY_661338441749384 where 1=1 " + condition);
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

	public void INFO() {
		// SCM工程
		// com.jl.web.controller.FrameActionExt.java
		// 根据ext值得到不同表单字段
		// 以下代码
		// private void load(ActionMapping mapping, ActionForm form,
		// HttpServletRequest request, HttpServletResponse response,
		// boolean isedit, boolean permission, boolean isprint)
		// throws Exception {

		// 找到// 单品
		// String ext = request.getParameter("ext");
		//
		/**
		 * <P>
		 * 代码里过滤入库表单字段<BR>
		 * 根据ext过滤 默认写死dl001到dl015<BR>
		 * <table border=0 >
		 * <tr>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl001" checked="checked" /><font style="font-size: 14px;">镶嵌类</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl002" /><font style="font-size: 14px;">素金(件)</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl003" /><font style="font-size: 14px;">玉器类</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl004" /><font style="font-size: 14px;">宝石类</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl005" /><font style="font-size: 14px;">银饰(件)</font></td>
		 * </tr>
		 * <tr style="line-height: 25px;">
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl006" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">黄金(克)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl007"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">铂金(克)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl008"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">钯金(克)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl009"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">K金(克)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl010"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">银饰(克)</font></td>
		 * </tr>
		 * <tr>
		 * <td><input type='radio' id="bigcategorie" value="dl011"
		 * name="bigcategorie" /><font style="font-size: 14px;">半成品</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl012"
		 * name="bigcategorie" /><font style="font-size: 14px;">定单类</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl013"
		 * name="bigcategorie" /><font style="font-size: 14px;">物价类</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl014"
		 * name="bigcategorie" /><font style="font-size: 14px;">赠品类</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl015"
		 * name="bigcategorie" /><font style="font-size: 14px;">其他类</font></td>
		 * </tr>
		 * </table>
		 */

	}

}
