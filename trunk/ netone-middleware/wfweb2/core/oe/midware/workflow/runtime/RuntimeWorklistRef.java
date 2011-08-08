package oe.midware.workflow.runtime;

/**
 * �����ڻ�ο�
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */                                                   
public interface RuntimeWorklistRef {
	// ����ִ���� - ͨ������������
	String[] STATUS_RUNNING = { "01", "ִ����" };

	// ��������ִ�����
	String[] STATUS_DONE = { "02", "���" };

	// �����쳣
	String[] STATUS_EXCEPTION = { "03", "�쳣" };

	// ȡ��- ���������ʱ���״̬�����ڸ�״̬�Ļ��Normal �Ļһ���������workList��
	// Ψһ���������ڴ���Quash״̬�Ļ���ܲ���ִ�е�,ֻ�ܲ鿴
	String[] STATUS_QUASH = { "04", "ȡ��" };

	String[] STATUS_IGNORE = { "05", "����" };

	String[] STATUS_SUBFLOW_WAITING = { "06", "�ȴ�������" };

	String[] STATUS_SUBFLOW_STOP = { "07", "��ͣ" };

	// �״̬���б�
	String[][] STATUSLIST = { { "01", "ִ����" }, { "02", "���" }, { "03", "�쳣" },
			{ "04", "ȡ��" }, { "05", "����" }, { "06", "�ȴ�������" }, { "07", "��ͣ" } };

}
