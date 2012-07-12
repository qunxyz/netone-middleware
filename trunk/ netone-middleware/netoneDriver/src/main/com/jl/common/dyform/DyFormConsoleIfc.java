package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;

import com.jl.common.workflow.TWfActive;

/**
 * 动态表单控制台
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface DyFormConsoleIfc {

	String[][] _HTML_LIST = { { "00", "通用" }, { "01", "数字/金额" },
			{ "02", "时间hh:mm:ss" }, { "03", "日期YYYY-MM-DD" },
			{ "04", "日期时间YYYY-MM-DD hh:mm:ss" }, { "05", "真假" },
			{ "06", "邮件地址" }, { "10", "列表信息" }, { "11", "列表信息K-V" },
			{ "12", "IP地址" }, { "13", "大文本" }, {"14", "文件" }, {"15", "图片" }, {"16", "按钮" }, { "17", "单资源选择" },
			{ "18", "多资源选择" }, { "20", "PORTAL项" }, { "21", "多彩文档" },
			{ "22", "组织人员" }, { "23", "组织人员多选" }, { "24", "当前用户" },
			{ "25", "所属部门" }, { "26", "多选组" }, { "27", "组织机构单选" },
			{ "28", "组织机构多选" } , {"29", "URL" } ,  {"30", "单选组" } ,{"31","真假radio"},{"32","隐藏域"}};;

	String _EVENT_INIT = "init_event";
	String _EVENT_FOCUS = "focus_event";
	String _EVENT_LOSEFOCUS = "losefocus_event";
	String _TYPE_XYOFFSET = "xyoffset";
	String _TYPE_SQLTYPE = "sqltype";

	/**
	 * 装载表单
	 * 
	 * @param formid
	 *            表单唯一标识码
	 * @return
	 */
	DyForm loadForm(String formid) throws Exception;

	/**
	 * 装载表单字段
	 * 
	 * @param formid
	 * @return
	 */
	List<DyFormColumn> fetchColumnList(String formid) throws Exception;
	
	/**
	 * 装载表单字段(设计时使用不要去调度列表脚本执行)
	 * 
	 * @param formid
	 * @return  
	 */
	List<DyFormColumn> fetchColumnListForDesign(String formid) throws Exception;

	/**
	 * 装载表单字段
	 * 
	 * @param formid
	 * @return
	 */
	DyFormColumn loadColumn(String formid, String columnid) throws Exception;

	/**
	 * 往表单中添加数据
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	String addData(String formid, DyFormData bus) throws Exception;

	/**
	 * 修改表单数据
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	boolean modifyData(String formid, DyFormData bus) throws Exception;

	/**
	 * 删除数据
	 * 
	 * @param formid
	 * @param id
	 * @return
	 */
	boolean deleteData(String formid, String id) throws Exception;

	/**
	 * 逻辑删除表单
	 * 
	 * @param formid
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteDataByLogic(String formid, String id) throws Exception;

	/**
	 * 查询数据
	 * 
	 * @param bus
	 * @param form
	 *            起始记录
	 * @param size
	 *            记录数
	 * @param condition
	 * @return
	 */
	List queryData(DyFormData bus, int form, int size, String condition)
			throws Exception;

	/**
	 * 装载数据
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadData(String formcode, String bussid) throws Exception;

	/**
	 * 特殊装载数据
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadDataS(String formcode, String bussid) throws Exception;

	/**
	 * 查询符合条件的记录
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(DyFormData bus, String condition) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param formcode
	 *            表单code
	 * @param fatherlsh
	 *            父节点id，如果是顶级表单父亲节点id为1
	 * @return 删除的条数
	 */
	int deleteAll(String formcode, String fatherlsh) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param formcode
	 *            表单code
	 * @param fatherlsh
	 *            父节点id，如果是顶级表单父亲节点id为1
	 * @param data
	 *            表单记录数据
	 * @return
	 */
	String[] addAll(List<DyFormData> data) throws Exception;

	/**
	 * 表单鉴权
	 * 
	 * @param form
	 *            表单对象
	 * @param usercode
	 *            用户编码
	 * @param TWfActive
	 *            活动信息
	 * @return
	 */
	void permission(DyForm form, String usercode, TWfActive act)
			throws Exception;

	/**
	 * 查询字段装载(用于列表展示字段)
	 * 
	 * @param 简单查询model=0，高级查询model=1，字段列表model=2，字段列表汇总model=3
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnX(String formcode, String model,String lsh) throws Exception;
	
	/**
	 * 查询字段装载(用于查询展示字段)
	 * 
	 * @param 简单查询model=0，高级查询model=1，字段列表model=2，字段列表汇总model=3
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnQ(String formcode,String lsh) throws Exception;
}
