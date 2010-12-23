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
 * Ԥ�����
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
		// ����ָ���ά��ֵ
		String forcastValue = graph.getXoffsetDimensionForcastValue();
		if (forcastValue == null || "".equals(forcastValue)) {
			return;// û��Ԥ���ά����Ϣ
		}
		String[] forcastDimensionValue = forcastValue.split(",");
		if (forcastDimensionValue == null || forcastDimensionValue.length == 0) {
			return;// û��Ԥ���ά����Ϣ
		}
		// ��ҪԤ���ָ���ά��
		String forcastValueNext = graph.getXoffsetDimensionForcastValueNext();
		if (forcastValueNext == null || "".equals(forcastValueNext)) {
			return;// û��Ԥ���ά����Ϣ
		}
		String[] forcastDimensionValueNext = FlitDimensionValue3
				.filtDimensionValue(forcastValueNext);

		int choiceDimensionIndex = FetchDimensionIndex.fetchDimensionIndex(
				viewObj.getDimensionid(), graph);

		// ��������Ҫ������ά�ȵ�ֵת��Ϊ��ֵ����
		double[] dimensionvalue = DealWithParamer.makeDimensionToRealvalue(
				viewObj.getDimensionvalue(), choiceDimensionIndex, viewObj
						.getDimensionType());

		// �������е�ָ���ά����Ϣ������Ԥ����
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

		// ������Ԥ���ά�ȵ�ֵת��Ϊ��ֵ����
		double[] dimensionvalueForcast = DealWithParamer
				.makeForcastDimensionToRealvalue(forcastDimensionValueNext,
						viewObj.getDimensionType()[choiceDimensionIndex]);

		// ��Ԥ�����ݹ���ɿɷ��������ݼ���
		DataSet[] datasetNext = new DataSet[targetColumnNumber];
		for (int i = 0; i < datasetNext.length; i++) {
			datasetNext[i] = new DataSet();
			PreAnalysis.makeDataset(dimensionvalueForcast, datasetNext[i]);
			forecasters[i].forecast(datasetNext[i]);
		}

		// ���Ԥ��������
		double[][] forcastTarget = FetchForcastValue.fetchFormcastValue(
				datasetNext, dimensionvalueForcast.length);

		// ����Ԥ��������  modify 2008-02-01 
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

		// �����ٹ���������ͼ
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
