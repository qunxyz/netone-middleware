package oe.frame.web.util;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;

/**
 * 通用Ajax服务程序接口设计
 * 
 * @author hls
 */
public interface IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap reqmap)
			throws Exception;

}
