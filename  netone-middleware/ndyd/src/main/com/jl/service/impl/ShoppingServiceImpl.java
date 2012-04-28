/**
 * 
 */
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.frame.web.util.WebStr;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.TimeUtil;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfConsoleImpl;
import com.jl.common.workflow.TWfWorklistExt;
import com.jl.common.workflow.WfEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.CensorShip;
import com.jl.entity.CensorShipLog;
import com.jl.entity.CensorShipStatus;
import com.jl.entity.Shopping;
import com.jl.entity.ShoppingDetail;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.ShoppingService;

public class ShoppingServiceImpl extends BaseService implements ShoppingService {

	private final Logger LOG = Logger.getLogger(ShoppingServiceImpl.class);

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void loadShopping(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("shoppingId");
		Shopping Shopping = new Shopping();
		try {
			User user = getOnlineUser(request);
			if (StringUtils.isNotEmpty(id)) {
				Shopping = (Shopping) commonDAO.findForObject(
						"Shopping.selectShoppingById", id);
			} else {
			}
			request.setAttribute("cid", user.getUserCode());
			request.setAttribute("cname", user.getDepartmentName());
			request.setAttribute("shopping", Shopping);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void queryShoppingForPage(HttpServletRequest request,
			HttpServletResponse response) {
		User user = getOnlineUser(request);

		Map conditionMap = new HashMap();
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage("Shopping.findShoppingPageCount",
					"Shopping.findShoppingForPage", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Shopping shop = (Shopping) iterator.next();
				// shop.setType(BussVar.returnPlanType(plan.getType()));

				String jsonStr = JSONUtil2
						.fromBean(shop, "yyyy-MM-dd HH:mm:ss").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			LOG.error("获取请购单信息出错!", e);
		}

	}

	public void saveShoppingAndDetail(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String shoppingId = request.getParameter("shoppingId");
		Shopping shop = this.getShopping(request);
		try {
			if (StringUtils.isNotEmpty(shoppingId)) {
				shop.setShoppingId(shoppingId.trim());
				shop = (Shopping) commonDAO.update("Shopping.updateShopping",
						shop);
			} else {
				shop.setStatus(0);
				shop = (Shopping) this.commonDAO.insert(
						"Shopping.insertShopping", shop);
				request.setAttribute("shoppingId", shop.getShoppingId());
			}
			String shoppingDetailInfo = request
					.getParameter("shoppingDetailInfo");
			ShoppingDetail shopDetail = new ShoppingDetail();
			shopDetail.setShoppingId(shop.getShoppingId());
			this.commonDAO.delete("Shopping.deleteAllByShoppingId", shop
					.getShoppingId());

			JSONArray jsonArray = JSONArray.fromObject(shoppingDetailInfo);
			List result = new ArrayList();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String deviceName = obj.getString("deviceName");
				String units = obj.getString("units");
				String consignmentCount = obj.getString("consignmentCount");
				String unitPrice = obj.getString("unitPrice");
				String hopeDeliveryTime_ = obj.getString("hopeDeliveryTime")
						.replace("T00:00:00", "");
				Date hopeDeliveryTime = TimeUtil.parseDate(hopeDeliveryTime_);
				String suggestBrand = obj.getString("suggestBrand");
				String note = obj.getString("note");
				ShoppingDetail _shopDetail = new ShoppingDetail();
				_shopDetail.setDeviceName(deviceName);
				_shopDetail.setShoppingId(shop.getShoppingId());
				_shopDetail.setUnits(units);
				_shopDetail.setConsignmentCount(consignmentCount);
				_shopDetail.setUnitPrice(unitPrice);
				_shopDetail.setHopeDeliveryTime(hopeDeliveryTime);
				_shopDetail.setSuggestBrand(suggestBrand);
				if (StringUtils.isNotEmpty(note)) {
					_shopDetail.setNote(note);
				}
				result.add(_shopDetail);
			}
			this.commonDAO.insertBatch("Shopping.insertShoppingDetail",
					result);
			json.put("shoppingId", shop.getShoppingId());
			json.put("tip", "保存信息成功!");
		} catch (Exception e) {
			json.put("tip", "保存信息失败!");
			json.put("error", "yes");
			LOG.error("保存信息失败!", e);
			throw new RuntimeException("保存信息失败，请与管理员联系!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	private Shopping getShopping(HttpServletRequest request) {
		String shoppingId = request.getParameter("shoppingId");
		String purchaseIllustrate = request.getParameter("purchaseIllustrate");
		String clientId = request.getParameter("clientId");
		String operateTime = request.getParameter("operateTime");
		Shopping shop = new Shopping();
		if (StringUtils.isNotEmpty(shoppingId)) {
			try {
				shop = (Shopping) commonDAO.findForObject(
						"Shopping.selectShoppingById", shoppingId.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(purchaseIllustrate)) {
			shop.setPurchaseIllustrate(purchaseIllustrate);
		}
		if (StringUtils.isNotEmpty(clientId)) {
			shop.setClientId(clientId);
		}
		if (StringUtils.isNotEmpty(operateTime)) {
			shop.setOperateTime(TimeUtil.parseDateTime(operateTime));
		}

		return shop;
	}

	public void audit(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("shoppingId");
		String choiceCode = request.getParameter("values");// 选择的客户
		String workcode = request.getParameter("workcode");// 选择的客户
		
		User user = getOnlineUser(request);
		try {
			if (StringUtils.isEmpty(id)) {
			} else {
				id = id.trim();
				
				// TODO

				if (workcode == null||workcode.equals("")) {
					Shopping Shopping = new Shopping();
					Shopping.setShoppingId(id);
					Shopping.setStatus(1);
					commonDAO.update("Shopping.updateShoppingStatus", Shopping);

					String processid = "BUSSWF.BUSSWF.NDYD.GCWZQG";
					String clientId = user.getUserCode();
					String mode = "1";
					String ddid = id;
					String ddurl = "shopping/shopping.do?method=onEditView&shoppingId=";
					String runtimeid = WfEntry.iv().newProcess(processid,
							clientId, mode, "", ddid, ddurl);

					WfEntry.iv().runProcess(runtimeid);

					String participant = choiceCode;

//					WfEntry.iv().nextByRuntimeid(runtimeid, user.getUserCode());

//					WfEntry.iv().specifyParticipantByRuntimeid(runtimeid,
//							participant);

					// 打开新窗口 直接在页面处理 _auditNext
				} else {
//					WfEntry.iv().next(workcode, user.getUserCode());
					String runtimeid=WfEntry.iv().getRuntimeIdByWorkcode(workcode);
//					WfEntry.iv().specifyParticipantByRuntimeid(runtimeid,
//							choiceCode);
				}

				json.put("tip", "提交下一处理成功!");

				// WfEntry.iv().getRuntimeIdByWorkcode(workcode)

			}
		} catch (Exception e) {
			json.put("tip", "提交下一处理失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("shoppingId");
		try {
			commonDAO.delete("Shopping.deleteAllByShoppingId", id);
			commonDAO.delete("Shopping.deleteShopping", id);
			json.put("tip", "删除成功");
		} catch (Exception e) {
			json.put("tip", "删除失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void queryShoppingDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String shoppingId = request.getParameter("shoppingId");
		try {
			if (StringUtils.isEmpty(shoppingId)) {
				return;
			}

			Collection result = commonDAO.select(
					"Shopping.queryShoppingDetail", shoppingId);
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				ShoppingDetail ShoppingDetail = (ShoppingDetail) iterator
						.next();

				String jsonStr = JSONUtil2.fromBean(ShoppingDetail,
						"yyyy-MM-dd").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}

			request.setAttribute("shoppingDetail", result);
			super.writeJsonStr(response, super.buildJsonStr(0, jsonBuffer
					.toString()));
		} catch (Exception e) {
			LOG.error("获取明细信息出错!", e);
		}
	}

	public void loadWFInfoToSelect(HttpServletRequest request,
			HttpServletResponse response) {
		Collection coll = null;
		String split = "";
		StringBuffer jSonBuf = new StringBuffer();
		try {
			String workcode = request.getParameter("workcode");
			if(StringUtils.isEmpty(workcode)){
				System.out.println("workcode为空!");
				return;
			}
			String runtimeid=WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			List list=WfEntry.iv().listAllDoneWorklistByRuntimeid(runtimeid);
			for (int i = 0; i < list.size(); i++) {
				TWfWorklistExt object = (TWfWorklistExt) list.get(i);
				String jsonStr = JSONUtil2.fromBean(object, "yyyy-MM-dd HH:mm:ss").toString();
				jSonBuf.append(split + jsonStr);
				split = ",";
			}
			String jsonStr2 = "[" + jSonBuf + "]";
			super.writeJsonStr(response, jsonStr2);
		} catch (Exception e) {
			LOG.error("出错了!", e);
		}
	}

	public void rollBackEvent(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		
		User user = getOnlineUser(request);
		String _workcode = request.getParameter("_workcode");
		String info = request.getParameter("idea_info");
		String activeid = request.getParameter("rollBackInfo");
		String clientId= user.getUserCode();
		try {
			WebStr.encode(request, info); // 转码
			TWfConsoleIfc tc = new TWfConsoleImpl();
			tc.saveAuditNote(_workcode,clientId, info);
			
//			WfEntry.iv().jump(_workcode, clientId, activeid);
			json.put("tip", "退办/特送成功!");
		} catch (Exception e) {
			json.put("tip", "退办/特送失败!");
			json.put("error", "yes");
			LOG.error("退办/特送失败!", e);
			throw new RuntimeException("退办/特送失败，请与管理员联系!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		
	}
}
