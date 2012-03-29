/*
 * 创建日期 2006-3-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class WfLine {
	static float offTop = 0;// 轨迹在纵座标的方向上的偏移量

	/**
	 * 获得活动的所有的下一个活动的轨迹
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
			System.out.println(activityObj.getId());
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
				point = pointStation(fp[0], fp[1], tp[0], tp[1], activityObj.getId(),nextActivityObj.getId());
				lineStr += "<v:line style='position:absolute;left:0;top:0;'from='"+point[0]+","+point[1]+"' to='"+point[2]+","+point[3]+"'>\n"
						+ "<v:stroke EndArrow='Classic' Color='blue'/>\n"
						+ "</v:line>\n";
			}

		} catch (Exception e) {
			System.out.println("" + e);
		}
		return lineStr;
	}

	/**
	 * 调整开始点结束点的坐标中心信息
	 * 
	 * @param fx
	 * @param fy
	 * @param tx
	 * @param ty
	 * @return
	 */
	public static float[] makeMarkPointCenter(String fx, String fy, String tx,
			String ty ,String id ,String nextId) {
		float[] tp = activityCg(tx, ty);
		float[] fp = activityCg(fx, fy);
		return pointStation(fp[0], fp[1], tp[0], tp[1], id, nextId);
	}

	/**
	 * 获得活动的重心
	 * 
	 * @param offX
	 * @param offY
	 * @return
	 */
	private static float[] activityCg(String offX, String offY) {
		float[] point = new float[2];
		float tableHeigth = 48;
		float tableWidth = 112;
		float x = Float.valueOf(offX).floatValue();// 活动的横坐标
		float y = Float.valueOf(offY).floatValue();// 活动的纵坐标
		point[0] = (x + tableWidth / 2);
		point[1] = (y + tableHeigth / 2);
		return point;
	}

	/**
	 * 获得准确的轨迹起点,和终点的位置
	 * 
	 * @param fx
	 * @param fy
	 * @param tx
	 * @param ty
	 * @return
	 */
	public static float[] pointStation(float fx, float fy, float tx, float ty, String activityId , String nextActivityId) {
		float[] point = new float[4];
		float height = 45;
		float width = 110;
		float onX = 0;// 起点在X上的偏移量
		float onY = 0;// 起点在Y上的偏移量
		float offX = 0;// 终点在X上的偏移量
		float offY = 0;// 钟点在Y上的偏移量
		float rate = height / width;
		float rate1 = 1;
		if (fx != tx) {
			try {
				rate1 = (fy - ty) / (fx - tx);
			} catch (ArithmeticException ae) {
				// TODO Auto-generated catch block
				rate1 = (fy - ty) / 1;
			}
			rate1 = rate1 > 0 ? rate1 : -rate1;
		}

		if(activityId.indexOf("start") >= 0){

			if(nextActivityId.indexOf("turning") >= 0){
				if (rate1 > rate) {
					if (fy < ty) {
						tx -= 42;
						ty -= 23;
					}else{
						tx -= 42;
						ty += 4;
					}

				} else {

					if (fx < tx) {
						tx -= 55;
						ty -= 10;
					} else {
						ty -= 10;
						tx -= 30;
					}
				}
				
			}else{
				if (rate1 > rate) {
					if (fy < ty) {
						tx += 0;
						ty -= 23;
					}else{
						tx += 0;
						ty += 23;
					}
	
	
				} else {
	
					if (fx < tx) {	
						ty -= 0;
						tx -= 55;
					} else {
						ty -= 0;
						tx += 55;
	
					}
	
				}
			}

			fx -= 37;
			fy -= 3;

		}else if(activityId.indexOf("end") >= 0){
			

			if(nextActivityId.indexOf("turning") >= 0){
				if (rate1 > rate) {
					if (fy < ty) {
						fx -= 37;
						fy += 14;
					}else{
						fx -= 37;
						fy -= 25;
					}

				} else {

					if (fx < tx) {
						fx -= 17;
						fy -= 5;
					} else {
						fy -= 5;
						fx -= 57;
					}
				}
				tx -= 43;
				ty -= 10;
			}else{
				if (rate1 > rate) {
					if (fy > ty) {
						fx -= 35;
						fy -= 24;
					}else{
						fx -= 37;
						fy += 15;
					}
	
				} else {
					if (fx > tx) {	
						fx -= 55;
						fy -= 4;
					} else {
						fy -= 4;
						fx -= 16;
	
					}
	
				}
				tx += 2;
				ty -= 2;
			}

		}else if(activityId.indexOf("trackAction") >= 0 || activityId.indexOf("route") >= 0){
			
			if(nextActivityId.indexOf("turning") >= 0){
				if (rate1 > rate) {
					if (fy < ty) {
						tx -= 42;
						ty -= 23;
					}else{
						tx -= 42;
						ty += 4;
					}

				} else {

					if (fx < tx) {
						tx -= 55;
						ty -= 10;
					} else {
						ty -= 10;
						tx -= 30;
					}
				}
				
			}else{
				if (rate1 > rate) {
					if (fy < ty) {
						tx += 0;
						ty -= 23;
					}else{
						tx += 0;
						ty += 23;
					}
	
	
				} else {
	
					if (fx < tx) {	
						ty -= 0;
						tx -= 55;
					} else {
						ty -= 0;
						tx += 55;
	
					}
	
				}
			}
			
			fx += 0;
			fy -= 0;

		}else if(activityId.indexOf("turningpoint") >= 0 ){

			if(nextActivityId.indexOf("turning") >= 0){
				if (rate1 > rate) {
					if (fy < ty) {
						tx -= 42;
						ty -= 23;
					}else{
						tx -= 42;
						ty += 4;
					}

				} else {

					if (fx < tx) {
						tx -= 55;
						ty -= 10;
					} else {
						ty -= 10;
						tx -= 30;
					}
				}
				
			}else{
				if (rate1 > rate) {
					if (fy < ty) {
						tx += 0;
						ty -= 23;
					}else{
						tx += 0;
						ty += 23;
					}
	
	
				} else {
	
					if (fx < tx) {	
						ty -= 0;
						tx -= 55;
					} else {
						ty -= 0;
						tx += 55;
	
					}
	
				}
			}

			fx -= 42;
			fy -= 9;

		}

		point[0] = fx ;
		point[1] = fy ;
		point[2] = tx ;
		point[3] = ty ;
		return point;
	}
}
