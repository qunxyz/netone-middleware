package oe.rmi.message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ���ʼ��ӿ���һ�������߶�N�������ߣ�ͨ����������߱�ʾһ����֯���������߹���Ա������ ͳһ���ⷢ��һЩҵ����Ϣ
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface SendMail extends Remote {
	/**
	 * �����ʼ�
	 * 
	 * @param receiver
	 *            ������
	 * @param subject
	 *            �ʼ�����
	 * @param body
	 *            �ʼ�����
	 * @return ���ͽ����������Ϊ��˵�����ͳɹ������򷵻ش�����Ϣ
	 */
	public String send(String receiver, String subject, String body)
			throws RemoteException;

}
