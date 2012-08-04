package oe.jz.khjd;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * 客户接待流程
 * @author robanco
 *
 */
public class Khjd extends OeScript{
	static String  runtimeid="";
	public static void main(String[] args) {
		
	}
	
	/**
	 * 根据来源来自动筛选设计部门
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
	public static void addDesignerMark()throws Exception {
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String designer=dy.get(lshinfo, "column33");/*设计师字段*/
		
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
	}
	
	/**
	 * 人员成绩添加 丢分
	 * @throws Exception
	 */
	public static void addDesignerMark2()throws Exception {
		String runtimeid="";
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
	}
	
	/**
	 * 部门成绩加分
	 */
	public static void addDEPTMark()throws Exception {
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String dept=dy.get(lshinfo, "column30");/*部门字段*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", dept);
		dy.set(lsh,"column4","1");/*1表示得分,0标示失分*/
	}
	/**
	 * 部门成绩去分
	 */
	public static void addDEPTMark2()throws Exception {
		
		String runtimeid="";
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		String lshinfo=bussid+":"+formcode;
		String dept=dy.get(lshinfo, "column30");/*部门字段*/
		
		String lsh=dy.newInstance("BUSSFORM.BUSSFORM.TEST.JJXT.GCSJ.DY_581343874103969");
		dy.set(lsh, "fatherlsh", bussid);
		dy.set(lsh, "column3", dept);
		dy.set(lsh,"column4","0");/*1表示得分,0标示失分*/
	}
	
}
