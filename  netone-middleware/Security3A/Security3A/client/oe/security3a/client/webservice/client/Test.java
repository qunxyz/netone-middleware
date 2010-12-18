package oe.security3a.client.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import oe.security3a.client.webservice.CupmService;
import oe.security3a.client.webservice.CupmServiceService;
import oe.security3a.client.webservice.CupmServiceServiceLocator;

public class Test {

	public static void main(String[] args) throws ServiceException,
			RemoteException, MalformedURLException {
		CupmServiceService sl = new CupmServiceServiceLocator();
		URL url =new URL("http://localhost:8080/Security3A/services/CupmService");
//		CupmService mb = sl.getCupmService();// default port
		
		CupmService mb = sl.getCupmService(url);

		/**
		 * 鉴权逻辑,判断角色是否有某个资源的权限。
		 * 
		 * @param roleName
		 *            角色的名字
		 * @param dnname
		 *            保护对象的dnname名字
		 * @param action
		 *            操作(操作目前有三种模式,可以继续扩展 1,3,7)
		 * @return
		 */
		
		boolean result = mb.checkRolePermission("ADMINX", "BUSSENV.BUSSENV.SECURITY.SYSROLE.ADMINX", "3");
		System.out.println(result);

	}
}
