package oe.mid.netone.other;

import java.sql.Timestamp;

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
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param userName �û���(�û���ѯ��˭���˶�ʱ��
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return ʱ���ۼ�ֵ����λ���룩
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	/**
	 * �˶������ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param userName �û���(�û���ѯ��˭���˶�����
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return �˶������ۼ�ֵ����λ�ǹ��
	 */
	double  sportDistanceTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	/**
	 * �˶������������ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param userName �û���(�û���ѯ��˭���˶�����
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return �����������ۼ�ֵ����λ��ǧ����
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	
	/**
	 * �������˶�ʱ���ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return ʱ���ۼ�ֵ����λ���룩
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
	/**
	 * �������˶������ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return �˶������ۼ�ֵ����λ�ǹ��
	 */
	double  sportDistanceTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
	/**
	 * �������˶������������ۼ�
	 * @param from ��ʼʱ��(���ڲ�ѯ��ʱ����ʼ��)
	 * @param to ����ʱ��(���ڲ�ѯ��ʱ�������)
	 * @param sportType �˶�����( 01 ����·,02 �ǵǸ� 03 ���ܲ�)
	 * @param equipmentType �˶�ʱʹ�õ��豸���� (01�Ʋ��� 02 �Ǹ߱� 03 �켣��)
	 * @return �����������ۼ�ֵ����λ��ǧ����
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
}
