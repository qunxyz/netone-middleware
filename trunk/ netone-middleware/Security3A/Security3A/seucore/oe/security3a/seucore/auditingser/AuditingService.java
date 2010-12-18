package oe.security3a.seucore.auditingser;

import oe.frame.orm.Querist;
import oe.frame.orm.Serializer;
import oe.security3a.seucore.obj.UMSLog;


public interface AuditingService {
	/**
	 * ��־
	 * 
	 * @param log
	 */
	void log(UMSLog log);

	/**
	 * �����Դ����Ĳ�ѯ���
	 * 
	 * @return
	 */
	public Querist fetchQurist();

	/**
	 * ��ó־û����
	 * 
	 * @return
	 */
	public Serializer fetchSerializer();

}
