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

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * Holds a validation error as detected by the package validator.
 *
 * @see PackageValidator
 * @author Adrian Price
 */
public class ValidationError implements Serializable {
    static final long serialVersionUID = -3856974003132862868L;
    public static final int TYPE_WARNING = 0;
    public static final int TYPE_ERROR = 1;
    private static final String BUNDLE_NAME = PackageValidatorMessages.class.getName();
    private static final ResourceBundle _bundle;
    private int _type;
    private int _msgCode;
    private Object[] _args;
    private String _msg;
    private transient Object _src;

    static {
        _bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }

    ValidationError(int type, int msgCode, Object[] args, Object src,
        String msg) {

        _type = type;
        _msgCode = msgCode;
        _args = args;
        _src = src;
        _msg = msg;
    }

    /**
     * Returns a textual description of the error in English.  N.B. the text
     * differs from that supplied by {@link #getLocalizedMessage}, and includes
     * a representation of the full path to the source of the error.
     *
     * @return Error/warning message.
     */
    public String getMessage() {
        return _msg;
    }

    /**
     * Returns a localized textual description of the error.
     *
     * @return Error/warning message.
     */
    public String getLocalizedMessage() {
        String msg = _bundle.getString(String.valueOf(_msgCode));
        return MessageFormat.format(msg, _args);
    }

    /**
     * Returns the array of replaceable arguments for the message.
     *
     * @return Message arguments.
     */
    public Object[] getMessageArgs() {
        return _args;
    }

    /**
     * Returns the code for the message.
     *
     * @return Message code.
     */
    public int getMessageCode() {
        return _msgCode;
    }

    /**
     * Returns the source workflow object that caused the error.
     *
     * @return Error source object.
     */
    public Object getSource() {
        return _src;
    }

    /**
     * Returns the validation error type.
     *
     * @return Error type, one of: {@link #TYPE_WARNING}, {@link #TYPE_ERROR}.
     */
    public int getType() {
        return _type;
    }

    public String toString() {
        return String.valueOf(_msgCode) + ": " + _msg +
            (_src == null ? "" : "; source=" + _src);
    }
}
