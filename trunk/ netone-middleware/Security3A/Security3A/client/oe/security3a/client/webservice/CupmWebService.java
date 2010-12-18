/**
 * CupmWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

public interface CupmWebService extends javax.xml.rpc.Service {
    public java.lang.String getCupmWebAddress();

    public CupmWeb getCupmWeb() throws javax.xml.rpc.ServiceException;

    public CupmWeb getCupmWeb(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
