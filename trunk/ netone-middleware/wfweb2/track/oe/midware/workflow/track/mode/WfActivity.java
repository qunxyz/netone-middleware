/*
 * �������� 2006-3-30
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package oe.midware.workflow.track.mode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oe.midware.workflow.engine.actor.activity.utils.ActivityTypeFetcher;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 *
 */
public class WfActivity {
	static float offTop = 0;// �켣��������ķ����ϵ�ƫ����

	public static String[] activityInfo(Activity activityObj, List worklist,
			String runtimeId) {

		String actType = makeType(activityObj, worklist);
		String activity = "";
		String offX = "";
		String offY = "";

		String name = activityObj.getName();
		String id = activityObj.getId();
		try {
			offX = (activityObj.getExtendedAttributes()).get("XOffset");
			offY = (activityObj.getExtendedAttributes()).get("YOffset");
		} catch (Exception e) {
			System.out.println("" + e);
		}
		activity = "<div  style='position:absolute;visibility: visible; padding:2px; height:60px; width:90px; left: "
				+ offX
				+ "px; top: "
				+ (Float.valueOf(offY).floatValue() + offTop)
				+ "px;'>\n"
				+ "<table cellspacing=0 cellpadding=0 width=100% onclick=\"showDetail('"
				+ runtimeId
				+ "','"
				+ id
				+ "')\"  height=100%  border='0' style='cursor:hand;font-size:12px;'>"
				+ "<tr><td align=center class="
				+ actType
				+ ">"
				+ name
				+ "</td></tr></table>" + "</div>";
		return new String[] { activity, offY };
	}

	private static String makeType(Activity activityObj, List worklist) {

		String staticType = (ActivityTypeFetcher.fetchType(activityObj));
		TWfWorklist worklistObj = fetchRunTimeAct(activityObj.getId(), worklist);
		if (worklistObj == null) {
			return "static" + staticType;
		} else {
			String runtimeType = worklistObj.getExecutestatus();
			return "dynamic" + staticType + runtimeType;
		}

	}

//	/**
//	 * ��øû�Ķ�̬�����
//	 * 
//	 * @param activityId
//	 * @param worklist
//	 * @return
//	 */
//	public static TWfWorklist fetchRunTimeActs(String activityId, List worklist) {
//		if (activityId == null || worklist == null || worklist.size() == 0) {
//			return null;
//		}
//		List availableWork = new ArrayList();
//		for (Iterator itr = worklist.iterator(); itr.hasNext();) {
//			TWfWorklist worklistPre = (TWfWorklist) itr.next();
//			if (activityId.equals(worklistPre.getActivityid())) {
//				availableWork.add(worklistPre);
//			}
//		}
//
//		int findWorklist = availableWork.size();
//		if (findWorklist > 0) {
//			return (TWfWorklist) availableWork.get(findWorklist - 1);
//		}
//		return null;
//	}
	/**
	 * ��øû�Ķ�̬�����
	 * 
	 * @param activityId
	 * @param worklist
	 * @return
	 */
	public static TWfWorklist fetchRunTimeAct(String activityId, List worklist) {

		for (int i = 0; i < worklist.size(); i++) {
			String id = ((TWfWorklist) worklist.get(i)).getActivityid();
			if (id.equals(activityId)) {
				return  (TWfWorklist) worklist.get(i);
			}
		}

		return null;
	}
	
	
}
