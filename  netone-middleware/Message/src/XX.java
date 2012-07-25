import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.SendMail;

public class XX {

	public static void main(String[] args) {
		try {
			SendMail send = (SendMail) RmiEntry.iv("sendmail");
			send.send("oesee@163.com", "aa111", "11111111");
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

}
