package oe.netone.app;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.impl2.AnalysisAppThree;
import com.jl.common.app.impl2.AppThree;


public class Querydaiban{
	
	public  List step2(String idcreated) {
		final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
				"busstype", "busstip" };
		List lisxt = new ArrayList();
		String id = idcreated;

		String processid = "";
		ResourceRmi resourceRmi = null;
		String formcode = "";
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(id);
			formcode = upo.getExtendattribute();
			String name=upo.getNaturalname();
			DomXml domxml=new DomXml();
			List<Person> list1=domxml.readXML(formcode);
			
			formcode=StringUtils.substringBetween(list1.get(0).getFormcode(), "[", "]");
			
		    processid =StringUtils.substringBetween(list1.get(0).getProcessid(), "[", "]");

			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			WorkflowProcess list = wfview.fetchWorkflowProcess(processid);
			DataField[] data = list.getDataField();
			for (int i = 0; i < data.length; i++) {
				String fileid = data[i].getId();
				boolean isKeyvar = false;
				for (int j = 0; j < _CORE_KEY_VAR.length; j++) {
					// 需要忽略掉流程内部关键变量
					if (_CORE_KEY_VAR[j].equalsIgnoreCase(fileid)) {
						isKeyvar = true;
						break;
					}
				}
				if (isKeyvar)
					continue;
				if (fileid.startsWith("r_")) {
					// 说明是内部路由变量忽略
					continue;
				}
				UmsProtectedobject upobj = new UmsProtectedobject();
				upobj.setNaturalname(name+"."+fileid.toUpperCase());	
				UmsProtectedobject upob=resourceRmi.loadResourceByNatural(name+"."+fileid.toUpperCase());
				String boj=upob.getExtendattribute();
				if(boj==null || boj.equals("SS")){
					
				}else{
					List<AppThree> list2=Xmldaiban.readXML(boj);
					 String objecttype=list2.get(0).getFormcolumn();
					   
					upobj.setObjecttype(objecttype);
				}
				upobj.setName(data[i].getName());
				lisxt.add(upobj);
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
	
	public  List<String> chanxunshuju(String lujing) {
      List<String> list=new ArrayList<String>();
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
				UmsProtectedobject upob=null;
				try {
					upob = resourceRmi.loadResourceByNatural(lujing);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String boj=upob.getExtendattribute();
				if(!StringUtils.isEmpty(boj)){
					AppThree app=AnalysisAppThree.readXML(boj);
					AppThree appThree=new AppThree();
					
					list.add(app.getNaturalname());
					list.add(app.getName());
				    list.add(app.getFormcolumn());
					list.add(app.getIshidden());
					
					list.add(app.getLength());
					list.add(app.getActname());
					list.add(app.getCommitername());
					list.add(app.getStarttime());
					list.add(app.getScript());
				}				 
	    return list;
	}


}
	
