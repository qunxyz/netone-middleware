package oe.netone.buss.zb.baseinfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.web.util.WebStr;
import oescript.parent.OeScript;

/**
 * ��������SOA�ű�
 * @author robanco
 *
 */
public class Dsell_script extends OeScript{
	/**
	 * ��������ȡ��Ʒ����ű�
	 * ��Ӧ����ԴĿ¼�� SOASCRIPT.SOASCRIPT.ZB.FXGL.DPRK
	 * @return
	 */
	public String todo1(){
		return "Key1-"+WebStr.iso8859ToGBK("f��˳��")+",KEY2-�������ط�,KEY3-��˳��";
	}
	
	public String todo2(){
		return "Key1-"+WebStr.iso8859ToGBK("f��˳��")+",KEY2-�������ط�,KEY3-��˳��";
	}

	

}
