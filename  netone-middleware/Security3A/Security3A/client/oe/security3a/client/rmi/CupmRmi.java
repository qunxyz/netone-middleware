package oe.security3a.client.rmi;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsOperationLog;

/**
 * ��ȫ���ʿ��Ʒ���
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 */

public interface CupmRmi extends Remote {
	/**
	 * todo��xml�е������4���ṹ����
	 */
	String _TODO_SUBJECT_ATTRIBUTE_OPE = "ope";

	String _TODO_SUBJECT_ATTRIBUTE_CONDITION = "condition";

	String _TODO_SUBJECT_ATTRIBUTE_PARTICIPANT = "participant";

	String _TODO_SUBJECT_ATTRIBUTE_PASSWORD = "password";

	/**
	 * 
	 */
	String[] _TODO_SUBJECT_ATTRIBUTE_ALL = { _TODO_SUBJECT_ATTRIBUTE_OPE,
			_TODO_SUBJECT_ATTRIBUTE_CONDITION,
			_TODO_SUBJECT_ATTRIBUTE_PARTICIPANT,
			_TODO_SUBJECT_ATTRIBUTE_PASSWORD };

	/**
	 * todo��ope��7�������߼�ֵ
	 */
	String _OPE_GET_RESOURCE = "getResource";

	String _OPE_ADD_RESOURCE = "addResource";

	String _OPE_GET_CLERK = "getClerk";

	String _OPE_ADD_CLERK = "addClerk";

	String _OPE_CHECK_ROLE_PERMISSION = "checkRolePermission";

	String _OPE_CHECK_CLERK_PERMISSION = "checkClerkPermission";

	String _OPE_LOGIN = "login";

	/**
	 * �����߼�ֵ����
	 */
	String[] _TODO_ALL = { _OPE_LOGIN, _OPE_GET_RESOURCE, _OPE_ADD_RESOURCE,
			_OPE_GET_CLERK, _OPE_ADD_CLERK, _OPE_CHECK_ROLE_PERMISSION,
			_OPE_CHECK_CLERK_PERMISSION };

	/**
	 * ��Ȩ�߼�,�жϽ�ɫ�Ƿ���ĳ����Դ��Ȩ�ޡ�
	 * 
	 * @param roleName
	 *            ��ɫ��dnname(��·����naturalname)
	 * @param dnname
	 *            ���������dnname����(��·����naturalname)
	 * @param action
	 *            ����(ͨ����С����ֱ�Ϊ 1,3,7,15,31.....) 2��n�η���1
	 * @return
	 */
	public boolean checkRolePermission(String rolename, String dnname,
			String action) throws Exception;

	/**
	 * ��Ȩ�߼��жϽ�ɫ�����Ƿ���ĳ����Դ��Ȩ��-���ķ���(����OU)
	 * 
	 * @param roleid
	 *            ��ɫid
	 * @param ou
	 *            ���������ou����(��·����id��)
	 * @param action
	 *            ����(ͨ����С����ֱ�Ϊ 1,3,7,15,31.....) 2��n�η���1
	 * @return
	 */
	public boolean checkRolePermissionCore(String roleid, String ou,
			String action) throws Exception;

	/**
	 * ��Ȩ�߼�,�жϽ�ɫ�����Ƿ���ĳ����Դ��Ȩ��(��������ɫ����û�а����̳еĽ�ɫ��Ȩ��)
	 * 
	 * @param roleName
	 *            ��ɫ��dnname(��·����naturalname)
	 * @param dnname
	 *            ���������dnname����(��·����naturalname)
	 * @param action
	 *            ����(����Ŀǰ������ģʽ,���Լ�����չ 1,3,7)
	 * @return
	 */
	public boolean checkRoleSelfPermission(String rolename, String dnname,
			String action) throws Exception;

	/**
	 * ��Ȩ�߼�,�жϽ�ɫ�Ƿ���ĳ����Դ��Ȩ��(��������ɫ����û�а����̳еĽ�ɫ��Ȩ��)
	 * 
	 * @param roleid
	 *            ��ɫid
	 * @param ou
	 *            ���������ou����(��·����id��)
	 * @param action
	 *            ����(ͨ����С����ֱ�Ϊ 1,3,7,15,31.....) 2��n�η���1
	 * @return
	 */
	public boolean checkRoleSelfPermissionCore(String roleid, String ou,
			String action) throws Exception;

	/**
	 * ��Ȩ�߼�,�ж��û��Ƿ���ĳ����Դ��Ȩ��
	 * 
	 * @param code
	 *            �û������ڵ���Ĭ��Ϊ0000
	 * @param loginname
	 *            �û���½��
	 * @param dnname
	 *            ���������dnname(��·����naturalname)
	 * @param action
	 *            ����(����Ŀǰ������ģʽ,���Լ�����չ 1,3,7,15)
	 * @return
	 */
	public boolean checkUserPermission(String code, String loginname,
			String dnname, String action) throws Exception;

	/**
	 * ��Ȩ�߼�,�ж��û��Ƿ���ĳ����Դ��Ȩ��-���ķ���(����ID)
	 * 
	 * @param code
	 *            �û������ڵ���Ĭ��Ϊ0000
	 * @param loginname
	 *            �û���½��
	 * @param ou
	 *            ���������ou����(��·����id��)
	 * @param dnname
	 *            ���������dnname(��·����naturalname)
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserPermissionCore(String code, String loginname,
			String ou, String action) throws Exception;

	/**
	 * ����߼�,������־
	 * 
	 * @param dnname
	 *            ��������Դ��naturalname
	 * @param ip
	 *            �����ߵ���Դ,����:IP
	 * @param userid
	 *            �����ߵ��ʻ�
	 * @param rsinfo
	 *            ��Դ�������
	 * @param remark
	 *            ��Դ�����Ĳ�������
	 * @return
	 * @throws Exception
	 */
	public boolean log(String dnname, String ip, String userid, String rsinfo,
			String remark) throws Exception;

	/**
	 * ����߼�,��ѯ��־
	 * 
	 * @param condition
	 *            �ο�SQL92��where����,�ֹ��༭�ֶ�userid,
	 *            operatetime,operationid,resultinfo,userip,remark ���������
	 * @return
	 */
	public String[] queryLog(String condition) throws Exception;

	/**
	 * ͨ���߼�,����XMLģʽ�ķ��ʿ���
	 * 
	 * @param code
	 *            �û�������
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String todo(String code, String request) throws Exception;

	/**
	 * ��ȫϵͳ�߼�,��ʼ��ȫ������
	 * 
	 * @throws Exception
	 */
	public void initCacheall() throws Exception;

	/**
	 * ��ȫϵͳ�߼�,��ʼ��ָ���û��Ļ���
	 * 
	 * @param userid
	 * @throws Exception
	 */
	public void initCacheUser(String userid) throws Exception;

	/**
	 * ��ȫϵͳ�߼�,��ʼ��ָ����ɫ�Ļ���
	 * 
	 * @param rolename
	 *            ��ɫ��dnname(��·����naturalname)
	 * @throws Exception
	 */
	public void initCacheRole(String rolename) throws Exception;

	/**
	 * ��ȫϵͳ�߼�,��ʼ��ָ����ɫ�Ļ���
	 * 
	 * @param roleid
	 * @throws Exception
	 */
	public void initCacheRoleCore(String roleid) throws Exception;

	/**
	 * ���3A��ȫ��������Ϣ
	 * 
	 * @param name
	 *            ������õ�key����Щ������Ϣͨ���ڰ�ȫ��rmi�����jndi��
	 * @return
	 * @throws Exception
	 */
	public String fetchConfig(String name) throws Exception;

	/**
	 * ��ѯ��־��Ϣ
	 * 
	 * @param uolog
	 * @param compation
	 * @param condition
	 * @param form
	 * @param to
	 * @return
	 */
	public List logList(UmsOperationLog uolog, Map compation, String condition,
			int form, int to) throws Exception;

	/**
	 * ��ѯ��־��Ϣ
	 * 
	 * @param uolog
	 * @param compation
	 * @param condition
	 * @param form
	 * @param to
	 * @return
	 */
	public long logsNumber(UmsOperationLog uolog, Map compation,
			String condition) throws Exception;
}
