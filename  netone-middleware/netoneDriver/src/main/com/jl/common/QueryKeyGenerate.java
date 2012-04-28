package com.jl.common;

import java.util.Date;
import java.util.UUID;

import oe.frame.web.WebCache;

/**
 * ��ѯƾ֤������ <br>
 * 
 * �ɽ����ѯ���ܻ���ڵĲ���©��,�����ṩ�о���ʱЧ(10����ʧЧ)��32λ��ѯƾ֤Key,��ѯ����ͨ������key���ݸ�ִ�в�ѯ�Ŀͻ���ҳ��,
 * ���ͻ���ҳ���ύ��ѯ��ʱ��,������keyһ�𴫵ݸ������,�����ͨ����֤��key�Ƿ�Ϸ�,�������Ƿ�����ִ�в�ѯ
 * 
 * 
 * @author chenjx
 * 
 */
public class QueryKeyGenerate {

	static int keyLifeCycle = 1800000;// key��Чʱ�� 30����

	static String QUERY_CAHCE_HEAD = "QCH";

	/**
	 * ������ѯƾ֤,���ص�key���÷��ڲ�ѯҳ���ϵ�����ѯƾ֤
	 * 
	 * @param loginname
	 *            ִ�в�ѯ���û���½��
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
	 * ��֤��ѯƾ֤����Ч��
	 * 
	 * @param loginname
	 *            ִ�в�ѯ���û���½��
	 * @param key
	 *            Ҫ��֤��Ч�ԵĲ�ѯƾ֤Key
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
