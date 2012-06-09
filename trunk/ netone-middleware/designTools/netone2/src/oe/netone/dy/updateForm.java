package oe.netone.dy;

import java.awt.print.Book;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class updateForm {
	// 修改表单的方法
	public boolean updateform(String formid, String formname,
			String styinfo, String subform, String Description,String bussdata,String busstype,String time)
			throws RemoteException, MalformedURLException, NotBoundException {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm csForm = dysc.loadForm(formid); // 获取ID
		csForm.setFormname(formname); // 表名
		csForm.setStyleinfo(styinfo);// 风格
		csForm.setSubform(subform);// 子表单
		csForm.setDimdata(bussdata);
		csForm.setDimlevel(busstype); 
		csForm.setTimelevel(time);
		csForm.setDescription(csForm.getTablename());// 描述
		Boolean fal=dys.updateForm(csForm);
		
		// 增加扩展属性的展示 主要针对 html 头信息的追加
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formid);
		List listq = rsrmi.queryObjectProtectedObj(upo, null, 0, 1, "");
		if (listq != null && listq.size() == 1) {
			UmsProtectedobject upox = (UmsProtectedobject) listq.get(0);
			//upox.setDescription(busForm.getExtendattribute());
			upox.setReference(Description);
			upox.setName(formname);
			rsrmi.updateResource(upox);
		}
		return fal;
	}

	// 获取表单信息
	public List selcetform(String formid) throws RemoteException,
			MalformedURLException, NotBoundException {
		List formlist = new ArrayList();
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm csForm = dysc.loadForm(formid); // 获取ID

		formlist.add(csForm);

		return formlist;
	}

}
