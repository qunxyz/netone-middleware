/**
 * 
 */
package com.jl.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import oe.serialize.dao.PageInfo;
import oe.serialize.dao.ReflectUtil;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.jl.dao.CommonDAO;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

/**
 * 基本数据访问对象<BR>
 * 目前不支持多数据源操作,基本DAO操作都可以用这个来实现
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Apr 6, 2010 create by Don
 * @version 1.1.0 date 2011.1.6 modify by Don
 * @history
 */
public class CommonDAOImpl extends SqlMapClientDaoSupport implements CommonDAO {
	private final Integer SQL_BATCH_COUNT = 1000;// 批量数量

	private SqlExecutor sqlExecutor;

	public SqlExecutor getSqlExecutor() {
		return this.sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
		setSQLExcuter();
	}

	private void setSQLExcuter() {
		if (this.sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate()
					.getSqlMapClient();
			if ((sqlMapClient instanceof ExtendedSqlMapClient))
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
						.getDelegate(), "sqlExecutor", SqlExecutor.class,
						this.sqlExecutor);
		}
	}

	public Collection select(String statement, Object object) throws Exception {
		return getSqlMapClientTemplate().queryForList(statement, object);
	}

	public PageInfo selectForPage(String totalStatement,
			String resultStatement, Map condition, PageInfo page)
			throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				totalStatement, condition);
		page.setTotalRows(count);

		List resultList = getSqlMapClientTemplate().queryForList(
				resultStatement, condition);
		page.setResultList(resultList);
		return page;
	}

	public Object findForObject(String statement, Object Object)
			throws Exception {
		return getSqlMapClientTemplate().queryForObject(statement, Object);
	}

	public Object insert(String statement, Object object) throws Exception {
		getSqlMapClientTemplate().insert(statement, object);
		return object;
	}

	public Object update(String statement, Object object) throws Exception {
		getSqlMapClientTemplate().update(statement, object);
		return object;
	}

	public Object delete(String statement, Object object) throws Exception {
		getSqlMapClientTemplate().delete(statement, object);
		return object;
	}

	public int deleteBatch(final String statement, final List entitySet)
			throws Exception {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			int executeCount = 0;

			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0, count = entitySet.size(); i < count; i++) {
					executor.delete(statement, entitySet.get(i));
					if (i % SQL_BATCH_COUNT == 0) {
						executor.executeBatch();
					}
				}
				executeCount += executor.executeBatch();
				return executeCount;
			}
		});
		return 0;
	}

	public int insertBatch(final String statement, final List entitySet)
			throws Exception {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			int executeCount = 0;

			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0, count = entitySet.size(); i < count; i++) {
					executor.insert(statement, entitySet.get(i));
					if (i % SQL_BATCH_COUNT == 0) {
						executor.executeBatch();
					}
				}
				executeCount += executor.executeBatch();
				return executeCount;
			}
		});
		return 0;
	}

	public int updateBatch(final String statement, final List entitySet)
			throws Exception {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			int executeCount = 0;

			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0, count = entitySet.size(); i < count; i++) {
					executor.update(statement, entitySet.get(i));
					if (i % SQL_BATCH_COUNT == 0) {
						executor.executeBatch();
					}
				}
				executeCount += executor.executeBatch();
				return executeCount;
			}
		});
		return 0;
	}

}
