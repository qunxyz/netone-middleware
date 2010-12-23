package oe.bi.analysis;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.exceptions.NeedMoreThenForcastOneValueException;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


/**
 * 预测操作
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface BiForcast extends Remote{

	String _LOSE_TIME_SERIAL = "缺少时间维度,无法预测!";

	String _ONLY_ONE_TIMEDATA = "用于预测分析的时间维度值必须1个以上!";

	String[][] FORACAST_ARITHMETIC = { { "auto", "自动选择预测算法" },
			{ "SimpleExponentialSmoothingModel", "单指数平滑预测" },
			{ "DoubleExponentialSmoothingModel", "双指数平滑预测" },
			{ "TripleExponentialSmoothingModel", "三指数平滑预测" },
			{ "RegressionModel", "线型回归模型预测" },
			{ "MultipleLinearRegressionModel", "多线型回归模型预测" },
			{ "PolynomialRegressionModel", "多项式回归模型预测" },
			{ "MovingAverageModel", "滑动均值模型预测法" },
			{ "NaiveForecastingModel", "自然数预测法" } };

	/**
	 * 预测操作
	 * 
	 * @param viewModel
	 *            数据载体
	 * @param
	 * 
	 * map 预测结果修订系数 key:指标ID,value:修订系数<br>
	 * 
	 *  功能描述： 自动根据数据载体中的指标和维度信息
	 * 
	 */
	ViewModel performBiForcast(ViewModel viewModel, GraphModel graphmodel,
			Map map) throws MoreThenOneDimensionViewModel,
			NeedMoreThenForcastOneValueException,RemoteException;

	String fetchTimeDimensionLink(ViewModel viewModel)throws RemoteException;

}
