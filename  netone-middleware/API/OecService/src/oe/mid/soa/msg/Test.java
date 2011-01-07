package oe.mid.soa.msg;

import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.SendMail;
import oe.rmi.message.UmsBussformworklist;

public class Test {

	public static void main(String[] args) throws Exception {
		// MessageImpl xx = new MessageImpl();
		// UmsBussformworklist work = new UmsBussformworklist();
		// work.setParticipant("mike");
		// work.setSender("jim");
		// work.setSendername("jim");
		// xx.send(work);
		// List list = xx.dailyMessageId("mike");
		// xx.dailyMessage("mike");
		// System.out.println(list.size());
		
//		MessageHandle db = (MessageHandle) RmiEntry.iv("msghandle");
//		UmsBussformworklist work = new UmsBussformworklist();
//		work.setParticipant("mikexx");
//		work.setSender("jim");
//		work.setSendername("jim");
//
//		db.send(work);
//		db.send(work);
//		db.send(work);
//
//		List list = db.personMessage("mikexx", 0, 10, "");
//		System.out.println(list);
		
		
		SendMail sendmail = null;
		try {
			sendmail = (SendMail) RmiEntry.iv("sendmail");
			sendmail.send("chenjx@fjycit.com", "aaa", "111");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

}
