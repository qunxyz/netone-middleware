package oe.frame.bus.workflow.rule;

import oe.mid.soa.bean.SoaBean;

public interface BeanScriptFunction extends ScriptFunction {

	/**
	 * 启动应用(默认的异步模式)
	 * 
	 * @param id
	 *            业务对象ID
	 * @return 启动结果，对象ID
	 */
	String run(String id);

	/**
	 * 启动应用(默认的异步模式)
	 * 
	 * @param id
	 *            业务对象ID
	 * @return 启动结果，对象ID
	 */
	SoaBean runSyncById(String id);

	/**
	 * 启动应用(默认的异步模式)
	 * 
	 * @param obj
	 *            业务对象ID
	 * @return 启动结果，对象ID
	 */
	SoaBean runSyncByObj(Object obj);

}
