/**
 * CupmWeb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

public interface CupmWeb extends java.rmi.Remote {
	public void main(java.lang.String[] args) throws java.rmi.RemoteException;

	public boolean log(java.lang.String dnname, java.lang.String ip,
			java.lang.String userid, java.lang.String rsinfo,
			java.lang.String remark) throws java.rmi.RemoteException;

	public java.lang.String todo(java.lang.String request)
			throws java.rmi.RemoteException;

	public boolean checkRolePermission(java.lang.String rolename,
			java.lang.String dnname, java.lang.String action)
			throws java.rmi.RemoteException;

	public boolean checkUserPermission(java.lang.String loginname,
			java.lang.String dnname, java.lang.String action)
			throws java.rmi.RemoteException;

	public void initCacheall() throws java.rmi.RemoteException;

	public void initRoleCache(java.lang.String roleid)
			throws java.rmi.RemoteException;

	public void initUserCache(java.lang.String userid)
			throws java.rmi.RemoteException;

	public boolean checkRolePermissionCore(java.lang.String roleid,
			java.lang.String ou, java.lang.String action)
			throws java.rmi.RemoteException;

	public boolean checkUserPermissionCore(java.lang.String loginname,
			java.lang.String ou, java.lang.String action)
			throws java.rmi.RemoteException;
}
