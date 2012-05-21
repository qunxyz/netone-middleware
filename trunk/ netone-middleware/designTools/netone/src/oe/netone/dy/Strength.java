package oe.netone.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class Strength {
	List list=new ArrayList();
	public List Strength(){
		
	EnvService env=null;
	try {
		env = (EnvService) RmiEntry.iv("envinfo");
	} catch (MalformedURLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NotBoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		String SpeedyForm_v= env.fetchEnvValue("WEBSER_SpeedyForm");
		list.add(SpeedyForm_v);
		System.out.println(SpeedyForm_v);
		String SECURITY3A = env.fetchEnvValue("WEBSER_SECURITY3A");
		list.add(SECURITY3A);
		System.out.println(SECURITY3A);
		String wfweb = env.fetchEnvValue("WEBSER_WFWEB");
		list.add(wfweb);
		System.out.println(wfweb);
		String WEBSER_APPFRAME = env.fetchEnvValue("WEBSER_APPFRAME");
		list.add(WEBSER_APPFRAME);
		System.out.println(WEBSER_APPFRAME);
		String WEBSER_CMSWEB = env.fetchEnvValue("WEBSER_CMSWEB");
		list.add(WEBSER_CMSWEB);
		String [] strlujinStrings=SpeedyForm_v.split("/netone");
		list.add(strlujinStrings[0]);
		String WEBSER_BIWEB=env.fetchEnvValue("WEBSER_BIWEB");
		list.add(WEBSER_BIWEB);
		String WEBSER_WFWEB=env.fetchEnvValue("WEBSER_WFWEB");
		list.add(WEBSER_WFWEB);
		String WEBSER_DYFORM=env.fetchEnvValue("WEBSER_DYFORM");
		list.add(WEBSER_DYFORM);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list;
	}
	
	public List  RMI_SER(){
		List list1=new ArrayList();
			
		EnvService env=null;
			try {
				env = (EnvService) RmiEntry.iv("envinfo");
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
			
			String WEBSER_PHPCMS;
			try {
				//门户框架
				WEBSER_PHPCMS = env.fetchEnvValue("WEBSER_PHPCMS");
				list1.add(WEBSER_PHPCMS);
				//报表模板 和 portlet设计管理
				String WEBSER_BIWEB = env.fetchEnvValue("WEBSER_BIWEB");
				list1.add(WEBSER_BIWEB);
				//计划任务
				String WEBSER_WFWEB  = env.fetchEnvValue("WEBSER_WFWEB");
				list1.add(WEBSER_WFWEB);
				//PORTAL 是 	业务组件管理  
			    String 	WEBSER_CMSWEB = env.fetchEnvValue("WEBSER_CMSWEB");
				list1.add(WEBSER_CMSWEB);
				//DRP分销资源计划产品  
				String WEBSER_ISS  = env.fetchEnvValue("WEBSER_ISS ");
				list1.add(WEBSER_PHPCMS);
				//OA系统办公 
				String WBESER_PHPCMS  = env.fetchEnvValue("WBESER_PHPCMS ");
				list1.add(WBESER_PHPCMS );
				//知识库管理 
			    String	WEBSER_KPORTAL = env.fetchEnvValue("WEBSER_KPORTAL");
				list1.add(WEBSER_KPORTAL);
			    String WEBSER_SECURITY3A=env.fetchEnvValue("WEBSER_SECURITY3A");
			    list1.add(WEBSER_SECURITY3A);
			    String WEBSER_WEBSPECI=env.fetchEnvValue("WEBSER_WEBSPECI");
			    list1.add(WEBSER_WEBSPECI);
			    String WEBSER_SpeedyForm=env.fetchEnvValue("WEBSER_SpeedyForm");
			    list1.add(WEBSER_SpeedyForm);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return list1;
	}

}
