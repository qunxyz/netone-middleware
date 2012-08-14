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
	 * ����� ����ϵͳ����
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
	 * ���뱣����޸ı༭ҳ��
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
	 * ��ӻ��޸���Ϣ
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
	 * ��ѯ���ݼ��ϣ�������б���ʽչ�֣�����ҳ<Br>
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
	 * ɾ������
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
	 * ȡ��ϵͳ������Ϣ ������ ѡ���
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
	 * ����ϵͳ���ƶ�ѡҳ��������
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
	 * �����ѯ������ҳ
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
