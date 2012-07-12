package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.UserService;

/**
 * 用户管理ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24
 * @history
 */
public class UserAction extends AbstractAction {

	// 管理主页面
	public ActionForward onUserView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onUserView");
	}

	public void onListUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.queryInfo(request, response);
	}
	
	public void onListUserX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.queryInfoX(request, response);
	}
	
	// 添加或修改信息
	public void onSaveOrUpdateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.saveOrUpdate(request, response);
	}

	// 删除信息
	public void onDeleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.deleteInfo(request, response);
	}

	// 编辑页面
	public ActionForward onEditUserView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService service = (UserService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("userService");
		service.loadInfo(request, response);
		getOnlineTreeNode(request);
		return mapping.findForward("onEditUserView");
	}

	// 多选
	public ActionForward onMultiSelectUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onMultiSelectUser");
	}

	// 单选
	public ActionForward onSingleSelectUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onSingleSelectUser");
	}
	
	// 多选
	public ActionForward onMultiSelectUserFDL(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onMultiSelectUserFDL");
	}
	
	public ActionForward onMultiSelectUserFDL2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getOnlineTreeNode(request);
		return mapping.findForward("onMultiSelectUserFDL2");
	}
	
	//多选用户 自定义部门树
	public ActionForward onMultiSelectUserX(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getTreeNode(request);
		return mapping.findForward("onMultiSelectUserX");
	}
	
	//多选用户 自定义部门树
	public ActionForward onSingleSelectUserX(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getTreeNode(request);
		return mapping.findForward("onSingleSelectUserX");
	}
	
}
