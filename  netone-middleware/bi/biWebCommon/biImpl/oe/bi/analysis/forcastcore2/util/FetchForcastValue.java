package oe.bi.analysis.forcastcore2.util;

import java.util.Iterator;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

public class FetchForcastValue {
	/**
	 * 获得预测到的指标值
	 * 
	 * @param datasetForcast
	 * @return
	 */
	public static double[][] fetchFormcastValue(DataSet[] datasetForcast,int length) {
		double[][] forcastValue = new double[length][datasetForcast.length];
		for (int i = 0; i < datasetForcast.length; i++) {
			DataSet dataSetpre = datasetForcast[i];
			int j=0;
			for (Iterator itr = dataSetpre.iterator(); itr.hasNext();) {
				DataPoint dataPoint = (DataPoint) itr.next();
				forcastValue[j++][i] = dataPoint.getDependentValue();
			}
		}

		return forcastValue;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
