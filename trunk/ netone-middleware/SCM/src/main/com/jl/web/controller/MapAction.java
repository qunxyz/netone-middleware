/**
 * 
 */
package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.service.MapService;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ��ͼ
 * 
 * @author clx
 * @date 2011214
 * @history
 */
public class MapAction extends AbstractAction {

	// �򿪵�ͼҳ��
	public ActionForward onQMap(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MapService mapservice = (MapService) WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("mapService");
		//mapservice.selectXY(request, response);
		return mapping.findForward("onQMap");
	}
	
	// ��λ
	public void onQMapXY(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MapService mapservice = (MapService) WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("mapService");
		mapservice.selectXY(request, response);
	}
	// �����̶�λ
	public void onQMapClientXY(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MapService mapservice = (MapService) WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("mapService");
		mapservice.selectClientXY(request, response);
	}
	
}
