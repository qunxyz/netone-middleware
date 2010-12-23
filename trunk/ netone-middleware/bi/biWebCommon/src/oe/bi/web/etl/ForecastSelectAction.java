//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.analysis.BiForcast;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.obj.ViewModel;
import oe.frame.web.util.WebStr;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * MyEclipse Struts Creation date: 07-16-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ForecastSelectAction extends Action {

	private static Logger logger = Logger.getLogger(ForecastSelectAction.class.getName());

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

		ViewModel vm = (ViewModel) request.getSession().getAttribute("viewModelOri");
		/**
		 * 08-12-1修订,在预测分析中增加预测修正系数
		 */
		String[] targetname = vm.getTargetname();
		String[] targetid = vm.getTargetid();
		String[][] tarnameid = new String[targetname.length][2];
		for (int i = 0; i < tarnameid.length; i++) {
			tarnameid[i][0] = targetid[i];
			tarnameid[i][1] = targetname[i];
		}
		request.setAttribute("tarnameid", tarnameid);

		/**
		 * 07-4-13日修订,之前vm中的dimtype 会和 dimId不一致
		 */
		modifyModel(vm);

		debugVm(vm);

		DimensionFilt df = (DimensionFilt) BiEntry.fetchBi("dimensionFilt");

		String[][] dmvlist = df.allDimensionValueList(vm);

		BiForcast bf = (BiForcast) BiEntry.fetchBi("biForcast");

		String timedimstr = null;
		try {
			timedimstr = bf.fetchTimeDimensionLink(vm);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("timedimstr:" + timedimstr);

		request.setAttribute("timedimstr", timedimstr);

		// String[] timedimitems = timedimstr.split(",");

		ArrayList timedimlist = new ArrayList();
		ArrayList otherdimlist = new ArrayList();
		for (int i = 0; i < dmvlist.length; i++) {
			logger.debug("dmv:" + dmvlist[i][0] + "   " + dmvlist[i][1] + "    " + dmvlist[i][2]);
			String[] lvb = { dmvlist[i][0], dmvlist[i][1], dmvlist[i][2] };

			if (timedimstr.indexOf(dmvlist[i][0] + ",") != -1) {
				timedimlist.add(lvb);
			} else {
				otherdimlist.add(lvb);
			}
		}

		request.setAttribute("timedimlist", timedimlist);
		request.setAttribute("otherdimlist", otherdimlist);

		if (timedimlist.size() == 1) {
			request.setAttribute("timeseldisabled", "disabled");
		}

		String[][] arts = bf.FORACAST_ARITHMETIC;

		request.setAttribute("arts", arts);

		String checkstr = checkVm(timedimlist);

		if (checkstr != null) {
			request.setAttribute("closesmg", checkstr);
		}
		String endTime = "";

		String[][] dimValue = vm.getDimensionvalue();

		String seldim = WebStr.iso8859ToGBK(request.getParameter("seldim"));
		if (StringUtils.isEmpty(seldim)) {
			seldim = dimValue[0][0];
		}
		for (int i = 0; i < dimValue.length; i++) {
			String[] value = dimValue[i];
			if (value[0].equals(seldim)) {
				if (endTime.compareTo(value[1]) < 0) {
					endTime = value[1];
				}
			}
		}
		endTime = endTime + " 00:00:00";
		
		// 使用flowpage
		if ("flowpage".equals(request.getParameter("flowpage"))) {
			request.setAttribute("flowpage", "flowpage");
			request.setAttribute("endTime", endTime);
			request.setAttribute("timelevel", request.getParameter("timelevel"));
			request.setAttribute("dataModelid", request.getParameter("dataModelid"));
			request.setAttribute("hour", request.getParameter("hour"));
		}
		if ("ajax".equals(request.getParameter("task"))) {
			try {
				PrintWriter out = response.getWriter();
				out.write(endTime);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return mapping.findForward("forecastselect");
	}

	private String checkVm(ArrayList timedimlist) {

		if (timedimlist.size() < 1) {
			return BiForcast._LOSE_TIME_SERIAL;
		} else if (timedimlist.size() == 1) {
			String[] dim = (String[]) timedimlist.get(0);
			String[] values = dim[2].split(",");
			if (values.length == 1) {
				return BiForcast._ONLY_ONE_TIMEDATA;
			}
		}

		return null;
	}

	private void modifyModel(ViewModel vm) {
		String[] dimtype = vm.getDimensionType();
		String[] dimId = vm.getDimensionid();
		if ("date".equals(dimtype[0])) {
			if (!"start_time".equals(dimId[0])) {
				String dimtypeTemp = dimtype[1];
				dimtype[1] = dimtype[0];
				dimtype[0] = dimtypeTemp;
				vm.setDimensionType(dimtype);
			}
		}
	}

	private void debugVm(ViewModel vm) {
		String[] dimtype = vm.getDimensionType();

		logger.debug("----dimtype----");
		for (int i = 0; i < dimtype.length; i++) {
			logger.debug(dimtype[i]);
		}
		logger.debug("----dimtype----");

		String[] dimId = vm.getDimensionid();
		logger.debug("----dimId----");
		for (int i = 0; i < dimId.length; i++) {
			logger.debug(dimId[i]);
		}
		logger.debug("----dimId----");

		String[] dimensionname = vm.getDimensionname();
		logger.debug("----dimensionname----");
		for (int i = 0; i < dimensionname.length; i++) {
			logger.debug(dimensionname[i]);
		}
		logger.debug("----dimensionname----");

		String[][] dimensionvalue = vm.getDimensionvalue();
		logger.debug("----dimensionvalue----");
		for (int i = 0; i < dimensionvalue.length; i++) {
			for (int j = 0; j < dimensionvalue[i].length; j++) {
				logger.debug(dimensionvalue[i][j]);
			}
			logger.debug("next--------");

		}
		logger.debug("----dimensionvalue----");

	}

}
