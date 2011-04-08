package oe.cms.dao.infocell;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfocell;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public interface InfoCellDao {
	/**
	 * Ԥ����,��Ҫ���ں���Դ������������ҳ��չ������
	 * 
	 * @param cellid
	 * @return
	 */
	TCmsInfocell viewPreOperation(String cellid, HttpServletRequest req);
	/**
	 * ��ext�õ�
	 * 
	 * @param cellid
	 * @return
	 */
	TCmsInfocell view4ext(String cellid, HttpServletRequest req);


	/**
	 * ���ݲ�ѯ����ѶԪSQLȡ�� ��ѶԪ�б�
	 * 
	 * @return
	 */
	List searchCellList(String sql, String participant, int from, int to);

	/**
	 * ���ݲ�ѯ����ѶԪSQL��ѯ ȡ�� ��ѶԪ�б������
	 * 
	 * @param name
	 * @return
	 */
	int searchCellNum(String sql, String participant);

	/**
	 * ��ȡ��ѶԪ�б�
	 * 
	 * @return
	 */
	List getCellToolList(String belongto, String participant);

	/**
	 * ��ȡͼ���б�
	 * 
	 * @return
	 */
	public List getChartList(String[][] chartStr);

	public boolean create(TCmsInfocell cell, String type, String bodyinfo);

	public boolean update(TCmsInfocell cell, String bodyinfo);

	public TCmsInfocell view(String cellid);

	public UmsProtectedobject viewRS(String cellid);

	/**
	 * ����cellid ������Դ2<br>
	 * �������ģ�洴��,��Ӧ���ƴ�����ҳ�Ҳ���������,��Ҫ����Դ���ҵ�����,����loadResourceByNatural API���в���
	 * 
	 * @author don (cai.you.dun) create by 2009-5-15
	 * @param cellid
	 * @return
	 */
	public UmsProtectedobject viewRS2(String cellid);

	public boolean delete(String id, String participant);

	public List queryByType(String userid, String cellTypes);

}
