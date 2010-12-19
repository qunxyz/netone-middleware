/*--

 Copyright (C) 2002-2003 Aetrion LLC.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	obe@aetrion.com.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Aetrion LLC (obe@aetrion.com).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://www.openbusinessengine.org/>.

 */

package oe.midware.workflow.xpdl.model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.jaxen.JaxenException;
import org.jaxen.SimpleVariableContext;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import oe.midware.workflow.XpdlRuntimeException;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.util.ClassUtils;
import oe.midware.workflow.util.SchemaUtils;
import oe.midware.workflow.util.W3CNames;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Reference to an external entity as defined in XPDL 1.0b1.
 *
 * @author Adrian Price
 */
public final class ExternalReference implements Type, Serializable {
   
	static final long serialVersionUID = -5052512021996360394L;
    private static final String WSDL_SCHEMA_XPATH =
        "/wsdl:definitions/wsdl:types/xsd:schema[@targetNamespace=$namespace]";
    private static final DocumentBuilderFactory _docBuilderFactory =
        DocumentBuilderFactory.newInstance();

    private String _location;
    private String _xref;
    private String _namespace;
    private transient QName _qname;
    private transient Type _xpdlType;

    static {
        _docBuilderFactory.setNamespaceAware(true);
        _docBuilderFactory.setIgnoringComments(true);
        _docBuilderFactory.setValidating(false);
    }

    public ExternalReference() {
    }

    /**
     * Construct a new ExternalReference.
     *
     * @param location The location
     */
    public ExternalReference(String location) {
        setLocation(location);
    }

    /**
     * Construct a new ExternalReference.
     *
     * @param location The location
     * @param xref
     * @param namespace
     */
    public ExternalReference(String location, String xref, String namespace) {
        _location = location;
        _xref = xref;
        _namespace = namespace;
    }

    public int value() {
        return EXTERNAL_REFERENCE_TYPE;
    }

    /**
     * Get the location URI.
     *
     * @return The location URI
     */
    public String getLocation() {
        return _location;
    }

    /**
     * Set the location URI.
     *
     * @param location The new location URI
     */
    public void setLocation(String location) {
        if (location == null)
            throw new IllegalArgumentException("location cannot be null");
        _location = location;
    }

    /**
     * Get the identity of the entity in the external reference.  This value is
     * optional and this method may return null.
     *
     * @return The entity identity
     */
    public String getXref() {
        return _xref;
    }

    /**
     * Set the identity of the entity in the external reference.  This value is
     * optional and can be set to null.
     *
     * @param xref The entity identity
     */
    public void setXref(String xref) {
        _xref = xref;
        _qname = null;
    }

    /**
     * Get the namespace of the external reference.  The value is optional and
     * this method may return null.
     *
     * @return The namespace
     */
    public String getNamespace() {
        return _namespace;
    }

    /**
     * Set the namespace of the external reference.  This value is optional and
     * can be set to null.
     *
     * @param namespace The namespace
     */
    public void setNamespace(String namespace) {
        _namespace = namespace;
        _qname = null;
    }

    public QName getQName() {
        if (_qname == null)
            _qname = new QName(_namespace, _xref);
        return _qname;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ExternalReference))
            return false;

        final ExternalReference externalReference = (ExternalReference)o;

        if (!_location.equals(externalReference._location))
            return false;
        if (_namespace != null
            ? !_namespace.equals(externalReference._namespace)
            : externalReference._namespace != null) {

            return false;
        }
        if (_xref != null
            ? !_xref.equals(externalReference._xref)
            : externalReference._xref != null) {

            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = _location.hashCode();
        result = 29 * result + (_xref != null ? _xref.hashCode() : 0);
        result = 29 * result + (_namespace != null ? _namespace.hashCode() : 0);
        return result;
    }

    public Type getImpliedType() {
        if (_xpdlType == null) {
            try {
                if (_location.startsWith("java:")) {
                    resolveJavaReference();
                } else if (_location.endsWith(".wsdl")) {
                    resolveWSDLReference();
                } else if (_location.endsWith(".xsd")) {
                    resolveSchemaReference();
                } else {
                    _xpdlType = this;
                }
            } catch (ClassNotFoundException e) {
                throw new XpdlRuntimeException(e);
            } catch (IOException e) {
                throw new XpdlRuntimeException(e);
            } catch (JaxenException e) {
                throw new XpdlRuntimeException(e);
            } catch (ParserConfigurationException e) {
                throw new XpdlRuntimeException(e);
            } catch (SAXException e) {
                throw new XpdlRuntimeException(e);
            } catch (TransformerException e) {
                throw new XpdlRuntimeException(e);
            } catch (XMLException e) {
                throw new XpdlRuntimeException(e);
            }
        }
        return _xpdlType;
    }

    private void resolveJavaReference() throws ClassNotFoundException {
        Class javaClass = ClassUtils.classForName(_location.substring(5));
        Type type = DataTypes.dataTypeForClass(javaClass).getType();
        _xpdlType = type instanceof ExternalReference ? this : type;
    }

    private void resolveSchemaReference()
        throws IOException, XMLException, TransformerException {

        // Map the schema type to a Java class.
        Source src = null;
        Class javaType = null;
        try {
            QName typeName = getQName();
            src = SchemaUtils.getURIResolver().resolve(_location, null);
            if (src == null) {
                throw new XMLException("Could not resolve reference '" +
                    _location + '\'');
            }

            if (src instanceof DOMSource) {
                DOMSource domSource = ((DOMSource)src);
                javaType = SchemaUtils.classForSchemaType(domSource.getNode(),
                    typeName);
            } else if (src instanceof StreamSource) {
                StreamSource ss = ((StreamSource)src);
                InputStream in = ss.getInputStream();
                if (in != null) {
                    javaType = SchemaUtils.classForSchemaType(in, typeName);
                } else {
                    Reader rdr = ss.getReader();
                    if (rdr != null)
                        javaType = SchemaUtils.classForSchemaType(rdr, typeName);
                }
            }
        } finally {
            SchemaUtils.close(src);
        }

        // Map a non-XML Java class to an XPDL data type.
        _xpdlType = DataTypes.dataTypeForClass(javaType).getType();
    }

    private void resolveWSDLReference() throws SAXException, IOException,
        ParserConfigurationException, JaxenException, XMLException {

        // Retrieve the WSDL and parse it into a DOM document.
        Document wsdlDoc;
        InputSource in = null;
        try {
            in = SchemaUtils.getEntityResolver().resolveEntity(null, _location);
            wsdlDoc = _docBuilderFactory.newDocumentBuilder().parse(in);
        } finally {
            SchemaUtils.close(in);
        }

        // Use XPath to select the appropriate schema element.
        XPath xpath = new DOMXPath(WSDL_SCHEMA_XPATH);
        xpath.addNamespace("wsdl", W3CNames.WSDL_URI);
        xpath.addNamespace("xsd", W3CNames.XSD_URI);
        SimpleVariableContext varCtx = new SimpleVariableContext();
        varCtx.setVariableValue("namespace", _namespace);
        xpath.setVariableContext(varCtx);
        Node node = (Node)xpath.selectSingleNode(wsdlDoc);

        // Examine the schema to determine the Java class which matches the type
        // referenced by this external reference.
        if (node == null) {
            throw new XMLException("WSDL file '" + _location +
                "' does not provide a schema for the target namespace '" +
                _namespace + '\'');
        }
        if (node instanceof Node) {
            // Map the XML Schema type to a Java class.
            Class javaType = SchemaUtils.classForSchemaType(node, getQName());

            // Map a non-XML Java class to an XPDL data type.
            _xpdlType = DataTypes.dataTypeForClass(javaType).getType();
        } else {
            _xpdlType = this;
        }
    }

    public boolean isAssignableFrom(Type fromType) {
        return equals(fromType) || getImpliedType() != this &&
            _xpdlType.isAssignableFrom(fromType);
    }

    public String toString() {
        return "ExternalReference[location=" + _location +
            ", namespace=" + _namespace + ", xref=" + _xref + ']';
    }
}