package oe.security3a.seucore.auditingser;

import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsOperationLog;



public interface OptrLogService {
	
	/**
	 * 操作日志查询
	 * @param obj
	 * @param condition
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public List searchOptrLog(UmsOperationLog obj , Map condition  , Integer from , Integer to) throws Exception  ;
	
	/**
	 * 查询操作日志的数量
	 * @param obj
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int searchOptrLogCount(UmsOperationLog obj , Map condition )  throws Exception  ;
	
	/**
	 * 操作日志入库
	 * @param optrlog
	 * @throws Exception
	 */
	public void writeOptrLog( UmsOperationLog optrlog ) throws Exception  ;
	
}
