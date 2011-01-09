package oe.common.workflow;

import java.util.List;
import java.util.Map;

/**
 * DRP工作流应用接口 <br>
 * 该接口作为一个适配器存在，为了支持DRP应用外部独立的工作流引擎服务<br>
 * 
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface TWfConsoleIfc {

	String OPE_TIP_SUCCESS = "SUCCESS:";
	String OPE_TIP_ERROR = "ERROR:";

	// 业务流程参考相关变量命名
	String _DEFAULT_REV_KEY_CUSTOMER = "customer"; // 参与者
	String _DEFAULT_REV_KEY_BUSSID = "bussid"; // 业务ID
	String _DEFAULT_REV_KEY_BUSSURL = "bussurl"; // 业务地址
	String _DEFAULT_REV_KEY_BUSSTYPE = "busstype"; // 业务模式
	String _DEFAULT_REV_KEY_BUSSTIP = "busstip"; // 业务提示
	String _DEFAULT_REV_KEY_STATUS = "status";// 流程环节，该变量是工作流内部控制表示当前的业务环节值
	String _DEFAULT_REV_KEY_WORKCODE = "workcode";
	String _DEFAULT_REV_KEY_BUSSSTATUS = "bussstatus";// 业务状态，该字段表示流程的环节计数

	/**
	 * 获得所有可用有代办任务的流程信息
	 * 
	 * @return key=processid value=WorkflowProcess object
	 */
	Map allAvailableProcess();

	// 如0、1、2、3、4、6

	/**
	 * 根据当前用户返回代办任务
	 * 
	 * @param clientId
	 * @return
	 * 
	 */
	List<TWfWorklistExt> worklist(String clientId) throws Exception;

	/**
	 * 创建流程实例
	 * 
	 * @param processid
	 *            流程id
	 * @param clientId
	 *            启动流程者
	 * @param busstype
	 *            业务参数，业务类型
	 * @param bussid
	 *            业务参数，表单id
	 * @param bussurl
	 *            业务参数，表单访问地址
	 * @return
	 * @throws Exception
	 * 
	 * 补充说明：流程创建实例时，必须将业务参数传入这样在代办任务中，才能衔接上业务
	 */
	String newProcess(String processid, String clientId, String busstype,
			String busstip, String bussid, String bussurl) throws Exception;

	/**
	 * 启动流程
	 * 
	 * @param runtimeid
	 *            流程实例id
	 */
	void runProcess(String runtimeid) throws Exception;

	/**
	 * 提交活动
	 * 
	 * @param workcode
	 *            活动ID, 该参数是从前台传入，当用户在前台访问具体某个workitem时，会传递workcode
	 *            调用此方法的父方法需要暂存workcode，直至调度完成该方法后
	 * @param participant
	 *            参与者，该参数通常是从安全接口中获得，识别当前的操作者
	 * @throws Exception
	 * 
	 * 注意：worklist的数据有时可能会被配置周期cache，这时提交过的代办任务可能会继续被用户看到
	 * 有可能会出现重复提交的问题，针对该问题该方法会自动检查活动是否提交过，如果提交过那么将抛出异常 业务系统可另行提示用户
	 * 
	 */
	String next(String workcode, String clientId) throws Exception;

	/**
	 * 带业务提示的下一步
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
	 * 流程回退
	 * 
	 * @param workcode
	 *            活动ID, 该参数是从前台传入，当用户在前台访问具体某个workitem时，会传递workcode
	 *            调用此方法的父方法需要暂存workcode，直至调度完成该方法后
	 * @param participant
	 *            参与者，该参数通常是从安全接口中获得，识别当前的操作者
	 * 
	 * 注意：该API是为了方便业务过程实现回退而提供的,回退的本质是在工作流相关变量中记住最后一次提交的节点
	 * 然后该方法将流程跳转到最后一次提交节点上完成回退业务。 如果跳转的节点是汇聚点(由多个节点并行完成后产生的)，那么
	 * 回退到的节点是最后一次提交的汇聚前分支点，而不是所有的汇聚前分支点
	 */
	String back(String workcode, String clientId) throws Exception;

	/**
	 * 带业务提示的回退
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
	 * 流程跳转
	 * 
	 * @param workcode
	 *            活动ID, 该参数是从前台传入，当用户在前台访问具体某个workitem时，会传递workcode
	 *            调用此方法的父方法需要暂存workcode，直至调度完成该方法后
	 * @param clientId
	 *            参与者，该参数通常是从安全接口中获得，识别当前的操作者
	 * @param activeid
	 *            跳转的目标活动节点
	 * @throws Exception
	 * 
	 * 注意：流程跳转是比较灵活的流程应用，在使用该方法的时候，在提供用户交互的跳转操作时常常需要
	 * 使用listActiveID方法获得所有可跳转的目标结点
	 */
	String jump(String workcode, String clientId, String activeid)
			throws Exception;

	/**
	 * 提交某流程中所有运行中的活动
	 * 
	 * @param clientId
	 * @throws Exception
	 */
	String nextByRuntimeid(String runtimeid, String clientId) throws Exception;

	/**
	 * 根据活动workcode获得流程runtimeid
	 * 
	 * @param workcode
	 * @return
	 */
	String getRuntimeIdByWorkcode(String workcode) throws Exception;

	/**
	 * 罗列出流程的所有活动节点
	 * 
	 * @param processid
	 * @return
	 * @throws Exception
	 * 
	 * 注：应该方法应用在需要做流程调整应用中使用到，比如：流程回退就属于跳转的一个应用
	 * 
	 */
	List<TWfActive> listActiveID(String processid) throws Exception;

	/**
	 * 获得流程中的相关变量值
	 * 
	 * @param runtimeid
	 *            流程运行实例ID
	 * @param revid
	 *            相关变量ID
	 * @return 相关变量值
	 * @throws Exception
	 */
	String getVarByRuntimeId(String runtimeid, String revid) throws Exception;

	/**
	 * 添加相关变量的值
	 * 
	 * @param runtimeid
	 *            流程运行实例ID
	 * @param revid
	 *            相关变量ID
	 * @param value
	 *            相关变量值
	 * @throws Exception
	 */
	void setVarValue(String runtimeId, String revId, String value)
			throws Exception;

	/**
	 * 判断流程是否在运行中
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowRunning(String runtimeId) throws Exception;

	/**
	 * 判断流程是否已经执行结束
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowDone(String runtimeId) throws Exception;

	/**
	 * 判断流程是否出现异常
	 * 
	 * @param runtimeId
	 * @return
	 */
	boolean isWorkflowException(String runtimeId) throws Exception;

}
