package oe.security3a.seucore.resourceser;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.resourceser.OperationDao;
import oe.security3a.seucore.resourceser.OperationService;

/**
 * ��������ӿ�ʵ��
 * @author ni.he.qing
 *
 */
public class OperationServiceImpl implements OperationService {

	/**
	 * ��ò�������dao
	 */
	public OperationDao fetchDao() {
		return (OperationDao) SeudaoEntry.iv("operationDao");
	}

}
