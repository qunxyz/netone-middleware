package oe.security3a.seucore.permission;

public interface PermissionManager {

	public String _DN_SELF_AND_SUB_SYMBOL = "*";

	public String _DN_SUB_ONLY_SYMBOL = "-";

	/**
	 * 检查能否创建natrualname,主要是检查natrualname是否已经存在
	 * 
	 * @param dnnaturalname
	 * @return
	 */
	public boolean checkCreate(String parentid,String subNatrualname) throws Exception;

	/**
	 * 根据应用Id和资源Id获得dn
	 * 
	 * @return
	 */
	public String getOuFromNaturalName(String dnnaturalname) throws Exception;

	/**
	 * 根据角色的naturalname获得角色id
	 * 
	 * @param roleid
	 * @return
	 */
	public String getRoleIdFromNaturalName(String rolename) throws Exception;

	/**
	 * 通过资源ID获得资源的OU信息
	 * 
	 * @param resourceid
	 * @return
	 * @throws Exception
	 */
	public String getOuFromResourceId(String resourceid) throws Exception;

	/**
	 * 通过资源ID获得资源的naturalname信息
	 * 
	 * @param dnId
	 * @return
	 * @throws Exception
	 */
	public String getNaturalNameFromResourceId(String dnId) throws Exception;

}
