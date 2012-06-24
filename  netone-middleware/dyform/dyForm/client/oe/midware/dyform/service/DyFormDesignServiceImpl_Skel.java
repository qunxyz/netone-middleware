// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package oe.midware.dyform.service;

public final class DyFormDesignServiceImpl_Skel
    implements java.rmi.server.Skeleton
{
    private static final java.rmi.server.Operation[] operations = {
	new java.rmi.server.Operation("java.lang.String addColumn(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("java.lang.String create(oe.cav.bean.logic.form.TCsForm, java.lang.String)[]"),
	new java.rmi.server.Operation("java.lang.String dropColumn(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("boolean dropForm(oe.cav.bean.logic.form.TCsForm)"),
	new java.rmi.server.Operation("java.lang.String formDescription(java.lang.String)"),
	new java.rmi.server.Operation("oe.cav.bean.logic.tools.DyObj fromDy(java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String fromDyObj(oe.cav.bean.logic.tools.DyObj)"),
	new java.rmi.server.Operation("oe.cav.bean.logic.tools.DyObj fromSQL(java.lang.String, java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String getNextColumn(java.lang.String)"),
	new java.rmi.server.Operation("long getNextIndexValue(java.lang.String)"),
	new java.rmi.server.Operation("java.util.List listDyObjFromLevel(java.lang.String)"),
	new java.rmi.server.Operation("oe.cav.bean.logic.column.TCsColumn loadColumn(java.lang.String, java.io.Serializable)"),
	new java.rmi.server.Operation("boolean movedownColumn(java.lang.String, java.lang.String, java.lang.String)"),
	new java.rmi.server.Operation("boolean moveupColumn(java.lang.String, java.lang.String, java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String parseViewType(java.lang.String)[]"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.column.TCsColumn, int, int)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.column.TCsColumn, int, int, java.lang.String)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.column.TCsColumn, java.lang.String)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.form.TCsForm)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.form.TCsForm, int, int)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.form.TCsForm, int, int, java.lang.String)"),
	new java.rmi.server.Operation("java.util.List queryObjects(oe.cav.bean.logic.form.TCsForm, java.lang.String)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.cav.bean.logic.column.TCsColumn, java.lang.String)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.cav.bean.logic.form.TCsForm)"),
	new java.rmi.server.Operation("long queryObjectsNumber(oe.cav.bean.logic.form.TCsForm, java.lang.String)"),
	new java.rmi.server.Operation("java.util.List queryObjectsUrl(java.lang.String)"),
	new java.rmi.server.Operation("boolean resizeColumnIndexValue(java.lang.String, java.lang.String)"),
	new java.rmi.server.Operation("java.lang.String updateColumn(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("java.lang.String updateColumnView(oe.cav.bean.logic.column.TCsColumn)"),
	new java.rmi.server.Operation("boolean updateForm(oe.cav.bean.logic.form.TCsForm)")
    };
    
    private static final long interfaceHash = -7002038514782190261L;
    
    public java.rmi.server.Operation[] getOperations() {
	return (java.rmi.server.Operation[]) operations.clone();
    }
    
    public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call, int opnum, long hash)
	throws java.lang.Exception
    {
	if (opnum < 0) {
	    if (hash == 2634447396021360586L) {
		opnum = 0;
	    } else if (hash == 1556187260694175781L) {
		opnum = 1;
	    } else if (hash == 7402733203527835865L) {
		opnum = 2;
	    } else if (hash == -4373006195158114182L) {
		opnum = 3;
	    } else if (hash == 2481426259236268859L) {
		opnum = 4;
	    } else if (hash == -1581258887312153277L) {
		opnum = 5;
	    } else if (hash == 6117677232398371592L) {
		opnum = 6;
	    } else if (hash == 3531348311875074841L) {
		opnum = 7;
	    } else if (hash == -3640816603369953488L) {
		opnum = 8;
	    } else if (hash == -3784025994445985539L) {
		opnum = 9;
	    } else if (hash == 7176163625422039430L) {
		opnum = 10;
	    } else if (hash == 999736544780109577L) {
		opnum = 11;
	    } else if (hash == 354564514460112861L) {
		opnum = 12;
	    } else if (hash == 5735718690013150883L) {
		opnum = 13;
	    } else if (hash == 8445271422392880129L) {
		opnum = 14;
	    } else if (hash == -757869916437176698L) {
		opnum = 15;
	    } else if (hash == 3589302673131975491L) {
		opnum = 16;
	    } else if (hash == -6471450459186451731L) {
		opnum = 17;
	    } else if (hash == 1901854186563156954L) {
		opnum = 18;
	    } else if (hash == -4949895506325029151L) {
		opnum = 19;
	    } else if (hash == -7057197919500946191L) {
		opnum = 20;
	    } else if (hash == 1010033046315739291L) {
		opnum = 21;
	    } else if (hash == -345158052365926477L) {
		opnum = 22;
	    } else if (hash == -5313284381005627202L) {
		opnum = 23;
	    } else if (hash == 3419453087047444736L) {
		opnum = 24;
	    } else if (hash == 1640563444064992589L) {
		opnum = 25;
	    } else if (hash == -5011899816666930625L) {
		opnum = 26;
	    } else if (hash == 779508253068444054L) {
		opnum = 27;
	    } else if (hash == -7836113802061637813L) {
		opnum = 28;
	    } else if (hash == -8711145843332917081L) {
		opnum = 29;
	    } else if (hash == 3064710673927008252L) {
		opnum = 30;
	    } else if (hash == -8664334215240035492L) {
		opnum = 31;
	    } else {
		throw new java.rmi.UnmarshalException("invalid method hash");
	    }
	} else {
	    if (hash != interfaceHash)
		throw new java.rmi.server.SkeletonMismatchException("interface hash mismatch");
	}
	
	oe.midware.dyform.service.DyFormDesignServiceImpl server = (oe.midware.dyform.service.DyFormDesignServiceImpl) obj;
	switch (opnum) {
	case 0: // addColumn(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.addColumn($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 1: // create(TCsForm, String)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String[] $result = server.create($param_TCsForm_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 2: // dropColumn(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.dropColumn($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 3: // dropForm(TCsForm)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.dropForm($param_TCsForm_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 4: // formDescription(String)
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
	    java.lang.String $result = server.formDescription($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 5: // fromDy(String)
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
	    oe.cav.bean.logic.tools.DyObj $result = server.fromDy($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 6: // fromDyObj(DyObj)
	{
	    oe.cav.bean.logic.tools.DyObj $param_DyObj_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_DyObj_1 = (oe.cav.bean.logic.tools.DyObj) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.fromDyObj($param_DyObj_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 7: // fromSQL(String, String)
	{
	    java.lang.String $param_String_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    oe.cav.bean.logic.tools.DyObj $result = server.fromSQL($param_String_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 8: // getNextColumn(String)
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
	    java.lang.String $result = server.getNextColumn($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 9: // getNextIndexValue(String)
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
	    long $result = server.getNextIndexValue($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeLong($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 10: // listDyObjFromLevel(String)
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
	    java.util.List $result = server.listDyObjFromLevel($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 11: // loadColumn(String, Serializable)
	{
	    java.lang.String $param_String_1;
	    java.io.Serializable $param_Serializable_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_Serializable_2 = (java.io.Serializable) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    oe.cav.bean.logic.column.TCsColumn $result = server.loadColumn($param_String_1, $param_Serializable_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 12: // movedownColumn(String, String, String)
	{
	    java.lang.String $param_String_1;
	    java.lang.String $param_String_2;
	    java.lang.String $param_String_3;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
		$param_String_3 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.movedownColumn($param_String_1, $param_String_2, $param_String_3);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 13: // moveupColumn(String, String, String)
	{
	    java.lang.String $param_String_1;
	    java.lang.String $param_String_2;
	    java.lang.String $param_String_3;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
		$param_String_3 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.moveupColumn($param_String_1, $param_String_2, $param_String_3);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 14: // parseViewType(String)
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
	    java.lang.String[] $result = server.parseViewType($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 15: // queryObjects(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 16: // queryObjects(TCsColumn, int, int)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    int $param_int_2;
	    int $param_int_3;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
		$param_int_2 = in.readInt();
		$param_int_3 = in.readInt();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsColumn_1, $param_int_2, $param_int_3);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 17: // queryObjects(TCsColumn, int, int, String)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    int $param_int_2;
	    int $param_int_3;
	    java.lang.String $param_String_4;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
		$param_int_2 = in.readInt();
		$param_int_3 = in.readInt();
		$param_String_4 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsColumn_1, $param_int_2, $param_int_3, $param_String_4);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 18: // queryObjects(TCsColumn, String)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsColumn_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 19: // queryObjects(TCsForm)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsForm_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 20: // queryObjects(TCsForm, int, int)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    int $param_int_2;
	    int $param_int_3;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
		$param_int_2 = in.readInt();
		$param_int_3 = in.readInt();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsForm_1, $param_int_2, $param_int_3);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 21: // queryObjects(TCsForm, int, int, String)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    int $param_int_2;
	    int $param_int_3;
	    java.lang.String $param_String_4;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
		$param_int_2 = in.readInt();
		$param_int_3 = in.readInt();
		$param_String_4 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsForm_1, $param_int_2, $param_int_3, $param_String_4);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 22: // queryObjects(TCsForm, String)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.util.List $result = server.queryObjects($param_TCsForm_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 23: // queryObjectsNumber(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    long $result = server.queryObjectsNumber($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeLong($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 24: // queryObjectsNumber(TCsColumn, String)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    long $result = server.queryObjectsNumber($param_TCsColumn_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeLong($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 25: // queryObjectsNumber(TCsForm)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    long $result = server.queryObjectsNumber($param_TCsForm_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeLong($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 26: // queryObjectsNumber(TCsForm, String)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    long $result = server.queryObjectsNumber($param_TCsForm_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeLong($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 27: // queryObjectsUrl(String)
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
	    java.util.List $result = server.queryObjectsUrl($param_String_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 28: // resizeColumnIndexValue(String, String)
	{
	    java.lang.String $param_String_1;
	    java.lang.String $param_String_2;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_String_1 = (java.lang.String) in.readObject();
		$param_String_2 = (java.lang.String) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.resizeColumnIndexValue($param_String_1, $param_String_2);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 29: // updateColumn(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.updateColumn($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 30: // updateColumnView(TCsColumn)
	{
	    oe.cav.bean.logic.column.TCsColumn $param_TCsColumn_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsColumn_1 = (oe.cav.bean.logic.column.TCsColumn) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    java.lang.String $result = server.updateColumnView($param_TCsColumn_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeObject($result);
	    } catch (java.io.IOException e) {
		throw new java.rmi.MarshalException("error marshalling return", e);
	    }
	    break;
	}
	    
	case 31: // updateForm(TCsForm)
	{
	    oe.cav.bean.logic.form.TCsForm $param_TCsForm_1;
	    try {
		java.io.ObjectInput in = call.getInputStream();
		$param_TCsForm_1 = (oe.cav.bean.logic.form.TCsForm) in.readObject();
	    } catch (java.io.IOException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } catch (java.lang.ClassNotFoundException e) {
		throw new java.rmi.UnmarshalException("error unmarshalling arguments", e);
	    } finally {
		call.releaseInputStream();
	    }
	    boolean $result = server.updateForm($param_TCsForm_1);
	    try {
		java.io.ObjectOutput out = call.getResultStream(true);
		out.writeBoolean($result);
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