package oe.frame.bus.workflow.rule;

/**
 * 消息脚本服务. 消息发送者采用默认的指定帐户来发送，所以在API中部涉及到发送者
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface MsgScriptFunction {
	/**
	 * 初始化
	 * 
	 * @param runtimeid
	 * @param workcode
	 */
	void init(String runtimeid, String workcode);

	/**
	 * 发送消息给指定地址
	 * 
	 * @param aimmail
	 *            接收者
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件(multi) 格式为 naturalname=title,naturalname=title
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 */
	void toMail(String aimmail, String title, String body, String append,
			String buss);

	/**
	 * 发送消息给人员
	 * 
	 * 
	 * @param belongto
	 *            用户隶属
	 * @param particiapnt
	 *            接收者
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件(multi) 格式为 naturalname=title,naturalname=title
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 */
	void toMen(String code, String particiapnt, String title, String body,
			String append, String buss);

	/**
	 * 发送消息给角色
	 * 
	 * @param sender
	 *            发送者
	 * @param rolename
	 *            角色名
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件 格式为 naturalname=title,naturalname=title
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 * 
	 */
	void toRole(String code, String rolename, String title, String body,
			String append, String buss);

	/**
	 * 发送消息给组织
	 * 
	 * @param sender
	 *            发送者
	 * @param groupname
	 *            角色名
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件 格式为 naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 */
	void toDept(String code, String deptname, String title, String body,
			String append, String buss);

	/**
	 * 发送消息给第一分组
	 * 
	 * @param code
	 *            发送编码
	 * @param groupname
	 *            组名
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件 格式为 naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 */
	void toGroupFirst(String code, String groupname, String title, String body,
			String append, String buss);

	/**
	 * 发送消息给第二分组
	 * 
	 * @param code
	 *            发送码
	 * @param groupname
	 *            角色名
	 * @param title
	 *            消息标题
	 * @param body
	 *            消息内容
	 * @param append
	 *            附件 格式为 naturalname=title,naturalname=title
	 * 
	 * @param buss
	 *            业务入口(multi) 格式为 url=title,url=title
	 */
	void toGroupSecond(String code, String groupname, String title,
			String body, String append, String buss);

}
