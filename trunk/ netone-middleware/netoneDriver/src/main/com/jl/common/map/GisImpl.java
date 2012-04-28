package com.jl.common.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jl.common.SpringBeanUtil;
import com.jl.common.map.obj.Display;
import com.jl.common.map.obj.Flow;
import com.jl.common.map.obj.Gis;
import com.jl.common.map.obj.Mark;
import com.jl.common.map.obj.Pic;
import com.jl.common.map.obj.Step;
import com.jl.dao.CommonDAO;

/**
 * 地图实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-16 下午03:41:37
 * @history
 */
public class GisImpl implements GisIfc {

	/** 日志 */
	private final Logger log = Logger.getLogger(GisImpl.class);

	public Gis load(String url) {
		// String curfile = GisSampleImpl.class.getResource("").getPath()
		// + "map.xml";
		// Document document = reader.read(curfile);
		SAXReader reader = new SAXReader();

		Gis gis = new Gis();
		Map flowMap = new HashMap();
		Document document;
		try {
			document = reader.read(url);
			Element root = document.getRootElement();
			// 遍历节点为flow的属性值
			for (Iterator i_flow = root.elementIterator(); i_flow.hasNext();) {
				Element e_flow = (Element) i_flow.next();
				Flow flow = new Flow();
				Map picMap = new HashMap();
				Map markMap = new HashMap();
				Map stepMap = new HashMap();

				flow.setId(e_flow.attributeValue("id"));
				flow.setDescription(e_flow.attributeValue("descrpition"));

				flowMap.put(e_flow.attributeValue("id"), flow);

				// 遍历节点为display及其子节点pic,mark的属性值
				Element e_disp = e_flow.element("display");
				for (Iterator i_disp = e_disp.elementIterator(); i_disp
						.hasNext();) {
					Element el_disp = (Element) i_disp.next();

					if (el_disp.getName() == "pic") {
						Pic pic = new Pic();
						pic.setId(el_disp.attributeValue("id"));
						pic.setUrl(el_disp.attributeValue("url"));
						pic.setWidth(Double.parseDouble(el_disp
								.attributeValue("width")));
						pic.setHeigth(Double.parseDouble(el_disp
								.attributeValue("height")));
						pic.setLevel(Integer.parseInt(el_disp
								.attributeValue("level")));

						if (pic.getId() == null || pic.equals("")) {
							String uuid = UUID.randomUUID().toString()
									.replaceAll("-", "");
							picMap.put(uuid, pic);
						} else {
							picMap.put(pic.getId(), pic);
						}

					} else {
						Mark mark = new Mark();
						mark.setId(el_disp.attributeValue("id"));
						mark.setUrl(el_disp.attributeValue("url"));
						mark.setWidth(Double.parseDouble(el_disp
								.attributeValue("width")));
						mark.setHeigth(Double.parseDouble(el_disp
								.attributeValue("height")));
						markMap.put(el_disp.attributeValue("id"), mark);
					}

					Display disp = new Display();
					disp.setPic(picMap);
					disp.setMark(markMap);

					flow.setDisplay(disp);
				}
				// 遍历节点为step的属性值
				for (Iterator i_step = e_flow.elementIterator("step"); i_step
						.hasNext();) {
					Element elt = (Element) i_step.next();
					Step step = new Step();

					step
							.setIndex(Integer.parseInt(elt
									.attributeValue("index")));
					String level = elt.attributeValue("piclevel");
					if (level != null && !level.equals("")) {
						step.setLevel(Integer.parseInt(level));
					}
					step.setPicid(elt.attributeValue("picid"));
					step.setArealevel(elt.attributeValue("arealevel"));
					step.setBusslevel(elt.attributeValue("busslevel"));
					step.setFlexibleRate(Float.parseFloat(elt
							.attributeValue("flexiblerate")));
					step.setExtendattribute(elt.getStringValue());
					stepMap.put(Integer.parseInt(elt.attributeValue("index")),
							step);
					flow.setStep(stepMap);

				}

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		gis.setFlow(flowMap);
		return gis;
	}

	public List treeData(Gis gis, String flowId, int parentstep,
			String areapointId, String busspointId, String extendinfo) {
		// CommonDAO commonDAO = BaseDAO.getCommonDAO();//J2EE使用
		CommonDAO commonDAO = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");// 调试

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

		Map map = new HashMap();
		map.put("arealevel", arealevel);
		map.put("busslevel", busslevel);
		map.put("areapointId",
				(areapointId == null || "".equals(areapointId)) ? "0"
						: areapointId);
		map.put("busspointId",
				(busspointId == null || "".equals(busspointId)) ? "0"
						: busspointId);

		log.debug("查询条件:" + map);

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

		// 地图点数据
		List listPoint = new ArrayList();

		try {
			// 区域数据
			// 根据step定义的需要展示的层次同时根据参数中的上级区域点 来过滤下一级的区域地图点
			List<Map> listAreapointRow = (List<Map>) commonDAO.select(
					"Map.findArea", map);// 地图横向数据
			for (Map object : listAreapointRow) {
				String _areaId = object.get("areaId").toString();
				String _areaCode = object.get("areaCode").toString();
				String _areaName = object.get("areaName").toString();
				String _level = object.get("level").toString();
				String _map = object.get("map").toString();
				String _parentAreaId = object.get("parentAreaId").toString();
				String[] mapx = null;
				if (_map != null) {
					mapx = _map.split("]");
				}
				if (mapx.length > 1) {
					for (int i = 0; i < mapx.length; i++) {
						String[] mapVar = mapx[i].replace("[", "").split(",");

						boolean pass = false;
						for (int j = 0; j < pic.length; j++) {
							if (pic[j].equals(mapVar[0])) {
								pass = true;
							}
						}

						if (pass) {
							String[] area = new String[8];
							area[0] = _areaName;
							area[1] = _areaId;
							area[2] = mapVar[1];
							area[3] = mapVar[2];
							area[4] = mapVar[0];
							area[5] = mapVar[3];
							area[6] = _level;
							area[7] = _parentAreaId;
							listPoint.add(area);
						}
					}
				} else if (mapx.length > 0) {
					String[] mapVar = mapx[0].replace("[", "").split(",");

					boolean pass = false;
					for (int j = 0; j < pic.length; j++) {
						if (pic[j].equals(mapVar[0])) {
							pass = true;
						}
					}
					if (pass) {
						String[] area = new String[8];
						area[0] = _areaName;
						area[1] = _areaId;
						area[2] = mapVar[1];
						area[3] = mapVar[2];
						area[4] = mapVar[0];
						area[5] = mapVar[3];
						area[6] = _level;
						area[7] = _parentAreaId;
						listPoint.add(area);
					}
				}
			}
			// 查询出来的公司数据可能重现重复(当一个公司关联上多个地区的时候会出现该问题)，需要把重复的数据过滤掉
			Map filterDuplicateMapinfo = new HashMap();
			// 公司数据
			List<Map> listBusspointRow = (List<Map>) commonDAO.select(
					"Map.findDepartment", map);// 地图横向数据
			for (Map object : listBusspointRow) {
				String _areaId = object.get("areaId").toString();
				String _departmentId = object.get("departmentId").toString();
				String _departmentCode = object.get("departmentCode")
						.toString();
				String _departmentName = object.get("departmentName")
						.toString();
				String _level = object.get("level").toString();
				String _map = object.get("map").toString();
				// 过滤掉重复的地图信息
				if (filterDuplicateMapinfo.containsKey(_map)) {
					continue;
				} else {
					filterDuplicateMapinfo.put(_map, "");
				}
				// ////////////
				String _parentDepartmentId = object.get("parentDepartmentId")
						.toString();
				String[] mapx = null;
				if (_map != null) {
					mapx = _map.split("]");
				}
				if (mapx.length > 1) {
					for (int i = 0; i < mapx.length; i++) {

						String[] mapVar = mapx[i].replace("[", "").split(",");

						boolean pass = false;
						for (int j = 0; j < pic.length; j++) {
							if (pic[j].equals(mapVar[0])) {
								pass = true;
							}
						}

						if (pass) {
							String[] buss = new String[9];
							buss[0] = _departmentName;
							buss[1] = _departmentId;
							buss[2] = mapVar[1];
							buss[3] = mapVar[2];
							buss[4] = mapVar[0];
							buss[5] = mapVar[3];
							buss[6] = _areaId;
							buss[7] = _level;
							buss[8] = _parentDepartmentId;
							listPoint.add(buss);
						}
					}
				} else if (mapx.length == 1) {
					String[] mapVar = mapx[0].replace("[", "").split(",");

					boolean pass = false;
					for (int j = 0; j < pic.length; j++) {
						if (pic[j].equals(mapVar[0])) {
							pass = true;
						}
					}
					if (pass) {
						String[] buss = new String[9];
						buss[0] = _departmentName;
						buss[1] = _departmentId;
						buss[2] = mapVar[1];
						buss[3] = mapVar[2];
						buss[4] = mapVar[0];
						buss[5] = mapVar[3];
						buss[6] = _areaId;
						buss[7] = _level;
						buss[8] = _parentDepartmentId;
						listPoint.add(buss);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listPoint;
	}

	public List lineData(String picid, String busslevel, String busspointId,
			String extendbussinfo) {
		// CommonDAO commonDAO = BaseDAO.getCommonDAO();//J2EE使用
		CommonDAO commonDAO = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");// 调试

		List listPoint = new ArrayList();
		// 公司数据
		try {
			Map map = new HashMap();
			map.put("busspointId", busspointId);
			map.put("busslevel", busslevel);
			if (StringUtils.isNotEmpty(extendbussinfo)) {
				map.put("extendbussinfo", extendbussinfo);
			}

			List<Map> listBusspointRow = (List<Map>) commonDAO.select(
					"Map.findDepartmentByLine", map);// 地图横向数据

			for (Map object : listBusspointRow) {
				String _areaId = object.get("areaId").toString();
				String _departmentId = object.get("departmentId").toString();
				String _departmentCode = object.get("departmentCode")
						.toString();
				String _departmentName = object.get("departmentName")
						.toString();
				String _level = object.get("level").toString();
				String _map = object.get("map").toString();
				String _parentDepartmentId = object.get("parentDepartmentId")
						.toString();
				String[] mapx = null;
				if (_map != null) {
					mapx = _map.split("]");
				}
				if (mapx.length > 1) {
					for (int i = 0; i < mapx.length; i++) {
						String[] mapVar = mapx[i].replace("[", "").split(",");

						if (picid.equals(mapVar[0])) {
							String[] buss = new String[9];
							buss[0] = _departmentName;
							buss[1] = _departmentId;
							buss[2] = mapVar[1];
							buss[3] = mapVar[2];
							buss[4] = mapVar[0];
							buss[5] = mapVar[3];
							buss[6] = _areaId;
							buss[7] = _level;
							buss[8] = _parentDepartmentId;
							listPoint.add(buss);
						}
					}
				} else if (mapx.length == 1) {
					String[] mapVar = mapx[0].replace("[", "").split(",");

					if (picid.equals(mapVar[0])) {
						String[] buss = new String[9];
						buss[0] = _departmentName;
						buss[1] = _departmentId;
						buss[2] = mapVar[1];
						buss[3] = mapVar[2];
						buss[4] = mapVar[0];
						buss[5] = mapVar[3];
						buss[6] = _areaId;
						buss[7] = _level;
						buss[8] = _parentDepartmentId;
						listPoint.add(buss);
					}
				}
			}
		} catch (Exception e) {
			log.error("出错了", e);
		}
		return listPoint;
	}

	public static void main(String[] args) {
		GisIfc gisifc = new GisImpl();
		Gis gis = gisifc.load("src/main/config/issmap.xml");

		List list = gisifc.treeData(gis, "1", -1,
				"89e45f2d8b87102e8de18fa692e7d162",
				"402882e61d6fdb70011d7728fba10011", "");
		System.out.println(list.size());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
		}

		System.out.println("-------------------");
		List list1 = gisifc.treeData(gis, "1", 0,
				"5417ce948b91102e8de18fa692e7d162", "", "");
		System.out.println(list1.size());
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("-------------------");
		List list2 = gisifc.treeData(gis, "1", 1,
				"002ded9b8b92102e8de18fa692e7d162", "", "");
		System.out.println(list2.size());
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("-------------------");
		List list3 = gisifc.treeData(gis, "1", 2,
				"002ded9b8b92102e8de18fa692e7d162",
				"402881852611009601261644263f00ec", "");
		System.out.println(list3.size());
		for (Iterator iterator = list3.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("---------line--9地市的所有经销商--------");
		List list4 = gisifc.lineData("fj", "4", "", "");
		for (Iterator iterator = list4.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("---------line--福州的所有经销商--------");
		List list5 = gisifc.lineData("fz", "4", "", "");
		for (Iterator iterator = list5.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("---------line--9地市的所有分销商--------");
		List list6 = gisifc.lineData("fj", "5", "", "");
		for (Iterator iterator = list6.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}
	}
}
