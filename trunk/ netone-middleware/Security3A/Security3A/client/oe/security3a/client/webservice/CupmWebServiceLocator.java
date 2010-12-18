/**
 * CupmWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oe.security3a.client.webservice;

public class CupmWebServiceLocator extends org.apache.axis.client.Service implements CupmWebService {

    public CupmWebServiceLocator() {
    }


    public CupmWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CupmWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CupmWeb
    private java.lang.String CupmWeb_address = "http://localhost:8080/axis/CupmWeb.jws";

    public java.lang.String getCupmWebAddress() {
        return CupmWeb_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CupmWebWSDDServiceName = "CupmWeb";

    public java.lang.String getCupmWebWSDDServiceName() {
        return CupmWebWSDDServiceName;
    }

    public void setCupmWebWSDDServiceName(java.lang.String name) {
        CupmWebWSDDServiceName = name;
    }

    public CupmWeb getCupmWeb() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CupmWeb_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCupmWeb(endpoint);
    }

    public CupmWeb getCupmWeb(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CupmWebSoapBindingStub _stub = new CupmWebSoapBindingStub(portAddress, this);
            _stub.setPortName(getCupmWebWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCupmWebEndpointAddress(java.lang.String address) {
        CupmWeb_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (CupmWeb.class.isAssignableFrom(serviceEndpointInterface)) {
                CupmWebSoapBindingStub _stub = new CupmWebSoapBindingStub(new java.net.URL(CupmWeb_address), this);
                _stub.setPortName(getCupmWebWSDDServiceName());
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
        if ("CupmWeb".equals(inputPortName)) {
            return getCupmWeb();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/axis/CupmWeb.jws", "CupmWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost:8080/axis/CupmWeb.jws", "CupmWeb"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CupmWeb".equals(portName)) {
            setCupmWebEndpointAddress(address);
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
