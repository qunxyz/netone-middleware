package oe.midware.workflow.util;

/**
 * Provides string constants for W3C names and URIs.
 *
 * @author Adrian Price
 */
public interface W3CNames {
    /** XML SOAP namespace URI. */
    String SOAP_URI = "http://schemas.xmlsoap.org/wsdl/soap/";

    /** WSDL namespace URI */
    String WSDL_URI = "http://schemas.xmlsoap.org/wsdl/";

    /** XML Schema namespace URI. */
    String XSD_URI = "http://www.w3.org/2001/XMLSchema";

    /** XML Schema-instance namespace URI. */
    String XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";

    /** XML Stylesheet namespace URI. */
    String XSL_URI = "http://www.w3.org/1999/XSL/Transform";

    /** XML Namespace prefix to use for XSD elements. */
    String XSD_NS_PREFIX = "xsd";

    /** XML Namespace prefix to use for XSI elements. */
    String XSI_NS_PREFIX = "xsi";

    /** XML Namespace prefix to use for XSL elements. */
    String XSL_NS_PREFIX = "xsl";

    /** XML Schema document element tag name. */
    String XSD_SCHEMA = "schema";

    /** XSI schema location attribute tag name. */
    String XSI_SCHEMA_LOCATION = "schemaLocation";

    /** XSI 'no-namespace' schema location attribute tag name. */
    String XSI_NO_NS_SCHEMA_LOCATION = "noNamespaceSchemaLocation";
}