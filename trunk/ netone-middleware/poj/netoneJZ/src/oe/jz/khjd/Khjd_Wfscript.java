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
 * �ͻ��Ӵ�ҵ�񣬹����������еĽű��߼�
 * @author robanco
 *
 */
public class Khjd_Wfscript extends OeScript{
	
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
	}
	
	/**
	 * ������Դ���Զ�ɸѡ��Ʋ���
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
		if("1".equals(column31)||"2".equals(column31)){// ����� ���� 
			   int rs=rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
		}
		if("3".equals(column31)){
			   int rs=5-rsx %5;
			   dy.set(bussid+":"+formcode,"column30" ,String.valueOf(rs) );
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
		String designer=dy.get(lshinfo, "column33");/*���ʦ�ֶ�*/
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
		String designer=dy.get(lshinfo, "column33");/*���ʦ�ֶ�*/
		
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
