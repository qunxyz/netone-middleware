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

package oe.midware.workflow.util;

import java.io.ObjectStreamException;
import java.io.InvalidObjectException;
import java.util.*;

/**
 * An abstract base implementation for enumerated types.  This class is very
 * similar to the primordial generic Enum class described by JDK 1.5:
 * <p/>
 * <code>public abstract class Enum &lt;T extends Enum&lt;T&gt;&gt;<br/>
 * implements Comparable&lt;T&gt;, Serializable<code>
 *
 * @author Adrian Price
 */
public abstract class AbstractEnum implements Enum {
    static final long serialVersionUID = -8760826706749553497L;
    public final String name;
    public final int ordinal;

    /**
     * Used by subclasses to initialize their static list and map fields.
     *
     * @param values The complete set of enum values.
     * @param tagMap The map-by-name to initialize.
     * @return An unmodifiable list of enumerated values, in declaration order.
     */
    protected static List clinit(AbstractEnum[] values, Map tagMap) {
        for (int i = 0; i < values.length; i++)
            tagMap.put(values[i].name, values[i]);
        return Collections.unmodifiableList(Arrays.asList(values));
    }

    protected AbstractEnum(String name, int ordinal) {
        this.ordinal = ordinal;
        this.name = name;
    }

    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final int compareTo(Object o) {
        if (o != null && getClass() != o.getClass()) {
            throw new IllegalArgumentException(getClass().getName() +
                " cannot be compared to " + o.getClass());
        }
        // (null sorts to the top)
        return o == null ? -1 : ordinal - ((AbstractEnum)o).ordinal;
    }

    public final boolean equals(Object o) {
        return this == o;
    }

    public final int hashCode() {
        return ordinal;
    }

    protected final Object readResolve() throws ObjectStreamException {
        try {
            return family().get(ordinal);
        } catch (Exception e) {
            throw new InvalidObjectException(String.valueOf(ordinal));
        }
    }

    public final String toString() {
        return name;
    }

    public final int value() {
        return ordinal;
    }
}