package oe.netone.app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.UmsProtecte;

public class AddApplication {

	public String addAppliString(String naturalname, String name,
			String extendattribute,String parentdir,String url,String name1,String naturalname1,String Toread) {
		Serializable idcreated = null;
		naturalname=StringUtils.substringBetween(naturalname,"[","]");
		String url1=url+"workList.do?method=onMainView2&mode=1&height=260&listtype=00&sortfield=&sort=&psize=12&appname="+StringUtils.substringBetween(name, "[", "]");
		String url2=url+"workList.do?method=onMainView2&mode=1&height=260&listtype=01&sortfield=&sort=&psize=12&appname="+StringUtils.substringBetween(name, "[", "]");
		String url3=url+"workList.do?method=onMainView2&mode=1&height=260&listtype=02&sortfield=&sort=&psize=12&appname="+StringUtils.substringBetween(name, "[", "]");
		String url4=url+"workList.do?method=onMainView2&mode=1&height=260&listtype=03&sortfield=&sort=&psize=12&appname="+StringUtils.substringBetween(name, "[", "]");
		String url5=url+"frame.do?method=onEditViewMain&naturalname="+StringUtils.substringBetween(name, "[", "]");
		String url6=url+"workList.do?method=onMainView2&mode=0&height=430&listtype=01&sortfield=&sort=&psize=20&appname="+StringUtils.substringBetween(name, "[", "]");
       
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(naturalname1);
		upo.setName(name1);
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription(naturalname);
		upo.setActive("1");
		upo.setInclusion("1");
		upo.setActionurl(url1);
		try {
			idcreated = up.addUmsProtecte(upo,naturalname);
			addpzzhiyuan(name1,naturalname1,parentdir,extendattribute,naturalname);
			addXXFKYCL(naturalname+"."+naturalname1,url2);
			addYBRW(naturalname+"."+naturalname1,url3);
			addALLDONE(naturalname+"."+naturalname1,url4);
			addALL(naturalname+"."+naturalname1,url1);
			addNEW(naturalname+"."+naturalname1,url5);
			if(Toread.trim().equals("1")){
				daiyue(naturalname+"."+naturalname1,url6);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcreated.toString();
	}
	public void addpzzhiyuan(String name1,String naturalname1,String parentdir,String extendattribute,String id) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte(); 
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(naturalname1);
		upo.setName(name1);
		upo.setActionurl("");
		upo.setExtendattribute(extendattribute);
		upo.setObjecttype(naturalname1);
		upo.setDescription(id);
		upo.setActive("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addXXFKYCL(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte(); 
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("XXFKYCL");
		upo.setName("待办流程");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addYBRW(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("YBRW");
		upo.setName("已处理流程");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addALLDONE(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("ALLDONE");
		upo.setName("已办结流程");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addALL(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("ALL");
		upo.setName("所有流程");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addNEW(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("NEW");
		upo.setName("新建");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void daiyue(String parentdir,String url) {
		Serializable idcreated = null;
		UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname("DAIYUE");
		upo.setName("待阅流程");
		upo.setActionurl("");
		upo.setExtendattribute("final");
		upo.setObjecttype("BUSSENV.BUSSENV.BUSSFRAME");
		upo.setDescription("");
		upo.setActionurl(url);
		upo.setActive("1");
		upo.setInclusion("1");
		try {
			idcreated = up.addUmsProtecte(upo,parentdir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
