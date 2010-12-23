// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.bi.wizard;

public final class WizardDaoImpl_Skel
    implements java.rmi.server.Skeleton
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("boolean checkExist(java.lang.String)"),
	new java.rmi.server.Operation("void create(oe.bi.etl.obj.ChoiceInfo, java.lang.String)"),
	new java.rmi.server.Operation("void delete(java.lang.String)"),
	new java.rmi.server.Operation("oe.bi.etl.obj.ChoiceInfo fromXml(java.lang.String)"),
	new java.rmi.server.Operation("java.util.List list()"),
	new java.rmi.server.Operation("java.lang.String listTree()[][]"),
	new java.rmi.server.Operation("void modify(oe.bi.etl.obj.ChoiceInfo)"),
	new java.rmi.server.Operation("java.lang.String subTree(java.lang.String, oe.bi.dao.ui.obj.NodeObj)"),
	new java.rmi.server.Operation("java.lang.String timeTree(oe.bi.dao.ui.obj.NodeObj)"),
	new java.rmi.server.Operation("java.lang.String toXml(oe.bi.etl.obj.ChoiceInfo)")
    };
    
    private static final long interfaceHash = 3147861746184854062L;
    
    public java.rmi.server.Operation[] getOperations() {
	return (java.rmi.server.Operation[]) operations.clone();
    }
    
    public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call, int opnum, long hash)
	throws java.lang.Exception
    {
	if (opnum < 0) {
	    if (hash == -7149324620357741348L) {
		opnum = 0;
	    } else if (hash == 5988886988205004612L) {
		opnum = 1;
	    } else if (hash == -5341333926771897608L) {
		opnum = 2;
	    } else if (hash == 1951238584395041705L) {
		opnum = 3;
	    } else if (hash == 3688649760882983761L) {
		opnum = 4;
	    } else if (hash == 653427802140339910L) {
		opnum = 5;
	    } else if (hash == -7007234126018957870L) {
		opnum = 6;
	    } else if (hash == -7499816922542882014L) {
		opnum = 7;
	    } else if (hash == -1964405901515290405L) {
		opnum = 8;
	    } else if (hash == -5130238065362880628L) {
		opnum = 9;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash)
		throw new java.rmi.server.SkeletonMismatchException("interface hash mismatch");
	}
	
	oe.bi.wizard.WizardDaoImpl server = (oe.bi.wizard.WizardDaoImpl) obj;
	switch (opnum) {
	case 0: // checkExist(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.checkExist($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 1: // create(ChoiceInfo, String)
	{
	    oe.bi.etl.obj.ChoiceInfo $param_ChoiceInfo_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ChoiceInfo_1 = (oe.bi.etl.obj.ChoiceInfo) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    server.create($param_ChoiceInfo_1, $param_String_2);
	    try {
		call.getResultStream(true);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 2: // delete(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    server.delete($param_String_1);
	    try {
		call.getResultStream(true);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 3: // fromXml(String)
	{
	    java.lang.String $param_String_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    oe.bi.etl.obj.ChoiceInfo $result = server.fromXml($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 4: // list()
	{
	    call.releaseInputStream();
	    java.util.List $result = server.list();
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 5: // listTree()
	{
	    call.releaseInputStream();
	    java.lang.String[][] $result = server.listTree();
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 6: // modify(ChoiceInfo)
	{
	    oe.bi.etl.obj.ChoiceInfo $param_ChoiceInfo_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ChoiceInfo_1 = (oe.bi.etl.obj.ChoiceInfo) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    server.modify($param_ChoiceInfo_1);
	    try {
		call.getResultStream(true);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 7: // subTree(String, NodeObj)
	{
	    java.lang.String $param_String_1;
	    oe.bi.dao.ui.obj.NodeObj $param_NodeObj_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_NodeObj_2 = (oe.bi.dao.ui.obj.NodeObj) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.subTree($param_String_1, $param_NodeObj_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 8: // timeTree(NodeObj)
	{
	    oe.bi.dao.ui.obj.NodeObj $param_NodeObj_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_NodeObj_1 = (oe.bi.dao.ui.obj.NodeObj) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.timeTree($param_NodeObj_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 9: // toXml(ChoiceInfo)
	{
	    oe.bi.etl.obj.ChoiceInfo $param_ChoiceInfo_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ChoiceInfo_1 = (oe.bi.etl.obj.ChoiceInfo) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.toXml($param_ChoiceInfo_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	default:
	    throw new java.rmi.UnmarshalException("invalid method number");
	}
    }
}
