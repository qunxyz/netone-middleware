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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oe.midware.workflow.util.AbstractEnum;

/**
 * Data type representing the basic workflow data types.
 *
 * @author Adrian Price
 */
public final class BasicType extends AbstractEnum implements Type {
    static final long serialVersionUID = -7894443551993705284L;

    public static final BasicType STRING = new BasicType("STRING", STRING_TYPE);
    public static final BasicType FLOAT = new BasicType("FLOAT", FLOAT_TYPE);
    public static final BasicType INTEGER = new BasicType("INTEGER", INTEGER_TYPE);
    /** @deprecated Use {@link ExternalReference} or @{@link SchemaType}. */
    public static final BasicType REFERENCE = new BasicType("REFERENCE", REFERENCE_TYPE);
    public static final BasicType DATETIME = new BasicType("DATETIME", DATETIME_TYPE);
    public static final BasicType BOOLEAN = new BasicType("BOOLEAN", BOOLEAN_TYPE);
    public static final BasicType PERFORMER = new BasicType("PERFORMER", PERFORMER_TYPE);

    private static final BasicType[] _values = {
        STRING,
        FLOAT,
        INTEGER,
        REFERENCE,
        DATETIME,
        BOOLEAN,
        PERFORMER
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    // syntax: boolean ok = _assignable[to][from];
    private static final boolean[][] _assignable = {
        // string, float, integer, reference, datetime, boolean, performer
        {  true,   true,  true,    false,     true,     true,    true},  // string
        {  true,   true,  true,    false,     false,    false,   false}, // float
        {  true,   true,  true,    false,     true,     false,   false}, // integer
        {  false,  false, false,   true,      false,    false,   false}, // reference
        {  true,   false, true,    false,     true,     false,   false}, // datetime
        {  true,   false, false,   false,     false,    true,    false}, // boolean
        {  true,   false, false,   false,     false,    false,   true}   // performer
    };

    public static BasicType valueOf(int type) {
        return _values[type];
    }

    public static BasicType valueOf(String tag) {
        BasicType basicType = (BasicType)_tagMap.get(tag);
        if (basicType == null && tag != null)
            throw new IllegalArgumentException(tag);
        return basicType;
    }

    /**
     * Construct a new BasicType.
     *
     * @param name
     * @param ordinal The int value
     */
    private BasicType(String name, int ordinal) {
        super(name, ordinal);
    }

    public Type getImpliedType() {
        return this;
    }

    public boolean isAssignableFrom(Type fromType) {
        fromType = fromType.getImpliedType();
        if (fromType instanceof ExternalReference) {
            // See whether the external reference refers to something we can
            // convert to this BasicType.
            Class valueType = DataTypes.classForType(fromType);
            fromType = DataTypes.dataTypeForClass(valueType).getType();
        }
        if (fromType instanceof BasicType) {
            BasicType basicType = (BasicType)fromType;
            return _assignable[ordinal][basicType.ordinal];
        }
        return false;
    }

    public List family() {
        return VALUES;
    }
}