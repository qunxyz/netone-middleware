package oe.mid.soa.security;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

/**
 * ��ȨӦ�� Sample
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class AuthenticationSample {

	// ��Դ��
	static String RESOURCE_ELEMENT = "RESOURCE.RESOURCE.APP_FJGL.AA";

	// ���ʲ���(1:001,3:011,7:111), �ֱ��ʾ��ֹ,�ɶ�,д,
	// �����廹���Լ�����չ ��1:0001,3:0011,7:0111,15:1111
	static String[] OPE_MODE = { "1", "3", "7" };

	static String ACCOUNT_NAME = "Robanco@oesee.com";// �ʻ���

	static String ACCOUNT_PASSWORD = "admin@1234";

	// ��ɫ��
	static String ROLE_NAME = "";



	public static void main(String[] args) throws Exception {
		// ��ð�ȫ���ʿ��Ʋ������,֮����صļ�Ȩ�����Ӵ�չ��
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");

		// ���ʿ���,����ĳ���ʻ���ĳ����Դ��дȨ��
		boolean rs1 = cupmRmi.checkUserPermission("0000", ACCOUNT_NAME,
				RESOURCE_ELEMENT, OPE_MODE[2]);// ִ�з��ʿ���

		// ���ʿ���,����ĳ����ɫ��ĳ����Դ��дȨ��
		boolean rs2 = cupmRmi.checkRolePermission(ROLE_NAME, RESOURCE_ELEMENT,
				OPE_MODE[2]);

		// ���ʿ���,����ĳ����ɫ��ĳ����Դ��дȨ��,����ý�ɫ�и���ɫ,��ô���Ը���ɫ��Ȩ��,����ʹ�ñ���ɫ�������Ȩ��
		boolean rs3 = cupmRmi.checkRoleSelfPermission(ROLE_NAME,
				RESOURCE_ELEMENT, OPE_MODE[2]);

		// ��ʼ��,��̨�����еĻ���,ÿ���κεķ��ʿ���ʱ,��̨�����п��ܻ�����ʿ��ƵĽ��(�ɺ�̨��������þ���,���������Ƿ�ʹ��cache)
		cupmRmi.initCacheall();
		// ��ʼ��ĳ��ָ����ɫ�Ļ���
		cupmRmi.initCacheRole(ROLE_NAME);
		// ��ʼ��ĳ��ָ���ʻ��Ļ���
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
		// upo.setName("����");
		// upo.setNaturalname("aaaa");
		// upo.setObjecttype(ResourceRmi._OBJ_TYPE_PAGEGROUP);
		// upo.setInclusion(ResourceRmi._OBJ_DIRECTORY_YES);
		// rsrmi.addResource(upo, "PAGEGROUP.PAGEGROUP");

	}
}
