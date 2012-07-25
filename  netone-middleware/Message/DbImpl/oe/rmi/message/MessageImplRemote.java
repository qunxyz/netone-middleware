package oe.rmi.message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MessageImplRemote extends UnicastRemoteObject implements
		MessageHandle {

	public MessageImplRemote() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private MessageHandle msg;

	public void deleteMessage(String[] messageid) throws RemoteException {
		msg.deleteMessage(messageid);
	}
	
	public void deleteMessage(String messageid) throws RemoteException {
		msg.deleteMessage(messageid);
	}
	
	public UmsBussformworklist load(String workid) throws RemoteException{
		return msg.load(workid);
	}
	
	public int totalNum(String loginname,String condition) throws RemoteException{
		return msg.totalNum(loginname, condition);
	}

	public List personMessage(String participant, int from, int to,
			String condition) throws RemoteException {
		// TODO Auto-generated method stub
		return msg.personMessage(participant, from, to, condition);
	}

	public List query(UmsBussformworklist worklist, int from, int to,
			String condition) throws RemoteException {
		// TODO Auto-generated method stub
		return msg.query(worklist, from, to, condition);
	}

	public Serializable send(UmsBussformworklist worklist)
			throws RemoteException {
		// TODO Auto-generated method stub
		return msg.send(worklist);
	}

	public Serializable[] send(List<UmsBussformworklist> worklist)
			throws RemoteException {
		// TODO Auto-generated method stub
		return msg.send(worklist);
	}

	public MessageHandle getMsg() {
		return msg;
	}

	public void setMsg(MessageHandle msg) {
		this.msg = msg;
	}
	
	
	public void setEmail(String email) {
	}
	
	public void setPassword(String password) {
	}

}
