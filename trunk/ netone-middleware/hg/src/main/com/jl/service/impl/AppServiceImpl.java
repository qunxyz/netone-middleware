/**
 * 
 */
package com.jl.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.jl.common.TimeUtil;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.dao.CommonDAO;
import com.jl.service.AppService;
import com.jl.service.BaseService;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * 应用程序实现
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-8-13 下午02:17:53
 * @history
 */
public class AppServiceImpl extends BaseService implements AppService {
	/** 日志 */
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
			// node为空 ext值为ynode 是ext问题
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
			log.error("出错", e);
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
			 * if ("684a303ce5af11e18d0e6cf04976618c".equals(type)) {// 有卖
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
			log.error("出错了!", e);
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
			log.error("出错", e);
		}

	}

	public void findProductSetByPC(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer jsonBuf = new StringBuffer();
		String id = request.getParameter("id");// 获取产品分类号
		String node = request.getParameter("node");
		String condition = request.getParameter("condition");// 模糊查询条件
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
				// 已关联类别的产品
				if (StringUtils.isNotEmpty(productstr)) {
					map.put("productIds", productstr.trim());
				}
			}

			List list = (List) getHgDAO().select("HG.findProductSetByPC2", map);
			// 遍历并构造json字符串
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map pInfo = (Map) iterator.next();
				String jsonStr = JSONUtil2.fromBean(pInfo).toString();

				jsonBuf.append(split + jsonStr);
				split = ",";
			}
		} catch (Exception e) {
			log.error("加载出错!", e);
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
					json.put("tip", "该产品已经被关联了!");
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
				json.put("tip", "操作成功!");
			} else if ("remove".equalsIgnoreCase(t)) {
				Map one = (Map) commonDAO.findForObject(
						"Part.findPartAProductByOne", map);
				if (one != null) {
					type = (String) one.get("type");
					// if ("684a303ce5af11e18d0e6cf04976618c".equals(type)) {//
					// 有卖
					one.put("status", 1);
					// map.put("status", 1);
					commonDAO.update("Part.updatePartAProductRelation", one);
					// } else {
					// commonDAO
					// .delete("Part.removePartAProductRelation", map);
					// }
					json.put("tip", "操作成功!");
				} else {
					json.put("tip", "查找不到要移除的产品!");
				}

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

		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		headerSet1.add(new TableCell("网点"));
		headerSet1.add(new TableCell("条型码"));
		headerSet1.add(new TableCell("产品名"));
		headerSet1.add(new TableCell("类别"));

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
			reportExt.setTitleHeader(report, "网点产品情况表", null, null);
			Long currentTimeMillis = System.currentTimeMillis();
			GroupReport groupReport = new GroupReport();
			response.reset();
			groupReport.format(format, "网点产品情况表" + currentTimeMillis, report,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findProductSetByParentId(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuffer jsonBuf = new StringBuffer();
		String id = request.getParameter("id");// 获取产品分类号
		String code = request.getParameter("code");
		String condition = request.getParameter("condition");// 模糊查询条件
		try {
			String split = "";
			Map map = new HashMap();
			map.put("categoriesId", id);
			if (StringUtils.isNotEmpty(condition)) {
				map.put("condition", condition.trim());
			}
			// map.put("categoriesCode",code);

			List list = (List) getHgDAO().select("HG.findProductSetByPCX", map);
			// 遍历并构造json字符串
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map pInfo = (Map) iterator.next();
				String jsonStr = JSONUtil2.fromBean(pInfo).toString();

				jsonBuf.append(split + jsonStr);
				split = ",";
			}
		} catch (Exception e) {
			log.error("加载出错!", e);
		} finally {
			super.writeJsonStr(response, "{info:[" + jsonBuf + "]}");
		}

	}

	public void querySellOutStorageDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String format = request.getParameter("format");
		Map map = new HashMap();
		try {

			List list = (List) getHgDAO().select(
					"HG.querySellOutStorageDetail", map);

			// 获得原始数据表格
			Table t = new Table();

			List<TableCell> headerSet1 = new ArrayList();

			headerSet1.add(new TableCell("数量"));
			headerSet1.add(new TableCell("总重"));
			headerSet1.add(new TableCell("金重"));
			headerSet1.add(new TableCell("主石(ct/p)"));
			headerSet1.add(new TableCell("副石(ct/p)"));
			headerSet1.add(new TableCell("进货成本"));
			headerSet1.add(new TableCell("真实成本"));
			headerSet1.add(new TableCell("入库市场成本"));
			headerSet1.add(new TableCell("入库售价"));

			ReportExt reportExt = new ReportExt();

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String pm = object.get("pm").toString();
				String gysname = object.get("gysname").toString();
				String zsh = object.get("zsh").toString();
				String fsh = object.get("fsh").toString();
				String rkno = object.get("rkno").toString();
				String sl = object.get("sl").toString();
				String zz = object.get("zz").toString();
				String jz = object.get("jz").toString();
				String zs = object.get("zs").toString();
				String fs = object.get("fs").toString();
				String jhcb = object.get("jhcb").toString();
				String zscb = object.get("zscb").toString();
				String rksccb = object.get("rksccb").toString();
				String rksj = object.get("rksj").toString();
				String gys = object.get("gys").toString();

				TableRow tr = new TableRow();
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell(zs));
				tr.addCell(new TableCell(fs));

				tr.addCell(new TableCell("" + MathHelper.moneyFormat(jhcb),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(zscb),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(rksccb),
						Rectangle.ALIGN_RIGHT));
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(rksj),
						Rectangle.ALIGN_RIGHT));
				t.addRow(tr);
			}
			Report report = reportExt.setSimpleColHeader(t, headerSet1);
			reportExt.setTitleHeader(report, "首饰入库统计表", null, null);
			Long currentTimeMillis = System.currentTimeMillis();
			GroupReport groupReport = new GroupReport();
			response.reset();
			groupReport.format(format, "首饰入库统计表" + currentTimeMillis, report,
					response);

		} catch (Exception e) {
			log.error("加载出错!", e);
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
	 * 同步产品
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
			// 有卖销售是否最新
			Integer isnew = (Integer) getHgDAO().findForObject(
					"HG.findSellInfoIsNew", conditionMap);
			if (isnew > 0) {
				// 查找有卖数据
				List resultx = (List) getHgDAO().select(
						"HG.findPartAProductRelation", conditionMap);
				// 生成关联有卖数据
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
			// 查找想卖数据
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

	public void querySellPivotTable(HttpServletRequest request,
			HttpServletResponse response) {
		String format = request.getParameter("format");
		// 行区域字段
		String rowcolumnStrs = request.getParameter("rowcolumnStrs");
		// 行区域字段中文名
		String rowcolumnNameStrs = request.getParameter("rowcolumnNameStrs");
		// 列区域字段
		String colcolumnStrs = request.getParameter("colcolumnStrs");
		// 统计字段
		String functionValueStrs = request.getParameter("functionValueStrs");

		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String FBillNo = request.getParameter("FBillNo");
		String partId = request.getParameter("partId");
		String productId = request.getParameter("productId");
		// String rowcolumnNameStrs =
		// "业务员,购货单位,产品名称,规格型号,FStockPrice,FStockAmount";
		// String rowcolumnStrs =
		// "FEmpIDName,FSupplyIDName,FItemName,FItemModel,FStockPrice,FStockAmount";
		// String colcolumnStrs = "FMonth";
		// String functionValueStrs = "{'FCUUnitQty':'count'}";

		// 获得原始数据表格
		Table t = new Table();
		try {
			Map conditionMap = new HashMap();
			if (StringUtils.isNotEmpty(beginDate)) {
				conditionMap.put("beginDate", beginDate.trim());
			}
			if (StringUtils.isNotEmpty(endDate)) {
				conditionMap.put("endDate", endDate.trim());
			}
			if (StringUtils.isNotEmpty(partId)) {
				conditionMap.put("partId", partId.trim());
			}
			if (StringUtils.isNotEmpty(productId)) {
				conditionMap.put("productId", productId.trim());
			}
			if (StringUtils.isNotEmpty(FBillNo)) {
				conditionMap.put("FBillNo", FBillNo.trim());
			}
			String vwICBill_8 = getVwICBill_8(conditionMap);

			JSONObject funcObj = JSONObject.fromObject(functionValueStrs);

			// 集合值
			StringBuffer sql = new StringBuffer();
			sql.append(" select ");

			// 汇总字段字符串
			StringBuffer groupByColumnstr = new StringBuffer();
			// 行列字段名
			String[] columnnames = rowcolumnNameStrs.split(",");
			// 行区域字段
			String[] rowcolumns = rowcolumnStrs.split(",");
			// 列区域字段
			String[] colcolumns = null;
			if (StringUtils.isNotEmpty(colcolumnStrs)) {
				colcolumns = colcolumnStrs.split(",");
			}

			String split = "";
			for (int i = 0; i < rowcolumns.length; i++) {
				String column = rowcolumns[i];

				sql.append(column + " as " + columnnames[i] + ",");
				groupByColumnstr.append(split + column);
				split = ",";

			}

			// 列区域
			if (colcolumns != null && colcolumns.length > 0) {
				for (Iterator iteratorx = funcObj.keys(); iteratorx.hasNext();) {
					String funcKey = (String) iteratorx.next();
					String funcVal = funcObj.getString(funcKey);

					for (int i = 0; i < colcolumns.length; i++) {
						String column = colcolumns[i];

						Map map = new HashMap();
						map.put("column", column);
						map.put("value", funcKey);
						map.put("function", funcVal);
						map.put("table", vwICBill_8);
						map.put("datatype", "decimal(22,2)");
						List sqls = new ArrayList();

						sqls = (List) getHgDAO().select("HG.getRowColumnSql",
								map);

						for (Iterator iterator = sqls.iterator(); iterator
								.hasNext();) {
							String object = (String) iterator.next();
							sql.append(object);
						}
						groupByColumnstr.append(split + column);
						split = ",";
					}

				}
			}

			String split2 = "";
			int index = 1;
			for (Iterator iteratorx = funcObj.keys(); iteratorx.hasNext();) {
				String funcKey = (String) iteratorx.next();
				String funcVal = funcObj.getString(funcKey);

				Map m = getFunc((index > 1) ? "" + index : "");

				if ("count".equals(funcVal)) {
					sql.append(split2 + " isnull(convert(int," + funcVal + "("
							+ funcKey + ")),0) as " + m.get(funcVal) + " ");
				} else {
					sql.append(split2 + " isnull(convert(decimal(22,2),"
							+ funcVal + "(" + funcKey + ")),0) as "
							+ m.get(funcVal) + " ");
				}

				split2 = ",";
				index++;
			}

			sql.append(" from (" + vwICBill_8 + ") as t ");

			sql.append(" group by " + groupByColumnstr.toString());

			// System.out.println(sql.toString());

			List result = (List) getHgDAO().select("HG.querySellPivotTable",
					sql.toString());

			List<TableCell> headerList = new ArrayList<TableCell>();

			if (result.size() > 0) {
				Map field = (LinkedHashMap) result.get(0);
				for (Iterator iterator2 = field.keySet().iterator(); iterator2
						.hasNext();) {
					String key = (String) iterator2.next();
					headerList.add(new TableCell("" + key));
				}

				for (Iterator iterator = result.iterator(); iterator.hasNext();) {
					Map object = (LinkedHashMap) iterator.next();
					TableRow row = new TableRow();
					for (Iterator iterator2 = object.keySet().iterator(); iterator2
							.hasNext();) {
						String key = (String) iterator2.next();
						if ("实发数量".equalsIgnoreCase(key)
								|| "基本单位实发数量".equalsIgnoreCase(key)
								|| "常用单位数量".equalsIgnoreCase(key)
								|| "销售金额".equalsIgnoreCase(key)
								|| "销售单价".equalsIgnoreCase(key)
								|| "出厂单价".equalsIgnoreCase(key)
								|| "出厂金额".equalsIgnoreCase(key)) {
							BigDecimal v = new BigDecimal("0");
							if (object.get(key) instanceof BigDecimal) {
								v = (BigDecimal) object.get(key);
							} else if (object.get(key) instanceof String) {
								String x = (String) object.get(key);
								x = x == null ? "" : x;
								v = BigDecimal.valueOf(Double.valueOf(x));
							}

							row.addCell(new TableCell(""
									+ MathHelper.round3(v, 2)));
						} else {
							row.addCell(new TableCell("" + object.get(key)));
						}

					}
					t.addRow(row);
				}
			} else {
				headerList.add(new TableCell("查找不到数据!"));
			}

			ReportExt reportExt = new ReportExt();

			Report report = reportExt.setSimpleColHeader(t, headerList);
			reportExt.setTitleHeader(report, "销售出库情况表", null, null);
			Long currentTimeMillis = System.currentTimeMillis();
			GroupReport groupReport = new GroupReport();
			response.reset();
			groupReport.format(format, "销售出库情况表" + currentTimeMillis, report,
					response);
		} catch (Exception e) {
			log.error("查询出错!", e);
		}

	}

	/**
	 * 获取统计函数
	 * 
	 * @param pos
	 * @return
	 */
	private Map getFunc(String pos) {
		Map map = new LinkedHashMap();
		map.put("sum", "合计" + pos);
		map.put("count", "计数" + pos);
		map.put("avg", "平均值" + pos);
		map.put("max", "最大值" + pos);
		map.put("min", "最小值" + pos);
		return map;
	}

	private String getVwICBill_8(Map conditionMap) {
		StringBuffer vwICBill_8 = new StringBuffer();
		// t_sub FTypeName 客户分类
		vwICBill_8
				.append(" Select *,isnull(convert(decimal(22,2),FBaseQty*FStockPrice),0) as FStockAmount From  ( Select isnull(t_sub.FName,'') as FTypeName,isnull(convert(decimal(22,2),t14.F_104),0) as FStockPrice,v1.FInterID AS FInterID,u1.FEntryID AS FEntryID,(DATENAME(Year,v1.Fdate)+'-'+DATENAME(Month,v1.Fdate)+'-'+DATENAME(Day,v1.Fdate)) AS Fdate,month(v1.FDate) as FMonth,case  when v1.FCheckerID>0 then 'Y' when v1.FCheckerID<0 then 'Y' else '' end AS FCheck,case when v1.FCancellation=1 then 'Y' else '' end AS FCancellation,v1.FBillNo AS FBillNo,t7.FName AS FSaleStyleName,t4.FName AS FSupplyIDName,t8.FName AS FDCStockIDName,t14.FShortNumber AS FItemIDName,t14.FNumber AS FFullNumber,t14.Fname AS FItemName,t14.Fmodel AS FItemModel,t17.FName AS FUnitIDName,u1.FBatchNo AS FBatchNo,u1.Fauxqty AS Fauxqty,u1.Fauxprice AS Fauxprice,u1.Famount AS Famount,t9.FName AS FFManagerIDName,t10.FName AS FSManagerIDName,t11.FName AS FuserName,t24.FName AS FCheckerName,case  when v1.FVchInterID>0 then 'Y' when v1.FVchInterID<0 then 'Y' else '' end AS FVoucherStatus,u1.FNote AS FNote,(SELECT (SELECT FName FROM t_VoucherGroup WHERE FGroupID=t_Voucher.FGroupID)+'-'+CONVERT(Varchar(30),FNumber)   FROM  t_Voucher  WHERE  FVoucherid=v1.FVchInterID)  AS FVoucherNumber,CASE WHEN v1.FHookStatus=1 THEN 'P' WHEN V1.FHookStatus=2 THEN 'Y' ELSE '' END  AS FHookStatus,left('Y',v1.FChildren) AS FReStatus,t40.FName AS FMarketingStyleName,v1.FPOOrdBillNo AS FPOOrdBillNo,u1.FAllHookQTY AS FAllHookQTY,(u1.FQty-u1.FAllHookQTY) AS FUNHookQTY,u1.FCurrentHookQTY AS FCurrentHookQTY,u1.FOrderBillNo AS FOrderBillNo,u1.FContractBillNo AS FContractBillNo,u1.FSourceBillNo AS FSourceBillNo,t70.FName AS FSourceTranType,t105.FName AS FDeptIDName,t106.FName AS FEmpIDName,t107.FName AS FManagerIDName,v1.FExplanation AS FExplanation,v1.FFetchAdd AS FFetchAdd,v1.FCheckDate AS FCheckDate, (CASE t112.FName WHEN '*' THEN '' ELSE t112.FName END)  AS FAuxPropIDName,t112.FNumber AS FAuxPropIDNumber,case when (v1.FOrgBillInterID <> 0) then 'Y' else null  end AS FHasSplitBill,u1.FAuxQtyInvoice AS FAuxQtyInvoice,u1.FQtyInvoice AS FQtyInvoice,t30.FName AS FBaseUnitID,u1.FQty AS FBaseQty,u1.FAuxQtyMust AS FAuxQtyMust,u1.FQtyMust AS FBaseQtyMust,u1.FAuxPlanPrice AS FAuxPlanPrice,u1.FPlanAmount AS FPlanAmount,Case WHEN t14.FStoreUnitID=0 THEN '' Else  t500.FName end AS FCUUnitName,Case WHEN t14.FStoreUnitID=0 THEN '' Else  u1.FQty/t500.FCoefficient end AS FCUUnitQty, (CASE t510.FName WHEN '*' THEN '' ELSE t510.FName END)  AS FSPName,u1.FKFPeriod AS FKFPeriod,u1.FKFDate AS FKFDate,u1.FPeriodDate AS FPeriodDate,t523.FBillNo AS FZPBillNo,u1.FMapName AS FMapName,u1.FMapNumber AS FMapNumber,u1.FConsignPrice AS FConsignPrice,u1.FConsignAmount AS FConsignAmount, (CASE t550.FName WHEN '*' THEN '' ELSE t550.FName END)  AS FRelateBrIDName,(CASE v1.FBrID WHEN 0 THEN NULL ELSE t560.FName END) AS FBrID,t8.FNumber AS FDCStockIDNumber,t552.FName AS FSecUnitName,u1.FSecCoefficient AS FSecCoefficient,u1.FSecQty AS FSecQty,T650.FName AS FVIPCardId,v1.FVIPScore AS FVIPScore,u1.FDiscountRate AS FDiscountRate,u1.FDiscountAmount AS FDiscountAmount,v1.FHolisticDiscountRate AS FHolisticDiscountRate,v1.FPOSName AS FPOSName,t651.FNumber AS FNumber,t3156.FPHONE AS FHeadSelfB0144,t3157.FADDRESS AS FHeadSelfB0145,t3158.FPHONE AS FHeadSelfB0146,v1.FHeadSelfB0147 AS FHeadSelfB0147,v1.FHeadSelfB0148 AS FHeadSelfB0148,v1.FHeadSelfB0149 AS FHeadSelfB0149,v1.FHeadSelfB0150 AS FHeadSelfB0150,v1.FHeadSelfB0151 AS FHeadSelfB0151,v1.FHeadSelfB0152 AS FHeadSelfB0152,v1.FHeadSelfB0153 AS FHeadSelfB0153,v1.FHeadSelfB0154 AS FHeadSelfB0154,t3167.F_109 AS FHeadSelfB0155,v1.FHeadSelfB0156 AS FHeadSelfB0156,v1.FHeadSelfB0157 AS FHeadSelfB0157,v1.FHeadSelfB0158 AS FHeadSelfB0158,v1.FHeadSelfB0159 AS FHeadSelfB0159,v1.FHeadSelfB0160 AS FHeadSelfB0160,v1.FHeadSelfB0161 AS FHeadSelfB0161,v1.FHeadSelfB0162 AS FHeadSelfB0162,v1.FHeadSelfB0163 AS FHeadSelfB0163,v1.FHeadSelfB0164 AS FHeadSelfB0164,v1.FHeadSelfB0165 AS FHeadSelfB0165,v1.FHeadSelfB0166 AS FHeadSelfB0166,v1.FHeadSelfB0167 AS FHeadSelfB0167,v1.FHeadSelfB0168 AS FHeadSelfB0168,v1.FHeadSelfB0169 AS FHeadSelfB0169,v1.FHeadSelfB0170 AS FHeadSelfB0170,v1.FHeadSelfB0171 AS FHeadSelfB0171,v1.FHeadSelfB0172 AS FHeadSelfB0172,v1.FHeadSelfB0173 AS FHeadSelfB0173,v1.FHeadSelfB0174 AS FHeadSelfB0174,v1.FHeadSelfB0175 AS FHeadSelfB0175,v1.FHeadSelfB0176 AS FHeadSelfB0176,v1.FHeadSelfB0177 AS FHeadSelfB0177,t3190.F_113 AS FHeadSelfB0178,u1.FEntrySelfB0155 AS FEntrySelfB0155,t3192.F_103 AS FEntrySelfB0156,u1.FEntrySelfB0157 AS FEntrySelfB0157,t3194.F_106 AS FEntrySelfB0158,u1.FEntrySelfB0159 AS FEntrySelfB0159,u1.FEntrySelfB0160 AS FEntrySelfB0160,u1.FEntrySelfB0161 AS FEntrySelfB0161,u1.FEntrySelfB0162 AS FEntrySelfB0162,u1.FEntrySelfB0163 AS FEntrySelfB0163,u1.FEntrySelfB0164 AS FEntrySelfB0164,t3201.F_105 AS FEntrySelfB0165,u1.FEntrySelfB0166 AS FEntrySelfB0166,u1.FEntrySelfB0167 AS FEntrySelfB0167,u1.FEntrySelfB0168 AS FEntrySelfB0168,t3205.F_113 AS FEntrySelfB0169");
		vwICBill_8
				.append("   from ICStockBill v1 Inner Join ICStockBillEntry u1 on v1.FInterID=u1.FInterID");
		vwICBill_8
				.append("  Inner Join t_Organization t4 on v1.FSupplyID=t4.FItemID");
		vwICBill_8
				.append("  left outer join t_SubMessage t_sub on t_sub.FInterID=t4.FTypeID");
		vwICBill_8
				.append("  left outer join t_SubMessage t7 on v1.FSaleStyle=t7.FInterID");
		vwICBill_8
				.append("  Inner Join t_Stock t8 on u1.FDCStockID=t8.FItemID");
		vwICBill_8
				.append("  left outer join t_Emp t9 on v1.FFManagerID=t9.FItemID");
		vwICBill_8
				.append("  left outer join t_Emp t10 on v1.FSManagerID=t10.FItemID");
		vwICBill_8
				.append("  Inner Join t_User t11 on v1.FBillerID=t11.FUserID");
		vwICBill_8
				.append("  Inner Join t_ICItem t14 on u1.FItemID=t14.FItemID");
		vwICBill_8
				.append("  Inner Join t_item t14000 on u1.FItemID=t14000.FItemID");
		vwICBill_8
				.append("  Inner Join t_MeasureUnit t17 on u1.FUnitID=t17.FItemID");
		vwICBill_8
				.append("  left outer join t_User t24 on v1.Fcheckerid=t24.FUserID");
		vwICBill_8
				.append("  Inner Join t_MeasureUnit t30 on t14.FUnitID=t30.FItemID");
		vwICBill_8
				.append("  left outer join t_SubMessage t40 on v1.FMarketingStyle=t40.FInterID");
		vwICBill_8
				.append("  left outer join v_ICTransType t70 on u1.FSourceTranType=t70.FID");
		vwICBill_8
				.append("  left outer join ICVoucherTpl t16 on v1.FPlanVchTplID=t16.FInterID");
		vwICBill_8
				.append("  left outer join ICVoucherTpl t13 on v1.FActualVchTplID=t13.FInterID");
		vwICBill_8
				.append("  left outer join t_Department t105 on v1.FDeptID=t105.FItemID");
		vwICBill_8
				.append("  left outer join t_Emp t106 on v1.FEmpID=t106.FItemID");
		vwICBill_8
				.append("  left outer join t_Emp t107 on v1.FManagerID=t107.FItemID");
		vwICBill_8
				.append("  left outer join t_AuxItem t112 on u1.FAuxPropID=t112.FItemid");
		vwICBill_8
				.append("  left outer join t_MeasureUnit t500 on t14.FStoreUnitID=t500.FItemID");
		vwICBill_8
				.append("  left outer join t_Currency t503 on v1.FCurrencyID=t503.FCurrencyID");
		vwICBill_8
				.append("  left outer join t_StockPlace t510 on u1.FDCSPID=t510.FSPID");
		vwICBill_8
				.append("  left outer join ZPStockBill t523 on v1.FInterID=t523.FRelateBillInterID");
		vwICBill_8
				.append("  left outer join t_SonCompany t550 on v1.FRelateBrID=t550.FItemID");
		vwICBill_8
				.append("  left outer join t_MeasureUnit t552 on t14.FSecUnitID=t552.FItemID");
		vwICBill_8
				.append("  left outer join t_SonCompany t560 on v1.FBrID=t560.FItemID");
		vwICBill_8
				.append("  left outer join rtl_vip t650 on v1.FVIPCardId=t650.Fid");
		vwICBill_8
				.append("  left outer join Rtl_WorkShift t651 on v1.FWorkShiftID=t651.FID");
		vwICBill_8
				.append("  left outer join t_Organization t3156 on v1.FSupplyID=t3156.FItemID");
		vwICBill_8
				.append("  left outer join t_Organization t3157 on v1.FSupplyID=t3157.FItemID");
		vwICBill_8
				.append("  left outer join t_Emp t3158 on v1.FEmpID=t3158.FItemID");
		vwICBill_8
				.append("  left outer join t_Organization t3167 on v1.FSupplyID=t3167.FItemID");
		vwICBill_8
				.append("  left outer join t_Organization t3190 on v1.FSupplyID=t3190.FItemID");
		vwICBill_8
				.append("  left outer join t_ICItem t3192 on u1.FItemID=t3192.FItemID");
		vwICBill_8
				.append("  left outer join t_ICItem t3194 on u1.FItemID=t3194.FItemID");
		vwICBill_8
				.append("  left outer join t_ICItem t3201 on u1.FItemID=t3201.FItemID");
		vwICBill_8
				.append("  left outer join t_ICItem t3205 on u1.FItemID=t3205.FItemID");

		vwICBill_8.append("  where 1=1 And v1.FTranType=21 ");

		if (conditionMap.containsKey("beginDate")) {
			vwICBill_8.append(" and v1.Fdate>= '"
					+ conditionMap.get("beginDate") + "' ");
		}
		if (conditionMap.containsKey("endDate")) {
			vwICBill_8.append(" and v1.Fdate<= '" + conditionMap.get("endDate")
					+ "' ");
		}
		if (conditionMap.containsKey("partId")) {
			String partId = (String) conditionMap.get("partId");
			String[] partcodeStrs = partId.split(",");
			vwICBill_8.append(" and ( 1=0 ");
			for (int i = 0; i < partcodeStrs.length; i++) {
				vwICBill_8.append(" or t4.FNumber like '" + partcodeStrs[i]
						+ "%' ");
			}
			vwICBill_8.append(" ) ");

		}
		if (conditionMap.containsKey("productId")) {
			String productId = (String) conditionMap.get("productId");
			String[] productcodeStrs = productId.split(",");
			vwICBill_8.append(" and ( 1=0 ");
			for (int i = 0; i < productcodeStrs.length; i++) {
				vwICBill_8.append(" or t14000.FFullNumber like '"
						+ productcodeStrs[i] + "%' ");
			}
			vwICBill_8.append(" ) ");
		}
		if (conditionMap.containsKey("FBillNo")) {
			vwICBill_8.append(" and v1.FBillNo like '%"
					+ conditionMap.get("FBillNo") + "%' ");
		}

		vwICBill_8.append(" ) As [vwICBill_8]");
		return vwICBill_8.toString();
	}

	public void queryNetPointManage(HttpServletRequest request,
			HttpServletResponse response) {
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String netName = request.getParameter("netName");
		String ppname = request.getParameter("ppname");
		String createPerson = request.getParameter("createPerson");
		String point = request.getParameter("point");

		String toPage = request.getParameter("toPage");
		String pageSize = request.getParameter("pageSize");
		String rowsize_ = request.getParameter("rowsize");
		String width = "138";
		String height = "143";
		try {
			Integer rowsize = 5;
			if (StringUtils.isNotEmpty(rowsize_)) {
				rowsize = Integer.parseInt(rowsize_);
			}

			Map map = new LinkedHashMap();
			map.put("startIndex", (Integer.parseInt(toPage) - 1)
					* Integer.parseInt(pageSize));
			map.put("pageSize", pageSize);
			if (StringUtils.isNotEmpty(beginDate)) {
				map.put("beginDate", beginDate.trim());
			}
			if (StringUtils.isNotEmpty(endDate)) {
				map.put("endDate", endDate.trim());
			}
			if (StringUtils.isNotEmpty(netName)) {
				map.put("netName", netName.trim());
			}
			if (StringUtils.isNotEmpty(ppname)) {
				map.put("ppname", ppname.trim());
			}
			if (StringUtils.isNotEmpty(createPerson)) {
				map.put("createPerson", createPerson.trim());
			}
			if (StringUtils.isNotEmpty(point)) {
				map.put("point", point.trim());
			}

			List result = (List) commonDAO.select("App.selectNetpoint", map);
			Integer total = (Integer) commonDAO.findForObject(
					"App.selectNetpointCount", map);

			String pointList = "1-合格,0-不合格,2-无效";

			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			String path = request.getContextPath();
			StringBuffer cache = new StringBuffer();
			for (int i = 0; i < result.size(); i++) {
				Map info = (Map) result.get(i);

				cache.append("\"");
				String address = (String) info.get("address");
				String createPerson_ = (String) info.get("createPerson");
				String createDate = (String) info.get("createDate");
				String point_ = (String) info.get("point");
				String lsh = (String) info.get("lsh");
				String fileunid = (String) info.get("fileunid");

				cache.append("<div  style='backgroud:#F7F7F7'>");

				if (StringUtils.isNotEmpty(address)) {

					String addr = path
							+ "/file.do?method=onDownLoadOptFile&width="
							+ width + "&height=" + height + "&isOnLine=0&unid="
							+ fileunid;
					String addr_scale = path
							+ "/file.do?method=onDownLoadOptFile&scale=" + 2
							+ "&isOnLine=0&unid=" + fileunid;
					String title = "";
					if (StringUtils.isNotEmpty(createPerson_)
							|| StringUtils.isNotEmpty(createDate)) {
						title = info.get("createPerson") + "于"
								+ info.get("createDate") + "创建";
					}

					cache.append("<div id='imagediv'><a class='imagex' href='"
							+ addr_scale + "' title='" + title
							+ "'><img id='imagesouce" + lsh + "' width="
							+ width + " height=" + height + " src='" + addr
							+ "' /></a></div>");

				} else {
					String addr = path + "/images/no_picture.jpg";
					cache.append("<div><img width=" + width + " height="
							+ height + " src='" + addr + "' /></div>");
				}
				cache.append("<div><div>网点:" + info.get("netName") + "</div>");
				cache.append("<div>理货员:" + info.get("createPerson") + "</div>");
				cache.append("<div>理货时间:" + info.get("createDate") + "</div>");
				cache.append("<div>品牌:" + info.get("ppname") + "</div></div>");
				boolean readonly = false;
				if (StringUtils.isNotEmpty(point_)) {
					readonly = true;
				}
				cache.append("<div>评分:"
						+ DyFormComp.getGroupRadio2(lsh, point_, "", "",
								readonly, pointList).replaceAll("\"", "\'")
						+ "</div>");

				cache.append("</div>");

				cache.append("\"");
				cache.append(",");

				if ((i + 1) % rowsize == 0) {
					jsonBuffer.append(split);
					jsonBuffer.append("[");
					jsonBuffer.append(StringUtils.substring(cache.toString(),
							0, cache.toString().length() - 1));
					jsonBuffer.append("]");
					split = ",";

					cache = new StringBuffer();
				}

				if (result.size() == (i + 1)) {
					jsonBuffer.append(split);
					jsonBuffer.append("[");

					Integer xx = rowsize - total % rowsize;
					for (int j = 0; j < xx; j++) {
						cache.append("\"<div></div>\",");
					}

					jsonBuffer.append(StringUtils.substring(cache.toString(),
							0, cache.toString().length() - 1));
					jsonBuffer.append("]");
					split = ",";

					cache = new StringBuffer();
				}

			}

			super.writeJsonStr(response, buildJsonStr2(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("出错了", e);
		}
	}

	public String buildJsonStr2(int total, String json) {
		StringBuilder store = new StringBuilder();
		store.append("{\"");
		store.append("totalItems");
		store.append("\" : ");
		store.append(total);
		store.append(",\"");
		store.append("data");
		store.append("\":[");
		store.append(json);
		store.append("]}");
		return store.toString();
	}

	public void updateNetPoint(HttpServletRequest request,
			HttpServletResponse response) {
		String lsh = request.getParameter("lsh");
		String point = request.getParameter("point");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(lsh)) {
				Map map = new HashMap();
				map.put("lsh", lsh.trim());
				map.put("point", point.trim());
				commonDAO.update("App.updateNetPoint", map);
				json.put("tip", "评分成功！");
			} else {
				json.put("error", "yes");
				json.put("tip", "找不到评分项目，评分失败！");
			}
		} catch (Exception e) {
			log.error("出错了!", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	/**
	 * 航港手机理货分析统计表
	 */
	public void queryHGReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String format = request.getParameter("format");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String wdms = request.getParameter("wdmc");// 网点名称
		String clientId = request.getParameter("clientId"); // 业务员
		String px = request.getParameter("px");// 品项
		String wdmc_check = request.getParameter("wdmc_check");// 网点名称
		String lhy_check = request.getParameter("lhy_check");// 理货员
		String pp_check = request.getParameter("pp_check");// 品牌

		Map map = new HashMap();
		if (StringUtils.isNotEmpty(beginDate)) {
			map.put("beginDate", beginDate.trim());
		}
		if (StringUtils.isNotEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (StringUtils.isNotEmpty(wdms)) {
			map.put("wdms", wdms.trim());
		}
		if (StringUtils.isNotEmpty(clientId)) {
			map.put("clientId", clientId.trim());
		}
		if (StringUtils.isNotEmpty(px)) {
			map.put("px", px.trim());
		}
		String splitx = "";
		StringBuffer sb = new StringBuffer();
		if (lhy_check.equals("1")) {
			sb.append(splitx + "ywybm");
			splitx = ",";
		}
		if (wdmc_check.equals("1")) {
			sb.append(splitx + "lsh576");
			splitx = ",";
		}
		if (pp_check.equals("1")) {
			sb.append(splitx + "px");
			splitx = ",";
		}
		map.put("sb", sb.toString());
		List<Map> list1 = (List<Map>) commonDAO.select("App.export_PH", map);
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if (wdmc_check.equals("1")) {
			headerSet1.add(new TableCell("网点名称"));
		}
		if (lhy_check.equals("1")) {
			headerSet1.add(new TableCell("业务员"));
		}
		if (pp_check.equals("1")) {
			headerSet1.add(new TableCell("品牌"));
		}

		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("频率（次）/分值"));
		headerSet1.add(new TableCell("汇总"));
		headerSet1.add(new TableCell("汇总"));

		List<TableCell> headerSet2 = new ArrayList();
		if (wdmc_check.equals("1")) {
			headerSet2.add(new TableCell("网点名称"));
		}
		if (lhy_check.equals("1")) {
			headerSet2.add(new TableCell("业务员"));
		}
		if (pp_check.equals("1")) {
			headerSet2.add(new TableCell("品牌"));
		}

		headerSet2.add(new TableCell("周一"));
		headerSet2.add(new TableCell("周一"));
		headerSet2.add(new TableCell("周二"));
		headerSet2.add(new TableCell("周二"));
		headerSet2.add(new TableCell("周三"));
		headerSet2.add(new TableCell("周三"));
		headerSet2.add(new TableCell("周四"));
		headerSet2.add(new TableCell("周四"));
		headerSet2.add(new TableCell("周五"));
		headerSet2.add(new TableCell("周五"));
		headerSet2.add(new TableCell("周六"));
		headerSet2.add(new TableCell("周六"));
		headerSet2.add(new TableCell("周日"));
		headerSet2.add(new TableCell("周日"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));

		ReportExt reportExt = new ReportExt();

		List list = list1;
		int[] tmpdata = new int[16];
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String wdmc = object.get("wdmc").toString();// 网点名称
			String ywybm = object.get("ywybm").toString();// 业务员编号
			String ywyname = object.get("ywyname").toString();// 业务员姓名
			String pxm = object.get("pxm").toString();// 品牌

			String w1 = object.get("w1").toString();// 周一
			String f1 = object.get("f1").toString();// 分值1
			String w2 = object.get("w2").toString();// 周二
			String f2 = object.get("f2").toString();// 分值2
			String w3 = object.get("w3").toString();// 周三
			String f3 = object.get("f3").toString();// 分值3
			String w4 = object.get("w4").toString();// 周四
			String f4 = object.get("f4").toString();// 分值4
			String w5 = object.get("w5").toString();// 周五
			String f5 = object.get("f5").toString();// 分值5
			String w6 = object.get("w6").toString();// 周六
			String f6 = object.get("f6").toString();// 分值6
			String w7 = object.get("w7").toString();// 周日
			String f7 = object.get("f7").toString();// 分值7
			String totalx = object.get("totalx").toString();// 频率统计
			String totalf = object.get("totalf").toString();// 分值统计

			TableRow tr = new TableRow();
			if (wdmc_check.equals("1")) {
				tr.addCell(new TableCell(wdmc));
			}
			if (lhy_check.equals("1")) {
				tr.addCell(new TableCell(ywybm + "/" + ywyname));
			}
			if (pp_check.equals("1")) {
				tr.addCell(new TableCell(pxm));
			}

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w1))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f1))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w2))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f2))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w3))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f3))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w4))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f4))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w5))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f5))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w6))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f6))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(w7))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(fontColor(format, (Double.valueOf(f7))
					.intValue()), Rectangle.ALIGN_RIGHT));

			tr.addCell(new TableCell(totalFont(format, (Double.valueOf(totalx))
					.intValue()), Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(totalFont(format, (Double.valueOf(totalf))
					.intValue()), Rectangle.ALIGN_RIGHT));

			t.addRow(tr);

			// 合计
			tmpdata[0] += Double.valueOf(w1);
			tmpdata[1] += Double.valueOf(f1);
			tmpdata[2] += Double.valueOf(w2);
			tmpdata[3] += Double.valueOf(f2);
			tmpdata[4] += Double.valueOf(w3);
			tmpdata[5] += Double.valueOf(f3);
			tmpdata[6] += Double.valueOf(w4);
			tmpdata[7] += Double.valueOf(f4);
			tmpdata[8] += Double.valueOf(w5);
			tmpdata[9] += Double.valueOf(f5);
			tmpdata[10] += Double.valueOf(w6);
			tmpdata[11] += Double.valueOf(f6);
			tmpdata[12] += Double.valueOf(w7);
			tmpdata[13] += Double.valueOf(f7);
			tmpdata[14] += Double.valueOf(totalx);
			tmpdata[15] += Double.valueOf(totalf);
		}

		// 扩展
		List<TableCell> totalTrData = new ArrayList<TableCell>();

		if (wdmc_check.equals("1")) {
			totalTrData.add(new TableCell(""));// 
		}
		if (lhy_check.equals("1")) {
			totalTrData.add(new TableCell(""));// 
		}
		if (pp_check.equals("1")) {
			totalTrData.add(new TableCell(""));// 
		}

		totalTrData.add(new TableCell("" + tmpdata[0], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[1], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[2], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[3], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[4], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[5], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[6], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[7], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[8], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[9], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[10], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[11], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[12], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[13], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[14], Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + tmpdata[15], Rectangle.ALIGN_RIGHT));// 

		totalTrData.set(0, new TableCell("合计"));
		TableRow tr = reportExt.getOneTableRow(totalTrData);
		tr.setType(Report.GROUP_TOTAL_TYPE);
		t.addRow(tr);

		Report report = reportExt.setComplexColHeader(t, headerSet1,
				headerSet2, true);
		String[] dateTitle = { beginDate + "至" + endDate,
				TimeUtil.formatDate(new Date()) };
		reportExt.setTitleHeader(report, "理货分析统计表", dateTitle, null);

		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "理货分析统计表" + currentTimeMillis, report,
				response);
	}

	// 周一-周日 颜色调协
	public String fontColor(String format, int wk) {
		String week = "";
		if (format.equals("html") && wk >= 1) {
			week = "<font color='green'>" + wk + "</font>";
		} else {
			week = "" + wk;
		}
		return week;
	}

	// 统计
	public String totalFont(String format, int tl) {
		String total = "";
		if (format.equals("html") && tl >= 1) {
			total = "<font color='blue'>" + tl + "</font>";
		} else {
			total = "" + tl;
		}
		return total;
	}

}
