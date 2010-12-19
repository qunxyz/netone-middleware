/*
 *  Copyright (C) 2002-2003 Aetrion LLC.
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions, and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions, and the disclaimer that follows
 *  these conditions in the documentation and/or other materials
 *  provided with the distribution.
 *
 *  3. The names "OBE" and "Open Business Engine" must not be used to
 *  endorse or promote products derived from this software without prior
 *  written permission.  For written permission, please contact
 *  obe@aetrion.com.
 *
 *  4. Products derived from this software may not be called "OBE" or
 *  "Open Business Engine", nor may "OBE" or "Open Business Engine"
 *  appear in their name, without prior written permission from
 *  Aetrion LLC (obe@aetrion.com).
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 *  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 *  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 *  STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 *  IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 *  For more information on OBE, please see
 *  <http://www.openbusinessengine.org/>.
 */
package oe.midware.workflow.xpdl.model.misc;

import oe.midware.workflow.util.AbstractBean;

/**
 * Abstract implementation of the WFElement interface which implements base
 * functionality used by all WFElement implementations. This class is not
 * threadsafe.
 *
 * @author Adrian Price
 */
public class AbstractWFElement extends AbstractBean {
    static final long serialVersionUID = 5028805438136004652L;

    private String _id;
    private String _name;
    private String _description;
    private ExtendedAttributes _extendedAttributes;

    protected AbstractWFElement() {
    }

    protected AbstractWFElement(String[] indexedPropertyNames,
        Object[] initalValues) {

        super(indexedPropertyNames, initalValues);
    }

    public AbstractWFElement(String[] indexedPropertyNames,
        Object[] initalValues, String id, String name) {

        super(indexedPropertyNames, initalValues);
        _id = id;
        _name = name;
    }

    /**
     * Constructs a new AbstractWFElement.
     *
     * @param id    The element ID
     * @param name  The element name
     */
    protected AbstractWFElement(String id, String name) {
        setId(id);
        setName(name);
    }

    public final String getId() {
        return _id;
    }

    public final void setId(String id) {
        if (id == null)
            throw new IllegalArgumentException("ID cannot be null");
        _id = id;
    }

    public final String getName() {
        return _name;
    }

    public final void setName(String name) {
        _name = name;
    }

    public final String getDescription() {
        return _description;
    }

    public final void setDescription(String description) {
        _description = description;
    }

    public final ExtendedAttributes createExtendedAttributes() {
        if (_extendedAttributes == null)
            _extendedAttributes = new ExtendedAttributes();
        return _extendedAttributes;
    }

    public final ExtendedAttributes getExtendedAttributes() {
        return _extendedAttributes;
    }

    public final void setExtendedAttributes(ExtendedAttributes extAttrs) {
        _extendedAttributes = extAttrs;
    }

    public int hashCode() {
        return _id.hashCode();
    }

    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() &&
            _id.equals(((AbstractWFElement)obj)._id);
    }

    public String toString() {
        return "AbstractWFElement[id=" + getId() +
            ", name=" + getName() +
            ", description=" + getDescription() +
            ", extendedAttributes=" + getExtendedAttributes() +
            ']';
    }
}