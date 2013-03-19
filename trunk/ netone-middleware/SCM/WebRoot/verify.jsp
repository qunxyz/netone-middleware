<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.*" %>

<%
		String textCert; // ֤���
		String textOriginData;// ԭ��(�����)
		String textSignData; // ǩ��
	
		HttpSession httpSession = request.getSession();
		textCert = request.getParameter("textCert");
		textOriginData =(String) httpSession.getAttribute("RANDOM");
		textSignData = request.getParameter("textSignData");
		
		//out.println("11111");
		//out.println(textCert);
		//out.println(textSignData);
		
		fjca.FJCAApps ca = new fjca.FJCAApps();
		// �籣4000
		ca.setErrorBase(4000);
		// Windows ��֤������IP
		ca.setServerURL("192.168.11.209:7010");
		byte[] bySubjectCN = ca.GetSubjectCN(textCert);
		if (bySubjectCN == null) {
			// �׳�֤�����
			RequestDispatcher rd = request.getRequestDispatcher("errorbd.jsp?style=9999");
			rd.forward(request, response);
			return;
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