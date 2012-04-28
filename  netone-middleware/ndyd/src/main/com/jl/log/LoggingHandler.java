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
 * ��־����ҵ����
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
		String businessType = "";// ҵ������
		// --------------------ҵ���ж�----------------------
		if (service.contains("FrameService")) {
			businessType = "��˾Ӧ��";
			if (method.contains("saveAndUpdate")) {
				businessType += "-����";
			} else if (method.contains("newEnd")) {
				businessType += "-�ύ";
			} else if (method.contains("auditEnd")) {
				businessType += "-�˻�/����";
			} else if (method.contains("assignEnd")) {
				businessType += "-ת��";
			} else if (method.contains("saveYijian")) {
				businessType += "-�������";
			} else if (method.contains("deleteByLogic")) {
				businessType += "-�鵵����";
			} else if (method.contains("delete")) {
				businessType += "-����";
			} else {
				businessType += "-��������";
			}
		} else if (service.contains("CensorShipService")) {
			businessType = "Ч�ܶ���";
			if (method.contains("save")) {
				businessType += "-����";
			} else if (method.contains("assign")) {
				businessType += "-����";
			} else if (method.contains("auditNext")) {
				businessType += "-�ύ";
			} else if (method.contains("audit")) {
				businessType += "-�������";
			} else if (method.contains("pack")) {
				businessType += "-�鵵";
			} else if (method.contains("saveyijian")) {
				businessType += "-�������";
			} else if (method.contains("delete")) {
				businessType += "-����";
			} else {
				businessType += "-��������";
			}
		} else if (service.contains("FileService")) {
			businessType = "�ļ�";
			if (method.contains("save")) {
				businessType += "-����";
			} else if (method.contains("deleteFileByUnidAndD_unid")) {
				businessType += "-ɾ��";
			} else if (method.contains("delete")) {
				businessType += "-ɾ��";
			} else {
				businessType += "-��������";
			}
		} else if (service.contains("DepartmentService")) {
			businessType = "����";
			String id = request.getParameter("departmentId");
			if (method.contains("save")) {
				if (StringUtils.isNotEmpty(id)) {
					businessType += "-����";
				} else {
					businessType += "-����";
				}
			} else if (method.contains("delete")) {
				businessType += "-ɾ��";
			}
		} else if (service.contains("UserService")) {
			businessType = "��Ա";
			String id = request.getParameter("userId");
			if (method.contains("save")) {
				if (StringUtils.isNotEmpty(id)) {
					businessType += "-����";
				} else {
					businessType += "-����";
				}
			} else if (method.contains("delete")) {
				businessType += "-ɾ��";
			}
		} else {
			businessType = "����";
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
