package oe.netone.bi;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.derby.impl.sql.compile.FromBaseTable;

import com.jl.common.workflow.DbTools;


import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;

public class getformname {

	/**
	 * @param args
	 */
	public static String selectRsInDir(String formcode) throws Exception {

		List list = new ArrayList();
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm busForm = dysc.loadForm(formcode);
		String tablename=busForm.getTablename();
		System.out.println(tablename);
         return tablename;
	}
	//中文
	public List<formcode> Columname(String formcode) throws Exception {

		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		//long indexname=dys.queryObjectsNumber(busForm);
	   	List listmame =dys.queryObjects(busForm);
	   	
	   	List<formcode>  dates=new ArrayList<formcode>();
		for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
		    TCsColumn columnname=(TCsColumn)iterator1.next();
		   if(columnname.getMusk().equals("0") || columnname.getMusk().equals("null")){
		    formcode form=new formcode();
		    form.setColumname(columnname.getColumname());
		    form.setColumnid(columnname.getColumnid());
		    dates.add(form);
		    }
		}
	   	return dates;
	}
	public List<formcode> mappingColumname(String formcode) throws Exception {

		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		//long indexname=dys.queryObjectsNumber(busForm);
	   	List listmame =dys.queryObjects(busForm);
	   	
	   	List<formcode>  dates=new ArrayList<formcode>();
		for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
		    TCsColumn columnname=(TCsColumn)iterator1.next();
		    formcode form=new formcode();
		    form.setColumname(columnname.getColumname());
		    form.setColumnid(columnname.getColumnid());
		    dates.add(form);
		}
	   	return dates;
	}
	//中文
	public List<formcode> RequiredColumname(String formcode) throws Exception {

		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		//long indexname=dys.queryObjectsNumber(busForm);
	   	List listmame =dys.queryObjects(busForm);
	   	
	   	List<formcode>  dates=new ArrayList<formcode>();
		for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
		    TCsColumn columnname=(TCsColumn)iterator1.next();
		    if(columnname.getMusk().equals("1")){
		    formcode form=new formcode();
		    form.setColumname(columnname.getColumname());
		    form.setColumnid(columnname.getColumnid());
		    dates.add(form);
		    }
		}
	   	return dates;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		getformname aGetformname=new getformname();
		System.out.println(aGetformname.Columname("71f4f0bff36c11e0b333d9557f6576ff_"));
		
	}
	
}
