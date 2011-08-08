package oe.midware.workflow.engine.actor.datafield;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.datafield.DataFieldDeployActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.misc.ExtendedAttributes;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class DataFieldDeployActorImp implements DataFieldDeployActor {
	private Log _log = LogFactory.getLog(DataFieldDeployActorImp.class);

	public void execute(TWfRuntime runtime) {
		if (runtime == null) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_001);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_001);
		}
		List list = readyForRelevantvar(runtime);
		OrmerEntry.fetchOrmer().fetchSerializer().creates(list);
	}

	private List readyForRelevantvar(TWfRuntime runtime) {
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");
		WorkflowProcess workflowProcess = null;
		try {
			workflowProcess = runtimemonitor.fetchWorkflowProcess(runtime
					.getProcessid());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataField[] datafield = workflowProcess.getDataField();
		List list = new ArrayList();
		String processId = runtime.getProcessid();
		for (int i = 0; i < datafield.length; i++) {
			TWfRelevantvar rev = new TWfRelevantvar();
			rev.setDatafieldid(datafield[i].getId());
			rev.setRuntimeid(runtime.getRuntimeid());
			rev.setValuenow(datafield[i].getInitialValue());
			rev.setProcessid(processId);

			Map ext = new HashMap();
			try {

				ExtendedAttributes exts = datafield[i].getExtendedAttributes();
				if (exts != null) {
					ext = exts.getMap();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer butvalue = new StringBuffer("name:"
					+ datafield[i].getName() + ";");
			for (Iterator itr = ext.keySet().iterator(); itr.hasNext();) {
				String key = (String) itr.next();
				String value = (String) ext.get(key);
				butvalue.append(key + ":" + value + ";");
			}

			rev.setExtendattribute(butvalue.toString());
			list.add(rev);
		}
		return list;
	}

}
