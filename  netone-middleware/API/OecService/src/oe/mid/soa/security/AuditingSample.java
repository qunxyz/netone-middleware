package oe.mid.soa.security;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;



/**
 * ���Ӧ��Sample
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class AuditingSample {

	// ��Դ��
	static String RESOURCE_ELEMENT = "RESOURCE.RESOURCE.APP_FJGL.AA";

	static String ACCOUNT_NAME = "Robanco@oesee.com";// �ʻ���

	static String ACCOUNT_PASSWORD = "admin@1234";

	// ��ɫ��
	static String ROLE_NAME = "";

	static String ACCOUNT_ADDRESS = "10.1.5.202";

	public static void main(String[] args) throws Exception {
		
	
		// ��ð�ȫ���ʿ��Ʋ������,֮����ص���Ʋ����Ӵ�չ��
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
		//cupmRmi.log(dnname, ip, userid, rsinfo, remark)

		
		boolean rs=cupmRmi.checkUserPermission("0000", "350201", "BUSSENV.BUSSENV.SECURITY.ROLE", "15");
		System.out.println(rs);
	

//		// ���Ӧ��,�Ǽǲ�����־
//		cupmRmi.log(RESOURCE_ELEMENT, ACCOUNT_ADDRESS, ACCOUNT_NAME, "done",
//				"normal login and normal logout");
//		
//
//		// ��ѯ�����е���־(���������Ż��Ŀ���,ÿ�β�ѯ��෵��1000����¼)
//		String[] logs = cupmRmi.queryLog("");
//		// ������� Robanco@oesee.com �ʻ�����־
//		String[] logDetail = cupmRmi
//				.queryLog(" and userid='Robanco@oesee.com'");

	}
}
