/**
 * 
 */
package com.jl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.MathHelper;
import com.jl.common.SpringBeanUtilHg;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.common.workflow.DbTools;
import com.jl.dao.CommonDAO;
import com.jl.service.AppService;
import com.jl.service.BaseService;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * Ӧ�ó���ʵ��
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-8-13 ����02:17:53
 * @history
 */
public class AppServiceImpl extends BaseService implements AppService {
	/** ��־ */
	private final Logger log = Logger.getLogger(AppServiceImpl.class);

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	private CommonDAO getHgDAO() {
		CommonDAO dao = (CommonDAO) SpringBeanUtilHg.getInstance().getBean(
				"commonDAO");
		return dao;
	}

	public void findPartTree(HttpServletRequest request,
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
				set = getHgDAO().select("HG.loadPartRoot", node);
			} else {
				Map map = new HashMap();
				map.put("partId", node);
				String partCode = request.getParameter("partCode");
				if (StringUtils.isNotEmpty(partCode)) {
					map.put("partCode", partCode);
				}
				set = getHgDAO().select("HG.findChildPart", map);
			}
			str = buildPartJsonStr(set);
		} catch (Exception e) {
			log.error("����", e);
		} finally {
			super.writeJsonStr(response, str);
		}
	}

	public void savePartAProductRelation(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String productCode = request.getParameter("productCode");
		String type = request.getParameter("type");
		Map conditionMap = new HashMap();
		conditionMap.put("partId", node);
		if (StringUtils.isNotEmpty(productCode)) {
			conditionMap.put("productCode", productCode.trim());
		}
		if (StringUtils.isNotEmpty(type)) {
			conditionMap.put("type", type.trim());
		}
		try {
			List result = new ArrayList();
			/*******************************************************************
			 * List result1 = new ArrayList(); List result2 = new ArrayList();
			 * if ("684a303ce5af11e18d0e6cf04976618c".equals(type)) {// ����
			 * result1 = (List) getHgDAO().select(
			 * "HG.findPartAProductRelation", conditionMap); } else if
			 * ("".equals(type)) { result1 = (List) getHgDAO().select(
			 * "HG.findPartAProductRelation", conditionMap); result2 = (List)
			 * commonDAO.select( "Part.findPartAProductRelation", conditionMap); }
			 * else { result2 = (List) commonDAO.select(
			 * "Part.findPartAProductRelation", conditionMap); }
			 * result.addAll(result1); result.addAll(result2);
			 */
			sync(node, conditionMap);
			result = (List) commonDAO.select("Part.findPartAProductRelation",
					conditionMap);

			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map info = (Map) iterator.next();

				String jsonStr = JSONUtil2.fromBean(info).toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(result.size(),
					jsonBuffer.toString()));
		} catch (Exception e) {
			log.error("������!", e);
		}
	}

	public void findCategoriesTree(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		try {
			// if(node.contains("ynode")) node="";
			Collection coll = getHgDAO().select("HG.findChildCategories", node);
			String categoriesJsonStr = buildCategoriesJsonStr(coll);
			super.writeJsonStr(response, categoriesJsonStr);
		} catch (Exception e) {
			log.error("����", e);
		}

	}

	public void findProductSetByPC(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer jsonBuf = new StringBuffer();
		String id = request.getParameter("id");// ��ȡ��Ʒ�����
		String node = request.getParameter("node");
		String condition = request.getParameter("condition");// ģ����ѯ����
		try {
			String split = "";
			Map map = new HashMap();
			map.put("categoriesId", id);
			if (StringUtils.isNotEmpty(condition)) {
				map.put("condition", condition.trim());
			}

			if (StringUtils.isNotEmpty(node)) {
				List strlist = (List) commonDAO.select(
						"Part.getRelationProductByPartIdStatus1", node);
				String productstr = getSeparatorStr(strlist);
				// �ѹ������Ĳ�Ʒ
				if (StringUtils.isNotEmpty(productstr)) {
					map.put("productIds", productstr.trim());
				}
			}

			List list = (List) getHgDAO().select("HG.findProductSetByPC2", map);
			// ����������json�ַ���
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map pInfo = (Map) iterator.next();
				String jsonStr = JSONUtil2.fromBean(pInfo).toString();

				jsonBuf.append(split + jsonStr);
				split = ",";
			}
		} catch (Exception e) {
			log.error("���س���!", e);
		} finally {
			super.writeJsonStr(response, "{info:[" + jsonBuf + "]}");
		}
	}

	public void relatePartAProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String t = request.getParameter("t");
		String partId = request.getParameter("partId");
		String partName = request.getParameter("partName");
		String productId = request.getParameter("productId");
		String productCode = request.getParameter("productCode");
		String productName = request.getParameter("productName");
		String productLSH = request.getParameter("productLSH");
		String type = request.getParameter("type");
		Map map = new HashMap();
		map.put("partId", partId);
		map.put("productId", productId);
		map.put("productCode", productCode);
		map.put("productName", productName);
		map.put("productLSH", productLSH);
		map.put("partName", partName);
		if (StringUtils.isNotEmpty(type)) {
			map.put("type", type.trim());
		}
		JSONObject json = new JSONObject();
		try {
			if ("add".equalsIgnoreCase(t)) {
				Integer count = (Integer) commonDAO.findForObject(
						"Part.findPartAProductIsExist", map);
				if (count > 0) {
					json.put("tip", "�ò�Ʒ�Ѿ���������!");
					json.put("error", "yes");
					return;
				} else {

					Map one = (Map) commonDAO.findForObject(
							"Part.findPartAProductByOne", map);
					if (one != null) {
						Integer status = (Integer) one.get("status");
						if (status == 0) {
							commonDAO.insert("Part.appendPartAProductRelation",
									map);
						} else {
							map.put("status", 0);
							commonDAO.update("Part.updatePartAProductRelation",
									map);
						}
					} else {
						commonDAO
								.insert("Part.appendPartAProductRelation", map);
					}

				}
				json.put("tip", "�����ɹ�!");
			} else if ("remove".equalsIgnoreCase(t)) {
				Map one = (Map) commonDAO.findForObject(
						"Part.findPartAProductByOne", map);
				if (one != null) {
					type = (String) one.get("type");
					// if ("684a303ce5af11e18d0e6cf04976618c".equals(type)) {//
					// ����
					one.put("status", 1);
					// map.put("status", 1);
					commonDAO.update("Part.updatePartAProductRelation", one);
					// } else {
					// commonDAO
					// .delete("Part.removePartAProductRelation", map);
					// }
					json.put("tip", "�����ɹ�!");
				} else {
					json.put("tip", "���Ҳ���Ҫ�Ƴ��Ĳ�Ʒ!");
				}

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

	public void syncProductData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = (List) getHgDAO()
					.select("HG.selectAllOrganization", "");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				sync(object.get("partId").toString(), object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void exportPartAndProduct(HttpServletRequest request,
			HttpServletResponse response) {

		String format = request.getParameter("format");
		String productId = request.getParameter("productId");
		String partId = request.getParameter("partId");
		String type = request.getParameter("type");
		StringBuffer sb = new StringBuffer();

		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(productId)) {
			conditionMap.put("productId", productId.trim().split(","));
		}
		if (StringUtils.isNotEmpty(partId)) {
			conditionMap.put("partId", partId.trim().split(","));
		}
		if (StringUtils.isNotEmpty(type)) {
			conditionMap.put("type", type.trim());
		}

		// ���ԭʼ���ݱ��
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("������"));
		headerSet1.add(new TableCell("��Ʒ��"));
		headerSet1.add(new TableCell("���"));

		ReportExt reportExt = new ReportExt();
		List list;
		try {
			list = (List) commonDAO.select("Part.selectPartAndProduct",
					conditionMap);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String partName = (String) object.get("partName");
				String productName = (String) object.get("productName");
				String productLSH = (String) object.get("productLSH");
				String typename = (String) object.get("typename");

				TableRow tr = new TableRow();
				tr.addCell(new TableCell(partName));
				tr.addCell(new TableCell(productLSH));
				tr.addCell(new TableCell(productName));
				tr.addCell(new TableCell(typename));
				t.addRow(tr);
			}

			Report report = reportExt.setSimpleColHeader(t, headerSet1);
			reportExt.setTitleHeader(report, "�����Ʒ�����", null, null);
			Long currentTimeMillis = System.currentTimeMillis();
			GroupReport groupReport = new GroupReport();
			response.reset();
			groupReport.format(format, "�����Ʒ�����" + currentTimeMillis, report,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findProductSetByParentId(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuffer jsonBuf = new StringBuffer();
		String id = request.getParameter("id");// ��ȡ��Ʒ�����
		String condition = request.getParameter("condition");// ģ����ѯ����
		try {
			String split = "";
			Map map = new HashMap();
			map.put("categoriesId", id);
			if (StringUtils.isNotEmpty(condition)) {
				map.put("condition", condition.trim());
			}

			List list = (List) getHgDAO().select("HG.findProductSetByPC", map);
			// ����������json�ַ���
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map pInfo = (Map) iterator.next();
				String jsonStr = JSONUtil2.fromBean(pInfo).toString();

				jsonBuf.append(split + jsonStr);
				split = ",";
			}
		} catch (Exception e) {
			log.error("���س���!", e);
		} finally {
			super.writeJsonStr(response, "{info:[" + jsonBuf + "]}");
		}

	}

	private String buildCategoriesJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Map pc2 = (Map) itr.next();
			String str = "{id: \"" + pc2.get("partId") + "\",code: \""
					+ pc2.get("partCode") + "\", text:\"" + pc2.get("partName")
					+ "\",pid: \"" + pc2.get("parentPartId") + "\",pcode: \""
					+ pc2.get("parentPartCode") + "\", ptext:\""
					+ pc2.get("parentPartName") + "\"}";

			jSonSet.add(str);
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";

		return str;
	}

	private String buildPartJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Map d = (Map) itr.next();
			d.put("text", d.get("partName"));
			d.put("id", d.get("partId"));
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	private String buildPartProductJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Map d = (Map) itr.next();
			Map map = new HashMap();
			map.put("text", map.get("productName"));
			map.put("id", map.get("productId"));
			jSonSet.add(JSONUtil2.fromBean(d));
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	/**
	 * ͬ����Ʒ
	 * 
	 * @param node
	 * @param conditionMap
	 * @throws Exception
	 */
	private void sync(String node, Map conditionMap) throws Exception {
		if (StringUtils.isNotEmpty(node)) {
			List strlist = (List) commonDAO.select(
					"Part.getRelationProductByPartId", node);
			String productstr = getSeparatorStr(strlist);

			// if (StringUtils.isNotEmpty(productstr)) {
			if (StringUtils.isNotEmpty(productstr)) {
				conditionMap.put("productIds", productstr.trim());
			}
			// ���������Ƿ�����
			Integer isnew = (Integer) getHgDAO().findForObject(
					"HG.findSellInfoIsNew", conditionMap);
			if (isnew > 0) {
				// ������������
				List resultx = (List) getHgDAO().select(
						"HG.findPartAProductRelation", conditionMap);
				// ���ɹ�����������
				commonDAO.insertBatch("Part.appendPartAProductRelation",
						resultx);
			}

			//
			List strlist2 = (List) commonDAO.select(
					"Part.getRelationProductByPartId13", node);

			productstr = getSeparatorStr(strlist2);
			if (StringUtils.isNotEmpty(productstr)) {
				conditionMap.put("productIds", productstr.trim());
			}
			// ������������
			List resultx2 = (List) getHgDAO().select(
					"HG.findPartAProductRelation2", conditionMap);
			if (resultx2.size() > 0) {
				StringBuffer strss = new StringBuffer();
				String split = "";
				for (Iterator iterator = resultx2.iterator(); iterator
						.hasNext();) {
					Map object = (Map) iterator.next();
					Integer productId_ = (Integer) object.get("productId");
					strss.append(split + productId_);
					split = ",";
				}
				conditionMap.put("productIds", strss.toString());
				List resultx3 = (List) commonDAO.select(
						"Part.getNotRelationProductByPartId13", conditionMap);
				if (resultx3.size() > 0) {
					resultx2 = resultx3;
				}

				List updateList = new ArrayList();
				List insertList = new ArrayList();
				for (Iterator iterator = resultx2.iterator(); iterator
						.hasNext();) {
					Map object = (Map) iterator.next();

					Map checkMap = new HashMap();
					checkMap.put("partId", node);
					checkMap.put("productId", object.get("productId"));
					Integer x = (Integer) commonDAO.findForObject(
							"Part.checkPartId3IsExist", checkMap);
					if (x > 0) {
						// updateList.add(object);
						// // updatePartAProductRelation
					} else {
						insertList.add(object);
					}
				}

				commonDAO.insertBatch("Part.appendPartAProductRelation",
						insertList);
				commonDAO.updateBatch("Part.updatePartAProductRelation",
						updateList);

			}

			// }

		}
	}

	private String getSeparatorStr(List<Object> list) {
		String split = "";
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object string = (Object) iterator.next();
			sb.append(split + "" + string);
			split = ",";
		}
		return sb.toString();
	}

}
