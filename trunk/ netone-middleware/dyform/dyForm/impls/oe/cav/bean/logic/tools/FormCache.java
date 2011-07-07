package oe.cav.bean.logic.tools;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import oe.cav.bean.logic.form.TCsForm;

import oe.frame.web.WebCache;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class FormCache {

	public static void initCache(String formcode) {
		if (WebCache.containCache(formcode)) {
			WebCache.removeCache(formcode);
		}
		try {
			DyFormDesignService dys = (DyFormDesignService) RmiEntry
					.iv("dydesign");

			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");

			TCsForm busForm = dysc.loadForm(formcode);

			// 增加扩展属性的展示 主要针对 html 头信息的追加
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(formcode);
			List listq = rsrmi.queryObjectProtectedObj(upo, null, 0, 1, "");
			if (listq != null && listq.size() == 1) {
				busForm.setExtendattribute(((UmsProtectedobject) listq.get(0))
						.getDescription());
				busForm.setDescription(((UmsProtectedobject) listq.get(0))
						.getReference());
			}
			WebCache.setCache(formcode, busForm, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String columncode = formcode + "column";
		WebCache.removeCache(columncode);
	}

	public static TCsForm getCache(String formcode) {
		if (!WebCache.containCache(formcode)) {
			initCache(formcode);
		}
		return (TCsForm) WebCache.getCache(formcode);
	}

	public static List getColumn(String formcode) {
		String columncode = formcode + "column";
		if (!WebCache.containCache(columncode)) {
			try {
				DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
				List list = dysc.fetchColumnList(formcode);
				WebCache.setCache(columncode, list, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (List) WebCache.getCache(columncode);

	}
}
