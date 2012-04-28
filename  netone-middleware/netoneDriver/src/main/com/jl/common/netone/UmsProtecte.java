package com.jl.common.netone;

import java.io.Serializable;

/*
 * xuwei(��Դ����Ĳ���)
 * 2012-4-26
 * */
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class UmsProtecte {
	// ����UmsProtectedobject ���� ������Դ
	public Serializable addUmsProtecte(UmsProtectedobject obj) {
		ResourceRmi resourceRmi = null;
		Serializable idcreated = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			idcreated = resourceRmi.addResource(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcreated;
	}
	// ����UmsProtectedobject ���� ������Դ
	public Serializable addUmsProtecte(UmsProtectedobject obj,String parentid) {
		ResourceRmi resourceRmi = null;
		Serializable idcreated = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			idcreated = resourceRmi.addResource(obj,parentid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcreated;
	}

	// ����UmsProtectedobject ���� ɾ����Դ
	public Boolean delUmsProtecte(UmsProtectedobject obj) {
		ResourceRmi resourceRmi = null;
		Boolean fal = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			fal = resourceRmi.dropResource(obj.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fal;
	}

	// ����UmsProtectedobject ����   �޸���Դ
	public Boolean UpdateUmsProtecte(UmsProtectedobject obj) {
		ResourceRmi resourceRmi = null;
		Boolean fal = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			fal = resourceRmi.updateResource(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fal;
	}
 
	// �������id װ�� ���� 
	public UmsProtectedobject loadUmsProtecteID(String id) {
		ResourceRmi resourceRmi = null;
		UmsProtectedobject upbj = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			upbj = resourceRmi.loadResourceById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upbj;
	}

	// �������Naturalname װ�� ���� 
	public UmsProtectedobject loadUmsProtecteNaturalname(String Naturalname) {
		ResourceRmi resourceRmi = null;
		UmsProtectedobject upbj = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			upbj = resourceRmi.loadResourceByNatural(Naturalname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upbj;
	}

}
