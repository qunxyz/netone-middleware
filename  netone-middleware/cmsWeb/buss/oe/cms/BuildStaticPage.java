package oe.cms;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.BuildStaticWeb;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildStaticPage {
	private static Log _log = LogFactory.getLog(BuildStaticPage.class);

	/**
	 * �㷨˵��: �������е��û�,Ȼ������û���ȥѰ��������Ӧ�Ŀռ�Infomodelid ���û�еĻ�,����һ��Ĭ��ҳ��,�еĻ�����
	 * Infomodelid������ҳ��
	 * 
	 */
	public static void initWeb() {
//		// �������ɾ�̬ҳ��
//		String path = EnvEntryInfo.env.fetchEnvValue("cpath");
//		path = StringUtils.replace(path, "%20", " ");
//		String userHtmlRootPath = path
//				+ EnvEntryInfo.env.fetchEnvValue("userHtmlRootPath");
//		_log.debug("System Path:" + userHtmlRootPath);
//		String url = EnvEntryInfo.env.fetchEnvValue("userSpaceUrl");
//		String curUrl = EnvEntryInfo.env.fetchEnvValue("curl");
//		_log.debug("System Page:" + curUrl);
//		try {
//
//			TCsUser user = new TCsUser();
//			user.setStatusinfo(UserExtendInfo._STATUS_NORMAL);
//			List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
//					user, null);
//			for (Iterator itr = list.iterator(); itr.hasNext();) {
//				TCsUser userPre = (TCsUser) itr.next();
//				String userid = userPre.getUsercode();
//				// �����û�����Infomdoelid
//				ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");
//				TCmsInfomodel modelX = modeldao.viewByUser(userid);
//				String urlThis = null;
//				if (modelX != null) {
//					urlThis = curUrl + url + modelX.getModelid();
//
//				} else {
//					urlThis = curUrl
//							+ EnvEntryInfo.env.fetchEnvValue("nullPage");
//				}
//				_log.debug("User Website:" + userid + ":" + urlThis);
//				try {
//					BuildStaticWeb.build(urlThis, userHtmlRootPath + userid
//							+ "/", "index.htm");
//					// BuildStaticPageCore.buildOther(userHtmlRootPath,
//					// urlThis,
//					// modelX.getParticipant());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// ��������Ҫ��Ҫ��ʼ����ϵͳ��ҳ
//
//		String url1 = null;
//		try {
//			url1 = EnvEntryInfo.env.fetchEnvValue("specialinit");
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String[] eachUrl = url1.split(",");
//		for (int i = 0; i < eachUrl.length; i++) {
//
//			String[] url1Arr = StringUtils.split(eachUrl[i], ";");
//			String pathInfo = path + url1Arr[0];
//			String urlInfo = curUrl + url1Arr[1];
//			_log.debug("System Site:" + pathInfo + " ; " + urlInfo);
//			BuildStaticWeb.build(urlInfo, pathInfo + "index.htm");
//
//		}
	}

}
