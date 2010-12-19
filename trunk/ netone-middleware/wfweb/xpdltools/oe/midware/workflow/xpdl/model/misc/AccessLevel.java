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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oe.midware.workflow.util.AbstractEnum;

/**
 * Type class for access level types.
 *
 * @author Adrian Price
 */
public final class AccessLevel extends AbstractEnum {
    static final long serialVersionUID = -4092983402479825768L;

    public static final int PUBLIC_INT = 0;
    public static final int PRIVATE_INT = 1;
    /** PUBLIC type object. */
    public static final AccessLevel PUBLIC =
        new AccessLevel("PUBLIC", PUBLIC_INT);
    /** PRIVATE type object. */
    public static final AccessLevel PRIVATE =
        new AccessLevel("PRIVATE", PRIVATE_INT);
    private static final AccessLevel[] _values = {
        PUBLIC,
        PRIVATE
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Convert the specified type String to an AccessLevel type object.  If
     * the specified type string can not be converted to an AccessLevel
     * object then this method will return null.
     *
     * @param tag The type String (PUBLIC or PRIVATE)
     * @return The AccessLevel object or null
     */
    public static AccessLevel valueOf(String tag) {
        AccessLevel accessLevel = (AccessLevel)_tagMap.get(tag);
        if (accessLevel == null && tag != null)
            throw new IllegalArgumentException(tag);
        return accessLevel;
    }

    /**
     * Construct a new AccessLevel type object.
     *
     * @param name
     * @param value The int value
     */
    private AccessLevel(String name, int value) {
        super(name, value);
    }

    public List family() {
        return VALUES;
    }
}