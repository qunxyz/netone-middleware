package oe.bi.etl.wizard.summarydata;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.XmlPools;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		// // DyFormService dy = new DyFormServiceImpl();
		// DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		// // List list =
		// // dy.fetchColumnList("221dc6c6-5447-11dc-9ac4-7beebbae7269_");
		// // TCsBus bus = dy.load("1197882767044",
		// // "221dc6c6-5447-11dc-9ac4-7beebbae7269_");
		// // System.out.println(bus);
		//
		// TCsBus bus = new TCsBus();
		// bus.setFormcode("66a62b84-86b9-11dc-bf3a-cb28f09b292c_");
		// List busxx = dy.queryData(bus, 0, 10, "");
		// System.out.println(busxx);
		// dy.queryData(bus, 0, 10, null);
		// System.out.println(busxx);
		//
		// dy.queryDataNum(bus, null);

		DyFormDesignService dfd = (DyFormDesignService) RmiEntry.iv("dydesign");
		// DyFormService dfxml =(DyFormService) RmiEntry.iv("dyhandle");

		// TCsForm form = new TCsForm();
		// // form.setFormcode(IdServer.uuid());
		// form.setFormname("MyTable");
		// form.setCreated((new Date(System.currentTimeMillis())).toString());
		// form.setSystemid("");
		// String[] tablename = dfd.create(form);
		// form.setDescription(tablename[0]);
		// form.setFormcode(tablename[1]);
		//
		// TCsColumn column = new TCsColumn();
		// column.setColumncode("abc");
		// column.setColumnid("abc");
		// column.setColumname("ÄãºÃ");
		// column.setFormcode(form.getFormcode());
		// column.setHtmltype("number");
		// dfd.addColumn(column);
		//
		// TCsColumn column2 = new TCsColumn();
		// column2.setColumncode("abc1");
		// column2.setColumnid("abc1");
		// column2.setColumname("ÄãºÃ1");
		// column2.setFormcode(form.getFormcode());
		// column2.setHtmltype("string");
		// dfd.addColumn(column2);
		//
		// DyObj obj = new DyObj();
		// obj.setFrom(form);
		// obj.setSystemid("");
		// List list = new ArrayList();
		// list.add(column);
		// list.add(column2);
		// obj.setColumn(list);
		//
		// String xml = dfd.fromDyObj(obj);
		//
		// // DyObj obj = dfd.fromSQL("", form.getDescription());
		//
		// // System.out.println(dfd.fromDyObj(obj));

		DyObj objx = dfd.fromDy("fe139374cef411dc8339555cb1441652_DATASOURCE.DATASOURCE.MYSQL");
		System.out.println(objx);
		System.out.println();
		System.out.println();
		// System.out.println(objx);

	}
}
