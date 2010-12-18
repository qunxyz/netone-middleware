package oe.security3a.client.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;


/**
 * ��Դ����,�������ڵ���Դ����,����������Ա�����Դ�Ƚ���������ר�������Ա����صķ��� ��Щ������ClerkΪ��׺
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 */
public interface ResourceRmi extends Remote {
	/**
	 * ��ɫ�ƶ�(������)
	 * 
	 * @param ouOri
	 * @param ouAim
	 * @return
	 * @throws RemoteException
	 */
	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim)
			throws RemoteException;

	/**
	 * ��Ա�ƶ�(������)
	 * 
	 * @param ouOri
	 * @param ouAim
	 * @return
	 * @throws RemoteException
	 */
	boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim)
			throws RemoteException;

	/**
	 * ��ѯ����������ְԱ
	 * 
	 * @param code �û�������
	 * @param user	Clerk
	 * @return
	 * @throws RemoteException
	 */
	List fetchClerk(String code, Clerk user, Map comparekey, String condition)
			throws RemoteException;

	/**
	 * �������Ƿ��Ѿ�����(�÷�����Ҫ��addResource ��صķ�������)
	 * 
	 * @param naturalname
	 * @return
	 */
	boolean checkExist(String naturalname) throws RemoteException;

	/**
	 * ����ְԱ
	 * 
	 * @param code �û�������
	 * @param user Clerk
	 * @throws RemoteException
	 */
	boolean addClerk(String code, Clerk user) throws RemoteException;

	/**
	 * ɾ��ְԱ
	 * 
	 * @param code ������ ����Clerk��officeNO
	 * @param id	Clerk �� description�ֶ�
	 * @return
	 * @throws RemoteException
	 */
	boolean dropClerk(String code, String id) throws RemoteException;

	/**
	 * ����ְԱ
	 * 
	 * @param code �û�������
	 * @param user Clerk
	 * @return
	 * @throws RemoteException
	 */
	boolean updateClerk(String code, Clerk user) throws RemoteException;

	/**
	 * װ��ְԱ
	 * ʹ��dbʱ��ָ�����Ǹ����ѯ
	 * @param code ������
	 * @param id id
	 * @return
	 * @throws RemoteException
	 */
	Clerk loadClerk(String code,String id) throws RemoteException;

	/**
	 * ��ѯ���е���Դ
	 * 
	 * @param pro
	 * @return
	 * @throws RemoteException
	 */
	List fetchResource(UmsProtectedobject pro, Map comparekey, String condition)
			throws RemoteException;

	/**
	 * ��ѯ���е���Դ
	 * 
	 * @param pro
	 * @param comparekey
	 * @return
	 * @throws RemoteException
	 */
	List fetchResource(UmsProtectedobject pro, Map comparekey)
			throws RemoteException;

	/**
	 * �����Դ<br>
	 * ע����ӵ�ʱ�� upo�е�naturalname ����ȫ���������������֡�����upo��parentdir������д,���Ǹ�upo��Ӧ�ĸ����id
	 * 
	 * @param upo
	 * @throws RemoteException
	 */
	Serializable addResource(UmsProtectedobject upo) throws RemoteException;

	/**
	 * �����Դ,�ڸ��ڵ��´���, ע���ڴ�����ʱ���Զ���д aggregation��ֵ ���ִ�С���� <br>
	 * ע����ӵ�ʱ�� upo�е�naturalname ����ȫ���������������֡���һ������natrualname�Ǹ�upo�ĸ���������<br>
	 * upo�� parentdir���Բ���д
	 * 
	 * @param upo
	 * @throws RemoteException
	 */
	Serializable addResource(UmsProtectedobject upo, String natrualname)
			throws RemoteException;

	/**
	 * ��ĳ����¿�����������Դ(Ŀ����Դ���еĵ�activeUrl�ǿգ���ôactiveUrl�����ڱ���Դ��Դ��naturalname)
	 * 
	 * @param copyToId
	 *            Ҫ��ÿ����ĸ����ID
	 * @param copyNatrualnames
	 *            ������Ҫ��������Դ����
	 * @param copylevel
	 *            ���б�������Դ�Ŀ������ (ע�⣺������ϵͳ���Զ���鿽������Ƿ񳬳����еĲ��,�����Զ�����ʵ���������)
	 * @throws RemoteException
	 */
	void addFormCopyResource(String copyToId, String[] copyNatrualnames,
			int copylevel) throws RemoteException;



	/**
	 * ɾ����Դ
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropResource(String id) throws RemoteException;

	/**
	 * ɾ����Դ
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropAllSubResource(String id) throws RemoteException;

	/**
	 * ������Դ
	 * 
	 * @param upo
	 * @return
	 * @throws RemoteException
	 */
	boolean updateResource(UmsProtectedobject upo) throws RemoteException;

	/**
	 * ������ԴID��װ����Դ
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	UmsProtectedobject loadResourceById(String id) throws RemoteException;

	/**
	 * ������Դ��naturalname��װ����Դ
	 * 
	 * @param naturalname
	 * @return
	 * @throws RemoteException
	 */
	UmsProtectedobject loadResourceByNatural(String naturalname)
			throws RemoteException;

	/**
	 * ������Դ�ĸ��ڵ��ID ������е��ӽڵ㣬ֻ�����һ��
	 * 
	 * @param parentid ����Դ��ID�ţ�
	 * @return ��������Դ��ע�⣬ֻ����һ������Դ
	 * @throws RemoteException
	 */
	List subResource(String parentid) throws RemoteException;
	
	
	/**
	 * ������Դ�ĸ��ڵ��Naturalname��������е�����Դ
	 * @param naturalname ����Դ��naturalname
	 * @return �������е�����Դ
	 * @throws RemoteException
	 */
	List subResourceByNaturalname(String naturalname) throws RemoteException;

	/**
	 * ��Դ�ľۺϿ���,��λ������
	 * 
	 * @param currentid
	 * @return
	 * @throws RemoteException
	 */
	boolean moveUpResource(String currentid) throws RemoteException;

	/**
	 * ��Դ�ľۺϿ���,��λ������
	 * 
	 * @param currentid
	 * @return
	 * @throws RemoteException
	 */
	boolean moveDownResource(String currentid) throws RemoteException;

	/**
	 * ���ݽ�ɫ������������з��������Ľ�ɫ
	 * 
	 * @param role
	 * @param comparekey
	 * @return
	 * @throws RemoteException
	 */
	List fetchRole(UmsRole role, Map comparekey, String conditionPre)
			throws RemoteException;

	/**
	 * �����û���ɫ���������з����������û���ɫ������ϵ
	 * 
	 * @param role
	 * @return
	 * @throws RemoteException
	 */
	List fetchUser2role(String code, User2Role user2role, Map map, String condtion)
			throws RemoteException;

	/**
	 * ���ӽ�ɫ
	 * @param code 
	 * 				��Ա�������ڣ��ڴ�����ɫʱ����������Ա����ô�ͻ������Ա���ɫ�Ĺ�ϵ��
	 * 				�����Ҫָ����Ա��������
	 * @param map	�������UmsRole��UmsUser2Role,UmsRole2role,UmsRolepermission
	 * 				map.put(RoleDao._ROLE, urole);
					map.put(RoleDao._ROLE2USER, userrole);
					map.put(RoleDao._ROLE2ROLE, urolerole);
					map.put(RoleDao._ROLE2PERMISSION, urplist);
	 * @throws RemoteException
	 */
	Serializable addRole(String code, Map map) throws RemoteException;

	/**
	 * ɾ����ɫ
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropRole(String id) throws RemoteException;

	/**
	 * װ�ؽ�ɫ
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	UmsRole loadRole(Long id) throws RemoteException;

	/**
	 * ����ɾ������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	boolean dropRoles(List objs) throws RemoteException;

	/**
	 * ����ɾ������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	boolean dropUserRoles(String code, List objs) throws RemoteException;

	/**
	 * ���½�ɫ
	 * @param code 
	 * 				�û��������ڣ����¸ý�ɫ���漰���иý�ɫ������û�ʱ����Ҫ���¸ý�ɫ�͸��������µ��û�֮��Ĺ�ϵ
	 * @param map
	 * @return
	 * @throws RemoteException
	 */
	boolean updateRole(String code, Map map) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param code 	
	 * 			  �û�������
	 * @param obj
	 *            Clerk
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	List queryObjectsClerk(String code, Clerk clerk, Map comparisonKey, int from, int to)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param code
	 *            �û�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            Clerk
	 * 
	 * @return long ��¼����
	 */
	long queryObjectsNumberClerk(String code, Clerk clerk, Map comparisonKey)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	List queryObjectsRole(UmsRole role, Map comparisonKey, int from, int to)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @return long ��¼����
	 */
	long queryObjectsNumberRole(UmsRole role, Map comparisonKey)
			throws RemoteException;

	/**
	 * �����û���ɫ�Ĺ�ϵ
	 * 
	 * @param code �û���������
	 * @param userid �û�id
	 * @param role
	 *            �㷨��<br>
	 *            1�����ݴ����useridֵ��ѯ���ݿ⣬������õ�����ȫ��ɾ��
	 *            2�����ݴ����UmsRole�������roleid�봫���userid�����µ�UmsUser2role���󣬼��뼯���У���һ�𴴽�
	 */
	void roleRelationupdate(String code, String userid, List<UmsRole> role)
			throws RemoteException;

	/**
	 * ��������<br>
	 * ע: �÷�������Ҫ���ȶ��Žӿڷ���Ϣ���û�
	 * 
	 * @param code �û�������
	 * @param clerk 
	 * @return ��ʼ������
	 */
	boolean resetPassword(String code, Clerk clerk) throws RemoteException;

	/**
	 * ��ȡ�û����еĽ�ɫ
	 * 
	 * @param code �û�������
	 * @param userid �û�id
	 * @return List<UmsRole>
	 * @throws Exception
	 *             �㷨��<br>
	 *             1�����ݴ����cnֵ��ѯ���ݿ⣬���UmsUser2role���󼯺�
	 *             2����ÿ�������еĶ���id��װ�س�UmsRole�����ٰѽ�������¼����з���
	 */
	List<UmsRole> getUserRoles(String code, String userid) throws Exception;

	/**
	 * ���ӵ�иý�ɫ���û�
	 * 
	 * @param code �û���������
	 * @param roleId ��ɫid
	 * @return List<Clerk> �㷨��<br>
	 *         1������roleId��ѯ�����ڵ�UmsUser2role����
	 *         2������UmsRole2role2�����е�userid��װ�س�SearchResult
	 *         3���ж��Ƿ���سɹ�,������ʼ��SearchResult�е����Թ���Clerk
	 */
	List<Clerk> fetchUser(String code, String roleId) throws RemoteException;

	/**
	 * ��ñ�roleId�̳еĽ�ɫ(��ǰֻ֧�ֵ��Ӽ̳�)
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ���̳еĽ�ɫ
	 * @return UmsRole �㷨��<br>
	 *         1������roleId�ͼ̳й�ϵ��ѯ�����ڵ�UmsRole2role2����
	 *         2������UmsRole2role2�����е�relationalrolemainid���õ�relationalrolesubid
	 *         3����relationalrolesubidװ��UmsRole,�ж��Ƿ���سɹ�����
	 */
	UmsRole fetchExtendedRole(String roleId) throws RemoteException;

	/**
	 * ��ý�ɫ����Ȩ
	 * 
	 * @param roleId
	 * @return List<UmsRolepermission>
	 */
	List<UmsRolepermission> fetchPermission(String roleId)
			throws RemoteException;

	/**
	 * ��½��֤
	 * 
	 * @param code ������
	 * @param name �û���
	 * @param pass ����
	 * @return clerk ����operationinfo�ֶ��Ƿ�����֤�Ĵ�����Ϣ
	 * @throws RemoteException
	 */
	Clerk validationUserOpe(String code, String name, String pass)
			throws RemoteException;
	
	
	/**
	 * �ʺ�ͬ��
	 * @param ope	ͬ������
	 * @param code	�û�������
	 * @param usercode	�û���
	 * @returnͬ�����ص���Ϣ
	 * @throws RemoteException
	 */
	public String[] SyncUser(String ope, String code, String usercode)throws RemoteException;
	
	/**
	 * ��Դ�ķ�ҳ��ѯ
	 * @param upo
	 * @param comparisonKey
	 * @param from
	 * @param to
	 * @return
	 * @throws RemoteException
	 */
	List queryObjectProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, int from, int to, String conditionPre) throws RemoteException;
	
	/**
	 * ��Դ�ķ�ҳ��ѯ���������
	 * @param upo
	 * @param comparisonKey
	 * @param conditionPre
	 * @return
	 * @throws RemoteException
	 */
	long queryObjectNumberProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, String conditionPre) throws RemoteException;

}
