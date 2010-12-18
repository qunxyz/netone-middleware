package oe.security3a.sso;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.sso.listener.LoginEvent;
import oe.security3a.sso.listener.LoginListener;
import oe.security3a.sso.util.FilterFile;
import oe.security3a.sso.util.HttpCommunicate;
import oe.security3a.sso.util.SsoUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 用户登录验证及SSO拦截器，用于验证用户是否登录，支持sso 。
 * <p>
 * 支持用户登录的监听，用于实现web应用在用户登录时需要做的一些事情，例如：写用户登录
 * 信息，给用户授权等。web应用编写的监听器通过配置参数listener传递到Filter中，多个监听 器用逗号(",")分割。
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class SsoFilter implements Filter {

	static Log log = LogFactory.getLog(SsoFilter.class);

	protected static final String CONTENT_TYPE = "text/html; charset=GBK";

	static ResourceBundle redirect = null;
	static {
		try {
			redirect = ResourceBundle.getBundle("urlRedirect");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List loginListenerList = null;

	protected List loginListenerClassList = new ArrayList();

	protected String ssoServerUrl;

	protected String validateurl;

	protected String loginurl;

	protected String timeouturl;

	protected HashSet abortUrlSet = new HashSet();

	protected HashSet filterUrlSet = new HashSet();

	protected HashSet loginUrlSet = new HashSet();

	protected HashSet checkLoginUrlSet = new HashSet();

	protected SsoUtil ssoutil = null;

	protected HashSet definedPara = new HashSet();

	protected boolean isDebug = false;

	/**
	 * Filter初始化，做配置登录监听器等操作。
	 * 
	 * @param filterConfig
	 *            FilterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		String realpath = filterConfig.getServletContext().getRealPath("");
		String info = StringUtils.substringAfterLast(realpath, File.separator);
		System.out.println("........... 启动SecurityGate  OK [" + info + "]");

		try {
			ssoutil = new SsoUtil();

			// 配置系统默认的参数
			definedPara.add("todo");
			definedPara.add("gotourl");
			definedPara.add("addtoken");

			SecurityConfig config = SecurityConfig.getInstance();
			isDebug = config.getIsDebug();
			ssoServerUrl = config.getSsoServerUrl();
			validateurl = ssoServerUrl + "/validatesvl";
			loginurl = ssoServerUrl + config.getLoginPage();
			timeouturl = ssoServerUrl + config.getTimeOutPage();
			String abortUrls = filterConfig.getInitParameter("AbortUrls");
			String abortUrlType = filterConfig.getInitParameter("AbortUrlType");

			List alllist = new ArrayList();

			if (abortUrlType != null) {
				// 零时处理 静态html页面的过滤处理
				String[] abortUrlTypeArr = StringUtils.split(abortUrlType, ",");
				for (int i = 0; i < abortUrlTypeArr.length; i++) {

					List listhtml = FilterFile.filterfile(realpath,
							abortUrlTypeArr[i]);
					for (Iterator iterator2 = listhtml.iterator(); iterator2
							.hasNext();) {
						String objectx = (String) iterator2.next();
						objectx = "/" + objectx;
						alllist.add(objectx);
					}
				}

			}
			if (abortUrls != null) {
				String[] urls = StringUtils.split(abortUrls, ",");
				List list = Arrays.asList(urls);

				alllist.addAll(list);

				abortUrlSet.addAll(alllist);
				System.out.println("Abort URL:");
				for (Iterator iterator = alllist.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					System.out.println(object);
				}

			}

			// 当为不同系统时可以选择配置以下几项
			// 配置登录监听
			String listeners = filterConfig.getInitParameter("LoginListener");
			if (listeners != null) {
				String[] lis = listeners.split(",");
				for (int i = 0; i < lis.length; i++) {
					log.debug("添加登录监听器：" + lis[i]);
					loginListenerClassList.add(lis[i]);
				}
			}
			// 配置系统的登录页，过滤器会屏蔽掉该页
			String LoginUrls = filterConfig.getInitParameter("LoginUrls");
			if (LoginUrls != null) {
				String[] urls = LoginUrls.split(",");
				loginUrlSet.addAll(Arrays.asList(urls));
			}
			// 配置需要验证登录用户的地址。
			String checkUrls = filterConfig.getInitParameter("CheckLoginUrls");
			if (checkUrls != null) {
				String[] urls = checkUrls.split(",");
				checkLoginUrlSet.addAll(Arrays.asList(urls));
			}
			// 配置仅需要过滤的地址。
			String filterUrls = filterConfig.getInitParameter("FilterUrls");
			if (filterUrls != null) {
				String[] urls = filterUrls.split(",");
				filterUrlSet.addAll(Arrays.asList(urls));
			}

			doOtherInit(filterConfig);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doOtherInit(FilterConfig filterConfig)
			throws ServletException {

	}

	protected void doOtherFilter(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IOException,
			ServletException {

	}

	/**
	 * 用户登录验证。
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @param filterChain
	 *            FilterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		String requri = req.getRequestURI();

		// 只处理jsp，html，do和servlet的请求.
		int i = requri.lastIndexOf(".");
		if (i != -1) {
			String extname = requri.substring(i + 1, requri.length());
			boolean b = extname.equalsIgnoreCase("jsp")
					|| extname.equalsIgnoreCase("html")
					|| extname.equalsIgnoreCase("do")
					|| extname.equalsIgnoreCase("htm");
			if (!b) {
				filterChain.doFilter(req, resp);
				return;
			}
		}

		// log.debug( "请求的URI:" + requri ) ;

		String gotourl = req.getRequestURL().toString();
		
		gotourl = redirector(gotourl);
		
		if (true) {
			StringBuffer sb = new StringBuffer();
			boolean first = true;
			Enumeration enum1 = req.getParameterNames();
			while (enum1.hasMoreElements()) {
				String name = enum1.nextElement().toString();
				if (!definedPara.contains(name)) {
					String pvalue = req.getParameter(name);
					if (first) {
						first = false;
					} else {
						sb.append("&");
					}
					sb.append(name + "=" + pvalue);
				}
			}
			if (!first) {
				gotourl = gotourl + "?" + sb.toString();
			}
		}
		String encodedgotourl = URLEncoder.encode(gotourl, "GBK");

		String contextPath = req.getContextPath();
		String reqPage = "";
		if (contextPath.equals("/")) {
			reqPage = requri;
		} else {
			reqPage = requri.replaceFirst(contextPath, "");
		}

		reqPage = reqPage.replaceAll("//", "/");

		// log.debug( "请求的页面：" + reqPage ) ;

		boolean isdofilter = true;
		if (filterUrlSet.size() != 0) {
			if (!filterUrlSet.contains(reqPage)) {
				isdofilter = false;
				filterChain.doFilter(req, resp);
			}
		} else {
			if (abortUrlSet.contains(reqPage) || reqPage.startsWith("/_seu9")) {
				log.debug("请求的页面uri：" + requri + "被忽略.");
				isdofilter = false;
				filterChain.doFilter(req, resp);
			}
			if (loginUrlSet.contains(reqPage)) {
				log.debug("请求的页面uri：" + requri + "被重定向到登录页。");
				isdofilter = false;
				resp.sendRedirect(loginurl);
			}
		}
		if (isdofilter) {

			String addToken = req.getParameter("addtoken");
			if (addToken != null && !addToken.equals("")) {
				log.debug("执行添加token到系统.");
				SsoToken st = new SsoToken();
				st.parse(addToken);
				// 验证token的真实性， todo=checktoken表示为请求需要验证token
				String url = validateurl + "?todo=checktoken&user="
						+ st.getLoginName();
				try {
					String returnstr = HttpCommunicate.httpRequest(url, st
							.getSessionId());
					if (returnstr.startsWith("checktoken:")) {
						returnstr = returnstr.substring(11, returnstr.length());
					}
					if (returnstr.equals("true")) {
						// 验证成功！
						log.debug("执行添加token到系统: 验证token成功！");
						st.setFirstToken(false);
						st.setTokenValidated(true);
						ssoutil.saveSsoToken(req, resp, st.tokenToString());
						resp.setContentType(CONTENT_TYPE);

						// 调用登录监听
						LoginEvent e = new LoginEvent(req, resp, st
								.getLoginName(), st);
						fireLoginListener(e, req);

						// 重定向
						postRedirect(gotourl, req, resp);

					} else {
						// 验证失败，重定向到登录页。
						log.debug("执行添加token到系统: token验证失败！");
						resp.sendRedirect(loginurl);
					}
				} catch (Exception ex) {
					log.error("验证token出错!", ex);
					resp.sendRedirect(loginurl);
				}
			} else {
				SsoToken token = ssoutil.getSsoToken(req);
				if (token == null) {
					// token不存在，重定向到sso server
					// todo=addtoken表示请求的系统token不存在需要添加token
					String redirecturl = validateurl
							+ "?todo=addtoken&gotourl=" + encodedgotourl;
					log.debug("本系统不存在token，重定向到ssoserver获取ssotoken,返回页面："
							+ redirecturl);
					resp.sendRedirect(redirecturl);
				} else {
					log.debug("系统中存在token，验证token有效性");
					boolean b = false;
					if (checkLoginUrlSet.contains(reqPage)
							|| checkLoginUrlSet.contains("/*")) {
						log.debug("请求的页面uri:" + requri + "需要与ssoserver验证身份！");
						String url = validateurl + "?todo=checktoken&user="
								+ token.getLoginName();
						try {
							String returnstr = HttpCommunicate.httpRequest(url,
									token.getSessionId());
							if (returnstr.startsWith("checktoken:")) {
								returnstr = returnstr.substring(11, returnstr
										.length());
							}
							if (returnstr.equals("true")) {
								log.debug("与ssoserver验证身份成功！");
								// 验证是否调用过登录监听器
								if (!hasLoginListenerFired(req)) {
									// 调用登录监听
									LoginEvent e = new LoginEvent(req, resp,
											token.getLoginName(), token);
									fireLoginListener(e, req);
									// 重定向
									postRedirect(gotourl, req, resp);
								} else {
									b = true;
								}
							} else if (returnstr.equals("relogin")) {
								log.debug("与ssoserver验证身份 ， 新用户登陆，重新获得token！");
								String regeturl = validateurl
										+ "?todo=regettoken";
								String newtoken = HttpCommunicate.httpRequest(
										regeturl, token.getSessionId());
								if (newtoken.startsWith("regettoken:")) {
									log.debug("与ssoserver验证身份 ，重新获得token成功！");
									newtoken = newtoken.substring(11, newtoken
											.length());
									if (!newtoken.equals("false")) {
										SsoToken relogintoken = new SsoToken();
										relogintoken.parse(newtoken);
										ssoutil.saveSsoToken(req, resp,
												newtoken);
										log
												.debug("与ssoserver验证身份 ，新用户登录，调用登录监听器！");
										// 调用登录监听
										LoginEvent e = new LoginEvent(req,
												resp, relogintoken
														.getLoginName(), token);
										fireLoginListener(e, req);
										// 重定向
										postRedirect(gotourl, req, resp);
									} else {
										// 重新登录
										log.debug("ssoserver的session过期，需重新登陆！");
										String redirecturl = timeouturl
												+ "?todo=addtoken&gotourl="
												+ encodedgotourl;
										resp.sendRedirect(redirecturl);
									}
								} else {
									// 重新登录
									log.debug("重新获得token失败，需重新登陆！");
									String redirecturl = validateurl
											+ "?todo=addtoken&gotourl="
											+ encodedgotourl;
									resp.sendRedirect(redirecturl);
								}
							} else {
								log.debug("与ssoserver验证身份失败, 用户重新登录！");
								String redirecturl = validateurl
										+ "?todo=addtoken&gotourl="
										+ encodedgotourl;
								resp.sendRedirect(redirecturl);
							}

						} catch (IOException ex2) {
							log.error("连接到服务器失败!", ex2);
							resp.sendRedirect(loginurl);
						}
					} else {
						b = true;
						// 调用登录监听 2009-7-12 modify by wu.shang.zhan 增加调用 登陆监听器
						// 方法，适应使用cookie 来进行身份验证
						if (!hasLoginListenerFired(req)) {
							LoginEvent e = new LoginEvent(req, resp, token
									.getLoginName(), token);
							fireLoginListener(e, req);
						}
					}

					if (b) {
						// 验证token是否过期。
						long nowtime = System.currentTimeMillis();
						if (nowtime > token.getLimitTime()) {
							// token过期，重新获取token；
							log
									.error("Token过期，[当前日期:"
											+ new Timestamp(nowtime)
											+ "]  [有效期为:"
											+ new Timestamp(token
													.getLimitTime()) + "]");
							String url = validateurl + "?todo=regettoken";
							try {
								String newtoken = HttpCommunicate.httpRequest(
										url, token.getSessionId());
								if (newtoken.startsWith("regettoken:")) {
									log.debug("重新获得token成功！");
									newtoken = newtoken.substring(11, newtoken
											.length());
									if (!newtoken.equals("false")) {

										ssoutil.saveSsoToken(req, resp,
												newtoken);
										log.debug("重新获得并且保存token成功！");
										// log.debug("请求页面uri:" + requri +
										// "，通过验证，正常访问！");
										// doOtherFilter(servletRequest,
										// servletResponse);
										// filterChain.doFilter(req, resp);
									} else {
										// 重新登录
										log.debug("重新获得token失败，需重新登陆！");
										String redirecturl = timeouturl
												+ "?todo=addtoken&gotourl="
												+ encodedgotourl;
										resp.sendRedirect(redirecturl);
									}
								} else {
									// 重新登录
									log.debug("重新获得token失败，需重新登陆！");
									String redirecturl = timeouturl
											+ "?todo=addtoken&gotourl="
											+ encodedgotourl;
									resp.sendRedirect(redirecturl);
								}
							} catch (Exception ex1) {
								log.error("重新获取token出错！" + ex1.getMessage());
								String redirecturl = validateurl
										+ "?todo=addtoken&gotourl="
										+ encodedgotourl;
								resp.sendRedirect(redirecturl);
							}
						} else {
							log.debug("请求页面uri:" + requri + "，通过验证，正常访问！");
							doOtherFilter(servletRequest, servletResponse);
							filterChain.doFilter(req, resp);
						}
					}
				}
			}
		}
	}

	// post方式提交重定向。
	private void postRedirect(String requri, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>redirecting...</title></head>");
		out.println("<body onload=\"javascript:document.forms[0].submit();\">");
		out.println("<form action='" + requri + "' method='post'>");

		Map map = req.getParameterMap();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			if (!definedPara.contains(key)) {
				Object value = map.get(key);
				out.println("<input type='hidden' name='" + key.toString()
						+ "' value='" + value.toString() + "' />");
			}
		}
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	private List getLoginListenerList() {
		if (loginListenerList == null) {
			loginListenerList = new ArrayList();
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			for (int i = 0; i < loginListenerClassList.size(); i++) {
				String classname = loginListenerClassList.get(i).toString();
				try {
					Object obj = Class.forName(classname, false, cl)
							.newInstance();
					loginListenerList.add(obj);
				} catch (Exception e) {
					log.error("实例化监听器出错：classname:" + classname, e);
				}
			}
		}
		return loginListenerList;
	}

	private void fireLoginListener(LoginEvent e, HttpServletRequest req) {
		List listeners = getLoginListenerList();
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			LoginListener lis = (LoginListener) iter.next();
			log.debug("调用登录监听器：" + lis.getClass());
			lis.onLogin(e);
		}
		if (listeners.size() > 0) {
			req.getSession().setAttribute(listeners.get(0).toString(), "ok");
		}
	}

	private boolean hasLoginListenerFired(HttpServletRequest req) {
		List listeners = getLoginListenerList();
		if (listeners.size() > 0) {
			Object obj = req.getSession().getAttribute(
					listeners.get(0).toString());
			if (obj == null)
				return false;
		}
		return true;
	}

	private String redirector(String url) {
		if (redirect == null) {
			return url;
		}
		Enumeration enu = redirect.getKeys();
		while (enu.hasMoreElements()) {
			String type = (String) enu.nextElement();
			if (type.matches("urlmode.*")) {
				String findurl = redirect.getString(type);
				if (url.matches(findurl + ".*")) {
					String redirecturl = redirect.getString("redirect"
							+ type.substring(7));
					url = StringUtils.replace(url, findurl, redirecturl);
				}
			}
		}
		return url;
	}

	public void destroy() {
	}

}
