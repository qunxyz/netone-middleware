package com.jl.common.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

public final class EveryWeekMsg {
	
	private static Log log = LogFactory.getLog(EveryWeekMsg.class);
	
	public static int todo() {

		int count = 0;
		String sql = "select count(*) countx,w2.usercode usercode  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.statusnow='01' and w2.types in('01','02') group by usercode;";
		List msgTo = new ArrayList();

		try {
			WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
			log.info("ready for everyWeek Msg Tip!");
			List listIsReader = wfview.coreSqlview(sql);
			log.info("total may be:"+listIsReader.size());
			for (Iterator iterator = listIsReader.iterator(); iterator
					.hasNext();) {
				Map object = (Map) iterator.next();
				Long countx = (Long) object.get("countx");
				String usercode = (String) object.get("usercode");
				String context = "";
				if (countx > 1) {
					context = "���ӹ�����ƽ̨�������ѣ���ǰ�����°���ӹ�����ƽ̨��" + countx
							+ "��δ���������뼰ʱ��¼10.51.176.5���д���";
					String[] info = { usercode, context };
					msgTo.add(info);
				}
			}
			String sqlx = "select w3.d0 tip,usercode usercode  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid "
					+ "where w1.EXECUTESTATUS='01' and w2.statusnow='01' and w2.types in('01','02') and usercode in(select usercode from (select count(*) countx,w2.usercode usercode  from t_wf_worklist w1 left join t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.statusnow='01' and w2.types in('01','02') group by usercode"
					+ ") as t1 where t1.countx=1)";
			log.info("ready for everyWeek Msg Tip(only one msg)!");
			List listIsReaderx = wfview.coreSqlview(sqlx);
			log.info("total(only one msg):"+listIsReaderx.size());
			for (Iterator iterator = listIsReaderx.iterator(); iterator
					.hasNext();) {
				Map object = (Map) iterator.next();
				String tip = (String) object.get("tip");
				String usercode = (String) object.get("usercode");
				String context =  "���ӹ�����ƽ̨�������ѣ���ǰ�����°���ӹ�����ƽ̨��1��δ������:"
							+ tip + "���뼰ʱ��10.51.176.5���д���";
				String[] info = { usercode, context };
				msgTo.add(info);
			}
			
			Message.toMessageByUser(msgTo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public static void main(String[] args) {
		todo();
	}
}
