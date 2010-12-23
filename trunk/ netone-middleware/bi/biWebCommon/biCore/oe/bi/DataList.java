package oe.bi;

/**
 * 维度树（基于配置文件中的数据列表来展现）
 * 
 * @author chen.jia.xun
 * 
 */
public interface DataList {

	String fetchDataInfo(String datamodelid, String columnid, String datainfo,
			int level);

	String[] fetchDataElement(String datamodelid, String columnid,
			String datainfo, int level);

}
