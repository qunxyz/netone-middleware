package oe.rmi.message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.orm.OrmerDao;

/**
 * ����Ormer��ʵ�ֻ���db����Ϣ����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class MessageImpl extends OrmerDao implements MessageHandle {

	public List personMessage(String participant, int from, int to,
			String condition) throws RemoteException {
		UmsBussformworklist worklist = new UmsBussformworklist();
		worklist.setParticipant(participant);
		return ormer.fetchQuerister().queryObjects(worklist, null, from, to,
				condition);

	}
	
	public int totalNum(String loginname,String condition) throws RemoteException{
		
		UmsBussformworklist worklist = new UmsBussformworklist();
		worklist.setParticipant(loginname);
		return (int)ormer.fetchQuerister().queryObjectsNumber(worklist, null,condition);
	}

	/**
	 * ɾ��һ����Ϣ
	 */
	public void deleteMessage(String[] messageid) throws RemoteException {
		List list = new ArrayList();
		for (int i = 0; i < messageid.length; i++) {
			UmsBussformworklist ums = (UmsBussformworklist) ormer
					.fetchQuerister().loadObject(UmsBussformworklist.class,
							messageid[i]);
			ums.setStatusinfo(_MESSAGE_MODE_CLOSE);
			list.add(ums);
		}
		ormer.fetchSerializer().drops(list);
	}
	
	/**
	 * ɾ��һ����Ϣ
	 */
	public void deleteMessage(String messageid) throws RemoteException {
			UmsBussformworklist ums = (UmsBussformworklist) ormer
					.fetchQuerister().loadObject(UmsBussformworklist.class,
							messageid);
		ormer.fetchSerializer().drop(ums);
	}

	/**
	 * ��ҳ��ѯ��Ϣ
	 */
	public List query(UmsBussformworklist ums, int from, int to, String condition) throws RemoteException {
		return ormer.fetchQuerister().queryObjects(ums, null, from, to,condition);
	}

	/**
	 * װ��һ����Ϣ
	 */
	public UmsBussformworklist load(String workid) throws RemoteException{
		return (UmsBussformworklist)ormer.fetchQuerister().loadObject(UmsBussformworklist.class, workid);
	}

	public Serializable send(UmsBussformworklist worklist)
			throws RemoteException {
		worklist.setStatusinfo(this._MESSAGE_MODE_NEW);
		worklist.setCreated(new Date(System.currentTimeMillis()));
		ormer.fetchSerializer().create(worklist);
		return worklist.getWorkid();
	}

	/**
	 * ���ؽ������Ҫ��ʵ�Ķ���id,ֻ��Ҫ���ⷵ��һ��ֵ����
	 */
	public Serializable[] send(List<UmsBussformworklist> worklist)
			throws RemoteException {
		for (Iterator iter = worklist.iterator(); iter.hasNext();) {
			UmsBussformworklist element = (UmsBussformworklist) iter.next();
			element.setStatusinfo(this._MESSAGE_MODE_NEW);
			element.setCreated(new Date(System.currentTimeMillis()));
		}
		ormer.fetchSerializer().creates(worklist);
		String[] rs = { "1" };
		return rs;
	}
	
	public void setEmail(String email) throws RemoteException  {
	}
	
	public void setPassword(String password) throws RemoteException  {
	}
}
