/**
 * 
 */
package com.jl.dao;

import java.util.Map;

import oe.serialize.dao.DAOException;
import oe.serialize.dao.PageInfo;

/**
 * 操作日志管理DAO接口
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */
public interface LogDAO {

	// 存储过程
	/**
	 * 插入日志 (若无 动态创建日志表 以天为单位)(调用存储过程)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	Object insertLog(Object object) throws Exception;

	/**
	 * 日志查询 调用存储过程(多表查询并分页)
	 * 
	 * @param condition
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageInfo findLog(Map condition) throws Exception;

}
