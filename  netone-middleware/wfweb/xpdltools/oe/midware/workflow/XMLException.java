package oe.midware.workflow;

/**
 * Thrown by XML processing methods for a variety of parser & transformer
 * problems.
 * @author Adrian Price
 * @version 
 */
public class XMLException extends XpdlException {
    static final long serialVersionUID = 8239694101248128531L;

    private XMLException() {
    }

    public XMLException(String message) {
        super(message);
    }

    public XMLException(Throwable t) {
        super(t);
    }
}