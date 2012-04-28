package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.AreaService;


/**
 * ��������ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15 ����11:47:48
 * @history
 */
public class AreaAction extends AbstractAction {

	// ������ҳ��
	public ActionForward onAreaManageView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onAreaManageView");
	}

	// ��ӻ��޸���Ϣ
	public void onSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.saveOrUpdateArea(request, response);
	}

	// ɾ����Ϣ
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.deleteArea(request, response);
	}

	// ��ѯ��
	public void onFindAreaTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.findAreaTree(request, response);
	}

	// ��ѯ�����빫˾������ϵ
	public void onFindAreaADepartmentRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.findAreaADepartmentRelation(request, response);
	}

	// ���������빫˾ҳ��
	public ActionForward onAreaADepartmentMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onAreaADepartmentMain");
	}

	// �༭ҳ��
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onEditView");
	}

	// ��ҳ��
	public ActionForward onAreaView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onAreaView");
	}

	// ������Ϣ��ѯ
	public void onListArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.queryArea(request, response);
	}

	// ����
	public void onExportAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.exportAreaInfo(request, response);
	}

	// �����������굼���ļ�
	public ActionForward onImportAreaFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onImportAreaFile");
	}

	// �����������굼��
	public void onImportAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.importAreaInfo(request, response);
	}

	// �������������빫˾
	public void onRelateAreaADepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.relateAreaADepartment(request, response);
	}
}
