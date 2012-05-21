package oe.netone.app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.netone.bi.DataBI;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.UmsProtecte;

public class AddCatalog {
	public String Str;
	public List lists;
	public String xmldata;

	public String AddCatalog(String naturalname, String name,
			String extendattribute, String parentdir) {
		Serializable idcreated = null;
		UmsProtecte up = new UmsProtecte();
		String cString = "<?xml version='1.0' encoding='UTF-8'?>";
		extendattribute = extendattribute.replace("&lt;", "<");
		extendattribute = cString + extendattribute.replace("&gt;", ">");

		DomXml domxml = new DomXml();
		List<Person> list1 = domxml.readXML(extendattribute);
		String str = list1.get(0).getProcessid() + ";"
				+ list1.get(0).getFormcode();

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(naturalname);
		upo.setName(name);
		upo.setActionurl("");
		upo.setExtendattribute(extendattribute);
		upo.setObjecttype("default");
		upo.setDescription("");
		StringUtils.substringBetween(list1.get(0).getProcessid(), "[", "]");
		try {
			idcreated = up.addUmsProtecte(upo, parentdir);
			Query(idcreated.toString());
			step2(idcreated.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcreated.toString();
	}

	private List step2(String idcreated) {
		final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
				"busstype", "busstip" };
		List lisxt = new ArrayList();
		String id = idcreated;
		String processid = "";
		String formcode = "";
		UmsProtecte up = new UmsProtecte();
		try {

			UmsProtectedobject upo = up.loadUmsProtecteID(id);
			formcode = upo.getExtendattribute();
			DomXml domxml = new DomXml();
			List<Person> list1 = domxml.readXML(formcode);

			formcode = StringUtils.substringBetween(list1.get(0).getFormcode(),
					"[", "]");

			processid = StringUtils.substringBetween(list1.get(0)
					.getProcessid(), "[", "]");

			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WorkflowProcess list = wfview.fetchWorkflowProcess(processid);
			DataField[] data = list.getDataField();
			for (int i = 0; i < data.length; i++) {
				String fileid = data[i].getId();
				boolean isKeyvar = false;
				for (int j = 0; j < _CORE_KEY_VAR.length; j++) {
					// 需要忽略掉流程内部关键变量
					if (_CORE_KEY_VAR[j].equalsIgnoreCase(fileid)) {
						isKeyvar = true;
						break;
					}
				}
				if (isKeyvar)
					continue;
				if (fileid.startsWith("r_")) {
					// 说明是内部路由变量忽略
					continue;
				}
				UmsProtectedobject upobj = new UmsProtectedobject();
				upobj.setNaturalname(fileid);
				upobj.setName(data[i].getName());
				upobj.setParentdir(id);
				upobj.setDescription("var");
				upobj.setAppid(upo.getAppid());
				upobj.setExtendattribute(null);
				try {
					up.addUmsProtecte(upobj);
					lisxt.add(upobj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lisxt;
	}

	public List<ResourData> Query(String idcreated) {
		List lisxt = new ArrayList();
		String id = idcreated;
		String processid = "";
		UmsProtecte up = new UmsProtecte();
		try {
			UmsProtectedobject upo = up.loadUmsProtecteID(id);

			String extendattribute = upo.getExtendattribute();
			DomXml domxml = new DomXml();
			List<Person> list1 = domxml.readXML(extendattribute);

			if (extendattribute != null && !extendattribute.equals("")) {
				processid = StringUtils.substringBetween(list1.get(0)
						.getProcessid(), "[", "]");
			}
			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WorkflowProcess list = wfview.fetchWorkflowProcess(processid);
			Activity[] act = list.getActivity();
			for (int i = 0; i < act.length; i++) {
				if (act[i].getImplementation() != null) {// 空节点不需要进入
					UmsProtectedobject upobj = new UmsProtectedobject();
					upobj.setNaturalname(act[i].getId());
					upobj.setName(act[i].getName());
					upobj.setParentdir(id);
					upobj.setObjecttype("1");
					upobj.setAppid(upo.getAppid());
					upobj.setDescription("pt");
					up.addUmsProtecte(upobj);
					// resourceRmi.
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return lisxt;
	}

	public String Updata(String id, String extendattribute, String name) {
		Serializable idcreated = null;
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upj = null;
		UmsProtectedobject updataupj = null;
		String cString = "<?xml version='1.0' encoding='UTF-8'?>";
		String aString = extendattribute.replace("&lt;", "<");
		extendattribute = cString + aString.replace("&gt;", ">");
		upj = up.loadUmsProtecteID(id);
		updataupj = up.loadUmsProtecteNaturalname(upj.getNaturalname());
		updataupj.setExtendattribute(extendattribute);
		updataupj.setName(name);
		try {
			idcreated = up.UpdateUmsProtecte(updataupj);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query(updataupj.getId());
		step2(updataupj.getId());
		return updataupj.getId();
	}

}
