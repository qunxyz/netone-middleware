package oe.bi.analysis.forcastcore;

import java.util.Map;

import oe.bi.analysis.forcastcore2.util.DealWithParamer;
import oe.bi.analysis.forcastcore2.util.FetchDimensionIndex;
import oe.bi.analysis.forcastcore2.util.FetchForcastValue;
import oe.bi.analysis.forcastcore2.util.ForcastFactory;
import oe.bi.analysis.forcastcore2.util.PreAnalysis;
import oe.bi.analysis.forcastcore2.util.ReBuildViewModel;
import oe.bi.dataModel.bus.util.FlitDimensionValue3;
import oe.bi.etl.bus.arithmetic.InvalidationLimitUpException;
import oe.bi.exceptions.NeedMoreThenForcastOneValueException;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.ForecastingModel;


/**
 * 预测分析
 * 
 * @author chen.jia.xun
 * 
 */
public class ForcastCore2 {

	public static void forcastCore2(ViewModel viewObj, GraphModel graph,
			Map mapmodifyvalue) throws NeedMoreThenForcastOneValueException {
		if (viewObj == null) {
			return;
		}
		double[][] xyvalyue = viewObj.getTargetvalue();
		int length = xyvalyue.length;
		if (length == 0 || length == 1) {
			return;
		}
		// 已有指标的维度值
		String forcastValue = graph.getXoffsetDimensionForcastValue();
		if (forcastValue == null || "".equals(forcastValue)) {
			return;// 没有预测的维度信息
		}
		String[] forcastDimensionValue = forcastValue.split(",");
		if (forcastDimensionValue == null || forcastDimensionValue.length == 0) {
			return;// 没有预测的维度信息
		}
		// 需要预测的指标的维度
		String forcastValueNext = graph.getXoffsetDimensionForcastValueNext();
		if (forcastValueNext == null || "".equals(forcastValueNext)) {
			return;// 没有预测的维度信息
		}
		String[] forcastDimensionValueNext = FlitDimensionValue3
				.filtDimensionValue(forcastValueNext);

		int choiceDimensionIndex = FetchDimensionIndex.fetchDimensionIndex(
				viewObj.getDimensionid(), graph);

		// 将所有需要分析的维度的值转换为数值类型
		double[] dimensionvalue = DealWithParamer.makeDimensionToRealvalue(
				viewObj.getDimensionvalue(), choiceDimensionIndex, viewObj
						.getDimensionType());

		// 根据已有的指标和维度信息，创建预测句柄
		double[][] target = viewObj.getTargetvalue();
		int targetColumnNumber = target[0].length;
		DataSet[] datasets = new DataSet[targetColumnNumber];
		for (int i = 0; i < targetColumnNumber; i++) {
			datasets[i] = new DataSet();
			PreAnalysis.makeDataset(dimensionvalue, target, i, datasets[i]);
		}
		ForecastingModel[] forecasters = new ForecastingModel[targetColumnNumber];
		for (int i = 0; i < forecasters.length; i++) {
			try {
				forecasters[i] = ForcastFactory.getForcastHandler(graph
						.getForcastArithmetic(), datasets[i]);
			} catch (Exception e) {
				throw new NeedMoreThenForcastOneValueException();
			}
		}

		// 将所有预测的维度的值转换为数值类型
		double[] dimensionvalueForcast = DealWithParamer
				.makeForcastDimensionToRealvalue(forcastDimensionValueNext,
						viewObj.getDimensionType()[choiceDimensionIndex]);

		// 将预测数据构造成可分析的数据集合
		DataSet[] datasetNext = new DataSet[targetColumnNumber];
		for (int i = 0; i < datasetNext.length; i++) {
			datasetNext[i] = new DataSet();
			PreAnalysis.makeDataset(dimensionvalueForcast, datasetNext[i]);
			forecasters[i].forecast(datasetNext[i]);
		}

		// 获得预测结果数据
		double[][] forcastTarget = FetchForcastValue.fetchFormcastValue(
				datasetNext, dimensionvalueForcast.length);

		// 修正预测结果数据  modify 2008-02-01 
		if (forcastTarget != null && forcastTarget.length > 0) {
			String[] targetid = viewObj.getTargetid();
			for (int i = 0; i < targetid.length; i++) {
				Double value = (Double) mapmodifyvalue.get(targetid[i]);
				if (value != null) {
					for (int j = 0; j < forcastTarget.length; j++) {
						forcastTarget[j][i] = forcastTarget[j][i]
								* value.doubleValue();
					}
				}
			}
		}

		// 重新再构建数据视图
		ReBuildViewModel.modifyViewObj(viewObj, forcastTarget,
				forcastDimensionValueNext, graph.getOtherDimension());
	}

	/**
	 * @param args
	 * @throws InvalidationLimitUpException
	 */
	public static void main(String[] args) throws InvalidationLimitUpException {

	}
}
