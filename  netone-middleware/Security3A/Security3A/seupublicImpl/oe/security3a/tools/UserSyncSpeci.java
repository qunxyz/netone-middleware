package oe.security3a.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * ��Աͬ��Ӧ��
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface UserSyncSpeci extends Remote {
	/**
	 * �˻�ͬ��
	 * 
	 * @param clerk
	 *            ְԱ
	 * @param dept
	 *            ����
	 * @param team
	 *            ��
	 * @param role
	 *            ��ɫ
	 */
	void sync(List clerk, List dept, List team, List role)
			throws RemoteException;

}
