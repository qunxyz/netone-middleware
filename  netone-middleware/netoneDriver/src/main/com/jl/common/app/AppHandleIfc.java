package com.jl.common.app;

import java.util.List;
import java.util.Map;

import com.jl.common.workflow.TWfRelevant;

import com.jl.common.workflow.TWfActive;

/**
 * 应用装载
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface AppHandleIfc {

	public final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
			"busstype", "busstip" };

	String _ACTIVITY_EXT_PARTICIPANT = "participant";// 参与者
	String _ACTIVITY_EXT_AUTOROUTE = "autoroute"; // 是否自动路由
	String _ACTIVITY_EXT_NEEDSYNC = "needsync"; // 多人审批时是否需要同步
	String _ACTIVITY_EXT_SINGLEMAN = "singleman"; // 是否只能单人办理

	// 角色模式，指定由拥有某类角色的人员处理，在上一个节点提交时需要明确指明下一个节点的处理者，但是
	// 人员的选择仅限制与某角色里
	String _PARTICIPANT_MODE_ROLE = "role";
	String _PARTICIPANT_MODE_FLOWROLE = "flowrole"; // 流程角色
	// 组织模式，指定由某个组织下的人员处理，在上一个节点提交时需要明确指明下一个节点的处理者，
	// 人员的选择仅限制与某组织里
	String _PARTICIPANT_MODE_DEPT = "dept";
	String _PARTICIPANT_MODE_GROUP = "department";
	String _PARTICIPANT_MODE_HUMAN = "human";
	String _PARTICIPANT_MODE_SYSTEM = "system";
	String _PARTICIPANT_MODE_RESOURCE = "resource";
	String _PARTICIPANT_MODE_RESOURCESET = "resourceset";
	String _PARTICIPANT_MODE_TEAM = "team";
	// 提交者
	String _PARTICIPANT_MODE_CREATER = "creater";
	String _PARTICIPANT_MODE_FLOWROLECREATER="flowrolecreater";
	String _PARTICIPANT_MODE_FLOWROLECREATER_AREA="flowrolecreaterarea";
	String _PARTICIPANT_MODE_FLOWROLE_AREA="flowrolearea";

	/**
	 * 装载应用
	 * 
	 * @param naturalname
	 *            应用的资源名
	 * @return
	 */
	public AppObj loadApp(String naturalname) throws Exception;

	/**
	 * 工作流相关变量与动态表单字段的绑定配置
	 * 
	 * @param naturalname  应用的资源名
	 * @return
	 * @throws Exception
	 */
	public List<TWfRelevant> wf2dyformBindCfg(String naturalname) throws Exception;

	/**
	 * 工作流相关变量与动态表单字段的绑定配置 MAP形式
	 * 
	 * @param naturalname 应用的资源名
	 * @return
	 * @throws Exception
	 */
	public Map<String,TWfRelevant> wf2dyformBindCfg2(String naturalname) throws Exception;

	/**
	 * 工作流活动与参与者的配置信息
	 * 
	 * @param naturalname 应用的资源名
	 * @param commiter 提交者
	 * @param runtimeid 流程实例ID
	 * @return
	 * @throws Exception
	 */
	public List<TWfActive> wf2participantBindCfg(String naturalname, String commiter,
			String runtimeid) throws Exception;

	/**
	 * 装载应用的流程环节配置的信息
	 * 
	 * @param naturalname 应用的资源名
	 * @param actid 流程活动的节点id
	 * @param commiter 提交者
	 * @return
	 */
	public TWfActive loadCfgActive(String naturalname, String actid,
			String commiter, String runtimeid) throws Exception;

	/**
	 * 检查某用戶能否启动业务
	 * 
	 * @param natrualname
	 *            应用的资源名
	 * @param userid
	 *            格式：中文名[登录名]
	 * @return
	 */
	public boolean canCreate(String natrualname, String userid)
			throws Exception;

}
