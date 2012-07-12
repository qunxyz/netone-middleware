package com.jl.scheduler.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

public class ModifyLeaderViewData {

	/**
	 * �����쵼��ͼ����runtime��ͳ���ܹ����ǳ��ֶ������ݵĴ���취��
	 * ���ݲ�ѯ���Ľ�������޸�runtimme���statenow�ֶε�״̬������Ϊ04
	 */
	public static void modifyData() {
		//�������naturalname
		String naturalname_sql = "SELECT DISTINCT SUBSTRING_INDEX(naturalname,'.',4) naturalname FROM netone.ums_protectedobject WHERE naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%'";

		List list_naturalname = new ArrayList();
		Map map = new HashMap();
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			list_naturalname = wfview.coreSqlview(naturalname_sql);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ʼ������...");
		}
		for (Iterator iterator = list_naturalname.iterator(); iterator
				.hasNext();) {
			 map = (Map) iterator.next();
			String naturalname = (String) map.get("naturalname");
			//����������ݵ�runtimeid
			String valuenow_sql = "SELECT * FROM  ( SELECT COUNT(valuenow) xx,valuenow FROM netone.t_wf_relevantvar WHERE DATAFIELDID='bussid' AND runtimeid IN "
				+" (SELECT runtimeid FROM netone.t_wf_runtime WHERE processid='"+naturalname+"' AND statusnow IN('01','02')) GROUP BY  valuenow  )AS xxxx WHERE xxxx.xx>1";
			List valuenow_list = new ArrayList();
			Map valuenow_map = new HashMap();
			try {
				valuenow_list = wfview.coreSqlview(valuenow_sql);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("���Ҷ��������д�...");
			}
			for (Iterator iterator2 = valuenow_list.iterator(); iterator2
					.hasNext();) {
				valuenow_map = (Map) iterator2.next();
				String valuenow = (String) valuenow_map.get("valuenow");
				//�޸�runtime���statenow04
				String runtime_sql="SELECT * FROM  t_wf_runtime  WHERE statusnow IN('01','02') AND runtimeid IN(SELECT runtimeid FROM netone.t_wf_relevantvar " +
						" WHERE DATAFIELDID='bussid' AND valuenow IN('"+valuenow+"' )) ORDER BY starttime";
				List runtime_list = new ArrayList();
				List temp_list = new ArrayList();
				try {
					temp_list=wfview.coreSqlview(runtime_sql);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if(temp_list.size()==2){
					runtime_list.add((Map) temp_list.get(1));
					//System.out.println("2==="+runtime_list.toString());
				}else{
					runtime_list.add((Map) temp_list.get(1));
					runtime_list.add((Map) temp_list.get(2));
					//System.out.println("3==="+runtime_list.toString());
				}
				for (Iterator iterator3 = runtime_list.iterator(); iterator3
						.hasNext();) {
					Map runtimeid_map = (Map) iterator3.next();
					String runtimeid = (String) runtimeid_map.get("RUNTIMEID");
					String update_sql="UPDATE netone.t_wf_runtime SET STATUSNOW='04' WHERE runtimeid='"+runtimeid+"'";
					try {
						wfview.coreSqlview(update_sql);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("�޸�״̬���ɹ� ...");
					}
				}
				
			}
		}
	}
	public static void main(String[] args) {
		modifyData();
	}
}
