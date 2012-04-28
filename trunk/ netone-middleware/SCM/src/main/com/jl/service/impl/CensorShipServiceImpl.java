package com.jl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jl.common.workflow.WfEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.CensorShip;
import com.jl.entity.CensorShipLog;
import com.jl.entity.CensorShipStatus;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.CensorShipService;

public class CensorShipServiceImpl extends BaseService implements
		CensorShipService {
	/** 日志 */
	private final Logger log = Logger.getLogger(CensorShipServiceImpl.class);

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public String delete(HttpServletRequest request, String unid)
			throws Exception {
		JSONObject json = new JSONObject();
		// file
		// log
		commonDAO.delete("CensorShip.deleteCensorShipLog", unid);
		// status
		commonDAO.delete("CensorShip.deleteCensorShipStatus", unid);
		// main
		commonDAO.delete("CensorShip.deleteCensorShip", unid);

		json.put("tip", "作废成功");
		return json.toString();
	}

	public String save(HttpServletRequest request, CensorShip CensorShip,
			User user) throws Exception {
		JSONObject json = new JSONObject();
		String id = CensorShip.getUnid();
		if (StringUtils.isNotEmpty(id)) {// 根据Id 来判断是 否是修改还是插入
			// main
			CensorShip = (CensorShip) commonDAO.update(
					"CensorShip.updateCensorShip", CensorShip);
		} else {
			// main
			CensorShip = (CensorShip) commonDAO.insert(
					"CensorShip.insertCensorShip", CensorShip);

			// log
			CensorShipLog CensorShipLog = newLog(CensorShip.getUnid(),
					CensorShip.getHandler(), FDL_VAR._ACTION_NEW, "-");

			CensorShipStatus CensorShipStatus = new CensorShipStatus();
			CensorShipStatus.setUnid(CensorShip.getUnid());
			CensorShipStatus.setPerunid(user.getUserCode());
			CensorShipStatus.setLoglinkunid(CensorShipLog.getPunid());
			newStatus0(CensorShipStatus);
		}
		json.put("tip", "保存成功");
		json.put("unid", CensorShip.getUnid());
		return json.toString();
	}

	public PageInfo select(Map conditionMap) throws Exception {
		String status = (String) conditionMap.get("status");
		PageInfo obj = new PageInfo();
		if (StringUtils.isNotEmpty(status)) {
			if ("2".equals(status)) {// 已办结
				obj = commonDAO.selectForPage(
						"CensorShip.selectCensorShipEndForPageCount",
						"CensorShip.selectCensorShipEndForPage", conditionMap,
						obj);
			} else if ("1".equals(status)) {// 已办理
				obj = commonDAO.selectForPage(
						"CensorShip.selectCensorShipNotEndForPageCount",
						"CensorShip.selectCensorShipNotEndForPage",
						conditionMap, obj);
			} else if ("0".equals(status)) {// 待办
				obj = commonDAO.selectForPage(
						"CensorShip.selectCensorShipForPageCount_status",
						"CensorShip.selectCensorShipForPage_status",
						conditionMap, obj);
			}
		} else {// 所有
			obj = commonDAO.selectForPage(
					"CensorShip.selectCensorShipForPageCount",
					"CensorShip.selectCensorShipForPage", conditionMap, obj);
		}
		return obj;
	}

	public String assign(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus,
			String notice) throws Exception {
		JSONObject json = new JSONObject();

		if (StringUtils.isNotEmpty(CensorShipStatus.getPerunid())) {
			String[] Perunids = CensorShipStatus.getPerunid().split(",");
			String[] Tnames = CensorShipLog.getTname().split(",");

			// check
			for (int i = 0; i < Perunids.length; i++) {
				CensorShipStatus.setUnid(unid);
				CensorShipStatus.setPerunid(Perunids[i]);
				CensorShipStatus.setYijian(null);
				if (findCensorShipStatusIsExistNper(CensorShipStatus)) {
					json.put("tip", "流程中存在重复用户,不能提交!");
					json.put("error", "yes");
					return json.toString();
				}
				if (StringUtils.isNotEmpty(notice)) {
					if ("0".equals(notice)) {
						WfEntry.iv().notice(user.getUserCode(), Perunids[i],
								"", "", unid, "");
					}
				}
			}

			for (int i = 0; i < Perunids.length; i++) {
				// log
				CensorShipLog.setTname(Tnames[i]);
				CensorShipLog = newLog(unid, user.getUserName(),
						FDL_VAR._ACTION_ASSIGN, Tnames[i]);

				// 新增办理完毕日志
				newTmpLog(unid, Tnames[i], FDL_VAR._ACTION_FINISH,
						CensorShipLog.getSname());

				// status
				CensorShipStatus.setUnid(unid);
				CensorShipStatus.setPerunid(Perunids[i]);
				CensorShipStatus.setLoglinkunid(CensorShipLog.getPunid());
				CensorShipStatus.setYijian(null);
				CensorShipStatus = newStatus1(CensorShipStatus);

				updateCensorshipTransdept(unid, Perunids[i]);
			}
		}
		json.put("tip", "交办成功!");
		return json.toString();
	}

	public String auditNext(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus,
			String notice) throws Exception {
		JSONObject json = new JSONObject();

		if (StringUtils.isNotEmpty(CensorShipStatus.getPerunid())) {
			String[] Perunids = CensorShipStatus.getPerunid().split(",");
			String[] Tnames = CensorShipLog.getTname().split(",");

			// check
			for (int i = 0; i < Perunids.length; i++) {
				CensorShipStatus.setUnid(unid);
				CensorShipStatus.setPerunid(Perunids[i]);
				CensorShipStatus.setYijian(null);

				if (findCensorShipStatusIsExistNper(CensorShipStatus)) {
					json.put("tip", "流程中存在重复用户,不能提交!");
					json.put("error", "yes");
					return json.toString();
				}
				if (StringUtils.isNotEmpty(notice)) {
					if ("0".equals(notice)) {
						WfEntry.iv().notice(user.getUserCode(), Perunids[i],
								"", "", unid, "");
					}
				}
			}

			for (int i = 0; i < Perunids.length; i++) {
				// log
				CensorShipLog.setTname(Tnames[i]);
				CensorShipLog = newLog(unid, user.getUserName(),
						FDL_VAR._ACTION_AUDIT, Tnames[i]);

				// 新增办理完毕日志
				newTmpLog(unid, Tnames[i], FDL_VAR._ACTION_FINISH, user
						.getUserName());

				// status
				CensorShipStatus.setUnid(unid);
				CensorShipStatus.setPerunid(Perunids[i]);
				CensorShipStatus.setLoglinkunid(CensorShipLog.getPunid());
				CensorShipStatus.setYijian(null);
				CensorShipStatus = newStatus1(CensorShipStatus);

				updateCensorshipTransdept(unid, Perunids[i]);
				// file
			}
		}
		json.put("tip", "提交下一处理成功!");
		return json.toString();
	}

	public String pack(HttpServletRequest request, User user,
			CensorShip CensorShip, CensorShipLog CensorShipLog,
			CensorShipStatus CensorShipStatus) throws Exception {
		JSONObject json = new JSONObject();
		String id = CensorShip.getUnid();
		// main
		CensorShip.setDonetime(new Date());
		CensorShip = (CensorShip) commonDAO.update(
				"CensorShip.updateCensorShip", CensorShip);

		CensorShip.setHandler(user.getUserCode());

		// log
		CensorShipLog = newLog(CensorShip.getUnid(), CensorShip.getHandler(),
				FDL_VAR._ACTION_PACK, "-");

		// status
		CensorShipStatus.setPerunid(user.getUserCode());
		CensorShipStatus.setState(3);
		updateStatus3(CensorShipStatus);
		json.put("tip", "归档成功!");
		return json.toString();
	}

	public String audit(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus)
			throws Exception {
		JSONObject json = new JSONObject();
		CensorShipStatus.setPerunid(user.getUserCode());
		CensorShipStatus.setState(3);
		// CensorShipStatus.setYijian(CensorShipStatus.getYijian());
		updateStatus3(CensorShipStatus);

		// log
		CensorShipLog = updateLog(unid, "NULL", FDL_VAR._ACTION_FINISH, user
				.getUserName());

		json.put("tip", "提交成功!");
		return json.toString();
	}

	public Map load(String unid, User user) throws Exception {
		CensorShip CensorShip = new CensorShip();
		CensorShipStatus CensorShipStatus = new CensorShipStatus();
		CensorShipLog CensorShipLog = new CensorShipLog();
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(unid)) {
			CensorShip = (CensorShip) commonDAO.findForObject(
					"CensorShip.loadCensorShipByUnid", unid);

			CensorShipStatus.setPerunid(user.getUserCode());
			CensorShipStatus.setUnid(unid);
			CensorShipStatus = (CensorShipStatus) commonDAO.findForObject(
					"CensorShip.loadCensorShipStatusByCondition",
					CensorShipStatus);

			CensorShipLog = (CensorShipLog) commonDAO.findForObject(
					"CensorShip.loadCensorShipLogByFinish", CensorShipStatus);
		} else {
			CensorShip.setHandler(user.getUserName());
			CensorShip.setChargedept(user.getDepartmentName());
			CensorShip.setNewtime(new Date());
		}
		map.put("CensorShip", CensorShip);
		map.put("CensorShipStatus", CensorShipStatus);
		map.put("CensorShipLog", CensorShipLog);
		return map;
	}

	public String saveyijian(HttpServletRequest request, User user,
			CensorShipStatus CensorShipStatus) throws Exception {
		JSONObject json = new JSONObject();

		// status
		CensorShipStatus.setYijian(CensorShipStatus.getYijian());
		CensorShipStatus.setState(2);
		CensorShipStatus.setParentunid(null);
		CensorShipStatus.setPerunid(user.getUserCode());
		CensorShipStatus.setLoglinkunid(null);
		CensorShipStatus.setIsdelete(0);
		newStatus2(CensorShipStatus);

		json.put("tip", "提交成功!");
		return json.toString();
	}

	public Map loadlog(String unid) throws Exception {
		List packlog = new ArrayList();

		List packlog1 = new ArrayList();
		List packlog2 = new ArrayList();
		List packlog3 = new ArrayList();
		if (StringUtils.isNotEmpty(unid)) {
			packlog = (List) commonDAO.select(
					"CensorShip.loadCensorShipLogByNode", unid);

			packlog1 = (List) commonDAO.select(
					"CensorShip.loadCensorShipLogBy1", unid);
			packlog2 = (List) commonDAO.select(
					"CensorShip.loadCensorShipLogBy2", unid);
			packlog3 = (List) commonDAO.select(
					"CensorShip.loadCensorShipLogBy3", unid);
		}
		Map map = new HashMap();
		map.put("packlog", packlog);
		map.put("packlog1", packlog1);
		map.put("packlog2", packlog2);
		map.put("packlog3", packlog3);
		return map;
	}

	private CensorShipLog newLog(String unid, String sname, String actionname,
			String tname) throws Exception {
		CensorShipLog CensorShipLog = new CensorShipLog();
		CensorShipLog.setSname(sname);
		CensorShipLog.setUnid(unid);
		CensorShipLog.setActionname(actionname);
		CensorShipLog.setTname(tname);
		CensorShipLog.setAddtime(new Date());

		try {
			Integer ise = (Integer) commonDAO.findForObject(
					"CensorShip.findCensorShipLogIsExist", CensorShipLog);

			if (ise > 0) {
				CensorShipLog = (CensorShipLog) commonDAO.update(
						"CensorShip.updateCensorShipLogByExist", CensorShipLog);
			} else {
				CensorShipLog = (CensorShipLog) commonDAO.insert(
						"CensorShip.insertCensorShipLog", CensorShipLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CensorShipLog;
	}

	private CensorShipLog updateLog(String unid, String sname,
			String actionname, String tname) throws Exception {
		CensorShipLog CensorShipLog = new CensorShipLog();
		CensorShipLog.setUnid(unid);
		CensorShipLog.setActionname(actionname);
		CensorShipLog.setTname(tname);
		CensorShipLog.setAddtime(new Date());

		CensorShipLog = (CensorShipLog) commonDAO.update(
				"CensorShip.updateCensorShipLogByExist2", CensorShipLog);

		return CensorShipLog;
	}

	private CensorShipLog newTmpLog(String unid, String sname,
			String actionname, String tname) throws Exception {
		CensorShipLog CensorShipLog = new CensorShipLog();
		CensorShipLog.setSname(sname);
		CensorShipLog.setUnid(unid);
		CensorShipLog.setActionname(actionname);
		CensorShipLog.setTname(tname);
		CensorShipLog.setAddtime(null);

		try {
			Integer ise = (Integer) commonDAO.findForObject(
					"CensorShip.findCensorShipLogIsExist", CensorShipLog);

			if (ise > 0) {
				CensorShipLog = (CensorShipLog) commonDAO.update(
						"CensorShip.updateCensorShipLogByExist", CensorShipLog);
			} else {
				CensorShipLog = (CensorShipLog) commonDAO.insert(
						"CensorShip.insertCensorShipLog", CensorShipLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CensorShipLog;
	}

	private CensorShipStatus newStatus(CensorShipStatus CensorShipStatus)
			throws Exception {
		if (StringUtils.isEmpty(CensorShipStatus.getParentunid())) {
			CensorShipStatus.setParentunid(null);
		}

		CensorShipStatus CensorShipStatus_ = (CensorShipStatus) commonDAO
				.findForObject("CensorShip.loadCensorShipStatusByCondition",
						CensorShipStatus);
		if (CensorShipStatus_ != null) {// update
			CensorShipStatus = (CensorShipStatus) commonDAO.update(
					"CensorShip.updateCensorShipStatus2", CensorShipStatus);
		} else {// new
			CensorShipStatus = (CensorShipStatus) commonDAO.insert(
					"CensorShip.insertCensorShipStatus", CensorShipStatus);
		}
		return CensorShipStatus;
	}

	private CensorShipStatus newStatus2(CensorShipStatus CensorShipStatus)
			throws Exception {
		if (StringUtils.isEmpty(CensorShipStatus.getParentunid())) {
			CensorShipStatus.setParentunid(null);
		}

		CensorShipStatus CensorShipStatus_ = (CensorShipStatus) commonDAO
				.findForObject("CensorShip.loadCensorShipStatusByCondition",
						CensorShipStatus);
		if (CensorShipStatus_ != null) {// update
			CensorShipStatus = (CensorShipStatus) commonDAO.update(
					"CensorShip.updateCensorShipStatus2", CensorShipStatus);
		} else {// new
			CensorShipStatus = (CensorShipStatus) commonDAO.insert(
					"CensorShip.insertCensorShipStatus", CensorShipStatus);
		}
		return CensorShipStatus;
	}

	/**
	 * 新建督办状态
	 * 
	 * @param CensorShipStatus
	 * @return
	 * @throws Exception
	 */
	private CensorShipStatus newStatus0(CensorShipStatus CensorShipStatus)
			throws Exception {
		if (StringUtils.isEmpty(CensorShipStatus.getParentunid())) {
			CensorShipStatus.setParentunid(null);
		}
		CensorShipStatus.setIsdelete(0);
		CensorShipStatus.setState(0);
		CensorShipStatus = newStatus(CensorShipStatus);
		return CensorShipStatus;
	}

	private CensorShipStatus newStatus1(CensorShipStatus CensorShipStatus)
			throws Exception {
		try {
			if (StringUtils.isEmpty(CensorShipStatus.getParentunid())) {
				CensorShipStatus.setParentunid(null);
			}
			CensorShipStatus.setIsdelete(0);
			CensorShipStatus.setState(1);
			CensorShipStatus = newStatus(CensorShipStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CensorShipStatus;
	}

	private CensorShipStatus updateStatus3(CensorShipStatus CensorShipStatus)
			throws Exception {
		CensorShipStatus = (CensorShipStatus) commonDAO.update(
				"CensorShip.updateCensorShipStatus3", CensorShipStatus);
		return CensorShipStatus;
	}

	// 判断是否存在流程中存在重复用户
	private boolean findCensorShipStatusIsExistNper(
			CensorShipStatus CensorShipStatus) throws Exception {
		Integer count = (Integer) commonDAO.findForObject(
				"CensorShip.findCensorShipStatusIsExistNper", CensorShipStatus);
		if (count == null)
			count = 0;

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新督办办理部门 交办或提交下一节点
	 * 
	 * @param unid
	 * @param tcode
	 *            办理部门编号
	 * @throws Exception
	 */
	private void updateCensorshipTransdept(String unid, String tcode)
			throws Exception {
		List<String> list = (List<String>) commonDAO.select(
				"CensorShip.findTransdept", tcode);
		String transdept = list.get(0);

		CensorShip censorship = (CensorShip) commonDAO.findForObject(
				"CensorShip.loadCensorShipByUnid", unid);
		String oldTransdept = censorship.getTransdept();
		if (oldTransdept == null)
			oldTransdept = "";
		if (oldTransdept.equals(transdept)
				|| oldTransdept.indexOf(transdept + ",") > 0
				|| oldTransdept.indexOf("," + transdept) > 0) {
			// not do
		} else {
			if (StringUtils.isEmpty(oldTransdept)) {
				transdept = transdept;
			} else {
				transdept = oldTransdept + "," + transdept;
			}
			censorship.setTransdept(transdept);
			commonDAO
					.update("CensorShip.updateCensorshipTransdept", censorship);
		}
	}

}
