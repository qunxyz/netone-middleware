package oe.security3a.seucore.resourceser;

import java.util.List;

import oe.security3a.seucore.obj.ProtectedObject;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface ProtectedObjectManager {

	UmsApplication fetchApplication(UmsProtectedobject ope);

	/**
	 * 获得子保护对象
	 * 
	 * @param group
	 * @return
	 */
	public List subProtectedObject(UmsProtectedobject protectdObject);

	/**
	 * 批量创建保护对象
	 * 
	 * @param list
	 *            保护对象数组
	 * @return 2维数组String[n][0] 保护对象ID,String[n][1]创建结果[成功,失败,覆盖]
	 */
	public String[][] createProtectedObjects(List list);

	/**
	 * 批量删除对象
	 * 
	 * @param list
	 *            保护对象数组
	 * @return 2维数组String[n][0] 保护对象ID,String[n][1]删除结果[成功,失败]
	 */
	public String[][] delProtectedObjects(List list);

}
