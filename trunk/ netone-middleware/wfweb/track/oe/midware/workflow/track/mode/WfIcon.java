/*
 * �������� 2006-3-30
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package oe.midware.workflow.track.mode;

import oe.midware.workflow.XMLException;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * @author robanco
 * 
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class WfIcon {
	static float offTop = 30;// �켣��������ķ����ϵ�ƫ����

	/**
	 * ��ÿ�ʼͼƬ
	 * 
	 * @param offX
	 * @param offY
	 * @return
	 */
	public static String makeIconInfo(String x, String y, String key) {
		String startIconStr = "<v:image style=\"POSITION:absolute;Z-INDEX:1;LEFT:"
				+ x
				+ "px;TOP:"
				+ y
				+ "px;width:80;height:55;\" fillcolor=\"#66FF33\">\n"
				+ "<v:Textbox class="
				+ key
				+ " print=\"t\" inset=\"1pt,1pt,1pt,1pt\"></v:Textbox>\n"
				+ "</v:image>\n";
		return startIconStr;
	}

	private static String[] fetchActivity(Activity act) {
		String[] off = new String[2];

		try {
			off[0] = (act.getExtendedAttributes()).get("XOffset");
			off[1] = (act.getExtendedAttributes()).get("YOffset");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return off;
	}

	public static String makeStartLineInfo(String x, String y, Activity act) {
		String[] activityxy = fetchActivity(act);
		float[] xyoffset = WfLine.makeMarkPointCenter(x, y, activityxy[0],
				activityxy[1]);
		String lineStr = "<v:line style='position:absolute;left:0;top:0;'from='"
				+ xyoffset[0]
				+ ","
				+ xyoffset[1]
				+ "' to='"
				+ xyoffset[2]
				+ ","
				+ xyoffset[3]
				+ "'>\n"
				+ "<v:stroke EndArrow='Classic' Color='blue'/>\n"
				+ "</v:line>\n";
		return lineStr;
	}

	public static String makeEndLineInfo(String x, String y, Activity act) {
		String[] activityxy = fetchActivity(act);
		float[] xyoffset = WfLine.makeMarkPointCenter(x, y, activityxy[0],
				activityxy[1]);
		String lineStr = "<v:line style='position:absolute;left:0;top:0;'from='"
				+ xyoffset[2]
				+ ","
				+ xyoffset[3]
				+ "' to='"
				+ xyoffset[0]
				+ ","
				+ xyoffset[1]
				+ "'>\n"
				+ "<v:stroke EndArrow='Classic' Color='blue'/>\n"
				+ "</v:line>\n";
		return lineStr;
	}

}
