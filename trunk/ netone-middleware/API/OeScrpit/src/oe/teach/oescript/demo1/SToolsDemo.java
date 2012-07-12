package oe.teach.oescript.demo1;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.workflow.engine.rule2.func.STools;

/**
 * 中间件的内部API开发到 script中去，用来增加Scrpit的功能
 * @author robanco
 *
 */
public class SToolsDemo {
	
	public static void main(String[] args) throws Exception{

	}
	
	
	/**
	 * say hello
	 * @throws RemoteException 
	 */
	public Object todo1() throws RemoteException{
			
		TCsBus bus=new TCsBus();
		bus.setFatherlsh("$(lsh)");
		bus.setFormcode("$(formcode)");
		STools st=new STools();
		List list=st.dy_.queryData(bus, 0, 1, "");
		TCsBus bux=(TCsBus)list.get(0);


		return null;

	}
	
	public Object todo2() throws Exception{
		STools st=new STools();
//		st.dy_.deleteData("formcode", "lsh");
//		st.cupm_.checkUserPermission("0000", "adminx", "fsdf.sdf.dsfds.", "3");
		
		TCsBus bus=new TCsBus();
		bus.setBelongx("DEPT.DEPT");
		bus.setCreated((new Timestamp(System.currentTimeMillis()).toString()));
		bus.setStatusinfo("00");
		bus.setParticipant("aaaa");
		bus.setLsh(UUID.randomUUID().toString().replace("-",""));
		bus.setFatherlsh("1");
		bus.setTimex((new Timestamp(System.currentTimeMillis()).toString()));
		bus.setFormcode("ae83340bb52f11e19acf75983f601bc3_");
		String lsh=st.dy_.addData("ae83340bb52f11e19acf75983f601bc3_", bus);
		System.out.println(lsh);
		return lsh;
	}

}
