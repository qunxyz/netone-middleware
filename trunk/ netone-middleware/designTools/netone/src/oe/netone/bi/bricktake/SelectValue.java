package oe.netone.bi.bricktake;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.netone.bi.ConnDB;
import oe.netone.bi.PAnalysis;
import oe.netone.bi.gd.GraphicXML;
import oe.netone.bi.gd.ObjectGraph;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;



public class SelectValue{
	
	public List Select(String naturalname){
		ObjectGraph OGXML = null;
		ResourceRmi resourceRmi = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsProtectedobject upof = null;
		try {
			upof = resourceRmi.loadResourceByNatural(naturalname.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String obl = upof.getExtendattribute();
		if (obl != "" || obl != null) {
			GraphicXML GX = new GraphicXML();
			obl = obl.trim();
			OGXML = GX.readXML(obl);
		}
		String Formcode = OGXML.getFormcode();
		String xData = OGXML.getZhankaiX();
		String tablename;
		TCsForm busForm = null;
		try {
			DyFormDesignService dys = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			ResourceInfo rsInfo = new ResourceInfo();
			busForm = dysc.loadForm(Formcode);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tablename = busForm.getTablename();
	 	String sqlString = null;
		List dtList = new ArrayList();
	 
	     sqlString = "select "+xData+ " from dyform." + tablename;
		 
 
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				dtList.add(rSet.getString(1));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connDB.close();
		return dtList;
	}
	
	
}