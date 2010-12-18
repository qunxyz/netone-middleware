package oe.security3a;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ��ȫDao���<br>
 * ������Ȱ�ȫϵͳ�����еĳ־û�����,ע:��ȫ�����еĳ־û�(����:db����,ladp����,�ĵ�xml....)����������װ��dao����
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
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		
		return appContext.getBean(functionname);
	}
}
