package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.DepartmentService;

/**
 * 部门/公司管理ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-11
 * @history
 */
public class DepartmentAction extends AbstractAction {

	// 管理主页面
	public ActionForward onDepartmentManageView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onDepartmentManageView");
	}

	// 添加或修改信息
	public void onSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.saveOrUpdateDepartment(request, response);
	}

	// 删除信息
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.deleteDepartment(request, response);
	}

	// 查询部门树
	public void onFindDeptTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.findDepartmentTree(request, response);
	}
	
	// 查询部门树X
	public void onFindDeptTreeX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.findDepartmentTreeX(request, response);
	}

	// 编辑页面
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onEditView");
	}

	// 多选
	public ActionForward onMultiSelectDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onMultiSelectDepartment");
	}

	// 单选
	public ActionForward onSingleSelectDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onSingleSelectDepartment");
	}

	// 单选2
	public ActionForward onSingleSelectDepartment2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onSingleSelectDepartment2");
	}

	// 部门、公司主页面
	public ActionForward onDepartmentView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		getClientPermissions(request, response);
		return mapping.findForward("onDepartmentView");
	}

	// 部门、公司具体信息查询
	public void onListDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.queryDepartment(request, response);
	}

	// 导出 type=dArea 区域导出
	public void onExportDepartmentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.exportDepartmentInfo(request, response);
	}

	// 行政区域坐标导入文件
	public ActionForward onImportDAreaFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onImportDAreaFile");
	}

	// 行政区域坐标导入
	public void onImportDAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.importDAreaInfo(request, response);
	}

	// 初始化扩展数据
	public void onInitExtData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.initExtData();
	}

	// 禁用或开启功能
	public void onFobidFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentService service = (DepartmentService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("departmentService");
		service.fobidFunction(request, response);
	}
}
