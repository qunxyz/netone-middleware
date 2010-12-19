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

package oe.midware.workflow.xpdl.model.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oe.midware.workflow.util.AbstractEnum;

/**
 * @author Adrian Price
 */
public final class ConditionType extends AbstractEnum {
    static final long serialVersionUID = 8019004705357307670L;

    public static final int CONDITION_INT = 0;
    public static final int OTHERWISE_INT = 1;
    public static final int EXCEPTION_INT = 2;
    public static final int DEFAULTEXCEPTION_INT = 3;
    public static final ConditionType CONDITION =
        new ConditionType("CONDITION", CONDITION_INT);
    public static final ConditionType OTHERWISE =
        new ConditionType("OTHERWISE", OTHERWISE_INT);
    public static final ConditionType EXCEPTION =
        new ConditionType("EXCEPTION", EXCEPTION_INT);
    public static final ConditionType DEFAULTEXCEPTION =
        new ConditionType("DEFAULTEXCEPTION", DEFAULTEXCEPTION_INT);

    private static final ConditionType[] _values = {
        CONDITION,
        OTHERWISE,
        EXCEPTION,
        DEFAULTEXCEPTION
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Convert the specified String to a ConditionType object.  If the tag
     * string is null then this method returns null.
     *
     * @param tag The String
     * @return The ConditionType object
     * @throws IllegalArgumentException if the tag is invalid.
     */
    public static ConditionType valueOf(String tag) {
        ConditionType conditionType = (ConditionType)_tagMap.get(tag);
        if (conditionType == null && tag != null)
            throw new IllegalArgumentException(tag);
        return conditionType;
    }

    /**
     * Construct a new ConditionType instance.
     *
     * @param name
     * @param ordinal The value
     */
    private ConditionType(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}