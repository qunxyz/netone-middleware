package oe.netone.app;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;

public class NaturalnmeErgodic {
	public static void main(String[] args) {
		NaturalnmeErgodic nnErgodic = new NaturalnmeErgodic();
		List lsit = nnErgodic.Naturalname("FRAMEPG.FRAMEPG.COMAPP");
	}

	public List Naturalname(String naturalname) {
		List lsit = new ArrayList();
		List listx = null;
		ResourceRmi resourceRmi = null;
		try {
			listx = SecurityEntry.iv().listDirRs(naturalname);
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			UmsProtectedobject upjid = null;
			NaturalObject no = new NaturalObject();

			try {
				upjid = resourceRmi.loadResourceByNatural(name
						.getResourcecode());
				no.setName(upjid.getName());
				no.setNaturalname(upjid.getNaturalname());
				no.setUrl(upjid.getActionurl());
				lsit.add(no);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return lsit;
	}
}
