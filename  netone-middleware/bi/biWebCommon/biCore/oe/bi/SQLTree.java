package oe.bi;

/**
 * ά����������SQL�����չ�֣�
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
