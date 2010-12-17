package oe.mid.web.rspage.pagelist.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * �ۺϹ���ֻ��ʾ�ļ��������ļ��У�
 * ͬʱ����Ҫ���ļ��ж���Ȩ��
 * 
 * Mar 8, 2009  8:00:27 PM<br>
 *
 * modify wu.shang.zhan<br>
 */
public class FetchResource {

	public static List todo(List list, String path, HttpServletRequest request) {
		// ɸѡ���
		List<UmsProtectedobject> newlist = new ArrayList<UmsProtectedobject>();
		Security sec = new Security(request);
		try {
			String rsLogicPath = path;
			//String realpath = evn.fetchEnvValue(rsLogicPath);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject tmp = (UmsProtectedobject) iter.next();
				if (StringUtils.contains(tmp.getActionurl(), rsLogicPath)) {
					tmp.setActionurl(StringUtils.replace(tmp.getActionurl(),
							rsLogicPath, ""));
				}
				if (!ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(tmp
						.getInclusion())
						&& sec.check(tmp.getNaturalname(), LogUtil._READ)) {
					newlist.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return newlist;
	}

}
