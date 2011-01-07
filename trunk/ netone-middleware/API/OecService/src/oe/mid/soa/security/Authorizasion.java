package oe.mid.soa.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * ��ȨӦ�� Sample ��ȨӦ����һ���ۺ�Ӧ��,ͨ��Auditing,Authentication��Resource �����ۺ���ʵ��<br>
 * ��Sample��������ĳ���û���������ӵ�е� ���� Sample �µ����пɲ�������Դ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Authorizasion {

	static String APPNAME = "DEPT.DEPT";

	static String ACCOUNT_ADDRESS = "10.1.5.202";

	static String ACCOUNT_NAME = "Robanco@oesee.com";// �ʻ���

	// ���ʲ���(1:001,3:011,7:111), �ֱ��ʾ��ֹ,�ɶ�,д,
	// �����廹���Լ�����չ ��1:0001,3:0011,7:0111,15:1111
	static String[] OPE_MODE = { "1", "3", "7" };

	public static void main(String[] args) throws Exception {

		// ��ð�ȫ���ʿ��Ʋ������,֮����ص���Ʋ����Ӵ�չ��
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
	
		// ��Դ�ķ��ʿ��ƾ��
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject ums = new UmsProtectedobject();
		String querycondition = " and naturalname like '" + APPNAME + "%'";
		List list = resourceRmi.fetchResource(ums, null, querycondition);
		
		System.out.println(list.size());

		// ���Ա��������Դ
		List authorizationResourcde = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject element = (UmsProtectedobject) iter.next();
			String naturalname = element.getNaturalname();
			if (cupmRmi.checkUserPermission("0000", ACCOUNT_NAME, naturalname,
					OPE_MODE[2])) {// ���û��и���Դ��дȨ��,����
				authorizationResourcde.add(element);
				// �Ǽ���־
				cupmRmi.log(naturalname, ACCOUNT_NAME, ACCOUNT_ADDRESS, "ok",
						"get resourcde");
			}
		}

	}
}
