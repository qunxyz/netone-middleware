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

package oe.midware.workflow.xpdl.model.data;

import oe.midware.workflow.util.AbstractBean;
import oe.midware.workflow.xpdl.PackageVisitor;

/**
 * FormalParameter defines input and output parameters which are passed to
 * tools.
 *
 * @author Adrian Price
 */
public final class FormalParameter extends AbstractBean {
    static final long serialVersionUID = 3019502908080770457L;

    private String _id;
    private Integer _index;
    private ParameterMode _mode;
    private DataType _dataType;
    private String _description;

    /**
     * Construct a new FormalParameter object.
     *
     * @param id The unique id
     * @param index The index of the parameter
     * @param mode The ParameterMode
     * @param dataType The DataType
     * @param description The name
     */
    public FormalParameter(String id, String index, String mode,
        DataType dataType, String description) {

        this(id, index == null ? null : Integer.valueOf(index),
            ParameterMode.valueOf(mode), dataType, description);
    }

    /**
     * Construct a new FormalParameter object.
     *
     * @param id The unique id
     * @param index The index of the parameter
     * @param mode The ParameterMode
     * @param dataType The DataType
     * @param description The description
     */
    public FormalParameter(String id, Integer index, ParameterMode mode,
        DataType dataType, String description) {

        _id = id;
        _description = description;
        setIndex(index);
        setMode(mode);
        setDataType(dataType);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Get the unique id.
     *
     * @return The id
     */
    public String getId() {
        return _id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public Integer getIndex() {
        return _index;
    }

    public void setIndex(Integer index) {
        _index = index;
    }

    public void setIndex(String index) {
        setIndex(index == null ? null : Integer.valueOf(index));
    }

    public DataType getDataType() {
        return _dataType;
    }

    public void setDataType(DataType dataType) {
        if (dataType == null)
            throw new IllegalArgumentException("Data type required");
        _dataType = dataType;
    }

    /**
     * Get the parameter mode.  The default parameter mode of
     * <code>ParameterMode.IN</code>.
     *
     * @return The parameter mode
     */
    public ParameterMode getMode() {
        return _mode;
    }

    /**
     * Set the parameter mode.  If a null value is passed then the parameter
     * mode will be reset to the default (ParameterMode.IN).
     *
     * @param mode The new parameter mode
     */
    public void setMode(ParameterMode mode) {
        _mode = mode;
    }

    public String toString() {
        return "FormalParameter[id=" + _id +
            ", index=" + _index +
            ", mode=" + _mode +
            ", dataType=" + _dataType +
            ", description=" + getDescription() +
            ']';
    }
}