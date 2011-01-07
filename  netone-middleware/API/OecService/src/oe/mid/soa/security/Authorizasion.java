package oe.mid.soa.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 授权应用 Sample 授权应用是一个综合应用,通过Auditing,Authentication和Resource 三者综合来实现<br>
 * 该Sample中我们向某个用户授予它所拥有的 在域 Sample 下的所有可操作的资源
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Authorizasion {

	static String APPNAME = "DEPT.DEPT";

	static String ACCOUNT_ADDRESS = "10.1.5.202";

	static String ACCOUNT_NAME = "Robanco@oesee.com";// 帐户名

	// 访问操作(1:001,3:011,7:111), 分别表示禁止,可读,写,
	// 其意义还可以继续扩展 如1:0001,3:0011,7:0111,15:1111
	static String[] OPE_MODE = { "1", "3", "7" };

	public static void main(String[] args) throws Exception {

		// 获得安全访问控制操作句柄,之后相关的审计操作从此展开
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
	
		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject ums = new UmsProtectedobject();
		String querycondition = " and naturalname like '" + APPNAME + "%'";
		List list = resourceRmi.fetchResource(ums, null, querycondition);
		
		System.out.println(list.size());

		// 可以被授予的资源
		List authorizationResourcde = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject element = (UmsProtectedobject) iter.next();
			String naturalname = element.getNaturalname();
			if (cupmRmi.checkUserPermission("0000", ACCOUNT_NAME, naturalname,
					OPE_MODE[2])) {// 该用户有该资源的写权限,授予
				authorizationResourcde.add(element);
				// 登记日志
				cupmRmi.log(naturalname, ACCOUNT_NAME, ACCOUNT_ADDRESS, "ok",
						"get resourcde");
			}
		}

	}
}
