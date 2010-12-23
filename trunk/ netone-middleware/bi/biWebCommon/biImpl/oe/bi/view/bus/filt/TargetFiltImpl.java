package oe.bi.view.bus.filt;

import java.util.Arrays;

import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.view.bus.filt.TargetFilt;
import oe.bi.view.bus.filt.util.CloneViewModel;
import oe.bi.view.bus.filt.util.FetchTargetColumnIndex;
import oe.bi.view.obj.ViewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ָ�����
 * 
 * @author wang-ting-jie
 * @author chen.jia.xun (Robanco)
 * 
 */
public class TargetFiltImpl implements TargetFilt {

	private static Log _log = LogFactory.getLog(TargetFiltImpl.class);

	/**
	 * 
	 * ָ�����
	 * 
	 * @param viewmodel
	 * @param targetElement
	 * @return
	 */
	public ViewModel filtvalue(ViewModel viewmodel, TargetFiltObj targetElement) {
		if (targetElement == null) {
			return viewmodel;
		}
		try {

			// ���ָ�����ڵ�λ������ֵ
			String id = targetElement.getTargetid();
			String[] targets = viewmodel.getTargetid();
			if (targets == null || targets.length == 0) {
				_log.warn("û��ѡ��ָ��");
				return viewmodel;
			}
			int indexValue = FetchTargetColumnIndex.fetchTargetColumnIndex(
					targets, id);
			if (indexValue == -1) {
				_log.warn("ѡ���ָ��:" + id + " ��Ч");
				return viewmodel;
			}
			// ������ͼ����
			ViewModel viewModeClone = new ViewModel();
			CloneViewModel.cloneViewModel(viewModeClone, viewmodel);

			// Ĭ��������
			ascValuesByTarget(targetElement, indexValue, viewModeClone);
			// ������
			if (targetElement._ORDER_DESC.equals(targetElement.getDesc())) {
				descValues(viewModeClone);
			}else if(targetElement._ORDER_ASC.equals(targetElement.getDesc())){
				
			} else if (targetElement.getTopn() != null
					&& !"".equals(targetElement.getTopn())) {// ����TopN
				if (targetElement.getBotn() != null
						&& !"".equals(targetElement.getBotn())) {
					_log.error("��ֵ����,������ͬʱ��TopN��BotN��ֵ,"
							+ targetElement.getTopn() + ","
							+ targetElement.getBotn());
					return viewmodel;
				}
				descValues(viewModeClone);
				setNValues(Integer.parseInt(targetElement.getTopn()),
						indexValue, viewModeClone);
			} else if (targetElement.getBotn() != null
					&& !"".equals(targetElement.getBotn())) {// ����BotN
				if (targetElement.getTopn() != null
						&& !"".equals(targetElement.getTopn())) {
					_log.error("��ֵ����,������ͬʱ��TopN��BotN��ֵ,"
							+ targetElement.getTopn() + ","
							+ targetElement.getBotn());
					return viewmodel;
				}

				setNValues(Integer.parseInt(targetElement.getBotn()),
						indexValue, viewModeClone);
			} else if (targetElement.getAlarm() != null) {// �澯����
				setAlarmValues(Double.parseDouble(targetElement.getAlarm()),
						indexValue, viewModeClone);
			}
			// ���ع��˹���ָ��
			return viewModeClone;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}

	}

	/**
	 * ��ȡ�澯���͵� ViewModel���� ����1: �ж϶�ά���������ȷ������,�Ƿ����targetElement.getTop()ֵ
	 * �������targetElement.getTop()ֵ,�򽫴��д��е�����ֵ������½��Ķ�ά����,�����½��Ķ�ά����
	 * 
	 * @param eleMentValue
	 * @param col
	 * @param viewmodel
	 * @return
	 */
	private void setNValues(int topn, int col, ViewModel viewmodelNew) {

		double[][] values = viewmodelNew.getTargetvalue();// ��ά
		String[][] dimensionvalues = viewmodelNew.getDimensionvalue();

		int topnReal = values.length > topn ? topn : values.length;

		double[][] valuesNew = new double[topnReal][values[0].length];
		String[][] dimensionvaluesNew = new String[topnReal][dimensionvalues[0].length];

		for (int i = 0; i < valuesNew.length; i++) {
			for (int j = 0; j < valuesNew[0].length; j++) {
				valuesNew[i][j] = values[i][j];
			}
			for (int j = 0; j < dimensionvalues[0].length; j++) {
				dimensionvaluesNew[i][j] = dimensionvalues[i][j];
			}
		}
		viewmodelNew.setTargetvalue(valuesNew);
		viewmodelNew.setDimensionvalue(dimensionvaluesNew);
	}

	private void setAlarmValues(double alarm, int col, ViewModel viewmodelNew) {
		double[][] values = viewmodelNew.getTargetvalue();// ��ά
		String[][] dimensionvalues = viewmodelNew.getDimensionvalue();
		int valuesYLength = values.length;
		int valuesXLength = values[0].length;

		int dimensionValueXLength = dimensionvalues[0].length;
		double[][] valueNew;
		String[][] dimensionvaluesNew;
		int indexAlarm = -1;
		double realAlarm = Math.abs(alarm);
		if (0 > alarm) {
			for (int i = values.length - 1; i >= 0; i--) {
				if (values[i][col] <= realAlarm) {
					indexAlarm = i;
					break;
				}
			}
		} else {
			for (int i = 0; i < values.length; i++) {
				if (values[i][col] >= realAlarm) {
					indexAlarm = i;
					break;
				}
			}
		}
		if (indexAlarm == -1) {
			viewmodelNew.setTargetvalue(new double[0][valuesXLength]);
			viewmodelNew
					.setDimensionvalue(new String[0][dimensionValueXLength]);
			return;
		}
		if (0 > alarm) {// ����ֵ
			indexAlarm = indexAlarm + 1;
			valueNew = new double[indexAlarm][valuesXLength];
			dimensionvaluesNew = new String[indexAlarm][dimensionValueXLength];
			for (int i = 0; i < indexAlarm; i++) {
				for (int k = 0; k < valuesXLength; k++) {
					valueNew[i][k] = values[i][k];
				}
				for (int k = 0; k < dimensionValueXLength; k++) {
					dimensionvaluesNew[i][k] = dimensionvalues[i][k];
				}
			}

		} else {// ����ֵ
			int total = valuesYLength - indexAlarm;
			valueNew = new double[total][valuesXLength];
			dimensionvaluesNew = new String[total][dimensionValueXLength];
			for (int i = 0; i < total; i++) {
				for (int k = 0; k < valuesXLength; k++) {
					valueNew[i][k] = values[indexAlarm + i][k];
				}
				for (int k = 0; k < dimensionValueXLength; k++) {
					dimensionvaluesNew[i][k] = dimensionvalues[indexAlarm + i][k];
				}
			}
		}
		viewmodelNew.setTargetvalue(valueNew);
		viewmodelNew.setDimensionvalue(dimensionvaluesNew);
	}

	/**
	 * ��ȡ�澯���͵� ViewModel���� ����1: ���ж϶�ά���������ȷ������,�Ƿ����targetElement.getBot()ֵ
	 * �������targetElement.getBot()ֵ,�򽫴��д��е�����ֵ������½��Ķ�ά����,�����½��Ķ�ά����
	 * 
	 * @param eleMentValue
	 * @param col
	 * @param viewmodel
	 * @return
	 */
	private ViewModel getBotNewValues(double eleMentValue, int col,
			ViewModel viewmodel) {

		double[][] values = viewmodel.getTargetvalue();// ��ά
		int row = 0;
		double[][] newValues;
		for (int i = 0; i < values.length; i++) {
			if (values[i][col] > eleMentValue) {
				row++;
			}
		}
		newValues = new double[row][values[0].length];
		int rowTemp = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i][col] > eleMentValue) {
				for (int j = 0; j < values[0].length; j++) {
					newValues[rowTemp][j] = values[i][j];
				}
				rowTemp++;
			}
		}
		viewmodel.setTargetvalue(newValues);
		return viewmodel;
	}

	/**
	 * 
	 * 
	 * @param eleMentValue
	 * @param col
	 * @param viewmodel
	 * @return
	 */
	private void ascValuesByTarget(TargetFiltObj targetElement, int col,
			ViewModel viewmodelNew) {
		double[][] values = viewmodelNew.getTargetvalue();
		String[][] diemsionValue = viewmodelNew.getDimensionvalue();

		double[] colValues = new double[values.length];
		double[][] newvalues = new double[values.length][values[0].length];
		String[][] diemsionValueNew = new String[diemsionValue.length][diemsionValue[0].length];
		// ��ȫ����Ԫ������
		for (int i = 0; i < values.length; i++) {
			colValues[i] = values[i][col];
		}
		Arrays.sort(colValues);
		// ����������ĳ��ָ�꣬��������ά�����������
		for (int i = 0; i < colValues.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (colValues[i] == values[j][col]) {
					for (int k = 0; k < values[0].length; k++) {// ���������µ�ָ��ֵ
						newvalues[i][k] = values[j][k];
					}
					for (int k = 0; k < diemsionValue[0].length; k++) {// ��������ά��ֵ
						diemsionValueNew[i][k] = diemsionValue[j][k];
					}
					values[j][col] = -999999999999d; // ����ֵ,��ֹ������ͬ��ֵ
					break;
				}
			}
		}

		viewmodelNew.setTargetvalue(newvalues);
		viewmodelNew.setDimensionvalue(diemsionValueNew);
	}

	/**
	 * 
	 * 
	 * @param eleMentValue
	 * @param col
	 * @param viewmodel
	 * @return
	 */
	private void descValues(ViewModel viewmodelNew) {
		double[][] values = viewmodelNew.getTargetvalue();// ��ά
		String[][] dimensionvalues = viewmodelNew.getDimensionvalue();
		int maxYLength = values[0].length;
		int maxXLength = values.length;
		double[][] valuesNew = new double[maxXLength][maxYLength];
		String[][] dimensionvaluesNew = new String[dimensionvalues.length][dimensionvalues[0].length];

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				valuesNew[maxXLength - i - 1][j] = values[i][j];
			}
			for (int j = 0; j < dimensionvalues[0].length; j++) {
				dimensionvaluesNew[dimensionvalues.length - i - 1][j] = dimensionvalues[i][j];
			}
		}

		viewmodelNew.setTargetvalue(valuesNew);
		viewmodelNew.setDimensionvalue(dimensionvaluesNew);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String id = targetElement.getTargetid();
		//
		// // ��ȡģ�� ���� һά
		// String[] targets = viewmodel.getTargetid();
		//
		// int col = 0;
		// // ����idֵȡ***��ֵ
		// for (int i = 0; i < targets.length; i++) {
		// if (targets[i].equals(id))
		// col = i;
		// }
		TargetFiltObj targetElement = new TargetFiltObj();
		targetElement.setTargetid("2"); // ��Ӧ�����getTargetidΪ��3��

		// targetElement.setAlarm("4"); //�澯ֵ
		// targetElement.setBotn("4"); //��ֵ
		// targetElement.setTopn("4"); //��ֵ
		// targetElement.setDesc(targetElement._ORDER_ASC); //��С����
		targetElement.setDesc(targetElement._ORDER_DESC); // �Ӵ�С
		ViewModel viewmodel = new ViewModel();
		String[] getTargetid = { "4", "3", "2", "1" };
		viewmodel.setTargetid(getTargetid);
		double[][] values = { { 1d, 2d, 3d }, { 3d, 4d, 5d }, { 5d, 6d, 7d } };
		viewmodel.setTargetvalue(values);

		TargetFiltImpl tfImpl = new TargetFiltImpl();
		ViewModel newviewmodel = tfImpl.filtvalue(viewmodel, targetElement);
		double[][] newvalues = newviewmodel.getTargetvalue();
		for (int i = 0; i < newvalues.length; i++) {
			for (int j = 0; j < newvalues[0].length; j++) {
				System.out.println("values=========" + newvalues[i][j]);
			}
		}

	}

}
