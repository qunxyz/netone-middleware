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

import oe.midware.workflow.xpdl.PackageVisitor;
import oe.midware.workflow.xpdl.model.data.ExternalReference;
import oe.midware.workflow.xpdl.model.misc.AbstractWFElement;
import oe.midware.workflow.xpdl.model.ext.CalendarRef;

/**
 * Representation of the XPDL Participant element.
 *
 * @author Adrian Price
 */
public final class Participant extends AbstractWFElement
    implements CalendarRef {

    static final long serialVersionUID = 3159696388261254775L;
    private ParticipantType _participantType;
    private ExternalReference _externalReference;
    private String _calendar;

    public Participant() {
    }

    /**
     * Construct a new Participant.
     *
     * @param id The participant id
     * @param name The participant name
     * @param pType The participant type
     * @param extRef
     */
    public Participant(String id, String name, ParticipantType pType,
        ExternalReference extRef) {

        super(id, name);
        _participantType = pType;
        _externalReference = extRef;
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
    }

    public ParticipantType getParticipantType() {
        return _participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        _participantType = participantType;
    }

    public ExternalReference getExternalReference() {
        return _externalReference;
    }

    public void setExternalReference(ExternalReference externalReference) {
        _externalReference = externalReference;
    }

    public String getCalendar() {
        return _calendar;
    }

    public void setCalendar(String calendar) {
        _calendar = calendar;
    }

    public String toString() {
        return "Participant[id=" + getId() +
            ", name=" + getName() +
            ", participantType=" + _participantType +
            ", externalReference=" + _externalReference +
            ", calendar=" + _calendar +
            ", description=" + getDescription() +
            ", extendedAttributes=" + getExtendedAttributes() +
            ']';
    }
}