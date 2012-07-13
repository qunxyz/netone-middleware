package oe.mid.soa.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class Test {
	
	public static void main(String[] args)throws Exception {
		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
		
		
//		CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
//		cupm.log("dbope", "", "scirptmen", "succ", "select * from t='1'dfds andf ''' sfdsfdsfdsf");
		
//		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
//		Clerk clll=rs.loadClerk("0000", "3_caiwanxing");
//		System.out.println();
//		Clerk cl=new Clerk();
//		cl.setProvince("%AA.AA.BB@%");
//		Map map=new HashMap();
//		map.put("major", "like");
//		List list=rs.queryObjectsClerk("0000", cl, map, 0, 1000);
//		System.out.println();
//		
//		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
//		List list1=view.coreSqlview("select usercode from t_cs_user where major like '%AA.AA.BB@%'");
//		System.out.println();
//		
		
//		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
//		CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
//		List okTeam = new ArrayList();
//		List sub = rs.subResourceByNaturalname("SYSTEAM.SYSTEAM");
//		for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
//			UmsProtectedobject object = (UmsProtectedobject) iterator
//					.next();
//			String name = object.getNaturalname();
//			boolean ok = cupm.checkUserPermission("0000", "admin",
//					name, "3");
//			if (ok) {
//				okTeam.add(name);
//			}
//		}
//		StringBuffer but = new StringBuffer("'" + "admin" + "'");
//
//		for (Iterator iterator = okTeam.iterator(); iterator.hasNext();) {
//			String object = (String) iterator.next();
//			Clerk c1 = new Clerk();
//			c1.setProvince("%" + object + "@%");
//			Map map = new HashMap();
//			map.put("major", "like");
//			//后期queryObjectsClerk方法中加入cache来缓存 读取用户的性能
//			List list = rs.queryObjectsClerk("0000", c1, map, 0, 1000);
//			for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
//				Clerk object2 = (Clerk) iterator2.next();
//				but.append(",'" + object2.getDescription() + "'");
//			}
//		}
//
//		//objx.setParticipant(but.toString());
//		
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
	
		TCsBus bus=new TCsBus();
		bus.setFormcode("981cdceec7ae11e08880b10a69337383_");
		bus.setFatherlsh("1");
		bus.setParticipant("admin");
		//bus.setColumn3("fdsf");
		//bus.setParticipant(")
		List listx=dy.queryData(bus,0,10,"");
		System.out.println();
	}

}
