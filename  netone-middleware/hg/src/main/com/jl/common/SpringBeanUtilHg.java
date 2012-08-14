package com.jl.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringBean ������
 * <p>
 * <li></li>
 * </p>
 * <description></description>
 * 
 * @author zhang.chao.yi
 * @version 1.0
 * @date 2008-1-28
 */
public class SpringBeanUtilHg {

	// ----------------------------------------------------------Class Methods

	private static SpringBeanUtilHg beanUtil = null;

	private static BeanFactory factory = null;

	private static final Logger LOG = Logger.getLogger(SpringBeanUtilHg.class);

	public static SpringBeanUtilHg getInstance() {
		if (beanUtil == null) {
			beanUtil = new SpringBeanUtilHg();
		}
		return beanUtil;
	}

	// -------------------------------------------------------Private Variables

	private SpringBeanUtilHg() {
		if (factory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:application-hg.xml");
			factory = (BeanFactory) context;
		}
	}

	/**
	 * ��ȡBean����
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		return factory.getBean(name);
	}

}
