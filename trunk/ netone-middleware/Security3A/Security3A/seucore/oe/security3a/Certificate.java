package oe.security3a;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

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

	// 提取证书信息
	public void init(HttpServletRequest request) throws Exception {
		X509Certificate cert = null;
		try {
			X509Certificate[] certArray = null;
			certArray = (X509Certificate[]) request
					.getAttribute("javax.servlet.request.X509Certificate");
			cert = certArray[0];
			String dn = cert.getSubjectDN().toString();
			System.out.println("dn:"+dn);
			this.cn = dn.substring(dn.lastIndexOf('=') + 1, dn.length());
			System.out.println("cn:"+cn);
			this.serialNumber = cert.getSerialNumber().toString();
			System.out.println("serialNumber:"+serialNumber);
			if(this.cn!=null&&this.cn.length()!=11){
				this.cn = StringUtils.substringBetween(dn, "CN=",",");
				System.out.println("newCN:"+this.cn);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 返回证书的序列号
	public String getSerialNumber() {
		return this.serialNumber;
	}

	// 返回证书的通用名
	public String getCn() {
		return this.cn;
	}

}