package oe.cav.web.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.cav.bean.logic.bus.BussDao;
import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;

import oe.env.client.EnvService;
import oe.frame.web.util.BuildStaticWeb;
import oe.rmi.client.RmiEntry;

/**
 * ר�����������̳ģʽ��������̬������д�ľ�̬ҳ�����ɳ���
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class BuildFormStaticPage {

	static EnvService env = null;

	static {
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
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
	}

	public static void buildForumAuto(String contextpath, String flsh,
			String lsh, String formcode, String participant) {
		String curl = "";
		;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			env.fetchEnvValue("curl");
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

		String urlx = "";
		String name = "";
		if ("1".equals(flsh)) {// ����ͷ���ಿ��(��OeseeԤ�ȶ����ķ���,�� Java��ѧ,������..)
			urlx = curl + "/cavserweb/data/showdata/listviews.do?lsh=" + flsh
					+ "&formcode=" + formcode;
			// ��̬ҳ���в���Ҫ����������Ϣ,��Ϊ������ͬһ������oesee�������
			name = formcode + "_" + flsh + ".htm";
			BuildStaticWeb.build(urlx, contextpath + name);

		} else {
			BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
			TCsBus bus = (TCsBus) bussDao.loadObject(formcode, flsh);
			String fnextLsh = bus.getFatherlsh();
			if ("1".equals(fnextLsh)) {// �ڶ�����(Ҳ������̳��ߵ��б�)
				// ��Ҫ����2����̬ҳ��
				// ��̬ҳ��1: ������̳����(�Ҳ��ֵ������б�)
				urlx = curl
						+ "/cavserweb/data/showdata/subListviewslink.do?lsh="
						+ flsh + "&formcode=" + bus.getFormcode();
				name = bus.getParticipant() + "_" + bus.getFormcode() + "_"
						+ flsh + ".htm";
				BuildStaticWeb.build(urlx, contextpath + name);
				// ��̬ҳ��2: ���ɸմ���/�޸�/ɾ�������ӵľ�̬ҳ��
				urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh="
						+ lsh + "&formcode=" + formcode;
				name = participant + "_" + formcode + "_" + lsh + ".htm";
				BuildStaticWeb.build(urlx, contextpath + name);

			} else {
				// ��������(Ҳ�������ӵĻظ�)
				urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh="
						+ flsh + "&formcode=" + bus.getFormcode();
				// ��Ҫע�����,���ӵĻظ������Ǻϲ������ӵ�URLһ����ʾ��,���Իظ�ʱ�����ɵľ�̬ҳ����Ҫ�������ӵ�ҳ��
				name = bus.getParticipant() + "_" + bus.getFormcode() + "_"
						+ flsh + ".htm";
				BuildStaticWeb.build(urlx, contextpath + name);
			}
		}
	}

	public static void buildForumLevel1(String contextpath, String formcode) {

		String curl = "";
		try {
			curl = env.fetchEnvValue("curl");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlx = "";
		String name = "";

		urlx = curl + "/cavserweb/data/showdata/listviews.do?lsh=1"
				+ "&formcode=" + formcode;
		// ��̬ҳ���в���Ҫ����������Ϣ,��Ϊ������ͬһ������oesee�������
		name = formcode + "_1.htm";
		BuildStaticWeb.build(urlx, contextpath + name);

	}

	public static void buildForumLevel2(String contextpath, String lsh,
			String formcode, String participant) {

		String curl = "";
		try {
			curl = env.fetchEnvValue("curl");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlx = "";
		String name = "";

		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		TCsBus bus = (TCsBus) bussDao.loadObject(formcode, lsh);

		// ��Ҫ����2����̬ҳ��
		// ��̬ҳ��1: ������̳����(�Ҳ��ֵ������б�)
		urlx = curl + "/cavserweb/data/showdata/subListviewslink.do?lsh=" + lsh
				+ "&formcode=" + bus.getFormcode();
		name = bus.getParticipant() + "_" + bus.getFormcode() + "_" + lsh
				+ ".htm";
		BuildStaticWeb.build(urlx, contextpath + name);

	}

	public static void buildForumLevel3(String contextpath, String lsh,
			String flsh, String formcode, String participant) {

		String curl = "";
		try {
			curl = env.fetchEnvValue("curl");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlx = "";
		String name = "";

		// ��̬ҳ��3: ���ɸմ���/�޸�/ɾ�������ӵľ�̬ҳ��
		urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh=" + lsh
				+ "&formcode=" + formcode;
		name = participant + "_" + formcode + "_" + lsh + ".htm";
		BuildStaticWeb.build(urlx, contextpath + name);

	}

}
