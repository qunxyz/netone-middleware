package oe.bi.analysis.forcastcore2.util;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.MultipleLinearRegressionModel;
import net.sourceforge.openforecast.models.PolynomialRegressionModel;
import net.sourceforge.openforecast.models.RegressionModel;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.DoubleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.TripleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.NaiveForecastingModel;

public class ForcastFactory {

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
