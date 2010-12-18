import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;

import org.apache.commons.lang.StringUtils;

public class Test {

	static ResourceBundle redirect = ResourceBundle.getBundle("urlRedirect");

	public static void main(String[] args) throws Exception {
		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
		System.out.println(rs.loadClerk("0000", "adminx").getExtendattribute());
		CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
		cupm.checkUserPermission("0000", "zzz", "DEPT.DEPT", "1");
		System.out.println(1);
		
		//rs.loadRole(1l);
//		String url="http://xxxx.xxxxx.xxxx:83/xxx/zzzzff/fsdfds.jsp";
//		System.out.println(redirector(url));
	}
	
	private static String redirector(String url) {
		if (redirect == null) {
			return url;
		}
		Enumeration enu = redirect.getKeys();
		while (enu.hasMoreElements()) {
			String type = (String) enu.nextElement();
			if (type.matches("urlmode.*")) {
				String findurl = redirect.getString(type);
				if (url.matches(findurl + ".*")) {
					String redirecturl = redirect.getString("redirect"
							+ type.substring(7));
					url = StringUtils.replace(url, findurl, redirecturl);
				}

			}
		}
		return url;
	}

}
