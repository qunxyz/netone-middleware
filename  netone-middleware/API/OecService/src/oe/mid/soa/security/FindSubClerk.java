package oe.mid.soa.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class FindSubClerk {

	public static void main(String[] args) throws Exception {

		CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
		cupm.checkUserPermission("0000", "cccddd", "dept.dept", "7");
		System.out.println("ok");

		// String usercode[] = fetchSubUserCode("adminx");
		// for (int i = 0; i < usercode.length; i++) {
		// System.out.println(usercode[i]);
		// }
	}

	/**
	 * 根据当前登陆者返回所有的隶属的下级人员
	 * 
	 * @param fatherusercode
	 *            当前登陆者的登陆名
	 * @return 隶属于当前登陆者的所有子人员登陆名数组
	 * @throws Exception
	 */
	public static String[] fetchSubUserCode(String fatherusercode)
			throws Exception {

		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		Clerk clerkFather = resourceRmi.loadClerk("0000", fatherusercode);
		String fatherDeptNaturalname = clerkFather.getExtendattribute();
		// 获得所有的子部门
		List listSub = resourceRmi
				.subResourceByNaturalname(fatherDeptNaturalname);

		List listClerkCode = new ArrayList();
		for (Iterator iterator = listSub.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			listClerkCode.add(object.getActionurl());
		}
		return (String[]) listClerkCode.toArray(new String[0]);
	}

}
