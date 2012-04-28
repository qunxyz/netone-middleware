package com.jl.common.security3a;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

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
	 * ��������û� �ỰID
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String onlineUserSessionId(HttpServletRequest request) throws Exception;

	/**
	 * ���ݿͻ�idװ�ؿͻ���Ϣ
	 * 
	 * @param clientid
	 * @return
	 */
	Client3A loadUser(String clientid) throws Exception;

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
	 * ����ID��Ȩ
	 * @param clientid
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean permissionById(String clientid, String id)
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
	 * �ָ������ʻ�
	 * 
	 * @param clientId
	 *            �ʻ���
	 * @return
	 */
	String recoveryAccount(String clientId) throws Exception;

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

	/**
	 * ���ȫ�ֱ���
	 * 
	 * @param key
	 * @return
	 */
	String getTopEnv(String key);

	/**
	 * ����û��Ƿ񱻽���
	 * 
	 * @param usercode
	 * @return
	 */
	boolean checkForbid(String usercode);

	/**
	 * �����û���¼������ò���Naturalname����;���ڼ�Ȩ��ʹ�ã����ж��û���������ϵ
	 * 
	 * @param clientid
	 * @return
	 */
	String deptNameOfUser(String clientcode) throws Exception;

	/**
	 * ���ݽ�ɫ����û�
	 * 
	 * @param roleid
	 *            ��ɫid
	 * @param commiter
	 *            ��ǰ�ύ��
	 * @param isFlowRole
	 *            �Ƿ�ʹ�����̽�ɫ
	 * @return username[usercode],username[usercode]
	 * @throws Exception
	 */
	String listUserByRoleId(String[] roleid, String commiter, boolean isFlowRole)
			throws Exception;

	/**
	 * ���ݲ���id����û���Ϣ
	 * 
	 * @param deptid
	 *            ����id
	 * 
	 * @return username[usercode],username[usercode]
	 * @throws Exception
	 */
	String listUserByDeptId(String[] deptid) throws Exception;

	/**
	 * �����û������û�
	 * 
	 * @param deptid
	 * @return username[usercode],username[usercode]
	 */
	String listUserByTeamId(String[] deptid) throws Exception;

	/**
	 * �����ԴĿ¼����һ��
	 * 
	 * @param parentNaturalname
	 *            ��ԴĿ¼naturalname
	 * @return ������һ����Ŀ¼
	 */
	List<Resource> listDirRs(String parentNaturalname) throws Exception;

	/**
	 * �����Դ�е��ļ�
	 * 
	 * @param parentNaturalname
	 *            ��ǰĿ¼naturalname
	 * @param rsType
	 *            ��Դ���� ͨ���Ƕ�̬�����ֶ�����
	 * @param from
	 *            ��ʼλ��
	 * @param size
	 *            ��ҳ��
	 * @param condition
	 *            ��������
	 * @return
	 */
	List<Resource> listRsInDir(String parentNaturalname, String rsType,
			int from, int size, String condition) throws Exception;

	/**
	 * ͳ����Դ��
	 * 
	 * @return
	 */
	long countRsInDir(String parentNaturalname,String rsType, String condition)
			throws Exception;

	/**
	 * װ����Դ
	 * 
	 * @param parentid
	 *            ��Դ�ĸ�id
	 * @param from
	 *            ��ҳ��ʼ��
	 * @param to
	 *            ��ҳ�յ�
	 * @param condition
	 *            ��ѯ����
	 * @return
	 */
	List<UmsProtectedobject> listOu(String parentid, int from, int to,
			String condition) throws Exception;

	/**
	 * ��Դ����
	 * 
	 * @param parentid
	 *            ��Դ�ĸ�id
	 * @param condition
	 *            ��ѯ����
	 * @return
	 */
	long listOuTotal(String parentid, String condition) throws Exception;

}
