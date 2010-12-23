package oe.bi.etl.wizard.summarydata.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.bi.datasource.SumUtilIfc;
import oe.bi.etl.wizard.summarydata.SummaryDataForm;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class SumStep {
	/**
	 * 进入Step.jsp
	 * 
	 * @param so
	 * @param request
	 */
	public static void main(SummaryDataForm so, HttpServletRequest request) {
		SumUtilIfc sumutil = null;
		try {
			sumutil = (SumUtilIfc) RmiEntry.iv("biData");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List tablenamelist = null;
		try {
			tablenamelist = sumutil.getAllTableName(new String[] {
					so.getDriver(), so.getUrl(), so.getLoginname(),
					so.getPassword() }, sumutil.getDatabaseName(StringUtils
					.trim(so.getDriver()), StringUtils.trim(so.getUrl())));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 去获得所有动态表单的 表名和中文名的对应关系
		Map map = new HashMap();
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			// 遍历动态表单的根目录开始(这个做法性能不高后面需要改进)
			List list = rmi.subResourceByNaturalname("BUSSFORM.BUSSFORM");
			// 保存动态表单的表名和中文的对应

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				String naturalname = object.getNaturalname();
				String tablename = StringUtils.substringAfterLast(naturalname,
						".");
				map.put(tablename.toLowerCase(), object.getName());
			}
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

		List listTablename = new ArrayList();
		for (Iterator iterator = tablenamelist.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			if (map.containsKey(object)) {
				object = map.get(object) + ":" + object;
			} else {
				object = "库表:" + object;
			}
			listTablename.add(object);
		}

		request.setAttribute("tablenamelist", listTablename);
	}
}
