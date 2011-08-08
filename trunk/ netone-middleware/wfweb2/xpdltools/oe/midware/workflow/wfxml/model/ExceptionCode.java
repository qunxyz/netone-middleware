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

package oe.midware.workflow.wfxml.model;

import java.util.*;
import oe.midware.workflow.util.AbstractEnum;

public final class ExceptionCode extends AbstractEnum {
    static final long serialVersionUID = -2572392200838043501L;
    private static final Map _tagMap = new HashMap();
    private static final List _values = new ArrayList(21);
    public static final List VALUES = Collections.unmodifiableList(_values);

    public static final int WF_PARSING_ERROR_TYPE = 100;
    public static final int WF_EVENT_MISSING_TYPE = 101;
    public static final int WF_INVALID_VERSION_TYPE = 102;
    public static final int WF_INVALID_RESPONSE_REQUIRED_VALUE_TYPE = 103;
    public static final int WF_INVALID_KEY_TYPE = 104;
    public static final int WF_INVALID_OPERATION_SPECIFICATION_TYPE = 105;
    public static final int WF_INVALID_REQUEST_ID_TYPE = 106;

    public static final int WF_INVALID_CONTEXT_DATA_TYPE = 201;
    public static final int WF_INVALID_RESULT_DATA_TYPE = 202;
    public static final int WF_INVALID_RESULT_DATA_SET_TYPE = 203;

    public static final int WF_NO_AUTHORIZATION_TYPE = 300;

    public static final int WF_OPERATION_FAILED_TYPE = 400;

    public static final int WF_NO_ACCESS_TO_RESOURCE_TYPE = 500;
    public static final int WF_INVALID_PROCESS_DEFINITION_TYPE = 502;
    public static final int WF_MISSING_PROCESS_INSTANCE_KEY_TYPE = 503;
    public static final int WF_INVALID_PROCESS_INSTANCE_KEY_TYPE = 504;

    public static final int WF_INVALID_STATE_TRANSITION_TYPE = 600;
    public static final int WF_INVALID_OBSERVER_FOR_RESOURCE_TYPE = 601;
    public static final int WF_MISSING_NOTIFICATION_NAME_TYPE = 602;
    public static final int WF_INVALID_NOTIFICATION_NAME_TYPE = 603;

    public static final int WF_OTHER_TYPE = 800;

    public static final ExceptionCode WF_PARSING_ERROR = new ExceptionCode("WF_PARSING_ERROR", WF_PARSING_ERROR_TYPE);
    public static final ExceptionCode WF_EVENT_MISSING = new ExceptionCode("WF_EVENT_MISSING", WF_EVENT_MISSING_TYPE);
    public static final ExceptionCode WF_INVALID_VERSION = new ExceptionCode("WF_INVALID_VERSION", WF_INVALID_VERSION_TYPE);
    public static final ExceptionCode WF_INVALID_RESPONSE_REQUIRED_VALUE = new ExceptionCode("WF_INVALID_RESPONSE_REQUIRED_VALUE", WF_INVALID_RESPONSE_REQUIRED_VALUE_TYPE);
    public static final ExceptionCode WF_INVALID_KEY = new ExceptionCode("WF_INVALID_KEY", WF_INVALID_KEY_TYPE);
    public static final ExceptionCode WF_INVALID_OPERATION_SPECIFICATION = new ExceptionCode("WF_INVALID_OPERATION_SPECIFICATION", WF_INVALID_OPERATION_SPECIFICATION_TYPE);
    public static final ExceptionCode WF_INVALID_REQUEST_ID = new ExceptionCode("WF_INVALID_REQUEST_ID", WF_INVALID_REQUEST_ID_TYPE);

    public static final ExceptionCode WF_INVALID_CONTEXT_DATA = new ExceptionCode("WF_INVALID_CONTEXT_DATA", WF_INVALID_CONTEXT_DATA_TYPE);
    public static final ExceptionCode WF_INVALID_RESULT_DATA = new ExceptionCode("WF_INVALID_RESULT_DATA", WF_INVALID_RESULT_DATA_TYPE);
    public static final ExceptionCode WF_INVALID_RESULT_DATA_SET = new ExceptionCode("WF_INVALID_RESULT_DATA_SET", WF_INVALID_RESULT_DATA_SET_TYPE);

    public static final ExceptionCode WF_NO_AUTHORIZATION = new ExceptionCode("WF_NO_AUTHORIZATION", WF_NO_AUTHORIZATION_TYPE);

    public static final ExceptionCode WF_OPERATION_FAILED = new ExceptionCode("WF_OPERATION_FAILED", WF_OPERATION_FAILED_TYPE);

    public static final ExceptionCode WF_NO_ACCESS_TO_RESOURCE = new ExceptionCode("WF_NO_ACCESS_TO_RESOURCE", WF_NO_ACCESS_TO_RESOURCE_TYPE);
    public static final ExceptionCode WF_INVALID_PROCESS_DEFINITION = new ExceptionCode("WF_INVALID_PROCESS_DEFINITION", WF_INVALID_PROCESS_DEFINITION_TYPE);
    public static final ExceptionCode WF_MISSING_PROCESS_INSTANCE_KEY = new ExceptionCode("WF_MISSING_PROCESS_INSTANCE_KEY", WF_MISSING_PROCESS_INSTANCE_KEY_TYPE);
    public static final ExceptionCode WF_INVALID_PROCESS_INSTANCE_KEY = new ExceptionCode("WF_INVALID_PROCESS_INSTANCE_KEY", WF_INVALID_PROCESS_INSTANCE_KEY_TYPE);

    public static final ExceptionCode WF_INVALID_STATE_TRANSITION = new ExceptionCode("WF_INVALID_STATE_TRANSITION", WF_INVALID_STATE_TRANSITION_TYPE);
    public static final ExceptionCode WF_INVALID_OBSERVER_FOR_RESOURCE = new ExceptionCode("WF_INVALID_OBSERVER_FOR_RESOURCE", WF_INVALID_OBSERVER_FOR_RESOURCE_TYPE);
    public static final ExceptionCode WF_MISSING_NOTIFICATION_NAME = new ExceptionCode("WF_MISSING_NOTIFICATION_NAME", WF_MISSING_NOTIFICATION_NAME_TYPE);
    public static final ExceptionCode WF_INVALID_NOTIFICATION_NAME = new ExceptionCode("WF_INVALID_NOTIFICATION_NAME", WF_INVALID_NOTIFICATION_NAME_TYPE);

    public static final ExceptionCode WF_OTHER = new ExceptionCode("WF_OTHER", WF_OTHER_TYPE);

    private ExceptionCode(String name, int value) {
        super(name, value);
        _values.add(this);
        _tagMap.put(name, this);
    }

    public List family() {
        return VALUES;
    }
}