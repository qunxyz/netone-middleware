package oe.netone.bi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sybase.jdbc3.jdbc.Convert;

import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.translator.ASTranslator;

public class ExpendAnalysis {

	public static List ExpendValue(List analysisList,
			List etimEdatetimee, String suanfa) {
		List list = new ArrayList();
		ArrayList<PAnalysis> myObjectArray = new ArrayList();
		ArrayList<Object> timeArray = new ArrayList();
		ASObject aso = new ASObject();

		ASTranslator ast = new ASTranslator();
		PAnalysis per = null;

		for (int a = 0; a < analysisList.size(); a++) {

			Map map = (Map)analysisList.get(a);
			PAnalysis pAnalysis=new PAnalysis();

			pAnalysis.setDimdatetime((String)map.get("dimdatetime"));
			pAnalysis.setEnddatetime((String)map.get("enddatetime"));
			pAnalysis.setEndvalue(Double.valueOf(map.get("endvalue").toString()).doubleValue());
			pAnalysis.setTarget(Double.valueOf(map.get("target").toString()).doubleValue());
			myObjectArray.add(pAnalysis);
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

	private static Object String(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
