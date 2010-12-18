package oe.security3a.sso.onlineuser;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;

/**
 * �����û���ȡ�࣬����֧�ֶ��ֵ�¼ģʽ����ͨ����ssoserver�����ļ��е���������ؼ��ɣ�
 * Ĭ��û�����õ�����£�ϵͳĬ��ʹ��WebOnlineUserMgr��
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
		if (classname == null) {// ���û�����õ�½�û�������,��ôĬ��ʹ�����µ���
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

	// ĿǰWEB�ϵ�¼ֻ�õ�����
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
