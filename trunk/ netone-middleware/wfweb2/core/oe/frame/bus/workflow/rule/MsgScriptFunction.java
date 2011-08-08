package oe.frame.bus.workflow.rule;

/**
 * ��Ϣ�ű�����. ��Ϣ�����߲���Ĭ�ϵ�ָ���ʻ������ͣ�������API�в��漰��������
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface MsgScriptFunction {
	/**
	 * ��ʼ��
	 * 
	 * @param runtimeid
	 * @param workcode
	 */
	void init(String runtimeid, String workcode);

	/**
	 * ������Ϣ��ָ����ַ
	 * 
	 * @param aimmail
	 *            ������
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ����(multi) ��ʽΪ naturalname=title,naturalname=title
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 */
	void toMail(String aimmail, String title, String body, String append,
			String buss);

	/**
	 * ������Ϣ����Ա
	 * 
	 * 
	 * @param belongto
	 *            �û�����
	 * @param particiapnt
	 *            ������
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ����(multi) ��ʽΪ naturalname=title,naturalname=title
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 */
	void toMen(String code, String particiapnt, String title, String body,
			String append, String buss);

	/**
	 * ������Ϣ����ɫ
	 * 
	 * @param sender
	 *            ������
	 * @param rolename
	 *            ��ɫ��
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ���� ��ʽΪ naturalname=title,naturalname=title
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 * 
	 */
	void toRole(String code, String rolename, String title, String body,
			String append, String buss);

	/**
	 * ������Ϣ����֯
	 * 
	 * @param sender
	 *            ������
	 * @param groupname
	 *            ��ɫ��
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ���� ��ʽΪ naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 */
	void toDept(String code, String deptname, String title, String body,
			String append, String buss);

	/**
	 * ������Ϣ����һ����
	 * 
	 * @param code
	 *            ���ͱ���
	 * @param groupname
	 *            ����
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ���� ��ʽΪ naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 */
	void toGroupFirst(String code, String groupname, String title, String body,
			String append, String buss);

	/**
	 * ������Ϣ���ڶ�����
	 * 
	 * @param code
	 *            ������
	 * @param groupname
	 *            ��ɫ��
	 * @param title
	 *            ��Ϣ����
	 * @param body
	 *            ��Ϣ����
	 * @param append
	 *            ���� ��ʽΪ naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            ҵ�����(multi) ��ʽΪ url=title,url=title
	 */
	void toGroupSecond(String code, String groupname, String title,
			String body, String append, String buss);

}
