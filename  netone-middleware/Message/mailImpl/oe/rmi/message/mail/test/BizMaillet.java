package oe.rmi.message.mail.test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.mailet.GenericMailet;
import org.apache.mailet.Mail;

/**
 * System jamesstudy Package com.yy.jamesstudy
 * 
 * @author Yang Yang Created on 2007-9-14обнГ02:16:31
 */
public class BizMaillet extends GenericMailet {
	public void init() throws MessagingException {
	}

	public void service(Mail mail) throws MessagingException {
		MimeMessage mmp;
		mmp = (MimeMessage) mail.getMessage();
		mmp.setSubject("Hello "+mmp.getSubject());
		System.out.println("Received a piece of Email");
	}
}