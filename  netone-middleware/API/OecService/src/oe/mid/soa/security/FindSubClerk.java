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
	 * ���ݵ�ǰ��½�߷������е��������¼���Ա
	 * 
	 * @param fatherusercode
	 *            ��ǰ��½�ߵĵ�½��
	 * @return �����ڵ�ǰ��½�ߵ���������Ա��½������
	 * @throws Exception
	 */
	public static String[] fetchSubUserCode(String fatherusercode)
			throws Exception {

		// ��Դ�ķ��ʿ��ƾ��
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		Clerk clerkFather = resourceRmi.loadClerk("0000", fatherusercode);
		String fatherDeptNaturalname = clerkFather.getExtendattribute();
		// ������е��Ӳ���
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
