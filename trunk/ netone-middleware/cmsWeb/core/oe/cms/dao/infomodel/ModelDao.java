package oe.cms.dao.infomodel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfomodel;

public interface ModelDao {

	public TCmsInfomodel userinfoModel(String userid, String modelx,
			HttpServletRequest request);

	boolean create(TCmsInfomodel model,String belongto);

	boolean update(TCmsInfomodel model);

	boolean delete(String id, String participant);

	boolean delete(String participant);

	boolean checkExist(String id);

	TCmsInfomodel view(String id);

	TCmsInfomodel viewByUser(String userid);

	// ��ʼ�����е�������Ϣ��Impl�е�����Map��
	void initview();

	// ��ʼ�����е�������Ϣ
	void initview(String level);

	// ��ʼ���̴������´����Ŀռ�
	void initNewCreateView();

	// ��ʼ���̴����������±��޸Ĺ��Ŀռ�
	void initNewModifyView();

	// �ھ��Ŀռ�ID
	TCmsInfomodel fetchWinner();

	// ����ģ��ID�������
	int fetchOrder(String level, String modelid);

	/**
	 * ����ռ�չ�ֽ���
	 * 
	 * @param level
	 *            ���뼶��Ŀǰ��:A��B��C��������
	 * @param from
	 *            �ӵڼ�����ʼ
	 * @param to
	 *            ���ڼ�������
	 * @return
	 */
	List richview(String level, int from, int to);

	/**
	 * ��ͨ�İ��մ���ʱ��˳����ʾ
	 * 
	 * @return
	 */
	List richViewByCreateTime();

	/**
	 * ��ͨ�İ��������޸�˳����ʾ
	 * 
	 * @return
	 */
	List richViewByModifyTime();

	/**
	 * ����ռ�����
	 * 
	 * @param level
	 * @return
	 */
	int totalRichview(String level);

	/**
	 * ֧�ֳ��룬ͶƱ֧��
	 * 
	 * @param index
	 *            �����ָ֧�ֶ����� ���еĶ���List�е�λ��
	 */
	void supportThis(String level, int index);

	/**
	 * �־û���Ϣ
	 * 
	 * @param level
	 */
	void serialRich(String level);

	List fetchList(String level);

	String fetchHitNum(String level, String modelid);

	//String fetchOrderIndex(String level, String modelid);

	/**
	 * Aˮƽ�ռ䣬��������ʱ��
	 */
	String fetchblogLevelARichSeriTime();

	/**
	 * Bˮƽ�ռ䣬��������ʱ��
	 */
	String fetchblogLevelBRichSeriTime();

	/**
	 * Cˮƽ�ռ䣬��������ʱ��
	 */
	String fetchblogLevelCRichSeriTime();

	/**
	 * ����Aˮƽ�ռ�ʱ��
	 */
	String fetchcomeAlevelTime();

	/**
	 * ����Bˮƽ�ռ�ʱ��
	 */
	String fetchcomeBlevelTime();

	/**
	 * �˵�Bˮƽ�ռ�ʱ��
	 */
	String fetchbackBlevelTime();

	/**
	 * �˵�Cˮƽ�ռ�ʱ��
	 */
	String fetchbackClevelTime();

	/**
	 * �ھ�����ʱ��
	 */
	String fetchWinnerTime();

	/**
	 * ����Aˮƽ�ռ�ĸ���
	 */
	String fetchcomeAlevelNum();

	/**
	 * �ڽ���Aˮƽ�ռ�ʱ����,��Ҫ��̭��B���ռ�ĵ����ռ����
	 */
	String fetchbackBlevelNum();

	/**
	 * ����Bˮƽ�ռ�ĸ���
	 */
	String fetchcomeBlevelNum();

	/**
	 * �ڽ���Bˮƽ�ռ�ʱ����,��Ҫ��̭��C���ռ�ĵ����ռ����
	 */
	String fetchbackClevelNum();

	List listAllModel();

}
