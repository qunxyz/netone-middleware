package oe.security3a.seucore.permission;

import java.security.Permission;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ���NmcPermissionCollection�ļ��ϣ�ʵ��Ȩ�޼̳е��㷨�� Ȩ���ж�ȡȨ������ڵ��Ȩ�޵�ֵ��
 * 
 * @author hls
 * @version 1.0
 */
public class InheritNmcPermission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List npclist = null;

	ResourceBundle messages = ResourceBundle.getBundle("jndi", Locale.CHINESE);

	public InheritNmcPermission() {
	}

	public void addOrderdParents(List<NmcPermissionCollection> npclist) {
		this.npclist = npclist;
	}

	/**
	 * ����Ȩ��ֵ
	 * 
	 * @param permission
	 *            Permission
	 * @return int Ϊ0��ʾû��Ȩ�ޣ�1��ʾ�ɼ���3��ʾ�ɶ���7��ʾ��д
	 */
	public int getPermissionActions(Permission permission) {
		int privilege = 0;
		if (permission instanceof NmcPermission) {
			Iterator rnpiter = npclist.iterator();
			while (rnpiter.hasNext()) {
				NmcPermissionCollection rnp = (NmcPermissionCollection) rnpiter
						.next();
				int i = rnp.getPermissionActions(permission);
				if (i >= NmcPermission.VISIABLE) {
					return privilege;
				}
			}
		}
		return privilege;
	}

	/**
	 * Ȩ���ж�
	 * 
	 * @param permission
	 *            Permission
	 * @return boolean
	 */
	public boolean implies(Permission permission) {
		if (permission instanceof NmcPermission) {
			NmcPermission np = (NmcPermission) permission;
			boolean success = false;
			Iterator rnpiter = npclist.iterator();
			while (rnpiter.hasNext()) {
				np.setMatched(false);
				NmcPermissionCollection rnp = (NmcPermissionCollection) rnpiter
						.next();
				if (rnp.implies(np)) {
					success = true;
					break;
				} else {
					// ---- ֧�ּ̳�ģʽ��������ָ�Ȩ�����Ե�ǰ��Ȩ�޶����ͻ����ô�Ե�ǰȨ��Ϊ��
					String premode = messages.getString("permissionMode");
					if ("1".equals(premode)) {
						// ֻ�л���DN����Ȩģʽ,����Ҫ����߼�
						if (np.isMatched()) {
							success = false;
							break;
						}
					}
					// --------------------ע�͵������� ��ʹ�����Ȩ��ԭ�򣬲����ָ���Ȩ�� 2008-1-24
				}
			}
			if (success) {
				return true;
			}
		}
		return false;
	}

}
