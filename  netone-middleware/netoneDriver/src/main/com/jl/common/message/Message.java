package com.jl.common.message;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import oe.frame.web.util.WebStr;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.SendMail;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

public final class Message {
	
	static ResourceBundle rsx=ResourceBundle.getBundle("config");
	static boolean isemail=true;
	
	public static String msg_head="";
	public static String msg_end="";
	static{
		try{
			String msgMode=rsx.getString("msgmode");
			isemail="email".equals(msgMode);
			msg_head=rsx.getString("msg_head");
			msg_end=rsx.getString("msg_end");
		}catch(Exception e){
			e.printStackTrace();
			if(StringUtils.isEmpty(msg_head)){
				msg_head="最新待办任务,";
				msg_end="请尽快处理";
			}
		}
	}


	public static String toMessageByUser(String toUser, String context) {

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
			if(isemail){
				Clerk clerk=new Clerk();
				clerk.setPhoneNO(mobile[i]);
				try {
					List list=rs.queryObjectsClerk("0000", clerk, null, 0, 1);
					if(list.size()==1){
						Clerk clerkx=(Clerk)list.get(0);
						String email=clerkx.getEmail();
						toMessageCoreEmail(email, context);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
