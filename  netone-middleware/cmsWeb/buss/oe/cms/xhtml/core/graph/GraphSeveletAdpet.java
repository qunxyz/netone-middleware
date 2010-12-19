package oe.cms.xhtml.core.graph;

import java.rmi.RemoteException;

import oe.cms.EnvEntryInfo;

/**
 * 创建多指标单柱对比图
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class GraphSeveletAdpet {

	public static String fetchGraph(String dimvaluelist,
			String[] targetvaluelist, String dimName, String[] targetname,
			String charttype, String title, String xoffset, String yoffset) {

		StringBuffer but1 = new StringBuffer();
		StringBuffer but2 = new StringBuffer();
		if (targetvaluelist != null && targetname != null) {
			for (int i = 0; i < targetvaluelist.length; i++) {
				String tar = "&tg" + i + "=" + targetvaluelist[i];
				String tarn = "&na" + i + "=" + targetname[i];
				but1.append(tar);
				but2.append(tarn);
			}
		}
		String tarname = but2.toString();

		String url = null;
		try {
			url = EnvEntryInfo.env.fetchEnvValue("jppWebBase")
					+ GraphBuilder._Context + "?dimvaluelist=" + dimvaluelist
					+ but1 + "&dimName=" + dimName + tarname + "&charttype="
					+ charttype + "&title=" + title + "&xoffset=" + xoffset
					+ "&yoffset=" + yoffset;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<img src='" + url + "' ");
		sb.append(" width='" + xoffset + "'");
		sb.append(" height='" + yoffset + "' ");
		sb.append(" ></img>");

		return sb.toString();
	}
}
