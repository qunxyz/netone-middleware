package oe.security3a.seucore.resourceser;

import java.util.List;

import oe.security3a.seucore.obj.db.UmsProtectedobject;


/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface ProtectedObjectService {

	ProtectedObjectDao fetchDao();
	
	List<UmsProtectedobject> getChildren(String uponame);
	

	
}
