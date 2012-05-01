package oe.msg.bean.util;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志工具，结合log4j.properties的配置，这里登记的日志可以写入文件，也可以如数据库
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
	 *            要求格式为三级码#二级码#一级码
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
