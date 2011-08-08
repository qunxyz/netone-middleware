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

package oe.midware.workflow.xpdl;

import oe.midware.workflow.xpdl.model.activity.*;
import oe.midware.workflow.xpdl.model.application.Application;
import oe.midware.workflow.xpdl.model.data.ActualParameter;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.data.FormalParameter;
import oe.midware.workflow.xpdl.model.data.TypeDeclaration;
import oe.midware.workflow.xpdl.model.ext.Event;
import oe.midware.workflow.xpdl.model.ext.EventType;
import oe.midware.workflow.xpdl.model.participant.Participant;
import oe.midware.workflow.xpdl.model.pkg.ExternalPackage;
import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.transition.Transition;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * A general purpose visitor for traversing an XPDL package object graph.
 *
 * @author Adrian Price
 */
public abstract class PackageVisitor {
    protected PackageVisitor() {
    }

    public void visit(Activity pkg) {
    }

    public void visit(ActivitySet pkg) {
    }

    public void visit(ActualParameter actualParameter) {
    }

    public void visit(Application application) {
    }

    public void visit(DataField dataField) {
    }

    public void visit(Event event) {
    }

    public void visit(EventType eventType) {
    }

    public void visit(ExternalPackage externalPackage) {
    }

    public void visit(FormalParameter formalParameter) {
    }

    public void visit(NoImplementation noImplementation) {
    }

    public void visit(Participant participant) {
    }

    public void visit(SubFlow subFlow) {
    }

    public void visit(Tool tool) {
    }

    public void visit(ToolSet toolSet) {
    }

    public void visit(Transition transition) {
    }

    public void visit(TypeDeclaration typeDeclaration) {
    }

    public void visit(WorkflowProcess pkg) {
    }

    public void visit(XPDLPackage pkg) {
    }
}