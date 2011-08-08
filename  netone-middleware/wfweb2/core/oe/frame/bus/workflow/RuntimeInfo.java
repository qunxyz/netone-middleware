package oe.frame.bus.workflow;

/**
 * 运行期常量
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeInfo {

	// /////////////流程定义错误信息///////////////////
	String OE_WF_DEF_ERR_001 = "OE_WF_DEF_ERR_001 [流程定义中缺少首活动]";

	String OE_WF_DEF_ERR_002 = "OE_WF_DEF_ERR_002 [流程定义中缺少尾活动]";

	String OE_WF_DEF_ERR_003 = "OE_WF_DEF_ERR_003 [子流程定义错误]";

	String OE_WF_DEF_ERR_004 = "OE_WF_DEF_ERR_004 [缺少静态活动对象]";

	String OE_WF_DEF_ERR_005 = "OE_WF_DEF_ERR_005 [缺少静态流程对象]";

	String OE_WF_DEF_ERR_006 = "OE_WF_DEF_ERR_006 [流程中缺少活动定义]";

	String OE_WF_DEF_ERR_007 = "OE_WF_DEF_ERR_007 [无效传输线]";

	String OE_WF_DEF_ERR_008 = "OE_WF_DEF_ERR_008 [不允许在传输线上定义公式计算]";

	String OE_WF_DEF_ERR_009 = "OE_WF_DEF_ERR_009 [流程定义文件不符合XPDL规范]";

	String OE_WF_DEF_ERR_010 = "OE_WF_DEF_ERR_010 [流程包为空]";

	String OE_WF_DEF_ERR_011 = "OE_WF_DEF_ERR_011 [无效活动类型]";

	String OE_WF_DEF_ERR_012 = "OE_WF_DEF_ERR_012 [流程不存在]";

	String OE_WF_DEF_ERR_013 = "OE_WF_DEF_ERR_013 [包中的流程定义不唯一]";

	String OE_WF_DEF_ERR_014 = "OE_WF_DEF_ERR_014 [流程未注册]";

	// ////////////流程运行错误信息////////////////
	String OE_WF_RMT_ERR_001 = "OE_WF_RMT_ERR_001 [缺少流程运行对象]";

	String OE_WF_RMT_ERR_002 = "OE_WF_RMT_ERR_002 [增加相关数据实例失败]";

	String OE_WF_RMT_ERR_003 = "OE_WF_RMT_ERR_003 [缺少活动运行对象]";

	String OE_WF_RMT_ERR_004 = "OE_WF_RMT_ERR_004 [流程运行处于非准备状态，无法激活]";

	String OE_WF_RMT_ERR_005 = "OE_WF_RMT_ERR_005 [非运行状态下的动态活动，无法提交]";

	String OE_WF_RMT_ERR_006 = "OE_WF_RMT_ERR_006 [流程运行对象处于非运行状态中，无法导航]";

	String OE_WF_RMT_ERR_007 = "OE_WF_RMT_ERR_007 [流程运行异常中断]";

	String OE_WF_RMT_ERR_008 = "OE_WF_RMT_ERR_008 [非同步等待活动]";

	String OE_WF_RMT_ERR_009 = "OE_WF_RMT_ERR_009 [同步活动等待]";

	String OE_WF_RMT_WAR_009 = "OE_WF_RMT_ERR_010 [自动提交非自动状态的活动]";

	// /////////////////流程运行信息///////////////////

	String OE_WF_RMT_INFO_001 = "OE_WF_RMT_INFO_001 [流程目前处于竞争模式中，当前活动的路由要被忽略]";

	String OE_WF_RMT_INFO_002 = "OE_WF_RMT_INFO_002 [流程执行结束]";

	String OE_WF_RMT_INFO_003 = "OE_WF_RMT_INFO_003 [等待同步]";

	String OE_WF_RMT_INFO_004 = "OE_WF_RMT_INFO_004 [空活动自动提交]";

	String OE_WF_RMT_INFO_005 = "OE_WF_RMT_INFO_005 [调度子流程]";

	String OE_WF_RMT_INFO_006 = "OE_WF_RMT_INFO_006 [同步子流程执行结束，激活父活动]";

	// ///////////////规则引擎异常/////////////////
	String OE_WF_RULE_001 = "OE_WF_RULE_001[变量没有赋值]";

	String OE_WF_RULE_002 = "OE_WF_RULE_002[语法错误]";

	String OE_WF_RULE_003 = "OE_WF_RULE_003[数组下标溢出]";

	String OE_WF_RULE_004 = "OE_WF_RULE_004[相关变量实例丢失]";

	String OE_WF_RULE_005 = "OE_WF_RULE_005[公式计算出错]";

	String OE_WF_RULE_006 = "OE_WF_RULE_006[缺少变量对象]";

	String OE_WF_RULE_007 = "OE_WF_RULE_007[读取变量值出错]";

	// ////////相关数据表单信息///////////
	String _OE_WF_REV_EXT_COLUMN_NAME = "name";// 扩展属性中的字段名
	String _OE_WF_REV_EXT_COLUMN_VALUEMODE = "valuemode";// 扩展属性中的字段名
	String _OE_WF_REV_EXT_COLUMN_VALUE = "value";// 扩展属性中的字段名

	String _OE_WF_REV_EXT_COLUMN_HIDDEN = "hidden";// 扩展属性中的字段名,标志隐蔽1为真

	String _OE_WF_REV_EXT_COLUMN_READONLY = "readonly";// 扩展属性中的字段名，标志只读 1为真

	String _OE_WF_REV_FORM_LIST = "list";
	String _OE_WF_REV_FORM_NUMBER = "number";
	String _OE_WF_REV_FORM_STRING = "string";
	String _OE_WF_REV_FORM_DYFORM = "dyform";
	String _OE_WF_REV_FORM_URL = "url";

}
