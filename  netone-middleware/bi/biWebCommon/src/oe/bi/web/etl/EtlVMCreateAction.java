//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.analysis.BiForcast;
import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.exceptions.NeedMoreThenForcastOneValueException;
import oe.bi.view.bus.filt.TargetFilt;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * MyEclipse Struts Creation date: 07-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class EtlVMCreateAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

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
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if ("flowpage".equals(request.getParameter("flowpage"))) {
			request.setAttribute("flowpage", "flowpage");
			request.setAttribute("endTime", request.getParameter("endTime"));
			request.setAttribute("dataModelid", request.getParameter("dataModelid"));
			request.setAttribute("timelevel", request.getParameter("timelevel"));
			request.setAttribute("hour", request.getParameter("hour"));
		}
		if (request.getAttribute("errsmg") != null) {
			return mapping.findForward("table");
		}

		String act = request.getParameter("act");

		ViewModel orivm = (ViewModel) request.getSession().getAttribute("viewModelOri");

		// 处理指标过滤
		String tgfiltvalue = request.getParameter("tgfiltvalue");
		if (tgfiltvalue != null && !tgfiltvalue.equals("")) {
			TargetFilt targetFilt = (TargetFilt) BiEntry.fetchBi("targetFilt");
			String[] tgfilt = tgfiltvalue.split(",");
			if (tgfilt.length == 4) {
				TargetFiltObj tfo = new TargetFiltObj();
				tfo.setTargetid(tgfilt[0]);
				String tmp1 = tgfilt[1].substring(0, tgfilt[1].indexOf("="));
				if (!tmp1.equals("-1")) {
					if (tmp1.equals(tfo._CHOICE_TOP_TOPN)) {
						tfo.setTopn(tgfilt[1].substring(tgfilt[1].indexOf("=") + 1));
					} else {
						tfo.setBotn(tgfilt[1].substring(tgfilt[1].indexOf("=") + 1));
					}
				}

				String tmp2 = tgfilt[2].substring(0, tgfilt[2].indexOf("="));
				String tmp2v = tgfilt[2].substring(tgfilt[2].indexOf("=") + 1);

				if (!tmp2.equals("-1")) {
					if (tmp2.equals(tfo._CHOICE_ALARM_UP)) {
						tfo.setAlarm(tmp2v);
					} else {
						tfo.setAlarm("-" + tmp2v);
					}
				}

				tfo.setDesc(tgfilt[3].substring(tgfilt[3].indexOf("=") + 1));

				ViewModel tmpvm = targetFilt.filtvalue(orivm, tfo);

				request.getSession().setAttribute("viewModelNext", tmpvm);
			}

		}

		// 处理预测分析
		String fcvalue = request.getParameter("forecastvalue");
		HashMap dimmap = new HashMap();
		if (fcvalue != null && !fcvalue.equals("")) {
			String[] fcitems = fcvalue.split(";");
			// 预测算法;预测维度;预测值;预测值等级;模型id;其他维度值;选择的维度的当前维度值;
			GraphModel gm = new GraphModel();
			gm.setForcastArithmetic(fcitems[0]);
			gm.setXOffsetDimension(fcitems[1]);
			gm.setXoffsetDimensionForcastValueNext(fcitems[2]);
			gm.setForcastLevelid(fcitems[3]);

			if (fcitems[5] != null && !fcitems[5].equals("")) {
				String[] seldims = fcitems[5].split(",");

				for (int i = 0; i < seldims.length; i++) {
					int index = seldims[i].indexOf("=");
					if (index != -1) {
						String key = seldims[i].substring(0, index);
						String value = seldims[i].substring(index + 1);
						dimmap.put(key, value);
					}
				}

			}
			gm.setOtherDimension(dimmap);
			gm.setXoffsetDimensionForcastValue(fcitems[6]);
			BiForcast biForcast=null;
			try {
				biForcast = (BiForcast)RmiEntry.iv("biForcast");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ViewModel tmpvm = null;

			try {

				// 修订 -增加预测结果修订系数 2008-2-1
				Map map = new HashMap();
				String modifyvalue = request.getParameter("modifyvalue");
				if (modifyvalue != null && !modifyvalue.equals("")) {
					String[] modifyvalueTmp = modifyvalue.split(";");
					for (int i = 0; i < modifyvalueTmp.length; i++) {
						String[] modifyvalueTmpIdValue = modifyvalueTmp[i].split(":");
						if (modifyvalueTmpIdValue.length == 2 && modifyvalueTmpIdValue[1] != null
								&& !modifyvalueTmpIdValue[1].equals("")) {

							double modifyValue = Double.parseDouble(modifyvalueTmpIdValue[1]);
							map.put(modifyvalueTmpIdValue[0], modifyValue);
						}
					}
					// ////////////////////////////////
				}
				tmpvm = biForcast.performBiForcast(orivm, gm, map);
			} catch (MoreThenOneDimensionViewModel e) {
				request.setAttribute("errsmg", e.getMessage());
				e.printStackTrace();
			} catch (NeedMoreThenForcastOneValueException e) {
				request.setAttribute("errsmg", e.getMessage());
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.getSession().setAttribute("viewModelNext", tmpvm);
		}

		if ("tableView".equals(act)) {
			return mapping.findForward("table");
		}

		// else if(act.equals("chartView")){
		// return mapping.findForward("chart");
		// }

		return mapping.findForward("table");

	}

}
