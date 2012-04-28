package com.jl.dao;

public class DbContextHolder {
	// ������DbContextHolderΪһ�̰߳�ȫ��ThreadLocal
	private static final ThreadLocal contextHolder = new ThreadLocal();

	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDbType() {
		return (String) contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}

}
