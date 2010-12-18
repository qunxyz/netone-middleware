/**
 * CupmServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

public class CupmServiceSoapBindingImpl implements
		oe.security3a.client.webservice.CupmService {

	public boolean checkRolePermission(java.lang.String in0,
			java.lang.String in1, java.lang.String in2)
			throws java.rmi.RemoteException {
		boolean result = false;
		try {
			CupmRmi cupm =  (CupmRmi) RmiEntry.iv("cupm");
			result = cupm.checkRolePermission(in0, in1, in2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
