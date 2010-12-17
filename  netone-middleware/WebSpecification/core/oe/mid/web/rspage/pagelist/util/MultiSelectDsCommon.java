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
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.beanutils.BeanUtils;

/**
 * ����ѡ���������Դʵ��,������Ҫ���ݸ�AjaxServiceSvl?class= ����ʹ�� <br>
 * �����ͨ��,����ѡ��������һ���ڵ�
 * 
 * 5-17�޶��������˲���selectvalue ���Ը��ݲ������������ص�ֵ��֮ǰĬ����naturalname�����ڿ���������������
 * 
 * @author chen.jia.xun
 * 
 * 2009-3-8�޶���������Դ��ȫ�ж�  wu.shang.zhan
 */
public class MultiSelectDsCommon implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap map)
			throws Exception {
		ResourceRmi rsrmi = null;
		StringBuffer sb = new StringBuffer();
		Security sec = new Security(request);
		try {

			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			List<UmsProtectedobject> list = rsrmi.subResource(map
					.getParameter("id"));
			String selectColumn = request.getParameter("selectvalue");
			if (selectColumn == null || selectColumn.equals("")) {
				selectColumn = "naturalname";
			}

			for (UmsProtectedobject cl : list) {

				if (!sec.check(cl.getNaturalname(), LogUtil._READ)) {
					// û��Ȩ�ޣ����˵�
				} else {
					sb.append("name=" + cl.getName() + ","); // ����
					String naturalname = null;
					try {
						naturalname = BeanUtils.getProperty(cl, selectColumn);
					} catch (Exception e) {
						naturalname = cl.getNaturalname();
					}
					sb.append("naturalname=" + naturalname + ";"); // 
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
