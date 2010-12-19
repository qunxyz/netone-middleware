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

import java.util.Arrays;
import java.beans.PropertyVetoException;
import oe.midware.workflow.util.AbstractBean;

/**
 * Abstract base implementation of the Condition interface.
 *
 * @author Adrian Price
 */
public final class Condition extends AbstractBean {
    static final long serialVersionUID = -6563693584809960943L;
    public static final String VALUE = "value";
    public static final String XPRESSION = "xpression";
    public static final String TYPE = "type";
    private static final Xpression[] EMPTY_XPRESSION = {};
    private static final String[] _indexedPropertyNames = {XPRESSION};
    private static final Object[] _indexedPropertyValues = {EMPTY_XPRESSION};
    private static final int XPRESSION_IDX = 0;

    private String _value;
    private Xpression[] _xpression = EMPTY_XPRESSION;
    private ConditionType _type = ConditionType.CONDITION;

    /**
     * Construct a new Condition.
     */
    public Condition() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Get the condition type.
     *
     * @return The condition type
     */
    public ConditionType getType() {
        return _type;
    }

    /**
     * Set the condition type.
     *
     * @param type The condition type
     */
    public void setType(ConditionType type) {
        _type = type;
    }

    /**
     * Get the condition value.  This value represents a conditional
     * expression.
     *
     * @return The value
     */
    public String getValue() {
        return _value;
    }

    /**
     * Set the condition value.
     *
     * @param value The value
     */
    public void setValue(String value) {
        _value = value;
    }

    public void add(Xpression xpression) throws PropertyVetoException {
        _xpression = (Xpression[])add(XPRESSION_IDX, xpression);
    }

    public void remove(Xpression xpression) throws PropertyVetoException {
        _xpression = (Xpression[])remove(XPRESSION_IDX, xpression);
    }

    /**
     * Get a list of Xpressions.
     *
     * @return A List of Xpressions
     */
    public Xpression[] getXpression() {
        return (Xpression[])get(XPRESSION_IDX);
    }

    public Xpression getXpression(int i) {
        return _xpression[i];
    }

    public void setXpression(Xpression[] xpressions)
        throws PropertyVetoException {

        set(XPRESSION_IDX, _xpression = xpressions == null
            ? EMPTY_XPRESSION : xpressions);
    }

    public void setXpression(int i, Xpression xpression)
        throws PropertyVetoException {

        set(XPRESSION_IDX, i, xpression);
    }

    public String toString() {
        return "Condition[value='" + _value + '\'' +
            ", xpressions=" +
            (_xpression == null ? null : Arrays.asList(_xpression)) +
            ", type=" + _type +
            ']';
    }
}