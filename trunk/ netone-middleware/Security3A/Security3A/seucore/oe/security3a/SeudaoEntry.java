package oe.security3a;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 安全Dao入口<br>
 * 负责调度安全系统中所有的持久化操作,注:安全中所有的持久化(包括:db操作,ladp操作,文档xml....)操作都被封装在dao类中
 * 
 * @author chen.jia.xun
 * 
 */
public class SeudaoEntry {
	private static Log _log = LogFactory.getLog(SeudaoEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"seudao_cfg.xml");

	public static Object iv(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		
		return appContext.getBean(functionname);
	}
}
