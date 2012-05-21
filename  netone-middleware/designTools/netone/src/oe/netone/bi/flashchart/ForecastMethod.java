package oe.netone.bi.flashchart;

import java.util.ArrayList;
import java.util.List;

import oe.netone.bi.ForcastTest;
import oe.netone.bi.PAnalysis;
import oe.netone.bi.xyData;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.translator.ASTranslator;

/**
 * 预测的方法的算法 xuwei（2012-2-14）
 * 
 * 
 * 
 */
public class ForecastMethod {

	public static List<xyData> ExpendValue(List<PAnalysis> analysisList,
			ArrayCollection etimEdatetimee, String suanfa) {
		List<xyData> list = new ArrayList<xyData>();
		ArrayList<PAnalysis> myObjectArray = new ArrayList();
		ArrayList<Object> timeArray = new ArrayList();
		ASObject aso = new ASObject();

		ASTranslator ast = new ASTranslator();
		PAnalysis per = null;

		for (int a = 0; a < analysisList.size(); a++) {

			PAnalysis objct = analysisList.get(a);
			myObjectArray.add(objct);
		}
		for (int b = 0; b < etimEdatetimee.size(); b++) {
			Object per1 = etimEdatetimee.get(b);
			timeArray.add(per1);

		}
		String[] dimtime = new String[myObjectArray.size()];
		double[] target = new double[myObjectArray.size()];
		String[] endtime = new String[timeArray.size()];
		for (int i = 0; i < myObjectArray.size(); i++) {
			dimtime[i] = (myObjectArray.get(i)).getDimdatetime();
			target[i] = (myObjectArray.get(i)).getTarget();
		}
		for (int j = 0; j < timeArray.size(); j++) {
			endtime[j] = timeArray.get(j).toString();
		}
		String forcastmode = suanfa;

		Double[] rs = ForcastTest.toForcast(dimtime, target, forcastmode,
				endtime);

		for (int i = 0; i < rs.length; i++) {
			xyData pAnalysis = new xyData();
			pAnalysis.setXdata(endtime[i]);
			pAnalysis.setYdata(rs[i].toString());
			list.add(pAnalysis);
		}

		return list;
	}

}
