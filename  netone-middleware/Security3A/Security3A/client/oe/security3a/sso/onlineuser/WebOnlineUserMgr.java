package oe.security3a.sso.onlineuser;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.util.ClerkResponseToClerk;
import oe.security3a.sso.SsoToken;
import oe.security3a.sso.util.SsoUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WebOnlineUserMgr implements OnlineUserMgr {

	static Log log = LogFactory.getLog(WebOnlineUserMgr.class);

	private static final String Filter_OnlineUser = "SetOnlineUserLoginListener_userinfo";

	public WebOnlineUserMgr() {
	}

	private OnlineUser getOnlineUserFromSsoToken(SsoToken st) {
		if (st != null) {
			OnlineUser oluser = new OnlineUser();
			oluser.setLoginname(st.getLoginName());
			oluser.setUserid(st.getUserid());
			oluser.setLoginseq(st.getLoginseq());
			oluser.setLoginip(st.getLoginip());
			oluser.setLoginhost(st.getLoginhost());
			oluser.setBelongto(st.getCode());//设置四位代码
			return oluser;
		} else {
			return null;
		}
	}

	public OnlineUser getOnlineUser(HttpServletRequest request) {
		SsoUtil su = new SsoUtil();
		SsoToken st = su.getSsoToken(request);
		return getOnlineUserFromSsoToken(st);
		
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
			requestctx.getSubject().newAttribute("participant", oluser.getLoginname());
			requestctx.getSubject().newAttribute("condition", oluser.getLoginname());
			String response = ps.todo(oluser.getBelongto(), requestctx.toString());
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

}
