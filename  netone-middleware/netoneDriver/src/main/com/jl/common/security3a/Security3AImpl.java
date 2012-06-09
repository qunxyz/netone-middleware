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
 * DRP��ȫʵ������������NETONE�м����
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
			throw new Exception(this.OPE_TIP_ERROR + "��ȫ�޷���ȡ�����û���Ϣ");
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
			throw new RuntimeException("�û�����:" + clientid + "�����ڣ�");
		}
		// �����û���¼��
		user.setClientId(clientid);
		// �����û���ʾ��
		user.setName(clerk.getName());
		// �����û������� �м��Ŀ¼ID
		String systemid = clerk.getDeptment();
		user.setBelongto(systemid);
		// �����û������� ҵ��ϵͳĿ¼ID
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
			return OPE_TIP_ERROR + "������Ŀ¼Ǩ�Ƶ���Ŀ¼����Ŀ¼��";
		}

		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// ��λ��Ŀ��Ŀ¼��id�� naturalname��ou
		String newParentId = upoParent.getId();
		String newParentNaturalname = upoParent.getNaturalname();
		String newParentOu = upoParent.getOu();

		// ��λ�� ��Ǩ��Ŀ¼��Ŀ¼�� ��Naturalname�͸�OuĿ¼
		String oriParentNatrualname = StringUtils.substringBeforeLast(upoOri
				.getNaturalname(), ".");
		String oriParentOu = StringUtils.substringBeforeLast(upoOri.getOu(),
				".");

		// �޸ı�Ǩ��Ŀ¼�ĸ��ڵ�� ��ID Ϊ�µĸ�Ŀ¼
		upoOri.setParentdir(newParentId);
		rmi.updateResource(upoOri);

		// �����������б�Ǩ��Ŀ¼������natrualnameǰ׺�� ou ǰ׺
		UmsProtectedobject upoQ = new UmsProtectedobject();
		upoQ.setNaturalname(orgNaturalname + "%");
		Map queryMode = new HashMap();
		queryMode.put("naturalname", "like");
		List listOriDir = rmi.queryObjectProtectedObj(upoQ, queryMode, 0, 1000,
				"");

		for (Iterator iterator = listOriDir.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			// �޸�naturalanme ��Ӧ�µ�Ŀ¼
			String natrualname = object.getNaturalname();
			String naturalnameNew = StringUtils.replaceOnce(natrualname,
					oriParentNatrualname, newParentNaturalname);
			object.setNaturalname(naturalnameNew);
			// �޸�Ou ��Ӧ�µ�Ŀ¼
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
			return this.OPE_TIP_ERROR + "��ʧ�ʻ�" + clientId;
		}
		clerk.setName(displayname);
		if (extAttribute != null && extAttribute.containsKey("orders")) {
			clerk.setRemark(((Integer) extAttribute.get("orders")).toString());
		}
		// �����û���������
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
			return this.OPE_TIP_SUCCESS + "Ŀ¼���";
		}
		return this.OPE_TIP_SUCCESS;

	}

	public String fobidAccount(String clientId) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rmi.loadClerk(DEMAIN_CODE, clientId);
		if (clerk == null) {
			return this.OPE_TIP_ERROR + "��ʧ�ʻ�" + clientId;
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
			return this.OPE_TIP_ERROR + "��ʧĿ¼" + orgNaturalname;
		}
		Clerk clerkFind = null;
		try {
			clerkFind = rmi.loadClerk(DEMAIN_CODE, clientId);

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (clerkFind != null) {
			return this.OPE_TIP_ERROR + "�û�" + clientId + "�Ѵ���";
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
		// ����Ŀ¼��Ӧ��ϵͳ��Ϣ
		ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
		UmsApplication app = new UmsApplication();
		app.setNaturalname("DEPT");
		List list = rmi.queryObjects(app, null);
		if (list.size() != 1) {
			return this.OPE_TIP_ERROR + "Ӧ�ö����쳣";
		}
		Long appid = ((UmsApplication) list.get(0)).getId();
		upo.setAppid(appid);
		// ����Ŀ¼��Ӧ�ĸ��ڵ�ID
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject parentUpo = this
				.getUmsProtectedobjectByBussid(parentBussDirId);
		if (parentUpo == null) {
			return this.OPE_TIP_ERROR + "ҵ��������Ϊ" + parentBussDirId + "��Ŀ¼��ʧ";
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
			throw new Exception(this.OPE_TIP_ERROR + "��ʧĿ¼,Ҫʹ�õ�Ŀ¼����Ϊ"
					+ bussDirId);
		}
		if (list.size() != 1) {
			throw new Exception(this.OPE_TIP_ERROR + "Ŀ¼�����쳣,�ҵ������ʹ�õ�Ŀ¼������Ϊ:"
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
			return this.OPE_TIP_ERROR + "�ýڵ������Ŀ¼,�޷�ɾ��";
		}
		Clerk clerk = new Clerk();
		clerk.setDeptment(upo.getId());
		List list2 = rmi.queryObjectsClerk("0000", clerk, null, 0, 10);

		if (list2 != null && list2.size() > 0) {
			return this.OPE_TIP_ERROR + "�ýڵ������Ա,�޷�ɾ��";
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

	// ���������Ķ�����д�������У�������ҳ����ȷ���Ƿ�Ϊ�����ʻ�
	// �����ʻ�������Ϊ 9$9$
	public boolean checkForbid(String loginname) {
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");

			Clerk clerk = resourceRmi.loadClerk("0000", loginname);
			String pass = clerk.getPassword();
			String key = cupmRmi.fetchConfig("EncrypKey");
			String password = Encryption.encry(pass, key, false);// ��ԭ����
			if ("9$9$".equals(password)) {// ���˻��ѱ���ֹ��¼
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
			return this.OPE_TIP_ERROR + "��ʧ�ʻ�" + clientId;
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
	 * �÷��������и����ã�������û�Ŀ¼ʱ����Ҫ��ʱ������Ա����֯naturaname�Ļ���
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
		// ��鱾����
		String partCoreCode = StringUtils.substringBetween(roleid, "[", "]");
		List<Clerk> list = rs.fetchUser(DEMAIN_CODE, partCoreCode);
		Client3A curUser =null;
		if (StringUtils.isNotEmpty(commiter)) {
			curUser = this.loadUser(commiter);
			String deptid = curUser.getBelongto();
			if (isflowrole) {
				// ���̽�ɫ
				String deptname = rs.loadResourceById(deptid).getNaturalname();
				addUserWhenIsSameDept(but, list, deptid, deptname);
			} else {
				// ��̬��ɫ
				addUser(but, list);
			}
		} else {
			// ���û���ṩ��ǰ��¼�ߣ�ϵͳĬ�ϰ��վ�̬��ɫ������
			addUser(but, list);
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {// ���̽�ɫ�ڵ�ǰ����û���ҵ���Ա����ô��ʼ��������,��֤ÿ����ɫ��������һ��
			
			String deptid = curUser.getBelongto();
			nextMen(but, list, deptid, rs);
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {// ���̽�ɫ�ڵ�ǰ����û���ҵ���Ա����ô��ʼ��������,��֤ÿ����ɫ��������һ�Σ���������һ��������������
			
			String deptid = curUser.getBelongto();
			for (int j = 0; j < 2; j++) {// ��֤ÿ����ɫ��������һ�Σ���������һ��������������
				// ��д�����ظ����������������Ҫ�Ľ�
				// �M��v�����
				deptid = nextMen(but, list, deptid, rs);
				if (StringUtils.isEmpty(deptid)) {
					break;
				}
			}
		}

		if (curUser!=null&&isflowrole && but.length() == 0) {
			String deptid = curUser.getBelongto();
			for (int j = 0; j < 3; j++) {
				// �M��v�����
				deptid = nextMen(but, list, deptid, rs);
				if (StringUtils.isEmpty(deptid)) {
					break;
				}
			}
		}

		// ����Ѱ��
		if (curUser!=null&&isflowrole && but.length() == 0) {// ���̽�ɫ�ڵ�ǰ����û���ҵ���Ա����ô��ʼ��������,��֤ÿ����ɫ��������һ��
			String deptid = curUser.getBelongto();
			nextDownMen(but, list, deptid, rs);
		}
		return but.toString();
	}

	private String nextMen(StringBuffer but, List user, String dept,
			ResourceRmi rs) {
		// �����ǰ����û����Ա���ǙM����貿�T
		UmsProtectedobject upopar = null;
		try {
			upopar = rs.loadResourceById(dept);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (dept != null
				&& StringUtils.split(upopar.getNaturalname(), '.').length == 4) {
			// �����������ˣ�ֻ�ܼ����ýڵ�����ݣ����ܺ�����
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

		if (parentName != null && StringUtils.split(parentName, '.').length > 3) {// ���ܳ�����������
			// dept.dept.�����ƶ�.��������
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

	// ���±���
	private void nextDownMen(StringBuffer but, List user, String dept,
			ResourceRmi rs) {
		// �����ǰ����û����Ա���ǙM����貿�T
		UmsProtectedobject upopar = null;
		try {
			upopar = rs.loadResourceById(dept);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List depts = null;
		try {
			// ������е��ӽڵ�
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
		// �����û���¼��
		userx.setClientId(clerk.getDescription());
		// �����û���ʾ��
		userx.setName(clerk.getName());
		// �����û������� �м��Ŀ¼ID
		String systemid = clerk.getDeptment();
		userx.setBelongto(systemid);
		// �����û������� ҵ��ϵͳĿ¼ID
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
