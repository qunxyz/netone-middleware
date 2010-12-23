package oe.bi.analysis;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.exceptions.NeedMoreThenForcastOneValueException;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


/**
 * Ԥ�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface BiForcast extends Remote{

	String _LOSE_TIME_SERIAL = "ȱ��ʱ��ά��,�޷�Ԥ��!";

	String _ONLY_ONE_TIMEDATA = "����Ԥ�������ʱ��ά��ֵ����1������!";

	String[][] FORACAST_ARITHMETIC = { { "auto", "�Զ�ѡ��Ԥ���㷨" },
			{ "SimpleExponentialSmoothingModel", "��ָ��ƽ��Ԥ��" },
			{ "DoubleExponentialSmoothingModel", "˫ָ��ƽ��Ԥ��" },
			{ "TripleExponentialSmoothingModel", "��ָ��ƽ��Ԥ��" },
			{ "RegressionModel", "���ͻع�ģ��Ԥ��" },
			{ "MultipleLinearRegressionModel", "�����ͻع�ģ��Ԥ��" },
			{ "PolynomialRegressionModel", "����ʽ�ع�ģ��Ԥ��" },
			{ "MovingAverageModel", "������ֵģ��Ԥ�ⷨ" },
			{ "NaiveForecastingModel", "��Ȼ��Ԥ�ⷨ" } };

	/**
	 * Ԥ�����
	 * 
	 * @param viewModel
	 *            ��������
	 * @param
	 * 
	 * map Ԥ�����޶�ϵ�� key:ָ��ID,value:�޶�ϵ��<br>
	 * 
	 *  ���������� �Զ��������������е�ָ���ά����Ϣ
	 * 
	 */
	ViewModel performBiForcast(ViewModel viewModel, GraphModel graphmodel,
			Map map) throws MoreThenOneDimensionViewModel,
			NeedMoreThenForcastOneValueException,RemoteException;

	String fetchTimeDimensionLink(ViewModel viewModel)throws RemoteException;

}
