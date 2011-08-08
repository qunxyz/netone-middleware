package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 流程列表-子流程应用
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RListUseAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws RemoteException {

		String processid = request.getParameter("processid"); // 标志位

		WorkflowView wfview = null;
		String processname = null;
		List list = null;
		String name = null;
		
		String isQuery = request.getParameter("operate");
		System.out.println(isQuery);
		List testList = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			WorkflowProcess wfx = wfview.fetchWorkflowProcess(processid);
			name = wfx.getName();
			String condition = " order by starttime desc";
			if(isQuery == null || !isQuery.equals("t")){
	
				//List l = wfview.listxinstance(processid, condition);

				String sqlStr = "select *from t_wf_runtime where processid='" + processid +"'";
				list = wfview.coreSqlview(sqlStr);

				WorkflowProcess proc = wfview.fetchWorkflowProcess(processid);
			}else{
				String queryName = request.getParameter("pname");
				String queryState = request.getParameter("pstate");
				String queryStartTime = request.getParameter("starttime");
				//System.out.println("start"+queryStartTime);
				String queryEndTime = request.getParameter("endtime");
				boolean isFirst = false;
				String sqlStr = "select *from t_wf_runtime";
				if(queryName != null && !queryName.equals("")){
					sqlStr += " where processid='" + queryName + "'";
					isFirst = true;
				}
				if(!queryState.equals("10")){
					if(isFirst)
						sqlStr += " and ";
					else
						sqlStr += " where ";
					sqlStr += "statusnow='" + queryState + "'";
				}
				if(queryStartTime != null && !queryStartTime.equals("")){
					if(isFirst)
						sqlStr += " and ";
					else{
						sqlStr += " where ";
						isFirst = true;
					}
					sqlStr += "starttime>='" + queryStartTime + "'";
				}
				if(queryEndTime != null && !queryEndTime.equals("")){
					if(isFirst)
						sqlStr += " and ";
					else{
						sqlStr += " where ";
					}
					sqlStr += "endtime<='" + queryEndTime + "'";
				}
				sqlStr += condition;
				list = wfview.coreSqlview(sqlStr);
				//[LIMITS, BELONGRUNTIMEID, BELONGACTIVITYID, RUNTIMEID, PARTICIPANT, PRIORITY, EXTENDATTRIBUTE, PROCESSID, STARTTIME, ENDTIME, KIND, STATUSNOW]
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("processname", processname);
		request.setAttribute("listinfo", list);
		request.setAttribute("pname", name);
		return mapping.getInputForward();
	}

}
