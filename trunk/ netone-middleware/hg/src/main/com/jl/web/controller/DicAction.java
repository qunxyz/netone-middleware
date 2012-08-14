package com.jl.web.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.jl.service.ClientService;
import com.jl.service.DicService;
import com.jl.service.SystemConfigService;

public class DicAction extends AbstractAction {
	/**
	 * 主入口 进入系统配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onMainView");
	}

	/**
	 * 进入保存或修改编辑页面
	 */
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DicService configServer = (DicService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("dicService");
		configServer.loadInfo(request, response);
		return mapping.findForward("onEditView");
	}

	/**
	 * 添加或修改信息
	 */
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DicService configServer = (DicService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("dicService");
		configServer.saveOrUpdate(request, response);
		return null;
	}

	/**
	 * 查询数据集合，结果以列表形式展现，并分页<Br>
	 */
	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DicService configServer = (DicService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("dicService");
		configServer.queryInfo(request, response);
	}

	/**
	 * 删除部门
	 */
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DicService configServer = (DicService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("dicService");
		configServer.deleteInfo(request, response);
	}

	/**
	 * 取得系统配制信息 返回至 选择框
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void onGetSystemConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DicService configServer = (DicService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("dicService");
		configServer.loadSystemConfigToSelect(request, response);
	}
	
	/**
	 * 进入系统配制多选页面的主入口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onMSelectView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onMSelectView");
	}

	/**
	 * 进入查询的主面页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onQueryView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onQueryView");
	}
}
