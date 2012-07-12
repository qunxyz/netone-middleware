package oe.teach.oescript;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.midware.workflow.engine.rule2.func.STools;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.seucore.obj.Clerk;
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
	
	public static void todo3(){
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
	
	public static void invokeForWorkflow() throws Exception {
		// 定义保存流程实例ID变量
		String runtimeId = null;
		// 定义保存当前活动节点实例ID变量
		String workItemId = null;
		// 创建流程实例BUSSWF.BUSSWF.OESCRIPTDEMO 对应着 中间件平台上的 OeScript测试)
		runtimeId = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");
		// WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
		// console.runProcess(runtimeId);
		// 启动流程
		wf.run(runtimeId);
		// 节点的ID
		String activivtyId = "trackAction1237274662875";

		List worklist = view.fetchRunningWorklist(runtimeId, activivtyId); // 获取该活动节点的实例对象

		for (Iterator iter = worklist.iterator(); iter.hasNext();) {
			TWfWorklist work = (TWfWorklist) iter.next();
			workItemId = work.getWorkcode();

		}
		// 将流程实例Id与流程活动节点实例ID打印到控制台
		System.out.println("runtimeid: " + runtimeId + "  workItemId : "
				+ workItemId);

		/*-
		 * 查询启动巡检任务的bean服务并启动
		 */
		// String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.INISTASK");
		//		
		// System.out.println("task bean id : " + beanid);
		//		 
		// Object obj = bean.getInobj(beanid);
		// bean.setIn("taskName", "taskTest", obj);
		// bean.setIn("runtimeId", "1111", obj);
		// bean.setIn("workItemId", "2222", obj);
		// bean.setIn("paramStr", "ParamTest:pt[8888]", obj);
		// bean.setIn("model", "one", obj);
		// bean.submit(obj);
		// String rs = bean.run(beanid);
		// System.out.println(rs);
		// wf.commitAct(workItemId);
	}
	
	public void todox(){
		dy.set("$(lsh)"+":"+"$(formcode)","column15" , "新建");
		STools st=new STools();
		Clerk clerk=null;
		try {
			clerk=st.rs_.loadClerk("0000", "$(participant)");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dy.set("$(lsh)"+":"+"$(formcode)","column13" , "$(participant)"+"["+clerk.getName()+"]");
		java.text.SimpleDateFormat dateformat=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a1=dateformat.format(new java.util.Date());
		dy.set("$(lsh)"+":"+"$(formcode)","column12" , a1);
		
		
//		String dyformnaturalname = "BUSSFORM.BUSSFORM.XYCIT.RCZP.DY_901227410298808";
//		String lsh = dy.newInstance(dyformnaturalname);
//		String position = wf.get(runtimeid, "position");
//		dy.set(lsh, "belongx", position);
//		wf.set(runtimeid, "applyInfo", lsh);
		
		
//		String dyformnaturalname = "BUSSFORM.BUSSFORM.FLOWFORM.DY_251234231749142";
//		String lsh = dy.newInstance(dyformnaturalname);
//        dy.set(lsh, "column3", "Robanco"); 
//		wf.set(runtimeid, "formbinds", lsh);
		
		/*获得流程中绑定的表单实例*/
		String lshinfo=wf.get("402882821f69a800011f69aa8d370019", "formbinds");
		String checkinfo=dy.get(lshinfo, "column4");
		System.out.println(lshinfo+","+checkinfo);
		
		
		String formnatrualname="BUSSFORM.BUSSFORM.DY_321248053007297";
		String formruntimeid=dy.newInstance(formnatrualname);
		wf.set(runtimeid, "formai", formruntimeid);
		
		String formid=wf.get(runtimeid, "formai");
		String varinfo=dy.get(formid, "column7");
		
		boolean rs="new".equals(varinfo);
		
		wf.set(runtimeid, "isnew", rs?1:0);
		

	}

}
