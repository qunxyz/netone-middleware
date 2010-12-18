package oe.security3a.seucore.roleser;

import oe.frame.orm.Querist;
import oe.frame.orm.Serializer;

/**
 * 
 * @author chen.jia.xun
 * 
 * 注意RoleDao的dao操作对应着最终的db中的3张表,他们分别对应着对象 UmsRole2role,UmsUser2Role,UmsRole
 * 所有dao的入口参数是个MAP 其中用常量ID来识别不同的对象
 * 
 */
public interface RoleDao extends Querist, Serializer {

	String _ROLE = "role";

	String _ROLE2USER = "role2user";

	String _ROLE2ROLE = "role2role";
	
	String _ROLE2PERMISSION = "role2permission";
	
	String _CODE = "code";

	String[][] _ROLE_RELATION_TYPE = { { "00", "包含" }, { "01", "关联" },
			{ "02", "继承" }, { "03", "聚合" } };
	
	/**
	 * 移动角色
	 * 
	 * @param ouOri
	 *            原始部门的OU
	 * @param ouAim
	 *            目标部门的OU
	 * @return
	 */
	public boolean moveRoleDept(String roleid, String ouOri, String ouAim);

}
