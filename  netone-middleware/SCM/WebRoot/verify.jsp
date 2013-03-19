<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.*" %>

<%
		String textCert; // 证书号
		String textOriginData;// 原文(随机数)
		String textSignData; // 签名
	
		HttpSession httpSession = request.getSession();
		textCert = request.getParameter("textCert");
		textOriginData =(String) httpSession.getAttribute("RANDOM");
		textSignData = request.getParameter("textSignData");
		
		//out.println("11111");
		//out.println(textCert);
		//out.println(textSignData);
		
		fjca.FJCAApps ca = new fjca.FJCAApps();
		// 社保4000
		ca.setErrorBase(4000);
		// Windows 验证服务器IP
		ca.setServerURL("192.168.11.209:7010");
		byte[] bySubjectCN = ca.GetSubjectCN(textCert);
		if (bySubjectCN == null) {
			// 抛出证书错误
			RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp?style=9999");
			rd.forward(request, response);
			return;
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
							httpSession.setAttribute("style", "4012");
							RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
							rd.forward(request, response);
							return;
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
							httpSession.setAttribute("style", "4020");
							RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
							rd.forward(request, response);
							return;

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
					
					RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp?style=9999");
					rd.forward(request, response);
				}else{
					request.setAttribute("username",strSubjectCN);
					request.setAttribute("password",strSerial);
					request.getRequestDispatcher("/LoginSvlCa").forward(request,response);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			httpSession.setAttribute("style", String.valueOf(retCode));
			RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp");
			rd.forward(request, response);
		}
		

%>