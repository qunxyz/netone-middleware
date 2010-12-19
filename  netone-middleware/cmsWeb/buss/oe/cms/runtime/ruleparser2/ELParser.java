package oe.cms.runtime.ruleparser2;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.cms.CmsEL;
import oe.cms.CmsEntry;
import oe.cms.EnvEntryInfo;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.infocell.JppMiddwareDIY;
import oe.cms.runtime.ruleparser.SyntaxInfo2;

import org.apache.commons.lang.StringUtils;

public class ELParser {
	// ����Ƿ���JPP�Ķ�̬��������html�ľ�̬����
	private static boolean checkIfInJppScript(String func, String param) {
		// ȡ��func���ʽ��param֮ǰ����Ϣ
		String subStr = StringUtils.substringBefore(func, param);
		if (subStr != null && subStr.trim().length() > 0) {
			// ��JPP�ű�ͷ
			int findJppAhead = StringUtils.lastIndexOf(subStr,
					SyntaxInfo2.XSCRIPT_STA);
			// ��JPP�ű�β
			int findJppEnd = StringUtils.lastIndexOf(subStr,
					SyntaxInfo2.XSCRIPT_END);
			if (findJppAhead != -1 && findJppAhead > findJppEnd) {
				// ˵����param��JPP�Ķ�̬����
				return true;
			}
			return false;
		} else {
			return false;
		}

	}

	private static String checkRecursion(String tmpInfocell) {
		int st1 = StringUtils.indexOf(tmpInfocell, CmsEL._EL_FUNC_PARAM_START);
		int en1 = StringUtils.indexOf(tmpInfocell, CmsEL._EL_FUNC_PARAM_END);
		String subStr = StringUtils.substring(tmpInfocell, st1, en1 + 1);
		if (StringUtils.indexOf(subStr, CmsEL._EL_FUNC) > 0) {
			return CmsEL._EL_FUNC
					+ StringUtils.substringAfter(subStr, CmsEL._EL_FUNC);
		} else {
			return null;
		}

	}

//	/**
//	 * JPP-EL�﷨�Ż���֧���Զ����ʽ <br>
//	 * <br>
//	 * ELģ�嶨�壺��envconfig.properties�����ö��� ��ʽ:�����html/js��д������Ҫ��Ӧ���н����Ĳ�����Ҫ�����
//	 * ${������} <br>
//	 * <br>
//	 * Ӧ��EL�� $:ELģ���е�ģ����{key1:=value1&key2:=value2......}
//	 * 
//	 * @param infocell
//	 * @return
//	 */
//	public static String parser(String infocell, String cellid, String basepath) {
//		String tmpInfocell = infocell;
//		// ���ҳ���϶����EL���ʽ
//		String key = StringUtils.substringBetween(tmpInfocell, CmsEL._EL_FUNC,
//				CmsEL._EL_FUNC_PARAM_START);
//		long curintbase = System.currentTimeMillis();
//		while (key != null) {
//
//			String recursion = checkRecursion(tmpInfocell);
//			int loopTime = 0;
//			while (recursion != null) {
//				String preDo = parser(recursion, cellid, basepath);
//				tmpInfocell = StringUtils.replaceOnce(tmpInfocell, recursion,
//						preDo);
//				infocell = StringUtils.replaceOnce(infocell, recursion, preDo);
//				recursion = checkRecursion(tmpInfocell);
//				loopTime++;
//				try {
//					if (loopTime > Integer.parseInt(EnvEntryInfo.env
//							.fetchEnvValue("jppScriptLoopLimit"))) {
//						return null;
//					}
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (RemoteException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			// ���ҳ��ELģ��Ԫ����
//			String func = null;
//			try {
//				func = EnvEntryInfo.env.fetchJppScriptModel(key);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (func == null) {
//				JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
//						.fetchDao("jppMiddwareDIY");
//				TCmsJppmidware tjm = (TCmsJppmidware) jppMiddwareDIY.load(key);
//				if (tjm != null) {
//					func = tjm.getJppmidscript();
//				}
//			}
//			if (func == null) {
//				return "��Ч�ű�����ȱ�ٽű�";
//			}
//			// ���ҳ��ELģ��Ԫ�صĲ���
//
//			String params = StringUtils.substringBetween(tmpInfocell,
//					CmsEL._EL_FUNC_PARAM_START, CmsEL._EL_FUNC_PARAM_END);
//			String paramFormat = params == null ? "" : params.trim();
//			// ��ELģ��Ԫ�ظ�ֵ
//			String[] paramArr = splitStr(paramFormat,
//					CmsEL._EL_FUNC_PARAM_SPLIT);
//			// Ԥ�ȴ����з���\r\n
//			for (int i = 0; i < paramArr.length; i++) {
//				paramArr[i] = StringUtils.replace(paramArr[i], "\r\n", "");
//				paramArr[i] = paramArr[i].trim();
//			}
//			// �����ڲ�����autoid
//			func = dealwithCoreParamAutoIdx(func, curintbase++);
//
//			// Ԥ����������
//
//			func = dealwithCoreParamEnvInfo(func, basepath);
//
//			for (int i = 0; i < paramArr.length; i++) {
//
//				String[] tmp = splitStr(paramArr[i], CmsEL._EL_FUNC_SET_PARAM);
//				String keyIs = tmp[0];
//				String valueIs = "";
//				if (tmp.length == 2) {
//					valueIs = tmp[1];
//				}
//				String setValue = null;
//				// ����Ƿ���JPP�Ķ�̬��������html�ľ�̬����,�����JPP��̬������Ҫ�Զ�����������˫����
//				boolean jppScriptIs = checkIfInJppScript(func, keyIs);
//				if (checkArr(valueIs)) {
//					valueIs = dealWithArrValue(valueIs, jppScriptIs);
//					setValue = CmsEL._EL_TEMPLATE_PARAM_ARR_START + keyIs
//							+ CmsEL._EL_TEMPLATE_PARAM_ARR_END;
//				} else {
//					valueIs = dealWithValue(valueIs, jppScriptIs);
//					setValue = CmsEL._EL_TEMPLATE_PARAM_START + keyIs
//							+ CmsEL._EL_TEMPLATE_PARAM_END;
//				}
//
//				func = StringUtils.replace(func, setValue, valueIs);
//			}
//
//			// ��ԭҳ��EL���ʽ
//			String realExpress = CmsEL._EL_FUNC + key
//					+ CmsEL._EL_FUNC_PARAM_START + params
//					+ CmsEL._EL_FUNC_PARAM_END;
//			// �ø�ֵ����ELģ��Ԫ�أ��滻ҳ���ϵ�EL���ʽ
//			infocell = StringUtils.replace(infocell, realExpress, func);
//			// ����Ѱ���µ�δ�������ҳ��ELģ��Ԫ��
//			tmpInfocell = StringUtils.substringAfter(tmpInfocell, "}");
//			key = StringUtils.substringBetween(tmpInfocell, "$:", "{");
//		}
//		// �����ڲ�����cellid
//		infocell = dealwithCoreParamCellId(infocell, cellid);
//		return infocell;
//	}

	/**
	 * JPP-EL�﷨�Ż���֧���Զ����ʽ <br>
	 * <br>
	 * ELģ�嶨����ģ�������ʵ��,���к�������ʽ�� ${������} <br>
	 * <br>
	 * Ӧ��EL�� $:naturalname{key1:=value1&key2:=value2......}
	 * 
	 * @param infocell
	 * @return
	 */
	public static String parserUseNatrualname(String infocell, String cellid,
			String basepath) {
		String tmpInfocell = infocell;
		// ���ҳ���϶����EL���ʽ
		String name = StringUtils.substringBetween(tmpInfocell, CmsEL._EL_FUNC,
				CmsEL._EL_FUNC_PARAM_START);
		long curintbase = System.currentTimeMillis();
		while (name != null) {

			String recursion = checkRecursion(tmpInfocell);
			int loopTime = 0;
			while (recursion != null) {
				String preDo = parserUseNatrualname(recursion, cellid, basepath);
				tmpInfocell = StringUtils.replaceOnce(tmpInfocell, recursion,
						preDo);
				infocell = StringUtils.replaceOnce(infocell, recursion, preDo);
				recursion = checkRecursion(tmpInfocell);
				loopTime++;
				try {
					if (loopTime > Integer.parseInt(EnvEntryInfo.env
							.fetchEnvValue("jppScriptLoopLimit"))) {
						return null;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// ���ҳ��ELģ��Ԫ����
			String func = null;
			JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
					.fetchDao("jppMiddwareDIY");
			TCmsJppmidware tjm = (TCmsJppmidware) jppMiddwareDIY
					.loadByNaturalname(name);
			if (tjm != null) {
				func = tjm.getJppmidscript();
			}
			if (func == null) {
				return "��Ч�ű�����ȱ�ٽű�";
			}
			// ���ҳ��ELģ��Ԫ�صĲ���

			String params = StringUtils.substringBetween(tmpInfocell,
					CmsEL._EL_FUNC_PARAM_START, CmsEL._EL_FUNC_PARAM_END);
			String paramFormat = params == null ? "" : params.trim();
			// ��ELģ��Ԫ�ظ�ֵ
			String[] paramArr = splitStr(paramFormat,
					CmsEL._EL_FUNC_PARAM_SPLIT);
			// Ԥ�ȴ����з���\r\n
			for (int i = 0; i < paramArr.length; i++) {
				paramArr[i] = StringUtils.replace(paramArr[i], "\r\n", "");
				paramArr[i] = paramArr[i].trim();
			}
			// �����ڲ�����autoid
			func = dealwithCoreParamAutoIdx(func, curintbase++);

			// Ԥ����������

			func = dealwithCoreParamEnvInfo(func, basepath);

			for (int i = 0; i < paramArr.length; i++) {

				String[] tmp = splitStr(paramArr[i], CmsEL._EL_FUNC_SET_PARAM);
				String keyIs = tmp[0];
				String valueIs = "";
				if (tmp.length == 2) {
					valueIs = tmp[1];
				}
				String setValue = null;
				// ����Ƿ���JPP�Ķ�̬��������html�ľ�̬����,�����JPP��̬������Ҫ�Զ�����������˫����
				boolean jppScriptIs = checkIfInJppScript(func, keyIs);
				if (checkArr(valueIs)) {
					valueIs = dealWithArrValue(valueIs, jppScriptIs);
					setValue = CmsEL._EL_TEMPLATE_PARAM_ARR_START + keyIs
							+ CmsEL._EL_TEMPLATE_PARAM_ARR_END;
				} else {
					valueIs = dealWithValue(valueIs, jppScriptIs);
					setValue = CmsEL._EL_TEMPLATE_PARAM_START + keyIs
							+ CmsEL._EL_TEMPLATE_PARAM_END;
				}

				func = StringUtils.replace(func, setValue, valueIs);
			}

			// ��ԭҳ��EL���ʽ
			String realExpress = CmsEL._EL_FUNC + name
					+ CmsEL._EL_FUNC_PARAM_START + params
					+ CmsEL._EL_FUNC_PARAM_END;
			// �ø�ֵ����ELģ��Ԫ�أ��滻ҳ���ϵ�EL���ʽ
			infocell = StringUtils.replace(infocell, realExpress, func);
			// ����Ѱ���µ�δ�������ҳ��ELģ��Ԫ��
			tmpInfocell = StringUtils.substringAfter(tmpInfocell, "}");
			name = StringUtils.substringBetween(tmpInfocell, "$:", "{");
		}
		// �����ڲ�����cellid
		infocell = dealwithCoreParamCellId(infocell, cellid);
		return infocell;
	}

	final static String cellidParam = "%{cellid}";

	private static String dealwithCoreParamCellId(String infocell, String cellid) {
		cellid = dealWithValue(cellid, true);
		return StringUtils.replace(infocell, cellidParam, cellid);
	}

	final static String AUTOID = "%{autoid}";

	private static String dealwithCoreParamAutoIdx(String infocellPre,
			long idBase) {
		String autoid = "autoid" + idBase;
		return StringUtils.replace(infocellPre, AUTOID, autoid);
	}

	//
	// final static String CURLx = "%{$curl}";
	//
	// final static String CPATH = "%{cpath}";
	//
	// final static String CIP = "%{$localIp}";
	//
	// final static String REMOTESER = "%{$remoteServer}";

	private static String dealwithCoreParamEnvInfo(String infocellPre,
			String basepath) {
		// �滻�ڲ�����

		infocellPre = StringUtils.replace(infocellPre, "%{$curl}", basepath);

		try {
			String[][] envinfo = EnvEntryInfo.env.fetchAllEnvSystemInfo();
			for (int i = 0; i < envinfo.length; i++) {
				infocellPre = StringUtils.replace(infocellPre, envinfo[i][0],
						envinfo[i][1]);
			}
			return infocellPre;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private static boolean checkArr(String value) {
		if (value.indexOf(",") >= 0) {
			return true;
		}
		return false;
	}

	private static String dealWithValue(String value, boolean needStr) {
		if (needStr) {
			return "\"" + value + "\"";
		}
		return value;
	}

	private static String dealWithArrValue(String value, boolean isjppScriptIs) {
		if (value.indexOf(",") >= 0) {
			if (value.indexOf(";") == -1) {// ˵����1ά����

				return "new String[]" + link(value, isjppScriptIs);

			} else {// ˵����2ά����
				String[] valArr = value.split(";");

				StringBuffer but = new StringBuffer();
				for (int i = 0; i < valArr.length; i++) {
					but.append("," + link(valArr[i], isjppScriptIs));
				}
				String newLink = link(but.substring(1), false);
				return "new String[][]" + newLink;
			}
		}
		return value;
	}

	private static String link(String value, boolean useStr) {
		String[] valueArr = value.split(",");
		StringBuffer but = new StringBuffer();
		String str = "";
		if (useStr) {
			str = "\"";
		}
		for (int i = 0; i < valueArr.length; i++) {
			but.append("," + str + valueArr[i] + str);
		}
		return "{" + but.substring(1) + "}";
	}

	private static String[] splitStr(String info, String tip) {
		List list = new ArrayList();
		String preInfo = StringUtils.substringBefore(info, tip);
		while (preInfo != null && preInfo.trim().length() > 0) {
			list.add(preInfo);
			info = StringUtils.substringAfter(info, tip);
			preInfo = StringUtils.substringBefore(info, tip);
		}
		return (String[]) list.toArray(new String[0]);
	}

	public static void main(String[] arg) {
		int[][] abc = { { 1, 2 }, { 3, 4 } };
	}
}
