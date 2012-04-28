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
	/** �ļ����б� */
	private Map fileFields = new TreeMap();
	/** �����б� */
	private Map formFields = new TreeMap();

	/** Check that we have a file upload request */
	private boolean isMultipart = false;
	private HttpServletRequest request = null;

	private CommonUploadUtil() {
	}

	/**
	 * �������в�������һ���ϴ�������.
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	public CommonUploadUtil(HttpServletRequest request) {
		setRequest(request);
	}

	/**
	 * * ���� HttpServletRequest ����������ı�����.
	 * 
	 * @param request -
	 *            HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		isMultipart = FileUpload.isMultipartContent(request);

		// ������ļ��ϴ�����, ����ȡ����Ĳ���
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
				System.err.println("�޷������ϴ�����:" + ex);
			}
		}

	}

	/**
	 * �������Ŀ.
	 * 
	 * @param item -
	 *            FileItem ����
	 */
	private void processFormField(FileItem item) throws Exception {
		String name = item.getFieldName();
		String value = item.getString("utf-8");
		// String value = item.getString();
		// ���ȳ��Ի�ȡԭ����ֵ
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
	 * �����ļ���Ŀ.
	 * 
	 * @param item -
	 *            FileItem ����
	 */
	private void processUploadedFile(FileItem item) {
		String name = item.getFieldName();
		fileFields.put(name, item);
	}

	/**
	 * ��ȡ�ϴ����ļ���Ŀ.FileItem(��Ŀ)������Ĺؼ�����ϴ���������ͨ�����FileItem����Ϊ������
	 * 
	 * @param name -
	 *            String, �ļ�������-->���ϴ���Ԫ�ص�name����
	 * @return FileItem - org.apache.commons.fileupload.FileItem ����
	 */
	public FileItem getFileItem(String name) {
		if (!isMultipart) {
			return null;
		}
		return (FileItem) (fileFields.get(name));
	}

	/**
	 * ��ȡ������.(�൱��request.getParameter(String
	 * name)�������˷���������UploadFile�ڲ�ʵ��request�Ĺ��ܣ��ﵽ�˷�װ������Ŀ��)
	 * 
	 * @param name -
	 *            String, ��������
	 * @return String - ����ֵ
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
	 * ��ȡ����Ķ������ֵ.
	 * 
	 * @param name -
	 *            String, ��������
	 * @return String[] - ����Ķ��ȡֵ���÷�����getParameter ����չ
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
	 * ���ص�ǰ�����Ƿ�Ϊ�ಿ���ϴ�����.
	 * 
	 * @return boolean
	 */
	public boolean isMultipart() {
		return isMultipart;
	}

	/**
	 * ���� FileItem ����ָ�����ļ�.
	 * 
	 * @param item -
	 *            FileItem, Ҫ������ϴ��ļ���Ŀ
	 * @param filename -
	 *            String, Ҫ���浽���ļ��ķ�����·��
	 * @return boolean - �Ƿ񱣴�ɹ�
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
	 * ���� FileItem ����ָ�����ļ�.
	 * 
	 * @param item -
	 *            FileItem, Ҫ������ϴ��ļ���ȫ·��
	 * @param file -
	 *            File, Ҫ���浽���ļ�����
	 * @return boolean - �Ƿ񱣴�ɹ�
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