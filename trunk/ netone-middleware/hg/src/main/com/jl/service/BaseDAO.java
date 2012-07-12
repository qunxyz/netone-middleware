/**
 * 
 */
package com.jl.service;

import com.jl.dao.CommonDAO;

/**
 * DAO»ùÀà
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-24 ÉÏÎç11:38:37
 * @history
 */
public class BaseDAO {

	private static CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public static CommonDAO getCommonDAO() {
		return commonDAO;
	}
}
