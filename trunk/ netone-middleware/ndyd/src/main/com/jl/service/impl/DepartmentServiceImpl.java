/**
 * 
 */
package com.jl.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.serialize.dao.PageInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.CommonUploadUtil;
import com.jl.common.JSONUtil2;
import com.jl.common.JxlUtilsTemplate;
import com.jl.common.app.SpringBeanUtilExam;
import com.jl.dao.CommonDAO;
import com.jl.entity.Client;
import com.jl.entity.Department;
import com.jl.entity.DepartmentLevel;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.DepartmentService;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * ����/��˾����ʵ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-10 ����02:48:01
 * @history
 */
public class DepartmentServiceImpl extends BaseService implements
		DepartmentService {

	/** ��־ */
	private final Logger log = Logger.getLogger(DepartmentServiceImpl.class);

	private final boolean enableSyncComponent = true;// �Ƿ�ͬ�����

	private final boolean enableSyncExam = ("yes".equalsIgnoreCase(config
			.getString("syncexam"))) ? true : false;

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public DepartmentServiceImpl() {

	}

	public void queryDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		String level = request.getParameter("l");
		String parentDepartmentId = request.getParameter("pid");
		String code = request.getParameter("code");
		String list = request.getParameter("list");
		Map conditionMap = new HashMap();
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ��ʼ����
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		if (StringUtils.isNotEmpty(level)) {
			conditionMap.put("level", level.trim());
		}
		if (StringUtils.isNotEmpty(code)) {
			conditionMap.put("code", code.trim());
		}
		if (StringUtils.isNotEmpty(parentDepartmentId)) {
			conditionMap.put("departmentId", parentDepartmentId.trim());
		}
		if (StringUtils.isNotEmpty(list)) {
			conditionMap.put("list", list.trim());
		}
		conditionMap.put("forPage", 1);
		try {
			obj = commonDAO.selectForPage(
					"Department.queryDepartmentDetailInfoForPageCount",
					"Department.queryDepartmentDetailInfoForPage",
					conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map info = (Map) iterator.next();

				String jsonStr = JSONUtil2.fromBean(info).toString();
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

	public void saveOrUpdateDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		Department dept = this.getBean(request);// ��ȡʵ��
		String departmentId = request.getParameter("departmentId");
		// ҵ������ ��Ŀ¼:0 ����:-1 ʡ��˾:1 ��Ӫ����:2 Ӫ����:3 ������:4 ������:5 �ֿ�:s
		String bussType = request.getParameter("bussType");
		String batchCountx = request.getParameter("batchCount");
		List<Department> levelList = new ArrayList<Department>();
		int nlevel = 0;// ��ǰ�����ڼ���
		try {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			if (oluser == null) {
				json.put("tip", "��¼��ʱ!");
				json.put("error", "yes");
			}

			log.debug("����:" + bussType);

			// �������Ƿ��ظ�
			if (!BussVar.BUSSTYPE_4.equals(bussType)
					&& !BussVar.BUSSTYPE_5.equals(bussType)) {
				Integer isexist = (Integer) commonDAO.findForObject(
						"Department.findCodeIsExist", dept);
				if (isexist > 0) {
					json.put("tip", "��������ظ�!");
					json.put("error", "yes");
					return;
				}
			}

			if (StringUtils.isNotEmpty(departmentId)) {// ����Id ���ж��� �����޸Ļ��ǲ���
				// ����,���޸� Id ������ �򱣴�\
				String originalParentDepartmentId = request
						.getParameter("originalParentDepartmentId");
				originalParentDepartmentId = "0"
						.equals(originalParentDepartmentId) ? ""
						: originalParentDepartmentId;
				dept.setDepartmentId(departmentId);
				dept = (Department) commonDAO.update(
						"Department.updateDepartment", dept);
				String deptId = dept.getDepartmentId();
				String deptCode = dept.getDepartmentCode();
				String deptName = dept.getDepartmentName();
				String pDeptId = dept.getParentDepartmentId();
				String pDeptId_ = dept.getParentDepartmentId() == null ? getSecurityAPI(request).DEFAULT_DEPT_APP
						: dept.getParentDepartmentId();
				String deptId_ = deptId == null ? getSecurityAPI(request).DEFAULT_DEPT_APP
						: deptId;

				// ɾ������Ӫ�����������̡���������Ϣ

				// ���±��ڵ�
				nlevel = findTreeRelation(commonDAO, levelList, deptId,
						++nlevel);
				// ���±�����֯��������
				buildTreeRelation(commonDAO, levelList, deptId, nlevel,
						bussType, getIsBuildTreeLevel(bussType));
				// ͬ���ֿ����
				// buildStorageTreeRelation(commonDAO, dept);
				// ���¹�˾��������
				updateDepartmentlevelrow(commonDAO, dept);
				log.debug("�޸ĵ�ǰ�ڵ�ID--" + deptId);

				if (!originalParentDepartmentId.equals(pDeptId == null ? ""
						: pDeptId)) {// ������ڸ���Ŀ¼���¼���ϵ
					// �����������нڵ�
					buildChildTreeRelation(commonDAO, deptId);
				}
				// ͬ���ͻ���Ϣ
				// commonDAO.update("Department.syncUpdateInfo", dept);
				// ͬ���ֿ���Ϣ
				// commonDAO.update("Department.syncUpdateStorageInfo", dept);
				// // ͬ����Ʒ�۸񼶱����ṹ
				// Integer nodeIsExist = (Integer) commonDAO.findForObject(
				// "Client.findClientLevelNodeIsExist", deptId);
				// ClientPriceLevel clientLevel = new ClientPriceLevel();
				// clientLevel.setLevelCode(deptId);
				// clientLevel.setLevelName(deptName);
				// if (StringUtils.isEmpty(pDeptId)) {
				// clientLevel.setParentLevelCode(null);
				// } else {
				// clientLevel.setParentLevelCode(pDeptId);
				// }
				// if (nodeIsExist > 0) {
				// commonDAO.update("Client.modifyClientLevelNode",
				// clientLevel);
				// } else {
				// commonDAO.insert("Client.appendClientLevelNode",
				// clientLevel);
				// }
				// // ������Ʒ�۸񵽿ͻ� Ŀǰ����������
				// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// �ͻ�
				// Map mapxx = new HashMap();
				// mapxx.put("levelCode", deptId);
				// mapxx.put("clientId", deptId.split(","));
				// commonDAO.update("Client.updateClientLevel", mapxx);
				// }
				// ͬ�����Ŀ¼API
				Map extAttribute = new HashMap();
				extAttribute.put("orders", dept.getOrders());
				if (enableSyncComponent)
					getSecurityAPI(request).editOrganization(pDeptId_, deptId_,
							deptName, extAttribute);

				if (enableSyncExam)
					SpringBeanUtilExam.getInstance().updateStudentGroup(dept);

				// ͬ���ʺ���Ϣ
				// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// �ͻ�
				// // if (findUserCodeIsExist(deptCode)) {
				// // Department deptClone = (Department) BeanUtils
				// // .cloneBean(dept);
				// // deptClone.setDepartmentCode(null);// ������
				// // commonDAO.update("Department.syncUpdateUserInfo",
				// // deptClone);
				// // if (enableSyncComponent) {
				// // // getSecurityAPI(request).editAccount(deptId_,
				// // // deptCode, deptName, null);
				// // }
				// // } else {
				// commonDAO.update("Department.syncUpdateUserInfo", dept);
				// if (enableSyncComponent) {
				// getSecurityAPI(request).editAccount(deptId_, deptCode,
				// deptName, null);
				// }
				// // }
				// }

			} else {
				int batchCount = Integer.parseInt(batchCountx);
				for (int i = 1; i <= batchCount; i++) {
					Department _dept = (Department) commonDAO.insert(
							"Department.insertDepartment", dept);
					log.debug("����ID--" + _dept.getDepartmentId());

					String deptId = _dept.getDepartmentId();
					String deptName = _dept.getDepartmentName();
					String deptCode = _dept.getDepartmentCode();
					String pDeptId = _dept.getParentDepartmentId();
					String deptId_ = deptId == null ? getSecurityAPI(request).DEFAULT_DEPT_APP
							: deptId;
					String pDeptId_ = pDeptId == null ? getSecurityAPI(request).DEFAULT_DEPT_APP
							: pDeptId;
					nlevel = findTreeRelation(commonDAO, levelList, deptId,
							++nlevel);
					// ��ʼ����˾��������
					initDepartmentlevelrow(commonDAO, deptId);
					// ���±�����֯��������
					buildTreeRelation(commonDAO, levelList, deptId, nlevel,
							bussType, getIsBuildTreeLevel(bussType));
					// ͬ���ֿ����
					// buildStorageTreeRelation(commonDAO, _dept);
					// ���¹�˾��������
					updateDepartmentlevelrow(commonDAO, _dept);
					// ͬ���ͻ���Ϣ
					// commonDAO.insert("Department.syncInsertInfo", _dept);
					// ͬ���ֿ���Ϣ
					// commonDAO.insert("Department.syncInsertStorageInfo",
					// _dept);
					// // ͬ����Ʒ�۸񼶱����ṹ
					// ClientPriceLevel clientLevel = new ClientPriceLevel();
					// clientLevel.setLevelCode(deptId);
					// clientLevel.setLevelName(deptName);
					// if (StringUtils.isEmpty(pDeptId)) {
					// clientLevel.setParentLevelCode(null);
					// } else {
					// clientLevel.setParentLevelCode(pDeptId);
					// }
					// commonDAO.insert("Client.appendClientLevelNode",
					// clientLevel);
					// // ������Ʒ�۸񵽿ͻ� Ŀǰ����������
					// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// �ͻ�
					// Map mapxx = new HashMap();
					// mapxx.put("levelCode", deptId);
					// mapxx.put("clientId", deptId.split(","));
					// commonDAO.update("Client.updateClientLevel", mapxx);
					// }
					// ͬ�����Ŀ¼API
					Map extAttribute = new HashMap();
					extAttribute.put("orders", _dept.getOrders());
					if (enableSyncComponent)
						getSecurityAPI(request).newOrganization(pDeptId_,
								deptId_, deptName, extAttribute);

					if (enableSyncExam)
						SpringBeanUtilExam.getInstance().insertStudentGroup(
								dept);

					// // ͬ���ʺ���Ϣ
					// if (BussVar.BUSSTYPE_4.equals(_dept.getLevel())) {// �ͻ�
					// if (findUserCodeIsExist(commonDAO, deptCode)) {
					// Department deptClone = (Department) BeanUtils
					// .cloneBean(dept);
					//
					// Integer count = (Integer) commonDAO.findForObject(
					// "Department.selectLastUserCodeCount",
					// deptCode);
					// String newUserCode = deptCode + "x" + (++count);
					// deptClone.setDepartmentCode(newUserCode);
					// commonDAO.insert("Department.syncInsertUserInfo",
					// deptClone);
					// // ͬ������ʺ�API
					// if (enableSyncComponent)
					// getSecurityAPI(request).newAccount(deptId_,
					// deptClone.getDepartmentCode(),
					// deptClone.getDepartmentName(), null);
					//
					// json.put("tip2", "�ͻ������ظ�,�ʺ�ϵͳ�Զ�����,�ʺ�:"
					// + newUserCode + "<br>");
					// } else {
					// commonDAO.insert("Department.syncInsertUserInfo",
					// _dept);
					// // ͬ������ʺ�API
					// if (enableSyncComponent)
					// getSecurityAPI(request).newAccount(deptId_,
					// deptCode, deptName, null);
					// }
					// }
				}
			}
			json.put("tip", (json.get("tip2") == null ? "" : json.get("tip2"))
					+ "������Ϣ�ɹ�!");
		} catch (Exception e) {
			json.put("tip", "������Ϣ����ʧ�ܣ��������Ա��ϵ!");
			json.put("error", "yes");
			log.error("������Ϣʧ��!", e);
			throw new RuntimeException("������Ϣʧ��!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void getDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		String departmentId = request.getParameter("departmentId");
		try {
			Department dept = (Department) commonDAO.findForObject(
					"Department.selectInfo", departmentId);
			request.setAttribute("dept", dept);
		} catch (Exception e) {
			log.error("����ʧ��!", e);
		}
	}

	public void deleteDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String departmentId = request.getParameter("departmentId");
		try {
			Map map = new HashMap();
			map.put("levelCode", null);
			map.put("clientId", departmentId.split(","));
			// commonDAO.update("Client.updateClientLevel", map);
			// commonDAO.delete("Client.removeClientLevelNode", departmentId);
			// commonDAO.delete("Client.removePriceOfClientLevel",
			// departmentId);
			commonDAO
					.delete("Department.buildTreeLevelRelation1", departmentId);
			// commonDAO.delete("Department.syncDeleteInfo", departmentId);
			// commonDAO.delete("Department.syncDeleteStorageInfo",
			// departmentId);
			commonDAO.delete("Department.deleteDepartmentlevelrow",
					departmentId);// ɾ����˾��������
			Collection<String> deleteList = new ArrayList();
			if (enableSyncComponent) {
				deleteList = commonDAO.select(
						"Department.selectDeleteUserByDepartmentId",
						departmentId);
			}
			commonDAO.delete("Department.syncDeleteUserInfo", departmentId);
			commonDAO.delete("Department.deleteDepartment", departmentId);
			if (enableSyncComponent) {
				for (String userCode : deleteList) {
					getSecurityAPI(request).deleteAccount(userCode);
				}
				getSecurityAPI(request).deleteOrganization(departmentId);
			}

			if (enableSyncExam) {
				for (String userCode : deleteList) {
					SpringBeanUtilExam.getInstance().deleteStudent(userCode);
				}
				SpringBeanUtilExam.getInstance().deleteStudentGroup(
						departmentId);
			}

			json.put("tip", "ɾ����Ϣ�ɹ�!");
		} catch (Exception e) {
			json.put("tip", "ɾ��ʧ��,���ڹ�����Ϣ�޷�ɾ��!");

			json.put("error", "yes");
			log.error("������!ɾ��ʧ��,���ڹ�����Ϣ�޷�ɾ��!", e);
			throw new RuntimeException("ɾ��ʧ��,���ڹ�����Ϣ�޷�ɾ��!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void findDepartmentTree(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String isShowStatus = request.getParameter("show");// �Ƿ�����״̬
		String include = request.getParameter("include");// �Ƿ�������ڵ�
		String str = "";
		try {
			Collection set = new ArrayList();
			// nodeΪ�� extֵΪynode ��ext����
			if (StringUtils.isEmpty(node) || node.contains("ynode")) {
				node = "0";
			}
			if ("0".equals(node)) {
				Map map = new HashMap();
				User user = getOnlineUser(request);
				if ("adminx".equals(user.getLevel())) {
					//
				} else if (BussVar.BUSSTYPE_S.equals(user.getLevel())) {// �ֿ�
					node = user.getDepartmentLevelRow().getDepartmentIds1();
				} else {
					String p = request.getParameter("p");
					if ("0".equals(p)) {
						// �ֿ����
						if (BussVar.BUSSTYPE_1.equals(user.getLevel())
								|| BussVar.BUSSTYPE_2.equals(user.getLevel())
								|| BussVar.BUSSTYPE_3.equals(user.getLevel())) {// ʡ��˾��Ӫ�������Ӫ����
							// node = user.getDepartmentLevelRow()
							// .getDepartmentId1();
							//
						} else {
							node = user.getDepartmentId();
						}
					} else if ("map".equals(p)) {
						// ��ͼ����
						// if (BussVar.BUSSTYPE_1.equals(user.getLevel())
						// || BussVar.BUSSTYPE_2.equals(user.getLevel())
						// || BussVar.BUSSTYPE_3.equals(user.getLevel())
						// || BussVar.BUSSTYPE_4.equals(user.getLevel())) {//
						// ʡ��˾��Ӫ�������Ӫ����������
						node = user.getParentDepartmentId();
						node = "".equals(node) ? "0" : node;
						node = node == null ? "0" : node;
						map.put("filter", user.getDepartmentId());
						// } else {
						// node = user.getDepartmentId();
						// }
					} else {
						node = user.getDepartmentId();
					}
				}
				map.put("departmentId", node);
				if (StringUtils.isNotEmpty(isShowStatus)) {
					map.put("isShowStatus", isShowStatus);
				}
				set = commonDAO.select("Department.loadDepartmentRoot", map);
			} else {
				String excludeNode = request.getParameter("excludeNode");
				Map map = new HashMap();
				map.put("departmentId", node);
				if ("yes".equals(excludeNode)) {
					map.put("excludeNode", "yes");
				}
				if (StringUtils.isNotEmpty(isShowStatus)) {
					map.put("isShowStatus", isShowStatus);
				}
				if ("1".equals(include)) {
					map.put("include", include);
				}
				// User user = getOnlineUser(request);
				// map.put("permission", user.getDepartmentId());
				// if ("adminx".equals(user.getUserCode())) {// ����Ա
				// map.put("permission", "0");
				// }
				String list = request.getParameter("list");
				if (StringUtils.isNotEmpty(list)) {
					map.put("list", list);
					if ("all".equals(list)) {
						map.put("departmentId_", node);
						map.put("departmentId", "");
					}
				}
				String departmentCode = request.getParameter("departmentCode");
				if (StringUtils.isNotEmpty(departmentCode)) {
					map.put("departmentCode", departmentCode);
				}
				set = commonDAO.select("Department.findChildDepartment", map);
			}
			str = buildDepartmentJsonStr(set);
		} catch (Exception e) {
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, str);
		}
	}

	public void findDepartmentTreeX(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String str = "";
		try {
			Collection set = new ArrayList();
			// nodeΪ�� extֵΪynode ��ext����
			if (StringUtils.isEmpty(node) || node.contains("ynode")) {
				node = "0";
			}
			if ("0".equals(node)) {
				Map map = new HashMap();
				map.put("departmentId", node);
				set = commonDAO.select("Department.loadDepartmentRoot", map);
			} else {
				String excludeNode = request.getParameter("excludeNode");
				Map map = new HashMap();
				map.put("departmentId", node);
				String departmentCode = request.getParameter("departmentCode");
				if (StringUtils.isNotEmpty(departmentCode)) {
					map.put("departmentCode", departmentCode);
				}
				set = commonDAO.select("Department.findChildDepartment", map);
			}
			str = buildDepartmentJsonStr(set);
		} catch (Exception e) {
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, str);
		}
	}

	public void exportDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response) {
	}

	public void importDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	public String checkBussType(String id) {
		String busstype = null;
		try {
			busstype = (String) commonDAO.findForObject(
					"Department.checkBussType", id);
			if (StringUtils.isEmpty(busstype))
				busstype = null;
			return busstype;
		} catch (Exception e) {
			log.error("������", e);
		}
		return busstype;
	}

	public void importDAreaInfo(HttpServletRequest request,
			HttpServletResponse response) {
		CommonUploadUtil importS = new CommonUploadUtil(request);
		request.setAttribute("ErrorJson", "Yes"); // Json������ʾ
		String tip = "�������ݳɹ���";
		try {
			FileItem fileItem = importS.getFileItem("importFile");// ��ȡҳ�洫�����ļ�
			InputStream in = fileItem.getInputStream();
			Object[] sheetSet = JxlUtilsTemplate.newInstanct().readAll(in);
			List list1 = (List) sheetSet[0];
			List list = null;
			if (list1.size() <= 1) {
				tip = "��������ǿ������ļ�";
			} else {
				list = new ArrayList();
				for (int i = 2; i < list1.size(); i++) {
					Object[] object = (Object[]) list1.get(i);
					if (object.length == 7) {// ��ǰ�ı������������Ƿ�һ��
						Department dept = new Department();
						dept.setDepartmentId(object[0].toString().trim());
						dept.setMap(object[6].toString().trim());
						list.add(dept);
					}
				}
				this.commonDAO.updateBatch("Department.importDMap", list);
			}
		} catch (Exception e) {
			tip = "����������Ϣʧ��";
			log.error("����������Ϣʧ��!", e);
			throw new RuntimeException("����������Ϣʧ��!");
		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(tip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void initExtData() {
		try {
			Long startTime = System.currentTimeMillis();
			buildChildTreeRelation(commonDAO, "0");
			Long endTime = System.currentTimeMillis();
			System.out.println("Totle time is " + (endTime - startTime)
					+ "milliseconds");
			System.out.println("����");
		} catch (Exception e) {
			log.error("������", e);
		}
	}

	public void fobidFunction(HttpServletRequest request,
			HttpServletResponse response) {
		String departmentId = request.getParameter("departmentId");
		String userId = request.getParameter("userId");
		String s = request.getParameter("s");
		request.setAttribute("ErrorJson", "Yes"); // Json������ʾ
		JSONObject jsonTip = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(departmentId)) {// ����
				Client client = new Client();
				User user = new User();
				client.setClientId(departmentId);
				String tips = "";
				if ("1".equals(s)) {// ����
					client.setCancelTime(null);
					user.setCancelDate(null);
					user.setStatus("1");

					Collection<String> userIdStrs = commonDAO.select(
							"User.selectUserByPID", departmentId);
					for (String ids : userIdStrs) {
						User userx = (User) BeanUtils.cloneBean(user);
						userx.setUserId(ids);
						commonDAO.update("User.updateUserStatus", userx);
						// ͬ�����Ŀ¼API
						if (enableSyncComponent) {
							String usercode = (String) commonDAO.findForObject(
									"User.selectUserCodeByUserId", userId);
							getSecurityAPI(request).recoveryAccount(usercode);
						}
					}

					tips = "�����ɹ�,��Ҫ�ֶ�������ԱȨ��!";
				} else if ("0".equals(s)) {// ����
					client.setCancelTime(new Date());
					user.setCancelDate(new Date());
					user.setStatus("0");

					Collection<String> userIdStrs = commonDAO.select(
							"User.selectUserByPID", departmentId);
					for (String ids : userIdStrs) {
						User userx = (User) BeanUtils.cloneBean(user);
						userx.setUserId(ids);
						commonDAO.update("User.updateUserStatus", userx);
						// ͬ�����Ŀ¼API
						if (enableSyncComponent) {
							String usercode = (String) commonDAO.findForObject(
									"User.selectUserCodeByUserId", userId);
							getSecurityAPI(request).fobidAccount(usercode);
						}
					}

					tips = "���óɹ�,����������Աȫ������!";
				}
				commonDAO.update("Client.updateClientStatus", client);
				jsonTip.put("tip", tips);
			} else if (StringUtils.isNotEmpty(userId)) {// ���
				User user = new User();
				user.setUserId(userId);
				String tips = "";
				if ("1".equals(s)) {// ����
					user.setCancelDate(null);
					user.setStatus("1");
					// ͬ�����Ŀ¼API
					if (enableSyncComponent) {
						String usercode = (String) commonDAO.findForObject(
								"User.selectUserCodeByUserId", userId);
						getSecurityAPI(request).recoveryAccount(usercode);
					}
					tips = "�����ɹ�!";
				} else if ("0".equals(s)) {// ����
					user.setCancelDate(new Date());
					user.setStatus("0");
					// ͬ�����Ŀ¼API
					if (enableSyncComponent) {
						String usercode = (String) commonDAO.findForObject(
								"User.selectUserCodeByUserId", userId);
						getSecurityAPI(request).fobidAccount(usercode);
					}
					tips = "���óɹ�!";
				}
				commonDAO.update("User.updateUserStatus", user);
				jsonTip.put("tip", tips);
			} else {
				jsonTip.put("error", "yes");
				jsonTip.put("tip", "�޲���,�Ҳ�����Ҫ��������Ŀ!");
			}
		} catch (Exception e) {
			jsonTip.put("error", "yes");
			jsonTip.put("tip", "����ʧ��!");
			log.error("����ʧ��!", e);
			throw new RuntimeException("����ʧ��!");
		} finally {
			super.writeJsonStr(response, jsonTip.toString());
		}

	}

	// -- ˽�з���

	private String buildDepartmentJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Department d = (Department) itr.next();
			d.setText(d.getDepartmentName());
			d.setId(d.getDepartmentId());
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	/**
	 * ȡ��Bean
	 * 
	 * @param request
	 * @return
	 */
	private Department getBean(HttpServletRequest request) {
		Department dept = new Department();
		super.setJavaBean(request, dept);
		Department parentDepartment = new Department();
		String parentDepartmentId = request.getParameter("parentDepartmentId");
		String departmentCode = request.getParameter("departmentCode");
		String departmentName = request.getParameter("departmentName");
		parentDepartmentId = "0".equals(parentDepartmentId) ? ""
				: parentDepartmentId;
		if (StringUtils.isNotEmpty(parentDepartmentId)) {
			parentDepartment.setDepartmentId(parentDepartmentId);
			parentDepartment.setParentDepartment(parentDepartment);
			dept.setParentDepartmentId(parentDepartmentId);
		} else {
			parentDepartment.setDepartmentId(null);
			parentDepartment.setParentDepartment(null);
			dept.setDepartmentId(null);
			dept.setParentDepartmentId(null);
		}
		String bussType = request.getParameter("bussType");
		if (StringUtils.isNotEmpty(bussType)) {
			dept.setLevel(bussType);
		}
		if (StringUtils.isNotEmpty(departmentName)) {
			dept.setDepartmentName(departmentName.trim());
		}
		if (StringUtils.isNotEmpty(departmentCode)) {
			dept.setDepartmentCode(departmentCode.trim());
		}
		dept.setParentDepartment(parentDepartment);
		return dept;
	}

	/**
	 * ��������ϵ�ṹ
	 * 
	 * @param list
	 *            ��ѯ�ļ�����
	 * @param id
	 *            ����ID
	 * @param level
	 *            ��ǰ������
	 * @return
	 * @throws Exception
	 */
	public int findTreeRelation(CommonDAO dao, List<Department> list,
			String id, int level) throws Exception {
		Department dept = (Department) dao.findForObject(
				"Department.selectInfo", id);
		list.add(dept);
		if (dept.getParentDepartment() != null) {
			level = findTreeRelation(dao, list, dept.getParentDepartment()
					.getDepartmentId(), ++level);
		}
		return level;
	}

	/**
	 * ��������ϵ�ṹ
	 * 
	 * @param list
	 *            ��ѯ�ļ�����
	 * @param id
	 *            ����/��˾ID
	 * @param level
	 *            ��ǰ������
	 * @param isBuildTreeLevel
	 *            �Ƿ���������
	 * @return
	 * @throws Exception
	 */
	public void buildTreeRelation(CommonDAO dao, List<Department> list,
			String id, int level, String bussType, boolean isBuildTreeLevel)
			throws Exception {
		int nLevel = level;
		String nLevelCode = "";
		String nLevelName = "";
		String levelCode = "";
		List<DepartmentLevel> dlList = new ArrayList<DepartmentLevel>();
		int orders = 0;
		for (Department department : list) {
			nLevelCode = "[" + department.getDepartmentId() + "]" + nLevelCode;
			nLevelName = "[" + department.getDepartmentName() + "]"
					+ nLevelName;

			if (isBuildTreeLevel) {
				String levelx = department.getLevel();
				if (isBuildAnalysisLevel(levelx, bussType)) {
					DepartmentLevel dl = new DepartmentLevel();
					dl.setDepartmentLevelItemId(id);
					dl.setDepartmentLevel(levelx);
					dl.setDepartmentLevelId(department.getDepartmentId());
					dl.setDepartmentLevelCode(department.getDepartmentCode());
					dl.setDepartmentLevelName(department.getDepartmentName());
					dl.setOrders(orders++);
					dlList.add(dl);
				}
			}
			log
					.debug("id:" + id + " " + bussType + " PASS:"
							+ isBuildTreeLevel);
		}
		Map map = new HashMap();
		map.put("nLevel", nLevel);
		map.put("nLevelCode", "[0]" + nLevelCode);
		map.put("nLevelName", "[0]" + nLevelName);
		map.put("levelCode", levelCode);
		map.put("level", bussType);
		map.put("departmentId", id);
		dao.update("Department.buildTreeRelation", map);

		// ����/��˾�������ͬ��
		if (dlList.size() > 0) {
			dao.delete("Department.buildTreeLevelRelation1", id);
			dao.insertBatch("Department.buildTreeLevelRelation2", dlList);
		}

	}

	/**
	 * �����������нڵ�
	 * 
	 * @param pid
	 * @throws Exception
	 */
	public void buildChildTreeRelation(CommonDAO dao, String pid)
			throws Exception {
		Map map = new HashMap();
		map.put("departmentId", pid);
		Collection<Department> set = dao.select(
				"Department.findChildDepartment", map);
		for (Department department : set) {
			List levelList_child = new ArrayList();
			int level_child = 0;
			level_child = findTreeRelation(dao, levelList_child, department
					.getDepartmentId(), ++level_child);
			buildTreeRelation(dao, levelList_child, department
					.getDepartmentId(), level_child, null,
					getIsBuildTreeLevel(department.getLevel()));
			// buildStorageTreeRelation(dao, department);// ���²ֿ�
			updateDepartmentlevelrow(dao, department);
			log.debug("�޸��ӽڵ�ID--" + department.getDepartmentId());
			buildChildTreeRelation(dao, department.getDepartmentId());
		}
	}

	/**
	 * �ֿ������ϵ����
	 * 
	 * @param department
	 * @throws Exception
	 */
	public void buildStorageTreeRelation(CommonDAO dao, Department department)
			throws Exception {
		if (BussVar.BUSSTYPE_S.equals(department.getLevel())) {// �ֿ�
			Map dept = (Map) commonDAO.findForObject(
					"Department.findPDeptBussType", department
							.getParentDepartmentId());

			boolean pass = false;
			String bussvar = "";
			if (dept != null) {
				String busstype = (String) dept.get("level");

				if (BussVar.BUSSTYPE_X.equals(busstype)) {// Ŀ¼
					// not do
				} else if (BussVar.BUSSTYPE_S.equals(busstype)) {// �ֿ�
					// not do
				} else {// һ����
					pass = true;
					bussvar = BussVar.BUSSTYPE_S1;
				}
			}

			if (pass) {
				DepartmentLevel dl = new DepartmentLevel();
				dl.setDepartmentLevel(bussvar);// �ֿ�
				dl.setDepartmentLevelItemId(department.getDepartmentId());
				dl.setDepartmentLevelId(department.getParentDepartmentId());
				dl.setDepartmentLevelCode((String) dept.get("departmentCode"));
				dl.setDepartmentLevelName((String) dept.get("departmentName"));
				dl.setOrders(0);
				Integer isExist = (Integer) commonDAO.findForObject(
						"Department.findTreeLevelRelationIsExist", dl);
				if (isExist > 0) {
					commonDAO.update("Department.buildTreeLevelRelation3", dl);
				} else {
					commonDAO.insert("Department.buildTreeLevelRelation2", dl);
				}
			}

		}
	}

	/**
	 * ��ʼ����˾��������
	 * 
	 * @param departmentId
	 * @throws Exception
	 */
	public void initDepartmentlevelrow(CommonDAO dao, String departmentId)
			throws Exception {
		Integer count = (Integer) dao.findForObject(
				"Department.findDepartmentlevelrowIsExist", departmentId);
		if (count > 0) {
			// not do
		} else {
			dao.insert("Department.initDepartmentlevelrow", departmentId);
		}
	}

	/**
	 * ���¹�˾��������
	 * 
	 * @param dept
	 * @throws Exception
	 */
	public void updateDepartmentlevelrow(CommonDAO dao, Department dept)
			throws Exception {
		Integer count = (Integer) dao.findForObject(
				"Department.findDepartmentlevelrowIsExist", dept
						.getDepartmentId());
		if (count > 0) {
			dao.update("Department.updateDepartmentlevelrow", dept);
		} else {
			dao.insert("Department.initDepartmentlevelrow", dept
					.getDepartmentId());
			dao.update("Department.updateDepartmentlevelrow", dept);
		}
		dao.update("Department.updateCurrDepartmentlevelrow", dept);
	}

	/**
	 * ��ȡ��ǰ���ڵ��Ƿ���Ҫ�����������Ϣ
	 * 
	 * @param level
	 * @return
	 */
	public boolean getIsBuildTreeLevel(String level) {
		if (BussVar.BUSSTYPE_D.equals(level)) {// ����������
			return true;
		}
		if (BussVar.BUSSTYPE_1.equals(level)) {// ���ʡ��˾����
			return true;
		}
		if (BussVar.BUSSTYPE_2.equals(level)) {// ��Դ��������
			return true;
		}
		if (BussVar.BUSSTYPE_3.equals(level)) {// ���Ӫ��������
			return true;
		}
		if (BussVar.BUSSTYPE_4.equals(level)) {// ��Ծ����̷���
			return true;
		}
		if (BussVar.BUSSTYPE_5.equals(level)) {// ��Է����̷���
			return true;
		}
		if (BussVar.BUSSTYPE_S.equals(level)) {// ��Բֿ����
			return true;
		}
		return false;
	}

	/**
	 * �����Ͻ��������������
	 * 
	 * @param level
	 * @param bussType
	 * @return
	 */
	public boolean isBuildAnalysisLevel(String level, String bussType) {
		Map<String, String> analysisItems = new HashMap<String, String>();
		if (BussVar.BUSSTYPE_5.equals(bussType)) {// ������ֻ��Ծ����̷���
			analysisItems.put(BussVar.BUSSTYPE_1, BussVar.BUSSTYPE_1);// ʡ��˾
			analysisItems.put(BussVar.BUSSTYPE_2, BussVar.BUSSTYPE_2);// ��Ӫ����
			analysisItems.put(BussVar.BUSSTYPE_3, BussVar.BUSSTYPE_3);// Ӫ����
			analysisItems.put(BussVar.BUSSTYPE_4, BussVar.BUSSTYPE_4);// ������
			analysisItems.put(BussVar.BUSSTYPE_5, BussVar.BUSSTYPE_5);// ������
			analysisItems.put(BussVar.BUSSTYPE_S, BussVar.BUSSTYPE_S);// �ֿ�
		} else {
			analysisItems.put(BussVar.BUSSTYPE_1, BussVar.BUSSTYPE_1);// ʡ��˾
			analysisItems.put(BussVar.BUSSTYPE_2, BussVar.BUSSTYPE_2);// ��Ӫ����
			analysisItems.put(BussVar.BUSSTYPE_3, BussVar.BUSSTYPE_3);// Ӫ����
			analysisItems.put(BussVar.BUSSTYPE_4, BussVar.BUSSTYPE_4);// ������
			analysisItems.put(BussVar.BUSSTYPE_S, BussVar.BUSSTYPE_S);// �ֿ�
		}
		if (analysisItems.containsKey(level)) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��û������Ƿ��Ѵ���
	 * 
	 * @param request
	 * @return
	 */
	public Boolean findUserCodeIsExist(CommonDAO dao, String userCode)
			throws Exception {
		Boolean flag = true;
		Integer result = (Integer) dao.findForObject(
				"User.findUserCodeIsExist", userCode);
		if (result == 0) {
			flag = false;
		} else {
			flag = true;
		}

		return flag;
	}

}
