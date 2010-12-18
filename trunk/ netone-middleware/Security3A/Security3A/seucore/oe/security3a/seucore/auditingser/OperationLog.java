
package oe.security3a.seucore.auditingser;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import oe.frame.orm.util.IdServer;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操作日志记录
 * 
 * @author hls
 * 
 */
public class OperationLog {

	/**
	 * 将日志记录到数据库的Log实现。参见lo4j.propeties中配置。
	 */
	private static Log log = LogFactory.getLog(OperationLog.class);

	private static OnlineUserMgr olusermgr = new DefaultOnlineUserMgr();

	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 记录成功的操作。
	 * 
	 * @param request
	 * @param optrmsg
	 * @param remark
	 */
	public static void info(HttpServletRequest request, String optrmsg,
			String remark) {
		String msg = createLogMsg(request, optrmsg, remark, "success");
		if (msg != null) {
			log.info(msg);
		}
	}

	/**
	 * 记录失败的操作
	 * 
	 * @param request
	 * @param optrmsg
	 * @param remark
	 */
	public static void error(HttpServletRequest request, String optrmsg,
			String remark) {
		String msg = createLogMsg(request, optrmsg, remark, "error");
		if (msg != null) {
			log.error(msg);
		}
	}

	/**
	 * 构造插入的sql语句的值
	 * 
	 * sql: insert into
	 * ums_operation_log(logid,userid,operatetime,operationid,resultinfo,remark)
	 * values(seq_ums_optrlog.nextval,%m)
	 * 
	 * @param request
	 * @param optrmsg
	 * @param remark
	 * @param success
	 * @return
	 */
	private static String createLogMsg(HttpServletRequest request,
			String optrmsg, String remark, String success) {
		OnlineUser olser = olusermgr.getOnlineUser(request);
		String userid = null;
		if (olser == null) {
			userid = request.getRemoteHost() + "unknow user";
		} else {
			userid = olser.getBelongto() + "_" + olser.getLoginname();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("'" + IdServer.uuid() + "',");
		sb.append("'" + userid + "',");
		// sb.append("to_date('" + df.format(new Date())
		// + "','yyyy-mm-dd hh24:mi:ss'),");
		sb.append("'" + df.format(new Date()) + "',");
		sb.append(getsqlvalue(optrmsg) + ",");
		sb.append(getsqlvalue(success) + ",");
		sb.append(getsqlvalue(remark));
		return sb.toString();
	}

	private static String getsqlvalue(String str) {
		if (str == null) {
			return "null";
		} else {
			return "'" + str + "'";
		}
	}

}
