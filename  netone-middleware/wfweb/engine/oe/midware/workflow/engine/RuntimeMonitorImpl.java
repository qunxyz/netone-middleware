package oe.midware.workflow.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

public class RuntimeMonitorImpl implements RuntimeMonitor {
	public List fetchRunningWorklist(String runtimeid) {

		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_RUNNING[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);
	}

	public List fetchDoneWorklist(String runtimeid) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_DONE[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchExceptionWorklist(String runtimeid) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_EXCEPTION[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchQuashWorklist(String runtimeid) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_QUASH[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchWorklist(String runtimeid) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchRelevantVar(String runtimeid) {
		TWfRelevantvar var = new TWfRelevantvar();
		var.setRuntimeid(runtimeid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(var, null);

	}

	public List fetchRunningWorklist(String runtimeid, String activityId) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setActivityid(activityId);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_RUNNING[0]);

		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null);

	}

	public boolean haveRunningOrSubflowWaittingWorklist(String runtimeid) {
		List list = fetchRunningWorklist(runtimeid);
		int number = list.size();
		if (number > 0) {
			return true;
		}
		return false;
	}

	public List fetchDoneWorklist(String runtimeid, String activityId) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setActivityid(activityId);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_DONE[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchExceptionWorklist(String runtimeid, String activityId) {
		TWfWorklist worklist = new TWfWorklist();
		worklist.setRuntimeid(runtimeid);
		worklist.setActivityid(activityId);
		worklist.setExecutestatus(RuntimeWorklistRef.STATUS_EXCEPTION[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	/**
	 * 取得流程中指定活动的所有撤销状态下的实例
	 * 
	 * @param activityId
	 *            String:活动ID
	 * @return List
	 */
	public List fetchQuashWorklist(String runtimeid, String activityId) {
		TWfWorklist work = new TWfWorklist();
		work.setRuntimeid(runtimeid);
		work.setActivityid(activityId);
		work.setExecutestatus(RuntimeWorklistRef.STATUS_QUASH[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(work,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);
	}

	/**
	 * 获得流程中指定活动所对应的所有实例
	 * 
	 * @param activityID
	 *            String:活动ID
	 * @return List:活动对象列表
	 */
	public List fetchWorkList(String runtimeid, String activityId) {
		TWfWorklist work = new TWfWorklist();
		work.setRuntimeid(runtimeid);
		work.setActivityid(activityId);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(work,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	public List fetchSyncWaittingWorkList(String runtimeid) {
		TWfWorklist work = new TWfWorklist();
		work.setRuntimeid(runtimeid);
		work.setExecutestatus(RuntimeWorklistRef.STATUS_SUBFLOW_WAITING[0]);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(work,
				null, RuntimeProcessRef._QUERY_WORKLIST_APPEND_CONDITION);

	}

	/**
	 * 获得流程中指定的相关数据
	 * 
	 * @param relevantVarID
	 *            String:相关数据ID
	 * @return TWfRelevantvar: 相关数据对象
	 */
	public TWfRelevantvar fetchRelevantVar(String runtimeid,
			String relevantVarID) {
		if (relevantVarID == null || relevantVarID.trim().equals("")) {
			return null;
		}
		TWfRelevantvar rev = new TWfRelevantvar();
		rev.setRuntimeid(runtimeid);
		rev.setDatafieldid(relevantVarID);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(rev,
				null);
		if (list != null || list.size() != 0) {
			return (TWfRelevantvar) list.get(0);
		}
		return null;
	}

	/**
	 * getsSubflow
	 * 
	 * @return TWfRuntime
	 */
	public List fetchSubflowByRuntimeid(String runtimeid) {
		TWfRuntime runtime = new TWfRuntime();
		runtime.setBelongruntimeid(runtimeid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null);
	}

	public List fetchSubflowByWorkcode(String workcode) {
		TWfRuntime runtime = new TWfRuntime();
		runtime.setBelongactivityid(workcode);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null);
	}

	public WorkflowProcess fetchWorkflowProcess(String processid) {
		return ((XpdlModelHandler) WfEntry.fetchBean

		("xpdlModelHandler")).fetchXpdlWorkflowByProcessid(processid);
	}

	public Map fetchRelevantvarMap(String runtimeid) {
		List list = this.fetchRelevantVar(runtimeid);
		Map map = new HashMap();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TWfRelevantvar var = (TWfRelevantvar) itr.next();
			map.put(var.getDatafieldid(), var);
		}
		return map;
	}

	public Activity fetchActivity(String processid, String activityid) {
		WorkflowProcess proc = fetchWorkflowProcess(processid);
		return proc.getActivity(activityid);
	}

	public List listRelevantvar(String processid) {
		WorkflowProcess wf = loadProcess(processid);
		return Arrays.asList(wf.getDataField());
	}

	public List listWorklist(String runtimeid) {
		TWfWorklist wflist = new TWfWorklist();
		wflist.setRuntimeid(runtimeid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(wflist,
				null);
	}

	public List listinstance(String processid) {
		TWfRuntime runtime = new TWfRuntime();
		runtime.setProcessid(processid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null);
	}

	public List listRelevartvarInstance(String runtimeId) {
		// TODO Auto-generated method stub
		TWfRelevantvar runtime = new TWfRelevantvar();
		runtime.setRuntimeid(runtimeId);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null);
	}

	public WorkflowProcess loadProcess(String processid) {
		XpdlModelHandler process = (XpdlModelHandler) WfEntry
				.fetchBean("xpdlModelHandler");
		return process.fetchXpdlWorkflowByProcessid(processid);
	}

	public TWfRuntime loadRuntime(String runtimeid) {
		return (TWfRuntime) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(TWfRuntime.class, runtimeid);
	}

	public TWfWorklist loadWorklist(String workcode) {
		return (TWfWorklist) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(TWfWorklist.class, workcode);
	}

	public TWfRelevantvar loadRelevantvar(String varcode) {
		return (TWfRelevantvar) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(TWfRelevantvar.class, varcode);
	}

	public WorkflowProcess loadProcessUrl(String urladdress)
			throws RemoteException {
		InputStream in = null;
		try {
			URL url = new URL(urladdress);
			try {
				in = url.openConnection().getInputStream();
				XpdlModelHandler process = (XpdlModelHandler) WfEntry
						.fetchBean("xpdlModelHandler");
				return process.fetchXpdlWorkflowByProcessid(in);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) {
		// TODO Auto-generated method stub
		TWfRelevantvar runtime = new TWfRelevantvar();
		runtime.setRuntimeid(runtimeId);
		runtime.setDatafieldid(dataid);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				runtime, null);
		if (list != null || list.size() > 0) {
			return (TWfRelevantvar) list.get(0);
		}
		return null;
	}

	public List listxRelevartvarInstance(String runtimeId, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		TWfRelevantvar runtime = new TWfRelevantvar();
		runtime.setRuntimeid(runtimeId);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null, extinfo);

	}

	public List listxWorkListInstance(String runtimeid, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		TWfWorklist work = new TWfWorklist();
		work.setRuntimeid(runtimeid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(work,
				null, extinfo);
	}

	public List listxinstance(String processid, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		TWfRuntime runtime = new TWfRuntime();
		runtime.setProcessid(processid);
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(runtime,
				null, extinfo);
	}
}
