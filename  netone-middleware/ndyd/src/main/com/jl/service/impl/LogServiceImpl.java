/**
 * 
 */
package com.jl.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.ApplicationContext;
import com.jl.common.JSONUtil2;
import com.jl.dao.LogDAO;
import com.jl.entity.Log;
import com.jl.service.BaseService;
import com.jl.service.LogService;

/**
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 30, 2010 create by Don
 * @history
 */
public class LogServiceImpl extends BaseService implements LogService {
	/** 日志 */
	private final Logger LOG = Logger.getLogger(LogServiceImpl.class);

	private LogDAO logDAO;

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	public void queryLog(HttpServletRequest request,
			HttpServletResponse response) {

		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		String mode = request.getParameter("mode");
		String userId = paramJson.getString("userId");
		String beginDate = paramJson.getString("beginDate");
		String endDate = paramJson.getString("endDate");
		String operateId = paramJson.getString("operateId");
		String logseq = paramJson.getString("logseq");
		String remark = paramJson.getString("remark");
		String operateIdDetail = paramJson.getString("operateIdDetail");
		String salesDeptList = "";
		if (paramJson.containsKey("salesDeptList")) {
			salesDeptList = paramJson.getString("salesDeptList");
		}

		Map map = new HashMap();
		if (StringUtils.isNotEmpty(userId)) {
			map.put("userId", userId.trim());
		}
		if (StringUtils.isNotEmpty(beginDate)) {
			map.put("beginDate", beginDate.trim());
		}
		if (StringUtils.isNotEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (StringUtils.isNotEmpty(salesDeptList)) {
			map.put("salesDeptList", salesDeptList.trim());
		}
		if (StringUtils.isNotEmpty(logseq)) {
			map.put("logseq", logseq.trim());
		}
		if (StringUtils.isNotEmpty(remark)) {
			map.put("remark", remark.trim());
		}
		String operateItem = "";
		if (StringUtils.isNotEmpty(operateId)) {
			operateItem = operateId.trim();
		}
		if (StringUtils.isNotEmpty(operateIdDetail)) {
			if ("未知操作".equals(operateIdDetail)) {
				operateItem = operateIdDetail;
			} else {
				operateItem = operateIdDetail + operateItem;
			}
		}
		if (StringUtils.isNotEmpty(operateItem)) {
			map.put("operateItem", operateItem.trim());
		}

		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");
		map.put("startIndex", start);
		map.put("pageSize", limit);
		PageInfo obj = new PageInfo();
		try {
			String[] excludesSeqProperties = null;// 排除日志明细
			ApplicationContext context = (ApplicationContext) WebApplicationContextUtils
					.getRequiredWebApplicationContext(
							request.getSession().getServletContext()).getBean(
							"appContext");
			Map logFieldMap = context.getLogField();
			if ("admin".equalsIgnoreCase(mode)) {// 管理员模式
				excludesSeqProperties = null;
			} else if ("admin2".equalsIgnoreCase(mode)) {// 管理员 模式2 - 程序员模式
				excludesSeqProperties = null;
				logFieldMap = new HashMap();
			} else {// 普通模式
				String[] excludesSeqProperties3 = { "codeCheck",
						"remittanceTime", "querType", "otherNote", "typeO",
						"allPriceCount", "categoriesId", "JavaMethod", "method" };
				excludesSeqProperties = excludesSeqProperties3;
			}

			String[] properties = { "logId", "userId", "userName",
					"operateTime", "operateId", "resultInfo", "logSeq",
					"userIp", "userHost", "userAgent", "remark" };
			String[] excludesRemarkProperties = { "AwardSum", "SellAwardSum",
					"AwardBalances" };// 排除日志备注
			obj = this.logDAO.findLog(map);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Log log = (Log) iterator.next();
				log.setLogSeq(parseLogSeq(log.getLogSeq(), log.getOperateId(),
						logFieldMap, null, excludesSeqProperties));
				log.setRemark(parseRemark(log.getRemark(), log.getOperateId(),
						logFieldMap, null, excludesRemarkProperties));

				String jsonStr = JSONUtil2.fromBean(log, properties, null,
						"yyyy-MM-dd HH:mm:ss").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			LOG.error("获取日志信息出错!", e);
		}
	}

	public void queryLog2(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String operateId = request.getParameter("operateId");
		String operateIdDetail = request.getParameter("operateIdDetail");
		String salesDeptList = request.getParameter("salesDeptList");
		String logseq = request.getParameter("logseq");
		String remark = request.getParameter("remark");

		Map map = new HashMap();
		if (StringUtils.isNotEmpty(userId)) {
			map.put("userId", userId.trim());
		}
		if (StringUtils.isNotEmpty(beginDate)) {
			map.put("beginDate", beginDate.trim());
		}
		if (StringUtils.isNotEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (StringUtils.isNotEmpty(salesDeptList)) {
			map.put("salesDeptList", salesDeptList.trim());
		}
		if (StringUtils.isNotEmpty(logseq)) {
			map.put("logseq", logseq.trim());
		}
		if (StringUtils.isNotEmpty(remark)) {
			map.put("remark", remark.trim());
		}
		String operateItem = "";
		if (StringUtils.isNotEmpty(operateId)) {
			operateItem = operateId.trim();
		}
		if (StringUtils.isNotEmpty(operateIdDetail)) {
			if ("未知操作".equals(operateIdDetail)) {
				operateItem = operateIdDetail;
			} else {
				operateItem = operateIdDetail + operateItem;
			}
		}
		if (StringUtils.isNotEmpty(operateItem)) {
			map.put("operateItem", operateItem.trim());
		}

		String page = request.getParameter("page");// The page number
		String rows = request.getParameter("rows");// The page rows per page.
		map.put("startIndex", page);
		map.put("pageSize", rows);
		PageInfo obj = new PageInfo();
		try {
			String[] properties = { "logId", "userId", "userName",
					"operateTime", "operateId", "resultInfo", "logSeq",
					"userIp", "userHost", "userAgent", "remark" };
			obj = this.logDAO.findLog(map);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";

			ApplicationContext context = (ApplicationContext) WebApplicationContextUtils
					.getRequiredWebApplicationContext(
							request.getSession().getServletContext()).getBean(
							"appContext");
			Map logFieldMap = context.getLogField();

			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Log log = (Log) iterator.next();

				log.setLogSeq(parseLogSeq(log.getLogSeq(), log.getOperateId(),
						logFieldMap, null, null));

				String jsonStr = JSONUtil2.fromBean(log, properties, null,
						"yyyy-MM-dd HH:mm:ss").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			LOG.error("获取日志信息出错!", e);
		}

	}

	public void queryDailyLog(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	// Private Methods
	/**
	 * 解析日志序列明细<BR>
	 * 区别大小写
	 * 
	 * @param logSeq
	 * @param operateId
	 * @param logSeqMap
	 * @param includes
	 * @param excludes
	 * @return
	 */
	private String parseLogSeq(String logSeq, String operateId, Map logSeqMap,
			String[] includes, String[] excludes) {
		StringBuffer str = new StringBuffer();
		if (StringUtils.isNotEmpty(logSeq)) {
			try{
			JSONObject json = (JSONObject) JSONUtil2.fromBean(logSeq, includes,
					excludes, "yyyy-MM-dd");
			if (json!=null){
				if (json.size() < 0) {
					return null;
				}
				str
						.append("<table border='1' cellPadding='0' bordercolor='gray' style='border-collapse: collapse;font-size: 14'>");
				// 目前只支持三层解析
				for (Iterator iter = json.keys(); iter.hasNext();) {
					String key = (String) iter.next();
					// 替换
					String keyParse = StringUtils.replace(key, key,
							(String) logSeqMap.get(key));
					Object jsonValue = (Object) json.get(key);
					if (jsonValue instanceof net.sf.json.JSONArray) {// JSONArray
						str.append("<tr><th colspan='2' align='center'>" + keyParse
								+ "</th></tr>");
						net.sf.json.JSONArray detailJson = (net.sf.json.JSONArray) jsonValue;
						if (detailJson.size() > 0) {
							str.append("<tr><th colspan='2'>");
							str
									.append("<table border='1' cellPadding='0' bordercolor='gray' style='border-collapse: collapse;font-size: 14'>");
							for (Iterator iterator = detailJson.iterator(); iterator
									.hasNext();) {
								Object object = (Object) iterator.next();
								if (object instanceof net.sf.json.JSONObject) {// 二层
									JSONObject _object = (JSONObject) object;
									if ("null".equals(_object) || _object == null
											|| _object.isNullObject()
											|| _object.isEmpty()) {
									} else {
										for (Iterator iterx = _object.keys(); iterx
												.hasNext();) {
											String keyx = (String) iterx.next();
											String keyParsex = StringUtils.replace(
													keyx, keyx, (String) logSeqMap
															.get(keyx));
											Object valuex = (Object) _object
													.get(keyx);
											str.append("<tr><th>" + keyParsex
													+ "</th><th>" + valuex
													+ "</th></tr>");
										}
									}
								} else if (object instanceof net.sf.json.JSONArray) {// 三层
									net.sf.json.JSONArray detailJsonx = (net.sf.json.JSONArray) object;
									if (detailJsonx.size() > 0) {
										for (Iterator iteratorx = detailJsonx
												.iterator(); iteratorx.hasNext();) {
											Object objectx = (Object) iteratorx
													.next();
											if (objectx instanceof net.sf.json.JSONObject) {
												JSONObject _object = (JSONObject) objectx;
												if ("null".equals(_object)
														|| _object == null
														|| _object.isNullObject()
														|| _object.isEmpty()) {
												} else {
													for (Iterator iterx = _object
															.keys(); iterx
															.hasNext();) {
														String keyx = (String) iterx
																.next();
														// 替换
														String keyParsex = StringUtils
																.replace(
																		keyx,
																		keyx,
																		(String) logSeqMap
																				.get(keyx));
														Object valuex = (Object) _object
																.get(keyx);
														str.append("<tr><th>"
																+ keyParsex
																+ "</th><th>"
																+ valuex
																+ "</th></tr>");
													}
												}
											} else {
												str.append("<tr><th colspan='2'>"
														+ objectx + "</th></tr>");
											}
										}
									}
								}// End If 三层
							}// End If 二层或三层
							str.append("</table>");
							str.append("</th></tr>");
						}
					} else {// String 一层
						str.append("<tr><th>" + keyParse + "</th><th>" + jsonValue
								+ "</th></tr>");
					}
				}
				str.append("</table>");
			} else {
				str.append(logSeq);
			}
			} catch(Exception e){
				str.append(logSeq);
			}
			
		} else {
			return "";
		}

		return str.toString();
	}

	/**
	 * 解析备注字段<BR>
	 * 区别大小写
	 * 
	 * @param remark
	 * @param operateId
	 * @param logSeqMap
	 * @param includes
	 * @param excludes
	 * @return
	 */
	private String parseRemark(String remark, String operateId, Map logSeqMap,
			String[] includes, String[] excludes) {
		StringBuffer str = new StringBuffer();
		if (StringUtils.isNotEmpty(remark)) {
			JSONArray jsonArray = JSONUtil2.fromBeanToJsonArray(remark,
					includes, excludes, "yyyy-MM-dd");
			if (jsonArray.size() > 0) {
				str
						.append("<table border='1' cellPadding='0' bordercolor='gray' style='border-collapse: collapse;font-size: 14'>");
				for (Iterator iterator = jsonArray.iterator(); iterator
						.hasNext();) {
					Object object = (Object) iterator.next();
					if (object instanceof net.sf.json.JSONObject) {
						JSONObject _object = (JSONObject) object;
						if ("null".equals(_object) || _object == null
								|| _object.isNullObject() || _object.isEmpty()) {
						} else {
							for (Iterator iterx = _object.keys(); iterx
									.hasNext();) {
								String keyx = (String) iterx.next();
								// 替换
								String keyParsex = StringUtils.replace(keyx,
										keyx, (String) logSeqMap.get(keyx));
								Object valuex = (Object) _object.get(keyx);
								str.append("<tr><th>" + keyParsex + "</th><th>"
										+ valuex + "</th></tr>");
							}
						}
					} else {
						str.append("<tr><th colspan='2'>" + object
								+ "</th></tr>");
					}
				}
				str.append("</table>");
			} else {
				return "";
			}
		}
		return str.toString();
	}
}
