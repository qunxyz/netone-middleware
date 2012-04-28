package com.jl.dao;

public class DbContextHolder {
	// 上下文DbContextHolder为一线程安全的ThreadLocal
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
