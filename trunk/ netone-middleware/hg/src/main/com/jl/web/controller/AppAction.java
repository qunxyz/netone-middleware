package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.AppService;

public class AppAction extends AbstractAction {

	// ���ݷ�����Ҳ�Ʒ��Ϣ
	public void onFindProductSetByPC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByPC(request, response);
	}

	// ��ѯ����������
	public void onFindCategoriesTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findCategoriesTree(request, response);
	}

	// ��ѯ��
	public void onFindPartTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findPartTree(request, response);
	}

	// ��ѯ�����빫˾������ϵ
	public void onFindPartAProductRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.savePartAProductRelation(request, response);
	}

	// ���������빫˾ҳ��
	public ActionForward onPartAProductMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");// Ӧ�÷�����Ŀ¼
		String forward = "/app/partAProductRelation.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// �������������빫˾
	public void onRelatePartAProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.relatePartAProduct(request, response);
	}

	// ͬ����Ʒ����
	public void syncProductData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.syncProductData(request, response);
	}

	// ���������Ʒ�����
	public ActionForward exportPartAndProductViewMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/partAndProductReport.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ���������Ʒ�����
	public void exportPartAndProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.exportPartAndProduct(request, response);
	}

	// HG����ѡ��
	public ActionForward clientMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/clientMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��Ʒѡ��
	public ActionForward productMultiSelectMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/app/productMultiSelect.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��Ʒ�б���ݸ�ID���˲�Ʒ����
	public void findProductSetByParentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppService service = (AppService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("appService");
		service.findProductSetByParentId(request, response);
	}

}
