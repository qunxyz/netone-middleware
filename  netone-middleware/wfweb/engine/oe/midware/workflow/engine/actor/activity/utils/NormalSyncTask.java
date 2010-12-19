package oe.midware.workflow.engine.actor.activity.utils;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 在SOA配置中要求执行的外部任务，基于普通进程模式执行
 * 
 * @author chen.jia.xun
 * 
 */
public class NormalSyncTask {

	String runtimeid;

	String workcode;

	public NormalSyncTask(String runtimeid, String workcode) {
		this.runtimeid = runtimeid;
		this.workcode = workcode;
		run();
	}

	public void run() {
		WorkflowView wfview = null;
		WorkflowConsole wfhandle = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			wfhandle = (WorkflowConsole) RmiEntry.iv("wfhandle");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			// 资源的访问控制句柄
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject pojProcess = new UmsProtectedobject();
			pojProcess.setExtendattribute(wfview.loadRuntime(runtimeid)
					.getProcessid());
			List list = resourceRmi.fetchResource(pojProcess, null);
			pojProcess = (UmsProtectedobject) list.get(0);
			String naturalname = pojProcess.getNaturalname()
					+ "."
					+ wfview.loadWorklist(workcode).getActivityid()
							.toUpperCase();
			String eaixml = resourceRmi.loadResourceByNatural(naturalname)
					.getExtendattribute();

			wfhandle.eai(eaixml, runtimeid, workcode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
