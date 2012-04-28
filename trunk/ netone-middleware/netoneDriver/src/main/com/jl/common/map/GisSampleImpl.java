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
 * 地图测试类
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */

public class GisSampleImpl implements GisIfc {

	// 格式：地区名、地区ID、地区X坐标、地区Y坐标、picid，markid,层次级别(busslevel),父节点ID
	static String area[] = { "福州,fz1,300,100,p1,m1,0,0",
			"厦门,xm1,400,150,p1,m1,0,0", "南平,np1,500,200,p1,m1,0,0",
			"闽侯,fz2,350,120,p1,m1,fz1,1", "台江,fz3,290,150,p1,m1,fz1,1",
			"湖里区,xm2,350,150,p2,m1,xm1,1", "延平区,np2,450,200,p2,m1,np1,1" };

	// 格式：公司名、公司ID、公司X坐标、公司Y坐标、公司的picid，公司的markid,公司隶属的区域ID,公司层次级别(busslevel),公司的父节点ID
	static String buss[] = { "福州营销部,b1,300,100,p1,m1,fz1,0,0",
			"厦门营销部,b2,400,150,p1,m1,xm1,0,0", "南平营销部,b3,500,200,p1,m1,np1,0,0",
			"武夷山营销部,b4,500,200,p1,m1,np1,0,0",
			"闽侯营销部,b5,300,100,p1,m1,fz2,1,b1",
			"台江营销部,b6,330,120,p1,m1,fz2,1,b1",
			"湖里区营销部,b7,400,150,p1,m1,xm2,1,b2",
			"延平区营销部,b8,500,200,p1,m1,np2,1,b3",
			"福州经销商1,b9,350,150,p2,m1,fz2,2,b5",
			"福州经销商2,b10,450,200,p2,m1,fz2,2,b5" };

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
		flow.setDescription("默认地图");
		flow.setDisplay(disp);
		flow.setStep(stepMap);

		Map flowMap = new HashMap();
		flowMap.put("1", flow);
		gis.setFlow(flowMap);

		return gis;
	}

	public List treeData(Gis gis, String flowId, int parentstep,
			String areapointId, String busspointId, String extendinfo) {
		// 选择地图钻取的方案
		Flow flow = (Flow) gis.getFlow().get(flowId);
		if (flow == null) {
			return new ArrayList();
		}
		// 定位下一个钻取面
		int step = ++parentstep;
		Step stepObj = flow.getStep().get(step);
		if (stepObj == null) {
			// 说明地图已经钻取到最底层了
			return new ArrayList();
		}
		// 获得地区的级别值(该值与业务系统中区域的level是一致的)
		String arealevel = stepObj.getArealevel();
		// 获得公司的级别值(该值与业务系统中的公司level是一致的),
		// 系统允许配置多个公司级别，其含义为在一个图层中允许同时显示上级和下级公司
		String[] busslevel = stepObj.getBusslevel().split(",");

		// 图层有2中情况，一种是明确的指明id，还有一种只指明级别
		String picidtmp = stepObj.getPicid();

		String[] pic = null;
		if (picidtmp == null || picidtmp.equals("")) {
			// 如果只是指明级别，那么根据级别找出所有可能的图形ID
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

		// 区域的地图点数据
		List listAreapoint = new ArrayList();

		// 追加区域数据
		for (int i = 0; i < area.length; i++) {
			String areainfo[] = area[i].split(",");
			// 根据step定义的需要展示的层次同时根据参数中的上级区域点 来过滤下一级的区域地图点
			if (areainfo[7].equals(arealevel)
					&& areainfo[6].equals(areapointId)) {
				for (int j = 0; j < pic.length; j++) {
					if (pic[j].equals(areainfo[4]))
						listAreapoint.add(areainfo);
				}

			}
		}

		// 公司的地图点数据
		List listBusspoint = new ArrayList();
		// 存储公司ID信息
		List busssidarr = new ArrayList();
		if (busspointId == null) {// 地图钻取时可能是通过区域来钻取，这时就需要把该区域下的所有公司罗列出来
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
		} else {// 如果指明了公司节点，那么钻取就是从该公司开始
			busssidarr.add(busspointId);
		}
		// 追加公司的地图点数据
		for (int i = 0; i < buss.length; i++) {
			String bussinfo[] = buss[i].split(",");
			for (Iterator iterator = busssidarr.iterator(); iterator.hasNext();) {
				String bussid = (String) iterator.next();
				// 注意：step中配置的公司级别，可以有多个，允许多级公司在同一图层展示
				for (int j = 0; j < busslevel.length; j++) {
					// 只有公司的业务级别符合step中的定义，并且该公司是隶属与指定上级公司时，系统才追加数据
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
				// 注意：step中配置的公司级别，可以有多个，允许多级公司在同一图层展示
				for (int j = 0; j < busslevel.length; j++) {
					// 只有公司的业务级别符合step中的定义，并且该公司是隶属与指定上级公司时，系统才追加数据
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
		
		
		// 将公司地图点和区域地图点数据合并
		listAreapoint.addAll(listBusspoint);
		return listAreapoint;
	}

	public static void main(String[] args) {
		GisIfc gisifc = new GisSampleImpl();
		Gis gis = gisifc.load("");
		/*
		 * 这里描述了3次的钻取操作 第一次 { "0", "0" } 表明从根开始，第二次 { "fz1" } 表明钻取时是通过点区域进来 第三次{
		 * "fz1","b1" }表明通过公司b1进入(公司是包含在区域中的，所以区域信息要连带跟上) 第四次{ "fz2", "b5" }
		 * 表明公司b5进入
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
		System.out.println("---------钻取---------");

		List list2 = gisifc.treeData(gis, "1", 0, "" + data[1][0],
				data[1].length == 2 ? "" + data[1][1] : null, null);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------钻取---------");

		List list3 = gisifc.treeData(gis, "1", 1, "" + data[2][0],
				data[2].length == 2 ? "" + data[2][1] : null, null);
		for (Iterator iterator = list3.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------钻取---------");
		
	
		List list4 = gisifc.lineData( "p2", "2","","");
		for (Iterator iterator = list4.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}
		System.out.println("---------钻取---------");
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
