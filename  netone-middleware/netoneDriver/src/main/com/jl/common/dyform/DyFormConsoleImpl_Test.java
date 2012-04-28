package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import oe.cav.bean.logic.column.TCsColumn;

import com.jl.common.dyform.DyFormConsoleIfc;
import com.jl.common.workflow.TWfActive;

public class DyFormConsoleImpl_Test implements DyFormConsoleIfc {

	static String formcode = "0f61be745cd811e0b954370b5cde244c_";

	static List list = new ArrayList();
	static {

		DyFormData d1 = new DyFormData();
		d1.setParticipant("管理员[adminx]");
		d1.setBelongx("默认[AREA.AREA.DEFAULT]");
		d1.setColumn1("李明");
		d1.setColumn2("1");
		d1.setColumn3("台湾人");
		d1.setColumn4("departname[departid]");

		d1.setLsh("1");
		d1.setFormcode(formcode);
		d1.setFatherlsh("1");

		DyFormData d2 = new DyFormData();
		d2.setParticipant("管理员[adminx]");
		d2.setBelongx("默认[AREA.AREA.DEFAULT]");
		d2.setColumn1("李白");
		d2.setColumn2("1");
		d2.setColumn3("美国人");
		d2.setColumn4("departname[departid]");

		d2.setLsh("2");
		d2.setFormcode(formcode);
		d2.setFatherlsh("1");

		DyFormData d3 = new DyFormData();
		d3.setBelongx("默认[AREA.AREA.DEFAULT]");
		d3.setParticipant("管理员[adminx]");
		d3.setColumn1("林玲");
		d3.setColumn2("0");
		d3.setColumn3("中国人");
		d3.setColumn4("departname[departid]");

		d3.setLsh("3");
		d3.setFormcode(formcode);
		d3.setFatherlsh("1");

		list.add(d1);
		list.add(d2);
		list.add(d3);
		
		
		DyFormData d4 = new DyFormData();
		d4.setBelongx("默认[AREA.AREA.DEFAULT]");
		d4.setParticipant("管理员[adminx]");
		d4.setColumn1("林玲");
		d4.setColumn2("0");
		d4.setColumn3("中国人");
		d4.setColumn4("departname[departid]");
		d4.setLsh("100");
		d4.setFormcode(formcode);
		d4.setFatherlsh("3");
		
		
		DyFormData d5 = new DyFormData();
		d5.setBelongx("默认[AREA.AREA.DEFAULT]");
		d5.setParticipant("管理员[adminx]");
		d5.setColumn1("林玲");
		d5.setColumn2("0");
		d5.setColumn3("中国人");
		d5.setColumn4("departname[departid]");
		d5.setLsh("101");
		d5.setFormcode(formcode);
		d5.setFatherlsh("3");
		
		list.add(d4);
		list.add(d5);

	}

	public String addData(String formid, DyFormData bus) throws RemoteException {
		if (bus.getParticipant() == null) {
			throw new RemoteException("表单缺少参与者");
		}
		bus.setFormcode(formid);
		bus.setCreated(new Timestamp(System.currentTimeMillis()).toString());
		bus.setTimex(new Timestamp(System.currentTimeMillis()).toString());
		bus.setLsh(UUID.randomUUID().toString().replaceAll("-", ""));

		list.add(bus);
		return bus.getLsh();
	}

	public boolean deleteData(String formid, String id) throws RemoteException {
		DyFormData data = this.loadData(formid, id);
		return list.remove(data);
	}

	public List<DyFormColumn> fetchColumnList(String formid) throws Exception {

		List dycolumn = new ArrayList();

		DyFormColumn c1 = new DyFormColumn();
		c1.setColumnid("column1");
		c1.setColumname("姓名");
		c1.setFormcode(formcode);
		c1.setValuelist("");
		c1.setFocusScript("");
		c1.setInitScript("");
		c1.setLoseFocusScript("");
		c1.setHidden(false);
		c1.setHtmltype(this._HTML_LIST[0][0]);
		//c1.setMusk(true);
		c1.setReadonly(false);
		c1.setXoffset(1);
		c1.setYoffset(1);

		DyFormColumn c2 = new DyFormColumn();
		c2.setColumnid("column2");
		c2.setColumname("性别");
		c2.setFormcode(formcode);
		c2.setValuelist("1-男,2-女"); // 有kv、等类型

		c2.setFocusScript("");
		c2.setInitScript("");
		c2.setLoseFocusScript("");
		c2.setHidden(false);
		c2.setHtmltype(this._HTML_LIST[8][0]);
		//c2.setMusk(true);
		c2.setReadonly(false);
		c2.setXoffset(1);
		c2.setYoffset(2);

		DyFormColumn c3 = new DyFormColumn();
		c3.setColumnid("column3");
		c3.setColumname("描述");
		c3.setFormcode(formcode);
		c3.setValuelist(""); // 有kv、等类型

		c3
				.setFocusScript("<script>document.getElementById('column3').value='';</script>");
		c3
				.setInitScript("<script>document.getElementById('column3').value='请输入内容';</script>");
		c3
				.setLoseFocusScript("<script>var data=document.getElementById('column3').value;var reg = new RegExp('^[\u0391-\uFFE5]$’);if(!reg.test(data)){alert('请输入汉字');};</script>");
		c3.setHidden(false);
		c3.setHtmltype(this._HTML_LIST[10][0]);
		//c3.setMusk(true);
		c3.setReadonly(false);
		c3.setXoffset(2);
		c3.setYoffset(1);

		DyFormColumn c4 = new DyFormColumn();
		c4.setColumnid("column4");
		c4.setColumname("部门");
		c4.setFormcode(formcode);
		c4.setValuelist("departname[departid]"); // 部门id
		c4.setFocusScript("");
		c4.setInitScript("");
		c4.setLoseFocusScript("");
		c4.setHidden(false);
		c4.setHtmltype(this._HTML_LIST[15][0]);
		//c4.setMusk(true);
		c4.setReadonly(false);
		c4.setXoffset(3);
		c4.setYoffset(1);

		DyFormColumn c5 = new DyFormColumn();
		c5.setColumnid("column5");
		c5.setColumname("出生");
		c5.setFormcode(formcode);
		c5.setValuelist("1984-12-12"); // 部门id
		c5.setFocusScript("");
		c5.setInitScript("");
		c5.setLoseFocusScript("");
		c5.setHidden(false);
		c5.setHtmltype(this._HTML_LIST[3][0]);
		//c5.setMusk(true);
		c5.setReadonly(false);
		c5.setXoffset(3);
		c5.setYoffset(2);

		dycolumn.add(c1);
		dycolumn.add(c2);
		dycolumn.add(c3);
		dycolumn.add(c4);
		dycolumn.add(c5);
		return dycolumn;
	}

	public DyFormColumn loadColumn(String formid, String columnid)
			throws Exception {
		List list = this.fetchColumnList(formid);
		if ("column1".equals(columnid)) {
			return (DyFormColumn) list.get(0);
		}
		if ("column2".equals(columnid)) {
			return (DyFormColumn) list.get(1);
		}
		if ("column3".equals(columnid)) {
			return (DyFormColumn) list.get(2);
		}
		if ("column4".equals(columnid)) {
			return (DyFormColumn) list.get(3);
		}
		if ("column5".equals(columnid)) {
			return (DyFormColumn) list.get(4);
		}
		return null;
	}

	public DyFormData loadData(String formcode, String bussid)
			throws RemoteException {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormData data = (DyFormData) iterator.next();
			if (data.getFormcode().equals(formcode)
					&& data.getLsh().equals(bussid)) {
				return data;
			}
		}
		return null;
	}

	public DyForm loadForm(String formid) throws Exception {
		DyForm dy = new DyForm();
		dy.setFormcode(formid);
		dy.setFormname("测试表单");
		dy.setHtmltitleinfo_("<font color='red' size=10>bbbbbbbb</font>");
		DyFormColumn[] dyx = (DyFormColumn[]) this.fetchColumnList(formid)
				.toArray(new DyFormColumn[0]);
		dy.setListColumn_(dyx);
		dy.setQueryColumn_(dyx);
		dy.setOrderinfo("order by column1 desc");
		dy.setEachPageSize_(5);
		// 表单支持嵌套的，自己作为自己的子表单
		dy.setSubform_(new DyForm[] { dy, dy });
		return dy;
	}

	public boolean modifyData(String formid, DyFormData bus)
			throws RemoteException {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormData data = (DyFormData) iterator.next();
			if (data.getFormcode().equals(formid)
					&& data.getLsh().equals(bus.getLsh())) {
				list.remove(data);
				break;
			}
		}
		list.add(bus);
		return true;
	}

	public List queryData(DyFormData bus, int form, int to, String condition)
			throws RemoteException {
		if(bus.getFatherlsh()==null){
			bus.setFatherlsh("1");
		}

		if (bus == null) {
			return list;
		}
		List newdata = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormData data = (DyFormData) iterator.next();
			if (data.getFormcode().equals(bus.getFormcode())&&data.getFatherlsh().equals(bus.getFatherlsh())) {
				newdata.add(data);
			}
		}
		if (to > newdata.size()) {
			to = newdata.size();
		}
		return newdata.subList(form, to);
	}

	public int queryDataNum(DyFormData bus, String condition)
			throws RemoteException {
		return queryData(bus, 0, 10000, condition).size();
	}

	public String[] addAll(String formcode, String fatherlsh,
			List<DyFormData> data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteAll(String formcode, String fatherlsh) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public String[] addAll(List<DyFormData> data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteDataByLogic(String formid, String id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public List<DyFormColumn> fetchColumnList(String formid, String usercode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DyFormColumn> columnList(List<DyFormColumn> list,
			String usercode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void permission(DyForm form, String usercode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void permission(DyForm form, String usercode, TWfActive act)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


	public List<TCsColumn> QueryColumn(String formcode, String model)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
 
	public DyFormData loadDataS(String formcode, String bussid)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
