package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.ruleparser2.ELParser;
import oe.frame.web.WebCache;

public class XHtmlCachepoolJsp {

	public static void removeInfoCacheAll() {
		WebCache.initCache();
	}

	public static String fetchInfo(TCmsInfocell info, HttpServletRequest req) {

		throw new RuntimeException("缓存保存取消");

		// if (info == null) {
		// return "";
		// }
		// if (!CellInfo._IN_TIME.equals(info.getIntime())) {
		// return fetchInfoCore(info, req)[1];
		// } else if (!CmsCache.getCache().containsKey(info.getCellid())) {
		// String[] infoRs = fetchInfoCore(info, req);
		// if (infoRs[0].equals("1")) {
		// CmsCache.getCache().put(info.getCellid(), infoRs[1]);
		// }
		// return infoRs[1];
		// } else {
		// return (String) CmsCache.getCache().get(info.getCellid());
		// }
	}

	private static String[] fetchInfoCore(TCmsInfocell info,
			HttpServletRequest request) {
		InfoCellParser infoCellParser = (InfoCellParser) CmsEntry
				.fetchBean("infoCellParser");
		infoCellParser.setRequestInfo(request);
		String infoCell = "";
		String exeRs = "1";
		try {
			String bodyinfo = info.getBody();
			// 先处理EL脚本
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";

			bodyinfo = ELParser.parserUseNatrualname(bodyinfo,
					info.getCellid(), basePath);

			infoCell = infoCellParser.performInfoCellParser(bodyinfo);

		} catch (Exception e) {
			infoCell = "出错了!<br>" + e.getMessage();
			exeRs = "0";
		}
		return new String[] { exeRs, infoCell };
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
