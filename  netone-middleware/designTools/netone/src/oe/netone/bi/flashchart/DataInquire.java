package oe.netone.bi.flashchart;

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
import oe.netone.bi.xyData;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;



public class DataInquire {

	
	public static List ZhuTiBIzhibiao(String xdata, List zbList,String Formcode,
			String limit, String limitcolumn,String childrenstring) throws MalformedURLException, RemoteException, NotBoundException {
			if(childrenstring==null){
				childrenstring="";
			}
		    String	tablename;
		    DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			ResourceInfo rsInfo = new ResourceInfo();
			TCsForm busForm = dysc.loadForm(Formcode);
			 tablename=busForm.getTablename();
				String str = null;
				int j = 1;
				StringBuffer fdBuffer = new StringBuffer();
				String sqlString = null;
				ArrayList bArrayList = new ArrayList();
				List list = new ArrayList();
				for (int i = 0; i < zbList.size(); i++) {
					if (i != zbList.size() - 1) {

						fdBuffer.append(zbList.get(i));
						fdBuffer.append(",");

					} else {
						j = i + 2;
						fdBuffer.append(zbList.get(i));
					}
				}
				if(limit.equals("")){
				sqlString = "select " + xdata + "," + fdBuffer.toString()
			    +" from dyform."+tablename +  "";
				}else{
				sqlString = "select  "+xdata+" ," + fdBuffer.toString() + " from dyform."
				+tablename + " where "+limitcolumn+"='"+limit+"'"+childrenstring+" order by TIMEX";
				}
				System.out.println("......" + sqlString);
				ConnDB connDB = new ConnDB();
				ResultSet rSet = connDB.doQuery(sqlString);
				try {
					while (rSet.next()) {
						if (j == 1) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							list.add(xyobjcect);
						}
						if (j == 2) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							list.add(xyobjcect);
						}
						if (j == 3) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							list.add(xyobjcect);
						}
						if (j == 4) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							list.add(xyobjcect);
						}
						if (j == 5) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							xyobjcect.setY3data(rSet.getString(5));
							list.add(xyobjcect);
						}
						if (j == 6) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							xyobjcect.setY3data(rSet.getString(5));
							xyobjcect.setY4data(rSet.getString(6));
							list.add(xyobjcect);
						}
						if (j == 7) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							xyobjcect.setY3data(rSet.getString(5));
							xyobjcect.setY4data(rSet.getString(6));
							xyobjcect.setY5data(rSet.getString(7));
							list.add(xyobjcect);
						}
						if (j == 8) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							xyobjcect.setY3data(rSet.getString(5));
							xyobjcect.setY4data(rSet.getString(6));
							xyobjcect.setY5data(rSet.getString(7));
							xyobjcect.setY6data(rSet.getString(8));
							list.add(xyobjcect);
						}
						if (j == 9) {
							xyData xyobjcect = new xyData();
							xyobjcect.setXdata(rSet.getString(1));
							xyobjcect.setYdata(rSet.getString(2));
							xyobjcect.setY1data(rSet.getString(3));
							xyobjcect.setY2data(rSet.getString(4));
							xyobjcect.setY3data(rSet.getString(5));
							xyobjcect.setY4data(rSet.getString(6));
							xyobjcect.setY5data(rSet.getString(7));
							xyobjcect.setY6data(rSet.getString(8));
							xyobjcect.setY7data(rSet.getString(9));
							list.add(xyobjcect);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connDB.close();
				for (int i = 0; i < list.size(); i++) {
					System.out.println(((xyData) list.get(i)).xdata.toString());
				}
				return list;
		}
}
