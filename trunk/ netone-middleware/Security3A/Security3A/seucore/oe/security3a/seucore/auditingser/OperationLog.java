package oe.security3a.seucore.auditingser;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.obj.db.UmsOperationLog;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 操作日志记录
 * 
 * @author hls
 * 
 */
public class OperationLog {

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
			String remark, boolean rs) {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String reqip = request.getRemoteAddr();

		UmsOperationLog log = new UmsOperationLog();
		log.setOperatetime(new Date());
		log.setRemark(remark);
		if (rs) {
			log.setResultinfo("成功!");
		} else {
			log.setResultinfo("失败!");
		}
		log.setUserid(oluser.getUserid());
		log.setUserip(reqip);
		log.setOperationid(optrmsg);
		OptrLogDao optrLogDao = (OptrLogDao) SeudaoEntry.iv("optrLogDao");
		optrLogDao.create(log);

	}

}
