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

package oe.midware.workflow.xpdl.model.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oe.midware.workflow.util.AbstractEnum;

public final class ParticipantType extends AbstractEnum {
    static final long serialVersionUID = 8229202140364297618L;

    public static final int RESOURCE_SET_INT = 0;
    public static final int RESOURCE_INT = 1;
    public static final int ROLE_INT = 2;
    public static final int ORGANIZATIONAL_UNIT_INT = 3;
    public static final int HUMAN_INT = 4;
    public static final int SYSTEM_INT = 5;
    public static final ParticipantType RESOURCE_SET =
        new ParticipantType("RESOURCE_SET", RESOURCE_SET_INT);
    public static final ParticipantType RESOURCE =
        new ParticipantType("RESOURCE", RESOURCE_INT);
    public static final ParticipantType ROLE =
        new ParticipantType("ROLE", ROLE_INT);
    public static final ParticipantType ORGANIZATIONAL_UNIT =
        new ParticipantType("ORGANIZATIONAL_UNIT", ORGANIZATIONAL_UNIT_INT);
    public static final ParticipantType HUMAN =
        new ParticipantType("HUMAN", HUMAN_INT);
    public static final ParticipantType SYSTEM =
        new ParticipantType("SYSTEM", SYSTEM_INT);

    private static final ParticipantType[] _values = {
        RESOURCE_SET,
        RESOURCE,
        ROLE,
        ORGANIZATIONAL_UNIT,
        HUMAN,
        SYSTEM
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    public static ParticipantType valueOf(String tag) {
        ParticipantType participantType = (ParticipantType)_tagMap.get(tag);
        if (participantType == null && tag != null)
            throw new IllegalArgumentException(tag);
        return participantType;
    }

    private ParticipantType(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}