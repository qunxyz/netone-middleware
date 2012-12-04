package com.jl.common;

import java.util.Comparator;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class ComparatorDept implements Comparator {

	public int compare(Object o1, Object o2) {
		UmsProtectedobject object1 = (UmsProtectedobject)o1;
		UmsProtectedobject object2 = (UmsProtectedobject)o2;
		if(Integer.parseInt(object1.getReference())>Integer.parseInt(object2.getReference()))
			return 0;
		else 
			return 1;
	}
}
