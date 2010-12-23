package oe.bi.common.chart;

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
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;
import org.jfree.util.TableOrder;

public class ChartCreater {

	public JFreeChart createVerticalbar3DChart(CategoryDataset dataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D(chartpara.getChartTitle(), chartpara.getXlable(),
				chartpara.getYlable(), dataset, PlotOrientation.VERTICAL, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		setFont(categoryplot);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		double xqingxie = 1.1;
		try {
			if (chartpara.getXqingxie() != null && !"".equals(chartpara.getXqingxie())) {
				xqingxie = Double.parseDouble(chartpara.getXqingxie());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(xqingxie));

		if (chartpara.getMaxvalue() != 0) {
			categoryplot.getRangeAxis().setLowerBound(chartpara.getMaxvalue());
		}
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setItemLabelsVisible(chartpara.isShowvalue());

		BarRenderer3D barrenderer = (BarRenderer3D) categoryitemrenderer;

		barrenderer.setMaximumBarWidth(0.05D);

		return jfreechart;
	}

	public JFreeChart createVerticalbar2DChart(CategoryDataset dataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createBarChart(chartpara.getChartTitle(), chartpara.getXlable(), chartpara
				.getYlable(), dataset, PlotOrientation.VERTICAL, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		setFont(categoryplot);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		double xqingxie = 1.1;
		try {
			if (chartpara.getXqingxie() != null && !"".equals(chartpara.getXqingxie())) {
				xqingxie = Double.parseDouble(chartpara.getXqingxie());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(xqingxie));

		if (chartpara.getMaxvalue() != 0) {
			categoryplot.getRangeAxis().setLowerBound(chartpara.getMaxvalue());
		}
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setItemLabelsVisible(chartpara.isShowvalue());

		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;

		barrenderer.setMaximumBarWidth(0.05D);

		return jfreechart;
	}

	public JFreeChart createLineChart(CategoryDataset dataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createLineChart(chartpara.getChartTitle(), chartpara.getXlable(),
				chartpara.getYlable(), dataset, PlotOrientation.VERTICAL, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		double xqingxie = 0.3;
		try {
			if (chartpara.getXqingxie() != null && !"".equals(chartpara.getXqingxie())) {
				xqingxie = Double.parseDouble(chartpara.getXqingxie());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(xqingxie));

		if (chartpara.getMaxvalue() != 0) {
			categoryplot.getRangeAxis().setLowerBound(chartpara.getMaxvalue());
		}
		setFont(categoryplot);

		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setItemLabelsVisible(chartpara.isShowvalue());

		return jfreechart;
	}

	/**
	 * 创建柱线图
	 * 
	 * @param bardataset
	 * @param linedataset
	 * @param chartpara
	 * @return
	 */
	public JFreeChart createBarLineChart(CategoryDataset bardataset, CategoryDataset linedataset,
			ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createBarChart(chartpara.getChartTitle(), chartpara.getXlable(), chartpara
				.getYlable(), bardataset, PlotOrientation.VERTICAL, false, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		BarRenderer barrender = (BarRenderer) categoryplot.getRenderer();
		barrender.setMaximumBarWidth(0.05D);
		setFont(categoryplot);
		categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		categoryplot.setDataset(1, linedataset);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		double xqingxie = 0.3;
		try {
			if (chartpara.getXqingxie() != null && !"".equals(chartpara.getXqingxie())) {
				xqingxie = Double.parseDouble(chartpara.getXqingxie());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(xqingxie));

		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setItemLabelsVisible(chartpara.isShowvalue());

		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		categoryplot.setRenderer(1, lineandshaperenderer);
		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		LegendTitle legendtitle = new LegendTitle(categoryplot.getRenderer(0));
		legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle.setBorder(new BlockBorder());
		LegendTitle legendtitle1 = new LegendTitle(categoryplot.getRenderer(1));
		legendtitle1.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));
		legendtitle1.setBorder(new BlockBorder());
		BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
		blockcontainer.add(legendtitle, RectangleEdge.LEFT);
		blockcontainer.add(legendtitle1, RectangleEdge.RIGHT);
		blockcontainer.add(new EmptyBlock(2000D, 0.0D));
		CompositeTitle compositetitle = new CompositeTitle(blockcontainer);
		compositetitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(compositetitle);

		return jfreechart;
	}

	public JFreeChart createCombinedBarLineChart(CategoryDataset bardataset, CategoryDataset linedataset,
			ChartParameter chartpara) {

		NumberAxis numberaxis = new NumberAxis(chartpara.getYlable());
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
		lineandshaperenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryPlot categoryplot = new CategoryPlot(bardataset, null, numberaxis, lineandshaperenderer);
		categoryplot.setDomainGridlinesVisible(true);

		NumberAxis numberaxis1 = new NumberAxis(chartpara.getYlable());
		numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer barrenderer = new BarRenderer();
		barrenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		barrenderer.setMaximumBarWidth(0.05D);
		CategoryPlot categoryplot1 = new CategoryPlot(linedataset, null, numberaxis1, barrenderer);
		categoryplot1.setDomainGridlinesVisible(true);
		CategoryAxis categoryaxis = new CategoryAxis(chartpara.getXlable());
		CombinedDomainCategoryPlot combineddomaincategoryplot = new CombinedDomainCategoryPlot(categoryaxis);
		combineddomaincategoryplot.add(categoryplot, 2);
		combineddomaincategoryplot.add(categoryplot1, 1);
		JFreeChart jfreechart = new JFreeChart(chartpara.getChartTitle(), new Font("SansSerif", 1, 12),
				combineddomaincategoryplot, true);

		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		double xqingxie = 0.3;
		try {
			if (chartpara.getXqingxie() != null && !"".equals(chartpara.getXqingxie())) {
				xqingxie = Double.parseDouble(chartpara.getXqingxie());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		CategoryAxis axis = combineddomaincategoryplot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(xqingxie));

		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setItemLabelsVisible(chartpara.isShowvalue());

		setFont(categoryplot);
		setFont(categoryplot1);

		return jfreechart;

	}

	public JFreeChart createPieChart(PieDataset piedataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createPieChart(chartpara.getChartTitle(), piedataset, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PiePlot pieplot = (PiePlot) jfreechart.getPlot();
		pieplot.setLabelFont(new Font("SansSerif", 0, 12));
		pieplot.setNoDataMessage("No data available");
		pieplot.setCircular(false);
		pieplot.setLabelGap(0.02D);
		return jfreechart;
	}

	public JFreeChart createPieChart3D(PieDataset piedataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createPieChart3D(chartpara.getChartTitle(), piedataset, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PiePlot3D pieplot3d = (PiePlot3D) jfreechart.getPlot();
		pieplot3d.setStartAngle(270D);
		pieplot3d.setDirection(Rotation.ANTICLOCKWISE);
		pieplot3d.setForegroundAlpha(0.6F);
		pieplot3d.setInteriorGap(0.33000000000000002D);
		return jfreechart;
	}

	public JFreeChart createMultiplePieChart(CategoryDataset categorydataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createMultiplePieChart(chartpara.getChartTitle(), categorydataset,
				TableOrder.BY_ROW, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MultiplePiePlot multiplepieplot = (MultiplePiePlot) jfreechart.getPlot();
		JFreeChart jfreechart1 = multiplepieplot.getPieChart();
		PiePlot pieplot = (PiePlot) jfreechart1.getPlot();
		pieplot.setLabelFont(new Font("SansSerif", 0, 12));
		pieplot.setNoDataMessage("No data available");
		pieplot.setCircular(false);
		pieplot.setInteriorGap(0.29999999999999999D);
		return jfreechart;
	}
	
	public JFreeChart createMultiplePieChart3D(CategoryDataset categorydataset, ChartParameter chartpara) {
		JFreeChart jfreechart = ChartFactory.createMultiplePieChart3D(chartpara.getChartTitle(), categorydataset,
				TableOrder.BY_ROW, true, true, false);
		if (chartpara.getPiccolor() != null && !"".equals(chartpara.getPiccolor())) {
			try {
				String[] colors = chartpara.getPiccolor().split("#");
				Color col = new Color(Integer.parseInt(colors[colors.length - 1], 16));
				jfreechart.setBackgroundPaint(col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MultiplePiePlot multiplepieplot = (MultiplePiePlot) jfreechart.getPlot();
		JFreeChart jfreechart1 = multiplepieplot.getPieChart();
		PiePlot3D pieplot = (PiePlot3D) jfreechart1.getPlot();
		pieplot.setLabelFont(new Font("SansSerif", 0, 12));
		pieplot.setNoDataMessage("No data available");
		pieplot.setCircular(false);
		pieplot.setInteriorGap(0.33000000000000002D);
		return jfreechart;
	}

	public void setFont(CategoryPlot categoryplot) {
		Font f1 = new Font("宋体", Font.BOLD, 14);
		Font f2 = new Font("宋体", Font.BOLD, 12);
		int xcount = categoryplot.getDomainAxisCount();
		for (int i = 0; i < xcount; i++) {
			categoryplot.getDomainAxis(i).setLabelFont(f1);
			categoryplot.getDomainAxis(i).setTickLabelFont(f2);
		}
		int count = categoryplot.getRangeAxisCount();
		for (int i = 0; i < count; i++) {
			categoryplot.getRangeAxis(i).setLabelFont(f1);
		}

	}

	public static void main(String[] args) {
		String str = "FFFFFF";
		String[] col = str.split("#");
		System.out.println(col[col.length - 1]);
	}

}
