package oe.security3a;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 安全管理功能入口<br>
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
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		return appContext.getBean(functionname);
	}

}
