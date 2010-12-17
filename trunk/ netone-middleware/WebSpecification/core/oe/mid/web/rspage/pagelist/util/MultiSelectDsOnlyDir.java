package oe.mid.web.rspage.pagelist.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;


/**
 * ����ѡ���������Դʵ��,������Ҫ���ݸ�AjaxServiceSvl?class= ����ʹ�� <br>
 * �����ͨ��,����ѡ��������һ���ڵ�, ���˵�Ŀ¼
 * 
 * @author chen.jia.xun
 * 
 */
public class MultiSelectDsOnlyDir implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap map)
			throws Exception {
		ResourceRmi rsrmi = null;
		StringBuffer sb = new StringBuffer();
		try {
			String selectColumn = request.getParameter("selectvalue");
			if (selectColumn == null || selectColumn.equals("")) {
				selectColumn = "naturalname";
			}

			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			List<UmsProtectedobject> list = rsrmi.subResource(map
					.getParameter("id"));

			for (UmsProtectedobject cl : list) {
				if (ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(cl
						.getInclusion())) {
					// ���˵�Ŀ¼
					sb.append("name=" + cl.getName() + ","); // ����
					String naturalname = null;
					try {
						naturalname = BeanUtils.getProperty(cl, selectColumn);
					} catch (Exception e) {
						naturalname = cl.getNaturalname();
					}
					sb.append("naturalname=" + naturalname + ";"); // }

				}
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
