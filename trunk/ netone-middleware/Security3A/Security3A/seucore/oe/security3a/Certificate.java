package oe.security3a;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class Certificate {
	// private
	private String serialNumber = null;
	private String cn = null;

	public Certificate() {

	}

	// ��ȡ֤����Ϣ
	public void init(HttpServletRequest request) throws Exception {
		X509Certificate cert = null;
		try {
			X509Certificate[] certArray = null;
			certArray = (X509Certificate[]) request
					.getAttribute("javax.servlet.request.X509Certificate");
			cert = certArray[0];
			String dn = cert.getSubjectDN().toString();
			this.cn = dn.substring(dn.lastIndexOf('=') + 1, dn.length());
			this.serialNumber = cert.getSerialNumber().toString();
		} catch (Exception e) {
			throw e;
		}
	}

	// ����֤������к�
	public String getSerialNumber() {
		return this.serialNumber;
	}

	// ����֤���ͨ����
	public String getCn() {
		return this.cn;
	}

}