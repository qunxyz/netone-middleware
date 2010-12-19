package oe.cms.dao.blog;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import oe.cms.dao.blog.util.DataTransfer;
import oe.cms.dao.blog.util.ZipUtil;
import oe.frame.web.WebHandler;

import org.apache.commons.lang.StringUtils;

public class ExportImpl implements Export {

	public void export(String particiapnt, HttpServletResponse response) {
//		String cpath = EnvEntry.getEnvInfo("cpath");
//		cpath = StringUtils.replace(cpath, "%20", " ");
//		String bkpath = cpath + "/bin/" + EnvEntry.getEnvInfo("backupRoot")
//				+ particiapnt;
//
//		// 导出 SQL
//		String tablename = EnvEntry.getEnvInfo("tablename");
//		String dbscriptSplitLine = EnvEntry.getEnvInfo("dbscriptSplitLine");
//		String condition = " participant='" + particiapnt + "'";
//
//		if (tablename != null) {
//			String[] dbList = tablename.split(",");
//			StringBuffer allScript = new StringBuffer();
//			for (int i = 0; i < dbList.length; i++) {
//				String info = DataTransfer.parserTableRecordToSqlinfo(
//						dbList[i], condition);
//				allScript.append(info + dbscriptSplitLine);
//
//			}
//			// 创建SQL文件
//			InputStream input = new ByteArrayInputStream(allScript.toString()
//					.getBytes());
//			String fileName = EnvEntry.getEnvInfo("dbscriptfile");
//			DataTransfer.sqlinfoSerial(input, bkpath + "/", fileName);
//
//			// 临时-处理TCmsInfocell中的body
//		}
//		// 拷贝相关的文件资源
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
//		// 下载
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
