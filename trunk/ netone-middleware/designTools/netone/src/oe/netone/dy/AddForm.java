package  oe.netone.dy;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class AddForm{

	public void AddColumn(String name,String subinfo,String  form,String tion) throws MalformedURLException, NotBoundException, RemoteException {
  
	
	
	DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

	DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
	// �����?ͷ

  TCsForm busForm = new TCsForm();
	 busForm.setFormname(name);
	 busForm.setStyleinfo(subinfo);
	 busForm.setSubform(form);
	 busForm.setDescription(tion);

     String[] info;
	try {
		info = dys.create(busForm, "BUSSFORM.BUSSFORM");
		 ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		 String rsname = "BUSSFORM.BUSSFORM." + info[0];
		 UmsProtectedobject upo = rsrmi.loadResourceByNatural(rsname);
		 upo.setDescription(busForm.getExtendattribute());
		 upo.setReference(busForm.getDescription());
		 rsrmi.updateResource(upo);
	     System.out.print(upo);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	

	}
	}