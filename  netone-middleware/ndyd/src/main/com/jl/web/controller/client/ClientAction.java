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

	// �ͻ���Ϣ��ʾ��ҳ��
	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onMainView");
	}

	// ��ѯ���ݼ��ϣ�������б���ʽչ�֣�����ҳ
	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.queryClientInfo(request, response);
	}

	// // �ͻ���ִ
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
	// // �ͻ���ִ���
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
	 * ���뱣����޸ı༭ҳ�桾�����̡�
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
	 * ���뱣����޸ı༭ҳ�桾�����̡�
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
	// ��ӻ��޸���Ϣ
	public void onSaveOrUpdateClientInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.saveOrUpdateClientInfo(request, response);
	}

	// ȡ�ÿͻ���Ϣ �س�ѡ���¼�
	public void onGetClientInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.getClientInfo(request, response);
	}

	// ���ҿͻ�������
	public void onFindClientLevelTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.findClientLevelTree(request, response);
	}

	// �ͻ��������ù�����ҳ
	public ActionForward onLevelConfigMainView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onLevelConfigMainView");
	}

	// ���ҿͻ�����Ŀͻ�������Ϣ
	public void onLoadClientLevelOfRelation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.loadClientLevelOfRelation(request, response);
	}

	// �������ÿͻ�����
	public void onUpdateClientLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.updateClientLevel(request, response);
	}

	// �ͻ�����ڵ������ӡ��޸ġ�ɾ����
	public void onManageClientLevelNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.saveOrUpdateOrDeleteClientLevelNode(request, response);
	}

	// ���ü���Ŀͻ�ѡ��ҳ��
	public ActionForward onClientRelationSelectView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("onClientRelationSelectView");
	}

	// ����
	public void onExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClientService clientService = (ClientService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("clientService");
		clientService.export(request, response);
	}
}
