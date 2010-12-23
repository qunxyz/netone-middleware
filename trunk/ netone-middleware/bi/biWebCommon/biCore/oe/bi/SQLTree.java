package oe.bi;

/**
 * 维度树（基于SQL语句来展现）
 * 
 * @author chen.jia.xun
 * 
 */
public interface SQLTree {

	String fetchTreeInfo(String datamodelid, String columnid, String node,
			String sqltreeinfo, int level);

	String[] fetchElement(String datamodelid, String columnid, String node,
			String sqltreeinfo, int level);
}
