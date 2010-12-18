package oe.security3a.seucore.auditingser;

import oe.frame.orm.Querist;
import oe.frame.orm.Serializer;
import oe.security3a.seucore.obj.UMSLog;


public interface AuditingService {
	/**
	 * 日志
	 * 
	 * @param log
	 */
	void log(UMSLog log);

	/**
	 * 获得资源对象的查询句柄
	 * 
	 * @return
	 */
	public Querist fetchQurist();

	/**
	 * 获得持久化句柄
	 * 
	 * @return
	 */
	public Serializer fetchSerializer();

}
