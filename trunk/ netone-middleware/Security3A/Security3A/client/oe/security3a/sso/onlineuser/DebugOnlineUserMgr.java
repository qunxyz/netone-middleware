package oe.security3a.sso.onlineuser;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.util.ClerkResponseToClerk;


public class DebugOnlineUserMgr implements OnlineUserMgr {

	private OnlineUser user;

	private Clerk userinfo;

	public DebugOnlineUserMgr() {
		user = new OnlineUser();
		user.setLoginname("newland");
		user.setBelongto("0001");
		try {
			CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");
			RequestCtx requestctx = new RequestCtx();
			requestctx.newSubject();
			requestctx.newResource();
			requestctx.newAction();
			requestctx.getSubject().newAttribute("ope", "getClerk");
			requestctx.getSubject().newAttribute("participant", "newland");
			requestctx.getSubject().newAttribute("condition", "newland");
			String response = ps.todo(user.getBelongto(), requestctx.toString());
			userinfo = ClerkResponseToClerk.makeClerk(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OnlineUser getOnlineUser(HttpServletRequest request) {
		return user;
	}

	public OnlineUser getOnlineUser(String ssosessionid) {
		return user;
	}

	public OnlineUser getOnlineUser(JApplet applet) {
		return user;
	}

	public int getOnlineUserCount() {
		return 1;
	}

	public Clerk getDetailOnlineUser(HttpServletRequest request) {
		return userinfo;
	}

	public Clerk getDetailOnlineUser(JApplet applet) {
		return userinfo;
	}

	public Clerk getDetailOnlineUser(String ssosessionid) {
		return userinfo;
	}

}
