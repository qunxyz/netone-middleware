package oe.jl.common.config;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.netone.UmsProtecte;

public class ResourceOperate {
	// 添加资源 active: 1 有效， active：0 无效
	public String addRseource(String URLname, String xml, String repidID,
			String repdimxpla, String objecttype, String active) {
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = new UmsProtectedobject();
		upobj.setName(repdimxpla);
		upobj.setNaturalname(repidID);
		upobj.setExtendattribute(xml);
		upobj.setInclusion("0");
		upobj.setObjecttype(objecttype);
		upobj.setActive(active);
		return (String) up.addUmsProtecte(upobj, URLname);
	}
	// 添加资源 active: 1 有效， active：0 无效
	public String addRsfile(String URLname, String xml, String repidID,
			String repdimxpla, String objecttype, String active) {
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = new UmsProtectedobject();
		upobj.setName(repdimxpla);
		upobj.setNaturalname(repidID);
		upobj.setExtendattribute(xml);
		upobj.setInclusion("1");
		upobj.setObjecttype(objecttype);
		upobj.setActive(active);
		return (String) up.addUmsProtecte(upobj, URLname);
	}

	// 修改资源 active: 1 有效， active：0 无效
	public boolean UpdateRseource(String xml, String repidID,
			String repdimxpla, String type,String active) {
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = new UmsProtectedobject();
		upobj.setName(repdimxpla);
		upobj.setNaturalname(repidID);
		upobj.setExtendattribute(xml);
		upobj.setObjecttype(type);
		upobj.setInclusion("1");
		upobj.setActive(active);
		return up.UpdateUmsProtecte(upobj);
	}
}
