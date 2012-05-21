package com.jl.common.netone;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class RMI_SER{
	 /*
	  * ��ȡrmi�������������
	  * xuwei(2012-5-4)
	  * */
	public RMI_SER_obj  RMI_SER(){
 
		RMI_SER_obj rmi_ser_obj=new RMI_SER_obj();
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
				//�Ż����
				WEBSER_PHPCMS = env.fetchEnvValue("WEBSER_PHPCMS");
				rmi_ser_obj.setWEBSER_PHPCMS(WEBSER_PHPCMS) ;
				//����ģ�� �� portlet��ƹ���
				String WEBSER_BIWEB = env.fetchEnvValue("WEBSER_BIWEB");
				rmi_ser_obj.setWEBSER_BIWEB(WEBSER_BIWEB);
				//�ƻ�����
				String WEBSER_WFWEB  = env.fetchEnvValue("WEBSER_WFWEB");
				rmi_ser_obj.setWEBSER_WFWEB(WEBSER_WFWEB);
				//PORTAL �� 	ҵ���������  
			    String 	WEBSER_CMSWEB = env.fetchEnvValue("WEBSER_CMSWEB");
			    rmi_ser_obj.setWEBSER_CMSWEB(WEBSER_CMSWEB);
				//DRP������Դ�ƻ���Ʒ  
				String WEBSER_ISS  = env.fetchEnvValue("WEBSER_ISS");
				rmi_ser_obj.setWEBSER_ISS(WEBSER_ISS);
				//OAϵͳ�칫 
				String WBESER_PHPCMS  = env.fetchEnvValue("WBESER_PHPCMS");
				rmi_ser_obj.setWBESER_PHPCMS(WBESER_PHPCMS);
				//֪ʶ����� 
			    String	WEBSER_KPORTAL = env.fetchEnvValue("WEBSER_KPORTAL");
			    rmi_ser_obj.setWEBSER_KPORTAL(WEBSER_KPORTAL);
			    String WEBSER_SECURITY3A=env.fetchEnvValue("WEBSER_SECURITY3A");
			    rmi_ser_obj.setWEBSER_SECURITY3A(WEBSER_SECURITY3A);
			    String WEBSER_WEBSPECI=env.fetchEnvValue("WEBSER_WEBSPECI");
			    rmi_ser_obj.setWEBSER_WEBSPECI(WEBSER_WEBSPECI);
			    String WEBSER_SpeedyForm=env.fetchEnvValue("WEBSER_SpeedyForm");
			    rmi_ser_obj.setWEBSER_SpeedyForm(WEBSER_SpeedyForm);
			    String WEBSER_APPFRAME=env.fetchEnvValue("WEBSER_APPFRAME");
			    rmi_ser_obj.setWEBSER_APPFRAME(WEBSER_APPFRAME);
			    String WEBSER_WebSerivce=env.fetchEnvValue("WEBSER_WebSerivce");
			    rmi_ser_obj.setWEBSER_WebSerivce(WEBSER_WebSerivce);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return rmi_ser_obj;
	}
}
