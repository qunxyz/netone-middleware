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
 * ��������ҵ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public class AreaServiceImpl extends BaseService implements AreaService {
	/** ��־ */
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
			log.error("������!", e);
		}

	}

	public void saveOrUpdateArea(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		Area area = this.getBean(request);// ��ȡʵ��
		String areaId = request.getParameter("areaId");
		String bussType = request.getParameter("bussType");
		List<Area> levelList = new ArrayList<Area>();
		int nlevel = 0;// ��ǰ�����ڼ���
		try {

			// �������Ƿ��ظ�
			Integer isexist = (Integer) commonDAO.findForObject(
					"Area.findCodeIsExist", area);
			if (isexist > 0) {
				json.put("tip", "��������ظ�!");
				json.put("error", "yes");
				return;
			}
			if (StringUtils.isNotEmpty(areaId)) {// ����Id ���ж��� �����޸Ļ��ǲ���
				// ����,���޸� Id ������ �򱣴�\
				area.setAreaId(areaId);
				area = (Area) commonDAO.update("Area.updateArea", area);

				// ���±��ڵ�
				nlevel = findTreeRelation(levelList, areaId, ++nlevel);
				buildTreeRelation(levelList, areaId, nlevel, bussType);
				log.debug("�޸ĵ�ǰ�ڵ�ID--" + areaId);

				// �����������нڵ�
				buildChildTreeRelation(areaId);
			} else {
				Area _area = (Area) commonDAO.insert("Area.insertArea", area);
				areaId = _area.getAreaId();
				log.debug("����ID--" + _area.getAreaId());

				nlevel = findTreeRelation(levelList, areaId, ++nlevel);
				buildTreeRelation(levelList, areaId, nlevel, bussType);
			}
			json.put("tip", "������Ϣ�ɹ�!");
		} catch (Exception e) {
			json.put("tip", "������Ϣ����ʧ�ܣ��������Ա��ϵ!");
			json.put("error", "yes");
			log.error("������Ϣʧ��!", e);
			throw new RuntimeException("������Ϣʧ��!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void deleteArea(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		JSONObject json = new JSONObject();
		String areaId = request.getParameter("areaId");
		try {
			commonDAO.delete("Area.deleteArea", areaId);
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

	public void findAreaTree(HttpServletRequest request,
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
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, str);
		}
	}

	public void findAreaADepartmentRelation(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String departmentCode = request.getParameter("departmentCode");
		Map conditionMap = new HashMap();
		String start = request.getParameter("start");// ��ʼ����
		String limit = request.getParameter("limit");// ��ʼ����
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
			log.error("������!", e);
		}
	}

	public void getArea(HttpServletRequest request, HttpServletResponse response) {
		String areaId = request.getParameter("areaId");
		try {
			Area area = (Area) commonDAO.findForObject("Area.selectInfo",
					areaId);
			request.setAttribute("area", area);
		} catch (Exception e) {
			log.error("����ʧ��!", e);
		}

	}

	public void importAreaInfo(HttpServletRequest request,
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
					if (object.length == 5) {// ��ǰ�ı������������Ƿ�һ��
						Area areaInfo = new Area();
						areaInfo.setAreaId(object[0].toString().trim());
						areaInfo.setMap(object[4].toString().trim());
						list.add(areaInfo);
					}
				}
				this.commonDAO.updateBatch("Area.importMap", list);
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
			groupReport.format(format, "��������������Ϣ", getReport(conditionMap),
					response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("������Ϣ����!", e);
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
					json.put("tip", "�ù�˾�Ѿ���������!");
					json.put("error", "yes");
					return;
				} else {
					commonDAO.insert("Area.appendAreaADepartmentRelation", map);
				}
				json.put("tip", "�����ɹ�!");
			} else if ("multAdd".equalsIgnoreCase(t)) {
				commonDAO.insert("Area.multAppendAreaADepartmentRelation", map);
				json.put("tip", "�����ɹ�!");
			} else if ("remove".equalsIgnoreCase(t)) {
				commonDAO.delete("Area.removeAreaADepartmentRelation", map);
				json.put("tip", "�����ɹ�!");
			} else if ("multRemove".equalsIgnoreCase(t)) {
				commonDAO.delete("Area.multRemoveAreaADepartmentRelation", map);
				json.put("tip", "�����ɹ�!");
			} else {
				json.put("tip", "û���κβ���!");
			}
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "����ʧ��!");
			log.error("������", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// -- ˽�з���

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
	 * ȡ��Bean
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
	 * ��������ϵ�ṹ
	 * 
	 * @param list
	 *            ��ѯ�ļ�����
	 * @param id
	 *            AreaID
	 * @param level
	 *            ��ǰ������
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
	 * �����������нڵ�
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
			log.debug("�޸��ӽڵ�ID--" + area.getAreaId());
			buildChildTreeRelation(area.getAreaId());
		}
	}

	private Report getReport(Map conditionMap) throws Exception {
		Collection result = commonDAO.select("Area.queryAreaDetailInfoForPage",
				conditionMap);
		String[] dataHeaderSet = { "areaId", "areaCode", "areaName", "nLevel",
				"map" };
		String[] headerSet = { "���", "����", "����", "������", "��ͼ��Ϣ" };
		ReportExt reportExt = new ReportExt();
		Map<String, Integer> position = new HashMap();
		Report report = reportExt.getReportList(result, dataHeaderSet,
				headerSet, position);
		reportExt.setSimpleTitleFooter(report, "����������ͼ��Ϣ");
		return report;
	}

}
