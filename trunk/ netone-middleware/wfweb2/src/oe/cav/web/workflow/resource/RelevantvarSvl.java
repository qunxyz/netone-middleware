package oe.cav.web.workflow.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

public class RelevantvarSvl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String runtimeid = req.getParameter("runtimeid");

		WorkflowView wfview = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List relevantvarResult = new ArrayList();
		List fetchRelevantVarinfo = wfview.fetchRelevantVar(runtimeid);
		for (Iterator iterator = fetchRelevantVarinfo.iterator(); iterator
				.hasNext();) {
			TWfRelevantvar object = (TWfRelevantvar) iterator.next();
			String varname = StringUtils.substringBetween(object
					.getExtendattribute(), ":", ";");
			object.setNameExt(varname);
			relevantvarResult.add(object);
		}
		req.setAttribute("relevantvarResltInfo", relevantvarResult);
		req.getRequestDispatcher("/workflow/monitor/Relevantvarinfo.jsp")
				.forward(req, resp);
	}
}
