package oe.jz.sgjk;
import oe.midware.workflow.engine.rule2.func.STools;
import oe.security3a.seucore.obj.Clerk;
import oescript.parent.OeScript;

/**
 * 公司监控管理
 * @author robanco
 *
 */
public class Sgjk extends OeScript{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 施工新建保存事件
	 * 资源定位:SOASCRIPT.SOASCRIPT.JZHY.SGJK
	 */
	public static void soa_commmon_sgjk(){
		//#需要引入的包
		//import oe.midware.workflow.engine.rule2.func.STools;
		//import oe.security3a.seucore.obj.Clerk;		
		STools st=new STools();
		Clerk clerk =null;
		try {
				clerk=st.rs_.loadClerk("0000", "$(participant)");
		} catch (Exception e) {
				e.printStackTrace();
		}
		dy.set("$(lsh)"+":"+"$(formcode)","column7" , "$(participant)"+"["+clerk.getName()+"]");
		java.text.SimpleDateFormat dateformat=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a1=dateformat.format(new java.util.Date());
		dy.set("$(lsh)"+":"+"$(formcode)","column6" , a1);
	}

}
