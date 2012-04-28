package com.jl.common;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

public class CommonUploadUtil {
	/** 文件域列表 */
	private Map fileFields = new TreeMap();
	/** 表单域列表 */
	private Map formFields = new TreeMap();

	/** Check that we have a file upload request */
	private boolean isMultipart = false;
	private HttpServletRequest request = null;

	private CommonUploadUtil() {
	}

	/**
	 * 根据现有参数构造一个上传处理器.
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	public CommonUploadUtil(HttpServletRequest request) {
		setRequest(request);
	}

	/**
	 * * 设置 HttpServletRequest 并分析里面的表单数据.
	 * 
	 * @param request -
	 *            HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		isMultipart = FileUpload.isMultipartContent(request);

		// 如果是文件上传请求, 就提取里面的参数
		if (isMultipart) { // Create a new file upload handler
			DiskFileUpload upload = new DiskFileUpload();
			try {
				upload.setHeaderEncoding("utf-8");
				// Parse the request
				/* FileItem */
				List items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					String name = item.getFieldName();
					String value = item.getString();
					if (item.isFormField()) {
						processFormField(item);
					} else {
						processUploadedFile(item);
					}
				}
			} catch (Exception ex) {
				System.err.println("无法处理上传数据:" + ex);
			}
		}

	}

	/**
	 * 处理表单项目.
	 * 
	 * @param item -
	 *            FileItem 对象
	 */
	private void processFormField(FileItem item) throws Exception {
		String name = item.getFieldName();
		String value = item.getString("utf-8");
		// String value = item.getString();
		// 首先尝试获取原来的值
		Object oldValue = formFields.get(name);
		if (oldValue == null) {
			formFields.put(name, value);
		} else {
			List fieldValueSet = null;
			if (oldValue instanceof String) {
				fieldValueSet = new ArrayList();
				fieldValueSet.add(oldValue);
				fieldValueSet.add(value);
			}
			if (oldValue instanceof List) {
				fieldValueSet = (List) oldValue;
				fieldValueSet.add(value);
			}
		}
	}

	/**
	 * 处理文件项目.
	 * 
	 * @param item -
	 *            FileItem 对象
	 */
	private void processUploadedFile(FileItem item) {
		String name = item.getFieldName();
		fileFields.put(name, item);
	}

	/**
	 * 获取上传的文件项目.FileItem(项目)是这里的关键概念。上传函数就是通过这个FileItem来做为桥梁的
	 * 
	 * @param name -
	 *            String, 文件域名称-->即上传表单元素的name属性
	 * @return FileItem - org.apache.commons.fileupload.FileItem 对象
	 */
	public FileItem getFileItem(String name) {
		if (!isMultipart) {
			return null;
		}
		return (FileItem) (fileFields.get(name));
	}

	/**
	 * 获取表单参数.(相当与request.getParameter(String
	 * name)方法。此方法可以在UploadFile内部实现request的功能，达到了封装的良好目的)
	 * 
	 * @param name -
	 *            String, 表单域名称
	 * @return String - 表单域值
	 */
	public String getParameter(String name) {
		if (!isMultipart) {
			return request.getParameter(name);
		}
		Object value = formFields.get(name);
		if (value != null) {
			if (value instanceof String) {
				return (String) value;
			}
		}
		return null;
	}

	/**
	 * 获取表单域的多个参数值.
	 * 
	 * @param name -
	 *            String, 表单域名称
	 * @return String[] - 表单域的多个取值，该方法是getParameter 的扩展
	 */
	public String[] getParameterValues(String name) {
		if (!isMultipart) {
			return request.getParameterValues(name);
		}
		Object value = formFields.get(name);
		if (value != null) {
			if (value instanceof List) {
				return (String[]) ((List) value).toArray(new String[0]);
			}
		}
		return null;
	}

	/**
	 * 返回当前请求是否为多部分上传请求.
	 * 
	 * @return boolean
	 */
	public boolean isMultipart() {
		return isMultipart;
	}

	/**
	 * 保存 FileItem 对象到指定的文件.
	 * 
	 * @param item -
	 *            FileItem, 要保存的上传文件项目
	 * @param filename -
	 *            String, 要保存到的文件的服务器路径
	 * @return boolean - 是否保存成功
	 */
	public static boolean saveFileItem(FileItem item, String filename) {
		try {
			item.write(new File(filename));
			return true;
		} catch (Exception ex) { //
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存 FileItem 对象到指定的文件.
	 * 
	 * @param item -
	 *            FileItem, 要保存的上传文件名全路径
	 * @param file -
	 *            File, 要保存到的文件对象
	 * @return boolean - 是否保存成功
	 */
	public static InputStream getInputStreamFromFileItem(FileItem item,
			File file) {
		try {
			return item.getInputStream();
		}

		catch (Exception ex) { //
			ex.printStackTrace();
		}
		return null;
	}

}