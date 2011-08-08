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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A typesafe list that only accepts elements of a specified class.
 * @author Adrian Price
 */
public class TypesafeList extends ArrayList {
    static final long serialVersionUID = -3285282543401783232L;
    private Class _clazz;
    private boolean _allowNulls;

    public TypesafeList(int initialCapacity, Class clazz, boolean allowNulls) {
        super(initialCapacity);
        _clazz = clazz;
        _allowNulls = allowNulls;
    }

    public TypesafeList(Class clazz, boolean allowNulls) {
        _clazz = clazz;
        _allowNulls = allowNulls;
    }

    public TypesafeList(Collection c, Class clazz, boolean allowNulls) {
        super(c);
        _clazz = clazz;
        _allowNulls = allowNulls;
    }

    /**
     * Returns an array of the base type is the class specified in the
     * constructor.
     * @return A suitably typed array.
     */
    public Object[] toArray() {
        return super.toArray((Object[])Array.newInstance(_clazz, size()));
    }

    public Object set(int index, Object element) {
        checkElement(element);
        return super.set(index, element);
    }

    public boolean add(Object o) {
        checkElement(o);
        return super.add(o);
    }

    public void add(int index, Object element) {
        checkElement(element);
        super.add(index, element);
    }

    public boolean addAll(Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); )
            checkElement(i.next());
        return super.addAll(c);
    }

    public boolean addAll(int index, Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); )
            checkElement(i.next());
        return super.addAll(index, c);
    }

    private void checkElement(Object o) {
        if (!_allowNulls && o == null)
            throw new NullPointerException();
        if (o != null && !(_clazz.isAssignableFrom(o.getClass())))
            throw new IllegalArgumentException(String.valueOf(o));
    }
}