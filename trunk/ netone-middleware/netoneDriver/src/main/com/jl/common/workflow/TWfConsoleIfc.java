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
	 * @param lsh ҵ���ĳ����¼��lsh
	 * @return
	 */
	boolean bussFormLock(String lsh)throws Exception;
	
	/**
	 * ҵ��������������Ӧ���У�������Ѿ��ύ������ôϵͳ��Ҫ
	 * �ڹ��������������������޸����ϵȲ���
	 * @param workcode �����������workcode
	 * @return
	 */
	boolean bussFormLockByWorkcode(String workcode)throws Exception;
	
	/**
	 * ��������׽ڵ�
	 * @param processid ������Դ��
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
	 * @param runtimeid ����ʵ��id
	 * @return
	 */
	List<TWfWorklist> listAllRunningWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * ������������еĻ�ڵ�
	 * 
	 * @param runtimeid ����ʵ��id
	 * @return
	 */
	List<TWfWorklist> listAllDoneWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * ������������еĻ workcode
	 * 
	 * @param runtimeid ����ʵ��id
	 * @return workcode���飬�����������еĻʵ����ID����
	 */
	String[] getRunningWorkCodeByRuntimeid(String runtimeid) throws Exception;

	/**
	 * ��������runtimeid���������ID
	 * 
	 * @param runtimeid  ����ʵ��id
	 * @return  ������Դ��
	 * @throws Exception
	 */
	String getProcessIdByRuntimeId(String runtimeid) throws Exception;

	/**
	 * ���ݵ�ǰ�û����ش�������
	 * 
	 * @param userid �û�id
	 * @return
	 * 
	 */
	List<TWfWorklistExt> worklist(String userid) throws Exception;

	/**
	 * ���ݵ�ǰ�û������Ѿ�������ĵ���δ�鵵������������
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDone(String userid) throws Exception;

	/**
	 * ���ݵ�ǰ�û������Ѿ�������ҹ鵵������������
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDoneAndProcessDone(String userid)
			throws Exception;

	/**
	 * ���ݵ�ǰ�û����ػ�б�
	 * 
	 * @param userid
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
	List<TWfWorklistExt> worklist(String userid, String processid,
			boolean mode, int limit, String listtype) throws Exception;

	/**
	 * ��������ʵ��
	 * 
	 * @param processid
	 *            ����id
	 * @param userid
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
	String newProcess(String processid, String userid, String busstype,
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
	 * @param runtimeid
	 *            ����ʵ��id
	 */
	String stopProcess(String runtimeid) throws Exception;

	/**
	 * delete����
	 * @param runtimeid
	 *            ����ʵ��id
	 */
	String deleteProcess(String runtimeid) throws Exception;
	
	/**
	 * ����Ťת,�Զ�Ťת�ɹ�������·���߼�����
	 * 
	 * @param workcode �ʵ��id
	 * @param userid �û�id
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextByAuto(String workcode, String userid) throws Exception;

	/**
	 * ����Ťת��ֱ�ӽ�������
	 * 
	 * @param workcode
	 * @param userid
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToEnd(String workcode, String userid) throws Exception;

	/**
	 * ת��
	 * 
	 * @param workcode �ʵ��id
	 * @param userid �û�id
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToZhuanbang(String workcode, String userid) throws Exception;

	/**
	 * ����Ťת�����˹�ָ����һ�����ڵĻ�ڵ�
	 * 
	 * @param workcode  �ʵ��id
	 * @param actid �����Դ��
	 * @param userid �û�id
	 * @return
	 * @throws Exception
	 */
	String nextByManual(String workcode, String actid, String userid)
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
	 * @param appid
	 *            Ӧ��ID
	 * @param commiter
	 *            �ύ��
	 * @param runtimeid
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
	 * @param runtimeId ��������ʵ��ID
	 * @return
	 */
	boolean isWorkflowRunning(String runtimeId) throws Exception;

	/**
	 * �ж������Ƿ��Ѿ�ִ�н���
	 * 
	 * @param runtimeId ��������ʵ��ID
	 * @return
	 */
	boolean isWorkflowDone(String runtimeId) throws Exception;

	/**
	 * �ж������Ƿ�����쳣
	 * 
	 * @param runtimeId ��������ʵ��ID
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
	 * ����ҵ��ID �� Ӧ��ID �������ID���е�ʱ��һ���������ڶ��������Ťת
	 */
	String getSession(String key,String appname);
	/**
	 * ���¼���ִ�й���ĳ�ڵ�
	 * 
	 * @param workcode �ʵ��id
	 * @return
	 * @throws Exception
	 */
	String reActive(String workcode) throws Exception;

	/**
	 * ָ����һ�����ڵ�ִ����
	 * @param commiter
	 *            ��ύ�� 
	 * @param workcode
	 *            �ʵ�� id
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
	 * @param commiter
	 *            ��ύ�� 
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
	 * @param commiter
	 *            ��ύ�� 
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
	 * @param commiter
	 *            ��ύ�� 
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
	 * �ֲ�ʽ�ύҵ��
	 * @param commiter �ύ��
	 * @param workcode �ʵ��id
	 * @param participant ������
	 * @param opemode ����ģʽ 
	 * @throws Exception
	 */
	void distributedSubmit(String commiter, String workcode,
			String participant, String opemode) throws Exception;

	/**
	 * �Զ�·����һ�����ڵ�ִ����
	 * 
	 * @param commiter �ύ��
	 * @param workcode �ʵ��id
	 * 
	 * @throws Exception
	 */
	void specifyParticipantAutoByWorkcode(String commiter, String workcode)
			throws Exception;

	/**
	 * ���������������ɵĽڵ�
	 * 
	 * @param runtimeid ����ʵ��id
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
     *  ��д�������
     * @param workcode �ʵ��id
     * @param participant ������
     * @param note ���˵��
     * @return
     * @throws Exception
     */
	public Integer saveAuditNote(String workcode, String participant, String note)
			throws Exception;

	
	/**
	 * װ�ػ
	 * 
	 * @param workcode �ʵ��id
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws Exception;

	/**
	 * װ�ػ����
	 * 
	 * @param appname
	 *            Ӧ�ó�����
	 * @param workcode
	 *            �ʵ��id
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
	 * @param ������Դ��
	 * @return
	 * @throws Exception
	 */
	public WorkflowProcess loadProcess(String processid) throws Exception;

	/**
	 * ����������̰�����Ϣ
	 * 
	 * @param runtimeid ����ʵ��id
	 * @param onlyDone  �Ƿ����ʾ�����Ѿ���ɵ�
	 * @return
	 * @throws Exception
	 */
	public List<TWfParticipant> listAllParticipantinfo(String runtimeid,boolean onlyDone)
			throws Exception;

	/**
	 * ����������̰�����Ϣ
	 * 
	 * @param workcode �ʵ��id
	 * @param participant ������
	 * @return
	 * @throws Exception
	 */
	public TWfParticipant loadParticipantinfo(String workcode,
			String participant) throws Exception;

	/**
	 * ��������
	 * 
	 * @param workcode �ʵ��id
	 * @param participant ������
	 * @param limit ����
	 * @throws Exception
	 */
	public void specifyLimit(String workcode, String participant, long limit)
			throws Exception;

	/**
	 * ��鵱ǰ�ڵ����һ����֧�Ƿ�Ϊͬ��ģʽ
	 * 
	 * @param processid ����ʵ��id
	 * @param activeid �ʵ��id
	 * @return
	 */
	public boolean isAndBranchMode(String processid, String activeid)
			throws Exception;

	/**
	 * ����Ƿ�Ϊβ�ڵ�
	 * 
	 * @param workcode �ʵ��id
	 * @return
	 */
	public boolean checkFinalAct(String workcode) throws Exception;
	
	/**
	 * ����Ƿ�Ϊ�׽ڵ�
	 * 
	 * @param workcode �ʵ��id
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
	 * @param runtimeid ����ʵ��id
	 */
	public void pendingProcess(String runtimeid)throws Exception;
	/**
	 * ���ѱ�����Ļ
	 * @param runtimeid����ʵ��id
	 */
	public void WakeUpProcess(String runtimeid)throws Exception;
	
	/**
	 * Ѱ�������쳣������(�������������첽�������ڲ����������п��ܻ���ֻ�Ĳ����߷��䶪ʧ�����)
	 * @param fromTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return
	 */
	public String[] errorProcess(String fromTime,String endTime);
	
    /**
     * �ع�ʧЧ�Ĳ���

     * @param runtimeid ����ʵ��id
     * @param workcode ��ǰ��ڵ�
     * @param actid ��Ҫ�ع����Ľڵ㣬���û������̹켣��������ѡ�� 
     * @return
     */
	public int rollbackErrorProcess(String runtimeid,String workcode,String actid);
	
   /**
    * �޸�ʧЧ�Ĳ���
    * @param runtimeid ����ʵ��id
    * @param commiter �ύ��
    * @param operater ִ����
    * @return
    */
	public int repairErrorProcess(String runtimeid,String commitercode,String operatercode);

	/**
	 * ����û��Ĵ�������
	 * @param userid
	 * @param workcode
	 * @return
	 */
	public boolean checkUserworklist(String userid,String workcode);
	
	/**
	 * ����û��Ƿ�����ĳ����
	 * @param userid �û���¼�� 
	 * @param runtimeid �������̵�ʵ��ID
	 * @return ������������true
	 */
	public boolean checkIfUserJoinProcess(String userid, String runtimeid);
}
