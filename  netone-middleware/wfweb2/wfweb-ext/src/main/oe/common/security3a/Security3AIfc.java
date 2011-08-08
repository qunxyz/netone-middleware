package oe.common.security3a;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * DRP��ȫӦ�ýӿ�<br>
 * 
 * ҵ��ϵͳ��Ҫ�����ⲿ������ר�Ű�ȫ����������ҵ��ϵͳ�ڲ��İ�ȫ���ýӿ���Ϊһ��������Ϊ�˷����ϵͳ��ʵ��Ӧ���ܿ�<br>
 * �л���ͬ�İ�ȫ��Ʒ��֧�ָ��������ڲ���ȫ����<br>
 * 
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface Security3AIfc {
	/**
	 * ��Ȩ��ɵı�־ PERMISSION_ALLOW
	 */
	String PERMISSION_ALLOW = "3";
	
	String PERMISSION_FOBIT = "1";
	/**
	 * ����ִ�гɹ��󣬷��ؽ����ǰ׺ ORG_OPE_TIP_SUCCESS
	 */
	String OPE_TIP_SUCCESS = "SUCCESS:";
	/**
	 * ����ִ��ʧ�ܺ󣬷��ؽ����ǰ׺ ORG_OPE_TIP_ERROR
	 */
	String OPE_TIP_ERROR = "ERROR:";
	/**
	 * �ʻ����ñ�־ ACCOUNT_FORBIT_MARK
	 */
	String ACCOUNT_FORBIT_MARK = "9$9$";
	/**
	 * �ⲿ��ȫ�����ĸ�Ӧ��Ŀ¼
	 */
	String DEFAULT_DEPT_APP = "DEPT";
	/**
	 * �ʻ�����������
	 */
	String DEMAIN_CODE = "0000";

	/**
	 * ��������û�
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Client3A onlineUser(HttpServletRequest request) throws Exception;

	/**
	 * ���ݿͻ�idװ�ؿͻ���Ϣ
	 * 
	 * @param clientid
	 * @return
	 */
	Client3A loadUser(String clientid)throws Exception;

	/**
	 * �Ե�ǰ�û����м�Ȩ��ʶ�����Ƿ���Ȩ��
	 * 
	 * @param clientid
	 *            �ʻ�ID
	 * @param resourceNaturalname
	 *            ��Ҫ��Ȩ����Դ���������ⲿ��ȫ������ע�����ԴĿ¼Naturalname
	 * @return
	 */
	boolean permission(String clientid, String resourceNaturalname)
			throws Exception;

	/**
	 * �������ʺ�
	 * 
	 * @param bussDirId
	 *            ҵ��ϵͳ��Ŀ¼id
	 * @param clientId
	 *            �ʺ��� ע���ʻ�����Ψһ�ģ����ڵ�¼ʱʶ���û����
	 * @param displayname
	 *            �ʻ���ʾ��
	 * @param extAttribute
	 *            �ʻ�����չ����
	 * @return �������
	 */
	String newAccount(String bussDirId, String clientId, String displayname,
			Map extAttribute) throws Exception;

	/**
	 * �༭�ʻ�����
	 * 
	 * @param bussDirId
	 *            ҵ��ϵͳ��Ŀ¼id
	 * @param clientId
	 *            �ʺ��� ע���ʻ�����Ψһ�ģ����ڵ�¼ʱʶ���û����
	 * @param displayname
	 *            �ʻ���ʾ��
	 * @param extAttribute
	 *            �ʻ�����չ����
	 * @return
	 * 
	 * ע���÷���������û���ʵ������Ŀ¼�����bussDirId�Ƿ�һ��
	 */
	String editAccount(String bussDirId, String clientId, String displayname,
			Map extAttribute) throws Exception;

	/**
	 * ɾ���ʺ�
	 * 
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	String deleteAccount(String clientId) throws Exception;

	/**
	 * �����ʻ�
	 * 
	 * @param clientId
	 *            �ʻ���
	 * @return
	 */
	String fobidAccount(String clientId) throws Exception;

	/**
	 * �����µ���֯�����ڵ�
	 * 
	 * @param parentBussDirId
	 *            ��������֯��ҵ��ID
	 * @param bussDirId
	 *            ��ǰ�ڵ�Id
	 * @param displayName
	 *            ��ǰ�ڵ���ʾ��
	 * @param extAttribute
	 *            �ڵ����չ����
	 * @return
	 * @throws Exception
	 */
	String newOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception;

	/**
	 * �༭��֯�����ڵ�
	 * 
	 * @param parentBussDirId
	 *            ��������֯��ҵ��ID
	 * @param bussDirId
	 *            ��ǰ�ڵ�Id
	 * @param displayName
	 *            �ڵ����ʾ��
	 * @param extAttribute
	 *            �ڵ����չ����
	 * @return
	 * @throws Exception
	 */
	String editOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception;

	/**
	 * ɾ����֯�ڵ�
	 * 
	 * @param bussDirId
	 *            ��ǰ�ڵ�Id
	 * @throws Exception
	 */
	String deleteOrganization(String bussDirId) throws Exception;

	/**
	 * ��ð�ȫȫ�����ò���
	 * 
	 * @param key
	 * @return
	 */
	String getSecurityEnv(String key);

}
