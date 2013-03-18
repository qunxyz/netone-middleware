/**
 * 
 */
package com.jl.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.CommonUploadUtil;
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.entity.User;
import com.jl.service.FileService;

public class FileAction extends AbstractAction {

	// 进入页面的主入口
	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String d_unid = request.getParameter("d_unid");
		List result = service.select(d_unid);
		Integer fileCount = service.fileCount(d_unid);
		request.setAttribute("fileCount", fileCount);
		request.setAttribute("list", result);
		return mapping.findForward("onMainView");
	}

	// 合同管理附件处理onContractMgr
	public ActionForward onContractMgr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String d_unid = request.getParameter("d_unid");
		// List result = service.select(d_unid);
		Integer fileCount = service.fileCount(d_unid);
		request.setAttribute("fileCount", fileCount);
		// request.setAttribute("list", result);
		return mapping.findForward("onContractMgr");
	}

	// 进入页面的主入口
	public ActionForward onPublicMainView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String d_unid = request.getParameter("d_unid");
		List result = service.select(d_unid);
		Integer fileCount = service.fileCount(d_unid);
		request.setAttribute("fileCount", fileCount);
		request.setAttribute("list", result);
		return mapping.findForward("onPublicMainView");
	}

	public ActionForward onFrameFileMainView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String d_unid = request.getParameter("d_unid");
		List result = service.select(d_unid);
		Integer fileCount = service.fileCount(d_unid);
		request.setAttribute("fileCount", fileCount);
		request.setAttribute("list", result);
		return mapping.findForward("onFrameFileMainView");
	}

	public void delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("unid");
			String jsonx = service.delete(request, id);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "删除附件失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onUploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		String html = "";
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");// 应用服务器目录
			String id = request.getParameter("id");
			String ext = request.getParameter("ext");// 扩展字段
			String filefield = "files";
			if (StringUtils.isNotEmpty(ext)) {
				filefield = ext;
			}
			String filetype = URLDecoder.decode(request.getParameter("f_type"),
					"UTF-8");// 去除中文乱码;
			String filename = URLDecoder.decode(request
					.getParameter("filename").replace(" ", "%2B"), "UTF-8");// 去除中文乱码;
			CommonUploadUtil importS = new CommonUploadUtil(request);
			FileItem fileItem = importS.getFileItem(filefield);// 获取页面传来的文件
			User user = getOnlineUser(request);
			String htmlx = service.save(request, id, filename, filetype, path,
					user.getUserCode(), user.getUserName(), fileItem);
			json = JSONObject.fromObject(htmlx);
			html = json.getString("unid") + "(~|~|~)"
					+ json.getString("filename") + "(~|~|~)"
					+ json.getString("f_type") + "(~|~|~)"
					+ json.getString("f_size") + "(~|~|~)"
					+ json.getString("updatetime") + "(~|~|~)"
					+ json.getString("note");

		} catch (Exception e) {
			json.put("tip", "上传附件失败!");
			json.put("error", "yes");
			html = "";
			log.error("上传附件失败!", e);
		} finally {
			// super.writeJsonStr(response, json.toString());
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(html);
			} catch (IOException e) {
				log.error("上传附件失败!", e);
			}
		}
	}

	public void onUploadFrameFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		String html = "";
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");// 应用服务器目录
			String id = request.getParameter("id");
			String filetype = URLDecoder.decode(request.getParameter("f_type"),
					"UTF-8");// 去除中文乱码;
			String filename = URLDecoder.decode(request
					.getParameter("filename"), "UTF-8");// 去除中文乱码;
			CommonUploadUtil importS = new CommonUploadUtil(request);
			FileItem fileItem = importS.getFileItem("files");// 获取页面传来的文件
			User user = getOnlineUser(request);
			String htmlx = service.saveFrame(request, id, filename, filetype,
					path, user.getUserCode(), user.getUserName(), fileItem);
			json = JSONObject.fromObject(htmlx);
			html = json.getString("unid") + "(~|~|~)"
					+ json.getString("filename") + "(~|~|~)"
					+ json.getString("f_type") + "(~|~|~)"
					+ json.getString("f_size") + "(~|~|~)"
					+ json.getString("updatetime") + "(~|~|~)"
					+ json.getString("note");

		} catch (Exception e) {
			html = "";
			json.put("tip", "上传附件失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			// super.writeJsonStr(response, json.toString());
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(html);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 下载或查看
	public void onDownLoadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String unid = request.getParameter("unid");
		String isOnLine = request.getParameter("isOnLine");
		service.downLoad(unid, response, "1".equals(isOnLine) ? true : false);
	}

	// 下载
	public void onDownLoadHttpFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		try {
			String jsonx = service.downLoadHttp(request, response);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "下载失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 更新d_unid
	public void onUpdateD_unid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		String unid = request.getParameter("unid");
		String d_unid = request.getParameter("d_unid");
		Map map = new HashMap();
		try {
			map.put("unid", unid.split(","));
			map.put("d_unid", d_unid);
			String jsonx = service.updateD_unidForUnid(map);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "保存或更新附件失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void deleteFileByUnidAndD_unid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		try {
			String unid = request.getParameter("unid");
			String d_unid = request.getParameter("d_unid");
			String jsonx = service.deleteFileByUnidAndD_unid(request, unid,
					d_unid);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "删除附件失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public static final String UPLOAD_IMAGE_DIR = "upload/image/";// 图片文件上传目录
	public static final String UPLOAD_MEDIA_DIR = "upload/media/";// 媒体文件上传目录
	public static final String UPLOAD_FILE_DIR = "upload/file/";// 其它文件上传目录

	public void image(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录

		CommonUploadUtil importS = new CommonUploadUtil(request);
		FileItem fileItem = importS.getFileItem("upload");// 获取页面传来的文件
		if (fileItem == null) {
			ajaxJsonErrorMessage(response, "请选择上传文件!");
			return;
		}
		String uploadFileName = fileItem.getName();
		String imageExtension = StringUtils.substringAfterLast(uploadFileName,
				".").toLowerCase();// 后辍名

		String allowedUploadImageExtension = config
				.getString("allowedUploadImageExtension");
		if (StringUtils.isEmpty(allowedUploadImageExtension)) {
			ajaxJsonErrorMessage(response, "不允许上传图片文件!");
			return;
		}
		String[] imageExtensionArray = allowedUploadImageExtension.split(",");
		if (!ArrayUtils.contains(imageExtensionArray, imageExtension)) {
			ajaxJsonErrorMessage(response, "只允许上传图片文件类型: "
					+ allowedUploadImageExtension + "!");
			return;
		}
		int uploadLimit = Integer.parseInt(config.getString("uploadLimit")) * 1024;
		if (uploadLimit != 0) {
			if (fileItem != null && fileItem.getSize() > uploadLimit) {
				ajaxJsonErrorMessage(response, "文件大小超出限制!");
				return;
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");

		String dateString = simpleDateFormat.format(new Date());
		String uploadImagePath = UPLOAD_IMAGE_DIR + dateString + "/"
				+ DyFormBuildHtml.uuid() + "." + imageExtension;

		if (fileItem != null) {
			File _filedir = new File(path + UPLOAD_IMAGE_DIR + dateString);
			if (!_filedir.exists()) {
				_filedir.mkdirs();
			}

			File _file = new File(path + uploadImagePath);
			fileItem.write(_file);
			fileItem.getOutputStream().flush();
			fileItem.getOutputStream().close();
			fileItem = null;
		}
		json.put("status", "success");
		String path_ = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path_ + "/";
		json.put("url", basePath + uploadImagePath);
		ajax(response, json.toString(), "text/html");
	}

	public void media(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录

		CommonUploadUtil importS = new CommonUploadUtil(request);
		FileItem fileItem = importS.getFileItem("upload");// 获取页面传来的文件
		if (fileItem == null) {
			ajaxJsonErrorMessage(response, "请选择上传文件!");
			return;
		}
		String uploadFileName = fileItem.getName();
		String mediaExtension = StringUtils.substringAfterLast(uploadFileName,
				".").toLowerCase();// 后辍名

		String allowedUploadMediaExtension = config
				.getString("allowedUploadMediaExtension");
		if (StringUtils.isEmpty(allowedUploadMediaExtension)) {
			ajaxJsonErrorMessage(response, "不允许上传媒体文件!");
			return;
		}
		String[] mediaExtensionArray = allowedUploadMediaExtension.split(",");
		if (!ArrayUtils.contains(mediaExtensionArray, mediaExtension)) {
			ajaxJsonErrorMessage(response, "只允许上传媒体文件类型: "
					+ allowedUploadMediaExtension + "!");
			return;
		}
		int uploadLimit = Integer.parseInt(config.getString("uploadLimit")) * 1024;
		if (uploadLimit != 0) {
			if (fileItem != null && fileItem.getSize() > uploadLimit) {
				ajaxJsonErrorMessage(response, "文件大小超出限制!");
				return;
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");

		String dateString = simpleDateFormat.format(new Date());
		String uploadMediaDir = UPLOAD_MEDIA_DIR + dateString + "/"
				+ DyFormBuildHtml.uuid() + "." + mediaExtension;

		if (fileItem != null) {
			File _filedir = new File(path + UPLOAD_MEDIA_DIR + dateString);
			if (!_filedir.exists()) {
				_filedir.mkdirs();
			}

			File _file = new File(path + uploadMediaDir);
			fileItem.write(_file);
			fileItem.getOutputStream().flush();
			fileItem.getOutputStream().close();
			fileItem = null;
		}
		json.put("status", "success");
		String path_ = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path_ + "/";
		json.put("url", basePath + uploadMediaDir);
		ajax(response, json.toString(), "text/html");
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "error");
		jsonMap.put("message", message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(response, jsonObject.toString(), "text/html");
	}

	// AJAX输出，返回null
	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 合同管理附件处理onContractMgr
	public void onGetFilename(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		String fid = request.getParameter("fid");
		String filename = service.getFilename(fid);
		super.writeJsonStr(response, filename);
	}

}
