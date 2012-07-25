package oe.rmi.message.mail;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.SendMail;

public class TEST {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {

		SendMail msgHandle = (SendMail) RmiEntry.iv("sendmail");

		msgHandle.send("oesee@139.com", "xxxxx", "ÄãºÃ");

	}
}
