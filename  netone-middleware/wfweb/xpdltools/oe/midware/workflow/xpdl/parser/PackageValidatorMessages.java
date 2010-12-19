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

package oe.midware.workflow.xpdl.parser;

/**
 * Provides error codes for package validator messages.
 *
 * @author Adrian Price
 */
public interface PackageValidatorMessages {
    /** "{0}: Id must be specified." */
    int ID_MUST_BE_SPECIFIED = 0;
    /** "{0} is already defined. Remove the duplicate entity or assign it a unique ID." */
    int ID_ALREADY_DEFINED = 1;
    /** "{0} references an undefined {1}[{2}]." */
    int UNDEFINED_REFERENCE = 2;
    /** "{0}: Property {1} must be specified." */
    int PROPERTY_MISSING = 3;
    /** "{0}: Property {1} count incorrect: expected &lt;{2}&gt;, actual &lt;{3}&gt;." */
    int COUNT_INCORRECT = 4;
    /** "{0}: Property {1} value must be at least &lt;{2}&gt;." */
    int PROPERTY_VALUE_TOO_LOW = 5;
    /** "{0}: must have at least one start activity (one with no afferent (inbound) transitions)." */
    int START_ACTIVITY_REQUIRED = 6;
    /** "{0}: must have at least one exit activity (one with no efferent (outbound) transitions)." */
    int EXIT_ACTIVITY_REQUIRED = 7;
    /** "{0}: the BlockActivity references an undefined ActivitySet[{1}]." */
    int UNDEFINED_ACTIVITY_SET = 8;
    /** "Activity[{0}]: the SubFlow references an undefined WorkflowProcess[{1}]." */
    int UNDEFINED_SUBPROCESS = 9;
    /** "Activity[{0}]: a Route cannot have a Performer." */
    int ROUTE_CANNOT_HAVE_PERFORMER = 10;
    /** "Activity[{0}]: must include only one of: Route, Implementation, or BlockActivity." */
    int ACTIVITY_BODY_MISSING = 11;
    /** "Activity[{0}]: activities can only contain one synchronous deadline." */
    int MAX_ONE_SYNC_DEADLINE = 12;
    /** "Activity[{0}]: is incompatible with Transition[{1}]/ExtendedAttribute[Execution]." */
    int INCOMPATIBLE_TRANSITION = 13;
    /** "Activity[{0}]: ExceptionName[{1}] is not handled by any efferent (outbound) transition." */
    int EXCEPTION_NOT_HANDLED = 14;
    /** "Activity[{0}]: TransitionRestrictions can only contain one Join." */
    int TRANSITION_RESTRICTIONS_ONLY_ONE_JOIN = 15;
    /** "Activity[{0}]: TransitionRestrictions can only contain one Split in OBE (XPDL permits more, but does not define the semantics)." */
    int TRANSITION_RESTRICTIONS_ONLY_ONE_SPLIT = 16;
    /** "Activity[{0}]: a TransitionRef references non-efferent (non-outbound) Transition[{1}]." */
    int INVALID_TRANSITION_REF = 17;
    /** "Activity[{0}]: the Split does not reference Transition[{1}]." */
    int TRANSITION_REF_MISSING = 18;
    /** "Activity[{0}]: the Split does not reference any transitions." */
    int SPLIT_MISSING_REFERENCES = 19;
    /** "Transition[{0}]: OBE event transitions cannot be used in conjunction with an XPDL transition type {1}." */
    int EVENT_TRANSITION_MISMATCH = 20;
    /** "Activity[{0}]: an OTHERWISE transition has already been defined." */
    int OTHERWISE_ALREADY_DEFINED = 21;
    /** "Activity[{0}] has {1} afferent (inbound) transitions, and therefore requires a Join." */
    int JOIN_REQUIRED = 22;
    /** "Activity[{0}] has {1} regular efferent (outbound) transitions, and therefore requires a Split." */
    int SPLIT_REQUIRED = 23;
    /** "Transition [{0}] references an undefined 'from' Activity[{1}]." */
    int TRANSITION_FROM_ACTIVITY_UNDEFINED = 24;
    /** "Transition [{0}] references an undefined 'to' Activity[{1}]." */
    int TRANSITION_TO_ACTIVITY_UNDEFINED = 25;
    /** "Unable to determine DataType for ActualParameter[{0}]: {1}" */
    int DATATYPE_NOT_FOUND = 26;
    /** "Unsupported type conversion: from &lt;${0}&gt; to &lt;${1}&gt;." */
    int UNSUPPORTED_TYPE_CONVERSION = 27;
    /** "&lt;{0}&gt; is not a valid XPDL identifier (NMTOKEN)." */
    int INVALID_NMTOKEN = 28;
    /** "Unsupported codepage: {0}." */
    int UNSUPPORTED_CODEPAGE = 29;
    /** "Unsupported country: {0}." */
    int UNSUPPORTED_COUNTRY = 30;
    /**
     * "Script[{0}] is not a valid script declaration. The script type is an
     * extended MIME Media Type and must start with "text/xml/x-" or "text/x-""
     */
    int INVALID_SCRIPT = 31;
    /** {0} is not a valid type declaration: a type is required. */
    int INVALID_TYPE_DECL = 32;
    /**
     * "Invalid ValidFrom/ValidTo specification: {0,date}/{1,date}. \
     * ValidFrom must pre-date ValidTo."
     */
    int INVALID_FROM_TO_DATES = 33;
    /**
     * "Activity[{0}] is in a FULL_BLOCKED WorkflowProcess and has an XOR-Split,
     * and therefore requires an OTHERWISE or unconditional transition."
     */
    int OTHERWISE_REQUIRED = 34;
}