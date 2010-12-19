package oe.frame.bus.workflow;

import oe.midware.workflow.service.soatools.ActiveBindDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 工作流功能入口
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class WfEntry {

	private static Log _log = LogFactory.getLog(WfEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"wf_config.xml");

	public static Object fetchBean(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static void main(String[] args) {
	}

}
