package oe.security3a.seucore.accountser;

import oe.security3a.seucore.obj.User;

public interface UserSession {

	/**
	 * 超时检查
	 * 
	 */
	public void outtimeCheck();

	/**
	 * 将登陆用的用户放入安全帐户服务的Session中
	 * 
	 * @param user
	 */
	public void addUserToSession(User user);

	/**
	 * 从安全帐户服务的Session中获得用户对象
	 * 
	 * @param name
	 * @return
	 */
	public User getUserFormSession(String name);

	/**
	 * 删除用户登陆会话
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeUserFromSession(String name);

}
