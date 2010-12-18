package oe.security3a.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 人员同步应用
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface UserSyncSpeci extends Remote {
	/**
	 * 账户同步
	 * 
	 * @param clerk
	 *            职员
	 * @param dept
	 *            部门
	 * @param team
	 *            组
	 * @param role
	 *            角色
	 */
	void sync(List clerk, List dept, List team, List role)
			throws RemoteException;

}
