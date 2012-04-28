/**
 * 
 */
package com.jl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.dao.CommonDAO;
import com.jl.entity.Yijian;
import com.jl.service.BaseService;
import com.jl.service.YijianService;

public class YijianServiceImpl extends BaseService implements YijianService {

	private final Logger LOG = Logger.getLogger(YijianServiceImpl.class);

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String delete(String unid) throws Exception {
		JSONObject json = new JSONObject();
		commonDAO.delete("Yijian.delete", unid);
		json.put("tip", "删除成功");
		return json.toString();
	}

	public String insert(String userid, String yijian) throws Exception {
		Yijian bean = new Yijian();
		bean.setUserid(userid);
		bean.setYijian(yijian.trim());

		Integer co = (Integer) commonDAO.findForObject("Yijian.findx", bean);
		if (co > 0) {
			return null;
		} else {
			bean = (Yijian) commonDAO.insert("Yijian.insert", bean);
			
		}
		JSONObject json = new JSONObject();
		json = json.fromObject(bean);
		json.put("tip", "插入成功");
		return json.toString();
	}

	public String load(String userid) throws Exception {
		Collection coll = new ArrayList();
		String split = "";
		StringBuffer jSonBuf = new StringBuffer();
		coll = (Collection) this.commonDAO.select("Yijian.load", userid);
		for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
			Yijian object = (Yijian) iterator.next();
			String jsonStr = JSONUtil2.fromBean(object, "yyyy-MM-dd HH:mm:ss")
					.toString();
			jSonBuf.append(split + jsonStr);
			split = ",";
		}
		String jsonStr2 = "[" + jSonBuf + "]";
		return jsonStr2;
	}

}
