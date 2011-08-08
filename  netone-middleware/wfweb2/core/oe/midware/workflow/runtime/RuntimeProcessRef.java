package oe.midware.workflow.runtime;

/**
 * ���������̲ο�
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeProcessRef {
	/** user-defined area begin */

	String _QUERY_WORKLIST_APPEND_CONDITION = " order by starttime desc";

	// ׼����
	static String[] STATUS_READY = { "00", "׼����" };

	// �����еĻ������ִ���е�״̬
	static String[] STATUS_RUNNING = { "01", "ִ����" };

	// ��������ִ����Ϻ��״̬
	static String[] STATUS_END = { "02", "���" };

	// �쳣- ͨ��������������������˹��϶��������̳����쳣��״̬
	static String[] STATUS_EXCEPTION = { "03", "�쳣" };

	// ��������
	static String[] STATUS_QUASH = { "04", "����" };

	// ����״̬���б�
	static String[][] STATUSLIST = { { "00", "׼����" }, { "01", "ִ����" },
			{ "02", "���" }, { "03", "�쳣" }, { "04", "����" } };

	// ������
	static String[] TYPE_MAIN_FLOW = { "01", "������" };

	// ������
	static String[] TYPE_SUB_FLOW = { "02", "������" };

	// ͬ��������
	static String[] TYPE_SYNC_SUB_FLOW = { "03", "ͬ��������" };

	static String[][] TYPELIST = { { "01", "������" }, { "02", "������" },
			{ "03", "ͬ��������" } };

}
