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
 * �ͻ��Ӵ�ҵ�񣬹����������еĽű��߼�
 * @author robanco
 *
 */
public class Khjd_Wfscript extends OeScript{
	
	String workcode="";//ģ�⹤���������еı���workcode
	String runtimeid="";//ģ�⹤���������еı���runtime

	
	/**
	 * ʵ���Զ�����ر������м�ҵ�����ݣ�����������Ĭ������½������½�����ʱ��
	 * ��һ���԰ѱ�����ע��������ر����У������Ҫ��������Ťת�����У��ٽ���
	 * ���������ע��������ر�����Ҫ�ֹ���SOA���ӽű�����
	 */
	public  void todo2()throws Exception{
		
		//import com.jl.common.app.*;
		
		//��û�����Ϣ
		String bussid=wf.get(runtimeid,"bussid");
		String busstype=wf.get(runtimeid,"busstype");
		String formcode=AppEntry.iv().loadApp(busstype).getDyformCode_();
		//���ָ���ֶε�ֵ
		String columnid="";
		String columnvalue=dy.get(bussid+":"+formcode,columnid);
		
		//��������ر�����д����
		String revname="";//��ر�����
		wf.set(runtimeid, revname, columnvalue);
		
		//�°湤��������ʹ�ú���������  t_wf_relevantvar_tmp ����
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		// ע��d3����Ҫ�Լ�ȥ����Ӧ�ó������� �����󶨵�ǰ��λ�����㣬��һ����d0���ڶ�����d1�Դ�����
		db.execute(con, "update netone.t_wf_relevantvar_tmp set d3='"+columnvalue+"' where runtimeid='"+runtimeid+"'");
		
	}

}
