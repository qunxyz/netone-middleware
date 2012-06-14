/**
 * 
 */
package com.jl.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.ClassLoaderUtil;
import com.jl.common.MathHelper;
import com.jl.common.TimeUtil;
import com.jl.common.map.GisIfc;
import com.jl.common.map.GisImpl;
import com.jl.common.map.obj.Flow;
import com.jl.common.map.obj.Gis;
import com.jl.common.map.obj.Mark;
import com.jl.common.map.obj.Pic;
import com.jl.common.map.obj.Step;
import com.jl.dao.CommonDAO;
import com.jl.entity.Client;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.MapService;

/**
 * 地图业务实现类
 * 
 * @author clx
 * @date 2011214
 * @history
 */
public class MapServiceImpl extends BaseService implements MapService {
	private final Logger LOG = Logger.getLogger(MapServiceImpl.class);

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void selectXY(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getContextPath();// 取路径
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		JSONObject json = new JSONObject();
		try {
			User user = getOnlineUser(request);
			boolean chinaLimit = false;// 中国地图允许点击
			if ("adminx".equals(user.getLevel())) {
				chinaLimit = true;
			} else if (BussVar.BUSSTYPE_1.equals(user.getLevel())) {// 省公司
				chinaLimit = true;
			}

			URL url = ClassLoaderUtil.newInstance().getResourceAbsoluteURL(
					"issmap.xml");

			GisIfc gisifc = new GisImpl();

			Gis gis = gisifc.load(url.getPath());
			Map flow_ = gis.getFlow();

			String flowId = "1";// 方案
			String _parentstep = request.getParameter("parentstep");
			String areapointId = request.getParameter("areapointId");
			String busspointId = request.getParameter("busspointId");
			String parentPicId = request.getParameter("picId");

			boolean finalPoint = false;

			String _oparentstep = "";// 原始值
			int parentstep = -1;
			if (StringUtils.isNotEmpty(_parentstep)) {
				parentstep = Integer.valueOf(_parentstep);
			} else {
				_oparentstep = _parentstep;
			}

			Flow flow = (Flow) flow_.get(flowId);
			int stepvalue = 0;
			stepvalue = Integer.parseInt(_parentstep);
			stepvalue++;

			boolean init = false;
			if (areapointId.equals("#$#")) {
				init = true;
				areapointId = (String) commonDAO.findForObject(
						"Area.select2levelArea", null);
			}
			if (busspointId.equals("#$#")) {
				init = true;
				busspointId = (String) commonDAO.findForObject(
						"Department.selectRootDepartment", null);// 福建的ID
			}

			List list = null;
			StringBuffer sb = new StringBuffer();// 构造左边HTML
			StringBuffer rightInfo = new StringBuffer();// 构造右边HTML

			LOG.debug("参数:" + flowId + "," + parentstep + "," + areapointId
					+ "," + busspointId);
			list = gisifc.treeData(gis, flowId, parentstep, areapointId,
					busspointId, "");

			Step step = (Step) flow.getStep().get(stepvalue);
			String picId = null;
			if (step == null) {
				int maxstep = flow.getStep().keySet().size();
				step = (Step) flow.getStep().get(maxstep - 1);

			}
			picId = step.getPicid();
			if (picId == null) {// picId不存在意味着使用动态图层，从数据中获得图层ID
				// if (list.size() > 0) {
				// String[] info = (String[]) list.get(0);
				// picId = info[4];
				// } else {

				finalPoint = true;
				// 查找父节点的的地图配置信息
				String map = (String) commonDAO.findForObject(
						"Map.findCompanyXY", busspointId);
				String req = "\\[\\w+\\,";

				Pattern pat = Pattern.compile(req);
				Matcher mat = pat.matcher(map);
				List eachFatherId = new ArrayList();
				while (mat.find()) {
					String findinfo = mat.group();
					findinfo = StringUtils.substringBetween(findinfo, "[", ",");
					eachFatherId.add(findinfo);
				}
				if (eachFatherId.size() > 0) {
					// 选择父节点的最末的图层
					picId = (String) eachFatherId.get(eachFatherId.size() - 1);

				} else {
					picId = "defualt";// 没有数据的情况下，就显示默认的图层
				}

				// }
			}
			Pic pic = flow.getDisplay().getPic().get(picId);
			String picurl = pic.getUrl();

			Float percent = step.getFlexibleRate();// 伸缩比例
			Double bgWidth = pic.getWidth() * percent;// 画布宽
			Double bgHeight = pic.getHeigth() * percent;// 画布高

			if (init) {
				sb
						.append("<div style='border-top-style:Ridge;border-right-style:Ridge;border-left-style:Ridge;border-bottom-style:Ridge;border-width: 7pt;overflow:hidden;position:absolute;border:0px solid #FFCF5F;left:"
								+ 0 + "px;top:" + 0 + "px;' >");
				sb
						.append("<img id=\"_mapx\" src=\""
								+ path
								+ "/images/map/zg.png\" border=\"0\" width=\"600\" height=\"727\" style=\"left:0;top:0;\" border=\"0\" title=\"中国地图\" usemap=\"#Map\" />");

				sb
						.append("<map name=\"Map\" id=\"Map\"><area shape=\"poly\" coords=\"483,426,476,430,467,434,464,438,464,448,458,456,452,466,452,479,453,486,458,489,462,489,467,497,468,508,475,501,479,494,487,486,491,477,496,470,496,458,500,447,500,443,493,437,483,435,484,433,480,429\" ");
				//sb
				//		.append("<map name=\"Map\" id=\"Map\"><area shape=\"poly\" coords=\"241,194,245,197,253,198,260,198,269,199,277,200,285,205,290,210,298,211,303,216,311,219,316,222,319,218,329,211,342,205,355,205,365,202,381,186,386,176,384,165,386,159,395,163,401,163,408,158,409,152,420,148,424,141,431,135,439,132,446,127,459,124,459,118,456,107,445,102,434,100,421,105,418,103,424,98,420,89,424,81,425,81,435,80,444,73,447,62,450,50,451,40,447,31,445,26,451,22,454,21,453,32,456,37,461,35,466,31,469,37,470,45,472,46,478,46,481,44,484,42,490,40,490,40,492,47,493,51,495,63,495,72,495,81,496,87,489,83,487,93,481,102,479,111,483,117,487,117,487,123,487,129,486,131,475,129,475,135,478,143,478,140,479,147,481,154,487,158,489,156,492,155,494,159,493,172,497,163,499,170,500,174,500,177,494,178,485,182,482,189,477,194,471,196,469,197,464,192,461,198,461,208,448,193,436,196,435,203,425,204,420,209,421,212,418,203,410,207,411,212,406,220,410,229,403,234,393,235,388,239,383,247,374,251,365,256,359,266,358,276,355,281,348,278,341,274,338,270,339,262,335,253,331,255,325,266,319,277,311,280,302,280,300,271,306,259,308,253,298,253,293,256,287,255,282,262,274,257,267,247,262,242,269,231,261,227,252,229,249,231,243,215,241,203,240,194\" ");
				if (chinaLimit == true) {
					sb
							.append(" href=\"javascript:void(0)\" onclick=\"onLoadNext(-1,'"
									+ areapointId
									+ "','"
									+ busspointId
									+ "');\" />  ");
				}
				sb.append("</map></div>");
				//
				// // 详细信息
				// rightInfo
				// .append("<table cellspacing='0' cellpadding='0'
				// id=\"maptable\" width=\"100%\">");
				// rightInfo.append(" <tr height='19' bgcolor='#C0C0C0'>");
				// rightInfo
				// .append("<th class='_rhigtTh' colspan=\"4\"><font color='red'
				// style='font-weight: bold;'>"
				// + "烟台张裕葡萄酿酒股份有限公司"
				// + "</font></th></tr><tr height='21' bgcolor='#C0C0C0'><th
				// class='_rhigtTh' colspan=\"4\">地址："
				// + "中国山东烟台市大马路56号" + "</th>");
				// rightInfo
				// .append("</tr><tr height='19' bgcolor='#C0C0C0'> <th
				// class='_rhigtTh' colspan=\"4\">责任人："
				// + ""
				// + " 联系方式:"
				// + "(0535)6216000"
				// + "</th></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='alt'
				// nowrap=\"nowrap\">年资金回收计划:</td><td class='alt1_'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='alt1_' colspan=\"2\" nowrap=\"nowrap\"> 年份：<font
				// color='red'>"
				// + TimeUtil.getYear(new Date())
				// + "</font></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='alt1'
				// nowrap=\"nowrap\">年累计资金回收总额:</td><td class='alt_'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='alt_' nowrap=\"nowrap\"><span
				// class=\"right_\">[完成比例]</td><td class='alt_'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a> <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='alt1'
				// nowrap=\"nowrap\">年累计发出总额:</td><td class='alt_'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='alt_' nowrap=\"nowrap\"><span class=\"right_\"><a
				// href='#' >[完成比例]</a></span></td><td class='alt_'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				// rightInfo
				// .append("<tr height='17'><td height='17' class='alt1'
				// nowrap=\"nowrap\">本月资金回收总额:</td><td class='alt_'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='alt_'><span class=\"right_\"><a href='#'
				// >[完成比例]</a></span></td><td class='alt_'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				// rightInfo
				// .append("<tr height='17'> <td height='17' class='alt1'
				// nowrap=\"nowrap\">本月累计发出总额:</td><td class='alt_'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='alt_'><span class=\"right_\"><a href='#'
				// >[完成比例]</a></span></td><td class='alt_'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='mapkc2'
				// nowrap=\"nowrap\">库存总金额:</td><td class='mapkc2'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='mapkc_1'>&nbsp;</td><td class='mapkc_1'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='mapkc'
				// nowrap=\"nowrap\">滞销产品总额:</td><td class='mapkc1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='mapkc_'>&nbsp;</td><td class='mapkc_'
				// nowrap=\"nowrap\"><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='mapkc'
				// nowrap=\"nowrap\">销售费用:</td><td class='mapkc1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font></td><td class='mapkc_'>&nbsp;</td><td
				// class='mapkc_'><span class=\"right_\"><a href='#'
				// >[详细]</a></span></td></tr>");
				//
				// rightInfo
				// .append("<tr height='17'> <td height='17' class='mapqd'
				// nowrap=\"nowrap\" colspan=\"4\">经销商总数:<font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>家</font></td></tr>");
				//
				// rightInfo
				// .append("<tr height='17'> <td height='17' class='mapqd_'
				// nowrap=\"nowrap\" colspan=\"4\">渠道终端总数:<font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap'>家</font></td></tr>");
				//
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd'
				// nowrap=\"nowrap\" >渠道年计划产出总额:</td><td class='_mapqd'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap' >万元</font></td><td
				// class='_mapqd' colspan=\"2\">&nbsp;</td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >渠道年累计产出总额:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
				// + "0"
				// + "</td><td class='_mapqd1_1'
				// nowrap=\"nowrap\">&nbsp;</td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >渠道本月产出总额:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
				// + "0"
				// + "</td><td class='_mapqd1'
				// nowrap=\"nowrap\">&nbsp;</td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\">各类渠道计划与产出</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + ""
				// + "</font><font class='fontMap' >&nbsp;</font></td><td
				// class='_mapqd1' colspan=\"2\"></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >A类酒店年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >A类商超年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >B类酒店年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >B类商超年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >C类渠道年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo
				// .append("<tr height='19'> <td height='19' class='_mapqd1'
				// nowrap=\"nowrap\" >酒行年计划产出:</td><td class='_mapqd1'
				// nowrap=\"nowrap\" align=\"right\"><font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red'
				// style='font-weight: bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></td><td
				// class='_mapqd1_1' nowrap=\"nowrap\"><span
				// class=\"right_\">累计产出:<font color='red' style='font-weight:
				// bold;'>"
				// + "0"
				// + "</font><font class='fontMap'>万元</font></a>
				// <span></td></tr>");
				// rightInfo.append("</table>");

			} else {
				Client client = (Client) commonDAO.findForObject(
						"Map.findCompanyInfo", busspointId);
				if (client != null) {
					String level = client.getClientTag();
					if ("1".equals(level) || "2".equals(level)
							|| "3".equals(level)) {
						getBuss1_3(client, sb, rightInfo, picId, _oparentstep,
								areapointId, busspointId);
					} else if ("4".equals(level)) {
						getBuss4(client, sb, rightInfo, picId, _oparentstep,
								areapointId, busspointId);
					} else if ("5".equals(level)) {
						getBuss5(client, sb, rightInfo, picId, _oparentstep,
								areapointId, busspointId);
					} else {
						// not do
					}
				}

				// 地图信息 坐标

				sb
						.append("<div id='_direction' style='left:20px;top:10px;z-index:999;position: absolute; '>");
				sb.append("<img id='direction_img' src='" + path
						+ "/images/map/direction.png' width=\"" + 48
						+ "\" height=\"" + 120
						+ "\" usemap=\"#Map_Direction\">");
				sb.append("</img>");
				sb.append("</div>");

				sb.append("<img id=\"_mapx\" v='" + 0 + "," + 0 + "," + bgWidth
						+ "," + bgHeight + "' src='" + path + picurl
						+ "' width=\"" + bgWidth + "\" height=\"" + bgHeight
						+ "\" style='left:0;top:0'>");

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					String[] object = (String[]) iterator.next();
					addpointToMap(sb, object, flow, areapointId, busspointId,
							percent, parentstep);
				}
				// 钻取到末尾，没有下级节点，那么直接显示上一级的节点

				if (list.size() == 0) {
					String name = (String) commonDAO.findForObject(
							"Map.findCompanyName", busspointId);
					String map = (String) commonDAO.findForObject(
							"Map.findCompanyXY", busspointId);
					map = StringUtils.substringAfterLast(map, "[");
					map = StringUtils.substringBefore(map, "]");
					String[] finalPointinfo = map.split(",");

					if (finalPointinfo != null && finalPointinfo.length == 4) {
						String[] obj = { name, busspointId, finalPointinfo[1],
								finalPointinfo[2], finalPointinfo[0],
								finalPointinfo[3], "m1", "3", "" };
						addpointToMap(sb, obj, flow, areapointId, busspointId,
								percent, parentstep);
					}
				}
				sb.append("</img>");
			}

			json.put("data", sb.toString());
			json.put("rightInfo", rightInfo.toString());

		} catch (Exception e) {
			LOG.error("出错了!", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void selectClientXY(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getContextPath();// 取路径
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		JSONObject json = new JSONObject();
		try {
			GisIfc gisifc = new GisImpl();
			String servicePath = this.getClass().getClassLoader().getResource(
					".").getPath()
					+ "WEB-INF/classes/";// 应用服务器目录
			URL url = ClassLoaderUtil.newInstance().getResourceAbsoluteURL(
					"issmap.xml");
			Gis gis = gisifc.load(url.getPath());
			Map flow_ = gis.getFlow();

			String flowId = "1";
			String picid = request.getParameter("picid");
			String busslevel = request.getParameter("busslevel");
			String busspointId = request.getParameter("busspointId");
			String extendbussinfo = request.getParameter("extendbussinfo");

			Flow flow = (Flow) flow_.get(flowId);
			List list = null;
			StringBuffer sb = new StringBuffer();// 构造左边HTML
			StringBuffer rightInfo = new StringBuffer();// 构造右边HTML

			list = gisifc.lineData(picid, busslevel, busspointId,
					extendbussinfo);

			Step step = (Step) flow.getStep().get(0);
			String picId = step.getPicid();
			if (picId == null) {// picId不存在意味着使用动态图层，从数据中获得图层ID
				if (list.size() > 0) {
					String[] info = (String[]) list.get(0);
					picId = info[4];
				} else {
					picId = "defualt";// 没有数据的情况下，就显示默认的图层
				}
			}
			Pic pic = flow.getDisplay().getPic().get(picId);
			String picurl = pic.getUrl();

			Float percent = step.getFlexibleRate();// 伸缩比例
			Double bgWidth = pic.getWidth() * percent;// 画布宽
			Double bgHeight = pic.getHeigth() * percent;// 画布高

			// 地图信息 坐标
			sb.append("<div>");
			sb.append("<img id=\"_mapx\" src='" + path + picurl + "' width=\""
					+ bgWidth + "\" height=\"" + bgHeight
					+ "\" style='left:0;top:0'>");

			String classPic = null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				String[] object = (String[]) iterator.next();
				classPic = ((Mark) flow.getDisplay().getMark().get(object[5]))
						.getUrl();

				String _name = object[0];
				String _id = object[1];
				int _x = Integer.valueOf(object[2].toString());
				int _y = Integer.valueOf(object[3].toString());

				if (object.length == 9) {
					sb
							.append("<div v='"
									+ _x * percent
									+ ","
									+ _y * percent
									+ ","
									+ 0
									+ ","
									+ 0
									+ "' style='border-top-style:Ridge;border-right-style:Ridge;border-left-style:Ridge;border-bottom-style:Ridge;border-width: 7pt;overflow:hidden;position:absolute;border:0px solid #FFCF5F;left:"
									+ _x * percent + "px;top:" + _y * percent
									+ "px;' class='" + classPic + "' "
									+ "title='(x:" + _x * percent + ",y:"
									+ _y * percent + "&nbsp;&nbsp;" + _name
									+ ")' id='map_" + _id
									+ "' onmouseover='_classOver(this,\""
									+ classPic
									+ "\")' onmouseout='_classOut(this,\""
									+ classPic + "\")'>");
					sb.append("</div>");
					sb
							.append("<div class='_mapxNav' v='"
									+ (_x + 18)
									* percent
									+ ","
									+ (_y * percent)
									+ ","
									+ 0
									+ ","
									+ 0
									+ "' style='position:absolute;left:"
									+ (_x + 18)
									* percent
									+ "px;top:"
									+ (_y * percent)
									+ "px;' title='"
									+ _name
									+ "' onmouseover='_focus(this)' onmouseout='_focusOut(this)'>");
					sb.append("<a href='#' class='map'>" + object[0] + "</a>");
					sb.append("</div>");
				}
			}
			sb.append("</img>");
			sb.append("</div>");

			json.put("data", sb.toString());
			json.put("rightInfo", rightInfo.toString());
		} catch (Exception e) {
			LOG.error("出错了!", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	private void addpointToMap(StringBuffer sb, String[] object, Flow flow,
			String areapointId, String busspointId, double percent,
			int parentstep) {

		Map markMap = flow.getDisplay().getMark();
		Mark mark = (Mark) markMap.get(object[5]);

		if (mark == null) {
			mark = (Mark) markMap.get("default");
		}

		String classPic = mark.getUrl();

		int _busslevel = 0;
		String _name = object[0];
		String _id = object[1];
		int _x = Integer.valueOf(object[2].toString());
		int _y = Integer.valueOf(object[3].toString());
		String areapointIdStr = areapointId;
		String busspointIdStr = busspointId;
		String picid = object[4];
		if (object.length == 8) {
			areapointIdStr = object[1];
			busspointIdStr = "0";
			_busslevel = Integer.valueOf(object[6].toString());
		} else if (object.length == 9) {
			areapointIdStr = object[6];
			busspointIdStr = object[1];
			_busslevel = Integer.valueOf(object[7].toString());
		}
		if (object.length == 9) {// 只展示公司
			sb
					.append("<div v='"
							+ _x * percent
							+ ","
							+ _y * percent
							+ ","
							+ 0
							+ ","
							+ 0
							+ "' style='border-top-style:Ridge;border-right-style:Ridge;border-left-style:Ridge;border-bottom-style:Ridge;border-width: 7pt;overflow:hidden;position:absolute;border:0px solid #FFCF5F;left:"
							+ _x * percent + "px;top:" + _y * percent
							+ "px;' class='" + classPic + "' " + "title='(x:"
							+ _x * percent + ",y:" + _y * percent
							+ "&nbsp;&nbsp;" + _name + ")' id='map_" + _id
							+ "'  onmouseover='_classOver(this,\"" + classPic
							+ "\")' onmouseout='_classOut(this,\"" + classPic
							+ "\")" + "'");
			if (_busslevel < 6) {
				sb.append(" onclick='onLoadNext(" + (parentstep + 1) + ","
						+ "\"" + areapointIdStr + "\"" + "," + "\""
						+ busspointIdStr + "\"" + "," + "\"" + picid + "\""
						+ ");' ");

			}
			sb.append("></div>");
			sb
					.append("<div class='_mapxNav' v='"
							+ (_x + 18)
							* percent
							+ ","
							+ (_y * percent)
							+ ","
							+ 0
							+ ","
							+ 0
							+ "' style='position:absolute; left:"
							+ (_x + 18)
							* percent
							+ "px;top:"
							+ (_y * percent)
							+ "px;' title='"
							+ _name
							+ "' onmouseover='_focus(this)' onmouseout='_focusOut(this)'>");

			sb.append("<a href='javascript:void(0)' class='map' ");
			if (_busslevel < 6) {// 除渠道外
				sb.append("onclick='onLoadNext(" + (parentstep + 1) + ","
						+ "\"" + areapointIdStr + "\"" + "," + "\""
						+ busspointIdStr + "\"" + "," + "\"" + picid + "\""
						+ ");  ");
			}
			sb.append("'>" + _name + "</a>");
			sb.append("</div>");
		}
	}

	/**
	 * 
	 * @param type
	 * @param areapointId
	 * @param busspointId
	 * @param busslevel
	 * @param picid
	 * @return
	 * @throws Exception
	 */
	private String getInfo(String type, String areapointId, String busspointId,
			String busslevel, String picid) throws Exception {
		String value = "";
		Map map = new HashMap();
		map.put("areapointId",
				(areapointId == null || "".equals(areapointId)) ? "0"
						: areapointId);
		map.put("busspointId",
				(busspointId == null || "".equals(busspointId)) ? "0"
						: busspointId);
		if ("selectBadPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject("Map.selectBadPaymentSum",
					map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectYearSelltargetSum".equals(type)) {
			String level = (String) commonDAO.findForObject(
					"Department.checkBussType", busspointId);
			map.put("level", level == null ? "" : level);
			value = (String) commonDAO.findForObject(
					"Map.selectYearSelltargetSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectMonthSelltargetSum".equals(type)) {
			String level = (String) commonDAO.findForObject(
					"Department.checkBussType", busspointId);
			map.put("level", level == null ? "" : level);
			value = (String) commonDAO.findForObject(
					"Map.selectMonthSelltargetSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectYearBackPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectYearBackPaymentSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectCurrMonthBackPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectCurrMonthBackPaymentSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectYearIndentPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectYearIndentPaymentSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectCurrMonthIndentPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectCurrMonthIndentPaymentSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectCurrStorageBalanceSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectCurrStorageBalanceSum", map);
			value = MathHelper.moneyFormat(Double.valueOf(value));
		} else if ("selectClientCount".equals(type)) {
			value = (String) commonDAO.findForObject("Map.selectClientCount",
					map);
		} else if ("selectChildClientCount".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectChildClientCount", map);
		} else if ("selectYearChildClientIndentPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectYearChildClientIndentPaymentSum", map);
		} else if ("selectYearChildSelltargetSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectYearChildSelltargetSum", map);
		} else if ("selectMonthChildClientIndentPaymentSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectMonthChildClientIndentPaymentSum", map);
		} else if ("selectMonthChildSelltargetSum".equals(type)) {
			value = (String) commonDAO.findForObject(
					"Map.selectMonthChildSelltargetSum", map);
		} else if ("selectChildClientTypeCountX".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectChildClientTypeCountX", map);
			StringBuffer sb = new StringBuffer("");
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("sellTargetValue").toString();
				String object3 = map2.get("sid").toString();

				String object5 = map2.get("yearIndentSum").toString();
				String object6 = map2.get("monthIndentSum").toString();

				tmp
						.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\" >"
								+ object
								+ "年计划产出:</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
								+ object2
								+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1' nowrap=\"nowrap\">本月产出:<font color='red' style='font-weight: bold;'>"
								+ object5
								+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1'  nowrap=\"nowrap\"><span class=\"right_\">累计产出:<font color='red' style='font-weight: bold;'>"
								+ object6
								+ "</font><font class='fontMap'>万元</font></a> <span></td></tr>");
			}
			value = tmp.toString();
		}
		return value;

	}

	/**
	 * 
	 * @param type
	 * @param areapointId
	 * @param busspointId
	 * @param busslevel
	 * @param picid
	 * @return
	 * @throws Exception
	 */
	private Object[] getInfo2(String type, String areapointId,
			String busspointId, String busslevel, String picid)
			throws Exception {
		Map map = new HashMap();
		map.put("areapointId",
				(areapointId == null || "".equals(areapointId)) ? "0"
						: areapointId);
		map.put("busspointId",
				(busspointId == null || "".equals(busspointId)) ? "0"
						: busspointId);
		Object[] v = new Object[2];
		if ("selectClientTypeCount1".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectClientTypeCount1", map);
			StringBuffer sb = new StringBuffer("");
			int x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("count").toString();
				String object3 = map2.get("sid").toString();
				x += Integer.parseInt(map2.get("count").toString());
				tmp
						.append("<td class='mapqd1' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + object2
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >各类渠道经销商数:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >&nbsp;</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		} else if ("selectClientTypeCount2".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectClientTypeCount2", map);
			StringBuffer sb = new StringBuffer("");
			int x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("count").toString();
				String object3 = map2.get("sid").toString();
				x += Integer.parseInt(map2.get("count").toString());
				tmp
						.append("<td class='mapqd1' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + 0
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >各类渠道经销商数:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >&nbsp;(郊&nbsp;&nbsp;&nbsp;&nbsp;县)</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		} else if ("selectChildClientTypeCount1".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectChildClientTypeCount1", map);
			StringBuffer sb = new StringBuffer("");
			int x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("count").toString();
				String object3 = map2.get("sid").toString();
				x += Integer.parseInt(map2.get("count").toString());
				tmp
						.append("<td class='mapqd1_' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + object2
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >各类渠道终端数:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		} else if ("selectChildClientTypeCount2".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectChildClientTypeCount2", map);
			StringBuffer sb = new StringBuffer("");
			int x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("count").toString();
				String object3 = map2.get("sid").toString();
				x += Integer.parseInt(map2.get("count").toString());
				tmp
						.append("<td class='mapqd1_' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + 0
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >各类渠道终端数:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;(郊&nbsp;&nbsp;&nbsp;&nbsp;县)</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		} else if ("selectChildClientTypeSellTargetCount1".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectChildClientTypeSellTargetCount1", map);
			StringBuffer sb = new StringBuffer("");
			double x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("sellTarget").toString();
				String object3 = map2.get("sid").toString();
				x += Double.parseDouble(object2.toString());
				tmp
						.append("<td class='mapqd1_' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + object2
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >各类渠道计划产出额:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;(地级城市)</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		} else if ("selectChildClientTypeSellTargetCount2".equals(type)) {
			Collection<Map> values = commonDAO.select(
					"Map.selectChildClientTypeSellTargetCount2", map);
			StringBuffer sb = new StringBuffer("");
			double x = 0;
			Map[] list = values.toArray(new HashMap[values.size()]);
			StringBuffer tmp = new StringBuffer("");
			List<String> tmpList = new ArrayList<String>();
			for (int i = 0; i < list.length; i++) {
				Map map2 = list[i];
				String object = map2.get("clientType").toString();
				Object object2 = map2.get("sellTarget").toString();
				String object3 = map2.get("sid").toString();
				x += Double.parseDouble(object2.toString());
				tmp
						.append("<td class='mapqd1_' nowrap=\"nowrap\">"
								+ "<span class=\"right_\"><a href='javascript:void(0)' onclick=\"_loadLineData('"
								+ picid + "','" + busslevel + "','"
								+ busspointId + "','" + object3 + "')\" >"
								+ object + "<font color='red'>" + 0
								+ "</font></a></span></td>");
				if ((i + 1) % 3 == 0) {
					tmpList.add("<tr>");
					if (i == 2) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >各类渠道计划产出额:</td>"
										+ tmp.toString() + "</tr>");
					} else {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;(郊&nbsp;&nbsp;&nbsp;&nbsp;县)</td>"
										+ tmp.toString() + "</tr>");
					}
					tmp = new StringBuffer("");
				} else if ((list.length - i - 1) < (list.length % 3)) {
					tmpList
							.add("<tr><td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
									+ tmp.toString() + "");
					for (int j = 0; j < (3 - (list.length % 3)); j++) {
						tmpList
								.add("<td class='mapqd1_' nowrap=\"nowrap\" >&nbsp;</td>"
										+ "");
					}
					tmpList.add("</tr>");
					tmp = new StringBuffer("");
				}
			}
			v[0] = "" + x;
			v[1] = tmpList;
		}
		return v;

	}

	private String getPercent(String v1, String v2) {
		double v1$ = Double.valueOf(v1);
		double v2$ = Double.valueOf(v2);
		if (v2$ == 0) {
			return "<font color='red'><B>" + "0.00%" + "</B></font>";
		} else {
			return "<font color='red'><B>"
					+ MathHelper.round2(v1$ / v2$ * 100, 2) + "%"
					+ "</B></font>";
		}
	}

	@Deprecated
	private String getLineDataNav(String path) throws Exception {
		StringBuffer sb = new StringBuffer("");
		sb
				.append("<a href=\"javascript:void(0)\" onclick=\"_loadLineData(2)\">[显示大区营销部]</a><BR>");
		sb
				.append("<a href=\"javascript:void(0)\" onclick=\"_loadLineData(3)\">[显示小营销部]</a><BR>");
		sb
				.append("<a href=\"javascript:void(0)\" onclick=\"_loadLineData(4)\">[显示经销商]</a><BR>");
		sb
				.append("<a href=\"javascript:void(0)\" onclick=\"_loadLineData(5)\">[显示分销商]</a><BR>");
		sb
				.append("<select onchange=\"_loadTypeData()\"><option value=\"\"></option>");
		Map map = new HashMap();
		map.put("type", "marketType");
		Collection<Map> coll = commonDAO.select(
				"SystemConfig.loadSystemConfigToSelect", map);
		for (Map map2 : coll) {
			Object name = map2.get("name");
			Object sid = map2.get("sid");
			sb.append("<option value=\"" + sid + "\">" + name + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 构造省公司级别
	 * 
	 * @param client
	 * @param sb
	 * @param rightInfo
	 * @param picId
	 * @param _oparentstep
	 * @param areapointId
	 * @param busspointId
	 * @throws Exception
	 */
	private void getBuss1_3(Client client, StringBuffer sb,
			StringBuffer rightInfo, String picId, String _oparentstep,
			String areapointId, String busspointId) throws Exception {

		Object[] v1 = getInfo2("selectClientTypeCount1", areapointId,
				busspointId, _oparentstep, picId);
		Object[] v2 = getInfo2("selectClientTypeCount2", areapointId,
				busspointId, _oparentstep, picId);

		Object[] v3 = getInfo2("selectChildClientTypeCount1", areapointId,
				busspointId, _oparentstep, picId);
		Object[] v4 = getInfo2("selectChildClientTypeCount2", areapointId,
				busspointId, _oparentstep, picId);

		// 详细信息
		rightInfo
				.append("<table cellspacing='0' cellpadding='0'  id=\"maptable\" width=\"100%\">");
		rightInfo.append("  <tr height='19'>");
		rightInfo
				.append("<th  class='_rhigtTh' colspan=\"4\"><font color='red' style='font-weight: bold;'>"
						+ client.getClientName()
						+ "</font></th></tr><th class='_rhigtTh' colspan=\"4\">地址："
						+ client.getCompanyAddress() + "</th>");
		rightInfo
				.append("</tr><tr height='19'>  <th  class='_rhigtTh' colspan=\"4\">责任人："
						+ client.getLinkman()
						+ " 联系方式:"
						+ client.getTelphone()
						+ "</th></tr>");

		String vs0 = getInfo("selectYearSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs0_month = getInfo("selectMonthSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs1 = getInfo("selectYearBackPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs2 = getInfo("selectYearIndentPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs3 = getInfo("selectCurrMonthBackPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs4 = getInfo("selectCurrMonthIndentPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt' nowrap=\"nowrap\">年资金回收计划:</td><td class='alt1_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs0
						+ "</font><font class='fontMap'>万元</font></td><td class='alt1_' colspan=\"2\" nowrap=\"nowrap\"> 年&nbsp;&nbsp;&nbsp;&nbsp;份:<font color='red'>"
						+ TimeUtil.getYear(new Date()) + "</font></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt1' nowrap=\"nowrap\">年累计资金回收总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1, vs0)
						+ "</td><td class='alt_'  nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"1\")' >[详细]</a> <span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt1' nowrap=\"nowrap\">年累计发出总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs2
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs2, vs0)
						+ "</td><td class='alt_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"3\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='17'><td height='17' class='alt1' nowrap=\"nowrap\">本月资金回收总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs3
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs3, vs0_month)
						+ "</td><td class='alt_'  nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"6\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='17'>  <td height='17' class='alt1' nowrap=\"nowrap\">本月累计发出总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs4
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs4, vs0_month)
						+ "</td><td class='alt_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"7\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc2' nowrap=\"nowrap\">库存总金额:</td><td class='mapkc2' nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ getInfo("selectCurrStorageBalanceSum", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>万元</font></td><td class='mapkc_1'>&nbsp;</td><td class='mapkc_1' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"9\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc' nowrap=\"nowrap\">滞销产品总额:</td><td class='mapkc1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ getInfo("selectBadPaymentSum", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>万元</font></td><td class='mapkc_'>&nbsp;</td><td class='mapkc_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"11\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc' nowrap=\"nowrap\">销售费用:</td><td class='mapkc1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ ""
						+ "</font></td><td class='mapkc_'>&nbsp;</td><td class='mapkc_'><span class=\"right_\"><a href='#' onClick='openQuery(\"12\")'>[详细]</a></span></td></tr>");

		rightInfo
				.append("<tr height='17'>  <td height='17' class='mapqd' nowrap=\"nowrap\" colspan=\"4\">经销商总数:<font color='red' style='font-weight: bold;'>"
						+ getInfo("selectClientCount", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>家</font></td></tr>");

		List v1list = (List) v1[1];
		List v2list = (List) v2[1];

		rightInfo.append("<tr>");
		for (int i = 0; i < v1list.size(); i++) {
			rightInfo.append(v1list.get(i));
		}
		rightInfo.append("</tr>");
		// rightInfo.append("<tr>");
		// for (int i = 0; i < v2list.size(); i++) {
		// rightInfo.append(v2list.get(i));
		// }
		// rightInfo.append("</tr>");

		rightInfo
				.append("<tr height='17'>  <td height='17' class='mapqd_' nowrap=\"nowrap\" colspan=\"4\">渠道终端总数:<font color='red' style='font-weight: bold;'>"
						+ getInfo("selectChildClientCount", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>家</font></td></tr>");

		//
		List v3list = (List) v3[1];
		List v4list = (List) v4[1];
		rightInfo.append("<tr>");
		for (int i = 0; i < v3list.size(); i++) {
			rightInfo.append(v3list.get(i));
		}
		rightInfo.append("</tr>");
		// rightInfo.append("<tr>");
		// for (int i = 0; i < v4list.size(); i++) {
		// rightInfo.append(v4list.get(i));
		// }
		// rightInfo.append("</tr>");

		// Object[] v1_1 = getInfo2("selectChildClientTypeSellTargetCount1",
		// areapointId, busspointId, _oparentstep, picId);
		// Object[] v2_2 = getInfo2("selectChildClientTypeSellTargetCount2",
		// areapointId, busspointId, _oparentstep, picId);

		String vs1_1 = getInfo("selectYearChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String vs1_2 = getInfo("selectYearChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		String vs1_3 = getInfo("selectMonthChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String vs1_4 = getInfo("selectMonthChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd' nowrap=\"nowrap\" >渠道年计划产出总额:</td><td class='_mapqd'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_1
						+ "</font><font class='fontMap' >万元</font></td><td class='_mapqd' colspan=\"2\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\" >渠道年累计产出总额:</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_2
						+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1_2, vs1_1)
						+ "</td><td class='_mapqd1_1'  nowrap=\"nowrap\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\" >渠道本月产出总额:</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_4
						+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1_4, vs1_3)
						+ "</td><td class='_mapqd1'  nowrap=\"nowrap\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\">各类渠道计划与产出</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ ""
						+ "</font><font class='fontMap' >&nbsp;</font></td><td class='_mapqd1' colspan=\"2\"></td></tr>");

		rightInfo.append((getInfo("selectChildClientTypeCountX", areapointId,
				busspointId, _oparentstep, picId)));

		rightInfo.append("</table>");
	}

	/**
	 * 构造省公司级别
	 * 
	 * @param client
	 * @param sb
	 * @param rightInfo
	 * @param picId
	 * @param _oparentstep
	 * @param areapointId
	 * @param busspointId
	 * @throws Exception
	 */
	private void getBuss4(Client client, StringBuffer sb,
			StringBuffer rightInfo, String picId, String _oparentstep,
			String areapointId, String busspointId) throws Exception {

		// Object[] v1 = getInfo2("selectClientTypeCount1", areapointId,
		// busspointId, _oparentstep, picId);
		// Object[] v2 = getInfo2("selectClientTypeCount2", areapointId,
		// busspointId, _oparentstep, picId);

		Object[] v3 = getInfo2("selectChildClientTypeCount1", areapointId,
				busspointId, _oparentstep, picId);
		Object[] v4 = getInfo2("selectChildClientTypeCount2", areapointId,
				busspointId, _oparentstep, picId);

		// 详细信息
		rightInfo
				.append("<table cellspacing='0' cellpadding='0'  id=\"maptable\" width=\"100%\">");
		rightInfo.append("  <tr height='19'>");
		rightInfo
				.append("<th  class='_rhigtTh' colspan=\"4\"><font color='red' style='font-weight: bold;'>"
						+ client.getClientName()
						+ "</font></th></tr><th class='_rhigtTh' colspan=\"4\">地址："
						+ client.getCompanyAddress() + "</th>");
		rightInfo
				.append("</tr><tr height='19'>  <th  class='_rhigtTh' colspan=\"4\">责任人："
						+ client.getLinkman()
						+ " 联系方式:"
						+ client.getTelphone()
						+ "</th></tr>");

		String vs0 = getInfo("selectYearSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs0_month = getInfo("selectMonthSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs1 = getInfo("selectYearBackPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs2 = getInfo("selectYearIndentPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs3 = getInfo("selectCurrMonthBackPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		String vs4 = getInfo("selectCurrMonthIndentPaymentSum", areapointId,
				busspointId, _oparentstep, picId);
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt' nowrap=\"nowrap\">年资金回收计划:</td><td class='alt1_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs0
						+ "</font><font class='fontMap'>万元</font></td><td class='alt1_' colspan=\"2\" nowrap=\"nowrap\"> 年&nbsp;&nbsp;&nbsp;&nbsp;份:<font color='red'>"
						+ TimeUtil.getYear(new Date()) + "</font></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt1' nowrap=\"nowrap\">年累计资金回收总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1, vs0)
						+ "</td><td class='alt_'  nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"1\")' >[详细]</a> <span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt1' nowrap=\"nowrap\">年累计发出总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs2
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs2, vs0)
						+ "</td><td class='alt_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"3\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='17'><td height='17' class='alt1' nowrap=\"nowrap\">本月资金回收总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs3
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs3, vs0_month)
						+ "</td><td class='alt_'  nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"6\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='17'>  <td height='17' class='alt1' nowrap=\"nowrap\">本月累计发出总额:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs4
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs4, vs0_month)
						+ "</td><td class='alt_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"7\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc2' nowrap=\"nowrap\">库存总金额:</td><td class='mapkc2' nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ getInfo("selectCurrStorageBalanceSum", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>万元</font></td><td class='mapkc_1'>&nbsp;</td><td class='mapkc_1' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"9\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc' nowrap=\"nowrap\">滞销产品总额:</td><td class='mapkc1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ getInfo("selectBadPaymentSum", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>万元</font></td><td class='mapkc_'>&nbsp;</td><td class='mapkc_' nowrap=\"nowrap\"><span class=\"right_\"><a href='#' onClick='openQuery(\"11\")'>[详细]</a></span></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='mapkc' nowrap=\"nowrap\">销售费用:</td><td class='mapkc1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ ""
						+ "</font></td><td class='mapkc_'>&nbsp;</td><td class='mapkc_'><span class=\"right_\"><a href='#' onClick='openQuery(\"12\")'>[详细]</a></span></td></tr>");

		rightInfo
				.append("<tr height='17'>  <td height='17' class='mapqd_' nowrap=\"nowrap\" colspan=\"4\">渠道终端总数:<font color='red' style='font-weight: bold;'>"
						+ getInfo("selectChildClientCount", areapointId,
								busspointId, _oparentstep, picId)
						+ "</font><font class='fontMap'>家</font></td></tr>");

		//
		List v3list = (List) v3[1];
		List v4list = (List) v4[1];
		rightInfo.append("<tr>");
		for (int i = 0; i < v3list.size(); i++) {
			rightInfo.append(v3list.get(i));
		}
		rightInfo.append("</tr>");
		// rightInfo.append("<tr>");
		// for (int i = 0; i < v4list.size(); i++) {
		// rightInfo.append(v4list.get(i));
		// }
		// rightInfo.append("</tr>");

		// Object[] v1_1 = getInfo2("selectChildClientTypeSellTargetCount1",
		// areapointId, busspointId, _oparentstep, picId);
		// Object[] v2_2 = getInfo2("selectChildClientTypeSellTargetCount2",
		// areapointId, busspointId, _oparentstep, picId);

		String vs1_1 = getInfo("selectYearChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String vs1_2 = getInfo("selectYearChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		String vs1_3 = getInfo("selectMonthChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String vs1_4 = getInfo("selectMonthChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd' nowrap=\"nowrap\" >渠道年计划产出总额:</td><td class='_mapqd'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_1
						+ "</font><font class='fontMap' >万元</font></td><td class='_mapqd' colspan=\"2\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\" >渠道年累计产出总额:</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_2
						+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1_2, vs1_1)
						+ "</td><td class='_mapqd1_1'  nowrap=\"nowrap\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\" >渠道本月产出总额:</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ vs1_4
						+ "</font><font class='fontMap'>万元</font></td><td class='_mapqd1_1' nowrap=\"nowrap\">完成比例:"
						+ getPercent(vs1_4, vs1_3)
						+ "</td><td class='_mapqd1'  nowrap=\"nowrap\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='_mapqd1' nowrap=\"nowrap\">各类渠道计划与产出</td><td class='_mapqd1'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ ""
						+ "</font><font class='fontMap' >&nbsp;</font></td><td class='_mapqd1' colspan=\"2\"></td></tr>");

		rightInfo.append((getInfo("selectChildClientTypeCountX", areapointId,
				busspointId, _oparentstep, picId)));
		rightInfo.append("</table>");
	}

	/**
	 * 构造省公司级别
	 * 
	 * @param client
	 * @param sb
	 * @param rightInfo
	 * @param picId
	 * @param _oparentstep
	 * @param areapointId
	 * @param busspointId
	 * @throws Exception
	 */
	private void getBuss5(Client client, StringBuffer sb,
			StringBuffer rightInfo, String picId, String _oparentstep,
			String areapointId, String busspointId) throws Exception {

		String v1 = getInfo("selectYearChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String v2 = getInfo("selectYearChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		String v3 = getInfo("selectMonthChildSelltargetSum", areapointId,
				busspointId, _oparentstep, picId);

		String v4 = getInfo("selectMonthChildClientIndentPaymentSum",
				areapointId, busspointId, _oparentstep, picId);

		// 详细信息
		rightInfo
				.append("<table cellspacing='0' cellpadding='0'  id=\"maptable\" width=\"100%\" >");
		rightInfo.append("  <tr height='19'>");
		rightInfo
				.append("<th  class='_rhigtTh' colspan=\"4\"><font color='red' style='font-weight: bold;'>"
						+ client.getClientName()
						+ "</font></th></tr><th class='_rhigtTh' colspan=\"4\">地址："
						+ client.getCompanyAddress() + "</th>");
		rightInfo
				.append("</tr><tr height='19'>  <th  class='_rhigtTh' colspan=\"4\">责任人："
						+ client.getLinkman()
						+ " 联系方式:"
						+ client.getTelphone()
						+ "</th></tr>");

		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt' nowrap=\"nowrap\">年计划产出:</td><td class='alt1_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ v1
						+ "</font><font class='fontMap'>万元</font></td><td class='alt1_' colspan=\"2\" nowrap=\"nowrap\"> 年&nbsp;&nbsp;&nbsp;&nbsp;份:<font color='red'>"
						+ TimeUtil.getYear(new Date()) + "</font></td></tr>");
		rightInfo
				.append("<tr height='19'>  <td height='19' class='alt1' nowrap=\"nowrap\">年累计产出:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ v2
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:"
						+ "</td><td class='alt_'  nowrap=\"nowrap\">"
						+ getPercent(v2, v1) + "</td></tr>");
		rightInfo
				.append("<tr height='17'><td height='17' class='alt1' nowrap=\"nowrap\">本月计划产出:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ v3
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">&nbsp;</td><td class='alt_'  nowrap=\"nowrap\">&nbsp;</td></tr>");
		rightInfo
				.append("<tr height='17'>  <td height='17' class='alt1' nowrap=\"nowrap\">本月累计产出:</td><td class='alt_'  nowrap=\"nowrap\" align=\"right\"><font color='red' style='font-weight: bold;'>"
						+ v4
						+ "</font><font class='fontMap'>万元</font></td><td class='alt_' nowrap=\"nowrap\">完成比例:</td><td class='alt_' nowrap=\"nowrap\">"
						+ getPercent(v4, v3) + "</td></tr>");

		rightInfo.append("</table>");
	}

	public void loadAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

	public int modifyCoordinates(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int modifyCoordinates1(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
