/**
 * CupmServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

public class CupmServiceServiceLocator extends org.apache.axis.client.Service implements oe.security3a.client.webservice.CupmServiceService {

    public CupmServiceServiceLocator() {
    }


    public CupmServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CupmServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CupmService
    private java.lang.String CupmService_address = "http://soft.fjycit.com/Security3A/services/CupmService";

    public java.lang.String getCupmServiceAddress() {
        return CupmService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CupmServiceWSDDServiceName = "CupmService";

    public java.lang.String getCupmServiceWSDDServiceName() {
        return CupmServiceWSDDServiceName;
    }

    public void setCupmServiceWSDDServiceName(java.lang.String name) {
        CupmServiceWSDDServiceName = name;
    }

    public oe.security3a.client.webservice.CupmService getCupmService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CupmService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCupmService(endpoint);
    }

    public oe.security3a.client.webservice.CupmService getCupmService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            oe.security3a.client.webservice.CupmServiceSoapBindingStub _stub = new oe.security3a.client.webservice.CupmServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCupmServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCupmServiceEndpointAddress(java.lang.String address) {
        CupmService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (oe.security3a.client.webservice.CupmService.class.isAssignableFrom(serviceEndpointInterface)) {
                oe.security3a.client.webservice.CupmServiceSoapBindingStub _stub = new oe.security3a.client.webservice.CupmServiceSoapBindingStub(new java.net.URL(CupmService_address), this);
                _stub.setPortName(getCupmServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CupmService".equals(inputPortName)) {
            return getCupmService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:CupmService", "CupmServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:CupmService", "CupmService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CupmService".equals(portName)) {
            setCupmServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
