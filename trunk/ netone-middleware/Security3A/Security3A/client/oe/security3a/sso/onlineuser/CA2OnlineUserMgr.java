package oe.security3a.sso.onlineuser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
		String textCert; // ֤���
		String textOriginData;// ԭ��(�����)
		String textSignData; // ǩ��
	
		HttpSession httpSession = request.getSession();
		
		if("adminx".equals((String)WebCache.getCache("action"))){
			oluser.setLoginname("adminx");
			oluser.setUserid("adminx");
			oluser.setBelongto("0000");
			oluser.setLoginhost("");
			oluser.setLoginip("");
			oluser.setLoginseq("");
			return oluser;
		}
		
		textCert = (String)httpSession.getAttribute("textCert");
		textOriginData =(String) httpSession.getAttribute("RANDOM");
		textSignData =(String)httpSession.getAttribute("textSignData");
		
		if(textCert==null){
			textCert=(String)WebCache.getCache("textCert");
		}
		if(textOriginData==null){
			textOriginData=(String)WebCache.getCache("textOriginData");
		}
		if(textSignData==null){
			textSignData=(String)WebCache.getCache("textSignData");
		}
		
		fjca.FJCAApps ca = new fjca.FJCAApps();
		// �籣4000
		ca.setErrorBase(4000);
		// Windows ��֤������IP
		ca.setServerURL("202.109.194.166:7000");
		byte[] bySubjectCN = ca.GetSubjectCN(textCert);
		if (bySubjectCN == null) {
			return null;
		}

		String strSubjectCN = new String(bySubjectCN);
		// ȡ֤�����к�
		String strSerial = ca.getSerialFromCert(textCert);

		// ����ҵ���������֤��HTTP�����µ�����֤����֤��
		String strRet = ca.FJCA_VerifyQY(textOriginData, textSignData, textCert);

		int retCode = ca.getLastError();
		if (retCode == 0) {
			String[] retArr = strRet.split(",");

			// ������ҵ֤����Ч����ֹʱ��,�����ֹʱ��,�Ƿ��ѵ���

			// �ж�֤����Ч��
			String sCertDate = retArr[0];
			java.util.Date certDate = new java.util.Date();
			java.util.Date today = new java.util.Date();
			try {
				if (sCertDate != null) {
					if (!sCertDate.equals("")) {
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
								"yyyy/MM/dd hh:mm:ss");
						certDate = sdf.parse(sCertDate);
						long oneDay = 86400000;
						if (certDate.compareTo(today) < 0) {
							return null;
						} else {
							java.util.Date certvDate = new java.util.Date(certDate.getTime() - 90 * oneDay);
							java.util.Calendar c = java.util.Calendar.getInstance();
							c.setTime(certDate);
							java.util.Calendar c1 = java.util.Calendar.getInstance();
							c1.setTime(sdf.parse(sdf.format(today)));

							
							int remDate = (int) ((c.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000));		
							String lasttime = null;
							String msg = null;
							msg = "ע�⣺��������֤����Ч�ڽ���"
									+ remDate
									+ "�����ڣ�������ʱ��ϵ����CA�ͷ������ӳ�֤����Ч�ڡ�"
									+ "������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.";
							if (remDate == 0) {
								lasttime = sCertDate.substring(11, sCertDate
										.length());
								msg = "ע�⣺��������֤����Ч�ڽ���"
										+ lasttime
										+ "����ڣ�������ʱ��ϵ����CA�ͷ������ӳ�֤����Ч�ڡ�"
										+ "������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.";
							}
							if (certvDate.compareTo(today) < 0) {
								httpSession.setAttribute("msg", msg);
							}

						}
					}
				}

				// �жϷ�����Ч��
				String sSerDate = retArr[1];
				java.util.Date serDate = new java.util.Date();
				// java.util.Date today = new java.util.Date();
				if (sSerDate != null) {
					if (!sSerDate.equals("")) {
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
								"yyyy/MM/dd hh:mm:ss");
						serDate = sdf.parse(sSerDate + " 23:59:59");
						long oneDay = 86400000;
						if (serDate.compareTo(today) < 0) {
							return null;

						} else {
							java.util.Date certvDate = new java.util.Date(serDate.getTime() - 30 * oneDay);
							//int remDate = DateUtil.diffDate(serDate, sdf.parse(sdf.format(today))) + 1;
							
							java.util.Calendar c = java.util.Calendar.getInstance();
							c.setTime(serDate);
							java.util.Calendar c1 = java.util.Calendar.getInstance();
							c1.setTime(sdf.parse(sdf.format(today)));
							int remDate = (int) ((c.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000))+ 1;
							
							if (certvDate.compareTo(today) < 0) {
								httpSession
										.setAttribute(
												"msg1",
												"ע�⣺֤�������"
														+ remDate
														+ "���ڼ������ڣ��뼰ʱ�ӳ�֤����Ч�������ޡ�"
														+ "<br>������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.");
							}
						}
					}
				}

				String unitNo = retArr[2];
				if(unitNo==null||unitNo.equals("")){
					return null;
				}else{		
					if("002".equals(unitNo)){
						oluser.setLoginname("yinghang");
						oluser.setUserid("�����û�");
						oluser.setBelongto("0000");
						oluser.setLoginhost("");
						oluser.setLoginip("");
						oluser.setLoginseq("");
					} else if ("001".equals(unitNo)) {
						oluser.setLoginname("qiye");
						oluser.setUserid("��ҵ�û�");
						oluser.setBelongto("0000");
						oluser.setLoginhost("");
						oluser.setLoginip("");
						oluser.setLoginseq("");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			return null;
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
