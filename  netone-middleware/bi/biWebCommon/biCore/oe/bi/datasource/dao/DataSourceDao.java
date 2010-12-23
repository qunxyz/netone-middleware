package oe.bi.datasource.dao;

import java.util.List;

import oe.bi.datasource.obj.Datasource;


/**
 * 数据源DAO接口 该接口中定义了数据源的创建、修改、删除、查询、装载的操作。
 * 
 * 实现说明: 其中数据源的DAO的持久化目标,可配置实现驱动. 1)数据库实现 PRI (LOW) 2)XML实现 PRI (High)
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DataSourceDao {
	/**
	 * 创建数据源
	 * 
	 * @param tBiDatasource
	 *            数据源对象
	 */
	void create(Datasource tBiDatasource);

	/**
	 * 更新数据源
	 * 
	 * @param tBiDatasource
	 */
	void update(Datasource tBiDatasource);

	/**
	 * 删除数据源
	 * 
	 * @param tBiDatasource
	 */
	void drop(Datasource tBiDatasource);

	/**
	 * 查询
	 * 
	 * @return
	 */
	List query(String condition);
	/**
	 * 装载数据
	 * @param id
	 * @return
	 */
	Datasource load(String id);

}
