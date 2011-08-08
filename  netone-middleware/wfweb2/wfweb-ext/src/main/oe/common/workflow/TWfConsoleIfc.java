package oe.common.workflow;

import java.util.List;
import java.util.Map;

/**
 * DRP������Ӧ�ýӿ� <br>
 * �ýӿ���Ϊһ�����������ڣ�Ϊ��֧��DRPӦ���ⲿ�����Ĺ������������<br>
 * 
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface TWfConsoleIfc {

	String OPE_TIP_SUCCESS = "SUCCESS:";
	String OPE_TIP_ERROR = "ERROR:";

	// ҵ�����̲ο���ر�������
	String _DEFAULT_REV_KEY_CUSTOMER = "customer"; // ������
	String _DEFAULT_REV_KEY_BUSSID = "bussid"; // ҵ��ID
	String _DEFAULT_REV_KEY_BUSSURL = "bussurl"; // ҵ���ַ
	String _DEFAULT_REV_KEY_BUSSTYPE = "busstype"; // ҵ��ģʽ
	String _DEFAULT_REV_KEY_BUSSTIP = "busstip"; // ҵ����ʾ
	String _DEFAULT_REV_KEY_STATUS = "status";// ���̻��ڣ��ñ����ǹ������ڲ����Ʊ�ʾ��ǰ��ҵ�񻷽�ֵ
	String _DEFAULT_REV_KEY_WORKCODE = "workcode";
	String _DEFAULT_REV_KEY_BUSSSTATUS = "bussstatus";// ҵ��״̬�����ֶα�ʾ���̵Ļ��ڼ���

	/**
	 * ������п����д��������������Ϣ
	 * 
	 * @return key=processid value=WorkflowProcess object
	 */
	Map allAvailableProcess();

	// ��0��1��2��3��4��6

	/**
	 * ���ݵ�ǰ�û����ش�������
	 * 
	 * @param clientId
	 * @return
	 * 
	 */
	List<TWfWorklistExt> worklist(String clientId) throws Exception;

	/**
	 * ��������ʵ��
	 * 
	 * @param processid
	 *            ����id
	 * @param clientId
	 *            ����������
	 * @param busstype
	 *            ҵ�������ҵ������
	 * @param bussid
	 *            ҵ���������id
	 * @param bussurl
	 *            ҵ������������ʵ�ַ
	 * @return
	 * @throws Exception
	 * 
	 * ����˵�������̴���ʵ��ʱ�����뽫ҵ��������������ڴ��������У������ν���ҵ��
	 */
	String newProcess(String processid, String clientId, String busstype,
			String busstip, String bussid, String bussurl) throws Exception;

	/**
	 * ��������
	 * 
	 * @param runtimeid
	 *            ����ʵ��id
	 */
	void runProcess(String runtimeid) throws Exception;

	/**
	 * �ύ�
	 * 
	 * @param workcode
	 *            �ID, �ò����Ǵ�ǰ̨���룬���û���ǰ̨���ʾ���ĳ��workitemʱ���ᴫ��workcode
	 *            ���ô˷����ĸ�������Ҫ�ݴ�workcode��ֱ��������ɸ÷�����
	 * @param participant
	 *            �����ߣ��ò���ͨ���ǴӰ�ȫ�ӿ��л�ã�ʶ��ǰ�Ĳ�����
	 * @throws Exception
	 * 
	 * ע�⣺worklist��������ʱ���ܻᱻ��������cache����ʱ�ύ���Ĵ���������ܻ�������û�����
	 * �п��ܻ�����ظ��ύ�����⣬��Ը�����÷������Զ�����Ƿ��ύ��������ύ����ô���׳��쳣 ҵ��ϵͳ��������ʾ�û�
	 * 
	 */
	String next(String workcode, String clientId) throws Exception;

	/**
	 * ��ҵ����ʾ����һ��
	 * 
	 * @param workcode
	 * @param clientId
	 * @param busstip
	 * @return
	 * @throws Exception
	 */
	String next(String workcode, String clientId, String busstip)
			throws Exception;

	/**
	 * ���̻���
	 * 
	 * @param workcode
	 *            �ID, �ò����Ǵ�ǰ̨���룬���û���ǰ̨���ʾ���ĳ��workitemʱ���ᴫ��workcode
	 *            ���ô˷����ĸ�������Ҫ�ݴ�workcode��ֱ��������ɸ÷�����
	 * @param participant
	 *            �����ߣ��ò���ͨ���ǴӰ�ȫ�ӿ��л�ã�ʶ��ǰ�Ĳ�����
	 * 
	 * ע�⣺��API��Ϊ�˷���ҵ�����ʵ�ֻ��˶��ṩ��,���˵ı������ڹ�������ر����м�ס���һ���ύ�Ľڵ�
	 * Ȼ��÷�����������ת�����һ���ύ�ڵ�����ɻ���ҵ�� �����ת�Ľڵ��ǻ�۵�(�ɶ���ڵ㲢����ɺ������)����ô
	 * ���˵��Ľڵ������һ���ύ�Ļ��ǰ��֧�㣬���������еĻ��ǰ��֧��
	 */
	String back(String workcode, String clientId) throws Exception;

	/**
	 * ��ҵ����ʾ�Ļ���
	 * 
	 * @param workcode
	 * @param clientId
	 * @param busstip
	 * @return
	 * @throws Exception
	 */
	String back(String workcode, String clientId, String busstip)
			throws Exception;

	/**
	 * ������ת
	 * 
	 * @param workcode
	 *            �ID, �ò����Ǵ�ǰ̨���룬���û���ǰ̨���ʾ���ĳ��workitemʱ���ᴫ��workcode
	 *            ���ô˷����ĸ�������Ҫ�ݴ�workcode��ֱ��������ɸ÷�����
	 * @param clientId
	 *            �����ߣ��ò���ͨ���ǴӰ�ȫ�ӿ��л�ã�ʶ��ǰ�Ĳ�����
	 * @param activeid
	 *            ��ת��Ŀ���ڵ�
	 * @throws Exception
	 * 
	 * ע�⣺������ת�ǱȽ���������Ӧ�ã���ʹ�ø÷�����ʱ�����ṩ�û���������ת����ʱ������Ҫ
	 * ʹ��listActiveID����������п���ת��Ŀ����
	 */
	String jump(String workcode, String clientId, String activeid)
			throws Exception;

	/**
	 * �ύĳ���������������еĻ
	 * 
	 * @param clientId
	 * @throws Exception
	 */
	String nextByRuntimeid(String runtimeid, String clientId) throws Exception;

	/**
	 * ���ݻworkcode�������runtimeid
	 * 
	 * @param workcode
	 * @return
	 */
	String getRuntimeIdByWorkcode(String workcode) throws Exception;

	/**
	 * ���г����̵����л�ڵ�
	 * 
	 * @param processid
	 * @return
	 * @throws Exception
	 * 
	 * ע��Ӧ�÷���Ӧ������Ҫ�����̵���Ӧ����ʹ�õ������磺���̻��˾�������ת��һ��Ӧ��
	 * 
	 */
	List<TWfActive> listActiveID(String processid) throws Exception;

	/**
	 * ��������е���ر���ֵ
	 * 
	 * @param runtimeid
	 *            ��������ʵ��ID
	 * @param revid
	 *            ��ر���ID
	 * @return ��ر���ֵ
	 * @throws Exception
	 */
	String getVarByRuntimeId(String runtimeid, String revid) throws Exception;

	/**
	 * �����ر�����ֵ
	 * 
	 * @param runtimeid
	 *            ��������ʵ��ID
	 * @param revid
	 *            ��ر���ID
	 * @param value
	 *            ��ر���ֵ
	 * @throws Exception
	 */
	void setVarValue(String runtimeId, String revId, String value)
			throws Exception;

	/**
	 * �ж������Ƿ���������
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowRunning(String runtimeId) throws Exception;

	/**
	 * �ж������Ƿ��Ѿ�ִ�н���
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowDone(String runtimeId) throws Exception;

	/**
	 * �ж������Ƿ�����쳣
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowException(String runtimeId) throws Exception;

}
