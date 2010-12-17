package oe.teach.mid.buss;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * ���Oecstudent��̨ҵ�����ҵ��Ӧ���߼����<br>
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public interface OecStuDao {
	/**
	 * �б���������
	 * 
	 * @param request
	 * @return
	 */
	String listCondition(HttpServletRequest request);

	/**
	 * ͳ�����������ļ�¼����
	 * 
	 * @param queryinfo
	 *            SQL��Select��where������
	 * @return
	 */
	public int totalNum(String queryinfo);

	/**
	 * ��ѯ���������ļ�¼
	 * 
	 * @param queryinfo
	 *            SQL��Select��where������
	 * @return BussObj����
	 */
	public List query(String queryinfo, int form, int to);

	/**
	 * ����OecStudent
	 * 
	 * @param tobj
	 */
	public void create(OecStudent tobj);

	/**
	 * ��������
	 * 
	 * @param tobj
	 */
	public void creates(List<OecStudent> tobj);

	/**
	 * �޸�OecStudent
	 * 
	 * @param tobj
	 */
	public void update(OecStudent tobj);

	/**
	 * �����޸�
	 * 
	 * @param tobj
	 */
	public void update(List<OecStudent> tobj);

	/**
	 * ɾ��OecStudent
	 * 
	 * @param tobj
	 *            stuid
	 */
	public void delete(String tobj);

	/**
	 * ����ɾ��OecStudent
	 * 
	 * @param tobj
	 *            stuid����
	 */
	public void delete(String[] tobj);

	/**
	 * װ��OecStudent
	 * 
	 * @param id
	 * @return
	 */
	public OecStudent load(String id);
}
