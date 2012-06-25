package oe.frame.bus.workflow.rule;

import oe.frame.bus.workflow.WfEntry;

/**
 * 规则引擎
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuleEngine {

	/**
	 * 判断业务逻辑
	 * 
	 * @param elogicExpress
	 *            String ：格式如 ?*?(?+?)
	 * @param values
	 *            List :值个数为elogicExpress中的？个数，并且值得先后选顺必须与其对应的？的位置一致
	 * @return boolean
	 */
	public boolean rule(String elogicExpress, String runtimeid, String workcode);

	/**
	 * 执行脚本处理
	 * 
	 * @param elogicExpress
	 * @param runtimeid,如果不在流程环境下,runtimeid可为空,同时如果使用rv相关的方法都返回0
	 * @return
	 */
	public String todo(String elogicExpress, String runtimeid, String workcode);
	
	/**
	 * 执行脚本处理
	 * 
	 * @param elogicExpress
	 * @return 原始對象
	 */
	public Object todo(String elogicExpress);
	
	/**
	 * 执行脚本处理
	 * 
	 * @param elogicExpress
	 * @param param 外部参数对象  这些对象必须是持久化对象
	 * @return 原始對象
	 */
	public Object todo(String elogicExpress,Object []param);

}
