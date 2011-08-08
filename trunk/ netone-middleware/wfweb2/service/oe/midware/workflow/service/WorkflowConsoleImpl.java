package oe.midware.workflow.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.ProcessEngine;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.DbTools;
import oe.midware.workflow.client.Session;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.soatools.ActiveBindDao;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 工作流控制服务
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class WorkflowConsoleImpl extends UnicastRemoteObject implements
		WorkflowConsole {

	Map sessions = new Hashtable();

	ProcessEngine processEngine;

	RuntimeMonitor runtimemonitor;

	ActiveBindDao activeBindDao;

	public ActiveBindDao getActiveBindDao() {
		return activeBindDao;
	}

	public void setActiveBindDao(ActiveBindDao activeBindDao) {
		this.activeBindDao = activeBindDao;
	}

	public WorkflowConsoleImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void commitActivity(String workcode) throws RemoteException {
		TWfWorklist wf = (TWfWorklist) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(TWfWorklist.class, workcode);
		processEngine.commitActivity(wf);

	}

	public void commitActivityByManual(String workcode, String actid)
			throws RemoteException {

		TWfWorklist wf = runtimemonitor.loadWorklist(workcode);

		TWfRuntime runtime = runtimemonitor.loadRuntime(wf.getRuntimeid());

		String processid = runtime.getProcessid();

		WorkflowProcess proc = runtimemonitor.loadProcess(processid);
		processEngine.commitActivityByManual(wf, proc.getActivity(actid));

	}

	public void commitActivityByManual(String workcode, String[] actid)
			throws RemoteException {
		TWfWorklist wf = runtimemonitor.loadWorklist(workcode);

		TWfRuntime runtime = runtimemonitor.loadRuntime(wf.getRuntimeid());

		String processid = runtime.getProcessid();

		WorkflowProcess proc = runtimemonitor.loadProcess(processid);

		List listAct = new ArrayList();
		for (int i = 0; i < actid.length; i++) {
			listAct.add(proc.getActivity(actid[i]));
		}
		processEngine.commitActivityByManual(wf, listAct);

	}

	public void dropProcess(String runtimeid) throws RemoteException {
		processEngine.dropProcess(runtimemonitor.loadRuntime(runtimeid));

	}

	public void initProcess(String runtimeid) throws RemoteException {

		processEngine.initProcess(runtimemonitor.loadRuntime(runtimeid));

	}

	public boolean judgementRule(String elogicExpress, String runtimeid)
			throws RemoteException {

		return processEngine.judgementRule(elogicExpress, runtimeid);
	}

	public String newProcess(String processsid) throws RemoteException {
		WorkflowProcess wf = runtimemonitor.loadProcess(processsid);

		TWfRuntime runtime = processEngine.newProcess(wf);
		return runtime.getRuntimeid();
	}

	public void runProcess(String runtimeid) throws RemoteException {
		TWfRuntime runtime = runtimemonitor.loadRuntime(runtimeid);

		processEngine.runProcess(runtime);

		// 创建该流程的会话对象
		Session session = new Session();
		session.setSessionid(runtimeid);
		session.setCreated(new Timestamp(System.currentTimeMillis()));
		sessions.put(runtimeid, session);

	}

	public void updateRelevantvar(TWfRelevantvar rev) throws RemoteException {
		OrmerEntry.fetchOrmer().fetchSerializer().update(rev);
	}

	public void worklistAppBind(String workcode, String apptype, String appname)
			throws RemoteException {
		TWfWorklist worklist = (TWfWorklist) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TWfWorklist.class, workcode);
		worklist.setExtendattribute(apptype + ":" + appname);
		OrmerEntry.fetchOrmer().fetchSerializer().update(worklist);
	}

	public void updateWorklistStatus(String workcode, String status)
			throws RemoteException {
		boolean availableStatus = false;
		if (status != null) {
			String[][] statusx = RuntimeWorklistRef.STATUSLIST;
			for (int i = 0; i < statusx.length; i++) {
				if (statusx[i][0].equals(statusx)) {
					availableStatus = true;
					break;
				}
			}
		}
		if (availableStatus) {
			TWfWorklist worklist = (TWfWorklist) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TWfWorklist.class, workcode);
			worklist.setExecutestatus(status);
			OrmerEntry.fetchOrmer().fetchSerializer().update(worklist);
		}

	}

	public void runSubFlow(String subRuntimeid, String fatherRuntimeid,
			String workcode, boolean sync) throws RemoteException {
		TWfRuntime runtimeSub = runtimemonitor.loadRuntime(subRuntimeid);
		// 添加子流程的隶属信息
		runtimeSub.setBelongactivityid(workcode);
		runtimeSub.setBelongruntimeid(fatherRuntimeid);
		if (sync) {
			runtimeSub.setKind(RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0]);
		} else {
			runtimeSub.setKind(RuntimeProcessRef.TYPE_SUB_FLOW[0]);
		}

		OrmerEntry.fetchOrmer().fetchSerializer().update(runtimeSub);
		this.runProcess(subRuntimeid);

	}

	public String exeScript(String elogicExpress, String runtimeid)
			throws RemoteException {

		return processEngine.exeScript(elogicExpress, runtimeid);
	}

	public void updateRelevantvar(List revs) throws RemoteException {
		processEngine.updateRelevantvars(revs);

	}

	public void commitActivity(TWfWorklist currentworklist)
			throws RemoteException {
		processEngine.commitActivity(currentworklist);

	}

	public void commitActivityByManual(TWfWorklist currentworklist, Activity act)
			throws RemoteException {
		processEngine.commitActivityByManual(currentworklist, act);

	}

	public void commitActivityByManual(TWfWorklist currentworklist, List act)
			throws RemoteException {
		processEngine.commitActivityByManual(currentworklist, act);

	}

	public void dropProcess(TWfRuntime runtime) throws RemoteException {
		processEngine.dropProcess(runtime);

	}

	public void initProcess(TWfRuntime runtime) throws RemoteException {
		processEngine.initProcess(runtime);

	}

	public TWfRuntime newProcess(WorkflowProcess proc) throws RemoteException {
		return processEngine.newProcess(proc);
	}

	public void runProcess(TWfRuntime runtime) throws RemoteException {
		processEngine.runProcess(runtime);

	}

	public void updateRelevantvarUseLog(TWfRelevantvar relevantvar,
			String extendinfo) throws RemoteException {
		processEngine.updateRelevantvarUseLog(relevantvar, extendinfo);

	}

	public void updateRelevantvars(List relevantvar) throws RemoteException {
		processEngine.updateRelevantvars(relevantvar);

	}

	public void updateRelevantvarsUseLog(List relevantvar, String extendinfo)
			throws RemoteException {
		processEngine.updateRelevantvarsUseLog(relevantvar, extendinfo);

	}

	public String eai(String eaixml, String runtimeid, String workcode)
			throws RemoteException {
		if (eaixml == null || eaixml.equals("")) {// 没有soa集成处理
			return null;
		}

		TWfWorklist worklist = runtimemonitor.loadWorklist(workcode);

		// 解析SOA参数
		SoaObj listParam = activeBindDao.fromXml(eaixml);
		String scriptinfo = listParam.getScript().getCdata();
		System.out.println(listParam.getActivity().isSync() + ","
				+ worklist.isDone());
		if (!listParam.getActivity().isSync() && worklist.isRunning()) {// 异步模式，在激活节点的时候先执行脚本
			return exeScript(scriptinfo, runtimeid);
		} else if (listParam.getActivity().isSync() && worklist.isDone()) {// 同步模式，必须等节点结束后才去执行
			return exeScript(scriptinfo, runtimeid);
		}
		return ""; // 什么都不执行

	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public RuntimeMonitor getRuntimemonitor() {
		return runtimemonitor;
	}

	public void setRuntimemonitor(RuntimeMonitor runtimemonitor) {
		this.runtimemonitor = runtimemonitor;
	}

	public Object getSessionAttribute(String runtimeid, String name)
			throws RemoteException {
		// TODO Auto-generated method stub
		Session ssion = (Session) sessions.get(runtimeid);
		return ssion.getAttribute(name);
	}

	public void setSessionAttribute(String runtimeid, String name, Object obj)
			throws RemoteException {
		Session ssion = (Session) sessions.get(runtimeid);
		ssion.setAttribute(name, obj);
	}

	public Session descriptSession(String runtimeid) throws RemoteException {
		return (Session) sessions.get(runtimeid);
	}

	public void removeSession(String runtimeid) throws RemoteException {
		sessions.remove(runtimeid);

	}

	public void updateWorklistExtendattribute(String workcode, String ext)
			throws RemoteException {
		TWfWorklist worklist = (TWfWorklist) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TWfWorklist.class, workcode);
		worklist.setExtendattribute(ext);
		OrmerEntry.fetchOrmer().fetchSerializer().update(worklist);

	}

	public int coreSqlhandle(String sql) throws RemoteException{
		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DbTools.execute(con, sql);
		
	}

}
