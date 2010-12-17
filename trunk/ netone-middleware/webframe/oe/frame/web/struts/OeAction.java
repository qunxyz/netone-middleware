package oe.frame.web.struts;

import javax.servlet.http.HttpServletRequest;

public interface OeAction {

	/**
	 * 安全校验
	 * 
	 * @param request
	 * @return
	 */
	boolean securityCheck(HttpServletRequest request);
	
	

}
