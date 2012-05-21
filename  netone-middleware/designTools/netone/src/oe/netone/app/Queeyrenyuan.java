package oe.netone.app;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.impl2.AnalysisAppSecond;
import com.jl.common.app.impl2.AppSecond;
import com.jl.common.dyform.DyEntry;

public class Queeyrenyuan {

	public List<ResourData> Query(String idcreated){
			List lisxt=new ArrayList();
			String id = idcreated;

			String processid = "";
			ResourceRmi resourceRmi = null;
			try {
				
				resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(id);
				
				String extendattribute = upo.getExtendattribute();
				String name=upo.getNaturalname();
				DomXml domxml=new DomXml();
				List<Person> list1=domxml.readXML(extendattribute);
				
				
				
				if (extendattribute != null && !extendattribute.equals("")) {
						processid = StringUtils.substringBetween(list1.get(0).getProcessid(), "[", "]");
				}
			

				WorkflowView wfview = null;

				try {
					wfview = (WorkflowView) RmiEntry.iv("wfview");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				WorkflowProcess list = wfview.fetchWorkflowProcess(processid);
				String obl=null;
				Activity[] act = list.getActivity();
				for (int i = 0; i < act.length; i++) {
					if (act[i].getImplementation() != null) {// 空节点不需要进入
						UmsProtectedobject upobj = new UmsProtectedobject();
						upobj.setNaturalname(name+"."+act[i].getId().toUpperCase());
						
						UmsProtectedobject upof=resourceRmi.loadResourceByNatural(name+"."+act[i].getId().toUpperCase());
						 obl=upof.getExtendattribute();
						 
					    upobj.setName(act[i].getName());
						upobj.setParentdir(id);
						if(obl == null || obl.equals("")){
						upobj.setObjecttype("");
						}else{
							List<Renyuan> list2=Xmlrenyuan.readXML(obl);
						   String Objecttype=list2.get(0).getObjecttype();
						   if(Objecttype.equals("human")){
							   upobj.setObjecttype("人员");
						   }
						    if(Objecttype.equals("team")){
					                   upobj.setObjecttype("组");
						    }  if(Objecttype.equals("dept")){
					                   upobj.setObjecttype("组织");
						    }    if(Objecttype.equals("role")){
					                   upobj.setObjecttype("角色");
						    }      if(Objecttype.equals("flowrole")){
					                   upobj.setObjecttype("流程角色");
						    }      if(Objecttype.equals("creater")){
					                   upobj.setObjecttype("创建者");
						    }if(Objecttype.equals("flowrolecreater")){
					                   upobj.setObjecttype("创建流程者角色");
						    }        
						    if(list2.get(0).getIsmanual().equals("1")){
								upobj.setDescription("手动");
							}else{
								upobj.setDescription("自动");
							}
						    
						}
						upobj.setAppid(upo.getAppid());
						
						
						lisxt.add(upobj);
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return lisxt;
		}
	
	public  AppSecond puzirenyuan(String lujing){
		AppSecond appSecond=new AppSecond();
		ResourceRmi resourceRmi = null;
		try {
			resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsProtectedobject upof=null;
		try {
			upof = resourceRmi.loadResourceByNatural(lujing);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String obl=upof.getExtendattribute();
		if(obl==null || obl.equals("")){
		 
		}
		else{
		appSecond=AnalysisAppSecond.readXML(obl);
		}
		
		return appSecond;
	}
	public String zibiaodanshu(String idcreated){
		
		UmsProtectedobject upof1=null;
		try {
			ResourceRmi resourceRmi = null;
				try {
					resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			upof1 = resourceRmi.loadResourceById(idcreated);
		} catch (RemoteException e) {

		}
		String formcode="";
		String str="";
		try {
			formcode = AppEntry.iv().loadApp(upof1.getNaturalname()).getDyformCode_();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			str=DyEntry.iv().loadForm(formcode).getSubform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//[formcode]name,[formcode]name
		return str;
	}
	public String formcounid(String idcreated) {
        String str="";
		String id = idcreated;
		String processid = "";
		ResourceRmi resourceRmi = null;
		String formcode = "";
		try {
			try {
				resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		UmsProtectedobject upo=null;
		try {
			upo = resourceRmi.loadResourceById(id);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		formcode = upo.getExtendattribute();
		DomXml domxml=new DomXml();
		List<Person> list1=domxml.readXML(formcode);
		
		formcode=StringUtils.substringBetween(list1.get(0).getFormcode(), "[", "]");
		List list=new ArrayList();
		DyFormDesignService dys = null;
		try {
			try {
				dys = (DyFormDesignService)RmiEntry.iv("dydesign");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		//long indexname=dys.queryObjectsNumber(busForm);
			List listmame=null;
			try {
				listmame = dys.queryObjects(busForm);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(listmame!=null){
			for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
			    TCsColumn columnname=(TCsColumn)iterator1.next();
			    str+=columnname.getColumname()+"["+columnname.getColumnid()+"],";
			}
			}
		return str;
	}
	public List renyaunid(String idcreated) {
		String id = idcreated;
		String processid = "";
		ResourceRmi resourceRmi = null;
		String formcode = "";
		try {
			try {
				resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		UmsProtectedobject upo=null;
		try {
			upo = resourceRmi.loadResourceById(id);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		formcode = upo.getExtendattribute();
		DomXml domxml=new DomXml();
		List<Person> list1=domxml.readXML(formcode);
		
		formcode=StringUtils.substringBetween(list1.get(0).getFormcode(), "[", "]");
		List list=new ArrayList();
		DyFormDesignService dys = null;
		try {
			try {
				dys = (DyFormDesignService)RmiEntry.iv("dydesign");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		//long indexname=dys.queryObjectsNumber(busForm);
			List listmame=null;
			try {
				listmame = dys.queryObjects(busForm);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
		    TCsColumn columnname=(TCsColumn)iterator1.next();
		    ResourData resourData=new ResourData();
		    resourData.setNaturalname(columnname.getColumnid());
		    resourData.setName(columnname.getColumname());
		    list.add(resourData);
		}
		return list;
	}

}
