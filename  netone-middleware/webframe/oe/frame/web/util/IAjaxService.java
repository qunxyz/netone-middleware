package oe.frame.web.util;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;

/**
 * ͨ��Ajax�������ӿ����
 * 
 * @author hls
 */
public interface IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap reqmap)
			throws Exception;

}
