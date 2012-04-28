/**
 * 
 */
package com.jl.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.entity.User;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.jl.common.SpringBeanUtil;
import com.jl.dao.LogDAO;
import com.jl.entity.Log;
import com.jl.service.BaseService;

/**
 * �쳣������־ҵ����
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 26, 2010 create by Don
 * @history
 */
public class ExceptionHandler extends BaseLogging implements ThrowsAdvice {

	private final Logger LOG = Logger.getLogger(ExceptionHandler.class);

	// private LogDAO logDAO;
	//
	// public void setLogDAO(LogDAO logDAO) {
	// this.logDAO = logDAO;
	// }
	// �и����� ���� ����ع� ֮��Ͳ����ٵ���DAO
	/**
	 * 
	 * @param _method
	 *            ����
	 * @param args1
	 *            ����
	 * @param arg2
	 *            Ŀ��
	 * @param e
	 *            �쳣
	 * @throws Throwable
	 */
	public void afterThrowing(Method _method, Object[] arg1, Object arg2,
			Throwable e) throws Throwable {

		HttpServletRequest request = (HttpServletRequest) arg1[0];

		HttpServletResponse response = null;
		if (arg1[1] != null) {
			response = (HttpServletResponse) arg1[1];
		}

		
		User user=BaseService.getOnlineUser(request);

		String method = _method.getName();
		String service = arg2.toString();
		Log log = new Log();
		String businessType = "";// ҵ������
		String id = "";
		// --------------------ҵ���ж�----------------------
		if (service.contains("IndentService")) {
			businessType = "����";
			id = request.getParameter("indentId");
		} else if (service.contains("PlanService")) {
			businessType = "�ƻ�";
			id = request.getParameter("planId");
		} else if (service.contains("PaymentService")) {
			businessType = "�������";
			id = request.getParameter("paymentId");
		} else if (service.contains("ManagePaymentService")) {
			businessType = "��ʶ�Ѽ���";
			id = request.getParameter("paymentId");
		} else if (service.contains("OtherPaymentService")) {
			businessType = "��ʶ����������";
			id = request.getParameter("paymentId");
		} else if (service.contains("IncentivePaymentCashService")) {
			businessType = "���Ͷ�����";
			id = request.getParameter("cashId");
		} else if (service.contains("ZPaymentService")) {
			businessType = "ת���¼���";
			id = request.getParameter("zpaymentId");
		}
		// -------------------- END ------------------------

		if (method.contains("save")) {
			if (StringUtils.isNotEmpty(id)) {
				log.setOperateId("�޸�" + businessType);
			} else {
				log.setOperateId("����" + businessType);
			}
		} else if (method.contains("delete")) {
			log.setOperateId("ɾ��" + businessType);
		} else {
			log.setOperateId("δ֪����(" + businessType + ")");
		}
		if (user != null) {
			log.setUserId(user.getDepartmentCode());
			log.setUserName(user.getDepartmentName());
		}
		JSONObject jsonObject = setRequestJSON(request);
		jsonObject.put("JavaMethod", StringUtils.substringBefore(service, "@")
				+ "." + method);
		log.setLogSeq(jsonObject.toString());
		log.setResultInfo("FAILED");
		log.setOperateTime(new Date());
		log.setUserIp(getIpAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setUserHost(request.getHeader("Host"));

		LOG.debug("�쳣����: " + arg2 + " ִ�� " + _method.getName() + " ʱ���쳣�׳�...."
				+ e);

		LogDAO logDAO = (LogDAO) SpringBeanUtil.getInstance().getBean("logDAO");
		logDAO.insertLog(log);

	}
}
