package oe.cms.xhtml.core.graph;

import java.rmi.RemoteException;

import oe.cms.EnvEntryInfo;





public class Graph2SeveletAdpet {

	public static String fetchGraphX(String dimvaluelist,
			String targetvaluelist[], String targetvaluelistR[],
			String dimName, String[] targetname, String[] targetnameR,
			String charttype, String title, String xoffset, String yoffset) {

		String url=null;
		try {
			url = EnvEntryInfo.env.fetchEnvValue("jppWebBase") + Graph2Builder._Context;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer butTar = new StringBuffer();
		for (int i = 0; i < targetvaluelist.length; i++) {
			butTar.append("&targetvaluelist" + i + "=" + targetvaluelist[i]);
		}

		StringBuffer butTarName = new StringBuffer();
		for (int i = 0; i < targetname.length; i++) {
			butTarName.append("&targetname" + i + "=" + targetname[i]);
		}

		StringBuffer butTarR = new StringBuffer();
		for (int i = 0; i < targetvaluelistR.length; i++) {
			butTarR.append("&targetvaluelistR" + i + "=" + targetvaluelistR[i]);
		}

		StringBuffer butTarRName = new StringBuffer();
		for (int i = 0; i < targetnameR.length; i++) {
			butTarRName.append("&targetnameR" + i + "=" + targetnameR[i]);
		}

		url += "?dimvaluelist=" + dimvaluelist + "&dimName=" + dimName + butTar
				+ butTarR + butTarName + butTarRName + "&charttype="
				+ charttype + "&title=" + title + "&xoffset=" + xoffset
				+ "&yoffset=" + yoffset;

		StringBuffer sb = new StringBuffer();
		sb.append("<img src='" + url + "' ");

		sb.append(" width='" + xoffset + "'");
		sb.append(" height='" + yoffset + "' ");
		sb.append(" ></img>");

		return sb.toString();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
