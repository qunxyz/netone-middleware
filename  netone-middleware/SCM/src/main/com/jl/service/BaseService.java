package com.jl.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.TimeUtil;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.Security3AIfc;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.dao.CommonDAO;
import com.jl.entity.Department;
import com.jl.entity.User;
import com.jl.service.impl.BussVar;
import com.jspsmart.upload.Request;

/**
 * 
 * 
 * <code> oe.gsxx.kf.service.BaseService.java
 * <p><li>业务对象抽象类</li></p>
 * <description></description>
 ×  author zhang.chao.yi
 *          mail: eduzcy@126.com
 *</code>
 * 
 * @version 1.0.0 date 2009-1-5 created by zhang.chao.yi（Jim）
 */
public abstract class BaseService {
	/** 每页的默认行量设定 */
	public static final Integer PAGE_SIZE = 15;
	/** 每页的默认行量设定 */
	public static final Integer PAGE_SIZE_MAX = 50;
	/** JSON字符串关键字 */
	private static final String RESULT = "total";
	/** JSON字符串关键字 */
	private static final String ROWS = "rows";

	private static final String CHATSET = "UTF-8";

	public static ResourceBundle config = ResourceBundle.getBundle("config",
			Locale.CHINESE);

	/**
	 * 构造返回的JSON数据，这样前台就可以通过Ext来解析数据并占现
	 * 
	 * @param result
	 * @param json
	 * @return
	 */
	public String buildJsonStr(int total, String json) {
		StringBuilder store = new StringBuilder();
		store.append("{");
		store.append(RESULT);
		store.append(" : ");
		store.append(total);
		store.append(",");
		store.append(ROWS);
		store.append(":[");
		store.append(json);
		store.append("]}");
		return store.toString();
	}

	/**
	 * 构造无分页JSON数据
	 * 
	 * @param json
	 * @return
	 */
	public String buildJsonStr(String json) {
		StringBuilder store = new StringBuilder();
		store.append("{");
		store.append(ROWS);
		store.append(":[");
		store.append(json);
		store.append("]}");
		return store.toString();
	}

	/**
	 * 回响JSon的数据结果
	 * 
	 * @param response
	 * @param str
	 */
	public void writeJsonStr(HttpServletResponse response, String str) {
		response.setContentType("text/json;charset=UTF-8");
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置JavaBean
	 * 
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	public void setJavaBean(HttpServletRequest request, Object obj) {
		Enumeration paramNameSet = request.getParameterNames();

		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					BeanUtils.setProperty(obj, paramName, values[0]);
				} else if (values != null && values.length > 1) {
					BeanUtils.setProperty(obj, paramName, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 把请求参数中的值设置到相应Obj属性
	 * 
	 * @param request
	 * @param obj
	 */
	public void setJavaBean2(Request request, Object obj) {
		Enumeration paramNameSet = request.getParameterNames();

		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					// values[0] = toUtf8String(values[0]);
					BeanUtils.setProperty(obj, paramName, values[0]);
				} else if (values != null && values.length > 1) {
					BeanUtils.setProperty(obj, paramName, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置HttpServletRequest编码 "UTF-8"
	 * 
	 * @param request
	 */
	public void setRequestCharacterEncoding(HttpServletRequest request) {
		try {
			request.setCharacterEncoding(this.CHATSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造操作是否成功 JSON字符串
	 * 
	 * @param isSuccess
	 * @return
	 */
	public String buildIsSuceessJson(Boolean isSuccess) {
		StringBuilder str = new StringBuilder();
		str.append("{success:");
		str.append(isSuccess);
		str.append("}");
		return str.toString();
	}

	protected void outXml(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/xml;charset=GBK");
			PrintWriter out = response.getWriter();
			out.print("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + str);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出文件名称
	 * 
	 * @param response
	 * @param request
	 */
	public void getReportExcelName(HttpServletResponse response,
			HttpServletRequest request, String fileName) {
		Long dateLong = System.currentTimeMillis();
		String fileSaveName = fileName + "_" + dateLong.toString();
		// response.addHeader("Content-Disposition", "attachment;filename="
		// + new String(fileSaveName.getBytes("GBK"), "ISO8859-1"));
		String type = request.getParameter("t");// t值0或null(EXCEL) or 1(JSP)
		if (!"1".equals(type)) {// EXCEL
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileSaveName + ".xls");
		}
	}

	/**
	 * 编号构造
	 * 
	 * @param commonDAO
	 * @param statement
	 * @param code
	 * @return payCode
	 * @throws Exception
	 */
	public String getCode(CommonDAO commonDAO, String statement, String code)
			throws Exception {
		/*-
		 * 构造单号 以时间的年月日后留3位
		 */
		String date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
		String _code = (String) commonDAO.findForObject(statement, code + date);
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 3, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1位补2个0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2位补1个0
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {// New
			_code = date + "001";
		}
		code += _code;
		return code;
	}

	/**
	 * 编号构造 4位流水
	 * 
	 * @param commonDAO
	 * @param statement
	 * @param code
	 * @return payCode
	 * @throws Exception
	 */
	public String getCode4(CommonDAO commonDAO, String statement, String code)
			throws Exception {
		/*-
		 * 构造单号 以时间的年月日后留3位
		 */
		String date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
		String _code = (String) commonDAO.findForObject(statement, code + date);
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 4, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1位补3个0
				queneNumber = "000" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2位补2个0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 3) {// 3位补1个0
				queneNumber = "0" + queneNumber;
			}
			_code = date + queneNumber;
		} else {// New
			_code = date + "0001";
		}
		code += _code;
		return code;
	}

	/**
	 * 编号构造 2位流水
	 * 
	 * @param commonDAO
	 * @param statement
	 * @param code
	 * @return payCode
	 * @throws Exception
	 */
	public String getUserCode(CommonDAO commonDAO, String statement,
			String usercode) throws Exception {
		String _code = (String) commonDAO.findForObject(statement, usercode);
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 2, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1位补1个0
				queneNumber = "0" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2位补0个0
				queneNumber = queneNumber;
			}
			_code += queneNumber;
		} else {// New
			_code += "01";
		}
		return _code;
	}

	/**
	 * 调用安全API
	 * 
	 * @param request
	 * @return
	 */
	public static Security3AIfc getSecurityAPI(HttpServletRequest request) {
		Security3AIfc security3AIfc = (Security3AIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"securityIfc");
		return security3AIfc;
	}

	/**
	 * 调用工作流API
	 * 
	 * @param request
	 * @return
	 */
	public static TWfConsoleIfc getWorkflowAPI(HttpServletRequest request) {
		TWfConsoleIfc twfIfc = (TWfConsoleIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"twfIfc");
		return twfIfc;
	}

	/**
	 * 获取登录者信息
	 * 
	 * User userId/userCode 帐号编码/userName 帐号名称/departmentId 隶属ID/departmentCode
	 * 隶属编码/departmentName 隶属名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static User getOnlineUser(HttpServletRequest request) {
		Security3AIfc security3AIfc = getSecurityAPI(request);
		User user = null;
		final String _session_var = BussVar.SESSION_USER;

		try {
			String sessionId = security3AIfc.onlineUserSessionId(request);
			if (sessionId == null) {// 不在线上或会话过期
				return null;
			}

			user = (User) WebCache.getCache(_session_var + "_" + sessionId);
			if (user != null) {
				return user;
			} else {
				user = new User();

				Client3A client = security3AIfc.onlineUser(request);
				user.setUserId(client.getClientId());// 该方法建议取消
				user.setUserCode(client.getClientId());
				user.setUserName(client.getName());
				user.setDepartmentId(client.getBussid());

				if ("adminx".equals(user.getUserId())) {
					user.setDepartmentCode("adminx");
					user.setDepartmentName("adminx");
					user.setParentDepartmentId("");
					user.setLevel("adminx");
				} else {
					Department department = (Department) getCommonDAO()
							.findForObject("Department.selectInfo",
									client.getBussid());
					Department departmentlevelrow = (Department) getCommonDAO()
							.findForObject(
									"Department.selectDepartmentlevelrow",
									client.getBussid());// 上级数据信息集合

					user.setDepartmentCode(department.getDepartmentCode());
					user.setDepartmentName(department.getDepartmentName());
					String pDeptId = department.getParentDepartmentId();
					pDeptId = pDeptId == null ? "" : pDeptId;
					user.setParentDepartmentId(pDeptId);
					user.setLevel(department.getLevel());
					user.setDepartmentLevelRow(departmentlevelrow);
					user.setParentDepartment(department.getParentDepartment());
				}
				// 过30分钟
				Calendar calendar = Calendar.getInstance();// 当前日期
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, 15);//
				WebCache.setCache(_session_var + "_" + sessionId, user,
						calendar.getTime());// 15分钟后失效
				request.getSession().setAttribute(_session_var, sessionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public static User getUserByCode(String clientid) {

		User user = new User();
		try {
			Client3A client = SecurityEntry.iv().loadUser(clientid);
			user.setUserId(client.getClientId());
			user.setUserCode(client.getClientId());
			user.setUserName(client.getName());
			user.setDepartmentId(client.getBussid());

			if ("adminx".equals(user.getUserId())) {
				Department department = new Department();
				department.setDepartmentCode("adminx");
				department.setDepartmentName("adminx");
				user.setLevel("adminx");
			} else {
				Department department = (Department) getCommonDAO()
						.findForObject("Department.selectInfo",
								client.getBussid());
				user.setDepartmentCode(department.getDepartmentCode());
				user.setDepartmentName(department.getDepartmentName());
				user.setLevel(department.getLevel());
			}

			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public static Department getDepartmentByBussId(String clientid) {
		Department department = new Department();
		try {
			if ("adminx".equals(clientid)) {
				department.setDepartmentCode("adminx");
				department.setDepartmentName("adminx");
			} else {
				department = (Department) getCommonDAO().findForObject(
						"Department.selectInfo", clientid);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}

	public static Department getDepartmentByCode(String clientCode) {
		Department department = new Department();
		try {
			if ("adminx".equals(clientCode)) {
				department.setDepartmentCode("adminx");
				department.setDepartmentName("adminx");
			} else {
				department = (Department) getCommonDAO().findForObject(
						"Department.selectInfoByCode", clientCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}

	
	/**
	 * UUID生产器
	 * 
	 * @param head
	 *            UUID头标志 加入头标志后返回总长度为 HEAD{X}32
	 * @param timehead
	 *            UUID头是否加入时间日期 加入时间日期后为 HEADYYYYMMDD{X}32
	 * @return
	 */
	public synchronized static String uuid(String head, boolean timehead) {
		String date = "";
		if (timehead) {
			date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
		}
		return head + date + UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static CommonDAO getCommonDAO() {
		return BaseDAO.getCommonDAO();
	}

}
