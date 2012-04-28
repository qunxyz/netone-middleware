package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.DepartmentService;

/**
 * ����/��˾����ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-11
 * @history
 */
public class DepartmentAction extends AbstractAction {

	// ������ҳ��
	public ActionForward onDepartmentManageView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onDepartmentManageView");
	}

	// ��ӻ��޸���Ϣ
	public void onSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.saveOrUpdateDepartment(request, response);
	}

	// ɾ����Ϣ
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.deleteDepartment(request, response);
	}

	// ��ѯ������
	public void onFindDeptTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.findDepartmentTree(request, response);
	}
	
	// ��ѯ������X
	public void onFindDeptTreeX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.findDepartmentTreeX(request, response);
	}

	// �༭ҳ��
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onEditView");
	}

	// ��ѡ
	public ActionForward onMultiSelectDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onMultiSelectDepartment");
	}

	// ��ѡ
	public ActionForward onSingleSelectDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onSingleSelectDepartment");
	}

	// ��ѡ2
	public ActionForward onSingleSelectDepartment2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onSingleSelectDepartment2");
	}

	// ���š���˾��ҳ��
	public ActionForward onDepartmentView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onDepartmentView");
	}

	// ���š���˾������Ϣ��ѯ
	public void onListDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.queryDepartment(request, response);
	}

	// ���� type=dArea ���򵼳�
	public void onExportDepartmentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.exportDepartmentInfo(request, response);
	}

	// �����������굼���ļ�
	public ActionForward onImportDAreaFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onImportDAreaFile");
	}

	// �����������굼��
	public void onImportDAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.importDAreaInfo(request, response);
	}

	// ��ʼ����չ����
	public void onInitExtData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.initExtData();
	}

	// ���û�������
	public void onFobidFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.fobidFunction(request, response);
	}
}
