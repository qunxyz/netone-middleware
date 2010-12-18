package oe.security3a.seucore.resourceser;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.resourceser.OperationDao;
import oe.security3a.seucore.resourceser.OperationService;

/**
 * 操作服务接口实现
 * @author ni.he.qing
 *
 */
public class OperationServiceImpl implements OperationService {

	/**
	 * 获得操作对象dao
	 */
	public OperationDao fetchDao() {
		return (OperationDao) SeudaoEntry.iv("operationDao");
	}

}
