package oe.msg.bean.util;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��־���ߣ����log4j.properties�����ã�����Ǽǵ���־����д���ļ���Ҳ���������ݿ�
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class PLog {

	private static Log log = LogFactory.getLog(PLog.class);

	/**
	 * 
	 * @param code
	 *            Ҫ���ʽΪ������#������#һ����
	 * @param note
	 * @return
	 */
	public static String log(String code, String note) {

		//String toLog = code + "','" + note + "'";
	    String toLog = code + "','" + note + 
	      "'";
		log.info(toLog);

		return null;
	}

	public static void main(String[] args) {
		PLog.log("111111111111", "sdfdsfdsf");
	}
}
