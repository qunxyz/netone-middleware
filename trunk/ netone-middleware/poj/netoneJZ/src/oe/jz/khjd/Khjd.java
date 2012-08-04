package oe.jz.khjd;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * �ͻ��Ӵ�����
 * @author robanco
 *
 */
public class Khjd extends OeScript{
	static String  runtimeid="";
	public static void main(String[] args) {
		
	}
	
	/**
	 * ������Դ���Զ�ɸѡ��Ʋ���
	 */
	public static void soa_auto_team()throws Exception{
		
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
	public static void addDesignerMark()throws Exception {
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column33");/*���ʦ�ֶ�*/
		
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
	}
	
	/**
	 * ��Ա�ɼ���� ����
	 * @throws Exception
	 */
	public static void addDesignerMark2()throws Exception {
		String runtimeid="";
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
	}
	
	/**
	 * ���ųɼ��ӷ�
	 */
	public static void addDEPTMark()throws Exception {
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String dept=dy.get(lshinfo, "column30");/*�����ֶ�*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", dept);
		dy.set(lsh,"column4","1");/*1��ʾ�÷�,0��ʾʧ��*/
	}
	/**
	 * ���ųɼ�ȥ��
	 */
	public static void addDEPTMark2()throws Exception {
		
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String dept=dy.get(lshinfo, "column30");/*�����ֶ�*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", dept);
		dy.set(lsh,"column4","0");/*1��ʾ�÷�,0��ʾʧ��*/
	}
	
}
