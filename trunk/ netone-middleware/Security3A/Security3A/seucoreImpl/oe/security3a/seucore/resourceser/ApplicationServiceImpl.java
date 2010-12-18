package oe.security3a.seucore.resourceser;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.resourceser.ApplicationDao;
import oe.security3a.seucore.resourceser.ApplicationService;

/**
 * 应用系统服务接口实现
 * @author ni.he.qing
 *
 */
public class ApplicationServiceImpl implements ApplicationService {
	
	/**
	 * 获得系统对象dao
	 */
	public ApplicationDao fetchDao() {
		return (ApplicationDao) SeudaoEntry.iv("applicationDao");
	}

}
