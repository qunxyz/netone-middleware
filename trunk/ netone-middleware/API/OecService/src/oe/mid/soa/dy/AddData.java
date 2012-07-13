package oe.mid.soa.dy;

import java.sql.Timestamp;
import java.util.UUID;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.orm.util.IdServer;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class AddData {
public static void main(String[] args) throws Exception{
	DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
	TCsBus bus=new TCsBus();
	bus.setBelongx("DEPT.DEPT");
	bus.setCreated((new Timestamp(System.currentTimeMillis()).toString()));
	bus.setStatusinfo("00");
	bus.setParticipant("aaaa");
	bus.setLsh(UUID.randomUUID().toString().replace("-",""));
	bus.setFatherlsh("1");
	bus.setTimex((new Timestamp(System.currentTimeMillis()).toString()));
	bus.setFormcode("ae83340bb52f11e19acf75983f601bc3_");
	String lsh=dy.addData("ae83340bb52f11e19acf75983f601bc3_", bus);

	
	System.out.println(lsh);
	
//	List list = dy.fetchColumnList("23f307f0bd6111dd9c573f3e864d2bf2_");
}
}
