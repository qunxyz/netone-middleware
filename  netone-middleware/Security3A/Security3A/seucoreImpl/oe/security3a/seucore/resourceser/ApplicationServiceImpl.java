package oe.security3a.seucore.resourceser;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.resourceser.ApplicationDao;
import oe.security3a.seucore.resourceser.ApplicationService;

/**
 * Ӧ��ϵͳ����ӿ�ʵ��
 * @author ni.he.qing
 *
 */
public class ApplicationServiceImpl implements ApplicationService {
	
	/**
	 * ���ϵͳ����dao
	 */
	public ApplicationDao fetchDao() {
		return (ApplicationDao) SeudaoEntry.iv("applicationDao");
	}

}
