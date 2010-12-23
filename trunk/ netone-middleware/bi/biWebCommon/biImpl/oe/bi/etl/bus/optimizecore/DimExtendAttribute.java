package oe.bi.etl.bus.optimizecore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oe.bi.dataModel.obj.DimColumn;
import oe.bi.etl.obj.MergerObj;


public class DimExtendAttribute {

	public static List parseMergerObj(DimColumn dimcolumn) {
		try {
			List treeModelList = new ArrayList();
			String extendattribute = dimcolumn.getExtendattribute();
			if (extendattribute == null || extendattribute.length() == 0) {
				return treeModelList;
			}
			StringTokenizer st = new StringTokenizer(extendattribute, ";");
			while (st.hasMoreTokens()) {
				MergerObj opObj = new MergerObj();
				StringTokenizer stL = new StringTokenizer(st.nextToken(), "[");

				if (stL.hasMoreTokens()) {

					StringTokenizer stindex = new StringTokenizer(stL
							.nextToken(), "]");
					if (stindex.hasMoreTokens()) {
						opObj.setIndex(stindex.nextToken()); // ȡ��indexֵ
					}

					StringTokenizer stTalbe = new StringTokenizer(stL
							.nextToken(), "]");

					opObj.setTableinfo(stTalbe.nextToken());// ȡ�ñ�����

					StringTokenizer stTarget = new StringTokenizer(stL
							.nextToken(), "]");
					if (stTarget.hasMoreTokens()) {
						StringTokenizer stTargetTemp = new StringTokenizer(
								stTarget.nextToken(), ",");
						Map map = new HashMap();
						while (stTargetTemp.hasMoreTokens()) {
							String preTarget = stTargetTemp.nextToken();
							String[] preTargets = preTarget.split("=");
							if (preTargets == null || preTargets.length != 2) {
								throw new RuntimeException("��Чά����չ���ԣ�ָ�겿�֣�");
							}
							map.put(preTargets[0], preTargets[1]);
						}
						opObj.setTargetInfo(map);
					} // ȡ��ָ��Map

					StringTokenizer stDim = new StringTokenizer(
							stL.nextToken(), "]");
					if (stDim.hasMoreTokens()) {
						StringTokenizer stDimTemp = new StringTokenizer(
								stDim.nextToken(), ",");
						Map map = new HashMap();
						while (stDimTemp.hasMoreTokens()) {
							String preDim = stDimTemp.nextToken();
							String[] preDims = preDim.split("=");
							if (preDims == null || preDims.length != 2) {
								throw new RuntimeException("��Чά����չ���ԣ�ά�Ȳ��֣�");
							}
							map.put(preDims[0], preDims[1]);
						}
						opObj.setDimensionInfo(map); // ȡ��ά��Map
					}
				}
				treeModelList.add(opObj);
			}
			return treeModelList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
