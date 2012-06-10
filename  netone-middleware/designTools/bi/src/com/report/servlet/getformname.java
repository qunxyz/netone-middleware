package com.report.servlet;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.view.Dyviewscript;
import com.jl.common.netone.view.Dyviewxml;
import com.jl.common.netone.view.Putcolumnsobj;

public class getformname {

	/**
	 * @param args
	 */
	public static String selectRsInDir(String formcode) throws Exception {

		List list = new ArrayList();
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		ResourceInfo rsInfo = new ResourceInfo();
		TCsForm busForm = dysc.loadForm(formcode);
		String tablename=busForm.getTablename();
         return tablename;
	}
	//中文
	public List<formcode> Columname(String formcode) throws Exception {
		
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(formcode);
		formcode=upo.getExtendattribute();
		List<formcode>  dates=new ArrayList<formcode>();
		if (upo != null) {
			String extinfo = upo.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					Dyviewxml dyxml=new Dyviewxml();
					Dyviewscript dyscript=dyxml.readXML(extinfo);
					List list=dyscript.getOutput();
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						Putcolumnsobj put = (Putcolumnsobj) iterator.next();
					    formcode form=new formcode();
					    form.setColumname(put.getDisplayname());
					    form.setColumnid(put.getColumns());
					    form.setViewtype("");
					    dates.add(form);
					}
				}else{
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
					List listmame =null;
					try{
				   	 listmame =dys.queryObjects(busForm);
					}catch(Exception e){
						e.printStackTrace();
					}
					for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
					    TCsColumn columnname=(TCsColumn)iterator1.next();
					    formcode form=new formcode();
					    form.setColumname(columnname.getColumname());
					    form.setColumnid(columnname.getColumnid());
					    form.setViewtype(columnname.getViewtype());
					    dates.add(form);
					}
				}
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
 
}
