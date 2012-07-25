package oe.rmi.message;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 消息服务接口
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface MessageHandle extends Remote {

	String _MESSAGE_MODE_CLOSE = "1";

	String _MESSAGE_MODE_NEW = "0";

	String _MESSAGE_MODE_UNACTIVE = "2";

	String[][] _MESSAGE = { { "0", "新消息" }, { "1", "已处理" }, { "2", "未生效" } };

	/**
	 * 发送消息
	 * 
	 * @param worklist
	 *            消息对象
	 * @return 消息ID
	 * @throws RemoteException
	 */
	Serializable send(UmsBussformworklist worklist) throws RemoteException;

	/**
	 * 批量发送消息
	 * 
	 * @param worklist
	 *            消息对象数组
	 * @return 消息ID数组
	 * @throws RemoteException
	 */
	Serializable[] send(List<UmsBussformworklist> worklist)
			throws RemoteException;

	/**
	 * 获得个人的消息
	 * 
	 * @param participant
	 *            消息接收者
	 * @param from
	 *            从第n条开始
	 * @param to
	 *            从第n条结束
	 * @param condition
	 *            扩充条件Sql子条件
	 * @return
	 * @throws RemoteException
	 */
	List personMessage(String participant, int from, int to, String condition)
			throws RemoteException;

	/**
	 * 根据条件获取所有人消息
	 * 
	 * @param worklist
	 *            消息对象
	 * @param from
	 *            从第n条开始
	 * @param to
	 *            从第n条结束
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List query(UmsBussformworklist worklist, int from, int to, String condition)
			throws RemoteException;

	/**
	 * 删除多条消息
	 * 
	 * @param messageid
	 *            消息ID数组
	 * @throws RemoteException
	 */
	void deleteMessage(String[] messageid) throws RemoteException;

	/**
	 * 删除消息
	 * 
	 * @param messageid
	 *            消息ID
	 * @throws RemoteException
	 */
	void deleteMessage(String messageid) throws RemoteException;

	/**
	 * 装载一条消息
	 * 
	 * @param workid
	 *            消息ID
	 * @return
	 * @throws RemoteException
	 */
	UmsBussformworklist load(String workid) throws RemoteException;

	/**
	 * 查询某人的消息总条数
	 * 
	 * @param loginname
	 *            消息接收者
	 * @param condition
	 *            消息扩充条件SQL子条件
	 * @return
	 */
	int totalNum(String loginname, String condition) throws RemoteException;

	/**
	 * 设置邮箱地址
	 * 
	 * @param email
	 */
	public void setEmail(String email) throws RemoteException;

	/**
	 * 设置邮件密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) throws RemoteException;
}
