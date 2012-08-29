package com.jl.common.workflow.worklist;

import java.util.List;

/**
 * 工作流待办任务专题
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface WorklistViewIfc {
	/**
	 * 符合条件的待办任务
	 * 
	 * @param clientid
	 *            客户id
	 * @param appname
	 *            应用名
	 * @param mode
	 *            业务模式true 待办、抄送,false 抄阅
	 * @param from
	 * @param size
	 * @param listtype
	 *            任务类型、01 待办、02已办但未归档 、03 已办已归档
	 * @param query
	 *            查询字段对象
	 * @return 数值数组罗列所有对应的记录值
	 * @throws Exception
	 */
	List<DataObj> worklist(String clientid, String appname, boolean mode,
			int from, int size, String listtype, QueryColumn query)
			throws Exception;
	/**
	 * 符合条件的待办任务(仅返回表单的lsh)
	 * 
	 * @param clientid
	 *            客户id
	 * @param appname
	 *            应用名
	 * @param mode
	 *            业务模式true 待办、抄送,false 抄阅
	 * @param from
	 * @param size
	 * @param listtype
	 *            任务类型、01 待办、02已办但未归档 、03 已办已归档
	 * @param query
	 *            查询字段对象
	 * @return 数值数组罗列所有对应的记录值
	 * @throws Exception
	 */
	List<String> worklistOnlyLsh(String clientid, String appname, boolean mode,
			int from, int size, String listtype, QueryColumn query)
			throws Exception;

	/**
	 * 符合条件的待办任务总数
	 * 
	 * @param clientid
	 *            客户id
	 * @param appname
	 *            应用名
	 * 
	 * @param mode
	 *            业务模式true 待办、抄送,false 抄阅
	 * 
	 * @param listtype
	 *            任务类型、01 待办、02已办但未归档 、03 已办已归档
	 * @param query
	 *            查询字段对象
	 * @return
	 * @throws Exception
	 */
	int count(String clientid, String appname, boolean mode, String listtype,
			QueryColumn query) throws Exception;
	/**
	 * 符合条件的待办任务总数（仅表单lsh）
	 * 
	 * @param clientid
	 *            客户id
	 * @param appname
	 *            应用名
	 * 
	 * @param mode
	 *            业务模式true 待办、抄送,false 抄阅
	 * 
	 * @param listtype
	 *            任务类型、01 待办、02已办但未归档 、03 已办已归档
	 * @param query
	 *            查询字段对象
	 * @return
	 * @throws Exception
	 */
	int countOnlyLsh(String clientid, String appname, boolean mode, String listtype,
			QueryColumn query) throws Exception;
	/**
	 * 罗列查询字段
	 * 
	 * @param appname
	 *            应用名
	 * @return
	 */
	List<QueryColumn> listQueryColumn(String appname) throws Exception;

	/**
	 * 装载查询字段
	 * 
	 * @param appname
	 *            应用名
	 * @param index
	 *            字段索引号
	 * @return
	 */
	QueryColumn loadQueryColumn(String appname, int index) throws Exception;

	/**
	 * 装载查询字段序号
	 * 
	 * @param appname
	 *            应用名
	 * @param columnid
	 *            字段索名
	 * @return
	 */
	int fetchQueryColumnIndex(String appname, String columnid) throws Exception;

}
