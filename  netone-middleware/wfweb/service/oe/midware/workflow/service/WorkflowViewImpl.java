package oe.midware.workflow.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;

import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.soatools.ActiveBindDao;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 工作流展现服务
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class WorkflowViewImpl extends UnicastRemoteObject implements
		WorkflowView {

	RuntimeMonitor runtimemonitor;

	public WorkflowViewImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public List listRelevantvar(String processid) throws RemoteException {

		WorkflowProcess wf = loadProcess(processid);
		return Arrays.asList(wf.getDataField());
	}

	public List listWorklist(String runtimeid) throws RemoteException {

		return runtimemonitor.listWorklist(runtimeid);
	}

	public List listinstance(String processid) throws RemoteException {
		return runtimemonitor.listinstance(processid);
	}

	public List listRelevartvarInstance(String runtimeId)
			throws RemoteException {
		return runtimemonitor.listRelevartvarInstance(runtimeId);
	}

	public WorkflowProcess loadProcess(String processid) throws RemoteException {
		return runtimemonitor.loadProcess(processid);
	}

	public TWfRuntime loadRuntime(String runtimeid) throws RemoteException {
		return runtimemonitor.loadRuntime(runtimeid);
	}

	public TWfWorklist loadWorklist(String workcode) throws RemoteException {
		return runtimemonitor.loadWorklist(workcode);
	}

	public TWfRelevantvar loadRelevantvar(String varcode)
			throws RemoteException {
		return runtimemonitor.loadRelevantvar(varcode);
	}

	public WorkflowProcess loadProcessUrl(String urladdress)
			throws RemoteException {
		return runtimemonitor.loadProcessUrl(urladdress);

	}

	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) throws RemoteException {
		return runtimemonitor.listRelevartvarInstance(runtimeId, dataid);
	}

	public RuntimeMonitor getRuntimemonitor() throws RemoteException {
		return runtimemonitor;
	}

	public void setRuntimemonitor(RuntimeMonitor runtimemonitor)
			throws RemoteException {
		this.runtimemonitor = runtimemonitor;
	}

	public Activity fetchActivity(String processid, String activityid)
			throws RemoteException {
		return runtimemonitor.fetchActivity(processid, activityid);
	}

	public List fetchDoneWorklist(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchDoneWorklist(runtimeid);
	}

	public List fetchDoneWorklist(String runtimeid, String activityId)
			throws RemoteException {
		return runtimemonitor.fetchDoneWorklist(runtimeid, activityId);
	}

	public List fetchExceptionWorklist(String runtimeid) throws RemoteException {
		return runtimemonitor.fetchExceptionWorklist(runtimeid);
	}

	public List fetchExceptionWorklist(String runtimeid, String activityId)
			throws RemoteException {
		return runtimemonitor.fetchExceptionWorklist(runtimeid, activityId);
	}

	public List fetchQuashWorklist(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchQuashWorklist(runtimeid);
	}

	public List fetchQuashWorklist(String runtimeid, String activityId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchQuashWorklist(runtimeid, activityId);
	}

	public List fetchRelevantVar(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchRelevantVar(runtimeid);
	}

	public TWfRelevantvar fetchRelevantVar(String runtimeid,
			String relevantVarID) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchRelevantVar(runtimeid, relevantVarID);
	}

	public Map fetchRelevantvarMap(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchRelevantvarMap(runtimeid);
	}

	public List fetchRunningWorklist(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchRunningWorklist(runtimeid);
	}

	public List fetchRunningWorklist(String runtimeid, String activityId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchRunningWorklist(runtimeid, activityId);
	}

	public List fetchSubflowByRuntimeid(String runtimeid)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchSubflowByRuntimeid(runtimeid);
	}

	public List fetchSubflowByWorkcode(String workcode) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchSubflowByWorkcode(workcode);
	}

	public List fetchSyncWaittingWorkList(String runtimeid)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchSyncWaittingWorkList(runtimeid);
	}

	public List fetchWorkList(String runtimeid, String activityId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchWorkList(runtimeid, activityId);
	}

	public WorkflowProcess fetchWorkflowProcess(String processid)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchWorkflowProcess(processid);
	}

	public List fetchWorklist(String runtimeid) throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.fetchWorklist(runtimeid);
	}

	public boolean haveRunningOrSubflowWaittingWorklist(String runtimeid)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.haveRunningOrSubflowWaittingWorklist(runtimeid);
	}

	public List listxRelevartvarInstance(String runtimeId, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.listxRelevartvarInstance(runtimeId, extinfo);
	}

	public List listxWorkListInstance(String runtimeid, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.listxWorkListInstance(runtimeid, extinfo);
	}

	public List listxinstance(String processid, String extinfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		return runtimemonitor.listxinstance(processid, extinfo);
	}

}
