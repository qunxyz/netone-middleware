package oe.security3a.seucore.permission.tag;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AbstractUITag extends TagSupport {

	protected static Log log = LogFactory.getLog(AbstractUITag.class);

	protected String resource;

	protected String action;

	protected String name;

	protected String id;

	protected String value;

	protected String disabled;

	protected String style;

	protected String cssclass;

	protected String tabindex;

	protected String title;

	protected String accept;

	protected String accesskey;

	protected String alt;

	protected String onblur;

	protected String onchange;

	protected String onclick;

	protected String ondblclick;

	protected String onfocus;

	protected String onkeydown;

	protected String onkeypress;

	protected String onkeyup;

	protected String onmousedown;

	protected String onmousemove;

	protected String onmouseout;

	protected String onmouseover;

	protected String onmouseup;

	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}

	public boolean checkLogin() {
		String oluserkey = "onlineuser";
		if (pageContext.getRequest().getAttribute(oluserkey) != null) {
			return true;
		} else {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr
					.getOnlineUser((HttpServletRequest) pageContext
							.getRequest());
			if (oluser != null) {
				pageContext.getRequest().setAttribute(oluserkey,
						oluser.getLoginname());
				return true;
			} else {
				String gotourl = ((HttpServletRequest) pageContext.getRequest())
						.getRequestURL().toString();
				String loginurl = SecurityConfig.getInstance()
						.getSsoServerUrl()
						+ "/sso/login.jsp"
						+ "?gotourl="
						+ gotourl
						+ "&todo=addtoken";
				try {
					((HttpServletResponse) pageContext.getResponse())
							.sendRedirect(loginurl);
				} catch (IOException e) {
					log.error("跳转到登录页面出错！", e);
				}
				return false;
			}
		}
	}

	public boolean checkPermission() {
		String oluserkey = "onlineuser";

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr
				.getOnlineUser((HttpServletRequest) pageContext
						.getRequest());
		String code = oluser.getBelongto();
		try {
			CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");

			boolean b = ps.checkUserPermission(code, pageContext.getRequest()
					.getAttribute(oluserkey).toString(), resource, action);
			System.out.println("permission rs:"
					+ pageContext.getRequest().getAttribute(oluserkey)
							.toString() + "_" + b);
			return b;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String createAttrContent() throws Exception {
		Class c = AbstractUITag.class;
		Field[] fs = c.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].getName().equals("resource")
					|| fs[i].getName().equals("action")
					|| fs[i].getName().equals("application")
					|| fs[i].getName().equals("log")) {
				continue;
			} else {
				fs[i].setAccessible(true);
				Object obj = fs[i].get(this);
				if (obj != null) {
					if (fs[i].getName().equals("cssclass")) {
						sb.append(" class");
					} else {
						sb.append(" " + fs[i].getName());
					}
					sb.append("=\"" + obj.toString() + "\"");
				}
			}
		}
		return sb.toString();
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCssclass() {
		return cssclass;
	}

	public void setCssclass(String cssclass) {
		this.cssclass = cssclass;
	}

}
