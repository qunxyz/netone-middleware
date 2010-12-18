/**
 * CupmServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

public interface CupmServiceService extends javax.xml.rpc.Service {
    public java.lang.String getCupmServiceAddress();

    public oe.security3a.client.webservice.CupmService getCupmService() throws javax.xml.rpc.ServiceException;

    public oe.security3a.client.webservice.CupmService getCupmService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
