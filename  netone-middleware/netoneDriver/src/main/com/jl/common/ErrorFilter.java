package com.jl.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ErrorFilter implements Filter {

	// �Ƿ���JSON,�����Ҫ����AJAX�ύ��������ʾ
	// ��Ҫ����request.setAttribute("ErrorJson", "Yes");// Json������ʾ
	private String ISJSON = "No";

	private FilterConfig filterConfig;

	public ErrorFilter() {
	}

	/**
	 * init
	 * 
	 * @param filterConfig
	 *            FilterConfig
	 * @throws ServletException
	 * @todo Implement this javax.servlet.Filter method
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * doFilter
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @param filterChain
	 *            FilterChain
	 * @throws IOException
	 * @throws ServletException
	 * @todo Implement this javax.servlet.Filter method
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				servletRequest.setAttribute("message", e.getMessage());
			} catch (Exception ex) {
				servletRequest.setAttribute("message", "δ֪��������ϵϵͳ����Ա��");
			} finally {
				ISJSON = (String) servletRequest.getAttribute("ErrorJson");

				if ("Yes".equalsIgnoreCase(ISJSON)) {// JSON������ʾ����
					// Do Nothing
					// AJAX �ύ��������ʾ
				} else {// ���ش���ҳ��error.jsp
					this.filterConfig.getServletContext().getRequestDispatcher(
							"/error.jsp").forward(servletRequest,
							servletResponse);
				}
			}
		}
	}

	/**
	 * destroy
	 * 
	 * @todo Implement this javax.servlet.Filter method
	 */
	public void destroy() {
	}
}
