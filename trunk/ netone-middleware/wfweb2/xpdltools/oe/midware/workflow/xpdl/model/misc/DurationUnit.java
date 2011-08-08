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
import java.util.Calendar;
import oe.midware.workflow.util.AbstractEnum;

/**
 * Class which defines all duration units available.  This class defines
 * the following duration units:
 *
 * <ul>
 * <li><b>Y</b> - Year
 * <li><b>M</b> - Month
 * <li><b>D</b> - Day
 * <li><b>h</b> - Hour
 * <li><b>m</b> - Minute
 * <li><b>s</b> - Second
 * </ul>
 *
 * @author Adrian Price
 */
public final class DurationUnit extends AbstractEnum {
    static final long serialVersionUID = 3955674746845399636L;

    public static final int SECOND_INT = 0;
    public static final int MINUTE_INT = 1;
    public static final int HOUR_INT = 2;
    public static final int DAY_INT = 3;
    public static final int MONTH_INT = 4;
    public static final int YEAR_INT = 5;
    public static final DurationUnit SECOND = new DurationUnit("s", SECOND_INT);
    public static final DurationUnit MINUTE = new DurationUnit("m", MINUTE_INT);
    public static final DurationUnit HOUR = new DurationUnit("h", HOUR_INT);
    public static final DurationUnit DAY = new DurationUnit("D", DAY_INT);
    public static final DurationUnit MONTH = new DurationUnit("M", MONTH_INT);
    public static final DurationUnit YEAR = new DurationUnit("Y", YEAR_INT);
    public static final DurationUnit DEFAULT;

    private static final DurationUnit[] _values = {
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        MONTH,
        YEAR
    };
    private static final int[] _jdkCalendarFields = {
        Calendar.SECOND,
        Calendar.MINUTE,
        Calendar.HOUR,
        Calendar.DAY_OF_MONTH,
        Calendar.MONTH,
        Calendar.YEAR
    };
    private static final Map _tagMap = new HashMap();
    private static final Map unitToMillisecondsMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    static {
        unitToMillisecondsMap.put(SECOND, new Long(1000L));
        unitToMillisecondsMap.put(MINUTE, new Long(1000L * 60));
        unitToMillisecondsMap.put(HOUR, new Long(1000L * 60 * 60));
        unitToMillisecondsMap.put(DAY, new Long(1000L * 60 * 60 * 24));
        unitToMillisecondsMap.put(MONTH, new Long(1000L * 60 * 60 * 24 * 30));
        unitToMillisecondsMap.put(YEAR, new Long(1000L * 60 * 60 * 24 * 365));
        DEFAULT = DurationUnit.valueOf(System.getProperty("obe.duration.unit", 
            DAY.name));
    }

    /**
     * Convert the specified type String to a DurationUnit object.  If the
     * String cannot be converted to a DurationUnit then the value null
     * is returned.
     *
     * @param tag The duration unit String
     * @return A DurationUnit or null
     */
    public static DurationUnit valueOf(String tag) {
        DurationUnit durationUnit = (DurationUnit)_tagMap.get(tag);
        if (durationUnit == null && tag != null)
            throw new IllegalArgumentException(tag);
        return durationUnit;
    }

    /**
     * Convert the specified DurationUnit to milliseconds.
     *
     * @param unit The DurationUnit
     * @return The number of milliseconds
     */
    public static long unitToMilliseconds(DurationUnit unit) {
        Long value = (Long)unitToMillisecondsMap.get(unit);
        if (value == null) {
            throw new IllegalArgumentException("Unknown duration unit: " +
                unit);
        } else {
            return value.longValue();
        }
    }

    /**
     * Construct a new duration unit.
     *
     * @param name
     * @param ordinal An int value
     */
    private DurationUnit(String name, int ordinal) {
        super(name, ordinal);
    }

    /**
     * Returns the integer code for the corresponding JDK Calendar field.  This
     * value can be passed to the <code>java.util.Calendar.add(int, int)</code>
     * method.
     *
     * @return One of the constants declared by the
     * <code>java.util.Calendar</code> class.
     */
    public int getJDKCalendarField() {
        return _jdkCalendarFields[ordinal];
    }

    /**
     * Convert the duration unit to milliseconds.
     *
     * @return Convert duration unit to milliseconds
     */
    public long toMilliseconds() {
        return unitToMilliseconds(this);
    }

    public List family() {
        return VALUES;
    }
}