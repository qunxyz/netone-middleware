package oe.frame.web.struts;

import javax.servlet.http.HttpServletRequest;

public interface OeAction {

	/**
	 * ��ȫУ��
	 * 
	 * @param request
	 * @return
	 */
	boolean securityCheck(HttpServletRequest request);
	
	

}
