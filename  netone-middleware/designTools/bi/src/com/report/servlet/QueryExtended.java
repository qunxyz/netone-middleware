package com.report.servlet;

  
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.netone.UmsProtecte;

public class QueryExtended {
	public List<String> qudongxuanz(String name) throws MalformedURLException, RemoteException, NotBoundException{
		ResourceRmi resourceRmi = null;
		List<String> list=new ArrayList<String>();
		resourceRmi=(ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject upof=resourceRmi.loadResourceByNatural(name);
		String obl=upof.getExtendattribute();
		list.add(obl.trim());
		String name1=upof.getName();
		list.add(name1.trim());
		String naturalname=upof.getNaturalname();
		list.add(naturalname.trim());
		return list;
	}
	 public String describe(String naturalname){
		 UmsProtecte up=new UmsProtecte();
		 UmsProtectedobject umsobj=up.loadUmsProtecteNaturalname(naturalname);
		 return umsobj.getDescription();
	 }
}
