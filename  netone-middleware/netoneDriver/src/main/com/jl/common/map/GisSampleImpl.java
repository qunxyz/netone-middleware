package com.jl.common.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jl.common.map.obj.Display;
import com.jl.common.map.obj.Flow;
import com.jl.common.map.obj.Gis;
import com.jl.common.map.obj.Mark;
import com.jl.common.map.obj.Pic;
import com.jl.common.map.obj.Step;


/**
 * ��ͼ������
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */

public class GisSampleImpl implements GisIfc {

	// ��ʽ��������������ID������X���ꡢ����Y���ꡢpicid��markid,��μ���(busslevel),���ڵ�ID
	static String area[] = { "����,fz1,300,100,p1,m1,0,0",
			"����,xm1,400,150,p1,m1,0,0", "��ƽ,np1,500,200,p1,m1,0,0",
			"����,fz2,350,120,p1,m1,fz1,1", "̨��,fz3,290,150,p1,m1,fz1,1",
			"������,xm2,350,150,p2,m1,xm1,1", "��ƽ��,np2,450,200,p2,m1,np1,1" };

	// ��ʽ����˾������˾ID����˾X���ꡢ��˾Y���ꡢ��˾��picid����˾��markid,��˾����������ID,��˾��μ���(busslevel),��˾�ĸ��ڵ�ID
	static String buss[] = { "����Ӫ����,b1,300,100,p1,m1,fz1,0,0",
			"����Ӫ����,b2,400,150,p1,m1,xm1,0,0", "��ƽӪ����,b3,500,200,p1,m1,np1,0,0",
			"����ɽӪ����,b4,500,200,p1,m1,np1,0,0",
			"����Ӫ����,b5,300,100,p1,m1,fz2,1,b1",
			"̨��Ӫ����,b6,330,120,p1,m1,fz2,1,b1",
			"������Ӫ����,b7,400,150,p1,m1,xm2,1,b2",
			"��ƽ��Ӫ����,b8,500,200,p1,m1,np2,1,b3",
			"���ݾ�����1,b9,350,150,p2,m1,fz2,2,b5",
			"���ݾ�����2,b10,450,200,p2,m1,fz2,2,b5" };

	public Gis load(String url) {
		Gis gis = new Gis();
		Pic pic = new Pic();
		pic.setId("p1");
		pic.setUrl("/images/default/add.gif");

		Pic pic1 = new Pic();
		pic1.setId(null);
		pic1.setUrl("/images/default/add.gif");
		pic1.setLevel(1);
		Map picMap = new HashMap();
		picMap.put("p1", pic);
		picMap.put(UUID.randomUUID().toString(), pic1);

		Mark mark = new Mark();
		mark.setId("m1");
		mark.setUrl("/images/default/mark.gif");
		Map markMap = new HashMap();
		markMap.put("m1", mark);

		Display disp = new Display();
		disp.setMark(markMap);
		disp.setPic(picMap);

		Step step1 = new Step();
		step1.setIndex(0);
		step1.setPicid("p1");
		step1.setArealevel("0");
		step1.setBusslevel("0");


		Step step2 = new Step();
		step2.setIndex(1);
		step2.setPicid("p1");
		step2.setArealevel("1");
		step2.setBusslevel("1");

		Step step3 = new Step();
		step3.setIndex(2);
		step3.setPicid("p2");
		step3.setArealevel("1");
		step3.setBusslevel("2");

		Map stepMap = new HashMap();
		stepMap.put(0, step1);
		stepMap.put(1, step2);
		stepMap.put(2, step3);

		Flow flow = new Flow();
		flow.setId("1");
		flow.setDescription("Ĭ�ϵ�ͼ");
		flow.setDisplay(disp);
		flow.setStep(stepMap);

		Map flowMap = new HashMap();
		flowMap.put("1", flow);
		gis.setFlow(flowMap);

		return gis;
	}

	public List treeData(Gis gis, String flowId, int parentstep,
			String areapointId, String busspointId, String extendinfo) {
		// ѡ���ͼ��ȡ�ķ���
		Flow flow = (Flow) gis.getFlow().get(flowId);
		if (flow == null) {
			return new ArrayList();
		}
		// ��λ��һ����ȡ��
		int step = ++parentstep;
		Step stepObj = flow.getStep().get(step);
		if (stepObj == null) {
			// ˵����ͼ�Ѿ���ȡ����ײ���
			return new ArrayList();
		}
		// ��õ����ļ���ֵ(��ֵ��ҵ��ϵͳ�������level��һ�µ�)
		String arealevel = stepObj.getArealevel();
		// ��ù�˾�ļ���ֵ(��ֵ��ҵ��ϵͳ�еĹ�˾level��һ�µ�),
		// ϵͳ�������ö����˾�����京��Ϊ��һ��ͼ��������ͬʱ��ʾ�ϼ����¼���˾
		String[] busslevel = stepObj.getBusslevel().split(",");

		// ͼ����2�������һ������ȷ��ָ��id������һ��ָֻ������
		String picidtmp = stepObj.getPicid();

		String[] pic = null;
		if (picidtmp == null || picidtmp.equals("")) {
			// ���ֻ��ָ��������ô���ݼ����ҳ����п��ܵ�ͼ��ID
			int piclevel = stepObj.getLevel();
			List piclistall = new ArrayList();
			Map picmap = flow.getDisplay().getPic();
			for (Iterator iterator = picmap.keySet().iterator(); iterator
					.hasNext();) {
				String picid = (String) iterator.next();
				Pic picobj = (Pic) picmap.get(picid);
				int level = picobj.getLevel();
				if (level == piclevel) {
					piclistall.add(picobj.getId());
				}
			}
			pic = (String[]) piclistall.toArray(new String[0]);
		} else {
			pic = picidtmp.split(",");
		}

		// ����ĵ�ͼ������
		List listAreapoint = new ArrayList();

		// ׷����������
		for (int i = 0; i < area.length; i++) {
			String areainfo[] = area[i].split(",");
			// ����step�������Ҫչʾ�Ĳ��ͬʱ���ݲ����е��ϼ������ ��������һ���������ͼ��
			if (areainfo[7].equals(arealevel)
					&& areainfo[6].equals(areapointId)) {
				for (int j = 0; j < pic.length; j++) {
					if (pic[j].equals(areainfo[4]))
						listAreapoint.add(areainfo);
				}

			}
		}

		// ��˾�ĵ�ͼ������
		List listBusspoint = new ArrayList();
		// �洢��˾ID��Ϣ
		List busssidarr = new ArrayList();
		if (busspointId == null) {// ��ͼ��ȡʱ������ͨ����������ȡ����ʱ����Ҫ�Ѹ������µ����й�˾���г���
			for (Iterator iterator = listAreapoint.iterator(); iterator
					.hasNext();) {
				String[] objectx = (String[]) iterator.next();
				String areaId = objectx[1];
				for (int i = 0; i < buss.length; i++) {
					String bussinfo[] = buss[i].split(",");
					if (bussinfo[6].equals(areaId)) {
						busssidarr.add(bussinfo[1]);
					}
				}
			}
		} else {// ���ָ���˹�˾�ڵ㣬��ô��ȡ���ǴӸù�˾��ʼ
			busssidarr.add(busspointId);
		}
		// ׷�ӹ�˾�ĵ�ͼ������
		for (int i = 0; i < buss.length; i++) {
			String bussinfo[] = buss[i].split(",");
			for (Iterator iterator = busssidarr.iterator(); iterator.hasNext();) {
				String bussid = (String) iterator.next();
				// ע�⣺step�����õĹ�˾���𣬿����ж��������༶��˾��ͬһͼ��չʾ
				for (int j = 0; j < busslevel.length; j++) {
					// ֻ�й�˾��ҵ�񼶱����step�еĶ��壬���Ҹù�˾��������ָ���ϼ���˾ʱ��ϵͳ��׷������
					if (bussinfo[7].equals(busslevel[j])
							&& (bussinfo[1].equals(bussid))) {

						for (int j2 = 0; j2 < pic.length; j2++) {
							if (pic[j2].equals(bussinfo[4]))
								listBusspoint.add(bussinfo);
						}

					}
				}
			}
		}
		
		for (int i = 0; i < buss.length; i++) {
			String bussinfo[] = buss[i].split(",");
			for (Iterator iterator = busssidarr.iterator(); iterator.hasNext();) {
				String bussid = (String) iterator.next();
				// ע�⣺step�����õĹ�˾���𣬿����ж��������༶��˾��ͬһͼ��չʾ
				for (int j = 0; j < busslevel.length; j++) {
					// ֻ�й�˾��ҵ�񼶱����step�еĶ��壬���Ҹù�˾��������ָ���ϼ���˾ʱ��ϵͳ��׷������
					if (bussinfo[7].equals(busslevel[j])
							&& (bussinfo[8].equals(bussid))) {

						for (int j2 = 0; j2 < pic.length; j2++) {
							if (pic[j2].equals(bussinfo[4]))
								listBusspoint.add(bussinfo);
						}

					}
				}
			}
		}
		
		
		// ����˾��ͼ��������ͼ�����ݺϲ�
		listAreapoint.addAll(listBusspoint);
		return listAreapoint;
	}

	public static void main(String[] args) {
		GisIfc gisifc = new GisSampleImpl();
		Gis gis = gisifc.load("");
		/*
		 * ����������3�ε���ȡ���� ��һ�� { "0", "0" } �����Ӹ���ʼ���ڶ��� { "fz1" } ������ȡʱ��ͨ����������� ������{
		 * "fz1","b1" }����ͨ����˾b1����(��˾�ǰ����������еģ�����������ϢҪ��������) ���Ĵ�{ "fz2", "b5" }
		 * ������˾b5����
		 */
		String data[][] = { { "0", "0" }, { "fz1" }, { "fz1", "b5" } };

		List list = gisifc.treeData(gis, "1", -1, "" + data[0][0],
				data[0].length == 2 ? "" + data[0][1] : null, null);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------��ȡ---------");

		List list2 = gisifc.treeData(gis, "1", 0, "" + data[1][0],
				data[1].length == 2 ? "" + data[1][1] : null, null);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------��ȡ---------");

		List list3 = gisifc.treeData(gis, "1", 1, "" + data[2][0],
				data[2].length == 2 ? "" + data[2][1] : null, null);
		for (Iterator iterator = list3.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------��ȡ---------");
		
	
		List list4 = gisifc.lineData( "p2", "2","","");
		for (Iterator iterator = list4.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------��ȡ---------");
	}

	public List lineData(String picid, String busslevel, String busspointId,
			String extendbussinfo) {
		List list = new ArrayList();
		for (int i = 0; i < buss.length; i++) {
			String[] data = buss[i].split(",");
			if (data[4].equals(picid) && data[7].equals(busslevel)) {
				list.add(data);
			}
		}
		return list;
	}


}
