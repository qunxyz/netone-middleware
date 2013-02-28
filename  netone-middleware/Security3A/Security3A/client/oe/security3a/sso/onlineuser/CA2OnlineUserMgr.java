package oe.security3a.sso.onlineuser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.util.ClerkResponseToClerk;
import oe.security3a.sso.SsoToken;
import oe.security3a.sso.util.SsoUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CA2OnlineUserMgr implements OnlineUserMgr {

	static Log log = LogFactory.getLog(DefaultOnlineUserMgr.class);

	private static final String Filter_OnlineUser = "SetOnlineUserLoginListener_userinfo";

	public CA2OnlineUserMgr() {
	}

	private OnlineUser getOnlineUserFromSsoToken(SsoToken st) {
		if (st != null) {
			OnlineUser oluser = new OnlineUser();
			oluser.setLoginname(st.getLoginName());
			oluser.setUserid(st.getUserid());
			oluser.setLoginseq(st.getLoginseq());
			oluser.setLoginip(st.getLoginip());
			oluser.setLoginhost(st.getLoginhost());
			oluser.setBelongto(st.getCode());// ������λ����
			return oluser;
		} else {
			return null;
		}
	}

	/**
	 * ��д����,���õ�ǰ��½�� dxl 2009-11-11
	 */
	public OnlineUser getOnlineUser(HttpServletRequest request) {
		OnlineUser oluser = new OnlineUser();
		String uname=(String)request.getSession().getAttribute("cakey");
		try {

			// ��ǰ��֤�����ȡ��֤��������
			if (StringUtils.isNotEmpty(uname)) {
				// ��ӳ�䴦��Ŀǰ����2��ӳ�䴦��1��������ʱ�������޵�ӳ�����޼���� 2������Excel�ļ���ӳ��
				keyMap(oluser, uname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oluser;
	}

	private void keyMap(OnlineUser oluser, String uname) {
		// ��ȥ�����ʱӳ���Ƿ����
		String tmpkey = fromTempCache(uname);
		System.out.println("tmpkey:" + tmpkey);

		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = null;
			if (tmpkey != null) {
				clerk = resourceRmi.loadClerk("0000", tmpkey);
			}
			if (clerk != null) {// ��� ��TempCache������ôֱ���ø��˻�ӳ��
				oluser.setLoginname(tmpkey);
				oluser.setUserid(clerk.getName());
				oluser.setBelongto("0000");// ������λ����
			} else {// ��û������ʱӳ���ʱ���ñ�׼��Excel�ĵ����������������
				Clerk clerkQ = new Clerk();
				clerkQ.setCompany(uname);// ���ֶβ�ѯ���ǿ���е�
				// bussiness�ֶε���Ϣ���������Ϣ����Excel���ݵ����

				List list = resourceRmi.fetchClerk("0000", clerkQ, null, null);
				if (list != null && list.size() == 1) {
					Clerk clerkX = (Clerk) list.get(0);
					oluser.setLoginname(clerkX.getNaturalname());
					oluser.setUserid(clerkX.getName());
					oluser.setBelongto("0000");// ������λ����
				} else if (list.size() > 1) {
					oluser
							.setLoginname("ϵͳ�ʻ�ӳ���쳣 CAKey��" + uname
									+ "�����ڶ�����û���");
					oluser.setUserid("ϵͳ�ʻ��쳣 CAKey��" + uname + "�����ڶ�����û���");
					oluser.setBelongto("0000");
				} else {
					oluser.setLoginname("ϵͳ�ʻ��쳣 �����ڵ��û�");
					oluser.setUserid("ϵͳ�ʻ��쳣 �����ڵ��û�");
					oluser.setBelongto("0000");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			oluser.setLoginname("ϵͳ�ʻ��쳣" + e.getMessage());
			oluser.setUserid("ϵͳ�ʻ��쳣" + e.getMessage());
			oluser.setBelongto("0000");
		}
	}

	public OnlineUser getOnlineUser(JApplet applet) {
		SsoUtil su = new SsoUtil();
		SsoToken st = su.getSsoToken(applet);
		OnlineUser oluser = getOnlineUserFromSsoToken(st);
		return oluser;
	}

	public OnlineUser getOnlineUser(String ssosessionid) {
		OnlineUser oluser = null;
		SsoUtil su = new SsoUtil();
		SsoToken st = su.getSsoToken(ssosessionid);
		oluser = getOnlineUserFromSsoToken(st);
		return oluser;
	}

	public Clerk getDetailOnlineUser(HttpServletRequest request) {
		OnlineUser oluser = getOnlineUser(request);
		if (oluser != null) {
			Object obj = request.getSession().getAttribute(Filter_OnlineUser);
			Clerk user = null;
			if (obj != null) {
				user = (Clerk) obj;
				if (user.getName().equals(oluser.getLoginname())) {
					return user;
				}
			} else {
				try {
					user = getDetailOnlineUser(oluser);
					request.getSession().setAttribute(Filter_OnlineUser, user);
					return user;
				} catch (Exception e) {
					log.error("��ȡ�����û���ϸ���Գ���", e);
				}
			}
		}
		return null;
	}

	private Clerk getDetailOnlineUser(OnlineUser oluser) {
		try {
			CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");
			RequestCtx requestctx = new RequestCtx();
			requestctx.newSubject();
			requestctx.newResource();
			requestctx.newAction();
			requestctx.getSubject().newAttribute("ope", "getClerk");
			requestctx.getSubject().newAttribute("participant",
					oluser.getLoginname());
			requestctx.getSubject().newAttribute("condition",
					oluser.getLoginname());
			String response = ps.todo(oluser.getBelongto(), requestctx
					.toString());
			return ClerkResponseToClerk.makeClerk(response);
		} catch (Exception e) {
			log.error("��ȡ�����û���ϸ���Գ���", e);
		}
		return null;
	}

	public Clerk getDetailOnlineUser(String ssosessionid) {
		OnlineUser oluser = getOnlineUser(ssosessionid);
		return getDetailOnlineUser(oluser);
	}

	public Clerk getDetailOnlineUser(JApplet applet) {
		OnlineUser oluser = getOnlineUser(applet);
		return getDetailOnlineUser(oluser);
	}

	/**
	 * ͨ����ǰkey�ı�����ӳ������ʺţ����û��ӳ���򷵻�ԭkey
	 * 
	 * @param uname
	 * @return
	 */
	public static String fromTempCache(String uname) {

		// �ڶ��ַ�ʽͨ����ʱӳ��������޵��˺�
		String tempkey = "CAKEY_TEMP_" + uname;

		if (WebCache.containCache(tempkey)) {// ֧����ʱKEYӦ��,��ҪΪ����Ӧ����key�𻵵�����£��ͻ������ñ���KEY������ϵͳ

			String tempkeyValue = (String) WebCache.getCache(tempkey);
			if (tempkeyValue != null && !tempkeyValue.equals("")) {
				return tempkeyValue;
			}
		}
		return null;

	}

	public static void main(String[] args) {
		// String uname = getReplaceCaKey("3500");// ���key��Ӧ��½����
		// System.out.println(uname);
	}
}
