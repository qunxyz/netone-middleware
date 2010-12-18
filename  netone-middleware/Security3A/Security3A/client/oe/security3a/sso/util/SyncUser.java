package oe.security3a.sso.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

/**
 * SyncUser ����SyncUser����������ʵ���û����Զ�ͬ����ͬ����jndi�����õ�ϵͳ��
 * 
 * @author wsz
 * 
 */
public class SyncUser {

	public String invokeUrl(String url) {
		URL rul = null;
		InputStream input = null;
		try {
			rul = new URL(url);
			URLConnection urlc = rul.openConnection();
			input = urlc.getInputStream();
			byte[] bytex = new byte[input.available()];
			int num = input.read(bytex);
			return new String(bytex, 0, num);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��jndi�л�õ�ǰ��Ҫͬ����ÿ��ϵͳ��URL����String���鷵��
	 * 
	 * @param code
	 *            �û�������
	 * @return
	 * @throws Exception
	 */
	public List<String> getSystemName(String code) throws Exception {
		List<String> list = new ArrayList<String>();
		CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
		String str = "_SYNC_USER_CODE_" + code;
		String all = cupm.fetchConfig(str);
		if (null == all || "".equals(all.trim())) {
			return null;
		} else {
			String[] allx = all.split(",");
			if (allx.length > 0) {
				for (int i = 0; i < allx.length; i++) {
					String ai = allx[i];
					String jndix = str + "_" + ai;
					String url = cupm.fetchConfig(jndix);
					list.add(url);
				}
			}
			return list;
		}
	}

	// http://192.168.2.100:8080/pms/TaskSynSvl?OPE=ADD&CODE=0001&USERCODE=adminx
	// ����URL
	public List<String> initUrls(String ope, String code, String usercode) {
		List<String> urls = new ArrayList<String>();

		List<String> list = new ArrayList<String>();
		String url = "";
		try {
			list = this.getSystemName(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			return null;
		} else {
			if (!list.isEmpty()) {
				for (String sysname : list) {
					url = sysname + "?";

					String param1 = "";
					String param2 = "";
					String param3 = "";

					param1 = SyncUserUtil._PARAM_OPE + "=";
					if (SyncUserUtil._PARAM_OPE_ADD.endsWith(ope)) {
						param1 = param1 + SyncUserUtil._PARAM_OPE_ADD;
					} else if (SyncUserUtil._PARAM_OPE_MOD.endsWith(ope)) {
						param1 = param1 + SyncUserUtil._PARAM_OPE_MOD;
					} else if (SyncUserUtil._PARAM_OPE_DEL.endsWith(ope)) {
						param1 = param1 + SyncUserUtil._PARAM_OPE_DEL;
					} else if (SyncUserUtil._PARAM_OPE_ROLE.endsWith(ope)) {
						param1 = param1 + SyncUserUtil._PARAM_OPE_ROLE;
					} else {
						return null;
					}

					param2 = SyncUserUtil._PAEAM_CODE + "=" + code;
					param3 = SyncUserUtil._PARAM_USERCODE + "=" + usercode;

					url = url + param1 + "&" + param2 + "&" + param3;

					urls.add(url);

				}
			}
		}

		return urls;
	}

	/**
	 * �ж�����û����������Ƿ���Ҫͬ��
	 * 
	 * @param code
	 *            �û�������
	 * @return�Ƿ���Ҫͬ�� true��Ҫ
	 */
	public boolean needSync(String code) {
		CupmRmi cupm;
		boolean result = false;
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			String str = "_SYNC_USER_CODES";
			String all = cupm.fetchConfig(str);
			if (null == all || "".equals(all.trim())) {
				result = false;
				return false;
			} else {
				String[] allx = all.split(",");
				if (allx.length > 0) {
					for (int i = 0; i < allx.length; i++) {
						if (code.equals(allx[i].trim())) {
							result = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * �ⲿ����������������ʵ���û����Զ�ͬ��
	 * @param ope	ͬ���������� ADD,MOD,DEL,ROLE
	 * @param code
	 * @param usercode
	 * @return
	 */
	public String[] SyncUser(String ope, String code, String usercode) {

		if (this.needSync(code)) {
			List<String> urls = new ArrayList<String>();
			List<String> lists = new ArrayList<String>();
			urls = initUrls(ope, code, usercode);
			if (urls == null || urls.isEmpty()) {
				return null;
			} else {
				for (String url : urls) {
					String result = this.invokeUrl(url);
					lists.add(result);
				}
			}
			String[] abc = (String[]) lists.toArray(new String[0]);
			return abc;
		} else {
			return null;
		}

	}

	public static void main(String[] args) {
		SyncUser s = new SyncUser();
		String[] list = s.SyncUser("ADD", "0001", "adminx");
		for (String ss : list) {
		}
	}

}
