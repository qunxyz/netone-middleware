package oe.common.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import oe.common.security3a.Security3AIfc;
import oe.common.security3a.SecurityEntry;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;

/**
 * DRP工作流接口实现，基于NETONE中间件
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class TWfConsoleImpl implements TWfConsoleIfc {

	// 流程数据缓存
	static Map allflow = new HashMap();

	public List<TWfWorklistExt> worklist(String customer) throws Exception {
		// 预先装载工作流句柄
		CupmRmi cupm = null;
		ResourceRmi rsrmi = null;
		WorkflowView wfview = null;

		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		List permissonAllowFlow = new ArrayList();

		List worklist = wfview
				.coreSqlview("select * from t_wf_worklist where EXECUTESTATUS='"
						+ RuntimeWorklistRef.STATUS_RUNNING[0] + "'");
		List listWorklist = new ArrayList();
		for (Iterator iterator = worklist.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();

			String activeNaturalname = object.get("PROCESSID") + "."
					+ object.get("ACTIVITYID");
			boolean available = cupm.checkUserPermission(
					Security3AIfc.DEMAIN_CODE, customer, activeNaturalname,
					Security3AIfc.PERMISSION_ALLOW);
			if (available) {
				TWfWorklistExt wfext = new TWfWorklistExt();
				wfext.setWorkcode((String) object.get("WORKCODE"));
				wfext.setStarttime((String) object.get("STARTTIME"));
				String processid = (String) object.get("PROCESSID");
				String activeid = (String) object.get("ACTIVITYID");
				wfext.setProcessid(processid);
				wfext.setActiveid(activeid);
				WorkflowProcess wfDesign = null;
				String nameExt = null;
				String processnameExt = null;
				try {
					if (!allflow.containsKey(processid)) {
						wfDesign = wfview.loadProcess(processid);
						allflow.put(processid, wfDesign);
					}
					wfDesign = (WorkflowProcess) allflow.get(processid);

					nameExt = wfDesign.getActivity(activeid).getName();
					processnameExt = StringUtils.substringBetween(wfDesign
							.getName(), "[", "]");
				} catch (Exception e) {
					e.printStackTrace();
					continue;// 流程设计文件丢失
				}
				wfext.setProcessName(processnameExt);
				wfext.setActiveName(nameExt);
				wfext.setRuntimeid((String) object.get("RUNTIMEID"));
				// 获得流程的所有相关变量
				List listRev = wfview.fetchRelevantVar((String) object
						.get("RUNTIMEID"));
				for (Iterator iterator3 = listRev.iterator(); iterator3
						.hasNext();) {
					TWfRelevantvar name = (TWfRelevantvar) iterator3.next();
					String filedid = name.getDatafieldid();
					String valuenow = name.getValuenow();

					BeanUtils.setProperty(wfext, filedid, valuenow);

				}

				listWorklist.add(wfext);// 添加符合条件的活动任务
			}

		}

		return areaPermission(cupm, listWorklist, customer);
	}

	/**
	 * 检查区域权限
	 * 
	 * @param cupm
	 * @param listWorklist
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	private List areaPermission(CupmRmi cupm, List listWorklist, String customer)
			throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");

		List availableAreaWorklist = new ArrayList();
		for (Iterator iterator = listWorklist.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			// String clientid = StringUtils.substringBetween(
			// object.getCustomer(), "[", "]");
			String clientid = object.getCustomer();
			try {
				String belongtoid = SecurityEntry.iv().loadUser(clientid)
						.getBelongto();
				String deptInfo = rs.loadResourceById(belongtoid)
						.getNaturalname();
				boolean checkrs = cupm.checkUserPermission(
						Security3AIfc.DEMAIN_CODE, customer, deptInfo,
						Security3AIfc.PERMISSION_ALLOW);
				if (checkrs) {
					availableAreaWorklist.add(object);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return availableAreaWorklist;
	}

	public String next(String workcode, String participant, String busstip)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);

		if (wl == null
				|| !RuntimeWorklistRef.STATUS_RUNNING[0].equals(wl
						.getExecutestatus())) {
			return this.OPE_TIP_ERROR + "无法提交非运行状态的活动";
		}
		console.commitActivity(workcode);

		// 流程相关变量注入提交者信息
		updateRev(wl.getRuntimeid(), this._DEFAULT_REV_KEY_CUSTOMER,
				participant);
		updateRev(wl.getRuntimeid(), this._DEFAULT_REV_KEY_BUSSTIP, busstip);
		return this.OPE_TIP_SUCCESS;
	}

	public String newProcess(String processid, String participant,
			String busstype, String busstip, String bussid, String bussurl)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		String runtimeid = console.newProcess(processid);
		// 流程相关变量注入提交者信息
		updateRev(runtimeid, this._DEFAULT_REV_KEY_CUSTOMER, participant);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSTYPE, busstype);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSID, bussid);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSURL, bussurl);
		updateRev(runtimeid, this._DEFAULT_REV_KEY_BUSSTIP, busstip);
		return runtimeid;
	}

	private void updateRev(String runtimeid, String code, String value)
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
	}

	public String nextByRuntimeid(String runtimeid, String participant)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		List list = wfview.fetchRunningWorklist(runtimeid);

		if (list.size() > 0) {// 提交审核操作
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				TWfWorklist element = (TWfWorklist) iter.next();
				// 流程应用,提交活动
				if (element == null
						|| !RuntimeWorklistRef.STATUS_RUNNING[0].equals(element
								.getExecutestatus())) {
					return this.OPE_TIP_ERROR + "无法提交非运行状态的活动";
				}
				console.commitActivity(element.getWorkcode());
			}
		}
		return this.OPE_TIP_SUCCESS;

	}

	public String jump(String workcode, String participant, String activeid)
			throws Exception {
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);

		if (wl == null
				|| !RuntimeWorklistRef.STATUS_RUNNING[0].equals(wl
						.getExecutestatus())) {
			return this.OPE_TIP_ERROR + "无法提交非运行状态的活动";
		}
		console.commitActivityByManual(workcode, activeid);
		// 流程相关变量注入提交者信息

		updateRev(wl.getRuntimeid(), this._DEFAULT_REV_KEY_CUSTOMER,
				participant);
		return this.OPE_TIP_SUCCESS;

	}

	public List<TWfActive> listActiveID(String processid) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		Activity[] act = wfview.loadProcess(processid).getActivity();
		List list = new ArrayList();
		for (int i = 0; i < act.length; i++) {
			TWfActive actx = new TWfActive();
			actx.setName(act[i].getName());
			actx.setNaturalname(act[i].getId());
			list.add(actx);
		}
		return list;
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

		return RuntimeProcessRef.STATUS_END[0].equals(wfview.loadRuntime(
				runtimeId).getStatusnow());
	}

	public String back(String workcode, String clientId, String busstip)
			throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);
		String runtimeid = wl.getRuntimeid();
		// 找到最后一次提交的节点，忽略掉非完成状态的节点、路由节点
		String extcondition = "and donetime is not null and types!='"
				+ ActivityRef.ACT_ROUTE_KEY[0] + "' order by DONETIME desc";
		List list = wfview.listxWorkListInstance(runtimeid, extcondition);

		if (list == null || list.size() == 0) {
			return this.OPE_TIP_ERROR + "首节点无法回退";
		}
		TWfWorklist lastCommitWorklist = (TWfWorklist) list.get(0);
		String actid = lastCommitWorklist.getActivityid();

		updateRev(wl.getRuntimeid(), this._DEFAULT_REV_KEY_BUSSTIP, busstip);
		// 将业务状态数据减一
		try {
			String rev = this.getVarByRuntimeId(lastCommitWorklist
					.getRuntimeid(), this._DEFAULT_REV_KEY_BUSSSTATUS);
			Integer newStatus = Integer.parseInt(rev) - 1;
			updateRev(wl.getRuntimeid(), this._DEFAULT_REV_KEY_BUSSSTATUS,
					newStatus.toString());
		} catch (Exception e) {
			return this.OPE_TIP_ERROR + e.getMessage();
		}

		// 执行跳转
		return this.jump(workcode, clientId, actid);

	}

	public String back(String workcode, String clientId) throws Exception {
		// TODO Auto-generated method stub
		return this.back(workcode, clientId, "");
	}

	public String next(String workcode, String clientId) throws Exception {
		// TODO Auto-generated method stub
		return this.next(workcode, clientId, "");
	}

	public String getRuntimeIdByWorkcode(String workcode) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		TWfWorklist wl = wfview.loadWorklist(workcode);
		return wl.getRuntimeid();

	}

	public Map allAvailableProcess() {
		// TODO Auto-generated method stub
		return allflow;
	}

}
