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
 * 专门针对类似论坛模式的三级别动态表单所编写的静态页面生成程序
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
		if ("1".equals(flsh)) {// 来自头分类部分(由Oesee预先定死的分类,如 Java教学,星期六..)
			urlx = curl + "/cavserweb/data/showdata/listviews.do?lsh=" + flsh
					+ "&formcode=" + formcode;
			// 静态页面中不需要增加作者信息,因为都属于同一个作者oesee总设计者
			name = formcode + "_" + flsh + ".htm";
			BuildStaticWeb.build(urlx, contextpath + name);

		} else {
			BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
			TCsBus bus = (TCsBus) bussDao.loadObject(formcode, flsh);
			String fnextLsh = bus.getFatherlsh();
			if ("1".equals(fnextLsh)) {// 第二级表单(也就是论坛左边的列表)
				// 需要创建2个静态页面
				// 静态页面1: 生成论坛主题(右部分的帖子列表)
				urlx = curl
						+ "/cavserweb/data/showdata/subListviewslink.do?lsh="
						+ flsh + "&formcode=" + bus.getFormcode();
				name = bus.getParticipant() + "_" + bus.getFormcode() + "_"
						+ flsh + ".htm";
				BuildStaticWeb.build(urlx, contextpath + name);
				// 静态页面2: 生成刚创建/修改/删除的帖子的静态页面
				urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh="
						+ lsh + "&formcode=" + formcode;
				name = participant + "_" + formcode + "_" + lsh + ".htm";
				BuildStaticWeb.build(urlx, contextpath + name);

			} else {
				// 第三级表单(也就是帖子的回复)
				urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh="
						+ flsh + "&formcode=" + bus.getFormcode();
				// 需要注意的是,帖子的回复内容是合并在帖子的URL一起显示的,所以回复时候生成的静态页面需要覆盖帖子的页面
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
		// 静态页面中不需要增加作者信息,因为都属于同一个作者oesee总设计者
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

		// 需要创建2个静态页面
		// 静态页面1: 生成论坛主题(右部分的帖子列表)
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

		// 静态页面3: 生成刚创建/修改/删除的帖子的静态页面
		urlx = curl + "/cavserweb/data/showdata/sublistview.do?lsh=" + lsh
				+ "&formcode=" + formcode;
		name = participant + "_" + formcode + "_" + lsh + ".htm";
		BuildStaticWeb.build(urlx, contextpath + name);

	}

}
