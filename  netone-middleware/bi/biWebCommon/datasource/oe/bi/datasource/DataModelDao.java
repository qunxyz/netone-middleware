package oe.bi.datasource;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.etl.obj.ChoiceInfo;


/**
 * 
 * 多维业务数据模型DAO. 操作目标对象的XML文档
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DataModelDao {
	/**
	 * 创建多维业务数据模型,如果存在TargetColumn中存在unitedRule,那么创建视图
	 * 视图的命名规则为DatasetId+_view_unitedId
	 * 
	 * @param dataModelObj
	 *            数据源对象
	 */
	void create(DataModel dataModelObj) throws NullDataSetException;

	/**
	 * 更新多维业务数据模型
	 * 
	 * @param dataModelObj
	 */
	void update(DataModel dataModelObj);

	/**
	 * 删除多维业务数据模型
	 * 
	 * @param dataModelObj
	 */
	void drop(DataModel dataModelObj);

	/**
	 * 查询
	 * 
	 * @return
	 */
	String[][] listAll(String condtion);

	/**
	 * 装载
	 * 
	 * @param id
	 * @return
	 */
	DataModel fetchDataModel(String formcode) throws UnableLoadDataModel;

}
