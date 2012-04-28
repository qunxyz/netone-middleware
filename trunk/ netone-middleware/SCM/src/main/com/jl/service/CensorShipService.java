package com.jl.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.serialize.dao.PageInfo;

import com.jl.entity.CensorShip;
import com.jl.entity.CensorShipLog;
import com.jl.entity.CensorShipStatus;
import com.jl.entity.User;

public interface CensorShipService {

	public Map load(String unid, User user) throws Exception;

	public String save(HttpServletRequest request, CensorShip CensorShip,
			User user) throws Exception;

	public String delete(HttpServletRequest request, String unid)
			throws Exception;

	public PageInfo select(Map conditionMap) throws Exception;

	// �ύ��һ����
	public String auditNext(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus,
			String notice) throws Exception;

	// ����
	public String assign(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus,
			String notice) throws Exception;

	// �������
	public String saveyijian(HttpServletRequest request, User user,
			CensorShipStatus CensorShipStatus) throws Exception;

	// �ύ
	public String audit(HttpServletRequest request, String unid, User user,
			CensorShipLog CensorShipLog, CensorShipStatus CensorShipStatus)
			throws Exception;

	// �鵵
	public String pack(HttpServletRequest request, User user,
			CensorShip CensorShip, CensorShipLog CensorShipLog,
			CensorShipStatus CensorShipStatus) throws Exception;

	public Map loadlog(String unid) throws Exception;

}
