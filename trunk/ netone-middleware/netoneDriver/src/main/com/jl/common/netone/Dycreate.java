package com.jl.common.netone;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.workflow.DbTools;

public class Dycreate {
	
	//创建表单的方法
	public String createForms(TCsForm busForm,String parentdir) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
	
		String[] info=null;
        try {
        	info = dys.create(busForm, parentdir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(info[0]);
		System.out.println(info[1]);
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		String rsname =parentdir+ "." + info[0];
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(rsname);
		upo.setDescription(busForm.getExtendattribute());
		upo.setReference(busForm.getDescription());
		rsrmi.updateResource(upo);
		return  info[1];
	}
 
	//删除表单的方法
	public Boolean DeleteForm(TCsForm busForm){
		DyFormDesignService dys;
		Boolean fal=null;
		try {
			 dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			 fal=dys.dropForm(busForm);  
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return fal;
	}
}
