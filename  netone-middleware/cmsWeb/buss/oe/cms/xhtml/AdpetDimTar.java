package oe.cms.xhtml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AdpetDimTar {

	public static String[][] adeptDimTar2(String[] dim1, String[] dim2,
			String[] tar1, String[] tar2) {
		if (dim1 == null) {
			dim1 = new String[0];
		}
		if (dim2 == null) {
			dim2 = new String[0];
		}
		if (tar1 == null) {
			tar1 = new String[0];
		}
		if (tar2 == null) {
			tar2 = new String[0];
		}
		Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		List targ1 = new ArrayList();
		List targ2 = new ArrayList();

		for (int i = 0; i < dim1.length; i++) {
			map.put(dim1[i], dim1[i]);
			map1.put(dim1[i], tar1[i]);
		}
		for (int i = 0; i < dim2.length; i++) {
			map.put(dim2[i], dim2[i]);
			map2.put(dim2[i], tar2[i]);
		}

		List dim = new ArrayList();
		for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			dim.add(key);
		}
		String[] dimTmp = (String[]) dim.toArray(new String[0]);
		Arrays.sort(dimTmp);
		dim = Arrays.asList(dimTmp);
		List listDim = new ArrayList();
		for (Iterator itr = dim.iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			listDim.add(key);
			if (!map1.containsKey(key)) {
				targ1.add("0");
			} else {
				targ1.add((String) map1.get(key));
			}
			if (!map2.containsKey(key)) {
				targ2.add("0");
			} else {
				targ2.add((String) map2.get(key));
			}
		}

		String[] target1 = (String[]) targ1.toArray(new String[0]);
		String[] target2 = (String[]) targ2.toArray(new String[0]);
		String[] dimension = (String[]) listDim.toArray(new String[0]);

		return new String[][] { target1, target2, dimension };

	}

	/**
	 * 适应通用 维度和指标的一致处理
	 * 
	 * @param dim
	 * @param tar
	 * @return
	 */
	public static String[][] adeptDimTarCommon(String[][] dim, String[][] tar) {
		if (dim == null || tar == null || dim.length != tar.length) {
			return new String[][] { { "0" }, { "0" }, { "0" } };
		}
		Map mapDim = new HashMap();
		for (int i = 0; i < dim.length; i++) {
			if (dim[i] != null) {
				for (int j = 0; j < dim[i].length; j++) {
					mapDim.put(dim[i][j], dim[i][j]);
				}
			}
		}
		List dimx = new ArrayList();
		for (Iterator itr = mapDim.keySet().iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			dimx.add(key);
		}
		String[] dimTmp = (String[]) dimx.toArray(new String[0]);
		Arrays.sort(dimTmp);

		// 重新构建维度
		String[][] tarTmp = new String[dim.length][dimTmp.length];
		for (int i = 0; i < tarTmp.length; i++) {
			Arrays.fill(tarTmp[i], "0");
		}
		List list = new ArrayList();
		list.add(dimTmp);
		for (int i = 0; i < dim.length; i++) {
			if (dim[i] != null) {
				for (int j = 0; j < dim[i].length; j++) {
					for (int k = 0; k < dimTmp.length; k++) {
						for (int m = 0; m < dim[i].length; m++) {
							if (dim[i][m] != null
									&& dim[i][m].equals(dimTmp[k])) {
								tarTmp[i][k] = tar[i][m];
								break;
							}
						}
					}
				}
			}
			list.add(tarTmp[i]);

		}

		return (String[][]) list.toArray(new String[0][dimTmp.length]);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] dim3 = { "a1", "a2", "a5", "a6" };
		String[] dim2 = { "a3", "a2", "a7" };
		String[] dim1 = {};
		String[] t3 = { "1", "2", "5", "6" };
		String[] t2 = { "3", "2", "7" };
		String[] t1 = { };
		// String[][] info = adeptDimTar2(dim1, dim2, t1, t2);
		// for (int i = 0; i < info.length; i++) {
		// for (int j = 0; j < info[i].length; j++) {
		// System.out.print(info[i][j] + ",");
		// }
		// System.out.println();
		// }

		String[][] dimx = new String[][] { dim1, dim2,dim3 };
		String[][] tx = new String[][] { t1, t2 ,t3};

		String[][] info2 = adeptDimTarCommon(dimx, tx);
		for (int i = 0; i < info2.length; i++) {
			for (int j = 0; j < info2[i].length; j++) {
				System.out.print(info2[i][j] + ",");
			}
			System.out.println();
		}

	}

}
