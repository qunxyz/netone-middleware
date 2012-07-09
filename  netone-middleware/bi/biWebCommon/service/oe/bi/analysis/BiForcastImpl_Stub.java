// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.bi.analysis;

public final class BiForcastImpl_Stub
    extends java.rmi.server.RemoteStub
    implements oe.bi.analysis.BiForcast, java.rmi.Remote
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("java.lang.String fetchTimeDimensionLink(oe.bi.view.obj.ViewModel)"),
	new java.rmi.server.Operation("oe.bi.view.obj.ViewModel performBiForcast(oe.bi.view.obj.ViewModel, oe.bi.view.obj.GraphModel, java.util.Map)")
    };
    
    private static final long interfaceHash = 4948264772285978450L;
    
    private static final long serialVersionUID = 2;
    
    private static boolean useNewInvoke;
    private static java.lang.reflect.Method $method_fetchTimeDimensionLink_0;
    private static java.lang.reflect.Method $method_performBiForcast_1;
    
    static {
	try {
	    java.rmi.server.RemoteRef.class.getMethod("invoke",
		new java.lang.Class[] {
		    java.rmi.Remote.class,
		    java.lang.reflect.Method.class,
		    java.lang.Object[].class,
		    long.class
		});
	    useNewInvoke = true;
	    $method_fetchTimeDimensionLink_0 = oe.bi.analysis.BiForcast.class.getMethod("fetchTimeDimensionLink", new java.lang.Class[] {oe.bi.view.obj.ViewModel.class});
	    $method_performBiForcast_1 = oe.bi.analysis.BiForcast.class.getMethod("performBiForcast", new java.lang.Class[] {oe.bi.view.obj.ViewModel.class, oe.bi.view.obj.GraphModel.class, java.util.Map.class});
	} catch (java.lang.NoSuchMethodException e) {
	    useNewInvoke = false;
	}
    }
    
    // constructors
    public BiForcastImpl_Stub() {
	super();
    }
    public BiForcastImpl_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of fetchTimeDimensionLink(ViewModel)
    public java.lang.String fetchTimeDimensionLink(oe.bi.view.obj.ViewModel $param_ViewModel_1)
	throws java.rmi.RemoteException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_fetchTimeDimensionLink_0, new java.lang.Object[] {$param_ViewModel_1}, -112082108387577458L);
		return ((java.lang.String) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 0, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_ViewModel_1);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		java.lang.String $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (java.lang.String) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of performBiForcast(ViewModel, GraphModel, Map)
    public oe.bi.view.obj.ViewModel performBiForcast(oe.bi.view.obj.ViewModel $param_ViewModel_1, oe.bi.view.obj.GraphModel $param_GraphModel_2, java.util.Map $param_Map_3)
	throws java.rmi.RemoteException, oe.bi.exceptions.MoreThenOneDimensionViewModel, oe.bi.exceptions.NeedMoreThenForcastOneValueException
    {
	try {
	    if (useNewInvoke) {
		Object $result = ref.invoke(this, $method_performBiForcast_1, new java.lang.Object[] {$param_ViewModel_1, $param_GraphModel_2, $param_Map_3}, 460403682279015130L);
		return ((oe.bi.view.obj.ViewModel) $result);
	    } else {
		java.rmi.server.RemoteCall call = ref.newCall((java.rmi.server.RemoteObject) this, operations, 1, interfaceHash);
		try {
		    java.io.ObjectOutput out = call.getOutputStream();
		    out.writeObject($param_ViewModel_1);
		    out.writeObject($param_GraphModel_2);
		    out.writeObject($param_Map_3);
		} catch (java.io.IOException e) {
		    throw new java.rmi.MarshalException("error marshalling arguments", e);
		}
		ref.invoke(call);
		oe.bi.view.obj.ViewModel $result;
		try {
		    java.io.ObjectInput in = call.getInputStream();
		    $result = (oe.bi.view.obj.ViewModel) in.readObject();
		} catch (java.io.IOException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} catch (java.lang.ClassNotFoundException e) {
		    throw new java.rmi.UnmarshalException("error unmarshalling return", e);
		} finally {
		    ref.done(call);
		}
		return $result;
	    }
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (oe.bi.exceptions.MoreThenOneDimensionViewModel e) {
	    throw e;
	} catch (oe.bi.exceptions.NeedMoreThenForcastOneValueException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}