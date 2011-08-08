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
 * DRP��ȫʵ������������NETONE�м����
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
			throw new Exception(this.OPE_TIP_ERROR + "��ȫ�޷���ȡ�����û���Ϣ");
		}
		return loadUser(oluser.getLoginname());
		
	}
	
	public Client3A loadUser(String clientid) throws Exception  {
		Client3A user = new Client3A();

		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rs.loadClerk(DEMAIN_CODE, clientid);
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
		rmi.updateResource(upo);
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
			return this.OPE_TIP_ERROR + "�ýڵ������Ŀ¼,�޷�ɾ��";
		}
		Clerk clerk = new Clerk();
		clerk.setDeptment(upo.getId());
		List list2 = rmi.queryObjectsClerk("0000", clerk, null, 0, 10);

		if (list2 != null && list2.size() > 0) {
			return this.OPE_TIP_ERROR + "�ýڵ������Ա,�޷�ɾ��";
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
