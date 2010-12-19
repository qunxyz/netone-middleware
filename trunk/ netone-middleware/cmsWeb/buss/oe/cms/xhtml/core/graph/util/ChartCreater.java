package oe.cms.xhtml.core.graph.util;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;


/**
 * 生成图表工具类
 * 
 * @author hls
 * 
 */
public class ChartCreater {

	public static final String CHART_Verticalbar3D = "Verticalbar3D";

	public static final String CHART_Line = "Line";

	public static final String CHART_BarLine = "BarLine";

	public static final String CHART_CombinedBarLin = "CombinedBarLin";

	public static final String[][] CHAR_TIP = { { "立体柱图", "Verticalbar3D" },
			{ "线图", "Line" }, { "柱线", "BarLine" }, { "柱线对比", "CombinedBarLin" } };

	private static ChartCreater chartcreater = new ChartCreater();

	private ChartCreater() {

	}

	public static ChartCreater getInstance() {
		return chartcreater;
	}

	/**
	 * 生成3D柱图
	 * 
	 * @param dataset
	 * @param chartpara
	 * @return
	 */
	public JFreeChart createVerticalbar3DChart(CategoryDataset dataset,
			ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D(chartpara
				.getChartTitle(), chartpara.getXlable(), chartpara.getYlable(),
				dataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();

		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createDownRotationLabelPositions(0.3));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		this.setMaxYOffsetLimit(chartpara, categoryplot);
		categoryitemrenderer.setItemLabelsVisible(true);
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
		barrenderer.setMaximumBarWidth(0.050000000000000003D);
		return jfreechart;
	}

	/**
	 * 生成线条
	 * 
	 * @param dataset
	 * @param chartpara
	 * @return
	 */
	public JFreeChart createLineChart(CategoryDataset dataset,
			ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createLineChart(chartpara
				.getChartTitle(), chartpara.getXlable(), chartpara.getYlable(),
				dataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();

		this.setMaxYOffsetLimit(chartpara, categoryplot);
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createDownRotationLabelPositions(0.3));
		// ---- 在线条上加点------------
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setShapesVisible(true);
		// ---------------------------
		return jfreechart;
	}

	private void setMaxYOffsetLimit(ChartParameter chartpara,
			CategoryPlot categoryplot) {
		String minNumStr1 = chartpara.getMinNum();
		// 设置纵坐标的最小值
		if (minNumStr1 == null || minNumStr1.equals(""))
			minNumStr1 = "0";
		categoryplot.getRangeAxis().setLowerBound(
				Double.parseDouble(minNumStr1));
	}

	/**
	 * 生成柱线图
	 * 
	 * @param bardataset
	 * @param linedataset
	 * @param chartpara
	 * @return
	 */
	public JFreeChart createBarLineChart(CategoryDataset bardataset,
			CategoryDataset linedataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createBarChart(chartpara
				.getChartTitle(), chartpara.getXlable(), chartpara.getYlable(),
				bardataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		this.setMaxYOffsetLimit(chartpara, categoryplot);
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		categoryplot.setDataset(1, linedataset);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createDownRotationLabelPositions(0.3));

		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		categoryplot.setRenderer(1, lineandshaperenderer);

		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		LegendTitle legendtitle = new LegendTitle(categoryplot.getRenderer(0));
		legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle.setBorder(new BlockBorder());
		LegendTitle legendtitle1 = new LegendTitle(categoryplot.getRenderer(1));
		legendtitle1.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle1.setBorder(new BlockBorder());
		BlockContainer blockcontainer = new BlockContainer(
				new BorderArrangement());
		blockcontainer.add(legendtitle, RectangleEdge.LEFT);
		blockcontainer.add(legendtitle1, RectangleEdge.RIGHT);
		blockcontainer.add(new EmptyBlock(2000D, 0.0D));
		CompositeTitle compositetitle = new CompositeTitle(blockcontainer);
		compositetitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(compositetitle);
		return jfreechart;
	}

	/**
	 * 生成柱线对比图
	 * 
	 * @param bardataset
	 * @param linedataset
	 * @param chartpara
	 * @return
	 */
	public JFreeChart createCombinedBarLineChart(CategoryDataset bardataset,
			CategoryDataset linedataset, ChartParameter chartpara) {

		NumberAxis numberaxis = new NumberAxis(chartpara.getYlable());
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		lineandshaperenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryPlot categoryplot = new CategoryPlot(bardataset, null,
				numberaxis, lineandshaperenderer);


		this.setMaxYOffsetLimit(chartpara, categoryplot);

		NumberAxis numberaxis1 = new NumberAxis(chartpara.getYlable());
		numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer barrenderer = new BarRenderer();
		barrenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryPlot categoryplot1 = new CategoryPlot(linedataset, null,
				numberaxis1, barrenderer);
		categoryplot1.setDomainGridlinesVisible(true);
		CategoryAxis categoryaxis = new CategoryAxis(chartpara.getXlable());
		CombinedDomainCategoryPlot combineddomaincategoryplot = new CombinedDomainCategoryPlot(
				categoryaxis);
		combineddomaincategoryplot.add(categoryplot, 2);
		combineddomaincategoryplot.add(categoryplot1, 1);
		JFreeChart jfreechart = new JFreeChart(chartpara.getChartTitle(),
				new Font("SansSerif", 1, 12), combineddomaincategoryplot, true);

		CategoryAxis axis = combineddomaincategoryplot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions
				.createDownRotationLabelPositions(0.3));

		return jfreechart;
	}

	/**
	 * 
	 * @param bardataset
	 *            柱图
	 * @param chartpara
	 *            柱图参数
	 * @param linedataset
	 *            线图
	 * @param chartpara2
	 *            线图参数
	 * @return
	 */
	public JFreeChart createCombinedBarAndLineChart(CategoryDataset bardataset,
			ChartParameter chartpara, CategoryDataset linedataset,
			ChartParameter chartpara2) {
		JFreeChart jfreechart = ChartFactory.createBarChart(chartpara
				.getChartTitle(), chartpara.getXlable(), chartpara.getYlable(),
				bardataset, PlotOrientation.VERTICAL, false, false, true);
		return createCombinedBarLineChart2(bardataset, chartpara, linedataset,
				chartpara2, jfreechart);
	}

	/**
	 * 
	 * @param bardataset
	 *            3d 柱图
	 * @param chartpara
	 *            柱图参数
	 * @param linedataset
	 *            线图
	 * @param chartpara2
	 *            线图参数
	 * @return
	 */
	public JFreeChart createCombined3DBarAndLineChart(
			CategoryDataset bardataset, ChartParameter chartpara,
			CategoryDataset linedataset, ChartParameter chartpara2) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D(chartpara
				.getChartTitle(), chartpara.getXlable(), chartpara.getYlable(),
				bardataset, PlotOrientation.VERTICAL, false, false, true);

		return createCombinedBarLineChart2(bardataset, chartpara, linedataset,
				chartpara2, jfreechart);
	}

	/**
	 * 
	 * @param bardataset
	 *            柱图
	 * @param chartpara
	 *            柱图参数
	 * @param linedataset
	 *            线图
	 * @param chartpara2
	 *            线图参数
	 * @return
	 */
	private JFreeChart createCombinedBarLineChart2(CategoryDataset bardataset,
			ChartParameter chartpara, CategoryDataset linedataset,
			ChartParameter chartpara2, JFreeChart jfreechart) {

		// jfreechart.setBackgroundPaint(Color.white);
		jfreechart.setBackgroundPaint(new Color(238, 246, 254));
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(new Color(238, 238, 255));
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		// 设置另一个纵坐标
		categoryplot.setDataset(1, linedataset);
		categoryplot.mapDatasetToRangeAxis(1, 1);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
	
		// ////////////////
		this.setMaxYOffsetLimit(chartpara, categoryplot);
		// ///////////
		NumberAxis numberaxis = new NumberAxis(chartpara2.getYlable());
		categoryplot.setRangeAxis(1, numberaxis);
		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		lineandshaperenderer
				.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		categoryplot.setRenderer(1, lineandshaperenderer);
		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// ////双轴信息/////////////
		LegendTitle legendtitle = new LegendTitle(categoryplot.getRenderer(0));
		legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle.setBorder(new BlockBorder());
		LegendTitle legendtitle1 = new LegendTitle(categoryplot.getRenderer(1));
		legendtitle1.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle1.setBorder(new BlockBorder());
		BlockContainer blockcontainer = new BlockContainer(
				new BorderArrangement());
		blockcontainer.add(legendtitle, RectangleEdge.LEFT);
		blockcontainer.add(legendtitle1, RectangleEdge.RIGHT);
		blockcontainer.add(new EmptyBlock(2000D, 0.0D));
		CompositeTitle compositetitle = new CompositeTitle(blockcontainer);
		compositetitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(compositetitle);
		// //// 双轴信息/////////////
		return jfreechart;
	}

}
