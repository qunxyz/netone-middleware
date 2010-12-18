package oe.security3a.seupublic.authentication.obj.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 属性集合
 * 
 * @author chen.jia.xun
 * 
 */
public class AttributeSetCtx implements Serializable {

	private Set<AttributeCtx> attribute;

	public void newAttribute(String key, String value) {
		if (attribute == null) {
			attribute = new HashSet();
		}
		AttributeCtx attr = new AttributeCtx(key, value);
		attribute.add(attr);
	}

	public Set<AttributeCtx> getAttribute() {
		return attribute;
	}

	public void setAttribute(Set<AttributeCtx> attribute) {
		this.attribute = attribute;
	}

	public void addAttribute(AttributeCtx attribute) {
		if (this.attribute == null) {
			this.attribute = new HashSet();
		}
		this.attribute.add(attribute);
	}

	public void removeAttribute(AttributeCtx attribute) {
		if (this.attribute == null) {
			return;
		}
		this.attribute.remove(attribute);
	}

	/**
	 * 对象的唯一索引
	 * 
	 * @return
	 */
	public String hashKey() {
		StringBuffer but = new StringBuffer();
		for (Iterator itr = attribute.iterator(); itr.hasNext();) {
			AttributeCtx ctx = (AttributeCtx) itr.next();
			but.append(ctx.getName() + "=" + ctx.getValue() + ",");
		}
		return but.toString();

	}
}
