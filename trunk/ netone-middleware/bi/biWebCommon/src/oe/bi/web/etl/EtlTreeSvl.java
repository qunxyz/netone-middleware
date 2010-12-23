package oe.bi.web.etl;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.bi.BiEntry;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.dataModel.bus.ActionDigTree;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.datasource.DimensionAct;
import oe.bi.exceptions.TreeModelException;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;
import oe.bi.wizard.WizardDao;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;

public class EtlTreeSvl extends HttpServlet {

	static Log log = LogFactory.getLog(EtlTreeSvl.class);

	/**
	 * Constructor of the object.
	 */
	public EtlTreeSvl() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");

		// 返回值。
		String ele = "";

		String flag = request.getParameter(DigTreeBuilder._SRC_ANALYSIS_TYPE);
		// 预测分析的树图数据
		if (flag != null && flag.equals("1")) {
			ActionDigTree actionDigTree = (ActionDigTree) BiEntry
					.fetchBi("actionDigTree");

			// 默认维度选择的树图数据
			String dmid = request.getParameter(DigTreeBuilder._SRC_DATAMODELID);
			String dimid = request
					.getParameter(DigTreeBuilder._SRC_DIMENSIONID);
			String dimvalue = request
					.getParameter(DigTreeBuilder._SRC_DIMENSIONVALUE);
			String levels = request.getParameter(DigTreeBuilder._SRC_LEVEL);

			if (levels == null) {
				levels = "0";
			}

			ViewModel viewModel = (ViewModel) request.getSession()
					.getAttribute("viewModelOri");
			String[] dimensionid = viewModel.getDimensionid();
			String[] dimlevels = viewModel.getDimensionlevel();
			String dimLevel = null;
			for (int i = 0; i < dimensionid.length; i++) {
				if (dimensionid[i].equals(dimid)) {
					dimLevel = dimlevels[i];
				}
			}

			String otherdim = request.getParameter("fc_otherdim");
			String levelx = request.getParameter("fc_level");
			String offdimstr = request.getParameter("fc_offdimstr");

			if (levelx != null) {
				request.getSession().setAttribute("fc_otherdim", otherdim);
				request.getSession().setAttribute("fc_level", levelx);
				request.getSession().setAttribute("fc_offdimstr", offdimstr);
			} else {
				otherdim = request.getSession().getAttribute("fc_otherdim")
						.toString();
				levelx = request.getSession().getAttribute("fc_level")
						.toString();
				offdimstr = request.getSession().getAttribute("fc_offdimstr")
						.toString();
			}

			GraphModel gm = new GraphModel();
			gm.setForcastLevelid(levelx);
			gm.setXOffsetDimension(dimid);
			gm.setXoffsetDimensionForcastValue(offdimstr);
			if (otherdim != null) {
				String[] seldims = otherdim.split(",");
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

			try {
				ele = actionDigTree.fetchTreeElement(dmid, dimid, dimLevel,
						dimvalue, levels, gm);
			} catch (TreeModelException e) {
				e.printStackTrace();
				throw new ServletException(e);
			}

			// actionDigTree.fetchTreeElement()

		} else {
			WizardDao wd = null;
			try {
				wd = (WizardDao) RmiEntry.iv("bihandle");
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String treeModelId = request.getParameter("treeModelId");
			String nodeid = request.getParameter("nodeid");
			String nodename = request.getParameter("nodename");
			String fathernodeid = request.getParameter("fathernodeid");
			String levelname = request.getParameter("levelname");
			NodeObj nodeobj = null;
			if (nodeid != null) {
				nodeobj = new NodeObj();
				nodeobj.setNodeid(nodeid);
				nodeobj.setNodename(nodename);
				nodeobj.setFathernodeid(fathernodeid);
				nodeobj.setLevelname(levelname);
			}

			if ("timetree".equals(treeModelId)) {
				// 时间树图
				if (nodeobj == null) {
					nodeobj = new NodeObj();
					nodeobj.setNodeid("root");
				}
				ele = wd.timeTree(nodeobj);
			} else {
				// 维度树图
				ele = wd.subTree(treeModelId, nodeobj);
			}
		}
		/***********************************************************************
		 * * 原来的备份 ActionDigTree actionDigTree = (ActionDigTree) BiEntry
		 * .fetchBi("actionDigTree"); String ele = "";
		 * 
		 * String flag =
		 * request.getParameter(DigTreeBuilder._SRC_ANALYSIS_TYPE); //
		 * 默认维度选择的树图数据 String dmid =
		 * request.getParameter(DigTreeBuilder._SRC_DATAMODELID); String dimid =
		 * request.getParameter(DigTreeBuilder._SRC_DIMENSIONID); String
		 * dimvalue = request .getParameter(DigTreeBuilder._SRC_DIMENSIONVALUE);
		 * String levels = request.getParameter(DigTreeBuilder._SRC_LEVEL);
		 * 
		 * if (levels == null) { levels = "0"; } // 预测分析的树图数据 if (flag != null &&
		 * flag.equals("1")) {
		 * 
		 * ViewModel viewModel = (ViewModel) request.getSession()
		 * .getAttribute("viewModelOri"); String[] dimensionid =
		 * viewModel.getDimensionid(); String[] dimlevels =
		 * viewModel.getDimensionlevel(); String dimLevel = null; for (int i =
		 * 0; i < dimensionid.length; i++) { if (dimensionid[i].equals(dimid)) {
		 * dimLevel = dimlevels[i]; } }
		 * 
		 * String otherdim = request.getParameter("fc_otherdim"); String levelx =
		 * request.getParameter("fc_level"); String offdimstr =
		 * request.getParameter("fc_offdimstr");
		 * 
		 * if (levelx != null) {
		 * request.getSession().setAttribute("fc_otherdim", otherdim);
		 * request.getSession().setAttribute("fc_level", levelx);
		 * request.getSession().setAttribute("fc_offdimstr", offdimstr); } else {
		 * otherdim = request.getSession().getAttribute("fc_otherdim")
		 * .toString(); levelx = request.getSession().getAttribute("fc_level")
		 * .toString(); offdimstr =
		 * request.getSession().getAttribute("fc_offdimstr") .toString(); }
		 * 
		 * GraphModel gm = new GraphModel(); gm.setForcastLevelid(levelx);
		 * gm.setXOffsetDimension(dimid);
		 * gm.setXoffsetDimensionForcastValue(offdimstr); if (otherdim != null) {
		 * String[] seldims = otherdim.split(","); HashMap dimmap = new
		 * HashMap(); for (int i = 0; i < seldims.length; i++) { int index =
		 * seldims[i].indexOf("="); if (index != -1) { String key =
		 * seldims[i].substring(0, index); String value =
		 * seldims[i].substring(index + 1); dimmap.put(key, value); } }
		 * gm.setOtherDimension(dimmap); }
		 * 
		 * try { ele = actionDigTree.fetchTreeElement(dmid, dimid, dimLevel,
		 * dimvalue, levels, gm); } catch (TreeModelException e) {
		 * e.printStackTrace(); throw new ServletException(e); } //
		 * actionDigTree.fetchTreeElement() } else {
		 * 
		 * try { ele = actionDigTree.fetchTreeElement(dmid, dimid, dimvalue,
		 * levels); } catch (TreeModelException e) { e.printStackTrace(); throw
		 * new ServletException(e); } }
		 * 
		 **********************************************************************/

		log.debug(ele);

		PrintWriter out = response.getWriter();
		out.println(ele);
		out.flush();
		out.close();
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
