package oe.cms.dao.blog;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.util.DataContainer;
import oe.cms.dao.blog.util.ZipUtil;
import oe.cms.dao.infomodel.ModelDao;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebHandler;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class InportImpl2 implements Inport {

	public void inport(InputStream input, String participant) {
//		// 解开压缩包
//		WebHandler webHandler = (WebHandler) EnvEntry.fetchBean("webHandler");
//		String cpath = EnvEntry.getEnvInfo("cpath");
//		cpath = StringUtils.replace(cpath, "%20", " ");
//		String bkpath = cpath + "/bin/" + EnvEntry.getEnvInfo("backupRoot")
//				+ "/temp/";
//		try {
//			webHandler.fetchFileTransfers().uploadFile(input, bkpath,
//					participant + ".zip");
//			ZipUtil.unzip(bkpath + participant + ".zip", bkpath + participant
//					+ "/");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 继续倒入脚本
//		String fileName = EnvEntry.getEnvInfo("dbscriptfile");
//		String fileInfo = bkpath + participant + "/" + fileName;
//		List list = (List) DataContainer.fetchData(fileInfo);
//
//		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
//		modelDao.delete(participant);
//
//		for (Iterator itr = list.iterator(); itr.hasNext();) {
//			Object objCur = itr.next();
//			try {
//				BeanUtils.setProperty(objCur, "participant", participant);
//				if (TCmsInfomodel.class.isInstance(objCur)) {
//					BeanUtils.setProperty(objCur, "modelid", participant);
//				}
//			} catch (IllegalAccessException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (InvocationTargetException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				OrmerEntry.fetchOrmer().fetchSerializer().create(objCur);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		String x = OrmerEntry.fetchOrmer().fetchSqlBean().executesql(
//				"select cellid,cellname from t_cms_infocell");
//		System.out.println(x);
//		// 拷贝文件
//		String filePathx = bkpath + participant + "/";
//		File fileAll = new File(filePathx);
//		File[] listx = fileAll.listFiles();
//		String fileinfo = cpath + "/bin/" + EnvEntry.getEnvInfo("cmsFileRoot");
//		for (int i = 0; i < listx.length; i++) {
//			String name = listx[i].getName();
//			String filetype = StringUtils.substringAfter(name, ".");
//			if ("file".equals(filetype)) {
//				File newFile = new File(fileinfo + name);
//				listx[i].renameTo(newFile);
//			}
//		}

	}
}
