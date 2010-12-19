package oe.cms.dao.blog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.blog.util.DataContainer;
import oe.cms.dao.blog.util.ZipUtil;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebHandler;

import org.apache.commons.lang.StringUtils;

public class ExportImpl2 implements Export {

	public void export(String particiapnt, HttpServletResponse response) {
//		String cpath = EnvEntry.getEnvInfo("cpath");
//		cpath = StringUtils.replace(cpath, "%20", " ");
//		String bkpath = cpath + "/bin/" + EnvEntry.getEnvInfo("backupRoot")
//				+ particiapnt;
//
//		// 导出 SQL 持久化对象
//		String tablename = EnvEntry.getEnvInfo("tablename");
//		String[] tablelist = tablename.split(",");
//		List listAll = new ArrayList();
//		for (int i = 0; i < tablelist.length; i++) {
//			Object obj = null;
//			if (tablelist[i].equalsIgnoreCase("IIS_FILEUPLOAD_FILES")) {
//				obj = new IisFileuploadFiles();
//
//			} else if (tablelist[i].equalsIgnoreCase("t_cms_infocell")) {
//				obj = new TCmsInfocell();
//
//			} else if (tablelist[i].equalsIgnoreCase("t_cms_jppmidware")) {
//				obj = new TCmsJppmidware();
//
//			} else if (tablelist[i].equalsIgnoreCase("t_cms_infomodel")) {
//				obj = new TCmsInfomodel();
//			}
//			List listx = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
//					obj,null, " and participant='" + particiapnt + "'");
//			listAll.addAll(listx);
//		}
//		String fileName = EnvEntry.getEnvInfo("dbscriptfile");
//		File moduleDir = new File(bkpath + "/");
//		if (moduleDir.exists() == false) {
//			moduleDir.mkdirs();
//		}
//		DataContainer.serial(listAll, bkpath + "/" + fileName);
//
//		FileDao fileDao = (FileDao) EnvEntry.fetchBean("fileDao");
//		List list = fileDao.listFileByParticipant(particiapnt);
//		WebHandler webHandler = (WebHandler) EnvEntry.fetchBean("webHandler");
//		for (Iterator itr = list.iterator(); itr.hasNext();) {
//			IisFileuploadFiles file = (IisFileuploadFiles) itr.next();
//			String path = cpath + "/bin/" + fileDao.fileUrl(file.getFileid());
//			File fileAim = new File(bkpath + "/" + file.getFileid() + ".file");
//			try {
//				OutputStream out = new FileOutputStream(fileAim);
//				try {
//					webHandler.fetchFileTransfers().downloadFile(out, path);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		// 打包
//		File zipPath = new File(bkpath + "/");
//		String zipfile = bkpath + ".zip";
//		try {
//			ZipUtil.zip(zipfile, zipPath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// 下载
//
//		response.setContentType("application/x-msdownload");
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ particiapnt + ".zip");
//		try {
//			webHandler.fetchFileTransfers().downloadFile(
//					response.getOutputStream(), zipfile);
//		} catch (Exception e) {
//			response.setContentType("text/html; charset=GBK");
//			PrintWriter out = null;
//			try {
//				out = response.getWriter();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//			out.println("<html>");
//			out.println("<body>");
//			out.println("<script type='text/javascript'>");
//			out.println("alert('该文件不存在！');");
//			out.println("history.back();");
//			out.println("</script>");
//			out.println("</body>");
//			out.println("</html>");
//		}
	}
}
