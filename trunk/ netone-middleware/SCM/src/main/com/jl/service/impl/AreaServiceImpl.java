/**
 * 
 */
package com.jl.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.common.CommonUploadUtil;
import com.jl.common.JxlUtilsTemplate;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.entity.Area;
import com.jl.entity.Department;
import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.dao.CommonDAO;
import com.jl.service.AreaService;
import com.jl.service.BaseService;
import com.lucaslee.report.model.Report;

/**
 * 行政区划业务处理
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class AreaServiceImpl extends BaseService implements AreaService {
	/** 日志 */
	private final Logger log = Logger.getLogger(AreaServiceImpl.class);

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void queryArea(HttpServletRequest request,
			HttpServletResponse response) {
		String level = request.getParameter("l");
		String parentAreaId = request.getParameter("pid");
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
		if (StringUtils.isNotEmpty(parentAreaId)) {
			conditionMap.put("areaId", parentAreaId.trim());
		}
		if (StringUtils.isNotEmpty(list)) {
			conditionMap.put("list", list.trim());
		}
		conditionMap.put("forPage", 1);
		try {
			obj = commonDAO.selectForPage(
					"Area.queryAreaDetailInfoForPageCount",
					"Area.queryAreaDetailInfoForPage", conditionMap, obj);
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

	public void saveOrUpdateArea(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		Area area = this.getBean(request);// 获取实例
		String areaId = request.getParameter("areaId");
		String bussType = request.getParameter("bussType");
		List<Area> levelList = new ArrayList<Area>();
		int nlevel = 0;// 当前树所在级别
		try {

			// 检查编码是否重复
			Integer isexist = (Integer) commonDAO.findForObject(
					"Area.findCodeIsExist", area);
			if (isexist > 0) {
				json.put("tip", "编码出现重复!");
				json.put("error", "yes");
				return;
			}
			if (StringUtils.isNotEmpty(areaId)) {// 根据Id 来判断是 否是修改还是插入
				// 存在,则修改 Id 不存在 则保存\
				area.setAreaId(areaId);
				area = (Area) commonDAO.update("Area.updateArea", area);

				// 更新本节点
				nlevel = findTreeRelation(levelList, areaId, ++nlevel);
				buildTreeRelation(levelList, areaId, nlevel, bussType);
				log.debug("修改当前节点ID--" + areaId);

				// 更新其下所有节点
				buildChildTreeRelation(areaId);
			} else {
				Area _area = (Area) commonDAO.insert("Area.insertArea", area);
				areaId = _area.getAreaId();
				log.debug("新增ID--" + _area.getAreaId());

				nlevel = findTreeRelation(levelList, areaId, ++nlevel);
				buildTreeRelation(levelList, areaId, nlevel, bussType);
			}
			json.put("tip", "保存信息成功!");
		} catch (Exception e) {
			json.put("tip", "保存信息操作失败，请与管理员联系!");
			json.put("error", "yes");
			log.error("保存信息失败!", e);
			throw new RuntimeException("保存信息失败!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void deleteArea(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String areaId = request.getParameter("areaId");
		try {
			commonDAO.delete("Area.deleteArea", areaId);
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

	public void findAreaTree(HttpServletRequest request,
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
				set = commonDAO.select("Area.loadAreaRoot", node);
			} else {
				Map map = new HashMap();
				map.put("areaId", node);
				String areaCode = request.getParameter("areaCode");
				map.put("areaCode", areaCode);
				set = commonDAO.select("Area.findChildArea", map);
			}
			str = buildAreaJsonStr(set);
		} catch (Exception e) {
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, str);
		}
	}

	public void findAreaADepartmentRelation(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String departmentCode = request.getParameter("departmentCode");
		Map conditionMap = new HashMap();
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 开始索引
		PageInfo obj = new PageInfo();
		conditionMap.put("areaId", node);
		if (StringUtils.isNotEmpty(departmentCode)) {
			conditionMap.put("departmentCode", departmentCode.trim());
		}
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = commonDAO.selectForPage(
					"Area.findAreaADepartmentRelationByCount",
					"Area.findAreaADepartmentRelation", conditionMap, obj);
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

	public void getArea(HttpServletRequest request, HttpServletResponse response) {
		String areaId = request.getParameter("areaId");
		try {
			Area area = (Area) commonDAO.findForObject("Area.selectInfo",
					areaId);
			request.setAttribute("area", area);
		} catch (Exception e) {
			log.error("加载失败!", e);
		}

	}

	public void importAreaInfo(HttpServletRequest request,
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
					if (object.length == 5) {// 当前的表格的数据列数是否一置
						Area areaInfo = new Area();
						areaInfo.setAreaId(object[0].toString().trim());
						areaInfo.setMap(object[4].toString().trim());
						list.add(areaInfo);
					}
				}
				this.commonDAO.updateBatch("Area.importMap", list);
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

	public void exportAreaInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String format = request.getParameter("format");
		String level = request.getParameter("l");
		String parentAreaId = request.getParameter("pid");
		String code = request.getParameter("code");
		String list = request.getParameter("list");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(level)) {
			conditionMap.put("level", level.trim());
		}
		if (StringUtils.isNotEmpty(code)) {
			conditionMap.put("code", code.trim());
		}
		if (StringUtils.isNotEmpty(parentAreaId)) {
			conditionMap.put("areaId", parentAreaId.trim());
		}
		if (StringUtils.isNotEmpty(list)) {
			conditionMap.put("list", list.trim());
		}
		conditionMap.put("forPage", 0);
		try {
			GroupReport groupReport = new GroupReport();
			groupReport.format(format, "行政区域坐标信息", getReport(conditionMap),
					response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出信息出错!", e);
		}
	}

	public void relateAreaADepartment(HttpServletRequest request,
			HttpServletResponse response) {
		String t = request.getParameter("t");
		String areaId = request.getParameter("areaId");
		String departmentId = request.getParameter("departmentId");
		Map map = new HashMap();
		map.put("areaId", areaId);
		map.put("departmentId", departmentId);
		JSONObject json = new JSONObject();
		try {
			if ("add".equalsIgnoreCase(t)) {
				Integer count = (Integer) commonDAO.findForObject(
						"Area.findAreaADepartmentIsExist", map);
				if (count > 0) {
					json.put("tip", "该公司已经被关联了!");
					json.put("error", "yes");
					return;
				} else {
					commonDAO.insert("Area.appendAreaADepartmentRelation", map);
				}
				json.put("tip", "操作成功!");
			} else if ("multAdd".equalsIgnoreCase(t)) {
				commonDAO.insert("Area.multAppendAreaADepartmentRelation", map);
				json.put("tip", "操作成功!");
			} else if ("remove".equalsIgnoreCase(t)) {
				commonDAO.delete("Area.removeAreaADepartmentRelation", map);
				json.put("tip", "操作成功!");
			} else if ("multRemove".equalsIgnoreCase(t)) {
				commonDAO.delete("Area.multRemoveAreaADepartmentRelation", map);
				json.put("tip", "操作成功!");
			} else {
				json.put("tip", "没有任何操作!");
			}
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "操作失败!");
			log.error("出错了", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// -- 私有方法

	private String buildAreaJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Area d = (Area) itr.next();
			d.setText(d.getAreaName());
			d.setId(d.getAreaId());
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	private String buildAreaDepartmentJsonStr(Collection col) {
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
	private Area getBean(HttpServletRequest request) {
		Area area = new Area();
		super.setJavaBean(request, area);
		Area parentArea = new Area();
		String parentAreaId = request.getParameter("parentAreaId");
		String areaCode = request.getParameter("areaCode");
		String areaName = request.getParameter("areaName");
		parentAreaId = "0".equals(parentAreaId) ? "" : parentAreaId;
		if (StringUtils.isNotEmpty(parentAreaId)) {
			parentArea.setAreaId(parentAreaId);
			parentArea.setParentArea(parentArea);
			area.setParentAreaId(parentAreaId);
		} else {
			parentArea.setAreaId(null);
			parentArea.setParentArea(null);
			area.setAreaId(null);
			area.setParentAreaId(null);
		}
		if (StringUtils.isNotEmpty(areaName)) {
			area.setAreaName(areaName.trim());
		}
		if (StringUtils.isNotEmpty(areaCode)) {
			area.setAreaCode(areaCode.trim());
		}
		area.setParentArea(parentArea);
		return area;
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
	private int findTreeRelation(List<Area> list, String id, int level)
			throws Exception {
		Area area = (Area) commonDAO.findForObject("Area.selectInfo", id);
		list.add(area);
		if (area.getParentArea() != null) {
			level = findTreeRelation(list, area.getParentArea().getAreaId(),
					++level);
		}
		return level;
	}

	/**
	 * 建立树关系结构
	 * 
	 * @param list
	 *            查询的级别树
	 * @param id
	 *            AreaID
	 * @param level
	 *            当前树级别
	 * @return
	 * @throws Exception
	 */
	private void buildTreeRelation(List<Area> list, String id, int level,
			String bussType) throws Exception {
		int nLevel = level;
		String nLevelCode = "";
		String nLevelName = "";
		String levelCode = "";
		for (Area area : list) {
			nLevelCode = "[" + area.getAreaId() + "]" + nLevelCode;
			nLevelName = "[" + area.getAreaName() + "]" + nLevelName;
		}
		Map map = new HashMap();
		map.put("nLevel", nLevel);
		map.put("nLevelCode", "[0]" + nLevelCode);
		map.put("nLevelName", "[0]" + nLevelName);
		map.put("levelCode", levelCode);
		map.put("level", nLevel);
		map.put("areaId", id);
		commonDAO.update("Area.buildTreeRelation", map);
	}

	/**
	 * 更新其下所有节点
	 * 
	 * @param pid
	 * @throws Exception
	 */
	private void buildChildTreeRelation(String pid) throws Exception {
		Map map = new HashMap();
		map.put("areaId", pid);
		Collection<Area> set = commonDAO.select("Area.findChildArea", map);
		for (Area area : set) {
			List levelList_child = new ArrayList();
			int level_child = 0;
			level_child = findTreeRelation(levelList_child, area.getAreaId(),
					++level_child);
			buildTreeRelation(levelList_child, area.getAreaId(), level_child,
					null);
			log.debug("修改子节点ID--" + area.getAreaId());
			buildChildTreeRelation(area.getAreaId());
		}
	}

	private Report getReport(Map conditionMap) throws Exception {
		Collection result = commonDAO.select("Area.queryAreaDetailInfoForPage",
				conditionMap);
		String[] dataHeaderSet = { "areaId", "areaCode", "areaName", "nLevel",
				"map" };
		String[] headerSet = { "编号", "编码", "名称", "树级别", "地图信息" };
		ReportExt reportExt = new ReportExt();
		Map<String, Integer> position = new HashMap();
		Report report = reportExt.getReportList(result, dataHeaderSet,
				headerSet, position);
		reportExt.setSimpleTitleFooter(report, "行政区划地图信息");
		return report;
	}

}
