package oe.security3a.seucore.auditingser;

import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsOperationLog;



public interface OptrLogService {
	
	/**
	 * ������־��ѯ
	 * @param obj
	 * @param condition
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public List searchOptrLog(UmsOperationLog obj , Map condition  , Integer from , Integer to) throws Exception  ;
	
	/**
	 * ��ѯ������־������
	 * @param obj
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int searchOptrLogCount(UmsOperationLog obj , Map condition )  throws Exception  ;
	
	/**
	 * ������־���
	 * @param optrlog
	 * @throws Exception
	 */
	public void writeOptrLog( UmsOperationLog optrlog ) throws Exception  ;
	
}
