package com.jl.common.app;

import java.util.List;
import java.util.Map;

import com.jl.common.workflow.TWfRelevant;

import com.jl.common.workflow.TWfActive;

/**
 * Ӧ��װ��
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface AppHandleIfc {

	public final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
			"busstype", "busstip" };

	String _ACTIVITY_EXT_PARTICIPANT = "participant";// ������
	String _ACTIVITY_EXT_AUTOROUTE = "autoroute"; // �Ƿ��Զ�·��
	String _ACTIVITY_EXT_NEEDSYNC = "needsync"; // ��������ʱ�Ƿ���Ҫͬ��
	String _ACTIVITY_EXT_SINGLEMAN = "singleman"; // �Ƿ�ֻ�ܵ��˰���

	// ��ɫģʽ��ָ����ӵ��ĳ���ɫ����Ա��������һ���ڵ��ύʱ��Ҫ��ȷָ����һ���ڵ�Ĵ����ߣ�����
	// ��Ա��ѡ���������ĳ��ɫ��
	String _PARTICIPANT_MODE_ROLE = "role";
	String _PARTICIPANT_MODE_FLOWROLE = "flowrole"; // ���̽�ɫ
	// ��֯ģʽ��ָ����ĳ����֯�µ���Ա��������һ���ڵ��ύʱ��Ҫ��ȷָ����һ���ڵ�Ĵ����ߣ�
	// ��Ա��ѡ���������ĳ��֯��
	String _PARTICIPANT_MODE_DEPT = "dept";
	String _PARTICIPANT_MODE_GROUP = "department";
	String _PARTICIPANT_MODE_HUMAN = "human";
	String _PARTICIPANT_MODE_SYSTEM = "system";
	String _PARTICIPANT_MODE_RESOURCE = "resource";
	String _PARTICIPANT_MODE_RESOURCESET = "resourceset";
	String _PARTICIPANT_MODE_TEAM = "team";
	// �ύ��
	String _PARTICIPANT_MODE_CREATER = "creater";
	String _PARTICIPANT_MODE_FLOWROLECREATER="flowrolecreater";
	String _PARTICIPANT_MODE_FLOWROLECREATER_AREA="flowrolecreaterarea";
	String _PARTICIPANT_MODE_FLOWROLE_AREA="flowrolearea";

	/**
	 * װ��Ӧ��
	 * 
	 * @param naturalname
	 *            Ӧ�õ���Դ��
	 * @return
	 */
	public AppObj loadApp(String naturalname) throws Exception;

	/**
	 * ��������ر����붯̬���ֶεİ�����
	 * 
	 * @param naturalname  Ӧ�õ���Դ��
	 * @return
	 * @throws Exception
	 */
	public List<TWfRelevant> wf2dyformBindCfg(String naturalname) throws Exception;

	/**
	 * ��������ر����붯̬���ֶεİ����� MAP��ʽ
	 * 
	 * @param naturalname Ӧ�õ���Դ��
	 * @return
	 * @throws Exception
	 */
	public Map<String,TWfRelevant> wf2dyformBindCfg2(String naturalname) throws Exception;

	/**
	 * �������������ߵ�������Ϣ
	 * 
	 * @param naturalname Ӧ�õ���Դ��
	 * @param commiter �ύ��
	 * @param runtimeid ����ʵ��ID
	 * @return
	 * @throws Exception
	 */
	public List<TWfActive> wf2participantBindCfg(String naturalname, String commiter,
			String runtimeid) throws Exception;

	/**
	 * װ��Ӧ�õ����̻������õ���Ϣ
	 * 
	 * @param naturalname Ӧ�õ���Դ��
	 * @param actid ���̻�Ľڵ�id
	 * @param commiter �ύ��
	 * @return
	 */
	public TWfActive loadCfgActive(String naturalname, String actid,
			String commiter, String runtimeid) throws Exception;

	/**
	 * ���ĳ�Ñ��ܷ�����ҵ��
	 * 
	 * @param natrualname
	 *            Ӧ�õ���Դ��
	 * @param userid
	 *            ��ʽ��������[��¼��]
	 * @return
	 */
	public boolean canCreate(String natrualname, String userid)
			throws Exception;

}
