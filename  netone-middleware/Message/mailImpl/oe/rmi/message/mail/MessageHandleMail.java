/**
 * MessageHandleMail.java	2007/1/5
 */
package oe.rmi.message.mail;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Flags.Flag;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.util.Encryption;

/**
 * 通过Mail的方式实现信息的传送
 * 
 * @author HTB
 * 
 */
public class MessageHandleMail extends UnicastRemoteObject implements
		MessageHandle {

	private ManageMail manageMail;// 管理邮件

	/**
	 * @throws RemoteException
	 * 
	 */
	public MessageHandleMail() throws RemoteException {
		super();
	}

	/**
	 * @return the manageMail
	 */
	public ManageMail getManageMail() {
		return manageMail;
	}

	public void setEmail(String email) throws RemoteException {
		manageMail.setAdminmail(email);
	}

	public void setPassword(String password) throws RemoteException {
		String encrypKey=null;
		try {
			CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
			encrypKey=cupm.fetchConfig("EncrypKey");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		password=Encryption.encry(password, encrypKey, false);
		manageMail.setPassword(password);
	}

	/**
	 * @param manageMail
	 *            the manageMail to set
	 */
	public void setManageMail(ManageMail manageMail) {
		this.manageMail = manageMail;
	}

	/**
	 * 获得个人的消息
	 * 
	 * @see oe.rmi.message.MessageHandle#personMessage(java.lang.String,int,int,java.lang.String)
	 */
	public List<UmsBussformworklist> personMessage(String participant,
			int from, int to, String condition) throws RemoteException {
		UmsBussformworklist _worklist = new UmsBussformworklist();
		_worklist.setParticipant(participant);
		return this.query(_worklist, from, to, condition);
	}

	/**
	 * 删除一条消息
	 * 
	 * @see oe.rmi.message.MessageHandle#deleteMessage(java.lang.String)
	 */
	public void deleteMessage(String messageid) throws RemoteException {
		this.deleteMessage(new String[] { messageid });
	}

	/**
	 * 删除消息
	 * 
	 * @see oe.rmi.message.MessageHandle#deleteMessage(java.lang.String[])
	 */
	public void deleteMessage(String[] messageid) throws RemoteException {
		Folder Inbox = manageMail.getFolder(manageMail.getAdminmail(),
				manageMail.getPassword(), "inbox");
		try {
			Inbox.open(Folder.READ_WRITE);
			for (String _messageid : messageid) {
				SearchTerm st = new MessageIDTerm(_messageid);
				Message[] message = Inbox.search(st);
				message[0].setFlag(Flag.DELETED, true);
			}
			Inbox.close(true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public int totalNum(String loginname, String condition) {
		try {
			Folder Inbox = manageMail.getFolder(manageMail.getAdminmail(),
					manageMail.getPassword(), "inbox");
			Inbox.open(Folder.READ_ONLY);
			int num = Inbox.getMessageCount();
			Inbox.close(true);
			return num;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public UmsBussformworklist load(String workid) {
		UmsBussformworklist _worklist = new UmsBussformworklist();
		Folder Inbox = manageMail.getFolder(manageMail.getAdminmail(),
				manageMail.getPassword(), "inbox");
		try {
			Inbox.open(Folder.READ_WRITE);
			// 以可读写的方式开启接收邮件的文件夹
			SearchTerm st = new MessageIDTerm(workid);
			Message[] message = Inbox.search(st);

			String _content = "";

			MimeMultipart mmp = (MimeMultipart) message[0].getContent();
			BodyPart bp = mmp.getBodyPart(0);
			_content = bp.getContent().toString();

			_worklist.setSender(message[0].getFrom()[0].toString());
			_worklist.setSendername(message[0].getFrom()[0].toString());
			_worklist.setParticipantname(manageMail.getAdminmail());
			_worklist.setParticipant(manageMail.getAdminmail());
			_worklist.setWorkid(workid);
			_worklist.setCreated(message[0].getSentDate());
			_worklist.setExtattr1(message[0].getSubject());
			_worklist.setExtattr2(_content);
			_worklist.setExtattr3("");
			for (int i = 1; i < mmp.getCount(); i++) {
				Part part = mmp.getBodyPart(i);
				if (_worklist.getExtattr3() == "") {
					_worklist.setExtattr3(part.getHeader("naturalname")[0]);
				} else {
					_worklist.setExtattr3(_worklist.getExtattr3() + ","
							+ part.getHeader("naturalname")[0]);
					_worklist.setExtattr4(part.getHeader("basePath")[0]);
				}
			}
			Inbox.close(true);
			return _worklist;

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return _worklist;
	}

	/**
	 * 查询消息 查询条件格式 为and sender like '%XX@XX.com%' and participant like
	 * '%XX@XX.com%' and created >= 'XXXX-XX-XX' and created <= 'XXXX-XX-XX' and
	 * statusinfo like '%[0|1]%' and extattr2 like '%XX %' 形式的字符串
	 * 
	 * @see oe.rmi.message.MessageHandle#query(oe.rmi.message.UmsBussformworklist,int,int,java.lang.String)
	 */
	public List<UmsBussformworklist> query(UmsBussformworklist worklist,
			int from, int to, String condition) throws RemoteException {
		to = from + to;
		from += 1;
		if (from <= 0) {
			from = 1;
		}
		Folder Inbox = manageMail.getFolder(manageMail.getAdminmail(),
				manageMail.getPassword(), "inbox");
		List<UmsBussformworklist> resultMessage = new ArrayList<UmsBussformworklist>();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> _map = this.ParseCondition(condition);
		// 邮件 发件人
		String sender = "";
		// 邮件 接收人
		String participant = "";
		// 邮件 开始时间
		String startTime = "";
		// 邮件 结束时间
		String endTime = "";
		// 邮件 状态
		String statusinfo = "";
		// 邮件 内容
		String extattr2 = "";

		sender = _map.get("sender");
		participant = _map.get("participant");
		startTime = _map.get("startTime");
		endTime = _map.get("endTime");
		statusinfo = _map.get("statusinfo");
		extattr2 = _map.get("extattr2");

		InternetAddress senderAddress = null;
		InternetAddress participantAddress = null;
		try {
			if (!sender.equals("")) {
				senderAddress = new InternetAddress(sender);
			}
			if (!participant.equals("")) {
				participantAddress = new InternetAddress(participant);
			}
		} catch (AddressException e1) {
			e1.printStackTrace();
		}

		try {
			// 打开接收邮件的文件夹并获取所有邮件
			Inbox.open(Folder.READ_ONLY);
			Message[] message = Inbox.getMessages(from, to);
			for (int i = 0; i < message.length; i++) {
				// 匹配邮件主题
				if (sender.equals("")
						|| message[i].getFrom().equals(senderAddress)) {
					if (participant.equals("")
							|| message[i].getFrom().equals(participantAddress)) {
						if (startTime.equals("")
								|| message[i].getSentDate().after(
										dayFormat.parse(startTime))) {
							if (endTime.equals("")
									|| message[i].getSentDate().before(
											dayFormat.parse(endTime))) {
								String _content = "";
								if (String.class.isInstance(message[i]
										.getContent())) {
									_content = message[i].getContent()
											.toString();
								} else if (MimeMultipart.class
										.isInstance(message[i].getContent())) {
									MimeMultipart mmp = (MimeMultipart) message[i]
											.getContent();

									BodyPart bp = mmp.getBodyPart(0);
									_content = bp.getContent().toString();
								}

								if (extattr2.equals("")
										|| _content.indexOf(extattr2) > -1) {
									UmsBussformworklist _worklist = new UmsBussformworklist();

									_worklist.setSender(message[i].getFrom()[0]
											.toString());
									_worklist.setSendername(message[i]
											.getFrom()[0].toString());
									_worklist.setParticipant(manageMail
											.getAdminmail());
									_worklist.setParticipantname(manageMail
											.getAdminmail());
									_worklist
											.setWorkid(((MimeMessage) message[i])
													.getMessageID());

									_worklist.setCreated(message[i]
											.getSentDate());
									_worklist.setExtattr1(message[i]
											.getSubject());
									_worklist.setExtattr2(_content);
									resultMessage.add(_worklist);
								}
							}
						}
					}
				}

			}
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Inbox.close(true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMessage;
	}

	/**
	 * 分析查询条件
	 * 
	 * @param condition
	 *            为and sender like '%XX@XX.com%' and participant like
	 *            '%XX@XX.com%' and created >= 'XXXX-XX-XX' and created <=
	 *            'XXXX-XX-XX' and statusinfo like '%[0|1]%' and extattr2 like
	 *            '%XX %' 形式的字符串
	 * @return
	 */
	public Map<String, String> ParseCondition(String condition) {
		Map<String, String> map = new Hashtable<String, String>();
		// 查询条件 发件人
		String sender = "";
		// 查询条件 接收人
		String participant = "";
		// 查询条件 开始时间
		String startTime = "";
		// 查询条件 结束时间
		String endTime = "";
		// 查询条件 状态
		String statusinfo = "";
		// 查询条件 内容
		String extattr2 = "";
		Pattern pattern;
		Matcher match;

		// 发送者邮件地址正则表达式
		pattern = Pattern
				.compile("sender like '%\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*%'");
		match = pattern.matcher(condition);
		if (match.find()) {
			sender = match.group();
			sender = sender.substring(sender.indexOf("%") + 1, sender
					.lastIndexOf("%"));
		}

		// 接收正则表达式
		pattern = Pattern
				.compile("participant like '%\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*%'");
		match = pattern.matcher(condition);
		if (match.find()) {
			participant = match.group();
			participant = participant.substring(participant.indexOf("%") + 1,
					participant.lastIndexOf("%"));
		}

		// 起始日期时间 正则表达式
		pattern = Pattern.compile("created >=  '[0-9]{4}-[0-9]{2}-[0-9]{2}'");
		match = pattern.matcher(condition);
		if (match.find()) {
			startTime = match.group();
			startTime = startTime.substring(startTime.indexOf("'") + 1,
					startTime.lastIndexOf("'"));
		}

		// 结束日期时间 正则表达式
		pattern = Pattern.compile("created <=  '[0-9]{4}-[0-9]{2}-[0-9]{2}'");
		match = pattern.matcher(condition);
		if (match.find()) {
			endTime = match.group();
			endTime = endTime.substring(endTime.indexOf("'") + 1, endTime
					.lastIndexOf("'"));
		}

		// 状态正则表达式
		pattern = Pattern.compile("statusinfo like '%[0-1]{1}%'");
		match = pattern.matcher(condition);
		if (match.find()) {
			statusinfo = match.group();
			statusinfo = statusinfo.substring(statusinfo.indexOf("%") + 1,
					statusinfo.lastIndexOf("%"));
		}
		// 内容正则表达式
		pattern = Pattern.compile("extattr2 like '%[^\\uFF00-\\uFFFF]*%'");
		match = pattern.matcher(condition);
		if (match.find()) {
			extattr2 = match.group();
			extattr2 = extattr2.substring(extattr2.indexOf("%") + 1, extattr2
					.lastIndexOf("%"));
		}

		map.put("sender", sender);
		map.put("participant", participant);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("statusinfo", statusinfo);
		map.put("extattr2", extattr2);
		return map;
	}

	/**
	 * 发送邮件
	 * 
	 * @see oe.rmi.message.MessageHandle#send(oe.rmi.message.client.UmsBussformworklist)
	 */
	public Serializable send(UmsBussformworklist worklist)
			throws RemoteException {
		// 邮件主题
		String subject = worklist.getExtattr1();
		// 邮件内容
		String content = worklist.getExtattr2();
		// 资源模型附件
		String fileAttachment = worklist.getExtattr3();
		// 工程基地址
		String basePath = worklist.getExtattr4();
		String receiveName = worklist.getParticipant();
		String password = manageMail.getPassword();
		String user = manageMail.getAdminmail();
		manageMail.send(user, password, receiveName, subject, content,
				fileAttachment, basePath);
		return "ok";
	}

	/**
	 * 发送邮件
	 * 
	 * @see oe.rmi.message.MessageHandle#send(oe.rmi.message.client.UmsBussformworklist)
	 */
	public Serializable[] send(List<UmsBussformworklist> list)
			throws RemoteException {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsBussformworklist worklist = (UmsBussformworklist) iter.next();
			this.send(worklist);
		}
		return null;
	}
}
