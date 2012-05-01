package oe.msg.bean;

import oe.mid.soa.bean.OecBean;
import oe.mid.soa.bean.SoaBean;
import oe.mid.soa.bean.SoaBeanTools;
import oe.msg.bean.util.PLog;
import oe.msg.bean.util.SMSMachine;

/**
 * wild bean sample <br>
 * 
 * 非面向对象，原始自由的开发
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class MsgBean implements OecBean {

	public SoaBean todo(SoaBean obj) {
		String mobile = (String) obj.getValues().get("mobile");
		String context = (String) obj.getValues().get("context");
		
		int rs=SMSMachine.run(mobile, context);
		SoaBean outvalue = outParamDescription();
		if(rs==0){
			outvalue.getValues().put("types", "ok");
		}else{
			outvalue.getValues().put("types", "fail");
		}
		return outvalue;
	}

	/**
	 * 根据XML描述返回参数对象,该对象在远程被使用,并且写入相关的值
	 */
	public SoaBean inParamDescription() {
		return SoaBeanTools.fromXml(InObject.class);
	}

	public SoaBean outParamDescription() {
		return SoaBeanTools.fromXml(OutObject.class);
	}

}
