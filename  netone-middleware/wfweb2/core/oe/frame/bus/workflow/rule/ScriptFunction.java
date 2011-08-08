package oe.frame.bus.workflow.rule;

/**
 * SOAҵ��ű����ܽӿ�<br>
 * <br>
 * ���ڸýӿ���ʵ��ҵ����,���ǻ��ڹ������������Ļ���.<br>
 * ��Ҫʵ��4��Ӧ�� 1:������,2:��̬��,3:bean����,4:��Ϣ
 * 
 * <br>
 * ������Ϊ���ṩЧ��,֧���첽����ģʽ,
 * д�����������,ʹ��getInobj,���n��setInobj(),���submit()�ύ,���Ǹ�����Ĺ���.ֻ��submit��
 * д������ݲ���Ч��Ŀ�����������. �ڶ���ʱ��Ϊ�����Ч�ʿ���ʹ��getOutobj
 * 
 * 
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface ScriptFunction {
	/**
	 * ���еĹ�������ǻ������������е�����������ζ�����Ҫͨ������ط��������������Ĺ���
	 * �÷���һ������²����û�д�ű���ʱ��ȥ���õ�,���ǽű������ʼ����ʱ��,�Զ����ݳ��������� ���Զ�д���
	 * 
	 * @param runtimeid
	 * @param workcode
	 */
	void init(String runtimeid, String workcode);

	/**
	 * ����һ��Ӧ��ʵ��
	 * 
	 * @param beanname��ҵ�����ID
	 * @return bean�����ʵ��ID,���ڸ���ص�beanдֵ��������
	 */
	String newInstance(String beanname);

	/**
	 * ����Ӧ��(Ĭ�ϵ��첽ģʽ)
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @return ������������Ϊ�ձ�ʾ���������ɹ������򷵻ش�����ʾ��Ϣ
	 */
	String run(String id);

	/**
	 * ���������дֵ
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @param value
	 *            �ֶ�ֵ
	 */
	void set(String id, String paramName, Object value);

	/**
	 * ����ҵ�����ID,���ָ�����ֵ��ֶ�ֵ
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @return
	 */
	String get(String id, String paramname);

	/**
	 * ��ֵ��������
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @return
	 */
	long getl(String id, String paramname);

	/**
	 * ��ֵ������
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @return
	 */
	int getn(String id, String paramname);

	/**
	 * ��ֵ��������
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @return
	 */
	float getf(String id, String paramname);

	/**
	 * ��ֵ��˫������
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @param paramname
	 *            �ֶ�ID
	 * @return
	 */
	double getd(String id, String paramname);

	/**
	 * �����ʵ����Ķ���,�÷�����Ϊ�����setIn, ��������������ֵ����Ҫÿ�ζ���Զ�̵��ȶ��������
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @return
	 */
	Object getInobj(String id);

	/**
	 * д����ʵ��������ֵ
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����
	 */
	void setIn(String fieldId, Object value, Object obj);

	/**
	 * ����������,�÷�������� getoutn ����صķ�ʽʵ��һ����Ч�Ķ�ֵ����
	 * 
	 * @param id
	 * @return
	 */
	Object getOutobj(String id);

	/**
	 * ��ֵ������
	 * 
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����ݶ���
	 * @return
	 */
	int getOutn(String fieldId, Object obj);

	/**
	 * ��ֵ��������
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����ݶ���
	 * @return
	 */
	float getOutf(String fieldId, Object obj);

	/**
	 * ��ֵ��˫������
	 * 
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����ݶ���
	 * @return
	 */
	double getOutd(String fieldId, Object obj);

	/**
	 * ��ֵ��������
	 * 
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����ݶ���
	 * @return
	 */
	long getOutl(String fieldId, Object obj);

	/**
	 * ��ֵ���ַ�
	 * 
	 * 
	 * @param fieldId
	 *            �ֶ�ID
	 * @param obj
	 *            ҵ�����ݶ���
	 * @return
	 */
	String getOut(String fieldId, Object obj);

	/**
	 * �ύҵ�����,�÷�����֮ǰ��
	 * 
	 * @param obj
	 */
	void submit(Object obj);

}
