package oe.bi.web.etl.chart;

import oe.bi.common.chart.ChartCreater;
import oe.bi.common.chart.ChartParameter;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


public class EtlChart {

	static Log log = LogFactory.getLog(EtlChart.class);

	private ChartCreater provider = new ChartCreater();

	private ViewModel viewModel;

	private ChartParameter chartparam;

	private GraphModel graphModel;

	private JFreeChart jfreeChart;

	private String targetId;

	public EtlChart(ViewModel viewModel, GraphModel graphModel) {
		this(viewModel, null, graphModel, null);
	}

	public EtlChart(ViewModel viewModel, GraphModel graphModel, String targetId) {
		this(viewModel, null, graphModel, targetId);
	}

	public EtlChart(ViewModel viewModel, ChartParameter chartparam, GraphModel graphModel, String targetId) {
		this.viewModel = viewModel;
		this.chartparam = chartparam;
		this.graphModel = graphModel;
		this.targetId = targetId;

		setNeTimeChart();

		if (chartparam == null) {
			if (targetId == null) {
				this.chartparam = getDefaultChartParameter();
			} else {
				this.chartparam = getMultiChartParameter();
			}
		}

	}

	public void setPicWidth(int width) {
		if (width != 0) {
			this.chartparam.setWidth(width);
		}
	}

	public void setMaxValue(int maxvalue) {
		if (maxvalue != 0) {
			this.chartparam.setMaxvalue(maxvalue);
		}
	}

	public void setShowvalue(String showvalue) {
		boolean b = new Boolean(showvalue).booleanValue();
		this.chartparam.setShowvalue(b);
	}

	public void setPictitle(String pictitle) {
		if (pictitle != null && !"".equals(pictitle)) {
			this.chartparam.setChartTitle(pictitle);
		}
	}

	public void setPiccolor(String piccolor) {
		if (StringUtils.isEmpty(piccolor)) {
			piccolor = "#FFFFFF";
		}
		this.chartparam.setPiccolor(piccolor);
	}

	public void setXqingxie(String xqingxie) {
		this.chartparam.setXqingxie(xqingxie);
	}

	private void setNeTimeChart() {
		if (graphModel.getGraphType().endsWith("ne-time")) {
			graphModel.setXOffsetDimension("name_en");
		}
		if (graphModel.getGraphType().endsWith("time-ne")) {
			graphModel.setXOffsetDimension("start_time");
		}
	}

	public CategoryDataset createCategoryDataset() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// 图表多指标字段
		String[] tgids = viewModel.getTargetid();
		String[] tgfield = viewModel.getTargetname();
		double[][] targetValue = viewModel.getTargetvalue();
		String[][] dimValue = (String[][]) viewModel.getDimensionvalue();

		if (graphModel.getGraphType().endsWith("ne-time")) {
			int neindex = 0;
			int timeindex = 1;

			String[] dimids = viewModel.getDimensionid();
			for (int i = 0; i < dimids.length; i++) {
				if (dimids[i].equals("name_en")) {
					neindex = i;
				}
				if (dimids[i].equals("start_time")) {
					timeindex = i;
				}
			}
			for (int i = 0; i < targetValue.length; i++) {
				for (int j = 0; j < tgfield.length; j++) {
					if (targetId != null) {
						if (targetId.equals(tgids[j])) {
							dataset.addValue(targetValue[i][j], dimValue[i][timeindex], dimValue[i][neindex]);
						}
					}
				}
			}
		} else if (graphModel.getGraphType().endsWith("time-ne")) {
			int neindex = 0;
			int timeindex = 1;

			String[] dimids = viewModel.getDimensionid();
			for (int i = 0; i < dimids.length; i++) {
				if (dimids[i].equals("name_en")) {
					neindex = i;
				}
				if (dimids[i].equals("start_time")) {
					timeindex = i;
				}
			}

			for (int i = 0; i < targetValue.length; i++) {
				for (int j = 0; j < tgfield.length; j++) {
					if (targetId != null) {
						if (targetId.equals(tgids[j])) {
							dataset.addValue(targetValue[i][j], dimValue[i][neindex], dimValue[i][timeindex]);
						}
					}
				}
			}
		} else {
			int dimindex = getOffsetDimensionIndex();
			// 图表数据填充
			for (int i = 0; i < targetValue.length; i++) {
				for (int j = 0; j < tgfield.length; j++) {
					if (targetId != null) {
						if (targetId.equals(tgids[j])) {
							// log.debug(targetValue[i][j]+","+tgfield[j]+","+dimValue[i][dimindex]);
							dataset.addValue(targetValue[i][j], tgfield[j], dimValue[i][dimindex]);
						}
					} else {
						// log.debug(targetValue[i][j]+","+tgfield[j]+","+dimValue[i][dimindex]);
						dataset.addValue(targetValue[i][j], tgfield[j], dimValue[i][dimindex]);
					}
				}
			}
		}

		return dataset;
	}

	public PieDataset createPieDataset() {
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();

		int dimindex = getOffsetDimensionIndex();

		// 图表多指标字段
		double[][] targetValue = viewModel.getTargetvalue();
		String[][] dimValue = (String[][]) viewModel.getDimensionvalue();

		for (int i = 0; i < targetValue.length; i++) {
			defaultpiedataset.setValue(dimValue[i][dimindex], targetValue[i][0]);
		}

		return defaultpiedataset;
	}

	private int getOffsetDimensionIndex() {
		int dimindex = -1;
		// 设置选择的维度
		String[] dimids = viewModel.getDimensionid();
		for (int i = 0; i < dimids.length; i++) {
			if (dimids[i].equals(graphModel.getXOffsetDimension())) {
				dimindex = i;
				break;
			}
		}
		return dimindex;
	}

	private int getTargetIdIndex() {
		int tgindex = -1;
		String[] tgids = viewModel.getTargetid();
		for (int i = 0; i < tgids.length; i++) {
			if (tgids[i].equals(targetId)) {
				tgindex = i;
				break;
			}
		}
		return tgindex;
	}

	private ChartParameter getDefaultChartParameter() {
		int dimindex = getOffsetDimensionIndex();
		ChartParameter cp = new ChartParameter();
		cp.setChartTitle("");
		cp.setXlable(viewModel.getDimensionname()[dimindex]);
		cp.setYlable("");
		cp.setWidth(600);
		cp.setHeigth(400);
		cp.setShowvalue(false);
		return cp;
	}

	private ChartParameter getMultiChartParameter() {
		int dimindex = getOffsetDimensionIndex();
		int tgindex = getTargetIdIndex();
		String tgname = viewModel.getTargetname()[tgindex];
		ChartParameter cp = new ChartParameter();
		cp.setChartTitle(tgname + "");
		cp.setXlable(viewModel.getDimensionname()[dimindex]);
		cp.setYlable(tgname);
		cp.setWidth(600);
		cp.setHeigth(350);
		return cp;
	}

	private void setJfreeChart() {

		CategoryDataset categoryDataset = null;
		PieDataset piedateset = null;

		String charttype = graphModel.getGraphType();
		int index = charttype.indexOf("$");
		if (index != -1) {
			charttype = charttype.substring(0, index);
		}

		if (charttype.equals("pie") || charttype.equals("pie3D")) {
			if (graphModel.getGraphType().endsWith("default")) {
				piedateset = createPieDataset();
			} else {
				categoryDataset = createCategoryDataset();
			}
		} else {
			categoryDataset = createCategoryDataset();
		}

		if (charttype.equals("verticalbar3D")) {
			jfreeChart = provider.createVerticalbar3DChart(categoryDataset, chartparam);
		} else if (charttype.equals("verticalbar2D")) {
			jfreeChart = provider.createVerticalbar2DChart(categoryDataset, chartparam);
		} else if (charttype.equals("line")) {
			jfreeChart = provider.createLineChart(categoryDataset, chartparam);
		} else if (charttype.equals("verticalbarLine")) {
			jfreeChart = provider.createBarLineChart(categoryDataset, categoryDataset, chartparam);
		} else if (charttype.equals("verticalbarLinec")) {
			jfreeChart = provider.createCombinedBarLineChart(categoryDataset, categoryDataset, chartparam);
		} else if (charttype.equals("pie")) {
			if (graphModel.getGraphType().endsWith("default")) {
				jfreeChart = provider.createPieChart(piedateset, chartparam);
			} else {
				jfreeChart = provider.createMultiplePieChart(categoryDataset, chartparam);
			}
		} else if (charttype.equals("pie3D")) {
			if (graphModel.getGraphType().endsWith("default")) {
				jfreeChart = provider.createPieChart3D(piedateset, chartparam);
			} else {
				jfreeChart = provider.createMultiplePieChart3D(categoryDataset, chartparam);
			}
		}
	}

	public JFreeChart getJfreeChart() {
		setJfreeChart();
		return jfreeChart;
	}

	public ChartParameter getChartparam() {
		return chartparam;
	}

}
