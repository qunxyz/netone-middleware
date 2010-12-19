package oe.midware.workflow.track.mode;

import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

public class WfTrackTable {

	static String _TD = "<td>";

	static String _TDE = "</td>";

	static String _TR_Head = "<tr bgcolor='#CCCCFF'>";
	
	static String _TR = "<tr>";

	static String _TRE = "</tr>";

	static String _TABLE = "<table width=\"80%\"  border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"blue\" align=\"left\">";

	static String _TABLEE = "</table>";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String abc="01";
		// System.out.println(Integer.parseInt(abc));

	}

	public static String trackTable(Activity[] activityObj, List worklist) {
		StringBuffer buf = new StringBuffer();
		for (Iterator itr = worklist.iterator(); itr.hasNext();) {
			TWfWorklist worklistPre = (TWfWorklist) itr.next();
			String tablecol = makeTableRowCol(worklistPre, activityObj);
			buf.append(tablecol);
		}

		return makeTableInfo(buf.toString());
	}

	private static String makeTableInfo(String str) {
		String headCol = _TD + "开始时间" + _TDE  + _TD
				+ "节点名" + _TDE + _TD + "结束时间" + _TDE + _TD + "执行状态" + _TDE;

		String headRow = _TR_Head + headCol + _TRE;
		return _TABLE + headRow + str + _TABLEE;

	}

	private static String makeTableRowCol(TWfWorklist worklistPre,
			Activity[] activityObj) {
		// 活动ID
		String activityid = worklistPre.getActivityid();
		// 活动名字
		String activityname = null;
		for (int i = 0; i < activityObj.length; i++) {
			Activity act = activityObj[i];
			if (activityid.equals(act.getId())) {
				activityname = act.getName();
			}
		}
		// 开始时间
		String starttime = worklistPre.getStarttime().substring(0, 19);
		// 结束时间
		String endtime = worklistPre.getDonetime();
		endtime = endtime != null && endtime.length() > 19 ? endtime.substring(
				0, 19) : "-";
		// 活动状态
		String status = worklistPre.getExecutestatus();
		int statusValue = Integer.parseInt(status) - 1;
		status = RuntimeWorklistRef.STATUSLIST[statusValue][1];

		String rowCol = _TD + starttime + _TDE + _TD
				+ activityname + _TDE + _TD + endtime + _TDE + _TD + status
				+ _TDE;
		return _TR + rowCol + _TRE;

	}

}
