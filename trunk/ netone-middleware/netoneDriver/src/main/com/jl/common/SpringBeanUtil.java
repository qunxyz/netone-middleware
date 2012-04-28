package com.jl.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringBean 工具类
 * <p>
 * <li></li>
 * </p>
 * <description></description>
 * 
 * @author zhang.chao.yi
 * @version 1.0
 * @date 2008-1-28
 */
public class SpringBeanUtil {

	// ----------------------------------------------------------Class Methods

	private static SpringBeanUtil beanUtil = null;
	
	

	private static BeanFactory factory = null;

	private static final Logger LOG = Logger.getLogger(SpringBeanUtil.class);

	public static SpringBeanUtil getInstance() {
		if (beanUtil == null) {
			beanUtil = new SpringBeanUtil();
		}
		return beanUtil;
	}

	// -------------------------------------------------------Private Variables

	private SpringBeanUtil() {
		if (factory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:application.xml");
			factory = (BeanFactory) context;
		}
	}

	/**
	 * 获取Bean对象
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		return factory.getBean(name);
	}

}
