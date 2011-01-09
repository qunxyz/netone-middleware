package oe.common.security3a;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * DRP安全实现驱动（基于NETONE中间件）
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class Security3AImpl implements Security3AIfc {

	public Client3A onlineUser(HttpServletRequest request) throws Exception {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		if (oluser == null) {
			throw new Exception(this.OPE_TIP_ERROR + "安全无法获取在线用户信息");
		}
		return loadUser(oluser.getLoginname());
		
	}
	
	public Client3A loadUser(String clientid) throws Exception  {
		Client3A user = new Client3A();

		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rs.loadClerk(DEMAIN_CODE, clientid);
		// 设置用户登录名
		user.setClientId(clientid);
		// 设置用户显示名
		user.setName(clerk.getName());
		// 设置用户隶属的 中间件目录ID
		String systemid = clerk.getDeptment();
		user.setBelongto(systemid);
		// 设置用户隶属的 业务系统目录ID
		UmsProtectedobject upo = rs.loadResourceById(systemid);
		user.setBussid(upo.getActionurl());
		return user;
	}

	public boolean permission(String customer, String resourceNaturalname)
			throws Exception {
		CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
		return cupm.checkUserPermission(DEMAIN_CODE, customer,
				resourceNaturalname, PERMISSION_ALLOW);
	}

	private String changeOrganization(UmsProtectedobject upoParent,
			UmsProtectedobject upoOri) throws Exception {
		String orgNaturalname = upoOri.getNaturalname();

		if (upoParent.getNaturalname().matches(orgNaturalname + ".%")) {
			return OPE_TIP_ERROR + "不允许将目录迁移到该目录的子目录中";
		}

		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 定位到目标目录的id、 naturalname和ou
		String newParentId = upoParent.getId();
		String newParentNaturalname = upoParent.getNaturalname();
		String newParentOu = upoParent.getOu();

		// 定位到 被迁移目录根目录的 父Naturalname和父Ou目录
		String oriParentNatrualname = StringUtils.substringBeforeLast(upoOri
				.getNaturalname(), ".");
		String oriParentOu = StringUtils.substringBeforeLast(upoOri.getOu(),
				".");

		// 修改被迁移目录的根节点的 父ID 为新的父目录
		upoOri.setParentdir(newParentId);
		rmi.updateResource(upoOri);

		// 批量更新所有被迁移目录的所有natrualname前缀和 ou 前缀
		UmsProtectedobject upoQ = new UmsProtectedobject();
		upoQ.setNaturalname(orgNaturalname + "%");
		Map queryMode = new HashMap();
		queryMode.put("naturalname", "like");
		List listOriDir = rmi.queryObjectProtectedObj(upoQ, queryMode, 0, 1000,
				"");

		for (Iterator iterator = listOriDir.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			// 修改naturalanme 适应新的目录
			String natrualname = object.getNaturalname();
			String naturalnameNew = StringUtils.replaceOnce(natrualname,
					oriParentNatrualname, newParentNaturalname);
			object.setNaturalname(naturalnameNew);
			// 修改Ou 适应新的目录
			String ou = object.getOu();
			String ouNew = StringUtils
					.replaceOnce(ou, oriParentOu, newParentOu);
			object.setOu(ouNew);
			rmi.updateResource(object);
		}
		return OPE_TIP_SUCCESS + listOriDir.size();
	}

	public String editAccount(String bussDirId, String clientId,
			String displayname, Map extAttribute) throws Exception {
		UmsProtectedobject upo = this.getUmsProtectedobjectByBussid(bussDirId);

		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rmi.loadClerk(DEMAIN_CODE, clientId);
		if (clerk == null) {
			return this.OPE_TIP_ERROR + "丢失帐户" + clientId;
		}
		clerk.setName(displayname);

		// 更新用户隶属部门
		clerk.setDeptment(upo.getId());
		rmi.updateClerk(DEMAIN_CODE, clerk);
		return this.OPE_TIP_SUCCESS;
	}

	public String editOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upoParent = this
				.getUmsProtectedobjectByBussid(parentBussDirId);
		UmsProtectedobject upo = this.getUmsProtectedobjectByBussid(bussDirId);

		upo.setName(displayName);
		rmi.updateResource(upo);
		if (!upo.getParentdir().equals(upoParent.getId())) {
			changeOrganization(upoParent, upo);
			return this.OPE_TIP_SUCCESS + "目录变更";
		}
		return this.OPE_TIP_SUCCESS;

	}

	public String fobidAccount(String clientId) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rmi.loadClerk(DEMAIN_CODE, clientId);
		if (clerk == null) {
			return this.OPE_TIP_ERROR + "丢失帐户" + clientId;
		}
		clerk.setPassword(ACCOUNT_FORBIT_MARK);
		rmi.updateClerk(DEMAIN_CODE, clerk);

		return this.OPE_TIP_SUCCESS;
	}

	public String newAccount(String bussDirId, String clientId,
			String displayname, Map extAttribute) throws Exception {

		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = getUmsProtectedobjectByBussid(bussDirId);
		String orgNaturalname = upo.getNaturalname();
		if (upo == null) {
			return this.OPE_TIP_ERROR + "丢失目录" + orgNaturalname;
		}
		Clerk clerkFind = null;
		try {
			clerkFind = rmi.loadClerk(DEMAIN_CODE, clientId);

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (clerkFind != null) {
			return this.OPE_TIP_ERROR + "用户" + clientId + "已创建";
		}

		Clerk clerk = new Clerk();
		clerk.setName(displayname);
		clerk.setNaturalname(clientId);
		clerk.setDeptment(upo.getId());
		clerk.setDescription(clientId);
		boolean rs = rmi.addClerk(DEMAIN_CODE, clerk);
		if (rs) {
			return this.OPE_TIP_SUCCESS;
		} else {
			return this.OPE_TIP_ERROR;
		}
	}

	public String newOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception {

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setName(displayName);
		upo.setActionurl(bussDirId);

		upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
		upo.setCreated(new Date(System.currentTimeMillis()));
		upo.setActive(ProtectedObjectReference._OBJ_ACTIVE_YES);
		// 设置目录对应的系统信息
		ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
		UmsApplication app = new UmsApplication();
		app.setNaturalname("DEPT");
		List list = rmi.queryObjects(app, null);
		if (list.size() != 1) {
			return this.OPE_TIP_ERROR + "应用定义异常";
		}
		Long appid = ((UmsApplication) list.get(0)).getId();
		upo.setAppid(appid);
		// 设置目录对应的父节点ID
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject parentUpo = this
				.getUmsProtectedobjectByBussid(parentBussDirId);
		if (parentUpo == null) {
			return this.OPE_TIP_ERROR + "业务属性名为" + parentBussDirId + "的目录丢失";
		}
		String parentId = parentUpo.getId();
		upo.setParentdir(parentId);
		upo.setNaturalname(bussDirId);
		rs.addResource(upo, parentUpo.getNaturalname());
		return this.OPE_TIP_SUCCESS;
	}

	private UmsProtectedobject getUmsProtectedobjectByBussid(String bussDirId)
			throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setActionurl(bussDirId);
		List list = rmi.queryObjectProtectedObj(upo, null, 0, 10, "");
		if (list == null || list.size() == 0) {
			throw new Exception(this.OPE_TIP_ERROR + "丢失目录,要使用的目录属性为"
					+ bussDirId);
		}
		if (list.size() != 1) {
			throw new Exception(this.OPE_TIP_ERROR + "目录定义异常,找到多个可使用的目录，个数为:"
					+ list.size());
		}

		return (UmsProtectedobject) list.get(0);
	}

	public String deleteAccount(String clientId) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		boolean rs = rmi.dropClerk(this.DEMAIN_CODE, clientId);
		if (rs)
			return this.OPE_TIP_SUCCESS;
		return this.OPE_TIP_ERROR;
	}

	public String deleteOrganization(String bussDirId) throws Exception {
		UmsProtectedobject upo = getUmsProtectedobjectByBussid(bussDirId);
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		List list = rmi.subResource(upo.getId());
		if (list != null && list.size() > 0) {
			return this.OPE_TIP_ERROR + "该节点存在子目录,无法删除";
		}
		Clerk clerk = new Clerk();
		clerk.setDeptment(upo.getId());
		List list2 = rmi.queryObjectsClerk("0000", clerk, null, 0, 10);

		if (list2 != null && list2.size() > 0) {
			return this.OPE_TIP_ERROR + "该节点存在人员,无法删除";
		}
		boolean rs = rmi.dropResource(upo.getId());
		if (rs)
			return this.OPE_TIP_SUCCESS;
		return this.OPE_TIP_ERROR;
	}

	public String getSecurityEnv(String key) {
		CupmRmi cupm;
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			return cupm.fetchConfig(key);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


}
