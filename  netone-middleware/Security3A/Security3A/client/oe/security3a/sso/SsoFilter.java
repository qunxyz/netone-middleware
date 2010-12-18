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
 * �û���¼��֤��SSO��������������֤�û��Ƿ��¼��֧��sso ��
 * <p>
 * ֧���û���¼�ļ���������ʵ��webӦ�����û���¼ʱ��Ҫ����һЩ���飬���磺д�û���¼
 * ��Ϣ�����û���Ȩ�ȡ�webӦ�ñ�д�ļ�����ͨ�����ò���listener���ݵ�Filter�У�������� ���ö���(",")�ָ
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
	 * Filter��ʼ���������õ�¼�������Ȳ�����
	 * 
	 * @param filterConfig
	 *            FilterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		String realpath = filterConfig.getServletContext().getRealPath("");
		String info = StringUtils.substringAfterLast(realpath, File.separator);
		System.out.println("........... ����SecurityGate  OK [" + info + "]");

		try {
			ssoutil = new SsoUtil();

			// ����ϵͳĬ�ϵĲ���
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
				// ��ʱ���� ��̬htmlҳ��Ĺ��˴���
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

			// ��Ϊ��ͬϵͳʱ����ѡ���������¼���
			// ���õ�¼����
			String listeners = filterConfig.getInitParameter("LoginListener");
			if (listeners != null) {
				String[] lis = listeners.split(",");
				for (int i = 0; i < lis.length; i++) {
					log.debug("��ӵ�¼��������" + lis[i]);
					loginListenerClassList.add(lis[i]);
				}
			}
			// ����ϵͳ�ĵ�¼ҳ�������������ε���ҳ
			String LoginUrls = filterConfig.getInitParameter("LoginUrls");
			if (LoginUrls != null) {
				String[] urls = LoginUrls.split(",");
				loginUrlSet.addAll(Arrays.asList(urls));
			}
			// ������Ҫ��֤��¼�û��ĵ�ַ��
			String checkUrls = filterConfig.getInitParameter("CheckLoginUrls");
			if (checkUrls != null) {
				String[] urls = checkUrls.split(",");
				checkLoginUrlSet.addAll(Arrays.asList(urls));
			}
			// ���ý���Ҫ���˵ĵ�ַ��
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
	 * �û���¼��֤��
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

		// ֻ����jsp��html��do��servlet������.
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

		// log.debug( "�����URI:" + requri ) ;

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

		// log.debug( "�����ҳ�棺" + reqPage ) ;

		boolean isdofilter = true;
		if (filterUrlSet.size() != 0) {
			if (!filterUrlSet.contains(reqPage)) {
				isdofilter = false;
				filterChain.doFilter(req, resp);
			}
		} else {
			if (abortUrlSet.contains(reqPage) || reqPage.startsWith("/_seu9")) {
				log.debug("�����ҳ��uri��" + requri + "������.");
				isdofilter = false;
				filterChain.doFilter(req, resp);
			}
			if (loginUrlSet.contains(reqPage)) {
				log.debug("�����ҳ��uri��" + requri + "���ض��򵽵�¼ҳ��");
				isdofilter = false;
				resp.sendRedirect(loginurl);
			}
		}
		if (isdofilter) {

			String addToken = req.getParameter("addtoken");
			if (addToken != null && !addToken.equals("")) {
				log.debug("ִ�����token��ϵͳ.");
				SsoToken st = new SsoToken();
				st.parse(addToken);
				// ��֤token����ʵ�ԣ� todo=checktoken��ʾΪ������Ҫ��֤token
				String url = validateurl + "?todo=checktoken&user="
						+ st.getLoginName();
				try {
					String returnstr = HttpCommunicate.httpRequest(url, st
							.getSessionId());
					if (returnstr.startsWith("checktoken:")) {
						returnstr = returnstr.substring(11, returnstr.length());
					}
					if (returnstr.equals("true")) {
						// ��֤�ɹ���
						log.debug("ִ�����token��ϵͳ: ��֤token�ɹ���");
						st.setFirstToken(false);
						st.setTokenValidated(true);
						ssoutil.saveSsoToken(req, resp, st.tokenToString());
						resp.setContentType(CONTENT_TYPE);

						// ���õ�¼����
						LoginEvent e = new LoginEvent(req, resp, st
								.getLoginName(), st);
						fireLoginListener(e, req);

						// �ض���
						postRedirect(gotourl, req, resp);

					} else {
						// ��֤ʧ�ܣ��ض��򵽵�¼ҳ��
						log.debug("ִ�����token��ϵͳ: token��֤ʧ�ܣ�");
						resp.sendRedirect(loginurl);
					}
				} catch (Exception ex) {
					log.error("��֤token����!", ex);
					resp.sendRedirect(loginurl);
				}
			} else {
				SsoToken token = ssoutil.getSsoToken(req);
				if (token == null) {
					// token�����ڣ��ض���sso server
					// todo=addtoken��ʾ�����ϵͳtoken��������Ҫ���token
					String redirecturl = validateurl
							+ "?todo=addtoken&gotourl=" + encodedgotourl;
					log.debug("��ϵͳ������token���ض���ssoserver��ȡssotoken,����ҳ�棺"
							+ redirecturl);
					resp.sendRedirect(redirecturl);
				} else {
					log.debug("ϵͳ�д���token����֤token��Ч��");
					boolean b = false;
					if (checkLoginUrlSet.contains(reqPage)
							|| checkLoginUrlSet.contains("/*")) {
						log.debug("�����ҳ��uri:" + requri + "��Ҫ��ssoserver��֤��ݣ�");
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
								log.debug("��ssoserver��֤��ݳɹ���");
								// ��֤�Ƿ���ù���¼������
								if (!hasLoginListenerFired(req)) {
									// ���õ�¼����
									LoginEvent e = new LoginEvent(req, resp,
											token.getLoginName(), token);
									fireLoginListener(e, req);
									// �ض���
									postRedirect(gotourl, req, resp);
								} else {
									b = true;
								}
							} else if (returnstr.equals("relogin")) {
								log.debug("��ssoserver��֤��� �� ���û���½�����»��token��");
								String regeturl = validateurl
										+ "?todo=regettoken";
								String newtoken = HttpCommunicate.httpRequest(
										regeturl, token.getSessionId());
								if (newtoken.startsWith("regettoken:")) {
									log.debug("��ssoserver��֤��� �����»��token�ɹ���");
									newtoken = newtoken.substring(11, newtoken
											.length());
									if (!newtoken.equals("false")) {
										SsoToken relogintoken = new SsoToken();
										relogintoken.parse(newtoken);
										ssoutil.saveSsoToken(req, resp,
												newtoken);
										log
												.debug("��ssoserver��֤��� �����û���¼�����õ�¼��������");
										// ���õ�¼����
										LoginEvent e = new LoginEvent(req,
												resp, relogintoken
														.getLoginName(), token);
										fireLoginListener(e, req);
										// �ض���
										postRedirect(gotourl, req, resp);
									} else {
										// ���µ�¼
										log.debug("ssoserver��session���ڣ������µ�½��");
										String redirecturl = timeouturl
												+ "?todo=addtoken&gotourl="
												+ encodedgotourl;
										resp.sendRedirect(redirecturl);
									}
								} else {
									// ���µ�¼
									log.debug("���»��tokenʧ�ܣ������µ�½��");
									String redirecturl = validateurl
											+ "?todo=addtoken&gotourl="
											+ encodedgotourl;
									resp.sendRedirect(redirecturl);
								}
							} else {
								log.debug("��ssoserver��֤���ʧ��, �û����µ�¼��");
								String redirecturl = validateurl
										+ "?todo=addtoken&gotourl="
										+ encodedgotourl;
								resp.sendRedirect(redirecturl);
							}

						} catch (IOException ex2) {
							log.error("���ӵ�������ʧ��!", ex2);
							resp.sendRedirect(loginurl);
						}
					} else {
						b = true;
						// ���õ�¼���� 2009-7-12 modify by wu.shang.zhan ���ӵ��� ��½������
						// ��������Ӧʹ��cookie �����������֤
						if (!hasLoginListenerFired(req)) {
							LoginEvent e = new LoginEvent(req, resp, token
									.getLoginName(), token);
							fireLoginListener(e, req);
						}
					}

					if (b) {
						// ��֤token�Ƿ���ڡ�
						long nowtime = System.currentTimeMillis();
						if (nowtime > token.getLimitTime()) {
							// token���ڣ����»�ȡtoken��
							log
									.error("Token���ڣ�[��ǰ����:"
											+ new Timestamp(nowtime)
											+ "]  [��Ч��Ϊ:"
											+ new Timestamp(token
													.getLimitTime()) + "]");
							String url = validateurl + "?todo=regettoken";
							try {
								String newtoken = HttpCommunicate.httpRequest(
										url, token.getSessionId());
								if (newtoken.startsWith("regettoken:")) {
									log.debug("���»��token�ɹ���");
									newtoken = newtoken.substring(11, newtoken
											.length());
									if (!newtoken.equals("false")) {

										ssoutil.saveSsoToken(req, resp,
												newtoken);
										log.debug("���»�ò��ұ���token�ɹ���");
										// log.debug("����ҳ��uri:" + requri +
										// "��ͨ����֤���������ʣ�");
										// doOtherFilter(servletRequest,
										// servletResponse);
										// filterChain.doFilter(req, resp);
									} else {
										// ���µ�¼
										log.debug("���»��tokenʧ�ܣ������µ�½��");
										String redirecturl = timeouturl
												+ "?todo=addtoken&gotourl="
												+ encodedgotourl;
										resp.sendRedirect(redirecturl);
									}
								} else {
									// ���µ�¼
									log.debug("���»��tokenʧ�ܣ������µ�½��");
									String redirecturl = timeouturl
											+ "?todo=addtoken&gotourl="
											+ encodedgotourl;
									resp.sendRedirect(redirecturl);
								}
							} catch (Exception ex1) {
								log.error("���»�ȡtoken����" + ex1.getMessage());
								String redirecturl = validateurl
										+ "?todo=addtoken&gotourl="
										+ encodedgotourl;
								resp.sendRedirect(redirecturl);
							}
						} else {
							log.debug("����ҳ��uri:" + requri + "��ͨ����֤���������ʣ�");
							doOtherFilter(servletRequest, servletResponse);
							filterChain.doFilter(req, resp);
						}
					}
				}
			}
		}
	}

	// post��ʽ�ύ�ض���
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
					log.error("ʵ��������������classname:" + classname, e);
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
			log.debug("���õ�¼��������" + lis.getClass());
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
