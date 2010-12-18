package oe.security3a.sso;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsOperationLog;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * ��ȫ����<br>
 * ҵ������:���û��ɹ���½��,ϵͳ�����Զ��������û��İ�ȫ����, �ð�ȫ�����б������û�����ص�Ȩ����Ϣ,�����û���֤�û�Ȩ��ʹ�� <br>
 * ʹ������: ������ȫ����Security ser=new Security(request); ֱ��ʹ�����еĳ�Ա������ɰ�ȫ��Ӧ��
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class Security {

	private OnlineUser oluser;

	private CupmRmi cupm;

	private ResourceRmi rsrmi;

	Log log = LogFactory.getLog(Security.class);

	private void fetchCupm() {
		if (cupm == null) {
			try {
				cupm = (CupmRmi) RmiEntry.iv("cupm");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void fetchRs() {
		if (rsrmi == null) {
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public Security(HttpServletRequest req) {

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		oluser = olmgr.getOnlineUser(req);

	}

	/**
	 * ����û��Ƿ�����
	 * 
	 * @return
	 */
	public boolean checkOnLine() {

		if (oluser == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ����û���½����
	 * 
	 * @return
	 */
	public String getUserLoginName() {
		return oluser.getLoginname();
	}

	public Clerk getUser() {

		try {
			fetchRs();
			return rsrmi.loadClerk(oluser.getBelongto(), oluser.getLoginname());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// public Clerk getUserCommon() {
	// Clerk clerk = null;
	// try {
	// try {
	// cupm = (CupmRmi) RmiEntry.iv("cupm");
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// RequestCtx request = new RequestCtx();
	// SubjectCtx subject = request.newSubject();
	// subject.newAttribute(CupmRmi._TODO_SUBJECT_ATTRIBUTE_OPE,
	// CupmRmi._OPE_GET_CLERK);
	// subject.newAttribute(CupmRmi._TODO_SUBJECT_ATTRIBUTE_CONDITION,
	// oluser.getLoginname());
	// String response = cupm.todo(request.toString());
	// clerk = (Clerk) ClerkResponseToClerk.makeClerk(response);
	//
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// return clerk;
	//
	// }

	public String getUserName() {
		return getUser().getName();
	}

	public String getSystemId() {
		return getUser().getCompany();
	}

	public boolean checkUserPermission(String dnname, String action) {
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			String code = oluser.getBelongto();
			return cupm.checkUserPermission(code, oluser.getLoginname(),
					dnname, action);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getUserAction(String dnname) {
		if (checkUserPermission(dnname, "15")) {
			return 15;
		} else if (checkUserPermission(dnname, "7")) {
			return 7;
		} else if (checkUserPermission(dnname, "3")) {
			return 3;
		} else if (checkUserPermission(dnname, "1")) {
			return 1;
		} else
			return 0;
	}

	/**
	 * ���
	 * 
	 * @param naturlaname
	 *            ��Դnaturalname
	 * @param rsinfo
	 * @param remark
	 * @return
	 */
	public boolean log(String naturlaname, String rsinfo, String remark) {
		try {
			fetchCupm();

			cupm.log(naturlaname, oluser.getLoginip(), oluser.getBelongto()
					+ "_" + oluser.getLoginname(), rsinfo, remark);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �ж��û��Ƿ���Ȩ�޽��в��������ӣ��޸ģ�ɾ����
	 * 
	 * @param naturalName
	 *            ��Ҫ��������Դ��naturalname
	 * @param opeType
	 *            �������ͣ������� read add edit del
	 * @return boolean
	 */
	public boolean check(String naturalName, String opeType) {
		boolean b = false;
		fetchCupm();
		int userAction = getUserAction(naturalName);
		if ("read".equals(opeType.trim()) && userAction > 1) {
			b = true;
		} else if ("add".equals(opeType.trim()) && userAction > 3) {
			b = true;
		} else if ("edit".equals(opeType.trim())
				|| "del".equals(opeType.trim())) {
			if (userAction > 7) {
				// ���ǹ���ԱȨ��
				b = true;
			} else {
				if (userAction > 3) {
					UmsOperationLog uolog = new UmsOperationLog();
					Map<String, String> map = new HashMap<String, String>();
					if (StringUtils.isNotEmpty(naturalName)) {
						map.put("operationid", "like");
						uolog.setOperationid(naturalName);
					}
					uolog.setResultinfo("add");
					String condition = " order by operatetime ";
					List objlist = null;
					String wholeName = oluser.getBelongto() + "_"
							+ oluser.getLoginname();
					try {
						objlist = cupm.logList(uolog, map, condition, 0, 1);
						if (null == objlist || objlist.isEmpty()) {
							b = false;
						} else {
							UmsOperationLog upolog = (UmsOperationLog) objlist
									.get(0);
							if (wholeName.trim().equals(
									upolog.getUserid().trim())) {
								b = true;
							} else {
								b = false;
								log.debug("��ǰ���û�" + wholeName);
								log.debug("��Դ������" + upolog.getUserid());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return b;
	}

	public String todo(String code, String request) {
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			return cupm.todo(code, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ResponseCtx todo(String code, RequestCtx request) {
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			String reponse = cupm.todo(code, request.toString());
			return ResponseCtx.getInstance(reponse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String xxx = "cupmsystem.MenuRs.dyform.221dc6c6-5447-11dc-9ac4-7beebbae7269_oec.column.column1";
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			System.out.println(cupm.checkUserPermission("0001", "nlpA", xxx,
					"7"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
