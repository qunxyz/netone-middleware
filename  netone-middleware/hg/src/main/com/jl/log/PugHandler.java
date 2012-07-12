/**
 * 
 */
package com.jl.log;

import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * 用户操作痕迹拦截监听 暂时没用
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */

public class PugHandler implements MethodBeforeAdvice {

	public void before(Method method, Object[] objects, Object object)
			throws Throwable {
		System.out.println("logging before Class: "
				+ object.getClass().getName());
		System.out.println("logging before Method: " + method.getName());
		System.out.println("logging before Object: " + object);
	}
}
