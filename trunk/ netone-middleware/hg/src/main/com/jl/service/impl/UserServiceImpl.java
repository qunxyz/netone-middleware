/**
 * 
 */
package com.jl.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.MD5Util;
import com.jl.common.SpringBeanUtilHg;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.UserService;

/**
 * �û�ҵ�����ʵ��
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 ����02:48:50
 * @history
 */
public class UserServiceImpl extends BaseService implements UserService {

	/** ��־ */
	private final Logger log = Logger.getLogger(UserServiceImpl.class);

	private final boolean enableSyncComponent = true;// �Ƿ�ͬ�����

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	private CommonDAO getHgDAO() {
		CommonDAO dao = (CommonDAO) SpringBeanUtilHg.getInstance().getBean(
				"commonDAO");
		return dao;
	}

	public void queryInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map conditionMap = new HashMap();
		String condition = request.getParameter("condition");
		String special = request.getParameter("special");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		User userX = getOnlineUser(request);
		conditionMap.put("permission", userX.getDepartmentId());
		if ("adminx".equals(userX.getUserCode())) {// ����Ա
			conditionMap.put("permission", "0");
		}
		if ("1".equals(special)) {
			conditionMap.put("permission", "0");
		}
		if (paramJson.containsKey("departmentId")) {
			conditionMap.put("departmentId", paramJson
					.getString("departmentId"));
			conditionMap.put("departmentId_", paramJson
					.getString("departmentId"));
		}
		if (paramJson.containsKey("userCode")) {
			conditionMap.put("userCode", paramJson.getString("userCode"));
		}

		if (paramJson.containsKey("list")) {
			String list = paramJson.getString("list");
			if (StringUtils.isNotEmpty(list)) {
				conditionMap.put("list", list);
				if ("all".equals(list)) {
					conditionMap.put("departmentId", "");
				}
			}
		}
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ��ʼ����
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage("User.queryUserPageCount",
					"User.queryUserResultPage", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map user = (Map) iterator.next();

				String types = (String) user.get("types");
				if (BussVar.BUSSTYPE_4.equals(types)) {
					user.put("types", "�ͻ�");
				} else if (BussVar.BUSSTYPE_X.equals(types)) {
					user.put("types", "��˾");
				} else {
					user.put("types", "δ֪");
				}

				String jsonStr = JSONUtil2.fromBean(user, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("������!", e);
		}
	}

	public void queryInfoX(HttpServletRequest request,
			HttpServletResponse response) {
		Map conditionMap = new HashMap();
		String includedept = request.getParameter("includedept");
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		if (paramJson.containsKey("departmentId")) {
			conditionMap.put("departmentId", paramJson
					.getString("departmentId"));
			conditionMap.put("departmentId_", paramJson
					.getString("departmentId"));
		}
		if (paramJson.containsKey("userCode")) {
			conditionMap.put("userCode", paramJson.getString("userCode"));
		}

		if (paramJson.containsKey("list")) {
			String list = paramJson.getString("list");
			if (StringUtils.isNotEmpty(list)) {
				conditionMap.put("list", list);
				if ("all".equals(list)) {
					conditionMap.put("departmentId", "");
				}
			}
		}
		if (StringUtils.isNotEmpty(includedept)) {
			conditionMap.put("includedept", includedept.trim());
		} else {
			conditionMap.put("includedept", 0);
		}
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ��ʼ����
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage("User.queryUserPageCountX",
					"User.queryUserResultPageX", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map user = (Map) iterator.next();

				String jsonStr = JSONUtil2.fromBean(user, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("������!", e);
		}
	}

	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		String userId = request.getParameter("userId");
		User user = getBean(request);
		JSONObject json = new JSONObject();
		try {
			Map extAttribute = new HashMap();
			extAttribute.put("orders", user.getOrders());
			if (StringUtils.isNotEmpty(userId)) {// ����
				user = (User) commonDAO.update("User.updateUser", user);

				if (enableSyncComponent) {

					getSecurityAPI(request).editAccount(user.getDepartmentId(),
							user.getUserCode(), user.getUserName(),
							extAttribute);
				}
				json.put("tip", "����ɹ�!");
			} else {// ����
				if (findUserCodeIsExist(request)) {
					json.put("tip", "�û������Ѵ���!");
				} else {
					user.setStatus("1");
					user = (User) commonDAO.insert("User.insertUser", user);
					if (enableSyncComponent) {
						getSecurityAPI(request).newAccount(
								user.getDepartmentId(), user.getUserCode(),
								user.getUserName(), extAttribute);
					}
					json.put("tip", "����ɹ�!");
				}
			}
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "����ʧ��!");
			log.error("����ʧ��!", e);
			throw new RuntimeException("����ʧ��!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void loadInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		try {
			Map user = (Map) commonDAO.findForObject("User.loadUserById",
					userId);
			request.setAttribute("user", user);
		} catch (Exception e) {
			log.error("��������ʧ��!", e);
		}
	}

	public void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String userIdStr = request.getParameter("userId") == null ? ""
				: request.getParameter("userId").trim();
		String userCodeStr = request.getParameter("userCode") == null ? ""
				: request.getParameter("userCode").trim();

		try {
			String[] userIdStr1 = userIdStr.split(",");
			List deleteList = new ArrayList();
			for (int i = 0; i < userIdStr1.length; i++) {
				deleteList.add(userIdStr1[i]);
			}
			commonDAO.deleteBatch("User.deleteUser", deleteList);
			String[] userCodeStr1 = userCodeStr.split(",");
			for (int i = 0; i < userCodeStr1.length; i++) {
				if (enableSyncComponent) {
					getSecurityAPI(request).deleteAccount(userCodeStr1[i]);
				}
			}
			json.put("tip", "ɾ���ɹ�!");
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "ɾ��ʧ��!");
			log.error("ɾ��������Ϣ����!", e);
			throw new RuntimeException("ɾ��ʧ��!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void syncUserFromK3(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map map = new HashMap();
			List list = (List) commonDAO.select("Part.selectAllDept", map);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String departmentCode = (String) object.get("departmentCode");
				String departmentId = (String) object.get("departmentId");

				Map map2 = new HashMap();
				map2.put("deptcode", departmentCode);
				List listx = (List) getHgDAO().select("HG.selectEmp", map2);
				for (Iterator iterator2 = listx.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();

					Integer FGender = (Integer) object2.get("FGender");
					String FPhone = (String) object2.get("FPhone");
					String FShortNumber = (String) object2.get("FShortNumber");
					String FNumber = (String) object2.get("FNumber");
					Integer FParentID = (Integer) object2.get("FParentID");
					Integer FDepartmentID = (Integer) object2
							.get("FDepartmentID");
					Integer orders = (Integer) object2.get("F_102");
					String FName = (String) object2.get("FName");
					Integer FDeleted = (Integer) object2.get("FDeleted");

					User user = new User();
					user.setUserCode(FShortNumber);
					user.setOrders(orders);
					user.setUserName(FName);
					user.setDepartmentId(departmentId);
					user.setPhone(FPhone);
					if (StringUtils.isNotEmpty(FPhone)) {
						user.setEmail(FPhone + "@139.com");
					}
					user.setTypes("x");
					user.setAccounttypes(1);
					/**
					 * �� 1068 Ů 1069
					 */
					user.setSex("" + ((FGender == 1068) ? 0 : 1));

					Integer exists = (Integer) commonDAO.findForObject(
							"User.findUserCodeIsExist", FShortNumber);

					Map extAttribute = new HashMap();
					extAttribute.put("orders", user.getOrders());
					if (StringUtils.isNotEmpty(FPhone)) {
						extAttribute.put("mail", FPhone + "@139.com");
						extAttribute.put("phoneNO", FPhone);
					}
					if (exists > 0) {// ����
						user.setStatus((FDeleted == 0) ? "1" : "0");
						String userid = (String) commonDAO.findForObject(
								"Part.findUserInfoByCode", user.getUserCode());
						user.setUserId(userid);
						user = (User) commonDAO.update("User.updateUser", user);
						user = (User) commonDAO.update("User.updateUserStatus",
								user);
						extAttribute.remove("mail");// ����������
						if (enableSyncComponent) {
							getSecurityAPI(request).editAccount(
									user.getDepartmentId(), user.getUserCode(),
									user.getUserName(), extAttribute);
							if (FDeleted == 0) {
								getSecurityAPI(request).recoveryAccount(
										user.getUserCode());
							} else {
								getSecurityAPI(request).fobidAccount(
										user.getUserCode());
							}
						}
					} else {// ����
						user.setStatus((FDeleted == 0) ? "1" : "0");
						user = (User) commonDAO.insert("User.insertUser", user);
						user = (User) commonDAO.update("User.updateUserStatus",
								user);
						if (enableSyncComponent) {
							getSecurityAPI(request).newAccount(
									user.getDepartmentId(), user.getUserCode(),
									user.getUserName(), extAttribute);
							if (FDeleted == 0) {
								getSecurityAPI(request).recoveryAccount(
										user.getUserCode());
							} else {
								getSecurityAPI(request).fobidAccount(
										user.getUserCode());
							}
						}
					}

				}
			}

		} catch (Exception e) {
			log.error("ͬ������", e);
		}

	}

	// ----Private Methods

	private User getBean(HttpServletRequest request) {
		User user = new User();
		super.setJavaBean(request, user);
		String password = request.getParameter("password");

		String zw = request.getParameter("zw");// ְ��
		user.setZw(zw);
		int accounttypes = Integer.parseInt(request
				.getParameter("accounttypes"));// �ʺ�����
		user.setAccounttypes(accounttypes);
		String ltime = request.getParameter("leaveTime");// �뿪ʱ��
		String btime = request.getParameter("backTime");// ��ȥʱ��
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date leavetime = null;
		Date backtime = null;
		if (StringUtils.isNotEmpty(ltime)) {
			try {
				leavetime = format.parse(ltime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setLeavetime(leavetime);
		}

		if (StringUtils.isNotEmpty(btime)) {
			try {
				backtime = format.parse(btime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setBacktime(backtime);
		}

		String dlr = request.getParameter("departmentNamex");// ������
		user.setDlr(dlr);
		String notice_ = request.getParameter("notice");
		if (StringUtils.isNotEmpty(notice_) && !"undefined".equals(notice_)) {
			int notice = Integer.parseInt(notice_);// ֪ͨ��ʽ
			user.setNotice(notice);
		}
		if (StringUtils.isNotEmpty(password)) {
			password = MD5Util.getMD5String(password);
			user.setPassword(password);
		}
		user.setCreatedDate(new Date());
		return user;
	}

	/**
	 * �ж��û������Ƿ��Ѵ���
	 * 
	 * @param request
	 * @return
	 */
	private Boolean findUserCodeIsExist(HttpServletRequest request)
			throws Exception {
		Boolean flag = true;
		String userCode = request.getParameter("userCode");
		Integer result = (Integer) commonDAO.findForObject(
				"User.findUserCodeIsExist", userCode);
		if (result == 0) {
			flag = false;
		} else {
			flag = true;
		}

		return flag;
	}

}
