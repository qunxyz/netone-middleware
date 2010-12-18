package oe.security3a.seucore.auditingser;

import java.util.List;
import java.util.Map;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.auditingser.OptrLogDao;
import oe.security3a.seucore.auditingser.OptrLogService;
import oe.security3a.seucore.obj.db.UmsOperationLog;



public class OptrLogServiceImpl implements OptrLogService{
	
	private OptrLogDao dao = null ;
	
	public OptrLogServiceImpl(){
		dao = (OptrLogDao) SeudaoEntry.iv("optrLogDao");
	}
	
	public List searchOptrLog(UmsOperationLog obj , Map comparison , Integer from, Integer to) throws Exception {
		List list = dao.queryObjects(obj, comparison,from.intValue() ,to.intValue());
		return list ;
	}

	public int searchOptrLogCount(UmsOperationLog obj , Map comparison ) throws Exception {
		long i = dao.queryObjectsNumber(obj, comparison);
		return (int)i;
	}

	public void writeOptrLog(UmsOperationLog optrlog) throws Exception {
		dao.create(optrlog);
	}

}
