package oe.teach.oescript;

import org.apache.commons.lang.StringUtils;

import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 wf
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class wfDemo extends OeScript {
	static String runtimeid;
	public static void main(String[] args) {



	}
	
	public static void todo1(){
		// 创建流程实例BUSSWF.BUSSWF.OESCRIPTDEMO 对应着 中间件平台上的 OeScript测试)
		 runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");

		// 对数据变量赋值(注意：变量的名称是流程设计时新建的变量)
		wf.set(runtimeid, "rev1", "OK");
		wf.set(runtimeid, "rev2", 100);
		


		// 启动流程
		wf.run(runtimeid);

		// 读取相关数据
		int rev1 = wf.getn(runtimeid, "rev2");
		// 将相关数据值打印到控制台
		System.out.println(rev1);
		System.out.println("done---wf");
	}
	
	public static void todo2(){
		
		// 创建流程实例BUSSWF.BUSSWF.OESCRIPTDEMO 对应着 中间件平台上的 OeScript测试)
		String userinfo=wf.get(runtimeid, "customer");
		String usercode=StringUtils.substringBetween(userinfo, "[", "]");
		String username=StringUtils.substringBefore(userinfo, "[");
		
	}
}
