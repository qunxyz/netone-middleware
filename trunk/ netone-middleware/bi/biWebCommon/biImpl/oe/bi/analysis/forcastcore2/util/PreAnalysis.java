package oe.bi.analysis.forcastcore2.util;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

import net.sourceforge.openforecast.Observation;

public class PreAnalysis {

	private static double UNKNOW_TRAGET = 0.0;

	public static void makeDataset(double[] dimensionvalue,
			double[][] targetvalue, int forcastindex, DataSet dataset) {
		for (int i = 0; i < targetvalue.length; i++) {
			DataPoint dp = new Observation(targetvalue[i][forcastindex]);
			dp.setIndependentValue("x",
							dimensionvalue[i]);
			dataset.add(dp);
		}
	}

	public static void makeDataset(double[] dimensionvalue, DataSet dataset) {
		for (int i = 0; i < dimensionvalue.length; i++) {
			DataPoint dp = new Observation(UNKNOW_TRAGET);
			dp.setIndependentValue("x",
							dimensionvalue[i]);
			dataset.add(dp);
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
