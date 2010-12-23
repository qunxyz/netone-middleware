package oe.bi.datasource.bus;

import java.util.List;

import oe.bi.datasource.obj.Datasource;


/**
 * ����Դ���� ��������Դ�Ĳ���,���ӵ����������Դ. ��֧�ֵ�����Դ: 1)���ݿ� PRI(High) 2)XML 3)EXCEL
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface ExtractDataset {
	/**
	 * �������(�������Ϊnull��ô��Ĭ������Դ�л������ )
	 * 
	 * @param tBiDatasource
	 * @return
	 */
	boolean checkConnection(Datasource tBiDatasource);

	/**
	 * ������ݼ�
	 * 
	 * @param tBiDatasource
	 * @return List �б����ΪDataSetObj
	 */
	List fetchDataSet(Datasource tBiDatasource);
	/**
	 * ������ݱ�����
	 * @param tBiDatasource
	 * @return
	 */
	String[] fetchDataSetKey(Datasource tBiDatasource);
	/**
	 * ��������Դ�ͱ�������ֶ�
	 * @param tBiDatasource
	 * @param datasetKey
	 * @return
	 */
	String[] fetchDataSetColumnKey(Datasource tBiDatasource, String datasetKey);

}
