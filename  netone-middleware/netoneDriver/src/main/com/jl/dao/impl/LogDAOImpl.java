/**
 * 
 */
package com.jl.dao.impl;

import java.util.List;
import java.util.Map;

import oe.serialize.dao.IBatisDAOSupport;
import oe.serialize.dao.PageInfo;
import com.jl.dao.LogDAO;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 操作日志管理DAO实现类
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */
public class LogDAOImpl extends IBatisDAOSupport implements LogDAO {

	/**
	 * 数据源2
	 */
	private SqlMapClient sqlMapClient2;

	public Object insertLog(Object object) throws Exception {
		return sqlMapClient2.insert("Log.insertLog", object);
	}

	public PageInfo findLog(Map condition) throws Exception {
		PageInfo page = new PageInfo();
		Integer count = (Integer) sqlMapClient2.queryForObject(
				"Log.queryLogForPageByCount", condition);
		page.setTotalRows(count);

		List resultList = sqlMapClient2.queryForList("Log.queryLogForPage",
				condition);
		page.setResultList(resultList);
		return page;
	}

	public SqlMapClient getSqlMapClient2() {
		return sqlMapClient2;
	}

	public void setSqlMapClient2(SqlMapClient sqlMapClient2) {
		this.sqlMapClient2 = sqlMapClient2;
	}

}
