package oe.cav.bean.logic.tools;

import java.util.Map;

public interface DyObjFromDatabase {

	/**
	 * 根据ds从数据库中创建DyObj对象
	 * 
	 * @param ds(可直接用ormer来获得数据库资源)
	 * 
	 * 注: 对象中的formcode在内部自动生成
	 * @return
	 */
	DyObj[] parser(String ds);

	/**
	 * 根据ds从数据库,根据选定的表,创建DyObj对象
	 * 
	 * @param ds
	 *            可直接用ormer来获得数据库资源)
	 * @param table
	 *            选定的表
	 * 
	 * @return
	 */
	DyObj[] parser(String ds, String[] table);

	/**
	 * 根据ds从数据库,根据选定的表和表中的字段创建DyObj对象
	 * 
	 * @param ds
	 *            可直接用ormer来获得数据库资源)
	 * @param tableColumn
	 *            ,key为tablename, value=[]{columnid}
	 * 
	 * @param tableNameUUID
	 *            key为tablename,value=uuid
	 * @return
	 */
	DyObj[] parser(String ds, Map tableColumn);

}
