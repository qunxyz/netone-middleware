package oe.bi.datasource;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.etl.obj.ChoiceInfo;


/**
 * 
 * ��άҵ������ģ��DAO. ����Ŀ������XML�ĵ�
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface DataModelDao {
	/**
	 * ������άҵ������ģ��,�������TargetColumn�д���unitedRule,��ô������ͼ
	 * ��ͼ����������ΪDatasetId+_view_unitedId
	 * 
	 * @param dataModelObj
	 *            ����Դ����
	 */
	void create(DataModel dataModelObj) throws NullDataSetException;

	/**
	 * ���¶�άҵ������ģ��
	 * 
	 * @param dataModelObj
	 */
	void update(DataModel dataModelObj);

	/**
	 * ɾ����άҵ������ģ��
	 * 
	 * @param dataModelObj
	 */
	void drop(DataModel dataModelObj);

	/**
	 * ��ѯ
	 * 
	 * @return
	 */
	String[][] listAll(String condtion);

	/**
	 * װ��
	 * 
	 * @param id
	 * @return
	 */
	DataModel fetchDataModel(String formcode) throws UnableLoadDataModel;

}
