package com.jl.common.workflow;

import java.util.List;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

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
	
	
	String _ACT_EXT_DELAY="delay";// ���̽ڵ��ӳټ��������
	String _ACT_EXT_DELAY_TRUE="true";//���̽ڵ��ӳټ�������� Ϊ��
	
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
	 * ҵ��������������Ӧ���У�������Ѿ��ύ������ôϵͳ��Ҫ
	 * �ڹ��������������������޸����ϵȲ���
	 * @param lsh
	 * @return
	 */
	boolean bussFormLock(String lsh)throws Exception;
	
	/**
	 * ��������׽ڵ�
	 * @param processid
	 * @return
	 */
	String fetchFirstActivityId(String processid);
	

	/**
	 * ���ڸ澯
	 * 
	 * @return �澯��¼����
	 */
	int outTimeAlarm();

	/**
	 * ������������еĻ�ڵ�
	 * 
	 * @param runtimeid
	 * @return
	 */
	List<TWfWorklist> listAllRunningWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * ������������еĻ�ڵ�
	 * 
	 * @param runtimeid
	 * @return
	 */
	List<TWfWorklist> listAllDoneWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * ������������еĻworkcode
	 * 
	 * @param runtimeid
	 * @return
	 */
	String[] getRunningWorkCodeByRuntimeid(String runtimeid) throws Exception;

	/**
	 * ��������runtimeid���������ID
	 * 
	 * @param runtimeid
	 * @return
	 * @throws Exception
	 */
	String getProcessIdByRuntimeId(String runtimeid) throws Exception;

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
	 * ���ݵ�ǰ�û������Ѿ�������ĵ���δ�鵵������������
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDone(String customer) throws Exception;

	/**
	 * ���ݵ�ǰ�û������Ѿ�������ҹ鵵������������
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDoneAndProcessDone(String customer)
			throws Exception;

	/**
	 * ���ݵ�ǰ�û����ػ�б�
	 * 
	 * @param clientId
	 *            �ͻ�ID
	 * @param processid
	 *            ����ID
	 * @param mode
	 *            ҵ��ģʽ ���졢���͡�����
	 * @param limit
	 *            ��ʾ�������� limit
	 * @param needRunning
	 *            �Ƿ�������
	 * @param listtype
	 *            ������������ 01 �������С�02�ǽ���������δ������03�ǽ���������Ҳ����
	 * 
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklist(String clientId, String processid,
			boolean mode, int limit, String listtype) throws Exception;

	/**
	 * ��������ʵ��
	 * 
	 * @param processid
	 *            ����id
	 * @param clientId
	 *            ����������
	 * @param busstype
	 *            ҵ�������ҵ������ ͨ����Ӧ��Ӧ�ÿ�ܵ�natualname
	 * @param busstype
	 *            ҵ���������ʾ
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
	 * ֹͣ����
	 */
	String stopProcess(String runtimeid) throws Exception;

	/**
	 * delete����
	 */
	String deleteProcess(String runtimeid) throws Exception;
	
	/**
	 * ����Ťת,�Զ�Ťת�ɹ�������·���߼�����
	 * 
	 * @param workcode
	 * @param clientId
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextByAuto(String workcode, String clientId) throws Exception;

	/**
	 * ����Ťת��ֱ�ӽ�������
	 * 
	 * @param workcode
	 * @param clientId
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToEnd(String workcode, String clientId) throws Exception;

	/**
	 * ת��
	 * 
	 * @param workcode
	 * @param clientId
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToZhuanbang(String workcode, String clientId) throws Exception;

	/**
	 * ����Ťת�����˹�ָ����һ�����ڵĻ�ڵ�
	 * 
	 * @param workcode
	 * @param actid
	 * @param clientId
	 * @param busstip
	 *            �������
	 * @return
	 * @throws Exception
	 */
	String nextByManual(String workcode, String actid, String clientId)
			throws Exception;

	/**
	 * ҵ��֪ͨ
	 * 
	 * @param fromuser
	 *            ��ĳ���û�
	 * @param touser
	 *            ��ĳ���û�
	 * @param message
	 *            ֪ͨ����
	 * @param workcode
	 *            ���̻��� workcode������Ϊ�գ���ʾ������Ч�ܶ��죩
	 * @param dyform_lsh
	 *            ��̬������lsh dyform_lsh������Ϊ�գ���ʾ������Ч�ܶ��죩
	 * @param appname
	 *            Ӧ����Դ�� appname������Ϊ�գ���ʾ������Ч�ܶ��죩
	 */
	void notice(String fromuser, String touser, String message,
			String workcode, String dyform_lsh, String appname);

	/**
	 * ���ݻworkcode�������runtimeid
	 * 
	 * @param workcode
	 * @return
	 */
	String getRuntimeIdByWorkcode(String workcode) throws Exception;

	/**
	 * װ�س����̽ڵ�
	 * 
	 * @param processid
	 *            ����id
	 * @param activeid
	 *            �ڵ�ID
	 * @param activeid
	 *            Ӧ��ID
	 * @param activeid
	 *            �ύ��
	 * @param activeid
	 *            ��������ID
	 * @return
	 * @throws Exception
	 * 
	 * ע��Ӧ�÷���Ӧ������Ҫ�����̵���Ӧ����ʹ�õ������磺���̻��˾�������ת��һ��Ӧ��
	 * 
	 */
	TWfActive loadRuntimeActive(String processid, String activeid,
			String appid, String commiter, String runtimeid) throws Exception;

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
	 * ���Ӧ�������÷�������չ getVarByRuntimeid����Ҫ�Ƕ�ȡ busstype���ԣ����� ���˻��洦��
	 * 
	 * @param runtimeid
	 * @return
	 * @throws Exception
	 */
	String getAppNameByRuntimeId(String runtimeid) throws Exception;

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

	/**
	 * �������������ID
	 * 
	 * @param parentRuntimeid
	 *            ������ID
	 * @return
	 * @throws Exception
	 */
	String[] subFlowRuntimeId(String parentRuntimeid) throws Exception;

	/**
	 * ����ҵ��ID�������ID
	 */
	String getSession(String key);

	/**
	 * ���¼���ִ�й���ĳ�ڵ�
	 * 
	 * @param workcode
	 * @return
	 * @throws Exception
	 */
	String reActive(String workcode) throws Exception;

	/**
	 * ָ����һ�����ڵ�ִ����
	 * 
	 * @param workcode
	 *            �id
	 * @param participant
	 *            ������
	 * @param sync
	 *            �Ƿ���Ҫͬ��
	 * @param opemode
	 *            ����ģʽ 01 ������һ����02�˰�/���� 03 �鵵 ,04 �߰�
	 * @throws Exception
	 */
	void specifyParticipantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception;

	/**
	 * ָ�������ڵ�ת����Ϣ
	 * 
	 * @param workcode
	 *            �id
	 * @param participant
	 *            ������
	 * 
	 * @throws Exception
	 */
	void specifyzhuanbangByWorkcode(String commiter, String workcode,
			String participant) throws Exception;

	/**
	 * ָ����һ�����ڵĴ߰���Ϣ
	 * 
	 * @param workcode
	 *            �id
	 * @param participant
	 *            ������
	 * 
	 * @throws Exception
	 */
	void specifyCuibangByWorkcode(String commiter, String workcode,
			String participant) throws Exception;

	/**
	 * ָ����һ�����ڵĳ�����
	 * 
	 * @param commiter
	 *            �ύ��
	 * 
	 * @param workcode
	 *            �id
	 * @param participant
	 *            ������ʵ����ΪЭ���ߣ���Ҳ���Բ����ύ��������
	 * @param sync
	 *            �Ƿ���Ҫͬ��
	 * @param opemode
	 *            ����ģʽ 01 ������һ����02�˰�/���� 03 �鵵 ,04 �߰�
	 * @throws Exception
	 */
	void specifyAssistantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception;

	/**
	 * ָ����һ�����ڵĳ�����
	 * 
	 * @param workcode
	 *            �id
	 * @param participant
	 *            ������ʵ����ΪЭ���ߣ���Ҳ���Բ����ύ��������
	 * @param opemode
	 *            ����ģʽ 01 ������һ����02�˰�/���� 03 �鵵 ,04 �߰�
	 * @throws Exception
	 */
	void specifyReaderByWorkcode(String commiter, String workcode,
			String participant, String opemode) throws Exception;
	
	/**
	 * 
	 * �ֲ�ʽ�ύ 04
	 * 
	 * */
	void distributedSubmit(String commiter, String workcode,
			String participant, String opemode) throws Exception;

	/**
	 * �Զ�·����һ�����ڵ�ִ����
	 * 
	 * @param runtimeid
	 *            ����id
	 * @param participant
	 *            ������
	 * @throws Exception
	 */
	void specifyParticipantAutoByWorkcode(String commiter, String workcode)
			throws Exception;

	/**
	 * ���������������ɵĽڵ�
	 * 
	 * @param runtimeid
	 * @return
	 * @throws Exception
	 */
	List<TWfActivePass> listNextBackActive(String runtimeid) throws Exception;

	/**
	 * ��õ�ǰת��Ľڵ�
	 * 
	 * @param workcode
	 *            �code
	 * @param commiter
	 *            ��һ�����ڵ��ύ�ߣ�ע�������Ҫ�ڴ��������� ������
	 * @return
	 * @throws Exception
	 */
	TWfActive listNextZhuanbangActive(String workcode, String commiter)
			throws Exception;

	/**
	 * ��õ�ǰ�Ľڵ�
	 * 
	 * @param appname
	 *            Ӧ�ÿ����
	 * @param workcode
	 *            �code
	 * @param commiter
	 *            ��һ�����ڵ��ύ�ߣ�ע�������Ҫ�ڴ��������� ������
	 * @return
	 * @throws Exception
	 */
	TWfActive listCurrentActive(String appname, String workcode, String commiter)
			throws Exception;

	/**
	 * ������������Ĭ�ϵ���һ���ڵ� ע�⣺����ͼ����Ƶ���һ���ڵ㲻�漰·��ѡ��ϵͳ�����·���� ��·��ѡ��������
	 * 
	 * @param processid
	 *            ����ID
	 * @param activeid
	 *            �ID
	 * @param runtimeid
	 *            ����ʵ��id
	 * @param commiter
	 *            �����ύ��
	 * @return
	 */
	List<TWfActive> listNextDesignActive(String processid, String activeid,
			String runtimeid, String commiter) throws Exception;

	/**
	 * ������������Ĭ�ϵ���һ���ڵ� ע�⣺����ͼ����Ƶ���һ���ڵ㲻�漰·��ѡ��ϵͳ�����·���� ��·��ѡ��������
	 * 
	 * @param processid
	 *            ����ID
	 * @param activeid
	 *            �ID
	 * @param runtimeid
	 *            ����ʵ��id
	 * @param commiter
	 *            �����ύ��
	 * @return
	 */
	List<TWfActive> listNextRouteActive(String processid, String activeid,
			String runtimeid, String commiter) throws Exception;

	/**
	 * ��д�������
	 */
	public void saveAuditNote(String workcode, String participant, String note)
			throws Exception;

	
	/**
	 * װ�ػ
	 * 
	 * @param workcode
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws Exception;

	/**
	 * װ�ػ����
	 * 
	 * @param appname
	 *            Ӧ�ó�����
	 * @param workcode
	 *            �workcode
	 * @return
	 * @throws Exception
	 */
	public TWfActive loadActive(String appname, String workcode)
			throws Exception;

	/**
	 * ������̺��ĵ����̿�����ͼ���
	 * 
	 * @return
	 */
	public WorkflowView useCoreView() throws Exception;

	/**
	 * ������̺��ĵ����̿��ƾ��
	 * 
	 * @return
	 */
	public WorkflowConsole useCoreConsole() throws Exception;

	/**
	 * �����������
	 * 
	 * @return
	 * @throws Exception
	 */
	public WorkflowProcess loadProcess(String processid) throws Exception;

	/**
	 * ����������̰�����Ϣ
	 * 
	 * @param runtimeid
	 * @param onlyDone  �Ƿ����ʾ�����Ѿ���ɵ�
	 * @return
	 * @throws Exception
	 */
	public List<TWfParticipant> listAllParticipantinfo(String runtimeid,boolean onlyDone)
			throws Exception;

	/**
	 * ����������̰�����Ϣ
	 * 
	 * @param workcode
	 * @param participant
	 * @return
	 * @throws Exception
	 */
	public TWfParticipant loadParticipantinfo(String workcode,
			String participant) throws Exception;

	/**
	 * ��������
	 * 
	 * @param workcode
	 * @param participant
	 * @param limit
	 * @throws Exception
	 */
	public void specifyLimit(String workcode, String participant, long limit)
			throws Exception;

	/**
	 * ��鵱ǰ�ڵ����һ����֧�Ƿ�Ϊͬ��ģʽ
	 * 
	 * @param processid
	 * @param activeid
	 * @return
	 */
	public boolean isAndBranchMode(String processid, String activeid)
			throws Exception;

	/**
	 * ����Ƿ�Ϊ�׽ڵ�
	 * 
	 * @param workcode
	 * @return
	 */
	public boolean checkFirstAct(String workcode) throws Exception;

	/**
	 * ��û����
	 * 
	 * @param appid
	 *            Ӧ�ÿ��ID
	 * @param workcode
	 *            �ID (��Ϊ��,��ʱ�ǻ���׽ڵ������)
	 * @return
	 */
	public String getActivityName(String appid, String workcode)
			throws Exception;
	
	/**
	 * ����֧���ӳټ���
	 * @param workcode
	 */
	public void pendingProcess(String runtimeid)throws Exception;
	/**
	 * ���ѱ�����Ļ
	 * @param workcode
	 */
	public void WakeUpProcess(String runtimeid)throws Exception;

}
