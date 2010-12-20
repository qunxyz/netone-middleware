package oe.cav.bean.logic.tools;

import java.util.Map;

public interface DyObjFromDatabase {

	/**
	 * ����ds�����ݿ��д���DyObj����
	 * 
	 * @param ds(��ֱ����ormer��������ݿ���Դ)
	 * 
	 * ע: �����е�formcode���ڲ��Զ�����
	 * @return
	 */
	DyObj[] parser(String ds);

	/**
	 * ����ds�����ݿ�,����ѡ���ı�,����DyObj����
	 * 
	 * @param ds
	 *            ��ֱ����ormer��������ݿ���Դ)
	 * @param table
	 *            ѡ���ı�
	 * 
	 * @return
	 */
	DyObj[] parser(String ds, String[] table);

	/**
	 * ����ds�����ݿ�,����ѡ���ı�ͱ��е��ֶδ���DyObj����
	 * 
	 * @param ds
	 *            ��ֱ����ormer��������ݿ���Դ)
	 * @param tableColumn
	 *            ,keyΪtablename, value=[]{columnid}
	 * 
	 * @param tableNameUUID
	 *            keyΪtablename,value=uuid
	 * @return
	 */
	DyObj[] parser(String ds, Map tableColumn);

}
