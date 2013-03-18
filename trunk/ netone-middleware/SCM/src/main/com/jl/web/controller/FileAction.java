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

	// ����ҳ��������
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

	// ��ͬ����������onContractMgr
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

	// ����ҳ��������
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
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("unid");
			String jsonx = service.delete(request, id);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "ɾ������ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onUploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		String html = "";
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");// Ӧ�÷�����Ŀ¼
			String id = request.getParameter("id");
			String ext = request.getParameter("ext");// ��չ�ֶ�
			String filefield = "files";
			if (StringUtils.isNotEmpty(ext)) {
				filefield = ext;
			}
			String filetype = URLDecoder.decode(request.getParameter("f_type"),
					"UTF-8");// ȥ����������;
			String filename = URLDecoder.decode(request
					.getParameter("filename").replace(" ", "%2B"), "UTF-8");// ȥ����������;
			CommonUploadUtil importS = new CommonUploadUtil(request);
			FileItem fileItem = importS.getFileItem(filefield);// ��ȡҳ�洫�����ļ�
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
			json.put("tip", "�ϴ�����ʧ��!");
			json.put("error", "yes");
			html = "";
			log.error("�ϴ�����ʧ��!", e);
		} finally {
			// super.writeJsonStr(response, json.toString());
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(html);
			} catch (IOException e) {
				log.error("�ϴ�����ʧ��!", e);
			}
		}
	}

	public void onUploadFrameFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
		FileService service = (FileService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("fileSerivce");
		JSONObject json = new JSONObject();
		String html = "";
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");// Ӧ�÷�����Ŀ¼
			String id = request.getParameter("id");
			String filetype = URLDecoder.decode(request.getParameter("f_type"),
					"UTF-8");// ȥ����������;
			String filename = URLDecoder.decode(request
					.getParameter("filename"), "UTF-8");// ȥ����������;
			CommonUploadUtil importS = new CommonUploadUtil(request);
			FileItem fileItem = importS.getFileItem("files");// ��ȡҳ�洫�����ļ�
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
			json.put("tip", "�ϴ�����ʧ��!");
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

	// ���ػ�鿴
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

	// ����
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
			json.put("tip", "����ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ����d_unid
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
			json.put("tip", "�������¸���ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void deleteFileByUnidAndD_unid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json������ʾ
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
			json.put("tip", "ɾ������ʧ��!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public static final String UPLOAD_IMAGE_DIR = "upload/image/";// ͼƬ�ļ��ϴ�Ŀ¼
	public static final String UPLOAD_MEDIA_DIR = "upload/media/";// ý���ļ��ϴ�Ŀ¼
	public static final String UPLOAD_FILE_DIR = "upload/file/";// �����ļ��ϴ�Ŀ¼

	public void image(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");// Ӧ�÷�����Ŀ¼

		CommonUploadUtil importS = new CommonUploadUtil(request);
		FileItem fileItem = importS.getFileItem("upload");// ��ȡҳ�洫�����ļ�
		if (fileItem == null) {
			ajaxJsonErrorMessage(response, "��ѡ���ϴ��ļ�!");
			return;
		}
		String uploadFileName = fileItem.getName();
		String imageExtension = StringUtils.substringAfterLast(uploadFileName,
				".").toLowerCase();// �����

		String allowedUploadImageExtension = config
				.getString("allowedUploadImageExtension");
		if (StringUtils.isEmpty(allowedUploadImageExtension)) {
			ajaxJsonErrorMessage(response, "�������ϴ�ͼƬ�ļ�!");
			return;
		}
		String[] imageExtensionArray = allowedUploadImageExtension.split(",");
		if (!ArrayUtils.contains(imageExtensionArray, imageExtension)) {
			ajaxJsonErrorMessage(response, "ֻ�����ϴ�ͼƬ�ļ�����: "
					+ allowedUploadImageExtension + "!");
			return;
		}
		int uploadLimit = Integer.parseInt(config.getString("uploadLimit")) * 1024;
		if (uploadLimit != 0) {
			if (fileItem != null && fileItem.getSize() > uploadLimit) {
				ajaxJsonErrorMessage(response, "�ļ���С��������!");
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
		String path = request.getSession().getServletContext().getRealPath("/");// Ӧ�÷�����Ŀ¼

		CommonUploadUtil importS = new CommonUploadUtil(request);
		FileItem fileItem = importS.getFileItem("upload");// ��ȡҳ�洫�����ļ�
		if (fileItem == null) {
			ajaxJsonErrorMessage(response, "��ѡ���ϴ��ļ�!");
			return;
		}
		String uploadFileName = fileItem.getName();
		String mediaExtension = StringUtils.substringAfterLast(uploadFileName,
				".").toLowerCase();// �����

		String allowedUploadMediaExtension = config
				.getString("allowedUploadMediaExtension");
		if (StringUtils.isEmpty(allowedUploadMediaExtension)) {
			ajaxJsonErrorMessage(response, "�������ϴ�ý���ļ�!");
			return;
		}
		String[] mediaExtensionArray = allowedUploadMediaExtension.split(",");
		if (!ArrayUtils.contains(mediaExtensionArray, mediaExtension)) {
			ajaxJsonErrorMessage(response, "ֻ�����ϴ�ý���ļ�����: "
					+ allowedUploadMediaExtension + "!");
			return;
		}
		int uploadLimit = Integer.parseInt(config.getString("uploadLimit")) * 1024;
		if (uploadLimit != 0) {
			if (fileItem != null && fileItem.getSize() > uploadLimit) {
				ajaxJsonErrorMessage(response, "�ļ���С��������!");
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

	// ���JSON������Ϣ������null
	public String ajaxJsonErrorMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "error");
		jsonMap.put("message", message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(response, jsonObject.toString(), "text/html");
	}

	// AJAX���������null
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
	
	// ��ͬ����������onContractMgr
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
