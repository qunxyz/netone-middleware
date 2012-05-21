package oe.netone.bi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.DoubleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.MultipleLinearRegressionModel;
import net.sourceforge.openforecast.models.NaiveForecastingModel;
import net.sourceforge.openforecast.models.PolynomialRegressionModel;
import net.sourceforge.openforecast.models.RegressionModel;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.TripleExponentialSmoothingModel;

public class ForcastTest {
	static String _RULE_TIMESTAMP = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})\\s+(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})$";

	static String _RULE_DAY = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})$";

	static String _RULE_HOUR = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})\\s+(\\d{2})$";

	static String _RULE_MIN = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})\\s+(\\d{2}):(\\d{2})$";

	static String _RULE_SEC = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})\\s+(\\d{2}):(\\d{2}):(\\d{2})$";

	static String _RULE_YEAR = "^(\\d{4})$";

	static String _RULE_MONTH = "^(\\d{4})\\-(\\d{2})$";

	private static String _FIRST_DAY = "-01 00:00:00.000";

	private static String _FIRST_HOUR = " 00:00:00.000";

	private static String _FIRST_MIN = ":00:00.000";

	private static String _FIRST_SEC = ":00.000";

	private static String _PREVIEW_YEAR_LAST_TIME = "-12-31 23:59:59.999";

	public static Double[] toForcast(String[] timeinfo, double[] target,
			String forcastmode, String[] dimtimeFurther) {

		// 需要将时间处理成数值类型
		double[] dimensionvalue = new double[timeinfo.length];
		for (int i = 0; i < timeinfo.length; i++) {
			String dimensionvaluex = repairedTimeValue(timeinfo[i]);
			dimensionvalue[i] = Timestamp.valueOf(dimensionvaluex).getTime();

		}

		DataSet ds = new DataSet();

		makeDataset(dimensionvalue, target, ds);

		ForecastingModel forecasters = getForcastHandler(forcastmode, ds);

		DataSet datasetNext = new DataSet();

		// 需要将时间处理成数值类型
		double[] dimensionvalueFurther = new double[dimtimeFurther.length];
		for (int i = 0; i < dimtimeFurther.length; i++) {
			String dimensionvaluex = repairedTimeValue(dimtimeFurther[i]);
			dimensionvalueFurther[i] = Timestamp.valueOf(dimensionvaluex)
					.getTime();

		}

		makeDataset(dimensionvalueFurther, datasetNext);
		forecasters.forecast(datasetNext);

		List list = new ArrayList();
		for (Iterator itr = datasetNext.iterator(); itr.hasNext();) {
			DataPoint dataPoint = (DataPoint) itr.next();
			list.add(dataPoint.getDependentValue());

		}
		return (Double[]) list.toArray(new Double[0]);

	}

	public static ForecastingModel getForcastHandler(String forcastType,
			DataSet dataSet) {
		try {
			if ("MovingAverageModel".equals(forcastType)) {
				MovingAverageModel model = new MovingAverageModel();
				model.init(dataSet);
				return model;
			}
			if ("MultipleLinearRegressionModel".equals(forcastType)) {
				MultipleLinearRegressionModel model = new MultipleLinearRegressionModel();
				model.init(dataSet);
				return model;
			}
			if ("PolynomialRegressionModel".equals(forcastType)) {
				PolynomialRegressionModel model = new PolynomialRegressionModel(
						"x");
				model.init(dataSet);
				return model;
			}
			if ("RegressionModel".equals(forcastType)) {
				RegressionModel model = new RegressionModel("x");
				model.init(dataSet);
				return model;
			}
			if ("SimpleExponentialSmoothingModel".equals(forcastType)) {
				return SimpleExponentialSmoothingModel.getBestFitModel(dataSet);
			}
			if ("DoubleExponentialSmoothingModel".equals(forcastType)) {
				return DoubleExponentialSmoothingModel.getBestFitModel(dataSet);
			}
			if ("TripleExponentialSmoothingModel".equals(forcastType)) {
				return TripleExponentialSmoothingModel.getBestFitModel(dataSet);
			}
			if ("NaiveForecastingModel".equals(forcastType)) {
				NaiveForecastingModel model = new NaiveForecastingModel();
				model.init(dataSet);
				return model;
			}
		} catch (Exception e) {
			return Forecaster.getBestForecast(dataSet);
		}
		return Forecaster.getBestForecast(dataSet);

	}
   public static String repairedTimeValue(String dimensionvalue) {
		if (dimensionvalue.matches(_RULE_YEAR)) {
			// 将本年构造成上一年的最后一瞬时
			int value = Integer.valueOf(dimensionvalue).intValue() - 1;
			return value + _PREVIEW_YEAR_LAST_TIME;
		} else if (dimensionvalue.matches(_RULE_MONTH)) {
			// 将本月构造成本月第一天的最早时刻
			return dimensionvalue + _FIRST_DAY;
		} else if (dimensionvalue.matches(_RULE_DAY)) {
			// 将本日构造成本日的最早时刻
			return dimensionvalue + _FIRST_HOUR;
		} else if (dimensionvalue.matches(_RULE_HOUR)) {
			// 将本日构造成本时最早时刻
			return dimensionvalue + _FIRST_MIN;
		} else if (dimensionvalue.matches(_RULE_MIN)) {
			// 将本日构造成本分最早时刻
			return dimensionvalue + _FIRST_SEC;
		}
		return dimensionvalue;

	}

	public static void makeDataset(double[] dimensionvalue,
			double[] targetvalue, DataSet dataset) {
		for (int i = 0; i < targetvalue.length; i++) {
			DataPoint dp = new Observation(targetvalue[i]);
			dp.setIndependentValue("x", dimensionvalue[i]);
			dataset.add(dp);
		}
	}
public static void makeDataset(double[] dimensionvalue, DataSet dataset) {
		for (int i = 0; i < dimensionvalue.length; i++) {
			DataPoint dp = new Observation(0.0);
			dp.setIndependentValue("x", dimensionvalue[i]);
			dataset.add(dp);
		}
	}
}
