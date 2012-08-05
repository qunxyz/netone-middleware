package oe.jz.khjd;


import org.apache.commons.lang.StringUtils;

import oescript.parent.OeScript;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.WfEntry;
import com.jl.common.workflow.TWfConsoleIfc;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import com.jl.common.workflow.TWfActive;

/**
 * 客户接待业务，工作流环节中的脚本逻辑
 * @author robanco
 *
 */
public class Khjd_Wfscript extends OeScript{
	
	String workcode="";//模拟工作流环境中的变量workcode
	String runtimeid="";//模拟工作流环境中的变量runtime
	
	/**
	 * 所有节点的启动事件里都加入该脚本，用于在表单中标示业务状态
	 * @throws Exception
	 */
	public void todo1() throws Exception{
		//#需要加入如下一些引用
		//import com.jl.common.workflow.TWfConsoleIfc;
		//import com.jl.common.workflow.TWfActive;

		//import com.jl.common.app.*;
		//import oe.midware.workflow.runtime.ormobj.TWfWorklist;
		
		//将流程状态写入业务表单中去

		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();		
		String actname=com.jl.common.workflow.WfEntry.iv().loadActive(busstype, workcode).getName();
		
		dy.set(bussid+":"+formcode,"column48" , actname);
	}
	
	/**
	 * 根据来源来自动筛选设计部门
	 */
	public  void todo2()throws Exception{
		
		//import com.jl.common.app.*;
		
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String formNa=AppEntry.iv().loadApp(busstype).getFormnatualname();
		String column31=dy.get(bussid+":"+formcode,"column31");
		
		TCsBus bus=new TCsBus();
		bus.setColumn31(column31);
		int rsx=dy.queryDataNum(formNa, bus, "");
		if("1".equals(column31)||"2".equals(column31)){// 如果是 来电 
			   int rs=rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
		}
		if("3".equals(column31)){
			   int rs=5-rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
		}
	}
	
	/**
	 * 人员成绩添加 得分 
	 */
	public  void todo3()throws Exception {
		
		//import org.apache.commons.lang.StringUtils;
		//import com.jl.common.app.*;

		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column33");/*设计师字段*/
		/*往人员统计表单中写入数据*/
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", designer);
		dy.set(lsh,"column4","1");/*1表示得分,0标示失分*/
		/*追加新设计师*/
		String designer2=dy.get(lshinfo, "column47");/*设计师字段*/
		String []designerArr=StringUtils.split(designer2,",");
		for(int i=0;i<designerArr.length;i++){
			String lshx=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
			dy.set(lshx, "fatherlsh", bussid);
			dy.set(lshx, "column3", designer);
			dy.set(lshx,"column4","1");/*1表示得分,0标示失分*/
		}
		/*往部门统计表中加入数据*/
		String dept=dy.get(lshinfo, "column30");/*部门字段*/
		String lsh2=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh2, "fatherlsh", bussid);
		dy.set(lsh2, "column3", dept);
		dy.set(lsh2,"column4","1");/*1表示得分,0标示失分*/
		
	}
	
	/**
	 * 人员成绩添加 丢分
	 * @throws Exception
	 */
	public  void todo4()throws Exception {
		
		
		//import org.apache.commons.lang.StringUtils;
		//import com.jl.common.app.*;
		
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column33");/*设计师字段*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", designer);
		dy.set(lsh,"column4","0");/*1表示得分,0标示失分*/
		/*追加新设计师*/
		String designer2=dy.get(lshinfo, "column47");/*设计师字段*/
		String []designerArr=StringUtils.split(designer2,",");
		for(int i=0;i<designerArr.length;i++){
			String lshx=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
			dy.set(lshx, "fatherlsh", bussid);
			dy.set(lshx, "column3", designer);
			dy.set(lshx,"column4","0");/*1表示得分,0标示失分*/
		}
		
		/*往部门统计表中加入数据*/
		String dept=dy.get(lshinfo, "column30");/*部门字段*/
		String lsh2=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh2, "fatherlsh", bussid);
		dy.set(lsh2, "column3", dept);
		dy.set(lsh2,"column4","0");/*1表示得分,0标示失分*/
	}

}
