package oe.security3a.seupublic.authentication.obj.core;

import java.io.Serializable;

/**
 * ������
 * 
 * @author chen.jia.xun
 * 
 */
public class ActionCtx extends AttributeSetCtx implements Serializable{
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
