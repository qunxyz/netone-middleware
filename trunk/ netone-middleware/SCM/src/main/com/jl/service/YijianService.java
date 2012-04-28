/**
 * 
 */
package com.jl.service;

/**
 * 
 * 
 */
public interface YijianService {

	public String delete(String unid) throws Exception;

	public String insert(String userid, String yijian) throws Exception;

	public String load(String userid) throws Exception;

}
