package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * 综合测试，应用 dy 和 wf对象 一起完成一个业务逻辑的实现
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class wfAndDyDemo extends OeScript {
	public static void main(String[] args) {
		/* 该功能工作流节点过行表单配制 dy为变量名称在工作流当中新建扩展性的值valuemode的值为dyform,的value为空 */

		// 表单的Naturalname(名称)
		String formNaturalname = "BUSSFORM.BUSSFORM.DY_81237253530248";
		// 创建表单实例
		String lsh = dy.newInstance(formNaturalname);
		// 为指定列设值
		dy.set(lsh, "column3", "dai");
		dy.set(lsh, "column4", 20);
		// 创建一个流程实列ID
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");// BUSSWF.BUSSWF.OESCRIPTDEMO是流程静态ID(流程名称)
		// 为dy变量设值
		wf.set(runtimeid, "dy", lsh);
		// 将变量dy的值打印到控制台
		System.out.println(wf.get(runtimeid, "dy"));
		
		
		int planmode=wf.getn(runtimeid,"planmode");
		if(planmode==1) { /*取消库存*/
		   		String formid=wf.get(runtimeid, "formai");
		                  float number=0-dy.getn(formid, "column4");
		                  dy.set(formid,"column4",number);
		}
		
	}
	
	
	
}
