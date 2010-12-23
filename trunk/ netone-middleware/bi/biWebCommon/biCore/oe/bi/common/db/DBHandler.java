package oe.bi.common.db;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

import oe.bi.datasource.obj.Datasource;


/**
 * 数据库查询操作入口
 * 
 * 内部功能说明:数据库查询操作入口,提供三类数据查询操作的句柄. 他们都能用于执行指定的SQL语句,并且返回ResultSet(forward only
 * ,read only) 结果集合. 其中,每个方法操作句柄,都对应于一个Connection,这个Connection从系统启动开始,直到系统关闭期间永远
 * 只保持一个实例,所有无需关闭(如果万一被数据库回收,那么要重新创建一个).而对于Stament,preparedStatement,DatabaseMetaData
 * 使用者必须手工关闭,还有执行结果Resultset使用结束后也必须手工关闭
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DBHandler {
	/**
	 * 获得SQL执行状态句柄
	 * 
	 * @param datasourceObj
	 * @return
	 */
	Statement fetchHanderStatement(Datasource datasourceObj);

	/**
	 * 获得带预制的SQL执行状态句柄
	 * 
	 * @param datasourceObj
	 * @param sql
	 * @return
	 */
	PreparedStatement fetchHanderPreparedStatement(Datasource datasourceObj,
			String sql);

	/**
	 * 获得数据源的DatabaseMetaData
	 * 
	 * @param datasourceObj
	 * @return
	 */
	DatabaseMetaData fetchDatabaseMetaData(Datasource datasourceObj);

}
