package com.jl.common.security3a;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.env.client.EnvService;
import oe.frame.web.WebCache;
import oe.midware.workflow.service.WorkflowView;
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
import oe.security3a.sso.util.Encryption;

import org.apache.commons.lang.StringUtils;

import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;

/**
 * DRP安全实现驱动（基于NETONE中间件）
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class Security3AImpl implements Security3AIfc {

	public Client3A onlineUser(HttpServletRequest request) throws Exception {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		if (oluser == null) {
			throw new Exception(this.OPE_TIP_ERROR + "安全无法获取在线用户信息");
		}
		return loadUser(oluser.getLoginname());

	}

	public String onlineUserSessionId(HttpServletRequest request)
			throws Exception {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		if (oluser == null) {
			return null;
		} else {
			return oluser.getLoginname();
		}
	}

	public Client3A loadUser(String clientid) throws Exception {
		Client3A user = new Client3A();

		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rs.loadClerk(DEMAIN_CODE, clientid);

		if (clerk == null) {
			throw new RuntimeException("用户编码:" + clientid + "不存在！");
		}
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

		user.setMobile(clerk.getPhoneNO());
		user.setEmail(clerk.getEmail());
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
		if (extAttribute != null && extAttribute.containsKey("orders")) {
			clerk.setRemark(((Integer) extAttribute.get("orders")).toString());
		}
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
		if (extAttribute != null && extAttribute.containsKey("orders")) {
			upo.setReference(((Integer) extAttribute.get("orders")).toString());
		}

		rmi.updateResource(upo);
		init_deptCache(upo.getNaturalname(), upo.getId());

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
		clerk.setEmail("oesee@139.com");
		clerk.setPhoneNO("15860836998");
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

	private void init_userCache(String usercode) {
		WebCache.removeCache("USER$" + usercode);
	}

	private void init_deptCache(String depcode, String deptid) {
		WebCache.removeCache("NA$_" + depcode);
		WebCache.removeCache("NID$" + deptid);
	}

	public String deleteAccount(String clientId) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		boolean rs = rmi.dropClerk(this.DEMAIN_CODE, clientId);
		init_userCache(clientId);
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
		init_deptCache(upo.getNaturalname(), upo.getId());
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

	public String getTopEnv(String key) {
		EnvService env = null;

		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			return env.fetchEnvValue(key);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 将密码明文读到，写到密码中，用于在页面上确认是否为禁用帐户
	// 禁用帐户的密码为 9$9$
	public boolean checkForbid(String loginname) {
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");

			Clerk clerk = resourceRmi.loadClerk("0000", loginname);
			String pass = clerk.getPassword();
			String key = cupmRmi.fetchConfig("EncrypKey");
			String password = Encryption.encry(pass, key, false);// 还原密码
			if ("9$9$".equals(password)) {// 该账户已被禁止登录
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String recoveryAccount(String clientId) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rmi.loadClerk(DEMAIN_CODE, clientId);
		if (clerk == null) {
			return this.OPE_TIP_ERROR + "丢失帐户" + clientId;
		}

		CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
		String initpassword = cupmRmi.fetchConfig("initpassword");
		String key = cupmRmi.fetchConfig("EncrypKey");
		initpassword = Encryption.encry(initpassword, key, true);
		clerk.setPassword(initpassword);
		rmi.updateClerk(DEMAIN_CODE, clerk);

		return this.OPE_TIP_SUCCESS;
	}

	/**
	 * 该方法可能有副作用，当变更用户目录时，需要及时更新人员和组织naturaname的缓存
	 */
	public String deptNameOfUser(String clientid) throws Exception {

		String key = "USERID_NATURALNAME:" + clientid;
		if (WebCache.containCache(key)) {
			return (String) WebCache.getCache(key);
		}
		System.out
				.println("warning!had not yet created cache for clientid-deptNaturalname:"
						+ key);
		String belongtoid = loadUser(clientid).getBelongto();
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		return rs.loadResourceById(belongtoid).getNaturalname();

	}

	public List<UmsProtectedobject> listOu(String parentid, int from, int to,
			String condition) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setParentdir(parentid);
		return rs.queryObjectProtectedObj(upo, null, from, to, condition);
	}

	public long listOuTotal(String parentid, String condition) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setParentdir(parentid);
		return rs.queryObjectNumberProtectedObj(upo, null, condition);
	}

	public String listUserByRoleId(String roleid[], String commiter,
			boolean isflowrole) throws Exception {
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < roleid.length; i++){
			
			String rs=listUserByRoleIdCore(roleid[i],commiter,isflowrole);
			but.append(rs);
		}
			
		return but.toString();
	}

	private String listUserByRoleIdCore(String roleid, String commiter,
			boolean isflowrole) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		StringBuffer but = new StringBuffer();
		// 检查本部门
		String partCoreCode = StringUtils.substringBetween(roleid, "[", "]");
		List<Clerk> list = rs.fetchUser(DEMAIN_CODE, partCoreCode);
		Client3A curUser =null;
		if (StringUtils.isNotEmpty(commiter)) {
			curUser = this.loadUser(commiter);
			String deptid = curUser.getBelongto();
			if (isflowrole) {
				// 流程角色
				String deptname = rs.loadResourceById(deptid).getNaturalname();
				addUserWhenIsSameDept(but, list, deptid, deptname);
			} else {
				// 静态角色
				addUser(but, list);
			}
		} else {
			// 如果没有提供当前登录者，系统默认按照静态角色来处理
			addUser(but, list);
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {// 流程角色在当前区域没有找到人员，那么开始横向搜索,保证每个角色横向搜索一次
			
			String deptid = curUser.getBelongto();
			nextMen(but, list, deptid, rs);
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {// 流程角色在当前区域没有找到人员，那么开始横向搜索,保证每个角色横向搜索一次，并且往上一级再做横向搜索
			
			String deptid = curUser.getBelongto();
			for (int j = 0; j < 2; j++) {// 保证每个角色横向搜索一次，并且往上一级再做横向搜索
				// 该写法有重复上面的搜索将来需要改进
				// 橫向縱向掃描
				deptid = nextMen(but, list, deptid, rs);
				if (StringUtils.isEmpty(deptid)) {
					break;
				}
			}
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {
			String deptid = curUser.getBelongto();
			for (int j = 0; j < 3; j++) {
				// 橫向縱向掃描
				deptid = nextMen(but, list, deptid, rs);
				if (StringUtils.isEmpty(deptid)) {
					break;
				}
			}
		}

		// 下行寻找
		if (curUser!=null&&isflowrole && but.length() == 0) {// 流程角色在当前区域没有找到人员，那么开始横向搜索,保证每个角色横向搜索一次
			String deptid = curUser.getBelongto();
			nextDownMen(but, list, deptid, rs);
		}
		return but.toString();
	}

	private String nextMen(StringBuffer but, List user, String dept,
			ResourceRmi rs) {
		// 如果当前部门没有人员，那橫向掃描部門
		UmsProtectedobject upopar = null;
		try {
			upopar = rs.loadResourceById(dept);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (dept != null
				&& StringUtils.split(upopar.getNaturalname(), '.').length == 4) {
			// 本级是区域级了，只能检索该节点的数据，不能横向了
			addUserWhenIsSameDept(but, user, upopar.getId(), upopar
					.getNaturalname());
			return "";
		}

		String parentid = upopar.getParentdir();
		String parentName = null;
		try {
			parentName = rs.loadResourceById(parentid).getNaturalname();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (parentName != null && StringUtils.split(parentName, '.').length > 3) {// 不能超出顶级区域
			// dept.dept.宁德移动.顶级区域
			List depts = new ArrayList();
			try {
				depts = rs.subResource(parentid);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Iterator iterator = depts.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				addUserWhenIsSameDept(but, user, object.getId(), object
						.getNaturalname());
			}
			return parentid;
		} else {
			return "";
		}

	}

	// 向下遍历
	private void nextDownMen(StringBuffer but, List user, String dept,
			ResourceRmi rs) {
		// 如果当前部门没有人员，那橫向掃描部門
		UmsProtectedobject upopar = null;
		try {
			upopar = rs.loadResourceById(dept);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List depts = null;
		try {
			// 获得所有的子节点
			depts = rs.subResourceByNaturalname(upopar.getNaturalname());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = depts.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			addUserWhenIsSameDept(but, user, object.getId(), object
					.getNaturalname());
		}
	}

	private void addUser(StringBuffer but, List list) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Clerk clerk = (Clerk) iterator.next();
			but.append(clerk.getName() + "[" + clerk.getDescription() + "],");
		}
	}

	private void addUserWhenIsSameDept(StringBuffer but, List list,
			String deptid, String deptname) {
		if (StringUtils.split(deptname, '.').length < 3) {
			return;
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Clerk clerk = (Clerk) iterator.next();
			if (deptid.equals(clerk.getDeptment())) {
				but.append(clerk.getName() + "[" + clerk.getDescription()
						+ "],");
			}
		}
	}

	private Client3A loadClient3A(Clerk clerk) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Client3A userx = new Client3A();
		// 设置用户登录名
		userx.setClientId(clerk.getDescription());
		// 设置用户显示名
		userx.setName(clerk.getName());
		// 设置用户隶属的 中间件目录ID
		String systemid = clerk.getDeptment();
		userx.setBelongto(systemid);
		// 设置用户隶属的 业务系统目录ID
		UmsProtectedobject upo = rs.loadResourceById(systemid);
		userx.setBussid(upo.getActionurl());

		return userx;
	}

	public String listUserByTeamId(String[] teamid) throws Exception {
		WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < teamid.length; i++) {
			String partCoreCode = StringUtils.substringBetween(teamid[i], "[",
					"]");
			List list = wfview
					.coreSqlview("select usercode usercode,name namex,systemid systemid from t_cs_user where major like '%"
							+ partCoreCode + "@%'");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String usercode = (String) object.get("usercode");
				String namex = (String) object.get("namex");
				String systemid = (String) object.get("systemid");
				but.append(namex + "[" + usercode + "],");
			}
		}
		return but.toString();

	}

	public String listUserByDeptId(String[] deptid) throws Exception {

		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < deptid.length; i++) {
			Clerk clerk = new Clerk();
			String partCoreCode = StringUtils.substringBetween(deptid[i], "[",
					"]");
			String deptide = rs.loadResourceByNatural(partCoreCode).getId();
			clerk.setDeptment(deptide);
			List list = rs.fetchClerk("0000", clerk, null, "");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Clerk object = (Clerk) iterator.next();
				String userstr = object.getName() + "["
						+ object.getDescription() + "],";
				but.append(userstr);
			}
		}
		return but.toString();

	}

	public List<Resource> listDirRs(String parentNaturalname) throws Exception {
		if (!StringUtils.contains(parentNaturalname, ".")) {
			parentNaturalname = parentNaturalname + "." + parentNaturalname;
		}
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setParentdir(rs.loadResourceByNatural(parentNaturalname).getId());

//		List list = rs.queryObjectProtectedObj(upo, null, 0, 1000,
//				" order by CAST(reference AS UNSIGNED) desc");
		List list = rs.queryObjectProtectedObj(upo, null, 0, 1000,
		" order by aggregation");
		List listData = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			if ("1".equals(object.getInclusion())) {
				Resource rsx = new Resource();

				rsx.setResourceid(object.getNaturalname());
				rsx.setResourcecode(object.getNaturalname());
				rsx.setResourcename(object.getName());
				rsx.setParentid(rs.loadResourceById(object.getParentdir())
						.getNaturalname());

				listData.add(rsx);
			}
		}
		return listData;
	}

	public List<Resource> listRsInDir(String parentNaturalname, String rstype,
			int from, int size, String condition) throws Exception {
		List rsinfo = new ArrayList();
		if ("22".equals(rstype) || "23".equals(rstype)) {
			rsinfo = this.listHumanInDir(parentNaturalname, from, size,
					condition);
			return rsinfo;
		}
		List other = listOtherRsInDir(parentNaturalname, from, size, condition);
		rsinfo.addAll(other);
		return rsinfo;

	}

	private List<Resource> listHumanInDir(String parentNaturalname, int from,
			int size, String condition) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = new Clerk();
		clerk.setDeptment(rs.loadResourceByNatural(parentNaturalname).getId());
		List list = rs.queryObjectsClerk("0000", clerk, null, from, size);
		list = order(list);
		List listData = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Clerk object = (Clerk) iterator.next();
			UmsProtectedobject objectx = rs.loadResourceById(object
					.getDeptment());
			Resource rsx = new Resource();
			rsx.setResourceid(object.getDescription());
			rsx.setResourcecode(object.getDescription());
			rsx.setResourcename(parseDnName(objectx) + object.getName());
			listData.add(rsx);
		}
		return listData;
	}

	private List order(List list) {
		int remark[] = new int[list.size()];
		int i = 0;
		Map map = new HashMap();
		int max_x = 100;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Clerk object = (Clerk) iterator.next();
			String remarkStr = object.getRemark();
			if (remarkStr != null && remarkStr.matches("\\d+")) {
				int remarkTmp = Integer.parseInt(remarkStr);
				remark[i] = remarkTmp;
			} else {
				remark[i] = max_x++;
			}
			map.put(remark[i], object);
			i++;
		}
		Arrays.sort(remark);
		List listNew = new ArrayList();
		for (int j = remark.length - 1; j >= 0; j--) {
			int key = remark[j];
			listNew.add(map.get(key));
		}
		return listNew;
	}

	private List<Resource> listOtherRsInDir(String parentNaturalname, int from,
			int size, String condition) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		UmsProtectedobject pupo = rs.loadResourceByNatural(parentNaturalname);
		upo.setParentdir(pupo.getId());
		if (condition != null && !condition.equals("")) {
			condition = StringUtils.replace(condition, "$nodecode$",
					"naturalname");
			condition = StringUtils.replace(condition, "$nodename$", "name");
		}
		List listAll = new ArrayList();
		listAll.add(pupo);
		List list = rs.queryObjectProtectedObj(upo, null, from, size, condition
				+ " order by CAST(reference AS UNSIGNED) desc");
		listAll.addAll(list);

		List listData = new ArrayList();
		for (Iterator iterator = listAll.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			String rsname = object.getName();

			if ("1".equals(object.getInclusion())) {
				rsname = rsname + "/";
			}

			Resource rsx = new Resource();
			rsx.setResourceid(object.getNaturalname());
			rsx.setResourcecode(object.getNaturalname());
			rsx.setResourcename(parseDnName(object) + rsname);
			rsx.setTypes(object.getObjecttype());
						rsx.setId(object.getId());
			rsx.setParentid(object.getParentdir());
			rsx.setText(object.getExtendattribute());
			rsx.setInclusion(object.getInclusion());
			rsx.setActive(object.getActive());
			rsx.setAggregation(object.getAggregation());
			listData.add(rsx);
		}
		return listData;
	}

	private String parseDnName(UmsProtectedobject object) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		String naturalname = object.getNaturalname();
		String naturalnameArr[] = StringUtils.split(naturalname, ".");

		String dirname = "";
		if (naturalnameArr != null) {
			for (int i = 2; i < naturalnameArr.length; i++) {
				naturalname = StringUtils.substringBeforeLast(naturalname, ".");
				String name = rs.loadResourceByNatural(naturalname).getName();
				dirname = name + "/" + dirname;
			}
		}
		return StringUtils.substringAfter(dirname, "/");

	}

	public long countRsInDir(String parentNaturalname, String rstype,
			String condition) throws Exception {

		if ("22".equals(rstype) || "23".equals(rstype)) {
			return this.countlistHumanInDir(parentNaturalname, condition);
		}
		return countlistOtherRsInDir(parentNaturalname, condition);

	}

	private long countlistHumanInDir(String parentNaturalname, String condition)
			throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = new Clerk();
		clerk.setDeptment(rs.loadResourceByNatural(parentNaturalname).getId());
		return rs.queryObjectsNumberClerk("0000", clerk, null);
	}

	private long countlistOtherRsInDir(String parentNaturalname,
			String condition) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setParentdir(rs.loadResourceByNatural(parentNaturalname).getId());
		if (condition != null && !condition.equals("")) {
			condition = StringUtils.replace(condition, "$nodecode$",
					"naturalname");
			condition = StringUtils.replace(condition, "$nodename$", "name");
		}
		return rs.queryObjectNumberProtectedObj(upo, null, condition);
	}

	public boolean permissionById(String clientid, String id) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		String name = rs.loadResourceById(id).getNaturalname();

		return this.permission(clientid, name);
	}

}
