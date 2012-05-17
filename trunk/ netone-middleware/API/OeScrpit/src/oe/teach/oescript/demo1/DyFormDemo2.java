package oe.teach.oescript.demo1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * 动态表单后台脚本应用 例子
 * 
 * 在新版本动态表单中通过前台脚本扩展和后台脚本扩展，来进一步提升定制表单的灵活性。其中后台脚本部分可对
 * 业务数据的进一步加工处理，中间件平台通过事件拦截提供脚本执行入口，涉及到的相关事件有<br>
 * 1、新建装载事件 <br>
 * 2、新建保存事件 <br>
 * 3、保存事件 <br>
 * 4、删除事件 <br>
 * 5、显示装载事件 <br>
 * 6、修改装载事件<br>
 * 
 * 
 * @author jiaxun chen
 * 
 */
public class DyFormDemo2  extends OeScript{
	/**
	 *最后修改时间<br>
	 *可以通过 $(column) 来获得当前提交的表单数据信息，其中column对应着动态表单中字段名，
	 */
	public void todo1(){
		
		//最后修改时间
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String a1=dateformat.format(new java.util.Date());
		//最后时间
		dy.set("$(formcode)", "$(lsh)", a1); 
		//最后修改人
		dy.set("$(formcode)", "column12", "$(participant)");
	}
	/*
	 * 默认子表单记录，在某些表单应用场景中，当我们新建一个表单的时候，该表单需要自动带出N条有默认数据的子表单记录，方便
	 * 用户填写。<br>
	 * 注意事件需要放在 创建装载事件中
	 */
	public List todo2(){
		
		TCsBus bux = new TCsBus();
		bux.setColumn3("创造创新能力");
		bux.setFatherlsh("$(fatherlsh)");
		bux.setFormcode("$(formcode)");
		
		TCsBus bux1 = new TCsBus();
		bux1.setColumn3("组织管理能力");
		bux1.setFatherlsh("$(fatherlsh)");
		bux1.setFormcode("$(formcode)");
		
		TCsBus bux2 = new TCsBus();
		bux2.setColumn3("人文素质");
		bux2.setFatherlsh("$(fatherlsh)");
		bux2.setFormcode("$(formcode)");
		
		TCsBus bux3 = new TCsBus();
		bux3.setColumn3("文体素质");
		bux3.setFatherlsh("$(fatherlsh)");
		bux3.setFormcode("$(formcode)");
		
		List list=new ArrayList();
		list.add(bux);
		list.add(bux1);
		list.add(bux2);
		list.add(bux3);
		
		return list;
	}

}
