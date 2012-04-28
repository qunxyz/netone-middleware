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
 * ��ͼʵ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-16 ����03:41:37
 * @history
 */
public class GisImpl implements GisIfc {

	/** ��־ */
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
			// �����ڵ�Ϊflow������ֵ
			for (Iterator i_flow = root.elementIterator(); i_flow.hasNext();) {
				Element e_flow = (Element) i_flow.next();
				Flow flow = new Flow();
				Map picMap = new HashMap();
				Map markMap = new HashMap();
				Map stepMap = new HashMap();

				flow.setId(e_flow.attributeValue("id"));
				flow.setDescription(e_flow.attributeValue("descrpition"));

				flowMap.put(e_flow.attributeValue("id"), flow);

				// �����ڵ�Ϊdisplay�����ӽڵ�pic,mark������ֵ
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
				// �����ڵ�Ϊstep������ֵ
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
		// CommonDAO commonDAO = BaseDAO.getCommonDAO();//J2EEʹ��
		CommonDAO commonDAO = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");// ����

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

		Map map = new HashMap();
		map.put("arealevel", arealevel);
		map.put("busslevel", busslevel);
		map.put("areapointId",
				(areapointId == null || "".equals(areapointId)) ? "0"
						: areapointId);
		map.put("busspointId",
				(busspointId == null || "".equals(busspointId)) ? "0"
						: busspointId);

		log.debug("��ѯ����:" + map);

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

		// ��ͼ������
		List listPoint = new ArrayList();

		try {
			// ��������
			// ����step�������Ҫչʾ�Ĳ��ͬʱ���ݲ����е��ϼ������ ��������һ���������ͼ��
			List<Map> listAreapointRow = (List<Map>) commonDAO.select(
					"Map.findArea", map);// ��ͼ��������
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
			// ��ѯ�����Ĺ�˾���ݿ��������ظ�(��һ����˾�����϶��������ʱ�����ָ�����)����Ҫ���ظ������ݹ��˵�
			Map filterDuplicateMapinfo = new HashMap();
			// ��˾����
			List<Map> listBusspointRow = (List<Map>) commonDAO.select(
					"Map.findDepartment", map);// ��ͼ��������
			for (Map object : listBusspointRow) {
				String _areaId = object.get("areaId").toString();
				String _departmentId = object.get("departmentId").toString();
				String _departmentCode = object.get("departmentCode")
						.toString();
				String _departmentName = object.get("departmentName")
						.toString();
				String _level = object.get("level").toString();
				String _map = object.get("map").toString();
				// ���˵��ظ��ĵ�ͼ��Ϣ
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
		// CommonDAO commonDAO = BaseDAO.getCommonDAO();//J2EEʹ��
		CommonDAO commonDAO = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");// ����

		List listPoint = new ArrayList();
		// ��˾����
		try {
			Map map = new HashMap();
			map.put("busspointId", busspointId);
			map.put("busslevel", busslevel);
			if (StringUtils.isNotEmpty(extendbussinfo)) {
				map.put("extendbussinfo", extendbussinfo);
			}

			List<Map> listBusspointRow = (List<Map>) commonDAO.select(
					"Map.findDepartmentByLine", map);// ��ͼ��������

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
			log.error("������", e);
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

		System.out.println("---------line--9���е����о�����--------");
		List list4 = gisifc.lineData("fj", "4", "", "");
		for (Iterator iterator = list4.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("---------line--���ݵ����о�����--------");
		List list5 = gisifc.lineData("fz", "4", "", "");
		for (Iterator iterator = list5.iterator(); iterator.hasNext();) {
			String[] object = (String[]) iterator.next();
			for (int k = 0; k < object.length; k++) {
				System.out.print(object[k] + ",");
			}
			System.out.println();
			// }
		}

		System.out.println("---------line--9���е����з�����--------");
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
