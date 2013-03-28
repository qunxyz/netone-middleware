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
			oluser.setBelongto(st.getCode());// 设置四位代码
			return oluser;
		} else {
			return null;
		}
	}

	/**
	 * 重写代码,区得当前登陆者 dxl 2009-11-11
	 */
	public OnlineUser getOnlineUser(HttpServletRequest request) {
		OnlineUser oluser = new OnlineUser();
		String textCert; // 证书号
		String textOriginData;// 原文(随机数)
		String textSignData; // 签名
	
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
		// 社保4000
		ca.setErrorBase(4000);
		// Windows 验证服务器IP
		ca.setServerURL("202.109.194.166:7000");
		byte[] bySubjectCN = ca.GetSubjectCN(textCert);
		if (bySubjectCN == null) {
			return null;
		}

		String strSubjectCN = new String(bySubjectCN);
		// 取证书序列号
		String strSerial = ca.getSerialFromCert(textCert);

		// 对企业进行身份认证（HTTP环境下的数字证书认证）
		String strRet = ca.FJCA_VerifyQY(textOriginData, textSignData, textCert);

		int retCode = ca.getLastError();
		if (retCode == 0) {
			String[] retArr = strRet.split(",");

			// 检验企业证书有效期终止时间,服务截止时间,是否已到期

			// 判断证书有效期
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
							msg = "注意：您的数字证书有效期将在"
									+ remDate
									+ "天后过期，请您及时联系福建CA客服中心延长证书有效期。"
									+ "如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806.";
							if (remDate == 0) {
								lasttime = sCertDate.substring(11, sCertDate
										.length());
								msg = "注意：您的数字证书有效期将在"
										+ lasttime
										+ "后过期，请您及时联系福建CA客服中心延长证书有效期。"
										+ "如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806.";
							}
							if (certvDate.compareTo(today) < 0) {
								httpSession.setAttribute("msg", msg);
							}

						}
					}
				}

				// 判断服务有效期
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
												"注意：证书服务在"
														+ remDate
														+ "天内即将过期，请及时延长证书有效服务期限。"
														+ "<br>如果您无法处理，请联系当地社保服务商或者拨打客户服务热线:0591-968806.");
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
						oluser.setUserid("银行用户");
						oluser.setBelongto("0000");
						oluser.setLoginhost("");
						oluser.setLoginip("");
						oluser.setLoginseq("");
					} else if ("001".equals(unitNo)) {
						oluser.setLoginname("qiye");
						oluser.setUserid("企业用户");
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
		// 先去检查临时映射是否存在
		String tmpkey = fromTempCache(uname);
		System.out.println("tmpkey:" + tmpkey);

		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = null;
			if (tmpkey != null) {
				clerk = resourceRmi.loadClerk("0000", tmpkey);
			}
			if (clerk != null) {// 如果 在TempCache中有那么直接用该账户映射
				oluser.setLoginname(tmpkey);
				oluser.setUserid(clerk.getName());
				oluser.setBelongto("0000");// 设置四位代码
			} else {// 当没有做临时映射的时候，用标准的Excel文档导入的数据来处理
				Clerk clerkQ = new Clerk();
				clerkQ.setCompany(uname);// 该字段查询的是库表中的
				// bussiness字段的信息，这里的信息是由Excel数据导入的
				List list = resourceRmi.fetchClerk("0000", clerkQ, null, null);
				if (list != null && list.size() == 1) {
					Clerk clerkX = (Clerk) list.get(0);
					oluser.setLoginname(clerkX.getNaturalname());
					oluser.setUserid(clerkX.getName());
					oluser.setBelongto("0000");// 设置四位代码
				} else if (list.size() > 1) {
					oluser
							.setLoginname("系统帐户映射异常 CAKey：" + uname
									+ "存在于多个的用户中");
					oluser.setUserid("系统帐户异常 CAKey：" + uname + "存在于多个的用户中");
					oluser.setBelongto("0000");
				} else {
					oluser.setLoginname("系统帐户异常 不存在的用户");
					oluser.setUserid("系统帐户异常 不存在的用户");
					oluser.setBelongto("0000");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			oluser.setLoginname("系统帐户异常" + e.getMessage());
			oluser.setUserid("系统帐户异常" + e.getMessage());
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
					log.error("获取在线用户详细属性出错！", e);
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
			log.error("获取在线用户详细属性出错！", e);
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
	 * 通过当前key的编码获对映射后有帐号，如果没有映射则返回原key
	 * 
	 * @param uname
	 * @return
	 */
	public static String fromTempCache(String uname) {

		// 第二种方式通过临时映射的有期限的账号
		String tempkey = "CAKEY_TEMP_" + uname;

		if (WebCache.containCache(tempkey)) {// 支持临时KEY应用,主要为了适应由于key损坏的情况下，客户可以用备用KEY来访问系统

			String tempkeyValue = (String) WebCache.getCache(tempkey);
			if (tempkeyValue != null && !tempkeyValue.equals("")) {
				return tempkeyValue;
			}
		}
		return null;

	}

	public static void main(String[] args) {
		// String uname = getReplaceCaKey("3500");// 获得key对应登陆编码
		// System.out.println(uname);
	}
}
