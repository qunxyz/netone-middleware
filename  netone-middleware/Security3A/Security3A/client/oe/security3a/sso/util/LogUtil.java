package oe.security3a.sso.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

/**
 * ������־�Ǽǵ�����ֵ���<br>
 * 
 * Mar 9, 2009 11:13:05 AM<br>
 * 
 * @author wu.shang.zhan<br>
 */
public class LogUtil {

	// �������� �鿴 ���� �޸� ɾ��
	//��������ʧ�ܺͳɹ����֣�����ȷ����Դ�����ߵĲ�ѯ
	public static final String _READ = "read";
	
	public static final String _ADD = "add";

	public static final String _EDIT = "edit";

	public static final String _DEL = "del";

	// �������
	public static final String _ADD_SUCCESS = "�����ɹ�";
	public static final String _ADD_FAIL = "����ʧ��";

	public static final String _EDIT_SUCCESS = "�༭�ɹ�";
	public static final String _EDIT_FAIL = "�༭ʧ��";

	public static final String _DEL_SUCCESS = "ɾ���ɹ�";
	public static final String _DEL_FAIL = "ɾ��ʧ��";

}
