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

import java.util.Collection;
import java.util.Iterator;

/**
 * An abstract typesafe collection class that manages its elements.  Subclasses
 * can call operations on member elements as they are added or removed from the
 * collection.
 * @author   Adrian Price
 */
public abstract class ManagedList extends TypesafeList {
    protected ManagedList(Class clazz, boolean allowNulls) {
        super(clazz, allowNulls);
    }

    protected ManagedList(Collection c, Class clazz, boolean allowNulls) {
        super(c, clazz, allowNulls);
    }

    protected ManagedList(int initialCapacity, Class clazz, boolean allowNulls) {
        super(initialCapacity, clazz, allowNulls);
    }

    public boolean add(Object element) {
        boolean ret = super.add(element);
        objectAdded(element);
        return ret;
    }

    public void add(int index, Object element) {
        super.add(index, element);
        objectAdded(element);
    }

    public boolean addAll(Collection c) {
        boolean ret = super.addAll(c);
        objectAdded(c);
        return ret;
    }

    public boolean addAll(int index, Collection c) {
        boolean ret = super.addAll(index, c);
        objectAdded(c);
        return ret;
    }

    public void clear() {
        clear(true);
    }

    public void clear(boolean notify) {
        if (notify)
            objectRemoved(this);
        super.clear();
    }

    public Object remove(int index) {
        Object ret = super.remove(index);
        objectRemoved(ret);
        return ret;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        for (int i = fromIndex; i <= toIndex; i++)
            objectRemoved(get(i));
        super.removeRange(fromIndex, toIndex);
    }

    public boolean remove(Object o) {
        boolean ret = super.remove(o);
        if (ret)
            objectRemoved(o);
        return ret;
    }

    public Object set(int index, Object element) {
        Object ret = super.set(index, element);
        objectRemoved(ret);
        objectAdded(element);
        return ret;
    }

    private void objectAdded(Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); )
            objectAdded(i.next());
    }

    /**
     * Called when an element is added to the collection.
     * @param element The element that has been added.
     */
    protected abstract void objectAdded(Object element);

    private void objectRemoved(Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); )
            objectRemoved(i.next());
    }

    /**
     * Called when an element is removed from the collection.
     * @param element The element that has been removed.
     */
    protected abstract void objectRemoved(Object element);
}