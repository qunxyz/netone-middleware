//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;






import oe.bi.BiEntry;
import oe.bi.analysis.BiAnalysis;
import oe.bi.analysis.util.BiAnalysisBind;
import oe.bi.common.PropertiesConf;
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
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;

/**
 * MyEclipse Struts Creation date: 06-28-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class EtlAnalyseAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(EtlAnalyseAction.class);

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

		String seltgvalue = request.getParameter("seltgvalue");
		String seldimvalue = request.getParameter("seldimvalue");
		String dataModelid = request.getParameter("datamodelid");
		String tgfiltSqlvalueReq = request.getParameter("tgfiltSqlvalue"); // 过滤SQL语句
		String topValue = request.getParameter("tgfiltvalue"); // 如果过滤条件是最值的值
		String exterValue = request.getParameter("exterValue"); // 如果是华为无线,中兴无线,摩托无线时的维度查找条件
		// String dataModelid = "CommuteMscAll";
		log.debug("tgfiltSqlvalue:" + tgfiltSqlvalueReq);
		log.debug("dataModelid:" + dataModelid);
		log.debug("seltgvalue:" + seltgvalue);
		log.debug("seldimvalue:" + seldimvalue);
		log.debug("exterValue:" + exterValue);
		Map tgMap = this.getTgGroupValue(seltgvalue);
		String[] tgfiltSqlvalue = new String[2];
		if (tgfiltSqlvalueReq != null && !tgfiltSqlvalueReq.equals("")) {
			tgfiltSqlvalue = tgfiltSqlvalueReq.split(":");
		}
		List viewList = new ArrayList();
		int count = 0;
		try {
			Map otherdim = new HashMap();
			for (Iterator i = tgMap.keySet().iterator(); i.hasNext();) {
				String tgGroupName = i.next().toString();
				String tgvalue = tgMap.get(tgGroupName).toString();
				log.debug("tgvalue:" + tgvalue);

				ChoiceInfo choiceinfo = new ChoiceInfo();

				choiceinfo.setDataModelid(tgGroupName);

				List levelinfo = new ArrayList();
				List parserDimValue = null;
				Map objnodes = new HashMap();
				if (tgfiltSqlvalue[1] != null
						&& tgfiltSqlvalue[1].equals(tgGroupName)) {
					parserDimValue = paserDimValue(seldimvalue,
							tgfiltSqlvalue[0], exterValue, levelinfo, objnodes);

				} else {
					parserDimValue = paserDimValue(seldimvalue, null,
							exterValue, levelinfo, objnodes);

				}
				choiceinfo.setDimensionElement(parserDimValue);

				for (Iterator itr = levelinfo.iterator(); itr.hasNext();) {
					log.debug("LEVEL TYPE =" + itr.next());
					break;
				}

				choiceinfo.setTargetElement(parseTgValue(tgvalue));
				// ////////////////// add by hls,cjx
				// 2007-8-17////////////////////
				PropertiesConf conf = new PropertiesConf("analyse.properties");
				// 验证查询条件的数据量是否过大
				if (choiceinfo != null) {

					int timecount = 0;
					int necount = 0;
					String levelcolumnid = (String) levelinfo.get(0);
					List DimensionElementList = choiceinfo
							.getDimensionElement();
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
					for (Iterator itr = objnodes.keySet().iterator(); itr
							.hasNext();) {
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

					System.out.println("当前数据总数预计:" + timecount * counts);
					System.out.println("时间数:" + timecount);
					System.out.println("网元数:" + counts);
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
				BiAnalysis analysis = (BiAnalysis)RmiEntry.iv("biAnalysis");

				String timespecial = request.getParameter("timespecial");
				choiceinfo.setOtherinfo(timespecial);

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
			}

			ViewModel viewmodel = BiAnalysisBind.bindViewModel(viewList,
					tgfiltSqlvalue[0] == null
							|| tgfiltSqlvalue[0].indexOf("order") < 0 ? ""
							: tgfiltSqlvalue[0]);
			viewmodel.setOtherDim(otherdim);
			if (topValue != null && !topValue.trim().equals("")) {
				int topInt = Integer.parseInt(topValue);
				double[][] tg = viewmodel.getTargetvalue();
				String[][] dim = viewmodel.getDimensionvalue();
				if (topInt > dim.length)
					topInt = dim.length;
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
			request.getSession().setAttribute("viewModelOri", viewmodel);

			request.getSession().setAttribute("viewModelNext", viewmodel);
		} catch (ErrorDataModelException e) {
			request.setAttribute("errsmg", e.getMessage());
			e.printStackTrace();
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

		// 设置第一个网元的图表。
		GraphModel gm = createFirstGraphModel(request, true);
		request.setAttribute("FirstGraphModel", gm);

		return mapping.findForward("vmcreate");
	}

	private List paserDimValue(String dimvalue, String tgfiltSqlvalue,
			String exterValue, List levelinfo, Map nodex) {
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
			List nodelist = (List) dimmap.get(iter.next());
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
			boolean encode) {
		DimensionFilt dimfilt = (DimensionFilt) BiEntry
				.fetchBi("dimensionFilt");
		ViewModel viewModel = (ViewModel) request.getSession().getAttribute(
				"viewModelNext");
		GraphModel gm = null;
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

		return gm;
	}

}
