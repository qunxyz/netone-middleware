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
	 * 针对表单编辑，如果在流程环节中，提交给其他人员，那么表单创建者需要控制权限
	 * @param bussid 业务数据的lsh
	 * @param participant 参与者
	 * @return true表示可以编辑 
	 */
	boolean whenFlowPageEdit(String bussid,String participant)throws Exception;

	/**
	 * 装载表单
	 * 
	 * @param formcode
	 *            表单唯一标识码，在表单的配置文件中的UUID字段
	 * @return
	 */
	DyForm loadForm(String formcode) throws Exception;

	/**
	 * 装载表单字段
	 * 
	 * @param formcode   表单唯一标识码，在表单的配置文件中的UUID字段
	 * @return
	 */
	List<DyFormColumn> fetchColumnList(String formcode) throws Exception;
	
	/**
	 * 装载表单字段(用在设计时使用，这服务不会表单业务脚本)
	 * 
	 * @param formcode   表单唯一标识码，在表单的配置文件中的UUID字段
	 * @return   
	 */
	List<DyFormColumn> fetchColumnListForDesign(String formcode) throws Exception;

	/**
	 * 装载表单字段
	 * 
	 * @param formcode   表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param columnid 表单字段id,在表单的配置文件中的 columnid
	 * @return
	 */
	DyFormColumn loadColumn(String formcode, String columnid) throws Exception;

	/**
	 * 往表单中添加数据
	 * 
	 * @param formcode  表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param bus 表单对象 
	 * 对象构造参考:
	 * DyFormData bus=new DyFormData();
	 * bus.setParticipant("userid");//数据的创建人员
	 * bus.setFatherlsh("1");//如果非子表单为1，否则需要输入父表单的lsh
	 * @return 如果表单创建成功 返回 表单记录的唯一ID 
	 */
	String addData(String formcode, DyFormData bus) throws Exception;

	/**
	 * 修改表单数据
	 * 
	 * @param formcode  表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param bus 一条表单数据对象，注意该对象不是手工构造的，通常是通过loaddata方法构造的

	 * @return
	 */
	boolean modifyData(String formcode, DyFormData bus) throws Exception;

	/**
	 * 删除数据
	 * 
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param lsh 表单的lsh
	 * 
	 * @return 
	 */
	boolean deleteData(String formcode, String lsh) throws Exception;

	/**
	 * 逻辑删除表单
	 * 
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param lsh 表单的lsh
	 * @return
	 * @throws Exception
	 */
	boolean deleteDataByLogic(String formcode, String lsh) throws Exception;

	/**
	 * 查询数据
	 * 
	 * @param bus 一条表单数据对象,里面非空字段系统会自动转换为查询条件内容
	 * @param from
	 *            起始记录
	 * @param size
	 *            记录数
	 * @param condition 扩张的SQL的where子条件
	 * @return
	 */
	List<DyFormData> queryData(DyFormData bus, int from, int size, String condition)
			throws Exception;

	/**
	 * 装载数据
	 * 
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param lsh 表单的lsh
	 * @return 表单设计结构对象
	 * @throws RemoteException 
	 */
	DyFormData loadData(String formcode, String lsh) throws Exception;

	/**
	 * 特殊装载数据
	 * 
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param lsh 表单的lsh
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadDataS(String formcode, String lsh) throws Exception;

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
	 *            表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param fatherlsh
	 *            父节点id，如果是顶级表单父亲节点id为1
	 * @return 删除的条数
	 */
	int deleteAll(String formcode, String fatherlsh) throws Exception;

	/**
	 * 批量添加
	 * 
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
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnX(String formcode, String model) throws Exception;
	
	/**
	 * 查询字段装载(用于查询展示字段)
	 * 
	 * @param 简单查询model=0，高级查询model=1，字段列表model=2，字段列表汇总model=3
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnQ(String formcode) throws Exception;
	
	/**
	 * 管理表单，针对特殊应用，通过授权用户可以再任何时候去修改相关字段，无论该表单
	 * 是归档过还是未归档过
	 * @param formcode 表单唯一标识码，在表单的配置文件中的UUID字段
	 * @param participant
	 * @return
	 */
	String[] manageColumn(String formcode,String participant) throws Exception;
	
}
