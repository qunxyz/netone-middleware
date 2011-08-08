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

package oe.midware.workflow.xpdl.model.misc;

import oe.midware.workflow.util.AbstractBean;

/**
 * Class representing a script language which is meant to be used for an XPDL
 * expression.
 *
 * @author Adrian Price
 */
public final class Script extends AbstractBean {
    static final long serialVersionUID = 7887734498657291623L;

    private String _type;
    private String _version;
    private String _grammar;

    /**
     * Construct a new Script object.
     *
     * @param type The script type
     */
    public Script(String type) {
        setType(type);
    }

    /**
     * Set the scripting language type.
     *
     * @return The scripting language type
     */
    public String getType() {
        return _type;
    }

    /**
     * Set the scripting language type.  This method will throw an
     * IllegalArgumentException if the type is null.
     *
     * @param type The scripting language type
     */
    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type can not be null");
        }
        _type = type;
    }

    /**
     * Get the scripting language version.
     *
     * @return The scripting language version
     */
    public String getVersion() {
        return _version;
    }

    /**
     * Set the scripting language version.
     *
     * @param version The scripting language version
     */
    public void setVersion(String version) {
        _version = version;
    }

    /**
     * Get a URI reference to a document defining the scripting language's
     * grammar.
     *
     * @return The grammar URI
     */
    public String getGrammar() {
        return _grammar;
    }

    /**
     * Set a URI reference to a document defining the scripting language's
     * grammar.
     *
     * @param grammar The grammar URI
     */
    public void setGrammar(String grammar) {
        _grammar = grammar;
    }

    public String toString() {
        return "Script[type=" + _type +
            ", version=" + _version +
            ", grammar=" + _grammar +
            ']';
    }
}