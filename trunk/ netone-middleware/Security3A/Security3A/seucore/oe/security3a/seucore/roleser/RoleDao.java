package oe.security3a.seucore.roleser;

import oe.frame.orm.Querist;
import oe.frame.orm.Serializer;

/**
 * 
 * @author chen.jia.xun
 * 
 * ע��RoleDao��dao������Ӧ�����յ�db�е�3�ű�,���Ƿֱ��Ӧ�Ŷ��� UmsRole2role,UmsUser2Role,UmsRole
 * ����dao����ڲ����Ǹ�MAP �����ó���ID��ʶ��ͬ�Ķ���
 * 
 */
public interface RoleDao extends Querist, Serializer {

	String _ROLE = "role";

	String _ROLE2USER = "role2user";

	String _ROLE2ROLE = "role2role";
	
	String _ROLE2PERMISSION = "role2permission";
	
	String _CODE = "code";

	String[][] _ROLE_RELATION_TYPE = { { "00", "����" }, { "01", "����" },
			{ "02", "�̳�" }, { "03", "�ۺ�" } };
	
	/**
	 * �ƶ���ɫ
	 * 
	 * @param ouOri
	 *            ԭʼ���ŵ�OU
	 * @param ouAim
	 *            Ŀ�겿�ŵ�OU
	 * @return
	 */
	public boolean moveRoleDept(String roleid, String ouOri, String ouAim);

}
