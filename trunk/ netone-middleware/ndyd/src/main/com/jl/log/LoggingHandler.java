/**
 * 
 */
package com.jl.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.jl.dao.CommonDAO;
import com.jl.dao.LogDAO;
import com.jl.entity.Log;
import com.jl.entity.User;
import com.jl.service.BaseService;

/**
 * 日志拦截业务处理
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */

public class LoggingHandler extends BaseLogging implements AfterReturningAdvice {

	private final Logger LOG = Logger.getLogger(LoggingHandler.class);

	private LogDAO logDAO;

	private CommonDAO commonDAO;

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void afterReturning(Object object, Method _method, Object[] arg1,
			Object arg2) throws Throwable {
		HttpServletRequest request = (HttpServletRequest) arg1[0];

		String method = _method.getName();
		String service = arg2.toString();
		Log log = new Log();
		String businessType = "";// 业务类型
		// --------------------业务判断----------------------
		if (service.contains("FrameService")) {
			businessType = "公司应用";
			if (method.contains("saveAndUpdate")) {
				businessType += "-保存";
			} else if (method.contains("newEnd")) {
				businessType += "-提交";
			} else if (method.contains("auditEnd")) {
				businessType += "-退回/特送";
			} else if (method.contains("assignEnd")) {
				businessType += "-转办";
			} else if (method.contains("saveYijian")) {
				businessType += "-保存意见";
			} else if (method.contains("deleteByLogic")) {
				businessType += "-归档作废";
			} else if (method.contains("delete")) {
				businessType += "-作废";
			} else {
				businessType += "-其它操作";
			}
		} else if (service.contains("CensorShipService")) {
			businessType = "效能督办";
			if (method.contains("save")) {
				businessType += "-保存";
			} else if (method.contains("assign")) {
				businessType += "-交办";
			} else if (method.contains("auditNext")) {
				businessType += "-提交";
			} else if (method.contains("audit")) {
				businessType += "-办理完毕";
			} else if (method.contains("pack")) {
				businessType += "-归档";
			} else if (method.contains("saveyijian")) {
				businessType += "-保存意见";
			} else if (method.contains("delete")) {
				businessType += "-作废";
			} else {
				businessType += "-其它操作";
			}
		} else if (service.contains("FileService")) {
			businessType = "文件";
			if (method.contains("save")) {
				businessType += "-保存";
			} else if (method.contains("deleteFileByUnidAndD_unid")) {
				businessType += "-删除";
			} else if (method.contains("delete")) {
				businessType += "-删除";
			} else {
				businessType += "-其它操作";
			}
		} else if (service.contains("DepartmentService")) {
			businessType = "部门";
			String id = request.getParameter("departmentId");
			if (method.contains("save")) {
				if (StringUtils.isNotEmpty(id)) {
					businessType += "-保存";
				} else {
					businessType += "-新增";
				}
			} else if (method.contains("delete")) {
				businessType += "-删除";
			}
		} else if (service.contains("UserService")) {
			businessType = "人员";
			String id = request.getParameter("userId");
			if (method.contains("save")) {
				if (StringUtils.isNotEmpty(id)) {
					businessType += "-保存";
				} else {
					businessType += "-新增";
				}
			} else if (method.contains("delete")) {
				businessType += "-删除";
			}
		} else {
			businessType = "其它";
		}
		log.setOperateId(businessType);
		JSONObject jsonObject = setRequestEncodeJSON(request);
		// -------------------- END ------------------------
		User oluser = BaseService.getOnlineUser(request);
		if (oluser != null) {
			log.setUserId(oluser.getUserId());
			log.setUserName(oluser.getUserName());
		}

		jsonObject.put("JavaMethod", StringUtils.substringBefore(service, "@")
				+ "." + method);
		log.setLogSeq(jsonObject.toString());
		log.setResultInfo("SUCCESS");
		log.setOperateTime(new Date());
		log.setUserIp(getIpAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setUserHost(request.getHeader("Host"));
		LOG.debug("Method:" + _method.getName());
		LOG.debug("service:" + service);
		LOG.debug("SEQ:" + setRequestJSON(request));

		logDAO.insertLog(log);
	}

}
