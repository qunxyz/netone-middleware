/**
 * 
 */
package com.jl.service;

import java.util.Collection;
import java.util.Map;

import com.jl.entity.GroupTerminal;
import com.jl.entity.User;

import oe.serialize.dao.PageInfo;

/**
 * 
 * @author Administrator
 * 
 */
public interface GroupTerminalService {

	/** 抄送 */
	public static final String trackActionSpecialType1 = "trackActionSpecialType1";
	/** 抄阅 */
	public static final String trackActionSpecialType2 = "trackActionSpecialType2";

	public PageInfo queryForPage(Map map, PageInfo obj);

	public String saveAndUpdate(GroupTerminal groupTerminal,
			String groupTerminalDetailInfo) throws Exception;

	public String delete(String groupTerminalId) throws Exception;

	public Map load(String groupTerminalId) throws Exception;

	public Collection loadGroupTerminalDetail(String groupTerminalId)
			throws Exception;

	/**
	 * 
	 * @param groupTerminalId
	 * @return MAP node:流程部门ID runtimeid:流程ID
	 * @throws Exception
	 */
	public Map newWfNode(String runtimeid, User user, String groupTerminalId,
			String participant) throws Exception;

	/**
	 * 新建结束
	 * 
	 * @param activityid
	 * @param workcode
	 * @param participant
	 * @param userCodes
	 * @param userNames
	 * @param processlen
	 * @param issync
	 *            抄送 传递参数
	 * @return
	 * @throws Exception
	 */
	public String newEnd(String activityid, String workcode,
			String participant, String userCodes, String userNames,
			String limittimes, String processlen, boolean issync,
			String operatemode) throws Exception;

	/**
	 * 审核结束
	 * 
	 * @param activityid
	 * @param workcode
	 * @param participant
	 * @param userCodes
	 * @param userNames
	 * @param issync
	 *            抄送 传递参数
	 * @return
	 * @throws Exception
	 */
	public String auditEnd(String activityid, String workcode,
			String participant, String userCodes, String userNames,
			String limittimes, boolean issync) throws Exception;

	/**
	 * 保存意见
	 * 
	 * @param workcode
	 * @param yijian
	 * @return
	 * @throws Exception
	 */
	public String saveYijian(String workcode,String userCode, String yijian) throws Exception;
}
