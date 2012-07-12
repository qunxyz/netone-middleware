package com.jl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public interface FileService {

	/**
	 * �ϴ��ļ�
	 * 
	 * @param id
	 * @param filename
	 * @param filetype
	 * @param path
	 * @param usercode
	 * @param fileItem
	 * @return
	 * @throws Exception
	 */
	public String save(HttpServletRequest request,String id, String filename, String filetype,
			String path, String usercode, String userName, FileItem fileItem)
			throws Exception;
	
	/**
	 * �ϴ��ļ�
	 * 
	 * @param id
	 * @param filename
	 * @param filetype
	 * @param path
	 * @param usercode
	 * @param fileItem
	 * @return
	 * @throws Exception
	 */
	public String saveFrame(HttpServletRequest request,String id, String filename, String filetype,
			String path, String usercode, String userName, FileItem fileItem)
			throws Exception;

	/**
	 * ɾ���ļ�
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request,String id) throws Exception;

	/**
	 * ��ʾ����
	 * 
	 * @param d_unid
	 * @return
	 * @throws Exception
	 */
	public List select(String d_unid) throws Exception;

	/**
	 * �ļ�����
	 * 
	 * @param unid
	 * @param response
	 * @param isOnLine
	 * @throws Exception
	 */
	public void downLoad(String unid, HttpServletResponse response,
			boolean isOnLine) throws Exception;

	/**
	 * �ļ�����
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String  downLoadHttp(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * ͨ��unid��ȡ�ļ���
	 * 
	 * @param d_unid
	 * @throws Exception
	 */
	public Integer fileCount(String d_unid) throws Exception;

	/**
	 * ͨ��map���¸����е�d_unid�ֶ� ���б���
	 * 
	 * @param map
	 * @return String
	 * @throws Exception
	 */
	public String updateD_unidForUnid(Map map) throws Exception;

	/**
	 * ͨ��unid��d_unidɾ������
	 * 
	 * @param unid
	 * @param d_unid
	 * @return
	 * @throws Exception
	 */
	public String deleteFileByUnidAndD_unid(HttpServletRequest request,String unid, String d_unid)
			throws Exception;
}
