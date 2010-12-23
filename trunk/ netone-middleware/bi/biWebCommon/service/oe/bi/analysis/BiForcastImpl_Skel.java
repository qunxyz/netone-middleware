// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.bi.analysis;

public final class BiForcastImpl_Skel
    implements java.rmi.server.Skeleton
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("java.lang.String fetchTimeDimensionLink(oe.bi.view.obj.ViewModel)"),
	new java.rmi.server.Operation("oe.bi.view.obj.ViewModel performBiForcast(oe.bi.view.obj.ViewModel, oe.bi.view.obj.GraphModel, java.util.Map)")
    };
    
    private static final long interfaceHash = 4948264772285978450L;
    
    public java.rmi.server.Operation[] getOperations() {
	return (java.rmi.server.Operation[]) operations.clone();
    }
    
    public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call, int opnum, long hash)
	throws java.lang.Exception
    {
	if (opnum < 0) {
	    if (hash == -112082108387577458L) {
		opnum = 0;
	    } else if (hash == 460403682279015130L) {
		opnum = 1;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash)
		throw new java.rmi.server.SkeletonMismatchException("interface hash mismatch");
	}
	
	oe.bi.analysis.BiForcastImpl server = (oe.bi.analysis.BiForcastImpl) obj;
	switch (opnum) {
	case 0: // fetchTimeDimensionLink(ViewModel)
	{
	    oe.bi.view.obj.ViewModel $param_ViewModel_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ViewModel_1 = (oe.bi.view.obj.ViewModel) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.fetchTimeDimensionLink($param_ViewModel_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 1: // performBiForcast(ViewModel, GraphModel, Map)
	{
	    oe.bi.view.obj.ViewModel $param_ViewModel_1;
	    oe.bi.view.obj.GraphModel $param_GraphModel_2;
	    java.util.Map $param_Map_3;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_ViewModel_1 = (oe.bi.view.obj.ViewModel) in.readObject();
		$param_GraphModel_2 = (oe.bi.view.obj.GraphModel) in.readObject();
		$param_Map_3 = (java.util.Map) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    oe.bi.view.obj.ViewModel $result = server.performBiForcast($param_ViewModel_1, $param_GraphModel_2, $param_Map_3);
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
