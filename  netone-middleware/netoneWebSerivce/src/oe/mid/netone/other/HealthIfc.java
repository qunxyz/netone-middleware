package oe.mid.netone.other;

import java.sql.Timestamp;
import java.util.List;

/**
 * �������ݽӿ�
 * 
 * @author jiaxun.chen (Robanco)<br> 
 *    email:oesee@139.com <br>
 *    tel:86+18060473012<br>
 *    my blog:http://blog.sina.com.cn/robanco
 *
 */

public interface HealthIfc {
	/**
	 * �˶�ʱ���ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��) ���ʱ��Ϊ�շ�����������
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)���ʱ��Ϊ�շ�����������
	 * @param userName �û���(�û���ѯ��˭���˶�ʱ�䣬���usernameΪ�ձ�ʾ�����û�
	 * @return ʱ���ۼ�ֵ����λ���룩
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String userName);
	/**
	 * �˶������������ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)���ʱ��Ϊ�շ�����������
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)���ʱ��Ϊ�շ�����������
	 * @param userName �û���(�û���ѯ��˭���˶����룬���usernameΪ�ձ�ʾ�����û�

	 * @return �����������ۼ�ֵ����λ��ǧ����
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * �Ǹߵĸ߶��ۼ�
	 * @param from  ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)���ʱ��Ϊ�շ�����������
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)���ʱ��Ϊ�շ�����������
	 * @param userName�����usernameΪ�ձ�ʾ�����û�
	 * @return
	 */
	List<HeightInfo> heightTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * �켣���ۻ�
	 * @param from��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)���ʱ��Ϊ�շ�����������
	 * @param to����ʱ��(���ڲ�ѯ��ʱ�������)���ʱ��Ϊ�շ�����������
	 * @param userName�����usernameΪ�ձ�ʾ�����û�
	 * @return
	 */
	List<TrackInfo> trackTotal(Timestamp from,Timestamp to,String userName);
	
	
	/**
	 * �Ʋ����ۻ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)���ʱ��Ϊ�շ�����������
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)���ʱ��Ϊ�շ�����������
	 * @param userName�����usernameΪ�ձ�ʾ�����û�
	 * @return
	 */
	long stepTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * �˶�����
	 * @param userName �����usernameΪ�ձ�ʾ�����û�
	 * @param toptype 01 ��������������,02�����˶������������ 
	 * @return
	 */
	List<SportInfo> sportTop(String userName,String toptype);
	
	/**
	 * ����û� 
	 * @param userid
	 * @return
	 */
	boolean syncAddUser(String userid);
	/**
	 * �����û�
	 * @param userid
	 * @return
	 */
	boolean syncFobitUser(String userid);


}
