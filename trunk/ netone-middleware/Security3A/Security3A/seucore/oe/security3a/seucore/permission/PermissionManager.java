package oe.security3a.seucore.permission;

public interface PermissionManager {

	public String _DN_SELF_AND_SUB_SYMBOL = "*";

	public String _DN_SUB_ONLY_SYMBOL = "-";

	/**
	 * ����ܷ񴴽�natrualname,��Ҫ�Ǽ��natrualname�Ƿ��Ѿ�����
	 * 
	 * @param dnnaturalname
	 * @return
	 */
	public boolean checkCreate(String parentid,String subNatrualname) throws Exception;

	/**
	 * ����Ӧ��Id����ԴId���dn
	 * 
	 * @return
	 */
	public String getOuFromNaturalName(String dnnaturalname) throws Exception;

	/**
	 * ���ݽ�ɫ��naturalname��ý�ɫid
	 * 
	 * @param roleid
	 * @return
	 */
	public String getRoleIdFromNaturalName(String rolename) throws Exception;

	/**
	 * ͨ����ԴID�����Դ��OU��Ϣ
	 * 
	 * @param resourceid
	 * @return
	 * @throws Exception
	 */
	public String getOuFromResourceId(String resourceid) throws Exception;

	/**
	 * ͨ����ԴID�����Դ��naturalname��Ϣ
	 * 
	 * @param dnId
	 * @return
	 * @throws Exception
	 */
	public String getNaturalNameFromResourceId(String dnId) throws Exception;

}
