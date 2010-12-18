package oe.security3a.client.rmi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class Test {

	
	
//	static String APPNAME = "RSSAMPLE.RSSAMPLE.DIRA";
	static String APPNAME = "DEPT.DEPT";

	static String ACCOUNT_ADDRESS = "192.168.2.100";

	static String ACCOUNT_NAME = "adminx";// 帐户名

	// 访问操作(1:001,3:011,7:111), 分别表示禁止,可读,写,
	// 其意义还可以继续扩展 如1:0001,3:0011,7:0111,15:1111
	static String[] OPE_MODE = { "1", "3", "7","15"};

	public static void main(String[] args) throws Exception {

		// 获得安全访问控制操作句柄,之后相关的审计操作从此展开
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject ums = new UmsProtectedobject();
		String querycondition = " and naturalname like '" + APPNAME + "%'";
		List list = resourceRmi.fetchResource(ums, null, querycondition);
		for(Iterator iter = list.iterator();iter.hasNext();){
			UmsProtectedobject element = (UmsProtectedobject) iter.next();
			System.out.println(element.getNaturalname());
		}

//		 可以被授予的资源
		List authorizationResourcde = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject element = (UmsProtectedobject) iter.next();
			String naturalname = element.getNaturalname();
			if (cupmRmi.checkUserPermission("0001", ACCOUNT_NAME, naturalname,
					OPE_MODE[3])) {// 该用户有该资源的写权限,授予
				System.out.println("许可:" + ACCOUNT_NAME + ",对资源:" + naturalname
						+ ",操作:" + OPE_MODE[3]);
				authorizationResourcde.add(element);
				// 登记日志
				boolean result = cupmRmi.log(naturalname, ACCOUNT_ADDRESS,ACCOUNT_NAME,"xxxxxxxxxx",
						"xxxxxxxxxx");
				System.out.println(result);
			}

		}

	}
	
//	
//	public static void main(String[] args) throws Exception {
//		CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");
//		// System.out.println(ps.checkRolePermission("Robanco@oesee.com",
//		// "RESOURCE.RESOURCE.APP_FJGL.AA", "7"));
////		System.out.println(ps.checkUserPermission("0001","adminx", "DEPT.DEPT.XXC", "15"));
////		System.out.println(ps.checkRolePermission("C", "DEPT.DEPT.XXC", "15"));
////		System.out.println(ps.checkRoleSelfPermission("C", "DEPT.DEPT.XXC", "15"));
//		// ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
//		// UmsProtectedobject pro=new UmsProtectedobject();
//		// pro.setOu("1");
//		// rs.fetchResource(pro, null,"");
//
//		// //
//		// // List list = rs.fetchResource(new UmsProtectedobject(), null);
//		// UmsApplication app = new UmsApplication();
//		//
//		// ApplicationRmi appx = (ApplicationRmi) RmiEntry.iv("application");
//		// List list1 = appx.queryObjects(app, null, " and active='1'");
//		// for (Iterator iter = list1.iterator(); iter.hasNext();) {
//		// UmsApplication element = (UmsApplication) iter.next();
//		// System.out.println(element.getName());
//		// }
//		//
//		// appx.drop(new Long(8));
//
//		// ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
//		// UmsProtectedobject upo = new UmsProtectedobject();
//		// upo.setName("可以");
//		// upo.setNaturalname("aaaa");
//		// upo.setObjecttype(ResourceRmi._OBJ_TYPE_PAGEGROUP);
//		// upo.setInclusion(ResourceRmi._OBJ_DIRECTORY_YES);
//		// rsrmi.addResource(upo, "PAGEGROUP.PAGEGROUP");
//
//	}
}
