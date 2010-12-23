package oe.bi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bi��������ģ�����
 * 
 * @author chen.jia.xun
 * 
 */
public class BiEntry {
	private static Log _log = LogFactory.getLog(BiEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"bi_config.xml");

	public static Object fetchBi(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static void main(String[] args) {
		BiEntry.fetchBi("extract");
	}

}
