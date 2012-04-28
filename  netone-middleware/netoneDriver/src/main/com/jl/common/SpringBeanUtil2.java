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
public class SpringBeanUtil2 {

	// ----------------------------------------------------------Class Methods

	private static SpringBeanUtil2 beanUtil = null;

	private static BeanFactory factory = null;

	private static final Logger LOG = Logger.getLogger(SpringBeanUtil2.class);

	public static SpringBeanUtil2 getInstance() {
		if (beanUtil == null) {
			beanUtil = new SpringBeanUtil2();
		}
		return beanUtil;
	}

	public static SpringBeanUtil2 getInstance(String xmlName) {
		if (beanUtil == null) {
			beanUtil = new SpringBeanUtil2(xmlName);
		}
		return beanUtil;
	}

	// -------------------------------------------------------Private Variables

	private SpringBeanUtil2() {
		if (factory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:application.xml");
			factory = (BeanFactory) context;
		}
	}

	private SpringBeanUtil2(String xmlName) {
		if (factory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:" + xmlName);
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
