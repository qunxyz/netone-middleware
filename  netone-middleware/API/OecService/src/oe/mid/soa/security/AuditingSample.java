package oe.mid.soa.security;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;



/**
 * 审计应用Sample
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class AuditingSample {

	// 资源名
	static String RESOURCE_ELEMENT = "RESOURCE.RESOURCE.APP_FJGL.AA";

	static String ACCOUNT_NAME = "Robanco@oesee.com";// 帐户名

	static String ACCOUNT_PASSWORD = "admin@1234";

	// 角色名
	static String ROLE_NAME = "";

	static String ACCOUNT_ADDRESS = "10.1.5.202";

	public static void main(String[] args) throws Exception {
		
	
		// 获得安全访问控制操作句柄,之后相关的审计操作从此展开
		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
		//cupmRmi.log(dnname, ip, userid, rsinfo, remark)

		
		boolean rs=cupmRmi.checkUserPermission("0000", "350201", "BUSSENV.BUSSENV.SECURITY.ROLE", "15");
		System.out.println(rs);
	

//		// 审计应用,登记操作日志
//		cupmRmi.log(RESOURCE_ELEMENT, ACCOUNT_ADDRESS, ACCOUNT_NAME, "done",
//				"normal login and normal logout");
//		
//
//		// 查询出所有的日志(出于性能优化的考虑,每次查询最多返回1000条记录)
//		String[] logs = cupmRmi.queryLog("");
//		// 查出所有 Robanco@oesee.com 帐户的日志
//		String[] logDetail = cupmRmi
//				.queryLog(" and userid='Robanco@oesee.com'");

	}
}
