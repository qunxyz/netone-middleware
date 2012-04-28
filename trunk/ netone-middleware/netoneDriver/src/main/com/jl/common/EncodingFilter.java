package com.jl.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <code> oe.gsxx.common.EncodingFilter.java
 * <p><li>�ַ����ݹ���(�û�����ַ���������)</li></p>
 * <description></description>
 ��  author 
 *</code>
 * 
 * @version 1.0.0 date 2009-1-14 created by zhang.chao.yi��Jim��
 */
public final class EncodingFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = true;

	public EncodingFilter() {
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/**
	 * �����ַ�������˲���
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (ignore || (request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(selectEncoding(request));
		}

		chain.doFilter(request, response);
	}

	/**
	 * ��ʼ������
	 * 
	 * @param filterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");

		String value = filterConfig.getInitParameter("ignore");

		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")
				|| value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}
	}

	/**
	 * ѡ�����
	 * 
	 * @param request
	 * @return
	 */
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * ��������
	 * 
	 * @param filterConfig
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
