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
 * 异常捕获日志业务处理
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
	// 有个问题 出错 事务回滚 之后就不能再调用DAO
	/**
	 * 
	 * @param _method
	 *            方法
	 * @param args1
	 *            参数
	 * @param arg2
	 *            目标
	 * @param e
	 *            异常
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
		String businessType = "";// 业务类型
		String id = "";
		// --------------------业务判断----------------------
		if (service.contains("IndentService")) {
			businessType = "订单";
			id = request.getParameter("indentId");
		} else if (service.contains("PlanService")) {
			businessType = "计划";
			id = request.getParameter("planId");
		} else if (service.contains("PaymentService")) {
			businessType = "货款记账";
			id = request.getParameter("paymentId");
		} else if (service.contains("ManagePaymentService")) {
			businessType = "标识费记账";
			id = request.getParameter("paymentId");
		} else if (service.contains("OtherPaymentService")) {
			businessType = "标识费其它记账";
			id = request.getParameter("paymentId");
		} else if (service.contains("IncentivePaymentCashService")) {
			businessType = "差价投入记账";
			id = request.getParameter("cashId");
		} else if (service.contains("ZPaymentService")) {
			businessType = "转线下记账";
			id = request.getParameter("zpaymentId");
		}
		// -------------------- END ------------------------

		if (method.contains("save")) {
			if (StringUtils.isNotEmpty(id)) {
				log.setOperateId("修改" + businessType);
			} else {
				log.setOperateId("新增" + businessType);
			}
		} else if (method.contains("delete")) {
			log.setOperateId("删除" + businessType);
		} else {
			log.setOperateId("未知操作(" + businessType + ")");
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

		LOG.debug("异常捕获: " + arg2 + " 执行 " + _method.getName() + " 时有异常抛出...."
				+ e);

		LogDAO logDAO = (LogDAO) SpringBeanUtil.getInstance().getBean("logDAO");
		logDAO.insertLog(log);

	}
}
