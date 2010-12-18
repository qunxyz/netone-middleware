package oe.security3a.seupublic.authentication;

import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;



/**
 * 策略执行点控制中心
 * @author chen.jia.xun
 * 
 */
public interface PEPConsole {

	/**
	 * 访问控制中心
	 * 
	 * @param request
	 *            文档格式可参考Resource/sample/xacml/requst中的xml文档
	 * @return response 文挡格式可参考Resource/sample/xacml/response中的文档
	 * 
	 * 该接口是控制台程序,它根据policy_config.xml中的配置信息,来决定不同的访问 控制采用不同的类来实现,
	 * 而这些类目前默认是从Spring配置中获得 seupub_cfg.xml.
	 * 
	 * 该控制台要提高效率,可以使用上缓存
	 * 
	 */
	ResponseCtx evaluate(String code, RequestCtx request) throws Exception;

}
