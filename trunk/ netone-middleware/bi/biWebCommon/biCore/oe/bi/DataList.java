package oe.bi;

/**
 * ά���������������ļ��е������б���չ�֣�
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
