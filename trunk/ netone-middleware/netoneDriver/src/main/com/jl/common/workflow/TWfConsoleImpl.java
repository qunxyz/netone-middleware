package com.jl.common.workflow;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.UUID;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.web.WebCache;

import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.condition.Condition;
import oe.midware.workflow.xpdl.model.transition.Split;
import oe.midware.workflow.xpdl.model.transition.Transition;
import oe.midware.workflow.xpdl.model.transition.TransitionRestriction;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppHandleIfc;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.message.Message;

/**
 * 面向审批人员选择人员业务的 工作流应用驱动
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class TWfConsoleImpl implements TWfConsoleIfc {

	Map appnameToName = new HashMap();

	public List<TWfWorklistExt> worklist(String customer) throws Exception {

		// 获得所有许可的代办任务
		String loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"
				+ customer + "' and w2.statusnow='01'";

		return worklistCore(loadworklist);

	}

	public List<TWfWorklistExt> worklistDone(String customer) throws Exception {
		// 获得所有许可的代办任务
		String loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
				+ customer + "' and w2.statusnow='02' and wx.STATUSNOW='01'";
		return worklistCore(loadworklist);
	}

	private List<TWfWorklistExt> worklistCore(String loadworklist)
			throws Exception {
		// 预先装载工作流句柄
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}

		List list = wfview.coreSqlview(loadworklist);

		// 获得所有许可的活动实例和相关变量信息构造TWfWorklistExt对象返回
		List listWorklist = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String processid = (String) object.get("processid");
			String actid = (String) object.get("actid");
			String runtimeid = (String) object.get("runtimeid");
			String workcode = (String) object.get("workcode");
			String startime = (String) object.get("starttime");
			String userinfo = (String) object.get("userinfo");
			String types = (String) object.get("types");
			boolean sync = "1".equals((String) object.get("sync"));

			String appid = getAppNameByRuntimeId(runtimeid);

			TWfWorklistExt wfext = new TWfWorklistExt();
			TWfActive act = this.loadRuntimeActive(processid, actid, appid, "",
					runtimeid);

			wfext.setAct(act);
			wfext.setParticipant(userinfo);
			wfext.setWorkcode(workcode);
			wfext.setStarttime(startime);

			wfext.setRuntimeid(runtimeid);
			wfext.setOperatemode(types);

			String modetip = "";
			if (sync) {
				modetip = "同步";
			}
			String operateMode = "";
			if ("01".equals(types)) {
				operateMode = "" + modetip;
			} else if ("02".equals(types)) {
				operateMode = "抄送" + "|" + modetip;
			} else {
				operateMode = "抄阅";
			}

			wfext.setOperatemodetip(operateMode);

			// 获得流程的所有相关变量
			List listRev = wfview.fetchRelevantVar((String) object
					.get("runtimeid"));
			List revExt = new ArrayList();
			for (Iterator iterator3 = listRev.iterator(); iterator3.hasNext();) {
				TWfRelevantvar name = (TWfRelevantvar) iterator3.next();
				String filedid = name.getDatafieldid();
				String valuenow = name.getValuenow();
				if (this._DEFAULT_REV_KEY_BUSSTYPE.equals(filedid)) {
					wfext.setAppid(filedid);
				} else if (this._DEFAULT_REV_KEY_BUSSID.equals(filedid)) {
					wfext.setBussid(valuenow);
				} else if (this._DEFAULT_REV_KEY_BUSSURL.equals(filedid)) {
					wfext.setBussurl(valuenow);
				} else if (this._DEFAULT_REV_KEY_BUSSTIP.equals(filedid)) {
					wfext.setBusstip(valuenow);
				} else {
					revExt.add(name);
				}
			}
			wfext.setRev(revExt);
			listWorklist.add(wfext);// 添加符合条件的活动任务

		}
		return listWorklist;

	}

	private void dealwith_buss_done(TWfWorklist wl) throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		List list = this.listNextRouteActive(wl.getProcessid(), wl
				.getActivityid(), wl.getRuntimeid(), wl.getParticipant());
		if (list == null || list.size() == 0) {
			console
					.coreSqlhandle("update t_wf_runtime set STATUSNOW='02' where runtimeid='"
							+ wl.getRuntimeid() + "'");
		}
	}

	private void checkRunningAct(String runtimeid) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		List list = wfview.fetchRunningWorklist(runtimeid);
		if (list == null || list.size() == 0) {
			return;
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			String activityid = object.getActivityid();

			String processid = object.getProcessid();

			String appid = getAppNameByRuntimeId(runtimeid);

			TWfActive act = this.loadRuntimeActive(processid, activityid,
					appid, "", runtimeid);

			if (act.isAutoroute()) {
				specifyParticipantAutoByWorkcode("系统[SYSTEM]", object
						.getWorkcode());
			} else {
				// 附加处理，分配并发任务中指派的人员（在并发任务中，由于下一个节点未启动所以指派的人员是被延迟的）

				String sqzl = "select lsh from t_wf_participant where workcode='"
						+ object.getWorkcode()
						+ "' and extendattribute='"
						+ activityid
						+ "' and statusnow='00'";
				List read_to_assigntask = wfview.coreSqlview(sqzl);

				String workcode = object.getWorkcode();
				for (Iterator iterator2 = read_to_assigntask.iterator(); iterator2
						.hasNext();) {
					Map object2 = (Map) iterator2.next();
					String lsh = (String) object2.get("lsh");
					String sql = "update t_wf_participant set workcode='"
							+ workcode + "',statusnow='01' where lsh='" + lsh
							+ "'";
					console.coreSqlhandle(sql);
				}
			}
		}
	}

	private boolean sync_pass(String workcode) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		String sql_2 = "select count(*) countx from t_wf_participant where types!='03' and  statusnow='01'  and workcode='"
				+ workcode + "'";
		List listCheck = wfview.coreSqlview(sql_2);
		Map map2 = (Map) listCheck.get(0);
		return (Long) map2.get("countx") == 0;
	}

	private boolean need_sync_check(String workcode) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		boolean needsync = false;
		String sql_sync = "select count(*) countx from t_wf_participant where types!='03' and sync='1'  and workcode='"
				+ workcode + "'";
		List list_sync = wfview.coreSqlview(sql_sync);
		Map mapx = (Map) list_sync.get(0);
		return (Long) mapx.get("countx") > 0;
	}

	/**
	 * 提交个人任务，非工作流驱动
	 * 
	 * @param participant
	 * @param workcode
	 * @throws Exception
	 */
	private void commitMyTask(String participant, String workcode)
			throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String sql_done = "update t_wf_participant set statusnow='02',donetime='"
				+ time
				+ "' where usercode='"
				+ participant
				+ "' and workcode='" + workcode + "'";
		console.coreSqlhandle(sql_done);
	}

	protected void updateRev(String runtimeid, String code, String value)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfRelevantvar rev = wfview.fetchRelevantVar(runtimeid, code);// 获得表单变量
		rev.setValuenow(value);
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.updateRelevantvar(rev);
	}

	public void runProcess(String runtimeid) throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.runProcess(runtimeid);

		String participant = getVarByRuntimeId(runtimeid,
				this._DEFAULT_REV_KEY_CUSTOMER);
		String workcode[] = this.getRunningWorkCodeByRuntimeid(runtimeid);
		if (workcode.length == 1) {
			this.specifyParticipantByWorkcode(participant, workcode[0],
					participant, false, "01");
		} else {
			throw new RuntimeException("流程启动失败，无法激活首节点");
		}

	}

	public void setVarValue(String runtimeId, String revId, String value)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfRelevantvar rev = wfview.fetchRelevantVar(runtimeId, revId);
		rev.setValuenow(value);
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.updateRelevantvar(rev);
	}

	public String getVarByRuntimeId(String runtimeId, String revId)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfRelevantvar rev = wfview.fetchRelevantVar(runtimeId, revId);
		return rev.getValuenow();
	}

	public boolean isWorkflowDone(String runtimeId) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");

		return RuntimeProcessRef.STATUS_RUNNING[0].equals(wfview.loadRuntime(
				runtimeId).getStatusnow());

	}

	public boolean isWorkflowException(String runtimeId) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");

		return RuntimeProcessRef.STATUS_EXCEPTION[0].equals(wfview.loadRuntime(
				runtimeId).getStatusnow());
	}

	public boolean isWorkflowRunning(String runtimeId) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return RuntimeProcessRef.STATUS_RUNNING[0].equals(wfview.loadRuntime(
				runtimeId).getStatusnow());
	}

	public String getRuntimeIdByWorkcode(String workcode) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);
		return wl.getRuntimeid();
	}

	public String[] subFlowRuntimeId(String parentRuntimeid) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		List data = new ArrayList();
		List list = wfview.fetchSubflowByRuntimeid(parentRuntimeid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfRuntime object = (TWfRuntime) iterator.next();
			data.add(object.getRuntimeid());
		}
		return (String[]) data.toArray(new String[0]);
	}

	public String stopProcess(String runtimeid) throws Exception {
		if (runtimeid == null) {
			return this.OPE_TIP_ERROR + "无效流程runtimeID";
		}
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		// 撤销流程
		console.coreSqlhandle("update t_wf_runtime set statusnow='"
				+ RuntimeProcessRef.STATUS_QUASH[0] + "' where runtimeid='"
				+ runtimeid + "'");
		// 撤销的活动
		console.coreSqlhandle("update t_wf_worklist set EXECUTESTATUS='"
				+ RuntimeWorklistRef.STATUS_QUASH[0] + "' where runtimeid='"
				+ runtimeid + "' and EXECUTESTATUS='"
				+ RuntimeWorklistRef.STATUS_RUNNING[0] + "'");
		return this.OPE_TIP_SUCCESS;

	}
	
	public String deleteProcess(String runtimeid) throws Exception {
		if (runtimeid == null) {
			return this.OPE_TIP_ERROR + "无效流程runtimeID";
		}
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		// 撤销流程
		console.coreSqlhandle("delete from t_wf_runtime where runtimeid='"
				+ runtimeid + "'");
		// 撤销的活动
		console.coreSqlhandle("delete from t_wf_worklist where runtimeid='"
				+ runtimeid + "'");
		return this.OPE_TIP_SUCCESS;
	}

	public String getProcessIdByRuntimeId(String runtimeid) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return wfview.loadRuntime(runtimeid).getProcessid();

	}

	public String reActive(String workcode) throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.coreSqlhandle("update t_wf_worklist set executestatus='"
				+ RuntimeWorklistRef.STATUS_RUNNING[0] + "' where workcode='"
				+ workcode + "'");
		return null;
	}

	public String getSession(String key) {
		WorkflowView wfview;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			List list = wfview
					.coreSqlview("select RUNTIMEID from t_wf_relevantvar where VALUENOW='"
							+ key + "'");
			if (list.size() > 0) {
				// 可能查到多个数据，特别是重置后，由于业务系统继续保留使用之前业务数据的ID，
				// 所以会出现多个相同的相关变量数据，实属正常现象，在这里通过修改条件适应即可
				return (String) ((Map) list.get(list.size() - 1))
						.get("RUNTIMEID");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void specifyParticipantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception {
		String types = "01";// 01表示任务主办者
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		specifyOperateByWorkcode(commiter, workcode, participant, types, sync,
				"", null, opemode, actid, actname);
	}

	private void specifyOperateByWorkcode(String uuid,String commiter, String workcode,
			String participant, String types, boolean sync, String ext,
			String status, String opemode, String actid, String actname)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String usercode = StringUtils.substringBetween(participant, "[", "]");
		String username = StringUtils.substringBefore(participant, "[");
		String commitercode = StringUtils.substringBetween(commiter, "[", "]");
		String commitername = StringUtils.substringBefore(commiter, "[");
		String syncvalue = sync ? "1" : "0";
		String statusValue = status == null ? "01" : status;
		String createtime = (new Timestamp(System.currentTimeMillis()))
				.toString();

		console
				.coreSqlhandle("insert into  t_wf_participant(lsh,username,usercode,types,workcode,statusnow,commitercode,commitername,extendattribute,createtime,opemode,actid,actname,sync)values('"
						+ uuid
						+ "','"
						+ username
						+ "','"
						+ usercode
						+ "','"
						+ types
						+ "','"
						+ workcode
						+ "','"
						+ statusValue
						+ "','"
						+ commitercode
						+ "','"
						+ commitername
						+ "','"
						+ ext
						+ "','"
						+ createtime
						+ "','"
						+ opemode
						+ "','"
						+ actid + "','" + actname + "','" + syncvalue + "')");
		if ("02".equals(statusValue)) {
			String sql = "update t_wf_participant set donetime='" + createtime
					+ "' where lsh='" + uuid + "'";
			console.coreSqlhandle(sql);
		}
		
		
		// 支持流程延迟
		TWfWorklist wok=WfEntry.iv().loadWorklist(workcode);
		String processid=wok.getProcessid();
		Activity actx = this.loadProcess(processid)
				.getActivity(wok.getActivityid());
		if (this._ACT_EXT_DELAY_TRUE.equals(actx.getExtendedAttributes()
				.get(this._ACT_EXT_DELAY))) {
			// 如果需要延迟 那么系统将活动挂起
			this.pendingProcess(wok.getRuntimeid());
		}

	}
	
	private void specifyOperateByWorkcode(String commiter, String workcode,
			String participant, String types, boolean sync, String ext,
			String status, String opemode, String actid, String actname)
			throws Exception {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		this.specifyOperateByWorkcode(uuid, commiter, workcode, participant, types, sync, ext, status, opemode, actid, actname);
	}

	private void addWorklistExtInfo(String workcode, String info)
			throws Exception {

		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.updateWorklistExtendattribute(workcode, info);

	}

	public List<TWfActive> makeTWfActive(List<Activity> listRouteAimActivity,
			String appname, String commiter, String runtimeid) throws Exception {
		List newact = new ArrayList();
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		for (Iterator iterator = listRouteAimActivity.iterator(); iterator
				.hasNext();) {
			Activity object = (Activity) iterator.next();
			String processname = StringUtils.substringBetween(object
					.getWorkflowProcess().getName(), "[", "]");
			String processid = object.getWorkflowProcess().getId();
			TWfActive actx = AppEntry.iv().loadCfgActive(appname,
					object.getId(), commiter, runtimeid);
			actx.setProcessid(processid);
			actx.setProcessname(processname);
			newact.add(actx);

		}
		return newact;
	}

	public TWfActive makeTWfActive(Activity object, String appid,
			String commiter, String runtimeid) throws Exception {
		List list = new ArrayList();
		list.add(object);
		return (TWfActive) makeTWfActive(list, appid, commiter, runtimeid).get(
				0);

	}

	public TWfWorklist loadWorklist(String workcode) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return wfview.loadWorklist(workcode);
	}

	public WorkflowConsole useCoreConsole() throws Exception {

		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		return console;
	}

	public WorkflowView useCoreView() throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return wfview;
	}

	public void specifyAssistantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception {
		String types = "02";// 02表示任务协办者
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		specifyOperateByWorkcode(commiter, workcode, participant, types, sync,
				"", null, "01", actid, actname);

	}

	public void specifyReaderByWorkcode(String commiter, String workcode,
			String participant, String opemode) throws Exception {
		String types = "03";// 03表示抄阅者
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		specifyOperateByWorkcode(commiter, workcode, participant, types, false,
				"", null, opemode, actid, actname);

	}

	public void distributedSubmit(String commiter, String workcode,
			String participant, String opemode) throws Exception {
		String types = "04";// 04分布式提交
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		specifyOperateByWorkcode(uuid,commiter, workcode, participant, types, false,
				"", null, opemode, actid, actname);
		
		
		String commitercode = StringUtils.substringBetween(commiter, "[", "]");
		String commitername = StringUtils.substringBefore(commiter, "[");
		String sql = "select auditnode from t_wf_participant where usercode='" + commitercode + "' and workcode = '"+workcode+"' order by createtime";
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		List<Map> list = wfview.coreSqlview(sql);
		String auditnode = "";
		if (list!=null) 
			auditnode = (String) list.get(0).get("auditnode");
		
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String time=(new Timestamp(System.currentTimeMillis())).toString();
		String sql_done = "update t_wf_participant set statusnow='02',donetime='"+time+"',auditnode='" + auditnode
				+ "' where lsh='"+uuid+"' and types='04' and statusnow!='02' and usercode='normaluser' and workcode='"
				+ workcode + "'";
		console.coreSqlhandle(sql_done);

	}

	public void specifyLimit(String workcode, String participant, long limit)
			throws Exception {
		String usercode = StringUtils.substringBetween(participant, "[", "]");
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		console.coreSqlhandle("update  t_wf_participant set limitime=" + limit
				+ " where workcode='" + workcode + "' and usercode='"
				+ usercode + "'");
	}

	public String[] getRunningWorkCodeByRuntimeid(String runtimeid)
			throws Exception {
		List list = listAllRunningWorklistByRuntimeid(runtimeid);
		List workcodes = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			workcodes.add(object.getWorkcode());
		}
		return (String[]) workcodes.toArray(new String[0]);
	}

	public List<TWfWorklist> listAllRunningWorklistByRuntimeid(String runtimeid)
			throws Exception {
		// TODO Auto-generated method stub
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return wfview.fetchRunningWorklist(runtimeid);
	}

	public List<TWfWorklist> listAllDoneWorklistByRuntimeid(String runtimeid)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		return wfview.fetchDoneWorklist(runtimeid);
	}

	public List<TWfParticipant> listAllParticipantinfo(String runtimeid,boolean onlyDone)
			throws Exception {
		String extcondition="";
		if(onlyDone){
			extcondition=" and statusnow='02' order by donetime";
		}else{
			extcondition=" order by createtime";
			
		}
		String sql = "select * from t_wf_participant where workcode in (select workcode from t_wf_worklist where runtimeid='"
				+ runtimeid + "')"+extcondition;
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		List list = wfview.coreSqlview(sql);
		List listrt = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			TWfParticipant wpt = new TWfParticipant();
			wpt.setCommitercode((String) object.get("commitercode"));
			wpt.setCommitername((String) object.get("commitername"));
			wpt.setExtendattribute((String) object.get("extendattribute"));
			wpt.setStatusnow((String) object.get("statusnow"));
			wpt.setSync(((String) object.get("sync")).equals("1"));
			wpt.setTypes((String) object.get("types"));
			wpt.setUsercode((String) object.get("usercode"));
			wpt.setUsername((String) object.get("username"));
			wpt.setWorkcode((String) object.get("workcode"));
			wpt.setAuditnode((String) object.get("auditnode"));
			wpt.setDonetime((String) object.get("donetime"));
			wpt.setCreatetime((String) object.get("createtime"));
			wpt.setOpemode((String) object.get("opemode"));
			wpt.setActid((String) object.get("actid"));
			wpt.setActname((String) object.get("actname"));
			listrt.add(wpt);
		}
		return listrt;
	}

	public TWfParticipant loadParticipantinfo(String workcode,
			String participant) throws Exception {
		String sql = "select * from t_wf_participant where workcode in (select workcode from t_wf_worklist where workcode='"
				+ workcode + "' and usercode='" + participant + "')";
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		List list = wfview.coreSqlview(sql);
		if (list.size() == 1) {
			Map object = (Map) list.get(0);
			TWfParticipant wpt = new TWfParticipant();
			wpt.setCommitercode((String) object.get("commitercode"));
			wpt.setCommitername((String) object.get("commitername"));
			wpt.setExtendattribute((String) object.get("extendattribute"));
			wpt.setStatusnow((String) object.get("statusnow"));
			wpt.setSync(((String) object.get("sync")).equals("1"));
			wpt.setTypes((String) object.get("types"));
			wpt.setUsercode((String) object.get("usercode"));
			wpt.setUsername((String) object.get("username"));
			wpt.setWorkcode((String) object.get("workcode"));
			wpt.setAuditnode((String) object.get("auditnode"));
			wpt.setDonetime((String) object.get("donetime"));
			wpt.setCreatetime((String) object.get("createtime"));
			return wpt;
		}
		return null;

	}

	public void notice(String fromuser, String touser, String message,
			String workcode, String dyform_lsh, String appname) {
		System.out.println("消息发送了：" + fromuser + "," + touser + "," + message
				+ "," + workcode + "," + dyform_lsh + "," + appname);

		try {
			List list = AppEntry.iv().wf2dyformBindCfg(appname);
			String first_rev = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TWfRelevant object = (TWfRelevant) iterator.next();
				first_rev = object.getRevid();
				break;
			}
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			Clerk fromUserObj = rs.loadClerk("0000", fromuser);

			WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
			TWfWorklist wf = wfview.loadWorklist(workcode);
			TWfRelevantvar revtemp = wfview.fetchRelevantVar(wf.getRuntimeid(),
					first_rev);

			String noticetitle = revtemp != null ? revtemp.getValuenow() : "";

			String context = "您好！您在新版电子工作流平台有新的待办任务.文件标题:" + noticetitle
					+ ", 发送人:" + fromUserObj.getName() + " 请尽快登陆10.51.176.5处理";

			Message.toMessageByUser(touser, context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<TWfWorklistExt> worklist(String clientId, String processid,
			boolean mode, int limit, String listType) throws Exception {
		// 预先装载工作流句柄
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03')";
		}
		String processidStr = "";
		if (processid != null && !"".equals(processid)) {
			processidStr = "and w1.processid='" + processid + "'";
		}
		String limitx = "";
		if (limit != 0) {
			limitx = "limit 0," + limit;
		} else {
			limitx = "limit 0,300";
		}

		// 获得所有许可的代办任务
		String loadworklist = "";

		if ("01".equals(listType)) {
			// 待办任务
			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"
					+ clientId
					+ "' and w2.statusnow='01"
					+ "' "
					+ processidStr
					+ " and w2.types in "
					+ opemode
					+ " order by w1.starttime desc" + " " + limitx;
		} else if ("02".equals(listType)) {
			// 所有结束任务但未归档

			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
					+ clientId
					+ "' and w2.statusnow='02' and wx.STATUSNOW='01'"
					+ " order by w1.starttime desc" + " " + limitx;
		} else if ("03".equals(listType)) {
			// 所有结束任务且已经归档

			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
					+ clientId
					+ "' and w2.statusnow='02' and wx.STATUSNOW='02'"
					+ " order by w1.starttime desc" + " " + limitx;
		} else {
			// 所有个人任务
			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"
					+ clientId
					+ "' "
					+ processidStr
					+ " and w2.types in "
					+ opemode + " order by w1.starttime desc" + " " + limitx;
		}

		List list = wfview.coreSqlview(loadworklist);

		// 获得所有许可的活动实例和相关变量信息构造TWfWorklistExt对象返回
		List listWorklist = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String actid = (String) object.get("actid");
			String runtimeid = (String) object.get("runtimeid");
			String workcode = (String) object.get("workcode");
			String startime = (String) object.get("starttime");
			String userinfo = (String) object.get("userinfo");
			String processidx = (String) object.get("processid");
			String types = (String) object.get("types");
			boolean sync = "1".equals((String) object.get("sync"));

			String appid = getAppNameByRuntimeId(runtimeid);

			TWfWorklistExt wfext = new TWfWorklistExt();
			TWfActive act = this.loadRuntimeActive(processidx, actid, appid,
					"", runtimeid);

			wfext.setAct(act);
			wfext.setParticipant(userinfo);
			wfext.setWorkcode(workcode);
			wfext.setStarttime(startime);

			wfext.setRuntimeid(runtimeid);
			wfext.setOperatemode(types);

			String modetip = "";
			if (sync) {
				modetip = "同步";
			}
			String operateMode = "";
			if ("01".equals(types)) {
				operateMode = "" + modetip;
			} else if ("02".equals(types)) {
				operateMode = "抄送" + "|" + modetip;
			} else {
				operateMode = "抄阅";
			}

			wfext.setOperatemodetip(operateMode);
			act.setName(act.getName() + "|" + operateMode + "|");

			// 获得流程的所有相关变量
			List listRev = wfview.fetchRelevantVar((String) object
					.get("runtimeid"));

			// 针对默认的流程关键变量设置参数
			List linkToDyColumn = new ArrayList();
			for (Iterator iterator3 = listRev.iterator(); iterator3.hasNext();) {
				TWfRelevantvar name = (TWfRelevantvar) iterator3.next();
				String filedid = name.getDatafieldid();
				boolean iskey = false;
				for (int i = 0; i < AppHandleIfc._CORE_KEY_VAR.length; i++) {
					if (AppHandleIfc._CORE_KEY_VAR[i].equalsIgnoreCase(filedid)) {
						String valuenow = name.getValuenow();
						BeanUtils.setProperty(wfext, filedid, valuenow);
						iskey = true;
						break;
					}
				}
				if (!iskey) {
					linkToDyColumn.add(name);
				}

			}
			wfext.setRev(linkToDyColumn);
			listWorklist.add(wfext);// 添加符合条件的活动任务

		}
		return listWorklist;
	}

	public void saveAuditNote(String workcode, String participant, String note)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String sql_done = "update t_wf_participant set auditnode='" + note
				+ "' where usercode='" + participant + "' and workcode='"
				+ workcode + "' and statusnow='01'";
		console.coreSqlhandle(sql_done);
	}
	
	

	public List<TWfWorklistExt> worklistDoneAndProcessDone(String customer)
			throws Exception {
		// 获得所有许可的代办任务
		String loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
				+ customer + "' and w2.statusnow='02' and wx.STATUSNOW='02'";
		return worklistCore(loadworklist);
	}

	public boolean isAndBranchMode(String processid, String activeid)
			throws Exception {
		WorkflowProcess wfDesign = loadProcess(processid);
		TransitionRestriction tx[] = wfDesign.getActivity(activeid)
				.getTransitionRestriction();
		for (int i = 0; i < tx.length; i++) {
			Split split = tx[i].getSplit();
			if (split != null) {
				String type = split.getType().toString();
				return "AND".equals(type);
			}
		}
		return false;
	}

	public WorkflowProcess loadProcess(String processid) throws Exception {
		String key = "WORKFLOW_" + processid;
		try {
			if (!WebCache.containCache(key)) {
				WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
				WorkflowProcess wf = wfview.fetchWorkflowProcess(processid);
				WebCache.setCache(key, wf, null);
				return wf;
			}
			return (WorkflowProcess) WebCache.getCache(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TWfActive loadRuntimeActive(String processid, String activeid,
			String appid, String commiter, String runtimeid) throws Exception {
		Activity act = this.loadProcess(processid).getActivity(activeid);
		return this.makeTWfActive(act, appid, commiter, runtimeid);
	}

	public String nextByAuto(String workcode, String clientId) throws Exception {
		try {
			return nextCore(workcode, "", clientId);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String nextByManual(String workcode, String actid, String clientId)
			throws Exception {
		try {
			return nextCore(workcode, actid, clientId);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String nextCore(String workcode, String actid, String clientId)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);

		if (wl == null
				|| !RuntimeWorklistRef.STATUS_RUNNING[0].equals(wl
						.getExecutestatus())) {
			// throw new Exception(this.OPE_TIP_ERROR + "无法提交非运行状态的活动");
			return this.OPE_TIP_ERROR + "无法提交非运行状态的活动";
		}
		String usercode = clientId;
		if(StringUtils.contains(clientId, "[")){
			usercode=StringUtils.substringBetween(clientId, "[", "]");
		}
		
		// 提交个人任务
		String sql = "select types from t_wf_participant where statusnow='01' and usercode='"
				+ usercode + "' and workcode='" + workcode + "'";
		List listIsReader = wfview.coreSqlview(sql);
		if (listIsReader != null && listIsReader.size() > 0) {
			Map map = (Map) listIsReader.get(0);
			String types = (String) map.get("types");
			// 提交个人任务
			commitMyTask(usercode, workcode);
			if ("03".equals(types)) {
				// return false; // 抄阅无需提交流程直接结束
				return "";
			}
		}

		// 检查同步逻辑，注意系统中存在3个同步模式一个是多个主办者间的同步，另一个是多个抄送者的同步，另一个是抄送者与主办者的同步
		// 需要注意的是 只要存在同步，无论是流程设计存在同步，还是抄送存在同步，只要出现一个同步，全部都用同步控制
		boolean needsync = need_sync_check(workcode);
		System.out.println("needsync:" + needsync);
		// 检查同步的任务的执行情况，如果全部都执行完毕了，那么可以提交流程
		if(needsync){
			boolean syncpass=sync_pass(workcode);
			if(!syncpass){
				return "";// 同步任务都未经执行完毕无法马上提交流程
			}
		}

		// 同步任务都已经执行完毕或者是 竞争者模式,可以继续提交流程了
		if (StringUtils.isNotEmpty(actid)) {
			console.commitActivityByManual(wl.getWorkcode(), actid);
			System.out.println("--手动提交到流程节点--"+actid);
		} else {
			console.commitActivity(wl);
			System.out.println("--自动提交流程节点--"+wl.getActivityid());
		}

		// 检查新的活动节点,做附加处理，
		String runtimeid = wl.getRuntimeid();
		checkRunningAct(runtimeid);
		System.out.println("--完成人员分配--"+wl.getActivityid());
		// 检查是否特送方式归档
		// 如果到了最后一个环节，可能是跳转过来的 ，这时流程还无法结束，我们需要手工结束流程
		dealwith_buss_done(wl);
		System.out.println("--本环节业务提交完毕--"+wl.getRuntimeid());
		return "";
	}

	public String getAppNameByRuntimeId(String runtimeid) throws Exception {
		// TODO Auto-generated method stub
		if (!this.appnameToName.containsKey(runtimeid)) {
			String name = this.getVarByRuntimeId(runtimeid,
					this._DEFAULT_REV_KEY_BUSSTYPE);
			if (name != null && !name.equals("")) {
				this.appnameToName.put(runtimeid, name);
			}
			return name;
		} else {
			return (String) this.appnameToName.get(runtimeid);
		}
	}

	public void setAppNameByRuntimeId(String runtimeid, String appname) {
		this.appnameToName.put(runtimeid, appname);
	}

	public String nextToEnd(String workcode, String clientId) throws Exception {
		try {
			return this.nextToEndCore(workcode, clientId);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String nextToEndCore(String workcode, String clientId)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);

		if (wl == null
				|| !RuntimeWorklistRef.STATUS_RUNNING[0].equals(wl
						.getExecutestatus())) {
			// throw new Exception(this.OPE_TIP_ERROR + "无法提交非运行状态的活动");
			return this.OPE_TIP_ERROR + "无法提交非运行状态的活动";
		}
		String usercode = StringUtils.substringBetween(clientId, "[", "]");
		if(StringUtils.isEmpty(usercode)){
			throw new RuntimeException("lose user");
		}
		// 提交个人任务
		String sql = "select types from t_wf_participant where statusnow='01' and usercode='"
				+ usercode + "' and workcode='" + workcode + "'";
		List listIsReader = wfview.coreSqlview(sql);
		if (listIsReader != null && listIsReader.size() > 0) {
			Map map = (Map) listIsReader.get(0);
			String types = (String) map.get("types");
			// 提交个人任务
			commitMyTask(usercode, workcode);

			if ("03".equals(types) || "04".equals(types)) {
				return ""; // 抄阅无需提交流程直接结束
			}
		}

		// // 检查同步逻辑，注意系统中存在3个同步模式一个是多个主办者间的同步，另一个是多个抄送者的同步，另一个是抄送者与主办者的同步
		// // 需要注意的是 只要存在同步，无论是流程设计存在同步，还是抄送存在同步，只要出现一个同步，全部都用同步控制
		// boolean needsync = need_sync_check(workcode);
		//
		// // 检查同步的任务的执行情况，如果全部都执行完毕了，那么可以提交流程
		// if (needsync && !sync_pass(workcode)) {
		// return false;// 同步任务都未经执行完毕无法马上提交流程
		// }

		// 在追加一个归档信息
		if(StringUtils.isEmpty(clientId)){
			throw new RuntimeException("lose user");
		}
		this.specifyParticipantByWorkcode(clientId, workcode, clientId, false,
				"03");
		//commitMyTask(usercode, workcode);

		this.nextByAuto(workcode, usercode);

//		console
//				.coreSqlhandle("update t_wf_worklist set EXECUTESTATUS='02',donetime='"
//						+ (new Timestamp(System.currentTimeMillis()))
//								.toString()
//						+ "' where workcode='"
//						+ wl.getWorkcode() + "'");
//		console
//				.coreSqlhandle("update t_wf_runtime set STATUSNOW='02' where runtimeid='"
//						+ wl.getRuntimeid() + "'");
		return "";
	}

	public int outTimeAlarm() {

		int count = 0;
		String sql = "select w2.lsh lsh, w3.d0 tip,w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w2.createtime,w2.limitime limitime,w2.usercode usercode,w2.commitername commitername from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01'"
				+ " and w2.statusnow='01"
				+ "' and w2.types in('01','02') and w2.limitime is not null and w2.msg!='1' ";
		try {
			WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
			List listIsReader = wfview.coreSqlview(sql);

			for (Iterator iterator = listIsReader.iterator(); iterator
					.hasNext();) {
				Map object = (Map) iterator.next();
				String createtime = (String) object.get("createtime");
				String usercode = (String) object.get("usercode");
				String workcode = (String) object.get("workcode");
				String commitername = (String) object.get("commitername");

				String tip = (String) object.get("tip");
				int limittime = (Integer) object.get("limitime");
				long time = Timestamp.valueOf(createtime).getTime();
				long timeOver = System.currentTimeMillis() - time - limittime
						* 3600 * 1000;
				if (timeOver / (3600 * 1000) <= 0)
					timeOver = 0;
				if (timeOver > 0) {

					String context = "电子工作流平台友情提醒:" + commitername
							+ "发给您的待处理任务:" + tip + " 已经超出最后办理时限:" + timeOver
							/ (3600 * 1000) + "小时,请尽快登陆新版电子工作流平台10.51.176.5处理";

					String rs = Message.toMessageByUser(usercode, context);
					if ("ok".equals(rs)) {
						String lsh = (String) object.get("lsh");
						if (lsh != null && !lsh.equals("")) {
							String update = "update t_wf_participant set msg='1' where lsh='"
									+ lsh + "'";
							wfview.coreSqlview(update);
						}
					}
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public List<TWfWorklistExt> worklistByPage(String clientid,
			String processid, boolean mode, int from, int size,
			String listtype, String filterColumn, String filterValue,
			boolean desc) throws Exception {
		// 预先装载工作流句柄
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03')";
		}
		String processidStr = "";
		if (processid != null && !"".equals(processid)) {
			processidStr = "and w1.processid='" + processid + "'";
		}

		// 获得所有许可的代办任务
		String loadworklist = "";

		if ("01".equals(listtype)) {
			// 待办任务
			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"
					+ clientid
					+ "' and w2.statusnow='01"
					+ "' "
					+ processidStr
					+ " and w2.types in "
					+ opemode
					+ " limit "
					+ from
					+ ","
					+ (from + size);
		} else if ("02".equals(clientid)) {
			// 所有结束任务但未归档

			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
					+ clientid
					+ "' and w2.statusnow='02' and wx.STATUSNOW='01'";
		} else if ("03".equals(clientid)) {
			// 所有结束任务且已经归档

			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.username,'[',w2.usercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_runtime wx on w1.RUNTIMEID=wx.RUNTIMEID left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where  w2.usercode='"
					+ clientid
					+ "' and w2.statusnow='02' and wx.STATUSNOW='02'";
		} else {
			// 所有个人任务
			loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"
					+ clientid
					+ "' "
					+ processidStr
					+ " and w2.types in "
					+ opemode + " limit " + from + "," + (from + size);
		}

		List list = wfview.coreSqlview(loadworklist);

		// 获得所有许可的活动实例和相关变量信息构造TWfWorklistExt对象返回
		List listWorklist = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String actid = (String) object.get("actid");
			String runtimeid = (String) object.get("runtimeid");
			String workcode = (String) object.get("workcode");
			String startime = (String) object.get("starttime");
			String userinfo = (String) object.get("userinfo");
			String processidx = (String) object.get("processid");
			String types = (String) object.get("types");
			boolean sync = "1".equals((String) object.get("sync"));

			String appid = getAppNameByRuntimeId(runtimeid);

			TWfWorklistExt wfext = new TWfWorklistExt();
			TWfActive act = this.loadRuntimeActive(processidx, actid, appid,
					"", runtimeid);

			wfext.setAct(act);
			wfext.setParticipant(userinfo);
			wfext.setWorkcode(workcode);
			wfext.setStarttime(startime);

			wfext.setRuntimeid(runtimeid);
			wfext.setOperatemode(types);

			String modetip = "";
			if (sync) {
				modetip = "同步";
			}
			String operateMode = "";
			if ("01".equals(types)) {
				operateMode = "" + modetip;
			} else if ("02".equals(types)) {
				operateMode = "抄送" + "|" + modetip;
			} else {
				operateMode = "抄阅";
			}

			wfext.setOperatemodetip(operateMode);
			act.setName(act.getName() + "|" + operateMode + "|");

			// 获得流程的所有相关变量
			List listRev = wfview.fetchRelevantVar((String) object
					.get("runtimeid"));

			// 针对默认的流程关键变量设置参数
			List linkToDyColumn = new ArrayList();
			for (Iterator iterator3 = listRev.iterator(); iterator3.hasNext();) {
				TWfRelevantvar name = (TWfRelevantvar) iterator3.next();
				String filedid = name.getDatafieldid();
				boolean iskey = false;
				for (int i = 0; i < AppHandleIfc._CORE_KEY_VAR.length; i++) {
					if (AppHandleIfc._CORE_KEY_VAR[i].equalsIgnoreCase(filedid)) {
						String valuenow = name.getValuenow();
						BeanUtils.setProperty(wfext, filedid, valuenow);
						iskey = true;
						break;
					}
				}
				if (!iskey) {
					linkToDyColumn.add(name);
				}

			}
			wfext.setRev(linkToDyColumn);
			listWorklist.add(wfext);// 添加符合条件的活动任务

		}
		return listWorklist;

	}

	public boolean checkFirstAct(String workcode) throws Exception {
		TWfWorklist wf = this.loadWorklist(workcode);
		Activity act = this.loadProcess(wf.getProcessid()).getActivity(
				wf.getActivityid());
		return act.isStartActivity();

	}

	public void specifyCuibangByWorkcode(String commiter, String workcode,
			String participant) throws Exception {
		String types = "01";// 01表示任务主办者
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		specifyOperateByWorkcode(commiter, workcode, participant, types, false,
				"", "02", "04", actid, actname);

	}

	public void specifyzhuanbangByWorkcode(String commiter, String workcode,
			String participant) throws Exception {
		String types = "01";// 01表示任务主办者
		TWfWorklist worklist = this.loadWorklist(workcode);
		String actid = worklist.getActivityid();
		String actname = this.loadProcess(worklist.getProcessid()).getActivity(
				actid).getName();
		specifyOperateByWorkcode(commiter, workcode, participant, types, false,
				"", "01", "05", actid, actname);

	}

	public String nextToZhuanbang(String workcode, String clientId)
			throws Exception {
		try {
			String usercode = StringUtils.substringBetween(clientId, "[", "]");
			commitMyTask(usercode, workcode);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	public String newProcess(String processid, String participant,
			String busstype, String busstip, String bussid, String bussurl)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String runtimeid = console.newProcess(processid);
		// 流程相关变量注入提交者信息
		updateRev(runtimeid, this._DEFAULT_REV_KEY_CUSTOMER, participant);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSID, bussid);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSURL, bussurl);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSTYPE, busstype);

		this.setAppNameByRuntimeId(runtimeid, busstype);

		List appdy = AppEntry.iv().wf2dyformBindCfg(busstype);
		// 动态复制动态表单中的业务数据到工作流中
		StringBuffer rev_view_sql_column = new StringBuffer();
		StringBuffer rev_view_sql_value = new StringBuffer();
		int count = 0;
		for (Iterator iterator = appdy.iterator(); iterator.hasNext();) {
			TWfRelevant object = (TWfRelevant) iterator.next();
			String revid = object.getRevid();
			String columnid = object.getRev2column();
			if (columnid == null) {
				continue;
			}
			if (revid.startsWith("r_")) {
				continue;
			}
			String formcode = object.getRev2formcode();
			DyFormData data = DyEntry.iv().loadData(formcode, bussid);
			String value = BeanUtils.getProperty(data, columnid);
			updateRev(runtimeid, revid, value);
			value=StringUtils.replace(value, "'", "’");
			rev_view_sql_value.append(",'" + value + "'");
			rev_view_sql_column.append(",d" + count++);
		}
		String rev_view_sql_column_s = "runtimeid,appname,lsh"
				+ rev_view_sql_column.toString();
		String rev_view_sql_value_s = "'" + runtimeid + "','" + busstype
				+ "','" + bussid + "'" + rev_view_sql_value.toString();
		String rev_view_sql = "insert into t_wf_relevantvar_tmp("
				+ rev_view_sql_column_s + ")values(" + rev_view_sql_value_s
				+ ")";
		console.coreSqlhandle(rev_view_sql);

		return runtimeid;

	}

	public List<TWfActive> listNextRouteActive(String processid,
			String activeid, String runtimeid, String commiter)
			throws Exception {

		String appname = this.getVarByRuntimeId(runtimeid,
				this._DEFAULT_REV_KEY_BUSSTYPE);
		if (StringUtils.isEmpty(appname)) {
			throw new RuntimeException("缺少应用框架Naturalname");
		}
		return listNextActivityRefAppCfg(processid, activeid, runtimeid, true,
				appname, commiter);

	}

	public List<TWfActivePass> listNextBackActive(String runtimeid)
			throws Exception {
		String appname = this.getVarByRuntimeId(runtimeid,
				this._DEFAULT_REV_KEY_BUSSTYPE);

		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		List list = wfview.fetchDoneWorklist(runtimeid);
		List listWorklist = new ArrayList();

		Map repeatCheck = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			String processid = object.getProcessid();
			String actid = object.getActivityid();
			if (repeatCheck.containsKey(actid)) {
				continue;
			} else {
				repeatCheck.put(actid, "");
			}

			String workcode = object.getWorkcode();

			WorkflowProcess wfDesign = this.loadProcess(processid);

			Activity act = wfDesign.getActivity(actid);
			TWfActive actx = makeTWfActive(act, appname, "", runtimeid);

			TWfActivePass actPass = new TWfActivePass();
			BeanUtils.copyProperties(actPass, actx);

			String sql = "select usercode,username,types,sync from t_wf_participant where workcode='"
					+ workcode + "'";

			List info = WfEntry.iv().useCoreView().coreSqlview(sql);

			List participantold = new ArrayList();
			List assistant = new ArrayList();
			List reader = new ArrayList();

			for (Iterator iterator2 = info.iterator(); iterator2.hasNext();) {
				Map object2 = (Map) iterator2.next();
				String usercode = (String) object2.get("usercode");
				String username = (String) object2.get("username");
				String userinfo = username + "[" + usercode + "]";
				String types = (String) object2.get("types");
				String sync = (String) object2.get("sync");

				if ("01".equals(types)) {
					participantold.add(userinfo);
				} else if ("02".equals(types)) {
					assistant.add(userinfo);
					if ("1".equals(sync)) {
						actPass.setAssistantSync(true);
					}
				} else if ("03".equals(types)) {
					reader.add(userinfo);
				}
			}

			actPass.setAssistant((String[]) assistant.toArray(new String[0]));
			actPass.setReader((String[]) reader.toArray(new String[0]));
			actPass.setParticipantOld((String[]) participantold
					.toArray(new String[0]));
			actPass.setName(actPass.getName() + "["
					+ StringUtils.substring(object.getDonetime(), 0, 16) + "]");

			listWorklist.add(actPass);// 添加符合条件的活动任务

		}

		return listWorklist;
	}

	public List<TWfActive> listNextDesignActive(String processid,
			String activeid, String runtimeid, String commiter)
			throws Exception {
		String appname = this.getVarByRuntimeId(runtimeid,
				this._DEFAULT_REV_KEY_BUSSTYPE);
		if (StringUtils.isEmpty(appname)) {
			throw new RuntimeException("缺少应用框架Naturalname");
		}

		return listNextActivityRefAppCfg(processid, activeid, runtimeid, false,
				appname, commiter);

	}

	private List<TWfActive> listNextActivityRefAppCfg(String processid,
			String activeid, String runtimeid, boolean needroute, String appid,
			String commiter) throws Exception {
		WorkflowProcess wfDesign = this.loadProcess(processid);

		List listRouteAimActivity = new ArrayList();

		Activity act = wfDesign.getActivity(activeid);
		if (act == null) {
			throw new Exception(RuntimeInfo.OE_WF_DEF_ERR_006);
		}
		Map map = act.getEfferentTransitions();
		if (map == null) {// 尾活动
			return new ArrayList();
		}

		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		
		// 预处理SOA脚本
		//soaScript_preparecare(runtimeid, activeid);

		findNext(map, needroute, console, listRouteAimActivity, runtimeid);
		return makeTWfActive(listRouteAimActivity, appid, commiter, runtimeid);

	}

	private void findNext(Map map, boolean needroute, WorkflowConsole console,
			List listRouteAimActivity, String runtimeid) throws Exception {
		for (Iterator itr = map.values().iterator(); itr.hasNext();) {
			Transition trans = (Transition) itr.next();
			if (trans == null) {
				continue;
			}
			Activity actNext = trans.getToActivity();
			if (actNext.getImplementation() == null) {// 空节点忽略
				Map mapx = actNext.getEfferentTransitions();
				findNext(mapx, needroute, console, listRouteAimActivity,
						runtimeid);
				continue;
			}
			if (needroute) {// 如果需要路由那么执行路由脚本
				Condition condition = trans.getCondition();
				String script = null;
				if (condition != null) {
					script = condition.getValue();
				}
				String rsinfo = "true";
				if (script != null && !script.equals("")) {

					rsinfo = console.exeScript(script, runtimeid);
				}
				if ("true".equals(rsinfo)) {
					listRouteAimActivity.add(actNext);
				}
			} else {
				listRouteAimActivity.add(actNext);
			}

		}

	}

	/**
	 * 提供预执行SOA脚本的逻辑，这个会导致SOA脚本异步的被执行两次，这个后期需要考虑处理<br>
	 * 或者要求SOA脚本（异步）的在编写的时候需要注意能重复或者能根据条件判断只执行一次
	 * 
	 * @param runtimeid
	 * @param actid
	 * @throws Exception
	 */
	private void soaScript_preparecare(String runtimeid, String actid)
			throws Exception {
		WorkflowView wfview = null;
		WorkflowConsole wfhandle = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			wfhandle = (WorkflowConsole) RmiEntry.iv("wfhandle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject pojProcess = new UmsProtectedobject();
		pojProcess.setExtendattribute(wfview.loadRuntime(runtimeid)
				.getProcessid());
		List list = resourceRmi.fetchResource(pojProcess, null);
		pojProcess = (UmsProtectedobject) list.get(0);
		String naturalname = pojProcess.getNaturalname() + "."
				+ actid.toUpperCase();
		String eaixml = null;
		try {
			eaixml = resourceRmi.loadResourceByNatural(naturalname)
					.getExtendattribute();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无SOA配置资源的流程");
		}
		if (StringUtils.isNotEmpty(eaixml)) {
			if (StringUtils.contains(eaixml, "activity sync=\"true\"/")) {
				eaixml = StringUtils.substringBetween(eaixml, "![CDATA[",
						"]]></script></soa>");
				wfhandle.exeScript(eaixml, runtimeid);
			}

		}

	}

	public TWfActive listNextZhuanbangActive(String workcode, String commiter)
			throws Exception {
		if (StringUtils.isEmpty(commiter)) {
			throw new RuntimeException("缺少转办提交者");
		}

		TWfWorklist worklist = this.loadWorklist(workcode);
		String appname = this.getVarByRuntimeId(worklist.getRuntimeid(),
				this._DEFAULT_REV_KEY_BUSSTYPE);
		Activity act = this.loadProcess(worklist.getProcessid()).getActivity(
				worklist.getActivityid());
		return this.makeTWfActive(act, appname, commiter, worklist
				.getRuntimeid());

	}

	public TWfActive listCurrentActive(String appname, String workcode,
			String commiter) throws Exception {
		if (StringUtils.isNotEmpty(workcode)) {
			return listNextZhuanbangActive(workcode, commiter);
		} else {
			String processid = AppEntry.iv().loadApp(appname)
					.getWorkflowCode_();
			WorkflowProcess wf = WfEntry.iv().loadProcess(processid);
			Activity[] act = wf.getActivity();
			for (int i = 0; i < act.length; i++) {
				if (act[i].isStartActivity()) {
					return this.makeTWfActive(act[i], appname, null, null);
				}
			}
		}
		return null;

	}

	public void specifyParticipantAutoByWorkcode(String commiter,
			String workcode) throws Exception {
		// TODO Auto-generated method stub

	}

	public String getActivityName(String appid, String workcode)
			throws Exception {
		if (StringUtils.isNotEmpty(workcode)) {
			String actid = this.loadWorklist(workcode).getActivityid();
			String processid = this.loadWorklist(workcode).getProcessid();
			System.out.println("--------------------------------------");
			System.out.println("actid:"+this.loadWorklist(workcode).getActivityid());
			System.out.println("processid:"+processid);
			System.out.println("1111:"+this.loadProcess(processid).getActivity(actid).getName());
			System.out.println("--------------------------------------");
			return this.loadProcess(processid).getActivity(actid).getName();
		}
		if (StringUtils.isEmpty(appid)) {
			return "";
		}
		String processid = AppEntry.iv().loadApp(appid).getWorkflowCode_();
		Activity[] actx = this.loadProcess(processid).getActivity();
		for (int i = 0; i < actx.length; i++) {
			actx[i].isStartActivity();
			return actx[i].getName();
		}
		return "";

	}

	public TWfActive loadActive(String appname, String workcode)
			throws Exception {
		TWfWorklist worklist = this.loadWorklist(workcode);
		Activity act = this.loadProcess(worklist.getProcessid()).getActivity(
				worklist.getActivityid());
		return this.makeTWfActive(act, appname, null, worklist.getRuntimeid());
	}

	public void WakeUpProcess(String runtimeid) throws Exception {
		WorkflowView wfview = null;
		WorkflowConsole console = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(runtimeid)) {
			List list = wfview.fetchExceptionWorklist(runtimeid);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TWfWorklist object = (TWfWorklist) iterator.next();
				console.coreSqlhandle("update t_wf_worklist set EXECUTESTATUS='01' where workcode='"+object.getWorkcode()+"'");
			}
		}
	}

	public void pendingProcess(String runtimeid) throws Exception {

		WorkflowConsole console = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (StringUtils.isNotEmpty(runtimeid)) {
			List list = this.listAllRunningWorklistByRuntimeid(runtimeid);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TWfWorklist object = (TWfWorklist) iterator.next();
				console.coreSqlhandle("update t_wf_worklist set EXECUTESTATUS='03' where workcode='"+object.getWorkcode()+"'");
			}

		}

	}

 
	public String fetchFirstActivityId(String processid) {
		Activity[] act=null;
		try {
			act = WfEntry.iv().loadProcess(processid).getActivity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String firstActname = null;
		for (int i = 0; i < act.length; i++) {
			if (act[i].isStartActivity()) {
				return act[i].getId();
			}
		}
		throw new RuntimeException("流程定义异常丢失首节点:"+processid);
	}


}
