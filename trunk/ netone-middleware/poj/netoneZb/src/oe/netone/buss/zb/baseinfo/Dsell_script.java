package oe.netone.buss.zb.baseinfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.web.util.WebStr;
import oescript.parent.OeScript;

/**
 * 分销管理SOA脚本
 * @author robanco
 *
 */
public class Dsell_script extends OeScript{
	/**
	 * 分销入库获取单品条码脚本
	 * 对应的资源目录： SOASCRIPT.SOASCRIPT.ZB.FXGL.DPRK
	 * @return
	 */
	public String todo1(){
		return "Key1-"+WebStr.iso8859ToGBK("f发顺丰")+",KEY2-所发生地方,KEY3-发顺丰";
	}
	
	public String todo2(){
		return "Key1-"+WebStr.iso8859ToGBK("f发顺丰")+",KEY2-所发生地方,KEY3-发顺丰";
	}

	

}
