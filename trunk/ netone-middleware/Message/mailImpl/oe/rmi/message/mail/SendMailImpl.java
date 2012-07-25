package oe.rmi.message.mail;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

import oe.rmi.message.SendMail;

public class SendMailImpl extends UnicastRemoteObject implements SendMail {

	protected SendMailImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String adminmail;

	private String password;

	private String smtpServer = null; // SMTP��������ַ

	private String popServer = null;// pop������

	private String protocol = null;// �����ʼ���Э��

	private Properties proper = null; // ���������ʼ��Ự����

	public String getAdminmail() {
		return adminmail;
	}

	public void setAdminmail(String adminmail) {
		this.adminmail = adminmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getPopServer() {
		return popServer;
	}

	public void setPopServer(String popServer) {
		this.popServer = popServer;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String send(String sender, String receiver, String subject,
			String body) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ��ʼ��
	 * 
	 */
	private void init() {
		proper = new Properties();
		proper.put("mail.smtp.host", smtpServer);
		proper.put("mail.smtp.auth", "true");
		proper.put("mail.smtp.port", "25");
		proper.put("mail.transport.protocol", "smtp");
		proper.put("mail.store.protocol", protocol);

		// proper.put("mail.smtp.socketFactory.fallback", "false");
		// proper.setProperty("mail.smtp.quitwait", "false");

	}

	/**
	 * �����ʼ�
	 * 
	 * @param receiveAddress
	 *            �Ӽ���
	 * @param subject
	 *            ����
	 * @param body
	 *            �ʼ�����
	 */
	public String send(String receiver, String subject, String body) {
		if (proper == null) {
			this.init();
		}

		// �����ʼ���ַ
		InternetAddress[] receiveAddress = new InternetAddress[1];
		try {

			receiveAddress[0] = new InternetAddress(receiver);

		} catch (AddressException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		SmtpAuth sa = new SmtpAuth();
		sa.setUserinfo(StringUtils.substringBefore(adminmail, "@"), password);
		Session session = Session.getInstance(proper, sa);
		// session = Session.getDefaultInstance(proper);
		session.setPasswordAuthentication(new URLName(smtpServer), sa
				.getPasswordAuthentication());

		MimeMessage sendMess = new MimeMessage(session);
		MimeBodyPart mbp = new MimeBodyPart();
		MimeMultipart mmp = new MimeMultipart();

		// ����ʼ���
		try {
			mbp.setContent(body, "text/plain; charset=GBK");
			mmp.addBodyPart(mbp);

			sendMess.setSubject(subject);
			sendMess.setContent(mmp);

			sendMess.setFrom(new InternetAddress(adminmail));
			sendMess.setRecipients(Message.RecipientType.TO, receiveAddress);
			Transport.send(sendMess);
		} catch (MessagingException ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
		return null;
	}

}
