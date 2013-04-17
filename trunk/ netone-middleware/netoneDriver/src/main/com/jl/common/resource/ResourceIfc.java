package com.jl.common.resource;

import java.util.List;

import oe.security3a.seucore.obj.db.UmsProtectedobject;
/**
 * 资源管理
 * @author robanco
 *
 */
public interface ResourceIfc  {
	/**
	 * 根据ID仅获得下一级的子资源
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subNextById(String id)throws Exception;
	/**
	 * 根据ID获得其下N级的所有子资源
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subAllById(String id)throws Exception;
	/**
	 * 根据natualname仅获得下一级的子资源
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subNextByName(String name)throws Exception;
	/**
	 * 根据natualname获其下N级的所有子资源
	 * @param id
	 * @return
	 */
	public List<UmsProtectedobject> subAllByName(String name)throws Exception;
	
	
	public UmsProtectedobject loadResourceById(String id)throws Exception;
	
	public UmsProtectedobject loadResourceByNaturalname(String naturalname)throws Exception;

}
