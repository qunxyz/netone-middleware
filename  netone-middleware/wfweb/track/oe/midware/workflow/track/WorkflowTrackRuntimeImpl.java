/*
 * 创建日期 2006-3-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package oe.midware.workflow.track;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.track.mode.WfControl;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * @author robanco
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class WorkflowTrackRuntimeImpl implements WorkflowRuntime {


	/**
	 * 获得静态流程轨迹
	 * 
	 * @param pkgCode
	 *            包id
	 * @param processId
	 *            流程id
	 * @return
	 */
	public String viewFlow(String processid) {
		XpdlModelHandler xpdlModelHandler = (XpdlModelHandler) WfEntry
				.fetchBean("xpdlModelHandler");
		WorkflowProcess proc = xpdlModelHandler
				.fetchXpdlWorkflowByProcessid(processid);
		return WorkflowInfo.wfTrack(proc, null);
	}

	/**
	 * 获得动态流程轨迹
	 * 
	 * @param runtimeId
	 *            流程Id
	 * @return
	 */
	public String controlFlow(String runtimeId) {

		String info = useFlow(runtimeId);
		String controlinfo = info + WfControl.controlInfo();
		return controlinfo;
	}

	public String useFlow(String runtimeId) {
		TWfRuntime runtimeObj = (TWfRuntime) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TWfRuntime.class, runtimeId);
		String processid = runtimeObj.getProcessid();

		XpdlModelHandler xpdlModelHandler = (XpdlModelHandler) WfEntry
				.fetchBean("xpdlModelHandler");

		WorkflowProcess proc = xpdlModelHandler
				.fetchXpdlWorkflowByProcessid(processid);
		return WorkflowInfo.wfTrack(proc, runtimeObj);

	}

}
