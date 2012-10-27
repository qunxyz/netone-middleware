package oe.ndyd;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.WfEntry;

/**
 * �ͻ��Ӵ�ҵ�񣬹����������еĽű��߼�
 * @author robanco
 *
 */
public class Wfscript extends OeScript{
	
	String workcode="";//ģ�⹤���������еı���workcode
	String runtimeid="";//ģ�⹤���������еı���runtime
	
	/**
	 * ���нڵ�������¼��ﶼ����ýű��������ڱ��б�ʾҵ��״̬
	 * @throws Exception
	 */
	public void todo1() throws Exception{
		//#��Ҫ��������һЩ����
		//import com.jl.common.workflow.TWfConsoleIfc;
		//import com.jl.common.workflow.TWfActive;

		//import com.jl.common.app.*;
		//import oe.midware.workflow.runtime.ormobj.TWfWorklist;
		
		//������״̬д��ҵ�����ȥ

		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();		
		String actname=com.jl.common.workflow.WfEntry.iv().loadActive(busstype, workcode).getName();
		
		dy.set(bussid+":"+formcode,"column48" , actname);
		
		String user=dy.get(bussid+":"+formcode, "participant");
	}
	
	public void todoxx()throws Exception{
		String []workcode=WfEntry.iv().getRunningWorkCodeByRuntimeid(runtimeid);
		if(workcode==null||workcode.length==0){
			return;
		}
	}
	
	/**
	 * ������Դ���Զ�ɸѡ��Ʋ���
	 */
	public  void todo2()throws Exception{
		
		//import com.jl.common.app.*;
		
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String formNa="BUSSFORM.BUSSFORM.TEST.JJXT.DY_731343095376648";
		String column31=dy.get(bussid+":"+formcode,"column31");
		
		TCsBus bus=new TCsBus();
		bus.setColumn31(column31);
		int rsx=dy.queryDataNum(formNa, bus, "");
		int rs=1;
		if("1".equals(column31)||"2".equals(column31)){// ����� ���� 
			rs=rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
		}
		if("3".equals(column31)){
			   rs=5-rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
		}

		String sql="select column3 hr from DY_71344346481385  where column5= '"+rs+"'";
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list=db.queryData(con, sql);
		if(list.size()>0){
			Map mapx=(Map)list.get(0);
			dy.set(bussid+":"+formcode,"column49" ,mapx.get("hr"));
		}
	}
	
	/**
	 * ��Ա�ɼ���� �÷� 
	 */
	public  void todo3()throws Exception {
		
		//import org.apache.commons.lang.StringUtils;
		//import com.jl.common.app.*;

		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column49");/*���ʦ�ֶ�*/
		/*����Աͳ�Ʊ���д������*/
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", designer);
		dy.set(lsh,"column4","1");/*1��ʾ�÷�,0��ʾʧ��*/
		/*׷�������ʦ*/
		String designer2=dy.get(lshinfo, "column47");/*���ʦ�ֶ�*/
		String []designerArr=StringUtils.split(designer2,",");
		for(int i=0;i<designerArr.length;i++){
			String lshx=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
			dy.set(lshx, "fatherlsh", bussid);
			dy.set(lshx, "column3", designer);
			dy.set(lshx,"column4","1");/*1��ʾ�÷�,0��ʾʧ��*/
		}
		/*������ͳ�Ʊ��м�������*/
		String dept=dy.get(lshinfo, "column30");/*�����ֶ�*/
		String lsh2=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh2, "fatherlsh", bussid);
		dy.set(lsh2, "column3", dept);
		dy.set(lsh2,"column4","1");/*1��ʾ�÷�,0��ʾʧ��*/
		
	}
	
	/**
	 * ��Ա�ɼ���� ����
	 * @throws Exception
	 */
	public  void todo4()throws Exception {
				
		//import org.apache.commons.lang.StringUtils;
		//import com.jl.common.app.*;
		
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column49");/*���ʦ�ֶ�*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", designer);
		dy.set(lsh,"column4","0");/*1��ʾ�÷�,0��ʾʧ��*/
		/*׷�������ʦ*/
		String designer2=dy.get(lshinfo, "column47");/*���ʦ�ֶ�*/
		String []designerArr=StringUtils.split(designer2,",");
		for(int i=0;i<designerArr.length;i++){
			String lshx=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103968");
			dy.set(lshx, "fatherlsh", bussid);
			dy.set(lshx, "column3", designer);
			dy.set(lshx,"column4","0");/*1��ʾ�÷�,0��ʾʧ��*/
		}
		
		/*������ͳ�Ʊ��м�������*/
		String dept=dy.get(lshinfo, "column30");/*�����ֶ�*/
		String lsh2=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh2, "fatherlsh", bussid);
		dy.set(lsh2, "column3", dept);
		dy.set(lsh2,"column4","0");/*1��ʾ�÷�,0��ʾʧ��*/
	}
	
	

}
