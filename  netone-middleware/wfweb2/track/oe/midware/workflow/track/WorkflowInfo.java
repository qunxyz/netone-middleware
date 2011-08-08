/*
 * 创建日期 2006-3-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package oe.midware.workflow.track;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.track.mode.WfActivity;
import oe.midware.workflow.track.mode.WfControl;
import oe.midware.workflow.track.mode.WfIcon;
import oe.midware.workflow.track.mode.WfLine;

import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * @author robanco
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class WorkflowInfo {
	/**
	 * 获得流程轨迹
	 * 
	 * @param proc
	 * @param runtime
	 * @return
	 */
	public static String wfTrack(WorkflowProcess proc, TWfRuntime runtime) {
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");

		List worklist=null;
		try {
			worklist = runtime == null ? new ArrayList() : runtimemonitor
					.fetchWorklist(runtime.getRuntimeid());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer activityStr = new StringBuffer();// 流程的活动
		StringBuffer lineStr = new StringBuffer();// 流程连接线
		Activity[] activitys = proc.getActivity();
		String runtimeId = (runtime == null ? null : runtime.getRuntimeid());
		float maxYoffset = 0f;
		int length = activitys.length;
		for (int i = 0; i < length; i++) {
			String[] activityinfo = WfActivity.activityInfo(activitys[i],
					worklist, runtimeId);
			activityStr.append(activityinfo[0]);
			maxYoffset = maxYoffset(activityinfo[1], maxYoffset);
			String lineInfo = WfLine.lineInfo(activitys[i]);
			lineStr.append(lineInfo);
		}
		try {
			List startpoint = proc.getExtendedAttributes().getList(
					"StartOfWorkflow");
			String[] startinfo = dealWithMarkPoint(startpoint, "start", proc);
			String[] startYoffsetAll = startinfo[2].split(",");
			for (int i = 0; i < startYoffsetAll.length; i++) {
				maxYoffset = maxYoffset(startYoffsetAll[i], maxYoffset);
			}
			List endpoint = proc.getExtendedAttributes().getList(
					"EndOfWorkflow");
			String[] endinfo = dealWithMarkPoint(endpoint, "end", proc);
			String[] endYoffsetAll = endinfo[2].split(",");
			for (int i = 0; i < endYoffsetAll.length; i++) {
				maxYoffset = maxYoffset(endYoffsetAll[i], maxYoffset);
			}
			activityStr.append(startinfo[0]);
			activityStr.append(endinfo[0]);
			lineStr.append(startinfo[1]);
			lineStr.append(endinfo[1]);
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxYoffset += 80;
		maxYoffset = maxYoffset < 400 ? 400 : maxYoffset;
		String workflowtrack = "<div style=\"POSITION: absolute;left:30px;\" >"
				+ activityStr.toString() + lineStr.toString() + "</div>";
		// String tableInfo = "<div style=\"POSITION: absolute;left:50px;top:"
		// + maxYoffset + "\" >"
		// + WfTrackTable.trackTable(activitys, worklist) + "</div>";
		// String controlinfo = WfControl.controlInfo() ;
		// return controlinfo + workflowtrack + tableInfo;

		return workflowtrack;
	}

	private static float maxYoffset(String yoffset, float maxPre) {
		try {
			float yoffsetNext = Float.parseFloat(yoffset);
			if (yoffsetNext > maxPre) {
				return yoffsetNext;
			}
		} catch (Exception e) {

		}
		return maxPre;
	}

	private static String[] dealWithMarkPoint(List list, String name,
			WorkflowProcess procs) {

		StringBuffer bufPoint = new StringBuffer();
		StringBuffer line = new StringBuffer();
		Activity[] markActivity = null;
		if ("start".equals(name)) {
			markActivity = fetchAheadActivity(procs);
		} else {
			markActivity = fetchEndActivity(procs);
		}
		int i = 0;
		StringBuffer yoffset = new StringBuffer();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			String value = (String) itr.next();
			value = value == null ? "" : value;
			String[] valueArr = value.split(";");
			if (valueArr.length >= 4) {
				yoffset.append(valueArr[3] + ",");
				String preinfo = WfIcon.makeIconInfo(valueArr[2], valueArr[3],
						name);
				bufPoint.append(preinfo);
				int actIndex = i < markActivity.length ? i : 0;
				if ("start".equals(name)) {
					String lineInfo = WfIcon.makeStartLineInfo(valueArr[2],
							valueArr[3], markActivity[actIndex]);
					line.append(lineInfo);
				} else {
					String lineInfo = WfIcon.makeEndLineInfo(valueArr[2],
							valueArr[3], markActivity[actIndex]);
					line.append(lineInfo);
				}
			}
			i++;
		}
		String[] ret = new String[] { bufPoint.toString(), line.toString(),
				yoffset.toString() };
		return ret;
	}

	private static Activity[] fetchAheadActivity(WorkflowProcess proc) {
		Activity[] actStart = proc.getActivity();
		List list = new ArrayList();
		for (int i = 0; i < actStart.length; i++) {
			if (actStart[i].isStartActivity()) {
				list.add(actStart[i]);
			}
		}
		return (Activity[]) list.toArray(new Activity[0]);
	}

	private static Activity[] fetchEndActivity(WorkflowProcess proc) {
		Activity[] actStart = proc.getActivity();
		List list = new ArrayList();
		for (int i = 0; i < actStart.length; i++) {
			if (actStart[i].isExitActivity()) {
				list.add(actStart[i]);
			}
		}
		return (Activity[]) list.toArray(new Activity[0]);
	}
}
