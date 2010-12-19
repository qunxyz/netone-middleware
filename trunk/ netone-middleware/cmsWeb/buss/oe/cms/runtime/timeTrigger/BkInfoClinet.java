package oe.cms.runtime.timeTrigger;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oe.cms.EnvEntryInfo;
import oe.cms.dao.blog.util.ReadNetInfo2;



/**
 * ���ݹ��߰� �ͻ��˳���
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class BkInfoClinet {

	public static void bkClient() throws RemoteException {
		// �ӻ���������,��ñ��ص�ַ, ���ڴ�Ŵӷ����������ص���Դ
		String localuri = EnvEntryInfo.env.fetchEnvValue("localuri");
		// �ӻ���������,��÷������б�����Դ�ĵ�ַ
		String remoteuriTmp = EnvEntryInfo.env.fetchEnvValue("remoteuri");

		if (remoteuriTmp == null || remoteuriTmp.equals("")) {
			return;
		}
		// �������ص������ļ�������
		String[] remoteuri = remoteuriTmp.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String dateinfo = sdf.format(new Date());
		String nameAhead = localuri + dateinfo + "_";

		for (int i = 0; i < remoteuri.length; i++) {
			// ȡ��ZIP�ļ�����
			String fileName = remoteuri[i].substring(remoteuri[i]
					.lastIndexOf("/") + 1);
			// ���ص����ص��ļ�����
			String localuriReal = nameAhead + fileName;
			// �������س���,���������е���Դ���ص�����
			ReadNetInfo2.netParser(remoteuri[i], localuriReal);
		}

	}

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		bkClient();
	}

}
