package oe.cav.web.workflow.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

public class WorkListInfoSvl extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String runtimeid = req.getParameter("runtimeid");

		WorkflowView wfview = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		List workList = new ArrayList();
		List workListinfo = new ArrayList();
		WorkflowProcess workflowProcessinfo = null;
		Activity activityinfo = new Activity();
		workList = wfview.listWorklist(runtimeid);
		for (Iterator iterator = workList.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			if (workflowProcessinfo == null) {
				workflowProcessinfo = wfview.loadProcess(object.getProcessid());
			}
			activityinfo = workflowProcessinfo.getActivity(object
					.getActivityid());

			// 判断流程状态
			if (object.getExecutestatus().equals(
					RuntimeProcessRef.STATUS_READY[0])) {
				object.setExecutestatus(RuntimeProcessRef.STATUS_READY[1]);
			}
			if (object.getExecutestatus().equals(
					RuntimeProcessRef.STATUS_RUNNING[0])) {
				object.setExecutestatus(RuntimeProcessRef.STATUS_RUNNING[1]);
			}
			if (object.getExecutestatus().equals(
					RuntimeProcessRef.STATUS_END[0])) {
				object.setExecutestatus(RuntimeProcessRef.STATUS_END[1]);
			}
			if (object.getExecutestatus().equals(
					RuntimeProcessRef.STATUS_EXCEPTION[0])) {
				object.setExecutestatus(RuntimeProcessRef.STATUS_EXCEPTION[1]);
			}
			if (object.getExecutestatus().equals(
					RuntimeProcessRef.STATUS_QUASH[0])) {
				object.setExecutestatus(RuntimeProcessRef.STATUS_QUASH[1]);
			}

			// 判断活动节点类型
			if (object.getTypes().equals(ActivityRef.ACT_SUBFLOW_KEY[0])) {
				object.setTypes(ActivityRef.ACT_SUBFLOW_KEY[1]);
			}
			if (object.getTypes().equals(ActivityRef.ACT_TOOLS_KEY[0])) {
				object.setTypes(ActivityRef.ACT_TOOLS_KEY[1]);
			}
			if (object.getTypes().equals(ActivityRef.ACT_NORMAL_KEY[0])) {
				object.setTypes(ActivityRef.ACT_NORMAL_KEY[1]);
			}
			if (object.getTypes().equals(ActivityRef.ACT_ROUTE_KEY[0])) {
				object.setTypes(ActivityRef.ACT_ROUTE_KEY[1]);
			}
			if (object.getTypes().equals(ActivityRef.ACT_SUBFLOW_SYNC_KEY[0])) {
				object.setTypes(ActivityRef.ACT_SUBFLOW_SYNC_KEY[1]);
			}

			object.setNameExt(activityinfo.getName());
			workListinfo.add(object);
		}

		req.setAttribute("WorkListinfo", workListinfo);
		req.getRequestDispatcher("/workflow/monitor/WorkListInfo.jsp").forward(
				req, resp);

	}
}
