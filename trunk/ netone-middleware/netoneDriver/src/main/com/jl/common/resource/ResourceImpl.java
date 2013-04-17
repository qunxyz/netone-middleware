package com.jl.common.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class ResourceImpl implements ResourceIfc {

	public List<UmsProtectedobject> subAllById(String id) throws Exception{
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			String name=rs.loadResourceById(id).getNaturalname();
			List list=rs.subResourceByNaturalname(name);
			dealwithDefaulPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public List<UmsProtectedobject> subAllByName(String name)throws Exception {
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			List list=rs.subResourceByNaturalname(name);
			dealwithDefaulPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public List<UmsProtectedobject> subNextById(String id)throws Exception {
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			List list=rs.subResource(id);
			dealwithDefaulPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public List<UmsProtectedobject> subNextByName(String name)throws Exception {
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			String id=rs.loadResourceByNatural(name).getId();
			List list=rs.subResource(id);
			dealwithDefaulPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	private void dealwithDefaulPath(List list)throws Exception{
		EnvService env=(EnvService)RmiEntry.iv("envinfo");
		// 带流程逻辑的应用程序前缀
		String urlHead1=env.fetchEnvValue("WEBSER_APPFRAME");
		// 无流程逻辑的应用程序前缀
		String urlHead2=env.fetchEnvValue("WEBSER_APPFRAMEX");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			//处理动态地址
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			String actionulr=object.getActionurl();
			if(StringUtils.isNotEmpty(actionulr)){
				if(actionulr.contains("$(npath)")){
					actionulr=StringUtils.replace(actionulr, "$(npath)", urlHead1);
				}else if(actionulr.contains("$(nxpath)")){
					actionulr=StringUtils.replace(actionulr, "$(nxpath)", urlHead2);
				}
			}
			object.setActionurl(actionulr);
		}
	}

	@Override
	public UmsProtectedobject loadResourceById(String id) throws Exception {
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			return rs.loadResourceById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public UmsProtectedobject loadResourceByNaturalname(String naturalname)
			throws Exception {
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			return rs.loadResourceByNatural(naturalname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

}
