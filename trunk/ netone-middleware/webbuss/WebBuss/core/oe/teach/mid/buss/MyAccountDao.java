package oe.teach.mid.buss;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * ��Ժ�̨ҵ�����ҵ��Ӧ���߼����<br>
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public interface MyAccountDao {
	/**
	 * �б���������
	 * 
	 * @param request
	 * @return
	 */
	String makeCondition(HttpServletRequest request);

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
	public List query(String queryinfo, int from, int to);

	/**
	 * ����MyAccount
	 * 
	 * @param tobj
	 */
	public void create(MyAccount tobj);

	/**
	 * �޸�MyAccount
	 * 
	 * @param tobj
	 */
	public void update(MyAccount tobj);

	/**
	 * ɾ��MyAccount
	 * 
	 * @param tobj
	 *            stuid
	 */
	public void delete(String tobj);

	/**
	 * ����ɾ��MyAccount
	 * 
	 * @param tobj
	 *            stuid����
	 */
	public void delete(String[] tobj);

	/**
	 * װ��MyAccount
	 * 
	 * @param id
	 * @return
	 */
	public MyAccount load(String id);
}
