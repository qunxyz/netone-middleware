package oe.mid.soa.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) throws Exception {

		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		

		TCsBus bus=new TCsBus();
		bus.setFormcode("f047597a250711debe4aa1770d20661e_");
		bus.setFatherlsh("1");
		int list=dy.queryData(bus, " and participant='' order by timex desc");
		
		System.out.println(list);
//		TCsBus bus=new TCsBus();
//		bus.setBelongx("DEPT.DEPT");
//		bus.setCreated((new Timestamp(System.currentTimeMillis()).toString()));
//		bus.setStatusinfo("00");
//		bus.setParticipant("aaaa");
//		bus.setLsh(IdServer.uuid());
//		bus.setFatherlsh("1");
//		bus.setTimex((new Timestamp(System.currentTimeMillis()).toString()));
//		bus.setFormcode("c3b59eac130411debe158fbae95a9e2c_");
//		dy.addData("c3b59eac130411debe158fbae95a9e2c_", bus);
//		bus.setCreated(null);
//		bus.setTimex(null);
//		bus.setLsh(null);
//		int num=dy.queryDataNum(bus, "");
//		
//		System.out.println(num);
		
//		List list = dy.fetchColumnList("23f307f0bd6111dd9c573f3e864d2bf2_");
//		
//
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			TCsColumn object = (TCsColumn) iterator.next();
//			System.out.println(object.getColumname());
//		}
		// DyFormDesignService dfd = (DyFormDesignService)
		// RmiEntry.iv("dydesign");
		//
		// // // List list =
		// // // dy.fetchColumnList("221dc6c6-5447-11dc-9ac4-7beebbae7269_");
		// // // TCsBus bus = dy.load("1197882767044",
		// // // "221dc6c6-5447-11dc-9ac4-7beebbae7269_");
		// // // System.out.println(bus);
		// //
		// // TCsBus bus = new TCsBus();
		// // bus.setFormcode("66a62b84-86b9-11dc-bf3a-cb28f09b292c_");
		// // List busxx = dy.queryData(bus, 0, 10, "");
		// // System.out.println(busxx);
		// // dy.queryData(bus, 0, 10, null);
		// // System.out.println(busxx);
		// //
		// // dy.queryDataNum(bus, null);
		//
		// TCsForm form = new TCsForm();
		// form.setFormname("MyTable");
		// form.setCreated((new Date(System.currentTimeMillis())).toString());
		// form.setSystemid("");
		// form.setBelongx("1");
		// form.setTimelevel("2h");
		// form.setSqlinfo("selectfsd dsfsdfdsfdsfdsfsdfsdfsfdsfdsfdsfdsfs");
		//
		// String[] tablename = dfd.create(form);
		// form.setDescription(tablename[0]);
		//
		// TCsColumn column = new TCsColumn();
		// column.setColumncode("abc");
		// column.setColumnid("abc");
		// column.setColumname("你好");
		// column.setFormcode(form.getFormcode());
		// column.setHtmltype("number");
		// dfd.addColumn(column);
		//
		// column.setColumname("你不好");
		// dfd.updateColumnView(column);
		//
		// TCsColumn column2 = new TCsColumn();
		// column2.setColumncode("abc1");
		// column2.setColumnid("abc1");
		// column2.setColumname("你好1");
		// column2.setFormcode(form.getFormcode());
		// column2.setHtmltype("string");
		// dfd.addColumn(column2);

		// DyObj obj = new DyObj();
		//
		// String xml = dfd.fromDyObj(obj);

		// DyObj obj = dfd.fromSQL("", form.getDescription());

		// System.out.println(dfd.fromDyObj(obj));
		//
		// DyObj objx = dfd.fromDy(XmlPools.fetchXML(form.getFormcode())
		// .toString());
		// System.out.println(objx.getFrom().getSqlinfo());
		// System.out.println();
		// System.out.println();
		// // System.out.println(objx);
		
		
//		TCsBus bus=new TCsBus();
//		bus.setFormcode("d891ee6bb85b11dd9813957f0248184c_");
//		bus.setFatherlsh("1");
//		
//		List listData=dy.queryData(bus, 0, 10, "");
//		for (Iterator iterator = listData.iterator(); iterator.hasNext();) {
//			TCsBus object = (TCsBus) iterator.next();
//			System.out.println(object.getColumn3());
//		}

	}
}
