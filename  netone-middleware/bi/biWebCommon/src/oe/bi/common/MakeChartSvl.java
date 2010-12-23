package oe.bi.common;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.rmi.NotBoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.analysis.BiAnalysis;
import oe.bi.analysis.util.BiAnalysisBind;
import oe.bi.common.chart.CachedChart;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;
import oe.bi.web.etl.chart.EtlChart;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartUtilities;

public class MakeChartSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1904987127165609704L;

	/**
	 * Constructor of the object.
	 */
	public MakeChartSvl() {
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
		ResourceRmi rsrmi = null;
		WizardDao wd = null;
		String lsh = "";
		String extendattribute = "";
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			wd = (WizardDao) RmiEntry.iv("bihandle");
			String chkid = request.getParameter("chkid");
			UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
			extendattribute = upo.getExtendattribute();
			String datasource = StringUtils.substringBetween(extendattribute,
					"datasource@$", "$");

			UmsProtectedobject upo2 = rsrmi.loadResourceByNatural(datasource);
			lsh = upo2.getExtendattribute();
		} catch (Exception e) {
			response.getWriter().print(e.getMessage());
			e.printStackTrace();
			return;
		}

		ChoiceInfo choiceinfo = wd.fromXml(lsh);
		List oldlist = choiceinfo.getDimensionElement();
		List<DimensionElement> newlist = new ArrayList<DimensionElement>();
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
		if (StringUtils.isNotEmpty(request.getParameter("sqlview"))) {
			choiceinfo.setOtherinfo(request.getParameter("sqlview"));
		}
		if (StringUtils.isNotEmpty(extendattribute)) {
			String[] extend = StringUtils.split(extendattribute, ";");
			if (extend != null && extend.length > 0) {
				choiceinfo.setActive(true);
				for (int i = 0; i < extend.length; i++) {
					String[] d = StringUtils.split(extend[i], "@");
					String value = StringUtils.substringBetween(d[1], "$", "$");

					String manualkey = d[0];
					String manualvalue = request.getParameter(manualkey);// 如果用户在外部设置
					System.out.println(manualkey+","+manualvalue);														// 图表的参数，那么读取用户设定的值
					if (manualvalue != null && !manualvalue.equals("")) {
						try {
							BeanUtils.setProperty(choiceinfo, manualkey,
									manualvalue);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}

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
		
		List<ViewModel> viewList = new ArrayList<ViewModel>();
		BiAnalysis analysis = null;
		try {
			analysis = (BiAnalysis) RmiEntry.iv("biAnalysis");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ViewModel vm = analysis.performBiAnaysis(choiceinfo);
			viewList.add(vm);
		} catch (ErrorDataModelException e) {
			e.printStackTrace();
		}

		ViewModel viewmodel = BiAnalysisBind.bindViewModel(viewList, null);

		GraphModel gm = createFirstGraphModel(request, true, choiceinfo);

		DimensionFilt dimfilt = (DimensionFilt) BiEntry
				.fetchBi("dimensionFilt");

		boolean multichart = false;
		String multichartstr = choiceinfo.getMultichart();
		if (multichartstr != null && multichartstr.equals("1")) {
			multichart = true;
		}

		if (gm.getGraphType().endsWith("ne-time")
				|| gm.getGraphType().endsWith("time-ne")) {
			multichart = true;
		}

		ViewModel newvm = null;

		if (gm.getGraphType().endsWith("ne-time")
				|| gm.getGraphType().endsWith("time-ne")) {
			newvm = viewmodel;
		} else {
			try {
				newvm = dimfilt.filtvalue(viewmodel, gm);
			} catch (MoreThenOneDimensionViewModel e) {
				request.setAttribute("errsmg", e.getMessage());
				e.printStackTrace();
			}
		}

		// 构建图表数据
		String pngwidthstr = choiceinfo.getPngwidth();
		String maxvaluestr = choiceinfo.getMaxvalue();
		String showvalue = choiceinfo.getShowvalue();
		String pictitle = choiceinfo.getPictitle();
		String piccolor = choiceinfo.getPiccolor();
		String xqingxie = choiceinfo.getXqingxie();
		int pngwidth = 0;
		int maxvalue = 0;
		try {
			if (pngwidthstr != null) {
				pngwidth = Integer.parseInt(pngwidthstr);
			}
		} catch (Exception e) {
		}
		try {
			if (maxvaluestr != null) {
				maxvalue = Integer.parseInt(maxvaluestr);
			}
		} catch (Exception e) {
		}
		EtlChart etlchart = null;
		boolean b = true;
		if (!multichart) {
			etlchart = new EtlChart(newvm, gm);
			etlchart.setMaxValue(maxvalue);
			etlchart.setPicWidth(pngwidth);
			etlchart.setPiccolor(piccolor);
			etlchart.setPictitle(pictitle);
			etlchart.setXqingxie(xqingxie);
			if (showvalue != null) {
				etlchart.setShowvalue(showvalue);
			}
		} else {
			String seltg = gm.getChoicetarget();
			String[] tgs;
			if (seltg.equals("")) {
				tgs = newvm.getTargetid();
			} else {
				tgs = seltg.split(",");
			}
			String index = request.getParameter("index");
			if (StringUtils.isEmpty(index)) {
				index = "0";
			}
			int indexValue = Integer.valueOf(index);
			if (indexValue > tgs.length) {
				b = false;
			} else {
				for (int i = 0; i < tgs.length; i++) {
					if (i == indexValue) {
						etlchart = new EtlChart(newvm, gm, tgs[i]);
						etlchart.setMaxValue(maxvalue);
						etlchart.setPicWidth(pngwidth);
						etlchart.setPiccolor(piccolor);
						etlchart.setPictitle(pictitle);
						etlchart.setXqingxie(xqingxie);
					}
				}
			}

		}
		if (b) {
			BufferedImage bufimg = etlchart.getJfreeChart()
					.createBufferedImage(etlchart.getChartparam().getWidth(),
							etlchart.getChartparam().getHeigth());
			try {
				byte[] imgbyte = ChartUtilities.encodeAsPNG(bufimg);
				CachedChart cchart = new CachedChart();
				cchart.setImg(imgbyte);
				BufferedOutputStream bos = new BufferedOutputStream(response
						.getOutputStream());
				bos.write(cchart.getImg());
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
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
		GraphModel gm = new GraphModel();
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
		HashMap<String, String> dimmap = new HashMap<String, String>();
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
		return gm;
	}
}
