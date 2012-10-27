package oe.jz.khjd;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.WfEntry;

/**
 * 客户接待业务，工作流环节中的脚本逻辑
 * @author robanco
 *
 */
public class Khjd_Wfscript extends OeScript{
	
	String workcode="";//模拟工作流环境中的变量workcode
	String runtimeid="";//模拟工作流环境中的变量runtime

	
	/**
	 * 实现自动往相关变量表中加业务数据，工作流引擎默认情况下仅仅在新建表单的时候
	 * 会一次性把表单数据注入流程相关变量中，如果需要表单在流程扭转过程中，再将表单
	 * 的相关数据注入流程相关变量需要手工在SOA处加脚本处理
	 */
	public  void todo2()throws Exception{
		
		//import com.jl.common.app.*;
		
		//获得环境信息
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		//获得指定字段的值
		String columnid="";
		String columnvalue=dy.get(bussid+":"+formcode,columnid);
		
		//往流程相关变量中写数据
		String revname="";//相关变量名
		wf.set(runtimeid, revname, columnvalue);
		
		//新版工作流还有使用横向数据在  t_wf_relevantvar_tmp 表中
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		// 注意d3是需要自己去根据应用程序框架中 变量绑定的前后位置来算，第一个是d0，第二个是d1以此类推
		db.execute(con, "update netone.t_wf_relevantvar_tmp set d3='"+columnvalue+"' where runtimeid='"+runtimeid+"'");
		
	}

}
