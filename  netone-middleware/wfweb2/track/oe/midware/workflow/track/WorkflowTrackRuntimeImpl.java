/*
 * �������� 2006-3-30
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class WorkflowTrackRuntimeImpl implements WorkflowRuntime {


	/**
	 * ��þ�̬���̹켣
	 * 
	 * @param pkgCode
	 *            ��id
	 * @param processId
	 *            ����id
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
	 * ��ö�̬���̹켣
	 * 
	 * @param runtimeId
	 *            ����Id
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
