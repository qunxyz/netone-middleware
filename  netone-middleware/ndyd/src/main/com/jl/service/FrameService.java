/**
 * 
 */
package com.jl.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.serialize.dao.PageInfo;

import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.ResourceNode;
import com.jl.entity.User;

/**
 * 
 * @author Administrator
 * 
 */
public interface FrameService {

	/** ���� */
	public static final String trackActionSpecialType1 = "trackActionSpecialType1";
	/** ���� */
	public static final String trackActionSpecialType2 = "trackActionSpecialType2";
	/** �鵵������ */
	public static final String trackActionSpecialType4 = "trackActionSpecialType4";
	/** �ֲ�ʽ�Ķ� */
	public static final String trackActionSpecialType3 = "trackActionSpecialType3";

	public static final String trackActionSpecialTypeEND = "trackActionSpecialTypeEND";

	public PageInfo queryForPage(DyFormData dydata, int from, int to,
			String condition);

	public String saveAndUpdate(HttpServletRequest request, String participant,
			String formcode, DyFormData form, String subform, String fatherlsh)
			throws Exception;

	/**
	 * ����ɾ��
	 * 
	 * @param formcode
	 * @param lsh
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request, String formcode, String lsh)
			throws Exception;

	/**
	 * �߼�ɾ��
	 * 
	 * @param formcode
	 * @param lsh
	 * @return
	 * @throws Exception
	 */
	public String deleteByLogic(HttpServletRequest request, String formcode,
			String lsh) throws Exception;

	/**
	 * ���ض�̬��
	 * 
	 * @param workcode
	 * @param naturalname
	 * @param dyform
	 * @param lsh
	 * @param isedit
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public String load(String workcode, String naturalname, DyForm dyform,
			String lsh, boolean isedit, Map subformmode, String userinfo,
			String parameter) throws Exception;

	/**
	 * 
	 * @param groupTerminalId
	 * @return MAP node:���̲���ID runtimeid:����ID
	 * @throws Exception
	 */
	public Map newWfNode(HttpServletRequest request, String naturalname,
			String processid, String runtimeid, User user, String lsh,
			String participant) throws Exception;

	/**
	 * �½�����
	 * 
	 * @param activityid
	 * @param workcode
	 * @param participant
	 * @param userCodes
	 * @param userNames
	 * @param processlen
	 * @param issync
	 *            ���� ���ݲ���
	 * @return
	 * @throws Exception
	 */
	public String newEnd(HttpServletRequest request, String naturalname,
			String cachekey, String activityid, String filteractiveids,
			String workcode, String participant, String userCodes,
			String userNames, String limittimes, String processlen,
			boolean issync, String operatemode) throws Exception;

	/**
	 * ��˽���
	 * 
	 * @param activityid
	 * @param workcode
	 * @param participant
	 * @param userCodes
	 * @param userNames
	 * @param issync
	 *            ���� ���ݲ���
	 * @return
	 * @throws Exception
	 */
	public String auditEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames, String limittimes,
			boolean issync) throws Exception;

	/**
	 * ת�����
	 * 
	 * @param naturalname
	 * @param activityid
	 * @param workcode
	 * @param participant
	 * @param userCodes
	 * @param userNames
	 * @return
	 * @throws Exception
	 */
	public String assignEnd(HttpServletRequest request, String naturalname,
			String activityid, String workcode, String participant,
			String userCodes, String userNames) throws Exception;

	/**
	 * �������
	 * 
	 * @param workcode
	 * @param yijian
	 * @return
	 * @throws Exception
	 */
	public String saveYijian(HttpServletRequest request, String workcode,
			String userCode, String yijian) throws Exception;

	/**
	 * ������Դ��
	 * 
	 * @param naturalname
	 * @return
	 * @throws Exception
	 */
	public String findResourceTree(String naturalname) throws Exception;

	/**
	 * ������Դ���½ڵ�
	 * 
	 * @param type
	 * @param naturalname
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageInfo findResourceNodeInfo(String type, String naturalname,
			String condition, String start, String limit) throws Exception;

	/**
	 * ���ظ��ڵ㼰��ǰ�ڵ���Ϣ
	 * 
	 * @param naturalname
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public ResourceNode getResourceNode(String naturalname, String node)
			throws Exception;

	/**
	 * ��������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String dyformDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * ������������
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String dyformDealDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * ȷ��״̬
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * ��ȷ��״̬
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveUnConfirmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
