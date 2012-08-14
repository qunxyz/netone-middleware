package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.AppService;

public class AppAction extends AbstractAction {

	// 根据分类查找产品信息
	public void onFindProductSetByPC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByPC(request, response);
	}

	// 查询分类树数据
	public void onFindCategoriesTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findCategoriesTree(request, response);
	}

	// 查询树
	public void onFindPartTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findPartTree(request, response);
	}

	// 查询区域与公司关联关系
	public void onFindPartAProductRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findPartAProductRelation(request, response);
	}

	// 关联区域与公司页面
	public ActionForward onPartAProductMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		String forward = "/app/partAProductRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 关联行政区划与公司
	public void onRelatePartAProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.relatePartAProduct(request, response);
	}

}
