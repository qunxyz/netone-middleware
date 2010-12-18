package oe.security3a;

import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.PEPConsole;

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
public class SeupubEntry {
	private static Log _log = LogFactory.getLog(SeupubEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"seupub_cfg.xml");

	public static PDP iv(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		return (PDP) appContext.getBean(functionname);
	}

	public static PEPConsole iv() {

		return (PEPConsole) appContext.getBean("");
	}
}
