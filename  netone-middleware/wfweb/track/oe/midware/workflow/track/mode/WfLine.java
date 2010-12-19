/*
 * �������� 2006-3-30
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package oe.midware.workflow.track.mode;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.transition.Transition;

/**
 * @author robanco
 * 
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class WfLine {
	static float offTop = 0;// �켣��������ķ����ϵ�ƫ����

	/**
	 * ��û�����е���һ����Ĺ켣
	 * 
	 * @param activityObj
	 * @param activityMap
	 * @return
	 */
	public static String lineInfo(Activity activityObj) {
		String lineStr = "";
		try {
			String XoffSet = (activityObj.getExtendedAttributes())
					.get("XOffset");
			String YoffSet = (activityObj.getExtendedAttributes())
					.get("YOffset");
			float[] fp = activityCg(XoffSet, YoffSet);
			float[] tp;
			float[] point;
			Map nextlink = activityObj.getEfferentTransitions();
			Transition nextTran;
			Activity nextActivityObj;
			Set keySet = nextlink.keySet();
			Object key;
			Iterator iter = keySet.iterator();
			while (iter.hasNext()) {
				key = iter.next();
				nextTran = (Transition) nextlink.get(key);
				nextActivityObj = nextTran.getToActivity();
				tp = activityCg((nextActivityObj.getExtendedAttributes())
						.get("XOffset"), (nextActivityObj
						.getExtendedAttributes()).get("YOffset"));
				point = pointStation(fp[0], fp[1], tp[0], tp[1]);
				lineStr += "<v:line style='position:absolute;left:0;top:0;'from='"
						+ (point[0])
						+ ","
						+ point[1]
						+ "' to='"
						+ (point[2])
						+ ","
						+ (point[3])
						+ "'>\n"
						+ "<v:stroke EndArrow='Classic' Color='blue'/>\n"
						+ "</v:line>\n";
			}

		} catch (Exception e) {
			System.out.println("" + e);
		}
		return lineStr;
	}

	/**
	 * ������ʼ������������������Ϣ
	 * 
	 * @param fx
	 * @param fy
	 * @param tx
	 * @param ty
	 * @return
	 */
	public static float[] makeMarkPointCenter(String fx, String fy, String tx,
			String ty) {
		float[] tp = activityCg(tx, ty);
		float[] fp = activityCg(fx, fy);
		return pointStation(fp[0], fp[1], tp[0], tp[1]);
	}

	/**
	 * ��û������
	 * 
	 * @param offX
	 * @param offY
	 * @return
	 */
	private static float[] activityCg(String offX, String offY) {
		float[] point = new float[2];
		float tableHeigth = 60;
		float tableWidth = 90;
		float x = Float.valueOf(offX).floatValue();// ��ĺ�����
		float y = Float.valueOf(offY).floatValue();// ���������
		point[0] = (x + tableWidth / 2);
		point[1] = (y + tableHeigth / 2);
		return point;
	}

	/**
	 * ���׼ȷ�Ĺ켣���,���յ��λ��
	 * 
	 * @param fx
	 * @param fy
	 * @param tx
	 * @param ty
	 * @return
	 */
	public static float[] pointStation(float fx, float fy, float tx, float ty) {
		float[] point = new float[4];
		float height = 60;
		float width = 90;
		float onX = 0;// �����X�ϵ�ƫ����
		float onY = 0;// �����Y�ϵ�ƫ����
		float offX = 0;// �յ���X�ϵ�ƫ����
		float offY = 0;// �ӵ���Y�ϵ�ƫ����
		float rate = height / width;
		float rate1 = 1;
		if (fx != tx) {
			rate1 = (fy - ty) / (fx - tx);
			rate1 = rate1 > 0 ? rate1 : -rate1;
		}
		if (rate1 <= rate) {
			if (fx > tx) {
				onX = -(width / 2);
				offX = width / 2;
			} else if (fx < tx) {
				onX = width / 2;
				offX = -(width / 2);
			}
			if (fy > ty) {
				onY = -(width / 2) * rate1;
				offY = 0;
			} else if (fy < ty) {
				onY = (width / 2) * rate1;
				offY = 0;
			}

		}
		if (rate1 > rate) {
			if (fx > tx) {
				onX = -(height / (2 * rate1));
				offX = 0;
			} else if (fx < tx) {
				onX = (height / (2 * rate1));
				offX = 0;
			}

			if (fy > ty) {
				onY = -(height / 2);
				offY = (height / 2);
			} else if (fy < ty) {
				onY = height / 2;
				offY = -(height / 2);
			}
		}
		point[0] = fx + onX;
		point[1] = fy + onY + offTop;
		point[2] = tx + offX;
		point[3] = ty + offY + offTop;
		return point;
	}
}
