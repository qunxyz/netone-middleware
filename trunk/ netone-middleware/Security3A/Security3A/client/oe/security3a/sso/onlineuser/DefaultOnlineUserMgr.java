package oe.security3a.sso.onlineuser;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;

/**
 * 在线用户获取类，该类支持多种登录模式，可通过在ssoserver配置文件中的配置上相关即可，
 * 默认没有配置的情况下，系统默认使用WebOnlineUserMgr类
 * 
 * @author chenjx
 * 
 * 
 */
public class DefaultOnlineUserMgr implements OnlineUserMgr {

	static OnlineUserMgr onlineuse = null;

	static {
		String classname = null;
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			classname = cupm.fetchConfig("loginmodeDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (classname == null) {// 如果没有配置登陆用户控制类,那么默认使用以下的类
			onlineuse = new WebOnlineUserMgr();
		} else {
			try {
				onlineuse = (OnlineUserMgr) Class.forName(classname)
						.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 目前WEB上登录只用到该类
	public OnlineUser getOnlineUser(HttpServletRequest request) {

		return onlineuse.getOnlineUser(request);
	}

	public Clerk getDetailOnlineUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public Clerk getDetailOnlineUser(JApplet applet) {
		// TODO Auto-generated method stub
		return null;
	}

	public Clerk getDetailOnlineUser(String ssosessionid) {
		// TODO Auto-generated method stub
		return null;
	}

	public OnlineUser getOnlineUser(String ssosessionid) {
		// TODO Auto-generated method stub
		return null;
	}

	public OnlineUser getOnlineUser(JApplet applet) {
		// TODO Auto-generated method stub
		return null;
	}

}
