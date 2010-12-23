package oe.bi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bi分析程序模块入口
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
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static void main(String[] args) {
		BiEntry.fetchBi("extract");
	}

}
