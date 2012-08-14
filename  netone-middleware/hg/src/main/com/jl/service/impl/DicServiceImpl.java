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
import com.jl.common.TimeUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.Dic;
import com.jl.service.BaseService;
import com.jl.service.DicService;

public class DicServiceImpl extends BaseService implements
		DicService {
	/** 日志 */
	private final Logger LOG = Logger.getLogger(DicServiceImpl.class);
	private CommonDAO commonDAO;
	
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	public void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String sid = request.getParameter("sid");
		String[] sidList = sid.split(",");
		List sysConfigList = new ArrayList();
		try {
			for (int i = 0; i < sidList.length; i++) {
				sysConfigList.add(sidList[i]);
			}
			this.commonDAO.deleteBatch("Dic.deleteSystemConfig", sysConfigList);
			json.put("tip", "删除信息成功!");
		} catch (Exception e) {
			json.put("tip", "删除失败，请与管理员联系!");
			json.put("error", "yes");
			LOG.error("删除失败，请与管理员联系!", e);
		}finally{
			super.writeJsonStr(response, json.toString());
		}
	}

	public void loadInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String sid = request.getParameter("sid");
		try {
			if (StringUtils.isNotEmpty(sid)) {
				request.setAttribute("sysconfig", this.commonDAO.findForObject(
						"Dic.loadSystemConfig", sid));
			}
		} catch (Exception e) {
			LOG.error("加载失败！", e);
		}
	}

	public void queryInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		String type = paramJson.getString("type");
		
		String start = request.getParameter("start");// 开始索引
		String limit_ = request.getParameter("limit");// 结束索引
		Integer limit = Integer.parseInt(limit_);
		PageInfo obj = new PageInfo();
		if (StringUtils.isNotEmpty(start)) {
			obj.setCurrentPage(Integer.parseInt(start) / limit + 1);
		} else {
			obj.setCurrentPage(1);
		}
		
		obj.setNumPerPage(limit);
		Map conditionMap = new HashMap();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		if (StringUtils.isNotEmpty(type)) {
			conditionMap.put("type", type);
		}
		try {
			obj = this.commonDAO.selectForPage("Dic.totalPageByCondition",
					"Dic.resultPageByCondition", conditionMap, obj);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map mapx = (Map) iterator.next();
				String jsonStr = JSONUtil2.fromBean(mapx, "yyyy-MM-dd HH:mm:ss").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer.toString()));
		} catch (Exception e) {
			LOG.error("出错了!", e);
		}
	}

	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		Dic config = this.getSystemConfig(request);// 提交参数包装
		try {
			if (StringUtils.isNotEmpty(config.getSid())) {
				this.commonDAO.update("Dic.updateSystemConfig", config);
			} else {
				config = (Dic) this.commonDAO.insert(
						"Dic.insertSystemConfig", config);
			}
			json.put("tip", "保存信息成功!");
		} catch (Exception e) {
			json.put("tip", "保存信息操作失败，请与管理员联系!");
			json.put("error", "yes");
			LOG.error("保存信息操作失败，请与管理员联系!", e);
			throw new RuntimeException("保存信息操作失败，请与管理员联系!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void loadSystemConfigToSelect(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(type)) {
			conditionMap.put("type", type);
		}
		Collection coll = null;
		String split = "";
		StringBuffer jSonBuf = new StringBuffer();
		try {
			coll = (Collection) this.commonDAO.select("Dic.loadSystemConfigToSelect", conditionMap);
			for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
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
	
	private Dic getSystemConfig(HttpServletRequest request){
		Dic result = new Dic();
		//Department olmgr = getOnlineUser(request);// 取当前用户
		super.setJavaBean(request, result);
		
		result.setOperate("adminx");// 取当前用户名
		result.setOperateTime(TimeUtil.parseDateTime(TimeUtil
				.formatDateTime(new Date())));
		return result;
	}
}
