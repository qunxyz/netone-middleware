package oe.mid.soa.security;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

/**
 * 鉴权应用 Sample
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class AuthenticationSample {

	// 资源名
	static String RESOURCE_ELEMENT = "RESOURCE.RESOURCE.APP_FJGL.AA";

	// 访问操作(1:001,3:011,7:111), 分别表示禁止,可读,写,
	// 其意义还可以继续扩展 如1:0001,3:0011,7:0111,15:1111
	static String[] OPE_MODE = { "1", "3", "7" };

	static String ACCOUNT_NAME = "Robanco@oesee.com";// 帐户名

	static String ACCOUNT_PASSWORD = "admin@1234";

	// 角色名
	static String ROLE_NAME = "";



	public static void main(String[] args) throws Exception {
		// 获得安全访问控制操作句柄,之后相关的鉴权操作从此展开
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");

		// 访问控制,检验某个帐户对某个资源的写权限
		boolean rs1 = cupmRmi.checkUserPermission("0000", ACCOUNT_NAME,
				RESOURCE_ELEMENT, OPE_MODE[2]);// 执行访问控制

		// 访问控制,检验某个角色对某个资源的写权限
		boolean rs2 = cupmRmi.checkRolePermission(ROLE_NAME, RESOURCE_ELEMENT,
				OPE_MODE[2]);

		// 访问控制,检验某个角色对某个资源的写权限,如果该角色有父角色,那么忽略父角色的权限,仅仅使用本角色自身定义的权限
		boolean rs3 = cupmRmi.checkRoleSelfPermission(ROLE_NAME,
				RESOURCE_ELEMENT, OPE_MODE[2]);

		// 初始化,后台服务中的缓存,每次任何的访问控制时,后台服务都有可能缓存访问控制的结果(由后台服务的配置决定,可以配置是否使用cache)
		cupmRmi.initCacheall();
		// 初始化某个指定角色的缓存
		cupmRmi.initCacheRole(ROLE_NAME);
		// 初始化某个指定帐户的缓存
		cupmRmi.initCacheUser(ACCOUNT_NAME);

		// ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		// UmsProtectedobject pro=new UmsProtectedobject();
		// pro.setOu("1");
		// rs.fetchResource(pro, null,"");

		// //
		// // List list = rs.fetchResource(new UmsProtectedobject(), null);
		// UmsApplication app = new UmsApplication();
		//
		// ApplicationRmi appx = (ApplicationRmi) RmiEntry.iv("application");
		// List list1 = appx.queryObjects(app, null, " and active='1'");
		// for (Iterator iter = list1.iterator(); iter.hasNext();) {
		// UmsApplication element = (UmsApplication) iter.next();
		// System.out.println(element.getName());
		// }
		//
		// appx.drop(new Long(8));

		// ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		// UmsProtectedobject upo = new UmsProtectedobject();
		// upo.setName("可以");
		// upo.setNaturalname("aaaa");
		// upo.setObjecttype(ResourceRmi._OBJ_TYPE_PAGEGROUP);
		// upo.setInclusion(ResourceRmi._OBJ_DIRECTORY_YES);
		// rsrmi.addResource(upo, "PAGEGROUP.PAGEGROUP");

	}
}
