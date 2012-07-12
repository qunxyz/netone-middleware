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
 * Ӧ�ýű��ڲ����� wf
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
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		 runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");

		// �����ݱ�����ֵ(ע�⣺�������������������ʱ�½��ı���)
		wf.set(runtimeid, "rev1", "OK");
		wf.set(runtimeid, "rev2", 100);
		
		// ��������
		wf.run(runtimeid);

		// ��ȡ�������
		int rev1 = wf.getn(runtimeid, "rev2");
		// ���������ֵ��ӡ������̨
		System.out.println(rev1);
		System.out.println("done---wf");
	}
	
	public static void todo2(){
		
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		String userinfo=wf.get(runtimeid, "customer");
		String usercode=StringUtils.substringBetween(userinfo, "[", "]");
		String username=StringUtils.substringBefore(userinfo, "[");
		
	}
	
	public static void todo3(){
		/* �ù��ܹ������ڵ���б����� dyΪ���������ڹ����������½���չ�Ե�ֵvaluemode��ֵΪdyform,��valueΪ�� */

		// ����Naturalname(����)
		String formNaturalname = "BUSSFORM.BUSSFORM.DY_81237253530248";
		// ������ʵ��
		String lsh = dy.newInstance(formNaturalname);
		// Ϊָ������ֵ
		dy.set(lsh, "column3", "dai");
		dy.set(lsh, "column4", 20);
		// ����һ������ʵ��ID
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");// BUSSWF.BUSSWF.OESCRIPTDEMO�����̾�̬ID(��������)
		// Ϊdy������ֵ
		wf.set(runtimeid, "dy", lsh);
		// ������dy��ֵ��ӡ������̨
		System.out.println(wf.get(runtimeid, "dy"));
		
		
		int planmode=wf.getn(runtimeid,"planmode");
		if(planmode==1) { /*ȡ�����*/
		   		String formid=wf.get(runtimeid, "formai");
		                  float number=0-dy.getn(formid, "column4");
		                  dy.set(formid,"column4",number);
		}
		

	}
	
	public static void invokeForWorkflow() throws Exception {
		// ���屣������ʵ��ID����
		String runtimeId = null;
		// ���屣�浱ǰ��ڵ�ʵ��ID����
		String workItemId = null;
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		runtimeId = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");
		// WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
		WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
		// console.runProcess(runtimeId);
		// ��������
		wf.run(runtimeId);
		// �ڵ��ID
		String activivtyId = "trackAction1237274662875";

		List worklist = view.fetchRunningWorklist(runtimeId, activivtyId); // ��ȡ�û�ڵ��ʵ������

		for (Iterator iter = worklist.iterator(); iter.hasNext();) {
			TWfWorklist work = (TWfWorklist) iter.next();
			workItemId = work.getWorkcode();

		}
		// ������ʵ��Id�����̻�ڵ�ʵ��ID��ӡ������̨
		System.out.println("runtimeid: " + runtimeId + "  workItemId : "
				+ workItemId);

		/*-
		 * ��ѯ����Ѳ�������bean��������
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
		dy.set("$(lsh)"+":"+"$(formcode)","column15" , "�½�");
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
		
		/*��������а󶨵ı�ʵ��*/
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
