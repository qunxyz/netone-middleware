package oe.netone.app;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.workflow.DbTools;

public class Query {
	public ArrayList<ResourceInfo> list1;
	public List<ResourceInfo> Querty(){
		list1=new ArrayList<ResourceInfo>();
		  String sqlString = "select * from netone.ums_protectedobject where NATURALNAME like  '%APPFRAME.APPFRAME.%'and objectType='default';";
	         List list=DbTools.queryData(sqlString);
	         for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	    		  Map object = (Map)iterator.next();
	    		     ResourceInfo rsInfo=new ResourceInfo();
	    		     rsInfo.setRname(((String)object.get("NAME")));
	                rsInfo.setRcode(((String)object.get("NATURALNAME")));
		            rsInfo.setObjecttype(((String)object.get("objectType")));
		            rsInfo.setDateTime(((Timestamp)object.get("created")).toString());
		            if(((String)object.get("ACTIVE")).equals("1")){
		            	 rsInfo.setACTIVE("有效");
		             }else{
		            	 rsInfo.setACTIVE("无效");
		             }
		            rsInfo.setFID((String)object.get("ID"));
	    		    
	    		     list1.add(rsInfo);
	         }
	      return list1;
	}
	
	public List step2(String idcreated) {
		final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
				"busstype", "busstip" };
		List lisxt = new ArrayList();
		String id = idcreated;

		String processid = "";
		ResourceRmi resourceRmi = null;
		String formcode = "";

			try {
				resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
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
			UmsProtectedobject upo=null;
			try {
				upo = resourceRmi.loadResourceById(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			formcode = upo.getExtendattribute();
			AnalysisAppFirst domxml=new AnalysisAppFirst();
			
			AppFirst appFirst=AnalysisAppFirst.readXML(formcode);
			
				lisxt.add(appFirst.getFormname()+"["+appFirst.getFormcode()+"]");
				lisxt.add(appFirst.getProcessname()+"["+appFirst.getProcessid()+"]");
				lisxt.add(appFirst.getFormendtitle());
				lisxt.add(appFirst.getFormtitle());
				lisxt.add(appFirst.getWorklistsize());
				lisxt.add(upo.getNaturalname());
				lisxt.add(upo.getName());
				lisxt.add(StringUtils.substringBetween(appFirst.getWorklistDefaultColumn(), "[", "]"));

		return lisxt;
	}
	
}
