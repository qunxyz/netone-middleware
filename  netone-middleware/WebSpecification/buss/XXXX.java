import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

public class XXXX {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws Exception, NotBoundException {

		
		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
		Clerk clerk=rs.loadClerk("0000", "chijh");
		System.out.println(clerk.getExtendattribute());
	}

}
