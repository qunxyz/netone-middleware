package com.jl.web.controller;

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
import oe.frame.web.util.WebTip;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.QueryKeyGenerate;
import com.jl.common.TimeUtil;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.Security3AIfc;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.dao.CommonDAO;
import com.jl.entity.Department;
import com.jl.entity.User;
import com.jl.service.BaseDAO;
import com.jl.service.BaseService;
import com.jl.service.DepartmentService;
import com.jl.service.impl.BussVar;
import com.jspsmart.upload.Request;

/**
 * 
 * 
 * <code> oe.gsxx.web.controller.AbstractAction.java
 * <p><li>����Action</li></p>
 * <description></description>
 ��  author zhang.chao.yi
 *          mail: eduzcy@126.com
 *</code>
 * 
 * @version 1.0.0 date 2009-1-14 created by zhang.chao.yi��Jim��
 */
public abstract class AbstractAction extends DispatchAction {
	/** ÿҳ��Ĭ�������趨 */
	public static final Integer PAGE_SIZE = 15;
	/** ÿҳ��Ĭ�������趨 */
	public static final Integer PAGE_SIZE_MAX = 50;
	/** JSON�ַ����ؼ��� */
	private static final String RESULT = "total";
	/** JSON�ַ����ؼ��� */
	private static final String ROWS = "rows";

	private static final String CHATSET = "UTF-8";

	public static ResourceBundle config = ResourceBundle.getBundle("config",
			Locale.CHINESE);

	/**
	 * ���췵�ص�JSON���ݣ�����ǰ̨�Ϳ���ͨ��Ext���������ݲ�ռ��
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
	 * �����޷�ҳJSON����
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
	 * ����JSon�����ݽ��
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
	 * ����JavaBean
	 * 
	 * @param request
	 *            �������
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
	 * ����������е�ֵ���õ���ӦObj����
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
	 * ����HttpServletRequest���� "UTF-8"
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
	 * ��������Ƿ�ɹ� JSON�ַ���
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
	 * ����ļ�����
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
		String type = request.getParameter("t");// tֵ0��null(EXCEL) or 1(JSP)
		if (!"1".equals(type)) {// EXCEL
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileSaveName + ".xls");
		}
	}

	/**
	 * ��Ź���
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
		 * ���쵥�� ��ʱ��������պ���3λ
		 */
		String date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
		String _code = (String) commonDAO.findForObject(statement, code + date);
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 3, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1λ��2��0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2λ��1��0
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
	 * ��Ź��� 4λ��ˮ
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
		 * ���쵥�� ��ʱ��������պ���3λ
		 */
		String date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
		String _code = (String) commonDAO.findForObject(statement, code + date);
		if (_code != null) {
			String queneNumber = StringUtils.substring(_code,
					_code.length() - 4, _code.length());
			Integer x = Integer.parseInt(queneNumber) + 1;
			queneNumber = x.toString();
			if (queneNumber.length() == 1) {// 1λ��3��0
				queneNumber = "000" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2λ��2��0
				queneNumber = "00" + queneNumber;
			} else if (queneNumber.length() == 3) {// 3λ��1��0
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
	 * ��Ź��� 2λ��ˮ
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
			if (queneNumber.length() == 1) {// 1λ��1��0
				queneNumber = "0" + queneNumber;
			} else if (queneNumber.length() == 2) {// 2λ��0��0
				queneNumber = queneNumber;
			}
			_code += queneNumber;
		} else {// New
			_code += "01";
		}
		return _code;
	}

	/**
	 * ���ð�ȫAPI
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
	 * ���ù�����API
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
	 * ��ȡ��¼����Ϣ
	 * 
	 * User userId/userCode �ʺű���/userName �ʺ�����/departmentId ����ID/departmentCode
	 * ��������/departmentName ��������
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
			if (sessionId == null) {// �������ϻ�Ự����
				return null;
			}

			user = (User) WebCache.getCache(_session_var + "_" + sessionId);
			if (user != null) {
				return user;
			} else {
				user = new User();

				Client3A client = security3AIfc.onlineUser(request);
				user.setUserId(client.getClientId());// �÷�������ȡ��
				user.setUserCode(client.getClientId());
				user.setUserName(client.getName());
				user.setDepartmentId(client.getBussid());

				if ("adminx".equals(user.getUserId())) {
					user.setDepartmentCode("adminx");
					user.setDepartmentName("adminx");
					user.setParentDepartmentId("");
					user.setLevel("adminx");
					user.setNLevelName("/");
				} else {
					Department department = (Department) getCommonDAO()
							.findForObject("Department.selectInfo",
									client.getBussid());
					Department departmentlevelrow = (Department) getCommonDAO()
							.findForObject(
									"Department.selectDepartmentlevelrow",
									client.getBussid());// �ϼ�������Ϣ����

					user.setDepartmentCode(department.getDepartmentCode());
					user.setDepartmentName(department.getDepartmentName());
					String pDeptId = department.getParentDepartmentId();
					pDeptId = pDeptId == null ? "" : pDeptId;
					user.setParentDepartmentId(pDeptId);
					user.setLevel(department.getLevel());
					user.setDepartmentLevelRow(departmentlevelrow);
					user.setParentDepartment(department.getParentDepartment());

					String nlevelName = department.getNLevelName();
					nlevelName = StringUtils.replace(nlevelName, "[0]", "");
					nlevelName = StringUtils.replace(nlevelName, "][", "/");
					nlevelName = StringUtils.replace(nlevelName, "[", "");
					nlevelName = StringUtils.replace(nlevelName, "]", "");
					user.setNLevelName(nlevelName);
				}
				// ��30����
				Calendar calendar = Calendar.getInstance();// ��ǰ����
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, 15);//
				WebCache.setCache(_session_var + "_" + sessionId, user,
						calendar.getTime());// 15���Ӻ�ʧЧ
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
	 * UUID������
	 * 
	 * @param head
	 *            UUIDͷ��־ ����ͷ��־�󷵻��ܳ���Ϊ HEAD{X}32
	 * @param timehead
	 *            UUIDͷ�Ƿ����ʱ������ ����ʱ�����ں�Ϊ HEADYYYYMMDD{X}32
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

	/**
	 * ������ҳ����<br>
	 * ��ע��һ������������������ʲô����ֻ���ṩ��ת��<br>
	 * �����ҳ��ҪԤ�ȴ���Щ���ݣ����������������
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
	 * ��ѯ���ݼ��ϣ�������б���ʽչ�֣�����ҳ<Br>
	 * ��ע:
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	}

	/**
	 * ���뱣����޸ı༭ҳ��<br>
	 * ��ע��������޸Ĳ������ȴ����ݿ��ѯ������Ϣ������ѡ����Ϣ���������ݷ��ص�<br>
	 * ҳ����ʾ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onEditView");
	}

	/**
	 * ɾ������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	}

	/**
	 * ��ӻ��޸Ĳ���<Br>
	 * ��ע��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	/**
	 * �ж�Ȩ��
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getClientPermissions(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = BaseService.getOnlineUser(request);
		if (user.getUserId() != null) {
			try {
				if ("adminx".equals(user.getUserId())) {
					request.setAttribute("adminx", "adminx");// ��������ԱȨ��
					// request.setAttribute("sgs", "adminx");// ��������ԱȨ��
					request.setAttribute("sgs", "sgs");// ��������ԱȨ��
					return;
				}
				DepartmentService service = (DepartmentService) WebApplicationContextUtils
						.getRequiredWebApplicationContext(
								servlet.getServletContext()).getBean(
								"departmentService");
				String bussType = service.checkBussType(user.getDepartmentId());
				if ("1".equals(bussType)) {// ��ԣʡ��˾
					request.setAttribute("sgs", "sgs");
				} else if ("2".equals(bussType) || "3".equals(bussType)) {// ��ԣӪ����
					request.setAttribute("sgs", "zgs");
				} else if ("4".equals(bussType)) {// ������
					request.setAttribute("sgs", "kh");
				}
				request.setAttribute("_APP_ID", user.getDepartmentId());
				request.setAttribute("_APP_CODE", user.getDepartmentCode());
				request.setAttribute("_APP_NAME", user.getDepartmentName());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ������ɵ�key ͨ����ǰ��½�� dxl
	 * 
	 * @param request
	 * @return
	 */
	public void generateKey(HttpServletRequest request) {

		User user = BaseService.getOnlineUser(request);
		request.setAttribute("queryKey", QueryKeyGenerate.generateKey(user
				.getUserCode()));
	}

	/**
	 * ��֤��ѯƾ֤����Ч�� dxl
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean checkKey(HttpServletRequest request,
			HttpServletResponse response) {
		// OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		// OnlineUser oluser = olmgr.getOnlineUser(request);
		// String loginName = oluser.getLoginname();
		String key = request.getParameter("queryKey");
		String type = request.getParameter("t");// tֵ0��null(EXCEL) or html(JSP)
		if ("".equals(type) || type == null) {
			WebTip.htmlInfo("�Ƿ�����!", true, response);
			return false;
		}
		if ("html".equals(type)) {// JSP
			return true;
		}
		// boolean result = QueryKeyGenerate.checkKey(loginName, key);
		// if (!result) {
		// WebTip.htmlInfo("�Ƿ�����!", true, response);
		// } else {
		// return true;
		// }
		return true;
	}

	/**
	 * ��ȡ������Ա����������Ŀ¼
	 * 
	 * @param request
	 */
	protected void getOnlineTreeNode(HttpServletRequest request) {
		User user = BaseService.getOnlineUser(request);
		String p = request.getParameter("p");
		String node = "0";
		String nodeName = "";
		String nodeCode = "";
		String parentNode = "";
		String special = request.getParameter("special");

		if ("adminx".equals(user.getLevel())) {
			//
		} else if ("s".equals(user.getLevel())) {// �ֿ�
			node = user.getDepartmentLevelRow().getDepartmentIds1();
			nodeCode = user.getDepartmentLevelRow().getDepartmentCodes1();
			nodeName = user.getDepartmentLevelRow().getDepartmentNames1();
			parentNode = "0";
		} else {
			if ("0".equals(p)) {// ����Ȩ��
				if (BussVar.BUSSTYPE_1.equals(user.getLevel())
						|| BussVar.BUSSTYPE_2.equals(user.getLevel())
						|| BussVar.BUSSTYPE_3.equals(user.getLevel())) {// ʡ��˾��Ӫ�������Ӫ����
					node = user.getDepartmentLevelRow().getDepartmentId1();
					nodeCode = user.getDepartmentLevelRow()
							.getDepartmentCode1();
					nodeName = user.getDepartmentLevelRow()
							.getDepartmentName1();
					parentNode = "0";
				} else {
					node = user.getDepartmentId();
					nodeCode = user.getDepartmentCode();
					nodeName = user.getDepartmentName();
					parentNode = user.getParentDepartmentId();
				}
			} else {
				node = user.getDepartmentId();
				nodeCode = user.getDepartmentCode();
				nodeName = user.getDepartmentName();
				parentNode = user.getParentDepartmentId();
			}
		}
		if ("1".equals(special)) {
			if (user.getParentDepartment() != null) {
				node = user.getParentDepartment().getDepartmentId();
				nodeCode = user.getParentDepartment().getDepartmentCode();
				nodeName = user.getParentDepartment().getDepartmentName();
				parentNode = "0";
			}
		}
		node = "".equals(node) ? "0" : node;
		parentNode = "".equals(parentNode) ? "0" : parentNode;
		request.setAttribute("node", node);
		request.setAttribute("nodeCode", nodeCode);
		request.setAttribute("nodeName", nodeName);
		request.setAttribute("parentNode", parentNode);
	}

	/**
	 * ��ȡ������Ŀ¼
	 * 
	 * @param request
	 * @throws Exception
	 */
	protected void getTreeNode(HttpServletRequest request) throws Exception {
		String node = request.getParameter("node");
		String hiddendept = request.getParameter("hiddendept");// �Ƿ�������֯������

		// if ("0".equals(node)) {
		// request.setAttribute("node", "0");
		// request.setAttribute("nodeCode", "0");
		// request.setAttribute("nodeName", "��֯����");
		// request.setAttribute("parentNode", "0");
		// } else {
		// Department department = (Department) getCommonDAO().findForObject(
		// "Department.selectDepartmentById", node);
		// String nodeName = department.getDepartmentName();
		// String nodeCode = department.getDepartmentCode();
		// String parentNode = department.getParentDepartmentId();
		//
		// node = "".equals(node) ? "0" : node;
		// parentNode = "".equals(parentNode) ? "0" : parentNode;
		// request.setAttribute("node", node);
		// request.setAttribute("nodeCode", nodeCode);
		// request.setAttribute("nodeName", nodeName);
		// request.setAttribute("parentNode", parentNode);
		// }
		node = "DEPT".equals(node) ? "0" : node;
		if ("0".equals(node)) {
			request.setAttribute("node", "0");
		} else {
			request.setAttribute("node", node);
		}
		request.setAttribute("node0", "0");
		request.setAttribute("nodeCode", "0");
		request.setAttribute("nodeName", "��֯����");
		request.setAttribute("parentNode", "0");

		if (StringUtils.isNotEmpty(hiddendept)) {
			request.setAttribute("hiddendept", hiddendept);
		} else {
			request.setAttribute("hiddendept", 0);
		}
	}

}
