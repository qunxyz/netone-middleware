package oe.mid.soa.wf;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

/**
 * �ֹ��ύִ������<br>
 * ����˵��,Ӧ�ù�����RMI���񴴽���������,�����̳ɹ�������,ͨ����ѯ�Ա�����Ĺ������ڵ� Ȼ���ó����ύ�ڵ�,�����̼�������ֱ������ִ�н���
 * 
 * @author chen.jia.xun
 * 
 */
public class CopyOfAutoExecuteSample {

	static String _PROCESS_ID = "BUSSWF.BUSSWF.ZYERP.INVOICE";

	public static void main(String[] args) throws Exception {
		// ���̿���̨�������
		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		// ������ͼ�������
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");

		// ���̿���,��������ʵ��
		String runtimeid = console.newProcess(_PROCESS_ID);
		// ���̿���,��������
		console.runProcess(runtimeid);
		
		//view.fetchRelevantVar(runtimeid, "fsdfs111");
		
		StringUtils.equals(null, null);
	}
}
