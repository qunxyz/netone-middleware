package oe.frame.bus.workflow.rule;

/**
 * SOA业务脚本功能接口<br>
 * <br>
 * 基于该接口在实现业务中,都是基于工作流的上下文环境.<br>
 * 需要实现4类应用 1:工作流,2:动态表单,3:bean处理,4:消息
 * 
 * <br>
 * 在这里为了提供效率,支持异步工作模式,
 * 写入可用事务处理,使用getInobj,配合n个setInobj(),最后submit()提交,这是个事务的过程.只有submit后
 * 写入的数据才生效到目标输入对象中. 在读的时候为了提高效率可以使用getOutobj
 * 
 * 
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface ScriptFunction {
	/**
	 * 所有的规则服务都是基于流程来进行的所有无论如何都必须要通过这个地方来与流程上下文关联
	 * 该方法一般情况下不是用户写脚本的时候去调用的,而是脚本引擎初始化的时候,自动根据程序上下文 来自动写入的
	 * 
	 * @param runtimeid
	 * @param workcode
	 */
	void init(String runtimeid, String workcode);

	/**
	 * 创建一个应用实例
	 * 
	 * @param beanname，业务对象ID
	 * @return bean对象的实例ID,后期给相关的bean写值都基于它
	 */
	String newInstance(String beanname);

	/**
	 * 启动应用(默认的异步模式)
	 * 
	 * @param id
	 *            业务对象ID
	 * @return 启动结果，如果为空表示正常启动成功，否则返回错误提示信息
	 */
	String run(String id);

	/**
	 * 对输入对象写值
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @param value
	 *            字段值
	 */
	void set(String id, String paramName, Object value);

	/**
	 * 根据业务对象ID,获得指定名字的字段值
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @return
	 */
	String get(String id, String paramname);

	/**
	 * 读值，长整型
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @return
	 */
	long getl(String id, String paramname);

	/**
	 * 读值，整型
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @return
	 */
	int getn(String id, String paramname);

	/**
	 * 读值，浮点型
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @return
	 */
	float getf(String id, String paramname);

	/**
	 * 读值，双精度型
	 * 
	 * @param id
	 *            业务对象ID
	 * @param paramname
	 *            字段ID
	 * @return
	 */
	double getd(String id, String paramname);

	/**
	 * 获得真实输入的对象,该方法是为了配合setIn, 这样给对象设置值不需要每次都做远程调度而提高性能
	 * 
	 * @param id
	 *            业务对象ID
	 * @return
	 */
	Object getInobj(String id);

	/**
	 * 写入真实输入对象的值
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务对象
	 */
	void setIn(String fieldId, Object value, Object obj);

	/**
	 * 获得输出对象,该方法是配合 getoutn 等相关的方式实现一个高效的读值操作
	 * 
	 * @param id
	 * @return
	 */
	Object getOutobj(String id);

	/**
	 * 读值，整型
	 * 
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务数据对象
	 * @return
	 */
	int getOutn(String fieldId, Object obj);

	/**
	 * 读值，浮点型
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务数据对象
	 * @return
	 */
	float getOutf(String fieldId, Object obj);

	/**
	 * 读值，双精度型
	 * 
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务数据对象
	 * @return
	 */
	double getOutd(String fieldId, Object obj);

	/**
	 * 读值，长整型
	 * 
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务数据对象
	 * @return
	 */
	long getOutl(String fieldId, Object obj);

	/**
	 * 读值，字符
	 * 
	 * 
	 * @param fieldId
	 *            字段ID
	 * @param obj
	 *            业务数据对象
	 * @return
	 */
	String getOut(String fieldId, Object obj);

	/**
	 * 提交业务对象,该方法对之前的
	 * 
	 * @param obj
	 */
	void submit(Object obj);

}
