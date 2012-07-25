package oe.rmi.message.mail.test;

import org.apache.mailet.GenericRecipientMatcher;
import org.apache.mailet.MailAddress;

/**
 * System jamesstudy Package com.yy.jamesstudy
 * 
 * @author Yang Yang Created on 2007-9-14����02:17:07
 */

public class BizMatcher extends GenericRecipientMatcher {
	public boolean matchRecipient(MailAddress recipient) {
		// �ʼ���ַ�������hello��
		if (recipient.getUser().indexOf("hello") != -1) {
			return true;
		}
		return false;
	}

}