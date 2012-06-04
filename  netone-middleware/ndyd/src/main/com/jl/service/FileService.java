package com.jl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public interface FileService {

	/**
	 * 上传文件
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
	 * 上传文件
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
	 * 删除文件
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request,String id) throws Exception;

	/**
	 * 显示附件
	 * 
	 * @param d_unid
	 * @return
	 * @throws Exception
	 */
	public List select(String d_unid) throws Exception;

	/**
	 * 文件下载
	 * 
	 * @param unid
	 * @param response
	 * @param isOnLine
	 * @throws Exception
	 */
	public void downLoad(String unid, HttpServletResponse response,
			boolean isOnLine) throws Exception;

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String  downLoadHttp(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 通过unid获取文件数
	 * 
	 * @param d_unid
	 * @throws Exception
	 */
	public Integer fileCount(String d_unid) throws Exception;

	/**
	 * 通过map更新附件中的d_unid字段 进行保存
	 * 
	 * @param map
	 * @return String
	 * @throws Exception
	 */
	public String updateD_unidForUnid(Map map) throws Exception;

	/**
	 * 通过unid和d_unid删除附件
	 * 
	 * @param unid
	 * @param d_unid
	 * @return
	 * @throws Exception
	 */
	public String deleteFileByUnidAndD_unid(HttpServletRequest request,String unid, String d_unid)
			throws Exception;
}
