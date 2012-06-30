/**
 * 
 */
package oe.netone.buss.zb.singleproduct;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONObject;

import oescript.parent.OeScript;

/**
 * ��������
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-6-26 ����11:23:14
 * @history
 */
public class SellOut_script extends OeScript {

	// ȷ��
	public void confirm() throws RemoteException {
		oe.midware.workflow.engine.rule2.func.STools st = new oe.midware.workflow.engine.rule2.func.STools();

		/** ����* */
		String lsh = "$(lsh)";

		/** ��Ʒ��������* */
		oe.cav.bean.logic.bus.TCsBus busx0 = new oe.cav.bean.logic.bus.TCsBus();
		busx0.setFatherlsh("1");
		busx0.setFormcode("4313df5dc03911e19e849de029b8abb5_");
		List listx0 = st.dy_.queryData(busx0, 0, 99999999, "");
		System.out.println("����:" + listx0.size());
		for (int i = 0; i < listx0.size(); i++) {
			oe.cav.bean.logic.bus.TCsBus x0 = (oe.cav.bean.logic.bus.TCsBus) listx0
					.get(i);
			/** * */
			String p1 = x0.getColumn3();
			/** *��ɫ * */
			String p2 = x0.getColumn4();
			System.out.println(p1);

			/** ������ϸ��* */
			oe.cav.bean.logic.bus.TCsBus busx = new oe.cav.bean.logic.bus.TCsBus();
			busx.setFatherlsh(lsh);
			busx.setFormcode("1dde2f9fa81711e19b54fb13b166e993_");
			List listx = st.dy_.queryData(busx, 0, 99999999, "");
			for (int j = 0; j < listx.size(); j++) {
				oe.cav.bean.logic.bus.TCsBus x = (oe.cav.bean.logic.bus.TCsBus) listx
						.get(j);
				String pcode = x.getColumn3();
				System.out.println("pcode:" + pcode);

				/** ������Ʒ������Ϣ */
				if (org.apache.commons.lang.StringUtils.contains(pcode, p1)) {
					oe.cav.bean.logic.bus.TCsBus bus2 = new oe.cav.bean.logic.bus.TCsBus();
					bus2.setFatherlsh("1");
					bus2.setColumn3("");
					bus2.setColumn4(lsh);
					bus2.setColumn5(pcode);
					bus2.setColumn6("," + p2 + ",");
					bus2.setFormcode("d000dcffc1ee11e19e849de029b8abb5_");
					bus2.setTimex("");
					bus2.setBelongx("");
					bus2.setParticipant("$(participant)");
					bus2.setCreated("");
					bus2.setLsh(java.util.UUID.randomUUID().toString().replace(
							"-", ""));
					String lshxxxx = st.dy_.addData(
							"d000dcffc1ee11e19e849de029b8abb5_", bus2);
					System.out.println("����:" + lshxxxx);
				}

			}
		}
	}

	/**
	 * �����Ʒ���� SOASCRIPT.SOASCRIPT.ZB.GETPRODUCTTIPS
	 * 
	 * @return
	 * @throws Exception
	 */
	public String GETPRODUCTTIPS() throws Exception {

		oe.midware.workflow.engine.rule2.func.STools st = new oe.midware.workflow.engine.rule2.func.STools();
		List roles = st.rs_.getUserRoles("0000", "$(sr_participant)");

		StringBuffer conditions = new StringBuffer();
		for (int i = 0; i < roles.size(); i++) {
			oe.security3a.seucore.obj.db.UmsRole r = (oe.security3a.seucore.obj.db.UmsRole) roles
					.get(i);
			conditions.append(" or  column6 like '%[" + r.getId() + "]%'");
		}

		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						" select * from DY_671340788796713 where column3 not like '%[$(sr_participant)]%' and 1=1  ");
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

	/**
	 * ȡ����Ʒ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String CANCELPRODUCTTIPS() throws Exception {
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		String sql = " update DY_671340788796713 set column3=CONCAT(ifnull(column3,''),'[$(sr_participant)]') where column5='$(q)' ";
		int result = db.execute(con, sql);

		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		if (result > 0) {
			json.put("tips", "�����ɹ�!");
		} else {
			json.put("tips", "����ʧ��!");
			json.put("error", "yes");
		}
		db.close(con);
		return "" + json.toString() + "";
	}
}
