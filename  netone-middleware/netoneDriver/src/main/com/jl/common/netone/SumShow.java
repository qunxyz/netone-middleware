package  com.jl.common.netone;
 
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import oe.bi.datasource.SumUtilIfc;
import oe.rmi.client.RmiEntry;
import org.apache.commons.lang.StringUtils;

public class SumShow {
	/**
	 *视图表单 返回sql语句 字段、类型
	 * xuwei(2012-4-25)
	 * @param request
	 * @param response
	 */

	public String Viewform(String driver1,String url1,String loginname1,String password1,String formid){
		
		SumUtilIfc sumutil=null;
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
		String driver = StringUtils.trim(driver1);
		String url = StringUtils.trim(url1);
		String loginname = StringUtils.trim(loginname1);
		String password = StringUtils.trim(password1);
		String tablename = StringUtils.trim(formid);

		List list = null;
		try {
			list = sumutil.getColumnNameByTableName(new String[] { driver, url,
					loginname, password }, tablename);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?><sql>");
		StringBuffer sb2 = new StringBuffer("select ");
		int i = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (LinkedHashMap) iterator.next();
			String columnName = map.get("name").toString();
			if (i != list.size() - 1) {
				sb2 = sb2.append(columnName + " " + columnName + ",");
			} else {
				sb2 = sb2.append(columnName + " " + columnName + " ");
			}
			i++;
			String attributeType = map.get("type").toString();
			sb = sb.append("<option value=\"" + columnName + "\">");
			sb = sb.append("字段名:" + columnName + " 字段类型:" + attributeType);
			sb = sb.append("</option>");
		}
		sb2 = sb2.append("from " + tablename + " where 1=1");
		sb2=sb2.append("</select></sql>");
		sb = sb.append("<select>");
		 return (sb.toString()+ sb2.toString()).trim();
	}
}
