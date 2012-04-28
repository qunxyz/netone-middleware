package com.jl.web.controller.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.ClientService;
import com.jl.web.controller.AbstractAction;

public class ClientAction extends AbstractAction {

	// 客户信息显示主页面
	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onMainView");
	}

	// 查询数据集合，结果以列表形式展现，并分页
	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.queryClientInfo(request, response);
	}

	// // 客户回执
	// public ActionForward onClienttReceipt(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// return mapping.findForward("onClienttReceipt");
	// }
	//
	// public ActionForward onClientReceiptForm(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// OutStorageService outStorageService = (OutStorageService)
	// WebApplicationContextUtils
	// .getRequiredWebApplicationContext(servlet.getServletContext())
	// .getBean("outStorageService");
	// outStorageService.getClientReceiptFromInfo(request, response);
	// return mapping.findForward("onClientReceiptForm");
	// }
	//
	// // 客户回执审核
	// public ActionForward onAuditingClientReceiptForm(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// OutStorageService outStorageService = (OutStorageService)
	// WebApplicationContextUtils
	// .getRequiredWebApplicationContext(servlet.getServletContext())
	// .getBean("outStorageService");
	// outStorageService.getClientReceiptFromInfo(request, response);
	// return mapping.findForward("onAuditingClientReceiptForm");
	// }
	//
	// public ActionForward onQueryView(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// return mapping.findForward("onQueryView");
	// }

	/**
	 * 进入保存或修改编辑页面【经销商】
	 */
	public ActionForward onEditClientInfoView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.loadClientInfoById(request, response);
		return mapping.findForward("onEditClientInfoView");
	}
	/**
	 * 进入保存或修改编辑页面【分销商】
	 */
	public ActionForward onEditChildView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.loadClientInfoById(request, response);
		return mapping.findForward("onEditChildView");
	}
	// 添加或修改信息
	public void onSaveOrUpdateClientInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.saveOrUpdateClientInfo(request, response);
	}

	// 取得客户信息 回车选择事件
	public void onGetClientInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.getClientInfo(request, response);
	}

	// 查找客户级别树
	public void onFindClientLevelTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.findClientLevelTree(request, response);
	}

	// 客户级别配置管理主页
	public ActionForward onLevelConfigMainView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onLevelConfigMainView");
	}

	// 查找客户级别的客户关联信息
	public void onLoadClientLevelOfRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.loadClientLevelOfRelation(request, response);
	}

	// 批量设置客户级别
	public void onUpdateClientLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.updateClientLevel(request, response);
	}

	// 客户级别节点管理【添加、修改、删除】
	public void onManageClientLevelNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.saveOrUpdateOrDeleteClientLevelNode(request, response);
	}

	// 设置级别的客户选择页面
	public ActionForward onClientRelationSelectView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onClientRelationSelectView");
	}

	// 导出
	public void onExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.export(request, response);
	}
}
