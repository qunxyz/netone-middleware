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



/**
 * 通用轴指标图，安全稳定版本，利用JfreeChart中的 支持使用异步维度
 * 
 * @author chen.jia.xun
 * 
 */
public class Graph2Builder {
	public static final String _Context = "servlet/graph2Severlet";

	private static ChartCreater chartcreater = ChartCreater.getInstance();

	/**
	 * 
	 * @param dimvaluelist
	 * @param targetvaluelist
	 * @param targetvaluelistR
	 * @param dimName
	 * @param targetname
	 * @param targetnameR
	 * @param charttype
	 * @param title
	 * @param request
	 * @return
	 */
	public static byte[] fetchGraph(String dimvaluelist,
			String[] targetvaluelist, String[] targetvaluelistR,
			String dimName, String[] targetname, String[] targetnameR,
			String charttype, String title, String xoffset,String yoffset,HttpServletRequest request) {

		ChartParameter cp = new ChartParameter();
		cp.setChartTitle(title);
		cp.setXlable(dimName);
		cp.setYlable("");
		cp.setWidth(Integer.parseInt(xoffset));
		cp.setHeigth(Integer.parseInt(yoffset));
		cp.setMinNum(MinYOffset.minY(targetvaluelist));

		ChartParameter cp1 = new ChartParameter();
		cp1.setChartTitle(title);
		cp1.setXlable(dimName);
		cp1.setYlable("");
		cp1.setWidth(Integer.parseInt(xoffset));
		cp1.setHeigth(Integer.parseInt(yoffset));
		cp1.setMinNum(MinYOffset.minY(targetvaluelistR));

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < targetvaluelist.length; i++) {
			createCategoryDataset(dataset, targetvaluelist[i], targetname[i],
					dimvaluelist);
		}

		DefaultCategoryDataset datasetR = new DefaultCategoryDataset();
		for (int i = 0; i < targetvaluelistR.length; i++) {
			createCategoryDataset(datasetR, targetvaluelistR[i],
					targetnameR[i], dimvaluelist);
		}

		JFreeChart chart = createJFreeChart2(dataset, datasetR, cp, cp1,
				charttype);
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
	private static void createCategoryDataset(DefaultCategoryDataset dataset,
			String target, String targetname, String dimList) {
		String[] dimInfo = dimList.split(",");
		String[] targetInfo = TargetAdept.adpetTarget(target, dimInfo.length);
		for (int i = 0; i < targetInfo.length; i++) {
			Double value = null;
			if (targetInfo[i] == null || targetInfo[i].equals("")) {
				value = new Double("0");
			} else {
				value = new Double(targetInfo[i]);
			}

			dataset.addValue(value, targetname, dimInfo[i]);
		}

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
	private static JFreeChart createJFreeChart2(CategoryDataset dataset1,
			CategoryDataset dataset2, ChartParameter chartpara1,
			ChartParameter chartpara2, String chartType) {
		if (chartType.equals("3D")) {
			return chartcreater.createCombined3DBarAndLineChart(dataset1,
					chartpara1, dataset2, chartpara2);
		} else {
			return chartcreater.createCombinedBarAndLineChart(dataset1,
					chartpara1, dataset2, chartpara2);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
