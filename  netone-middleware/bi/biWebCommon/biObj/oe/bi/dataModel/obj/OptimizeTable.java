package oe.bi.dataModel.obj;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class OptimizeTable {

	private String id;

	private String name;

	private String targetmap;

	private String dimensionmap;

	private String extendattribute;

	private String description;

	public Map fetchTargetmap() {
		Map targetInfo = new HashMap();
		String[] targetTmp = targetmap.split(";");
		for (int i = 0; i < targetTmp.length; i++) {
			StringTokenizer st = new StringTokenizer(targetTmp[i], "[");
			if (st.hasMoreTokens()) {
				StringTokenizer stnext = new StringTokenizer(st.nextToken(),
						"]");
				String[] targetCompare = stnext.nextToken().split(":");
				targetInfo.put(targetCompare[0], targetCompare[1]);
			}
		}
		return targetInfo;
	}

	public Map fetchTargetmapKeyName() {
		Map targetInfo = new HashMap();
		String[] targetTmp = targetmap.split(";");
		for (int i = 0; i < targetTmp.length; i++) {
			StringTokenizer st = new StringTokenizer(targetTmp[i], "[");
			if (st.hasMoreTokens()) {
				StringTokenizer stnext = new StringTokenizer(st.nextToken(),
						"]");
				String[] targetCompare = stnext.nextToken().split(":");
				if (targetCompare.length == 3) {
					targetInfo.put(targetCompare[0], targetCompare[2]);
				} else {
					targetInfo.put(targetCompare[0], null);
				}
			}
		}
		return targetInfo;
	}

	public Map fetchDimensionmap() {
		Map dimInfo = new HashMap();
		String[] dimInfoTmp = dimensionmap.split(";");
		for (int i = 0; i < dimInfoTmp.length; i++) {
			StringTokenizer st = new StringTokenizer(dimInfoTmp[i], "[");
			String[] diminfotmp = new String[3];
			if (st.hasMoreTokens()) {
				StringTokenizer stnext = new StringTokenizer(st.nextToken(),
						"]");
				diminfotmp[0] = stnext.nextToken();
			}
			if (st.hasMoreTokens()) {
				StringTokenizer stnext = new StringTokenizer(st.nextToken(),
						"]");
				diminfotmp[1] = stnext.nextToken();
			}
			if (st.hasMoreTokens()) {
				StringTokenizer stnext = new StringTokenizer(st.nextToken(),
						"]");
				diminfotmp[2] = stnext.nextToken();
			}
			dimInfo.put(diminfotmp[0], diminfotmp[1] + "," + diminfotmp[2]);
		}
		return dimInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDimensionmap() {
		return dimensionmap;
	}

	public void setDimensionmap(String dimensionmap) {
		this.dimensionmap = dimensionmap;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetmap() {
		return targetmap;
	}

	public void setTargetmap(String targetmap) {
		this.targetmap = targetmap;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
