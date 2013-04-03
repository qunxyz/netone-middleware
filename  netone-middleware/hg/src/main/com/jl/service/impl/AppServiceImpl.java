/**
 * 
 */
package com.jl.service.impl;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.MathHelper;
import com.jl.common.SpringBeanUtilHg;
import com.jl.common.SpringBeanUtilHgTest;
import com.jl.common.TimeUtil;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
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

	// 测试
	private CommonDAO getHgTestDAO() {
		CommonDAO dao = (CommonDAO) SpringBeanUtilHgTest.getInstance().getBean(
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

	private String buildStoreJsonStr(Collection col) {
		List jSonSet = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Map pc2 = (Map) itr.next();
			String str = "{leaf:true,id: \"" + pc2.get("lsh") + "\", text:\""
					+ pc2.get("name") + "\"}";

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
		// JSONObject json = setRequestJSON(request);
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
							+ "'><img id='imagesouce" + fileunid + "' width="
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
				} else if (result.size() == (i + 1)) {
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

	public void queryNetPointManage2(HttpServletRequest request,
			HttpServletResponse response) {
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String netName = request.getParameter("netName");
		String ppname = request.getParameter("ppname");
		String createPerson = request.getParameter("createPerson");
		String point = request.getParameter("point");

		String fatherlsh = request.getParameter("fatherlsh");
		String queryDate = request.getParameter("queryDate");
		String ppid = request.getParameter("ppid");

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

			if (StringUtils.isNotEmpty(queryDate)) {
				map.put("queryDate", queryDate.trim());
			}
			if (StringUtils.isNotEmpty(fatherlsh)) {
				map.put("fatherlsh", fatherlsh.trim());
			}
			if (StringUtils.isNotEmpty(ppid)) {
				map.put("ppid", ppid.trim());
			}

			List result = (List) commonDAO.select("App.selectNetpoint", map);
			Integer total = (Integer) commonDAO.findForObject(
					"App.selectNetpointCount", map);

			String pointList = "1-合格,0-不合格,2-无效";

			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			String path = request.getContextPath();
			for (int i = 0; i < result.size(); i++) {
				Map info = (Map) result.get(i);

				String address = (String) info.get("address");
				String createPerson_ = (String) info.get("createPerson");
				String createDate = (String) info.get("createDate");
				String point_ = (String) info.get("point");
				String lsh = (String) info.get("lsh");
				String fileunid = (String) info.get("fileunid");

				jsonBuffer.append(split + "[\"");
				jsonBuffer.append("<div  style='backgroud:#F7F7F7'>");

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

					jsonBuffer
							.append("<div id='imagediv'><a class='imagex' href='"
									+ addr_scale
									+ "' title='"
									+ title
									+ "'><img id='imagesouce"
									+ fileunid
									+ "' width="
									+ width
									+ " height="
									+ height
									+ " src='" + addr + "' /></a></div>");

				} else {
					String addr = path + "/images/no_picture.jpg";
					jsonBuffer.append("<div><img width=" + width + " height="
							+ height + " src='" + addr + "' /></div>");
				}
				jsonBuffer.append("</div>");
				jsonBuffer.append("\",");

				jsonBuffer.append("\"");
				jsonBuffer.append("<div>" + info.get("netName") + "</div>");
				jsonBuffer.append("\",");

				jsonBuffer.append("\"");
				jsonBuffer
						.append("<div>" + info.get("createPerson") + "</div>");
				jsonBuffer.append("\",");

				jsonBuffer.append("\"");
				jsonBuffer.append("<div>" + info.get("createDate") + "</div>");
				jsonBuffer.append("\",");

				jsonBuffer.append("\"");
				jsonBuffer
						.append("<div>" + info.get("ppname") + "</div></div>");
				jsonBuffer.append("\"]");
				// boolean readonly = false;
				// if (StringUtils.isNotEmpty(point_)) {
				// readonly = true;
				// }
				// jsonBuffer.append("<div>评分:"
				// + DyFormComp.getGroupRadio2(lsh, point_, "", "",
				// readonly, pointList).replaceAll("\"", "\'")
				// + "</div>");
				split = ",";
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
			sb.append(splitx + "tt.ywybm");
			splitx = ",";
		}
		if (wdmc_check.equals("1")) {
			sb.append(splitx + "hh.outletsId");
			splitx = ",";
		}
		if (pp_check.equals("1")) {
			sb.append(splitx + "hh.brandId");
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

		headerSet1.add(new TableCell("周一"));
		headerSet1.add(new TableCell("周一"));
		headerSet1.add(new TableCell("周二"));
		headerSet1.add(new TableCell("周二"));
		headerSet1.add(new TableCell("周三"));
		headerSet1.add(new TableCell("周三"));
		headerSet1.add(new TableCell("周四"));
		headerSet1.add(new TableCell("周四"));
		headerSet1.add(new TableCell("周五"));
		headerSet1.add(new TableCell("周五"));
		headerSet1.add(new TableCell("周六"));
		headerSet1.add(new TableCell("周六"));
		headerSet1.add(new TableCell("周日"));
		headerSet1.add(new TableCell("周日"));
		headerSet1.add(new TableCell("汇总"));
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

		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));
		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("分值"));

		headerSet2.add(new TableCell("频率"));
		headerSet2.add(new TableCell("参考"));
		headerSet2.add(new TableCell("分值"));

		ReportExt reportExt = new ReportExt();

		List list = list1;
		int[] tmpdata = new int[17];
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String wdmc = object.get("outletsName").toString();// 网点名称
			String ywybm = object.get("ywybm").toString();// 业务员编号
			String ywyname = object.get("ywyname").toString();// 业务员姓名
			String pxm = object.get("brandName").toString();// 品牌

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
			String timesx = object.get("timesx").toString();// 频率依据
			String totalf = object.get("totalf").toString();// 分值统计

			TableRow tr = new TableRow();
			if (wdmc_check.equals("1")) {
				tr.addCell(new TableCell(wdmc));
			}
			if (lhy_check.equals("1")) {
				if (!ywybm.equals("")) {
					tr.addCell(new TableCell(ywybm + "/" + ywyname));
				} else {
					tr.addCell(new TableCell());
				}
			}
			if (pp_check.equals("1")) {
				tr.addCell(new TableCell(pxm));
			}

			tr.addCell(new TableCell(tabR(format, w1, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f1, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w2, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f2, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w3, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f3, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w4, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f4, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w5, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f5, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w6, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f6, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, w7, "week"),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(tabR(format, f7, "week"),
					Rectangle.ALIGN_RIGHT));

			if ((Integer.parseInt(timesx.trim())) > (Double.valueOf(totalx))
					.intValue()) {
				tr.addCell(new TableCell(fontColor(format, "total_", (Double
						.valueOf(totalx)).intValue()), Rectangle.ALIGN_RIGHT));
			} else {
				tr.addCell(new TableCell(fontColor(format, "total", (Double
						.valueOf(totalx)).intValue()), Rectangle.ALIGN_RIGHT));
			}

			tr.addCell(new TableCell(fontColor(format, "", (Integer
					.parseInt(timesx.trim()))), Rectangle.ALIGN_CENTER));

			tr.addCell(new TableCell(fontColor(format, "total", (Double
					.valueOf(totalf)).intValue()), Rectangle.ALIGN_RIGHT));

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
			tmpdata[15] += Double.valueOf(timesx);
			tmpdata[16] += Double.valueOf(totalf);
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
		totalTrData.add(new TableCell("" + tmpdata[16], Rectangle.ALIGN_RIGHT));// 

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

	// 取值
	public String tabR(String format, String wf, String info) {
		String str = "";
		if ((Double.valueOf(wf)).intValue() == 0) {
			str = "";
		} else {
			str = fontColor(format, "week", (Double.valueOf(wf)).intValue());
		}
		return str;
	}

	// 频率 颜色调协
	public String fontColor(String format, String type, int inf) {
		String info = "";
		if (type.equals("week")) {
			if (format.equals("html") && inf >= 1) {
				info = "<font color='blue'>" + inf + "</font>";
			} else {
				info = "" + inf;
			}
		} else if (type.equals("total")) {
			if (format.equals("html") && inf >= 1) {
				info = "<font color='blue' style='font-weight: bold;'>" + inf
						+ "</font>";
			} else {
				info = "" + inf;
			}
		} else if (type.equals("total_")) {
			if (format.equals("html")) {
				if (inf >= 1) {
					info = "<font style='font-weight: bold;background-color: yellow' color='red'>"
							+ inf + "</font>";
				} else {
					info = "<font style='background-color: yellow'>" + inf
							+ "</font>";
				}

			} else {
				info = "" + inf;
			}
		} else {
			if (format.equals("html")) {
				info = "<font color='red'>" + inf + "</font>";
			} else {
				info = "" + inf;
			}
		}
		return info;
	}

	public void queryNetPoint(HttpServletRequest request,
			HttpServletResponse response) {
		String naturalurl = request.getParameter("naturalurl");
		String condition = request.getParameter("condition");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		String json = "";
		try {
			Map conditionmap = new HashMap();
			if (StringUtils.isNotEmpty(condition)) {
				conditionmap.put("netName", condition.trim());
			}
			List<Map> list = (List) commonDAO.select("App.selectNetpointInfo",
					conditionmap);
			String path = request.getContextPath();
			StringBuilder str = new StringBuilder();
			for (Map data : list) {

				str.append("{");
				str.append("\"id\"");
				str.append(":\"");
				str.append(data.get("lsh") + "|");
				str.append("\",");

				str.append("\"text\"");
				str.append(":\"");
				str.append(data.get("netName"));
				str.append("\",");

				// str.append("\"attributes\":{");
				str.append("	\"linkurl\":\"" + path
						+ "/app.do?method=queryNetpointView2&fatherlsh="
						+ data.get("lsh") + "\"");
				// str.append("}");
				str.append(",");

				str.append("\"showcheck\":true,\"isexpand\": " + false
						+ ", \"complete\": true");

				Map conditionMap = new HashMap();
				conditionMap.put("fatherlsh", data.get("lsh"));
				List set = (List) commonDAO.select("App.selectNetpointDate",
						conditionMap);

				if (set.size() > 0) {

					// 二级
					StringBuffer jsonx = new StringBuffer();
					for (Iterator iterator = set.iterator(); iterator.hasNext();) {
						Map object = (Map) iterator.next();

						jsonx.append("{");
						jsonx.append("\"id\"");
						jsonx.append(":\"");
						jsonx.append(data.get("lsh") + "|"
								+ object.get("createDate"));
						jsonx.append("\",");

						jsonx.append("\"text\"");
						jsonx.append(":\"");
						jsonx.append(object.get("createDate"));
						jsonx.append("\",");

						// jsonx.append("\"attributes\":{");
						jsonx
								.append("	\"linkurl\":\""
										+ path
										+ "/app.do?method=queryNetpointView2&fatherlsh="
										+ data.get("lsh") + "&queryDate="
										+ object.get("createDate") + "\"");
						// str.append("}");
						jsonx.append(",");

						jsonx.append("\"showcheck\":true,\"isexpand\": "
								+ false + ", \"complete\": true");

						jsonx.append(",\"leaf\"");
						jsonx.append(":");
						jsonx.append("true");

						jsonx.append(",\"hasChildren\"");
						jsonx.append(":");
						jsonx.append("false");
						jsonx.append(",\"children\":null");
						jsonx.append("},");
					}

					str.append(",\"state\":" + (false ? false : "\"closed\""));

					str.append(",\"hasChildren\"");
					str.append(":");
					str.append("true");

					str.append(",\"leaf\"");
					str.append(":");
					str.append("false");

					str.append(",\"children\":[");
					str.append(jsonx.toString().substring(0,
							jsonx.toString().length() - 1));
					str.append("]},");
				} else {
					// 一级
					str.append(",\"leaf\"");
					str.append(":");
					str.append("true");

					str.append(",\"hasChildren\"");
					str.append(":");
					str.append("false");
					str.append(",\"children\":null");
					str.append("},");
				}

			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", "网点管理");
			if (StringUtils.isNotEmpty(str.toString())) {
				json = str.substring(0, str.length() - 1);
			}
			map.put("json", "[" + json + "]");

			jsonList.add(map);

			String html = DyFormComp.getEasyuiAccordionTree(jsonList);
			// request.setAttribute("accordhtml", html);
			// request.setAttribute("treejson", "[" + json + "]");

		} catch (Exception e) {
			log.error("查询网点失败!", e);
		} finally {
			super.writeJsonStr(response, "[" + json + "]");
		}
	}

	public void queryNetPointGrid(HttpServletRequest request,
			HttpServletResponse response) {
		String condition = request.getParameter("condition");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		User user = getOnlineUser(request);
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 开始索引
		PageInfo obj = new PageInfo();
		String usercode = user.getUserCode();
		Map conditionmap = new HashMap();
		if (StringUtils.isNotEmpty(condition)) {
			conditionmap.put("netName", condition.trim());
		}
		conditionmap.put("startIndex", start);
		conditionmap.put("pageSize", limit);

		try {
			obj = commonDAO.selectForPage("App.selectNetpointInfoPageCount",
					"App.selectNetpointInfoPage", conditionmap, obj);
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
			log.error("查询网点失败!", e);
		}
	}

	public void queryNetpointExtendInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String fatherlsh = request.getParameter("fatherlsh");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String netName = request.getParameter("netName");
		String ppname = request.getParameter("ppname");
		String createPerson = request.getParameter("createPerson");
		String point = request.getParameter("point");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(beginDate)) {
			conditionMap.put("beginDate", beginDate.trim());
		}
		if (StringUtils.isNotEmpty(endDate)) {
			conditionMap.put("endDate", endDate.trim());
		}
		if (StringUtils.isNotEmpty(netName)) {
			conditionMap.put("netName", netName.trim());
		}
		if (StringUtils.isNotEmpty(ppname)) {
			conditionMap.put("ppname", ppname.trim());
		}
		if (StringUtils.isNotEmpty(createPerson)) {
			conditionMap.put("createPerson", createPerson.trim());
		}
		if (StringUtils.isNotEmpty(point)) {
			conditionMap.put("point", point.trim());
		}
		if (StringUtils.isNotEmpty(fatherlsh)) {
			conditionMap.put("fatherlsh", fatherlsh);
		}
		try {
			List list = (List) commonDAO.select("App.selectNetpointPP",
					conditionMap);
			List list2 = (List) commonDAO.select("App.selectNetpointTime",
					conditionMap);
			List list3 = (List) commonDAO.select("App.selectNetpointNet",
					conditionMap);
			request.setAttribute("pplist", list);
			request.setAttribute("timelist", list2);
			request.setAttribute("netlist", list3);
		} catch (Exception e) {
			log.error("出错了!", e);
		}

	}

	public void findProTree(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		try {
			if ("0".equals(node)) {// 根节点
				Collection coll = commonDAO.select("App.findProInfo", node);
				String categoriesJsonStr = buildProJsonStr(coll);
				super.writeJsonStr(response, categoriesJsonStr);
			}
		} catch (Exception e) {
			log.error("出错", e);
		}

	}

	public void findOutletSetByLshId(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer jsonBuf = new StringBuffer();
		String lshId = request.getParameter("id");// 获取产品分类号
		String condition = request.getParameter("condition");// 模糊查询条件
		try {
			String split = "";
			Map map = new HashMap();
			map.put("lshId", lshId);
			if (StringUtils.isNotEmpty(condition)) {
				map.put("condition", condition.trim());
			}
			List list = (List) commonDAO
					.select("App.findOutletSetByLshId", map);
			// 遍历并构造json字符串
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map pInfo = (Map) iterator.next();

				String jsonStr = JSONObject.fromObject(pInfo).toString();
				jsonBuf.append(split + jsonStr);
				split = ",";
			}
		} catch (Exception e) {
			log.error("加载出错!", e);
		} finally {
			super.writeJsonStr(response, "{info:[" + jsonBuf + "]}");
		}
	}

	public void loadOutletsInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String related = request.getParameter("related");// 1.已关联 0.未关联
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		try {
			Collection infoResult = new ArrayList();

			String lshId = request.getParameter("id").trim();

			Map condition = new HashMap();
			if (StringUtils.isNotEmpty(name)) {
				condition.put("name", name.trim());
			}
			condition.put("lshId", lshId);
			infoResult = commonDAO.select("App.findUnChooseOutLetsByLshId",
					condition);

			String[] properties = { "outId", "outName" };
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = infoResult.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();

				String jsonStr = JSONUtil2.fromBean(map, properties, null,
						"yyyy-MM-dd HH:mm").toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			StringBuilder store = new StringBuilder();
			store.append("{info:[");
			store.append(jsonBuffer.toString());
			store.append("]}");

			super.writeJsonStr(response, store.toString());

		} catch (Exception e) {
			log.error("出错了!", e);
		}
	}

	private String buildProJsonStr(Collection col) {
		List jSonSet = new ArrayList();
		for (Iterator itr = col.iterator(); itr.hasNext();) {
			Map pc2 = (Map) itr.next();
			String str = "{id: \"" + pc2.get("lshId") + "\",code: \""
					+ pc2.get("proCode") + "\", text:\"" + pc2.get("proName")
					+ "\"}";

			jSonSet.add(str);
		}

		String str = StringUtils.join(jSonSet.iterator(), ",");
		str = "[" + str + "]";
		return str;
	}

	public void saveOutlets(HttpServletRequest request,
			HttpServletResponse response) {
		String outId = request.getParameter("outIdStr");
		String brandId = request.getParameter("lshId");
		String times = request.getParameter("times");
		String note = request.getParameter("note");
		String statusx = request.getParameter("statusx");
		String tip = "新增成功!";
		List list = new ArrayList();
		Map conditionMap = null;
		try {
			// 获取当前操作者
			User user = getOnlineUser(request);
			String[] outIdArr = outId.split(",");
			for (int i = 0; i < outIdArr.length; i++) {
				conditionMap = new HashMap();
				conditionMap.put("outletsId", outIdArr[i]);
				conditionMap.put("brandId", brandId);
				conditionMap.put("times", times);
				conditionMap.put("note", note);
				conditionMap.put("statusx", statusx);
				conditionMap.put("operate", user.getUserName());
				conditionMap.put("createdDate", TimeUtil.parseDateTime(TimeUtil
						.formatDateTime(new Date())));
				list.add(conditionMap);
			}
			commonDAO.insertBatch("App.insertBatchOutletsTimes", list);

		} catch (Exception e) {
			tip = "新增失败！";
			log.error("新增加失败！", e);
		} finally {
			tip = "{tip:\"" + tip + "\"}";
			super.writeJsonStr(response, tip);
		}
	}

	public void updateTimes(HttpServletRequest request,
			HttpServletResponse response) {
		String tip = "";
		String tallId = request.getParameter("tallId");
		String outletsId = request.getParameter("outletsId");
		String brandId = request.getParameter("brandId");
		String times = request.getParameter("times");
		String note1 = request.getParameter("note");

		User user = getOnlineUser(request);

		Map conditionMap = new HashMap();
		try {
			String note = URLDecoder.decode(note1, "UTF-8");// 去除中文乱码
			conditionMap.put("tallId", tallId);
			if (StringUtils.isNotEmpty(times)) {
				conditionMap.put("times", times);
			}
			if (StringUtils.isNotEmpty(note)) {
				conditionMap.put("note", note);
			}
			conditionMap.put("operate", user.getUserName());
			conditionMap.put("createdDate", TimeUtil.parseDateTime(TimeUtil
					.formatDateTime(new Date())));
			commonDAO.update("App.updateOutletsTimes", conditionMap);
		} catch (Exception e) {
			tip = "更新频率失败！";
			log.error("更新频率失败！", e);
		} finally {
			tip = "{tip:\"" + tip + "\"}";
			super.writeJsonStr(response, tip);
		}
	}

	public void deleteOutlets(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String tallIdStr = request.getParameter("tallIdStr");// 获取数据字典编号
		String[] tallIdStrArr = tallIdStr.split(",");
		List tallIdList = new ArrayList();
		try {
			for (int i = 0; i < tallIdStrArr.length; i++) {
				tallIdList.add(tallIdStrArr[i]);
			}
			commonDAO.deleteBatch("App.deleteOutlets", tallIdList);// 删除
			json.put("tip", "删除网点配置信息成功!");
		} catch (Exception e) {
			json.put("tip", "删除失败，请与管理员联系!");
			json.put("error", "yes");
			log.error("删除失败，请与管理员联系!", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void exportAppOBR(HttpServletRequest request,
			HttpServletResponse response) {
		String format = request.getParameter("format");
		String t = request.getParameter("t");

		try {
			GroupReport groupReport = new GroupReport();

			String lshId = request.getParameter("lshId");
			String tallIdStr = request.getParameter("tallIdStr");
			Map map = new HashMap();
			if (StringUtils.isNotEmpty(lshId)) {
				map.put("lshId", lshId.trim());
			}
			if (StringUtils.isNotEmpty(tallIdStr)) {
				map.put("tallId", tallIdStr.trim().split(","));
			}
			Long currentTimeMillis = System.currentTimeMillis();
			groupReport.format(format, "理货频率配置" + currentTimeMillis,
					getReport_outletsByPx(map), response);

		} catch (Exception e) {
			log.error("导出信息出错!", e);
		}
	}

	private Report getReport_outletsByPx(Map map) throws Exception {// 根据品牌导出
		Collection result = commonDAO.select("App.export_outlets", map);
		String[] dataHeaderSet = { "brandName", "outletsName", "times", "note",
				"statusx", "creadDate", "operate" };
		String[] headerSet = { "品牌", "网点名称", "每周几次", "备注", "状态", "创建时间", "录单人" };
		Map<String, Integer> position = new HashMap();
		position.put("times", Rectangle.ALIGN_RIGHT);
		ReportExt reportExt = new ReportExt();
		Report report = reportExt.getReportList(result, dataHeaderSet,
				headerSet, position);

		reportExt.setSimpleTitleFooter(report, "理货频率配置");
		return report;
	}

	public void deleteStoreJson(HttpServletRequest request,
			HttpServletResponse response) {
		String lsh = request.getParameter("lsh");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(lsh)) {
				commonDAO.delete("App.deleteStoreJson", lsh);
				json.put("tip", "删除成功!");
			}
		} catch (Exception e) {
			json.put("tip", "删除失败!");
			json.put("error", "yes");
			log.error("出错了", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void loadStoreJson(HttpServletRequest request,
			HttpServletResponse response) {
		String lsh = request.getParameter("lsh");
		String json = "";
		try {
			Map map = (Map) commonDAO.findForObject("App.selectStoreJsonByLsh",
					lsh);
			json = (String) map.get("json");
		} catch (Exception e) {
			log.error("出错了", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void queryStoreJson(HttpServletRequest request,
			HttpServletResponse response) {
		String node = request.getParameter("node");
		String type = "outstorageReport";
		try {
			User user = getOnlineUser(request);
			Map map = new HashMap();
			map.put("user", user.getUserId());
			map.put("type", type);
			Collection coll = new ArrayList();
			if ("0".equals(node)) {
				coll = commonDAO.select("App.selectStoreJson", map);
			}
			String categoriesJsonStr = buildStoreJsonStr(coll);
			super.writeJsonStr(response, categoriesJsonStr);
		} catch (Exception e) {
			log.error("出错", e);
		}

	}

	public void saveOrUpdateStoreJson(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name");
		String lsh = request.getParameter("lsh");
		String type = "outstorageReport";
		JSONObject json = new JSONObject();
		try {
			User user = getOnlineUser(request);
			Map map = new HashMap();
			map.put("user", user.getUserId());
			map.put("name", name);
			map.put("type", type);
			JSONObject jsonstr = setRequestJSON(request);
			map.put("json", jsonstr);
			if (StringUtils.isNotEmpty(lsh)) {
				map.put("lsh", lsh);
				commonDAO.update("App.updateStoreJson", map);
			} else {
				commonDAO.insert("App.insertStoreJson", map);
			}
			json.put("tip", "保存成功!");
		} catch (Exception e) {
			json.put("tip", "保存失败!");
			json.put("error", "yes");
			log.error("出错了", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void saveIcsale(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String FInterID = request.getParameter("FInterID");
		try {
			CommonDAO dao = getHgDAO();
			Map icstockbill = (Map) dao.findForObject(
					"HG.selectIcstockbillInfo", Integer.parseInt(FInterID));
			List<Map> icstockbillentry = new ArrayList();
			if (icstockbill != null) {
				Integer FInterIDx = Integer.parseInt(icstockbill
						.get("FInterID").toString());
				icstockbillentry = (List<Map>) dao.select(
						"HG.selectIcstockbillentry", FInterIDx);
			}

			if (icstockbillentry.size() > 0) {
				Map icsaleData = insertIcsale(icstockbill, dao);
				BigDecimal FAmountSum = insertICSaleEntry(icsaleData,
						icstockbill, icstockbillentry, dao);

				int r = FAmountSum.compareTo(BigDecimal.ZERO);
				if (r == -1) {// 款小于0
					dao.update("HG.updateIcsaleFROB-1", icsaleData
							.get("FInterID"));
				}

				p_UpdateBillRelateData(Integer.parseInt(icsaleData.get(
						"FInterID").toString()), dao);

				Integer hookid = insertICHookRelations(icstockbill, icsaleData,
						icstockbillentry, dao);

				updateIcstockbill(hookid, Integer.parseInt(icsaleData.get(
						"FInterID").toString()), icstockbill, dao);

				updateIcstockbillentry(icstockbillentry, dao);

				updateICSale(hookid, icsaleData, dao);

				Map contactMap = insertT_RP_Contact(icstockbill, Integer
						.parseInt(icsaleData.get("FInterID").toString()),
						FAmountSum, dao);
				insertT_RP_plan_ar(contactMap, Integer.parseInt(icsaleData.get(
						"FInterID").toString()), dao);

				updateOrgInfo(icsaleData, FAmountSum, dao);
				updateT_RP_ContactBal(icsaleData, FAmountSum, dao);

				json.put("tip", "生成销售发票成功!");
			} else {
				json.put("error", "yes");
				json.put("tip", "生成销售发票失败!");
			}

		} catch (Exception e) {
			json.put("tip", "生成销售发票失败!");
			json.put("error", "yes");
			log.error("出错了", e);
			throw new RuntimeException("生成销售发票失败!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void saveAllIcsale(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String FInterID = request.getParameter("FInterID");
		try {
			Map conditionMap = new HashMap();
			String FBillNo = request.getParameter("FBillNo");
			String FStartDate = request.getParameter("FStartDate");
			String FEndDate = request.getParameter("FEndDate");
			if (StringUtils.isNotEmpty(FBillNo)) {
				conditionMap.put("FBillNo", FBillNo.trim());
			}
			if (StringUtils.isNotEmpty(FStartDate)) {
				conditionMap.put("FStartDate", FStartDate.trim());
			}
			if (StringUtils.isNotEmpty(FEndDate)) {
				conditionMap.put("FEndDate", FEndDate.trim());
			}

			CommonDAO dao = getHgDAO();
			List icstockbilllist = (List) dao.select(
					"HG.selectAllIcstockbillInfo", conditionMap);
			for (Iterator iterator = icstockbilllist.iterator(); iterator
					.hasNext();) {
				Map icstockbill = (Map) iterator.next();
				List<Map> icstockbillentry = new ArrayList();
				if (icstockbill != null) {
					Integer FInterIDx = Integer.parseInt(icstockbill.get(
							"FInterID").toString());
					icstockbillentry = (List<Map>) dao.select(
							"HG.selectIcstockbillentry", FInterIDx);
				}

				if (icstockbillentry.size() > 0) {
					Map icsaleData = insertIcsale(icstockbill, dao);
					BigDecimal FAmountSum = insertICSaleEntry(icsaleData,
							icstockbill, icstockbillentry, dao);

					int r = FAmountSum.compareTo(BigDecimal.ZERO);
					if (r == -1) {// 款小于0
						dao.update("HG.updateIcsaleFROB-1", icsaleData
								.get("FInterID"));
					}

					p_UpdateBillRelateData(Integer.parseInt(icsaleData.get(
							"FInterID").toString()), dao);

					Integer hookid = insertICHookRelations(icstockbill,
							icsaleData, icstockbillentry, dao);

					updateIcstockbill(hookid, Integer.parseInt(icsaleData.get(
							"FInterID").toString()), icstockbill, dao);

					updateIcstockbillentry(icstockbillentry, dao);

					updateICSale(hookid, icsaleData, dao);

					Map contactMap = insertT_RP_Contact(icstockbill, Integer
							.parseInt(icsaleData.get("FInterID").toString()),
							FAmountSum, dao);
					insertT_RP_plan_ar(contactMap, Integer.parseInt(icsaleData
							.get("FInterID").toString()), dao);

					updateOrgInfo(icsaleData, FAmountSum, dao);
					updateT_RP_ContactBal(icsaleData, FAmountSum, dao);
				}

			}
			json.put("tip", "生成销售发票成功!");
		} catch (Exception e) {
			json.put("tip", "生成销售发票失败!");
			json.put("error", "yes");
			log.error("出错了", e);
			throw new RuntimeException("生成销售发票失败!");
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	/**
	 * 1.生成发票
	 * 
	 * @param data
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	private Map insertIcsale(Map data, CommonDAO dao) throws Exception {
		Map map = new HashMap();
		map.put("FBrNo", 0);
		String FBillNo = (String) data.get("FBillNo");
		String fdatestr = StringUtils.substring(FBillNo, 2, 6) + "-"
				+ StringUtils.substring(FBillNo, 6, 8) + "-"
				+ StringUtils.substring(FBillNo, 8, 10);
		Date fdate = TimeUtil.parseDate(fdatestr);

		// map.put("FInterID", null);
		map.put("FBillNo", data.get("FBillNo"));
		map.put("FTranType", 86);
		map.put("FDate", fdate);
		map.put("FCustID", data.get("FSupplyID"));
		map.put("FNote", "OA生成销售发票(普通)");

		map.put("FCurrencyID", 1);
		map.put("FPayStyleID", null);
		map.put("FTransportStyle", null);
		map.put("FDeptID", data.get("FDeptID"));
		map.put("FEmpID", data.get("FEmpID"));
		map.put("FHookInterID", null);// 暂为空
		map.put("FVchInterID", 0);

		map.put("FBillerID", 16409);// 管理员 16409陈晓燕
		map.put("FCheckerID", 16409);// 管理员 16409陈晓燕
		map.put("FPosterID", null);
		map.put("FManagerID", data.get("FManagerID"));
		map.put("FPosted", 0);
		map.put("FClosed", 0);
		map.put("FSettleID", 0);
		map.put("FROB", 1);

		map.put("FRSCBillNo", null);
		map.put("FExchangeRate", 1.0);
		map.put("FStatus", 0);
		map.put("FCompactNo", "");
		map.put("FCancellation", data.get("FCancellation"));
		map.put("FSaleStyle", data.get("FSaleStyle"));
		map.put("FAcctID", 0);
		map.put("FMultiCheckLevel1", data.get("FMultiCheckLevel1"));
		map.put("FMultiCheckLevel2", data.get("FMultiCheckLevel2"));
		map.put("FMultiCheckLevel3", data.get("FMultiCheckLevel3"));
		map.put("FMultiCheckLevel4", data.get("FMultiCheckLevel4"));

		map.put("FMultiCheckLevel5", data.get("FMultiCheckLevel5"));
		map.put("FMultiCheckLevel6", data.get("FMultiCheckLevel6"));
		map.put("FMultiCheckDate1", data.get("FMultiCheckDate1"));
		map.put("FMultiCheckDate2", data.get("FMultiCheckDate2"));
		map.put("FMultiCheckDate3", data.get("FMultiCheckDate3"));
		map.put("FMultiCheckDate4", data.get("FMultiCheckDate4"));
		map.put("FMultiCheckDate5", data.get("FMultiCheckDate5"));
		map.put("FMultiCheckDate6", data.get("FMultiCheckDate6"));
		map.put("FCurCheckLevel", data.get("FCurCheckLevel"));
		map.put("FYearPeriod", TimeUtil.formatDate(fdate, "yyyy-MM"));
		map.put("FYtdIntRate", 0);
		map.put("FOrgBillInterID", 0);

		map.put("FUUID", "newid()");
		map.put("FOperDate", "(cast(getdate() as timestamp))");
		map.put("FImport", data.get("FImport"));
		map.put("FSystemType", 1);
		map.put("FArApStatus", 0);// 生成为0 审核为1
		map.put("FYear", TimeUtil.getYear(fdate));
		map.put("FPeriod", TimeUtil.getMonth(fdate));
		map.put("FSubSystemID", 0);
		map.put("FFincDate", fdate);
		map.put("FInvoicer", 0);
		map.put("FAccount", null);
		map.put("FTaxNum", null);

		map.put("FHookerID", 0);// 初始为0 审核后更新新值
		map.put("FTranStatus", data.get("FTranStatus"));
		map.put("FOuterID", 0);
		map.put("FClassTypeID", "1000000");
		map.put("FItemClassID", 1);
		map.put("finterestrate", 0.0);
		map.put("FCOMINVID", null);
		map.put("FTaskID", 0);
		map.put("FOrderID", 0);
		map.put("FResourceID", 0);

		map.put("FBudgetAmountFor", 0);
		map.put("FOrderAmountFor", 0);
		map.put("FFreeItem1", null);
		map.put("FFreeItem2", null);
		map.put("FFreeItem3", null);
		map.put("FFreeItem4", null);
		map.put("FAddress", null);
		map.put("FBank", null);
		map.put("FCheckDate", TimeUtil.formatDate(new Date()));

		map.put("FExplanation", "");
		map.put("FPayStyle", 0);
		map.put("FAdjustExchangeRate", 1);
		map.put("FAdjustAmount", 0);
		map.put("FSelTranType", 21);
		map.put("FChildren", 0);// 0=>1
		map.put("FHookStatus", 2);// 勾稽状态 提交已勾稽
		map.put("FActPriceVchTplID", data.get("FActPriceVchTplID"));

		map.put("FPlanPriceVchTplID", data.get("FPlanPriceVchTplID"));
		map.put("FActualVchTplID", data.get("FActualVchTplID"));
		map.put("FPlanVchTplID", data.get("FPlanVchTplID"));
		map.put("FBrID", data.get("FBrID"));
		map.put("FCussentAcctID", 1347);// 往来科目 默认应收账款
		map.put("FCheckStatus", 0);
		map.put("fconnectflag", 0);
		map.put("FCheckAmount", 0);
		map.put("FCheckAmountFor", 0);
		map.put("FRemainAmount", 0);
		map.put("FRemainAmountFor", 0);

		map.put("FDC", 1);
		map.put("FGUID", "newid()");// 数据库生成
		map.put("FPOOrdBillNo", "");
		map.put("FRelateBrID", null);

		map = (Map) dao.insert("HG.insertIcsale", map);
		return map;
	}

	/**
	 * 1.生成发票子表单
	 * 
	 * @param form
	 * @param sourceform
	 * @param datas
	 * @param dao
	 * @throws Exception
	 */
	private BigDecimal insertICSaleEntry(Map form, Map sourceform,
			List<Map> datas, CommonDAO dao) throws Exception {
		List list = new ArrayList();
		BigDecimal FAmount = new BigDecimal("0");
		for (int i = 0; i < datas.size(); i++) {
			Map data = datas.get(i);

			Map map = new HashMap();
			map.put("FBrNo", 0);

			map.put("FInterID", form.get("FInterID"));
			map.put("FEntryID", data.get("FEntryID"));
			// map.put("FDetailID", null);
			map.put("FItemID", data.get("FItemID"));
			map.put("FQty", data.get("FQty"));
			map.put("FPrice", data.get("FEntrySelfB0167"));
			map.put("FAmount", data.get("FConsignAmount"));
			map.put("FTaxRate", 0);
			map.put("FTaxAmount", 0);
			FAmount = FAmount.add((BigDecimal) data.get("FConsignAmount"));

			map.put("FCommitQty", 0);
			map.put("FUnitID", data.get("FUnitID"));
			map.put("FAuxCommitQty", 0);
			map.put("FAuxPrice", data.get("FConsignPrice"));
			map.put("FAuxQty", data.get("FAuxQty"));
			map.put("FTaxPrice", data.get("FEntrySelfB0167"));
			map.put("FAuxTaxPrice", data.get("FConsignPrice"));
			map.put("FSourceEntryID", data.get("FEntryID"));
			map.put("FDiscountRate", 0);
			map.put("FMapNumber", data.get("FMapNumber"));

			map.put("FMapName", data.get("FMapName"));
			map.put("FOrgBillEntryID", 0);
			map.put("FOrderPrice", 0);
			map.put("FAuxOrderPrice", 0);
			map.put("FNote", "OA生成销售发票(普通)");
			map.put("FStdAmount", data.get("FConsignAmount"));
			map.put("FStdTaxAmount", 0);
			map.put("FAmountincludetax", data.get("FConsignAmount"));
			map.put("FStdAmountincludetax", data.get("FConsignAmount"));
			map.put("fauxqty_base", 0);
			map.put("FBatchNo", data.get("FBatchNo"));

			map.put("FCOMINVID", null);
			map.put("FEntryID_SRC", 0);
			map.put("FClassID_SRC", 21);// 关联合同号 关联源单类型
			map.put("FFreeItem1", null);
			map.put("FFreeItem2", null);
			map.put("FFreeItem3", null);
			map.put("FFreeItem4", null);
			map.put("FAuxPropID", data.get("FAuxPropID"));
			map.put("FAmtDiscount", 0);

			map.put("FStdAmtDiscount", 0);
			map.put("FKFDate", null);
			map.put("FKFperiod", 0);
			map.put("FPeriodDate", data.get("FPeriodDate"));
			map.put("FPriceDiscount", data.get("FEntrySelfB0167"));
			map.put("FAuxPriceDiscount", data.get("FConsignPrice"));
			map.put("FsecCoefficient", 0);
			map.put("FSecQty", data.get("FSecQty"));
			map.put("FQuantityReceive_Commit", 0);
			map.put("FQuantityPayApply_Commit", 0);

			map.put("FSecUnitID", 0);
			map.put("FAmountFor_Commit", 0);
			map.put("FAmount_Commit", 0);
			map.put("FAllAmount", 0);
			map.put("FStdAllAmount", 0);
			map.put("FSecCommitQty", data.get("FSecCommitQty"));
			map.put("FSourceTranType", 21);// 源单类型
			map.put("FSourceInterId", sourceform.get("FInterID"));// 源单内码
			map.put("FSourceBillNo", sourceform.get("FBillNo"));
			map.put("FContractInterID", data.get("FContractInterID"));

			map.put("FContractEntryID", data.get("FContractEntryID"));
			map.put("FContractBillNo", data.get("FContractBillNo"));
			map.put("FOrderInterID", data.get("FOrderInterID"));
			map.put("FOrderEntryID", data.get("FOrderEntryID"));
			map.put("FOrderBillNo", data.get("FOrderBillNo"));
			map.put("FAllHookQTY", data.get("FQty"));
			map.put("FAllHookAmount", data.get("FConsignAmount"));
			map.put("FCurrentHookQTY", data.get("FCurrentHookQTY"));
			map.put("FCurrentHookAmount", data.get("FCurrentHookAmount"));
			map.put("FStdAllHookAmount", data.get("FConsignAmount"));
			map.put("FStdCurrentHookAmount", data.get("FStdCurrentHookAmount"));
			map.put("FSplitSecQty", data.get("FSplitSecQty"));

			map.put("FPurchaseCommitQty", 0);
			map.put("FPurchaseSecCommitQty", 0);
			map.put("FCheckQty", 0);
			map.put("FRemainQty", data.get("FAuxQty"));
			map.put("FRemainAmount", data.get("FConsignAmount"));
			map.put("FCheckAmount", 0);
			map.put("FRemainAmountFor", data.get("FConsignAmount"));
			map.put("FCheckAmountFor", 0);
			map.put("FLinkCheckAmountFor", 0);
			map.put("FLinkCheckAmount", 0);
			map.put("FLinkCheckQty", 0);
			map.put("FGUID", "newid()");

			list.add(map);
		}
		dao.insertBatch("HG.insertICSaleEntry", list);
		return FAmount;
	}

	/**
	 * 2.生成销售与发票关系
	 * 
	 * @param icstockbillData
	 * @param icsaleData
	 * @param datas
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	private Integer insertICHookRelations(Map icstockbillData, Map icsaleData,
			List<Map> datas, CommonDAO dao) throws Exception {
		Integer fgroupno = (Integer) dao.findForObject(
				"HG.selectMaxICHookRelations", "");
		List list = new ArrayList();
		// 销售出库
		for (int i = 0; i < datas.size(); i++) {
			Map data = datas.get(i);
			Map map = new HashMap();
			map.put("FGroupNo", fgroupno);
			map.put("FHookType", 1);
			map.put("FBrNo", 0);
			map.put("FIBTag", 1);
			map.put("FIBInterID", icstockbillData.get("FInterID"));
			map.put("FIBNo", icstockbillData.get("FBillNo"));
			map.put("FNowCheck", "X");
			map.put("FPeriod", TimeUtil.getMonth((Date) icstockbillData
					.get("FDate")));
			map.put("FYear", TimeUtil.getYear((Date) icstockbillData
					.get("FDate")));
			map.put("FEquityHook", 0);
			map.put("FDate", icstockbillData.get("FDate"));
			map.put("FEntryID", i + 1);
			map.put("FItemID", data.get("FItemID"));
			map.put("FAuxPropID", data.get("FAuxPropID"));
			map.put("FHookQty", data.get("FQty"));
			map.put("FCustID", icstockbillData.get("FSupplyID"));
			map.put("FSupplyID", 0);
			map.put("FHookerID", 16409);// 操作者 填写管理员 16409陈晓燕
			map.put("FPOStyle", 0);
			map.put("FSaleStyle", icstockbillData.get("FSaleStyle"));
			map.put("FHookAmount", 0);
			map.put("FTranType", 21);
			map.put("FHookOperateType", 0);
			list.add(map);
		}
		// 发票
		for (int i = 0; i < datas.size(); i++) {
			Map data = datas.get(i);

			String FBillNo = (String) icsaleData.get("FBillNo");
			String fdatestr = StringUtils.substring(FBillNo, 2, 6) + "-"
					+ StringUtils.substring(FBillNo, 6, 8) + "-"
					+ StringUtils.substring(FBillNo, 8, 10);
			Date fdate = TimeUtil.parseDate(fdatestr);

			Map map = new HashMap();
			map.put("FGroupNo", fgroupno);
			map.put("FHookType", 1);
			map.put("FBrNo", 0);
			map.put("FIBTag", 0);
			map.put("FIBInterID", icsaleData.get("FInterID"));
			map.put("FIBNo", icsaleData.get("FBillNo"));
			map.put("FNowCheck", "X");
			map.put("FPeriod", TimeUtil.getMonth(fdate));
			map.put("FYear", TimeUtil.getYear(fdate));
			map.put("FEquityHook", 0);
			map.put("FDate", fdate);
			map.put("FEntryID", i + 1);
			map.put("FItemID", data.get("FItemID"));
			map.put("FAuxPropID", data.get("FAuxPropID"));
			map.put("FHookQty", data.get("FQty"));
			map.put("FCustID", icstockbillData.get("FSupplyID"));
			map.put("FSupplyID", 0);
			map.put("FHookerID", 16409);// 操作者 填写管理员 16409陈晓燕
			map.put("FPOStyle", 0);
			map.put("FSaleStyle", icstockbillData.get("FSaleStyle"));
			map.put("FHookAmount", data.get("FConsignAmount"));
			map.put("FTranType", 86);
			map.put("FHookOperateType", 0);
			list.add(map);
		}
		dao.insertBatch("HG.insertICHookRelations", list);
		return fgroupno;
	}

	/**
	 * 更新销售出库状态
	 * 
	 * @param FHookInterID
	 * @param FRelateInvoiceID
	 * @param icstockbillData
	 * @param dao
	 * @throws Exception
	 */
	private void updateIcstockbill(int FHookInterID, int FRelateInvoiceID,
			Map icstockbillData, CommonDAO dao) throws Exception {
		icstockbillData.put("FHookInterID", FHookInterID);
		icstockbillData.put("FRelateInvoiceID", FRelateInvoiceID);
		// icstockbillData.put("FOperDate", "(cast(getdate() as timestamp))");
		icstockbillData.put("FChildren", 1);
		icstockbillData.put("FHookStatus", 2);
		dao.update("HG.updateIcstockbill", icstockbillData);
	}

	/**
	 * 更新销售出库子表单信息
	 * 
	 * @param icstockbillentrydata
	 * @param dao
	 * @throws Exception
	 */
	private void updateIcstockbillentry(List<Map> datas, CommonDAO dao)
			throws Exception {
		List list = new ArrayList();
		for (int i = 0; i < datas.size(); i++) {
			Map data = datas.get(i);

			data.put("FQtyInvoice", data.get("FQty"));
			data.put("FAllHookQTY", data.get("FQty"));
			data.put("FAuxQtyInvoice", data.get("FAuxQty"));
			list.add(data);
		}

		dao.updateBatch("HG.updateIcstockbillentry", list);
	}

	/**
	 * 更新发票状态
	 * 
	 * @param FHookInterID
	 * @param icsaleData
	 * @param dao
	 * @throws Exception
	 */
	private void updateICSale(int FHookInterID, Map icsaleData, CommonDAO dao)
			throws Exception {
		icsaleData.put("FStatus", 1);
		icsaleData.put("FHookInterID", FHookInterID);
		icsaleData.put("FHookerID", 16409);// 管理员 16409陈晓燕
		icsaleData.put("FCheckerID", 16409);// 管理员 16409陈晓燕
		icsaleData.put("FArApStatus", 1);
		// icsaleData.put("FYearPeriod", "");
		dao.update("HG.updateICSale", icsaleData);
	}

	private void p_UpdateBillRelateData(int FInterID, CommonDAO dao)
			throws Exception {
		dao.update("HG.p_UpdateBillRelateData", FInterID);
	}

	private void updateOrgInfo(Map data, BigDecimal FAmountSum, CommonDAO dao)
			throws Exception {
		String FBillNo = (String) data.get("FBillNo");
		String fdatestr = StringUtils.substring(FBillNo, 2, 6) + "-"
				+ StringUtils.substring(FBillNo, 6, 8) + "-"
				+ StringUtils.substring(FBillNo, 8, 10);

		Map d = new HashMap();
		d.put("FLastTradeDate", fdatestr);
		d.put("FItemID", data.get("FCustID"));
		d.put("FLastTradeAmount", FAmountSum);
		Integer s = (Integer) dao.findForObject("HG.selectLastTradeDateOrg", d);
		if (s > 0) {
			dao.update("HG.UpdateOrgLastTradeDateAndAmount", d);
		}
	}

	private void updateT_RP_ContactBal(Map data, BigDecimal FAmountSum,
			CommonDAO dao) throws Exception {
		Map d = new HashMap();
		d.put("FAccountID", 1347);// 科目
		d.put("Amount", FAmountSum);
		d.put("FYear", data.get("FYear"));
		d.put("FPeriod", data.get("FPeriod"));
		d.put("FCustomer", data.get("FCustID"));
		d.put("FDepartment", data.get("FDeptID"));
		d.put("FEmployee", data.get("FEmpID"));
		Integer s = (Integer) dao.findForObject(
				"HG.selectCountT_RP_ContactBal", d);
		if (s > 0) {
			dao.update("HG.updateT_RP_ContactBal", d);
		} else {
			dao.insert("HG.insertT_RP_ContactBal", d);
		}
	}

	/**
	 * 3.应收
	 * 
	 * @param icsaleData
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	private Map insertT_RP_Contact(Map data, Integer icsaleID,
			BigDecimal FAmountSum, CommonDAO dao) throws Exception {
		Map map = new HashMap();
		String FBillNo = (String) data.get("FBillNo");
		String fdatestr = StringUtils.substring(FBillNo, 2, 6) + "-"
				+ StringUtils.substring(FBillNo, 6, 8) + "-"
				+ StringUtils.substring(FBillNo, 8, 10);
		Date fdate = TimeUtil.parseDate(fdatestr);
		// map.put("FID", null);
		map.put("FYear", TimeUtil.getYear(fdate));
		map.put("FPeriod", TimeUtil.getMonth(fdate));
		map.put("FRP", 1);
		map.put("FType", 3);
		map.put("FDate", fdate);
		map.put("FFincDate", fdate);
		map.put("FNumber", FBillNo);
		map.put("FCustomer", data.get("FSupplyID"));
		map.put("FDepartment", data.get("FDeptID"));
		map.put("FEmployee", data.get("FEmpID"));
		map.put("FCurrencyID", 1);
		map.put("FExchangeRate", 1.0);
		map.put("FAmount", FAmountSum);
		map.put("FAmountFor", FAmountSum);
		map.put("FRemainAmount", FAmountSum);
		map.put("FRemainAmountFor", FAmountSum);
		map.put("FContractNo", null);
		map.put("FInvoiceID", icsaleID);

		map.put("FRPBillID", 0);
		map.put("FBillID", 0);
		map.put("FBegID", 0);
		map.put("FExpenseID", 0);
		map.put("FBussinessDiscount", 0);
		map.put("FCashDiscount", 0);
		map.put("FRPDate", fdate);

		map.put("FSuperDays", 0);
		map.put("FDirectSale", 0);
		map.put("FSaleBackAmount", 0);
		map.put("FSaleBackAmountFor", 0);
		map.put("FDue", 0);
		map.put("FIsBad", 0);
		map.put("FBadReason", null);

		map.put("FVoucherID", 0);
		map.put("FGroupID", 0);
		map.put("FAccountID", 0);
		map.put("FIsInit", 0);
		map.put("FStatus", 1);
		map.put("FPost", 0);
		map.put("FToBal", 1);
		map.put("FPre", 0);
		map.put("FK3Import", 1);
		map.put("FInterestRate", 0.0);
		map.put("FCheckType", 0);
		map.put("FBillType", 1);
		map.put("FInvoiceType", 1);
		map.put("FItemClassID", 1);
		map.put("FExplanation", "");
		map.put("FSmInvID", 0);
		map.put("FPreparer", "16409");// 陈晓燕
		// map.put("FModifyTime", "(cast(getdate() as timestamp))");
		map.put("UUID", "newid()");

		map = (Map) dao.insert("HG.InsertT_RP_Contact", map);
		return map;
	}

	/**
	 * 应收计划
	 */
	private void insertT_RP_plan_ar(Map RP_Contact_data, Integer icsaleID,
			CommonDAO dao) throws Exception {
		Map map = new HashMap();
		map.put("FOrgID", RP_Contact_data.get("FID"));
		map.put("FDate", RP_Contact_data.get("FDate"));
		map.put("FAmount", RP_Contact_data.get("FAmount"));
		map.put("FAmountFor", RP_Contact_data.get("FAmountFor"));
		map.put("FRemainAmount", RP_Contact_data.get("FRemainAmount"));
		map.put("FRemainAmountFor", RP_Contact_data.get("FRemainAmountFor"));
		map.put("FType", 0);
		map.put("FExplanation", null);
		map.put("FInterID", icsaleID);
		map.put("FBillID", 0);
		map.put("FEntryID", 0);
		map.put("FRP", 1);
		map.put("FIsInit", 0);
		dao.insert("HG.insertT_RP_plan_ar", map);
	}

	public void queryIcsale(HttpServletRequest request,
			HttpServletResponse response) {
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo obj = new PageInfo();
		Map conditionMap = new HashMap();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		String FBillNo = request.getParameter("FBillNo");
		String FStartDate = request.getParameter("FStartDate");
		String FEndDate = request.getParameter("FEndDate");
		String sale = request.getParameter("sale");
		if (StringUtils.isNotEmpty(FBillNo)) {
			conditionMap.put("FBillNo", FBillNo.trim());
		}
		if (StringUtils.isNotEmpty(FStartDate)) {
			conditionMap.put("FStartDate", FStartDate.trim());
		}
		if (StringUtils.isNotEmpty(FEndDate)) {
			conditionMap.put("FEndDate", FEndDate.trim());
		}
		if (StringUtils.isNotEmpty(sale)) {
			conditionMap.put("sale", sale.trim());
		}
		CommonDAO dao = getHgDAO();
		try {
			obj = dao.selectForPage("HG.selectIcstockbillByCount",
					"HG.selectIcstockbill", conditionMap, obj);

			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Map data = (Map) iterator.next();
				data.put("FDate", data.get("FDate").toString());
				String jsonStr = JSONUtil2.fromBean(data).toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("出错了", e);
		}

	}

	public void exportsellinfo(HttpServletRequest request,
			HttpServletResponse response) {
		String FBillNo = request.getParameter("FBillNo");
		String FStartDate = request.getParameter("FStartDate");
		String FEndDate = request.getParameter("FEndDate");
		String sale = request.getParameter("sale");
		String FSupplyIDName = request.getParameter("FSupplyIDName");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(FSupplyIDName)) {
			conditionMap.put("FSupplyIDName", FSupplyIDName.trim());
		}
		if (StringUtils.isNotEmpty(FBillNo)) {
			conditionMap.put("FBillNo", FBillNo.trim());
		}
		if (StringUtils.isNotEmpty(FStartDate)) {
			conditionMap.put("FStartDate", FStartDate.trim());
		}
		if (StringUtils.isNotEmpty(FEndDate)) {
			conditionMap.put("FEndDate", FEndDate.trim());
		}
		if (StringUtils.isNotEmpty(sale)) {
			conditionMap.put("sale", sale.trim());
		}
		response.setContentType("text/plain");// 一下两行关键的设置
		response.addHeader("Content-Disposition", "attachment;filename=sell"
				+ FStartDate + "-" + FEndDate + ".txt");// filename指定默认的名字
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		String tab = "  ";
		String enter = "\r\n";
		ServletOutputStream outSTr = null;
		CommonDAO dao = getHgDAO();
		try {
			List list = (List) dao.select("HG.exportIcstockbillInfo",
					conditionMap);
			outSTr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outSTr);
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				write.append(data.get("FSupplyIDName") + "  ");
				write.append(data.get("FItemName") + "  ");
				write.append(data.get("FUnitIDName") + "  ");
				write.append(data.get("Fauxqty") + "  ");
				write.append(enter);
			}
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public void exportDeliveryInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String FBillNo = request.getParameter("FBillNo");
		String FStartDate = request.getParameter("FStartDate");
		String FEndDate = request.getParameter("FEndDate");
		String FCustIDName = request.getParameter("FCustIDName");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(FCustIDName)) {
			conditionMap.put("FCustIDName", FCustIDName.trim());
		}
		if (StringUtils.isNotEmpty(FBillNo)) {
			conditionMap.put("FBillNo", FBillNo.trim());
		}
		if (StringUtils.isNotEmpty(FStartDate)) {
			conditionMap.put("FStartDate", FStartDate.trim());
		}
		if (StringUtils.isNotEmpty(FEndDate)) {
			conditionMap.put("FEndDate", FEndDate.trim());
		}
		response.setContentType("text/plain");// 一下两行关键的设置
		response.addHeader("Content-Disposition", "attachment;filename=sell"
				+ FStartDate + "-" + FEndDate + ".txt");// filename指定默认的名字
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		String tab = "  ";
		String enter = "\r\n";
		ServletOutputStream outSTr = null;
		CommonDAO dao = getHgDAO();
		try {
			List list = (List) dao.select("HG.exportDeliveryNoticeInfo",
					conditionMap);
			outSTr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outSTr);
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				write.append(data.get("FCustIDName") + "  ");
				write.append(data.get("FItemIDName") + "  ");
				write.append(data.get("FUnitIDName") + "  ");
				write.append(((java.math.BigDecimal)data.get("Fauxqty")).intValue() + "  ");
				write.append(enter);
			}
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
