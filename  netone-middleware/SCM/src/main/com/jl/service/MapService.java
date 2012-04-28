/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��ͼҵ��
 * 
 * @author clx
 * @date 2010214
 * @history
 */
public interface MapService {

	/**
	 * ��ȡλ��
	 * 
	 * @param request
	 * @param response
	 * @return JSON
	 */
	public void selectXY(HttpServletRequest request,
			HttpServletResponse response)throws Exception;
	
	/**
	 * ��ȡ������λ��
	 * 
	 * @param request
	 * @param response
	 * @return JSON
	 */
	public void selectClientXY(HttpServletRequest request,
			HttpServletResponse response)throws Exception;

	public int modifyCoordinates(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public int modifyCoordinates1(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	public void loadAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
