/**
 * 
 */
package com.jl.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import oe.frame.web.WebCache;

/**
 * 会话监听
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-3-15 下午04:08:31
 * @history
 */
public class SessionListener implements HttpSessionListener {
	private static int count = 0;

	public void sessionCreated(HttpSessionEvent se) {
		count++;
		// System.out.println("session创建:" + new java.util.Date());
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		count--;
		// System.out.println("session销毁:" + new java.util.Date());
		HttpSession session = se.getSession();
		String sessionId = (String) session.getAttribute("_SESSION_USER");
		WebCache.removeCache("_SESSION_USER" + "_" + sessionId);
	}

	public static int getCount() {
		return (count);
	}

}
