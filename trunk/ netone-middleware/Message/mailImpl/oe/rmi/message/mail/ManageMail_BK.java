/**
 * ManageMail.java 2008.4.24
 */
package oe.rmi.message.mail;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 使用JavaMail来管理邮件的收发, 将资源模型的 资源 插入邮件的附件中去发送
 * 
 * @author HTB
 * 
 */
public class ManageMail_BK {
	private String adminmail;

	private String password;

	private String smtpServer = null; // SMTP服务器地址

	private String popServer = null;// pop服务器

	private String protocol = null;// 接收邮件的协议

	private Properties proper = null; // 用于配置邮件会话属性

	/**
	 * 
	 */
	public ManageMail_BK() {
	}

	/**
	 * @return the popServer
	 */
	public String getPopServer() {
		return popServer;
	}

	/**
	 * @param popServer
	 *            the popServer to set
	 */
	public void setPopServer(String popServer) {
		this.popServer = popServer;
	}

	/**
	 * @return the proper
	 */
	public Properties getProper() {
		return proper;
	}

	/**
	 * @param proper
	 *            the proper to set
	 */
	public void setProper(Properties proper) {
		this.proper = proper;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the smtpServer
	 */
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * @param smtpServer
	 *            the smtpServer to set
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
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
	 * 发送邮件
	 * 
	 * @param receiveAddress
	 *            接件人
	 * @param subject
	 *            主题
	 * @param body
	 *            邮件内容
	 */
	public void send(String sender, String password, String receiver,
			String subject, String body, String fileAttachment, String basePath) {
		if (proper == null) {
			this.init();
		}

		// 创建邮件地址
		InternetAddress[] receiveAddress = new InternetAddress[1];
		try {

			receiveAddress[0] = new InternetAddress(receiver);

		} catch (AddressException e) {
			e.printStackTrace();
		}

		SmtpAuth sa = new SmtpAuth();
		sa.setUserinfo(sender, password);
		Session session = Session.getInstance(proper, sa);
		// session = Session.getDefaultInstance(proper);
		session.setPasswordAuthentication(new URLName(smtpServer), sa
				.getPasswordAuthentication());

		MimeMessage sendMess = new MimeMessage(session);
		MimeBodyPart mbp = new MimeBodyPart();
		MimeMultipart mmp = new MimeMultipart();

		// 填充邮件体
		try {
			mbp.setContent(body, "text/plain; charset=GBK");
			mmp.addBodyPart(mbp);

			// 附件处理
			if (fileAttachment != null && fileAttachment != "") {
				String[] file = fileAttachment.split(",");
				for (int i = 0; i < file.length; i++) {
					System.out.println(file[i]);
					// DataSource source = new
					// FileDataSource("http:\\"+"\\localhost:8080\\Message\\DownloadSvl?filename="+file[i].split("=")[0]);
					// String name = source.getName();
					String filepath = basePath + "DownloadSvl?filename="
							+ file[i].split("=")[0];
					URL url = new URL(filepath);

					ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
					String[] spiltinfo = file[i].split("=");
					String naturalname = "";
					if (spiltinfo != null && spiltinfo.length > 1) {
						naturalname = spiltinfo[1];
					} else {
						System.err.println("error:" + file[i]);
					}

					UmsProtectedobject ums = rsrmi
							.loadResourceByNatural(spiltinfo[10]);
					// 扩展名在描述里
					String ext = ums.getDescription();

					mbp = new MimeBodyPart();
					mbp.setHeader("basePath", basePath
							+ "DownloadSvl?filename=");
					mbp.setHeader("naturalname", file[i]);
					mbp.setDataHandler(new DataHandler(url));
					mbp.setFileName(naturalname + "." + ext);
					mmp.addBodyPart(mbp);
				}
			}

			sendMess.setSubject(subject);
			sendMess.setContent(mmp);

			sendMess.setFrom(new InternetAddress(sender));
			sendMess.setRecipients(Message.RecipientType.TO, receiveAddress);
			Transport.send(sendMess);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param receiveAddress
	 *            接件人
	 * @param subject
	 *            主题
	 * @param body
	 *            邮件内容
	 */
	public void send(String receiver, String subject, String body,
			String fileAttachment, String basePath) {

		send(adminmail, password, receiver, subject, body, fileAttachment,
				basePath);

	}

	/**
	 * 获取并连接存储器
	 * 
	 * @param protocol
	 *            收件协议
	 * @return 存储器
	 */
	public Store getStore(String username, String password) {
		if (proper == null) {
			this.init();
		}
		SmtpAuth sa = new SmtpAuth();
		sa.setUserinfo(username, password);
		Session session = Session.getInstance(proper, sa);
		// session = Session.getDefaultInstance(proper);
		session.setPasswordAuthentication(new URLName(smtpServer), sa
				.getPasswordAuthentication());
		Store store = null;
		try {
			store = session.getStore(protocol);
			store.connect(popServer, username, password);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return store;
	}

	/**
	 * 获取邮件的文件夹
	 * 
	 * @return 邮件的文件夹
	 */
	public Folder getFolder(String username, String password, String folder) {
		Store store = getStore(username, password);
		username = StringUtils.substringBefore(username, "@");
		Folder _folder = null;
		if (store == null) {
			store = getStore(username, password);
		}
		if (folder == null || folder.equals("")) {
			folder = "inbox";
		}
		try {
			_folder = store.getFolder(folder);
			store.close();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return _folder;
	}

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
}
