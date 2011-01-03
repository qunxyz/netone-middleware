package oe.product.fck.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;



public class ResourceMgrImpl implements IResourceMgr {

	public String CreateResource() throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject addobj = new UmsProtectedobject();
		String newid="";
		addobj.setName(IdServer.uuid());
		addobj.setNaturalname(IdServer.uuid());
		addobj.setInclusion("0");
		newid =  (String) resourceRmi.addResource(addobj, "FCK.FCK.TEMP");
//		System.out.println("添加成功，该资源是");
//		System.out.println(newid);
		return newid;
	}
	
	

	

	/**
	 * 指定资源的必要属性，增加一个资源
	 * @return 资源ID
	 */
	public String CreateResource(String name, String naturalname,
			String inclusion, String path) throws MalformedURLException,
			RemoteException, NotBoundException {
		
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject addobj = new UmsProtectedobject();
		String resourceID = "";
		addobj.setName(name);
		addobj.setNaturalname(naturalname);
		addobj.setInclusion(inclusion);
		resourceID =   (String) resourceRmi.addResource(addobj, path);
		return resourceID;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub.
		
		IResourceMgr newResource = new ResourceMgrImpl();
		try {
			newResource.CreateResource();
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
		
//		
//		for(int i=0;i<100;i++){
//			System.out.println(System.currentTimeMillis());	
//		}
//		

	}
	


}
