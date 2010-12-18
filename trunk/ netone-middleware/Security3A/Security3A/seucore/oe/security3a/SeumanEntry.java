package oe.security3a;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ��ȫ���������<br>
 * 
 * @author chen.jia.xun
 * 
 */
public class SeumanEntry {
	private static Log _log = LogFactory.getLog(SeumanEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"seuman_cfg.xml");

	public static Object iv(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		return appContext.getBean(functionname);
	}

}
