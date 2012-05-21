package oe.netone.bi.pa;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.netone.app.ResourData;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class ProcessNode {
	public List<ResourData> QueryNode(String flowid) {
		List lisxt = new ArrayList();
		ResourceRmi resourceRmi = null;
		try {
			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");
				resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WorkflowProcess list = wfview.fetchWorkflowProcess(flowid);
			String obl = null;
			Activity[] act = list.getActivity();
			for (int i = 0; i < act.length; i++) {
				if (act[i].getImplementation() != null) {// 空节点不需要进入
					UmsProtectedobject upobj = new UmsProtectedobject();
					upobj.setNaturalname(flowid + "."
							+ act[i].getId().toUpperCase());

					UmsProtectedobject upo = resourceRmi
							.loadResourceByNatural(flowid + "."
									+ act[i].getId().toUpperCase());
					String gea = upo.getExtendattribute();

					if (gea == null) {
						upobj.setObjecttype("");
					} else {
						XMLAnalysis xmlAnalysis = new XMLAnalysis();
						Allocation allocation = xmlAnalysis.readXML(gea);
						upobj.setObjecttype(allocation.getModeltype());
					}

					upobj.setName(act[i].getName());
					lisxt.add(upobj);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return lisxt;
	}

	public int Nodestr(String flowid) {
		List lisxt = new ArrayList();
		ResourceRmi resourceRmi = null;

		WorkflowView wfview = null;
		WorkflowProcess list = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			list = wfview.fetchWorkflowProcess(flowid);
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
		Activity[] act = list.getActivity();
		return act.length;
	}

	//解析节点上的配置数据 返回到flex上展示出来
	public List<Allocation> Query(String flowid) {
		List<Allocation> lisxt = new ArrayList<Allocation>();

		Allocation actionAllocation = new Allocation();
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
		UmsProtectedobject upo = null;
		try {
			upo = resourceRmi.loadResourceByNatural(flowid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Extendattribute = upo.getExtendattribute();
		if (Extendattribute != null) {
			XMLAnalysis xmlAnalysis = new XMLAnalysis();
			Allocation allocation = xmlAnalysis.readXML(Extendattribute);
			lisxt.add(allocation);
		}
		System.out.println(Extendattribute);

		return lisxt;
	}

	//解析布局的配置数据 返回到flex上展示出来
	public List<Devise> LayoutQuery(String flowid) {
		List<Devise> lisxt = new ArrayList<Devise>();

		Allocation actionAllocation = new Allocation();
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
		UmsProtectedobject upo = null;
		try {
			upo = resourceRmi.loadResourceByNatural(flowid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Actionurl = upo.getActionurl();
		Actionurl.trim();
		if (Actionurl.equals("")) {
		}else{
			XMLDevise xmldevise = new XMLDevise();
			Devise devise = xmldevise.readXML(Actionurl);
			lisxt.add(devise);
		}
		return lisxt;
	}
}
