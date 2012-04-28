package com.jl.common.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

public final class Message {


	public static String toMessageByUser(String toUser, String context) {

			ResourceRmi rs = null;
			try {
				rs = (ResourceRmi) RmiEntry.iv("resource");
				Clerk touserObj = rs.loadClerk("0000", toUser);
				if (touserObj == null) {
					System.err.println("lose user:" + toUser);
					return "ok";
				}
				return toMessageCore(touserObj.getPhoneNO(), context);
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
						listinfo.add(infox1);
					}
				}
				return toMessageCore(listinfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return "ok";

	}

	public static String toMessageCore(String mobile, String context) {
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

	public static String toMessageCore(List<String[]> msg) {
		for (Iterator iterator = msg.iterator(); iterator.hasNext();) {
			String[] strings = (String[]) iterator.next();
			toMessageCore(strings[0], strings[1]);
		}
		return "ok";

	}
	
	public static String toMessageCore(String mobile[],String context,HttpServletRequest req) {
		System.out.println(context);
		context=WebStr.encode(req, context);
		for (int i=0;i<mobile.length;i++) {
			toMessageCore(mobile[i], context);
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
