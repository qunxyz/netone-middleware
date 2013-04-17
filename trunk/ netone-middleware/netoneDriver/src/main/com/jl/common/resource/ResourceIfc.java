package com.jl.common.resource;

import java.util.List;

import oe.security3a.seucore.obj.db.UmsProtectedobject;
/**
 * ��Դ����
 * @author robanco
 *
 */
public interface ResourceIfc  {
	/**
	 * ����ID�������һ��������Դ
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subNextById(String id)throws Exception;
	/**
	 * ����ID�������N������������Դ
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subAllById(String id)throws Exception;
	/**
	 * ����natualname�������һ��������Դ
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subNextByName(String name)throws Exception;
	/**
	 * ����natualname������N������������Դ
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subAllByName(String name)throws Exception;
	
	
	public UmsProtectedobject loadResourceById(String id)throws Exception;
	
	public UmsProtectedobject loadResourceByNaturalname(String naturalname)throws Exception;

}
