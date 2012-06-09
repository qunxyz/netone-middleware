package oe.netone.dy;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.jl.common.workflow.DbTools;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class AddColumn {

	public AddColumn() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */

	public String AddColmu(List list) {
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				String Columname = (String) map.get("Columname");
				String Columcode = (String) map.get("Columid");
				String Columid = (String) map.get("Columid");
				String Formcode = (String) map.get("Formcode");
				String Viewtype = (String) map.get("Viewtype");
				String Extendattribute = (String) map.get("property");
				String valuelist = (String) map.get("valuelist");
				String Musk = (String) map.get("Musk");
				String opemode = (String) map.get("opemode");
				String conceal = (String) map.get("conceal");
				System.out.println(Columname);
				// �����?
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

				TCsColumn busForm = new TCsColumn();
				busForm.setColumname(Columname);
				busForm.setColumncode(Columcode);
				busForm.setColumnid(Columid);
				busForm.setFormcode(Formcode);
				busForm.setViewtype(Viewtype);
				busForm.setExtendattribute(Extendattribute);
				busForm.setValuelist(valuelist);
				busForm.setMusk(Musk);
				busForm.setUseable(useable);
				busForm.setOpemode(opemode);
				
				String formcode = Formcode;
				long indexvalue = dys.getNextIndexValue(formcode);
				String newColumnid = dys.getNextColumn(formcode);
				busForm.setColumnid(newColumnid);
				busForm.setIndexvalue(new Long(indexvalue));
				String[] checkAndHtml = dys.parseViewType(Viewtype);

				busForm.setChecktype(checkAndHtml[0]);
				busForm.setHtmltype(checkAndHtml[1]);
				busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);
				busForm.setParticipant("newdesign");
				String info = dys.addColumn(busForm);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("添加字段出错");
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	public void NewlyPower(String naturalname, String name, String pid) {
		ResourceRmi rsrmi = null;
		Serializable idcreated = null;
		try {
			// 读取名为resource的rmi服务
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		UmsProtectedobject upo = null;
		try {

			upo = rsrmi.loadResourceByNatural(naturalname);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			idcreated = rsrmi.addResource(upo, pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
