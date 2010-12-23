package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.etl.obj.TargetElement;


public class FetchAlarmInfo {

	public static double[] fetchAlarmInfo(List targetList, DataModel dtamodel) {
		double[] alarminfo = new double[targetList.size()];
		int i = 0;
		for (Iterator itr = targetList.iterator(); itr.hasNext();) {
			TargetElement tarEle = (TargetElement) itr.next();
			String targetId = tarEle.getId();
			Map map = dtamodel.getTargetColumns();
			TargetColumn targetColumn = (TargetColumn) map.get(targetId);
			String alarm = targetColumn.getAlarm();
			if (alarm != null && !alarm.trim().equals("")) {
				try {
					alarminfo[i++] = Double.valueOf(alarm).doubleValue();
				} catch (Exception e) {
					System.out.println("无效的告警值："+alarm);
					e.printStackTrace();
				}
			}
		}
		return alarminfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
