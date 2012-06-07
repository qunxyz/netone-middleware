package com.jl.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.JSONUtil2;
import com.jl.common.JxlUtilsTemplate;
import com.jl.common.SpringBeanUtil;
import com.jl.common.TimeUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.scheduler.job.ContractPojo;
import com.jl.service.BaseService;
import com.jl.service.FileService;

public class FileServiceImpl extends BaseService implements FileService {
	/** 日志 */
	private final Logger log = Logger.getLogger(FileServiceImpl.class);

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String delete(HttpServletRequest request, String id)
			throws Exception {
		JSONObject json = new JSONObject();
		User user = getOnlineUser(request);
		com.jl.entity.File file = (com.jl.entity.File) commonDAO.findForObject(
				"File.selectFileById", id);
		if (user.getUserCode().equals(file.getU_unid())) {
			commonDAO.delete("File.delete", id);
			DirectoryManager dirmanage = new DirectoryManager();
			boolean judge = dirmanage.deleteFile(file.getAddress());
			json.put("tip", "删除文件成功");
		} else {
			json.put("error", "yes");
			json.put("tip", "删除" + file.getFilename() + "文件失败");
		}
		return json.toString();
	}

	public String save(HttpServletRequest request, String id, String filename,
			String filetype, String path, String usercode, String userName,
			FileItem fileItem) throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		boolean flag = false;
		String error = null;// 插入数据的错误信息
		String fileName = fileItem.getName();

		if ("public".equals(id)) {
			path += "appdata\\files" + "\\" + id;
		} else {
			path += "appdata\\files" + "\\"
					+ TimeUtil.formatDate(new Date(), "yyyyMM");
		}

		String format = StringUtils.substringAfterLast(fileName, ".");// 后辍名
		fileName = StringUtils.substringAfterLast(fileName, "\\");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (StringUtils.isEmpty(format)) {
			path += "\\" + UUID.randomUUID();// 保存的文件绝对路径
		} else {
			path += "\\" + UUID.randomUUID() + "." + format;// 保存的文件绝对路径
		}

		com.jl.entity.File files = saveFile(id, usercode, userName, path, ""
				+ formatKbSize(fileItem.getSize()), filetype, filename);

		json = JSONObject.fromObject(files);

		File _file = null;
		if (fileItem != null) {
			_file = new File(path);
			fileItem.write(_file);
			fileItem.getOutputStream().flush();
			fileItem.getOutputStream().close();
		}

		if ("contract".equals(id)) {
			// todo
			Object[] sheetSet = null;
			try {
				File fil = new File(_file.toString());
				InputStream is = new BufferedInputStream(new FileInputStream(
						fil));
				sheetSet = JxlUtilsTemplate.newInstanct().readAll(is);// 读取文件
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("读取xml文件失败...");
			}
			// 判断xml文档是否有内容
			List list = (List) sheetSet[0];
			if (list.size() < 1) {
				System.out.println("电子表格文件内容为空!");
			}
			ContractPojo cpo = new ContractPojo();
			// 获取信息
			for (int i = 1; i < list.size(); i++) {
				Object[] object = (Object[]) list.get(i);
				cpo.setNexthandler((String) object[0]);
				cpo.setColumn3((String) object[1]);
				cpo.setColumn4((String) object[2]);
				cpo.setColumn5((String) object[3]);
				cpo.setColumn6((String) object[4]);
				cpo.setColumn7((String) object[5]);
				cpo.setColumn8((String) object[6]);
				cpo.setColumn9((String) object[7]);
				cpo.setColumn10((String) object[8]);
				cpo.setColumn11((String) object[9]);
				cpo.setColumn12((String) object[10]);
				cpo.setColumn13((String) object[11]);
				cpo.setColumn17((String) object[12]);
				User user = getOnlineUser(request);
				String allName = user.getUserName() + "[" + user.getUserCode()
						+ "]";
				String lsh = UUID.randomUUID().toString().replaceAll("-", "");
				cpo.setLsh(lsh);
				String formcode = "1d3fad64141011e18330052303c5b600_";
				cpo.setFORMCODE(formcode);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 获取当前时间
				String currentdate = sdf.format(new Date());
				cpo.setTimex(currentdate);
				cpo.setCREATED(currentdate);
				cpo.setFATHERLSH("1");
				cpo.setSTATUSINFO("00");
				cpo.setEXTENDATTRIBUTE(allName);
				cpo.setFlag("0");
				cpo.setPARTICIPANT(user.getUserCode());
				// String insert_sql="insert into dyform.dy_111321859523995
				// values('"+lsh+"','"+formcode+"','"+user.getUserCode()+"','"+currentdate+"','1','00','"+allName+"','0','','"
				// +currentdate+"','"+cpo.getColumn3()+"','"+cpo.getColumn4()+"','"+cpo.getColumn5()+"','"+cpo.getColumn6()+"','"
				// +cpo.getColumn7()+"','"+cpo.getColumn8()+"','"+cpo.getColumn9()+"','"+cpo.getColumn10()+"','"+cpo.getColumn11()+"','"+cpo.getColumn12()+"','"+cpo.getColumn13()+"'"
				// +",'','','','"+cpo.getColumn17()+"','"+cpo.getNexthandler()+"','0','')";
				commonDAO.insert("ContractMgr.insert_data", cpo);
				flag = true;// 提示错误信息
				error = "第" + i + "行数据有误!导入数据失败!";
			}
		}
		if (flag) {
			json.put("tip", error);
		} else {
			json.put("tip", "上传附件成功");
		}
		return json.toString();
	}

	public String saveFrame(HttpServletRequest request, String id,
			String filename, String filetype, String path, String usercode,
			String userName, FileItem fileItem) throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		boolean flag = false;
		String error = null;// 插入数据的错误信息
		String fileName = fileItem.getName();
		path += "frameSCMExt";

		String format = StringUtils.substringAfterLast(fileName, ".");// 后辍名
		fileName = StringUtils.substringAfterLast(fileName, "\\");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (StringUtils.isEmpty(filename)) {
			filename = fileName.trim();
		}
		path += "\\" + filename;// 保存的文件绝对路径

		com.jl.entity.File files = saveFrameFile(id, usercode, userName, path,
				"" + formatKbSize(fileItem.getSize()), filetype, filename);

		json = JSONObject.fromObject(JSONUtil2.fromBean(files,
				"yyyy-MM-dd HH:mm:ss").toString());

		File _file = null;
		if (fileItem != null) {
			_file = new File(path);
			fileItem.write(_file);
			fileItem.getOutputStream().flush();
			fileItem.getOutputStream().close();
		}

		if (flag) {
			json.put("tip", error);
		} else {
			json.put("tip", "上传附件成功");
		}
		return json.toString();
	}

	public List select(String d_unid) throws Exception {
		List list = new ArrayList();
		if (StringUtils.isNotEmpty(d_unid)) {
			list = (List) commonDAO.select("File.selectFileByD_id", d_unid);
		}
		return list;
	}

	public void downLoad(String unid, HttpServletResponse response,
			boolean isOnLine) throws Exception {
		com.jl.entity.File file = (com.jl.entity.File) commonDAO.findForObject(
				"File.selectFileById", unid);
		String filePath = file.getAddress();
		File f = new File(filePath);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename="
					+ f.getName());
			// 文件名应该编码成UTF-8
		} else { // 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			response.flushBuffer();
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}

	public String downLoadHttp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String unid = request.getParameter("unid");
		com.jl.entity.File file = (com.jl.entity.File) commonDAO.findForObject(
				"File.selectFileById", unid);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String path_ = "\\appdata\\files";

		String httpurl = basePath + path_
				+ StringUtils.substringAfter(file.getAddress(), path_);
		httpurl = httpurl.replaceAll("\\\\", "\\/");
		json.put("httpurl", httpurl);
		return json.toString();
	}

	private com.jl.entity.File saveFile(String id, String userCode,
			String userName, String address, String size, String type,
			String filename) throws Exception {
		com.jl.entity.File file = new com.jl.entity.File();
		file.setAddress(address);
		file.setF_size(size);
		file.setU_unid(userCode);
		file.setFilename(filename);
		file.setF_type(type);
		file.setD_unid(id);
		file.setUpdatetime(TimeUtil.formatDateTime(new Date()));
		file.setNote("用户\"" + userName + "\"创建!");
		file = (com.jl.entity.File) commonDAO.insert("File.insert", file);
		return file;
	}

	private com.jl.entity.File saveFrameFile(String id, String userCode,
			String userName, String address, String size, String type,
			String filename) throws Exception {
		com.jl.entity.File file = new com.jl.entity.File();
		file.setAddress(address);
		file.setF_size(size);
		file.setU_unid(userCode);
		file.setFilename(filename);
		file.setF_type(type);
		file.setD_unid(id);
		file.setUpdatetime(TimeUtil.formatDateTime(new Date()));
		file.setNote("用户\"" + userName + "\"创建!");

		List list = (List) commonDAO.select("File.selectFileByName", file);
		if (list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				com.jl.entity.File object = (com.jl.entity.File) iterator
						.next();
				object.setUpdatetime(TimeUtil.formatDateTime(new Date()));
				commonDAO.update("File.update", object);
			}
			file = new com.jl.entity.File();
		} else {
			file = (com.jl.entity.File) commonDAO.insert("File.insert", file);
		}

		return file;
	}

	public Integer fileCount(String d_unid) throws Exception {
		Integer filecount = 0;
		if (StringUtils.isNotEmpty(d_unid)) {
			filecount = (Integer) this.commonDAO.findForObject(
					"File.selectFileCountByD_id", d_unid);
		}
		return filecount;
	}

	public String updateD_unidForUnid(Map map) throws Exception {
		JSONObject json = new JSONObject();
		this.commonDAO.update("File.updateD_unid", map);
		json.put("tip", "保存或更新附件成功");
		return json.toString();
	}

	public String deleteFileByUnidAndD_unid(HttpServletRequest request,
			String unid, String d_unid) throws Exception {
		JSONObject json = new JSONObject();
		String[] unidList = unid.split(",");
		List list = new ArrayList();
		Map map = new HashMap();
		for (int i = 0; i < unidList.length; i++) {
			map.put("unid", unidList[i]);
			map.put("d_unid", d_unid);
			com.jl.entity.File file = (com.jl.entity.File) commonDAO
					.findForObject("File.selectFileById", unidList[i]);
			commonDAO.delete("File.deleteFileByUnidAndD_unid", map);
			DirectoryManager dirmanage = new DirectoryManager();
			boolean judge = dirmanage.deleteFile(file.getAddress());
			// list.add(map);
		}
		// this.commonDAO.deleteBatch("File.deleteFileByUnidAndD_unid", list);
		json.put("tip", "删除文件成功!");
		return json.toString();
	}

	private static String formatSize(float size) {
		long kb = 1024;
		long mb = (kb * 1024);
		long gb = (mb * 1024);
		if (size < kb) {
			return String.format("%d", (int) size);
		} else if (size < mb) {
			return String.format("%.2f", size / kb); // 保留两位小数
		} else if (size < gb) {
			return String.format("%.2f", size / mb);
		} else {
			return String.format("%.2f", size / gb);
		}
	}

	private static String formatKbSize(float size) {
		long kb = 1024;
		return String.format("%.2f", size / kb); // 保留两位小数
	}

	private static int BUFFER_SIZE = 8096; // 缓冲区大小

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	private void saveToFile(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(fileName);

		// 保存文件
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	public static void main(String[] args) throws Exception {
		CommonDAO commonDAO = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");
		String unid = "0dcef6eaa55a11e0ba6d001a738688d7";
		com.jl.entity.File file = (com.jl.entity.File) commonDAO.findForObject(
				"File.selectFileById", unid);
		String path = "ndyd";
		String basePath = "http://127.0.0.1/ndyd/";
		String path_ = "\\appdata\\files";

		String httpurl = basePath + path_
				+ StringUtils.substringAfter(file.getAddress(), path_);
		System.out.println(httpurl.replaceAll("\\\\", "\\/"));
	}
}
