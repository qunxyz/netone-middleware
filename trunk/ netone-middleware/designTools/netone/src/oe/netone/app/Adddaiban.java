package oe.netone.app;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.UmsProtecte;

public class Adddaiban {
	public void step2(String name, String xml) {
		final String[] _CORE_KEY_VAR = { "customer", "bussid", "bussurl",
				"busstype", "busstip" };
		String cString = "<?xml version='1.0' encoding='UTF-8'?>";
		xml = cString + xml;
		xml = xml.replace("&lt;", "<");
		xml = xml.replace("&gt;", ">");

        UmsProtecte up=new UmsProtecte();
		UmsProtectedobject upoj = null;
		upoj = up.loadUmsProtecteNaturalname(name);
		upoj.setExtendattribute(xml);
		up.UpdateUmsProtecte(upoj);
	}
}
