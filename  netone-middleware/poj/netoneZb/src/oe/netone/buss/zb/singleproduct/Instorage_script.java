/**
 * 
 */
package oe.netone.buss.zb.singleproduct;

import java.sql.Connection;
import java.util.List;

import oescript.parent.OeScript;

/**
 * ��������̨�ű�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-26 ����09:52:23
 * @history
 */
public class Instorage_script extends OeScript {

	/**
	 * ��� �ɹ������ݽű�<BR>
	 * ���ݲɹ�����ȡ�òɹ���Ϣ
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
		// SCM����
		// com.jl.web.controller.FrameActionExt.java
		// ����extֵ�õ���ͬ���ֶ�
		// ���´���
		// private void load(ActionMapping mapping, ActionForm form,
		// HttpServletRequest request, HttpServletResponse response,
		// boolean isedit, boolean permission, boolean isprint)
		// throws Exception {

		// �ҵ�// ��Ʒ
		// String ext = request.getParameter("ext");
		//
		/**
		 * <P>
		 * ��������������ֶ�<BR>
		 * ����ext���� Ĭ��д��dl001��dl015<BR>
		 * <table border=0 >
		 * <tr>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl001" checked="checked" /><font style="font-size: 14px;">��Ƕ��</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl002" /><font style="font-size: 14px;">�ؽ�(��)</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl003" /><font style="font-size: 14px;">������</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl004" /><font style="font-size: 14px;">��ʯ��</font></td>
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl005" /><font style="font-size: 14px;">����(��)</font></td>
		 * </tr>
		 * <tr style="line-height: 25px;">
		 * <td><input type='radio' id="bigcategorie" name="bigcategorie"
		 * value="dl006" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">�ƽ�(��)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl007"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">����(��)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl008"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">�ٽ�(��)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl009"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">K��(��)</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl010"
		 * name="bigcategorie" /><font color="red" style="font-size:
		 * 14px;font-weight: bold;">����(��)</font></td>
		 * </tr>
		 * <tr>
		 * <td><input type='radio' id="bigcategorie" value="dl011"
		 * name="bigcategorie" /><font style="font-size: 14px;">���Ʒ</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl012"
		 * name="bigcategorie" /><font style="font-size: 14px;">������</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl013"
		 * name="bigcategorie" /><font style="font-size: 14px;">�����</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl014"
		 * name="bigcategorie" /><font style="font-size: 14px;">��Ʒ��</font></td>
		 * <td><input type='radio' id="bigcategorie" value="dl015"
		 * name="bigcategorie" /><font style="font-size: 14px;">������</font></td>
		 * </tr>
		 * </table>
		 */

	}

}
