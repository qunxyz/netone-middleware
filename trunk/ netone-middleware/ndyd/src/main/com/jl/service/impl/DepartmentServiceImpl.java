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
 * 部门/公司管理实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-10 下午02:48:01
 * @history
 */
public class DepartmentServiceImpl extends BaseService implements
		DepartmentService {

	/** 日志 */
	private final Logger log = Logger.getLogger(DepartmentServiceImpl.class);

	private final boolean enableSyncComponent = true;// 是否同步组件

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
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 开始索引
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
			log.error("出错了!", e);
		}

	}

	public void saveOrUpdateDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		Department dept = this.getBean(request);// 获取实例
		String departmentId = request.getParameter("departmentId");
		// 业务类型 根目录:0 区域:-1 省公司:1 大营销部:2 营销部:3 经销商:4 分销商:5 仓库:s
		String bussType = request.getParameter("bussType");
		String batchCountx = request.getParameter("batchCount");
		List<Department> levelList = new ArrayList<Department>();
		int nlevel = 0;// 当前树所在级别
		try {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			if (oluser == null) {
				json.put("tip", "登录超时!");
				json.put("error", "yes");
			}

			log.debug("类型:" + bussType);

			// 检查编码是否重复
			if (!BussVar.BUSSTYPE_4.equals(bussType)
					&& !BussVar.BUSSTYPE_5.equals(bussType)) {
				Integer isexist = (Integer) commonDAO.findForObject(
						"Department.findCodeIsExist", dept);
				if (isexist > 0) {
					json.put("tip", "编码出现重复!");
					json.put("error", "yes");
					return;
				}
			}

			if (StringUtils.isNotEmpty(departmentId)) {// 根据Id 来判断是 否是修改还是插入
				// 存在,则修改 Id 不存在 则保存\
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

				// 删除关联营销部、经销商、分销商信息

				// 更新本节点
				nlevel = findTreeRelation(commonDAO, levelList, deptId,
						++nlevel);
				// 更新本级组织机构关联
				buildTreeRelation(commonDAO, levelList, deptId, nlevel,
						bussType, getIsBuildTreeLevel(bussType));
				// 同步仓库关联
				// buildStorageTreeRelation(commonDAO, dept);
				// 更新公司横向数据
				updateDepartmentlevelrow(commonDAO, dept);
				log.debug("修改当前节点ID--" + deptId);

				if (!originalParentDepartmentId.equals(pDeptId == null ? ""
						: pDeptId)) {// 如果存在更改目录上下级关系
					// 更新其下所有节点
					buildChildTreeRelation(commonDAO, deptId);
				}
				// 同步客户信息
				// commonDAO.update("Department.syncUpdateInfo", dept);
				// 同步仓库信息
				// commonDAO.update("Department.syncUpdateStorageInfo", dept);
				// // 同步产品价格级别树结构
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
				// // 关联产品价格到客户 目前关联经销商
				// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// 客户
				// Map mapxx = new HashMap();
				// mapxx.put("levelCode", deptId);
				// mapxx.put("clientId", deptId.split(","));
				// commonDAO.update("Client.updateClientLevel", mapxx);
				// }
				// 同步组件目录API
				Map extAttribute = new HashMap();
				extAttribute.put("orders", dept.getOrders());
				if (enableSyncComponent)
					getSecurityAPI(request).editOrganization(pDeptId_, deptId_,
							deptName, extAttribute);

				if (enableSyncExam)
					SpringBeanUtilExam.getInstance().updateStudentGroup(dept);

				// 同步帐号信息
				// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// 客户
				// // if (findUserCodeIsExist(deptCode)) {
				// // Department deptClone = (Department) BeanUtils
				// // .cloneBean(dept);
				// // deptClone.setDepartmentCode(null);// 不更新
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
					log.debug("新增ID--" + _dept.getDepartmentId());

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
					// 初始化公司横向数据
					initDepartmentlevelrow(commonDAO, deptId);
					// 更新本级组织机构关联
					buildTreeRelation(commonDAO, levelList, deptId, nlevel,
							bussType, getIsBuildTreeLevel(bussType));
					// 同步仓库关联
					// buildStorageTreeRelation(commonDAO, _dept);
					// 更新公司横向数据
					updateDepartmentlevelrow(commonDAO, _dept);
					// 同步客户信息
					// commonDAO.insert("Department.syncInsertInfo", _dept);
					// 同步仓库信息
					// commonDAO.insert("Department.syncInsertStorageInfo",
					// _dept);
					// // 同步产品价格级别树结构
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
					// // 关联产品价格到客户 目前关联经销商
					// if (BussVar.BUSSTYPE_4.equals(dept.getLevel())) {// 客户
					// Map mapxx = new HashMap();
					// mapxx.put("levelCode", deptId);
					// mapxx.put("clientId", deptId.split(","));
					// commonDAO.update("Client.updateClientLevel", mapxx);
					// }
					// 同步组件目录API
					Map extAttribute = new HashMap();
					extAttribute.put("orders", _dept.getOrders());
					if (enableSyncComponent)
						getSecurityAPI(request).newOrganization(pDeptId_,
								deptId_, deptName, extAttribute);

					if (enableSyncExam)
						SpringBeanUtilExam.getInstance().insertStudentGroup(
								dept);

					// // 同步帐号信息
					// if (BussVar.BUSSTYPE_4.equals(_dept.getLevel())) {// 客户
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
					// // 同步组件帐号API
					// if (enableSyncComponent)
					// getSecurityAPI(request).newAccount(deptId_,
					// deptClone.getDepartmentCode(),
					// deptClone.getDepartmentName(), null);
					//
					// json.put("tip2", "客户编码重复,帐号系统自动生成,帐号:"
					// + newUserCode + "<br>");
					// } else {
					// commonDAO.insert("Department.syncInsertUserInfo",
					// _dept);
					// // 同步组件帐号API
					// if (enableSyncComponent)
					// getSecurityAPI(request).newAccount(deptId_,
					// deptCode, deptName, null);
					// }
					// }
				}
			}
			json.put("tip", (json.get("tip2") == null ? "" : json.get("tip2"))
					+ "保存信息成功!");
		} catch (Exception e) {
			json.put("tip", "保存信息操作失败，请与管理员联系!");
			json.put("error", "yes");
			log.error("保存信息失败!", e);
			throw new RuntimeException("保存信息失败!");
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
			log.error("加载失败!", e);
		}
	}

	public void deleteDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
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
					departmentId);// 删除公司横向数据
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

			json.put("tip", "删除信息成功!");
		} catch (Exception e) {
			json.put("tip", "删除失败,存在关联信息无法删除!");

			json.put("error", "yes");
			log.error("出错了!删除失败,存在关联信息无法删除!", e);
			throw new RuntimeException("删除失败,存在关联信息无法删除!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void findDepartmentTree(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String isShowStatus = request.getParameter("show");// 是否显新状态
		String include = request.getParameter("include");// 是否包含本节点
		String str = "";
		try {
			Collection set = new ArrayList();
			// node为空 ext值为ynode 是ext问题
			if (StringUtils.isEmpty(node) || node.contains("ynode")) {
				node = "0";
			}
			if ("0".equals(node)) {
				Map map = new HashMap();
				User user = getOnlineUser(request);
				if ("adminx".equals(user.getLevel())) {
					//
				} else if (BussVar.BUSSTYPE_S.equals(user.getLevel())) {// 仓库
					node = user.getDepartmentLevelRow().getDepartmentIds1();
				} else {
					String p = request.getParameter("p");
					if ("0".equals(p)) {
						// 仓库操作
						if (BussVar.BUSSTYPE_1.equals(user.getLevel())
								|| BussVar.BUSSTYPE_2.equals(user.getLevel())
								|| BussVar.BUSSTYPE_3.equals(user.getLevel())) {// 省公司或营销部或大营销部
							// node = user.getDepartmentLevelRow()
							// .getDepartmentId1();
							//
						} else {
							node = user.getDepartmentId();
						}
					} else if ("map".equals(p)) {
						// 地图操作
						// if (BussVar.BUSSTYPE_1.equals(user.getLevel())
						// || BussVar.BUSSTYPE_2.equals(user.getLevel())
						// || BussVar.BUSSTYPE_3.equals(user.getLevel())
						// || BussVar.BUSSTYPE_4.equals(user.getLevel())) {//
						// 省公司或营销部或大营销部或经销商
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
				// if ("adminx".equals(user.getUserCode())) {// 管理员
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
			log.error("出错", e);
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
			// node为空 ext值为ynode 是ext问题
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
			log.error("出错", e);
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
			log.error("出错了", e);
		}
		return busstype;
	}

	public void importDAreaInfo(HttpServletRequest request,
			HttpServletResponse response) {
		CommonUploadUtil importS = new CommonUploadUtil(request);
		request.setAttribute("ErrorJson", "Yes"); // Json出错提示
		String tip = "导入数据成功！";
		try {
			FileItem fileItem = importS.getFileItem("importFile");// 获取页面传来的文件
			InputStream in = fileItem.getInputStream();
			Object[] sheetSet = JxlUtilsTemplate.newInstanct().readAll(in);
			List list1 = (List) sheetSet[0];
			List list = null;
			if (list1.size() <= 1) {
				tip = "您导入的是空数据文件";
			} else {
				list = new ArrayList();
				for (int i = 2; i < list1.size(); i++) {
					Object[] object = (Object[]) list1.get(i);
					if (object.length == 7) {// 当前的表格的数据列数是否一置
						Department dept = new Department();
						dept.setDepartmentId(object[0].toString().trim());
						dept.setMap(object[6].toString().trim());
						list.add(dept);
					}
				}
				this.commonDAO.updateBatch("Department.importDMap", list);
			}
		} catch (Exception e) {
			tip = "导入数据信息失败";
			log.error("导入数据信息失败!", e);
			throw new RuntimeException("导入数据信息失败!");
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
			System.out.println("结束");
		} catch (Exception e) {
			log.error("出错了", e);
		}
	}

	public void fobidFunction(HttpServletRequest request,
			HttpServletResponse response) {
		String departmentId = request.getParameter("departmentId");
		String userId = request.getParameter("userId");
		String s = request.getParameter("s");
		request.setAttribute("ErrorJson", "Yes"); // Json出错提示
		JSONObject jsonTip = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(departmentId)) {// 批量
				Client client = new Client();
				User user = new User();
				client.setClientId(departmentId);
				String tips = "";
				if ("1".equals(s)) {// 开启
					client.setCancelTime(null);
					user.setCancelDate(null);
					user.setStatus("1");

					Collection<String> userIdStrs = commonDAO.select(
							"User.selectUserByPID", departmentId);
					for (String ids : userIdStrs) {
						User userx = (User) BeanUtils.cloneBean(user);
						userx.setUserId(ids);
						commonDAO.update("User.updateUserStatus", userx);
						// 同步组件目录API
						if (enableSyncComponent) {
							String usercode = (String) commonDAO.findForObject(
									"User.selectUserCodeByUserId", userId);
							getSecurityAPI(request).recoveryAccount(usercode);
						}
					}

					tips = "开启成功,需要手动启用人员权限!";
				} else if ("0".equals(s)) {// 禁用
					client.setCancelTime(new Date());
					user.setCancelDate(new Date());
					user.setStatus("0");

					Collection<String> userIdStrs = commonDAO.select(
							"User.selectUserByPID", departmentId);
					for (String ids : userIdStrs) {
						User userx = (User) BeanUtils.cloneBean(user);
						userx.setUserId(ids);
						commonDAO.update("User.updateUserStatus", userx);
						// 同步组件目录API
						if (enableSyncComponent) {
							String usercode = (String) commonDAO.findForObject(
									"User.selectUserCodeByUserId", userId);
							getSecurityAPI(request).fobidAccount(usercode);
						}
					}

					tips = "禁用成功,其下所属人员全部禁用!";
				}
				commonDAO.update("Client.updateClientStatus", client);
				jsonTip.put("tip", tips);
			} else if (StringUtils.isNotEmpty(userId)) {// 逐个
				User user = new User();
				user.setUserId(userId);
				String tips = "";
				if ("1".equals(s)) {// 开启
					user.setCancelDate(null);
					user.setStatus("1");
					// 同步组件目录API
					if (enableSyncComponent) {
						String usercode = (String) commonDAO.findForObject(
								"User.selectUserCodeByUserId", userId);
						getSecurityAPI(request).recoveryAccount(usercode);
					}
					tips = "开启成功!";
				} else if ("0".equals(s)) {// 禁用
					user.setCancelDate(new Date());
					user.setStatus("0");
					// 同步组件目录API
					if (enableSyncComponent) {
						String usercode = (String) commonDAO.findForObject(
								"User.selectUserCodeByUserId", userId);
						getSecurityAPI(request).fobidAccount(usercode);
					}
					tips = "禁用成功!";
				}
				commonDAO.update("User.updateUserStatus", user);
				jsonTip.put("tip", tips);
			} else {
				jsonTip.put("error", "yes");
				jsonTip.put("tip", "无操作,找不到需要操作的项目!");
			}
		} catch (Exception e) {
			jsonTip.put("error", "yes");
			jsonTip.put("tip", "操作失败!");
			log.error("操作失败!", e);
			throw new RuntimeException("操作失败!");
		} finally {
			super.writeJsonStr(response, jsonTip.toString());
		}

	}

	// -- 私有方法

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
	 * 取得Bean
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
	 * 查找树关系结构
	 * 
	 * @param list
	 *            查询的级别树
	 * @param id
	 *            分类ID
	 * @param level
	 *            当前树级别
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
	 * 建立树关系结构
	 * 
	 * @param list
	 *            查询的级别树
	 * @param id
	 *            部门/公司ID
	 * @param level
	 *            当前树级别
	 * @param isBuildTreeLevel
	 *            是否建立树级别
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

		// 部门/公司级别关联同步
		if (dlList.size() > 0) {
			dao.delete("Department.buildTreeLevelRelation1", id);
			dao.insertBatch("Department.buildTreeLevelRelation2", dlList);
		}

	}

	/**
	 * 更新其下所有节点
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
			// buildStorageTreeRelation(dao, department);// 更新仓库
			updateDepartmentlevelrow(dao, department);
			log.debug("修改子节点ID--" + department.getDepartmentId());
			buildChildTreeRelation(dao, department.getDepartmentId());
		}
	}

	/**
	 * 仓库关联关系建立
	 * 
	 * @param department
	 * @throws Exception
	 */
	public void buildStorageTreeRelation(CommonDAO dao, Department department)
			throws Exception {
		if (BussVar.BUSSTYPE_S.equals(department.getLevel())) {// 仓库
			Map dept = (Map) commonDAO.findForObject(
					"Department.findPDeptBussType", department
							.getParentDepartmentId());

			boolean pass = false;
			String bussvar = "";
			if (dept != null) {
				String busstype = (String) dept.get("level");

				if (BussVar.BUSSTYPE_X.equals(busstype)) {// 目录
					// not do
				} else if (BussVar.BUSSTYPE_S.equals(busstype)) {// 仓库
					// not do
				} else {// 一级库
					pass = true;
					bussvar = BussVar.BUSSTYPE_S1;
				}
			}

			if (pass) {
				DepartmentLevel dl = new DepartmentLevel();
				dl.setDepartmentLevel(bussvar);// 仓库
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
	 * 初始化公司横向数据
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
	 * 更新公司横向数据
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
	 * 获取当前树节点是否需要树级别关联信息
	 * 
	 * @param level
	 * @return
	 */
	public boolean getIsBuildTreeLevel(String level) {
		if (BussVar.BUSSTYPE_D.equals(level)) {// 针对区域分析
			return true;
		}
		if (BussVar.BUSSTYPE_1.equals(level)) {// 针对省公司分析
			return true;
		}
		if (BussVar.BUSSTYPE_2.equals(level)) {// 针对大区域分析
			return true;
		}
		if (BussVar.BUSSTYPE_3.equals(level)) {// 针对营销部分析
			return true;
		}
		if (BussVar.BUSSTYPE_4.equals(level)) {// 针对经销商分析
			return true;
		}
		if (BussVar.BUSSTYPE_5.equals(level)) {// 针对分销商分析
			return true;
		}
		if (BussVar.BUSSTYPE_S.equals(level)) {// 针对仓库分析
			return true;
		}
		return false;
	}

	/**
	 * 检查符合建立分析项的条件
	 * 
	 * @param level
	 * @param bussType
	 * @return
	 */
	public boolean isBuildAnalysisLevel(String level, String bussType) {
		Map<String, String> analysisItems = new HashMap<String, String>();
		if (BussVar.BUSSTYPE_5.equals(bussType)) {// 分销商只针对经销商分析
			analysisItems.put(BussVar.BUSSTYPE_1, BussVar.BUSSTYPE_1);// 省公司
			analysisItems.put(BussVar.BUSSTYPE_2, BussVar.BUSSTYPE_2);// 大营销部
			analysisItems.put(BussVar.BUSSTYPE_3, BussVar.BUSSTYPE_3);// 营销部
			analysisItems.put(BussVar.BUSSTYPE_4, BussVar.BUSSTYPE_4);// 经销商
			analysisItems.put(BussVar.BUSSTYPE_5, BussVar.BUSSTYPE_5);// 分销商
			analysisItems.put(BussVar.BUSSTYPE_S, BussVar.BUSSTYPE_S);// 仓库
		} else {
			analysisItems.put(BussVar.BUSSTYPE_1, BussVar.BUSSTYPE_1);// 省公司
			analysisItems.put(BussVar.BUSSTYPE_2, BussVar.BUSSTYPE_2);// 大营销部
			analysisItems.put(BussVar.BUSSTYPE_3, BussVar.BUSSTYPE_3);// 营销部
			analysisItems.put(BussVar.BUSSTYPE_4, BussVar.BUSSTYPE_4);// 经销商
			analysisItems.put(BussVar.BUSSTYPE_S, BussVar.BUSSTYPE_S);// 仓库
		}
		if (analysisItems.containsKey(level)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户编码是否已存在
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
