package oe.frame.bus.workflow.rule;

/**
 * 消息脚本服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface EnvScriptFunction {

	/**
	 * 从当前环境中获得配置值
	 * 
	 * @param key
	 * @return
	 */
	String value(String key);

	/**
	 * 从制定的地址中获得配置值
	 * 
	 * @param url
	 * @param key
	 * @return
	 */
	String value(String url, String key);

	/**
	 * 调度Web地址
	 * 
	 * @param url
	 * @return
	 */
	String invokeUrl(String url);

	/**
	 * 在相对路径下写文件
	 * 
	 * @param filename
	 * @param data
	 */
	void writeEnvFile(String filename, String data);

	/**
	 * 在相对路径中读文件信息
	 * 
	 * @param filename
	 * @return
	 */
	String readEnvFile(String filename);

	/**
	 * 调度执行外部脚本
	 * 
	 * @param uri： 格式, scripttype@url。 比如: tcl:/ext/telnet.tcl, awk:/exe/data.awk
	 * @param param 传递给脚本的参数
	 * @return
	 */
	String exeOuterScript(String uri, String[] param);

}
