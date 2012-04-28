package com.jl.common;

import java.util.Date;
import java.util.UUID;

import oe.frame.web.WebCache;

/**
 * 查询凭证生成器 <br>
 * 
 * 可解决查询可能会存在的参数漏洞,该类提供有具有时效(10分钟失效)的32位查询凭证Key,查询方法通过将该key传递给执行查询的客户端页面,
 * 当客户端页面提交查询的时候,连带该key一起传递给服务端,服务端通过验证该key是否合法,来决定是否允许执行查询
 * 
 * 
 * @author chenjx
 * 
 */
public class QueryKeyGenerate {

	static int keyLifeCycle = 1800000;// key有效时间 30分钟

	static String QUERY_CAHCE_HEAD = "QCH";

	/**
	 * 创建查询凭证,返回的key可置放于查询页面上当作查询凭证
	 * 
	 * @param loginname
	 *            执行查询的用户登陆名
	 * @return
	 */
	public static String generateKey(String loginname) {
		String key = UUID.randomUUID().toString().replaceAll("-", "");
		String markName = QUERY_CAHCE_HEAD + loginname;
		long timeUpTo = System.currentTimeMillis() + keyLifeCycle;
		WebCache.setCache(markName, key, new Date(timeUpTo));
		return key;
	}

	/**
	 * 验证查询凭证的有效性
	 * 
	 * @param loginname
	 *            执行查询的用户登陆名
	 * @param key
	 *            要验证有效性的查询凭证Key
	 * @return
	 */
	public static boolean checkKey(String loginname, String key) {
		if (key == null || key.equals("") || key.length() != 32) {
			return false;
		}
		String markName = QUERY_CAHCE_HEAD + loginname;
		String keyold = (String) WebCache.getCache(markName);
		return key.equals(keyold);
	}

}
