package oe.mid.soa.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class Test {

	public static void main(String[] args) throws Exception {
		
		TCsBus bus=new TCsBus();
		bus.setColumn10("1213");
		BeanUtils.setProperty(bus, "column10", "");
		System.out.println(bus.getColumn10());

		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
//		
//		
//		
//		String formcode="";
//		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
//		UmsProtectedobject upo = new UmsProtectedobject();
//		upo.setExtendattribute(formcode);
//		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
//		Map map = new HashMap();
//		map.put("naturalname", "like");
//		List formlist = rs.fetchResource(upo, map);
//		if(formlist.size()!=1){
//			throw new RuntimeException("���ڱ����쳣����");
//		}
//		String naturalname=((UmsProtectedobject)formlist.get(0)).getNaturalname();
//	
//		
//		TCsBus bus=new TCsBus();
//		bus.setFormcode("f047597a250711debe4aa1770d20661e_");
//		bus.setFatherlsh("1");
//		int list=dy.queryData(bus, " and participant='' order by timex desc");
		
//		System.out.println(list);
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
		
		List list = dy.fetchColumnList("1dff9941bda111e1b7984be393252048_");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			
		}
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
		// column.setColumname("���");
		// column.setFormcode(form.getFormcode());
		// column.setHtmltype("number");
		// dfd.addColumn(column);
		//
		// column.setColumname("�㲻��");
		// dfd.updateColumnView(column);
		//
		// TCsColumn column2 = new TCsColumn();
		// column2.setColumncode("abc1");
		// column2.setColumnid("abc1");
		// column2.setColumname("���1");
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
	
	public void todo() throws MalformedURLException, RemoteException, NotBoundException{
//		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
//		dys.loadColumn("915390d5bda211e1b7984be393252048_", key)
	}
}