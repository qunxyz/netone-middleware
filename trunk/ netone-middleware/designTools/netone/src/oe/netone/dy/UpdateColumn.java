package oe.netone.dy;
 
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyFormColumn;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class UpdateColumn {
 	 //修改字段的
	public String updateColumn(List list,String formid){
		List  list1=null;
		UpdateColumn  updateColumn=new UpdateColumn();
		try {
			list1=updateColumn.getc(formid);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
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
		List list2=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			list2.add(object);
		}
		List list3=new ArrayList();
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			Object  object= (Object) iterator.next();
			list3.add(object);
		}

		for (int i=0; i <list.size(); i++) {
			Map map = (Map) list.get(i);
			String Columname = (String) map.get("Columname");
			String Columcode = (String) map.get("Columid");
			String Columid = (String) map.get("Columid");
			String Formcode=null;
			if(StringUtils.isNotEmpty((String) map.get("Formcode"))){
				Formcode= (String) map.get("Formcode");
			}else{
				Formcode=formid;
			}
			String Viewtype = (String) map.get("Viewtype");
			String Extendattribute = (String) map.get("property");
			String valuelist = (String) map.get("valuelist");
			String Musk = (String) map.get("Musk");
			String opemode = (String) map.get("opemode");
			String conceal = (String) map.get("conceal");
			System.out.println(Columname);
			boolean useable = false;
			if (opemode.equals("是")) {
				opemode = "1";
			}
			if (opemode.equals("否")) {
				opemode = "0";
			}
			if (Musk.equals("是")) {
				Musk = "1";
			}
			if (Musk.equals("否")) {
				Musk = "0";
			}
			if (conceal.equals("是")) {
				useable = true;
			}
			if (conceal.equals("否")) {
				useable = false;
			}
			TCsColumn busColumn  = new TCsColumn();
			busColumn.setColumname(Columname);
			busColumn.setColumncode(Columcode);
			busColumn.setColumnid(Columid);
			busColumn.setFormcode(Formcode);
			busColumn.setViewtype(Viewtype);
			busColumn.setExtendattribute(Extendattribute);
			busColumn.setValuelist(valuelist);
			busColumn.setMusk(Musk);
			busColumn.setUseable(useable);
			busColumn.setOpemode(opemode);
			
			try {
				String formcode = Formcode;
				long indexvalue = dys.getNextIndexValue(formcode);
				busColumn.setIndexvalue(new Long(indexvalue));
				String[] checkAndHtml = dys.parseViewType(Viewtype);
				busColumn.setChecktype(checkAndHtml[0]);
				busColumn.setHtmltype(checkAndHtml[1]);
				busColumn.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);				 
		      	for (int j=0;j<list1.size(); j++) {
						TCsColumn tCsColumn = (TCsColumn)list1.get(j);
						if(tCsColumn.getColumnid().equals(Columid)){
							String info = dys.updateColumn(busColumn);
							list2.remove(map);
							list3.remove(tCsColumn);
					      }
			    }
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("修改字段失败");
				e.printStackTrace();
				return "0";
			}
		   }
		//添加字段
         for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			Map  map = (Map) iterator.next();
			try {
				String Columname = (String) map.get("Columname");
				String Columcode = (String) map.get("Columid");
				String Columid = (String) map.get("Columid");
				String Formcode=null;
				if(StringUtils.isNotEmpty((String) map.get("Formcode"))){
					Formcode= (String) map.get("Formcode");
				}else{
					Formcode=formid;
				}
				String Viewtype = (String) map.get("Viewtype");
				String Extendattribute = (String) map.get("property");
				String valuelist = (String) map.get("valuelist");
				String Musk = (String) map.get("Musk");
				String opemode = (String) map.get("opemode");
				String conceal = (String) map.get("conceal");
				boolean useable = false;
				if (opemode.equals("是")) {
					opemode = "1";
				}
				if (opemode.equals("否")) {
					opemode = "0";
				}
				if (Musk.equals("是")) {
					Musk = "1";
				}
				if (Musk.equals("否")) {
					Musk = "0";
				}
				if (conceal.equals("是")) {
					useable = true;
				}
				if (conceal.equals("否")) {
					useable = false;
				}
				TCsColumn busColumn  = new TCsColumn();
				busColumn.setColumname(Columname);
				busColumn.setColumncode(Columcode);
				busColumn.setColumnid(Columid);
				busColumn.setFormcode(Formcode);
				busColumn.setViewtype(Viewtype);
				busColumn.setExtendattribute(Extendattribute);
				busColumn.setValuelist(valuelist);
				busColumn.setMusk(Musk);
				busColumn.setUseable(useable);
				busColumn.setOpemode(opemode);
				
				String formcode = Formcode;
				long indexvalue = dys.getNextIndexValue(formcode);
				busColumn.setIndexvalue(new Long(indexvalue));
				String[] checkAndHtml = dys.parseViewType(Viewtype);
				busColumn.setChecktype(checkAndHtml[0]);
				busColumn.setHtmltype(checkAndHtml[1]);
				String newColumnid = dys.getNextColumn(formcode);
				busColumn.setColumnid(newColumnid);
				busColumn.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);
				busColumn.setParticipant("newdesign");
				String info = dys.addColumn(busColumn);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("添加字段失败");
				return "0";
			}
		}	  
	   //删除字段  
         for (Iterator iterator = list3.iterator(); iterator.hasNext();) {
        	TCsColumn tCsColumn = (TCsColumn) iterator.next();
        	try {
				String info = dys.dropColumn(tCsColumn);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("删除字段失败");
				return "0";
			}
		}
		 return "1";
 	}
	//删除字段添加的
//	public String updateColumn(List list,String formid){
//		
//		DyFormDesignService dys = null;
//		try {
//			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (RemoteException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (NotBoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
// 
//		List  list1=null;
//		UpdateColumn  updateColumn=new UpdateColumn();
//		try {
//			list1=updateColumn.getc(formid);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		//删除字段  
//      for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
//     	TCsColumn tCsColumn = (TCsColumn) iterator.next();
//     	try {
//				String info = dys.dropColumn(tCsColumn);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("删除字段失败");
//				return "0";
//			}
//		}
//        AddColumn addColumn=new AddColumn();
//        String str=addColumn.AddColmu(list);
// 	  
//      return str;
//	}
	public List getc(String formid) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		List aList = dysc.fetchColumnList(formid);
		List listx=new ArrayList();
		//过滤了belongx与TIMEX字段
		for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
		 	if(object.getColumnid().toUpperCase().equals("BELONGX") || object.getColumnid().toUpperCase().equals("TIMEX") ){
		 	}else{
		 		listx.add(object);
		 	}
		}
		return listx;
	}
}
