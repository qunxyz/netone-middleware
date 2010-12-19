package oe.cms.xhtml.core.graph;

import java.awt.Color;

import javax.servlet.http.HttpServletRequest;

import oe.cms.xhtml.core.graph.util.ChartCreater;
import oe.cms.xhtml.core.graph.util.ChartParameter;
import oe.cms.xhtml.core.graph.util.JFreeChartUtil;
import oe.cms.xhtml.core.graph.util.MinYOffset;
import oe.cms.xhtml.core.graph.util.TargetAdept;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;



public class GraphBuilder {
	public static final String _Context = "servlet/graphSeverlet";

	private static ChartCreater chartcreater = ChartCreater.getInstance();

	public static byte[] fetchGraph(String dimvaluelist,
			String[] targetvaluelist, String dimName, String[] targetname,
			String charttype, String title, String xoffset, String yoffset,
			HttpServletRequest request) {
		ChartParameter cp = new ChartParameter();
		cp.setChartTitle(title);
		cp.setXlable(dimName);
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < targetname.length; i++) {
			but.append(targetname[i] + ",");
		}
		cp.setYlable("");
		// cp.setWidth(ChartCreater._WIGHT);
		cp.setWidth(Integer.parseInt(xoffset));
		cp.setHeigth(Integer.parseInt(yoffset));

		cp.setMinNum(MinYOffset.minY(targetvaluelist));

		CategoryDataset dataset = createCategoryDataset(targetname,
				targetvaluelist, dimvaluelist);
		JFreeChart chart = createJFreeChart(dataset, charttype, cp);
		chart.setBackgroundPaint(new Color(255, 255, 255));

		return JFreeChartUtil.cacheChartToSession(chart, cp);

	}

	/**
	 * 根据查询结果集List<Map>，构造图表的结果集，
	 * 
	 * @param list -
	 *            List<Map>
	 * @param targets
	 *            指标的字段，
	 * @param dim
	 *            维度的字段
	 * @return
	 */
	private static CategoryDataset createCategoryDataset(String[] target,
			String[] targetlist, String dimList) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (target == null || targetlist == null || dimList == null) {
			return dataset;
		}
		String[] dimInfo = dimList.split(",");
		try {
			for (int j = 0; j < target.length; j++) {
				String[] targetInfo = TargetAdept.adpetTarget(targetlist[j],
						dimInfo.length);
				for (int i = 0; i < targetInfo.length; i++) {
					Double value = null;
					if (targetInfo[i] == null || targetInfo[i].equals("")) {
						value = new Double("0");
					} else {
						value = new Double(targetInfo[i]);
					}
					dataset.addValue(value, target[j], dimInfo[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;
	}

	/**
	 * 根据不同的图表类型构造图表
	 * 
	 * @param dataset
	 * @param chartType
	 *            类型参见oe.comtools.web.chart.ChartCreater;
	 * @param chartpara
	 * @return
	 */
	private static JFreeChart createJFreeChart(CategoryDataset dataset,
			String chartType, ChartParameter chartpara) {

		if (chartType.equals(ChartCreater.CHART_Verticalbar3D)) {// 3D柱图
			return chartcreater.createVerticalbar3DChart(dataset, chartpara);
		} else if (chartType.equals(ChartCreater.CHART_CombinedBarLin)) {// 柱线对比（分图对比）
			return chartcreater.createCombinedBarLineChart(dataset, dataset,
					chartpara);
		} else if (chartType.equals(ChartCreater.CHART_Line)) {// 线图
			return chartcreater.createLineChart(dataset, chartpara);
		} else if (chartType.equals(ChartCreater.CHART_BarLine)) {// 柱线对比（单图）
			return chartcreater.createBarLineChart(dataset, dataset, chartpara);
		} else {
			return null;
		}
	}

}
