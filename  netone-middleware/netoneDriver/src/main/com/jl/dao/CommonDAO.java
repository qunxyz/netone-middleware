package com.jl.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import oe.serialize.dao.PageInfo;

/**
 * 基本数据访问对象接口<BR>
 * 目前暂时不支持多数据源操作(可配置),基本DAO操作都可以用这个来实现
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Apr 6, 2010 create by Don
 * @history
 */
public abstract interface CommonDAO {

	/**
	 * 插入
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object insert(String statement, Object object) throws Exception;

	/**
	 * 批量插入
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract int insertBatch(String statement, List entitySet) throws Exception;

	/**
	 * 更新
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object update(String statement, Object object) throws Exception;

	/**
	 * 删除
	 * 
	 * @param statement
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public abstract Object delete(String statement, Object object) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param statement
	 * @param entitySet
	 * @throws Exception
	 */
	public abstract int deleteBatch(String statement, List entitySet) throws Exception;

	/**
	 * 批量更新
	 * 
	 * @param statement
	 * @param entitySet
	 * @throws Exception
	 */
	public abstract int updateBatch(String statement, List entitySet) throws Exception;

	/**
	 * 返回对象 可以是int 可以是string 也可以是一个对象
	 * 
	 * @param statement
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	public abstract Object findForObject(String statement, Object Object) throws Exception;

	/**
	 * 分页查询
	 * 
	 * @param totalStatement
	 * @param resultStatement
	 * @param condition
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract PageInfo selectForPage(String totalStatement, String resultStatement,
			Map condition, PageInfo page) throws Exception;

	/**
	 * 普通查询
	 * 
	 * @param statement
	 * @param object
	 *            implements Collection
	 * @return
	 * @throws Exception
	 */
	public abstract Collection select(String statement, Object object) throws Exception;
}
