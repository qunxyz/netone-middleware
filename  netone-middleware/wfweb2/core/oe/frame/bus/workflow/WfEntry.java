package oe.frame.bus.workflow;

import oe.midware.workflow.service.soatools.ActiveBindDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * �������������
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
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static void main(String[] args) {
	}

}
