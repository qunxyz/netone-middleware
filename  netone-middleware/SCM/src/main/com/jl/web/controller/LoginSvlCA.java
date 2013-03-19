package com.jl.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import com.jl.common.workflow.DbTools;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.util.CookiesOpe;

public class LoginSvlCA extends HttpServlet {
	  private String gotourlX = "";
   
      private int sessiontimeout;
	  public static final ResourceBundle messages = ResourceBundle.getBundle(
			    "ssoserver", Locale.CHINESE);
	  public void init() throws ServletException
	  {
	    try
	    {
	      this.gotourlX = messages.getString("gotourl");
	      int t = Integer.parseInt(messages.getString("sessionTimeOut"));
	      this.sessiontimeout = (t * 60*60);
	    }
	    catch (Exception e) {
	      System.err.println("lose imagecode config");
	    }
	  }
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String otherinfo = request.getParameter("otherinfo");
		String sql = "SELECT usercode FROM netone.t_cs_user WHERE otherinfo = '1234567890'";
		List list = DbTools.queryData(sql);
		Map m = (Map) list.get(0);
		System.out.println(m.get("usercode"));
		response.setContentType("text/html; charset=GBK");
		request.getSession().setAttribute("oajump",
				request.getParameter("oajump"));

		String username =(String)m.get("usercode");
	

		StringBuffer errormsg = new StringBuffer();

		OnlineUser oluser = new OnlineUser();
		String reqip = request.getRemoteAddr();
		String reqhost = request.getRemoteHost();

		try {
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
					if ((errormsg.toString() != null)
							&& (oe.security3a.sso.LoginInfo._ERROR_4[0]
									.equals(errormsg))) {
						request.setAttribute("errormsg", errormsg.toString());
						request.getRequestDispatcher("sso/login.jsp").forward(
								request, response);
						return;
					}

					String loginseq = UUID.randomUUID().toString();

					oluser.setUserid(username);
					oluser.setLoginname(username);
					oluser.setLoginseq(loginseq);
					oluser.setLoginip(reqip);
					oluser.setLoginhost(reqhost);
					oluser.setBelongto("0000");

					request.getSession().setAttribute("login", oluser);

					String[] cookies = { username, username,
							"", reqhost };
					CookiesOpe.addCookiesx(cookies, response);

					CupmRmi cupmrmi = (CupmRmi) RmiEntry.iv("cupm");

					String gotourl = request.getParameter("gotourl");
					if ((this.gotourlX != null) && (!this.gotourlX.equals(""))) {
						gotourl = this.gotourlX;
					}

					if ((gotourl == null) || (gotourl.equals(""))) {
						request.getRequestDispatcher("sso/index.jsp").forward(
								request, response);
					} else
						response.sendRedirect(gotourl);

					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(this.sessiontimeout);

					cupm.log("��½", reqip, username, "�ɹ�", "");
				
		}catch(Exception e){
			e.printStackTrace();
		}
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//	  
		//		String textCert; // ֤���
		//		String textOriginData;// ԭ��(�����)
		//		String textSignData; // ǩ��
		//	
		//		HttpSession httpSession = request.getSession();
		//		textCert = request.getParameter("textCert");
		//		textOriginData =(String) httpSession.getAttribute("RANDOM");
		//		textSignData = request.getParameter("textSignData");
		//		
		//		//out.println("11111");
		//		//out.println(textCert);
		//		//out.println(textSignData);
		//		
		//		fjca.FJCAApps ca = new fjca.FJCAApps();
		//		// �籣4000
		//		ca.setErrorBase(4000);
		//		// Windows ��֤������IP
		//		ca.setServerURL("192.168.11.209:7010");
		//		byte[] bySubjectCN = ca.GetSubjectCN(textCert);
		//		if (bySubjectCN == null) {
		//			// �׳�֤�����
		//			RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp?style=9999");
		//			rd.forward(request, response);
		//			return;
		//		}
		//
		//		String strSubjectCN = new String(bySubjectCN);
		//		// ȡ֤�����к�
		//		String strSerial = ca.getSerialFromCert(textCert);
		//
		//		// ����ҵ���������֤��HTTP�����µ�����֤����֤��
		//		String strRet = ca.FJCA_VerifyQY(textOriginData, textSignData, textCert);
		//
		//		int retCode = ca.getLastError();
		//		if (retCode == 0) {
		//			String[] retArr = strRet.split(",");
		//
		//			// ������ҵ֤����Ч����ֹʱ��,�����ֹʱ��,�Ƿ��ѵ���
		//
		//			// �ж�֤����Ч��
		//			String sCertDate = retArr[0];
		//			java.util.Date certDate = new java.util.Date();
		//			java.util.Date today = new java.util.Date();
		//			try {
		//				if (sCertDate != null) {
		//					if (!sCertDate.equals("")) {
		//						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		//								"yyyy/MM/dd hh:mm:ss");
		//						certDate = sdf.parse(sCertDate);
		//						long oneDay = 86400000;
		//						if (certDate.compareTo(today) < 0) {
		//							httpSession.setAttribute("style", "4012");
		//							RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
		//							rd.forward(request, response);
		//							return;
		//						} else {
		//							java.util.Date certvDate = new java.util.Date(certDate.getTime() - 90 * oneDay);
		//							java.util.Calendar c = java.util.Calendar.getInstance();
		//							c.setTime(certDate);
		//							java.util.Calendar c1 = java.util.Calendar.getInstance();
		//							c1.setTime(sdf.parse(sdf.format(today)));
		//
		//							
		//							int remDate = (int) ((c.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000));		
		//							String lasttime = null;
		//							String msg = null;
		//							msg = "ע�⣺��������֤����Ч�ڽ���"
		//									+ remDate
		//									+ "�����ڣ�������ʱ��ϵ����CA�ͷ������ӳ�֤����Ч�ڡ�"
		//									+ "������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.";
		//							if (remDate == 0) {
		//								lasttime = sCertDate.substring(11, sCertDate
		//										.length());
		//								msg = "ע�⣺��������֤����Ч�ڽ���"
		//										+ lasttime
		//										+ "����ڣ�������ʱ��ϵ����CA�ͷ������ӳ�֤����Ч�ڡ�"
		//										+ "������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.";
		//							}
		//							if (certvDate.compareTo(today) < 0) {
		//								httpSession.setAttribute("msg", msg);
		//							}
		//
		//						}
		//					}
		//				}
		//
		//				// �жϷ�����Ч��
		//				String sSerDate = retArr[1];
		//				java.util.Date serDate = new java.util.Date();
		//				// java.util.Date today = new java.util.Date();
		//				if (sSerDate != null) {
		//					if (!sSerDate.equals("")) {
		//						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		//								"yyyy/MM/dd hh:mm:ss");
		//						serDate = sdf.parse(sSerDate + " 23:59:59");
		//						long oneDay = 86400000;
		//						if (serDate.compareTo(today) < 0) {
		//							httpSession.setAttribute("style", "4020");
		//							RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
		//							rd.forward(request, response);
		//							return;
		//
		//						} else {
		//							java.util.Date certvDate = new java.util.Date(serDate.getTime() - 30 * oneDay);
		//							//int remDate = DateUtil.diffDate(serDate, sdf.parse(sdf.format(today))) + 1;
		//							
		//							java.util.Calendar c = java.util.Calendar.getInstance();
		//							c.setTime(serDate);
		//							java.util.Calendar c1 = java.util.Calendar.getInstance();
		//							c1.setTime(sdf.parse(sdf.format(today)));
		//							int remDate = (int) ((c.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000))+ 1;
		//							
		//							if (certvDate.compareTo(today) < 0) {
		//								httpSession
		//										.setAttribute(
		//												"msg1",
		//												"ע�⣺֤�������"
		//														+ remDate
		//														+ "���ڼ������ڣ��뼰ʱ�ӳ�֤����Ч�������ޡ�"
		//														+ "<br>������޷���������ϵ�����籣�����̻��߲���ͻ���������:0591-968806.");
		//							}
		//						}
		//					}
		//				}
		//
		//				String unitNo = retArr[2];
		//				if(unitNo==null||unitNo.equals("")){
		//					
		//					RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp?style=9999");
		//					rd.forward(request, response);
		//				}else{		
		//					String sql = "SELECT usercode FROM t_cs_user WHERE otherinfo = '" + unitNo + "'";
		//					List list=DbTools.queryData(sql);
		//					for (Object object : list) {
		//						Map m = (Map)object;
		//						System.out.println(m.get("usercode"));
		//					}
		//				}
		//			} catch (Exception e) {
		//				// TODO: handle exception
		//			}
		//		} else {
		//			httpSession.setAttribute("style", String.valueOf(retCode));
		//			RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
		//			rd.forward(request, response);
		//		}
		//		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}