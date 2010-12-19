package oe.cms.dao.blog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import oe.cms.CmsEntry;
import oe.cms.dao.blog.util.ZipUtil;
import oe.cms.dao.infomodel.ModelDao;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebHandler;

import org.apache.commons.lang.StringUtils;

public class InportImpl implements Inport {

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
//		StringBuffer but = new StringBuffer();
//		try {
//			InputStream inputInfo = new FileInputStream(fileInfo);
//			byte[] info = new byte[8912];
//			int read = 0;
//			while ((read = inputInfo.read(info)) > 0) {
//				but.append(new String(info, 0, read));
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String sql = but.toString();
//		sql = StringUtils.replace(sql, "\n\r", "");
//		String dbscriptSplitLine = EnvEntry.getEnvInfo("dbscriptSplitLine");
//		sql = StringUtils.replace(sql, dbscriptSplitLine, "");
//
//		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
//		modelDao.delete(participant);
//
//		OrmerEntry.fetchOrmer().fetchSqlBean().executesql(sql);
//
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
