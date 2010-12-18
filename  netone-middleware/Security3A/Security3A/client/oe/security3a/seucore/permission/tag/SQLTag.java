package oe.security3a.seucore.permission.tag;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;


import oe.frame.orm.OrmerEntry;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class SQLTag extends SimpleTagSupport {
	private String sql;

	private String ds;

	private String dataname;

	public void doTag() throws JspException {
		Connection con = null;
		if (ds == null || ds.equals("")) {
			throw new RuntimeException("数据源不允许为空");
		} else {
			ResourceRmi rsrmi;
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = rsrmi.loadResourceByNatural(ds);
				String extendattribute = upo.getExtendattribute();
				if (extendattribute == null || extendattribute.equals("")) {
					con = OrmerEntry.fetchDS().getConnection();
				} else {
					String[] extInfo = extendattribute.split("#");
					if (extInfo.length != 4) {
						throw new RuntimeException("无效数据源:"+ds);
					}
					con = DbTools.fetchConnection(extInfo[0], extInfo[1],
							extInfo[2], extInfo[3]);
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List data = DbTools.queryData(sql, con);

		getJspContext().setAttribute(dataname, data);

	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

}
