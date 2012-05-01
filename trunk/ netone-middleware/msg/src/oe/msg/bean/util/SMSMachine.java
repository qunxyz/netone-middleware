package oe.msg.bean.util;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


import com.jasson.im.api.APIClient;

/**
 * 短信驱动
 * 
 * @author robanco
 * 
 */
public class SMSMachine {

	private static ResourceBundle rs = ResourceBundle.getBundle("config");
	
	private static boolean enable=false;

	private static String host;
	private static String dbName;
	private static String apiId;
	private static String name;
	private static String pwd;
	private static long smId;

	static {
		host = rs.getString("host");
		dbName = rs.getString("dbName");
		apiId = rs.getString("apiId");
		name = rs.getString("name");
		pwd = rs.getString("pwd");
		smId = Integer.parseInt(rs.getString("smId"));
		enable="true".equals(rs);

	}

	private static APIClient con() {

		APIClient handler = null;
		try {
			handler = new APIClient();
			int connectRe = handler.init(host, name, pwd, apiId, dbName);
			if (connectRe == APIClient.IMAPI_CONN_ERR)
				System.err.println("连接失败");
			else if (connectRe == APIClient.IMAPI_API_ERR)
				System.err.println("apiID不存在");
			if (connectRe != APIClient.IMAPI_SUCC) {
				System.err.println("連接信息不對");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

	public static void run(List list) {
		if(!enable)return;
		APIClient handler = null;
		try {
			handler = con();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				String[] info = (String[]) iterator.next();
				int rs = handler.sendSM(info[0], info[1], smId);

				if (rs == 0) {
					PLog.log(info[0], info[1]);
				} else {
					PLog.log("Error:"+info[0], info[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			handler.release();
		}

	}

	public static int run(String mobile, String context) {
		if(!enable)return -1;
		APIClient handler = null;
		int rs=-1;
		try {
			handler = con();
			rs = handler.sendSM(mobile, context, smId);
			if (rs == 0) {
				PLog.log(mobile, context);
			} else {
				PLog.log("Error:"+mobile, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs=-1;
		} finally {
			handler.release();
		}
		return rs;
	}
}
