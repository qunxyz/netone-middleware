package oe.rmi.message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 该邮件接口是一个发送者对N个接收者，通常这个发送者表示一个组织，机构或者管理员，由他 统一对外发送一些业务信息
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface SendMail extends Remote {
	/**
	 * 发送邮件
	 * 
	 * @param receiver
	 *            接收者
	 * @param subject
	 *            邮件标题
	 * @param body
	 *            邮件正文
	 * @return 发送结果，如果结果为空说明发送成功，否则返回错误信息
	 */
	public String send(String receiver, String subject, String body)
			throws RemoteException;

}
