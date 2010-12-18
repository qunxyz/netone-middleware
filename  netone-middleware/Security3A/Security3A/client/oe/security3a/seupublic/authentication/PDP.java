package oe.security3a.seupublic.authentication;

import java.rmi.Remote;

/**
 * 该接口中是针对不同的访问控制主题,所实现的驱动鉴权类. 目前我们有实现 认证,鉴权,时效鉴权,获取资源
 * 
 * @author chen.jia.xun
 * 
 */
public interface PDP extends PEPConsole, Remote {

}
