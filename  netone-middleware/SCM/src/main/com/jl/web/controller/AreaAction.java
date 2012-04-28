package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.AreaService;


/**
 * 行政区域ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15 上午11:47:48
 * @history
 */
public class AreaAction extends AbstractAction {

	// 管理主页面
	public ActionForward onAreaManageView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onAreaManageView");
	}

	// 添加或修改信息
	public void onSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.saveOrUpdateArea(request, response);
	}

	// 删除信息
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.deleteArea(request, response);
	}

	// 查询树
	public void onFindAreaTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.findAreaTree(request, response);
	}

	// 查询区域与公司关联关系
	public void onFindAreaADepartmentRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.findAreaADepartmentRelation(request, response);
	}

	// 关联区域与公司页面
	public ActionForward onAreaADepartmentMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onAreaADepartmentMain");
	}

	// 编辑页面
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onEditView");
	}

	// 主页面
	public ActionForward onAreaView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// getClientPermissions(request, response);
		return mapping.findForward("onAreaView");
	}

	// 具体信息查询
	public void onListArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.queryArea(request, response);
	}

	// 导出
	public void onExportAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.exportAreaInfo(request, response);
	}

	// 行政区域坐标导入文件
	public ActionForward onImportAreaFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onImportAreaFile");
	}

	// 行政区域坐标导入
	public void onImportAreaInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.importAreaInfo(request, response);
	}

	// 关联行政区划与公司
	public void onRelateAreaADepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AreaService service = (AreaService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("areaService");
		service.relateAreaADepartment(request, response);
	}
}
