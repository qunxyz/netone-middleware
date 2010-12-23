package oe.bi.etl.wizard.analysesubject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;


import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.datasource.NodeToDimensionElement;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;
import oe.bi.wizard.WizardDao;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;

/**
 * 流程页面
 * 
 * @author chen.jia.xun
 * 
 */
public class FlowpageSvl extends HttpServlet {

	private Map getTgGroupValue(String tgvalue) {
		Map map = new HashMap();
		String[] tgs = tgvalue.split(",");
		for (int i = tgs.length - 1; i >= 0; i--) {
			String tgStr = tgs[i];
			if (tgs[i].length() > 0) {
				String value = tgStr.split(":")[0];
				String tgGroup = tgStr.split(":")[1];

				if (map.containsKey(tgGroup)) {
					value = value + "," + map.get(tgGroup).toString();
				}
				map.put(tgGroup, value);
			}
		}
		return map;
	}

	private List parseTgValue(String tgvalue, String[] tgname) {
		ArrayList list = new ArrayList();
		String[] tgs = tgvalue.split(",");
		for (int i = 0; i < tgs.length; i++) {
			if (tgs[i].length() > 0) {
				String value = tgs[i];
				value = StringUtils.replace(value, "$nofilter$", "");
				// value=value.replaceAll("$nofilter$","");
				TargetElement targetElement = new TargetElement();
				targetElement.setId(StringUtils.substringBefore(value, "{"));
				targetElement.setNativeid(StringUtils.substringBetween(value, "{", "}"));
				targetElement.setName(StringUtils.substringBefore(tgname[i], "{"));
				targetElement.setType(StringUtils.substringBetween(tgname[i], "{", "}"));
				list.add(targetElement);
			}
		}
		return list;
	}

	private List paserDimValue(String dimvalue, String[] tgfiltSqlvalue, String tgfiltvalue, String exterValue,
			List levelinfo, Map nodex) {
		HashMap dimmap = new HashMap();
		if (null != dimvalue) {
			String[] items = dimvalue.split("#");
			for (int i = 0; i < items.length; i++) {
				RequestParamMap map = new RequestParamMap();
				map.loadStringValue(items[i]);
				String treeModelId = map.getParameter("treeModelId");
				// 获得dimtreemodelid信息,通常情况下第一个是dimtreemodelid 其他的是timetree
				if (!"timetree".equals(treeModelId)) {
					NodeObj nodeobj = new NodeObj();
					nodeobj.setTreeModelId(treeModelId);
					nodeobj.setNodeid(map.getParameter("nodeid"));
					nodeobj.setLevelname(map.getParameter("levelnameOld"));
					nodeobj.setColumnname(map.getParameter("levelname"));
					nodex.put(map.getParameter("nodeid"), nodeobj);
				}
				List nodelist = null;
				Object obj = dimmap.get(treeModelId);
				if (obj != null) {
					nodelist = (List) obj;
				} else {
					nodelist = new ArrayList();
					dimmap.put(treeModelId, nodelist);
				}
				NodeObj node = (NodeObj) RequestUtil.mappingReqParam(NodeObj.class, map);
				// 原始类型在后面会被改掉

				String selectAllFlag = map.getParameter("selectAllFlag");

				if (selectAllFlag != null && !selectAllFlag.equals("")) {
					// node.setFathernodeid(selectAllFlag);
					String levelnameOld = map.getParameter("levelnameOld");
					// DimensionLoader dimloader = (DimensionLoader)
					// BiEntry.fetchBi("dimensionLoader");
					// List allLevelList=dimloader.getAllDimension(treeModelId,
					// node, levelnameOld);
					// for(int k=0;k<allLevelList.size();k++){
					// NodeObj no =(NodeObj)allLevelList.get(k);
					// nodelist.add(no);
					// dimmap.put(treeModelId, nodelist);
					// }
					// 表示为选择全部的节点
					levelinfo.add(node.getLevelname() + "_ALL");
					node.setColumnname(selectAllFlag.toLowerCase());
					node.setLevelname(selectAllFlag.toLowerCase());

				} else {

					levelinfo.add(node.getLevelname());
					if ("timetree".equals(treeModelId))
						node.setColumnname("start_time");
					else
						node.setColumnname("sys_int_id");

				}
				nodelist.add(node);
			}
		}

		ArrayList dimlist = new ArrayList();
		Iterator iter = dimmap.keySet().iterator();
		while (iter.hasNext()) {
			List nodelist = (List) dimmap.get(iter.next());
			dimlist.add(NodeToDimensionElement.nodeToDim(nodelist));
		}

		if (exterValue != null && !exterValue.trim().equals("")) {
			DimensionElement dime = new DimensionElement();
			dime.setLevelcolumnid("@");
			dime.setId(exterValue);
			dimlist.add(dime);
		}
		// 2008-3-3 nihq start
		// ---------------------------------------------------
		// 修改:生成DimensionElement方法
		if (StringUtils.isNotEmpty(tgfiltSqlvalue[0])) {
			String[] chkid = StringUtils.split(tgfiltSqlvalue[0], ",");
			String[] alarm = StringUtils.split(tgfiltSqlvalue[3], ",");
			String[] txtalarm = StringUtils.split(tgfiltSqlvalue[4], ",");
			if (chkid != null && chkid.length > 0) {
				for (int i = 0; i < chkid.length; i++) {
					DimensionElement dime = new DimensionElement();
					dime.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid[i], "{", "}");
					dime.setChoicenodename(choicenodename);
					dime.setId(StringUtils.substringBefore(chkid[i], "{"));
					// 门限
					if (StringUtils.isNotEmpty(tgfiltSqlvalue[3])) {
						dime.setName("limit");
						if ("like".equals(alarm[i])) {
							choicenode[0] = "like " + txtalarm[i];
						} else {
							choicenode[0] = alarm[i] + txtalarm[i];
						}
						dime.setOrder(false);
						dime.setDesc(false);
					}
					dime.setChoicenode(choicenode);
					dimlist.add(dime);
				}
			}
		}
		if (StringUtils.isNotEmpty(tgfiltSqlvalue[6])) {
			String[] top = StringUtils.split(tgfiltSqlvalue[1], ",");
			String[] topvalue = StringUtils.split(tgfiltSqlvalue[2], ",");
			String[] order = StringUtils.split(tgfiltSqlvalue[5], ",");
			String[] chkid2 = StringUtils.split(tgfiltSqlvalue[6], ",");
			if (chkid2 != null && chkid2.length > 0) {
				for (int i = 0; i < chkid2.length; i++) {
					DimensionElement dime = new DimensionElement();
					dime.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid2[i], "{", "}");
					dime.setChoicenodename(choicenodename);
					dime.setId(StringUtils.substringBefore(chkid2[i], "{"));
					// 最值
					if (StringUtils.isNotEmpty(tgfiltvalue)) {
						choicenode[0] = tgfiltvalue;
						// 排序
					} else {
						choicenode[0] = "";
					}
					dime.setName("order");
					dime.setOrder(true);
					if ("asc".equals(order[i])) {
						dime.setDesc(false);
					} else if ("desc".equals(order[i])) {
						dime.setDesc(true);
					}
					dime.setChoicenode(choicenode);
					dimlist.add(dime);
				}
			}
		}
		// 2008-3-3 nihq end
		// ---------------------------------------------------
		/***********************************************************************
		 * String[] dims = dimvalue.split(";"); for (int i = 0; i < dims.length;
		 * i++) {
		 * 
		 * if (dims[i].length() > 0) { String value = dims[i]; DimensionElement
		 * dimEle = new DimensionElement();
		 * dimEle.setDimcolumnid(getThValue(value, 1)); String[] choicenode =
		 * getThValue(value, 2).split(","); dimEle.setChoicenode(choicenode);
		 * dimEle.setLevelcolumnid(getThValue(value,3));
		 * dimEle.setChoicenodename(getThValue(value, 4).split(",")); //
		 * dimEle.setChoicenodename(StringUtil.iso8859ToGBK(getThValue(value,
		 * 4)).split(",")); list.add(dimEle); } }
		 **********************************************************************/
		return dimlist;

	}

	/**
	 * Constructor of the object.
	 */
	public FlowpageSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		String parentid = request.getParameter("parentid");
		String pagepath = request.getParameter("pagepath");
		String doneTime = request.getParameter("donething");

		// 在这里可以继续扩展你的业务逻辑
		String seltgvalue = request.getParameter("seltgvalue");
		String seldimvalue = request.getParameter("seldimvalue");
		// String dataModelid = request.getParameter("datamodelid");
		String tgfiltSqlvalueReq = request.getParameter("tgfiltSqlvalue"); // 过滤SQL语句
		String tgfiltvalue = request.getParameter("tgfiltvalue"); // 如果过滤条件是最值的值
		String exterValue = request.getParameter("exterValue"); // 如果是华为无线,中兴无线,摩托无线时的维度查找条件
		String tggroup = request.getParameter("tgGroup");
		String xlevel = request.getParameter("choice");
		String actcondition = request.getParameter("actcondition");
		String acturl = request.getParameter("acturl");
		String dynamicDim = request.getParameter("dynamicDim");
		Map tgMap = this.getTgGroupValue(seltgvalue);
		List viewList = new ArrayList();
		int count = 0;
		String lsh = "";
		Map otherdim = new HashMap();
		for (Iterator i = tgMap.keySet().iterator(); i.hasNext();) {
			String tgGroupName = i.next().toString();
			String tgvalue = tgMap.get(tgGroupName).toString();

			ChoiceInfo choiceinfo = new ChoiceInfo();
			choiceinfo.setActcondition(actcondition);
			choiceinfo.setActurl(acturl);
			choiceinfo.setDataModelid(tgGroupName);
			choiceinfo.setDynamicDim(Boolean.parseBoolean(dynamicDim));

			choiceinfo.setTggroup(tggroup);
			choiceinfo.setXlevel(xlevel);

			List levelinfo = new ArrayList();
			List parserDimValue = null;
			Map objnodes = new HashMap();

			String allchkid = request.getParameter("allchkid");
			String allchkid2 = request.getParameter("allchkid2");
			String alltop = request.getParameter("alltop");
			String alltopvalue = request.getParameter("alltopvalue");
			String allalarm = request.getParameter("allalarm");
			String alltxtalarm = request.getParameter("alltxtalarm");
			String allorder = request.getParameter("allorder");
			String[] tgfiltSqlvalue = new String[7];
			tgfiltSqlvalue[0] = allchkid;
			tgfiltSqlvalue[1] = alltop;
			tgfiltSqlvalue[2] = alltopvalue;
			tgfiltSqlvalue[3] = allalarm;
			tgfiltSqlvalue[4] = alltxtalarm;
			tgfiltSqlvalue[5] = allorder;
			tgfiltSqlvalue[6] = allchkid2;

			parserDimValue = paserDimValue(seldimvalue, tgfiltSqlvalue, tgfiltvalue, exterValue, levelinfo, objnodes);

			// 2008-2-14 nihq start
			// ---------------------------------------------------
			// 修改:附加过滤条件
			if (StringUtils.isNotEmpty(request.getParameter("sqlview"))) {
				choiceinfo.setOtherinfo(request.getParameter("sqlview"));
			}
			// 修改:添加时间模式2,3时的缺少id为"start_time"的dimensionElement,同时修改所有时间模式的levelcolumnid
//			if (!"1".equals(request.getParameter("timetype"))) {
				DimensionElement de = new DimensionElement();
				de.setId("start_time");
				de.setName("");
				String choicenode = request.getParameter("timeResults");
				String[] choicenodes = StringUtils.split(choicenode, ",");
				de.setChoicenode(choicenodes);
				String choicenodename = request.getParameter("hour") + "时";
				String[] choicenames = new String[choicenodes.length];
				for (int j = 0; j < choicenames.length; j++) {
					choicenames[j] = choicenodename;
				}
				de.setChoicenodename(choicenames);
				String timelevel = request.getParameter("timelevelinfo");
				String levelcolumnid = StringUtils.substringBetween(timelevel, "=", "&");
				de.setLevelcolumnid(levelcolumnid);
				parserDimValue.add(de);
//			} else {
//				List list = new ArrayList();
//				for (Iterator iter = parserDimValue.iterator(); iter.hasNext();) {
//					DimensionElement de = (DimensionElement) iter.next();
//					if ("start_time".equals(de.getId())) {
//						String timelevel = request.getParameter("timelevelinfo");
//						String levelcolumnid = StringUtils.substringBetween(timelevel, "=", "&");
//						de.setLevelcolumnid(levelcolumnid);
//					}
//					list.add(de);
//				}
//				parserDimValue = list;
//			}
			choiceinfo.setDimensionElement(parserDimValue);
			// 2008-2-14 nihq end
			// ---------------------------------------------------

			for (Iterator itr = levelinfo.iterator(); itr.hasNext();) {
				break;
			}
			String tgnames = request.getParameter("tgnames");
			String[] tgname = StringUtils.split(tgnames, ",");
			choiceinfo.setTargetElement(parseTgValue(tgvalue, tgname));

			// 2008-2-21 nihq start
			// ---------------------------------------------------
			// 修改:choiceinfo对象新增变更
			String active = request.getParameter("active");
			if ("true".equals(active)) {
				choiceinfo.setActive(Boolean.parseBoolean(request.getParameter("active")));
				if (StringUtils.isEmpty(request.getParameter("showactive"))) {
					choiceinfo.setShowactive("1");
				} else {
					choiceinfo.setShowactive(request.getParameter("showactive"));
				}
				choiceinfo.setSelcharttype(request.getParameter("selcharttype"));
				choiceinfo.setSeldatatype(request.getParameter("seldatatype"));
				choiceinfo.setMaxvalue((request.getParameter("maxvalue")));
				choiceinfo.setMultichart(request.getParameter("multichart"));
				choiceinfo.setPngwidth(request.getParameter("pngwidth"));
				choiceinfo.setShowvalue(request.getParameter("showvalue"));
				choiceinfo.setPictitle(request.getParameter("pictitle"));
				choiceinfo.setPiccolor(request.getParameter("piccolor"));
				choiceinfo.setXqingxie(request.getParameter("xqingxie"));
				String str = request.getParameter("str");
				String[] seldim = new String[2];
				if (StringUtils.isEmpty(str)) {
					choiceinfo.setName_en("-1");
					choiceinfo.setStart_time("-1");
				} else {
					seldim = StringUtils.split(str, ",");
					for (int j = 0; j < seldim.length; j++) {
						String tmp = StringUtils.substringBefore(seldim[j], "=");
						if ("name_en".equals(tmp)) {
							choiceinfo.setName_en(StringUtils.substringAfter(seldim[j], "="));
						} else if ("start_time".equals(tmp)) {
							choiceinfo.setStart_time(StringUtils.substringAfter(seldim[j], "="));
						}
					}
				}
				String tgstr = request.getParameter("tgstr");
				String[] seltg = new String[1];
				if (StringUtils.isNotEmpty(tgstr)) {
					seltg = StringUtils.split(tgstr, ",");
				} else {
					seltg[0] = "";
				}
				choiceinfo.setSeltg(seltg);
			}

			// 2008-2-21 nihq end
			// ---------------------------------------------------

			WizardDao wd = null;

			try {
				wd = (WizardDao) RmiEntry.iv("bihandle");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				e1.printStackTrace();
			}
			choiceinfo.setName(request.getParameter("name"));
			choiceinfo.setNaturalname(request.getParameter("naturalname"));

			wd.create(choiceinfo, pagepath);
			lsh = choiceinfo.getLsh();
		}
		response.getWriter().print("<script>alert('创建结束!');opener.search();window.close();</script>");
		// PrintWriter writer = response.getWriter();
		// writer.print("<br>系统ID:" + parentid);
		// writer.print("<br>系统路径:" + pagepath);
		// writer.print("<br>操作完成时间:" + doneTime);
		//
		// WebTip.htmlInfo("操作完毕! ", true, response);
		// writer.close();

		//
		// Security ser = new Security(request);
		//
		// Blog blog = (Blog) CmsEntry.fetchDao("blog");
		//
		// blog.addBLK(request, response, name, width, height, ser
		// .getUserLoginName());
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
