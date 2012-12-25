package com.jl.common.message;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import oe.frame.web.util.WebStr;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.SoaBean;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.SendMail;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

public final class Message {
	
	static ResourceBundle rsx=ResourceBundle.getBundle("config",Locale.CHINESE);
	static boolean isemail=true;
	
	public static String msg_head="";
	public static String msg_end="";
	public static String msg_app="";
	static{
		try{
			String msgMode=rsx.getString("msgmode");
			isemail="email".equals(msgMode);
			msg_head=WebStr.iso8859ToGBK(rsx.getString("msg_head"));
			msg_end=WebStr.iso8859ToGBK(rsx.getString("msg_end"));
			msg_app=WebStr.iso8859ToGBK(rsx.getString("msg_app"));
		}catch(Exception e){
			e.printStackTrace();
			if(StringUtils.isEmpty(msg_head)){
				msg_head="最新待办任务,";
				msg_end="请尽快处理";
			}
		}
	}
	
	private static boolean msgAvail(String loginName){
		String sql="select types from  netone.t_cs_user where usercode='"+loginName+"' and (types is null or types='0')";
		try {
			WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
			List list=view.coreSqlview(sql);
			if(list.size()>0){
				return true;
			}
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
		return false;
	}


	public static String toMessageByUser(String toUser, String context) {
		    if(!msgAvail(toUser)){
		    	return "fail";
		    }
			ResourceRmi rs = null;
			try {
				rs = (ResourceRmi) RmiEntry.iv("resource");
				Clerk touserObj = rs.loadClerk("0000", toUser);
				if (touserObj == null) {
					System.err.println("lose user:" + toUser);
					return "ok";
				}
				if(isemail){
					return toMessageCoreEmail(touserObj.getEmail(), context);
				}else{
					return toMessageCoreSMS(touserObj.getPhoneNO(), context);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "fail";


	}

	public static String toMessageByUser(List<String[]> info) {
			
			List listinfo = new ArrayList();
			ResourceRmi rs = null;
			try {
				rs = (ResourceRmi) RmiEntry.iv("resource");
				for (Iterator iterator = info.iterator(); iterator.hasNext();) {
					String[] infox = (String[]) iterator.next();
				    if(!msgAvail(infox[0])){
				    	continue;
				    }
					Clerk touserObj = rs.loadClerk("0000", infox[0]);

					if (touserObj == null) {
						System.err.println("lose user:" + infox[0]);
					} else {
						String[] infox1 = { touserObj.getPhoneNO(), infox[1] };
						if(isemail){
							infox1[0] = touserObj.getEmail();	
						}
						listinfo.add(infox1);
					}
				}
				return toMessageCoreBat(listinfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return "ok";

	}

	private static String toMessageCoreSMS(String mobile, String context) {
		
		try {
			BeanService bean = (BeanService) RmiEntry.iv("beanhandle");

			// == 测试异步模式 ==
			SoaBean obj = bean.inParamDescription("msgbean");
			obj.getValues().put("mobile", mobile);
			obj.getValues().put("context",context);
			// 保存业务对象
			bean.saveInParam(obj);
			bean.todo(obj.getLsh());
			
			String rs=bean.loadOutParam(obj.getLsh()).getValues().get(
			"types").toString();
			
			return rs;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";

	}
	
	private static String toMessageCoreEmail(String email, String context) {
		try {

			SendMail msgHandle = (SendMail) RmiEntry.iv("sendmail");

			msgHandle.send(email, "最新待办任务", context);
			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";

	}

	private static String toMessageCoreBat(List<String[]> msg) {
		for (Iterator iterator = msg.iterator(); iterator.hasNext();) {
			String[] strings = (String[]) iterator.next();
			if(isemail){
				toMessageCoreEmail(strings[0], strings[1]);
			}else{
				toMessageCoreSMS(strings[0], strings[1]);
			}
			
		}
		return "ok";

	}
	
	public static String toMessageCoreByMobileOrEmail(String mobile[],String context,HttpServletRequest req) {
		System.out.println(context);
		context=WebStr.encode(req, context);
		ResourceRmi rs=null;
		try {
			rs=(ResourceRmi)RmiEntry.iv("resource");
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
		for (int i=0;i<mobile.length;i++) {
			Clerk clerk=new Clerk();
			clerk.setPhoneNO(mobile[i]);
			Clerk clerkx=null;
			try {
				List list=rs.queryObjectsClerk("0000", clerk, null, 0, 1);
				if(list.size()==1){
					clerkx=(Clerk)list.get(0);
				    if(!msgAvail(clerkx.getNaturalname())){
				    	continue;
				    }
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    if(!msgAvail(clerkx.getNaturalname())){
		    	return "fail";
		    }
			
			if(isemail){
				String email=clerkx.getEmail();
				toMessageCoreEmail(email, context);
			}else{
				toMessageCoreSMS(mobile[i], context);
			}
			
		}
		return "ok";

	}
	
	public static void main(String[] args) {

		try {
			BeanService bean = (BeanService) RmiEntry.iv("beanhandle");

			// == 测试异步模式 ==
			SoaBean obj = bean.inParamDescription("msgbean");
			obj.getValues().put("mobile", "11");
			obj.getValues().put("context","11");
			// 保存业务对象
			bean.saveInParam(obj);
			bean.todo(obj.getLsh());
			
			String rs=bean.loadOutParam(obj.getLsh()).getValues().get(
			"types").toString();

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	


}
