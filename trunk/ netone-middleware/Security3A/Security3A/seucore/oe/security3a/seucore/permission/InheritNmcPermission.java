package oe.security3a.seucore.permission;

import java.security.Permission;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 多个NmcPermissionCollection的集合，实现权限继承的算法， 权限判断取权限最近节点的权限的值。
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
	 * 返回权限值
	 * 
	 * @param permission
	 *            Permission
	 * @return int 为0表示没有权限，1表示可见，3表示可读，7表示读写
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
	 * 权限判断
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
					// ---- 支持继承模式，如果出现父权限与以当前的权限定义冲突，那么以当前权限为主
					String premode = messages.getString("permissionMode");
					if ("1".equals(premode)) {
						// 只有基于DN的授权模式,有需要这个逻辑
						if (np.isMatched()) {
							success = false;
							break;
						}
					}
					// --------------------注释掉改内容 则使用最大权限原则，不区分父子权限 2008-1-24
				}
			}
			if (success) {
				return true;
			}
		}
		return false;
	}

}
