package oe.security3a.seucore.accountser;

import oe.security3a.seucore.obj.User;

public interface UserSession {

	/**
	 * ��ʱ���
	 * 
	 */
	public void outtimeCheck();

	/**
	 * ����½�õ��û����밲ȫ�ʻ������Session��
	 * 
	 * @param user
	 */
	public void addUserToSession(User user);

	/**
	 * �Ӱ�ȫ�ʻ������Session�л���û�����
	 * 
	 * @param name
	 * @return
	 */
	public User getUserFormSession(String name);

	/**
	 * ɾ���û���½�Ự
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeUserFromSession(String name);

}
