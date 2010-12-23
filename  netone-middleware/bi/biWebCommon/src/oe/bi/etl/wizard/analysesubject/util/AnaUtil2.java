package oe.bi.etl.wizard.analysesubject.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.TargetElement;
import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;


public class AnaUtil2 {
	/**
	 * 进入Step2.jsp或Done.jsp页面或filter1.jsp页面或filter2.jsp页面
	 * 
	 * @param fo
	 * @param request
	 */
	public static void main(AnalyseSubjectForm fo, HttpServletRequest request) {
		String[] tgname = null;
		String[] tgid = null;
		String tgids = fo.getTgids();
		List<String> tgnamelist = new ArrayList<String>();
		List<String> tgidlist = new ArrayList<String>();
		List<String> tgtypelist = new ArrayList<String>();
		if (tgids != null && !"".equals(tgids)) {// 进入Step21.jsp或Done.jsp页面
			tgid = tgids.split(",");
			String tgnames = fo.getTgnames();
			String enctgnames = tgnames;
			tgname = enctgnames.split(",");
			for (int i = 0; i < tgid.length; i++) {
				if (tgid[i].indexOf("$nofilter$") < 0) {
					tgnamelist.add(StringUtils.substringBefore(tgname[i], "{"));
					tgidlist.add(tgid[i]);
					tgtypelist.add(StringUtils.substringBetween(tgname[i], "{", "}"));
				}
			}
		} else {// 进入filter1.jsp，filter2.jsp
			ResourceRmi rsrmi = null;
			try {
				WizardDao wd = (WizardDao) RmiEntry.iv("bihandle");
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				String chkid = request.getParameter("openid");
				UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
				String lsh = upo.getExtendattribute();
				ChoiceInfo choiceinfo = wd.fromXml(lsh);
				List TargetElement = choiceinfo.getTargetElement();
				for (Iterator iter = TargetElement.iterator(); iter.hasNext();) {
					TargetElement te = (TargetElement) iter.next();
					String id = te.getId();
					String nativeid = te.getNativeid();
					String name = te.getName();
					String type = te.getType();
					tgidlist.add(id + "{" + nativeid + "}");
					tgnamelist.add(name);
					tgtypelist.add(type);
				}
			} catch (NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("tgtypelist", tgtypelist);
		request.setAttribute("tgnamelist", tgnamelist);
		request.setAttribute("tgidlist", tgidlist);
		TargetFiltObj tfo = new TargetFiltObj();
		request.setAttribute("_CHOICE_TOP", tfo._CHOICE_TOP);
		request.setAttribute("_CHOICE_TOP_VALUE", tfo._CHOICE_TOP_VALUE);
		request.setAttribute("_CHOICE_ORDER", tfo._CHOICE_ORDER);
		request.setAttribute("_CHOICE_ALARM", tfo._CHOICE_ALARM);
		request.setAttribute("_CHOICE_ALARM2", tfo._CHOICE_ALARM2);
		request.setAttribute("_CHOICE_ALARM3", tfo._CHOICE_ALARM3);
	}
}
