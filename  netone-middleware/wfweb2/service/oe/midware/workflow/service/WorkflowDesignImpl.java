package oe.midware.workflow.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import oe.frame.bus.workflow.WfEntry;
import oe.mid.io.CommonIoTools;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.track.WorkflowRuntime;
import oe.midware.workflow.xpdlresource.XpdlResourceDao;

public class WorkflowDesignImpl extends UnicastRemoteObject implements
		WorkflowDesign {

	XpdlResourceDao xpdlResourceDao;

	WorkflowRuntime workflowTrackRuntime;

	XpdlModelHandler xpdlModelHandler;

	public WorkflowDesignImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean dropOpe(String processid) throws RemoteException {
		return xpdlResourceDao.dropOpe(processid);
	}

	public void saveOpe(String context, String processid, String processname)
			throws RemoteException {
		xpdlResourceDao.saveOpe(context, processid, processname);

	}

	public boolean updateOpe(String context, String processid,
			String processname) throws RemoteException {
		return xpdlResourceDao.updateOpe(context, processid, processname);

	}

	public String viewOpe(String processid) throws RemoteException {
		return xpdlResourceDao.viewOpe(processid);
	}

	public String xpdldescription(String processid) throws RemoteException {
		return xpdlModelHandler.fetchXpdlpackageDoc(processid);

	}

	public XpdlResourceDao getXpdlResourceDao() {
		return xpdlResourceDao;
	}

	public void setXpdlResourceDao(XpdlResourceDao xpdlResourceDao) {
		this.xpdlResourceDao = xpdlResourceDao;
	}

	public WorkflowRuntime getWorkflowTrackRuntime() {
		return workflowTrackRuntime;
	}

	public void setWorkflowTrackRuntime(WorkflowRuntime workflowTrackRuntime) {
		this.workflowTrackRuntime = workflowTrackRuntime;
	}

	public XpdlModelHandler getXpdlModelHandler() {
		return xpdlModelHandler;
	}

	public void setXpdlModelHandler(XpdlModelHandler xpdlModelHandler) {
		this.xpdlModelHandler = xpdlModelHandler;
	}

	public String controlFlow(String runtimeId) throws RemoteException {
		// TODO Auto-generated method stub
		return workflowTrackRuntime.controlFlow(runtimeId);
	}

	public String useFlow(String runtimeId) throws RemoteException {
		// TODO Auto-generated method stub
		return workflowTrackRuntime.useFlow(runtimeId);
	}

	public String viewFlow(String processid) throws RemoteException {
		// TODO Auto-generated method stub
		return workflowTrackRuntime.viewFlow(processid);
	}



}
