package com.jl.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;

public class FrameResource {

	/**
	 * 
	 * @param id
	 * @param isexpand
	 *            是否默认全部打开
	 * @param rootShowcheck
	 *            根目录是否可选择
	 * @return
	 */
	public static String buildRootTreeRelation(String id, boolean isexpand,
			boolean rootShowcheck, String usercode) {
		String jsonx = buildChildTreeRelation(id, isexpand, usercode);
		String jsondata = "[" + jsonx + "]";
		return jsondata;
	}

	/**
	 * 
	 * @param pid
	 * @param isexpand
	 *            是否默认全部打开
	 * @param checkedmap
	 * @return
	 */
	public static String buildChildTreeRelation(String pid, boolean isexpand,
			String usercode) {
		List<UmsProtectedobject> set = findByPId(pid, usercode);
		if (set.size() == 0)
			return "";
		StringBuilder str = new StringBuilder();

		for (UmsProtectedobject r : set) {
			str.append("{");
			str.append("\"id\"");
			str.append(":\"");
			str.append(r.getNaturalname());
			str.append("\",");

			str.append("\"text\"");
			str.append(":\"");
			str.append(r.getName());
			str.append("\",");

			str.append("\"attributes\":{");
			str.append("	\"linkurl\":\"" + r.getActionurl() + "\"");
			str.append("},");

			if ("normal-close".equals(r.getExtendattribute())) {
				isexpand = false;
			}

			// JSON json = JSONUtil2.fromBean(r, "yyyy-MM-dd HH:mm:ss");
			// str.append(StringUtils.substringBetween(json.toString(), "{",
			// "}")
			// + ",");

			str.append("\"showcheck\":true,\"isexpand\": " + isexpand
					+ ", \"complete\": true");

			String jsonx = buildChildTreeRelation(r.getNaturalname(), isexpand,
					usercode);
			if (!"".equals(jsonx)) {
				str.append(",\"state\":" + (isexpand ? false : "\"closed\""));

				str.append(",\"hasChildren\"");
				str.append(":");
				str.append("true");

				str.append(",\"children\":[");
				str.append(jsonx);
				str.append("]},");
			} else {
				str.append(",\"hasChildren\"");
				str.append(":");
				str.append("false");
				str.append(",\"children\":null");
				str.append("},");
			}

		}

		return str.substring(0, str.length() - 1);
	}

	public static List<UmsProtectedobject> findByPId(String naturalname,
			String usercode) {
		List list = new ArrayList();
		List listx = null;
		ResourceRmi resourceRmi = null;
		try {
			listx = SecurityEntry.iv().listDirRs(naturalname);
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			UmsProtectedobject upjid = null;

			try {
				upjid = resourceRmi.loadResourceByNatural(name
						.getResourcecode());
				boolean perm = SecurityEntry.iv().permission(usercode,
						upjid.getNaturalname());

				// no.setName(upjid.getName());
				// no.setNaturalname(upjid.getNaturalname());
				// no.setUrl(upjid.getActionurl());

				if (perm)
					list.add(upjid);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}
}
