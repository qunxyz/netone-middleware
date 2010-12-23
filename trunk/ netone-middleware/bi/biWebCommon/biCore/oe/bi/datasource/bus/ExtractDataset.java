package oe.bi.datasource.bus;

import java.util.List;

import oe.bi.datasource.obj.Datasource;


/**
 * 数据源连接 根据数据源的参数,连接到具体的数据源. 可支持的数据源: 1)数据库 PRI(High) 2)XML 3)EXCEL
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface ExtractDataset {
	/**
	 * 检查连接(如果参数为null那么从默认数据源中获得数据 )
	 * 
	 * @param tBiDatasource
	 * @return
	 */
	boolean checkConnection(Datasource tBiDatasource);

	/**
	 * 获得数据集
	 * 
	 * @param tBiDatasource
	 * @return List 中保存的为DataSetObj
	 */
	List fetchDataSet(Datasource tBiDatasource);
	/**
	 * 获得数据表数组
	 * @param tBiDatasource
	 * @return
	 */
	String[] fetchDataSetKey(Datasource tBiDatasource);
	/**
	 * 根据数据源和表名获得字段
	 * @param tBiDatasource
	 * @param datasetKey
	 * @return
	 */
	String[] fetchDataSetColumnKey(Datasource tBiDatasource, String datasetKey);

}
