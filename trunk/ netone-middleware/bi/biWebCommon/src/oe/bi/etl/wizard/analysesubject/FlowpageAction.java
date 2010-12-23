//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.analysis.BiAnalysis;
import oe.bi.analysis.util.BiAnalysisBind;
import oe.bi.common.PropertiesConf;
import oe.bi.common.StringUtil;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.datasource.NodeToDimensionElement;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;
import oe.bi.web.etl.util.AutoDig;
import oe.bi.wizard.WizardDao;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 06-28-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class FlowpageAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(FlowpageAction.class);
	
	static PropertiesConf conf = new PropertiesConf("analyse.properties");

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ResourceRmi rsrmi = null;
		String lsh = "";
		String extendattribute = "";
		// 处理数据来源
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String chkid = request.getParameter("chkid");
			UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
			if (StringUtils.isNotEmpty(request.getParameter("task"))) {// 从图表分析进入
				extendattribute = upo.getExtendattribute();

				String datasource = StringUtils.substringBetween(
						extendattribute, "datasource@$", "$");
				if (datasource != null && !datasource.equals("")) {
					UmsProtectedobject upo2 = rsrmi
							.loadResourceByNatural(datasource);
					lsh = upo2.getExtendattribute();
				}
			} else {
				lsh = upo.getExtendattribute();
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		WizardDao wd = null;
		ChoiceInfo choiceinfo = null;

		try {
			wd = (WizardDao) RmiEntry.iv("bihandle");
			choiceinfo = wd.fromXml(lsh);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}

		List oldlist = choiceinfo.getDimensionElement();
		List newlist = new ArrayList();
		for (Iterator iterator = oldlist.iterator(); iterator.hasNext();) {
			DimensionElement de = (DimensionElement) iterator.next();
			String id = de.getId();
			if (StringUtils.contains(id, ":")) {
				id = StringUtils.substringBefore(id, ":");
				de.setId(id);
			}
			newlist.add(de);
		}
		choiceinfo.setDimensionElement(newlist);
		if (StringUtils.isNotEmpty(extendattribute)) {
			String[] extend = StringUtils.split(extendattribute, ";");
			if (extend != null && extend.length > 0) {
				choiceinfo.setActive(true);
				for (int i = 0; i < extend.length; i++) {
					String[] d = StringUtils.split(extend[i], "@");
					String value = StringUtils.substringBetween(d[1], "$", "$");
					if ("selcharttype".equals(d[0])) {
						choiceinfo.setSelcharttype(value);
					} else if ("seldatatype".equals(d[0])) {
						choiceinfo.setSeldatatype(value);
					} else if ("str".equals(d[0])) {
						String[] v = StringUtils.split(value, ",");
						if (v != null && v.length > 0) {
							for (int j = 0; j < v.length; j++) {
								String[] s = StringUtils.split(v[j], "=");
								if (s != null && s.length > 0) {
									if ("name_en".equals(s[0])) {
										choiceinfo.setName_en(s[1]);
									} else if ("start_time".equals(s[0])) {
										choiceinfo.setStart_time(s[1]);
									}
								}
							}
						}
					} else if ("tgstr".equals(d[0])) {
						String[] seltg = StringUtils.split(value, ",");
						choiceinfo.setSeltg(seltg);
					} else if ("multichart".equals(d[0])) {
						choiceinfo.setMultichart(value);
					} else if ("showactive".equals(d[0])) {
						choiceinfo.setShowactive(value);
					} else if ("maxvalue".equals(d[0])) {
						choiceinfo.setMaxvalue(value);
					} else if ("pngwidth".equals(d[0])) {
						choiceinfo.setPngwidth(value);
					} else if ("showvalue".equals(d[0])) {
						choiceinfo.setShowvalue(value);
					} else if ("pictitle".equals(d[0])) {
						choiceinfo.setPictitle(value);
					} else if ("piccolor".equals(d[0])) {
						choiceinfo.setPiccolor(value);
					} else if ("xqingxie".equals(d[0])) {
						choiceinfo.setXqingxie(value);
					}
				}
			}
		}

		// 2008-3-4 修改中分析 nihq-----------start-----------
		String task2 = request.getParameter("task2");
		// 表格分析
		if ("analyse1".equals(task2)) {
			String allchkid2 = request.getParameter("allchkid2");
			String allorder = request.getParameter("allorder");
			String tgfiltvalue = request.getParameter("tgfiltvalue");
			String[] order = StringUtils.split(allorder, ",");
			String[] chkid2 = StringUtils.split(allchkid2, ",");
			List delist = new ArrayList();
			List dimensionElement = choiceinfo.getDimensionElement();
			for (Iterator iter = dimensionElement.iterator(); iter.hasNext();) {
				DimensionElement de = (DimensionElement) iter.next();
				if ("start_time".equals(de.getId())) {
					de.setChoicenode(StringUtils.split(request
							.getParameter("timeResults"), ","));
					delist.add(de);
				} else if ("sys_int_id".equals(de.getId())) {
					de.setChoicenode(StringUtils.split(request
							.getParameter("dimResult"), ","));
					delist.add(de);
				} else if (!"order".equals(de.getName())) {
					delist.add(de);
				}

			}
			if (allchkid2 != null && chkid2.length > 0) {
				for (int i = 0; i < chkid2.length; i++) {
					DimensionElement de = new DimensionElement();
					de.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid2[i],
							"{", "}");
					de.setChoicenodename(choicenodename);
					de.setId(chkid2[i]);
					// 最值
					if (StringUtils.isNotEmpty(tgfiltvalue)) {
						choicenode[0] = tgfiltvalue;
						// 排序
					} else {
						choicenode[0] = "";
					}
					de.setName("order");
					choicenode[0] = "";
					de.setOrder(true);
					if ("asc".equals(order[i])) {
						de.setDesc(false);
					} else if ("desc".equals(order[i])) {
						de.setDesc(true);
					}
					de.setChoicenode(choicenode);
					delist.add(de);
				}
			}
			choiceinfo.setDimensionElement(delist);
			List TargetElement = choiceinfo.getTargetElement();
			List<TargetElement> list = new ArrayList<TargetElement>();
			String tgothername = request.getParameter("tgothername");
			String[] othername = StringUtils.split(tgothername, "#");
			for (int i = 0; i < othername.length; i++) {
				String tgid = StringUtils.substringBefore(othername[i], "=");
				for (Iterator iter = TargetElement.iterator(); iter.hasNext();) {
					TargetElement te = (TargetElement) iter.next();
					String id = te.getId();
					if (tgid.equals(id)) {
						te.setName(StringUtils
								.substringAfter(othername[i], "="));
						list.add(te);
					}
				}
			}
			choiceinfo.setTargetElement(list);
			choiceinfo.setActive(Boolean.parseBoolean(request
					.getParameter("active")));
			choiceinfo.setActcondition(request.getParameter("actcondition"));
			choiceinfo.setActurl(request.getParameter("acturl"));
			// 图形分析
		} else if ("analyse2".equals(task2)) {
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
						choiceinfo.setName_en(StringUtils.substringAfter(
								seldim[j], "="));
					} else if ("start_time".equals(tmp)) {
						choiceinfo.setStart_time(StringUtils.substringAfter(
								seldim[j], "="));
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
		// 2008-3-4 修改中分析 -----------end----------

		List dimensionElement = choiceinfo.getDimensionElement();
		// 2008-2-19 过滤 nihq-----------start-----------
		// 增加过滤条件
		String allchkid = request.getParameter("allchkid");
		String allchkid2 = request.getParameter("allchkid2");
		String alltop = request.getParameter("alltop");
		String alltopvalue = request.getParameter("alltopvalue");
		String allalarm = request.getParameter("allalarm");
		String alltxtalarm = request.getParameter("alltxtalarm");
		String allorder = request.getParameter("allorder");
		String tgfilt = request.getParameter("tgfiltvalue");
		if (StringUtils.isNotEmpty(allchkid)) {
			String[] chkid = StringUtils.split(allchkid, ",");
			String[] alarm = StringUtils.split(allalarm, ",");
			String[] txtalarm = StringUtils.split(alltxtalarm, ",");
			if (chkid != null && chkid.length > 0) {
				for (int i = 0; i < chkid.length; i++) {
					DimensionElement dime = new DimensionElement();
					dime.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid[i],
							"{", "}");
					dime.setChoicenodename(choicenodename);
					dime.setId(StringUtils.substringBefore(chkid[i], "{"));
					// 门限
					if (StringUtils.isNotEmpty(allalarm)) {
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
					dimensionElement.add(dime);
				}
				choiceinfo.setDimensionElement(dimensionElement);
			}
		}
		if (StringUtils.isNotEmpty(allchkid2)) {
			String[] top = StringUtils.split(alltop, ",");
			String[] topvalue = StringUtils.split(alltopvalue, ",");
			String[] order = StringUtils.split(allorder, ",");
			String[] chkid2 = StringUtils.split(allchkid2, ",");
			if (chkid2 != null && chkid2.length > 0) {
				for (int i = 0; i < chkid2.length; i++) {
					DimensionElement dime = new DimensionElement();
					dime.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid2[i],
							"{", "}");
					dime.setChoicenodename(choicenodename);
					dime.setId(StringUtils.substringBefore(chkid2[i], "{"));
					// 最值
					if (StringUtils.isNotEmpty(tgfilt)) {
						choicenode[0] = tgfilt;
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
					dimensionElement.add(dime);
				}
				choiceinfo.setDimensionElement(dimensionElement);
			}
		}
		String sqlview = request.getParameter("sqlview");
		if (StringUtils.isNotEmpty(sqlview)) {
			sqlview = StringUtils.replace(sqlview, "$", "%");
			choiceinfo.setOtherinfo(StringUtil.iso8859ToGBK(sqlview));
		}
		dimensionElement = choiceinfo.getDimensionElement();
		// 2008-2-19 过滤 -----------end----------

		String tgfiltSqlvalue = "";
		String tgfiltvalue = "";
		String endTime = "";
		String timelevel = "";
		String datamodelid = choiceinfo.getDataModelid();
		for (Iterator iter = dimensionElement.iterator(); iter.hasNext();) {
			DimensionElement de = (DimensionElement) iter.next();
			if ("start_time".equals(de.getId())) {
				String[] choicenode = de.getChoicenode();
				if (choicenode.length == 1
						&& choicenode[choicenode.length - 1].split("#").length == 2) {
					endTime = new Date(System.currentTimeMillis()).toString()
							+ " 00:00:00";
				} else {
					endTime = StringUtils.replace(
							choicenode[choicenode.length - 1], "#", "")
							+ ":00:00";
				}
				timelevel = de.getLevelcolumnid();
			} else if ("sys_int_id".equals(de.getId())) {
				continue;
			} else {
				// tgfiltSqlvalue = tgfiltSqlvalue + " " + de.getId();
				if ("order".equals(de.getName())) {
					if (de.getChoicenode() != null
							&& de.getChoicenode().length > 0) {
						tgfiltvalue = de.getChoicenode()[0];
					}
				}

			}
		}

		List viewList = new ArrayList();
		int count = 0;
		try {
			Map otherdim = new HashMap();
			// for (Iterator i = tgMap.keySet().iterator(); i.hasNext();) {
			// String tgGroupName = i.next().toString();
			// String tgvalue = tgMap.get(tgGroupName).toString();
			// log.debug("tgvalue:" + tgvalue);

			// ChoiceInfo choiceinfo = new ChoiceInfo();

			// choiceinfo.setDataModelid(tgGroupName);

			// List levelinfo = new ArrayList();
			// List parserDimValue = null;
			Map objnodes = new HashMap();
			// if (StringUtils.isNotEmpty((dataModelid))) {
			// parserDimValue = paserDimValue(seldimvalue, tgfiltSqlvalue,
			// tgfiltvalue, exterValue, levelinfo, objnodes);
			//
			// } else {
			// parserDimValue = paserDimValue(seldimvalue, null, tgfiltvalue,
			// exterValue, levelinfo, objnodes);
			//
			// }
			// choiceinfo.setDimensionElement(parserDimValue);

			// for (Iterator itr = levelinfo.iterator(); itr.hasNext();) {
			// log.debug("LEVEL TYPE =" + itr.next());
			// break;
			// }

			// choiceinfo.setTargetElement(parseTgValue(tgvalue));
			// ////////////////// add by hls,cjx
			// 2007-8-17////////////////////

			// 验证查询条件的数据量是否过大
			if (choiceinfo != null) {

				int timecount = 0;
				int necount = 0;
				// String levelcolumnid = (String) levelinfo.get(0);
				List DimensionElementList = choiceinfo.getDimensionElement();
				for (int k = 0; k < DimensionElementList.size(); k++) {
					DimensionElement de = (DimensionElement) DimensionElementList
							.get(k);
					if ("start_time".equalsIgnoreCase(de.getId())) {
						timecount = de.getChoicenode().length;

					} else {
						if (de.getChoicenode() != null) {
							necount = de.getChoicenode().length;
						}
					}
				}
				String limitnum = conf.getProperty("limitCount");
				String mode = conf.getProperty("mode");
				AutoDig dig = (AutoDig) BiEntry.fetchBi(mode);

				int counts = 0;
				for (Iterator itr = objnodes.keySet().iterator(); itr.hasNext();) {
					String key = (String) itr.next();
					NodeObj nodeobj = (NodeObj) objnodes.get(key);
					if (nodeobj.getLevelname() != null) {
						// 获得当前节点的下一级节点个数,这个主要是针对用鼠标右箭选择时候的处理
						counts = counts + dig.countCell(nodeobj);
					} else {
						// 这个是直接用鼠标明确的选节点
						counts = necount;
					}
				}


				if (limitnum != null) {
					// 修订要求通过时间个数*网元个数的结果来判断是否超过预期的总数
					if (timecount * counts > Integer.parseInt(limitnum)) {
						// request.setAttribute("errmsg", "类型为"
						// + levelcolumnid + "的网元对应的选择时间的个数超过了"
						// + limitnum + "个，无法查询!");
						request.setAttribute("errmsg", conf
								.getProperty("limitTipPublic")
								+ "[" + timecount * counts + "]");

						return mapping.findForward("errorPage");
					}
				}
			}

			// //////////////////add by hls,cjx
			// 2007-8-17////////////////////

			// 将choiceinfo对象转换为ViewModel对象
			BiAnalysis analysis = null;
			try {
				analysis = (BiAnalysis) RmiEntry.iv("biAnalysis");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// String timespecial = request.getParameter("timespecial");
			// choiceinfo.setOtherinfo(timespecial);

			ViewModel vm = analysis.performBiAnaysis(choiceinfo);

			Map map = vm.getOtherDim();
			if (map != null && map.size() > 0) {
				for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
					String key = (String) itr.next();
					Object value = map.get(key);
					if (count > 0) {
						key += String.valueOf(count);
					}
	
					otherdim.put(key, value);
				}
				count++;
			}
			viewList.add(vm);
			// }

			ViewModel viewmodel = BiAnalysisBind.bindViewModel(viewList,
					tgfiltSqlvalue == null
							|| tgfiltSqlvalue.indexOf("order") < 0 ? ""
							: tgfiltSqlvalue);
			viewmodel.setOtherDim(otherdim);
			if (tgfiltvalue != null && !tgfiltvalue.trim().equals("")) {
				int topInt = Integer.parseInt(tgfiltvalue);
				double[][] tg = viewmodel.getTargetvalue();
				String[][] dim = viewmodel.getDimensionvalue();
				if (topInt > dim.length)
					topInt = dim.length;
				if (tg.length > 0) {
					double[][] topTg = new double[topInt][tg[0].length];
					String[][] topDim = new String[topInt][dim[0].length];
					for (int m = 0; m < topTg.length; m++) {
						for (int n = 0; n < topTg[0].length; n++) {
							topTg[m][n] = tg[m][n];
						}
						for (int n = 0; n < topDim[0].length; n++) {
							topDim[m][n] = dim[m][n];
						}
					}

					viewmodel.setDimensionvalue(topDim);
					viewmodel.setTargetvalue(topTg);
				}
			}
			request.getSession().setAttribute("viewModelOri", viewmodel);

			request.getSession().setAttribute("viewModelNext", viewmodel);
		} catch (ErrorDataModelException e) {
			request.setAttribute("errsmg", e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2008-2-22 修改设置第一个网元的图表,加入choiceinfo参数 nihq-----------start-----------
		// 设置第一个网元的图表。
		GraphModel gm = createFirstGraphModel(request, true, choiceinfo);
		request.setAttribute("FirstGraphModel", gm);
		// nihq -----------end-----------

		Map<String, String[]> actmap = new LinkedHashMap<String, String[]>();
		if (StringUtils.isNotEmpty(choiceinfo.getActurl())) {
			String[] acturl = StringUtils.split(choiceinfo.getActurl(), ";");
			for (int i = 0; i < acturl.length; i++) {
				String[] act = StringUtils.split(acturl[i], "#");
				actmap.put(act[0], act);
			}
		}
		// 使用flowpage
		request.setAttribute("flowpage", "flowpage");
		request.setAttribute("dataModelid", datamodelid);
		request.setAttribute("endTime", endTime);
		request.setAttribute("hour", "0");
		request.setAttribute("display", choiceinfo.getShowactive());
		request.setAttribute("timelevel", timelevel);
		request.setAttribute("actmap", actmap);
		return mapping.findForward("vmcreate");
	}

	private List paserDimValue(String dimvalue, String tgfiltSqlvalue,
			String tgfiltvalue, String exterValue, List levelinfo, Map nodex) {
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
				NodeObj node = (NodeObj) RequestUtil.mappingReqParam(
						NodeObj.class, map);
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
			String key=(String)iter.next();
			List nodelist = (List) dimmap.get(key);
			System.out.println(key);
			dimlist.add(NodeToDimensionElement.nodeToDim(nodelist));
		}

		if (exterValue != null && !exterValue.trim().equals("")) {
			DimensionElement dime = new DimensionElement();
			dime.setLevelcolumnid("@");
			dime.setId(exterValue);
			dimlist.add(dime);
		}
		if (tgfiltSqlvalue != null && !tgfiltSqlvalue.equals("")) {
			DimensionElement dime = new DimensionElement();
			dime.setLevelcolumnid("@");
			dime.setId(tgfiltSqlvalue);
			String[] choicenode = new String[1];
			choicenode[0] = tgfiltvalue;
			dime.setChoicenode(choicenode);
			dimlist.add(dime);
		}

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

	private List parseTgValue(String tgvalue) {
		ArrayList list = new ArrayList();
		String[] tgs = tgvalue.split(",");
		for (int i = 0; i < tgs.length; i++) {
			if (tgs[i].length() > 0) {
				String value = tgs[i];
				value = StringUtils.replace(value, "$nofilter$", "");
				// value=value.replaceAll("$nofilter$","");
				TargetElement targetElement = new TargetElement();
				targetElement.setId(value);
				list.add(targetElement);
			}
		}
		return list;
	}

	public String getThValue(String str, int index) {
		int tmp = -1;
		for (int m = 0; m < index; m++) {
			tmp = str.indexOf("[", tmp + 1);
		}
		if (tmp != -1) {
			int i = tmp;
			int j = str.indexOf("]", i);
			return str.substring(i + 1, j);
		} else {
			return null;
		}
	}

	/**
	 * 构建图表对象(用于显示表格时,显示图表)
	 * 
	 * 注意: 该方法会自动选择一个维度(非时间维度的值)(在目前的业务中只有两个维度时间和非时间),并且 展现所有的指标,图表模式为立体柱图
	 * 
	 * @param request
	 * @param encode
	 * @return
	 */
	private GraphModel createFirstGraphModel(HttpServletRequest request,
			boolean encode, ChoiceInfo choiceinfo) {
		DimensionFilt dimfilt = (DimensionFilt) BiEntry
				.fetchBi("dimensionFilt");
		ViewModel viewModel = (ViewModel) request.getSession().getAttribute(
				"viewModelNext");
		GraphModel gm = null;
		// xml中自带选择图表定制条件
		if (choiceinfo.isActive()) {
			gm = new GraphModel();
			String seltgvalue = "";
			String OffsetDim = "";
			String selcharttype = choiceinfo.getSelcharttype();
			String seldatatype = choiceinfo.getSeldatatype();
			String name_en = choiceinfo.getName_en();
			String start_time = choiceinfo.getStart_time();
			String[] seltgvalues = choiceinfo.getSeltg();
			if (seltgvalues != null && seltgvalues.length > 0) {
				for (int i = 0; i < seltgvalues.length; i++) {
					seltgvalue += seltgvalues[i] + ",";
				}
			}
			gm.setChoicetarget(seltgvalue);
			gm.setGraphType(selcharttype + "$" + seldatatype);
			HashMap dimmap = new HashMap();
			if (!"-1".equals(name_en)) {
				dimmap.put("name_en", name_en);
			}
			if (!"-1".equals(start_time)) {
				String levelcolumnid = "";
				List list = choiceinfo.getDimensionElement();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DimensionElement de = (DimensionElement) iterator.next();
					if ("start_time".equals(de.getId())) {
						levelcolumnid = de.getLevelcolumnid();
					}
				}
				if ("1h".equals(levelcolumnid)) {
					dimmap.put("start_time", StringUtils.substringBefore(
							start_time, ":"));
				} else if ("1d".equals(levelcolumnid)) {
					dimmap.put("start_time", StringUtils.substringBefore(
							start_time, " "));
				} else if ("1m".equals(levelcolumnid)) {
					dimmap.put("start_time", StringUtils.substringBeforeLast(
							start_time, "-"));
				} else if ("1y".equals(levelcolumnid)) {
					dimmap.put("start_time", StringUtils.substringBefore(
							start_time, "-"));
				}
			}
			gm.setOtherDimension(dimmap);
			if (!"-1".equals(name_en) && !"-1".equals(start_time)) {
				OffsetDim = "undefined";
			} else if (!"-1".equals(name_en) && "-1".equals(start_time)) {
				OffsetDim = "start_time";
			} else if ("-1".equals(name_en) && !"-1".equals(start_time)) {
				OffsetDim = "name_en";
			}
			gm.setXOffsetDimension(OffsetDim);
			request.setAttribute("display", choiceinfo.getShowactive());
			request.getSession().setAttribute("choiceinfo", choiceinfo);
		} else {
			// 没有选择图表定制条件
			try {
				String[][] dimvalues = dimfilt.allDimensionValueList(viewModel);
				String dimSelectValue = "";
				if (dimvalues != null) {
					if ("start_time".equals(dimvalues[0][0])) {
						if (dimvalues[1][2] != null) {
							String[] dimValueArr = dimvalues[1][2].split(",");
							dimSelectValue = dimValueArr[0];
						}
					} else {
						if (dimvalues[0][2] != null) {
							String[] dimValueArr = dimvalues[0][2].split(",");
							dimSelectValue = dimValueArr[0];
						}
					}
				}
				// 构造GraphModel by hls
				String seldimvalue = "sys_int_id=" + dimSelectValue;
				String seltgvalue = "";
				String OffsetDim = "start_time";
				String selcharttype = "verticalbar3D";
				// --- nihq 2008-2-19 START------------
				// 目的:改变默认图表显示
				// 原始图表
				if ("chart0".equals(request.getParameter("task"))) {
					selcharttype = "verticalbar3D";
				}
				// ------------nihq 2008-2-13 END----------

				if (OffsetDim != null && !OffsetDim.equals("")) {
					gm = new GraphModel();
					gm.setChoicetarget(seltgvalue);
					gm.setGraphType(selcharttype);
					gm.setXOffsetDimension(OffsetDim);
					String[] seldims = seldimvalue.split(",");
					HashMap dimmap = new HashMap();
					for (int i = 0; i < seldims.length; i++) {
						int index = seldims[i].indexOf("=");
						if (index != -1) {
							String key = seldims[i].substring(0, index);
							String value = seldims[i].substring(index + 1);
							dimmap.put(key, value);
						}
					}
					gm.setOtherDimension(dimmap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return gm;
	}

}
