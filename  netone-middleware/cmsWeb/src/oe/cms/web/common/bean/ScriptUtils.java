package oe.cms.web.common.bean;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.cms.service.CmsService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

public class ScriptUtils {

	/**
	 * ������� OecScript �ű������������
	 * 
	 * @param cellid
	 *            ����ID
	 * @param naturalname
	 *            ������
	 * @param action
	 *            �ű�ִ�н�����ύĿ��URL
	 * @param belongtocellid
	 *            �ű���ִ�н���Ƿ������ҳ�У��������ô����ҳ��ID

	 * @return
	 */
	public static String addScriptView(String cellid, String naturalname,
			String action, String belongtocellid,String extValue) {
		String script = scriptExpress(cellid);
		// ������ʽ��,���Ҵ�����ʽ��ʾ��Ϣ

		String expressionPersent = StringUtils.substringBetween(script, "<!--",
				"-->");
		expressionPersent = expressionPersent == null ? "" : "<em>"
				+ expressionPersent + "</em>";

		StringBuffer htmlinfo = new StringBuffer(
				"<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\"></head><body>");
		htmlinfo.append(expressionPersent + "<br><br>");

		String expressionBody = StringUtils.substringAfter(script, "-->");
		String expressionParamer = StringUtils.substringBetween(expressionBody,
				"{", "}");
		String[] expressionParamers = StringUtils
				.split(expressionParamer, "&+");

		StringBuffer butParamLink = new StringBuffer();
		StringBuffer tableinfo = new StringBuffer("<table><form action='"
				+ action + "' method='post'");

		for (int i = 0; i < expressionParamers.length; i++) {

			String[] paramer = StringUtils.split(expressionParamers[i], ":=");
			tableinfo.append("<tr><td>" + paramer[1] + "</td><td>");
			String htmlparamerId = paramer[0];
			tableinfo.append("<input type='text' name='" + htmlparamerId
					+ "'/></td></tr>");

			butParamLink.append(htmlparamerId + ",");
		}
		tableinfo.append("<input type='hidden' name='#paraminfo' value='"
				+ butParamLink + "'/>");
		tableinfo.append("<input type='hidden' name='#naturalname' value='"
				+ naturalname + "'/>");
		tableinfo.append("<input type='hidden' name='#belongtocellid' value='"
				+ belongtocellid + "'/>");
		tableinfo.append("<input type='hidden' name='#extvalue' value='"
				+ extValue + "'/>");

		tableinfo.append("</table><br><br>");

		String button1 = "<input type='submit' value='ȷ��'/>";

		return htmlinfo.toString() + tableinfo + button1 + "</body></html>";

	}

	public static String scriptExpress(String jppid) {
		if (jppid == null || jppid.equals("")) {
			return "";
		}

		CmsService cmsServicex;
		try {
			cmsServicex = (CmsService) RmiEntry.iv("cmshandle");
			String script = cmsServicex.fetchJppTemplate(jppid);
			// script = WebStr.encode(request, script);
			if (script != null) {
				String key = StringUtils.substringBetween(script, "$:", "{");
				if (key != null && !key.equals("")) {
					script = StringUtils.replace(script, "$:" + key + "{", "$:"
							+ jppid + "{");
				}
			}
			return script;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new RuntimeException();
	}

}
