package com.jl.service.impl;

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
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.RandomGUID;
import com.jl.common.TimeUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.Client;
import com.jl.entity.ClientPriceLevel;
import com.jl.entity.DepartmentLevel;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.ClientService;

import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;

public class ClientServiceImpl extends BaseService implements ClientService {

	private final Logger log = Logger.getLogger(ClientServiceImpl.class);

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void loadClientInfoById(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("clientId");
		try {
			Client clientInfo = (Client) commonDAO.findForObject(
					"Client.loadClientInfoById", id);
			request.setAttribute("clientInfo", clientInfo);
			request.setAttribute("now", new Date());
		} catch (Exception e) {
			log.error("加载数据失败!", e);
		}
	}

	public void queryClientInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map conditionMap = new HashMap();
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		if (paramJson.containsKey("userCode")) {
			conditionMap.put("userCode", paramJson.getString("userCode"));
		}
		if (paramJson.containsKey("departmentId")) {
			conditionMap.put("departmentId", paramJson
					.getString("departmentId"));
		}
		String start = request.getParameter("start");// 开始索引
		String limit_ = request.getParameter("limit");// 开始索引
		int limit = Integer.parseInt(limit_);
		PageInfo obj = new PageInfo();
		if (StringUtils.isNotEmpty(start)) {
			obj.setCurrentPage(Integer.parseInt(start) / limit + 1);
		} else {
			obj.setCurrentPage(1);
		}
		obj.setNumPerPage(limit);

		try {
			obj = commonDAO.selectForPage("User.queryUserPageCount",
					"User.queryUserResultPage", conditionMap, obj);
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
			log.error("出错了!", e);
		}
	}

	public void saveOrUpdateClientInfo(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String clientId = request.getParameter("clientId");
		Client clientInfo = this.getClientBean(request);// 获取实例

		try {
			Integer checkNum = (Integer) commonDAO.findForObject(
					"Client.selectByClientTag", clientInfo);
			if (checkNum > 0) {
				json.put("error", "yes");
				json.put("tip", "保存失败,存在重复的客户标识!");
				return;
			}
			if (StringUtils.isNotEmpty(clientId)) {// 根据Id 来判断是 否是修改还是插入
				clientInfo = (Client) commonDAO.update(
						"Client.updateClientInfo", clientInfo);

				// 同步客户隶属业务主任信息
				String operationDirectorId = request
						.getParameter("operationDirector");
				String operationDirectorCode = request
						.getParameter("operationDirectorCode");
				String operationDirectorName = request
						.getParameter("operationDirectorName");
				if (StringUtils.isNotEmpty(operationDirectorId)) {
					DepartmentLevel dl = new DepartmentLevel();
					dl.setDepartmentLevel(BussVar.BUSSTYPE_X);// 业务主任
					dl.setDepartmentLevelItemId(clientId);
					dl.setDepartmentLevelId(operationDirectorId.trim());
					dl.setDepartmentLevelCode(operationDirectorCode.trim());
					dl.setDepartmentLevelName(operationDirectorName.trim());
					dl.setOrders(0);
					Integer isExist = (Integer) commonDAO.findForObject(
							"Department.findTreeLevelRelationIsExist", dl);
					if (isExist > 0) {
						commonDAO.update("Department.buildTreeLevelRelation3",
								dl);
					} else {
						commonDAO.insert("Department.buildTreeLevelRelation2",
								dl);
					}
					// 更新公司横向数据 - 业务主任
					Map dept = new HashMap();
					dept.put("departmentIdx", operationDirectorId.trim());
					dept.put("departmentCodex", operationDirectorCode.trim());
					dept.put("departmentNamex", operationDirectorName.trim());
					dept.put("departmentLevelItemId", clientId);
					updateDepartmentlevelrowX(dept);
				}
				json.put("tip", "保存成功!");
			} else {
				// not do
			}
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "保存失败!");
			log.error("保存信息请求失败", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ----------- 客户级别管理 DON 2010-1-6
	public void findClientLevelTree(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");

		try {
			if ("0".equals(node)) {// 根节点
				Collection rootNodeSet = commonDAO.select(
						"Client.loadClientLevelRoot", node);
				String levelJsonStr = buildClientLevelJsonStr(rootNodeSet);
				super.writeJsonStr(response, levelJsonStr);
			} else {
				Collection nodeSet = commonDAO.select(
						"Client.findChildClientLevel", node.trim());
				String levelJsonStr2 = buildClientLevelJsonStr(nodeSet);
				super.writeJsonStr(response, levelJsonStr2);
			}
		} catch (Exception e) {
			log.error("出错", e);
		}

	}

	public void loadClientLevelOfRelation(HttpServletRequest request,
			HttpServletResponse response) {
		String related = request.getParameter("related");// 1.已关联 0.未关联
		try {
			Collection clientInfoResult = new ArrayList();
			if ("1".equals(related)) {
				String levelCode = request.getParameter("id");// 级别
				clientInfoResult = commonDAO.select(
						"Client.findClientLevelOfRelated", levelCode);
			} else if ("0".equals(related)) {
				String departmentCode = request.getParameter("id");// 部门
				Map map = new HashMap();
				if (StringUtils.isNotEmpty(departmentCode)) {
					map.put("departmentCode", departmentCode.trim());
				}
				clientInfoResult = commonDAO.select(
						"Client.findClientLevelOfNotRelated", map);
			}

			String[] properties = { "clientId", "clientCode", "clientName" };
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = clientInfoResult.iterator(); iterator
					.hasNext();) {
				Client clientInfo = (Client) iterator.next();

				String jsonStr = JSONUtil2.fromBean(clientInfo, properties,
						null, "yyyy-MM-dd HH:mm").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			StringBuilder store = new StringBuilder();
			store.append("{clientInfo:[");
			store.append(jsonBuffer.toString());
			store.append("]}");

			super.writeJsonStr(response, store.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateClientLevel(HttpServletRequest request,
			HttpServletResponse response) {
		String clientIdList = request.getParameter("clientIdList");
		String levelCode = request.getParameter("levelCode");
		Map conditionMap = new HashMap();
		conditionMap.put("clientId", clientIdList.split(","));
		if (StringUtils.isNotEmpty(levelCode)) {
			conditionMap.put("levelCode", levelCode.trim());// 设置关联级别
		} else {
			conditionMap.put("levelCode", null);// 取消关联级别
		}

		try {
			commonDAO.update("Client.updateClientLevel", conditionMap);
		} catch (Exception e) {
			log.error("批量设置客户级别失败!", e);
		}
	}

	public void saveOrUpdateOrDeleteClientLevelNode(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");// append|modify|remove
		String levelCode = request.getParameter("levelCode");
		String levelName = request.getParameter("levelName");
		String parentLevelCode = request.getParameter("parentLevelCode");
		ClientPriceLevel clientLevel = new ClientPriceLevel();
		if (StringUtils.isNotEmpty(levelName)) {
			clientLevel.setLevelName(levelName);
		}
		try {
			if ("append".equals(type)) {
				if ("".equals(levelCode) || "".equals(parentLevelCode)) {
					clientLevel.setParentLevelCode(null);
				} else {
					clientLevel.setParentLevelCode(parentLevelCode);
				}
				RandomGUID GUID = new RandomGUID();
				clientLevel.setLevelCode(GUID.valueAfterMD5);

				commonDAO.insert("Client.appendClientLevelNode", clientLevel);
			} else if ("modify".equals(type)) {
				clientLevel.setLevelCode(levelCode);
				commonDAO.update("Client.modifyClientLevelNode", clientLevel);
			} else if ("remove".equals(type)) {
				commonDAO.delete("Client.removeClientLevelNode", levelCode);
				commonDAO.delete("Client.removePriceOfClientLevel", levelCode);
			}

		} catch (Exception e) {
			log.error("", e);
		}

	}

	public void getClientInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		String p = request.getParameter("p");
		try {
			if (StringUtils.isNotEmpty(code)) {
				User user = getOnlineUser(request);
				Map condition = new HashMap();
				condition.put("code", code.trim());
				if ("adminx".equals(user.getLevel())) {
					//
				} else if ("s".equals(user.getLevel())) {// 仓库
					condition.put("departmentId", user.getDepartmentLevelRow()
							.getDepartmentIds1());// 在线人员目录隶属
				} else {
					if ("0".equals(p)) {
						if (BussVar.BUSSTYPE_1.equals(user.getLevel())
								|| BussVar.BUSSTYPE_2.equals(user.getLevel())
								|| BussVar.BUSSTYPE_3.equals(user.getLevel())) {// 省公司或营销部或大营销部
							condition
									.put("departmentId", user
											.getDepartmentLevelRow()
											.getDepartmentId1());
						} else {
							condition.put("departmentId", user
									.getDepartmentId());// 在线人员目录隶属
						}
					} else {
						condition.put("departmentId", user.getDepartmentId());// 在线人员目录隶属
					}
				}
				Collection result = commonDAO.select("Client.selectClientInfo",
						condition);
				int total = result.size();
				StringBuffer jsonBuffer = new StringBuffer();
				String split = "";
				for (Iterator iterator = result.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();

					if (map.containsKey("level")){
						String level_ = (String) map.get("level");
						map.put("levelName", BussVar.returnBussType(level_));
					}
					
					String jsonStr = JSONUtil2.fromBean(map).toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);
					split = ",";
				}
				super.writeJsonStr(response, super.buildJsonStr(total,
						jsonBuffer.toString()));
			}
		} catch (Exception e) {
			log.error("出错了", e);
		}
	}

	public void export(HttpServletRequest request, HttpServletResponse response) {
	}

	// -- Private Methods
	/**
	 * 
	 * @param request
	 * @return
	 */
	private Client getClientBean(HttpServletRequest request) {
		Client info = new Client();
		String clientId = request.getParameter("clientId");
		String clientName = request.getParameter("clientName");
		String clientCode = request.getParameter("clientCode");
		String operationDirector = request.getParameter("operationDirector");
		String linkman = request.getParameter("linkman");
		String telphone = request.getParameter("telphone");
		String companyAddress = request.getParameter("companyAddress");
		String servicestation = request.getParameter("servicestation");
		String deliverGoodsAddress = request
				.getParameter("deliverGoodsAddress");
		String remitAccounts = request.getParameter("remitAccounts");
		String openAccountTitle = request.getParameter("openAccountTitle");
		String ratepayingType = request.getParameter("ratepayingType");
		String remitPersion = request.getParameter("remitPersion");
		String remitTime = request.getParameter("remitTime");
		String lyBailMonery = request.getParameter("lyBailMonery");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String clientType = request.getParameter("clientType");
		String marketType = request.getParameter("marketType");
		String clientTag = request.getParameter("clientTag");// 经销商标识
		String tons = request.getParameter("tons");
		String advanceTagPayment =  request.getParameter("advanceTagPayment");

		if (StringUtils.isNotEmpty(clientType)) {
			info.setClientType(clientType.trim());
		}
		if (StringUtils.isNotEmpty(marketType)) {
			info.setMarketType(marketType.trim());
		}

		if (StringUtils.isNotEmpty(clientName)) {
			info.setClientName(clientName.trim());
		}
		if (StringUtils.isNotEmpty(operationDirector)) {
			info.setOperationDirector(operationDirector.trim());
		}
		if (StringUtils.isNotEmpty(linkman)) {
			info.setLinkman(linkman.trim());
		}
		if (StringUtils.isNotEmpty(telphone)) {
			info.setTelphone(telphone.trim());
		}
		if (StringUtils.isNotEmpty(companyAddress)) {
			info.setCompanyAddress(companyAddress.trim());
		}
		if (StringUtils.isNotEmpty(servicestation)) {
			info.setServicestation(servicestation.trim());
		}
		if (StringUtils.isNotEmpty(deliverGoodsAddress)) {
			info.setDeliverGoodsAddress(deliverGoodsAddress.trim());
		}
		if (StringUtils.isNotEmpty(remitAccounts)) {
			info.setRemitAccounts(remitAccounts.trim());
		}
		if (StringUtils.isNotEmpty(openAccountTitle)) {
			info.setOpenAccountTitle(openAccountTitle.trim());
		}
		if (StringUtils.isNotEmpty(ratepayingType)) {
			info.setRatepayingType(ratepayingType.trim());
		}
		if (StringUtils.isNotEmpty(remitPersion)) {
			info.setRemitPersion(remitPersion.trim());
		}
		if (StringUtils.isNotEmpty(remitTime)) {
			info.setRemitTime(TimeUtil.parseDate(remitTime));
		}
		if (StringUtils.isNotEmpty(lyBailMonery)) {
			info.setLyBailMonery(Double.parseDouble(lyBailMonery.trim()));
		}

		if (StringUtils.isNotEmpty(mobile)) {
			info.setMobile(mobile.trim());
		}
		if (StringUtils.isNotEmpty(email)) {
			info.setEmail(email.trim());
		}
		if (StringUtils.isNotEmpty(clientCode)) {
			info.setClientCode(clientCode);
		}
		if (StringUtils.isNotEmpty(clientTag)) {
			info.setClientTag(clientTag);
		}
		if (StringUtils.isNotEmpty(clientId)) {
			info.setClientId(clientId);
		}
		if (StringUtils.isNotEmpty(tons)) {
			info.setTons(Double.parseDouble(tons));
		}
		if (StringUtils.isNotEmpty(advanceTagPayment)){
			info.setAdvanceTagPayment(Double.parseDouble(advanceTagPayment));
		} else {
			info.setAdvanceTagPayment(0.0);
		}
		return info;

	}

	private String buildClientLevelJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			ClientPriceLevel cl = (ClientPriceLevel) itr.next();
			String str = "{id: \"" + cl.getLevelCode() + "\", text:\""
					+ cl.getLevelName() + "\"}";

			jSonSet.add(str);
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";

		return str;
	}

	/**
	 * 更新公司横向数据
	 * 
	 * @param dept
	 * @throws Exception
	 */
	private void updateDepartmentlevelrowX(Map map) throws Exception {
		commonDAO.update("Department.updateDepartmentlevelrowX", map);
	}

	private Report getReport(String id) throws Exception {
		return null;
	}

	private Report getReport_client_(String id) throws Exception {
		return null;
	}
}
