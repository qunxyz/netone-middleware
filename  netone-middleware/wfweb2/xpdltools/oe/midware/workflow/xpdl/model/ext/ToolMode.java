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

package oe.midware.workflow.xpdl.model.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oe.midware.workflow.util.AbstractEnum;

/**
 * The ToolMode is used to indicate whether the tools in a tool set should be
 * executed sequentially or in parallel.  The default is SEQUENTIAL.  This
 * attribute is not described in the XPDL specification, but its predecessor
 * <a href="http://www.wfmc.org/standards/docs/if19807m.pdf">WPDL</a> states
 * that the workflow engine creates one work item per tool, and that execution
 * flow between these work items (and thus the invocation order of the tools)
 * can be controlled in a vendor-specific manner.  The WPDL spec. does suggest
 * SEQUENTIAL or PARALLEL modes, and states that the default is SEQUENTIAL
 * (curious that the spec. defines the default for a vendor-specific attribute?).
 *
 * @author Adrian Price
 */
public final class ToolMode extends AbstractEnum {
    static final long serialVersionUID = 2635003088809664289L;

    public static final int SEQUENTIAL_INT = 0;
    public static final int PARALLEL_INT = 1;
    /** ExecutionType representing sequential tool invocation. */
    public static final ToolMode SEQUENTIAL =
        new ToolMode("SEQUENTIAL", SEQUENTIAL_INT);
    /** ExecutionType representing parallel tool invocation. */
    public static final ToolMode PARALLEL =
        new ToolMode("PARALLEL", PARALLEL_INT);
    /** An array of all invocation modes. */
    public static final ToolMode[] types = {SEQUENTIAL, PARALLEL};

    private static final ToolMode[] _values = {
        SEQUENTIAL,
        PARALLEL
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Converts the specified string to a ToolMode object.
     *
     * @param tag The string, one of: SEQUENTIAL or PARALLEL.
     * @return The ToolMode object
     * @throws IllegalArgumentException if the <code>tag<code> parameter is
     * invalid.
     */
    public static ToolMode valueOf(String tag) {
        ToolMode tm = (ToolMode)_tagMap.get(tag);
        if (tm == null && tag != null)
            throw new IllegalArgumentException(tag);
        return tm;
    }

    /**
     * Construct a new ToolMode instance.
     *
     * @param name
     * @param ordinal The value
     */
    private ToolMode(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}