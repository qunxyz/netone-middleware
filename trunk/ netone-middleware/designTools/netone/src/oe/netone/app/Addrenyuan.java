package oe.netone.app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.jl.common.netone.UmsProtecte;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class Addrenyuan{
	public String Str;
	public void AddrenyuanQuery(String name,String naturalname,String xml) {
			Serializable idcreated = null;
			String cString="<?xml version='1.0' encoding='UTF-8'?>";
			xml=cString+xml;
			 UmsProtecte up=new UmsProtecte(); 
			UmsProtectedobject upo=null;
			upo = up.loadUmsProtecteNaturalname(name);
			upo.setExtendattribute(xml);
			idcreated = up.UpdateUmsProtecte(upo);
	 }
}