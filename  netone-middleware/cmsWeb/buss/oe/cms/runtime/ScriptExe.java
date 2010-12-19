package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsEntry;
import oe.cms.runtime.ruleparser2.ELParser;

public class ScriptExe {

	public static String[] executeScriptCore(String cellid, String bodyinfo,
			HttpServletRequest request) {
		InfoCellParser infoCellParser = (InfoCellParser) CmsEntry
				.fetchBean("infoCellParser");
		infoCellParser.setRequestInfo(request);
		String infoCell = "";
		String exeRs = "1";
		try {
			// 先处理EL脚本
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";

			bodyinfo = ELParser
					.parserUseNatrualname(bodyinfo, cellid, basePath);

			infoCell = infoCellParser.performInfoCellParser(bodyinfo);

		} catch (Exception e) {
			e.printStackTrace();
			infoCell = "出错了!<br>" + e.getMessage();
			exeRs = "0";
		}
		return new String[] { exeRs, infoCell };
	}

}
