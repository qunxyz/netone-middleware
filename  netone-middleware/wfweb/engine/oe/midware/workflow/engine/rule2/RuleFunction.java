package oe.midware.workflow.engine.rule2;

import java.util.List;

import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;

/**
 * 规则内部处理机制,该类中的两个方法get,set是被 规则脚本所用到的的
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class RuleFunction {

	public RuleFunction(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	private String runtimeid;

	public void set(String varid, String value) {
		if (runtimeid == null) {
			return;
		}
		TWfRelevantvar var = new TWfRelevantvar();
		var.setRuntimeid(runtimeid);
		var.setDatafieldid(varid);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(var,
				null);
		if (list == null) {
			return;
		} else {
			TWfRelevantvar varpre = (TWfRelevantvar) list.get(0);
			varpre.setValuenow(value);
			OrmerEntry.fetchOrmer().fetchSerializer().update(varpre);
		}
	}

	public void setn(String varid, int value) {
		set(varid, String.valueOf(value));
	}

	public void setd(String varid, double value) {
		set(varid, String.valueOf(value));
	}

	public void setf(String varid, float value) {
		set(varid, String.valueOf(value));
	}

	public String get(String varid) {
		if (runtimeid == null) {
			return "0";
		}
		TWfRelevantvar var = new TWfRelevantvar();
		var.setRuntimeid(runtimeid);
		var.setDatafieldid(varid);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(var,
				null);
		if (list == null) {
			return null;
		} else {
			TWfRelevantvar varpre = (TWfRelevantvar) list.get(0);
			return varpre.getValuenow();
		}
	}

	public double getd(String varid) {
		String obj = get(varid);
		try {
			Double value = Double.parseDouble(obj);
			return value.doubleValue();
		} catch (Exception e) {
			return -1.0;
		}
	}

	public float getf(String varid) {
		String obj = get(varid);
		try {
			Float value = Float.parseFloat(obj);
			return value.floatValue();
		} catch (Exception e) {
			return -1;
		}
	}

	public int getn(String varid) {
		String obj = get(varid);
		try {
			Integer value = Integer.parseInt(obj);
			return value.intValue();
		} catch (Exception e) {
			return -1;
		}
	}



}
